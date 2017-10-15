package NeuralNetwork.costFunctions;
import java.util.function.BiFunction;
import java.util.ArrayList;

public class CostFunction {
	public BiFunction<double[], double[], Double> function;
	
	public CostFunction(
			BiFunction<double[], double[], Double> function) {
		this.function = function;
	}

	public Double apply(double[] t, double[] u){
		return function.apply(t, u);
	}
}
