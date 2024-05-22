package rahulshettyacademy.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import rahulshettyacademy.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener{
	
	/*ITestListener is an interface provided by TestNG 
	 * If the methods are not displaying in the class, please perform below actions:
	 * 		Add unimplemented methods in Listener:
			Right click (where u have generate this interface) --> go to source---> 
			click on overide/implement methods --> select the check boxes for the ITest listener 
			(make sure all check box inside it should be checked )
			---click on oK....Then u r ready to Go. */
	
	//As this is a static method we can access the same using class name.methodname without creating object.
	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); //Thread safe
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestStart(result);
		/* Before any test, control will reach to onTestStart method
		 * Using extent.createTest(path); we are telling this method to create extent report, by putting
		 		this details here we are generically creating the reports
		 * To capture the test execution details configure below details and methods:
		 * result variable in the onTestStart method will be having the information of the test which
		 		is being executed, by using that variable we need to configure the extent.createTest(path);
		 		to get the test names dynamically.
		 * test holds the entry of your report about your test case*/
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);//Unique thread ID (Error Validation test) -> test
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSuccess(result);
		//If the test reaches this method, then the test is passed then define below details.
		extentTest.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailure(result);
		test.log(Status.FAIL, "Test Failed");
		//To get the error message of the failed test cases:
		extentTest.get().fail(result.getThrowable());
		/*we need to provide the path of the screenshot
		 * Screenshot details are available in the BaseTest class, inherit (extends) BaseTest to this class
		 		public class Listeners extends BaseTest implements ITestListener{
		 * call the method getScreenShot from BaseTest
		 * Provide the test name as argument result.getMethod().getMethodName() 
		 * As getScreenshot method returns the path, save the path in a variable called filePath
		 * pass the file path to addScreenCaptureFromPath method
		 * provide the test name as argument
		 * Surround with try catch and initialize variable*/
		
		/*Definition of below code:
		 * First hover the cursor and import the WebDriver
		 * Hover the cursor and select Surround with try/catch
		 * Try catch mentions different reasons for failure: IllegalArgumentException, IllegalAccessException
		 		NoSuchFieldException, SecurityException etc.., showing multiple reasons, your test may fail
		 * Instead of multiple class exceptions we can mention one generic exception, if that exception occurs then print it
		 * Exception is the parent class of all the exceptions
		 * getField("driver") - fields are associated with class level not method level.
		 * getTestClass() refers to <class name="rahulshettyacademy.tests.ErrorValidationsTest"/>
		 * getRealClass() refers to public void loginErrorValidation()
		 * From that real class whatever field that driver is using get it
		 * 
		 */
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
					.get(result.getInstance());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		/*catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		String filePath = null;
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onFinish(context);
		extent.flush();
	}
	
	

}
