package gtf.mandel.server;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;


/**
 *  Description of the Class
 *
 *@author     gtf
 */
public class ServerDefaults {

  private static ServerDefaults defaults;

  private final int maxThreads;

  public static ServerDefaults getInstance() {
    if(defaults == null) {
      defaults = new ServerDefaults();
    }
    return defaults;
  }

  /**
   *  Constructor for the ServerDefaults object
   *
   *@param  propertiesFile  Description of Parameter
   *@since
   */
  private ServerDefaults() {
    maxThreads = getCPUCount();
  }

  /**
   *  Gets the maxThreads attribute of the ServerDefaults object
   *
   *@return    The maxThreads value
   *@since
   */
  public int getMaxThreads() {
    return maxThreads;
  }

  /**
   * Get the number of CPU cores available on the system
   */
  private int getCPUCount() {
    OperatingSystemMXBean mxbean = ManagementFactory.getOperatingSystemMXBean();
    return mxbean.getAvailableProcessors();
  }
}
