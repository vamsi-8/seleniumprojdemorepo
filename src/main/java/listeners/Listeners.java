package listeners;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import resources.Base;
import utilities.ExtentReporter;

public class Listeners extends Base implements ITestListener {
	
	WebDriver driver = null;
	ExtentReports extentreport = ExtentReporter.getExtentReport();
	ExtentTest extentTest;
	//Because of thread safe we using this line
	ThreadLocal<ExtentTest> extentTestThread = new ThreadLocal<ExtentTest>();
	
	@Override
	public void onTestStart(ITestResult result) {
		
		String testName = result.getName();
		 extentTest = extentreport.createTest(testName);
		 extentTestThread.set(extentTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		String testName = result.getName();
		//extentTest.log(Status.PASS,testName+" got passed");
		//because of thread safe
		extentTestThread.get().log(Status.PASS,testName+" got passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		
		String testMethodName = result.getName();
		
		//extentTest.fail(result.getThrowable());
		//because of thread safe
	    extentTestThread.get().fail(result.getThrowable());
		
		try {
			driver = (WebDriver)result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
		} catch (Exception e){
			
			e.printStackTrace();
		}
		try {
	         String screenshotFilePath = ScreenShot(testMethodName,driver);
	         extentTestThread.get().addScreenCaptureFromPath(screenshotFilePath, testMethodName);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		
	}

	@Override
	public void onStart(ITestContext context) {
		
	}

	@Override
	public void onFinish(ITestContext context) {
		
		extentreport.flush();
	}
	
	 

}
