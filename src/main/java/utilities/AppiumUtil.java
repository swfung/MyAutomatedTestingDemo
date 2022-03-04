package utilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.Base;
import base.BasePage;
import base.DriverContext;

import com.ConstantFile;

public class AppiumUtil {

	//private static AppiumDriver<MobileElement> driver = DriverContext.getDriver();
	protected static Logger logger = Logger.getLogger(AppiumUtil.class.getName());
	private static String strPlatform = DriverContext.getDeviceOS();
	private static String language = "English";
	private static int screenHeight = DriverContext.driver.manage().window().getSize().getHeight();
	private static int screenwidth = DriverContext.driver.manage().window().getSize().getWidth();
	private static int eAppVisibleTop = 148;
	private static int eAppVisibleBottom = 1538;
	
	private static int display_x_min = (int)(screenwidth*0.1);
	private static int display_x_max = (int)(screenwidth*0.9);
	private static int display_y_min = (int)(screenHeight*0.1);
	private static int display_y_max = (int)(screenHeight*0.87); // 0.9
	private static int swipe_x_small = (int)(screenwidth*0.2);
	private static int swipe_x_big = (int)(screenwidth*0.8);
	private static int swipe_x_little_small = (int)(screenwidth*0.4);
	private static int swipe_x_little_big = (int)(screenwidth*0.6);
	private static int swipe_y_small = (int)(screenHeight*0.2);
	private static int swipe_y_big = (int)(screenHeight*0.8);
	private static int swipe_y_little_small = (int)(screenHeight*0.4);
	private static int swipe_y_little_big = (int)(screenHeight*0.6);
	private static int scale_down_y = 90;
	private static float scale_down_all = (float) 0.945;

	
	public static boolean isElementPresent(MobileElement element, int timeout) {
		// WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			new WebDriverWait(DriverContext.driver, timeout).until(ExpectedConditions.visibilityOf(element));
			sleepSeconds(ConstantFile.TIMEOUT2);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	
	public static boolean isElementPresent(By by, int timeout) {
		// WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			new WebDriverWait(DriverContext.driver, timeout).until(ExpectedConditions.presenceOfElementLocated(by));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public static boolean isElementPresentWithCheckLoading(MobileElement element, int timeout) {
		WebDriverWait wait = new WebDriverWait(DriverContext.driver, timeout);
		try {
			waitElementDisappear();
			wait.until(ExpectedConditions.visibilityOf(element));
			sleepSeconds(ConstantFile.TIMEOUT2);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	
	public static void waitElementDisappear() throws Exception {
		waitElementDisappear(ConstantFile.TIMEOUT3);
	}

	public static void waitElementDisappear(int timeout) throws Exception {
		MobileElement element = null;
		WebDriverWait wait = new WebDriverWait(DriverContext.driver, timeout);

		language = ExcelUtil.getValue("Language").trim();

		String loadingIconXpath = "";
		if ("IOS_APP".equalsIgnoreCase(DriverContext.getDeviceOS())) {
			//loadingIconXpath = Xpath_Locator.b99_Loading;
		} else {
			//loadingIconXpath = Xpath_Locator_Android.m_Loading;
		}

		try {
			element = (MobileElement) wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(loadingIconXpath)));
			System.out.println("Virtual Keyboard present..");
		} catch (Exception e) {
			System.out.println("Virtual Keyboard not present..");
		}

		if (element != null) {
			int count = 0;
			while (count < 300) {
				Thread.sleep(1000);
				System.out.println("Sleep 1 sec...");
				count++;
				try {
					element = DriverContext.driver.findElement(By.xpath(loadingIconXpath));
					System.out.println("inProgress still exists, already waited " + (1 * count) + " seconds..");
				} catch (Exception e) {
					element = null;
				}
				if (element == null) {
					System.out.println("good, inprogress disappeared!!!");
					break;
				}
			}
		}

		System.out.println("Exit waitElementDisappear()...");
		// set step status
		// client.report("loading.gif is disapper", true);
	}
	
	public static void swipeElementVisible(MobileElement element) throws Exception {
		swipeElementVisible(element,ConstantFile.TIMEOUT20);
	}
	
	public static void swipeLeftElementVisible(MobileElement element) throws Exception {
		swipeLeftElementVisible(element,ConstantFile.TIMEOUT20);
	}
	
	public static MobileElement waitElementClickable(MobileElement element,int timeout){
		WebDriverWait wait = new WebDriverWait(DriverContext.driver, timeout);
		try{
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		}catch(Exception e){
			
		}
		return element;
	}
	
	public static MobileElement waitElementClickable(By by,int timeout){
		WebDriverWait wait = new WebDriverWait(DriverContext.driver, timeout);
		MobileElement element = null;
		try{
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
		element = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(by));
		}catch(Exception e){
			
		}
		return element;
	}
	
