package test;

import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;


@CucumberOptions(
		monochrome = true,
		features = {"src/feature"},
		glue = { "stepdefinition" },
		tags = "@Demo and @ExtendedOutlook"
		)

public class TestRun extends AbstractTestNGCucumberTests {
	public static ITestContext itc;
	
	@BeforeSuite
	public void beforeSuite(ITestContext itc) throws Exception {
		this.itc = itc;
	}
}
