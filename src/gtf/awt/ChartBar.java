package gtf.awt;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/**
 *  Description of the Class
 *
 *@author     gtf
 */
public class ChartBar extends Canvas {

  private static final long serialVersionUID = 1L;

  private final static Color DEFAULT_BGCOLOR = Color.gray;
  private final static Color DEFAULT_FGCOLOR = Color.red;
  private final static int DEFAULT_WIDTH = 20;
  private final static int DEFAULT_UNIT = 15;
  private final static int DEFAULT_GAP = 3;

  protected int size;

  protected int count;

  protected boolean isVertical;

  protected Color bgColor;

  protected Color fgColor;

  /**
   *  Constructor for the ChartBar object
   *
   *@param  size     Description of Parameter
   *@param  bgColor  Description of Parameter
   *@param  fgColor  Description of Parameter
   *@since
   */
  public ChartBar(int size, Color bgColor, Color fgColor) {
    setBackground(bgColor);
    setForeground(fgColor);
    this.size = size;
    this.count = 0;
    isVertical = true;
    setSize();
  }

  /**
   *  Constructor for the ChartBar object
   *
   *@param  size  Description of Parameter
   *@since
   */
  public ChartBar(int size) {
    this(size, DEFAULT_BGCOLOR, DEFAULT_FGCOLOR);
  }

  /**
   * Set the size to the default size.
   */
  protected void setSize() {
    if(isVertical) {
      setSize(DEFAULT_WIDTH, size * DEFAULT_UNIT + (size - 1) * DEFAULT_GAP);
    } else {
      setSize(size * DEFAULT_UNIT + (size - 1) * DEFAULT_GAP, DEFAULT_WIDTH);
    }
  }

  /**
   *  Sets the count attribute of the ChartBar object
   *
   *@param  count  The new count value
   *@since
   */
  public void setCount(int count) {
    this.count = count;
    repaint();
  }

  /**
   *  Sets the vertical attribute of the ChartBar object
   *
   *@param  isVertical  The new vertical value
   *@since
   */
  public void setVertical(boolean isVertical) {
    this.isVertical = isVertical;
    setSize();
    repaint();
  }

  /**
   *  Gets the count attribute of the ChartBar object
   *
   *@return    The count value
   *@since
   */
  public int getCount() {
    return count;
  }

  /**
   *  Description of the Method
   *
   *@param  g  Description of Parameter
   *@since
   */
  @Override
  public void paint(Graphics g) {
    if (isVertical) {
      int offset = (size - 1) * (DEFAULT_GAP + DEFAULT_UNIT);
      for (int i = 0; i < count; i++) {
        g.fillRect(0, offset - i * (DEFAULT_GAP + DEFAULT_UNIT), DEFAULT_WIDTH, DEFAULT_UNIT);
      }
    } else {
      for (int i = 0; i < count; i++) {
        g.fillRect(i * (DEFAULT_GAP + DEFAULT_UNIT), 0, DEFAULT_UNIT, DEFAULT_WIDTH);
      }
    }
  }
}
