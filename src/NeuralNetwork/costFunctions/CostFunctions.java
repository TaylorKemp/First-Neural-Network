package NeuralNetwork.costFunctions;

import AssistiveClasses.Matrix;
import NeuralNetwork.assistiveClasses.Network;
import javafx.scene.control.ComboBox;

public class CostFunctions{
	public static CostFunction quadraticCost;
	public static CostPrime quadraticPrime;
	public static CostFunction crossEntropyCost;
	public static CostPrime entropicPrime;

	static {
		quadraticCost = new CostFunction(
				(double[] a, double[] y) -> { 
					double runningSum = 0.0;
					for(int i = 0; i < a.length; i++){
						runningSum += (y[i] - a[i]) *
								(y[i] - a[i]);
					}
					return new Double(runningSum / 2.0);
				});
		quadraticPrime = new CostPrime(
		//should be gradC = a - y
				(double[] a, double[] y) -> { 
					y = Matrix.scalarMultiply(y, -1);
					return Matrix.addMatrices(a, y);
				});
		
		crossEntropyCost = new CostFunction(
				(double[] a, double[] y) -> { 
					double runningSum = 0.0;
					for(int i = 0; i < a.length; i++){
						runningSum -= y[i] * Math.log(a[i]) +
						     (1.0 - y[i]) * Math.log(1 - a[i]);
					}
					return new Double(runningSum);
				});
		entropicPrime = new CostPrime(
				(double[] a, double[] y) -> { 
					double[] entropicPrime = new double[a.length];
					for(int i = 0; i < entropicPrime.length; i++){
						//output[i] = (output[i] == 1.0) ? 0.999999: output[i];
						//output[i] = (output[i] == 0.0) ? 0.000001: output[i];
						
						entropicPrime[i] = -(y[i] / a[i]) + (1 - y[i]) / (1 - a[i]);
					}
					return entropicPrime;
				});		
	}

    public static CostFunction getCost(ComboBox<String> costFunction) {
        if(costFunction.equals("quadraticCost")){
            System.out.println("quadratic");
            return quadraticCost;
        }
        else{
            return crossEntropyCost;
        }
    }
    
    public static CostPrime getCostPrime(ComboBox<String> costFunction){
        if(costFunction.equals("quadraticCost")){
            System.out.println("quadratic");
            return quadraticPrime;
        }
        else{
            return entropicPrime;
        }
    }
}
