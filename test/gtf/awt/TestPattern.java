package gtf.awt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


/**
 * Creates an image for testing purposes.
 */
class TestPattern {

  /**
   * Image width
   */
  public final static int WIDTH = 600;

  /**
   * Image height
   */
  public final static int HEIGHT = 600;

  static BufferedImage createImage() {
    BufferedImage im = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    Graphics g = im.getGraphics();
    scribble(g);
    g.dispose();
    return im;
  }

  /**
   * Draw something into the Graphics context.
   *
   * @param  g  The graphics context
   */
  private static void scribble(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, WIDTH, HEIGHT);
    g.setColor(Color.BLACK);
    g.drawLine(0, 0, WIDTH, HEIGHT);
    g.drawLine(WIDTH, 0, 0, HEIGHT);
    int cx = WIDTH / 2;
    int cy = HEIGHT / 2;
    int gap = 200;
    g.setColor(Color.BLUE);
    for (int r = (9 * WIDTH)/ 16; r > 0; gap = (9*gap) / 16 + 1, r -= gap) {
      g.drawOval(cx - r, cy - r, 2 * r, 2 * r);
    }
  }
}
