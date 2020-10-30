package app;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class InteractionModel {

    private SelectionGroup copiedShapes;
    private SelectionGroup selectedShapes;
    private SimpleObjectProperty<Rectangle> selectRegion;
    private String toggleButtonVal;

    public InteractionModel()
    {
        copiedShapes = new SelectionGroup();
        selectedShapes = new SelectionGroup();
        selectRegion = new SimpleObjectProperty<Rectangle>();
        toggleButtonVal = "Square";
    }

    public SimpleObjectProperty<Rectangle> selectRegionProperty()
    {
        return selectRegion;
    }

    public SelectionGroup getSelectedShapes(){
        return selectedShapes;
    }

    public void startSelectRegion(double x, double y)
    {
        selectRegion.set(new Rectangle(x,y,0,0));
    }

    //update the region that is dragged and selected
    public void updateSelectRegion(double x, double y)
    {
        Rectangle selectRect = selectRegionProperty().getValue();
        double deltaX = x - selectRect.getX();
        double deltaY = y - selectRect.getY();

        //if the x goes into the negative
        if(deltaX < 0){
            selectRect.setTranslateX(deltaX);
            selectRect.setWidth(-deltaX);
            //will need to change the actual square that is checking the bounds
        }else{
            selectRect.setWidth(x - selectRect.getX());
        }
        //if the y goes into the negative
        if(deltaY < 0){
            selectRect.setTranslateY(deltaY);
            selectRect.setHeight(-deltaY);
            ////will need to change the actual square that is checking the bounds
        }else{
            selectRect.setHeight(y - selectRect.getY());
        }



    }

    public void setCopiedShapes(SelectionGroup chosenGroup){
        if(chosenGroup != null){
            for(int i = 0; i < chosenGroup.itemsProperty().getSize(); i++ ) {
                copiedShapes.itemsProperty().add(chosenGroup.itemsProperty().get(i));
            }
        }

    }

    public SelectionGroup getCopiedShapes(){return copiedShapes;}

    public void setButtonValue(String toggleButton) {
        toggleButtonVal = toggleButton;
    }

    public String getButtonValue(){
        return toggleButtonVal;
    }
}