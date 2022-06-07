package shape;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public final class Circle<T extends Comparable<? super T>>  {
	final int fontSize= 16;
	
	final Font font = Font.font("Cooper Black", FontWeight.BOLD, fontSize);

	public static final int RADIUS = 26;
	private final T data;

	// The circle attributes
	private Point2D point;
	private Color backgroundColor;
	private Color borderColor;
	private Color fontColor;

	public Circle(T myData, Point2D point) {
		this.data = myData;
		this.point = point;
		this.backgroundColor = Color.LAVENDER;
		this.borderColor= Color.GRAY;
		this.fontColor = Color.BLACK;
	}

	public void draw(GraphicsContext gc) {
		gc.setLineWidth(3); // Sets the width of the lines

		gc.setFill(backgroundColor);
		gc.fillOval(point.getX() - RADIUS, point.getY() - RADIUS, 2 * RADIUS, 2 * RADIUS);

		gc.setStroke(borderColor);
		gc.strokeOval(point.getX() - RADIUS, point.getY() - RADIUS, 2 * RADIUS, 2 * RADIUS);

		gc.setFont(font);
		gc.setFill(fontColor);
		gc.fillText(getData().toString(),
				point.getX() - fontSize / 2,
				point.getY() + fontSize / 4);
	}

	public T getData() {
		return this.data;
	}

	@Override
	public String toString() {
		return "Search Key# " + data  +
				" (x,y) = ("  + point.getX() + ", " + point.getY() + ")";
	}

}