/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.controllers;

import edu.vanier.template.ui.MainApp;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

/**
 *
 * @author ChiliWasp
 */
public class ForcesFXMLController {
    @FXML
    Button btnBack;
    
    @FXML
    
    MenuBar menuBar;
    
    @FXML
    Menu menuHelp;
    
    @FXML
    MenuItem mitBack;
    
    @FXML
    PieChart pieForceDistribution;
    
    @FXML
    public void initialize() {
        mitBack.setOnAction(this::loadPrimaryScene);
        btnBack.setVisible(false);
        btnBack.setOnAction(Event -> {btnBack.setText("GO TO HELP!");
        });
    }

    private void loadPrimaryScene(Event e) {
        MainApp.switchScene(MainApp.priStage, MainApp.MAINAPP_SCENE);
    }
}
