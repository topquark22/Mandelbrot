package gtf.mandel.formula;

import gtf.math.Complex;


public class QuadraticFunction implements IterationFunction {

  @Override
  public Complex f(Complex z, Complex c) {
    double xi = z.getX();
    double yi = z.getY();
    double xt = xi * xi - yi * yi + c.getX();
    double yt = 2 * xi * yi + c.getY();
    return new Complex(xt, yt);
  }

  @Override
  public double getExponent() {
    return 2.0;
  }
}
