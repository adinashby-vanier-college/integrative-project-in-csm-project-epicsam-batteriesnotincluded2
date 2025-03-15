package edu.vanier.template.ui;

import edu.vanier.template.controllers.EnergyFXMLController;
import edu.vanier.template.controllers.ForcesFXMLController;
import edu.vanier.template.controllers.KinematicsFXMLController;
import edu.vanier.template.controllers.MainAppFXMLController;
import edu.vanier.template.controllers.MomentumFXMLController;
import edu.vanier.template.controllers.SceneController;
import edu.vanier.template.helpers.FxUIHelper;
import java.io.IOException;
import java.util.logging.Level;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a JavaFX project template to be used for creating GUI applications.
 *
 * The JavaFX GUI framework (version: 22.0.2) is linked to this project in the
 * build.gradle file.
 * @link: https://openjfx.io/javadoc/22/
 * @see: /Build Scripts/build.gradle
 * @author frostybee.
 */
public class MainApp extends Application {

    // The FXML file name of the primary scene.
    public static final String MAINAPP_SCENE = "MainApp_layout";
    // The FXML file name of the secondary scene.
    public static final String SECONDARY_SCENE = "secondary_layout";
    public static final String MOMENTUM_SCENE = "Momentum_layout";
    public static final String FORCES_SCENE = "Forces_layout";
    public static final String ENERGY_SCENE = "Energy_layout";
    public static final String KINEMATICS_SCENE = "Kinematics_layout";

    public static Stage priStage;
    
    private final static Logger logger = LoggerFactory.getLogger(MainApp.class);
    private static Scene scene;
    private static SceneController sceneController;

    @Override
    public void stop() {
        // TODO: 
        // Here, we need to perform teardown operations such as stopping running 
        // animation, etc.
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            this.priStage = primaryStage;
            logger.info("Bootstrapping the application...");
            // Load the scene of the primary stage.
            Parent root = FxUIHelper.loadFXML(MAINAPP_SCENE, new MainAppFXMLController());
            scene = new Scene(root, 1200, 800);
            // Add the primary scene to the scene-switching controller.
            sceneController = new SceneController(scene);
            sceneController.addScene(MAINAPP_SCENE, root);
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();
            primaryStage.setTitle("Mechanics Simulation");
            // Request putting this appliation's main window on top of other 
            // already-opened windows upon launching the app.
            primaryStage.setAlwaysOnTop(true);
            primaryStage.show();
            primaryStage.setAlwaysOnTop(false);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
            java.util.logging.Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Switches between scenes based on the provided FXML file name. This method
     * checks the type of scene (primary or secondary) and either activates an
     * existing scene or loads the specified FXML scene for the first time and
     * adds it to the scene controller.
     *
     * @param primaryStage
     * @param fxmlFileName the name of the FXML file that represents the scene
     * to switch to.
     */
    public static void switchScene(Stage primaryStage, String fxmlFileName) {
        try {

            switch(fxmlFileName) {
                case MAINAPP_SCENE:
                    // No need to register the primary scene as it
                    // was already done in the start method.
                    sceneController.activateScene(fxmlFileName);
                    break;

                case MOMENTUM_SCENE:
                    if (!sceneController.sceneExists(fxmlFileName)) {
                        MomentumFXMLController controller = new MomentumFXMLController();
                        Parent root = FxUIHelper.loadFXML(fxmlFileName, controller);
                        controller.setStage(primaryStage);
                        sceneController.addScene(MOMENTUM_SCENE, root);
                    }
                    sceneController.activateScene(fxmlFileName);
                    break;

                case FORCES_SCENE:
                    if (!sceneController.sceneExists(fxmlFileName)) {
                        ForcesFXMLController controller = new ForcesFXMLController();
                        Parent root = FxUIHelper.loadFXML(fxmlFileName, controller);
                        sceneController.addScene(FORCES_SCENE, root);
                    }
                    sceneController.activateScene(fxmlFileName);
                    break;

                case ENERGY_SCENE:
                    if (!sceneController.sceneExists(fxmlFileName)) {
                        EnergyFXMLController controller = new EnergyFXMLController();
                        Parent root = FxUIHelper.loadFXML(fxmlFileName, controller);
                        sceneController.addScene(ENERGY_SCENE, root);
                    }
                    sceneController.activateScene(fxmlFileName);
                    break;

                case KINEMATICS_SCENE:
                    if (!sceneController.sceneExists(fxmlFileName)) {
                        KinematicsFXMLController controller = new KinematicsFXMLController();
                        Parent root = FxUIHelper.loadFXML(fxmlFileName, controller);
                        sceneController.addScene(KINEMATICS_SCENE, root);
                    }
                    sceneController.activateScene(fxmlFileName);
                    break;
                    //test
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
