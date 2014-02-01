package common.swing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class DoubleField extends JTextField {
	private static final long serialVersionUID = 1L;
	
	public DoubleField() {
		super();
		init();
	}
	
	public DoubleField(double value) {
		super();
		setDouble(value);
		init();
	}
	
	private void init() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				final char c = e.getKeyChar();
				if (!Character.isDigit(c) &&
						!(c == '-' && getCaretPosition() == 0 && !getText().contains("-")) &&
						!(c == '.' && !getText().contains(".")))
					e.consume();
			}
		});
	}
	
	public void setDouble(double value) {
		setText(String.valueOf(value));
	}
	
	public double getDouble() {
		return Double.parseDouble(getText());
	}
}
