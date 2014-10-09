package common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.lang3.math.NumberUtils;

public final class Utils {
  private Utils() {}
  
  /**
   * Creates a String by concatenating each item together, separated by
   * a comma and space
   * @param i - the Iterable type to write
   * @return a String consisting of the elements of <tt>i</tt>
   * separated by ", "
   */
  public static String mkString(Iterable<?> i) {
    return mkString(i, ", ");
  }
  
  /**
   * Creates a String by concatenating each item together, separated by the
   * given delimiter
   * @param i - the Iterable type to write
   * @param delimiter - the delimiter used to separate values
   * @return a String consisting of the elements of <tt>i</tt>
   * separated by <tt>delimiter</tt>
   */
  public static String mkString(Iterable<?> i, String delimiter) {
    final StringBuilder sb = new StringBuilder();
    for (final Iterator<?> iter = i.iterator(); iter.hasNext();) {
      sb.append(iter.next());
      if (iter.hasNext())
        sb.append(delimiter);
    }
    return sb.toString();
  }
  
  /**
   * Creates a String by concatenating each item together, separated by the
   * given delimiter, with the given prefix on the front and suffix on the end.
   * @param i - the Iterable type to write
   * @param prefix - a String to add to the front of the result
   * @param delimiter - the delimiter used to separate values
   * @param suffix - a String to add to the end of the result
   * @return a String consisting of <tt>prefix</tt>, the elements of <tt>i</tt>
   * separated by <tt>delimiter</tt>, and the <tt>suffix</tt>
   */
  public static String mkString(Iterable<?> i, String prefix, String delimiter, String suffix) {
    return prefix + mkString(i, delimiter) + suffix;
  }
  
  /**
   * Counts the number of items and returns a String describing the size.
   * The pluralizing suffix is assumed to be "s".  Examples:
   * <ul>
   *   <li><tt>countItems({1}, "thing") ==> "1 thing"</tt></li>
   *   <li><tt>countItems({1, 2}, "item") ==> "2 items"</tt></li>
   *   <li><tt>countItems({1, 2, 3}, "fish") ==> "3 fishs"</tt></li>
   * </ul>
   * 
   * @param collection - the collection of items to count
   * @param typename - the name of the type to use
   * @return a String describing the size of <tt>collection</tt>
   */
  public static String countItems(Collection<?> collection, String typename) {
    return countItems(collection, typename, "s");
  }
  
  /**
   * Counts the number of items and returns a String describing the size.  Examples:
   * <ul>
   *   <li><tt>countItems({1}, "thing", "s") ==> "1 thing"</tt></li>
   *   <li><tt>countItems({1, 2}, "item", "s") ==> "2 items"</tt></li>
   *   <li><tt>countItems({1, 2, 3}, "fish", "es") ==> "3 fishes"</tt></li>
   * </ul>
   * 
   * @param collection - the collection of items to count
   * @param typename - the name of the type to use
   * @param pluralizer - the suffix to use to pluralize <tt>typename</tt>
   * @return a String describing the size of <tt>collection</tt>
   */
  public static String countItems(Collection<?> collection, String typename, String pluralizer) {
    final int size = collection.size();
    return size + " " + typename + (size == 1 ? "" : pluralizer);
  }
  
  /**
   * Creates a String that sorts and condenses a collection of Integers
   * into a comma-delimited list, with consecutive numbers represented
   * by a hyphen-separated range.  Examples:
   * <ul>
   *   <li><tt>makeRangeString({1, 2, 3}) ==> "1-3"</tt></li>
   *   <li><tt>makeRangeString({1, 3, 5}) ==> "1, 3, 5"<tt></li>
   *   <li><tt>makeRangeString({3, 1, 4, 6, 2}) ==> "1-4, 6"<tt></li>
   * </ul>
   * 
   * @param ints - the Integers to sort and group
   * @return a String with the integers sorted and grouped
   */
  public static String makeRangeString(Collection<Integer> ints) {
    // copy and remove duplicates:
    final List<Integer> copy = new ArrayList<>(new TreeSet<>(ints));
    final int size = copy.size();
    
    final int[] vals = new int[size]; for (int i = 0; i < size; ++i) vals[i] = copy.get(i).intValue();
    final int[] diffs = new int[size-1]; for (int i = 0; i < size-1; ++i) diffs[i] = vals[i+1]-vals[i];
    final StringBuilder sb = new StringBuilder();
    
    int i = 0, n, j;
    while (true) {
      n = vals[i];
      for (j = i; j < size-1 && diffs[j] == 1; ++j);
      sb.append(n);
      if (i != j) sb.append("-").append(n+j-i);
      i = j+1;
      if (i < size) sb.append(", ");
      else break;
    }
    
    return sb.toString();
  }
  
