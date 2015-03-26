package common.swing;

import java.awt.Color;
import java.io.PrintStream;

/**
 * A collection of utilities for manipulating {@link Color} objects.  Colors
 * are stored in RGB form internally, so this class has methods for
 * interpreting the colors in other spaces.
 * 
 * @author Matt Putnam
 */
public class ColorUtils {
  private ColorUtils() {}
  
  /**
   * Creates a color by applying an alpha to an exiting color (if the existing
   * color already has an alpha, it is overwritten)
   * @param base the base color
   * @param alpha the alpha to apply
   * @return a new color with the alpha applied
   */
  public static Color deriveColorAlpha(Color base, int alpha) {
    return new Color(base.getRed(), base.getGreen(), base.getBlue(), alpha);
  }

  /**
   * Creates a color by using an existing color and applying a differential to
   * the HSB values (as floats from 0 to 1).  If the differential pushes those
   * values out of range they are clamped to be in range.
   * @param base the base color
   * @param dH the change in hue
   * @param dS the change in saturation
   * @param dB the change in brightness
   * @return a new color with the changes applied
   */
  public static Color deriveColorHSB(Color base, float dH, float dS, float dB) {
    final float[] hsb = Color.RGBtoHSB(base.getRed(), base.getGreen(), base.getBlue(), null);
    hsb[0] += dH;
    hsb[1] += dS;
    hsb[2] += dB;
    return Color.getHSBColor(
        hsb[0] < 0 ? 0 : (hsb[0] > 1 ? 1 : hsb[0]),
        hsb[1] < 0 ? 0 : (hsb[1] > 1 ? 1 : hsb[1]),
        hsb[2] < 0 ? 0 : (hsb[2] > 1 ? 1 : hsb[2]));
  }

  /**
   * Creates an HTML String representation of the given color.  This is a '#'
   * followed by the RGB values as 2-digit hex strings.
   * @param color the color
   * @return the color as an HTML string
   */
  public static String getHTMLColorString(Color color) {
    final String red   = Integer.toHexString(color.getRed());
    final String green = Integer.toHexString(color.getGreen());
    final String blue  = Integer.toHexString(color.getBlue());
    
    return "#" +
        (red.length()   == 1 ? "0" + red   : red) +
        (green.length() == 1 ? "0" + green : green) +
        (blue.length()  == 1 ? "0" + blue  : blue);
  }

  /**
   * Prints a description of the given color to a PrintStream.  Prints both
   * the RGB and the HSB formats.
   * @param out the output PrintStream to use
   * @param key a name to prepend on the output
   * @param color the color to print
   */
  public static void printColor(PrintStream out, String key, Color color) {
    final int r = color.getRed(), g = color.getGreen(), b = color.getBlue();
    final float hsb[] = Color.RGBtoHSB(r, g, b, null);
    out.println(key + ": RGB=" + r + "," + g + "," + b + "  " +
              "HSB=" + String.format("%.0f%n", Float.valueOf(hsb[0]*360)) + "," +
                       String.format("%.3f%n", Float.valueOf(hsb[1])) + "," +
                       String.format("%.3f%n", Float.valueOf(hsb[2])));
  }
  
  /**
   * Gets the hue value as a float from 0 to 1.
   * @param color the color to use
   * @return the hue of the color
   */
  public static float getHue(Color color) {
    return Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null)[0];
  }
  
  /**
   * Gets the saturation value as a float from 0 to 1.
   * @param color the color to use
   * @return the saturation of the color
   */
  public static float getSaturation(Color color) {
    return Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null)[1];
  }
  
  /**
   * Gets the brightness value as a float from 0 to 1.
   * @param color the color to use
   * @return the brightness of the color
   */
  public static float getBrightness(Color color) {
    return Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null)[2];
  }
  
  /**
   * Computes the <a href=http://en.wikipedia.org/wiki/Relative_luminance>
   * relative luminance</a> of the given color, which is defined as
   * <pre>0.2126*R + 0.7152*G + 0.0722*B</pre>
   * @param color the color to use
   * @return the luminance as a value between 0 and 1
   */
  public static double getLuminance(Color color) {
    return (0.2126 * color.getRed() +
            0.7152 * color.getGreen() +
            0.0722 * color.getBlue())
          / 255.0;
  }
}
