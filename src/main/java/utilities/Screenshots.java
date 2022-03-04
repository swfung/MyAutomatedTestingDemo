package utilities;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;

import com.cucumber.listener.Reporter;

import base.DriverContext;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class Screenshots {

	public static String takeScreenshot(AppiumDriver<MobileElement> driver, String fileName) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		fileName = "screenshots/" + fileName + "_" + timeStamp + ".png";
		String directory = "output/";
		File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sourceFile, new File(directory + fileName));

		return fileName;
	}
	
	public static String takeScreenshot(String fileName) throws IOException {
		fileName = "screenshots/" + fileName + ".png";
		String directory = "output/";
		File sourceFile = ((TakesScreenshot) DriverContext.getDriver()).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sourceFile, new File(directory + fileName));

		return fileName;
	}
	
	public static File captureElement(AppiumDriver<MobileElement> driver, String fileName, MobileElement element) {
		File elementImg;
		
		//String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		fileName = "output/screenshots/" + fileName //+ "_" + timeStamp 
				+ ".png";
		elementImg = new File(fileName);
		
		File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		int ImageWidth = element.getSize().getWidth();
		int ImageHeight = element.getSize().getHeight();
		Point point = element.getLocation();
		int xcord = point.getX();
		int ycord = point.getY();
		BufferedImage img;
		try {
			img = ImageIO.read(screen);
			
			BufferedImage dest = img.getSubimage(xcord, ycord, ImageWidth, ImageHeight);
			ImageIO.write(dest, "png", screen);
			
			FileUtils.copyFile(screen, elementImg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return elementImg;
	}
	
	public static String takeScreenshotOfElement(AppiumDriver<MobileElement> driver, String fileName, MobileElement element) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		fileName = "screenshots/" + fileName + "_" + timeStamp + ".png";
		String directory = "output/";
		//File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		int ImageWidth = element.getSize().getWidth();
		int ImageHeight = element.getSize().getHeight();
		Point point = element.getLocation();
		int xcord = point.getX();
		int ycord = point.getY();
		BufferedImage img = ImageIO.read(screen);
		BufferedImage dest = img.getSubimage(xcord, ycord, ImageWidth, ImageHeight);
		ImageIO.write(dest, "png", screen);
		FileUtils.copyFile(screen, new File(directory + fileName));

		return fileName;
	}
	
    
	public static float getImagesSimilarity(AppiumDriver<MobileElement> driver, MobileElement element, String fileName)
			throws IOException {
		// Capture entire page screenshot as buffer.
		// Used TakesScreenshot, OutputType Interface of selenium and File class of java
		// to capture screenshot of entire page.
		
		File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
		// Used selenium getSize() method to get height and width of element.
		// Retrieve width of element.
		int ImageWidth = element.getSize().getWidth();
		// Retrieve height of element.
		int ImageHeight = element.getSize().getHeight();

		// Used selenium Point class to get x y coordinates of Image element.
		// get location(x y coordinates) of the element.
		Point point = element.getLocation();
		int xcord = point.getX();
		int ycord = point.getY();

		// Reading full image screenshot.
		BufferedImage img = ImageIO.read(screen);

		// cut Image using height, width and x y coordinates parameters.
		BufferedImage dest = img.getSubimage(xcord, ycord, ImageWidth, ImageHeight);
		ImageIO.write(dest, "png", screen);

		// Used FileUtils class of apache.commons.io.
		// save Image screenshot In D: drive.
		String fileA = "output/screenshots/imageComparison/" + fileName + "_A.png";
		FileUtils.copyFile(screen, new File(fileA));
		String fileB = "output/screenshots/imageComparison/" + fileName + "_B.png";
		// FileUtils.copyFile(screen, new File(fileB));

		return compareImage(new File(fileA), new File(fileB));
	}

	public static float compareImage(File fileA, File fileB) {

		try {
			Reporter.addScreenCaptureFromPath(fileA.getAbsolutePath());
			Reporter.addScreenCaptureFromPath(fileB.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		float percentage = 0;
		try {
			// take buffer data from both image files //
			BufferedImage biA = ImageIO.read(fileA);
			DataBuffer dbA = biA.getData().getDataBuffer();
			int sizeA = dbA.getSize();
			BufferedImage biB = ImageIO.read(fileB);
			DataBuffer dbB = biB.getData().getDataBuffer();
			int sizeB = dbB.getSize();
			int count = 0;
			// compare data-buffer objects //
          if (sizeA == sizeB) {
			for (int i = 0; i < Math.min(sizeA, sizeB); i++) {
				if (dbA.getElem(i) == dbB.getElem(i)) {
					count = count + 1;
				}
			}
			percentage = (count * 100) / Math.min(sizeA, sizeB);
            } else {
                System.out.println("Both the images are not of same size");
            }
		} catch (Exception e) {
			System.out.println("Failed to compare image files ...");
		}
		return percentage;
	}
	
	public static boolean isImageMatch(File image1, File image2) {
		boolean matched = true;

		BufferedImage img1 = null;
		BufferedImage img2 = null;
		try {
			img1 = ImageIO.read(image1);
			img2 = ImageIO.read(image2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		double d = CompareImageUtil.getImageDifferencePercent(img1, img2);
		System.out.println("Image Compare Difference => " + d);
		if ((int) (d*100) > 10) {
			matched = false;
		}
		return matched;
	}
	
	public static void main(String[] args) throws Exception {
		boolean match = isImageMatch(new File("/Users/aiait/eclipse-workspace_as/AIA_Connect/output/screenshots/rememberMe1.png"),
				new File("/Users/aiait/eclipse-workspace_as/AIA_Connect/output/screenshots/rememberMe2.png"));
	
		System.out.println(match);
	}

}