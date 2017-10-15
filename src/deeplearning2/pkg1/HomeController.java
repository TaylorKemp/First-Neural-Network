package deeplearning2.pkg1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tkemp
 */
public class HomeController implements Initializable {   
    @FXML
    private Button loadButton;

    @FXML
    private Button newButton;

    @FXML
    private Button helpButton;
    
    @FXML
    private Label title;
  
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        loadButton.setOnAction((ActionEvent e) -> {
            System.out.println("//TODO");
        });
        newButton.setOnAction((ActionEvent e) -> {
            try {               
                Parent root = FXMLLoader.load(
                        getClass().getResource("createNetwork.fxml"));
                Stage stage = (Stage) newButton.getScene().getWindow();
                stage.getScene().setRoot(root);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(
                        HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        helpButton.setOnAction((ActionEvent e) -> {
            System.out.println("//TODO");
        });        
    }     
}
