package NeuralNetwork.assistiveClasses;
import deeplearning2.pkg1.CreateNetworkController;
import deeplearning2.pkg1.RunNetworkController;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class IDXReader {
    public static Integer getInputDimensions(String file){
        FileInputStream inImage = null;
        int numRows = 0;
        int numCols = 0;
        int magicNumber = 0;
        int numExamples = 0;
        
        try {
            inImage = new FileInputStream(file);
            magicNumber = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            numExamples = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            numRows = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            numCols = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IDXReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IDXReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return numRows * numCols;
    }
    
    public static Integer getOutputDimensions(String file){
        return 10;
    }
    
	public static void read(RunNetworkController controller, String file, String labelFile, Network net, boolean isTestData, int num){
		FileInputStream inImage = null;
		FileInputStream inLabel = null;
		int magicLabels = 0;
		int numLabels = 0;
		
		int numImageRows = 0;
		int numImageCols = 0;
		int magicImages = 0;
		int pixels = 0;
		int numImages = 0;
		double[][] images = null;
		double[][] labels = null;
                //System.out.println(System.getProperty("user.dir"));
                //this is used to get the current location for this file
                //can be used to create a relative file path to a location
		try{
			inImage = new FileInputStream(file);
			inLabel = new FileInputStream(labelFile);
			magicLabels = (inLabel.read() << 24) | (inLabel.read() << 16) | (inLabel.read() << 8) | (inLabel.read());
			numLabels = (inLabel.read() << 24) | (inLabel.read() << 16) | (inLabel.read() << 8) | (inLabel.read());
			
			magicImages = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
			numImages = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
			numImageRows = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
			numImageCols = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
			pixels = numImageRows * numImageCols;
			
			if(num > 0){
				numImages = num;
				numLabels = num;
			}
			images = new double[numImages][pixels];
			labels = new double[numLabels][10];

			for(int i = 0; i < numImages && i < numLabels; i++){
				for(int j = 0; j < pixels; j++){
					images[i][j] = inImage.read() & 0xFF;
					images[i][j] /= 255.0;
				}
				if(i % 1000 == 0){
                                    controller.setProgress(((double)i )/ numImages);
				}
				labels[i][inLabel.read() & 0xFF] = 1.0;
			}
			
			
		}catch(FileNotFoundException e){
                    e.printStackTrace();
		}catch(IOException e){
                    e.printStackTrace();
		}
		try{
                    inImage.close();
		}catch(IOException e){
                    e.printStackTrace();
		}
		try{
                    inLabel.close();
		}catch(IOException e){
                    e.printStackTrace();
		}
		if(isTestData){
			net.testImages = images;
			net.testLabels = labels;
		}
		else{
			net.images = images;
			net.labels = labels;
		}
	}
}
