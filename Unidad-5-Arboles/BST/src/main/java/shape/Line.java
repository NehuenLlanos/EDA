package shape;

import javafx.geometry.Point2D; 
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class Line {

	private Point2D point1, point2;
	private Color color = Color.GRAY;

	public Line(Point2D point1, Point2D point2) {
		this.point1 = point1;
		this.point2 = point2;
	}

	public void draw(GraphicsContext gc) {
		gc.setLineWidth(4); // Sets the width of the lines

		gc.setStroke(color);
		gc.strokeLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
	}

	@Override
	public String toString() {
		return " (x,y) = ("  + point1.getX() + ", " + point1.getY() + ")"
				+  " (x,y) = ("  + point2.getX() + ", " + point2.getY()+ ")";
	}

}