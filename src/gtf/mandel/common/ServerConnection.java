package gtf.mandel.common;

import java.awt.Dimension;
import java.rmi.Remote;
import java.rmi.RemoteException;

import gtf.mandel.model.MDataset;
import gtf.mandel.model.Region;

/**
 *  Represents a connection to a computation server. There is a separate
 *  ServerConnection for each client. A client is either a Mandelbrot window or
 *  a Julia window. If a server connection is for a Julia set, its constructor
 *  should take a Complex juliaValue.
 *
 *@author     gtf
 */
public interface ServerConnection extends Remote {

  /**
   *  Calculate fractal data
   *
   *@param  region  Area of complex plane to plot
   *@param  bounds  Width and height of area to plot, in pixels
   *@param  limit   Iteration limit
   *@return         2-dimensional array of iteration results
   */
  MDataset calculate(Region region, Dimension bounds, int limit)
              throws RemoteException, InterruptedException;

  /**
   *  Free resources associated with this server
   */
  void close() throws RemoteException;
}
