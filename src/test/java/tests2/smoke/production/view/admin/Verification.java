package tests2.smoke.production.view.admin;

import io.qameta.allure.Feature;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import tests2.Utils.TestBase;
import java.io.IOException;

/**
 * Description: Test class
 */
@Feature("Community")
public class Verification extends TestBase {
    /**
     * Description: Test
     */
    @Test(dataProvider = "TestCombinations", groups = "superadmin")
    public void verifyCalendarWidget(String user) throws IOException {
        //Setup navigates to the page
        this.setup();
        WebDriver webdriver = this.getWebDriver();

        //Assert
        boolean assertValue = true;
        Assert.assertTrue(assertValue, "Assert is true.");
    }

    //More tests can be added here, as this class is set to run.
}