package common.io;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import org.apache.commons.lang3.SystemUtils;

import common.swing.DefaultFileFilter;
import common.swing.SwingUtils;

public class IOUtils {
  private IOUtils() {}
  
  public static List<String> getLineList(File file) throws IOException {
    return getLineList(file, Integer.MAX_VALUE);
  }
  
  public static List<String> getLineList(File file, int numLines) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      final List<String> lines = new ArrayList<>();
      int i = 0;
      while ((line = reader.readLine()) != null && i < numLines) {
        lines.add(line);
        i++;
      }
      return lines;
    }
  }
  
  public static String[] getLineArray(File file) throws IOException {
    final List<String> lines = getLineList(file);
    return lines.toArray(new String[lines.size()]);
  }
  
  public static String[] getLineArray(File file, int numLines) throws IOException {
    final List<String> lines = getLineList(file, numLines);
    return lines.toArray(new String[numLines]);
  }
  
  public static void copyFile(File srcFile, File dstFile) throws IOException {
    if (!dstFile.exists())
      dstFile.createNewFile();
    
    try (FileInputStream inStream = new FileInputStream(srcFile);
       FileOutputStream outStream = new FileOutputStream(dstFile)) {
      final FileChannel srcChannel = inStream.getChannel();
      final FileChannel dstChannel = outStream.getChannel();
      
      dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
    }
  }
  
  /**
   * Shows an OS-specific file open dialog.  Mac OS X has a different LAF; this
   * method checks the platform and shows the correct dialog
   * @param parent the parent component, used to anchor the dialog.  On Mac OS X
   * this must be a Frame or Dialog instance, on any other OS this can be any kind
   * of Component or null.
   * @param directory the starting directory, or null to use the platform default
   * @param extension the extension to accept, or null to accept any file type
   * @param fileDescription a description to use to describe the file type.  Unused
   * on Mac OS X.  May not be null if not on OS X and using a file extension filter.
   * @return an array of Files selected, or an empty array if the dialog was canceled
   */
  public static File[] showOpenFileDialog(Component parent, File directory,
      final String extension, final String fileDescription) {
    if (SystemUtils.IS_OS_MAC_OSX) {
      final FileDialog dialog;
      final Frame frame = SwingUtils.findContainingFrame(parent);
      if (frame != null)
        dialog = new FileDialog(SwingUtils.findContainingFrame(parent));
      else {
        final Dialog d = SwingUtils.findContainingDialog(parent);
        dialog = new FileDialog(d);
      }
      
      if (directory != null)
        dialog.setDirectory(directory.getAbsolutePath());
      if (extension != null)
        dialog.setFilenameFilter(new DefaultFileFilter(extension, fileDescription));
      dialog.setVisible(true);
      
      return dialog.getFiles();
    } else {
      final JFileChooser dialog = new JFileChooser();
      if (directory != null)
        dialog.setCurrentDirectory(directory);
      if (extension != null)
        dialog.setFileFilter(new DefaultFileFilter(extension, fileDescription));
      dialog.showOpenDialog(parent);
      
      return dialog.getSelectedFiles();
    }
  }
  
  /**
   * Shows an OS-specific file save dialog.  Mac OS X has a different LAF; this
   * method checks the platform and shows the correct dialog
   * @param parent the parent component, used to anchor the dialog.  On Mac OS X
   * this must be a Frame or Dialog instance, on any other OS this can be any kind
   * of Component or null.
   * @param directory the starting directory, or null to use the platform default
   * @param extension the extension to accept, or null to accept any file type
   * @param fileDescription a description to use to describe the file type.  Unused
   * on Mac OS X.  May not be null if not on OS X and using a file extension filter.
   * @return the File selected, null if the dialog was canceled
   */
  public static File showSaveFileDialog(Component parent, File directory,
      final String extension, final String fileDescription) {
    if (SystemUtils.IS_OS_MAC_OSX) {
      final FileDialog dialog;
      final Frame frame = SwingUtils.findContainingFrame(parent);
      if (frame != null)
        dialog = new FileDialog(SwingUtils.findContainingFrame(parent));
      else {
        final Dialog d = SwingUtils.findContainingDialog(parent);
        dialog = new FileDialog(d);
      }
      dialog.setMode(FileDialog.SAVE);
      dialog.setTitle("Save as...");
      
      if (directory != null)
        dialog.setDirectory(directory.getAbsolutePath());
      if (extension != null)
        dialog.setFilenameFilter(new DefaultFileFilter(extension, fileDescription));
      dialog.setVisible(true);
      
      final File[] files = dialog.getFiles();
      return files.length == 0 ? null : files[0];
    } else {
      final JFileChooser dialog = new JFileChooser();
      if (directory != null)
        dialog.setCurrentDirectory(directory);
      if (extension != null)
        dialog.setFileFilter(new DefaultFileFilter(extension, fileDescription));
      dialog.showSaveDialog(parent);
      return dialog.getSelectedFile();
    }
  }
}
