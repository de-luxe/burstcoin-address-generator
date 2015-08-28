/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 by luxe - https://github.com/de-luxe - BURST-LUXE-ZDVD-CX3E-3SM58
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package burstcoin.address.generator.core;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;
import burstcoin.address.generator.gui.result.ResultModel;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

/**
 * The type Generate actor.
 */
public class GenerateActor
  extends UntypedActor
{
  private final ActorRef workerPool;
  private long resultCount;
  private long lastAverage;

  /**
   * The interface Callback.
   */
  public interface Callback
  {
    /**
     * On result.
     *
     * @param resultModel the result model
     */
    void onResult(ResultModel resultModel);

    /**
     * Update progress.
     *
     * @param count the count
     * @param targetCount the target count
     * @param currentCount the current count
     * @param config the config
     * @param speed the speed
     */
    void updateProgress(double count, long targetCount, long currentCount, GeneratorConfig config, String speed);
  }

  private Date lastOutput;
  private long lastTries;

  private Date statLastOutput;
  private long statLastTries;

  private GeneratorConfig config;
  private Callback callback;

  private Set<String> jobKeys;
  private Map<String, Long> jobCountLookup;

  /**
   * Instantiates a new Generate actor.
   *
   * @param config the config
   * @param callback the callback
   */
  public GenerateActor(GeneratorConfig config, Callback callback)
  {
    this.config = config;
    this.callback = callback;

    // lookups
    jobKeys = new HashSet<>();
    jobCountLookup = new HashMap<>();

    // create pool of workers
    RoundRobinPool roundRobinPool = new RoundRobinPool(config.getThreads());
    workerPool = getContext().system().actorOf(roundRobinPool.props(Props.create(GenerateWorkActor.class)));

    // lets delegate some work
    for(int i = 0; i < config.getThreads(); i++)
    {
      String jobKey = UUID.randomUUID().toString();
      jobKeys.add(jobKey);
      workerPool.tell(new GenerateWorkMsg(jobKey, config), getSelf());
    }
  }

  @Override
  public void onReceive(Object message)
    throws Exception
  {
    if(message instanceof GenerateResultMsg)
    {
      GenerateResultMsg resultMessage = (GenerateResultMsg) message;
      ResultModel resultModel = resultMessage.getResult();
      resultModel.setTries(getCurrentCount(resultMessage.getJobKey(), resultModel.getTries()));
      callback.onResult(resultModel);
    }
    else if(message instanceof GeneratorStatusMsg)
    {
      GeneratorStatusMsg statusMsg = (GeneratorStatusMsg) message;

      long targetCount = config.getTriesPerThread() * config.getThreads();
      // update lookup
      long currentCount = getCurrentCount(statusMsg.getJobKey(), statusMsg.getCount());

      BigDecimal totalTries = new BigDecimal(targetCount);
      BigDecimal factor = BigDecimal.ONE.divide(totalTries, MathContext.DECIMAL32);
      BigDecimal progress = factor.multiply(new BigDecimal(currentCount));

      String speed = "- tries/ms";
      long triesPerMs = 0;
      if(statLastOutput != null && statLastTries > 0)
      {
        Date currentOutput = new Date();
        long timeInMs = currentOutput.getTime() - statLastOutput.getTime();
        long tries = currentCount - statLastTries;

        triesPerMs = tries / (timeInMs > 0 ? timeInMs : 1);
        statLastOutput = currentOutput;

        speed = String.valueOf(triesPerMs) + " tries/ms";

//                resultCount++;
//                lastAverage = (lastAverage * resultCount + triesPerMs) / resultCount-1;
      }
      else
      {
        statLastOutput = new Date();
//                resultCount = triesPerMs;
      }
//            speed += " ~"+lastAverage + " tries/ms";

      statLastTries = currentCount;

      callback.updateProgress(progress.doubleValue(), targetCount, currentCount, config, speed);
    }
  }

  private long getCurrentCount(String jobKey, long tries)
  {
    jobCountLookup.put(jobKey, tries);
    long currentCount = 0;
    for(Long jobCount : jobCountLookup.values())
    {
      currentCount += jobCount;
    }
    return currentCount;
  }

  @Override
  public void postStop()
    throws Exception
  {
    context().stop(workerPool);
    super.postStop();
  }
}

