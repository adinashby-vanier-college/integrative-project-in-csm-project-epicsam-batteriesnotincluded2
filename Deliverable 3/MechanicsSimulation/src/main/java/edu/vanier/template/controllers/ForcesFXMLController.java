/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.controllers;

import edu.vanier.template.ui.*;
import edu.vanier.physics.*;
import edu.vanier.template.tests.*;
import java.util.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

/**
 * The class that controls the FXML elements of the Forces simulation
 * @author ChiliWasp
 */
public class ForcesFXMLController {
    
    @FXML
    Button btnBack, btnIgnore, btnDelete, btnSummonVector; // various buttons
    
    @FXML
    MenuBar menuBar;
    
    @FXML
    MenuButton showCalcs;
    
    @FXML
    Menu menuHelp;
    
    @FXML
    MenuItem mitBack, mitClose, mitAbout, mitTips, mitDark, mitLight, mitSaveOn, mitSaveOff, calcFx, calcFy; // the menu items in the menu bar
    
    @FXML
    Slider slMag,slDir,slMass; // the sliders
    
    @FXML
    Label lbMag,lbDir,lbMass,lbNetForce,lbNetDir; // the sliders' labels and other labels
    
    @FXML
    Label fma1,fma2,fma3,fma4,fma5; // the 5 equation lines
    
    @FXML
    PieChart pieForceDistribution;
    
    @FXML
    public Pane megaPane;
    
    @FXML
    StackPane Arrow;
    
    @FXML
    Rectangle box,recBackground,forceDragArea,recxtraBg,selectedVectorBox; // the box, the force drag area, and the backgrounds
    
    VectorArrow netVector;
    
    boolean selectingVector=false;
    public static boolean autosave=false;
    
