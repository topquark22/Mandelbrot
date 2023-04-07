package gtf.mandel.formula;

import gtf.math.Complex;


public interface IterationFunction {

  Complex f(Complex z, Complex c);

  double getExponent();
}
