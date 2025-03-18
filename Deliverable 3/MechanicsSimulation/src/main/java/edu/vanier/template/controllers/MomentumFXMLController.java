/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.controllers;

import edu.vanier.physics.Momentum;
import edu.vanier.template.ui.MainApp;
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
    
    @FXML
    Label lbOne, lbTwo, lbThree, lbFour, lbFive;//the labels on top of the balls so it's easier to tell which ball's number what
    
    @FXML
    Label lbM1, lbM2, lbM3, lbM4, lbM5, lbV1, lbV2, lbV3, lbV4, lbV5;
    
    @FXML
    Label lbMomCalc1, lbMomCalc2, lbMomCalc3, lbImCalc1, lbImCalc2, lbImCalc3, lbEkCalc1, lbEkCalc2, lbEkCalc3;//the labels displaying the calculations
    
    @FXML
    Slider slM1, slM2, slM3, slM4, slM5, slV1, slV2, slV3, slV4, slV5;//sliders for the mass & velocities of balls        
            
    Stage stg;
    
    SpinnerValueFactory <Integer> balls;
    
    SpinnerValueFactory <Double> speed;

    String PlayPause = "paused";//a flag for the play/pause button
    
    Circle c1, c2, c3, c4, c5;//circle objects
    
    Momentum b1, b2, b3, b4, b5;//ball objects that correspond to every circle object
    
    double orgSceneX, orgSceneY;//original mouse click positions
    double ogBallX, ogBallY;//original position of the dragged ball
    
    int data = 1;//determines which ball's data to display
    
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
    }
    
    //all relevant variable setups to be initialized
    private void initSetup(){
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
        
        lbOne.toFront();
        lbTwo.setVisible(false);
        lbTwo.toFront();
        lbThree.setVisible(false);
        lbThree.toFront();
        lbFour.setVisible(false);
        lbFour.toFront();
        lbFive.setVisible(false);
        lbFive.toFront();
        
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
        
        lbX1.setText(String.valueOf(Math.round(b1.getPositionX()*10.0)/10.0));//displaying the starting positions of the balls
        lbY1.setText(String.valueOf(Math.round(b1.getPositionY()*10.0)/10.0));
        lbX2.setText(String.valueOf(Math.round(b2.getPositionX()*10.0)/10.0));
        lbY2.setText(String.valueOf(Math.round(b2.getPositionY()*10.0)/10.0));
        lbX3.setText(String.valueOf(Math.round(b3.getPositionX()*10.0)/10.0));
        lbY3.setText(String.valueOf(Math.round(b3.getPositionY()*10.0)/10.0));
        lbX4.setText(String.valueOf(Math.round(b4.getPositionX()*10.0)/10.0));
        lbY4.setText(String.valueOf(Math.round(b4.getPositionY()*10.0)/10.0));
        lbX5.setText(String.valueOf(Math.round(b5.getPositionX()*10.0)/10.0));
        lbY5.setText(String.valueOf(Math.round(b5.getPositionY()*10.0)/10.0));
        
        lbMomCalc2.setVisible(false);
        lbMomCalc3.setVisible(false);
        lbImCalc2.setVisible(false);
        lbImCalc3.setVisible(false);
        lbEkCalc2.setVisible(false);
        lbEkCalc3.setVisible(false);
        
        setBallLabels();//set the ball labels' positions to be right ontop of the balls
    }
    
    
    
    private void generateBalls(){
        b1 = new Momentum(30,60,2,3);//x, y, mass, velocity
        b2 = new Momentum(400,400,2,3);
        b3 = new Momentum(130,130,2,3);
        b4 = new Momentum(180,180,2,3);
        b5 = new Momentum(230,230,2,3);
        
        c1 = new Circle(b1.getPositionX(),b1.getPositionY(),20, Color.RED);
        c1.setStroke(Color.BLACK);
        c2 = new Circle(b2.getPositionX(),b2.getPositionY(),20, Color.GREEN);
        c2.setStroke(Color.BLACK);
        c3 = new Circle(b3.getPositionX(),b3.getPositionY(),20, Color.BLUE);
        c3.setStroke(Color.BLACK);
        c4 = new Circle(b4.getPositionX(),b4.getPositionY(),20, Color.BROWN);
        c4.setStroke(Color.BLACK);
        c5 = new Circle(b5.getPositionX(),b5.getPositionY(),20, Color.PURPLE);
        c5.setStroke(Color.BLACK);

        c1.setOnMouseDragged(circleOnMousePressedEventHandler(c1));
        c1.setOnMouseDragged(circleOnMouseDraggedEventHandler(c1));
        
        c2.setOnMouseDragged(circleOnMousePressedEventHandler(c2));
        c2.setOnMouseDragged(circleOnMouseDraggedEventHandler(c2));
        
        c3.setOnMouseDragged(circleOnMousePressedEventHandler(c3));
        c3.setOnMouseDragged(circleOnMouseDraggedEventHandler(c3));
        
        c4.setOnMouseDragged(circleOnMousePressedEventHandler(c4));
        c4.setOnMouseDragged(circleOnMouseDraggedEventHandler(c4)); 
        
        c5.setOnMouseDragged(circleOnMousePressedEventHandler(c5));
        c5.setOnMouseDragged(circleOnMouseDraggedEventHandler(c5));
    
        momRoot.getChildren().add(c1);
        momRoot.getChildren().add(c2);
        momRoot.getChildren().add(c3);
        momRoot.getChildren().add(c4);
        momRoot.getChildren().add(c5);

        c2.setVisible(false);
        c3.setVisible(false);
        c4.setVisible(false);
        c5.setVisible(false);

    }

    
    private EventHandler<MouseEvent> circleOnMousePressedEventHandler(Circle ball){
    return new EventHandler<MouseEvent>(){
    @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();//mouse click x position
            orgSceneY = t.getSceneY();//mouse click y position
            ogBallX = ball.getCenterX();//records ball's starting position
            ogBallY = ball.getCenterY();
        }
    };
    }
    
    private EventHandler<MouseEvent> circleOnMouseDraggedEventHandler(Circle ball){    
        return new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            double offsetX = t.getSceneX() - orgSceneX;//how much the mouse dragged away the ball
            double offsetY = t.getSceneY() - orgSceneY;

            ball.setCenterX(ogBallX + offsetX - 20);//original position + mouse dragged diistance + correction factor
            ball.setCenterY(ogBallY + offsetY - 40);
            
            switch(data){
                case 1: showValues(1);break;
                case 2: showValues(2);break;
                case 3: showValues(3);break;
                case 4: showValues(4);break;
                case 5: showValues(5);break;
                default: System.out.println("Something's wrong"); break;
            }
            
            setBallLabels();         
        }
    };
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
        
        c2.setVisible(f2);
        c3.setVisible(f3);
        c4.setVisible(f4);
        c5.setVisible(f5);
        
        lbTwo.setVisible(f2);
        lbThree.setVisible(f3);
        lbFour.setVisible(f4);
        lbFive.setVisible(f5);
        
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
          if(PlayPause.equals("paused")){
             ivPlayPause.setImage(new Image("/Images/pause.png"));
             PlayPause = "playing";//now the animation is playing
          }
          else if(PlayPause.equals("playing")){
             ivPlayPause.setImage(new Image("/Images/play.png"));
             PlayPause = "paused";//now the animation is paused
          }
       });
       
       btnB1.setOnAction((event)->{data = 1; showValues(1);});//ball 1's data will now be displayed
       btnB2.setOnAction((event)->{data = 2; showValues(2);});//ball 2's data will now be displayed
       btnB3.setOnAction((event)->{data = 3; showValues(3);});//ball 3's data will now be displayed
       btnB4.setOnAction((event)->{data = 4; showValues(4);});//ball 4's data will now be displayed
       btnB5.setOnAction((event)->{data = 5; showValues(5);});//ball 5's data will now be displayed

    }
    
    private void setBallLabels(){
            lbOne.setLayoutX(c1.getCenterX()-5);
            lbOne.setLayoutY(c1.getCenterY()-13);
            lbTwo.setLayoutX(c2.getCenterX()-5);
            lbTwo.setLayoutY(c2.getCenterX()-13);
            lbThree.setLayoutX(c3.getCenterX()-5);
            lbThree.setLayoutY(c3.getCenterX()-13);
            lbFour.setLayoutX(c4.getCenterX()-5);
            lbFour.setLayoutY(c4.getCenterY()-13);
            lbFive.setLayoutX(c5.getCenterX()-5);
            lbFive.setLayoutY(c5.getCenterY()-13);
    }
    
    private void setUpSliders() {
       slM1.valueProperty().addListener((event)->{          
        lbM1.setText(String.valueOf(Math.round(slM1.getValue()*10.0)/10.0));
        b1.setMass(slM1.getValue());
       });
       
       slM2.valueProperty().addListener((event)->{          
        lbM2.setText(String.valueOf(Math.round(slM2.getValue()*10.0)/10.0));
        b2.setMass(slM2.getValue());
       });
       
       slM3.valueProperty().addListener((event)->{          
        lbM3.setText(String.valueOf(Math.round(slM3.getValue()*10.0)/10.0));
        b3.setMass(slM3.getValue());
       });
       
       slM4.valueProperty().addListener((event)->{          
        lbM4.setText(String.valueOf(Math.round(slM4.getValue()*10.0)/10.0));
        b4.setMass(slM4.getValue());
       });
       
       slM5.valueProperty().addListener((event)->{          
        lbM5.setText(String.valueOf(Math.round(slM5.getValue()*10.0)/10.0));
        b5.setMass(slM5.getValue());
       });
       
       slV1.valueProperty().addListener((event)->{          
        lbV1.setText(String.valueOf(Math.round(slV1.getValue()*10.0)/10.0));
        b1.setVelocity(slV1.getValue());
       });
       
       slV2.valueProperty().addListener((event)->{          
        lbV2.setText(String.valueOf(Math.round(slV2.getValue()*10.0)/10.0));
        b2.setVelocity(slV2.getValue());
       });
       
       slV3.valueProperty().addListener((event)->{          
        lbV3.setText(String.valueOf(Math.round(slV3.getValue()*10.0)/10.0));
        b3.setVelocity(slV3.getValue());
       });
       
       slV4.valueProperty().addListener((event)->{          
        lbV4.setText(String.valueOf(Math.round(slV4.getValue()*10.0)/10.0));
        b4.setVelocity(slV4.getValue());
       });
       
       slV5.valueProperty().addListener((event)->{          
        lbV5.setText(String.valueOf(Math.round(slV5.getValue()*10.0)/10.0));
        b5.setVelocity(slV5.getValue());
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
        
        lbMomCalc2.setText("P = (" + Math.round(b.getMass()*10.0)/10.0 + ")(" + Math.round(b.getVelocity()*10.0)/10.0 + ")");
        lbMomCalc3.setText("P = " + Math.round(b.calcMomentum()*10.0)/10.0 + " kgm/s");
        
        //regardless of which ball is selected all their position & momentum will constantly be shown
        lbX1.setText(String.valueOf(Math.round(c1.getCenterX()*10.0)/10.0));
        lbY1.setText(String.valueOf(Math.round(c1.getCenterY()*10.0)/10.0));
        lbX2.setText(String.valueOf(Math.round(c2.getCenterX()*10.0)/10.0));
        lbY2.setText(String.valueOf(Math.round(c2.getCenterY()*10.0)/10.0));
        lbX3.setText(String.valueOf(Math.round(c3.getCenterX()*10.0)/10.0));
        lbY3.setText(String.valueOf(Math.round(c3.getCenterY()*10.0)/10.0));
        lbX4.setText(String.valueOf(Math.round(c4.getCenterX()*10.0)/10.0));
        lbY4.setText(String.valueOf(Math.round(c4.getCenterY()*10.0)/10.0));
        lbX5.setText(String.valueOf(Math.round(c5.getCenterX()*10.0)/10.0));
        lbY5.setText(String.valueOf(Math.round(c5.getCenterY()*10.0)/10.0));

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
