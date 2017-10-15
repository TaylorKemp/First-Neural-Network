/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deeplearning2.pkg1;

import NeuralNetwork.assistiveClasses.Network;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author tkemp
 */
public class CreateNetworkController implements Initializable {
    private Parent root = null;
    private final ImageView home;
    private final FileChooser fileChooser;
    private final String homeScreen;
    private final String runScreen;
    private StringBuffer trainData = null;
    private StringBuffer trainLabels = null;
    private StringBuffer testData = null;
    private StringBuffer testLabels = null;
    private Network net = null;
    private Integer inputLayerSize = null;
    private Integer outputLayerSize = null;
    private File fileDirectory = null;
    
    {
        fileDirectory = new File("C:\\Users\\tkemp\\Desktop\\Neural Network Concepts\\");
        net = new Network();
        home = new ImageView(
            new Image(getClass().getResourceAsStream("homeImage.jpg")));
        home.setFitHeight(20);
        home.setFitWidth(20);
        homeScreen = "HomeScreen.fxml";
        runScreen = "runNetwork.fxml";
        fileChooser = new FileChooser();
        trainData = new StringBuffer("./src/train-images.idx3-ubyte");
        trainLabels = new StringBuffer("./src/train-labels.idx1-ubyte");
        testData = new StringBuffer("./src/t10k-images.idx3-ubyte");
        testLabels = new StringBuffer("./src/t10k-labels.idx1-ubyte");
    }
    
    @FXML
    private TextField networkSizes;
     
    @FXML
    private Button getTestLabels;
    
    @FXML
    private Button getTestData;
    
    @FXML
    private Button getTrainLabels;
    
    @FXML
    private Button getTrainData;

    @FXML
    private Button createNetwork;
    
    @FXML
    private Label headerLabel;

    @FXML
    private Label invalidLabel;

    @FXML
    private Label sizesLabel;
    
    @FXML
    private Button confirm;

    @FXML
    private Button cancel;

    @FXML
    private Button returnHome;
    
    public void flipButtons(){
        confirm.setVisible(!confirm.isVisible());
        createNetwork.setVisible(!createNetwork.isVisible());
        cancel.setVisible(!cancel.isVisible());
    }
     
    public void handleButtonAction(ActionEvent event) throws IOException {              
        if(event.getSource() == createNetwork){
            invalidLabel.setVisible(false);
            flipButtons();
        }
        else if(event.getSource() == cancel){
            flipButtons();
        }
        else if(event.getSource() == returnHome){
            root = FXMLLoader.load(getClass().getResource(homeScreen));
            getStage(returnHome).show();
        }
        else if(event.getSource() == confirm){
            try{
                String[] sizes = this.networkSizes.getCharacters().toString().split(", ");
                int[] dimensions = new int[sizes.length + 2];
                dimensions[0] = inputLayerSize;
                dimensions[dimensions.length - 1] = outputLayerSize;
                for(int i = 0; i < sizes.length; i++){
                    dimensions[i + 1] = Integer.parseInt(sizes[i]);
                }
                net.setNetwork(dimensions);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(runScreen));
                
                loader.load();
               
                RunNetworkController controller = loader.getController();
                Thread t = new Thread(() -> {
                    controller.setLoadingTest(false);
                    net.setTestData(controller, testData.toString(), 
			testLabels.toString());
                    controller.setLoadingTest(true);
                    net.setTrainingData(controller, trainData.toString(), 
                        trainLabels.toString());
                    controller.setRunVisible(true);
                    controller.setLoadingTest(false);
                    controller.setProgressVisible(false);
                });
                t.setDaemon(true);
                t.start();
                controller.setNetwork(net);
                root = loader.getRoot();
                getStage(confirm).show();
            }catch(NumberFormatException | NullPointerException e){
            }catch(IOException e){
                throw e;
            }finally{
                invalidLabel.setVisible(true);
                flipButtons();
            }
        }
    }
    
    private Stage getStage(Button button){
        Stage stage = (Stage) button.getScene().getWindow();
        stage.getScene().setRoot(root);
        return stage;
    }
    
    private void setHandleEventButton(Button button){
        button.setOnAction((ActionEvent e) -> {
            try{
                handleButtonAction(e);
            }catch(IOException ex){
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void setFileLoader(Button button, StringBuffer filePath, String promptMessage){
        button.setOnAction((ActionEvent e) -> {
            fileChooser.setTitle(promptMessage);
            fileChooser.setInitialDirectory(fileDirectory);
            String output = fileChooser.showOpenDialog(
                    button.getScene().getWindow()).getAbsolutePath();
            System.out.println(output);
            filePath.replace(0, filePath.length(), output);
            button.setVisible(false);
            if(!(getTrainData.isVisible() || getTrainLabels.isVisible() ||
                getTestData.isVisible() || getTestLabels.isVisible())){
                inputLayerSize = net.getInputLayerSize(trainData.toString());
                outputLayerSize = net.getOutputLayerSize(trainLabels.toString());
                String netSizePrompt = "Input Layer: " + inputLayerSize + 
                        "Output Layer: " + outputLayerSize + 
                        " ...Input inner layers. (example format: 100, 33, 10)";
                networkSizes.setPromptText(netSizePrompt);
                networkSizes.setVisible(true);
            }
        });
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        invalidLabel.setVisible(false);    
        confirm.setVisible(false);
        cancel.setVisible(false);
        networkSizes.setVisible(false);
        returnHome.setGraphic(home);
        
            //Set buttons for finding training and testing data
        setFileLoader(getTrainData, trainData, "Select Training Data");
        setFileLoader(getTrainLabels, trainLabels, "Select Training Labels");
        setFileLoader(getTestData, testData, "Select Test Data");
        setFileLoader(getTestLabels, testLabels, "Select Test Labels");
            //Set buttons to be clicked
        setHandleEventButton(createNetwork);
        setHandleEventButton(confirm);
        setHandleEventButton(cancel);
        setHandleEventButton(returnHome);        
    }
}
