package gtf.mandel.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;
import java.awt.Dimension;
import org.apache.log4j.Logger;
import gtf.mandel.model.MDataset;
import gtf.mandel.model.Region;
import gtf.mandel.formula.Formula;

/**
 *  Description of the Class
 *
 *@author     gtf
 */

public class ThreadedEngine implements Engine {

  private static final Logger logger = Logger.getLogger(ThreadedEngine.class);

  private final Formula formula;
  private final int maxThreads;

  /**
   *  Constructor for the ThreadedEngine object.
   *
   *@param  formula           The Formula to use in the calculation
   *@param maxThreads         The max number of threads to use
   */
  public ThreadedEngine(Formula formula, int maxThreads) {

    this.formula = formula;
    this.maxThreads = maxThreads;
  }

  public MDataset calculate(Region region, Dimension bounds, int limit)
          throws InterruptedException {
    formula.setLimit(limit);
    MDataset dset = new MDataset(bounds, limit);
    int threadCount = (bounds.height < maxThreads) ? bounds.height : maxThreads;
    ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    CountDownLatch latch = new CountDownLatch(bounds.height);
    for (int i = 0; i < threadCount; i++) {
      EngineThread thread = new EngineThread(formula, region, dset, i, threadCount, latch);
      logger.debug("Executing thread " + i);
      executor.execute(thread);
    }
    latch.await();
    executor.shutdown();
    return dset;
  }
}
