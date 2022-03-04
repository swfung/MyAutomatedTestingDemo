package stepdefinition;

import base.Base;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import page.DemoPage;

import java.io.IOException;

public class DemoStep extends Base {
	
	private DemoPage demoPage = new DemoPage();

	@Given("User is on homepage")
	public void checkHomePage() throws IOException {
		System.out.println("Open HomePage");
		demoPage.checkHomePage();
	}

	@Then("User checks {string}")
	public void checkElement(String element) throws Throwable {
		switch (element){
			case "Current Date":
				System.out.println("Checking Current Date is Displayed");
				demoPage.checkCurrentDate();
				break;
			case "9-Day Forecast Page":
				System.out.println("Checking 9-Day Forecast Page");
				demoPage.check9DayForecastPage();
				break;
			case "Local Forecast Page":
				System.out.println("Checking Local Forecast Page");
				demoPage.checkLocalForecastPage();
				break;
			case "Extended Outlook Page":
				System.out.println("Checking Extended Outlook Page");
				demoPage.checkExtendedOutlookPage();
		}
	}

	@And("User taps {string} button")
	public void tapsBtn(String button) throws Throwable {
		switch (button){
			case "Menu":
				System.out.println("Taps Menu Button");
				demoPage.tapsMenuBtn();
				break;
			case "9-Day Forecast":
				System.out.println("Taps 9-Day Forecast button");
				demoPage.taps9DayForecastBtn();
				break;
			case "Local Forecast":
				System.out.println("Taps Local Forecast button");
				demoPage.tapsLocalForecastBtn();
				break;
			case "Extended Outlook":
				System.out.println("Taps Extended Outlook button");
				demoPage.tapsExtendedOutlookBtn();
		}
	}
}

	