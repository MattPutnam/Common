package common.swing;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.JLabel;

public class GradientLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	
	private Color _leftColor;
	private Color _rightColor;
	
	public GradientLabel(String text, Color left, Color right) {
		super(text);
		_leftColor = left;
		_rightColor = right;
	}
	
	public Color getLeftColor() {
		return _leftColor;
	}

	public void setLeftColor(Color leftColor) {
		_leftColor = leftColor;
	}

	public Color getRightColor() {
		return _rightColor;
	}

	public void setRightColor(Color rightColor) {
		_rightColor = rightColor;
	}

	@Override
	public void paint(Graphics g) {
		final int width = getWidth();
		final int height = getHeight();
		
		final GradientPaint paint = new GradientPaint(0, 0, _leftColor, width, height, _rightColor, true);
		final Graphics2D g2d = (Graphics2D) g;
		final Paint oldPaint = g2d.getPaint();
		
		g2d.setPaint(paint);
		g2d.fillRect(0, 0, width, height);
		g2d.setPaint(oldPaint);
		
		super.paint(g);
	}
}
