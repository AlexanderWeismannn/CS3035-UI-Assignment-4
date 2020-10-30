package app;

import java.util.ArrayList;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.lang.Math;

public class Model {
	private SimpleListProperty<Shape> shapeListProperty;
	private double shapeSideLength;
	//private Shape currentShape;

	public Model(int squareSideLength) {
		ArrayList<Shape> list = new ArrayList<>();
		ObservableList<Shape> observableList = FXCollections.observableArrayList(list);
		shapeListProperty = new SimpleListProperty<>(observableList);

		this.shapeSideLength = squareSideLength;
	}

	public SimpleListProperty<Shape> shapeListProperty(){ return shapeListProperty; }

	public double getShapeSideLength() {return shapeSideLength;}

	//choose square
	public void addSquare(double x, double y)
	{
		double squareX = x - Main.model.getShapeSideLength()/2;
		double squareY = y - Main.model.getShapeSideLength()/2;

		Rectangle newSquare = new Rectangle(squareX, squareY, shapeSideLength, shapeSideLength);
		shapeListProperty.add(newSquare);
	}

	//choose circle
	public void addCircle(double x, double y){
		
		double circleX = x;
		double circleY = y;
		double radius = Main.model.getShapeSideLength()/2;
		
		Circle newCircle = new Circle(circleX,circleY,radius);
		shapeListProperty.add(newCircle);
	}

	//choose triangle
	public void addTriangle(double x, double y){

		Polygon newTriangle = new Polygon();
		newTriangle.getPoints().addAll(x + Main.model.getShapeSideLength()/2, y + Main.model.getShapeSideLength()/2,
												x - Main.model.getShapeSideLength()/2, y + Main.model.getShapeSideLength()/2,
												x,y - (Main.model.getShapeSideLength()/2 * Math.sqrt(3)/2) );
		shapeListProperty.add(newTriangle);
	}


	public void deleteSquareAt(int x, int y)
	{
		Shape delSquare = getSquareAt(x, y);
		shapeListProperty.remove(delSquare);
	}

	private Shape getSquareAt(int x, int y)
	{
		Shape square = null;

		for (Shape s : shapeListProperty)
		{
			if (s.contains(x, y))
			{
				square = s;
			}
		}

		return square;
	}

}
