package gtf.mandel.configuration;

public class ConfigurationException extends Exception {

  private static final long serialVersionUID = 1L;

  public ConfigurationException(String message) {
    super(message);
  }

  public ConfigurationException(Throwable cause) {
    super(cause);
  }

  public ConfigurationException(String message, Throwable cause) {
    super(message, cause);
  }
}
