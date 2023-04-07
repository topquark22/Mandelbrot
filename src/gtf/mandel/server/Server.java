package gtf.mandel.server;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;

import gtf.mandel.common.BrokerServer;


/**
 *  Description of the Class
 *
 *@author     gtf
 */
public class Server {
  /**
   *  The main program for the Server class
   *
   *@param  args  The command line arguments
   *@since
   */
  public static void main(String[] args) {
    startRMI();
  }

  /**
   *  Description of the Method
   *
   *@since
   */
  private static void startRMI() {
    System.setProperty("java.security.policy", "java.policy");

    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new RMISecurityManager());
    }
    String name = "//localhost/BrokerServer";
    try {
      BrokerServer broker = new BrokerServerImpl();
      Naming.rebind(name, broker);
      System.out.println("BrokerServerImpl bound");
    } catch (Exception e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }
}
