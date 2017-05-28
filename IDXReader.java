import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class IDXReader {
	public static int[] readImage(String file){
		File imageFile = new File(file);
		FileInputStream imageInput = null;
		int numberOfRows = 0;
		int numberOfColumns = 0;
		int numberOfImages = 0;
		int[] pixels = null;
		int amount = 0;
		byte[] bytes = null;

		
		try{
			
			imageInput = new FileInputStream(imageFile);
			bytes = new byte[imageInput.available()];
			
			System.out.println(imageInput.read(bytes));
			//System.out.println(bytes.length);
			//int magic = (bytes[bytes.length - 1]) | (bytes[bytes.length - 2] << 8) | (bytes[bytes.length - 3] << 16) | (bytes[bytes.length - 4] << 24);
			//System.out.println(magic);
			Integer value = 0;
			value = (bytes[bytes.length - 1] << 24) | (bytes[bytes.length - 2] << 16) | (bytes[bytes.length - 3] << 8) |bytes[bytes.length - 4];
			System.out.println(Integer.reverseBytes(value));
			for(int i = 0; i < 1000; i++){
				System.out.println(bytes[bytes.length - 1 - i] & 0xFF);
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		try{
			imageInput.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return pixels;
	}
}
