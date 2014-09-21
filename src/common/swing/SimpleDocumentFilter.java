package common.swing;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * A DocumentFilter implementation that defers to the
 * {@link #test(String)} method to determine if the edit should be
 * allowed.  If the edit doesn't pass the <tt>test</tt> method, the
 * edit is rejected.
 * 
 * @author Matt Putnam
 */
abstract public class SimpleDocumentFilter extends DocumentFilter {
  @Override
  public final void insertString(FilterBypass fb, int offset,
      String string, AttributeSet attr) throws BadLocationException {
    final Document doc = fb.getDocument();
    final StringBuilder sb = new StringBuilder();
    sb.append(doc.getText(0, doc.getLength()));
    sb.insert(offset, string);
    
    if (test(sb.toString())) {
      super.insertString(fb, offset, string, attr);
    }
  }
  
  @Override
  public final void replace(FilterBypass fb, int offset, int length,
      String text, AttributeSet attrs)
      throws BadLocationException {
    final Document doc = fb.getDocument();
    final StringBuilder sb = new StringBuilder();
    sb.append(doc.getText(0, doc.getLength()));
    sb.replace(offset, offset + length, text);
    
    if (test(sb.toString())) {
      super.replace(fb, offset, length, text, attrs);
    }
  }
  
  @Override
  public final void remove(FilterBypass fb, int offset, int length)
      throws BadLocationException {
    final Document doc = fb.getDocument();
    final StringBuilder sb = new StringBuilder();
    sb.append(doc.getText(0, doc.getLength()));
    sb.delete(offset, offset + length);
    
    if (test(sb.toString())) {
      super.remove(fb, offset, length);
    }
  }
  
  /**
   * Tests if a given String matches this filter.
   * @param potentialValue the String that would result from the edit completing
   * @return <tt>true</tt> if <tt>potentialValue</tt> is a valid value,
   * <tt>false</tt> otherwise
   */
  abstract public boolean test(String potentialValue);
}
