package controller;



import core.BSTreeInterface;
import core.NodeTreeInterface;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import shape.Circle;
import shape.Line;

public class GraphicsTree<T extends Comparable<? super T>> extends Canvas  {

	private NodeTreeInterface<T> root;
	private int treeHeight;

	@Override
	public boolean isResizable() {
		return true;
	}

	public GraphicsTree(BSTreeInterface<T> myTree) {
		treeHeight = myTree.getHeight()+1;

		root = myTree.getRoot();

		widthProperty().addListener(evt -> drawTree());
		heightProperty().addListener(evt -> drawTree());
	}

	private void drawTree() {
		double width = getWidth();
		double height = getHeight();

		GraphicsContext gc = getGraphicsContext2D();
		gc.clearRect(0, 0, width, height);

		// draw lines and circles
		if (root != null) {
			drawLines(gc, root, 0, this.getWidth(), 0, this.getHeight() / treeHeight);
			drawCircles(gc, root, 0, this.getWidth(), 0, this.getHeight() / treeHeight);
		}
	}

	private void drawLines(GraphicsContext gc, NodeTreeInterface<T> treeNode, double xMin, double xMax, double yMin, double yMax) {
		Point2D p1, p2;
		Line newLine;

		// If left node is not null then draw a line to it
		if (treeNode.getLeft() != null) {

			// start and end points of the line
			p1 = new Point2D(((xMin + xMax) / 2), yMin + yMax / 2);
			p2 = new Point2D(((xMin + (xMin + xMax) / 2) / 2), yMin + yMax + yMax / 2);
			newLine= new Line(p1, p2);
			newLine.draw(gc);

			// Recurse
			drawLines(gc, treeNode.getLeft(), xMin, (xMin + xMax) / 2, yMin + yMax, yMax);
		}

		// If right node is not null then draw a line to it
		if (treeNode.getRight() != null) {

			// start and end points of the line
			p1 = new Point2D((xMin + xMax) / 2, yMin + yMax / 2);
			p2 = new Point2D((xMax + (xMin + xMax) / 2) / 2, yMin + yMax + yMax / 2);
			newLine= new Line(p1, p2);
			newLine.draw(gc);

			// Recurse
			drawLines(gc, treeNode.getRight(), (xMin + xMax) / 2, xMax, yMin + yMax, yMax);
		}
	}

	private void drawCircles(GraphicsContext gc, NodeTreeInterface<T> treeNode, double xMin, double xMax, double yMin, double yMax) {

		// Create a new point
		Point2D point = new Point2D((xMin + xMax) / 2, yMin + yMax / 2);

		// Draw the circle
		Circle<T> x = new Circle<>(treeNode.getData(), point);
		x.draw(gc);

		// Recurse
		if (treeNode.getLeft() != null) {
			drawCircles(gc, treeNode.getLeft(), xMin, (xMin + xMax) / 2, yMin + yMax,	yMax);
		}

		// Recurse
		if (treeNode.getRight() != null) {
			drawCircles(gc, treeNode.getRight(), (xMin + xMax) / 2, xMax, yMin + yMax, yMax);
		}
	}

	public void clearCanvas() {
		getGraphicsContext2D().clearRect(0, 0, this.getWidth(), this.getHeight());
	}

}