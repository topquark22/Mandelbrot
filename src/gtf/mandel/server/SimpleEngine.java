package gtf.mandel.server;

import java.awt.Dimension;
import gtf.mandel.model.*;
import gtf.mandel.formula.Formula;
import gtf.math.Complex;


/**
 * Simple, single-threaded calculation engine.
 *
 *@author     gtf
 */
public class SimpleEngine implements Engine {

  /**
   * The iteration limit.
   */
  protected int limit;

  /**
   * The calculation formula.
   */
  protected final Formula formula;

  /**
   * Constructor for the SimpleEngine object
   *
   *@param  formula  The calculation formula
   */
  public SimpleEngine(Formula formula) {
    this.formula = formula;
  }

  /**
   *  Abstract calculation method. Requires subclass to define the iterate()
   *  method.
   *
   *@param  region  Defines the region of the complex plane for this calculation
   *@param  bounds  The size of the canvas in pixels (width, height)
   *@param  limit   The iteration limit
   *@return         An array of iteration counts
   */
  public MDataset calculate(Region region, Dimension bounds, int limit) {
    double x = region.getX();
    double y = region.getY();
    double span = region.getSpan();
    int width = bounds.width;
    int height = bounds.height;
    double delta = span / width;
    formula.setLimit(limit);
    MDataset dset = new MDataset(bounds, limit);
    for (int j = 0; j < height; j++) {
      double yy = y + j * delta;
      for (int i = 0; i < width; i++) {
        double xx = x + i * delta;
        dset.mdata[j][i] = formula.iterate(new Complex(xx, yy));
      }
    }
    return dset;
  }
}
