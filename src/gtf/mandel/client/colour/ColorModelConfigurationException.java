package gtf.mandel.client.colour;

import gtf.mandel.configuration.ConfigurationException;

/**
 * Exception thrown when there is an error configuring a ColorModel.
 *
 *@author   gtf
 */
public class ColorModelConfigurationException extends ConfigurationException {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor for the ColorModelConfigurationException object
   *
   *@param message  The message
   */
  public ColorModelConfigurationException(String message) {
    super(message);
  }
}
