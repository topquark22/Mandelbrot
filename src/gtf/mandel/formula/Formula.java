package gtf.mandel.formula;

import java.io.Serializable;

import gtf.math.Complex;


/**
 * Support for basic functionality needed by Formula objects.
 * Custom formulas should extend this class if they do not support
 * Julia sets. Otherwise they should extend Formula2Base.
 *
 * @see Formula2Base
 * @author     gfalk
 */
public class Formula implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final int INTERIOR_POINT = -1;

  /**
   *Description of the Field
   *
   *@since
   */
  protected int limit;

  /**
   * The formula context
   */
  private final FormulaContext context;

  /**
   * The iteration function
   */
  private final IterationFunction function;

  /**
   * Constructor for the FormulaBase object.
   *
   *@param formulaContext the formula context
   */
  public Formula(FormulaContext context, IterationFunction function) {
    this.context = context;
    this.function = function;
  }

  /**
   *  Gets the squared modulus for testing whether a value will
   *  go to infinity, assuming iteration formula z -> z^exponent + c
   *
   *@param  exponent  Exponent of the polynomial iteration formula
   *@return           The squared modulus beyond which the value will go to infinity
   *@since
   */
  protected double getBoundary2() {
    return Math.pow(2.0, 2.0 / (function.getExponent() - 1.0));
  }

  /**
   * get the formula context.
   *
   *@return the formula context
   */
  public FormulaContext getContext() {
    return context;
  }

  /**
   * get the iteration function.
   *
   * @return the iteration function
   */
  protected IterationFunction getFunction() {
    return function;
  }

  public final boolean isJuliaSupported() {
    return context.getInitialJuliaRegion() != null;
  }

  /**
   *Sets the iteration limit. (How to enforce that this is called?)
   *
   *@param  limit  The new limit value
   *@since
   */
  public final void setLimit(int limit) {
    this.limit = limit;
  }

  protected int iterate(Complex z0, Complex c) {
    final double boundary2 = getBoundary2();
    Complex z = z0;
    int iter = 0;
    while (z.norm2() <= boundary2 && iter < limit) {
      z = function.f(z, c);
      iter++;
    }
    return (iter < limit ? iter : INTERIOR_POINT);
  }

  public int iterate(Complex c) {
    return iterate(Complex.ZERO, c);
  }
}
