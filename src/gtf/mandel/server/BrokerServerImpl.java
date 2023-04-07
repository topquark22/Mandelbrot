package gtf.mandel.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import gtf.mandel.common.BrokerServer;
import gtf.mandel.common.ServerConnection;
import gtf.mandel.formula.Formula;


/**
 * This class provides a mechanism for an RMI client to get a handle on a new ServerConnection.
 *
 *@author     gtf
 */
public class BrokerServerImpl extends UnicastRemoteObject implements BrokerServer {

  private static final long serialVersionUID = 1L;

  /**
   *  Constructor for the BrokerServerImpl object
   *
   *@exception  RemoteException  Description of Exception
   */
  public BrokerServerImpl() throws RemoteException {
    super();
  }

  /**
   *  Return an identifier for a ServerConnection, so that the client can
   * subsequently look it up in the rmiregistry.
   *
   *@param  formula                        Desired formula
   *@return                                RMI name registered for the new connection object
   *@exception  RemoteException            Remote exception
   */
  public String newConnection(Formula formula) throws RemoteException {
    String name = "ServerConnection" + SEP + "type=" + formula.getContext().getName();
    String fullname = "//localhost/" + name;
    try {
      ServerConnection conn = new ServerConnectionImpl(formula);
      Naming.rebind(fullname, conn);
      System.out.println("ServerConnectionImpl bound at " + fullname);
    } catch (Exception e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }
    return name;
  }
}
