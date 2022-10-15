package base;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestngListenerCapture extends TestListenerAdapter{
	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
	  }
	@Override
	  public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		String error_msg = tr.getThrowable().getMessage();
		Base.setErrorMsg(error_msg);
		Base.addCapture();
		Base.renameReportFolder("fail");
	  }

	@Override
	  public void onTestSkipped(ITestResult tr) {
		 super.onTestSkipped(tr);
	  }
	 @Override
	  public void onStart(ITestContext testContext) {
		 super.onStart(testContext);
	  }
	 @Override
	  public void onFinish(ITestContext testContext) {
		 super.onFinish(testContext);
	  }	
	
}
