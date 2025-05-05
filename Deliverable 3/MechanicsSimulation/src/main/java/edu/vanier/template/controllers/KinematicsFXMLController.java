package edu.vanier.template.controllers;

import edu.vanier.physics.Kinematics;
import edu.vanier.template.ui.MainApp;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

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
    Rectangle simScene;

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
    LineChart <Number, Number> lg_position, lg_velocity, lg_acceleration;


    @FXML
    public void initialize() {
        menuBarFunctionality();
        radioButtonToggle();

        tf_21.textProperty().bind(slider_1.valueProperty().asString("%.0f"));
        tf_22.textProperty().bind(slider_2.valueProperty().asString("%.0f"));
        tf_23.textProperty().bind(slider_3.valueProperty().asString("%.0f"));
        tf_24.textProperty().bind(slider_4.valueProperty().asString("%.0f"));

        lg_position.getData().add(new XYChart.Series<>());
        lg_velocity.getData().add(new XYChart.Series<>());
        lg_acceleration.getData().add(new XYChart.Series<>());
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
            particle.setLayoutX(88);
            particle.setLayoutY(477);
            particle.setTranslateX(0);
            particle.setTranslateY(0);
            particle.setCenterX(0);
            particle.setCenterY(0);

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
            particle.setLayoutX(88);
            particle.setLayoutY(477);
            particle.setTranslateX(0);
            particle.setTranslateY(0);
            particle.setCenterX(0);
            particle.setCenterY(0);

            kinematics_curve.setLayoutX(139);
            kinematics_curve.setLayoutY(478);
            kinematics_curve.setStartX(-76);
            kinematics_curve.setStartY(27);
            kinematics_curve.setEndX(271);
            kinematics_curve.setEndY(25);
            kinematics_curve.setControlX(92);
            kinematics_curve.setControlY(-248);

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
            slider_2.setValue(0);
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

        //Clearing chart data
        lg_position.getData().forEach(series -> series.getData().clear());
        lg_velocity.getData().forEach(series -> series.getData().clear());
        lg_acceleration.getData().forEach(series -> series.getData().clear());

        if(isProjectile) {
            particle.setLayoutX(88);
            particle.setLayoutY(477);
            particle.setTranslateX(0);
            particle.setTranslateY(0);
            particle.setCenterX(0);
            particle.setCenterY(0);

            kinematics_curve.setLayoutX(139);
            kinematics_curve.setLayoutY(478);
            kinematics_curve.setStartX(-76);
            kinematics_curve.setStartY(27);
            kinematics_curve.setEndX(271);
            kinematics_curve.setEndY(25);
            kinematics_curve.setControlX(92);
            kinematics_curve.setControlY(-248);
        } else {
            particle.setLayoutX(88);
            particle.setLayoutY(477);
            particle.setTranslateX(0);
            particle.setTranslateY(0);
            particle.setCenterX(0);
            particle.setCenterY(0);
        }
    }

    private double forceCoordinateRange(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    @FXML
    void playOnAction(ActionEvent event) {

        //Clearing previous chart data
        lg_position.getData().forEach(series -> series.getData().clear());
        lg_velocity.getData().forEach(series -> series.getData().clear());
        lg_acceleration.getData().forEach(series -> series.getData().clear());

        if(isProjectile) {
            double angle = slider_1.getValue();
            double height = slider_2.getValue();
            double initialVelocity = slider_3.getValue();
            double gravAcceleration = slider_4.getValue();

            Kinematics projectile = new Kinematics(angle, gravAcceleration, height, initialVelocity, 0, 0);
            double totalTime = projectile.proj_calcTime();
            double totalDistance = projectile.proj_calcDistance();
            double maxHeight = projectile.proj_calcMaxHeight();

            //Getting sizes of x and y axis and border so the ball doesnt fly off the rectangle
            double originX = y_axis.getLayoutX()+y_axis.getStartX();
            double originY = y_axis.getLayoutY() + y_axis.getStartY();

            double sceneLeft = simScene.getLayoutX();
            double sceneTop = simScene.getLayoutY();
            double sceneWidth = simScene.getWidth();
            double sceneHeight = simScene.getHeight();
            double availableSceneX = sceneLeft + sceneWidth - originX - 10; //10 is the margin
            double availableSceneY = originY - (sceneTop + 10);

            double scale = Math.min(availableSceneX/totalDistance, availableSceneY/(height+maxHeight));

            double relative_curveStartY = originY-height*scale;
            double relative_curveControlX = originX + (totalDistance/2)*scale;
            double relative_curveControlY = originY - (height+maxHeight)*scale;
            double relative_curveEndX = originX+totalDistance*scale;

            //Setting up the curve whenever the user changes the values
            kinematics_curve.getTransforms().clear();
            kinematics_curve.setLayoutX(0);
            kinematics_curve.setLayoutY(0);
            kinematics_curve.setStartX(originX);
            kinematics_curve.setStartY(relative_curveStartY);
            kinematics_curve.setControlX(relative_curveControlX);
            kinematics_curve.setControlY(relative_curveControlY);
            kinematics_curve.setEndX(relative_curveEndX);
            kinematics_curve.setEndY(originY);
            kinematics_curve.setStrokeWidth(2);

            //Setting up a path for the ball to go through
            Path path = new Path(
                    new MoveTo(originX, relative_curveStartY),
                    new QuadCurveTo(relative_curveControlX, relative_curveControlY, relative_curveEndX, originY)
            );

            particle.toFront();
            particle.getTransforms().clear();
            particle.setTranslateX(0);
            particle.setTranslateY(0);
            particle.setLayoutX(0);
            particle.setLayoutY(0);
            particle.setCenterX(originX);
            particle.setCenterY(relative_curveStartY);

            PathTransition transRights = new PathTransition(Duration.seconds(totalTime), path, particle);
            transRights.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            transRights.setCycleCount(1);
            transRights.play();

            ta_results.setText("The object traveled a total distance of " + String.format("%.2f", projectile.proj_calcDistance()) + " m\nTotal time: " + String.format("%.2f", projectile.proj_calcTime()) + " s.\nThe max height the object reached is " + String.format("%.2f", projectile.proj_calcMaxHeight()) + " m");


            //Adding points for projectile line chart
            XYChart.Series<Number, Number> positionSeries = new XYChart.Series<>();
            XYChart.Series<Number, Number> velocitySeries = new XYChart.Series<>();
            XYChart.Series<Number, Number> accelerationSeries = new XYChart.Series<>();

            for(double i = 0; i <= totalTime; i+=totalTime/100.0) { //Make 100 points
                double x = projectile.proj_calcHorizontalPosition(i); //x-component of position
                double y = projectile.proj_calcVerticalPosition(i); //y-component of position
                double vx = projectile.proj_calHorizontalVelocity(); //x-component of velocity
                double vy = projectile.proj_calcVerticalVelocity(i); //y-component of velocity
                double ax = 0; //x-component of acceleration (zero bc there is no horizontal acceleration in projectile motion)
                double ay = projectile.getAcceleration(); //y-component of acceleration

                positionSeries.getData().add(new XYChart.Data<>(i, Math.sqrt(x*x + y*y)));
                velocitySeries.getData().add(new XYChart.Data<>(i, Math.sqrt(vx*vx + vy*vy)));
                accelerationSeries.getData().add(new XYChart.Data<>(i, Math.sqrt(ax*ax + ay*ay)));
            }

            lg_position.getData().add(positionSeries);
            lg_velocity.getData().add(velocitySeries);
            lg_acceleration.getData().add(accelerationSeries);

        } else {
            double initialPosition = Double.parseDouble(tf_21.getText());
            double acceleration = Double.parseDouble(tf_22.getText());
            double initialVelocity = Double.parseDouble(tf_23.getText());
            double time = Double.parseDouble(tf_24.getText());

            Kinematics oneDimension = new Kinematics(0, acceleration, 0, initialVelocity, initialPosition, time);

            // 1D motion animation setup
            double finalPosition = oneDimension.kinematic_calcFinalPosition();

            double scale = 10;

            double startX = kinematics_line.getEndX() - initialPosition * scale;
            double endX = kinematics_line.getEndX() - finalPosition * scale;

            startX = Math.max(0, Math.min(800, startX));
            endX = Math.max(0, Math.min(800, endX));

            particle.setCenterX(startX);
            particle.setCenterY(kinematics_line.getStartY() - 10); // Y just above the line

            Path path = new Path();
            path.getElements().add(new MoveTo(startX, particle.getCenterY()));
            path.getElements().add(new LineTo(endX, particle.getCenterY()));

            PathTransition transition = new PathTransition(Duration.seconds(time), path, particle);
            transition.setCycleCount(1);
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();

            ta_results.setText(
                    "Final position: " + String.format("%.2f", finalPosition) + " m\n" +
                            "Total distance: " + String.format("%.2f", oneDimension.kinematic_calcTotalDistance()) + " m\n" +
                            "Final velocity: " + String.format("%.2f", oneDimension.kinematic_calcFinalVelocity()) + " m/s"
            );

            XYChart.Series<Number, Number> positionSeries = new XYChart.Series<>();
            XYChart.Series<Number, Number> velocitySeries = new XYChart.Series<>();
            XYChart.Series<Number, Number> accelerationSeries = new XYChart.Series<>();

            for(double i = 0; i <= time; i += time/100.0) { //Make 100 points
                double x = oneDimension.kinematic_calcWhatPosition(i); //Position of ball
                double v = oneDimension.kinematic_calcWhatVelocity(i); //Velocity of ball
                double a = oneDimension.getAcceleration(); //Acceleration of ball

                positionSeries.getData().add(new XYChart.Data<>(i, x));
                velocitySeries.getData().add(new XYChart.Data<>(i, v));
                accelerationSeries.getData().add(new XYChart.Data<>(i, a));
            }
        }
    }
}
