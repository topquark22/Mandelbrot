package gtf.awt;

import static junit.framework.Assert.assertNotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Before;


/**
 * Test for ImageOutputStream.
 *
 *@author     gtf
 */
public abstract class ImageTestBase {

  public static final String OUTPUT_PATH_PROP = "mandel.test.dir.output";

  private File outputDir;

  @Before
  public void setUp() throws IOException {
    String outputDirStr = (String) System.getProperties().getProperty(OUTPUT_PATH_PROP);
    assertNotNull("You must specify the \"" + OUTPUT_PATH_PROP + "\" System property", outputDirStr);
    outputDir = new File(outputDirStr);
  }

  protected final ImageOutput createImageOutput(String filename, ImageOutput.ImageType type) throws IOException {
    OutputStream o = new FileOutputStream(new File(outputDir, filename));
    ImageOutput out = new ImageOutput(o, type);
    return out;
  }
}