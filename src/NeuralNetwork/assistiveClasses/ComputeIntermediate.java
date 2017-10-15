package NeuralNetwork.assistiveClasses;
import AssistiveClasses.Matrix;

public class ComputeIntermediate {
	public static double[] getZValue(Network net, double[] activationLevels){
		double[] z = Matrix.multiply(
				net.weights[net.layer - 1], activationLevels);
		return Matrix.addMatrices(z, net.biases[net.layer - 1]);
	}
}
