package common.swing;

import java.awt.Color;
import java.io.PrintStream;

public class ColorUtils {
	private ColorUtils() {}
	
	public static Color deriveColorAlpha(Color base, int alpha) {
		return new Color(base.getRed(), base.getGreen(), base.getBlue(), alpha);
	}

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

	public static String getHTMLColorString(Color color) {
		final String red   = Integer.toHexString(color.getRed());
		final String green = Integer.toHexString(color.getGreen());
		final String blue  = Integer.toHexString(color.getBlue());
		
		return "#" +
				(red.length()   == 1 ? "0" + red   : red) +
				(green.length() == 1 ? "0" + green : green) +
				(blue.length()  == 1 ? "0" + blue  : blue);
	}

	public static void printColor(PrintStream out, String key, Color color) {
		final int r = color.getRed(), g = color.getGreen(), b = color.getBlue();
		final float hsb[] = Color.RGBtoHSB(r, g, b, null);
		out.println(key + ": RGB=" + r + "," + g + "," + b + "  " +
							"HSB=" + String.format("%.0f%n", Float.valueOf(hsb[0]*360)) + "," +
									 String.format("%.3f%n", Float.valueOf(hsb[1])) + "," +
									 String.format("%.3f%n", Float.valueOf(hsb[2])));
	}
	
	public static float getHue(Color color) {
		return Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null)[0];
	}
	
	public static float getSaturation(Color color) {
		return Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null)[1];
	}
	
	public static float getBrightness(Color color) {
		return Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null)[2];
	}
	
	public static double getLuminance(Color color) {
	  return (0.2126 * color.getRed() +
	          0.7152 * color.getGreen() +
	          0.0722 * color.getBlue())
	        / 255.0;
	}
}
