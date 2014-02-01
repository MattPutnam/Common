package common.swing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class NonNegativeIntField extends JTextField {
	private static final long serialVersionUID = 1L;
	
	public NonNegativeIntField() {
		super();
		init();
	}
	
	public NonNegativeIntField(int value) {
		super();
		setInt(value);
		init();
	}
	
	private void init() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				final char c = e.getKeyChar();
				if (!Character.isDigit(c))
					e.consume();
			}
		});
	}
	
	public void setInt(int value) {
		if (value < 0)
			throw new IllegalArgumentException("Value cannot be negative!");
		setText(String.valueOf(value));
	}
	
	public int getInt() {
		return Integer.parseInt(getText());
	}
	
	public void verify() throws VerificationException {
		try {
			final int val = getInt();
			if (val <= 0)
				throw new VerificationException("Please enter a positive number", this);
		} catch (NumberFormatException e) {
			throw new VerificationException("Please enter a positive number", this);
		}
	}
}
