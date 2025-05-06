/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.controllers;

import static edu.vanier.template.controllers.MainAppFXMLController.user;
import edu.vanier.template.ui.MainApp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author theli
 * Controls the new user window
 */
public class NewUserFXMLController extends Stage{
    
    @FXML
    Pane root;
    
    @FXML
    Button btnCancel, btnConfirm, btnLogin, btnNewUser, btnGuest, btnLogout;
    
    @FXML
    TextField txtUsername, txtPassword1, txtPassword2;
    
    @FXML
    PasswordField psPassword1, psPassword2;
    
    @FXML
    Label lbWarning, lbWarning1, lbWarning2, lbWarning3, lbPs2;//lbWarning is passed from main page
    
    @FXML
    CheckBox cbShow1;
    
    @FXML
    VBox vbMain;//the main encrypted VBox      
            
    String username, password1, password2;
    
    ArrayList <String> users = new ArrayList<>();
    
    boolean flag = false;//only true when username and password are all correct
    
    boolean userFlag = false;
    boolean userExistsFlag = true;
    boolean passwordFlag1 = false;
    boolean passwordFlag2 = false;
    
    public NewUserFXMLController(Button Login, Button NewUser, Button Guest, Button Logout, Label lbWarn, String title) throws IOException{
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
       
       txtPassword1.textProperty().bindBidirectional(psPassword1.textProperty());//synchronizing the fields
       txtPassword2.textProperty().bindBidirectional(psPassword2.textProperty());
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
        loadUsers();
        
        //path to the file that stores usernames and passwords
        File file = new File("src/main/resources/usernamespasswords/usernamespasswords.txt");

        lbWarning1.setVisible(false);
        lbWarning2.setVisible(false);
        lbWarning3.setVisible(false);
        
        btnCancel.setOnAction((event)->{close();});
        
        btnConfirm.setOnAction((event)->{
            
            username = txtUsername.getText().trim();
            password1 = psPassword1.getText().trim();
            password2 = psPassword2.getText().trim();     
            
            if(username.contains(" ")){//can't include spaces in username
              userFlag = false;
              lbWarning1.setVisible(true);
              lbWarning1.setText("Username can't include empty spaces");
           }
           if(username.length()<3){
              userFlag = false;
              lbWarning1.setVisible(true);
              lbWarning1.setText("Username must be at least 3 characters");
               System.out.println("ee");
           }
           
           for(String user: users){
              if(user.toLowerCase().equals(username.toLowerCase())){
                userExistsFlag = true;
                lbWarning1.setVisible(true);
                lbWarning1.setText("Username already exists");
                break;
              }
              else{
                userExistsFlag = false;
              }
           }
           
           if(username.length()>=3 && !username.contains(" ") && userExistsFlag == false){
              userFlag = true;
              lbWarning1.setVisible(false);
           }
           
           if(password1.contains(" ")){//can't include spaces in passwords
              passwordFlag1 = false;
              lbWarning2.setVisible(true);
              lbWarning2.setText("Password can't include empty spaces");
           }
           
           if(password1.length()<3){
              passwordFlag1 = false;
              lbWarning2.setVisible(true);
              lbWarning2.setText("Password must be at least 3 characters");
           }
           
           if(password1.length()>=3 && !password1.contains(" ")){
              passwordFlag1 = true;
              lbWarning2.setVisible(false);           
           }
           
           if(!password1.equals(password2)){
              passwordFlag2 = false;
              lbWarning3.setVisible(true);//when passwords don't match when confirming inputted password
              lbWarning3.setText("Passwords don't match");
           }
           
           if(password1.equals(password2)){
              passwordFlag2 = true;
              lbWarning3.setVisible(false);//when passwords don't match when confirming inputted password
           }
           
           if(userFlag == true && passwordFlag1 == true && passwordFlag2 == true){
            try (FileWriter writer = new FileWriter(file, true)) {
                        writer.write("\n" + username + " " + password1);
                       // System.out.println(username + " " + password1);
                        writer.flush();
                    } catch (IOException ex) {
                    Logger.getLogger(NewUserFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            MainAppFXMLController.loggedIn = true;
            MainAppFXMLController.user = username;
            
            try {
                try(BufferedReader br = new BufferedReader(new InputStreamReader(MainApp.class.getResourceAsStream("/usernamespasswords/usernamespasswords.txt")));){
         String line;
        
         while((line = br.readLine())!=null){      
            String split[] = line.split(" ");//since the usernames and passwords are separated by a space, it splits there
            if(split.length==2){System.out.println(split[0] + " " + split[1]);}
            else{System.out.println("noooo");}
         }
                    System.out.println(" ");
        }
            }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
              
            
            
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
        cbShow1.setOnAction((event)->{
          if(cbShow1.isSelected()){
            psPassword1.setVisible(false);
          //  psPassword1.setManaged(false);
            psPassword2.setVisible(false);
          //  psPassword2.setManaged(false);
            lbPs2.setVisible(false);
            vbMain.setMouseTransparent(true);
          }
          else{
             psPassword1.setVisible(true);
             psPassword1.setManaged(true);
             psPassword2.setVisible(true);
             psPassword2.setManaged(true);
             lbPs2.setVisible(true);
          }
        });

    }
    
    //loads all usernames into a linkedhashmap
    private void loadUsers() throws IOException{
        
        File file = new File ("src/main/resources/usernamespasswords/usernamespasswords.txt");

        try(BufferedReader br = new BufferedReader(new FileReader(file));){
         String line;
        
         while((line = br.readLine())!=null){      
            String split[] = line.split(" ");//since the usernames and passwords are separated by a space, it splits there
            if(split.length==2)users.add(split[0]);
            else{System.out.println("noooo");}
         }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
