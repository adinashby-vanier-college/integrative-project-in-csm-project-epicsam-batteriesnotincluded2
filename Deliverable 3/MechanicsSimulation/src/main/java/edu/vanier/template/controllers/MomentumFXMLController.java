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
    Slider slM1;//sliders for the mass of balls 
            
    @FXML
    Label lbOne, lbTwo, lbThree, lbFour, lbFive;//the labels on top of the balls so it's easier to tell which ball's number what       
            
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
        btnB2.setDisable(true);
        btnB3.setDisable(true);
        btnB4.setDisable(true);
        btnB5.setDisable(true);
        
        lbOne.toFront();
        lbTwo.setVisible(false);
        lbTwo.toFront();
        lbThree.setVisible(false);
        lbThree.toFront();
        lbFour.setVisible(false);
        lbFour.toFront();
        lbFive.setVisible(false);
        lbFive.toFront();
        
        setBallLabels();//set the ball labels' positions to be right ontop of the balls
    }
    
    
    
    private void generateBalls(){
        b1 = new Momentum(30,30,2,3);//x, y, mass, velocity
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
            lbTwo.setLayoutY(c2.getCenterY()-13);
            lbThree.setLayoutX(c3.getCenterX()-5);
            lbThree.setLayoutY(c3.getCenterY()-13);
            lbFour.setLayoutX(c4.getCenterX()-5);
            lbFour.setLayoutY(c4.getCenterY()-13);
            lbFive.setLayoutX(c5.getCenterX()-5);
            lbFive.setLayoutY(c5.getCenterY()-13);
    }
    
    private void setUpSliders() {
       slM1.valueProperty().addListener((event)->{          

       });
    }
    
    private void showValues(int i){
       
        Circle c = c1;
        switch(i){
            case 1: c = c1; break;     
            case 2: c = c2; break;
            case 3: c = c3; break;
            case 4: c = c4; break;
            case 5: c = c5; break;
            default: System.out.println("Error");
        }
        
        //lbPositionX.setText(String.valueOf(c.getCenterX() + "x"));
        //lbPositionY.setText(String.valueOf(c.getCenterY() + "y"));       
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
