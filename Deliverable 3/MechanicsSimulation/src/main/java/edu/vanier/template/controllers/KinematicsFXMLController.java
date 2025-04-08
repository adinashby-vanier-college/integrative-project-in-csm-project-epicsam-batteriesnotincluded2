/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.controllers;

import edu.vanier.physics.Kinematics;
import edu.vanier.template.ui.MainApp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import javafx.animation.PathTransition;
import javafx.scene.shape.Ellipse;

public class KinematicsFXMLController {

    @FXML
    MenuBar menuBar;

    @FXML
    Menu menusub_file, menusub_edit, menusub_help, menusub_back;

    @FXML
    MenuItem menuitem_about, menuitem_tips, menuitem_back, menuitem_exit;

    @FXML
    RadioButton radio_projectile, radio_kinematics;

    @FXML
    Line kinematics_line, y_axis, x_axis;

    @FXML
    QuadCurve kinematics_curve;

    @FXML
    TextField tf_11, tf_12, tf_13, tf_14, tf_21, tf_22, tf_23, tf_24;

    @FXML
    Slider slider_1, slider_2, slider_3, slider_4;

    @FXML
    Text txt_1, txt_2, txt_3, txt_4, txt_y, txt_x;

    @FXML
    TextArea ta_results;

    @FXML
    Button btn_play, btn_clear;

    @FXML
    HBox lastHBox;

    @FXML
    Ellipse particle;


    @FXML
    public void initialize() {
        menuBarFunctionality();
        radioButtonToggle();

        tf_21.textProperty().bind(slider_1.valueProperty().asString("%.0f"));
        tf_22.textProperty().bind(slider_2.valueProperty().asString("%.0f"));
        tf_23.textProperty().bind(slider_3.valueProperty().asString("%.0f"));
        tf_24.textProperty().bind(slider_4.valueProperty().asString("%.0f"));
    }

    private void menuBarFunctionality() {
        menuitem_back.setOnAction(this::loadPrimaryScene);

        menuitem_exit.setOnAction((event) -> {
            Platform.exit();
        });
    }

    private void loadPrimaryScene(Event e) {
        MainApp.switchScene(MainApp.priStage, MainApp.MAINAPP_SCENE);
    }

    boolean isProjectile = true; //Checks if it is in projectile mode or not (set to true)
    private void radioButtonToggle() {

        //Toggling to 1D kinematics mode
        radio_kinematics.setOnAction(event -> {
            txt_1.setText("Initial position (m)");
            slider_1.setMax(100);

            txt_2.setText("Acceleration (m/s^2)");
            txt_4.setText("Total time (s)");

            kinematics_curve.setVisible(false);
            y_axis.setVisible(false);
            x_axis.setVisible(false);

            txt_y.setVisible(false);
            txt_x.setVisible(false);

            kinematics_line.setVisible(true);

            slider_1.setValue(1);
            slider_2.setValue(1);
            slider_3.setValue(1);
            slider_4.setValue(1);

            isProjectile = false; //Set to not in projectile mode
        });

        //Toggling to projectile motion mode
        radio_projectile.setOnAction(event -> {

            txt_1.setText("Angle of launch (degrees)");
            slider_1.setMax(89);

            txt_2.setText("Height of launch (m)");
            txt_4.setText("Gravitational acceleration (m/s^2)");

            txt_4.setVisible(true);
            tf_14.setVisible(true);
            tf_24.setVisible(true);
            slider_4.setVisible(true);
            lastHBox.setBackground(Background.fill(Paint.valueOf("#b7ddee")));

            txt_y.setVisible(true);
            txt_x.setVisible(true);

            kinematics_curve.setVisible(true);
            y_axis.setVisible(true);
            x_axis.setVisible(true);

            kinematics_line.setVisible(false);

            slider_1.setValue(1);
            slider_2.setValue(1);
            slider_3.setValue(1);
            slider_4.setValue(1);


            isProjectile = true; //Set to in projectile mode
        });
    }

    @FXML
    void clearOnAction(ActionEvent event) {
        slider_1.setValue(1);
        slider_2.setValue(1);
        slider_3.setValue(1);
        slider_4.setValue(1);
    }

    @FXML
    void playOnAction(ActionEvent event) {
        if(isProjectile) {
            double angle = Double.parseDouble(tf_21.getText());
            double height = Double.parseDouble(tf_22.getText());
            double initialVelocity = Double.parseDouble(tf_23.getText());
            double gravAcceleration = Double.parseDouble(tf_24.getText());

            //Object for projectile motion
            Kinematics projectile = new Kinematics(angle, gravAcceleration, height, initialVelocity, 0,0);

//            kinematics_curve.setEndX(projectile.proj_calcDistance() + y_axis.getEndX());
//            kinematics_curve.setStartY(y_axis.getEndY() - projectile.getLaunchHeight());
//            kinematics_curve.setControlX((kinematics_curve.getEndX()+kinematics_curve.getStartX())/2);
//            kinematics_curve.setControlY(y_axis.getEndY()-projectile.proj_calcMaxHeight()-projectile.getLaunchHeight());

            kinematics_curve.setEndX(projectile.proj_calcDistance()+y_axis.getLayoutX());
            kinematics_curve.setStartY(y_axis.getEndY()-projectile.getLaunchHeight());
            kinematics_curve.setControlX((kinematics_curve.getEndX()+kinematics_curve.getStartX())/2);
            kinematics_curve.setControlY(y_axis.getEndY()-projectile.proj_calcMaxHeight());

            if(kinematics_curve.getEndX() > 500) {
                x_axis.setEndX(kinematics_curve.getEndX());
            }

            PathTransition transRights = new PathTransition(Duration.seconds(projectile.proj_calcTime()/5), kinematics_curve);
            transRights.setNode(particle);
            transRights.setCycleCount(1);
            transRights.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            transRights.play();

            ta_results.setText("The object traveled a total distance of " + projectile.kinematic_calcTotalDistance() + " metres in " + projectile.proj_calcTime() + " seconds. The max height the object reached is " + projectile.proj_calcMaxHeight() + " seconds");

        } else {
            double initialPosition = Double.parseDouble(tf_21.getText());
            double acceleration = Double.parseDouble(tf_22.getText());
            double initialVelocity = Double.parseDouble(tf_23.getText());
            double time = Double.parseDouble(tf_24.getText());

            Kinematics oneDimension = new Kinematics(0, acceleration, 0, initialVelocity, initialPosition, time);

        }
    }
}
