package page;

import com.Xpath_Locator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.ConstantFile;
import base.Base;
import base.BasePage;
import base.DriverContext;
import org.testng.ITestContext;
import utilities.AppiumUtil;
import utilities.Screenshots;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DemoPage extends BasePage {

	private final AppiumDriver<MobileElement> driver = DriverContext.getDriver();
	LocalDate currentDate = LocalDate.now();

	public void checkHomePage() throws IOException {
		try{
			Assert.assertTrue(AppiumUtil.isElementPresent(By.xpath(Xpath_Locator.MyObservatory),ConstantFile.TIMEOUT10));
		} catch (AssertionError e) {
			Base.setErrorMsg("HomePage can not be displayed");
			throw e;
		}
		Screenshots.takeScreenshot("checkHomePage");
	}

	public void checkCurrentDate() throws IOException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy(EEE)");
		try {
			String currentDateXpath = "//*[contains(@text,'"+currentDate.format(formatter)+"')]";
			System.out.println("Current Date is: "+currentDate.format(formatter));
			Assert.assertTrue(AppiumUtil.isElementPresent(By.xpath(currentDateXpath),ConstantFile.TIMEOUT10));
		} catch (AssertionError e) {
			Base.setErrorMsg("Current Date Verification Error");
			throw e;
		}
		Screenshots.takeScreenshot("checkCurrentDate");
	}

	public void tapsMenuBtn() throws IOException {
		try {
			Assert.assertTrue(AppiumUtil.isElementPresent(By.xpath(Xpath_Locator.menuBtn),ConstantFile.TIMEOUT10));
			driver.findElement(By.xpath(Xpath_Locator.menuBtn)).click();
		} catch (AssertionError e) {
			Base.setErrorMsg("Menu Button is not available");
			throw e;
		}
		Screenshots.takeScreenshot("tapsMenuBtn");
	}

	public void taps9DayForecastBtn() throws Exception {
		try {
			AppiumUtil.swipeToElmentByXpath(Xpath_Locator.nineDayForecastBtn);
			driver.findElement(By.xpath(Xpath_Locator.nineDayForecastBtn)).click();
		} catch (AssertionError e) {
			Base.setErrorMsg("9-Day Forecast Button is not available");
			throw e;
		}
		Screenshots.takeScreenshot("taps9DayForecastBtn");
	}

	public void tapsLocalForecastBtn() throws Exception {
		try {
			driver.findElement(By.xpath(Xpath_Locator.localForecastTab)).click();
		} catch (AssertionError e) {
			Base.setErrorMsg("Local Forecast Tab can not be tapped");
			throw e;
		}
		Screenshots.takeScreenshot("tapsLocalForecastBtn");
	}

	public void tapsExtendedOutlookBtn() throws Exception {
		try {
			driver.findElement(By.xpath(Xpath_Locator.extendedOutlookTab)).click();
		} catch (AssertionError e) {
			Base.setErrorMsg("Extended Outlook Tab can not be tapped");
			throw e;
		}
		Screenshots.takeScreenshot("tapsExtendedOutlookBtn");
	}

	public void check9DayForecastPage() throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM (EEE)");
		try {
			Assert.assertTrue(AppiumUtil.isElementPresent(By.xpath(Xpath_Locator.nineDayForecastTab),ConstantFile.TIMEOUT10));
			for(int i=1;i<=9;i++){
				LocalDate futureDate = currentDate.plusDays(i);
				String futureDateXpath = "//*[contains(@text,'"+futureDate.format(formatter)+"')]";
				System.out.println("Future Date is: "+futureDate.format(formatter));
				AppiumUtil.swipeToElmentByXpath(futureDateXpath);
				Assert.assertTrue(AppiumUtil.isElementPresent(By.xpath(futureDateXpath),ConstantFile.TIMEOUT10));
			}
		} catch (AssertionError e) {
			Base.setErrorMsg("9-Day Forecast Page have error");
			throw e;
		}
		Screenshots.takeScreenshot("check9DayForecastPage");
	}

	public void checkLocalForecastPage() throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			String currentDateXpath = "//*[contains(@text,'"+currentDate.format(formatter)+"')]";
			System.out.println("Current Date is: "+currentDate.format(formatter));
			Assert.assertTrue(AppiumUtil.isElementPresent(By.xpath(currentDateXpath),ConstantFile.TIMEOUT10));
		} catch (AssertionError e) {
			Base.setErrorMsg("Local Forecast Page have error");
			throw e;
		}
		Screenshots.takeScreenshot("checkLocalForecastPage");
	}

	public void checkExtendedOutlookPage() throws Exception {
		try {
			Assert.assertTrue(AppiumUtil.isElementPresent(By.xpath(Xpath_Locator.extendedOutlookImg),ConstantFile.TIMEOUT10));
		} catch (AssertionError e) {
			Base.setErrorMsg("No Extended Outlook Image can be shown");
			throw e;
		}
		Screenshots.takeScreenshot("checkExtendedOutlookPage");
	}
}