	public static MobileElement swipeElementVisible(MobileElement element, int timeout) throws Exception {
		// try {
		// new WebDriverWait(driver,
		// timeout).until(ExpectedConditions.visibilityOf(element));
		// } catch (Exception e) {
		// if(!element.isEnabled()) {
		// logger.info("Failed to find target element in current page");
		// return element;
		// }
		// }
		boolean visible = false;
		try {
			//waitElementDisappear();
			new WebDriverWait(DriverContext.driver, timeout).until(ExpectedConditions.visibilityOf(element));
//			new WebDriverWait(DriverContext.driver, timeout).until(ExpectedConditions.elementToBeClickable(element));
			visible = true;
			//logger.info("Element is visiable now...");
		} catch (Exception e) {
			visible = false;
			//logger.info("Element is not visiable now...");
		}
		
		int i = 1;
		while ((!visible) && (i <= 15)) {
			//Base.addCapture("swipe Element to Visible :" + i, true);
			//swipeDownOnLeftSide();
			swipeDown();
			//logger.info("Swiped Down...");
			try {
				visible = element.isDisplayed();
			} catch (Exception e) {
				visible = false;
			}
			//logger.info("visible: " + visible);
			i += 1;
		}

		int y = element.getCenter().getY();
		int swipeLittleTimes = 0;
		while ( y >display_y_max) {
			//logger.info("element is not shown in screen,y is " + y);
			swipeDownLittle();
			swipeLittleTimes++;
			y = element.getLocation().getY();
			if (swipeLittleTimes > 3) {
				System.err.println("swipe error");
				break;
			}
		}
		//logger.info("Swiped Element into view, exit func swipeElementVisible()...");
		return element;
	}
	
	public static MobileElement fastSwipeElementVisible(MobileElement element, int timeout) throws Exception {
		// try {
		// new WebDriverWait(driver,
		// timeout).until(ExpectedConditions.visibilityOf(element));
		// } catch (Exception e) {
		// if(!element.isEnabled()) {
		// logger.info("Failed to find target element in current page");
		// return element;
		// }
		// }
		boolean visible = false;
		try {
			waitElementDisappear();
			new WebDriverWait(DriverContext.driver, timeout).until(ExpectedConditions.visibilityOf(element));
			new WebDriverWait(DriverContext.driver, timeout).until(ExpectedConditions.elementToBeClickable(element));
			visible = true;
			//logger.info("Element is visiable now...");
		} catch (Exception e) {
			visible = false;
			//logger.info("Element is not visiable now...");
		}
		
		int i = 1;
		while ((!visible) && (i <= 50)) {
			//Base.addCapture("swipe Element to Visible :" + i, true);
			//swipeDownOnLeftSide();
			fastSwipeDown();
			//logger.info("Swiped Down...");
			try {
				visible = element.isDisplayed();
			} catch (Exception e) {
				visible = false;
			}
			//logger.info("visible: " + visible);
			i += 1;
		}

		int y = element.getCenter().getY();
		int swipeLittleTimes = 0;
		while ( y >display_y_max) {
			//logger.info("element is not shown in screen,y is " + y);
			swipeDownLittle();
			swipeLittleTimes++;
			y = element.getLocation().getY();
			if (swipeLittleTimes > 3) {
				System.err.println("swipe error");
				break;
			}
		}
		//logger.info("Swiped Element into view, exit func swipeElementVisible()...");
		return element;
	}
	
	public static MobileElement swipeLeftElementVisible(MobileElement element, int timeout) throws Exception {
		boolean visible = false;
		try {
			new WebDriverWait(DriverContext.driver, timeout).until(ExpectedConditions.visibilityOf(element));
			new WebDriverWait(DriverContext.driver, timeout).until(ExpectedConditions.elementToBeClickable(element));
			visible = true;
		} catch (Exception e) {
			visible = false;
		}
		
		int i = 1;
		while ((!visible) && (i <= 3)) {
			swipeDownOnLeftSide();
			try {
				visible = element.isDisplayed();
			} catch (Exception e) {
				visible = false;
			}
			i += 1;
		}

		int y = element.getCenter().getY();
		int swipeLittleTimes = 0;
		while ( y >display_y_max) {
			swipeLeftDownLittle();
			swipeLittleTimes++;
			y = element.getLocation().getY();
			if (swipeLittleTimes > 3) {
				System.err.println("swipe error");
				break;
			}
		}
		return element;
	}

