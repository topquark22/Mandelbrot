package gtf.mandel.formula;

import gtf.math.Complex;


public class BiSesquiFunction implements IterationFunction {

  @Override
  public Complex f(Complex z, Complex c) {
    return z.multiply(z).multiply(z.sqrt()).add(c);
  }

  @Override
  public double getExponent() {
    return 4.0;
  }
}
