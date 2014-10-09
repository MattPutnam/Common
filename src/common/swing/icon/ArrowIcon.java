package common.swing.icon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import common.swing.ColorUtils;
import common.swing.ImageUtils;

public class ArrowIcon implements Icon, SwingConstants {
  private static final float DB = -0.06f;
  
  private final int _direction;
  private final int _size;
  private final Color _color;
  private BufferedImage _arrowImage;
  
  public ArrowIcon(int direction) {
    this(direction, 10, null);
  }
  
  public ArrowIcon(int direction, Color color) {
    this(direction, 10, color);
  }
  
  public ArrowIcon(int direction, int size, Color color) {
    _direction = direction;
    _size = size;
    _color = color;
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
    g.drawImage(getArrowImage(), x, y, c);
  }
  
  protected Image getArrowImage() {
    if (_arrowImage == null) {
      _arrowImage = ImageUtils.createTranslucentImage(_size, _size);
      
      final AffineTransform atx = _direction != SOUTH ? new AffineTransform() : null;
      switch (_direction) {
        case NORTH:
          atx.setToRotation(Math.PI, _size/2, _size/2);
          break;
        case EAST:
          atx.setToRotation(-Math.PI/2, _size/2, _size/2);
          break;
        case WEST:
          atx.setToRotation(Math.PI/2, _size/2, _size/2);
        case SOUTH: // intentional fallthrough
        default: // nothing
      }
      
      final Graphics2D ig = (Graphics2D) _arrowImage.getGraphics();
      if (atx != null)
        ig.setTransform(atx);
      
      final int width = _size;
      final int height = _size/2 + 2;
      final int xx = (_size - width) / 2;
      final int yy = (_size - height + 1) / 2;
      
      final Color base = _color != null ? _color : UIManager.getColor("controlDkShadow").darker();
      
      paintArrow(ig, base, xx, yy);
      paintArrowBevel(ig, base, xx, yy);
      paintArrowBevel(ig, ColorUtils.deriveColorHSB(base, 0f, 0f, .20f), xx, yy+1);
    }
    
    return _arrowImage;
  }
  
  protected void paintArrow(Graphics2D g, Color base, int x, int y) {
    g.setColor(base);
    
    int len = _size - 2;
    int xx = x;
    int yy = y-1;
    while (len >= 2) {
      ++xx;
      ++yy;
      g.fillRect(xx, yy, len, 1);
      len -= 2;
    }
  }
  
  protected void paintArrowBevel(Graphics2D g, Color base, int x, int y) {
    int len = _size;
    int xx = x;
    int yy = y;
    Color c2 = ColorUtils.deriveColorHSB(base, 0f, 0f, (-DB)*(_size/2));
    while (len >= 2) {
      c2 = ColorUtils.deriveColorHSB(c2, 0f, 0f, DB);
      g.setColor(c2);
      g.fillRect(xx, yy, 1, 1);
      g.fillRect(xx + len - 1, yy, 1, 1);
      len -= 2;
      ++xx;
      ++yy;
    }
  }
}
