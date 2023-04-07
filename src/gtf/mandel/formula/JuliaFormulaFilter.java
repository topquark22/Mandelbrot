package gtf.mandel.formula;

import gtf.math.Complex;

/**
 * Translate a formula into a Julia formula.
 *
 *@author   gtf
 */
public final class JuliaFormulaFilter extends Formula {

  private static final long serialVersionUID = 1L;

  /**
   * The constant passed into the underlying formula for each calculation.
   *
   *@since
   */
  private final Complex juliaValue;

  /**
   * Constructor for the JuliaFormulaFilter object.
   *
   *@param formula     The underlying formula
   *@param juliaValue  The Julia value
   *@since
   */
  public JuliaFormulaFilter(Formula formula, Complex juliaValue) {
    super(generateMyContext(formula.getContext(), juliaValue), formula.getFunction());
    this.juliaValue = juliaValue;
  }

  /**
   * Generates a new context for this Julia formula based on the original
   * formula and the Julia value.
   *
   *@param formula     The original formula
   *@param juliaValue  The Julia value
   *@return            A context for this Julia formula
   */
  private static FormulaContext generateMyContext(FormulaContext origContext, Complex juliaValue) {
    FormulaContext context = new FormulaContext();
    context.setInitialRegion(origContext.getInitialJuliaRegion());
    context.setAttributes(origContext.getAttributes());
    context.setName(origContext.getName() + "Julia@" + juliaValue.toString());
    return context;
  }

  /**
   * Twist original formula.
   *
   *@param z0  The point to iterate
   *@return    The iteration count
   *@since
   */
  public int iterate(Complex z0, Complex c) {
    return super.iterate(c, juliaValue);
  }

}
