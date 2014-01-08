package common.swing;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.SwingConstants;

public class ImageUtils {
	private ImageUtils() {}
	
	/**
	 * Returns a BufferedImage with a data layout and color model compatible
	 * with this GraphicsConfiguration, with the given size.
	 * @param width the width of the desired image
	 * @param height the height of the desired image
	 * @return a BufferedImage with the given size
	 */
	public static BufferedImage createCompatibleImage(int width, int height) {
		return GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getDefaultConfiguration()
				.createCompatibleImage(width, height);
	}

	/**
	 * Returns a translucent BufferedImage with a data layout and color model
	 * compatible with this GraphicsConfiguration, with the given size.
	 * @param width the width of the desired image
	 * @param height the height of the desired image
	 * @return a translucent BufferedImage with the given size
	 */
	public static BufferedImage createTranslucentImage(int width, int height) {
		return GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getDefaultConfiguration()
				.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
	}

	
	public static BufferedImage createGradientImage(int width, int height, Color color1, Color color2) {
		final BufferedImage gradientImage = createCompatibleImage(width, height);
		final GradientPaint gradient = new GradientPaint(0,  0, color1,  0, height, color2, false);
		final Graphics2D g2 = (Graphics2D) gradientImage.getGraphics();
		
		g2.setPaint(gradient);
		g2.fillRect(0, 0, width, height);
		g2.dispose();
		
		return gradientImage;
	}

	public static BufferedImage createGradientMask(int width, int height, int orientation) {
		final BufferedImage gradient = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g = gradient.createGraphics();
		final GradientPaint paint = new GradientPaint(0.0f, 0.0f,
				new Color(1.0f, 1.0f, 1.0f, 1.0f),
				orientation == SwingConstants.HORIZONTAL ? width : 0.0f,
				orientation == SwingConstants.VERTICAL ? height : 0.0f,
				new Color(1.0f, 1.0f, 1.0f, 0.0f));
		g.setPaint(paint);
		g.fill(new Rectangle2D.Double(0, 0, width, height));
		
		g.dispose();
		gradient.flush();
		
		return gradient;
	}
}
