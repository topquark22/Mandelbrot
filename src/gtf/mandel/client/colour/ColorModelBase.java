package gtf.mandel.client.colour;

import java.awt.Color;
import java.awt.image.ColorModel;

/**
 * The base class for colour models. Any custom colour models for the Mandelbrot
 * application extend this class.
 *
 *@author   gtf
 */
public abstract class ColorModelBase extends ColorModel {

  /**
   * The colour model context
   */
  private final ColorModelContext context;

  /**
   * The colour for points in the interior
   */
  protected final Color interiorColor;

  /**
   * Constructor for the ColorModelBase object.
   *
   *@param context  The colour model context
   */
  public ColorModelBase(ColorModelContext context) {
    super(24);
    this.context = context;
    interiorColor = context.getAttributeAsColor("interiorColor");
  }

  /**
   * Get the colorModelContext attribute of the ColorModelBase object
   *
   *@return   The colorModelContext value
   */
  public ColorModelContext getColorModelContext() {
    return context;
  }

  /**
   * Interpolate between two colour values.
   *
   *@param color1  The color corresponding to value = 0.0
   *@param color2  The color corresponding to value = 1.0
   *@param value   A number in the range [0.0, 1.0]
   *@return        The interpolated colour
   */
  protected static Color interpolate(Color color1, Color color2, float value) {
    if (value < 0.0f || value > 1.0f) {
      throw new IllegalArgumentException("Normalized value " + value +
          " must be between 0.0f and 1.0f");
    }
    int red = (int) (color1.getRed() +
        value * (color2.getRed() - color1.getRed()));
    int green = (int) (color1.getGreen() +
        value * (color2.getGreen() - color1.getGreen()));
    int blue = (int) (color1.getBlue() +
        value * (color2.getBlue() - color1.getBlue()));
    return new Color(red, green, blue);
  }

  /**
   * Convert a value in our custom colour model into a red value 0..255 in the
   * standard colour model.
   *
   * TODO: This is an inefficient implementation. It calls toColor(iterations)
   * three times for each pixel: Once for getRed(), once for getGreen() and once
   * for getBlue().
   *
   *@param iterations  Number of iterations normalized to an int 0..(2^24 - 1)
   *@return            red iterations (0..255)
   */
  public int getRed(int iterations) {
    Color color = toColor(iterations);
    return color.getRed();
  }

  /**
   * Convert a value in our custom colour model into a green value 0..255 in the
   * standard colour model.
   *
   * TODO: This is an inefficient implementation. It calls toColor(iterations)
   * three times for each pixel: Once for getRed(), once for getGreen() and once
   * for getBlue().
   *
   *@param iterations  Number of iterations normalized to an int 0..(2^24 - 1)
   *@return            green value (0..255)
   */
  public int getGreen(int iterations) {
    Color color = toColor(iterations);
    return color.getGreen();
  }

  /**
   *  Convert a value in our custom colour model into a blue value 0..255 in the
   * standard colour model.
   *
   * TODO: This is an inefficient implementation. It calls toColor(iterations)
   * three times for each pixel: Once for getRed(), once for getGreen() and once
   * for getBlue().
   *
   *@param iterations  Number of iterations normalized to an int 0..(2^24 - 1)
   *@return            blue value (0..255)
   */
  public int getBlue(int iterations) {
    Color color = toColor(iterations);
    return color.getBlue();
  }

  /**
   * This is a more efficient method of returning the colour all at once.
   *
   *@param iterations  Input value from custom colour model
   *@return            32-bit standard RGB value
   */
  public int getRGB(int iterations) {
    Color color = toColor(iterations);
    int red = color.getRed();
    int green = color.getGreen();
    int blue = color.getBlue();
    int alpha = getAlpha(iterations);
    int rgb =
        (alpha << 24) |
        (red << 16) |
        (green << 8) |
        (blue);
    return rgb;
  }

  /**
   * Convert an int value between 0 .. 2^24-1 to a float between 0.0 and 1.0.
   */
  protected final static float normalize(int iter) {
    return ((float) iter) / (1 << 24);
  }

  /**
   * Return 255 (opaque). Alpha values other than 0xff are not supported by this
   * colour model.
   *
   * TODO: Specify this using the java.awt.Transparency.OPAQUE hint
   *
   *@param value  ignored
   *@return       255 = 0xff
   */
  public int getAlpha(int value) {
    return 0xff;
  }

  /**
   * Convert an iteration into a colour. Custom colour models implement
   * this method.
   *
   *@param iterations  Number of iterations normalized to an int 0..(2^24 - 1)
   *@return            The colour for the pixel
   */
  protected abstract Color toColor(int iterations);
}
