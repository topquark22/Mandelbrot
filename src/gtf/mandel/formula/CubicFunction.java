package gtf.mandel.formula;

import gtf.math.Complex;


public class CubicFunction implements IterationFunction {

  @Override
  public Complex f(Complex z, Complex c) {
    double xi = z.getX();
    double yi = z.getY();
    double xs = xi * xi;
    double ys = yi * yi;
    double xt = (xs - 3 * ys) * xi + c.getX();
    yi = (3 * xs - ys) * yi + c.getY();
    xi = xt;
    return new Complex(xt, yi);
  }

  @Override
  public double getExponent() {
    return 4.0;
  }
}
