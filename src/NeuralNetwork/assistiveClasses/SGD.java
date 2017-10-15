package NeuralNetwork.assistiveClasses;
//backProp entropic Cost
//sgd entropic cost

import NeuralNetwork.costFunctions.CostFunction;
import NeuralNetwork.costFunctions.CostPrime;
import AssistiveClasses.Matrix;
import deeplearning2.pkg1.RunNetworkController;

public class SGD {
	/* 
	 * A method to search for a minimum in the cost function and as a result
	 * a more optimal set of weights and biases in the network.
	 * 
	 * @param <inputs> the set of input data given to the program to learn from
	 * @param <outputs> the expected outputs for the given inputs
	 * @param <batchSize> the amount of cases to be averaged for each update
	 * 					  of the weights and biases
	 */
	public static void sGD(Network net, RunNetworkController controller, double[][] inputs, double[][] y, 
			int batchSize, int epoch, CostFunction cost, CostPrime costPrime, int testSize, double lambda){
		int batches = inputs.length / batchSize;
			//number of batches to be performed
		int index = 0;//index of current testCase
		
		for(int batch = 0; batch < batches; batch++){
			index = batch * batchSize;
			for(int epochNumber = 0; epochNumber < epoch; epochNumber++){
				for(int testCase = 0; testCase < batchSize; testCase++){
					//update the weights and biases using Stochastic gradient descent
					backPropogation(net, inputs[testCase + index], y[testCase + index], costPrime);		
				
					for(int i = 0; i < net.biases.length; i++){
						for(int j = 0; j < net.biases[i].length; j++){
							net.biases[i][j] -= net.learningRate / batchSize *
									net.nablaC_b[net.nablaC_b.length - 1 - i][j];
							}
					}
					for(int i = 0; i < net.weights.length; i++){
						for(int j = 0; j < net.weights[i].length; j++){
							for(int k = 0; k < net.weights[i][j].length; k++){
								net.weights[i][j][k] -= (net.nablaC_w[i][j][k] / batchSize
										+ lambda * net.weights[i][j][k] / inputs.length)* net.learningRate;
                                                            }
						}
					}
				}
			}
                        System.out.println(batch + " of " + batches);
                        double evaluation = Evaluate.testNetwork(net, testSize);
                        System.out.println(evaluation);
			controller.updateGraph(batch, evaluation);
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
	public static void backPropogation(Network net, double[] inputData, double[] y, CostPrime costPrime){
		double[][] activations = new double[net.num_Layers][];
			//each layer's output values
		double[][] zValues = new double[net.num_Layers - 1][];
			//each layer's z values
		double[][] errorVectors = new double[net.num_Layers - 1][];
			//the error in each neuron
		double[][] weightsInLayer = null;
			//to be used for matrix computation on the weights in a layer
		
		activations[0] = inputData;
		for(int i = 0; i < net.num_Layers - 1; i++){
		//obtain the activations and z values for each layer in the network
			zValues[i] = ComputeIntermediate.getZValue(net, activations[i]);
			activations[i + 1] = Evaluate.feedForward(activations[i], net);
		}
		errorVectors[0] = 
			costPrime.apply(activations[activations.length - 1], y);
		errorVectors[0] = Matrix.hadamardProduct(errorVectors[0], 
			Evaluate.sigmaPrime(net, zValues[zValues.length - 1]));
		
		for(int i = 1; i < net.num_Layers - 1; i++){
		//find the error vector for each layer
			weightsInLayer = net.weights[net.weights.length - i];
			weightsInLayer = Matrix.transpose(weightsInLayer);
			errorVectors[i] = 
				Matrix.multiply(weightsInLayer, errorVectors[i - 1]);
			errorVectors[i] = 
				Matrix.hadamardProduct(errorVectors[i], 
					Evaluate.sigmaPrime(net, zValues[zValues.length - 1 - i]));
		}
		
		net.nablaC_b = errorVectors;
		net.nablaC_w = new double[net.num_Layers - 1][][];
		
		for(int i = 0; i < net.sizes.length - 1; i++){
		//layer in network
			net.nablaC_w[i] = new double[net.sizes[i + 1]][];
			for(int j = 0; j < net.sizes[i + 1]; j++){
			//neuron in layer
				net.nablaC_w[i][j] = new double[net.sizes[i]];
				for(int k = 0; k < net.sizes[i]; k++){
					net.nablaC_w[i][j][k] = activations[i][k] * 
						errorVectors[errorVectors.length - 1 - i][j];
				}
			}
		}
	}
}
