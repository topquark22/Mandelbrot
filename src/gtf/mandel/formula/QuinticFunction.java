package gtf.mandel.formula;

import gtf.math.Complex;


public class QuinticFunction implements IterationFunction {

  @Override
  public Complex f(Complex z, Complex c) {
    double xi = z.getX();
    double yi = z.getY();
    double xs = xi * xi;
    double ys = yi * yi;
    double xt = xi * (xs * xs + (-10 * xs + 5 * ys) * ys) + c.getX();
    yi = ((5 * xs - 10 * ys) * xs + ys * ys) * yi + c.getY();
    return new Complex(xt, yi);
  }

  @Override
  public double getExponent() {
    return 5.0;
  }
}
