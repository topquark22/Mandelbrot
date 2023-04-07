package gtf.mandel.client.colour;

import java.awt.image.ImageProducer;
import java.awt.image.ImageConsumer;
import java.util.Collection;
import java.util.HashSet;

import gtf.mandel.model.MDataset;
import gtf.mandel.formula.Formula;


/**
 * Image producer for use in generating Mandelbrot and Julia images.
 *
 *@author   gtf
 */
public class MImageProducer implements ImageProducer {

  private Collection<ImageConsumer> consumers;
  private MDataset data;
  private int width;
  private int height;
  private ColorModelBase colorModel;

  /**
   * Constructor for the MImageProducer object
   *
   *@param data               The data set
   *@param colorModel         The colour model
   */
  public MImageProducer(MDataset data, ColorModelBase colorModel) {
    consumers = new HashSet<ImageConsumer>();
    this.data = data;
    width = data.getWidth();
    height = data.getHeight();
    this.colorModel = colorModel;
  }

  /**
   * Registers an <code>ImageConsumer</code> with the <code>ImageProducer</code>
   * for access to the image data during a later reconstruction of the <code>Image</code>
   * . The <code>ImageProducer</code> may, at its discretion, start delivering
   * the image data to the consumer using the <code>ImageConsumer</code>
   * interface immediately, or when the next available image reconstruction is
   * triggered by a call to the <code>startProduction</code> method.
   *
   *@param consumer  the specified <code>ImageConsumer</code>
   *@see             #startProduction
   */
  @Override
  public void addConsumer(ImageConsumer consumer) {
    consumers.add(consumer);
  }

  /**
   * Determines if a specified <code>ImageConsumer</code> object is currently
   * registered with this <code>ImageProducer</code> as one of its consumers.
   *
   *@param consumer  the specified <code>ImageConsumer</code>
   *@return          <code>true</code> if the specified <code>ImageConsumer</code>
   *      is registered with this <code>ImageProducer</code>; <code>false</code>
   *      otherwise.
   */
  @Override
  public boolean isConsumer(ImageConsumer consumer) {
    return consumers.contains(consumer);
  }

  /**
   * Removes the specified <code>ImageConsumer</code> object from the list of
   * consumers currently registered to receive image data. It is not considered
   * an error to remove a consumer that is not currently registered. The <code>ImageProducer</code>
   * should stop sending data to this consumer as soon as is feasible.
   *
   *@param consumer  the specified <code>ImageConsumer</code>
   */
  @Override
  public void removeConsumer(ImageConsumer consumer) {
    consumers.remove(consumer);
  }

  /**
   * Registers the specified <code>ImageConsumer</code> object as a consumer and
   * starts an immediate reconstruction of the image data which will then be
   * delivered to this consumer and any other consumer which might have already
   * been registered with the producer. This method differs from the addConsumer
   * method in that a reproduction of the image data should be triggered as soon
   * as possible.
   *
   *@param consumer  the specified <code>ImageConsumer</code>
   *@see             #addConsumer
   */
  @Override
  public void startProduction(ImageConsumer consumer) {
    addConsumer(consumer);
    for (ImageConsumer aConsumer: consumers) {
      requestTopDownLeftRightResend(aConsumer);
    }
  }

  /**
   * Requests, on behalf of the <code>ImageConsumer</code>, that the <code>ImageProducer</code>
   * attempt to resend the image data one more time in TOPDOWNLEFTRIGHT order so
   * that higher quality conversion algorithms which depend on receiving pixels
   * in order can be used to produce a better output version of the image. The
   * <code>ImageProducer</code> is free to ignore this call if it cannot resend
   * the data in that order. If the data can be resent, the <code>ImageProducer</code>
   * should respond by executing the following minimum set of <code>ImageConsumer</code>
   * method calls: <pre>
   *	consumer.setHints(TOPDOWNLEFTRIGHT | < otherhints >);
   *	consumer.setPixels(...);	// As many times as needed
   *	consumer.imageComplete();
   * </pre>
   *
   *@param consumer  the specified <code>ImageConsumer</code>
   *@see             ImageConsumer#setHints
   */
  @Override
  public void requestTopDownLeftRightResend(ImageConsumer consumer) {
    consumer.setDimensions(width, height); // otherwise setPixels() will cack
    consumer.setHints(ImageConsumer.COMPLETESCANLINES
         | ImageConsumer.SINGLEPASS
         | ImageConsumer.SINGLEFRAME
         | ImageConsumer.TOPDOWNLEFTRIGHT);
    for (int n = 0; n < height; n++) {
      int[] normalizedRow = normalizeRow(n);
      consumer.setPixels(0, n, width, 1, colorModel, normalizedRow, 0, 0);
    }
    consumer.imageComplete(ImageConsumer.STATICIMAGEDONE);
  }
  
  /**
   * Convert a dataset row into a normalized array of int values between
   * 0 and 2^24 - 1.
   *
   * TODO check for a more efficient implementation
   */
  private int[] normalizeRow(int rowIndex) {
    int limit = data.getLimit();
    int[] src = data.mdata[rowIndex];
    int[] res = new int[src.length];
    for (int i = 0; i < src.length; i++) {
      if (src[i] == Formula.INTERIOR_POINT) {
        res[i] = Formula.INTERIOR_POINT;
      } else {
        res[i] = (int) (((double) src[i]) * (1 << 24) / limit);
      }
    }
    return res;
  }
}
