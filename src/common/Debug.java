package common;

public class Debug {
  private static final boolean DEBUG = true;
  
  public static void print(String s) {
    if (DEBUG) System.out.print(s);
  }
  
  public static void println() {
    if (DEBUG) System.out.println();
  }
  
  public static void println(String s) {
    if (DEBUG) System.out.println(s);
  }
  
  public static void print(boolean on, String s) {
    if (on) System.out.print(s);
  }
  
  public static void println(boolean on) {
    if (on) System.out.println();
  }
  
  public static void println(boolean on, String s) {
    if (on) System.out.println(s);
  }
}
