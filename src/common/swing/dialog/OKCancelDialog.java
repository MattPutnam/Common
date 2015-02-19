package common.swing.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;

import common.swing.VerificationException;

/**
 * A JDialog subclass that includes OK and Cancel buttons, and provides the
 * user with the ability to tell which was pressed.
 * 
 * @author Matt Putnam
 */
public abstract class OKCancelDialog extends JDialog {
  private static final long serialVersionUID = 1L;
  
  private final Component _parent;
  
  private JButton _okButton;
  private JButton _cancelButton;
  
  private boolean _okPressed;
  
  public static OKCancelDialog create(Component parent, String title,
      JComponent content) {
    return new OKCancelDialog(parent) {
      private static final long serialVersionUID = -6634180474470652854L;

      @Override
      protected void verify() throws VerificationException { /* no op */ }
      
      @Override
      protected String declareTitle() {
        return title;
      }
      
      @Override
      protected JComponent buildContent() {
        return content;
      }
    };
  }
  
  /**
   * Creates a simple dialog from a given JComponent and displays it, blocks,
   * and returns whether or not the OK button was pressed.
   * @param parent the Component to anchor the dialog above.  Can be null,
   *        which causes the dialog to appear at the center of the screen.
   * @param title the title for the dialog
   * @param content the component that will comprise the dialog
   * @return <tt>true</tt> iff the OK button was pressed.
   */
  public static boolean showInDialog(Component parent, String title,
      JComponent content) {
    final OKCancelDialog dialog = create(parent, title, content);
    dialog.showDialog();
    return dialog.okPressed();
  }

  public static <T extends JComponent> void showInDialog(
      final Component parent, final String title, final T content,
      final OnOK<T> onOK) {
    if (showInDialog(parent, title, content))
      onOK.doOnOK(content);
  }
  
  /**
   * Shows a dialog and performs a given action if the OK button is pressed.
   * @param dialog the dialog to show
   * @param onOK a function from the created dialog to <tt>void</tt> that is
   *        called if the OK button is pressed
   */
  public static <T extends OKCancelDialog> void showDialog(final T dialog, OnOK<T> onOK) {
    dialog.showDialog();
    if (dialog.okPressed())
      onOK.doOnOK(dialog);
  }
  
  /**
   * A Functional Interface 
   * @param <T>
   */
  @FunctionalInterface
  public static interface OnOK<T extends Component> {
    public void doOnOK(T component);
  }
  
  /**
   * Creates a new OKCancelDialog
   * @param parent the Component to anchor this dialog above.  Can be
   *        null, which causes this to anchor to the center of the screen.
   */
  public OKCancelDialog(Component parent) {
    _parent = parent;
  }
  
  /**
   * Builds the view.
   */
  public final void showDialog() {
    _okButton = new JButton("OK");
    _cancelButton = new JButton("Cancel");
    getRootPane().setDefaultButton(_okButton);
    
    _okButton.addActionListener(e -> {
      try {
        verify();
        _okPressed = true;
        takeActionOnOK();
        dispose();
      } catch (VerificationException ve) {
        Dialog.error(OKCancelDialog.this, ve.getMessage(), "Malformed Input");
        ve.focusComponent();
      }
    });
    
    _cancelButton.addActionListener(e -> dispose());
    
    final JPanel subPanel = new JPanel();
    subPanel.add(_okButton);
    subPanel.add(_cancelButton);
    for (final JButton button : declareAdditionalButtons()) {
      subPanel.add(button);
    }
    
    setLayout(new BorderLayout());
    
    final JComponent content = buildContent();
    content.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(2, 2, 2, 2),
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            BorderFactory.createEmptyBorder(2, 2, 2, 2))));
    
    add(content, BorderLayout.CENTER);
    add(subPanel, BorderLayout.SOUTH);
    
    pack();
    setModal(true);
    setTitle(declareTitle());
    
    initialize();
    
    setLocationRelativeTo(_parent);
    setVisible(true);
  }
  
  /**
   * Returns whether or not this dialog was dismissed by pressing the "OK"
   * button.
   * @return
   */
  public final boolean okPressed() {
    return _okPressed;
  }
  
  /**
   * Programmatically presses the OK button
   */
  protected final void pressOK() {
    _okButton.doClick();
  }
  
  /**
   * Subclasses can override this method to take any action when the OK
   * button is pressed.
   */
  protected void takeActionOnOK() {}
  
  /**
   * Subclasses override this method to build the main content of the dialog
   * @return the main component of the dialog
   */
  protected abstract JComponent buildContent();
  
  /**
   * Subclasses can override this to add any dialog-level initialization,
   * such as resizability or size.  pack(), setModal(true), and
   * setTitle(declareTitle()) are called before initialize(), followed by
   * setLocationRelativeTo(parent) and setVisible(true)
   * 
   */
  protected void initialize() {}
  
  /**
   * Declares the title for this dialog.
   * @return the title for this dialog
   */
  protected abstract String declareTitle();
  
  /**
   * Used to declare any additional buttons that should go alongside the OK
   * and cancel buttons.
   * @return
   */
  protected JButton[] declareAdditionalButtons() { return new JButton[] {}; }
  
  /**
   * Called when the OK button is pressed.  Subclasses should check the
   * user's inputs and throw a VerificationException if any are invalid.
   * @throws VerificationException
   */
  protected abstract void verify() throws VerificationException;
}
