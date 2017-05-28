import java.util.Random;
import java.lang.Math;
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
	private int num_Layers;//number of layers in network
	private int[] sizes;//number of sigmoid neurons in each layer
	private Random rng = new Random(99);//for random initial weights and biases
	private double[][] biases;//all the biases for each sigmoid neuron
	private double[][][] weights;//all the weights for each sigmoid neuron
	private double[][] nablaC_b = null;//gradient with respect to each bias
	private double[][][] nablaC_w = null;//gradient with respect to each weight
	private int layer = 1;//current layer
	private double[] activationLevels;//the value output by each layer
	private double learningRate = 3.0;//how fast the program learns
	/* 
	 * A method to compute the activation levels for the next layer and to feed 
	 * the information on so the next layer can be computed.
	 * 
	 * @param <activationLevels> vector of values output by the previous layer
	 * @return <nextA> vector of values output by this layer
	 */	
	public double[] feedForward(double[] activationLevels){
		double[] nextA = new double[sizes[layer]];//next activation levels
		double z = 0.0;//an intermediate value to pass to the sigma function
		
		for(int i = 0; i < sizes[layer]; i++){
		//generate next layers activation levels
			z = Matrix.dotMatrices(weights[layer - 1][i], activationLevels);
			z += biases[layer - 1][i];
			nextA[i] = sigma(z);
		}
		layer++;
		return nextA;
	}
	/* 
	 * A method to compute the output of a neuron based on the 
	 * intermediate z value.
	 * 
	 * @param <z> an intermediate value to be passed into this function
	 * @return <double> the output value of the neuron
	 */
	public double sigma(double z){
		return 1 / (1 + Math.exp(z));
	}
	/* 
	 * A method to compute the gradient with respect to the z values of each 
	 * neuron in a given layer.
	 * 
	 * @param <zs> all of the z input values for a given layer
	 * @return <sigmaPrimeVector> the gradient of sigma prime values for each 
	 * 							  neuron
	 */
	public double[] sigmaPrimeVector(double[] zs){
		double[] sigmaPrimeVector = new double[sizes[--layer]];
			//values of how fast sigma is changing at each neuron
		double zValue = 0.0;//a given neuron's input value

		for(int i = 0; i < sizes[layer]; i++){
		//compute the sigma prime value for each neuron in layer
			for(int j = 0; j < sizes[layer - 1]; j++){
			//compute the value to input into a neurons sigma prime function
				zValue += weights[layer - 1][i][j] * zs[j];
			}
				zValue += biases[layer - 1][i];
				sigmaPrimeVector[i] += Math.exp(zValue) / 
						((1 + Math.exp(zValue)) * (1 + Math.exp(zValue)));
		}
		return sigmaPrimeVector;
	}
	/* 
	 * A method to search for a minimum in the cost function and as a result
	 * a more optimal set of weights and biases in the network.
	 * 
	 * @param <inputs> the set of input data given to the program to learn from
	 * @param <outputs> the expected outputs for the given inputs
	 * @param <batchSize> the amount of cases to be averaged for each update
	 * 					  of the weights and biases
	 */
	public void SGD(double[][] inputs, double[][] outputs, int batchSize){
		int batches = inputs.length / batchSize;
			//number of batches to be performed
		int index = 0;//index of current testCase
		try{
		//updates the weights and biases to approach a smaller cost error
			if(inputs.length != outputs.length){
			//inputs and outputs are not one to one throw exception
				throw new IncorrectDimensionException();
			}
			for(int batch = 0; batch < batches; batch++){
				for(int testCase = 0; testCase < batchSize; testCase++){
				//update the weights and biases using Stochastic gradient descent
					this.backPropogation(inputs[index], outputs[index]);
					index++;
					for(int i = 0; i < biases.length; i++){
						for(int j = 0; j < biases[i].length; j++){
							biases[i][j] -= nablaC_b[nablaC_b.length - 1 - i][j] 
											* learningRate / batchSize;
						}
					}
			
					for(int i = 0; i < weights.length; i++){
						for(int j = 0; j < weights[i].length; j++){
							for(int k = 0; k < weights[i][j].length; k++){
								weights[i][j][k] -= nablaC_w[i][j][k] * 
													learningRate / batchSize;
							}
						}
					}
				}
			}
		}catch(IncorrectDimensionException e){
				e.printStackTrace();
			}
	}
	/* 
	 * A method to find the slope with respect to each bias and weight in the 
	 * network. 
	 * 
	 * @param <inputData> the network input data
	 * @param <expectedOutput> the output the network should produce for the 
	 * 						   given input
	 */
	public void backPropogation(double[] inputData, double[] expectedOutput){
		double[][] activations = new double[num_Layers - 1][];
			//each layer's output values
		double[][] zValues = new double[num_Layers - 1][];
			//each layer's z values
		double[][] errorVectors = new double[num_Layers - 1][];
			//the error in each neuron
		double[][] weightsInLayer = null;
			//to be used for matrix computation on the weights in a layer
		
		zValues[0] = inputData;
		for(int i = 0; i < num_Layers - 2; i++){
		//obtain the activations and z values for each layer in the network
			activations[i] = feedForward(zValues[i]);
			zValues[i + 1] = activations[i];
		}
		activations[activations.length - 1] = 
					feedForward(zValues[zValues.length - 1]);
		errorVectors[0] = 
			costPrime(expectedOutput, activations[activations.length - 1]);
		errorVectors[0] = Matrix.hadamardProduct(errorVectors[0], 
			sigmaPrimeVector(zValues[zValues.length - 1]));
		for(int i = 1; i < num_Layers - 1; i++){
		//find the error vector for each layer
			weightsInLayer = weights[weights.length - i];
			weightsInLayer = Matrix.transpose(weightsInLayer);
			errorVectors[i] = 
				Matrix.multiply(weightsInLayer, errorVectors[i - 1]);
			errorVectors[i] = 
				Matrix.hadamardProduct(errorVectors[i], 
					sigmaPrimeVector(zValues[zValues.length - 1 - i]));
		}
		nablaC_b = errorVectors;
		nablaC_w = new double[num_Layers - 1][][];
		
		for(int i = 0; i < sizes.length - 1; i++){
		//find gradient of the cost function with respect to weights in network
			nablaC_w[i] = new double[sizes[i + 1]][];
			for(int j = 0; j < sizes[i + 1]; j++){
				nablaC_w[i][j] = new double[sizes[i]];
				for(int k = 0; k < sizes[i]; k++){
					nablaC_w[i][j][k] = zValues[i][k] * 
						errorVectors[errorVectors.length - 1 - i][j];
				}
			}
		}
	}
	/* 
	 * A method to compute the gradient of the cost function with respect to its
	 * output values.
	 * 
	 * @param <desiredOutput> desired output from the network
	 * @param <output> actual output from the network
	 * @return <double[]> returns a matrix with the gradient of the cost with
	 * 					  respect to its output values.
	 */
	public double[] costPrime(double[] desiredOutput, double[] output){
		output = Matrix.scalarMultiply(output, -1);
		return Matrix.addMatrices(desiredOutput, output);
	}
	/* 
	 * A method to evaluate a given input to the network.
	 * 
	 * @param <inputData> the set of input data for the network to evaluate
	 * @return <activationLevels> the value output by a given layer. when it is
	 * 							  returned by this program it will be the output
	 * 							  of this network evaluated at the given input.
	 */
	public double[] evaluate(double[] inputData){
		activationLevels = inputData;
		
		for(int i = 0; i < num_Layers - 1; i++){
		//compute the input through each layer in the network
			activationLevels = feedForward(activationLevels);
		}
		layer = 1;
		return activationLevels;
	}
	/* 
	 * A method to calculate how far from the correct answer the network is 
	 * for the current input.
	 * 
	 * @param <desiredOutput> the expected output for the given input
	 * @param <input> the input passed into the network
	 * @return <double> distance from the expected output the neural network is
	 */	
	public double cost(double[] desiredOutput, double[] input){
		double[] output = null;//the output from the neural network
		double sumValue = 0.0;//the distance from the correct value
		try{
		//compute the distance from the correct answer
			if(input.length == sizes[0] && 
					desiredOutput.length == sizes[sizes.length - 1]){
			//check that input is correct dimensions for network to evaluate
				output = this.evaluate(input);
			}
			else{
			//input is not the correct value throw an exception
				throw new IncorrectDimensionException();
			}
			for(int i = 0; i < input.length; i++){
			//calculate the distance from the correct answer
				sumValue += (output[i] - desiredOutput[i]) * 
							(output[i] - desiredOutput[i]);
			}
		}catch(IncorrectDimensionException e){
		//incorrect dimensions in the input to the neural network
			e.printStackTrace();
		}
		return Math.sqrt(sumValue) * Math.sqrt(sumValue);
	}
	/*
	 * A method to create a Neural network with layers based off of sizes. It
	 * will randomly initialize each layers weights and biases.
	 * 
	 * @param sizes and integer array containing the size of each layer
	 */
	public Network(int[] sizes){
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
}
