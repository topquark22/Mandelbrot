package gtf.mandel.client;

/**
 * Maintain copyright strings for display on the splash screen.
 */
public class CopyrightInfo {
  private String author;
  private String date;
  private String version;
  private int delayTime;

  /**
   *
   * @param a
   * @param d
   * @param v
   * @param dt
   */
  public CopyrightInfo(String a, String d, String v, int dt) {
    author = a;
    date = d;
    version = v;
    delayTime = dt;
  }

  /**
   *  Default constructor. Obtains defaults from the Defaults mechanism.
   */
  public CopyrightInfo() {
    Defaults defaults = Defaults.getInstance();
    author = defaults.getCopyrightAuthor();
    date = defaults.getCopyrightDate();
    version = defaults.getVersionString();
    delayTime = defaults.getCopyrightDelayTime();
  }

  /**
   *
   * @return string for the author.
   */
  public String getAuthor() {
    return author;
  }

  /**
   *
   * @return string for the date.
   */
  public String getDate() {
    return date;
  }

  /**
   *
   * @return string for the version.
   */
  public String getVersion() {
    return version;
  }

  /**
   *
   * @return time to display the splash screen (ms?).
   */
  public int getDelayTime() {
    return delayTime;
  }
}
