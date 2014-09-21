package common.swing;

import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

/**
 * A JTextField subtype that only allows valid integer values to be entered
 * 
 * @author Matt Putnam
 */
public class IntField extends JTextField {
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a new IntField with the given starting value.  Values are allowed
   * to take on any possible int value.
   * @param value the starting value
   */
  public IntField(int value) {
    this(value, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }
  
  /**
   * Creates a new IntField with the given starting value, and min/max.
   * @param value the starting value
   * @param min the minimum allowed value
   * @param max the maximum allowed value
   */
  public IntField(int value, final int min, final int max) {
    super();
    final PlainDocument doc = (PlainDocument) getDocument();
    doc.setDocumentFilter(new SimpleDocumentFilter() {
      @Override
      public boolean test(String potentialValue) {
        try {
          final int val = Integer.parseInt(potentialValue);
          return min <= val && val <= max;
        } catch (NumberFormatException e) {
          return false;
        }
      }
    });
    setInt(value);
  }
  
  /**
   * Sets this text field to have the given value
   * @param value the value
   */
  public void setInt(int value) {
    setText(String.valueOf(value));
  }
  
  /**
   * Gets the value from this text field
   * @return the value in the text field
   */
  public int getInt() {
    return Integer.parseInt(getText());
  }
}
