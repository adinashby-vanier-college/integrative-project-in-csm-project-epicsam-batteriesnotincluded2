/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.controllers;

import edu.vanier.template.ui.MainApp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author theli
 */
public class LoginFXMLController extends Stage{
    
    @FXML
    Pane root;
    
    @FXML
    PasswordField psPassword;
    
    @FXML
    TextField txtPassword, txtUsername;
    
    @FXML
    Label lbWarning1, lbWarning2;
    
    @FXML
    Button btnCancel, btnConfirm, btnLogin, btnNewUser, btnGuest, btnLogout;
    
    @FXML
    CheckBox cbShow;        
            
    @FXML
    VBox vbMain;
            
    String username, password;
    
    Label lbWarning;
    
    LinkedHashMap<String, String> users = new LinkedHashMap<>();
    
    String path = "/usernamespasswords/usernamespasswords.txt";
    
    boolean userFlag = false;//only true when username is valid
    boolean passwordFlag = false;//only true when password is valid
    
    public LoginFXMLController(Button Login, Button NewUser, Button Guest, Button Logout, Label lbWarn, String title) throws IOException{
       initModality(Modality.APPLICATION_MODAL);
       initStyle(StageStyle.UTILITY);
       setTitle(title);
       this.lbWarning = lbWarn;//to change label display on mainscreen after account has been created
       this.btnLogin = Login;
       this.btnGuest = Guest;
       this.btnNewUser = NewUser;
       this.btnLogout = Logout;
       form();
       CheckBoxes();
    }
    
    @FXML
    private void form() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login_layout.fxml"));
        loader.setController(this);
        root = loader.load();

        Scene scene = new Scene(root);
        setScene(scene);
        setResizable(false);
        show();
        
        lbWarning1.setVisible(false);
        lbWarning2.setVisible(false);
        
        txtPassword.textProperty().bindBidirectional(psPassword.textProperty());//syncing the fields

        loadUsers();

        btnCancel.setOnAction((event)->{close();});
        
         btnConfirm.setOnAction((event)->{  
             
            userFlag = false;
            passwordFlag = false;
            username = txtUsername.getText().trim();
            password = psPassword.getText().trim();
            
            for(Map.Entry<String, String> val : users.entrySet()){
               if(username.equals(val.getKey())){
                 userFlag = true;
                 lbWarning1.setVisible(false);
                 
                 if(password.equals(val.getValue())){
                 passwordFlag = true;
                 lbWarning2.setVisible(false);
                };
               };  
            }
            
            if(userFlag == false){
            lbWarning1.setVisible(true);
            lbWarning2.setVisible(false);
            }
            
            if(userFlag == true && passwordFlag == false){
            lbWarning2.setVisible(true);
            }
            
            if(userFlag == true && passwordFlag == true){
            MainAppFXMLController.loggedIn = true;
            MainAppFXMLController.user = username;
            
            lbWarning.setText("Welcome, " + username);
            lbWarning.setStyle("-fx-text-fill: green;");
            lbWarning.setVisible(true);
            btnGuest.setDisable(true);
            btnLogin.setDisable(true);
            btnNewUser.setDisable(true);
            btnLogout.setDisable(false);
            close();
            }
         });
    }
    
    private void CheckBoxes(){
        cbShow.setOnAction((event)->{
          if(cbShow.isSelected()){
            psPassword.setVisible(false);   
          //  lbPs2.setVisible(false);
            vbMain.setMouseTransparent(true);
          }
          else{
             psPassword.setVisible(true);
             psPassword.setManaged(true);
          
          }
        });

    }
    
    //loads all usernames and passwords into a linkedhashmap
    private void loadUsers() throws IOException{
        
        File file = new File ("src/main/resources/usernamespasswords/usernamespasswords.txt");
        
        try(BufferedReader br = new BufferedReader(new FileReader(file));){
         String line;
        
         while((line = br.readLine())!=null){      
            String split[] = line.split(" ");//since the usernames and passwords are separated by a space, it splits there
            if(split.length==2){users.put(split[0], split[1]);System.out.println(split[0] + " " + split[1]);}
            else{System.out.println("noooo");}
         }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
