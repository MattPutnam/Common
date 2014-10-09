package common.swing;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class GraphicsUtils {
  private GraphicsUtils() {}
  
  /**
   * {@link Graphics#drawString(String, int, int)} is fucked up when a
   * rotation is applied since Java 1.6 on Mac OS X.  Drawing a single
   * character works, but the method doesn't move to the next character
   * position correctly.  Use this instead.
   * @param g the Graphics context
   * @param text the text to draw
   * @param x the starting x coordinate, in the graphics coordinate frame
   * @param y the starting y coordinate, in the graphics coordinate frame
   */
  public static void drawString(Graphics g, String text, int x, int y) {
    final FontMetrics fm = g.getFontMetrics();
    for (final char c : text.toCharArray()) {
      final String str = String.valueOf(c);
      g.drawString(str, x, y);
      x += fm.getStringBounds(str, g).getWidth();
    }
  }
  
  /**
   * Draws an arrow from (x1, y1) to (x2, y2), composed of a simple line and
   * 5-pixel arrowhead at the (x2, y2) end
   * @param g2d the Graphics2D context to use
   * @param x1 the x coordinate of the start point
   * @param y1 the y coordinate of the start point
   * @param x2 the x coordinate of the end point
   * @param y2 the y coordinate of the end point
   */
  public static void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
    g2d.drawLine(x1, y1, x2, y2);
    
    final AffineTransform tx = g2d.getTransform();

    final Polygon arrowHead = new Polygon();  
    arrowHead.addPoint(0, 0);
    arrowHead.addPoint(-5, -5);
    arrowHead.addPoint(5, -5);
    
      final double angle = Math.atan2(y2-y1, x2-x1);
      tx.translate(x2, y2);
      tx.rotate((angle-Math.PI/2d));  

      final Graphics2D g = (Graphics2D) g2d.create();
      g.setTransform(tx);   
      g.fill(arrowHead);
      g.dispose();
  }
  
  /**
   * Creates a thick arrow Polygon poinint from the "from" point to the "to" point
   * @param fromPt the start point
   * @param toPt the end point
   * @return an arrow shape from fromPt to toPt
   */
  public static Shape createArrowShape(Point fromPt, Point toPt) {
      Polygon arrowPolygon = new Polygon();
      arrowPolygon.addPoint(-6,1);
      arrowPolygon.addPoint(3,1);
      arrowPolygon.addPoint(3,3);
      arrowPolygon.addPoint(6,0);
      arrowPolygon.addPoint(3,-3);
      arrowPolygon.addPoint(3,-1);
      arrowPolygon.addPoint(-6,-1);


      Point midPoint = midpoint(fromPt, toPt);

      double rotate = Math.atan2(toPt.y - fromPt.y, toPt.x - fromPt.x);

      AffineTransform transform = new AffineTransform();
      transform.translate(midPoint.x, midPoint.y);
      double ptDistance = fromPt.distance(toPt);
      double scale = ptDistance / 12.0; // 12 because it's the length of the arrow polygon.
      transform.scale(scale, scale);
      transform.rotate(rotate);

      return transform.createTransformedShape(arrowPolygon);
  }

  /**
   * Calculates the midpoint between two points
   * @param p1 the first point
   * @param p2 the second point
   * @return the midpoint between p1 and p2
   */
  public static Point midpoint(Point p1, Point p2) {
      return new Point((int)((p1.x + p2.x)/2.0), 
                       (int)((p1.y + p2.y)/2.0));
  }
  
  /**
   * Uses {@link RenderingHints} to tell the given graphics object to antialias
   * @param g the graphics object to antialias
   */
  public static void antialias(Graphics2D g) {
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
  }
}
