package gtf.mandel.server;

import java.awt.Dimension;
import gtf.mandel.model.Region;
import gtf.mandel.model.MDataset;

/**
 * An engine for making fractal calculations.
 *
 *@author   gtf
 */
public interface Engine {

  /**
   * Make a calculation.
   *
   *@param region                    The region of the complex plane
   *@param bounds                    The dimensions of the pixel array
   *@param limit                     The iteration limit
   *@return                          The dataset resulting from the calculation
   *@exception InterruptedException  If the calculation was interrupted
   */
  MDataset calculate(Region region, Dimension bounds, int limit)
       throws InterruptedException;
}
