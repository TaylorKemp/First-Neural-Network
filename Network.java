import java.util.Random;
import java.lang.Math;

public class Network {
	private int num_Layers;//number of layers in network
	private int[] sizes;//number of sigmoid neurons in each layer
	private Random rng = new Random(99);//for random initial weights and biases
	private double[][] biases;//all the biases for each sigmoid neuron
	private double[][][] weights;//all the weights for each sigmoid neuron
	private int layer = 1;//current layer
	private double[] activationLevels;
	private double learningRate = 0.001;
		
	public double[] feedForward(double[] activationLevels){
	//A class to use the previous layers neurons to generate the next layers
		double[] nextA = new double[sizes[layer]];
		double z = 0.0;
		
		for(int i = 0; i < sizes[layer]; i++){
		//generate next layers activation levels
			z = Matrix.dotMatrices(weights[layer - 1][i], activationLevels);
			z -= biases[layer - 1][i];
			nextA[i] = sigma(z);
		}
		layer++;
		return nextA;
	}
	
	public double sigma(double z){
		return 1 / (1 + Math.exp(z));
	}
	
	public double[] sigmaPrimeVector(double[] z){
		double[] sigmaPrimeVector = new double[sizes[--layer]];
		double zValue = 0.0;

		for(int i = 0; i < sizes[layer]; i++){
			for(int j = 0; j < sizes[layer - 1]; j++){
				zValue += weights[layer - 1][i][j] * z[j];
			}
				zValue += biases[layer - 1][i];
				sigmaPrimeVector[i] += Math.exp(zValue) / 
						((1 + Math.exp(zValue)) * (1 + Math.exp(zValue)));
		}
		return sigmaPrimeVector;
	}

	public void SGD(double[][] inputs, double[][] outputs, int batchSize){
		int batches = inputs.length / batchSize;
		int i = 0;
		try{
			if(inputs.length != outputs.length){
				throw new IncorrectDimensionException();
			}
			while(i < batches){
				for(int j = 0; j < batchSize; j++){
					this.backPropogation(inputs[i], outputs[i]);
					i++;
				}
			}
		}catch(IncorrectDimensionException e){
			e.printStackTrace();
		}
	}
	
	public void backPropogation(double[] inputData, double[] expectedOutput){
		double[][] activations = new double[num_Layers - 1][];
		double[][] zValues = new double[num_Layers - 1][];
		double[][] errorVectors = new double[num_Layers - 1][];
		double[][] weightsInLayer = null;
		double[][] nablaC_b = null;
		double[][][] nablaC_w = null;
		
		zValues[0] = inputData;
		for(int i = 0; i < num_Layers - 2; i++){
			activations[i] = feedForward(zValues[i]);
			zValues[i + 1] = activations[i];
		}
		activations[activations.length - 1] = feedForward(zValues[zValues.length - 1]);
		
		errorVectors[0] = costPrime(expectedOutput, activations[activations.length - 1]);
		errorVectors[0] = Matrix.hadamardProduct(errorVectors[0], 
				sigmaPrimeVector(zValues[zValues.length - 1]));
		for(int i = 1; i < num_Layers - 1; i++){
			weightsInLayer = weights[weights.length - i];
			weightsInLayer = Matrix.transpose(weightsInLayer);
			errorVectors[i] = Matrix.multiply(weightsInLayer, errorVectors[i - 1]);
			errorVectors[i] = Matrix.hadamardProduct(errorVectors[i], sigmaPrimeVector(zValues[zValues.length - 1 - i]));
		}
		
		nablaC_b = errorVectors;
		nablaC_w = new double[num_Layers - 1][][];
		
		for(int i = 0; i < sizes.length - 1; i++){
		//i is current layer
			nablaC_w[i] = new double[sizes[i + 1]][];
			for(int j = 0; j < sizes[i + 1]; j++){
			//j is current neuron in current layer
				nablaC_w[i][j] = new double[sizes[i]];
				for(int k = 0; k < sizes[i]; k++){
					nablaC_w[i][j][k] = zValues[i][k] * errorVectors[errorVectors.length - 1 - i][j];
				}
			}
		}
		
		for(int i = 0; i < biases.length; i++){
			for(int j = 0; j < biases[i].length; j++){
				biases[i][j] -= nablaC_b[nablaC_b.length - 1 - i][j] * learningRate;
			}
		}
		
		for(int i = 0; i < weights.length; i++){
			for(int j = 0; j < weights[i].length; j++){
				for(int k = 0; k < weights[i][j].length; k++){
					weights[i][j][k] -= nablaC_w[i][j][k] * learningRate;
				}
			}
		}
	}
	
	public double[] costPrime(double[] desiredOutput, double[] output){
		output = Matrix.scalarMultiply(output, -1);
		return Matrix.addMatrices(desiredOutput, output);
	}
	
	public double[] evaluate(double[] inputData){
		activationLevels = inputData;
		
		for(int i = 0; i < num_Layers - 1; i++){
			activationLevels = feedForward(activationLevels);
		}
		layer = 1;
		return activationLevels;
	}
	
	public double cost(double[] desiredOutput, double[] input){
		double[] output = null;
		double sumValue = 0.0;
		if(input.length == sizes[0] && desiredOutput.length == sizes[sizes.length - 1]){
			output = this.evaluate(input);
		}
		else{
			System.out.print("input was length: " + input.length);
			System.out.println(" input should be of length: " + sizes[0]);
			return 0.0;
		}
		for(int i = 0; i < input.length; i++){
			sumValue += (output[i] - desiredOutput[i]) * 
						(output[i] - desiredOutput[i]);
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
				biases[i][j] = .5;
				weights[i][j] = new double[sizes[i]];
				for(int k = 0; k < sizes[i]; k++){
				//initialize all input neuron weights for a given neuron
					weights[i][j][k] = .5;
				}
			}
		}
	}
}
