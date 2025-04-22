/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.controllers;

import edu.vanier.template.ui.*;
import edu.vanier.template.tests.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;

/**
 * The class that controls the FXML elements of the Forces simulation
 * @author ChiliWasp
 */
public class ForcesFXMLController {
    
    @FXML
    Button btnBack, btnIgnore, btnDelete, btnSummonVector;
    
    @FXML
    MenuBar menuBar;
    
    @FXML
    Menu menuHelp;
    
    @FXML
    MenuItem mitBack, mitClose;
    
    @FXML
    Slider slMag,slDir;
    
    @FXML
    Label lbMag,lbDir;
    
    @FXML
    PieChart pieForceDistribution;
    
    @FXML
    public Pane megaPane;
    
    @FXML
    StackPane Arrow;
    
    @FXML
    Rectangle box,recBackground,forceArrowBox;
    
    boolean selectingVector=false;
    
    @FXML
    public void initialize() {
        
        
        btnBack.setVisible(false);
        Arrow.setVisible(false);
        mitBack.setOnAction(this::loadPrimaryScene);
        mitClose.setOnAction((event)->{
            Stage s = (Stage)megaPane.getScene().getWindow();
            s.close();
        });
        
            // a temporary summon vector button since i havent gotten dragging yet
        btnSummonVector.setOnAction((event)->{
        VectorArrow sp=new VectorArrow(btnSummonVector.getLayoutX(), btnSummonVector.getLayoutY()+100);
        sp.setVisible(true);
        sp.setOnMouseDragged(onVectorDragged(sp));
        sp.setOnMousePressed(onVectorPressed(sp));
        megaPane.getChildren().add(sp);
        });
        
        slMag.setMax(100);
        slMag.setMin(0);
        slMag.valueProperty().setValue(50);
        slMag.valueProperty().addListener((event)->{ // magnitude slider: changes length of selected vectors tail
            lbMag.setText("Magnitude: "+Math.round(slMag.valueProperty().getValue())+"N");
            VectorArrow v=getSelectedVector();
            ((Rectangle)((VectorArrow) v).getChildren().get(0)).setWidth(slMag.valueProperty().getValue()*2.22);
            v.setMagnitude(slMag.valueProperty().getValue());
        });
        
        slDir.setMax(360);
        slDir.setMin(0);
        slDir.valueProperty().setValue(0);
        slDir.valueProperty().addListener((event)->{
            lbDir.setText("Direction: "+Math.round(slDir.valueProperty().getValue())+"°");
            VectorArrow v=getSelectedVector();
            v.setRotate(-1*(slDir.valueProperty().getValue()));
            v.setRotation(slDir.valueProperty().getValue()); // real life rotation value for math reasons
            
            double vX=v.getLayoutX();
            double vY=v.getLayoutY();
            double boxX=box.getLayoutX();
            double boxY=box.getLayoutY();
            double X=vX-boxX; // x difference between vector and box
            double Y=vY-boxY; // y dif of vector and box
            double hyp=Math.sqrt(Math.pow(X, 2)+Math.pow(Y, 2));
            double newX=hyp*Math.cos(Math.toRadians(v.getRotate()));
            double newY=hyp*Math.sin(Math.toRadians(v.getRotate()));
            v.setLayoutX(newX+boxX);
            v.setLayoutY(newY+boxY); // moves the vector along a radius around the box
            
            v.setRotate(v.getRotate()+180);
        });
        
        btnDelete.setOnAction((event)->{
            VectorArrow v=getSelectedVector();
            megaPane.getChildren().remove(v);
        });
        btnIgnore.setOnAction((event)->{
            getSelectedVector();
        });
        
        recBackground.setOnMousePressed(onVectorPressed(recBackground));
        
    }
    
