/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.controllers;

import edu.vanier.physics.Momentum;
import edu.vanier.template.ui.MainApp;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author theli
 */
public class MomentumFXMLController {

    @FXML
    MenuBar menuBar;
    
    @FXML
    Menu menuFile, menuEdit, menuHelp, menuBack;
    
    @FXML
    MenuItem mitBack, mitClose;    
        
    @FXML
    MenuItem mitAbout, mitTips;
    
    @FXML
    Spinner <Integer> spBalls;
    
    @FXML
    Spinner <Double> spPlayBack;
            
    @FXML
    Button btnPlayPause, btnReset;    
            
    @FXML
    Button btnB1, btnB2, btnB3, btnB4, btnB5;
    
    @FXML
    ImageView ivPlayPause;        
            
    @FXML
    Pane momRoot;//the momentum root pane where the animation is taking place
            
    @FXML
    Label lbX1, lbX2, lbX3, lbX4, lbX5, lbY1, lbY2, lbY3, lbY4, lbY5;//to display the positions of all balls     
    
    @FXML
    Label lb1, lb2, lb3, lb4, lb5, lbP1, lbP2, lbP3, lbP4, lbP5, lbPx1, lbPx2, lbPx3, lbPx4, lbPx5, lbPy1, lbPy2, lbPy3, lbPy4, lbPy5;//to display the momentum of all balls   
    
   // @FXML
   // Label lbOne, lbTwo, lbThree, lbFour, lbFive;//the labels on top of the balls so it's easier to tell which ball's number what
    
    @FXML
    Label lbM1, lbM2, lbM3, lbM4, lbM5, lbV1, lbV2, lbV3, lbV4, lbV5;
    
    @FXML
    Label lbMomCalc1, lbMomCalc2, lbMomCalc3, lbImCalc1, lbImCalc2, lbImCalc3, lbEkCalc1, lbEkCalc2, lbEkCalc3;//the labels displaying the calculations
    
    @FXML
    Slider slM1, slM2, slM3, slM4, slM5, slV1, slV2, slV3, slV4, slV5;//sliders for the mass & velocities of balls        
            
    @FXML
    StackPane spC1, spC2, spC3, spC4, spC5;       
            
    Stage stg;
    
    SpinnerValueFactory <Integer> balls;
    
    SpinnerValueFactory <Double> speed;

    String PlayPause = "paused";//a flag for the play/pause button
    
    @FXML
    Circle c1, c2, c3, c4, c5;//circle objects
    
    Momentum b1, b2, b3, b4, b5;//ball objects that correspond to every circle object
    
    double orgSceneX, orgSceneY;//original mouse click positions
    double ogBallX, ogBallY;//original position of the dragged ball
    
    int data = 1;//determines which ball's data to display
    
    ArrayList<Circle> ballList = new ArrayList <Circle>();
    ArrayList<StackPane> spList = new ArrayList <StackPane>();
    ArrayList<Momentum> momList = new ArrayList <Momentum>();

    AnimationTimer timer;
    
    public void setStage(Stage stage){
    this.stg = stage;
    }
    
    @FXML
    public void initialize() {
        initMenu();
        setUpSpinners();
        setUpButtons();
        setUpSliders();
        generateBalls();
        initSetup();
        animation();
    }
    
