package base;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;

import com.experitest.appium.SeeTestClient;

import io.cucumber.java.Scenario;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import test.TestRun;
import utilities.AppiumUtil;


public class Base {
	public static Scenario scenario;
	private static AppiumDriverLocalService service;
	private static String caseID;
	protected static SeeTestClient client;
	private static boolean original_flag, changed_flag;
	protected static String error_msg = "Sorry! Test failed !";
	public static String platform;
	private static long start_time;
	private static String url;

	public static void setStartTime() {
		start_time = System.currentTimeMillis();
	}

	public static String getRunTime() {
		long run_time = System.currentTimeMillis() - start_time;
		long currentMS = run_time % 1000;
		long totalSeconds = run_time / 1000;
		long currentSecond = totalSeconds % 60;
		long totalMinutes = totalSeconds / 60;
		long currentMinute = totalMinutes % 60;

		String use_time = String.valueOf(currentMinute) + "m" + String.valueOf(currentSecond) + "."
				+ String.valueOf(currentMS) + "s";
		return use_time;
	}

	public static void setErrorMsg(String msg) {
		error_msg = msg;
	}

	public static void setFlag(boolean value) {
		if (value) {
			original_flag = true;
			changed_flag = false;
		} else {
			original_flag = false;
			changed_flag = true;
		}
			client.setShowPassImageInReport(original_flag);
			client.setShowReport(original_flag);
	}

	public static SeeTestClient getClient() {
		return client;
	}

	public static void addCapture(String msg, boolean status) {
		addCapture(msg, status, 0);
	}

	// when test failed ,add error_msg
	public static void addCapture() {
		client.setShowReport(true);
		client.report(error_msg, false);
	}

	public static void addCapture(String msg, boolean status, int time) {
		System.out.println("add capture for " + msg);
		if (changed_flag) {
			client.setShowReport(changed_flag);
			client.setShowPassImageInReport(changed_flag);
			try {
				Thread.sleep(time);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			client.report(msg, status);
			client.setShowPassImageInReport(original_flag);
			client.setShowReport(original_flag);

		} else {
			client.report(msg, status);
		}
	}

	public static String getCaseID() {
		return caseID;
	}

	public static void setCaseID(String caseID) {
		Base.caseID = caseID;
	}

	public static void setCapabilities() throws IOException {
		String reportDirectory = "reports";
		String reportFormat = "xml";
		String fileSeparator = "/";
		String projectBaseDirectory = System.getProperties().getProperty("user.dir");
		String appPackage = "hko.MyObservatory_v1_0";
		String appActivity = ".AgreementPage";

		ITestContext itc = test.TestRun.itc;
		if (itc == null) {
			itc = test.TestRun.itc;
		}

		// get device setting from testng.xml
		String device = itc.getCurrentXmlTest().getParameter("device");
		String platform = itc.getCurrentXmlTest().getParameter("platform");
		url = itc.getCurrentXmlTest().getParameter("url");
		String accessKey = itc.getCurrentXmlTest().getParameter("accessKey");


		if ("".equals(url)) {
			url = "https://aia.experitest.com/wd/hub";
		}
		//setCapability
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("testName", caseID);
		dc.setCapability("instrumentApp", true);
		dc.setCapability(MobileCapabilityType.UDID, device);
		dc.setCapability("platformName", platform);
		dc.setCapability("accessKey", accessKey);
		dc.setCapability(MobileCapabilityType.UDID, device);
		dc.setCapability("platformName", platform);

		if (platform.equalsIgnoreCase("ANDROID")) {
			System.out.println("Connected to Android device");
			dc.setCapability(MobileCapabilityType.UDID, device);
			dc.setCapability("platformName", platform);
			dc.setCapability("appPackage", appPackage);
			dc.setCapability("appActivity", appActivity);
			dc.setCapability("instrumentApp", false);
			DriverContext.setDriver((AppiumDriver<MobileElement>) (new AndroidDriver<MobileElement>(new URL(url), dc)));
		} else {
			System.out.println("Connected to IOS device");
			appPackage = "locspc";
			dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, appPackage);   //Comment to skip app restart
			dc.setCapability("instrumentApp", true);
			dc.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,200);
			DriverContext.setDriver((AppiumDriver<MobileElement>) (new IOSDriver<MobileElement>(new URL(url), dc)));
		}

		System.out.println("=============create a new driver!");
		DriverContext.setDeviceModel();
		DriverContext.setDeviceOS();
		DriverContext.setDeviceName();

		client = new SeeTestClient(DriverContext.getDriver());

		client.setProjectBaseDirectory(projectBaseDirectory);
		setFlag(true);//enable screenshot

		if (!url.contains("experitest")) {
			client.setReporter(reportFormat, projectBaseDirectory + fileSeparator + reportDirectory + fileSeparator, caseID);
		}
		client.setSpeed("Fast");
		setStartTime();
	}

	public static void renameReportFolder(String status) {
		// pass,fail,skip
		String report_path = client.generateReport(false);
		System.out.println(report_path);
		if (url.contains("experitest")) {
			return;
		}

		String parentDir = report_path.substring(0, report_path.lastIndexOf(File.separator));
		File newFolder = null;
		File fail_Folder = null;
		boolean delete_fail_flag = false;
		switch (status) {
		case "pass":
			newFolder = new File(parentDir + File.separator + caseID);
			try {
				fail_Folder = new File(parentDir + File.separator + caseID + "_FAIL");
				if (fail_Folder.exists() && fail_Folder.isDirectory()) {
					System.out.println("has past failed report!");
					delete_fail_flag = true;
				} else {
					System.out.println("does not has past failed report!");
				}
			} catch (Exception e) {
			}
			break;
		case "fail":
			newFolder = new File(parentDir + File.separator + caseID + "_FAIL");
			break;
		default:
			newFolder = new File(parentDir + File.separator + caseID + "_SKIP");
			break;
		}
		if (newFolder.exists() && newFolder.isDirectory()) {
			System.out.println("new folder name already exist,delete it");
			try {
				FileUtils.deleteDirectory(newFolder);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// newFolder.delete();
		}
		if (delete_fail_flag) {
			System.out.println("delete past fail report");
			try {
				FileUtils.deleteDirectory(fail_Folder);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		new File(report_path).renameTo(newFolder);
		System.out.println("report renamed to " + caseID);
	}

	public void delay(long delaySec) {
		try {
			Thread.sleep(delaySec * 1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public void checkElementDisplayed(MobileElement element, String screenshotName) {
		try {
			Assert.assertTrue(AppiumUtil.isElementPresentWithCheckLoading(element, 60));
			addCapture(screenshotName, true);
		} catch (Error e) {
			addCapture(screenshotName, false);
		}
	}

}