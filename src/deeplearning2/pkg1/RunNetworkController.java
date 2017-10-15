/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deeplearning2.pkg1;

import NeuralNetwork.assistiveClasses.HyperParameters;
import NeuralNetwork.assistiveClasses.Network;
import NeuralNetwork.costFunctions.CostFunction;
import NeuralNetwork.costFunctions.CostFunctions;
import NeuralNetwork.costFunctions.CostPrime;
import NeuralNetwork.assistiveClasses.Train;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author tkemp
 */
public class RunNetworkController implements Initializable {
    private Network net;
    XYChart.Series series = new XYChart.Series();
    
    @FXML 
    private NumberAxis xAxis;
    
    @FXML
    private NumberAxis yAxis;
    
    @FXML
    private LineChart<Number, Number> graph;

    @FXML
    private Button run;

    @FXML
    private Label hyperParameterslabel;

    @FXML
    private Label learnRatelabel;

    @FXML
    private TextField learningRate;

    @FXML
    private Label epochsLabel;

    @FXML
    private TextField epoch;

    @FXML
    private Label minibatchLabel;

    @FXML
    private TextField miniBatchSize;

    @FXML
    private ComboBox<String> costFunction;
    
    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progressLabel;
    
    @FXML
    private Label loadTrainingData;
    
    @FXML
    private TextField sampleSize;
    
    public void setLoadingTest(Boolean isTest){
        progressLabel.setVisible(!isTest);
        loadTrainingData.setVisible(isTest);
        
    }
    
    public void setProgress(Double progress){
        progressBar.setProgress(progress);
    }
    
    public void setNetwork(Network net){
        this.net = net;
    }
    
    public Network getNetwork(){
        return net;
    }
    
    public void updateGraph(Integer x, Double y){
        this.series.getData().add(new XYChart.Data(x, y));
        
    }
    
    public void setRunVisible(Boolean visible){
        this.run.setVisible(visible);
    }
    
    public void setProgressVisible(Boolean visible){
        this.progressBar.setVisible(visible);
        this.progressLabel.setVisible(visible);
    }
    
    public void handleButtonAction(ActionEvent event)throws IOException{
        if(event.getSource() == run){
            try{
                RunNetworkController controller = this;
                Thread t = new Thread(() -> {
                    Integer miniBatch = Integer.parseInt(miniBatchSize.getText());
                    Integer epochs = Integer.parseInt(epoch.getText());
                    Integer sampleSize = Integer.parseInt(this.sampleSize.getText());
                    Double learnRate = Double.parseDouble(learningRate.getText());
                    CostFunction cost = CostFunctions.getCost(costFunction);
                    CostPrime costPrime = CostFunctions.getCostPrime(costFunction);
                    HyperParameters parameters = new HyperParameters(epochs, miniBatch, learnRate, cost, costPrime);
                    series.getData().clear();
                    Train.trainNetwork(net, controller, parameters, sampleSize);
                });
                t.setDaemon(true);
                t.start();
            }catch(NumberFormatException e){
               
            }
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        run.setVisible(false);
        costFunction.getItems().addAll(
                "Cross Entropy", "Quadratic Cost");
        yAxis.setUpperBound(1.00);
        graph.setCreateSymbols(false);
        graph.getData().add(series);  

        
        run.setOnAction((ActionEvent event) -> {
                try{
                    handleButtonAction(event);
                }catch(IOException e){
                    
                }
        }
        );
    }    
}
