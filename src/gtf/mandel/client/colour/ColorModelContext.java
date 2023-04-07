package gtf.mandel.client.colour;

import java.awt.Color;
import java.util.Collection;
import java.util.NoSuchElementException;
import gtf.mandel.configuration.ColorModelParam;
import gtf.mandel.client.ContextBase;

/**
 * A context for a colour model. Encapsulates parameters about the colour model
 * that were specified in the deployment descriptor.
 *
 *@author   gtf
 */
public class ColorModelContext extends ContextBase {

  private static final long serialVersionUID = 1L;

  public void setAttributes(Collection<ColorModelParam> attrs) {
    for (ColorModelParam param : attrs) {
      setAttribute(param.getName(), param.getValue());
    }
  }

  /**
   * Get the attribute as a Color.
   *
   *@param key                      Name of the attribute
   *@return                         The attribute value as a Color
   *@throws NoSuchElementException  The attribute does not exist in the
   *                                attribute list
   *@throws NumberFormatException   The attribute cannot be converted to a Color
   */
  public Color getAttributeAsColor(String key) throws NoSuchElementException, NumberFormatException {
    String value = getAttributeAsString(key);
    Color colour;
    try {
      colour = Color.decode(value);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Invalid colour value \"" + value +
          "\" for key \"" + key + "\". Check your mandelbrot-def.xml");
    }
    return colour;
  }
}
