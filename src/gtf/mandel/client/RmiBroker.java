package gtf.mandel.client;

import java.rmi.Naming;

import gtf.mandel.common.BrokerServer;
import gtf.mandel.common.ServerConnection;
import gtf.mandel.formula.Formula;


/**
 *  Client's way to get connections on a remote server
 *
 * @author     gtf
 */
public class RmiBroker implements Broker {
  BrokerServer broker;
  String serverHost;

  /**
   *  constructor
   *
   * @param  serverHost  Hostname of remote server
   * @since
   */
  public RmiBroker(String serverHost) {
    this.serverHost = serverHost;
    try {
      String name = "//" + serverHost + "/BrokerServer";
      broker = (BrokerServer)Naming.lookup(name);
    } catch (Exception e) {
      System.err.println("RmiBroker got exception: " + e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   *  Description of the Method
   *
   * @param  formula                     Desired formula (quadratic, cubic...)
   * @return                             Description of the Returned Value
   * @since
   */
  public ServerConnection newConnection(Formula formula) {
    ServerConnection conn = null;
    try {
      String name = broker.newConnection(formula);
      String fullname = "//" + serverHost + "/" + name;
      conn = (ServerConnection)Naming.lookup(fullname);
    } catch (Exception e) {
      System.err.println("Broker exception: " + e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }
    return conn;
  }
}
