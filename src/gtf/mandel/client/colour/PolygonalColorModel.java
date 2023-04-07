package gtf.mandel.client.colour;

import java.awt.Color;
import java.util.Vector;

import gtf.mandel.formula.Formula;


/**
 * ColorModel based on interpolating between colour values on a polygonal graph.
 * The x-axis of the curve is the interval [0.0f, 1.0f], representing the
 * normalized iteration value. The y-axis is the Color. Colours in-between
 * vertices are determined by linear interpolation.
 *
 *@author   gtf
 */
public class PolygonalColorModel extends ColorModelBase {

  /**
   * The set of vertices for this polygonal colour model
   */
  private Vector<Vertex> vertices;

  /**
   * The colour corresponding to 0 iterations
   */
  protected Color minIterationsColour;

  /**
   * The colour corresponding to (limit - 1) iterations
   */
  protected Color maxIterationsColour;

  /**
   * Constructor for the PolygonalColorModel object.
   *
   *@param context                               The context for this colour
   *                                             model
   *@exception ColorModelConfigurationException     Bad configuration data
   */
  public PolygonalColorModel(ColorModelContext context) throws ColorModelConfigurationException {
    super(context);
    this.minIterationsColour = context.getAttributeAsColor("minIterationsColor");
    this.maxIterationsColour = context.getAttributeAsColor("maxIterationsColor");
    vertices = new VertexParser(this).parse(context.getAttributeAsString("vertices"));
  }

  /**
   * Convert an iteration count into a colour.
   *
   *@param iterations  Number of iterations normalized to an int 0..(2^24 - 1)
   *@return            The colour for the pixel
   */
  protected Color toColor(int iterations) {
    if (iterations == Formula.INTERIOR_POINT) {
      return interiorColor;
    }
    if (iterations >= (1 << 24) || iterations < 0) {
      throw new IllegalArgumentException(
          "Iteration count " + iterations + " must be nonnegative" +
          " and strictly less than the iteration limit");
    }
    float value = normalize(iterations);
    Vertex v0 = new Vertex(0.0f, minIterationsColour);
    for (Vertex v1: vertices) {
      float mark0 = v0.getMark();
      float mark1 = v1.getMark();
      if (value <= mark1) {
        float normalizedSegment = (value - mark0) / (mark1 - mark0);
        return interpolate(v0.getColor(), v1.getColor(), normalizedSegment);
      }
      v0 = v1;
    }
    float mark0 = v0.getMark();
    float normalizedSegment = (value - mark0) / (1.0f - mark0);
    return interpolate(v0.getColor(), maxIterationsColour, normalizedSegment);
  }

  /**
   * Represents a vertex of the polygonal curve in colour space.
   *
   *@author   gtf
   */
  class Vertex {

    /**
     * The normalized value in the range 0.0 to 1.0 corresponding to this vertex
     */
    private float mark;

    /**
     * The colour for this vertex
     */
    private Color color;

    /**
     * Constructor for the Vertex object
     *
     *@param mark   Description of the Parameter
     *@param color  Description of the Parameter
     */
    Vertex(float mark, Color color) {
      this.mark = mark;
      this.color = color;
    }

    /**
     * Get the mark attribute of the Vertex object
     *
     *@return   The mark value
     */
    float getMark() {
      return mark;
    }

    /**
     * Get the color attribute of the Vertex object
     *
     *@return   The color value
     */
    Color getColor() {
      return color;
    }
  }
}
