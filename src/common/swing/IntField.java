package common.swing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class IntField extends JTextField {
	public IntField() {
		super();
		init();
	}
	
	public IntField(int value) {
		super();
		setInt(value);
		init();
	}
	
	private void init() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				final char c = e.getKeyChar();
				if (!Character.isDigit(c) && !(c == '-' && getCaretPosition() == 0 && !getText().contains("-")))
					e.consume();
			}
		});
	}
	
	public void setInt(int value) {
		setText(String.valueOf(value));
	}
	
	public int getInt() {
		return Integer.parseInt(getText());
	}
}
