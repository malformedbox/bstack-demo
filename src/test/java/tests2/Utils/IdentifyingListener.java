package tests2.Utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.util.ArrayList;

/**
 *  IdentifyingListener used to retry failed/skipped tests
 *
 *  Note: onTestSkipped is utilized as opposed to onTestFailed because retriable failures are reported as SKIP
 *  for more info see: https://github.com/cbeust/testng/pull/698
 */

public class IdentifyingListener extends TestListenerAdapter {
    public static boolean isRetried = false;
    public static ArrayList<String> users = new ArrayList<>();
    @Override
    public void onTestSkipped(ITestResult tr) {
        IRetryAnalyzer retry = tr.getMethod().getRetryAnalyzer();
        if (retry instanceof Retry) {
            //If retry count is greater than zero, it's retried
            if (((Retry) retry).getRetryCount() > 0) {
                // sets isRetried to true
                isRetried = true;
                // if test fails, users list tracks who has run the test once already
                users.addAll(((Retry)retry).getUserArray());
            }
        }
    }

    /**
     allows us to take certain actions depending on if the user has gone through the test once yet
     */
    public static boolean isFirstTimeThrough(String user) {
        if (users.contains(user)) {
            return false;
        }
        return true;
    }
}
