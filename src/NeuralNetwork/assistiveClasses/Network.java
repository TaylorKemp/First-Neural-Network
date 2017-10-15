package NeuralNetwork.assistiveClasses;
import deeplearning2.pkg1.RunNetworkController;
import java.util.Random;

/*******************************************************************************
 * The class Network is capable of building a basic neural network with some 
 * amount of layers where each layer has some other amount of neurons. This 
 * network will learn through stochastic gradient descent. Its purpose is to 
 * learn the fundamentals of ai programming.
 * 
 * @author tkemp
 *
 */
public class Network {
	public int num_Layers;//number of layers in network
	public int[] sizes;//number of sigmoid neurons in each layer
	private Random rng = new Random();//for random initial weights and biases
	public double[][] biases;//all the biases for each sigmoid neuron
	public double[][][] weights;//all the weights for each sigmoid neuron
	public double[][] nablaC_b = null;//gradient with respect to each bias
	public double[][][] nablaC_w = null;//gradient with respect to each weight
	public int layer = 1;//current layer
	public double[] activationLevels;//the value output by each layer
	public Double learningRate;//how fast the program learns
	public double[][] images = null;
	public double[][] labels = null;
	public double[][] testImages = null;
	public double[][] testLabels = null;
	
        
        public void setLearningRate(Double learningRate){
            this.learningRate = learningRate;
        }
        
        public Double getLearnRate(){
            return this.learningRate;
        }
        
        public void setTrainingData(RunNetworkController controller, String inputs, String labels){
            IDXReader.read(controller, inputs, labels, this, false, 60000);
        }
        public void setTestData(RunNetworkController controller, String inputs, String labels){
            IDXReader.read(controller, inputs, labels, this, true, -10000);
        }
        public void setSizes(int[] sizes){
            this.sizes = sizes;
        }
        public Integer getInputLayerSize(String inputs){
            return IDXReader.getInputDimensions(inputs);
        }
        public Integer getOutputLayerSize(String outputs){
            return IDXReader.getOutputDimensions(outputs);
        }
        
        public void setNetwork(int[] sizes){
            num_Layers = sizes.length;//number of layers in network
		this.sizes = sizes;//number of neurons in each layer
		biases = new double[num_Layers - 1][];//bias for each neuron
		weights = new double[num_Layers - 1][][];//weights for each neuron
		
		for(int i = 0; i < num_Layers - 1; i++){
		//for each layer initialize all biases and weights
			biases[i] = new double[sizes[i + 1]];
			weights[i] = new double[sizes[i + 1]][];
			for(int j = 0; j < sizes[i + 1]; j++){
			//for each neuron in a given layer initialize each bias and 
		    //all weights associated with given neuron
				biases[i][j] = rng.nextGaussian();
				weights[i][j] = new double[sizes[i]];
				for(int k = 0; k < sizes[i]; k++){
				//initialize all input neuron weights for a given neuron
					weights[i][j][k] = rng.nextGaussian();
				}
			}
		}
        }
        
        public Network(){
        
        }
        
	/*
	 * A method to create a Neural network with layers based off of sizes. It
	 * will randomly initialize each layers weights and biases.
	 * 
	 * @param sizes and integer array containing the size of each layer
	 */
	public Network(int[] sizes){
            setNetwork(sizes);
	}
}
