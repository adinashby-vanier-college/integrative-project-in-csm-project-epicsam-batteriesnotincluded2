package edu.vanier.template.controllers;

import edu.vanier.template.ui.MainApp;
import java.io.IOException;
import java.util.logging.Level;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML controller class for the primary stage scene.
 *
 * @author frostybee
 */
public class MainAppFXMLController {

    private final static Logger logger = LoggerFactory.getLogger(MainAppFXMLController.class);

    @FXML
    Button btnMomentum;
    @FXML
    Button btnForces;
    @FXML
    Button btnEnergy;
    @FXML
    Button btnKinematics;
    @FXML
    Button btnLogin, btnNewUser, btnGuest;         
    @FXML
    Label lbWarning;
    
    String username;
    
    int ctr = 0;
    public static boolean loggedIn = false;//you need to be logged in to access the simulations
    public static String user;
    
    @FXML
    public void initialize() {
        logger.info("Initializing MainAppController...");
        lbWarning.setVisible(false);
        
        btnMomentum.setOnAction((event)->{
            ctr=1;
            loadSecondaryScene(event);           
        });
        
        btnForces.setOnAction((event)->{
            ctr=2;
            loadSecondaryScene(event);           
        });
        
        btnEnergy.setOnAction((event)->{
            ctr=3;
            loadSecondaryScene(event);           
        });
        
        btnKinematics.setOnAction((event)->{
            ctr=4;
            loadSecondaryScene(event);           
        });
        
        btnGuest.setOnAction((event)->{
           loggedIn = true;
           lbWarning.setText("Guest mode activated");
           lbWarning.setStyle("-fx-text-fill: green;");
           lbWarning.setVisible(true);
        });
        
        btnNewUser.setOnAction((event)->{
            try {
                NewUserFXMLController obj = new NewUserFXMLController(btnLogin, btnNewUser, btnGuest, lbWarning, "Create an account");
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(MainAppFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnLogin.setOnAction((event)->{
            try {
                LoginFXMLController obj = new LoginFXMLController(btnLogin, btnNewUser, btnGuest, lbWarning, "Log in to your account");
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(MainAppFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        addFontIcons();
    }

    private void loadSecondaryScene(Event e) {
        if(loggedIn == true){
         switch(ctr){
            case 1:MainApp.switchScene(MainApp.priStage, MainApp.MOMENTUM_SCENE);break;
            case 2:MainApp.switchScene(MainApp.priStage, MainApp.FORCES_SCENE);break;
            case 3:MainApp.switchScene(MainApp.priStage, MainApp.ENERGY_SCENE);break;
            case 4:MainApp.switchScene(MainApp.priStage, MainApp.KINEMATICS_SCENE);break;
            default:;
         }  
        }
        else{
           lbWarning.setText("Please log in first");
           lbWarning.setStyle("-fx-text-fill: red;");
           lbWarning.setVisible(true);
        }
        //logger.info("Loaded the secondary scene...");
    }

    private void addFontIcons() {
        // @see: https://kordamp.org/ikonli/cheat-sheet-fontawesome5.html
        FontIcon playIcon = new FontIcon(FontAwesomeRegular.SHARE_SQUARE);
        // Alternatively, Material Design icon set can be used as follows.
        // @see: https://kordamp.org/ikonli/#_materialdesign2_latest
        // @see: https://kordamp.org/ikonli/cheat-sheet-medicons.html
        //FontIcon searchIcon = new FontIcon(MaterialDesignA.ATOM);
        FontIcon switchIcon = new FontIcon(MaterialDesignA.ATOM);
       // btnSwitchScene.setGraphic(switchIcon);
        //btnSwitchScene.setStyle("-fx-font-size: 16px;");
    }
}
