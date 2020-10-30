package app;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Controller {
	public enum State {READY, DRAG_SELECTION_STARTED, DRAG_ITEMS_STARTED}
	private State state;

	public Controller() {
		Main.view.addEventHandler(MouseEvent.ANY, new MouseHandler());
		state = State.READY;
	}

	public class MouseHandler implements EventHandler<MouseEvent>{
		private double prevX = 0, prevY = 0;

		@Override
		public void handle(MouseEvent e) {
			switch(state)
			{
				case READY:
					if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
						prevX = e.getSceneX();
						prevY = e.getSceneY();

						if (Shape.class.isAssignableFrom(e.getTarget().getClass()))
						{
							Shape node = ((Shape) e.getTarget());
							node.toFront();

							if (!Main.iModel.getSelectedShapes().itemsProperty().contains(node))
							{
								if (!e.isControlDown())
									Main.iModel.getSelectedShapes().itemsProperty().clear();

								Main.iModel.getSelectedShapes().itemsProperty().add(node);
							}
							else if (e.isControlDown())
							{
								Main.iModel.getSelectedShapes().itemsProperty().remove(node);
							}
						}
						else
						{
							Main.iModel.getSelectedShapes().itemsProperty().clear();
						}
					}
					else if (e.getEventType()==MouseEvent.DRAG_DETECTED)
					{
						if (e.getTarget().getClass() == Main.view.getClass())
						{
							state = State.DRAG_SELECTION_STARTED;
							Main.iModel.startSelectRegion(e.getX(), e.getY());
						}
						else if (Shape.class.isAssignableFrom(e.getTarget().getClass()))
						{
							state = State.DRAG_ITEMS_STARTED;
						}
					}
					else if (e.getEventType()==MouseEvent.MOUSE_RELEASED)
					{
						if (e.getTarget().getClass()==Main.view.getClass())
						{
							//check if its a square / circle / or triangle
							if(Main.iModel.getButtonValue().equals("Square")){
								Main.model.addSquare(e.getX(), e.getY());
							}else if(Main.iModel.getButtonValue().equals("Circle")){
								Main.model.addCircle(e.getX(), e.getY());
							}else if(Main.iModel.getButtonValue().equals("Triangle")){
								Main.model.addTriangle(e.getX(), e.getY());
							}

						}

						if (Shape.class.isAssignableFrom(e.getTarget().getClass()))
						{
							if (!e.isControlDown())
							{
								Main.iModel.getSelectedShapes().itemsProperty().clear();
							}
						}
					}
					break;	//end State.READY

				case DRAG_SELECTION_STARTED:
					if (e.getEventType() == MouseEvent.MOUSE_DRAGGED)
					{
						Main.iModel.updateSelectRegion(e.getX(), e.getY());
					}
					else if (e.getEventType() == MouseEvent.MOUSE_RELEASED)
					{
						Rectangle selectionBox = Main.iModel.selectRegionProperty().getValue();

						if(e.getX() < selectionBox.getX()){
							selectionBox.setX(e.getX());
						}
						if(e.getY() < selectionBox.getY()){
							selectionBox.setY(e.getY());
						}
						//sets the new location for the selection box
						Main.iModel.selectRegionProperty().setValue(selectionBox);

						state = State.READY;
						selectObjectsWithRegion();
						//resets the box back to nothing or null
						Main.iModel.selectRegionProperty().setValue(null);
					}
					break;// end State.DRAG_SELECTION_STARTED

				case DRAG_ITEMS_STARTED:
					if (e.getEventType()==MouseEvent.MOUSE_DRAGGED)
					{
						moveSelected(e.getSceneX() - prevX, e.getSceneY() - prevY);
						prevX = e.getSceneX();
						prevY = e.getSceneY();
					}

					else if (e.getEventType()==MouseEvent.MOUSE_RELEASED)
					{
						state = State.READY;
					}
					break; //end State.DRAG_ITEMS_STARTED
			}//end switch(state)
		}
	}

	private void selectObjectsWithRegion() {
		Rectangle selectRegion = Main.iModel.selectRegionProperty().getValue();
		Main.iModel.getSelectedShapes().itemsProperty().clear();

		if (selectRegion != null) {
			for (Shape s : Main.model.shapeListProperty()) {
				Point2D topLeft = new Point2D(s.getBoundsInParent().getMinX(), s.getBoundsInParent().getMinY());
				Point2D bottomRight = new Point2D(s.getBoundsInParent().getMaxX(), s.getBoundsInParent().getMaxY());

				if (selectRegion.contains(topLeft) && selectRegion.contains(bottomRight)) {
					Main.iModel.getSelectedShapes().itemsProperty().add(s);
				}
			}
		}
	}

	private void moveSelected(double addX, double addY)
	{
		for (Shape r : Main.iModel.getSelectedShapes().itemsProperty())
		{
			r.setTranslateX(r.getLayoutX() + r.getTranslateX() + addX);
			r.setTranslateY(r.getLayoutY() + r.getTranslateY() + addY);
		}
	}


}
