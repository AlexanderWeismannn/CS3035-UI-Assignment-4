package app;

import java.util.ArrayList;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class SelectionGroup {
	private SimpleListProperty<Shape> items;
	private BoundingBox bounds = new BoundingBox(0, 0, 0, 0);

	public SelectionGroup()
	{
		ArrayList<Shape> list = new ArrayList<>();
		ObservableList<Shape> observableList = FXCollections.observableArrayList(list);
		items = new SimpleListProperty<>(observableList);

		itemsProperty().addListener((ListChangeListener<Shape>) c -> calculateBoundingBox());
	}

	public BoundingBox getBoundingBox()
	{
		return bounds;
	}

	public SimpleListProperty<Shape> itemsProperty()
	{
		return items;
	}

	private void calculateBoundingBox() {

		double 	minX = Double.MAX_VALUE, minY = Double.MAX_VALUE,
				maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
		for (Shape r : itemsProperty())
		{
			if (r.getLayoutBounds().getMinX() < minX)
				minX = r.getLayoutBounds().getMinX();
			if (r.getLayoutBounds().getMinY() < minY)
				minY = r.getLayoutBounds().getMinY();
			if (r.getLayoutBounds().getMaxX() + r.getLayoutBounds().getWidth() > maxX)
				maxX = r.getLayoutBounds().getMaxX() + r.getLayoutBounds().getWidth();
			if (r.getLayoutBounds().getMaxY() + r.getLayoutBounds().getHeight() > maxY)
				maxY = r.getLayoutBounds().getMaxY() + r.getLayoutBounds().getHeight();
		}
		bounds = new BoundingBox(minX, minY, maxX - minX, maxY - minY);

	}

}