    //all relevant variable setups to be initialized
    private void initSetup(){
        ballList.add(c1);
        ballList.add(c2);
        ballList.add(c3);
        ballList.add(c4);
        ballList.add(c5);
        
        spList.add(spC1);
        spList.add(spC2);
        spList.add(spC3);
        spList.add(spC4);
        spList.add(spC5);
        
        momList.add(b1);
        momList.add(b2);
        momList.add(b3);
        momList.add(b4);
        momList.add(b5);
        
        btnB2.setDisable(true);//data buttons for all the balls
        btnB3.setDisable(true);
        btnB4.setDisable(true);
        btnB5.setDisable(true);
        
        slM2.setDisable(true);//mass sliders
        slM3.setDisable(true);
        slM4.setDisable(true);
        slM5.setDisable(true);
        
        slV2.setDisable(true);//velocity sliders
        slV3.setDisable(true);
        slV4.setDisable(true);
        slV5.setDisable(true);
        
        lb2.setVisible(false);//label of the balls
        lb3.setVisible(false);
        lb4.setVisible(false);
        lb5.setVisible(false);
        
        lbX2.setVisible(false);//X position displays
        lbX3.setVisible(false);
        lbX4.setVisible(false);
        lbX5.setVisible(false);
        
        lbY2.setVisible(false);//Y position displays
        lbY3.setVisible(false);
        lbY4.setVisible(false);
        lbY5.setVisible(false);
        
        lbP2.setVisible(false);//momentum displays
        lbP3.setVisible(false);
        lbP4.setVisible(false);
        lbP5.setVisible(false);
        
        lbPx2.setVisible(false);//X momentum displays
        lbPx3.setVisible(false);
        lbPx4.setVisible(false);
        lbPx5.setVisible(false);
        
        lbPy2.setVisible(false);//Y momentum displays
        lbPy3.setVisible(false);
        lbPy4.setVisible(false);
        lbPy5.setVisible(false);

        lbM1.setText(String.valueOf(slM1.getValue()));//displaying the values of the mass sliders
        lbM2.setText(String.valueOf(slM2.getValue()));
        lbM3.setText(String.valueOf(slM3.getValue()));
        lbM4.setText(String.valueOf(slM4.getValue()));
        lbM5.setText(String.valueOf(slM5.getValue()));
        
        lbV1.setText(String.valueOf(slV1.getValue()));//displaying the values of the velocity sliders
        lbV2.setText(String.valueOf(slV2.getValue()));
        lbV3.setText(String.valueOf(slV3.getValue()));
        lbV4.setText(String.valueOf(slV4.getValue()));
        lbV5.setText(String.valueOf(slV5.getValue()));
        
        lbX1.setText(String.valueOf(Math.round((spC1.getLayoutX()+22)*10.0)/10.0));//22 is correction factor for the coordinates of the center of the ball
        lbY1.setText(String.valueOf(Math.round((spC1.getLayoutY()+22)*10.0)/10.0));
        lbX2.setText(String.valueOf(Math.round((spC2.getLayoutX()+22)*10.0)/10.0));
        lbY2.setText(String.valueOf(Math.round((spC2.getLayoutY()+22)*10.0)/10.0));
        lbX3.setText(String.valueOf(Math.round((spC3.getLayoutX()+22)*10.0)/10.0));
        lbY3.setText(String.valueOf(Math.round((spC3.getLayoutY()+22)*10.0)/10.0));
        lbX4.setText(String.valueOf(Math.round((spC4.getLayoutX()+22)*10.0)/10.0));
        lbY4.setText(String.valueOf(Math.round((spC4.getLayoutY()+22)*10.0)/10.0));
        lbX5.setText(String.valueOf(Math.round((spC5.getLayoutX()+22)*10.0)/10.0));
        lbY5.setText(String.valueOf(Math.round((spC5.getLayoutY()+22)*10.0)/10.0));
        
        lbP1.setText(String.valueOf(Math.round(((double) b1.calcMomentum())*10.0)/10.0));
        lbP2.setText(String.valueOf(Math.round(((double) b2.calcMomentum())*10.0)/10.0));
        lbP3.setText(String.valueOf(Math.round(((double) b3.calcMomentum())*10.0)/10.0));
        lbP4.setText(String.valueOf(Math.round(((double) b4.calcMomentum())*10.0)/10.0));
        lbP5.setText(String.valueOf(Math.round(((double) b5.calcMomentum())*10.0)/10.0));
        
        lbMomCalc2.setVisible(false);
        lbMomCalc3.setVisible(false);
        lbImCalc2.setVisible(false);
        lbImCalc3.setVisible(false);
        lbEkCalc2.setVisible(false);
        lbEkCalc3.setVisible(false);
        
       // setBallLabels();//set the ball labels' positions to be right ontop of the balls
    }
    
    
    
