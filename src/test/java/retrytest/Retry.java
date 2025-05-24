package retrytest;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {


    private int retryCount=0;
    private int maxRetry=2;
    @Override
    public boolean retry(ITestResult iTestResult) {
        if(retryCount<maxRetry){

            retryCount ++;
            return true;
        }

        return false;
    }
}
