package common;

import java.util.Comparator;

import common.tuple.Pair;

public class Comparators {
  private Comparators() {}
  
  /**
   * Compares strings based on number first, then letter.<br>
   * A < B < 1 < 1A < 2 < 5C < 10 < 10A
   */
  public static Comparator<String> NUMERO_ALPHA = (s1, s2) -> {
    Pair<Integer, String> nl1 = getNumberAndLetter(s1);
    Pair<Integer, String> nl2 = getNumberAndLetter(s2);
    
    int temp = nl1._1().intValue() - nl2._1().intValue();
    if (temp == 0)
      return nl1._2().compareTo(nl2._2());
    else
      return temp;
  };
    
  private static Pair<Integer, String> getNumberAndLetter(String s) {
    StringBuilder sb = new StringBuilder();
    for (char c : s.toCharArray()) {
      if (Character.isDigit(c))
        sb.append(c);
      else break;
    }
    
    final Integer number = sb.length() == 0 ? Integer.valueOf(0) : Integer.valueOf(sb.toString());
    return Pair.make(number, s.substring(sb.length()));
  };
}
