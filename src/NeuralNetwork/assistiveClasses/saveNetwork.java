package NeuralNetwork.assistiveClasses;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class saveNetwork {
	public static void saveToFile(String fileName, Network net){
		FileWriter file = null;
		try {
			file = new FileWriter(fileName);
			file.write("w ");
			for(int i = 0; i < net.weights.length; i++){
				for(int j = 0; j < net.weights[i].length; j++){
					for(int k = 0; k < net.weights[i][j].length; k++){
						file.write(net.weights[i][j][k] + " ");
					}
				}
			}
			file.write("b ");
			for(int i = 0; i < net.biases.length; i++){
				for(int j = 0; j < net.weights[i].length; j++){
					file.write(net.biases[i][j] + " ");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createFile(String fileName, String absolutePath){
		File f = new File(absolutePath + fileName);
		f.getParentFile().mkdirs(); 
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
