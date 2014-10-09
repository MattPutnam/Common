package common.swing;

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.filechooser.FileFilter;

public class DefaultFileFilter extends FileFilter implements FilenameFilter {
  private final String _extension;
  private final String _description;
  
  public DefaultFileFilter(String extension, String description) {
    _extension = (extension.startsWith(".") ? extension : "." + extension).toLowerCase();
    _description = description + " (" + _extension + ")";
  }
  
  @Override
  public boolean accept(File file) {
    return file.isFile() && file.getName().toLowerCase().endsWith(_extension);
  }
  
  @Override
  public String getDescription() {
    return _description;
  }

  @Override
  public boolean accept(File dir, String name) {
    return name.toLowerCase().endsWith(_extension);
  }
  
}
