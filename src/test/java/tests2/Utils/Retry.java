package tests2.Utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.ArrayList;

/**
 *  Retry class.
 */
public class Retry implements IRetryAnalyzer {

    private int count = 0;
    public static int maxTry = 2;
    public static ArrayList<String> users = new ArrayList<>();

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {
            if (count < maxTry) {
                count++;
                iTestResult.setStatus(ITestResult.FAILURE);
                return true;
            } else {
                iTestResult.setStatus(ITestResult.FAILURE);
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);
        }
        return false;
    }

    public int getRetryCount() {
        return count;
    }

    public static void setMaxTry(int maxTryCount, String user) {
        // sets the max try count
        maxTry = maxTryCount;
        users.add(user);
    }

    public ArrayList getUserArray() {
        return users;
    }
}
