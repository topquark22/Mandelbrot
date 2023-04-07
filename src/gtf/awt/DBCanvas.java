package gtf.awt;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

/**
 *  Canvas with double-buffering.
 *
 * Based on Sun's example.
 *
 *@author     gtf
 */
public class DBCanvas extends Canvas {

  private static final long serialVersionUID = 1L;

  private Image offScreenBuffer;

  /**
   * Update the image on the canvas.
   *
   *@param  g  The graphics context
   */
  @Override
  public void update(Graphics g) {
    Graphics gr;
    // Will hold the graphics context from the offScreenBuffer.
    // We need to make sure we keep our offscreen buffer the same size
    // as the graphics context we're working with.
    if (   offScreenBuffer == null
        || offScreenBuffer.getWidth(this) != getWidth()
        || offScreenBuffer.getHeight(this) != getHeight()) {
      offScreenBuffer = this.createImage(getWidth(), getHeight());
    }

    // We need to use our buffer Image as a Graphics object:
    gr = offScreenBuffer.getGraphics();

    paint(gr);
    // Passes our off-screen buffer to our paint method, which,
    // unsuspecting, paints on it just as it would on the Graphics
    // passed by the browser or applet viewer.
    g.drawImage(offScreenBuffer, 0, 0, this);
    // And now we transfer the info in the buffer onto the
    // graphics context we got from the browser in one smooth motion.
  }
}
