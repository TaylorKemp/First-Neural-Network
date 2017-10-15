package NeuralNetwork.costFunctions;
import java.util.function.BiFunction;
import java.util.ArrayList;

public class CostPrime {
	public BiFunction<double[], double[], double[]> function;
	
	public CostPrime(
			BiFunction<double[], double[], double[]> function) {
		this.function = function;
	}

	public double[] apply(double[] t, double[] u){
		return function.apply(t, u);
	}
}