    /**
     * Runs through every child of the pane and gets the VectorArrow selected.
     * @return the selected VectorArrow
     */
    public VectorArrow getSelectedVector(){
        for (Node n:megaPane.getChildren()){
            if (n instanceof VectorArrow){
                if (((VectorArrow) n).getSelected()) {
                    return ((VectorArrow) n);
                }
            }
        }
        return null;
    }
    
    
    /**
     * this runs everything that must happen upon a vector getting selected (or unselected), such as: 
     * marking it as (un)selected and enabling/disabling sliders and buttons.
     * 
     */
    private void toggleSelectedVector(){
        
        if(selectingVector==true){
            slMag.setDisable(false);
            slDir.setDisable(false);
            btnIgnore.setDisable(false);
            btnDelete.setDisable(false);
            
            
        }
        else {
            slMag.setDisable(true);
            slDir.setDisable(true);
            btnIgnore.setDisable(true);
            btnDelete.setDisable(true);
        }
    }
    /**
     * Unselects all currently selected vectors. Sets ALL vectors to unselected and un-outlines them. 
     * There should only be 1 selected vector at a time, so it would only unselect that selected vector.
     */
    private void unselectAllVectors(){
        for (Node v:megaPane.getChildren())
            if (v instanceof VectorArrow){ // gets all VectorArrows
                for (int i=0;i<((VectorArrow) v).getChildren().size();i++){
                    Shape s=(Shape)((VectorArrow) v).getChildren().get(i); // gets the parts of a vector arrow
                    s.setStrokeWidth(0); // sets them to un-outlined
                }
                ((VectorArrow)v).setSelected(false); // unselects that vector (even if it's already unselected)
            }
        
        selectingVector=false; // 
    }
    
    /**
     * Updates the magnitude slider to match the currently selected vector's magnitude.
     */
    private void updateMagnitude(){
        VectorArrow v=getSelectedVector();
        slMag.valueProperty().setValue(v.getMagnitude());
        lbMag.setText("Magnitude: "+(int)v.getMagnitude()+"N");
    }
    /**
     * Updates the direction slider to match the currently selected vector's direction.
     */
    private void updateDirection(){
        VectorArrow v=getSelectedVector();
        double rotate=v.getRotate()*-1+180;
        if (rotate<0)
            rotate+=360;
        slDir.valueProperty().setValue(rotate);
    }
    
    public double combineMagnitudeX(){
        double totalX=0;
        for (Node n:megaPane.getChildren()){ // checks all children of the pane
            if (n instanceof VectorArrow){ // makes sure we're only dealing with VectorArrows
                VectorArrow v=(VectorArrow)n;
                double mag=v.getMagnitude();
                double ang=v.getRotation();
                
            }
            
        }
        return 0;
    }
    
    /**
     * Handles the clicking of a vector arrow. Selects the vector you click.
     * @param v The vector arrow clicked
     * @return the event of the vector arrow being clicked
     */
    private EventHandler<MouseEvent> onVectorPressed(Node n){
        return (MouseEvent t) -> {
            if (n instanceof VectorArrow){ // if you pressed a VectorArrow
                unselectAllVectors(); //unselects all previously selected vectors
                
                ((VectorArrow) n).setSelected(true); // sets the vector being clicked to selected
                for (int i=0;i<((VectorArrow) n).getChildren().size();i++){
                    Shape s=(Shape)((VectorArrow) n).getChildren().get(i); // gets the parts of a vector arrow
                    s.setStrokeWidth(1.5); // sets them to outlined
                    System.out.println();
                }
                
                selectingVector=true; // so that the program knows a vector is selected right now
                toggleSelectedVector();
                
                updateMagnitude();
                updateDirection();
            } else if (n instanceof Rectangle){ // if you pressed on the background (to unselect the vector)
                if (((Rectangle) n).getId().equals(recBackground.getId())){
                unselectAllVectors();
                toggleSelectedVector(); }
            }
        };
    };
    /**
     * Handles dragging a vector around. Moves the vector arrow being dragged to your mouse.
     * @param v The vector arrow being dragged
     * @return the new position of the vector arrow
     */
    private EventHandler<MouseEvent> onVectorDragged(VectorArrow v){
        return (MouseEvent t) -> {
            v.setLayoutX(t.getSceneX()-v.getWidth()/2);
            v.setLayoutY(t.getSceneY()-v.getHeight()/2);
            double ArrowX=v.getBoundsInParent().getCenterX();
            double ArrowY=v.getBoundsInParent().getCenterY();
            double boxCenterX=box.getBoundsInParent().getCenterX();
            double boxCenterY=box.getBoundsInParent().getCenterY();
            double rotate=Math.atan((boxCenterY-ArrowY)/(boxCenterX-ArrowX));
              rotate=Math.toDegrees(rotate);
              
            if (ArrowX<=boxCenterX)
                v.setRotate(rotate);
            else v.setRotate(rotate+180);
            if (v.getRotate()<0)
                v.setRotate(v.getRotate()+360);
            
            v.setRotation(v.getRotate());
            updateDirection();
        };
    }
    
/**
 * loads the main menu scene.
 */
    private void loadPrimaryScene(Event e) {
        MainApp.switchScene(MainApp.priStage, MainApp.MAINAPP_SCENE);
    }
}
