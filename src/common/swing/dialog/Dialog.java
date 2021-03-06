package common.swing.dialog;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * A collection of utilites for displaying dialogs
 * 
 * @author Matt Putnam
 */
public class Dialog {
  private Dialog() {}
  
  /**
   * Asks the user to confirm something.
   * @param parent the parent component, used for anchoring.  Can be null.
   * @param message the message to display
   * @return <tt>true</tt> if the user confirmed the dialog
   */
  public static boolean confirm(Component parent, Object message) {
    return confirm(parent, message, "Confirm");
  }
  
  /**
   * Asks the user to confirm something, and runs some logic if confirmed.
   * @param parent the parent component, used for anchoring.  Can be null.
   * @param message the message to display
   * @param onOK the logic to run if confirmed
   */
  public static void confirm(Component parent, Object message, Runnable onOK) {
    if (confirm(parent, message))
      onOK.run();
  }
  
  /**
   * Asks the user to confirm something.
   * @param parent the parent component, used for anchoring.  Can be null.
   * @param message the message to display
   * @param title the title of the dialog
   * @return <tt>true</tt> if the user confirmed the dialog
   */
  public static boolean confirm(Component parent, Object message, String title) {
    int result = JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.OK_CANCEL_OPTION);
    return result == JOptionPane.OK_OPTION;
  }
  
  /**
   * Asks the user to confirm something, and runs some logic if confirmed
   * @param parent the parent component, used for anchoring.  Can be null.
   * @param message the message to display
   * @param title the title of the dialog
   * @param onOK the logic to run if confirmed
   */
  public static void confirm(Component parent, Object message, String title, Runnable onOK) {
    if (confirm(parent, message, title))
      onOK.run();
  }
  
  /**
   * Asks the user to answer yes or no to a question.
   * @param parent the parent component, user for anchoring.  Can be null.
   * @param message the message to display
   * @param title the title of the dialog
   * @return <tt>true</tt> if the user answered "yes"
   */
  public static boolean askYesNo(Component parent, Object message, String title) {
    int result = JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    return result == JOptionPane.OK_OPTION;
  }
  
  /**
   * Asks the user to answer yes or no to a question, and runs some logic either way
   * @param parent the parent component, user for anchoring.  Can be null.
   * @param message the message to display
   * @param title the title of the dialog
   * @param onYes the logic to run if the 'Yes' button is pressed
   * @param onNo the logic to run if the 'No' button is pressed
   */
  public static void askYesNo(Component parent, Object message, String title, Runnable onYes, Runnable onNo) {
    if (askYesNo(parent, message, title))
      onYes.run();
    else
      onNo.run();
  }
  
  /**
   * Notifies the user of an error.
   * @param parent the parent component, used for anchoring.  Can be null.
   * @param message the message to display
   */
  public static void error(Component parent, Object message) {
    error(parent, message, "Error");
  }
  
  /**
   * Notifies the user of an error.
   * @param parent the parent component, used for anchoring.  Can be null.
   * @param message the message to diplay
   * @param title the title of the dialog
   */
  public static void error(Component parent, Object message, String title) {
    JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
  }
  
  /**
   * Displays information to the user.
   * @param parent the parent component, used for anchoring.  Can be null.
   * @param message the message to display
   */
  public static void info(Component parent, Object message) {
    info(parent, message, "Info");
  }
  
  /**
   * Displays information to the user.
   * @param parent the parent component, used for anchoring.  Can be null.
   * @param message the message to display
   * @param title the title of the dialog
   */
  public static void info(Component parent, Object message, String title) {
    JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
  }
}
