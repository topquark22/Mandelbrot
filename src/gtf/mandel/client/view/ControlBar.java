package gtf.mandel.client.view;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gtf.mandel.client.event.FractalControlListener;


/**
 *  Description of the Class
 *
 *@author     gfalk
 */
class ControlBar extends Panel {

  private static final long serialVersionUID = 1L;
  
  private ZoomField zoomField;

  private LimitField limitField;

  private Button back;
  private Button reset;
  private FractalControlListener listener;

  /**
   *  Constructor for the ControlBar object
   *
   *@param  limit  Initial limit for LimitField
   *@since
   */
  ControlBar(int limit) {
    super(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;

    gbc.weightx = 1.0;
    add(limitField = new LimitField(limit), gbc);

    gbc.weightx = 0.5;
    add(back = new Button("back"), gbc);

    gbc.weightx = 0.5;
    add(reset = new Button("reset"), gbc);

    gbc.weightx = 1.0;
    add(zoomField = new ZoomField(), gbc);

    back.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if(listener != null)
            listener.backPressed();
        }
      }
    );

    reset.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if(listener != null)
            listener.resetPressed();
        }
      }
    );
  }

  public void setListener(FractalControlListener listener) {
    this.listener = listener;
    limitField.setListener(listener);
  }
  
  /**
   *  Sets the limit attribute of the ControlBar object
   *
   *@param  limit  The new limit value
   *@since
   */
  public void setLimit(int limit) {
    limitField.setValue(limit);
  }


  /**
   *  Sets the zoom attribute of the ControlBar object
   *
   *@param  delta  The new zoom value
   *@since
   */
  public void setZoom(double delta) {
    zoomField.setZoom(delta);
  }


  /**
   *  Sets the enabled attribute of the ControlBar object
   *
   *@param  enabled  The new enabled value
   *@since
   */
  public void setEnabled(boolean enabled) {
    back.setEnabled(enabled);
    reset.setEnabled(enabled);
    limitField.setEditable(enabled);
  }

  /**
   *  Gets the limit attribute of the ControlBar object
   *
   *@return    The limit value
   *@since
   */
  public int getLimit() {
    return limitField.getValue();
  }
}

