package tests2.Utils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.*;
import org.testng.annotations.*;

import pages.Utils.PageBase;

import java.net.*;

@Listeners({HlListeners.class})
public class TestBase extends PageBase{

    private static final String USERNAME = System.getProperty("bsUsername");
    private static final String AUTOMATE_KEY = System.getProperty("bsAccessKey");
    private static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    Boolean isBS = System.getProperty("testPlatform").equalsIgnoreCase("BS");

    private final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    public static ThreadLocal<ITestResult> TESTRESULT = new ThreadLocal<>();

    public WebDriver getWebDriver() {
        return webDriver.get();
    }
    public ITestResult getTestResult() {
        return TESTRESULT.get();
    }

    /**
     *  These functions grab parameter values from the testng file
     */
    public String getBrowser() {
        return System.getProperty("browserName").toUpperCase();
    }
    public String getBrowserVersion() {
        return this.getTestResult().getTestContext().getCurrentXmlTest().getAllParameters().get("browserVersion").toUpperCase();
    }
    public String getOs() {
        return this.getTestResult().getTestContext().getCurrentXmlTest().getAllParameters().get("os").toUpperCase();
    }
    public String getOsVersion() {
        return this.getTestResult().getTestContext().getCurrentXmlTest().getAllParameters().get("osVersion").toUpperCase();
    }

    public String getRunMode() {
        return System.getProperty("runMode").toUpperCase();
    }

    public static String getSuiteType() {
        return System.getProperty("suiteType").toUpperCase();
    }


    public String getUrl() {
        switch (this.getRunMode().toUpperCase()) {
           case "PROD":
                return "https://" + System.getProperty("domainRoot") + ".connectedcommunity.org/";
            default:
                return this.getTestResult().getTestContext().getCurrentXmlTest().getAllParameters().get("rootUrl");
        }
    }

    @Step("Test case setup.")
    public void setup() throws IOException {
        //Create build name for browserstack.
        String build;
        switch (this.getRunMode().toUpperCase()) {
            case "PROD":
                build = System.getProperty("domainRoot") + ": " + System.getProperty("buildNumber") + " Prod";
                break;
            default:
                build = this.getTestResult().getTestContext().getSuite().getName();
                break;
        }

        Object[] parameters = this.getTestResult().getParameters();
        String testcase = this.getTestResult().getName() + "-" + parameters[0].toString();

        //Create webdriver
        MutableCapabilities capabilities = this.setCapabilities(build, testcase);
        this.createDriver(capabilities);
    }