    @FXML
    public void initialize() {
        netVector=new VectorArrow(box.getBoundsInParent().getMinX(), box.getBoundsInParent().getMinY());
        netVector.setNet(true);
        netVector.setColorYellow();
        netVector.setMagnitude(0);
        updateNetVector();
        selectedVectorBox.setFill(Color.GOLD);
        selectedVectorBox.setStroke(Color.DARKGOLDENROD);
        selectedVectorBox.setOpacity(0.5);
        recxtraBg.setVisible(true);
        recxtraBg.setOpacity(0.6);
        recxtraBg.setFill(Color.LIGHTGREEN);
        
        megaPane.getChildren().add(netVector);
        
        forceDragArea.setOpacity(0);
        forceDragArea.setOnMousePressed((event)->{
            btnSummonVector.fire();
        });
        
        
        btnBack.setVisible(false);
        Arrow.setVisible(false);
        pieForceDistribution.setVisible(true);
        mitBack.setOnAction(this::loadPrimaryScene);
        mitClose.setOnAction((event)->{
            Stage s = (Stage)megaPane.getScene().getWindow();
            s.close();
        });
        
        mitDark.setOnAction((event)->{
            recBackground.setFill(Color.GRAY);
            recBackground.setOpacity(0.6);
            recBackground.setVisible(true);
            recxtraBg.setVisible(true);
            recxtraBg.setFill(Color.GRAY);
            recxtraBg.setOpacity(0.6);
            
        });
        
        mitLight.setOnAction((event)->{
            recBackground.setOpacity(0);
            recxtraBg.setFill(Color.LIGHTGREEN);
        });
        
        mitSaveOff.setOnAction((event)->{
            autosave=false;
        });
        mitSaveOn.setOnAction((event)->{
            autosave=true;
        });
        mitAbout.setOnAction((event)->{
            Alert("Forces Simulation",
                    "Summation of vectors",
                    "You can add up vectors to see the magnitude and direction \nof the resultant vector.");
        });
        mitTips.setOnAction((event)->{
            Alert("Some Tips",
                    "Did you know?",
                    "- If the resultant magnitude is too small, it won't show.\n"
                            + "- You can use as many vectors as you want. There is no limit.\n"
                            + "- All vector colors are randomly generated.");
        });
        
        
            // a temporary summon vector button 
        btnSummonVector.setOnAction((event)->{
        VectorArrow sp=new VectorArrow(forceDragArea.getLayoutX()+forceDragArea.getWidth()/2-20, forceDragArea.getLayoutY());
        sp.setVisible(true);
        sp.setOnMouseDragged(onVectorDragged(sp));
        sp.setOnMousePressed(onVectorPressed(sp));
        megaPane.getChildren().add(sp);
        double ArrowX=sp.getBoundsInParent().getCenterX();
            double ArrowY=sp.getBoundsInParent().getCenterY();
            double boxCenterX=box.getBoundsInParent().getCenterX();
            double boxCenterY=box.getBoundsInParent().getCenterY();
            double rotate=Math.atan((boxCenterY-ArrowY)/(boxCenterX-ArrowX));
              rotate=Math.toDegrees(rotate);
              
            if (ArrowX<=boxCenterX)
                sp.setRotate(rotate);
            else sp.setRotate(rotate+180);
            if (sp.getRotate()<0)
                sp.setRotate(sp.getRotate()+360);
            
            sp.setRotation(sp.getRotate());
        
        });
        btnSummonVector.setVisible(false);
        
        slMag.setMax(100);
        slMag.setMin(0);
        slMag.valueProperty().setValue(50);
        slMag.valueProperty().addListener((event)->{ // magnitude slider: changes length of selected vectors taile
            lbMag.setText("Magnitude: "+Math.round(slMag.valueProperty().getValue())+"N");
            VectorArrow v=getSelectedVector();
            v.setMagnitude(slMag.valueProperty().getValue());
            updateMagnitude();
            updateNetVector();
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
            updateNetVector();
        });
        
        slMass.setMax(100);
        slMass.setMin(1);
        slMass.valueProperty().setValue(10);
        slMass.valueProperty().addListener((event)->{
            lbMass.setText("Mass of box: "+Math.round(slMass.valueProperty().getValue()*10.0)/10.0+"kg");
        });
        
        btnDelete.setOnAction((event)->{
            VectorArrow v=getSelectedVector();
            megaPane.getChildren().remove(v);
            updateNetVector();
            selectingVector=false; toggleSelectedVector();
        });
        btnIgnore.setOnAction((event)->{
            VectorArrow v=getSelectedVector();
            v.setIgnore(!v.getIgnore());
            updateNetVector();
        });
        recBackground.setOnMousePressed(onVectorPressed(recBackground));
        
        calcFx.setOnAction((event)->{
            String sma2="";
            String sma3="";
            String sma4="";
            String sma5="";
            double sum=0;
            for (Node n:megaPane.getChildren()){ // checks every children of the pane
            if (n instanceof VectorArrow){ // makes sure we're only dealing with VectorArrows
                VectorArrow v=(VectorArrow)n;
                if (v.getNet()==false&&v.getIgnore()==false) { // if the vector is not the net vector and not being ignored
                 sma2+=" "+slMass.valueProperty().getValue()+"*"+(Math.round(v.getMagnitude()*10.0)/10.0)+"cos"+(Math.round(v.getRotation()*10.0)/10.0)+" +";
                 sma3+=" "+slMass.valueProperty().getValue()+"*"+(Math.round(v.getMagnitude()*Math.cos(Math.toRadians(v.getRotation()))*10.0)/10.0)+" +";
                 sma4+=" "+( Math.round(slMass.valueProperty().getValue()*v.getMagnitude()*Math.cos(Math.toRadians(v.getRotation())*100.0))/100.0)+" +";
                }
            }
        }
            
            fma1.setText("Fx=ma");
            sma2=sma2.trim();
            sma3=sma3.trim();
            sma4=sma4.trim();
            sma5="Fx = "+(Math.round(addMagnitudeX()*10.0)/10.0);
            fma2.setText(sma2.substring(0,sma2.length()-1));
            fma3.setText(sma3.substring(0,sma3.length()-1));
            fma4.setText(sma4.substring(0, sma4.length()-1));
            fma5.setText(sma5);
        });
        
        calcFy.setOnAction((event)->{
         String sma2="";
            String sma3="";
            String sma4="";
            String sma5="";
            double sum=0;
            for (Node n:megaPane.getChildren()){ // checks every children of the pane
            if (n instanceof VectorArrow){ // makes sure we're only dealing with VectorArrows
                VectorArrow v=(VectorArrow)n;
                if (v.getNet()==false&&v.getIgnore()==false) { // if the vector is not the net vector and not being ignored
                 sma2+=" "+slMass.valueProperty().getValue()+"*"+(Math.round(v.getMagnitude()*10.0)/10.0)+"sin"+(Math.round(v.getRotation()*10.0)/10.0)+" +";
                 sma3+=" "+slMass.valueProperty().getValue()+"*"+(Math.round(v.getMagnitude()*Math.sin(Math.toRadians(v.getRotation()))*10.0)/10.0)+" +";
                 sma4+=" "+( Math.round(slMass.valueProperty().getValue()*v.getMagnitude()*Math.sin(Math.toRadians(v.getRotation())*100.0))/100.0)+" +";
                }
            }
        }
            
            fma1.setText("Fy=ma");
            sma2=sma2.trim();
            sma3=sma3.trim();
            sma4=sma4.trim();
            sma5="Fy = "+(Math.round(addMagnitudeY()*10.0)/10.0);
            fma2.setText(sma2.substring(0,sma2.length()-1));
            fma3.setText(sma3.substring(0,sma3.length()-1));
            fma4.setText(sma4.substring(0, sma4.length()-1));
            fma5.setText(sma5);
        });
        
        
    }// initialize method
    
