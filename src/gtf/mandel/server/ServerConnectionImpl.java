package gtf.mandel.server;

import java.awt.Dimension;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

import org.apache.log4j.Logger;

import gtf.mandel.common.ServerConnection;
import gtf.mandel.formula.Formula;
import gtf.mandel.model.MDataset;
import gtf.mandel.model.Region;

/**
 * Gets an engine to perform calculation for a given formula.
 *
 *@author     gtf
 */
public class ServerConnectionImpl extends UnicastRemoteObject implements ServerConnection {

  private static final long serialVersionUID = 1L;

  private static final Logger logger = Logger.getLogger(ServerConnectionImpl.class);

  protected Engine engine;

  public ServerConnectionImpl(Formula formula) throws RemoteException {
    this(formula, ServerDefaults.getInstance().getMaxThreads());
  }

  public ServerConnectionImpl(Formula formula, int numThreads) throws RemoteException {
    super();
    if (logger.isDebugEnabled()) {
      logger.debug("Using " + numThreads + " threads");
    }
    engine = newEngine(formula, numThreads);
  }

  /**
   * Factory method: If more than one thread is available,
   * then create a ThreadedEngine with that number of threads.
   * Otherwise just create a single-threaded SimpleEngine.
   * 
   * @param formula    The formula for the calculation engine
   * @param numThreads The number of threads allocated
   */
  private Engine newEngine(Formula formula, int numThreads) {
    if(numThreads > 1) {
      return new ThreadedEngine(formula, numThreads);
    } else {
      return new SimpleEngine(formula);
    }
  }

  /**
   *  Calculate fractal data
   *
   *  @param  region      Area of complex plane to plot
   *  @param  bounds      Width and height of area to plot, in pixels
   *  @param  limit       Iteration limit
   *  @return             2-dimensional array of iteration results
   */
  public MDataset calculate(Region region, Dimension bounds, int limit)
          throws RemoteException, InterruptedException {
    long startTime = new Date().getTime();
    MDataset data = engine.calculate(region, bounds, limit);
    long endTime = new Date().getTime();
    long calcTime = endTime - startTime;
    logger.info("Calculation took " + calcTime + " ms");
    data.setCalcTime(calcTime);
    return data;
  }

  /**
   *  Description of the Method
   */
  public void close() throws RemoteException { }
}
