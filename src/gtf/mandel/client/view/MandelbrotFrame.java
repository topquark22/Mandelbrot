package gtf.mandel.client.view;


/**
 * An FractalFrame for displaying Mandelbrot sets.
 *
 *@author   gtf
 */
public class MandelbrotFrame extends FractalFrame {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor for the MandelbrotFrame object.
   */
  public MandelbrotFrame(int limit) {
    super(limit);
    setTitle("Mandelbrot");
  }
}

