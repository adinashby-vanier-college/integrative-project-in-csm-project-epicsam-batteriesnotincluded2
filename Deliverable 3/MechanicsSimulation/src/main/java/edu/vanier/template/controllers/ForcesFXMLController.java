/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.controllers;

import edu.vanier.template.ui.MainApp;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

/**
 *
 * @author theli
 */
public class ForcesFXMLController {
    @FXML
    Button btnBack;
    
    @FXML
    MenuItem mitBack;
    
    @FXML
    public void initialize() {
        mitBack.setOnAction(this::loadPrimaryScene);
        btnBack.setOnAction(Event -> {btnBack.setText("GO TO HELP!");});
    }

    private void loadPrimaryScene(Event e) {
        MainApp.switchScene(MainApp.MAINAPP_SCENE);
    }
}
