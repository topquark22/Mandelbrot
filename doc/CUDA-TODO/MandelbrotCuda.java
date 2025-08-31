/**
 * Simple sample CUDA integration suggested by ChatGPT
 */

// ... package gtf.mandelbrot;
import gtf.mandelbrot.gpu.MandelbrotCuda;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Main {
  public static void main(String[] args) throws Exception {
    int width = 1920, height = 1080, maxIter = 1000;
    double xmin = -2.0, xmax = 1.0, ymin = -1.5, ymax = 1.5;

    MandelbrotCuda gpu = new MandelbrotCuda("native/mandelbrot.ptx");
    int[] iters = gpu.compute(width, height, xmin, xmax, ymin, ymax, maxIter);
    gpu.close();

    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    for (int y=0; y<height; y++) {
      for (int x=0; x<width; x++) {
        int iter = iters[y*width + x];
        int argb = (iter==maxIter) ? 0xFF000000 : hsv(iter, maxIter);
        img.setRGB(x, y, argb);
      }
    }
    ImageIO.write(img, "png", new File("out.png"));
  }

  private static int hsv(int iter, int maxIter) {
    float h = (iter % maxIter) / (float) maxIter;
    float s = 0.7f, v = 1.0f;
    int rgb = java.awt.Color.HSBtoRGB(h, s, v);
    return 0xFF000000 | (rgb & 0x00FFFFFF);
  }
}