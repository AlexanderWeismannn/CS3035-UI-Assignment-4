package app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.Objects;

public class View extends Pane {
	public static final Color FILL_COLOR = Color.GREEN;
	public static final Color SELECTED_FILL_COLOR = Color.BLUE;
	private static Group root;

	public View() {

		Main.model.shapeListProperty().addListener(new ListChangeListener<Shape>() {
			@Override
			public void onChanged(Change<? extends Shape> c) {
				while (c.next())
				{
					for (Shape r : c.getRemoved())
						root.getChildren().remove(r);

					for (Shape r : c.getAddedSubList())
					{
						r.setStroke(Color.BLACK);
						r.setFill(FILL_COLOR);
						root.getChildren().add(r);
					}
				}
			}
		});
		Main.iModel.getSelectedShapes().itemsProperty().addListener((ListChangeListener<Shape>) c -> {
			deselectAll();
			for (Shape r : Main.iModel.getSelectedShapes().itemsProperty()) {
				selectSquare(r);
			}
		});

		Main.iModel.selectRegionProperty().addListener((ChangeListener<Shape>) (observable, oldValue, newValue) -> {
			root.getChildren().remove(oldValue);

			if (newValue !=null) {
				root.getChildren().add(newValue);
				newValue.setFill(new Color(0,0,.5,.3));
				newValue.setStroke(new Color(0,0,.5,1));
			}
		});

		//creating a Vbox, Toolbar and the initial buttons for the toolbar
		VBox container = new VBox();
		ToolBar toolBar = new ToolBar();
		//binding the length of the toolbar to that of the View
		toolBar.prefWidthProperty().bind(this.widthProperty());


		Image squareImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/square.png")));
		Image circleImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/circle.png")));
		Image triangleImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/triangle.png")));
		Image cutImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/cut.png")));
		Image pasteImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/paste.png")));

		ToggleGroup group = new ToggleGroup();

		ToggleButton square = new ToggleButton("", new ImageView(squareImage));
		square.setToggleGroup(group);
		square.setSelected(true);

		ToggleButton circle = new ToggleButton("", new ImageView(circleImage));
		circle.setToggleGroup(group);

		ToggleButton triangle = new ToggleButton("", new ImageView(triangleImage));
		triangle.setToggleGroup(group);


		Button cut = new Button("", new ImageView(cutImage));
		Button paste = new Button("", new ImageView(pasteImage));

		//initializing the tooltips for each button
		Tooltip squareToolTip = new Tooltip("Square");
		Tooltip circleToolTip = new Tooltip("Circle");
		Tooltip triangleToolTip = new Tooltip("Triangle");
		Tooltip cutToolTip = new Tooltip("Cut");
		Tooltip pasteToolTip = new Tooltip("Paste");


		//adding the tooltips to each button
		square.setTooltip(squareToolTip);
		circle.setTooltip(circleToolTip);
		triangle.setTooltip(triangleToolTip);
		cut.setTooltip(cutToolTip);
		paste.setTooltip(pasteToolTip);

		//calling the methods to switch the object type depending on which toggle button is pressed
		group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

			if(newValue != null){
				//get the toolTip text of the toggleButton
				String toggleButton = ((ToggleButton)newValue).getTooltip().getText();
				//if no button has been pressed we will default it to be square
				Main.iModel.setButtonValue(toggleButton);
			}

		});


		//if cut is called, copy the selected values and delete the selected values
		cut.setOnAction(e -> {
			Main.iModel.setCopiedShapes(Main.iModel.getSelectedShapes());
			for(int i = 0; i < Main.iModel.getCopiedShapes().itemsProperty().getSize(); i++){
				Main.model.shapeListProperty().remove(Main.iModel.getCopiedShapes().itemsProperty().get(i));
			}

		});

		//add the cut values back into the arraylist
		paste.setOnAction(e -> {
			//check to make sure that something has actually been copied
			if(Main.iModel.getCopiedShapes() != null){
					Main.model.shapeListProperty().addAll(Main.iModel.getCopiedShapes().itemsProperty());

					Main.iModel.getCopiedShapes().itemsProperty().clear();

			}

		});



		//adding the buttons to the toolbar and adding everything into the Vbox
		toolBar.getItems().addAll(square,circle,triangle,new Separator(),cut,paste);
		container.getChildren().add(toolBar);
		root = new Group();
		getChildren().addAll(container,root);



	}


	public void deselect(Shape r)
	{
		r.setFill(FILL_COLOR);
		r.setStrokeWidth(1);
	}

	public void deselectAll() {
		for (Shape s : Main.model.shapeListProperty())
		{
			deselect(s);
		}
	}

	public void selectSquare(Shape node) {
		node.setFill(View.SELECTED_FILL_COLOR);
		node.setStrokeWidth(4);
	}
}
