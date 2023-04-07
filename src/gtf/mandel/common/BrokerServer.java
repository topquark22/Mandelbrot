package gtf.mandel.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

import gtf.mandel.formula.Formula;


/**
 * A factory for either kind of server connection. Sophisticated implementations
 * may use RMI, connection pooling, etc.
 *
 *@author     gtf
 */
public interface BrokerServer extends Remote {

  /**
   * Character to separate JNDI name from parameters
   */
  public static String SEP = ";";

  /**
   * Gets a new connection to the calculation server.
   *
   *@param  formula  The formula to use for the calculation
   */
  String newConnection(Formula formula) throws RemoteException;
}
