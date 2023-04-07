package gtf.mandel.model;

import java.awt.Dimension;

/**
 *  Description of the Class
 *
 *@author   gfalk
 */
public class MDataset implements java.io.Serializable {

  private static final long serialVersionUID = 1L;
  
  /**
   *  Description of the Field
   *
   *@since
   */
  public int[][] mdata;
  /**
   *  Description of the Field
   *
   *@since
   */
  protected int xsize, ysize;
  /**
   *  Description of the Field
   *
   *@since
   */
  protected int limit;

  /**
   * The calculation time in milliseconds (or 0 if not set)
   */
  private long calcTime;

  /**
   *  Constructor for the MDataset object
   *
   *@param size   Description of Parameter
   *@param limit  Description of Parameter
   *@since
   */
  public MDataset(Dimension size, int limit) {
    xsize = size.width;
    ysize = size.height;
    this.limit = limit;
    if (xsize > 0 && ysize > 0) {
      mdata = new int[ysize][xsize];
    }
  }

  /**
   *  Gets the size attribute of the MDataset object
   *
   *@return   The size value
   *@since
   */
  public Dimension getSize() {
    return new Dimension(xsize, ysize);
  }

  /**
   *  Gets the width attribute of the MDataset object
   *
   *@return   The width value
   *@since
   */
  public int getWidth() {
    return xsize;
  }

  /**
   *  Gets the height attribute of the MDataset object
   *
   *@return   The height value
   *@since
   */
  public int getHeight() {
    return ysize;
  }

  /**
   *  Gets the limit attribute of the MDataset object
   *
   *@return   The limit value
   *@since
   */
  public int getLimit() {
    return limit;
  }

  /**
   * Sets the length of time of the calculation in milliseconds.
   *
   *@param calcTime  The new calcTime value
   */
  public void setCalcTime(long calcTime) {
    this.calcTime = calcTime;
  }

  /**
   * Gets he length of time of the calculation in milliseconds.
   *
   *@return   The calcTime value
   */
  public long getCalcTime() {
    return calcTime;
  }
}
