/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deeplearning2.pkg1;

import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author tkemp
 */
public class JavaNet extends Application {
    /**
     * Load and display the homescreen file in a window.
     * @param primaryStage
     * @throws java.lang.Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("HomeScreen.fxml"));
        AnchorPane box = loader.load();
        HomeController controller = loader.getController();
        Scene currentScene = new Scene(box);
        primaryStage.getIcons().add(new Image(
                getClass().getResource("lightbulbIcon.png").toExternalForm()));
        primaryStage.setFullScreen(true);
        primaryStage.setScene(currentScene);
        primaryStage.show(); 
        primaryStage.setFullScreenExitHint("");
    }

    /**
     * Start the application JavaNet.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
