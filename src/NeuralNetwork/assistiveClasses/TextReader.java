package AssistiveClasses;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextReader {
	public static double[][] read(String fileName, double[][] labels){
		double[][] hands = new double[25010][52];
		double[] hand = null;
		int[] cardValues = null;
		Scanner input = null;
		int index = 0;
		
		try{
			input = new Scanner(new File(fileName));
			while(input.hasNextLine() && index < 25010){
				hand = new double[52];
				cardValues = toIntArray(input.nextLine(), ",");
				for(int i = 0; i < cardValues.length - 1; i += 2){
					hand[cardValues[i] * 13 - 13 + cardValues[i + 1] - 1] = 1.0;
				}
				labels[index][cardValues[cardValues.length - 1]] = 1.0;
				hands[index++] = hand;
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		return hands;
	}
	public static int[] toIntArray(String values, String delimeter){
		String[] stringIntegers = values.split(delimeter);
		int[] intArray = new int[stringIntegers.length];
		
		for(int i = 0; i < stringIntegers.length; i++){
			intArray[i] = Integer.parseInt(stringIntegers[i]);
		}
		return intArray;
	}
}
