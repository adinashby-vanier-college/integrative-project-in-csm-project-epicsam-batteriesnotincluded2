/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.controllers;

import static edu.vanier.template.controllers.MainAppFXMLController.user;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author theli
 */
public class NewUserFXMLController extends Stage{
    
    @FXML
    Pane root;
    
    @FXML
    Button btnCancel, btnConfirm;
    
    @FXML
    TextField txtUsername, txtPassword1, txtPassword2;
    
    @FXML
    Label lbWarning, lbWarning1, lbWarning2, lbWarning3;//lbWarning is passed from main page
    
    String username, password;
    
    boolean flag = true;//only true when username and password are all correct
    
    public NewUserFXMLController(Label lbWarn, String title) throws IOException{
       initModality(Modality.APPLICATION_MODAL);
       initStyle(StageStyle.UTILITY);
       setTitle(title);
       this.lbWarning = lbWarn;//to change label display on mainscreen after account has been created
       form();
    }
    
    @FXML
    private void form() throws IOException{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewUser_layout.fxml"));
        loader.setController(this);
        root = loader.load();

        Scene scene = new Scene(root);
        setScene(scene);
        setResizable(false);
        show();
        
        //path to the file that stores usernames and passwords
        File file = new File("src/main/resources/usernamespasswords/usernamespasswords.txt");

        lbWarning1.setVisible(false);
        lbWarning2.setVisible(false);
        lbWarning3.setVisible(false);
        
        btnCancel.setOnAction((event)->{close();});
        
        btnConfirm.setOnAction((event)->{
            
            username = txtUsername.getText().trim();
            password = txtPassword1.getText().trim();
        
           if(!txtPassword1.getText().equals(txtPassword2.getText())){
              lbWarning3.setVisible(true);//when passwords don't match when confirming inputted password
              lbWarning3.setText("Passwords don't match");
           }
           else if(username.contains(" ")){//can't include spaces in username
              lbWarning1.setVisible(true);
              lbWarning1.setText("Username includes empty spaces");
           }
           else if(password.contains(" ")){//can't include spaces in passwords
              lbWarning1.setVisible(true);
              lbWarning1.setText("Username includes empty spaces");
           }
           else if(username.length()<2){
              lbWarning1.setVisible(true);
              lbWarning1.setText("Username should be at least 3 characters");
           }
           else if(password.length()<2){
              lbWarning2.setVisible(true);
              lbWarning2.setText("Password should be at least 3 characters");
           }
           else{
            try (FileWriter writer = new FileWriter(file, true)) {
                        writer.write("\n" + username + " " + password);
                        writer.flush();
                    } catch (IOException ex) {
                    Logger.getLogger(NewUserFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            MainAppFXMLController.loggedIn = true;
            MainAppFXMLController.user = username;
            
            lbWarning.setText("Welcome, " + username);
            lbWarning.setStyle("-fx-text-fill: green;");
            lbWarning.setVisible(true);
            
            close();
           }
        });

    }
}
