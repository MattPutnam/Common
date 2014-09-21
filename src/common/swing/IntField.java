package common.swing;

import javax.swing.JFormattedTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

public class IntField extends JFormattedTextField {
  private static final long serialVersionUID = 1L;
  
  public IntField(int value) {
    this(value, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }
  
  public IntField(int value, int min, int max) {
    super();
    final PlainDocument doc = (PlainDocument) getDocument();
    doc.setDocumentFilter(new IntDocumentFilter(min, max));
    setInt(value);
  }
  
  public void setInt(int value) {
    setText(String.valueOf(value));
  }
  
  public int getInt() {
    return Integer.parseInt(getText());
  }
  
  private class IntDocumentFilter extends DocumentFilter {
    private final int _min;
    private final int _max;
    
    public IntDocumentFilter(int min, int max) {
      super();
      _min = min;
      _max = max;
    }
    
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
      final Document doc = fb.getDocument();
      final StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.insert(offset, string);
      
      if (test(sb.toString())) {
        super.insertString(fb, offset, string, attr);
      } else {
        // warn the user and don't allow the insert
      }
    }
    
    private boolean test(String text) {
      try {
        final int val = Integer.parseInt(text);
        return _min <= val && val <= _max;
      } catch (NumberFormatException e) {
        return false;
      }
    }
    
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
        throws BadLocationException {
      final Document doc = fb.getDocument();
      final StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.replace(offset, offset + length, text);
      
      if (test(sb.toString())) {
        super.replace(fb, offset, length, text, attrs);
      } else {
        // warn the user and don't allow the insert
      }
      
    }
    
    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
      final Document doc = fb.getDocument();
      final StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.delete(offset, offset + length);
      
      if (test(sb.toString())) {
        super.remove(fb, offset, length);
      } else {
        // warn the user and don't allow the insert
      }
      
    }
  }
}