  /**
   * Computes the Levenshtein distance between two Strings.  The Levenshtein
   * distance is the minimum number of single-character insertions, deletions,
   * or substitutions needed to transform one String into the other.  Examples:
   * 
   * <code><ul>
   *   <li>levenshteinDistance("hello", "mello") == 1</li>
   *   <li>levenshteinDistance("hello", "world") == 4</li>
   *   <li>levenshteinDistance("ABC", "") == 3</li>
   * </ul></code>
   * @param s the first String
   * @param t the second String
   * @return the Levenshtein distance between the two Strings
   */
  public static int levenshteinDistance(String s, String t) {
    final int sLength = s.length();
    final int tLength = t.length();

    // degenerate cases
    if (s == t) return 0;
    if (sLength == 0) return tLength;
    if (tLength == 0) return sLength;
    
    final char[] sChars = s.toCharArray();
    final char[] tChars = t.toCharArray();

    // create two work vectors of integer distances
    final int vLength = tLength+1;
    final int[] v0 = new int[vLength];
    final int[] v1 = new int[vLength];
    
    int i, j;

    // initialize v0 (the previous row of distances)
    // this row is A[0][i]: edit distance for an empty s
    // the distance is just the number of characters to delete from t
    for (i = 0; i < vLength; ++i)
      v0[i] = i;

    for (i = 0; i < sLength; ++i) {
      // calculate v1 (current row distances) from the previous row v0

      // first element of v1 is A[i+1][0]
      // edit distance is delete (i+1) chars from s to match empty t
      v1[0] = i + 1;

      // use formula to fill in the rest of the row
      for (j = 0; j < tLength; ++j) {
        final int cost = (sChars[i] == tChars[j]) ? 0 : 1;
        v1[j + 1] = NumberUtils.min(v1[j] + 1, v0[j + 1] + 1, v0[j] + cost);
      }

      // copy v1 (current row) to v0 (previous row) for next iteration
      for (j = 0; j < vLength; ++j)
        v0[j] = v1[j];
    }

    return v1[tLength];
  }
  
  /**
   * Returns the longest substring contained in each input String
   * @param s the first String
   * @param t the second String
   * @return the longest common substring of s and t
   */
  public static String longestCommonSubstring(String s, String t) {
    final int sLength = s.length();
    final int tLength = t.length();
    
    final char[] sChars = s.toCharArray();
    final char[] tChars = t.toCharArray();
    
    int maxLen = 0;
    int endIndexPlus1 = -1;
    int[][] L = new int[sLength+1][tLength+1];
 
    for (int i = 1; i <= sLength; i++) {
      for (int j = 1; j <= tLength; j++) {
        if (sChars[i-1] == tChars[j-1]) {
          if (i == 1 || j == 1)
            L[i][j] = 1;
          else
            L[i][j] = L[i-1][j-1] + 1;
          
          if (L[i][j] > maxLen) {
            maxLen = L[i][j];
            endIndexPlus1 = i;
          }
        }
      }
    }
    
    return s.substring(endIndexPlus1-maxLen, endIndexPlus1);
  }
  
  /**
   * Returns the longest common substring of all given Strings
   * @param strings the Strings to process
   * @return the longest common substring of all given Strings
   */
  public static String longestCommonSubstring(String... strings) {
    final int length = strings.length;
    if (length == 0) return "";
    else if (length == 1) return strings[0];
    
    String lcs = longestCommonSubstring(strings[0], strings[1]);
    for (int i = 2; i < strings.length; ++i)
      lcs = longestCommonSubstring(lcs, strings[i]);
    return lcs;
  }
}
