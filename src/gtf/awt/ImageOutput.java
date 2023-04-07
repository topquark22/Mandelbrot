package gtf.awt;

import java.awt.image.RenderedImage;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * <p>Supports saving from a RenderedImage to a file.
 * See also http://javaalmanac.com/egs/javax.imageio/Graphic2File.html</p>
 *
 * <p>Typical use case:
 * <ol>
 *  <li>Create a new ImageOutput and specify the output type.
 *     Currently supported formats are ImageOutput.IMAGE_TYPE_PNG
 *     and ImageOutput.IMAGE_TYPE_JPG.</li>
 *  <li>Call the writeImage() method to save the image to a file.
 *     This can be called only once.</li>
 *  <li>Close the ImageOutput. This closes the underlying output
 *     stream as well.
 * </ol></p>
 * 
 * TODO merge with ImageSaver (it handles saving from an ImageProducer).
 *@author   gtf
 */
public final class ImageOutput {

  private boolean done = false;

  /**
   * Enumeration of image type. Currently supported are JPG and PNG.
   *
   * @author   gtf
   */
  public final static class ImageType {

    private final String typeStr;

    private ImageType(String typeStr) {
      this.typeStr = typeStr;
    }
  }

  /**
   * Type parameter representing JPG format. Pass into the ImageOutput
   * constructor to specify that output will be a JPG.
   */
  public final static ImageType IMAGE_TYPE_JPG = new ImageType("jpg");

  /**
   * Type parameter representing PNG format. Pass into the ImageOutput
   * constructor to specify that output will be a PNG.
   */
  public final static ImageType IMAGE_TYPE_PNG = new ImageType("png");

  private final OutputStream out;

  private final ImageWriter writer;

  /**
   * Constructor.
   *
   *@param out   Wrapped OutputStream
   *@param type  The image type (taken from static constant)
   */
  public ImageOutput(OutputStream out, ImageType type) {
    this.out = out;
    Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(type.typeStr);
    // expect only 1 item in the iterator
    assert iter.hasNext() : "Output image format \"" + type.typeStr
        + "\" not supported by the JDK";
    writer = (ImageWriter) iter.next();
  }

  /**
   * Write the image to the output stream.
   *
   *@param image         The image to write
   *@throws IOException  oops
   */
  public synchronized void writeImage(RenderedImage image) throws IOException {
    if (done) {
      throw new IllegalStateException("Image already written out");
    }
    done = true;
    ImageOutputStream ios = ImageIO.createImageOutputStream(out);
    if (ios == null) {
      throw new IOException("Could not create image output stream");
    }
    writer.setOutput(ios);
    writer.write(image);
    ios.flush();
    ios.close();
  }

  /**
   * Close the stream.
   *
   *@throws IOException  oops
   */
  public void close() throws IOException {
    out.close();
    writer.dispose();
  }
}

