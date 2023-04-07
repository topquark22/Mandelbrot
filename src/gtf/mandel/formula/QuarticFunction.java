package gtf.mandel.formula;

import gtf.math.Complex;


public class QuarticFunction implements IterationFunction {

  @Override
  public Complex f(Complex z, Complex c) {
    Complex zs = z.multiply(z);
    return zs.multiply(zs).add(c);
  }

  @Override
  public double getExponent() {
    return 4.0;
  }
}