	public static MobileElement swipeUpElementVisible(MobileElement element, int timeout) throws Exception {
		boolean visible = false;
		try {
			waitElementDisappear();
			new WebDriverWait(DriverContext.driver, timeout).until(ExpectedConditions.visibilityOf(element));
			new WebDriverWait(DriverContext.driver, timeout).until(ExpectedConditions.elementToBeClickable(element));
			visible = true;
			logger.info("Element is visiable now...");
		} catch (Exception e) {
			visible = false;
			logger.info("Element is not visiable now...");
		}

		int i = 1;
		while (!visible) {
			Base.addCapture("swipe Element to Visible :" + i, true);
			swipeDownOnLeftSide();
			logger.info("Swiped Down...");
			visible = element.isDisplayed();
			logger.info("visible: " + visible);
			i += 1;
		}

		int y = element.getCenter().getY();
		int swipeLittleTimes = 0;
		while ( y < display_y_min) {
			logger.info("element is not shown in screen,y is " + y);
			swipeUpLittle();
			swipeLittleTimes++;
			y = element.getLocation().getY();
			if (swipeLittleTimes > 15) {
				System.err.println("swipe error");
				break;
			}
		}
		logger.info("Swiped Element into view, exit func swipeElementVisible()...");
		return element;
	}

	public static void waitUntil(By by, int TimeOutSeconds) throws Exception {
		logger.info("Waiting for element displayed...");
		WebDriverWait wait = new WebDriverWait(DriverContext.driver, TimeOutSeconds);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
		waitElementDisappear();
		logger.info("Expected Element Displayed...");
	}

	public static MobileElement swipeTransverseElementVisible(MobileElement element, By by) throws Exception {
		WebDriverWait wait = new WebDriverWait(DriverContext.driver, ConstantFile.TIMEOUT120);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
		
		int x = element.getLocation().getX();
		int y = element.getLocation().getY();

		sleepSeconds(5);
		boolean visible = element.isDisplayed();
		while (!visible) {
			swipeLeftTop(y);
			visible = element.isDisplayed();
		}
		int swipeLittleTimes = 0;
		while (x < (int)(0.1*screenwidth) || x > (int)(0.9*screenwidth)) {
			logger.info("element is not shown in screen,x is " + x);
			swipeLeftTopLittle(y);
			swipeLittleTimes++;
			x = element.getLocation().getX();
			if (swipeLittleTimes > 5) {
				System.err.println("swipe error");
				break;
			}
		}
		return element;
	}

	public MobileElement swipeElementVisibleWithWait(MobileElement element) {
		logger.info("Start of function swipeElementVisibleWithWait");
		for (int i = 1; i < 11; i++) {
			try {
				Thread.sleep(2000);
				if (element.getLocation().getY() <= DriverContext.driver.manage().window().getSize().getHeight() - 200) {
					break;
				}
				logger.info("element.getLocation().getY(): " + element.getLocation().getY());
			} catch (Exception e) {
			}
			swipeDown();
			// swipeDownOnLeftSide();
			System.out.println("Swiping down...");
			System.out.println("Swiped times:" + i);
		}

		if (element == null) {
			logger.info("element not found after swipe 10 times");
			throw new RuntimeException("element not found");
		}
		int y = element.getLocation().getY();
		logger.info("element.getLocation().getY(): " + y);
		int swipeLittleTimes = 0;
		while (y < 450 || y > 1550) {
			logger.info("element is not shown in screen,y is " + y);
			swipeDownLittle();
			swipeLittleTimes++;
			y = element.getLocation().getY();
			if (swipeLittleTimes > 2) {
				System.err.println("swipe error");
				break;
			}
		}
		logger.info("exiting function");
		return element;
	}

	public static void sleepSeconds(int seconds) throws InterruptedException {
		Thread.sleep(seconds * 1000);
	}

	public static void dragCoordinates(AppiumDriver<MobileElement> driver, int x1, int y1, int x2, int y2, int time) {
		//System.out.println(
		//		"drag from point (" + x1 + ", " + y1 + ") to point (" + x2 + ", " + y2 + ") in " + time + " ms..");
		DriverContext.driver.executeScript(
				"seetest:client.dragCoordinates(" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ", " + time + ")");

	}

