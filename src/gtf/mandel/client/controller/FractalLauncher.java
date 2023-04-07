package gtf.mandel.client.controller;

import gtf.mandel.client.Broker;
import gtf.mandel.client.colour.ColorModelBase;
import gtf.mandel.client.view.FractalFrame;
import gtf.mandel.client.view.JuliaFrame;
import gtf.mandel.client.view.MandelbrotFrame;
import gtf.mandel.common.ServerConnection;
import gtf.mandel.formula.Formula;
import gtf.mandel.formula.JuliaFormulaFilter;
import gtf.mandel.model.Region;
import gtf.math.Complex;

/**
 * <p>Create and manage Fractal windows and threads. Make
 * application-wide defaults available to various components.</p>
 *
 *@author     gtf
 */
class FractalLauncher {

  private final Broker broker;
  private final Formula formula;
  private final ColorModelBase colorModel;
  private final int initialLimit;
  private Region region;

  /**
   *  Constructor for the FractalLauncher object
   *
   * @param broker       mandatory
   * @param formula      mandatory
   * @param colorModel   mandatory
   * @param initialLimit the initial limit
   */
  FractalLauncher(Broker broker, Formula formula, ColorModelBase colorModel, int initialLimit) {
    if (broker == null || formula == null || colorModel == null) {
      throw new NullPointerException("Illegal null parameter");
    }
    this.broker = broker;
    this.formula = formula;
    this.colorModel = colorModel;
    this.initialLimit = initialLimit;
  }

  /**
   *  Constructor for the FractalLauncher object
   *
   * @param broker       mandatory
   * @param formula      mandatory
   * @param colorModel   mandatory
   * @param region       if it is null, use the default initial region
   * @param initialLimit the initial limit
   */
  FractalLauncher(Broker broker, Formula formula, ColorModelBase colorModel, Region region, int initialLimit) {
    this(broker, formula, colorModel, initialLimit);
    this.region = region;
  }

  /**
   * Callback to create a Julia set.
   *
   *@param  juliaValue  The complex number corresponding to the coordinates
   *                    of the point where the user double-clicked
   *@throws UnsupportedOperationException if the formula is not of a
   *                    type that can support Julia sets
   */
  void createJulia(Complex juliaValue) {
    createJulia(juliaValue, null);
  }

  /**
   * Initiate a new Julia set calculation.
   *
   * @param juliaValue    The complex number corresponding to the Julia value
   * @param initialRegion The initial region. If null, then the initial region is
   *                      obtained from the Formula itself.
   *@throws UnsupportedOperationException if the formula is not of a
   *                    type that can support Julia sets
   */
  void createJulia(Complex juliaValue, Region initialRegion) {
    if (!formula.isJuliaSupported()) {
      throw new UnsupportedOperationException("This formula doesn't support Julia sets");
    }
    Formula juliaFormula = new JuliaFormulaFilter(formula, juliaValue);
    if (initialRegion == null) {
      initialRegion = juliaFormula.getContext().getInitialRegion();
    }
    ServerConnection conn = broker.newConnection(juliaFormula);
    FractalFrame jf = new JuliaFrame(juliaValue, initialLimit);
    FractalController fc = new BacktrackableFractalController(
        jf,  conn, colorModel, initialRegion, initialLimit);
    fc.launchEventLoop();
  }

  /**
   * Initiate a new Mandelbrot set calculation.
   */
  void createMandelbrot() {
    ServerConnection conn = broker.newConnection(formula);
    Region initialRegion = region;
    if (region == null) {
      initialRegion = formula.getContext().getInitialRegion();
    }
    FractalFrame mf = new MandelbrotFrame(initialLimit);
    FractalController fc = new BacktrackableFractalController(
        mf,  conn, colorModel, initialRegion, initialLimit);
    fc.setClickHandler(
      new ClickHandler() {
        public void handleClick(Complex c) {
          if (formula.isJuliaSupported()) {
            createJulia(c);
          }
        }
      }
    );
    fc.launchEventLoop();
  }
}
