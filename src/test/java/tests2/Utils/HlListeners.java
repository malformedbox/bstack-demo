package tests2.Utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Description: This class highjacks the test listeners in order to do what we need to on test completion.
 */
public class HlListeners extends TestBase implements ITestListener, IExecutionListener{
    Boolean isBS = System.getProperty("testPlatform").equalsIgnoreCase("BS");

    @Override
    public void onTestFailure(ITestResult testResult) {
        long id = Thread.currentThread().getId();
        Object webDriverAttribute = this.getTestResult().getTestContext().getAttribute("WebDriver" + id);
        WebDriver webdriver = (WebDriver) webDriverAttribute;
        RemoteWebDriver remoteWebDriver = (RemoteWebDriver)webdriver;

        if (isBS && (this.getRunMode().toUpperCase().equals("PROD"))) {
            try {
                JsonObject build = getBuildInfo();
                this.attachBrowserstackLink(remoteWebDriver, build, testResult.getMethod().getMethodName());
                this.takeScreenShot(remoteWebDriver);
                this.markStatus(remoteWebDriver, testResult);

                String sessionid = remoteWebDriver.getSessionId().toString();
                this.closeBrowser(remoteWebDriver);
                this.attachJSConsoleErrors(build, sessionid);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        else {
            this.closeBrowser(webdriver);
        }
    }

    private void attachBrowserstackLink(RemoteWebDriver webdriver, JsonObject build, String methodName) throws URISyntaxException, IOException {
        Allure.addAttachment("Browserstack video :",
                "text/html",
                "<a href=\"https://automate.browserstack.com/dashboard/v2/builds/" + build.get("hashed_id").getAsString() + "/sessions/" + webdriver.getSessionId() + "\" target=\"_blank\">" + methodName + "</a>",
                ".html");
    }

    private void attachJSConsoleErrors(JsonObject build, String sessionid) throws URISyntaxException, IOException {
        Allure.addAttachment("JS Console Errors:", getJSConsoleInfo(build, sessionid));
    }
    public JsonObject getBuildInfo() throws URISyntaxException, IOException {
        //Get build info from browserstack.
        URI uri = new URI("https://" + System.getProperty("bsUsername") + ":" + System.getProperty("bsAccessKey") + "@api.browserstack.com/automate/builds.json?status=running");
        HttpGet getRequest = new HttpGet(uri);
        HttpResponse response = HttpClientBuilder.create().build().execute(getRequest);


        //Convert info to json and parse out the build id.
        BufferedReader rd = new BufferedReader
                (new InputStreamReader(
                        response.getEntity().getContent()));

        String line = rd.readLine();

        String buildName;
        switch (this.getRunMode().toUpperCase()) {
            case "PROD":
                buildName = System.getProperty("domainRoot") + ": " + System.getProperty("buildNumber") + " Prod";
                break;
            default:
                buildName = TESTRESULT.get().getTestContext().getSuite().getName();
                break;
        }

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(line);
        JsonArray array = element.getAsJsonArray();
        JsonObject desiredBuild = new JsonObject();
        for(int i = 0 ; i < array.size(); i++){
            JsonObject build = array.get(i).getAsJsonObject();
            build = build.getAsJsonObject("automation_build");
            //This is grabbed from https://api.browserstack.com/automate/builds.json?status=running
            //It takes the value for "name" and then matches it with buildName = domainRoot + " " + buildNumber
            if(build.get("name").getAsString().toUpperCase().contains(buildName.toUpperCase())){
                desiredBuild = build;
                break;
            }
        }
        return desiredBuild;
    }
    public String getJSConsoleInfo(JsonObject build, String sessionid) throws IOException, URISyntaxException {
        String buidlid = build.get("hashed_id").getAsString();

        URI uri = new URI("https://" + System.getProperty("bsUsername") + ":" + System.getProperty("bsAccessKey") + "@api.browserstack.com/automate/builds/" + buidlid + "/sessions/" + sessionid + "consolelogs.json");
        HttpGet getRequest = new HttpGet(uri);
        HttpResponse response = HttpClientBuilder.create().build().execute(getRequest);


        //Convert info to json and parse out the build id.
        BufferedReader rd = new BufferedReader
                (new InputStreamReader(
                        response.getEntity().getContent()));

        return rd.readLine();
    }

    @Attachment(value = "Screenshot at failure:", type = "image/png")
    private byte[] takeScreenShot(WebDriver webDriver) {
        return ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {}

    @Override
    public void onExecutionStart() {}

    @Override
    /* gets invoked at the very last (after report generation phase), before TestNG exits the JVM. */
    public void onExecutionFinish() {}

    @Override
    public void onTestSuccess(ITestResult testResult) {
        long id = Thread.currentThread().getId();
        Object webDriverAttribute = this.getTestResult().getTestContext().getAttribute("WebDriver" + id);
        WebDriver webdriver = (WebDriver) webDriverAttribute;


        //Send test status to Browserstack
        if ( isBS && this.getRunMode().toUpperCase().equals("PROD")) {
            RemoteWebDriver remoteWebDriver = (RemoteWebDriver)webdriver;
            try {
                this.markStatus(remoteWebDriver, testResult);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        this.closeBrowser(webdriver);
    }

    @Override
    public void onTestSkipped(ITestResult testResult){
        testResult.setStatus(ITestResult.SKIP);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {}

    @Override
    public void onStart(ITestContext iTestContext) {}

    @Override
    public void onFinish(ITestContext iTestContext) {}

    private void markStatus(WebDriver webDriver, ITestResult testResult) throws URISyntaxException, IOException{
        RemoteWebDriver driver = (RemoteWebDriver)webDriver;

        URI uri = new URI("https://" + System.getProperty("bsUsername") + ":" + System.getProperty("bsAccessKey") + "@api.browserstack.com/automate/sessions/" + driver.getSessionId() + ".json");
        HttpPut putRequest = new HttpPut(uri);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        if (testResult.getStatus() == ITestResult.SUCCESS)
            nameValuePairs.add((new BasicNameValuePair("status", "passed")));
        else
            nameValuePairs.add((new BasicNameValuePair("status", "failed")));

        putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpClientBuilder.create().build().execute(putRequest);
    }
    public void closeBrowser(WebDriver webDriver){
        while (!webDriver.toString().contains("null")) {
            webDriver.quit();
        }
    }
}
