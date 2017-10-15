package NeuralNetwork.assistiveClasses;

import deeplearning2.pkg1.RunNetworkController;

public class Evaluate {	
	public static double[] sigma(double[] zValues){
		for(int i = 0; i < zValues.length; i++)
			zValues[i] = 1.0 / (1.0 + Math.exp(-zValues[i]));
		return zValues;
	}
	public static double sigma(double z){
		return 1.0 / (1.0 + Math.exp(-z));
	}
	/* 
	 * A method to compute the gradient with respect to the z values of each 
	 * neuron in a given layer.
	 * 
	 * @param <zs> all of the z input values for a given layer
	 * @return <sigmaPrimeVector> the gradient of sigma prime values for each 
	 * 							  neuron
	 */
	public static double[] sigmaPrime(Network net, double[] zs){
		double[] sigmaPrimeVector = new double[net.sizes[--net.layer]];
			//values of how fast sigma is changing at each neuron
		for(int i = 0; i < zs.length; i++){
		//compute the sigma prime value for each neuron in layer
			sigmaPrimeVector[i] += sigma(zs[i]) - Math.pow(sigma(zs[i]), 2.0);
		}
		return sigmaPrimeVector;
	}
	/* 
	 * A method to compute the activation levels for the next layer and to feed 
	 * the information on so the next layer can be computed.
	 * 
	 * @param <activationLevels> vector of values output by the previous layer
	 * @return <nextA> vector of values output by this layer
	 */	
	public static double[] feedForward(double[] activationLevels, Network net){
		double[] nextA = new double[net.sizes[net.layer]];//next activation levels
		
		net.activationLevels = activationLevels;
		nextA = sigma(ComputeIntermediate.getZValue(net, net.activationLevels));
		net.layer++;
		return nextA;
	}
	/* 
	 * A method to evaluate a given input to the network.
	 * 
	 * @param <inputData> the set of input data for the network to evaluate
	 * @return <activationLevels> the value output by a given layer. when it is
	 * 							  returned by this program it will be the output
	 * 							  of this network evaluated at the given input.
	 */
	public static double[] evaluate(Network net, double[] inputData){
		net.activationLevels = inputData;
		
		for(int i = 0; i < net.num_Layers - 1; i++){
		//compute the input through each layer in the network
			net.activationLevels = Evaluate.feedForward(net.activationLevels, net);
		}
		net.layer = 1;
		return net.activationLevels;
	}
	
	public static Double testNetwork(Network net, int numTesting){
		double[] value = new double[net.testLabels[0].length];
		int max = 0;
		int answer = 0;
		int numCorrect = 0;
		numTesting = (numTesting > 0) ? numTesting: net.testImages.length;
		for(int j = 0; j < numTesting; j++){
				value = Evaluate.evaluate(net, net.testImages[j]);
				for(int k = 0; k < net.testLabels[0].length; k++){
					if(value[k] > value[max]){
						max = k;
					}
					if(net.testLabels[j][k] > 0.1){
						answer = k;
					}
				}
				if(answer == max){
					numCorrect++;
				}
				
			}
		return ((double)numCorrect ) / numTesting;
	}
}
