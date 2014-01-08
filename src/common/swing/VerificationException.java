package common.swing;

import java.awt.Component;

/**
 * An Exception that has been thrown because a GUI component has invalid
 * input.  Can also contain the offending Component so that it can receive
 * focus.
 * 
 * @author Matt Putnam
 */
public class VerificationException extends Exception {
	private Component _component;
	
	/**
	 * Creates a new VerificationException
	 * @param message - the error message to be displayed
	 */
	public VerificationException(String message) {
		this(message, null);
	}
	
	/**
	 * Creates a new VerificationException
	 * @param message - the error message to be displayed
	 * @param component - the Component that should receive focus to fix the
	 * problem.  Can be null.
	 */
	public VerificationException(String message, Component component) {
		super(message);
		_component = component;
	}
	
	/**
	 * @return the offending Component
	 */
	public Component getComponent() {
		return _component;
	}
	
	/**
	 * Request that the offending Component get focus.  No-op if no component
	 * was specified.
	 */
	public void focusComponent() {
		if (_component != null) {
			_component.requestFocus();
		}
	}
}
