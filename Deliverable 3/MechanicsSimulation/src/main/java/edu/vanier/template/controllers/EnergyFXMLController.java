/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.controllers;

import edu.vanier.template.ui.MainApp;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import javafx.event.Event;
import javafx.scene.chart.PieChart;
import javafx.scene.Node;

public class EnergyFXMLController {

    // UI Components
    @FXML private Label timerLabel;
    @FXML private Label frictionLabel;
    @FXML private Label gravityLabel;
    @FXML private Label massLabel;
    @FXML private Slider frictionSlider;
    @FXML private Slider gravitySlider;
    @FXML private Slider massSlider;
    @FXML private PieChart energyPieChart;

    // Timer Variables
    private int seconds = 0;
    private Timeline timeline;

    @FXML
    private void initialize() {
        loadPrimaryScene();
        setupPieChart();
    }

    /**
     * Loads the primary scene when the application starts.
     */
    private void loadPrimaryScene() {
        MainApp.switchScene(MainApp.priStage, MainApp.MAINAPP_SCENE);
    }
    
    @FXML
    private void loadEnergyScene(ActionEvent event) {
        MainApp.switchScene((Stage) ((Node) event.getSource()).getScene().getWindow(), MainApp.ENERGY_SCENE);
    }

    /**
     * Handles navigation back to the main menu.
     */
    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        MainApp.switchScene((Stage) ((Node) event.getSource()).getScene().getWindow(), MainApp.MAINAPP_SCENE);
    }

    /**
     * Closes the program.
     */
    @FXML
    private void exitProgram(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Starts the chronometer.
     */
    @FXML
    public void startTimer() {
        if (timeline == null || timeline.getStatus() != Animation.Status.RUNNING) {
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                seconds++;
                timerLabel.setText(formatTime(seconds));
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }

    /**
     * Stops the chronometer.
     */
    @FXML
    public void stopTimer() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    /**
     * Resets the chronometer.
     */
    @FXML
    public void resetTimer() {
        stopTimer();
        seconds = 0;
        timerLabel.setText("00:00:00");
    }

    /**
     * Formats the timer in HH:MM:SS.
     */
    private String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int secs = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    /**
     * Updates Friction slider label dynamically.
     */
    @FXML
    private void updateFriction() {
        frictionLabel.setText(String.format("%.1f N", frictionSlider.getValue()));
    }

    /**
     * Updates Gravity slider label dynamically.
     */
    @FXML
    private void updateGravity() {
        gravityLabel.setText(String.format("%.1f m/s²", gravitySlider.getValue()));
    }

    /**
     * Updates Mass slider label dynamically.
     */
    @FXML
    private void updateMass() {
        massLabel.setText(String.format("%.1f kg", massSlider.getValue()));
    }

    /**
     * Sets up the PieChart with energy data.
     */
    private void setupPieChart() {
        PieChart.Data potentialEnergy = new PieChart.Data("Potential Energy", 30);
        PieChart.Data kineticEnergy = new PieChart.Data("Kinetic Energy", 30);
        PieChart.Data frictionEnergy = new PieChart.Data("Friction Energy", 10);

        energyPieChart.getData().addAll(potentialEnergy, kineticEnergy, frictionEnergy);
        applyPieChartColors();
    }

    /**
     * Applies custom colors to the PieChart.
     */
    private void applyPieChartColors() {
        for (PieChart.Data data : energyPieChart.getData()) {
            String color = switch (data.getName()) {
                case "Potential Energy" -> "blue";
                case "Kinetic Energy" -> "green";
                case "Friction Energy" -> "red";
                default -> "gray";
            };
            Node node = data.getNode();
            if (node != null) {
                node.setStyle("-fx-pie-color: " + color + ";");
            }
        }
    }
}
