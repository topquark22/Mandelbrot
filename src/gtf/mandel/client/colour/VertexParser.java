package gtf.mandel.client.colour;

import java.util.Vector;
import java.util.StringTokenizer;
import java.awt.Color;

/**
 * A parser of vertex strings for the PolygonalColorModel.
 *
 *@author   gtf
 *@see      PolygonalColorModel
 */
class VertexParser {

  private PolygonalColorModel model;

  /**
   * Constructor for the VertexParser object.
   *
   *@param model  The colour model
   */
  VertexParser(PolygonalColorModel model) {
    this.model = model;
  }

  /**
   * Convert a string containing a sequence of mark, colour pairs into a vector
   * of vertices.
   *
   *@param vertexStr                             A string of tokens to configure
   *      the colour model. The format of the string is a sequence ( <i>mark
   *      colour</i> )* delimited by whitespace, where <i>mark</i> is a float
   *      value in the open interval (0.0, 1.0), and <i>colour</i> is a colour
   *      code in the format #rrggbb in hexadecimal. Marks must be in
   *      monotonically-increasing order.
   *@return                                      A vector of vertices for the
   *      colour model
   *@exception ColorModelConfigurationException  Bad format of the configuration
   *      string
   */
  Vector<PolygonalColorModel.Vertex> parse(String vertexStr) throws ColorModelConfigurationException {
    StringTokenizer tokenizer = new StringTokenizer(vertexStr);
    if (tokenizer.countTokens() % 2 != 0) {
      abort();
    }
    float previousMark = 0.0f;
    Vector<PolygonalColorModel.Vertex> vertices = new Vector<PolygonalColorModel.Vertex>();
    try {
      while (tokenizer.hasMoreTokens()) {
        float mark = Float.parseFloat(tokenizer.nextToken());
        if (mark <= previousMark || mark >= 1.0f) {
          abort();
        }
        Color colour = Color.decode(tokenizer.nextToken());
        vertices.add(model.new Vertex(mark, colour));
        previousMark = mark;
      }
    } catch (NumberFormatException e) {
      abort();
    }
    return vertices;
  }

  /**
   * Something is wrong. Abort the parse by throwing an exception.
   *
   *@exception ColorModelConfigurationException  Bad format of the configuration
   *      string
   */
  private void abort() throws ColorModelConfigurationException {
    throw new ColorModelConfigurationException("Bad format of vertex string for ColorModel \"" +
        model.getColorModelContext().getName() + "\"");
  }
}
