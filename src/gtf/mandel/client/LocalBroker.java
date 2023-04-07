package gtf.mandel.client;

import java.rmi.RemoteException;

import gtf.mandel.common.ServerConnection;
import gtf.mandel.formula.Formula;
import gtf.mandel.server.ServerConnectionImpl;


/**
 * A connection broker that returns local resources, a connection to an engine that
 * runs in the same JVM.
 *
 *@author     gtf
 */
public class LocalBroker implements Broker {

  /**
   * Get a connection to a local server for a Mandelbrot set.
   *
   *@param  formula      Formula for desired engine type (quadratic, cubic...)
   *@return              A server connection to a local server.
   *@since
   */
  public ServerConnection newConnection(Formula formula) {
    ServerConnection connection = null;
    try {
      connection = new ServerConnectionImpl(formula);
    } catch (RemoteException e) {
      // thrown in ServerConnectionImpl.super() -> UnicastRemoteObject().
      // This usually means that the stub was missing because rmic
      // part of the build wasn't done.
      throw new RuntimeException("cannot find RMIC stub", e);
    }
    return connection;
  }
}
