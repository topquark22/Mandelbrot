package gtf.mandel.configuration;

public class PluginException extends ConfigurationException {

  private static final long serialVersionUID = 1L;

  PluginException(String reason) {
    super(reason);
  }
}
