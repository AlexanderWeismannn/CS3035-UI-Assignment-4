package app;

import java.util.ArrayList;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.scene.shape.Shape;

public class SelectionGroup {
	private SimpleListProperty<Shape> items;
	private BoundingBox bounds = new BoundingBox(0, 0, 0, 0);

}
