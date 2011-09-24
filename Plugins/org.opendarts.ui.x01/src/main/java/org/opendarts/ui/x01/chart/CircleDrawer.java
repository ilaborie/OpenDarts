package org.opendarts.ui.x01.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import org.jfree.ui.Drawable;

/**
 * The Class CircleDrawer.
 */
public class CircleDrawer implements Drawable {

	/** The outline paint. */
	private final Paint outlinePaint;

	/** The outline stroke. */
	private final Stroke outlineStroke;

	/** The fill paint. */
	private final Paint fillPaint;

	/**
	 * Instantiates a new circle drawer.
	 *
	 * @param paramPaint1 the param paint1
	 * @param paramStroke the param stroke
	 * @param paramPaint2 the param paint2
	 */
	public CircleDrawer(Paint paramPaint1, Stroke paramStroke, Paint paramPaint2) {
		this.outlinePaint = paramPaint1;
		this.outlineStroke = paramStroke;
		this.fillPaint = paramPaint2;
	}

	/* (non-Javadoc)
	 * @see org.jfree.ui.Drawable#draw(java.awt.Graphics2D, java.awt.geom.Rectangle2D)
	 */
	@Override
	public void draw(Graphics2D paramGraph, Rectangle2D paramRect) {
		Ellipse2D.Double ellipse = new Ellipse2D.Double(paramRect.getX(),
				paramRect.getY(), paramRect.getWidth(), paramRect.getHeight());
		if (this.fillPaint != null) {
			paramGraph.setPaint(this.fillPaint);
			paramGraph.fill(ellipse);
		}
		if ((this.outlinePaint != null) && (this.outlineStroke != null)) {
			paramGraph.setPaint(this.outlinePaint);
			paramGraph.setStroke(this.outlineStroke);
			paramGraph.draw(ellipse);
		}
		paramGraph.setPaint(Color.black);
		paramGraph.setStroke(new BasicStroke(1.0F));
		Line2D.Double localDouble1 = new Line2D.Double(paramRect.getCenterX(),
				paramRect.getMinY(), paramRect.getCenterX(),
				paramRect.getMaxY());
		Line2D.Double localDouble2 = new Line2D.Double(paramRect.getMinX(),
				paramRect.getCenterY(), paramRect.getMaxX(),
				paramRect.getCenterY());
		paramGraph.draw(localDouble1);
		paramGraph.draw(localDouble2);
	}
}