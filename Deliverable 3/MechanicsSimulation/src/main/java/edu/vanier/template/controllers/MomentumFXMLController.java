/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.controllers;

import edu.vanier.physics.Momentum;
import edu.vanier.template.ui.MainApp;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
import javafx.scene.shape.Rectangle;
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
    Spinner <String> spBg;
    
    @FXML
    Button btnPlayPause, btnReset, btnTwoXSpeed, btnPointFiveXSpeed;    
            
    @FXML
    Button btnB1, btnB2, btnB3, btnB4, btnB5;
    
    @FXML
    ImageView ivPlayPause;        
            
    @FXML
    Pane momRoot;//the momentum root pane where the animation is taking place
            
    @FXML
    Label lbX1, lbX2, lbX3, lbX4, lbX5, lbY1, lbY2, lbY3, lbY4, lbY5;//to display the positions of all balls     
    
    @FXML
    Label lb1, lb2, lb3, lb4, lb5, lbv1, lbv2, lbv3, lbv4, lbv5, lbVx1, lbVx2, lbVx3, lbVx4, lbVx5, lbVy1, lbVy2, lbVy3, lbVy4, lbVy5;//to display the momentum of all balls   
    
    @FXML
    Label lbM1, lbM2, lbM3, lbM4, lbM5, lbV1, lbV2, lbV3, lbV4, lbV5;
    
    @FXML
    Label lbMomCalc1, lbMomCalc2, lbMomCalc3, lbImCalc1, lbImCalc2, lbImCalc3, lbEkCalc1, lbEkCalc2, lbEkCalc3;//the labels displaying the calculations
    
    @FXML
    Label lbTimePassed, lbGraph, lbV;
    
    @FXML
    Label lbCalc;//the label that says "Calculations (ball1)"
    
    @FXML
    Slider slM1, slM2, slM3, slM4, slM5, slV1, slV2, slV3, slV4, slV5;//sliders for the mass & velocities of balls        
            
    @FXML
    CheckBox cbDarkMode, cbDir, cbHUD;    
            
    Stage stg;
    
    SpinnerValueFactory <Integer> balls;

    SpinnerValueFactory <Double> speed;

    SpinnerValueFactory <String> background;
    
    String PlayPause = "paused";//a flag for the play/pause button
    
    @FXML
    Circle c1, c2, c3, c4, c5;//circle objects
    
    @FXML
    ComboBox cbGraph;        
            
    @FXML
    LineChart <Number, Number> lcGraph;        
            
    @FXML
    Rectangle recDark, recSim, recBg;//set this to visible for dark mode, invisible for light mode. recSim is the rectangle that covers up the simulation
            
    @FXML
    ImageView ivBackGround;
            
    Momentum b1, b2, b3, b4, b5;//ball objects that correspond to every circle object
    
    double orgSceneX, orgSceneY;//original mouse click positions
    double ogBallX, ogBallY;//original position of the dragged ball
    
    int data = 1;//determines which ball's data to display
    
    long min1, min2, sec1, sec2;//they make the digits in the timer 00:00
    
    double y1, y2, y3, y4, totalTime = 0;
    
    static public double timePerPixel = 0.015;//originally 0.01666667
        
    double timeRatio = 1;//playback speed
    
    ObservableList <String> bgs = FXCollections.observableArrayList("Blank", "Grass field", "Wooden floor", "Frozen lake");
    
    ArrayList<Circle> ballList = new ArrayList <Circle>();
    ArrayList<Momentum> momList = new ArrayList <Momentum>();
    boolean [][] collideflags = new boolean [5][5];//checking collision between every 2 balls
    boolean [] collidable = new boolean [5];
    double [] listPi = new double [5];//store the initial and final momentum before & after collision for impulse calculation
    double [] listPf = new double [5];
    
    AnimationTimer timer;
    
    XYChart.Series<Number,Number> series;//to make it easier to remove series when switching ball displays
    
    XYChart.Series<Number,Number> s1p;//series of data for momentum 
    XYChart.Series<Number,Number> s2p;
    XYChart.Series<Number,Number> s3p;
    XYChart.Series<Number,Number> s4p;
    XYChart.Series<Number,Number> s5p;
           
    XYChart.Series<Number,Number> s1j;//series of data for impulse 
    XYChart.Series<Number,Number> s2j;
    XYChart.Series<Number,Number> s3j;
    XYChart.Series<Number,Number> s4j;
    XYChart.Series<Number,Number> s5j;
    
    XYChart.Series<Number,Number> s1k;//series of data for kinetic energy 
    XYChart.Series<Number,Number> s2k;
    XYChart.Series<Number,Number> s3k;
    XYChart.Series<Number,Number> s4k;
    XYChart.Series<Number,Number> s5k;
    
    double b1X = 200; double b1Y = 100;//initial positions of the balls/stackpanes
    double b2X = 90; double b2Y = 200;
    double b3X = 300; double b3Y = 300;
    double b4X = 210; double b4Y = 400;
    double b5X = 400; double b5Y = 500;
    
    public void setStage(Stage stage){
    this.stg = stage;
    }
    
    @FXML
    public void initialize() {
        initMenu();
        setUpComboBox();
        setUpSpinners();
        setUpButtons();
        setUpSliders();
        setUpCheckBoxes();
        generateBalls();
        initSetup();
        tapToChangeDirection();
        animation();
    }
    
    //all relevant variable setups to be initialized
    private void initSetup(){
       
        s1p = new XYChart.Series<>();
        s2p = new XYChart.Series<>();
        s3p = new XYChart.Series<>();
        s4p = new XYChart.Series<>();
        s5p = new XYChart.Series<>();
        
        s1j = new XYChart.Series<>();
        s2j = new XYChart.Series<>();
        s3j = new XYChart.Series<>();
        s4j = new XYChart.Series<>();
        s5j = new XYChart.Series<>();
        
        s1k = new XYChart.Series<>();
        s2k = new XYChart.Series<>();
        s3k = new XYChart.Series<>();
        s4k = new XYChart.Series<>();
        s5k = new XYChart.Series<>();
        
        series = s1p;
        lcGraph.getData().add(series); 
        lcGraph.setLegendVisible(false);
        lcGraph.setAnimated(false);
        
        for(XYChart.Data<Number,Number> data: s1p.getData()){
          data.getNode().setVisible(false);
          }
        
        recDark.setVisible(false);//the background starts off in light mode
        
        collidable[0] = true;//ball 1 is always on screen and collidable
        collidable[1] = false;
        collidable[2] = false;
        collidable[3] = false;
        collidable[4] = false;
        
        ballList.add(c1);
        ballList.add(c2);
        ballList.add(c3);
        ballList.add(c4);
        ballList.add(c5);

        momList.add(b1);
        momList.add(b2);
        momList.add(b3);
        momList.add(b4);
        momList.add(b5);
        
        for(int i=0; i<4; i++){
                  for(int j=i+1; j<5; j++){
                       collideflags[i][j] = false;
                  }
                }
        
        b1.setPi(0);//initial momentum start off as 0 not the 3.14 pi
        b2.setPi(0);
        b3.setPi(0);
        b4.setPi(0);
        b5.setPi(0);
        
        b1.setPf(0);//final momentum start off as 0
        b2.setPf(0);
        b3.setPf(0);
        b4.setPf(0);
        b5.setPf(0);
        
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
        
        lbv2.setVisible(false);//momentum displays
        lbv3.setVisible(false);
        lbv4.setVisible(false);
        lbv5.setVisible(false);
        
        lbVx2.setVisible(false);//X momentum displays
        lbVx3.setVisible(false);
        lbVx4.setVisible(false);
        lbVx5.setVisible(false);
       
        lbVy2.setVisible(false);//Y momentum displays
        lbVy3.setVisible(false);
        lbVy4.setVisible(false);
        lbVy5.setVisible(false);

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
        
        lbX1.setText(String.valueOf(Math.round((c1.getCenterX())*10.0)/10.0));//22 is correction factor for the coordinates of the center of the ball
        lbY1.setText(String.valueOf(Math.round((c1.getCenterY())*10.0)/10.0));
        lbX2.setText(String.valueOf(Math.round((c2.getCenterX())*10.0)/10.0));
        lbY2.setText(String.valueOf(Math.round((c2.getCenterY())*10.0)/10.0));
        lbX3.setText(String.valueOf(Math.round((c3.getCenterX())*10.0)/10.0));
        lbY3.setText(String.valueOf(Math.round((c3.getCenterY())*10.0)/10.0));
        lbX4.setText(String.valueOf(Math.round((c4.getCenterX())*10.0)/10.0));
        lbY4.setText(String.valueOf(Math.round((c4.getCenterY())*10.0)/10.0));
        lbX5.setText(String.valueOf(Math.round((c5.getCenterX())*10.0)/10.0));
        lbY5.setText(String.valueOf(Math.round((c5.getCenterY())*10.0)/10.0));
        
        lbv1.setText(String.valueOf(Math.round(((double) slV1.getValue())*10.0)/10.0));
        lbv2.setText(String.valueOf(Math.round(((double) slV2.getValue())*10.0)/10.0));
        lbv3.setText(String.valueOf(Math.round(((double) slV3.getValue())*10.0)/10.0));
        lbv4.setText(String.valueOf(Math.round(((double) slV4.getValue())*10.0)/10.0));
        lbv5.setText(String.valueOf(Math.round(((double) slV5.getValue())*10.0)/10.0));
        
        lbVx1.setText(String.valueOf(Math.round(((double) b1.getVelocityX())*10.0)/10.0));
        lbVx2.setText(String.valueOf(Math.round(((double) b2.getVelocityX())*10.0)/10.0));
        lbVx3.setText(String.valueOf(Math.round(((double) b3.getVelocityX())*10.0)/10.0));
        lbVx4.setText(String.valueOf(Math.round(((double) b4.getVelocityX())*10.0)/10.0));
        lbVx5.setText(String.valueOf(Math.round(((double) b5.getVelocityX())*10.0)/10.0));
        
        lbVy1.setText(String.valueOf(Math.round(((double) b1.getVelocityY())*10.0)/10.0));
        lbVy2.setText(String.valueOf(Math.round(((double) b2.getVelocityY())*10.0)/10.0));
        lbVy3.setText(String.valueOf(Math.round(((double) b3.getVelocityY())*10.0)/10.0));
        lbVy4.setText(String.valueOf(Math.round(((double) b4.getVelocityY())*10.0)/10.0));
        lbVy5.setText(String.valueOf(Math.round(((double) b5.getVelocityY())*10.0)/10.0));

        showValues(1);
        cbGraph.setValue("Momentum");
        
        cbHUD.fire();//HUD is turned on to be visible by default
    }
    
    
    
    private void generateBalls(){
        b1 = new Momentum(b1X, b1Y, 5, slV1.getValue()/(Math.sqrt(2)), slV1.getValue()/(Math.sqrt(2)));//x, y, mass, velocityX, velocityY
        b2 = new Momentum(b2X, b2Y, 5, slV2.getValue()/(Math.sqrt(2)), slV2.getValue()/(Math.sqrt(2)));
        b3 = new Momentum(b3X, b3Y, 5, slV3.getValue()/(Math.sqrt(2)), slV3.getValue()/(Math.sqrt(2)));
        b4 = new Momentum(b4X, b4Y, 5, slV4.getValue()/(Math.sqrt(2)), slV4.getValue()/(Math.sqrt(2)));
        b5 = new Momentum(b5X, b5Y, 5, slV5.getValue()/(Math.sqrt(2)), slV5.getValue()/(Math.sqrt(2)));
        
        b1.setVelocity(slV1.getValue());
        b2.setVelocity(slV2.getValue());
        b3.setVelocity(slV3.getValue());
        b4.setVelocity(slV4.getValue());
        b5.setVelocity(slV5.getValue());

        c1.setCenterX(b1.getPositionX());
        c1.setCenterY(b1.getPositionY());
        c2.setCenterX(b2.getPositionX());
        c2.setCenterY(b2.getPositionY());
        c3.setCenterX(b3.getPositionX());
        c3.setCenterY(b3.getPositionY());
        c4.setCenterX(b4.getPositionX());
        c4.setCenterY(b4.getPositionY());
        c5.setCenterX(b5.getPositionX());
        c5.setCenterY(b5.getPositionY());
    
        c1.setOnMousePressed(circleOnMousePressedEventHandler(c1));
        c1.setOnMouseDragged(circleOnMouseDraggedEventHandler(c1));
        
        c2.setOnMousePressed(circleOnMousePressedEventHandler(c2));
        c2.setOnMouseDragged(circleOnMouseDraggedEventHandler(c2));
        
        c3.setOnMousePressed(circleOnMousePressedEventHandler(c3));
        c3.setOnMouseDragged(circleOnMouseDraggedEventHandler(c3));
        
        c4.setOnMousePressed(circleOnMousePressedEventHandler(c4));
        c4.setOnMouseDragged(circleOnMouseDraggedEventHandler(c4)); 
        
        c5.setOnMousePressed(circleOnMousePressedEventHandler(c5));
        c5.setOnMouseDragged(circleOnMouseDraggedEventHandler(c5));

        c2.setVisible(false);
        c3.setVisible(false);
        c4.setVisible(false);
        c5.setVisible(false);
    }

    private EventHandler<MouseEvent> circleOnMousePressedEventHandler(Circle c){
    return new EventHandler<MouseEvent>(){
    @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();//mouse click x position
            orgSceneY = t.getSceneY();//mouse click y position
            ogBallX = c.getCenterX();//records ball's starting position
            ogBallY = c.getCenterY();//top left corner
        }
    };
    }
    
    private EventHandler<MouseEvent> circleOnMouseDraggedEventHandler(Circle c){    
        return new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            double offsetX = t.getSceneX() - orgSceneX;//how much the mouse dragged away the ball
            double offsetY = t.getSceneY() - orgSceneY;
            
            double moveX = ogBallX + offsetX;//42 & 57 are the correction factor for when mouse moves a ball and it shifts
            double moveY = ogBallY + offsetY;
            
            if(moveX < 0){moveX = c.getRadius();}//if ball will move below 0, it will be capped at 0
            else if(moveX > 750){moveX = 750 + c.getRadius();}//if ball will move over 750, it will be capped at 750
            
            if(moveY < 0){moveY = c.getRadius();}//same logic here as above
            else if(moveY > 502){moveY = 502 + c.getRadius();}
            
            c.setCenterX(moveX);
            c.setCenterY(moveY);
            
            otherValues();
            
            switch(data){
                case 1: showValues(1);break;
                case 2: showValues(2);break;
                case 3: showValues(3);break;
                case 4: showValues(4);break;
                case 5: showValues(5);break;
                default: System.out.println("Something's wrong"); break;
            }            
        }
    };
    }

    int dataTimeCtr = 0;//helps keep track of the passage of every second
    
    private void animation(){
        
         timer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                lbV.setText("Apply velocity (m/s)");
                
                for(int i = 0; i<momList.size(); i++){
                   momList.get(i).setVelocity(momList.get(i).getVelocity());
                   momList.get(i).setVelocityX(momList.get(i).getVelocityX());
                   momList.get(i).setVelocityY(momList.get(i).getVelocityY());
                }
                
                
                timePerPixel = 0.015 * timeRatio; //changes as the user adjusts the speed of the simulation
                otherValues();//always changing and displaying all the balls' positions and momentums
                showValues(data);

                if(totalTime-dataTimeCtr>1){//every one second a new data point is added
                addDataToGraph(dataTimeCtr);
                graph();//displaying the graph
                dataTimeCtr++;
                }

                //constantly removing the data point labels on the graph to make the lines look more smooth
                for(XYChart.Data<Number,Number> data: series.getData()){
                    data.getNode().setVisible(false);
                } 
                
                //iterating through all balls to check their boundaries with the pane
                for(int i=0; i<ballList.size(); i++){
                       checkBounds(ballList.get(i), momList.get(i));
                  }
                
                checkCollision();
                //iterating through all balls to set their new movements corresponding to changing velocities
                for(int i=0; i<momList.size(); i++){
                       movingBalls(i, momList.get(i), ballList.get(i));
                }

                if(PlayPause.equals("playing")){//when the pause button isn't clicked yet i.e. when animation is currently playing
                  HUD(y1,y2,y3,y4);   
                  y1+=timePerPixel;
                  y2+=timePerPixel;
                  y3+=timePerPixel/60;
                  y4+=timePerPixel;
                  totalTime=totalTime+timePerPixel;
                }
                
                if(y1>10){
                y1=y1-10;
                }
                
                if(y2>60){
                y2=y2-60;
                }

                if(y3>10){
                y3=y3-10;
                }
                //the maximum time the animation can run for is an hour
                if(totalTime>3600){
                Alert("Watch out","You're getting kicked out","You've been staring at the pucks for an hour. What are you doing?");
                btnReset.fire();
                timer.stop();
                }                
                
            }
        
        };
    }
    
    private void movingBalls(int i, Momentum ball, Circle c){
        if(collidable[i] == true){//only moves when ball is collidable/enabled
        c.setCenterX(c.getCenterX() + ball.getVelocityX()*timeRatio);//moving the balls, their movement speed(not velocity) change proportional to the playback speed
        c.setCenterY(c.getCenterY() + ball.getVelocityY()*timeRatio);
        }
    }
    
    private void checkBounds(Circle c, Momentum b){        
                //if x and y positions of balls are out of bounds, their directions are reversed
                if(c.getCenterX() + c.getRadius() >= 794 || c.getCenterX() - c.getRadius() <= 0){
                   if(c.getCenterX() + c.getRadius() >= 794)c.setCenterX(794-c.getRadius()-1);//forcibly moves ball back onto the pane so it doesn't get stuck outside
                   if(c.getCenterX() - c.getRadius() <= 0)c.setCenterX(0+c.getRadius()+1);//forcibly moves ball back onto the pane so it doesn't get stuck outside
                   b.setVelocityX(b.getVelocityX()*-1);
                }

                if(c.getCenterY() + c.getRadius() >= 544 || c.getCenterY() - c.getRadius() <= 0){
                   if(c.getCenterY() + c.getRadius() >= 544)c.setCenterY(544-c.getRadius()-1);//forcibly moves ball back onto the pane so it doesn't get stuck outside
                   if(c.getCenterY() - c.getRadius() <= 0)c.setCenterY(0+c.getRadius()+1);//forcibly moves ball back onto the pane so it doesn't get stuck outside
                   b.setVelocityY(b.getVelocityY()*-1);
                }
                
               // if(c.getCenterX() + c.getRadius())
    }

    //when two balls collide, they should bounce off each other
    private void checkCollision(){
        Circle B1;
        Circle B2;
        
        for(int i=0; i<ballList.size(); i++){
            B1 = ballList.get(i);
            double x1 = B1.getCenterX();
            double y1 = B1.getCenterY();

            for(int j=i+1; j<ballList.size(); j++){
                B2 = ballList.get(j);
                 double x2 = B2.getCenterX();
                 double y2 = B2.getCenterY();
                 
                 double x = Math.abs(x1-x2);
                 double y = Math.abs(y1-y2);
                 
                 double radSum = B1.getRadius() + B2.getRadius();
               
        if (x*x + y*y <= radSum*radSum && B1.intersects(B2.getBoundsInParent())) {
            if(collideflags[i][j] == false){
                 if(collidable[i] == true && collidable[j] == true){
                     
                     setNewVelocities(momList.get(i),momList.get(j), B1, B2, i, j);
                     collideflags[i][j] = true;

                 }
            }
            
            }
        else{
           collideflags[i][j] = false;
        }
        
                  }
                }
    }

    private void setNewVelocities(Momentum ball1, Momentum ball2, Circle c1, Circle c2, int i, int j){
    
        double m1 = ball1.getMass();
        double m2 = ball2.getMass();
        double x1 = ball1.getPositionX();
        double x2 = ball2.getPositionX();
        double y1 = ball1.getPositionY();
        double y2 = ball2.getPositionY();
        double vx1 = ball1.getVelocityX();
        double vy1 = ball1.getVelocityY();
        double vx2 = ball2.getVelocityX();
        double vy2 = ball2.getVelocityY();
                    
        double x = x1-x2;
        double y = y1-y2;
        double distance = Math.sqrt(x*x+y*y);

        
        double normx = x/distance;
        double normy = y/distance;
        
        double tanx = -normy;
        double tany = normx;
        
        double v1n = vx1*normx+vy1*normy;
        double v1t = vx1*tanx+vy1*tany;
        double v2n = vx2*normx+vy2*normy;
        double v2t = vx2*tanx+vy2*tany;
        
        double v1nNew = (v1n*(m1-m2)+2*m2*v2n)/(m1+m2); 
        double v2nNew = (v2n*(m2-m1)+2*m1*v1n)/(m1+m2);
        
        double vx1New = v1nNew*normx+v1t*tanx; 
        double vy1New = v1nNew*normy+v1t*tany; 
        double vx2New = v2nNew*normx+v2t*tanx; 
        double vy2New = v2nNew*normy+v2t*tany;
        
        ball1.setVelocityX(vx1New);
        ball1.setVelocityY(vy1New);
        ball1.setVelocity(Math.sqrt(Math.abs(vx1New*vx1New + vy1New*vy1New)));
        ball2.setVelocityX(vx2New);
        ball2.setVelocityY(vy2New);
        ball2.setVelocity(Math.sqrt(vx2New*vx2New + vy2New*vy2New));
        
        
        ball1.setPi(ball1.getPf());
        ball2.setPi(ball2.getPf());
        
        ball1.setPf(ball1.calcMomentum());
        ball2.setPf(ball2.calcMomentum());
    }
    
    //manages the heads up display (current velocity, distance traveled, time passed)
    private void HUD(double y1, double y2, double y3, double y4){
              sec2=Math.round(y1)*100/100;
              sec1=(Math.round(y2)*100/100)/10;
              min2=(Math.round(y3-0.492)*100/100);
              
              if(y4>600&&y4<1200){
              min1=1;
              }
              else if(y4>1200&&y4<1800){
              min1=2;
              }
              else if(y4>1800&&y4<2400){
              min1=3;
              }
              else if(y4>2400&&y4<3000){
              min1=4;
              }
              else if(y4>3000&&y4<3600){
              min1=5;
              }
              
             String min2String = Double.toString(min2);
             String min1String = Double.toString(min1);

              if(sec2!=0 && sec2%10==0){
                  sec2=0;
              }
              
              if(sec1==6){
                 sec1=0;         
              }
              
    lbTimePassed.setText("Time passed: " + min1String.charAt(0) + min2String.charAt(0) + ":" + sec1 + sec2);
    };
    
    private void initMenu(){//initializes the buttons in the menu bar
        //File
        mitBack.setOnAction((event)->{
            loadPrimaryScene(event);
            btnReset.fire();
           }              
        );
        
        mitClose.setOnAction((event)->{
        stg.close();
        });
        //Edit
        //Help
        mitAbout.setOnAction((event)->{
        Alert("Momentum simulation","Momentum & collision","When two moving objects collide, a momentum transfer occurs. In the real world, friction would be"
        + "constantly eating away some energy, but the purpose of this simulation is to demonstrate only the concept of momentum transfer through collisions, "
        + "so we assume perfectly elastic collisions with no loss of energy through friction or any other means."
        );
        });
        mitTips.setOnAction((event)->{
        Alert("Some tips","Some features","Welcome to momentum simulation \n\nTapping on the x and y velocity components on the right side when the animation" 
                + " is running will change its direction. \n\nChanging the mass of the balls will change their size.\n\n"
           + "At any point during the simulation, you can manually apply an external velocity on any of the balls with the sliders."
        );
        });
    }

    private void setUpComboBox(){
       cbGraph.getItems().addAll("Momentum", "Impulse", "Kinetic energy");
       cbGraph.setOnAction((event)->{
          if(cbGraph.getValue().equals("Momentum")){
             lbGraph.setText("Momentum(kgm/s) vs time(s)");
          }
          else if(cbGraph.getValue().equals("Impulse")){
             lbGraph.setText("Impulse(kgm/s) vs time(s)");
          }
          else if(cbGraph.getValue().equals("Kinetic energy")){
             lbGraph.setText("Kinetic energy(J) vs time(s)");
          }
          lcGraph.getData().remove(series);
          
          if(cbGraph.getValue().equals("Momentum")){
             switch(data){
                 case 1: series = s1p; break;
                 case 2: series = s2p; break;
                 case 3: series = s3p; break;
                 case 4: series = s4p; break;
                 case 5: series = s5p; break;
             }
          }
          else if(cbGraph.getValue().equals("Impulse")){
             switch(data){
                 case 1: series = s1j; break;
                 case 2: series = s2j; break;
                 case 3: series = s3j; break;
                 case 4: series = s4j; break;
                 case 5: series = s5j; break;
             }
          }
          else if(cbGraph.getValue().equals("Kinetic energy")){
             switch(data){
                 case 1: series = s1k; break;
                 case 2: series = s2k; break;
                 case 3: series = s3k; break;
                 case 4: series = s4k; break;
                 case 5: series = s5k; break;
             }
          }
          
          if(lcGraph.getData().isEmpty())lcGraph.getData().add(series);
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
       
       speed = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1, 3, 1, 0.1);//min 0.1x times speed, max 3x speed, adds 0.1x at a time
       spPlayBack.setValueFactory(speed);
       spPlayBack.getEditor().setStyle("-fx-font-size: 15px;");    
       spPlayBack.valueProperty().addListener((event)->{
          timeRatio = spPlayBack.getValue();
          
       });
       
       background = new SpinnerValueFactory.ListSpinnerValueFactory<String>(bgs);      
       spBg.getEditor().setStyle("-fx-font-size: 15px;");
       spBg.setValueFactory(background);
       spBg.valueProperty().addListener((event)->{
          switch(background.getValue()){
              case "Blank": ivBackGround.setVisible(false); break;
              case "Grass field": ivBackGround.setVisible(true); ivBackGround.setImage(new Image("/Images/GrassField.png")); break;
              case "Wooden floor": ivBackGround.setVisible(true); ivBackGround.setImage(new Image("/Images/WoodenFloor.png")); break;
              case "Frozen lake": ivBackGround.setVisible(true); ivBackGround.setImage(new Image("/Images/FrozenLake.png")); break;
          }
       });
       background.setValue("Grass field");
    }
    
    private void setUpCheckBoxes(){
       cbHUD.setOnAction((event)->{
           if(cbHUD.isSelected()){
             lbTimePassed.setVisible(true);
           }
           else{
             lbTimePassed.setVisible(false);
           }
       });
       
       cbDarkMode.setOnAction((event)->{
           if(cbDarkMode.isSelected()){
             recDark.setVisible(true);
             recBg.setFill(Color.web("#969a9e"));
           }
           else{
             recDark.setVisible(false);
             recBg.setFill(Color.web("#f7f7f7"));
           }
       });
    }
    
    //to disable/enable UIs with flags based on how many balls there are
    //c1 will always be visible
    private void setUIsVisible(boolean f2, boolean f3, boolean f4, boolean f5){
        //PROBLEM: when circles are invisible, the active ones can still collide with them
        c2.setVisible(f2);
        collidable[1] = f2;
        c3.setVisible(f3);
        collidable[2] = f3;
        c4.setVisible(f4);
        collidable[3] = f4;
        c5.setVisible(f5);
        collidable[4] = f5;

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
        
        lbv2.setVisible(f2);
        lbv3.setVisible(f3);
        lbv4.setVisible(f4);
        lbv5.setVisible(f5);
        
        lbVx2.setVisible(f2);
        lbVx3.setVisible(f3);
        lbVx4.setVisible(f4);
        lbVx5.setVisible(f5);
        
        lbVy2.setVisible(f2);
        lbVy3.setVisible(f3);
        lbVy4.setVisible(f4);
        lbVy5.setVisible(f5);
        
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
       
       btnReset.setOnAction((event)->{
          lbV.setText("Initial velocity (m/s)");
          totalTime = 0;//resetting the time
          dataTimeCtr = 0;//resetting the counter for adding a data point to the series every second
          
          b1.setPositionX(b1X); b1.setPositionY(b1Y);
          b2.setPositionX(b2X); b2.setPositionY(b2Y);
          b3.setPositionX(b3X); b3.setPositionY(b3Y);
          b4.setPositionX(b4X); b4.setPositionY(b4Y);
          b5.setPositionX(b5X); b5.setPositionY(b5Y);
          
          c1.setCenterX(b1.getPositionX());
          c1.setCenterY(b1.getPositionY());
          c2.setCenterX(b2.getPositionX());
          c2.setCenterY(b2.getPositionY());
          c3.setCenterX(b3.getPositionX());
          c3.setCenterY(b3.getPositionY());
          c4.setCenterX(b4.getPositionX());
          c4.setCenterY(b4.getPositionY());
          c5.setCenterX(b5.getPositionX());
          c5.setCenterY(b5.getPositionY());
        
          sec1=sec2=0;//setting time displayed as zero
          min1=min2=0;
            
          timePerPixel = 0.015;
          y1=y2=y3=y4=0;
          HUD(y1,y2,y3,y4);
           
          balls.setValue(1);
          
          timer.stop();
          
          for(int i=0; i<4; i++){
                  for(int j=i+1; j<5; j++){
                       collideflags[i][j] = false;
                  }
                }
        
        if(PlayPause.equals("playing")){//
           btnPlayPause.fire();
        }
          
        speed.setValue(1.0);//setting speed back to 1x
        
        lcGraph.getData().clear();
        Time.clear();
        s1p.getData().clear(); s1j.getData().clear(); s1k.getData().clear();//clears all series
        s2p.getData().clear(); s2j.getData().clear(); s2k.getData().clear();
        s3p.getData().clear(); s3j.getData().clear(); s3k.getData().clear();
        s4p.getData().clear(); s4j.getData().clear(); s4k.getData().clear();
        s5p.getData().clear(); s5j.getData().clear(); s5k.getData().clear();
        
        series = s1p;
        lcGraph.getData().add(series); 
        lcGraph.setLegendVisible(false);
        lcGraph.setAnimated(false);
        
        for(XYChart.Data<Number,Number> data: s1p.getData()){
          data.getNode().setVisible(false);
          }
        
        if(!cbHUD.isSelected()){cbHUD.fire();}
        
        slV1.setValue(2); slM1.setValue(5);
        
        c2.setVisible(false);
        c3.setVisible(false);
        c4.setVisible(false);
        c5.setVisible(false);
        
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
        
        lbv2.setVisible(false);//momentum displays
        lbv3.setVisible(false);
        lbv4.setVisible(false);
        lbv5.setVisible(false);
        
        lbVx2.setVisible(false);//X momentum displays
        lbVx3.setVisible(false);
        lbVx4.setVisible(false);
        lbVx5.setVisible(false);
       
        lbVy2.setVisible(false);//Y momentum displays
        lbVy3.setVisible(false);
        lbVy4.setVisible(false);
        lbVy5.setVisible(false);

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
        
        lbX1.setText(String.valueOf(Math.round((c1.getCenterX())*10.0)/10.0));//22 is correction factor for the coordinates of the center of the ball
        lbY1.setText(String.valueOf(Math.round((c1.getCenterY())*10.0)/10.0));
        lbX2.setText(String.valueOf(Math.round((c2.getCenterX())*10.0)/10.0));
        lbY2.setText(String.valueOf(Math.round((c2.getCenterY())*10.0)/10.0));
        lbX3.setText(String.valueOf(Math.round((c3.getCenterX())*10.0)/10.0));
        lbY3.setText(String.valueOf(Math.round((c3.getCenterY())*10.0)/10.0));
        lbX4.setText(String.valueOf(Math.round((c4.getCenterX())*10.0)/10.0));
        lbY4.setText(String.valueOf(Math.round((c4.getCenterY())*10.0)/10.0));
        lbX5.setText(String.valueOf(Math.round((c5.getCenterX())*10.0)/10.0));
        lbY5.setText(String.valueOf(Math.round((c5.getCenterY())*10.0)/10.0));
        
        lbv1.setText(String.valueOf(Math.round(((double) b1.getVelocity())*10.0)/10.0));
        lbv2.setText(String.valueOf(Math.round(((double) b2.getVelocity())*10.0)/10.0));
        lbv3.setText(String.valueOf(Math.round(((double) b3.getVelocity())*10.0)/10.0));
        lbv4.setText(String.valueOf(Math.round(((double) b4.getVelocity())*10.0)/10.0));
        lbv5.setText(String.valueOf(Math.round(((double) b5.getVelocity())*10.0)/10.0));
        
        lbMomCalc2.setVisible(false);
        lbMomCalc3.setVisible(false);
        lbImCalc2.setVisible(false);
        lbImCalc3.setVisible(false);
        lbEkCalc2.setVisible(false);
        lbEkCalc3.setVisible(false);
        
       });
       
       btnB1.setOnAction((event)->{
           data = 1; 
           lbCalc.setText("Calculations (ball 1)");
           showValues(1);
           lcGraph.getData().remove(series);
           
           if(cbGraph.getValue().equals("Momentum")){
           series = s1p;
           }
           else if(cbGraph.getValue().equals("Impulse")){
           series = s1j;
           }
           else if(cbGraph.getValue().equals("Kinetic energy")){
           series = s1k;
           }
           
           if(lcGraph.getData().isEmpty())lcGraph.getData().add(series);
           for(XYChart.Data<Number,Number> data: series.getData()){
           data.getNode().setVisible(false);//this is here because this same block in the animationtimer doesn't work when the animationtimer paused
           }
           series.getNode().setStyle("-fx-stroke: #ff4500;");     
       });//ball 1's data will now be displayed
       btnB2.setOnAction((event)->{
           data = 2; 
           lbCalc.setText("Calculations (ball 2)");
           showValues(2);
           lcGraph.getData().remove(series);
           
           if(cbGraph.getValue().equals("Momentum")){
           series = s2p;
           }
           else if(cbGraph.getValue().equals("Impulse")){
           series = s2j;
           }
           else if(cbGraph.getValue().equals("Kinetic energy")){
           series = s2k;
           }
           
           if(lcGraph.getData().isEmpty())lcGraph.getData().add(series);
           for(XYChart.Data<Number,Number> data: series.getData()){
           data.getNode().setVisible(false);
           }
           series.getNode().setStyle("-fx-stroke: green;");   
       });//ball 2's data will now be displayed
       btnB3.setOnAction((event)->{
           data = 3; 
           lbCalc.setText("Calculations (ball 3)");
           showValues(3);
           lcGraph.getData().remove(series);
           
           if(cbGraph.getValue().equals("Momentum")){
           series = s3p;
           }
           else if(cbGraph.getValue().equals("Impulse")){
           series = s3j;
           }
           else if(cbGraph.getValue().equals("Kinetic energy")){
           series = s3k;
           }
           
           if(lcGraph.getData().isEmpty())lcGraph.getData().add(series);
           for(XYChart.Data<Number,Number> data: series.getData()){
           data.getNode().setVisible(false);
           }
           series.getNode().setStyle("-fx-stroke: blue;");   
       });//ball 3's data will now be displayed
       btnB4.setOnAction((event)->{
           data = 4; 
           lbCalc.setText("Calculations (ball 4)");
           showValues(4);
           lcGraph.getData().remove(series);
           
           if(cbGraph.getValue().equals("Momentum")){
           series = s4p;
           }
           else if(cbGraph.getValue().equals("Impulse")){
           series = s4j;
           }
           else if(cbGraph.getValue().equals("Kinetic energy")){
           series = s4k;
           }
           
           if(lcGraph.getData().isEmpty())lcGraph.getData().add(series);
           for(XYChart.Data<Number,Number> data: series.getData()){
           data.getNode().setVisible(false);
           }
           series.getNode().setStyle("-fx-stroke: brown;");   
       });//ball 4's data will now be displayed
       btnB5.setOnAction((event)->{
           data = 5; 
           lbCalc.setText("Calculations (ball 5)");
           showValues(5);
           lcGraph.getData().remove(series);
           
           if(cbGraph.getValue().equals("Momentum")){
           series = s5p;
           }
           else if(cbGraph.getValue().equals("Impulse")){
           series = s5j;
           }
           else if(cbGraph.getValue().equals("Kinetic energy")){
           series = s5k;
           }
           
           if(lcGraph.getData().isEmpty())lcGraph.getData().add(series);
           for(XYChart.Data<Number,Number> data: series.getData()){
           data.getNode().setVisible(false);
           }
           series.getNode().setStyle("-fx-stroke: purple;");   
       });//ball 5's data will now be displayed

       btnTwoXSpeed.setOnAction((event)->{speed.setValue(2.0);});
       btnPointFiveXSpeed.setOnAction((event)->{speed.setValue(0.5);});
    }
    
    private void setUpSliders() {
       slM1.valueProperty().addListener((event)->{          
        lbM1.setText(String.valueOf(Math.round(slM1.getValue()*10.0)/10.0));
        b1.setMass(slM1.getValue());//changing ball's mass
        c1.setRadius(22 + slM1.getValue()*0.5);
        if(data==1){
        showValues(1);
        }
       });
       
       slM2.valueProperty().addListener((event)->{          
        lbM2.setText(String.valueOf(Math.round(slM2.getValue()*10.0)/10.0));
        b2.setMass(slM2.getValue());
        c2.setRadius(22 + slM2.getValue()*0.5);
        if(data==2){
        showValues(2);
        }
       });
       
       slM3.valueProperty().addListener((event)->{          
        lbM3.setText(String.valueOf(Math.round(slM3.getValue()*10.0)/10.0));
        b3.setMass(slM3.getValue());
        c3.setRadius(22 + slM3.getValue()*0.5);
        if(data==3){
        showValues(3);
        }
       });
       
       slM4.valueProperty().addListener((event)->{          
        lbM4.setText(String.valueOf(Math.round(slM4.getValue()*10.0)/10.0));
        b4.setMass(slM4.getValue());
        c4.setRadius(22 + slM4.getValue()*0.5);
        if(data==4){
        showValues(4);
        }
       });
       
       slM5.valueProperty().addListener((event)->{          
        lbM5.setText(String.valueOf(Math.round(slM5.getValue()*10.0)/10.0));
        b5.setMass(slM5.getValue());
        c5.setRadius(22 + slM5.getValue()*0.5);
        if(data==5){
        showValues(5);
        }
       });
       
       slV1.valueProperty().addListener((event)->{          
        lbV1.setText(String.valueOf(Math.round(slV1.getValue()*10.0)/10.0));//the label next to the slider
        lbv1.setText(String.valueOf(Math.round(slV1.getValue()*10.0)/10.0));//the label on the right top V
        lbVx1.setText(String.valueOf(Math.round(((double) b1.getVelocityX())*10.0)/10.0));//the right top label Vx
        lbVy1.setText(String.valueOf(Math.round(((double) b1.getVelocityY())*10.0)/10.0));
        b1.setVelocity(slV1.getValue());//setting the overall velocity
        b1.setVelocityX(slV1.getValue()*Math.cos(b1.getAngle()));//setting the x & y velocities
        b1.setVelocityY(slV1.getValue()*Math.sin(b1.getAngle()));
        if(data==1)showValues(1);
       });
       
       slV2.valueProperty().addListener((event)->{          
        lbV2.setText(String.valueOf(Math.round(slV2.getValue()*10.0)/10.0));//the label next to the slider
        lbv2.setText(String.valueOf(Math.round(slV2.getValue()*10.0)/10.0));//the label on the right top V
        lbVx2.setText(String.valueOf(Math.round(((double) b2.getVelocityX())*10.0)/10.0));//the right top label Vx
        lbVy2.setText(String.valueOf(Math.round(((double) b2.getVelocityY())*10.0)/10.0));
        b2.setVelocity(slV2.getValue());//setting the overall velocity
        b2.setVelocityX(slV2.getValue()*Math.cos(b2.getAngle()));//setting the x & y velocities
        b2.setVelocityY(slV2.getValue()*Math.sin(b2.getAngle()));
        if(data==2)showValues(2);
       });
       
       slV3.valueProperty().addListener((event)->{          
        lbV3.setText(String.valueOf(Math.round(slV3.getValue()*10.0)/10.0));//the label next to the slider
        lbv3.setText(String.valueOf(Math.round(slV3.getValue()*10.0)/10.0));//the label on the right top V
        lbVx3.setText(String.valueOf(Math.round(((double) b3.getVelocityX())*10.0)/10.0));//the right top label Vx
        lbVy3.setText(String.valueOf(Math.round(((double) b3.getVelocityY())*10.0)/10.0));
        b3.setVelocity(slV3.getValue());//setting the overall velocity
        b3.setVelocityX(slV3.getValue()*Math.cos(b3.getAngle()));//setting the x & y velocities
        b3.setVelocityY(slV3.getValue()*Math.sin(b3.getAngle()));
        if(data==3)showValues(3);
       });
       
       slV4.valueProperty().addListener((event)->{          
        lbV4.setText(String.valueOf(Math.round(slV4.getValue()*10.0)/10.0));//the label next to the slider
        lbv4.setText(String.valueOf(Math.round(slV4.getValue()*10.0)/10.0));//the label on the right top V
        lbVx4.setText(String.valueOf(Math.round(((double) b4.getVelocityX())*10.0)/10.0));//the right top label Vx
        lbVy4.setText(String.valueOf(Math.round(((double) b4.getVelocityY())*10.0)/10.0));
        b4.setVelocity(slV4.getValue());//setting the overall velocity
        b4.setVelocityX(slV4.getValue()*Math.cos(b4.getAngle()));//setting the x & y velocities
        b4.setVelocityY(slV4.getValue()*Math.sin(b4.getAngle()));
        if(data==4)showValues(4);
       });
       
       slV5.valueProperty().addListener((event)->{          
        lbV5.setText(String.valueOf(Math.round(slV5.getValue()*10.0)/10.0));//the label next to the slider
        lbv5.setText(String.valueOf(Math.round(slV5.getValue()*10.0)/10.0));//the label on the right top V
        lbVx5.setText(String.valueOf(Math.round(((double) b5.getVelocityX())*10.0)/10.0));//the right top label Vx
        lbVy5.setText(String.valueOf(Math.round(((double) b5.getVelocityY())*10.0)/10.0));
        b5.setVelocity(slV5.getValue());//setting the overall velocity
        b5.setVelocityX(slV5.getValue()*Math.cos(b5.getAngle()));//setting the x & y velocities
        b5.setVelocityY(slV5.getValue()*Math.sin(b5.getAngle()));
        if(data==5)showValues(5);
       });
    }
    
    ArrayList<Double> MomTime = new ArrayList<>();
    Momentum b;   
    
    private void graph(){
       
       s1p.getData().add(new XYChart.Data(dataTimeCtr, b1.calcMomentum()));
       s2p.getData().add(new XYChart.Data(dataTimeCtr, b2.calcMomentum()));
       s3p.getData().add(new XYChart.Data(dataTimeCtr, b3.calcMomentum()));
       s4p.getData().add(new XYChart.Data(dataTimeCtr, b4.calcMomentum()));
       s5p.getData().add(new XYChart.Data(dataTimeCtr, b5.calcMomentum()));
         
       s1j.getData().add(new XYChart.Data(dataTimeCtr, b1.calcImpulse()));
       s2j.getData().add(new XYChart.Data(dataTimeCtr, b2.calcImpulse()));
       s3j.getData().add(new XYChart.Data(dataTimeCtr, b3.calcImpulse()));
       s4j.getData().add(new XYChart.Data(dataTimeCtr, b4.calcImpulse()));
       s5j.getData().add(new XYChart.Data(dataTimeCtr, b5.calcImpulse()));
       
       s1k.getData().add(new XYChart.Data(dataTimeCtr, b1.calcEk()));
       s2k.getData().add(new XYChart.Data(dataTimeCtr, b2.calcEk()));
       s3k.getData().add(new XYChart.Data(dataTimeCtr, b3.calcEk()));
       s4k.getData().add(new XYChart.Data(dataTimeCtr, b4.calcEk()));
       s5k.getData().add(new XYChart.Data(dataTimeCtr, b5.calcEk()));
    }
    
    ArrayList<Integer> Time = new ArrayList<>();//used to store time for graph in seconds
    ArrayList<Double> MomTime1 = new ArrayList<>();//used to store momentum data points for graph in seconds
    ArrayList<Double> MomTime2 = new ArrayList<>();
    ArrayList<Double> MomTime3 = new ArrayList<>();
    ArrayList<Double> MomTime4 = new ArrayList<>();
    ArrayList<Double> MomTime5 = new ArrayList<>();
    
    private void addDataToGraph(int i){
       Time.add(i);//adds one data point every second
       MomTime1.add(b1.calcMomentum());//always adding points to all the balls' lists
       MomTime2.add(b2.calcMomentum());
       MomTime3.add(b3.calcMomentum());
       MomTime4.add(b4.calcMomentum());
       MomTime5.add(b5.calcMomentum());
    }
    
    private void tapToChangeDirection(){
       lbVx1.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
              b1.setVelocityX(-b1.getVelocityX());
           }
       });
       
       lbVy1.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
              b1.setVelocityY(-b1.getVelocityY());
           }
       });
       
       lbVx2.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
              b2.setVelocityX(-b2.getVelocityX());
           }
       });
       
       lbVy2.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
              b2.setVelocityY(-b2.getVelocityY());
           }
       });
       
       lbVx3.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
              b3.setVelocityX(-b3.getVelocityX());
           }
       });
       
       lbVy3.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
              b3.setVelocityY(-b3.getVelocityY());
           }
       });
       
       lbVx4.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
              b4.setVelocityX(-b4.getVelocityX());
           }
       });
       
       lbVy4.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
              b4.setVelocityY(-b4.getVelocityY());
           }
       });
       
       lbVx5.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
              b5.setVelocityX(-b5.getVelocityX());
           }
       });
       
       lbVy5.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
              b5.setVelocityY(-b5.getVelocityY());
           }
       });
    }
    
    //int i only determines which ball's formulas are shown. All the balls' positions & momentums are shown at all times
    private void showValues(int i){
       // System.out.println(b1.getVelocity());
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
    
        lbImCalc2.setText("J = " + Math.round(b.getPf()*10.0)/10.0 + " - " + Math.round(b.getPi()*10.0)/10.0);
        lbImCalc3.setText("J = " + Math.round(b.calcImpulse()*10.0)/10.0 + "kgm/s");
        
        lbEkCalc2.setText("E = ½(" + Math.round(b.getMass()*10.0)/10.0 + ")(" + Math.round(b.getVelocity()*10.0)/10.0 + ")²");
        lbEkCalc3.setText("E = " + Math.round(b.calcEk()*10.0)/10.0 + " J");
                
    }
    
    //regardless of which ball is selected all their position & momentum will constantly be shown
    public void otherValues(){
        lbX1.setText(String.valueOf(Math.round((c1.getCenterX())*10.0)/10.0));//22 is correction factor for the coordinates of the center of the ball
        lbY1.setText(String.valueOf(Math.round((c1.getCenterY())*10.0)/10.0));
        lbX2.setText(String.valueOf(Math.round((c2.getCenterX())*10.0)/10.0));
        lbY2.setText(String.valueOf(Math.round((c2.getCenterY())*10.0)/10.0));
        lbX3.setText(String.valueOf(Math.round((c3.getCenterX())*10.0)/10.0));
        lbY3.setText(String.valueOf(Math.round((c3.getCenterY())*10.0)/10.0));
        lbX4.setText(String.valueOf(Math.round((c4.getCenterX())*10.0)/10.0));
        lbY4.setText(String.valueOf(Math.round((c4.getCenterY())*10.0)/10.0));
        lbX5.setText(String.valueOf(Math.round((c5.getCenterX())*10.0)/10.0));
        lbY5.setText(String.valueOf(Math.round((c5.getCenterY())*10.0)/10.0));

        lbv1.setText(String.valueOf(Math.round(((double) b1.getVelocity())*10.0)/10.0));
        lbv2.setText(String.valueOf(Math.round(((double) b2.getVelocity())*10.0)/10.0));
        lbv3.setText(String.valueOf(Math.round(((double) b3.getVelocity())*10.0)/10.0));
        lbv4.setText(String.valueOf(Math.round(((double) b4.getVelocity())*10.0)/10.0));
        lbv5.setText(String.valueOf(Math.round(((double) b5.getVelocity())*10.0)/10.0));
        
        lbVx1.setText(String.valueOf(Math.round(((double) b1.getVelocityX())*10.0)/10.0));
        lbVx2.setText(String.valueOf(Math.round(((double) b2.getVelocityX())*10.0)/10.0));
        lbVx3.setText(String.valueOf(Math.round(((double) b3.getVelocityX())*10.0)/10.0));
        lbVx4.setText(String.valueOf(Math.round(((double) b4.getVelocityX())*10.0)/10.0));
        lbVx5.setText(String.valueOf(Math.round(((double) b5.getVelocityX())*10.0)/10.0));
        
        lbVy1.setText(String.valueOf(Math.round(((double) b1.getVelocityY())*10.0)/10.0));
        lbVy2.setText(String.valueOf(Math.round(((double) b2.getVelocityY())*10.0)/10.0));
        lbVy3.setText(String.valueOf(Math.round(((double) b3.getVelocityY())*10.0)/10.0));
        lbVy4.setText(String.valueOf(Math.round(((double) b4.getVelocityY())*10.0)/10.0));
        lbVy5.setText(String.valueOf(Math.round(((double) b5.getVelocityY())*10.0)/10.0));
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
