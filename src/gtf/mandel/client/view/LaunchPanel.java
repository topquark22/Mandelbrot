package gtf.mandel.client.view;

import java.awt.Panel;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Label;
import java.awt.TextField;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import gtf.math.Complex;

import gtf.mandel.model.Region;

import gtf.mandel.client.event.LaunchListener;
import gtf.mandel.client.event.LaunchEvent;


public class LaunchPanel extends Panel {

  private static final long serialVersionUID = 1L;
  
  private final Choice formulaChooser = new Choice();
  private final Choice colorModelChooser = new Choice();
  private final TextField xField = new TextField();
  private final TextField yField = new TextField();
  private final TextField spanField = new TextField();
  private final TextField juliaXField = new TextField();
  private final TextField juliaYField = new TextField();
  private final TextField limitField = new TextField();
  private final Button goButton;

  /**
   *Constructor for the MainPanel object.
   */
  public LaunchPanel() {
    int limit = new PanelDefaults().getLimit();
    setLayout(new GridLayout(0, 2));
    add(new Label("Formula:"));
    add(formulaChooser);
    add(new Label("Colour model:"));
    add(colorModelChooser);
    add(new Label("x:"));
    add(xField);
    add(new Label("y:"));
    add(yField);
    add(new Label("span:"));
    add(spanField);
    add(new Label("juliaX:"));
    add(juliaXField);
    add(new Label("juliaY:"));
    add(juliaYField);
    limitField.setText(Integer.toString(limit));
    add(new Label("Limit:"));
    add(limitField);
    add(new Label(""));
    add(goButton = new Button("Go!"));
    goButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          notifyListeners();
        }
      }
    );
  }

  /**
   * Adds a formula to the formula chooser.
   */
  public void addFormula(String formulaName) {
    formulaChooser.add(formulaName);
  }

  /**
   * Adds a colour model to the colour model chooser.
   */
  public void addColorModel(String colorModelName) {
    colorModelChooser.add(colorModelName);
  }

  private LaunchListener listener;
  public void addListener(LaunchListener listener) {
    this.listener = listener; // TODO support multiple listeners
  }

  // TODO prevalidation and parsing of fields on user input,
  // so we don't need to do it here
  private void notifyListeners() {
    LaunchEvent event = new LaunchEvent(this);
    event.setFormulaName(formulaChooser.getSelectedItem());
    event.setColorModelName(colorModelChooser.getSelectedItem());
    String limitStr = limitField.getText().trim();
    int limit = Integer.parseInt(limitStr); // TODO handle NumberFormatException
    event.setLimit(limit);
    // if user entered something in the xField, yField and span, then use it
    String xs = xField.getText().trim();
    String ys = yField.getText().trim();
    String ss = spanField.getText().trim();
    if (!"".equals(xs) && !"".equals(ys) && !"".equals(ss)) { // FIXME
      double x = Double.parseDouble(xs); // TODO handle NumberFormatException
      double y = Double.parseDouble(ys);
      double span = Double.parseDouble(ss);
      event.setRegion(new Region(x, y, span));
    }
    // If user entered something for the JuliaValue, then use it
    String jxs = juliaXField.getText().trim();
    String jys = juliaYField.getText().trim();
    if (!"".equals(jxs) && !"".equals(jys)) { // FIXME
      double jx = Double.parseDouble(jxs); // TODO handle NumberFormatException
      double jy = Double.parseDouble(jys);
      event.setJuliaValue(new Complex(jx, jy));
    }
//  event.setRegion(regionPanel.getRegion());
    listener.fractalRequested(event);
  }

}