	public static void swipeCoordination(int fromX, int fromY, int toX, int toY, int duration) {
		dragCoordinates(DriverContext.driver, fromX, fromY, toX, toY, duration);
	}

	public static void swipeDownOnLeftSide() {
//		dragCoordinates(driver, 230, 1400, 230, 600, 1500);
		dragCoordinates(DriverContext.driver, 230, swipe_y_little_big, 230, swipe_y_little_small, 1500);
	}

	public static void swipeDown() {
//		dragCoordinates(driver, 450, 1400, 450, 600, 1500);
//		dragCoordinates(driver, 1600, swipe_y_big, 1600, swipe_y_small, 2500);
		dragCoordinates(DriverContext.driver, 1600, swipe_y_big, 1600, swipe_y_little_small, 2500);
	}
	
	public static void fastSwipeDown() {
//		dragCoordinates(driver, 450, 1400, 450, 600, 1500);
		dragCoordinates(DriverContext.driver, 1600, swipe_y_big, 1600, swipe_y_small, 1000);
	}

	public static void swipeUp() {
//		dragCoordinates(driver, 450, 1400, 450, 600, 1500);
		dragCoordinates(DriverContext.driver, 450, swipe_y_small, 450, swipe_y_big, 1500);
	}

	public static void swipeLeftTop() {
//		dragCoordinates(driver, 1000, 520, 400, 520, 1500);
		dragCoordinates(DriverContext.driver, swipe_x_big, 520, swipe_x_small, 520, 1500);
	}
	
	public static void swipeLeftTop(int y) {
//		dragCoordinates(driver, 1000, 520, 400, 520, 1500);
		dragCoordinates(DriverContext.driver, swipe_x_big, y, swipe_x_small, y, 1500);
	}

	public static void swipeLeftMiddle() {
//		dragCoordinates(driver, 1000, 900, 400, 900, 1500);
		dragCoordinates(DriverContext.driver, swipe_x_big, 900, swipe_x_small, 900, 1500);
	}

	public static void swipeDownLittle() {
//		dragCoordinates(driver, 450, 1200, 450, 700, 1500);
		dragCoordinates(DriverContext.driver, 1600, swipe_y_little_big, 1600, swipe_y_little_small, 1500);
	}
	
	public static void swipeLeftDownLittle() {
//		dragCoordinates(driver, 450, 1200, 450, 700, 1500);
		dragCoordinates(DriverContext.driver, 230, swipe_y_little_big, 230, swipe_y_little_small, 1500);
	}

	public static void swipeUpLittle() {
//		dragCoordinates(driver, 450, 1200, 450, 700, 1500);
		dragCoordinates(DriverContext.driver, 450, swipe_y_little_small, 450, swipe_y_little_big, 1500);
	}

	public static void swipeLeftTopLittle() {
//		dragCoordinates(driver, 800, 260, 500, 260, 1500);
		dragCoordinates(DriverContext.driver, swipe_x_little_big, 260, swipe_x_little_small, 260, 1500);
	}
	
	public static void swipeLeftTopLittle(int y) {
		dragCoordinates(DriverContext.driver, (int)(0.6*screenwidth), y, (int)(0.4*screenwidth), y, 1500);
	}
	
	public static MobileElement swipeToElmentWithFieldName(String fieldname) {
		MobileElement mobileElement = null;
		String xpath = "//*[@text='" + ExcelUtil.getValue(fieldname).trim() + "']";
		System.out.println(xpath);
		for(int i = 1; i<= 10; i++) {
			try {
				Thread.sleep(2000);
				mobileElement = DriverContext.driver.findElement(By.xpath(xpath));
			}catch (Exception e) {
				
			}
			
			if(mobileElement != null) {
				return mobileElement;
			}
			swipeDown();
			Base.addCapture("swipe to element times : " + i, true);
		}
		throw new RuntimeException("Cannot find element for " + ExcelUtil.getValue(fieldname));
	}
	
