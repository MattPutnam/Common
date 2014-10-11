package common.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import common.tuple.Pair;

/**
 * A file reader for reading property files.  Property files contain
 * entries of the form "[key]=[value]", with comment lines starting with a
 * specified marker (default "#")
 * 
 * @author Matt Putnam
 */
public class PropertiesFileReader implements AutoCloseable {
  private final BufferedReader _reader;
  private final String _commentMarker;
  
  private String _line;
  private boolean _hasBeenRead;
  
  /**
   * Creates a PropertiesFileReader for the given file and the comment
   * marker "#"
   * @param file the file to read
   * @throws FileNotFoundException if the file does not exist, is a directory
   * rather than a regular file, or for some other reason cannot be opened for reading.
   */
  public PropertiesFileReader(File file) throws FileNotFoundException {
    this(file, "#");
  }
  
  /**
   * Creates a PropertiesFileReader for the given file and comment marker
   * @param file the file to read
   * @param commentMarker the comment marker
   * @throws FileNotFoundException if the file does not exist, is a directory
   * rather than a regular file, or for some other reason cannot be opened for reading.
   */
  public PropertiesFileReader(File file, String commentMarker) throws FileNotFoundException {
    _reader = new BufferedReader(new FileReader(file));
    _commentMarker = commentMarker;
    
    _hasBeenRead = false;
  }
  
  /**
   * @return <tt>true</tt> iff there are still more properties to read
   * @throws IOException If an I/O error occurs
   */
  public synchronized boolean hasNext() throws IOException {
    if (!_hasBeenRead) {
      _line = getLine();
      _hasBeenRead = true;
    }
    return _line != null;
  }
  
  /**
   * Gets the next property as a Pair<String, String>.  The key is the first
   * item in the pair, the value is the second.
   * @return the next property
   * @throws IOException If an I/O error occurs
   * @throws ParseException If the next non-comment line fails to be read
   * @throws IllegalStateException If there is no next element
   */
  public synchronized Pair<String, String> next() throws IOException, ParseException {
    if (!_hasBeenRead) {
      _line = getLine();
    }
    
    if (_line == null)
      throw new IllegalStateException("No next String!");
    
    final int equalsIndex = _line.indexOf("=");
    if (equalsIndex == -1) {
      throw new ParseException("Unable to parse preferences line: " + _line, -1);
    }
    
    _hasBeenRead = false;
    
    return Pair.make(_line.substring(0, equalsIndex).trim(), _line.substring(equalsIndex+1).trim());
  }
  
  private String getLine() throws IOException {
    String line = null;
    while (true) {
      line = _reader.readLine();
      if (line == null)
        return null;
      
      line = line.trim();
      if (!line.isEmpty() && !line.startsWith(_commentMarker))
        return line;
    }
  }
  
  @Override
  public synchronized void close() throws Exception {
    _reader.close();
  }
}
