package gtf.mandel.client;

import java.awt.Dimension;
import java.io.InputStream;
import java.util.Properties;

/**
 *  Encapsulate read-only settings from mandelbrot.properties file.
 *
 *@author     gtf
 */
public class Defaults {

  /**
   * Name of the properties file.
   */
  public static final String PROPFILE = "mandelbrot.properties";

  private static Defaults defaults;

  private int limit;
  private Dimension canvasSize;
  private String copyrightDate;
  private String copyrightAuthor;
  private String version;
  private int copyrightDelayTime;
  private boolean multithreads;

  /**
   * Constructor for the Defaults object.
   */
  private Defaults() {
    Properties props = new Properties();
    try {
      ClassLoader loader = getClass().getClassLoader();
      InputStream propStream = loader.getResourceAsStream(PROPFILE);
      props.load(propStream);
    } catch (Exception e) {
      System.err.println("FATAL: Cannot open " + PROPFILE);
      e.printStackTrace();
      System.exit(1);
    }
    parse(props);
  }

  /**
   *Gets the instance attribute of the Defaults class
   *
   *@return    The instance value
   *@since
   */
  public synchronized static Defaults getInstance() {
    if (defaults == null) {
      defaults = new Defaults();
    }
    return defaults;
  }

  /**
   *  Description of the Method
   *
   *@param  s                             Description of Parameter
   *@return                               Description of the Returned Value
   *@exception  IllegalArgumentException  Description of Exception
   *@since
   */
  public static boolean parseBoolean(String s) throws IllegalArgumentException {
    boolean v;
    if (s.equals("yes") || s.equals("true") || s.equals("on")) {
      v = true;
    } else if (s.equals("no") || s.equals("false") || s.equals("off")) {
      v = false;
    } else {
      throw new IllegalArgumentException();
    }
    return v;
  }

  /**
   *  Gets the multithreads attribute of the Defaults object
   *
   *@return    The  multithreads value
   *@since
   */
  public boolean getMultithreads() {
    return multithreads;
  }

  /**
   *  Gets the default limit attribute from the properties file
   *
   *@return    The default limit value
   *@since
   */
  public int getLimit() {
    return limit;
  }

  /**
   *  Gets the canvasSize attribute of the Defaults object
   *
   *@return    The canvasSize value
   *@since
   */
  public Dimension getCanvasSize() {
    return canvasSize;
  }

  /**
   *  Gets the versionString attribute of the Defaults object
   *
   *@return    The versionString value
   *@since
   */
  public String getVersionString() {
    return version;
  }

  /**
   *  Gets the copyrightDate attribute of the Defaults object
   *
   *@return    The copyrightDate value
   *@since
   */
  public String getCopyrightDate() {
    return copyrightDate;
  }

  /**
   *  Gets the copyrightAuthor attribute of the Defaults object
   *
   *@return    The copyrightAuthor value
   *@since
   */
  public String getCopyrightAuthor() {
    return copyrightAuthor;
  }

  /**
   *  Gets the copyrightDelayTime attribute of the Defaults object
   *
   *@return    The copyrightDelayTime value
   *@since
   */
  public int getCopyrightDelayTime() {
    return copyrightDelayTime;
  }

  /**
   *  Description of the Method
   *
   *@param  props  Description of Parameter
   *@return        Description of the Returned Value
   *@since
   */
  private boolean parseMultithreads(Properties props) {
    boolean m = parseBoolean(props.getProperty("local.multithreads"));
    return m;
  }

  /**
   *  Description of the Method
   *
   *@param  props  Description of Parameter
   *@return        Description of the Returned Value
   *@since
   */
  private int parseCopyrightDelayTime(Properties props) {
    int dt = Integer.parseInt(props.getProperty("copyright.delaytime"));
    return dt;
  }

  /**
   *  Description of the Method
   *
   *@param  props  Description of Parameter
   *@since
   */
  private void parse(Properties props) {
    try {
      limit = parseLimit(props);
      canvasSize = parseCanvasSize(props);
      version = parseVersion(props);
      copyrightAuthor = parseCopyrightAuthor(props);
      copyrightDate = parseCopyrightDate(props);
      copyrightDelayTime = parseCopyrightDelayTime(props);
      multithreads = parseMultithreads(props);
      // etc. for other properties
    } catch (Throwable e) {
      System.err.println("Cannot parse properties file");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   *  Description of the Method
   *
   *@param  props  Description of Parameter
   *@return        Description of the Returned Value
   *@since
   */
  private int parseLimit(Properties props) {
    return Integer.parseInt(props.getProperty("control.limit"));
  }

  /**
   *  Will be combined to use a CopyrightData(version, author, date)
   *
   *@param  props  Description of Parameter
   *@return        Description of the Returned Value
   *@since
   */
  private String parseVersion(Properties props) {
    return props.getProperty("copyright.version");
  }

  /**
   *  Will be combined to use a CopyrightData(version, author, date)
   *
   *@param  props  Description of Parameter
   *@return        Description of the Returned Value
   *@since
   */
  private String parseCopyrightAuthor(Properties props) {
    return props.getProperty("copyright.author");
  }

  /**
   *  Will be combined to use a CopyrightData(version, author, date)
   *
   *@param  props  Description of Parameter
   *@return        Description of the Returned Value
   *@since
   */
  private String parseCopyrightDate(Properties props) {
    return props.getProperty("copyright.date");
  }

  /**
   *  Description of the Method
   *
   *@param  props  Description of Parameter
   *@return        Description of the Returned Value
   *@since
   */
  private Dimension parseCanvasSize(Properties props) {
    int width = Integer.parseInt(props.getProperty("panel.width"));
    int height = Integer.parseInt(props.getProperty("panel.height"));
    Dimension dim = new Dimension(width, height);
    return dim;
  }
}
