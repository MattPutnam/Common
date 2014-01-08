package common.swing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class NonNegativeDoubleField extends JTextField {
	public NonNegativeDoubleField() {
		super();
		init();
	}
	
	public NonNegativeDoubleField(double value) {
		super();
		setDouble(value);
		init();
	}
	
	private void init() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				final char c = e.getKeyChar();
				if (!Character.isDigit(c) && !(c == '.' && !getText().contains(".")))
					e.consume();
			}
		});
	}
	
	public void setDouble(double value) {
		if (value < 0)
			throw new IllegalArgumentException("Value cannot be negative!");
		setText(String.valueOf(value));
	}
	
	public double getDouble() {
		return Double.parseDouble(getText());
	}
}
