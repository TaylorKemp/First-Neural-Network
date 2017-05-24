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

		
		try{
			imageInput = new FileInputStream(imageFile);
			for(int i = 0; i < 100; i++){
				System.out.println((byte) imageInput.read());
			}
			
			int magic = (imageInput.read() << 24) | (imageInput.read() << 16) | (imageInput.read() << 8) | (imageInput.read());
			System.out.println(magic);
			//imageInput.read();
			numberOfImages = (imageInput.read() << 24) | (imageInput.read() << 16) | (imageInput.read() << 8) | (imageInput.read());
			System.out.println(numberOfImages);
			numberOfRows = (imageInput.read() << 24) | (imageInput.read() << 16) | (imageInput.read() << 8) | (imageInput.read());
			System.out.println(numberOfRows);
			numberOfColumns = (imageInput.read() << 24) | (imageInput.read() << 16) | (imageInput.read() << 8) | (imageInput.read());
			System.out.println(numberOfColumns);
            

			numberOfImages = (imageInput.read());
			//System.out.println(numberOfImages);
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