    private void generateBalls(){
        b1 = new Momentum(30,60,5, slV1.getValue()/(Math.sqrt(2)), slV1.getValue()/(Math.sqrt(2)));//x, y, mass, velocityX, velocityY
        b2 = new Momentum(100,60,5, slV2.getValue()/(Math.sqrt(2)), slV2.getValue()/(Math.sqrt(2)));
        b3 = new Momentum(200,50,5, slV3.getValue()/(Math.sqrt(2)), slV3.getValue()/(Math.sqrt(2)));
        b4 = new Momentum(50,100,5, slV4.getValue()/(Math.sqrt(2)), slV4.getValue()/(Math.sqrt(2)));
        b5 = new Momentum(300,80,5, slV5.getValue()/(Math.sqrt(2)), slV5.getValue()/(Math.sqrt(2)));
        
        spC2.setLayoutX(b2.getPositionX());
        spC2.setLayoutY(b2.getPositionY());
        spC3.setLayoutX(b3.getPositionX());
        spC3.setLayoutY(b3.getPositionY());
        spC4.setLayoutX(b4.getPositionX());
        spC4.setLayoutY(b4.getPositionY());
        spC5.setLayoutX(b5.getPositionX());
        spC5.setLayoutY(b5.getPositionY());
        //c1.setCenterY(b1.getPositionY());
        /*
        c2.setCenterX(b2.getPositionX());
        c2.setCenterY(b2.getPositionY());
        c3.setCenterX(b3.getPositionX());
        c3.setCenterY(b3.getPositionY());
        c4.setCenterX(b4.getPositionX());
        c4.setCenterY(b4.getPositionY());
        c5.setCenterX(b5.getPositionX());
        c5.setCenterY(b5.getPositionY());*/
        
        spC1.setOnMouseDragged(circleOnMousePressedEventHandler(spC1));
        spC1.setOnMouseDragged(circleOnMouseDraggedEventHandler(spC1));
        
        spC2.setOnMouseDragged(circleOnMousePressedEventHandler(spC2));
        spC2.setOnMouseDragged(circleOnMouseDraggedEventHandler(spC2));
        
        spC3.setOnMouseDragged(circleOnMousePressedEventHandler(spC3));
        spC3.setOnMouseDragged(circleOnMouseDraggedEventHandler(spC3));
        
        spC4.setOnMouseDragged(circleOnMousePressedEventHandler(spC4));
        spC4.setOnMouseDragged(circleOnMouseDraggedEventHandler(spC4)); 
        
        spC5.setOnMouseDragged(circleOnMousePressedEventHandler(spC5));
        spC5.setOnMouseDragged(circleOnMouseDraggedEventHandler(spC5));
    
      //  momRoot.getChildren().add(c1);
       // momRoot.getChildren().add(c2);
       // momRoot.getChildren().add(c3);
      //  momRoot.getChildren().add(c4);
       // momRoot.getChildren().add(c5);

        spC2.setVisible(false);
        spC3.setVisible(false);
        spC4.setVisible(false);
        spC5.setVisible(false);

    }

    
    private EventHandler<MouseEvent> circleOnMousePressedEventHandler(StackPane sp){
    return new EventHandler<MouseEvent>(){
    @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();//mouse click x position
            orgSceneY = t.getSceneY();//mouse click y position
            ogBallX = sp.getLayoutX();//records ball's starting position
            ogBallY = sp.getLayoutY();//top left corner
        }
    };
    }
    
    private EventHandler<MouseEvent> circleOnMouseDraggedEventHandler(StackPane sp){    
        return new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            double offsetX = t.getSceneX() - orgSceneX;//how much the mouse dragged away the ball
            double offsetY = t.getSceneY() - orgSceneY;
            
            double moveX = ogBallX + offsetX - 42;//42 & 57 are the correction factor for when mouse moves a ball and it shifts
            double moveY = ogBallY + offsetY - 57;
            
            if(moveX<0){moveX = 0;}//if ball will move below 0, it will be capped at 0
            else if(moveX>750){moveX = 750;}//if ball will move over 750, it will be capped at 750
            
            if(moveY<0){moveY = 0;}//same logic here as above
            else if(moveY>502){moveY = 502;}
            
            sp.setLayoutX(moveX);
            sp.setLayoutY(moveY);
            
            switch(data){
                case 1: showValues(1);break;
                case 2: showValues(2);break;
                case 3: showValues(3);break;
                case 4: showValues(4);break;
                case 5: showValues(5);break;
                default: System.out.println("Something's wrong"); break;
            }
            
           // setBallLabels();         
        }
    };
    }

    private void animation(){
        
         timer = new AnimationTimer(){
            @Override
            public void handle(long now) {
        
                //iterating all balls to check for collision with each other
                for(int i=0; i<ballList.size(); i++){
                  for(int j=i+1; j<ballList.size(); j++){
                       checkCollision(ballList.get(i),ballList.get(j));
                  }
                }
                
                //iterating through all balls to check their boundaries with the pane
                for(int i=0; i<ballList.size(); i++){
                       checkBounds(ballList.get(i), momList.get(i));
                  }
                
                //iterating through all balls to set their new movements corresponding to changing velocities
                for(int i=0; i<momList.size(); i++){
                       movingBalls(momList.get(i), spList.get(i));
                }                
                
            }
        
        };
    }
    
    private void movingBalls(Momentum ball, StackPane sp){
        sp.setTranslateX(sp.getTranslateX() + ball.getVelocityX());//moving the balls
        sp.setTranslateY(sp.getTranslateY() + ball.getVelocityY());
    }
    
    private void checkBounds(Circle c, Momentum b){
        
                //if x and y positions of balls are out of bounds, their directions are reversed
                if(c.localToScene(c.getBoundsInParent()).getCenterX() >= 815 || c.localToScene(c.getBoundsInParent()).getCenterX() <= 62){
                   b.setVelocityX(b.getVelocityX()*-1);
                }

                if(c.localToScene(c.getBoundsInParent()).getCenterY() >= 587 || c.localToScene(c.getBoundsInParent()).getCenterY() <= 79){
                   b.setVelocityY(b.getVelocityY()*-1);
                }
    }
    
    //when they collide, they should bounce off each other
    private void checkCollision(Circle ball1, Circle ball2){
         if(ball1.localToScene(ball1.getBoundsInParent()).intersects(ball2.localToScene(ball2.getBoundsInParent()))){
             //System.out.println("das");
             
         }
    }
    
    private void initMenu(){//initializes the buttons in the menu bar
        //File
        mitBack.setOnAction(this::loadPrimaryScene);
        mitClose.setOnAction((event)->{
        stg.close();
        });
        //Edit
        //Help
        mitAbout.setOnAction((event)->{
        Alert("Momentum simulation","s","s");
        });
        mitTips.setOnAction((event)->{
        Alert("Some tips","Here's my tips","That's right");
        });
    }

    private void setUpSpinners(){
       balls = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1, 1);//min 1 ball, max 5 balls, adds 1 at a time
       spBalls.setValueFactory(balls);
       spBalls.getEditor().setStyle("-fx-font-size: 15px;");

       spBalls.valueProperty().addListener((event)->{
           switch (balls.getValue()) {
               case 1: 
                   setUIsVisible(false, false, false, false);
                   showValues(1);
                   break;
               case 2:
                   setUIsVisible(true, false, false, false);
                   break;
               case 3:
                   setUIsVisible(true, true, false, false);
                   break;
               case 4:
                   setUIsVisible(true, true, true, false);
                   break;
               case 5:
                   setUIsVisible(true, true, true, true);
                   break;
               default:
                   System.out.println("Error");
                   break;
           }
       });
       
       spPlayBack.getEditor().setStyle("-fx-font-size: 15px;");               
    }
    
    //to disable/enable UIs with flags based on how many balls there are
    //c1 will always be visible
    private void setUIsVisible(boolean f2, boolean f3, boolean f4, boolean f5){
        
        spC2.setVisible(f2);
        spC3.setVisible(f3);
        spC4.setVisible(f4);
        spC5.setVisible(f5);
        
        //lbTwo.setVisible(f2);
        //lbThree.setVisible(f3);
        //lbFour.setVisible(f4);
        //lbFive.setVisible(f5);
        
        lb2.setVisible(f2);
        lb3.setVisible(f3);
        lb4.setVisible(f4);
        lb5.setVisible(f5);
        
        lbX2.setVisible(f2);
        lbX3.setVisible(f3);
        lbX4.setVisible(f4);
        lbX5.setVisible(f5);
        
        lbY2.setVisible(f2);
        lbY3.setVisible(f3);
        lbY4.setVisible(f4);
        lbY5.setVisible(f5);
        
        lbP2.setVisible(f2);
        lbP3.setVisible(f3);
        lbP4.setVisible(f4);
        lbP5.setVisible(f5);
        
        lbPx2.setVisible(f2);
        lbPx3.setVisible(f3);
        lbPx4.setVisible(f4);
        lbPx5.setVisible(f5);
        
        lbPy2.setVisible(f2);
        lbPy3.setVisible(f3);
        lbPy4.setVisible(f4);
        lbPy5.setVisible(f5);
        
        //the reverse of the inputted booleans for functions with effects reversive of "setVisible"
        boolean rev2, rev3, rev4, rev5;
        
        if(f2==false){rev2 = true;}
        else{rev2 = false;}
        
        if(f3==false){rev3 = true;}
        else{rev3 = false;}
        
        if(f4==false){rev4 = true;}
        else{rev4 = false;}
        
        if(f5==false){rev5 = true;}
        else{rev5 = false;}
        
        btnB2.setDisable(rev2);
        btnB3.setDisable(rev3);
        btnB4.setDisable(rev4);
        btnB5.setDisable(rev5);
        
        slM2.setDisable(rev2);
        slM3.setDisable(rev3);
        slM4.setDisable(rev4);
        slM5.setDisable(rev5);
        
        slV2.setDisable(rev2);
        slV3.setDisable(rev3);
        slV4.setDisable(rev4);
        slV5.setDisable(rev5);
    }
    
    private void setUpButtons() {
       
       btnPlayPause.setOnAction((event)->{
          if(PlayPause.equals("paused")){//when the animation is currently paused
             ivPlayPause.setImage(new Image("/Images/pause.png"));
             PlayPause = "playing";//now the animation is playing
             
             timer.start();
          }
          else if(PlayPause.equals("playing")){//when the animation is currently playing
             ivPlayPause.setImage(new Image("/Images/play.png"));
             PlayPause = "paused";//now the animation is paused
             
             timer.stop();
          }
       });
       
       btnB1.setOnAction((event)->{data = 1; showValues(1);});//ball 1's data will now be displayed
       btnB2.setOnAction((event)->{data = 2; showValues(2);});//ball 2's data will now be displayed
       btnB3.setOnAction((event)->{data = 3; showValues(3);});//ball 3's data will now be displayed
       btnB4.setOnAction((event)->{data = 4; showValues(4);});//ball 4's data will now be displayed
       btnB5.setOnAction((event)->{data = 5; showValues(5);});//ball 5's data will now be displayed

    }
    
    private void setUpSliders() {
       slM1.valueProperty().addListener((event)->{          
        lbM1.setText(String.valueOf(Math.round(slM1.getValue()*10.0)/10.0));
        b1.setMass(slM1.getValue());//changing ball's mass
        if(data==1){
        showValues(1);
        }
       });
       
       slM2.valueProperty().addListener((event)->{          
        lbM2.setText(String.valueOf(Math.round(slM2.getValue()*10.0)/10.0));
        b2.setMass(slM2.getValue());
        if(data==2){
        showValues(2);
        }
       });
       
       slM3.valueProperty().addListener((event)->{          
        lbM3.setText(String.valueOf(Math.round(slM3.getValue()*10.0)/10.0));
        b3.setMass(slM3.getValue());
        if(data==3){
        showValues(3);
        }
       });
       
       slM4.valueProperty().addListener((event)->{          
        lbM4.setText(String.valueOf(Math.round(slM4.getValue()*10.0)/10.0));
        b4.setMass(slM4.getValue());
        if(data==4){
        showValues(4);
        }
       });
       
       slM5.valueProperty().addListener((event)->{          
        lbM5.setText(String.valueOf(Math.round(slM5.getValue()*10.0)/10.0));
        b5.setMass(slM5.getValue());
        if(data==5){
        showValues(5);
        }
       });
       
       slV1.valueProperty().addListener((event)->{          
        lbV1.setText(String.valueOf(Math.round(slV1.getValue()*10.0)/10.0));
        b1.setVelocity(slV1.getValue());//setting the overall velocity
        b1.setVelocityX(slV1.getValue()*Math.cos(b1.getAngle()));//setting the x & y velocities
        b1.setVelocityY(slV1.getValue()*Math.sin(b1.getAngle()));
        showValues(1);
       });
       
       slV2.valueProperty().addListener((event)->{          
        lbV2.setText(String.valueOf(Math.round(slV2.getValue()*10.0)/10.0));
        b2.setVelocity(slV2.getValue());
        b2.setVelocityX(slV2.getValue()*Math.cos(b2.getAngle()));
        b2.setVelocityY(slV2.getValue()*Math.sin(b2.getAngle()));
        showValues(2);
       });
       
       slV3.valueProperty().addListener((event)->{          
        lbV3.setText(String.valueOf(Math.round(slV3.getValue()*10.0)/10.0));
        b3.setVelocity(slV3.getValue());
        b3.setVelocityX(slV3.getValue()*Math.cos(b3.getAngle()));
        b3.setVelocityY(slV3.getValue()*Math.sin(b3.getAngle()));
        showValues(3);
       });
       
       slV4.valueProperty().addListener((event)->{          
        lbV4.setText(String.valueOf(Math.round(slV4.getValue()*10.0)/10.0));
        b4.setVelocity(slV4.getValue());
        b4.setVelocityX(slV4.getValue()*Math.cos(b4.getAngle()));
        b4.setVelocityY(slV4.getValue()*Math.sin(b4.getAngle()));
        showValues(4);
       });
       
       slV5.valueProperty().addListener((event)->{          
        lbV5.setText(String.valueOf(Math.round(slV5.getValue()*10.0)/10.0));
        b5.setVelocity(slV5.getValue());
        b5.setVelocityX(slV5.getValue()*Math.cos(b5.getAngle()));
        b5.setVelocityY(slV5.getValue()*Math.sin(b5.getAngle()));
        showValues(5);
       });
    }
    
    //int i only determines which ball's formulas are shown. All the balls' positions & momentums are shown at all times
    private void showValues(int i){
       
        Circle c = c1;
        Momentum b = b1;
        
        switch(i){
            case 1: c = c1; b = b1; break;     
            case 2: c = c2; b = b2; break;
            case 3: c = c3; b = b3; break;
            case 4: c = c4; b = b4; break;
            case 5: c = c5; b = b5; break;
            default: System.out.println("Error");
        }
        
        lbMomCalc2.setVisible(true);
        lbMomCalc3.setVisible(true);
        
        lbImCalc2.setVisible(true);
        lbImCalc3.setVisible(true);
        
        lbEkCalc2.setVisible(true);
        lbEkCalc3.setVisible(true);
        
        lbMomCalc2.setText("P = (" + Math.round(b.getMass()*10.0)/10.0 + ")(" + Math.round(b.getVelocity()*10.0)/10.0 + ")");
        lbMomCalc3.setText("P = " + Math.round(b.calcMomentum()*10.0)/10.0 + " kgm/s");
    
        lbEkCalc2.setText("E = ½(" + Math.round(b.getMass()*10.0)/10.0 + ")(" + Math.round(b.getVelocity()*10.0)/10.0 + ")²");
        lbEkCalc3.setText("E = " + Math.round(b.calcEk()*10.0)/10.0 + " J");
        
        //regardless of which ball is selected all their position & momentum will constantly be shown
        lbX1.setText(String.valueOf(Math.round((spC1.getLayoutX()+22)*10.0)/10.0));//22 is correction factor for the coordinates of the center of the ball
        lbY1.setText(String.valueOf(Math.round((spC1.getLayoutY()+22)*10.0)/10.0));
        lbX2.setText(String.valueOf(Math.round((spC2.getLayoutX()+22)*10.0)/10.0));
        lbY2.setText(String.valueOf(Math.round((spC2.getLayoutY()+22)*10.0)/10.0));
        lbX3.setText(String.valueOf(Math.round((spC3.getLayoutX()+22)*10.0)/10.0));
        lbY3.setText(String.valueOf(Math.round((spC3.getLayoutY()+22)*10.0)/10.0));
        lbX4.setText(String.valueOf(Math.round((spC4.getLayoutX()+22)*10.0)/10.0));
        lbY4.setText(String.valueOf(Math.round((spC4.getLayoutY()+22)*10.0)/10.0));
        lbX5.setText(String.valueOf(Math.round((spC5.getLayoutX()+22)*10.0)/10.0));
        lbY5.setText(String.valueOf(Math.round((spC5.getLayoutY()+22)*10.0)/10.0));

        lbP1.setText(String.valueOf(Math.round(((double) b1.calcMomentum())*10.0)/10.0));
        lbP2.setText(String.valueOf(Math.round(((double) b2.calcMomentum())*10.0)/10.0));
        lbP3.setText(String.valueOf(Math.round(((double) b3.calcMomentum())*10.0)/10.0));
        lbP4.setText(String.valueOf(Math.round(((double) b4.calcMomentum())*10.0)/10.0));
        lbP5.setText(String.valueOf(Math.round(((double) b5.calcMomentum())*10.0)/10.0));
    }
    
    private static void Alert(String title, String header, String content){
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle(title);
       alert.setHeaderText(header);
       alert.setContentText(content);
       alert.show();
    }
    
    private void loadPrimaryScene(Event e) {
        MainApp.switchScene(MainApp.priStage, MainApp.MAINAPP_SCENE);
    }

    

}
