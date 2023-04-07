package gtf.awt;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * Supports saving directly from an ImageProducer to a file.
 *
 * @author gtf
 */
public class ImageSaver {

  private ImageOutput.ImageType imageFormat = ImageOutput.IMAGE_TYPE_PNG;

  private final OutputStream out;
  private final Consumer consumer;

  /**
   *
   * @param outputFile
   * @throws FileNotFoundException if file arg not found
   * @throws NullPointerException if arg is null
   */
  public ImageSaver(File outputFile) throws FileNotFoundException {
    this(outputFile, false);
  }

  /**
   *
   * @param outputFile
   * @throws FileNotFoundException if file arg not found
   * @throws NullPointerException if arg is null
   */
  public ImageSaver(String outputFileName) throws FileNotFoundException {
    this(new File(outputFileName));
  }

  /**
   *
   * @param outputFile
   * @param saveAlpha save alpha channel?
   * @throws FileNotFoundException if file arg not found
   * @throws NullPointerException if arg is null
   */
  public ImageSaver(String outputFileName, boolean saveAlpha) throws FileNotFoundException {
    this(new File(outputFileName), saveAlpha);
  }

  /**
   *
   * @param outputFile
   * @throws FileNotFoundException if file arg not found
   * @throws NullPointerException if arg is null
   */
  public ImageSaver(File outputFile, boolean saveAlpha) throws FileNotFoundException {
    if (outputFile == null) {
      throw new NullPointerException("File arg is null");
    }
    out = new FileOutputStream(outputFile);
    consumer = new Consumer(saveAlpha);
  }

  /**
   * Grab the image and save it.
   * @param producer
   */
  public void saveImage(ImageProducer producer) {
    producer.requestTopDownLeftRightResend(consumer);
  }

  private void imageComplete(BufferedImage buffer) {
    // TODO check status??
    ImageOutput imgOut = new ImageOutput(out, imageFormat);
    try {
      imgOut.writeImage(buffer);
      out.close();
    } catch (IOException e) {
      // TODO cleanup exception handling (maybe change ImageOutput)
      throw new RuntimeException("failed to write image", e);
    } finally {
      // buffer = null; // reclaim resources
    }
  }

  private class Consumer implements ImageConsumer {
    private final static int UNSET = -1;
    private final boolean saveAlpha;

    // private ColorModel model;
    // private int hintflags = 0;
    private int width = UNSET;
    private int height = UNSET;
    private BufferedImage buffer;

    private Consumer(boolean saveAlpha) {
      this.saveAlpha = saveAlpha;
    }

    /**
     * Returns the pixel buffer, allocating it first if necessary.
     * Due to synchronization and sanity checks, should be called
     * once, not from inside a loop.
     *
     * @return the pixel buffer
     */
    private synchronized BufferedImage getBuffer() {
      if (width == UNSET || height == UNSET) {
        throw new IllegalStateException("width/height not set");
      }
      if (buffer == null) {
        int channelType = (saveAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        buffer = new BufferedImage(width, height, channelType);
      }
      return buffer;
    }

    /**
     * Saves the image data down to the file, once the image is
     * done being sent.
     *
     * @see java.awt.image.ImageConsumer#imageComplete(int)
     */
    public void imageComplete(int status) {
      ImageSaver.this.imageComplete(getBuffer());
    }

    /*
     * (non-Javadoc)
     * @see java.awt.image.ImageConsumer#setColorModel(java.awt.image.ColorModel)
     */
    public void setColorModel(ColorModel model) {
      // this.model = model;
    }

    /*
     * (non-Javadoc)
     * @see java.awt.image.ImageConsumer#setHints(int)
     */
    public void setHints(int hintflags) {
      // this.hintflags = hintflags;
    }

    /*
     * (non-Javadoc)
     * @see java.awt.image.ImageConsumer#setDimensions(int, int)
     */
    public void setDimensions(int width, int height) {
      this.width = width;
      this.height = height;
    }

    /*
     * (non-Javadoc)
     * @see java.awt.image.ImageConsumer#setPixels(int, int, int, int, java.awt.image.ColorModel, byte[], int, int)
     */
    public void setPixels(int x, int y, int w, int h, ColorModel model,
        byte[] pixels, int off, int scansize) {
      BufferedImage image = getBuffer();
      for (int row = y; row < y + h; row++) {
        int rowStartIndex = row * scansize + off;
        for (int col = x; col < x + w; col++) {
          byte pixel = pixels[rowStartIndex + col];
          setOnePixel(image, model, row, col, pixel);
        }
      }
    }

    /*
     * (non-Javadoc)
     * @see java.awt.image.ImageConsumer#setPixels(int, int, int, int, java.awt.image.ColorModel, int[], int, int)
     */
    public void setPixels(int x, int y, int w, int h, ColorModel model,
        int[] pixels, int off, int scansize) {
      BufferedImage image = getBuffer();
      for (int row = y; row < y + h; row++) {
        int rowStartIndex = row * scansize + off;
        for (int col = x; col < x + w; col++) {
          int pixel = pixels[rowStartIndex + col];
          setOnePixel(image, model, row, col, pixel);
        }
      }
    }

    private void setOnePixel(BufferedImage image, ColorModel model, int row, int col, int pixel) {
      int red = model.getRed(pixel);
      int green = model.getGreen(pixel);
      int blue = model.getBlue(pixel);
      int rgbValue = (red << 16) + (green << 8) + blue;
      if (saveAlpha) {
        int alpha = model.getAlpha(pixel);
        rgbValue += (alpha << 24);
      }
      image.setRGB(col, row, rgbValue);
    }

    @Override
    public void setProperties(Hashtable<?, ?> arg0) {}

  }
}
