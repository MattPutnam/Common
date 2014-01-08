package common.swing.icon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.Icon;

import common.swing.ImageUtils;

public class DeleteIcon implements Icon {
	private final int _size;
	private BufferedImage _image;
	
	public DeleteIcon(int size) {
		_size = size;
	}

	@Override
	public int getIconHeight() {
		return _size;
	}

	@Override
	public int getIconWidth() {
		return _size;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.drawImage(getImage(), x, y, c);
	}
	
	protected Image getImage() {
		if (_image == null) {
			_image = ImageUtils.createTranslucentImage(_size, _size);
			
			final Graphics g = _image.getGraphics();
			
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, _size, _size);
			
			final int s = 3;
			final int l = _size-4;
			g.setColor(Color.DARK_GRAY);
			g.drawRect(0, 0, _size-1, _size-1);
			g.drawLine(s, s, l, l);
			g.drawLine(s, l, l, s);
		}
		return _image;
	}
}
