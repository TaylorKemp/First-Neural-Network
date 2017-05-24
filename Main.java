import java.util.Random;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	public static void main(String[] args){
		int[] sizes = {2, 2};
		//IDXReader.readImage("trainingImages/train-images-idx3-ubyte.gz");
		Network net = new Network(sizes);
		double[] array = {0.0, 0.0};
		net.evaluate(array);
		double[][] inputData = new double[4][2];
		double[][] expectedOutput = new double[4][2];
		double[] result = null;
		inputData[0][0] = 1.0;
		inputData[1][0] = 0.0;
		inputData[2][0] = 0.0;
		inputData[3][0] = 0.0;
		
		inputData[0][1] = 1.0;
		inputData[1][1] = 0.0;
		inputData[2][1] = 0.0;
		inputData[3][1] = 0.0;
		expectedOutput[0][0] = 1.0;
		expectedOutput[1][0] = 0.0;
		expectedOutput[2][0] = 0.0;
		expectedOutput[3][0] = 0.0;
		
		expectedOutput[0][1] = 0.0;
		expectedOutput[1][1] = 1.0;
		expectedOutput[2][1] = 1.0;
		expectedOutput[3][1] = 1.0;
		
		result = net.evaluate(array);
		System.out.println("chance yes:" + result[0]);
		System.out.println("chance no:" + result[1]);
		for(int i = 0; i < 1000000; i++){
			net.backPropogation(inputData[1], expectedOutput[1]);
		}
		
		result = net.evaluate(inputData[1]);
		System.out.println("chance yes:" + result[0]);
		System.out.println("chance no:" + result[1]);
		System.out.println("End of Program");
	}
				
}
