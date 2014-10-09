package common.swing;

import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

/**
 * A JTextField subtype that only allows valid double values to be entered
 * 
 * @author Matt Putnam
 */
public class DoubleField extends JTextField {
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a new DoubleField with the given starting value.  Values are allowed
   * to take on any possible double value.
   * @param value the starting value
   */
  public DoubleField(double value) {
    this(value, Double.MIN_VALUE, Double.MAX_VALUE);
  }
  
  /**
   * Creates a new DoubleField with the given starting value, and min/max.
   * @param value the starting value
   * @param min the minimum allowed value
   * @param max the maximum allowed value
   */
  public DoubleField(double value, final double min, final double max) {
    super();
    final PlainDocument doc = (PlainDocument) getDocument();
    doc.setDocumentFilter(new SimpleDocumentFilter() {
      @Override
      public boolean test(String potentialValue) {
        try {
          final double val = Double.parseDouble(potentialValue);
          return min <= val && val <= max;
        } catch (NumberFormatException e) {
          return false;
        }
      }
    });
    setDouble(value);
  }
  
  /**
   * Sets this text field to have the given value
   * @param value the value
   */
  public void setDouble(double value) {
    setText(String.valueOf(value));
  }
  
  /**
   * Gets the value from this text field
   * @return the value in the text field
   */
  public double getDouble() {
    return Double.parseDouble(getText());
  }
}
