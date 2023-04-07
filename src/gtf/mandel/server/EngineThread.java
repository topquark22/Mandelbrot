package gtf.mandel.server;

import java.util.concurrent.CountDownLatch;
import org.apache.log4j.Logger;
import gtf.mandel.model.MDataset;
import gtf.mandel.model.Region;
import gtf.mandel.formula.Formula;
import gtf.math.Complex;

/**
 * A thread for concurrent fractal calculation. It is initialized with
 * a starting row number and row step. It calculates every (step)th row
 * and decrements a count-down latch. The latch should be initialized with
 * the total number of rows in the calculation. When all threads have
 * completed their calculation, the latch will be released.
 *
 *@author   gtf
 */
public class EngineThread implements Runnable {

  private static final Logger logger = Logger.getLogger(ThreadedEngine.class);

  private final Formula formula;
  private final MDataset dset;
  private final double x;
  private final double y;
  private final double delta;
  private final CountDownLatch latch;
  private final int startRow;
  private final int step;

  /**
   * Constructor for the EngineThread object.
   *
   *@param formula   the Formula to use for the calculation
   *@param region    The Region to use for the calculation
   *@param dset      The dataset in which to store the results
   *@param startRow  The starting row number
   *@param step      the row increment
   *@param latch     The latch for counting how many rows are done
   */
  public EngineThread(Formula formula, Region region, MDataset dset,
      int startRow, int step, CountDownLatch latch) {
    this.formula = formula;
    this.dset = dset;
    x = region.getX();
    y = region.getY();
    delta = region.getSpan() / dset.getWidth();
    this.startRow = startRow;
    this.step = step;
    this.latch = latch;
  }

  /**
   * Main processing method for the EngineThread object.
   */
  public void run() {
    for (int j = startRow; j < dset.getHeight(); j += step) {
      int[] row = dset.mdata[j];
      double yy = y + j * delta;
      for (int i = 0; i < dset.getWidth(); i++) {
        double xx = x + i * delta;
        row[i] = formula.iterate(new Complex(xx, yy));
      }
      latch.countDown();
    }
    logger.debug("Thread " + startRow + " done its work");
  }
}
