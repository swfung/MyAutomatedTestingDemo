package stepdefinition;

import java.io.IOException;
import java.util.Collection;

import base.Base;
import base.DriverContext;
import io.cucumber.java.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import utilities.ExcelUtil;

public class Hook extends Base {

	@Before
	public void beforeScenario(Scenario scenario) throws IOException {
		Collection<String> tags = scenario.getSourceTagNames();
		for(String tag: tags){
			System.out.println(tag);
		}
		Base.setCaseID(scenario.getName());
		System.out.println("-----------------------------------");
		System.out.println("Starting - " + scenario.getName());
		System.out.println("-----------------------------------");

		setCapabilities();
//		ExcelUtil.readExcel(scenario.getName());
	}
	

	@After
	public void afterScenario(Scenario scenario) {
		System.out.println("-----------------------------------");
		System.out.println(scenario.getName() + " Status - " + scenario.getStatus());
		System.out.println("Run time - " + getRunTime());
		System.out.println("-----------------------------------");
			
		if ("FAILED".equalsIgnoreCase(scenario.getStatus().toString())) {
			client.setReportStatus("failed", error_msg);
		}else {
			client.setReportStatus("passed", "Test case passed");
		}
		DriverContext.getDriver().quit();
	}

}
