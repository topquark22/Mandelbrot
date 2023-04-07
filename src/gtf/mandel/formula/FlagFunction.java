package gtf.mandel.formula;

import gtf.math.Complex;


public class FlagFunction implements IterationFunction {

  @Override
  public Complex f(Complex z, Complex c) {
    double xi = z.getX();
    double yi = z.getY();
    double xs = xi * xi;
    double ys = yi * yi;
    double xt = xs - ys - xi + c.getX();
    yi = 2 * xi * yi - yi + c.getY();
    xi = xt;
    return new Complex(xt, yi);
  }

  @Override
  public double getExponent() {
    return 1 + 2 * Math.log10(2);
  }
}