	public static MobileElement swipeToElmentWithFieldNameInInvesment(String fieldname) {
		MobileElement mobileElement = null;
		String xpath = "(//*[contains(@text,'" + ExcelUtil.getValue(fieldname).trim() + "')])[2]";
		System.out.println(xpath);
		for(int i = 1; i<= 10; i++) {
			try {
				mobileElement = DriverContext.driver.findElement(By.xpath(xpath));
			}catch (Exception e) {
				
			}
			
			if(mobileElement != null) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return mobileElement;
			}
			swipeDown();
			Base.addCapture("swipe to element times : " + i, true);
		}
		throw new RuntimeException("Cannot find element for " + ExcelUtil.getValue(fieldname));
	}

	public static MobileElement swipeToElmentByValue(String value) {
		MobileElement mobileElement = null;
		String xpath = "//*[@text='" + value + "']";
		for(int i = 1; i<= 10; i++) {
			try {
				mobileElement = DriverContext.driver.findElement(By.xpath(xpath));
			}catch (Exception e) {
				
			}
			
			if(mobileElement != null) {
				return mobileElement;
			}
			swipeDown();
		}
		throw new RuntimeException("Cannot find element for " + ExcelUtil.getValue(value));
	}
		
	public static MobileElement swipeToElmentByXpath(String xpath) {
		MobileElement mobileElement = null;
		for(int i = 1; i<= 10; i++) {
			try {
				mobileElement = DriverContext.driver.findElement(By.xpath(xpath));
			}catch (Exception e) {
				
			}
			
			if(mobileElement != null) {
				return mobileElement;
			}
			swipeDown();
		}
		throw new RuntimeException("Cannot find element for " + ExcelUtil.getValue(xpath));
	}
	
	public static String transDateToChi(String text, String language) {
		if (language.equals("English")) {
			return text;
		}
		String[] tmp_date = text.split("-");
		String result = tmp_date[2] + "年" + tmp_date[0] + "月" + tmp_date[1] + "日";
		System.out.println(result);
		return result;
	}
	
	public static void selectDateInCalendar(MobileElement ele, String text) throws Exception {
		//format： mm-dd-yyyy
		long l = System.currentTimeMillis();
		Date date = new Date(l);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String[] date_today = dateFormat.format(date).split("-");
		String today_year = date_today[0];
		String today_month = changeMonthFromNum(date_today[1]);
		String case_language = BasePage.getPageLang();
		String day,month,year;

		if (text.contains("-")) {
			String[] date_targe = text.split("-");
			year = date_targe[2];
			month = changeMonthFromNum(date_targe[0]);
			day = date_targe[1];
		}else {
			 day = text.substring(0, 2);
			 month = text.substring(3, 6);
			 year = text.substring(7, 11);
		}
		
		if ("ANDROID".equalsIgnoreCase(DriverContext.getDeviceOS())) {
		
			ele.click();
			try {
				DriverContext.driver.findElement(By.xpath("//*[@text='Date of Consultation' or @text='Receipt Date' or @text='出生日期' or @text='Date of Birth']")).click();
			} catch (Exception e) {
				
			}
			DriverContext.driver.context("NATIVE_APP_INSTRUMENTED");
			DriverContext.driver.context("WEBVIEW_1");
			DriverContext.driver.context("NATIVE_APP");
			ele.click();
			DriverContext.driver.context("NATIVE_APP_INSTRUMENTED");
			DriverContext.driver.context("WEBVIEW_1");
			DriverContext.driver.context("NATIVE_APP");

//			if ("English".equals(case_language)) {
//				ele.sendKeys(text);
//				BasePage.pressEnter();
//				driver.hideKeyboard();
//			}else {
				String year_xpath = "//*[@class='android.widget.Spinner' and @text='" + today_year + "']";
				String month_xpath ="//*[@class='android.widget.Spinner' and @text='" + today_month + "']";
				String year_targe = "//*[@text='" + year + "']";
				String month_targe = "//*[@text='" + month + "']";
				String day_targe = day.startsWith("0") ?
						"//*[@class='android.widget.Spinner']//following::*[@text='" + day.substring(1) + "']":
						"(//*[@class='android.widget.Spinner']//following::*[@text='" + day + "'])[last()]";

				Thread.sleep(500);
				DriverContext.driver.findElementByXPath(year_xpath).click();
				Thread.sleep(500);
				int swipe_count =1 ;
//				MobileElement yearElement = driver.findElementByXPath(year_targe);
//				if (Integer.valueOf(today_year) < Integer.valueOf(day)) {
//					swipeUpElementVisible(yearElement,ConstantFile.TIMEOUT5).click();
//				}else {
//					swipeElementVisible(yearElement,ConstantFile.TIMEOUT5).click();
//				}
				while (swipe_count < 15) {
					try {
						DriverContext.driver.findElementByXPath(year_targe).click();
						break;
					} catch (Exception e) {
						swipeUp();
					}
					swipe_count ++;
				}
				
				Thread.sleep(500);
				try {
					DriverContext.driver.findElementByXPath(month_xpath).click();
				} catch (NoSuchElementException e) {
					// to change excting month 
					DriverContext.driver.findElementByXPath("//*[@class='android.widget.Spinner' and @text='Feb']").click();
				}

				Thread.sleep(500);
				MobileElement monthElement = DriverContext.driver.findElementByXPath(month_targe);
				
				swipeElementVisible(monthElement,ConstantFile.TIMEOUT5).click();
				
				Thread.sleep(500);
				DriverContext.driver.findElementByXPath(day_targe).click();
//				if (Integer.valueOf(day) < 22) {
//					driver.findElementByXPath(day_targe).click();
//				}else {
//					List<MobileElement> dayElements = driver.findElementsByXPath(day_targe);
//					if (dayElements.size() > 1) {
//						dayElements.get(1).click();
//					}else {
//						dayElements.get(0).click();
//					}
//				}
//			}
		}else {
			//add ios
			System.out.println("date: " + text);
			System.out.println("year: " + year);
			System.out.println("month: " + month);
			System.out.println("day: " + day);
			swipeElementVisible(ele);
				//Date today = Calendar.getInstance().getTime();
				//String[] today_list = String.valueOf(today).split(" ");
				
				String yearXpath = "(//*[@text='']/following::*[./*])[1]/*[last()]";
						//"//*[@text='" + today_list[5] + "']";
				String picker = "//*[@class='UIAPicker']";
				String monthXpath = "(//*[@text='']/following::*[./*])[1]/*[1]";
						//"//*[@text='" + today_list[1] + "']";
				ele.click();
				
				System.out.println("Select Year...");
				DriverContext.driver.findElement(By.xpath(yearXpath)).click();
				setPickerValue(picker, year);
				
				System.out.println("Select Month...");
				DriverContext.driver.findElement(By.xpath(monthXpath)).click();
				setPickerValue(picker, month);
				
				System.out.println("Select Day..." + day);
				String xpath;
				if(day.startsWith("0")){
					xpath = "(//*[@text='']/following::*[@text='"+day.substring(1)+"' and @y>0])[1]";
							//"(//*[@text='"+day.substring(1)+"' and @y>0])[1]";
				}else{
					xpath = "(//*[@text='" + day + "'])[last()]";
				}
				System.out.println(xpath);
				DriverContext.driver.findElement(By.xpath(xpath)).click();
				
				//label_Date_Of_Birth.click();
			}
	}
		
	public static void setPickerValue(String targetItemXpath,String value){
		System.out.println("Client - set picker value");
		Base.getClient().setPickerValues("NATIVE", "xpath=" + targetItemXpath, 0, 0, value);
		hideKeyboardByDone();
	}
	

    public static void hideKeyboardByDone(){
    	By by = By.xpath("//*[@text='Done']");
    	
    	if(isElementPresent(by,3)){
    		DriverContext.driver.findElement(by).click();
    	}
    }
	
	public static String changeMonthFromNum(String text) {
	     String language = ExcelUtil.getValue("language").trim();
	 	String result = null;
		if (language.equalsIgnoreCase("English")) {
			switch (text) {
			case "01":
				result = "Jan";
				break;
			case "02":
				result = "Feb";
				break;
			case "03":
				result = "Mar";
				break;
			case "04":
				result = "Apr";
				break;
			case "05":
				result = "May";
				break;
			case "06":
				result = "Jun";
				break;
			case "07":
				result = "Jul";
				break;
			case "08":
				result = "Aug";
				break;
			case "09":
				result = "Sep";
				break;
			case "10":
				result = "Oct";
				break;
			case "11":
				result = "Nov";
				break;
			case "12":
				result = "Dec";
				break;
			}
			}
		if (language.equalsIgnoreCase("Traditional Chinese")) {
			switch (text) {
			case "01":
				result = "1月";
				break;
			case "02":
				result = "2月";
				break;
			case "03":
				result = "3月";
				break;
			case "04":
				result = "4月";
				break;
			case "05":
				result = "5月";
				break;
			case "06":
				result = "6月";
				break;
			case "07":
				result = "7月";
				break;
			case "08":
				result = "8月";
				break;
			case "09":
				result = "9月";
				break;
			case "10":
				result = "10月";
				break;
			case "11":
				result = "11月";
				break;
			case "12":
				result = "12月";
				break;
			}
			}
		return result;
	}
}
