/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.controllers;

import edu.vanier.template.ui.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;

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
    MenuItem mitBack;
    
    @FXML
    Slider slMag,slDir;
    
    @FXML
    Label lblMag,lblDir;
    
    @FXML
    PieChart pieForceDistribution;
    
    @FXML
    Pane megaPane;
    
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
            lblMag.setText("Magnitude: "+Math.round(slMag.valueProperty().getValue())+"N");
            
            for (Node n:megaPane.getChildren()){
                if (n instanceof VectorArrow){
                    if (((VectorArrow) n).getSelected()){ // gets to the current selected vector
                       // ((VectorArrow) n).setMaxWidth(slMag.valueProperty().getValue()*2.22+50);
                    ((Rectangle)((VectorArrow) n).getChildren().get(0)).setWidth(slMag.valueProperty().getValue()*2.22);
                    }
                }
            }
        });
        
        slDir.valueProperty().addListener((event)->{
            // future deli put uh the direction of the selected arrow using the same tan angle and 180
            // and make it spin around the box aswell, so you'll have to make a circle of radius r and then rotate about it
            // good luck lol o7
            
            
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
        };
    }
    

    private void loadPrimaryScene(Event e) {
        MainApp.switchScene(MainApp.priStage, MainApp.MAINAPP_SCENE);
    }
}
