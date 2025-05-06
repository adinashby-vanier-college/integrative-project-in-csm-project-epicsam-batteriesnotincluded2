/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.controllers;

import edu.vanier.template.ui.MainApp;
import java.io.IOException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.util.Duration;
import javafx.scene.chart.PieChart;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class EnergyFXMLController {

    @FXML private Label timerLabel;
    @FXML private Label frictionLabel;
    @FXML private Label gravityLabel;
    @FXML private Label massLabel;
    @FXML private Label speedLabel;
    @FXML private Slider frictionSlider;
    @FXML private Slider gravitySlider;
    @FXML private Slider massSlider;
    @FXML private PieChart energyPieChart;
    @FXML private Pane simulationPane;
    @FXML private Group skateboarder;
    @FXML private Circle movementHandle;
    @FXML private TextField pathFunctionField;
    @FXML private Label functionErrorLabel;
    private Polyline functionPath;

    @FXML
    MenuItem mitBack;
    
    private Timeline timeline;
    private int milliseconds = 0;
    private boolean draggingSkater = false;
    private boolean playing = false;
    
    private double lastX = -1;
    private double lastY = -1;

    @FXML
    private void initialize() {
        mitBack.setOnAction(this::loadPrimaryScene);
        setupPieChart();
        setupSliderKeyEvents(frictionSlider, frictionLabel, "%.1f N");
        setupSliderKeyEvents(gravitySlider, gravityLabel, "%.1f m/s²");
        setupSliderKeyEvents(massSlider, massLabel, "%.1f kg");

        functionPath = new Polyline();
        functionPath.setStroke(Color.BLACK);
        functionPath.setStrokeWidth(2);
        simulationPane.getChildren().add(functionPath);
        setupFunctionInput();

        resetScene();
    }

    private void loadPrimaryScene(Event e) {
        MainApp.switchScene(MainApp.priStage, MainApp.MAINAPP_SCENE);
    }
    
    @FXML
    public void resetScene() {
        resetTimer();
        resetSpeed();
        speedLabel.setText("0.0 m/s");
        frictionSlider.setValue(25);
        gravitySlider.setValue(9.8);
        massSlider.setValue(50);
        updateFriction();
        updateGravity();
        updateMass();
        energyPieChart.getData().clear();
        setupPieChart();
        updatePlatformFromFunction("x");
        skateboarder.setLayoutX(0);
        skateboarder.setLayoutY(0);
        playing = false;
    }
    
   /* @FXML
    public void goBack() {
        handleBack(new ActionEvent());
    }*/
    
    @FXML
    public void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainApp_layout.fxml"));
            Parent root = loader.load();

            // Get stage from MenuItem OR Button
            Object source = event.getSource();
            Stage stage;
            if (source instanceof MenuItem) {
                MenuItem menuItem = (MenuItem) source;
                stage = (Stage) menuItem.getParentPopup().getOwnerWindow();
            } else {
                stage = (Stage) ((Node) source).getScene().getWindow();
            }

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSkaterPress(MouseEvent event) {
        draggingSkater = true;
        stopTimer();
        resetSpeed();
        playing = false;
        timeline = null;
    }

    @FXML
    private void handleSkaterDrag(MouseEvent event) {
        if (draggingSkater) {
            double x = event.getX();
            double y = event.getY();
            skateboarder.setLayoutX(x - movementHandle.getCenterX());
            skateboarder.setLayoutY(y - movementHandle.getCenterY());
        }
    }

    @FXML
    private void handleSkaterRelease(MouseEvent event) {
        draggingSkater = false;
    }

    @FXML
    private void startTimer() {
        if (!playing) {
            playing = true;
            if (timeline == null || timeline.getStatus() != Animation.Status.RUNNING) {
                timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
                    if (!draggingSkater && playing) {
                        milliseconds += 10;
                        timerLabel.setText(formatTime(milliseconds));
                        updateSpeed();
                        updateEnergyChart();
                    }
                }));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();
            } else {
                timeline.play();
            }
        }
    }

    @FXML
    private void stopTimer() {
        playing = false;
        if (timeline != null) {
            timeline.stop();
        }
    }

    @FXML
    private void resetTimer() {
        stopTimer();
        milliseconds = 0;
        timerLabel.setText("00:00:000");
    }

    public void resetSpeed() {
        speedLabel.setText("0.0 m/s");
    }

    private String formatTime(int totalMillis) {
        int minutes = (totalMillis / 1000) / 60;
        int seconds = (totalMillis / 1000) % 60;
        int millis = totalMillis % 1000;
        return String.format("%02d:%02d:%03d", minutes, seconds, millis);
    }

    private void setupSliderKeyEvents(Slider slider, Label label, String format) {
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            label.setText(String.format(format, newVal.doubleValue()));
        });

        slider.setOnKeyPressed(event -> {
            double value = slider.getValue();
            if (event.getCode() == KeyCode.RIGHT && value < slider.getMax()) {
                slider.setValue(value - 9);
            } else if (event.getCode() == KeyCode.LEFT && value > slider.getMin()) {
                slider.setValue(value + 9);
            }
        });
    }
    
    @FXML
    private void updateSpeed(){
        double x = skateboarder.getLayoutX();
        double y = skateboarder.getLayoutY();

        if (lastX != -1 && lastY != -1) {
            double dx = x - lastX;
            double dy = y - lastY;
            double distance = Math.sqrt(dx * dx + dy * dy);
            double speed = distance / 0.01; // since 10ms interval
            speedLabel.setText(String.format("%.2f m/s", speed));
        }

        lastX = x;
        lastY = y;
    }

    @FXML
    private void updateFriction() {
        frictionLabel.setText(String.format("%.1f N", frictionSlider.getValue()));
    }

    @FXML
    private void updateGravity() {
        gravityLabel.setText(String.format("%.1f m/s²", gravitySlider.getValue()));
    }

    @FXML
    private void updateMass() {
        massLabel.setText(String.format("%.1f kg", massSlider.getValue()));
    }

    private void setupPieChart() {
        PieChart.Data potentialEnergy = new PieChart.Data("Potential Energy", 50);
        PieChart.Data kineticEnergy = new PieChart.Data("Kinetic Energy", 40);
        PieChart.Data frictionEnergy = new PieChart.Data("Friction Energy", 10);

        energyPieChart.getData().addAll(potentialEnergy, kineticEnergy, frictionEnergy);
        energyPieChart.setLabelsVisible(false);
         
        potentialEnergy.getNode().setStyle("-fx-pie-color: blue;");
        kineticEnergy.getNode().setStyle("-fx-pie-color: green;");
        frictionEnergy.getNode().setStyle("-fx-pie-color: red;");
        energyPieChart.setLegendVisible(false);
    }

    private void updateEnergyChart() {
        if (!energyPieChart.getData().isEmpty()) {
            energyPieChart.getData().get(0).setPieValue(50);
            energyPieChart.getData().get(1).setPieValue(40);
            energyPieChart.getData().get(2).setPieValue(10);
        }
    }

    @FXML
    private void setupFunctionInput() {
        pathFunctionField.setOnAction(event -> {
            String input = pathFunctionField.getText().trim();

            if (isValidFunction(input)) {
                pathFunctionField.setStyle(""); // reset style
                updatePlatformFromFunction(input);
            } else {
                pathFunctionField.setStyle("-fx-border-color: red;");
                System.out.println("Invalid function: only one variable 'x' is allowed.");
            }
        });
    }
    
    private boolean isValidFunction(String fx) {
        // Must contain only variable 'x' and valid math characters
        if (fx.isBlank() || !fx.contains("x")) return false;

        // Check that there are no other letters
        return fx.matches("[\\d+\\-*/().x ]+");
    }

    public void updatePlatformFromFunction(String fx) {
        functionPath.getPoints().clear();
        try {
            for (int x = 0; x <= 600; x += 5) {
                double y = evaluateFunction(fx, x / 20.0);
                if (y > 500) y = 500;
                functionPath.getPoints().addAll((double) x, 300 - y);
            }
        } catch (Exception e) {
            System.out.println("Invalid function: " + fx);
        }
    }

    private double evaluateFunction(String fx, double x) {
        fx = fx.replace("x", String.valueOf(x));
        return eval(fx);
    }

    private double eval(String expression) {
        try {
            return new javax.script.ScriptEngineManager()
                    .getEngineByName("JavaScript")
                    .eval(expression) instanceof Number num
                    ? ((Number) num).doubleValue() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public void onFunctionChanged(String fx) {
        updatePlatformFromFunction(fx);
    }
    
    @FXML
    private void handleFunctionEnter() {
        String input = pathFunctionField.getText().trim();

        if (input.isEmpty() || !input.contains("x") || input.matches(".*[^0-9xX*+\\-/. ()].*")) {
            pathFunctionField.setStyle("-fx-border-color: red;");
            functionErrorLabel.setText("Invalid function. Use only 'x' as variable.");
            functionErrorLabel.setVisible(true);
        } else {
            try {
                // Try evaluating a test value
                eval(input.replace("x", "1.0"));
                functionErrorLabel.setVisible(false);
                pathFunctionField.setStyle(""); // Reset border
                updatePlatformFromFunction(input);
            } catch (Exception e) {
                pathFunctionField.setStyle("-fx-border-color: red;");
                functionErrorLabel.setText("Could not evaluate the function.");
                functionErrorLabel.setVisible(true);
            }
        }
    }

    @FXML
    private void exitProgram(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    private void delete() {
        resetScene();
    }
    
   /* @FXML
    private void backOneStep(ActionEvent event) {
        goBack(); 
    }*/
}