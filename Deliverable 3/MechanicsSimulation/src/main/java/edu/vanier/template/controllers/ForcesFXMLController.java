/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.controllers;

import edu.vanier.template.ui.*;
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
    Pane megaPane;
    
    @FXML
    StackPane Arrow;
    
    @FXML
    Rectangle box,recBackground,forceArrowBox;
    
    boolean selectingVector=false;
    
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
            for (Node n:megaPane.getChildren()){
                if (n instanceof VectorArrow){
                    if (((VectorArrow) n).getSelected()){ // gets to the current selected vector
                       // ((VectorArrow) n).setMaxWidth(slMag.valueProperty().getValue()*2.22+50);
                    ((Rectangle)((VectorArrow) n).getChildren().get(0)).setWidth(slMag.valueProperty().getValue()*2.22);
                    }
                }
            }
        });
        
        slDir.setMax(360);
        slDir.setMin(0);
        slDir.valueProperty().setValue(0);
        slDir.valueProperty().addListener((event)->{
            lbDir.setText("Direction: "+Math.round(slDir.valueProperty().getValue())+"°");
            VectorArrow v=getSelectedVector();
            v.setRotate(-1*(slDir.valueProperty().getValue()));
            v.setRotation(slDir.valueProperty().getValue()); // true rotation value
            System.out.println(v.getLayoutX());
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
            v.setLayoutY(newY+boxY);
            
            System.out.println(v.getRotation());
            System.out.println(v.getRotate());
            v.setRotate(v.getRotate()+180);
        });
        
        btnDelete.setOnAction((event)->{
            for (Node n:megaPane.getChildren()){
                if (n instanceof VectorArrow){
                    if (((VectorArrow) n).getSelected()){
                        megaPane.getChildren().remove(n); break;}
                }
            }
        });
        
        //for (Node n:megaPane.getChildren()) n.setOnMousePressed(onVectorPressed(n));
        recBackground.setOnMousePressed(onVectorPressed(recBackground));
        
    }
    /**
     * this runs everything that must happen upon a vector getting selected (or unselected), such as: 
     * marking it as (un)selected, enabling/disabling sliders and buttons, 
     * 
     */
    private void toggleSelectedVector(){
        if(selectingVector){
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
    private void unselectAllVectors(){
        for (Node v:megaPane.getChildren()) if (v instanceof VectorArrow) ((VectorArrow)v).setSelected(false);
        selectingVector=false;
    }
    
    /**
     * Handles clicking of a vector arrow.
     * @param v The vector arrow clicked
     * @return the stats clicked, bro.
     */
    private EventHandler<MouseEvent> onVectorPressed(Node n){
        return (MouseEvent t) -> {
            if (n instanceof VectorArrow){
                unselectAllVectors();
                ((VectorArrow) n).setSelected(true); 
                selectingVector=true;
                toggleSelectedVector();
            } else if (n instanceof Rectangle){
                if (((Rectangle) n).getId().equals(recBackground.getId())){
                unselectAllVectors();
                toggleSelectedVector(); }
            }
        };
    };
    /**
     * Moves the selected vector to the mouse (you can drag the vector around)
     * @param v The vector arrow being dragged
     * @return the new position of the vector arrow
     */
    private EventHandler<MouseEvent> onVectorDragged(VectorArrow v){
        return (MouseEvent t) -> {
            v.setLayoutX(t.getSceneX()-55);
            v.setLayoutY(t.getSceneY()-(88/2));
            double ArrowX=v.getBoundsInParent().getCenterX();
            double ArrowY=v.getBoundsInParent().getCenterY();
            double boxCenterX=box.getBoundsInParent().getCenterX();
            double boxCenterY=box.getBoundsInParent().getCenterY();
//            System.out.println(ArrowX);
//            System.out.println(boxCenterX);
//            System.out.println(ArrowY);
//            System.out.println(boxCenterY);
            double rotate=Math.atan((boxCenterY-ArrowY)/(boxCenterX-ArrowX));
//            System.out.println(rotate);
              rotate=Math.toDegrees(rotate);
            //rotate=rotate/Math.PI*180;
            if (ArrowX<=boxCenterX)
            v.setRotate(rotate);
            else v.setRotate(rotate +180);
            if (v.getRotate()<0)
                v.setRotate(v.getRotate()+360);
            System.out.println(v.getRotate());
            v.setRotation(v.getRotate());
        };
    }
    

    private void loadPrimaryScene(Event e) {
        MainApp.switchScene(MainApp.priStage, MainApp.MAINAPP_SCENE);
    }
}
