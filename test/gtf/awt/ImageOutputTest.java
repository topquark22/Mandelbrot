package gtf.awt;

import static junit.framework.Assert.*;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Test for ImageOutputStream.
 *
 *@author     gtf
 */
public class ImageOutputTest extends ImageTestBase {

  public static final String OUTPUT_FILE_JPG = "ImageOutputTest_out.jpg";

  public static final String OUTPUT_FILE_PNG = "ImageOutputTest_out.png";

  private BufferedImage image;

  @Before
  public void setUp() throws IOException {
    image = TestPattern.createImage();
  }

  @After
  public void tearDown() throws IOException {
    image.getGraphics().dispose();
    image = null;
  }

  /**
   * Write the image as a JPG to the output stream.
   *
   * @throws IOException  oops
   */
  @Test
  public void testWriteJPG() throws IOException {
    ImageOutput out = createImageOutput(OUTPUT_FILE_JPG, ImageOutput.IMAGE_TYPE_JPG);
    out.writeImage(image);
    out.close();
  }

  /**
   * Write the image as a PNG to the output stream.
   *
   * @throws IOException  oops
   */
  @Test
  public void testWritePNG() throws IOException {
    ImageOutput out = createImageOutput(OUTPUT_FILE_PNG, ImageOutput.IMAGE_TYPE_PNG);
    out.writeImage(image);
    out.close();
  }

  /**
   * Write the image twice. Expected to throw an exception.
   *
   * @throws IOException  oops
   */
  @Test
  public void testWriteTwice() throws IOException {
    ImageOutput out = createImageOutput(OUTPUT_FILE_JPG, ImageOutput.IMAGE_TYPE_JPG);
    out.writeImage(image);
    try {
      out.writeImage(image);
      assertTrue("IllegalStateException should have been thrown", false);
    } catch (IllegalStateException e) {
      assertEquals("Wrong exception message", "Image already written out", e.getMessage());
    }
    out.close();
  }
}

