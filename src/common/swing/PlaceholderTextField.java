package common.swing;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class PlaceholderTextField extends JTextField {
	private static final long serialVersionUID = 1L;
	
	private String _placeholder = "";
	private boolean _placeholding = false;
	private Color _storedColor;
	
	public PlaceholderTextField() {
		super();
		init();
	}
	
	public PlaceholderTextField(String text) {
		super(text);
		init();
	}
	
	public PlaceholderTextField(int columns) {
		super(columns);
		init();
	}
	
	public PlaceholderTextField(String text, int columns) {
		super(text, columns);
		init();
	}
	
	public PlaceholderTextField(Document doc, String text, int columns) {
		super(doc, text, columns);
		init();
	}
	
	public void init() {
		addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (_placeholding) {
					_placeholding = false;
					setText("");
					setForeground(_storedColor);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (getText().isEmpty()) {
					_placeholding = true;
					setText(_placeholder);
					_storedColor = getForeground();
					setForeground(getDisabledTextColor());
				}
			}
		});
	}
	
	public void setPlaceholder(String text) {
		_placeholder = text;
	}
	
	public String getPlaceholder() {
		return _placeholder;
	}
	
}
