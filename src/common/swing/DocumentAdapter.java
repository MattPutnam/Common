package common.swing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DocumentAdapter implements DocumentListener {
	@Override
	public void changedUpdate(DocumentEvent e) {
		documentChanged(e);
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		documentChanged(e);
	}
	
	@Override
	public void removeUpdate(DocumentEvent e) {
		documentChanged(e);
	}
	
	/**
	 * Called when any DocumentListener method is invoked
	 * @param e - the DocumentEvent from the original DocumentListener method
	 */
	public void documentChanged(DocumentEvent e) {}
}
