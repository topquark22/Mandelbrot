package gtf.awt;

import java.awt.Panel;

/**
 *  Description of the Class
 *
 *@author     gtf
 */
public class BarChart extends Panel {

  private static final long serialVersionUID = 1L;

  protected int size;

  protected ChartBar bar;

  /**
   *  Constructor for the BarChart object
   *
   *@param  size  size
   */
  public BarChart(int size) {
    this.size = size;
    bar = new ChartBar(size);
    bar.setVertical(false);
    add(bar);
  }

  /**
   *  Sets the count attribute of the BarChart object
   *
   *@param  count  The new count value
   *@since
   */
  public void setCount(int count) {
    bar.setCount(count);
  }
}
