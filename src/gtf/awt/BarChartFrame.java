package gtf.awt;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *  Description of the Class
 *
 *@author     gtf
 */
public class BarChartFrame extends Frame {

  private static final long serialVersionUID = 1L;

  BarChart panel;

  /**
   *  Constructor for the BarChartFrame object.
   *
   *@param  size  Size of the bar chart.
   *@since
   */
  public BarChartFrame(int size) {
    panel = new BarChart(size);
    add(panel);
    pack();
    setVisible(true);
    addWindowListener(
      new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
          dispose();
        }
      }
    );
  }

  /**
   *
   * @param count
   */
  public void setCount(int count) {
    System.out.println("BarChartFrame:setCount(" + count + ")");
    panel.setCount(count);
  }

  /**
   *  Description of the Class
   *
   *@author     gtf
   */
  public static class Test {
    static final int SIZE = 10;
    /**
     *  The main program for the Test class.
     *
     *@param  args  The command line arguments.
     *@since
     */
    public static void main(String[] args) {
      BarChartFrame frame = new BarChartFrame(SIZE);
      for(int i = 0; i < SIZE; i++) {
        frame.setCount(i + 1);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {}
      }
      System.exit(0);
    }
  }
}
