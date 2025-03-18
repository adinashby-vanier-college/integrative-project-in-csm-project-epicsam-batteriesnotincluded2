/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.controllers;

import edu.vanier.template.ui.MainApp;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

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
    Line kinematics_line;

    @FXML
    QuadCurve kinematics_curve;

    @FXML
    TextField tf_11, tf_12, tf_13, tf_14, tf_21, tf_22, tf_23, tf_24;

    @FXML
    Slider slider_1, slider_2, slider_3, slider_4;

    @FXML
    Text txt_1, txt_2, txt_3, txt_4;


    @FXML
    public void initialize() {
        menuBarFunctionality();
        radioButtonToggle();
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

    private void radioButtonToggle() {

        //Toggling to 1D kinematics mode
        radio_kinematics.setOnAction(event -> {
            txt_1.setText("Initial position (m)");
            tf_21.setText("100");

            txt_2.setText("Acceleration (m/s^2)");

            txt_4.setVisible(false);
            tf_14.setVisible(false);
            tf_24.setVisible(false);
            slider_4.setVisible(false);

            kinematics_curve.setVisible(false);
            kinematics_line.setVisible(true);
        });

        //Toggling to projectile motion mode
        radio_projectile.setOnAction(event -> {

            txt_1.setText("Angle of launch (degrees)");
            tf_21.setText("89");

            txt_2.setText("Height of launch (m)");

            txt_4.setVisible(true);
            tf_14.setVisible(true);
            tf_24.setVisible(true);
            slider_4.setVisible(true);

            kinematics_curve.setVisible(true);
            kinematics_line.setVisible(false);
        });
    }
}
