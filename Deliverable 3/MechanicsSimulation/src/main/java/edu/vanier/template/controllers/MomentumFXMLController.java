/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.controllers;

import edu.vanier.physics.Momentum;
import edu.vanier.template.ui.MainApp;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
            
    Stage stg;
    
    SpinnerValueFactory <Integer> balls;
    
    SpinnerValueFactory <Double> speed;

    String PlayPause = "paused";//a flag for the play/pause button
    
    Circle c1, c2, c3, c4, c5;//circle objects
    
    Momentum b1, b2, b3, b4, b5;//ball objects that correspond to every circle object
    
    public void setStage(Stage stage){
    this.stg = stage;
    }
    
    @FXML
    public void initialize() {
        initMenu();
        setUpSpinners();
        setUpButtons();
        generateBalls();
        initSetup();
    }
    
    //all relevant variable setups to be initialized
    private void initSetup(){
        btnB2.setDisable(true);
        btnB3.setDisable(true);
        btnB4.setDisable(true);
        btnB5.setDisable(true);
    }
    
    private void generateBalls(){
        b1 = new Momentum(30,30,2,3);//x, y, mass, velocity
        b2 = new Momentum(80,80,2,3);
        b3 = new Momentum(130,130,2,3);
        b4 = new Momentum(180,180,2,3);
        b5 = new Momentum(230,230,2,3);
        
        c1 = new Circle(b1.getPositionX(),b1.getPositionY(),20);
        c2 = new Circle(b2.getPositionX(),b2.getPositionY(),20);
        c3 = new Circle(b3.getPositionX(),b3.getPositionY(),20);
        c4 = new Circle(b4.getPositionX(),b4.getPositionY(),20);
        c5 = new Circle(b5.getPositionX(),b5.getPositionY(),20);

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
       spBalls.getEditor().setStyle("-fx-font-size: 20px");
       
       spBalls.valueProperty().addListener((event)->{
           switch (balls.getValue()) {
               case 1: 
                   setUIsVisible(false, false, false, false);
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
