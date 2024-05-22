package rahulshettyacademy.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{

	/* After executing the test, after completion of executing all the listener methods, then the test will
	 		come to this block and ask whether it should retry execution for the failed tests.
	 * Create a variable called count and initially the variable count will be zero
	 * maxTry defines that maximum number of retry is 1
	 * In the if condition initial count will be 0 which is less than maxTry 1 then the condition will return
	  		true and the test will be rerun, count++ will now set the count to 1, in the next loop, count
	  		is 1<1 then the condition returns false, then test will not rerun.
	  * Now how the test will know about this rerun for this in the test update this attribute 
	  		(retryAnalyzer = Retry.class) retryAnalyzer = class name.
	  * If you did not give the retryAnalyzer attribute in the test it wont rerun.
	 */
	int count =0;
	int maxTry = 1;
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		if(count<maxTry)
		{
			count++;
			return true;
		}
		return false;
	}

}
