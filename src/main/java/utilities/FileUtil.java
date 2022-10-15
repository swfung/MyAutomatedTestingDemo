package utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

public class FileUtil {
	public static void getResolution(String filePath) {
		File file = new File(filePath);
		FileChannel fc = null;
		if(file.exists() && file.isFile()){
		try {
		FileInputStream fs = new FileInputStream(file);
		fc = fs.getChannel();
		System.out.println(fc.size() + "-----fc.size()");
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
		}
		System.out.println(file.length() + "-----file.length  B");
		System.out.println(file.length() * 1024 + "-----file.length  kb");
		BufferedImage bi = null;
		try {
		bi = ImageIO.read(file);
		} catch (IOException e) {
		e.printStackTrace();
		}

		int width = bi.getWidth();
		int height = bi.getHeight();

		System.out.println("宽：像素-----" + width + "高：像素"  + height);


		}
	
	public static void main(String[] args) throws Exception {
		getResolution("/Users/aiait/eclipse-workspace_as/AIA_Connect/output/screenshots/Language Option.png");
	}
}
