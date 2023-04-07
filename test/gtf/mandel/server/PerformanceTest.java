package gtf.mandel.server;

import gtf.mandel.common.ServerConnection;
import gtf.mandel.formula.Formula;
import gtf.mandel.formula.FormulaContext;
import gtf.mandel.formula.QuadraticFunction;
import gtf.mandel.model.MDataset;
import gtf.mandel.model.Region;

import java.awt.Dimension;
import java.rmi.RemoteException;

/**
 * Test performance using various numbers of threads.
 *
 *@author   gtf
 */
public class PerformanceTest {

  /**
   * The main program for the PerformanceTest class.
   *
   *@param args  The command line arguments
   */
  public static void main(String[] args) throws Exception {
    Config config = new Config(args);
    new PerformanceTest(config).start();
  }

  private Region region;
  private Dimension bounds;
  private int limit;
  private int maxThreads;
  private int trialsPerTestCase;

  private PerformanceTest(Config config) {
    this.region = config.region;
    this.bounds = config.bounds;
    this.limit = config.limit;
    this.maxThreads = config.maxThreads;
    this.trialsPerTestCase = config.trialsPerTestCase;
    // MainController.configureLogging(); // FIXME - renamed/refactored??
  }

  public void start() throws RemoteException, InterruptedException {
    Formula formula = new Formula(new FormulaContext(), new QuadraticFunction());
    for (int numThreads = 1; numThreads <= maxThreads; numThreads++) {
      ServerConnection conn = new ServerConnectionImpl(formula, numThreads);
      System.out.println("Testing with " + numThreads + " threads...");
      long totalCalcTime = 0;
      for (int trial = 0; trial < trialsPerTestCase; trial++) {
        MDataset data = conn.calculate(region, bounds, limit);
        totalCalcTime += data.getCalcTime();
      }
      float avgCalcTime = (float) totalCalcTime / trialsPerTestCase;
      System.out.println(trialsPerTestCase + " trials per test case");
      System.out.println("Average calc time (ms) for " + numThreads +
            " threads: " + avgCalcTime);
    }
  }

  private static class Config {
    private Region region;
    private Dimension bounds;
    private int limit;
    private int maxThreads;
    private int trialsPerTestCase;

    private Config(String[] args) {
      // parse region
      // parse bounds
      // parse limit
      // parse number of threads
      // parse number of trials per test case
// FIXME -- hardcoded for now
region = new Region(-2.0, -1.5, 3.0);
bounds = new Dimension(256, 256);
limit = 300;
maxThreads = 10;
trialsPerTestCase = 20;

    }
  }
}