    public ObservableList<PieChart.Data> magToPie(){
        ArrayList<PieChart.Data> pieArray=new ArrayList<>();
        for (Node n:megaPane.getChildren()){ // checks every children of the pane
            if (n instanceof VectorArrow){ // makes sure we're only dealing with VectorArrows
                VectorArrow v=(VectorArrow)n;
                if (v.getNet()==false&&v.getIgnore()==false) { // if the vector is not the net vector and not being ignored
                    pieArray.add(new PieChart.Data(v.getColor(),v.getMagnitude()));
                }
            }
        }
       return FXCollections.observableArrayList(pieArray);
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
     * Runs everything that must happen upon a vector getting selected (or unselected), such as: 
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
        pieForceDistribution.setData(magToPie());
        pieForceDistribution.setVisible(true);
        pieForceDistribution.setAnimated(false);
        pieForceDistribution.setLabelsVisible(true);
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
    /**
     * calculates and updates the net force of all vectors on the box, and updates the "Net force" label.
     * @return the net magnitude of all active vectors on the box
     */
    private double updateNetForce(){
        double mX=addMagnitudeX();
        double mY=addMagnitudeY();
        double mNet=Math.sqrt( Math.pow(mX, 2)+Math.pow(mY, 2) ); // finds magnitude of net vector
        mNet=Math.round(mNet*10.0)/10.0;
        lbNetForce.setText("Net Force: "+mNet+"N");
        netVector.setMagnitude(mNet);
        return mNet;
    }
    /**
     * calculates and updates the net direction of alle the vectors on the box, and updates the "direction" label.
     * @return the net direction of the resultant vector
     */
    private double updateNetDir(){
        double mX=addMagnitudeX();
        double mY=addMagnitudeY();
        double angNet=Math.atan2(mY, mX);
        angNet=Math.toDegrees(angNet);
        if (angNet<0) angNet+=360;
        angNet=Math.round((angNet)*10.0)/10.0; // finds angle of direction of net vector
        lbNetDir.setText("Direction: "+angNet+"°");
        return angNet;
    }
    
    /**
     * updates the magnitude and direction of the resultant vector. the vector will disappear if the resulting magnitude is too small.
     */
    private void updateNetVector(){
         netVector.setMagnitude(updateNetForce());
         netVector.setRotate(-1*updateNetDir());
         if (netVector.getMagnitude()<=5) netVector.setVisible(false);
         else netVector.setVisible(true);
    }
    
    
    public double addMagnitudeX(){
        double totalX=0;
        Forces sumX=new Forces();
        for (Node n:megaPane.getChildren()){ // checks every children of the pane
            if (n instanceof VectorArrow){ // makes sure we're only dealing with VectorArrows
                VectorArrow v=(VectorArrow)n;
                if (v.getNet()==false&&v.getIgnore()==false) { // if the vector is not the net vector and not being ignored
                double mag=v.getMagnitude();
                double ang=v.getRotation()+180; // since vectors must point TOWARDS the center, not outward from it.
                
                totalX+=sumX.ForceX(mag, ang);}
            }
        }
        return totalX;
    }
    
    public double addMagnitudeY(){ // same thing as x but with Y components  
       double totalY=0;
        Forces sumY=new Forces();
        for (Node n:megaPane.getChildren()){
            if (n instanceof VectorArrow){
                VectorArrow v=(VectorArrow)n;
                if (v.getNet()==false&&v.getIgnore()==false) {
                double mag=v.getMagnitude();
                double ang=v.getRotation()+180;
                totalY+=sumY.ForceY(mag, ang);}
            }
        }
        return totalY;
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
                }
                
                selectingVector=true; // so that the program knows a vector is selected right now
                toggleSelectedVector();
                
                updateMagnitude();
                updateDirection();
                updateNetForce();
                updateNetDir();
                updateNetVector();
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
            updateNetForce();
            updateNetDir();
            updateNetVector();
        };
    }
    
    
    private static void Alert(String title, String header, String content){
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle(title);
       alert.setHeaderText(header);
       alert.setContentText(content);
       alert.show();
    }
    
    
/**
 * loads the main menu scene.
 */
    private void loadPrimaryScene(Event e) {
        MainApp.switchScene(MainApp.priStage, MainApp.MAINAPP_SCENE);
    }
}
