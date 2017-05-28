import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	public static void main(String[] args){
		int[] sizes = {52, 15, 10};
		Network net = new Network(sizes);
		double[][] labels = new double[25010][10];
		double[][] hands = null;
		double[] hand = new double[52];
		hand[0] = 1.0;
		hand[12] = 1.0;
		hand[16] = 1.0;
		hand[15] = 1.0;
		hand[11] = 1.0;
		double[] answer = net.evaluate(hand);
		System.out.println("before learning");
		for(int i = 0; i < answer.length; i++){
			System.out.println(i + ":" + answer[i]);
		}
		System.out.println();
		int batchSize = 5;
		hands = TextReader.read("trainingImages/pokerTrainData.txt", labels);
		
		net.SGD(hands, labels, batchSize);
		hand[0] = 1.0;
		hand[1] = 1.0;
		hand[8] = 1.0;
		hand[3] = 1.0;
		hand[4] = 1.0;
		answer = net.evaluate(hand);
		System.out.println("after learning");
		for(int i = 0; i < answer.length; i++){
			System.out.println(i + ":" + answer[i]);
		}
		System.out.println("program end");
		
	}
				
}
