package gtf.mandel.client.view;

import gtf.math.Complex;


/**
 * A FractalFrame with additional information pertaining to Julia sets
 *
 *@author     gfalk
 */

public class JuliaFrame extends FractalFrame {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor for a Julia frame.
   * TODO: Pass more information for the title bar
   *
   * @param juliaValue The Julia value to display in the title bar.
   */
  public JuliaFrame(Complex juliaValue, int limit) {
    super(limit);
    setTitle("Julia " + juliaValue.toString());
  }
}