    @Step("Create webdriver.")
    public void createDriver(MutableCapabilities capabilities)
            throws IOException {

        String[] runModes = {"PROD"};
        if (Arrays.stream(runModes).anyMatch(this.getRunMode().toUpperCase()::equals)) {
            webDriver.set(new RemoteWebDriver(new URL(URL), capabilities));
        } else {
            switch (this.getBrowser().toUpperCase()) {
                case "IE":
                    webDriver.set(new InternetExplorerDriver());
                    break;
                case "EDGE":
                    webDriver.set(new EdgeDriver());
                    break;
                case "FIREFOX":
                    webDriver.set(new FirefoxDriver());
                    break;
                default:
                    //Removed capabilities
                    webDriver.set(new ChromeDriver());
                    break;
            }
        }
        //Set browser size
        this.getWebDriver().manage().window().maximize();

        String codebase;
        String acCodebase = System.getProperty("acCodebase").toLowerCase();
        if (System.getProperty("codebase").toLowerCase().contains("qa"))
            codebase = System.getProperty("codebase").toLowerCase().toLowerCase();
        else {
            codebase = System.getProperty("codebase").substring(0,1).toLowerCase() + System.getProperty("codebase").substring(1).toLowerCase();
        }
//        Cookie apiCookie = new Cookie.Builder("HL_TEST", codebase)
//                .domain(".connectedcommunity.org")
//                .isHttpOnly(false)
//                .isSecure(true)
//                .path("/")
//                .build();
//
//        Cookie apiCookie1= new Cookie.Builder("HL_TEST", codebase)
//                .domain(".higherlogic.com")
//                .isHttpOnly(false)
//                .isSecure(true)
//                .path("/")
//                .build();
//
//        Cookie apiCookie2 = new Cookie.Builder("HL_TEST_AC", acCodebase)
//                .domain(".connectedcommunity.org")
//                .isHttpOnly(false)
//                .isSecure(true)
//                .path("/")
//                .build();
//
//        Cookie apiCookie3 = new Cookie.Builder("HL_TEST_AC", acCodebase)
//                .domain(".higherlogic.com")
//                .isHttpOnly(false)
//                .isSecure(true)
//                .path("/")
//                .build();
//
//        if(!this.getRunMode().toUpperCase().equals("PROD") && !this.getRunMode().toUpperCase().equals("IZ") && !this.getRunMode().toUpperCase().equals("IZRC") && !this.getRunMode().toUpperCase().equals("RM") && !this.getRunMode().toUpperCase().equals("RM-RD") && !this.getRunMode().toUpperCase().equals("CAN") && !this.getRunMode().toUpperCase().equals("VAN") && !this.getRunMode().toUpperCase().equals("IZADMINRC")){ //🚩
//            this.getWebDriver().navigate().to("https://api.connectedcommunity.org");
//            this.waitForPageToLoad(webDriver.get());
//            webDriver.get().manage().addCookie(apiCookie);
//            webDriver.get().manage().addCookie(apiCookie2);
//
//            this.getWebDriver().navigate().to("https://higherlogic.com");
//            this.waitForPageToLoad(webDriver.get());
//            webDriver.get().manage().addCookie(apiCookie1);
//            webDriver.get().manage().addCookie(apiCookie3);
//        }

        //Navigate to site
        this.getWebDriver().navigate().to("https://google.com");

        // If an OC run mode, disable pendo
        String[] ocModes = {"SCURD", "PROD", "RU", "CAN"};
        String domain = ".connectedcommunity.org";
//        if(Arrays.asList(ocModes).contains(this.getRunMode())) {
//            if(this.getRunMode().equalsIgnoreCase("CAN")) domain = ".onlinecommunity.ca";
//            Cookie disablePendoCookie = new Cookie.Builder("DisablePendo", "true")
//                    .domain(domain)
//                    .isHttpOnly(false)
//                    .isSecure(false)
//                    .path("/")
//                    .build();
//            webDriver.get().manage().addCookie(disablePendoCookie);
//        }

        //Set webdriver attribute in config
        long id = Thread.currentThread().getId();
        this.getTestResult().getTestContext().setAttribute("WebDriver" + id, this.getWebDriver());
    }
    @Step("Set general capabilities")
    public MutableCapabilities setCapabilities(String build, String testcase){
        //General capabilities for desktop
        MutableCapabilities capabilities = new MutableCapabilities();
        if (this.getRunMode().toUpperCase().equals("PROD")) {

            capabilities.setCapability("browserName", this.getBrowser());

            //Capabilities on cloud provider level
            HashMap<String, Object> browserstackOptions = new HashMap<>();
//            browserstackOptions.put("os", getOs());
//            browserstackOptions.put("osVersion", getOsVersion());
//            if (!this.getBrowserVersion().toUpperCase().equals("LATEST")) {
//                browserstackOptions.put("browserVersion", this.getBrowserVersion());
//            }
//            else {
//                browserstackOptions.put("browserVersion", "latest");
//            }
//            browserstackOptions.put("resolution", "1280x1024");
            browserstackOptions.put("buildName", build);
            browserstackOptions.put("sessionName", testcase);
//            browserstackOptions.put("local", "false");
//            browserstackOptions.put("seleniumVersion", "4.7.2");
//            browserstackOptions.put("acceptInsecureCerts", "true");
//            browserstackOptions.put("networkLogs", "true");

            String[] groups = this.getTestResult().getMethod().getGroups();
            boolean isBtest = Arrays.asList(groups).contains("btest");

            if(isBtest) browserstackOptions.put("maskCommands", "setValues");

            capabilities.setCapability("bstack:options", browserstackOptions);
        }

        if (this.getBrowser().toUpperCase().equals("CHROME")){
            Map<String, Object> preferences = new HashMap<>();
            preferences.put("credentials_enable_service", false);
            preferences.put("profile.password_manager_enabled", false);
            preferences.put("autofill.profile_enabled", false);

            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("prefs", preferences);
            options.addArguments("disable-infobars");
            options.addArguments("start-maximized");
            options.addArguments("--no-sandbox");

            //It helps to overcome memory related limited resource problems.
            options.addArguments("--disable-dev-shm-usage");

            capabilities.setCapability(ChromeOptions.CAPABILITY,options);
        }
        return capabilities;
    }

    @BeforeMethod
    public void setTestResult(ITestResult testResult){
        TESTRESULT.set(testResult);
    }

    @DataProvider(name = "TestCombinations", parallel = true)
    public static Object[][] testCombinationDataProvider(Method m) {
        Object[] superadmin = {"superadmin"};
        Object[] communityadmin = {"communityadmin"};

        ArrayList<Object[]> configurations = new ArrayList<>();
        Test t = m.getAnnotation(Test.class);
        String[] groups = t.groups();

        for (int i=0; i < t.groups().length;i++){
            if (groups[i].toUpperCase().equals("SUPERADMIN"))
                configurations.add(superadmin);
            else if (groups[i].toUpperCase().equals("COMMUNITYADMIN"))
                configurations.add(communityadmin);
        }
        return configurations.toArray(new Object[configurations.size()][]);
    }
}

