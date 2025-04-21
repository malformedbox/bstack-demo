package pages.Utils;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.*;

import java.util.List;
import java.util.function.Supplier;

public class PageBase {
    public static Boolean isAws = System.getProperty("testPlatform").equalsIgnoreCase("AWS");
    public static Boolean isBS = System.getProperty("testPlatform").equalsIgnoreCase("BS");

	protected void waitForVisibilityOfElement(WebDriver webdriver, WebElement element){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
		wait.until(ExpectedConditions.visibilityOf(element));
    }
	protected void waitForVisibilityOfElement(WebDriver webdriver, long time, WebElement element){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	protected void waitForInvisibilityOfElement(WebDriver webdriver, WebElement element){
		//Is invisible does not handle noSuchElementException this the expected conditions will not work correctly.
		//This is an open ticket to fix.  This needs to be updated when invisibilityOf becomes valid.
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
		wait.until(ExpectedConditions.invisibilityOf(element));
	}
	protected void waitForInvisibilityOfElement(WebDriver webdriver, long time, WebElement element){
		//Is invisible does not handle noSuchElementException this the expected conditions will not work correctly.
		//This is an open ticket to fix.  This needs to be updated when invisibilityOf becomes valid.
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.invisibilityOf(element));
	}
	protected void waitForVisibilityOfFrameAndSwitchToIt(WebDriver webdriver, WebElement frame){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
	}
	protected void waitForVisibilityOfFrameAndSwitchToIt(WebDriver webdriver, long time, WebElement frame){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
	}
	protected void waitForVisibilityOfAllElements(WebDriver webdriver, List<WebElement> elementList){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(elementList)));
	}
	protected void waitForVisibilityOfAllElements(WebDriver webdriver, long time, List<WebElement> elementList){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(elementList)));
	}
	protected void waitForInvisibilityOfAllElements(WebDriver webdriver, List<WebElement> elementList){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.invisibilityOfAllElements(elementList)));
	}
	protected void waitForInvisibilityOfAllElements(WebDriver webdriver, long time, List<WebElement> elementList){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.invisibilityOfAllElements(elementList)));
	}
	protected void waitForElementToBeClickable(WebDriver webdriver, WebElement element){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
	}
	protected void waitForElementToBeClickable(WebDriver webdriver, long time, WebElement element){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
	}
	protected void waitForAttributeToBe(WebDriver webdriver, WebElement element, String attribute, String value){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.attributeToBe(element, attribute, value)));
	}
    protected void waitForTextToBe(WebDriver webdriver, WebElement element, long time, String text){
        WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.textToBePresentInElement(element, text)));
    }
	protected void waitForAttributeNotToContain(WebDriver webdriver, WebElement element, String attribute, String value){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.not(ExpectedConditions.attributeContains(element, attribute, value))));
	}
	protected void waitForAttributeToBe(WebDriver webdriver, long time, WebElement element, String attribute, String value){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.attributeToBe(element, attribute, value)));
	}
	protected void waitForAttributeToContain(WebDriver webdriver, WebElement element, String attribute, String value){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.attributeContains(element, attribute, value)));
	}
	protected void waitForAttributeNotToBe(WebDriver webdriver, WebElement element, String attribute, String value){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.not(ExpectedConditions.attributeToBe(element, attribute, value))));
	}
	protected void waitForAttributeNotToBe(WebDriver webdriver, long time, WebElement element, String attribute, String value){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.not(ExpectedConditions.attributeToBe(element, attribute, value))));
	}
	public void waitForPageToLoad(WebDriver webdriver){
		waitForPageToLoad(webdriver, 8);
	}
	public void waitForPageToLoadNoJavascript(WebDriver webdriver){
		waitForPageToLoadNoJavascript(webdriver, 8);
	}
	public void waitForPageToLoadNoJavascript(WebDriver webdriver, long time){
		try {
			WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
			wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == \"complete\""));
		} catch (Exception e) {
			//Do nothing
		}
	}
	public void waitForPageToLoad(WebDriver webdriver, long time){
		try {
			WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
			wait.until(ExpectedConditions.jsReturnsValue("return window.jQuery != undefined && jQuery.active == 0 && document.readyState == \"complete\""));
		} catch (Exception e) {
			//Do nothing
		}
	}
	protected void waitForLoadImageToFinish(WebDriver webdriver){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("imgGlobalProcessing")));
			wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
			try{
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("imgGlobalProcessing")));
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		catch (Exception e){
			//No need to do anything because if it gets here the page is done loading.
		}
	}
	protected void waitForLoadImageToFinish(WebDriver webdriver, long time){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("imgGlobalProcessing")));
			wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
			try{
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("imgGlobalProcessing")));
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		catch (Exception e){
			//No need to do anything because if it gets here the page is done loading.
		}
	}
	protected void waitForReactLoadImageToFinish(WebDriver webdriver){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".hlc-progress.indeterminate")));
			wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
			try{
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".hlc-progress.indeterminate")));
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		catch (Exception e){
			//No need to do anything because if it gets here the page is done loading.
		}
	}
	protected void waitForReactLoadImageToFinish(WebDriver webdriver, long time){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".hlc-progress.indeterminate")));
			wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
			try{
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".hlc-progress.indeterminate")));
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		catch (Exception e){
			//No need to do anything because if it gets here the page is done loading.
		}
	}
	public void waitForTimeInSeconds(WebDriver webdriver, long timeToWaitForInSeconds, boolean refreshPage) {
		// Wait for time specified then refresh page
		try {
			Wait<WebDriver> wait = new FluentWait<>(webdriver)
					.withTimeout(Duration.ofSeconds(timeToWaitForInSeconds))
					.pollingEvery(Duration.ofSeconds(10))
					.ignoring(NoSuchElementException.class);

			wait.until(driver -> webdriver.findElement(By.cssSelector("notfindable")));
		} catch(TimeoutException e) {
			if(refreshPage) webdriver.navigate().refresh();
			this.waitForPageToLoad(webdriver);
		}
	}

    protected void waitForUrlToBe(WebDriver webdriver, String url){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(9));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.urlToBe(url)));
	}
	protected void waitForUrlToBe(WebDriver webdriver, long time, String url){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.urlToBe(url)));
	}
	protected void waitForUrlToContain(WebDriver webdriver, String url){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.urlContains(url)));
	}
	protected void waitForUrlToContain(WebDriver webdriver, long time, String url){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.urlContains(url)));
	}
	public void waitUntilElementExists(WebDriver webdriver, List<WebElement> selector) {
		waitUntilElementExists(webdriver, 8, selector);
	}
	public void waitUntilElementExists(WebDriver webdriver, long time, List<WebElement> selector){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
		try {
			wait.until((ExpectedCondition<Boolean>) driver -> {
				int elementCount = selector.size();
				if (elementCount > 0)
					return true;
				else
					return false;
			});
		}
		catch (TimeoutException e){}
	}
	/**
	 * Method that allows for waiting until an input element is visible
	 */
	public void waitForInputElementToDisplay(WebDriver webdriver, By inputElementBy){
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
		wait.until(ExpectedConditions.visibilityOfElementLocated(inputElementBy));
	}
	/**
	 * Method for waiting until a React Grid list has loaded
	 */
	public void waitForReactGridToLoad(WebDriver webdriver) {
		try {
			WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-labelledby='Loading interface...']")));
			waitForInvisibilityOfElement(webdriver, 8, webdriver.findElement(By.cssSelector("[aria-labelledby='Loading interface...']")));
		}
		catch (NoSuchElementException | TimeoutException e) {

		}
	}
	/**
	 * Method for waiting until a React Grid list has loaded within specified time
	 */
	public void waitForReactGridToLoad(WebDriver webdriver, long time) {
		try {
			WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(time));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-labelledby='Loading interface...']")));
			waitForInvisibilityOfElement(webdriver, time, webdriver.findElement(By.cssSelector("[aria-labelledby='Loading interface...']")));
		}
		catch (NoSuchElementException | TimeoutException e) {

		}
	}
	public void waitForReactLoadingOverlay(WebDriver webdriver) {
		try {
			WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".hlc-blockui-overlay")));
			waitForInvisibilityOfElement(webdriver, 8, webdriver.findElement(By.cssSelector(".hlc-blockui-overlay")));
		}
		catch (NoSuchElementException | TimeoutException e) {

		}
	}
	/**
	 * Method for waiting until a React Table list has updated ie. after entering text in the search bar
	 */
	public void waitForReactTableToRefresh(WebDriver webdriver) {
		try {
			WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.loading-container")));
			waitForInvisibilityOfElement(webdriver, 8, webdriver.findElement(By.cssSelector("div.loading-container")));
		}
		catch (NoSuchElementException | TimeoutException e) {

		}
	}
	public void waitForReactTableToRefreshNew(WebDriver webdriver) {
		try {
			WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.hlc-datagrid-loading-component.active")));
			waitForInvisibilityOfElement(webdriver, webdriver.findElement(By.cssSelector("div.hlc-datagrid-loading-component.active")));
		}
		catch (NoSuchElementException | TimeoutException e) {

		}
	}
	/**
	 * Method for waiting until a React modal closes ie. after saving changes to a record
	 */
	public void waitForModalToClose(WebDriver webdriver) {
		try {
			WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
			waitForInvisibilityOfElement(webdriver, 8, webdriver.findElement(By.cssSelector("div.modal.hlc-modal")));
		}
		catch (NoSuchElementException | TimeoutException e) {
		}
	}
	/**
	 * Method used for waiting for the Informz Grid loading overlay to disappear
	 */
	public void waitForInformzGridLoading(WebDriver webdriver) {
		new WebDriverWait(webdriver, Duration.ofSeconds(8)).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[id=load_mainTable][style*=block]")));
		this.waitForPageToLoad(webdriver);
	}
	/**
	 * Method used for waiting for the Informz Grid loading overlay to disappear for a period of time
	 */
	public void waitForInformzGridLoading(WebDriver webdriver, long time) {
		new WebDriverWait(webdriver, Duration.ofSeconds(time)).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[id=load_mainTable][style*=block]")));
		waitForPageToLoad(webdriver);
	}

	//Generate data functions
	public String createDateTime(int numberOfFutureDays, String format){
		String futureDateTime;
		LocalDateTime today = LocalDateTime.now();
		today = today.plusDays(numberOfFutureDays);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		futureDateTime = today.format(formatter);
		return futureDateTime;
	}
    public String createZonedDateTime(int numberOfFutureDays, String format, String zone){
        String futureDateTime;
        ZonedDateTime today = ZonedDateTime.now(ZoneId.of(zone));
        today = today.plusDays(numberOfFutureDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        futureDateTime = today.format(formatter);
        return futureDateTime;
    }
	public String createDateTime(int numberOfFutureDays, int numberOfFutureMinutes, String format){
		String futureDateTime;
		LocalDateTime today = LocalDateTime.now();
		today = today.plusDays(numberOfFutureDays).plusMinutes(numberOfFutureMinutes);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		futureDateTime = today.format(formatter);
		return futureDateTime;
	}
	public String randomIZPasswordGenerator() {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 12;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int)
					(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedPassword = buffer.toString();
		return generatedPassword;
	}
	protected int randomNumber(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	/**
	 * Generates one random email using domains in list
	 **/
	public String randomEmailGenerator(int numberOfCharacters) {
		List<String> domains = new ArrayList<>();
		domains.add("@gmail.com");
		domains.add("@outlook.com");
		domains.add("@yahoo.com");
		domains.add("@hotmail.com");
		Random random = new Random();
		String domain = domains.get(random.nextInt(domains.size()));
		String email = getRandomString(numberOfCharacters) + domain;
		return email;
	}
	/**
	 * Takes a date in the originalFormat and formats it to match the newFormat
	 * @param date Date in originalFormat
	 * @param originalFormat original format of the date
	 * @param newFormat new format of the date
	 * @return Date in the new format
	 */
	public String convertDateTimeFormat(String date, String originalFormat, String newFormat) {
		DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern(originalFormat);
		LocalDateTime originalDate = LocalDateTime.parse(date, originalFormatter);
		DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern(newFormat);
		return originalDate.format(newFormatter);

	}
	public String convertDateFormat(String date, String originalFormat, String newFormat) {
		DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern(originalFormat);
		LocalDate originalDate = LocalDate.parse(date, originalFormatter);
		DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern(newFormat);
		return originalDate.format(newFormatter);
	}
	public String getRandomString(int numberOfCharacters){
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= numberOfCharacters; i++) {
			if(i == numberOfCharacters || (i % 10!=0) ){
				char c = chars[this.randomNumber(0, chars.length-1 )];

				sb.append(c);
			}else{
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	public String getDailyUser(String dayOfTheWeek){
		switch(dayOfTheWeek){
			case "MONDAY":
				return "SUPERADMIN";
			case "TUESDAY":
				return "COMMUNITYMEMBER";
			case "WEDNESDAY":
				return "AUTHENTICATED";
			case "THURSDAY":
				return "NONCOMMUNITYMEMBER";
			case "FRIDAY":
				return "COMMUNITYADMIN";
			default:
				return "COMMUNITYMEMBER";
		}
	}
	public List<WebElement> getDropdownOptions(WebElement dropdown){
		Select select = new Select(dropdown);
		return select.getOptions();
	}

	//Enter get functions
	public String getDomainRootFromUrl(WebDriver webdriver) {
		String currentUrl = webdriver.getCurrentUrl();
		// grabs everything in the url after // and before the first . (e.g. returns regressiontestrun2812262)
		return currentUrl.substring(currentUrl.indexOf("/") + 2).split("\\.")[0];
	}
	public String getAlternateUserEmail(){
		String superadmin = "hladmin@higherlogic.test";
		String communitymember = "communitymember@higherlogic.test";
		if (Integer.parseInt(this.createDateTime(0, "dd")) % 2 == 0)
			return superadmin;
		else
			return communitymember;
	}
	public String getUserFromEmail(String email) throws Exception {
		switch(email.toLowerCase()){
			case "hladmin@higherlogic.test":
				return "SUPERADMIN TEST";
			case "communityadmin@higherlogic.test":
				return "COMMUNITYADMIN TEST";
			case "communitymember@higherlogic.test":
				return "COMMUNITYMEMBER TEST";
			case "noncommunitymember@higherlogic.test":
				return "NONCOMMUNITYMEMBER TEST";
			case "authenticated@higherlogic.test":
				return "AUTHENTICATED TEST";
			case "authenticatednonmember@higherlogic.test":
				return "AUTHENTICATEDNONMEMBER TEST";
			case "authenticatednonmember2@higherlogic.test":
				return "AUTHENTICATEDNONMEMBER2 TEST";
			case "restricteduser@higherlogic.test":
				return "RESTRICTEDUSER TEST";
			case "moderateduser3@higherlogic.test":
				return "MODERATEDUSER TEST";
			case "grandmatesthl@yahoo.com":
				return "GRANDMA TEST";
			case "grandpatesthl@yahoo.com":
				return "GRANDPA TEST";
			case "higherlogicautomation1@gmail.com":
				return "AUTOMATIONGMAIL1 TEST";
			case "nonmember@higherlogic.test":
				return "NONMEMBER TEST";
			case "voladmin@higherlogic.test":
				return "VOLUNTEERADMIN TEST";
			case "volunteerfan@higherlogic.test":
				return "VOLUNTEERFAN TEST";
			default:
				throw new Exception("No email was found with a correlating user.");
		}
	}
	public String getEmailFromUser(String user){
		switch(user.toUpperCase()){
			case"SUPERADMIN":
				return "hladmin@higherlogic.test";
			case"COMMUNITYADMIN":
				return "communityAdmin@higherlogic.test";
			case"COMMUNITYMEMBER":
				return "communitymember@higherlogic.test";
			case"NONCOMMUNITYMEMBER":
				return "NonCommunityMember@higherlogic.test";
			case"AUTHENTICATED":
				return "Authenticated@higherlogic.test";
			case"AUTHENTICATEDNONMEMBER":
				return "AuthenticatedNonMember@higherlogic.test";
			case"AUTHENTICATEDNONMEMBER2":
				return "AuthenticatedNonMember2@higherlogic.test";
			case"RESTRICTEDUSER":
				return "RestrictedUser@higherlogic.test";
			case"MODERATEDUSER":
				return "moderateduser3@higherlogic.test" ;
			case"GRANDMA":
				return "grandmatesthl@yahoo.com";
			case"GRANDPA":
				return "grandpatesthl@yahoo.com";
			case"AUTOMATIONGMAIL1":
				return "higherlogicautomation1@gmail.com";
			case"NONMEMBER":
				return "NonMember@higherlogic.test";
			case"VOLUNTEERADMIN":
				return "voladmin@higherlogic.test";
			case"VOLUNTEERFAN":
				return "volunteerfan@higherlogic.test";
			case "COMMUNITYMODERATOR":
				return "communitymoderator@higherlogic.test";
			default:
				throw new InvalidArgumentException("No such user exists by that name.");
		}
	}
	public String getLatestDownloadedFile(WebDriver webdriver){
		// open a new tab
		JavascriptExecutor js = (JavascriptExecutor)webdriver;
		js.executeScript("window.open()");
		// switch to new tab opened
		for(String winHandle : webdriver.getWindowHandles()){
			webdriver.switchTo().window(winHandle);
		}
		// navigate to chrome downloads
		webdriver.get("chrome://downloads");

		JavascriptExecutor js1 = (JavascriptExecutor)webdriver;
		// get the latest downloaded file name
		String toReturn = (String) js1.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
		// go and switch back to the first tab
		webdriver.switchTo().window(webdriver.getWindowHandles().iterator().next());
		return toReturn;
	}
	public String getPhoneNumberFromUser(String user){
		switch(user.toUpperCase()){
			case"SUPERADMIN":
				return "123-456-7890";
			case"COMMUNITYADMIN":
				return "888-888-8888";
			case"COMMUNITYMEMBER":
				return "234-567-8901";
			case"AUTHENTICATED":
				return "410-555-8888";
			case"RESTRICTEDUSER":
				return "555-555-5555";
			case"VOLUNTEERADMIN":
				return "444-859-9658";
			default:
				throw new InvalidArgumentException("No such user exists by that name.");
		}
	}
	public WebElement getCorrespondingElementInList(List<WebElement> elementList1, List<WebElement> elementList2, String text){
		for(int i = 0; i < elementList1.size(); i++){
			if(elementList1.get(i).getText().toUpperCase().trim().equals(text.toUpperCase()))
				return elementList2.get(i);
		}
		throw new NoSuchElementException(generateErrorMessage(text));
	}
    public WebElement getCorrespondingElementInListByContainsText(List<WebElement> elementList1, List<WebElement> elementList2, String text){
        for(int i = 0; i < elementList1.size(); i++){
            if(elementList1.get(i).getText().toUpperCase().trim().contains(text.toUpperCase()))
                return elementList2.get(i);
        }
        throw new NoSuchElementException(generateErrorMessage(text));
    }
	public String getCorrespondingElementText(List<WebElement> elementList, List<WebElement> elementList2, String text){
		for (int i=0; i < elementList.size(); i++){
			if (elementList.get(i).getText().toUpperCase().equals(text.toUpperCase())){
				return elementList2.get(i).getText();
			}
		}
		throw new NoSuchElementException(generateErrorMessage(text));
	}
	public String getCorrespondingElementTextInList(List<WebElement> elementList1, List<WebElement> elementList2, String text){
		for(int i = 0; i < elementList1.size(); i++){
			if(elementList1.get(i).getText().toUpperCase().trim().equals(text.toUpperCase()))
				return elementList2.get(i).getAttribute("innerHTML");
		}
		throw new NoSuchElementException(generateErrorMessage(text));
	}
	public String getMonthFromNumber(int month){
		switch(month){
			case 1:
				return "January";
			case 2:
				return "February";
			case 3:
				return "March";
			case 4:
				return "April";
			case 5:
				return "May";
			case 6:
				return "June";
			case 7:
				return "July";
			case 8:
				return "August";
			case 9:
				return "September";
			case 10:
				return "October";
			case 11:
				return "November";
			case 12:
				return "December";
		default:
			throw new InvalidArgumentException("A month with that number doesn't exist.");
		}
	}
	public String getStateOrProvinceAbbreviationFromName(String state){
		switch(state.toUpperCase()){
			case "AGUASCALIENTES":
				return "AGU";
			case "ALABAMA":
				return "AL";
			case "ALASKA":
				return "AK";
			case "ALBERTA":
				return "AB";
			case "AMERICAN SAMOA":
				return "AS";
			case "ARIZONA":
				return "AZ";
			case "ARKANSAS":
				return "AR";
			case "ARMED FORCES AFRICA, CANADA, EUROPE, MIDDLE EAST":
				return "AE";
			case "ARMED FORCES AMERICAS":
				return "AA";
			case "ARMED FORCES PACIFIC":
				return "AP";
			case "BAJA CALIFORNIA":
				return "BCN";
			case "BAJA CALIFORNIA SUR":
				return "BCS";
			case "BRITISH COLUMBIA":
				return "BC";
			case "CALIFORNIA":
				return "CA";
			case "CAMPECHE":
				return "CAM";
			case "CHIAPAS":
				return "CHP";
			case "CHIHUAHUA":
				return "CHH";
			case "CIUDAD DE MÉXICO":
				return "DIF";
			case "COAHUILA":
				return "COA";
			case "COLIMA":
				return "COL";
			case "COLORADO":
				return "CO";
			case "CONNECTICUT":
				return "CT";
			case "DELAWARE":
				return "DE";
			case "DISTRICT OF COLUMBIA":
				return "DC";
			case "DURANGO":
				return "DUR";
			case "ESTADO DE MÉXICO":
				return "MEX";
			case "FEDERATED STATES OF MICRONESIA":
				return "FM";
			case "FLORIDA":
				return "FL";
			case "GEORGIA":
				return "GA";
			case "GUANAJUATO":
				return "GUA";
			case "GUERRERO":
				return "GRO";
			case "HAWAII":
				return "HI";
			case "HIDALGO":
				return "HID";
			case "IDAHO":
				return "ID";
			case "ILLINOIS":
				return "IL";
			case "INDIANA":
				return "IN";
			case "IOWA":
				return "IA";
			case "JALISCO":
				return "JAL";
			case "KANSAS":
				return "KS";
			case "KENTUCKY":
				return "KY";
			case "LOUISIANA":
				return "LA";
			case "MAINE":
				return "ME";
			case "MANITOBA":
				return "MB";
			case "MARSHALL ISLANDS":
				return "MH";
			case "MARYLAND":
				return "MD";
			case "MASSACHUSETTS":
				return "MA";
			case "MICHIGAN":
				return "MI";
			case "MINNESOTA":
				return "MN";
			case "MISSISSIPPI":
				return "MS";
			case "MISSOURI":
				return "MO";
			case "MONTANA":
				return "MT";
			case "MORELOS":
				return "MOR";
			case "NAYARIT":
				return "NAY";
			case "NEBRASKA":
				return "NE";
			case "NEVADA":
				return "NV";
			case "NEW BRUNSWICK":
				return "NB";
			case "NEW HAMPSHIRE":
				return "NH";
			case "NEW JERSEY":
				return "NJ";
			case "NEW MEXICO":
				return "NM";
			case "NEW YORK":
				return "NY";
			case "NEWFOUNDLAND":
				return "NF";
			case "NORTH CAROLINA":
				return "NC";
			case "NORTH DAKOTA":
				return "ND";
			case "NORTHERN MARIANA ISLANDS":
				return "MP";
			case "NORTHWEST TERRITORIES":
				return "NT";
			case "NOVA SCOTIA":
				return "NS";
			case "NUEVO LEÓN":
				return "NLE";
			case "NUNAVUT":
				return "NU";
			case "OAXACA":
				return "OAX";
			case "OHIO":
				return "OH";
			case "OKLAHOMA":
				return "OK";
			case "ONTARIO":
				return "ON";
			case "OREGON":
				return "OR";
			case "PALAU":
				return "PW";
			case "PENNSYLVANIA":
				return "PA";
			case "PRINCE EDWARD ISLAND":
				return "PE";
			case "PEUBLA":
				return "PUE";
			case "QUEBEC":
				return "QC";
			case "QUERÉTARO":
				return "QUE";
			case "QUINTANA ROO":
				return "ROO";
			case "RHODE ISLAND":
				return "RI";
			case "SASKATCHEWAN":
				return "SK";
			case "SINALOA":
				return "SIN";
			case "SONORA":
				return "SON";
			case "SOUTH CAROLINA":
				return "SC";
			case "SOUTH DAKOTA":
				return "SD";
			case "TABASCO":
				return "TAB";
			case "TAMAULIPAS":
				return "TAM";
			case "TENNESSEE":
				return "TN";
			case "TEXAS":
				return "TX";
			case "TLAXCALA":
				return "TLA";
			case "UTAH":
				return "UT";
			case "VERACRUZ":
				return "VER";
			case "VERMONT":
				return "VT";
			case "VIRGIN ISLANDS":
				return "VI";
			case "VIRGINIA":
				return "VA";
			case "WASHINGTON":
				return "WA";
			case "WEST VIRGINIA":
				return "WV";
			case "WISCONSIN":
				return "WI";
			case "WYOMING":
				return "WY";
			case "YUKON":
				return "YT";
			case "ZACATECAS":
				return "ZAC";
			default:
				throw new NoSuchElementException("No State with that name was found.");
		}
	}
	public String getTimeZoneCode(String timeZone) {
		switch(timeZone) {
			case "(UTC-10:00) Hawaii":
				return "HAST";
			case "(UTC-7:00) Arizona":
				return "MT";
			case "(UTC-12:00) International Date Line West":
				return "IDLW";
			case "(UTC-8:00) Pacific Time (US & Canada)":
				return "PT";
			case "(UTC-5:00) Eastern Time (US & Candada)":
				return "ET";
			default:
				return "NOT FOUND";
		}
	}
	/**
	 * Takes time in the format HH:mm and returns it in the format hh:mm a
	 * @param time Time in the format HH:mm
	 * @return String of the time in the format hh:mm a
	 */
	public String get12HourTimeFormat(String time) {
		final String[] hourMin = time.split(":");
		int time12 = Integer.parseInt(hourMin[0]);
		final String amPM = time12 < 12 ? "AM" : "PM";
		time12 -= time12 <= 12 ? 0 : 12;
		return time12 + ":" + hourMin[1] + " " + amPM;
	}
	public int getIndexOfElementInListByExactText(List<WebElement> elementList, String name) {
		name = name.toUpperCase();
		for(int i = 0; i < elementList.size(); i++){
			if(isTextEquals(elementList.get(i), name))
				return i;
		}
		throw new NoSuchElementException(name);
	}
	public int getIndexOfElementInListByContainsText(List<WebElement> elementList, String name) {
		name = name.toUpperCase();
		for(int i = 0; i < elementList.size(); i++){
			if(elementList.get(i).getText().trim().toUpperCase().contains(name.toUpperCase()))
				return i;
		}
		throw new NoSuchElementException(name);
	}
	public int getIndexOfElementInListByContainsText(List<WebElement> elementList1, List<WebElement> elementList2, String text) {
		for(int i = 0; i < elementList1.size(); i++){
			if(elementList1.get(i).getText().toUpperCase().trim().contains(text.toUpperCase()))
				return i;
		}
		throw new NoSuchElementException(generateErrorMessage(text));
	}
	public int getIndexOfElementInListByContainsText(WebDriver webdriver, List<WebElement> elementList1, List<WebElement> elementList2, String text, WebElement nextPageElement) {
		while(!isAtEndOfList(webdriver, nextPageElement)) {
			try {
				return this.getIndexOfElementInListByContainsText(elementList1, text);
			} catch (NoSuchElementException e) {
				click(webdriver, nextPageElement);
				waitForPageToLoad(webdriver);
			}
		}
		return getIndexOfElementInListByContainsText(elementList1, text);
	}
	public int getIndexOfElementInListByExactText(WebDriver webdriver, List<WebElement> elementList, String name) {
		name = name.toUpperCase();
		for(int i = 0; i < elementList.size(); i++){
			WebElement element = elementList.get(i);
			this.scrollToAndHighlightElement(webdriver, element);
			if(isTextEquals(element, name))
				return i;
		}
		throw new NoSuchElementException(name);
	}
	public int getIndexOfElementInListByExactText(WebDriver webdriver, List<WebElement> elementList, String name, WebElement nextPageElement, Map indexMap) {
		while(!isAtEndOfList(webdriver, nextPageElement)) {
			try {
				return this.getIndexOfElementInListByExactText(webdriver, elementList, name);
			} catch (NoSuchElementException e) {
				click(webdriver, nextPageElement);
				indexMap.clear();
				waitForLoadImageToFinish(webdriver);
			}
		}

		return getIndexOfElementInListByExactText(webdriver, elementList, name);
	}
    public int getIndexOfElementInListByAttribute(WebDriver webdriver, List<WebElement> elementList, String attribute, String value) {
        for(int i = 0; i < elementList.size(); i++){
            if(isElementAttributeCorrect(webdriver, elementList.get(i), attribute, value))
                return i;
        }
        throw new NoSuchElementException(generateErrorMessage(attribute + " not found"));
    }
	/**
	 * Method that allows for getting all the values for a specific column on a React Grid
	 * @param columnToGetDataFor - name of column, e.g. 'name' 'owner' 'category' etc
	 * @return - returns a List<WebElement> of all elements in that column
	 */
	public List<WebElement> getReactGridColumnValues(WebDriver webdriver, String columnToGetDataFor) {
		List<WebElement> gridColumnNames = webdriver.findElements(By.cssSelector(".rt-th.-cursor-pointer"));
		int columnIndex = getIndexOfElementInListByExactText(gridColumnNames, columnToGetDataFor) + 1;
		return webdriver.findElements(By.cssSelector(".rt-tr[style*=cursor] .rt-td:nth-of-type(" + columnIndex + ")"));
	}

	//Select functions
	public void selectDoubleClickElementFromListByExactText(WebDriver webdriver, List<WebElement> elementList, String elementText){
		boolean elementFound = false;
		for(WebElement anElement : elementList){
			if(this.isElementDisplayed(webdriver, anElement) &&
					anElement.getText().toUpperCase().equals(elementText.toUpperCase())){
				Actions action = new Actions(webdriver);
				elementFound = true;
				action.doubleClick(anElement).perform();
				break;
			}
		}
		if(!elementFound){
			throw new NoSuchElementException(generateErrorMessage(elementText));
		}
	}
	public void selectDropdownOptionByVisibleText (WebElement dropdownElement, String visibleText){
		Select select = new Select(dropdownElement);
		select.selectByVisibleText(visibleText);
	}
    public void selectDropdownOptionByContainsText (WebDriver webdriver, WebElement dropdownElement, String text){
        this.click(webdriver, dropdownElement);
        List<WebElement> dropdownOptions = this.getDropdownOptions(dropdownElement);
        this.selectElementFromListByContainsText(webdriver, dropdownOptions, text);
    }
	public void selectDropdownOptionByIndex (WebElement dropdownElement, int index){
		Select select = new Select(dropdownElement);
		select.selectByIndex(index);
	}
	public void selectElementFromDropdownByContainsWithClick(WebDriver webdriver, WebElement dropdownToClick, List<WebElement> listOfOptions, String exactTextTofind) {
		this.click(webdriver, dropdownToClick);
		this.selectElementFromListByContainsText(webdriver, listOfOptions, exactTextTofind);
		this.waitForPageToLoad(webdriver);
	}
	/**
	 * Method that allows for selecting a dropdown option by Exact text on a React Page (non Select element)
	 * @param dropdownToClick - the element to Click to display the options within the list
	 * @param listOfOptions - List of WebElements that are the options you want to select
	 * @param exactTextTofind - exact text to select from the dropdown list
	 */
	public void selectElementFromDropdownWithClick(WebDriver webdriver, WebElement dropdownToClick, List<WebElement> listOfOptions, String exactTextTofind) {
		this.click(webdriver, dropdownToClick);
		this.selectElementFromListByExactText(webdriver, listOfOptions, exactTextTofind);
		this.waitForPageToLoad(webdriver);
	}

	/**
	 * Allows for selecting an item from a List by an attribute value
	 */
	public void selectElementFromListByAttribute(WebDriver webdriver, List<WebElement> elementList, String attributeTitle, String attributeValue) {
		for(WebElement element : elementList) {
			if(isElementAttributeCorrect(webdriver, element, attributeTitle, attributeValue)) {
				this.click(webdriver, element);
				break;
			}
		}
		this.waitForPageToLoad(webdriver);
	}
	/**
	 * Allows for selecting an item from a List by an attribute contains
	 */
	public void selectElementFromListByAttributeContains(WebDriver webdriver, List<WebElement> elementList, String attributeTitle, String attributeValue) {
		for(WebElement element : elementList) {
			if(isElementAttributeContains(webdriver, element, attributeTitle, attributeValue)) {
				this.click(webdriver, element);
				break;
			}
		}
		this.waitForPageToLoad(webdriver);
	}
	public void selectElementFromListByExactText(WebDriver webdriver, List<WebElement> elementList, String elementText, WebElement nextPageElement){
		//this.waitForVisibilityOfAllElements(webdriver, elementList);
		boolean found = false;
		while(!isAtEndOfList(webdriver, nextPageElement)) {
			try {
				selectElementFromListByExactText(webdriver, elementList, elementText);
				found = true;
                break;
			} catch (NoSuchElementException e) {
				click(webdriver, nextPageElement);
				waitForLoadImageToFinish(webdriver);
			}
		}

		/* If on last page, then try to find the element
		* If already found on page, then won't try to find it again
		* If still can't find element, then error thrown */
		if(!found)
			selectElementFromListByExactText(webdriver, elementList, elementText);
	}
	public void selectElementFromListByExactText(WebDriver webdriver, List<WebElement> elementList, String elementText){
		elementText = elementText.toUpperCase();
		boolean elementFound = false;
		for(WebElement anElement : elementList){
			if(this.isElementDisplayed(webdriver, anElement) && isTextEquals(webdriver, anElement, elementText)){
				elementFound = true;
				click(webdriver, anElement);
				break;
			}
		}
		if(!elementFound)
			throw new NoSuchElementException(generateErrorMessage(elementText));
	}
	public void selectElementFromListByExactTextNoScroll(WebDriver webdriver, List<WebElement> elementList, String elementText){
		elementText = elementText.toUpperCase();
		boolean elementFound = false;
		for(WebElement anElement : elementList){
			if(this.isElementDisplayedNoScroll(webdriver, anElement) && isTextEqualsNoScroll(webdriver, anElement, elementText)){
				elementFound = true;
				clickNoScroll(webdriver, anElement);
				break;
			}
		}
		if(!elementFound)
			throw new NoSuchElementException(generateErrorMessage(elementText));
	}
	/**
	 * Method for Selecting a react page (listbox) dropdown option from a dropdown
	 * @param webdriver - instance of the webdriver
	 * @param listOfOptions - WebElement List of Dropdown options
	 * @param optionToSelect - specific option to click
	 */
	public void selectElementFromReactList(WebDriver webdriver, List<WebElement> listOfOptions, String optionToSelect) {
		if (!listOfOptions.isEmpty()) {
			selectElementFromListByExactText(webdriver, listOfOptions, optionToSelect);
		}
	}
    public void selectElementFromReactListByExactText(WebDriver webdriver, List<WebElement> elementList, String elementText, WebElement nextPageElement){
        boolean found = false;
        while(!isAtEndOfReactList(webdriver, nextPageElement)) {
            try {
                selectElementFromListByExactText(webdriver, elementList, elementText);
                found = true;
                break;
            } catch (NoSuchElementException e) {
                click(webdriver, nextPageElement);
                waitForLoadImageToFinish(webdriver);
            }
        }

        /* If on last page, then try to find the element
         * If already found on page, then won't try to find it again
         * If still can't find element, then error thrown */
        if(!found)
            selectElementFromListByExactText(webdriver, elementList, elementText);
    }
	/**
	 * Method for Selecting a react page dropdown option from a dropdown menu with a scroll
	 * @param webdriver - instance of the webdriver
	 * @param elementList - WebElement List of Dropdown options
	 * @param elementText - specific option to click
	 */
	public void selectElementFromDropDownListByExactText(WebDriver webdriver, List<WebElement> elementList, String elementText){
		elementText = elementText.toUpperCase();
		boolean elementFound = false;
		for(WebElement anElement : elementList){
			scrollToElement(webdriver, anElement);
			if(isTextEquals(webdriver, anElement, elementText)){
				elementFound = true;
				click(webdriver, anElement);
				break;
			}
		}
		if(!elementFound)
			throw new NoSuchElementException(generateErrorMessage(elementText));
	}
	public void selectElementFromReactListByContainsText(WebDriver webdriver, List<WebElement> elementList, String elementText, WebElement nextPageElement){
		boolean found = false;
		while(!isAtEndOfReactList(webdriver, nextPageElement)) {
			try {
				selectElementFromListByContainsText(webdriver, elementList, elementText);
				found = true;
				break;
			} catch (NoSuchElementException e) {
				click(webdriver, nextPageElement);
				waitForLoadImageToFinish(webdriver);
			}
		}

		/* If on last page, then try to find the element
		 * If already found on page, then won't try to find it again
		 * If still can't find element, then error thrown */
		if(!found)
			selectElementFromListByContainsText(webdriver, elementList, elementText);
	}
	public void selectMultipleElementsFromListByExactText(WebDriver webdriver, List<WebElement> elementList, String[] elements) {
		Set<String> elementSet = convertArrayIntoSet(elements);
		int count = elementSet.size();
		int i = 0;

		for(WebElement element : elementList) {
			if(this.isElementDisplayed(webdriver, element)) {
				this.scrollToAndHighlightElement(webdriver, element);
				String elementName = element.getText().trim().toUpperCase();
				if(elementSet.contains(elementName)) {
					click(webdriver, element);
					i++;
					if(i >= count) break;
				}
			}
		}
	}
	protected void selectElementFromListByContainsText(WebDriver webdriver, List<WebElement> elementList, String elementText){
		for(WebElement anElement : elementList){
			if(this.isElementDisplayed(webdriver, anElement) &&
					isElementTextCorrectByContains(webdriver, anElement, elementText)) {
				this.click(webdriver, anElement);
				break;
			}
		}
	}
	protected void selectElementFromListByIndex(List<WebElement> elementList, int index){
		elementList.get(index).click();
	}
	protected void selectCorrespondingElementByExactText(WebDriver webdriver, List<WebElement> elementList1, List<WebElement> elementList2, String text){
		boolean elementFound = false;
		this.waitForVisibilityOfAllElements(webdriver, elementList1);
		for (int i=0; i < elementList1.size(); i ++){
			if (elementList1.get(i).getText().toUpperCase().equals(text.toUpperCase())) {
				this.click(webdriver, elementList2.get(i));
				elementFound = true;
				break;
			}
		}
		if(!elementFound){
			throw new NoSuchElementException(generateErrorMessage(text));
		}
	}
	protected void selectCorrespondingElementByExactTextNoWait(WebDriver webdriver, List<WebElement> elementList1, List<WebElement> elementList2, String text){
		boolean elementFound = false;
		for (int i=0; i < elementList1.size(); i ++){
			if (elementList1.get(i).getText().toUpperCase().equals(text.toUpperCase())) {
				this.click(webdriver, elementList2.get(i));
				elementFound = true;
				break;
			}
		}
		if(!elementFound){
			throw new NoSuchElementException(generateErrorMessage(text));
		}
	}
	protected void selectCorrespondingElementByExactText(WebDriver webdriver, List<WebElement> elementList1, List<WebElement> elementList2, String text,  WebElement nextPageElement){
		while(!isElementInListByExactText(webdriver, elementList1, text.toUpperCase())) {
			if (isAtEndOfList(webdriver, nextPageElement)) {
				throw new NoSuchElementException(generateErrorMessage(text));
			}
			else {
				this.click(webdriver, nextPageElement);
				this.waitForLoadImageToFinish(webdriver);
			}
		}
		for (int i=0; i < elementList1.size(); i ++){
			if (elementList1.get(i).getText().toUpperCase().equals(text.toUpperCase())) {
				this.click(webdriver, elementList2.get(i));
				break;
			}
		}
	}
    protected void selectCorrespondingElementByExactTextOnReactPage(WebDriver webdriver, List<WebElement> elementList1, List<WebElement> elementList2, String text,  WebElement nextPageElement){
        while(!isElementInListByExactText(webdriver, elementList1, text.toUpperCase())) {
            if (isAtEndOfReactList(webdriver, nextPageElement)) {
                throw new NoSuchElementException(generateErrorMessage(text));
            }
            else {
                this.click(webdriver, nextPageElement);
                this.waitForLoadImageToFinish(webdriver);
            }
        }
        for (int i=0; i < elementList1.size(); i ++){
            if (elementList1.get(i).getText().toUpperCase().equals(text.toUpperCase())) {
                this.click(webdriver, elementList2.get(i));
                break;
            }
        }
    }

	protected void selectCorrespondingElementByContainsTextOnReactPage(WebDriver webdriver, List<WebElement> elementList1, List<WebElement> elementList2, String text,  WebElement nextPageElement){
		while(!isElementInListByContainsText(webdriver, elementList1, text.toUpperCase())) {
			if (isAtEndOfReactList(webdriver, nextPageElement)) {
				throw new NoSuchElementException(generateErrorMessage(text));
			}
			else {
				this.click(webdriver, nextPageElement);
				this.waitForLoadImageToFinish(webdriver);
			}
		}
		for (int i=0; i < elementList1.size(); i ++){
			if (elementList1.get(i).getText().toUpperCase().contains(text.toUpperCase())) {
				this.click(webdriver, elementList2.get(i));
				break;
			}
		}
	}

	protected void selectCorrespondingElementByExactText(WebDriver webdriver, List<WebElement> elementList1, List<WebElement> elementList2, String text, List<WebElement> selectPageElement){
		this.waitForVisibilityOfAllElements(webdriver, elementList1);
		boolean elementFound = false;
		int i = 0;
		while (i < selectPageElement.size()) {
			if (this.isElementInListByExactText(webdriver, elementList1, text)) {
				this.selectCorrespondingElementByExactText(webdriver, elementList1, elementList2, text);
				elementFound = true;
				break;
			}
			else {
				this.click(webdriver, selectPageElement.get(i + 1));
			}
			i++;
		}
		if(!elementFound)
			throw new NoSuchElementException(generateErrorMessage(text));
	}
	protected void selectCorrespondingElementByContainsText(WebDriver webdriver, List<WebElement> elementList1, List<WebElement> elementList2, String text){
		boolean elementFound = false;
		for (int i=0; i < elementList1.size(); i ++){
			if (elementList1.get(i).getText().toUpperCase().contains(text.toUpperCase())) {
				this.click(webdriver, elementList2.get(i));
				elementFound = true;
				break;
			}
		}
		if(!elementFound){
			throw new NoSuchElementException(generateErrorMessage(text));
		}
	}
    protected void hoverOverCorrespondingElementByExactText(WebDriver webdriver, List<WebElement> elementList1, List<WebElement> elementList2, String text){
        boolean elementFound = false;
        this.waitForVisibilityOfAllElements(webdriver, elementList1);
        for (int i=0; i < elementList1.size(); i ++){
            if (elementList1.get(i).getText().toUpperCase().equals(text.toUpperCase())) {
                new Actions(webdriver).moveToElement(elementList2.get(i)).build().perform();
                elementFound = true;
                break;
            }
        }
        if(!elementFound){
            throw new NoSuchElementException(generateErrorMessage(text));
        }
    }
	public void selectElementByLinkText(WebDriver webdriver, WebElement container, String linkText) {
		this.waitForVisibilityOfElement(webdriver, container);
		WebElement communityElement = container.findElement(By.linkText(linkText));
		this.click(webdriver, communityElement);
		this.waitForPageToLoad(webdriver);
	}
	public void selectElementByLinkText(WebDriver webdriver, WebElement container, String linkText, WebElement nextPageElement) {
        boolean found = false;
        while(!isAtEndOfList(webdriver, nextPageElement)) {
            try {
                selectElementByLinkText(webdriver, container, linkText);
                found = true;
                break;
            } catch (NoSuchElementException e) {
                click(webdriver, nextPageElement);
                waitForLoadImageToFinish(webdriver);
                this.waitForPageToLoad(webdriver);
            }
        }

        /* If on last page, then try to find the element
         * If already found on page, then won't try to find it again
         * If still can't find element, then error thrown */
        if(!found)
            selectElementByLinkText(webdriver, container, linkText);
    }
	public void selectElementByPartialLinkText(WebDriver webdriver, WebElement container, String linkText) {
		this.waitForVisibilityOfElement(webdriver, container);
		WebElement communityElement = container.findElement(By.partialLinkText(linkText));
		this.click(webdriver, communityElement);
		this.waitForPageToLoad(webdriver);
	}
	public void selectElementByPartialLinkTextNoWait(WebDriver webdriver, WebElement container, String linkText) {
		this.waitForVisibilityOfElement(webdriver, container);
		WebElement communityElement = container.findElement(By.partialLinkText(linkText));
		this.click(webdriver, communityElement);
	}
	public void selectElementByPartialLinkText(WebDriver webdriver, WebElement container, String linkText, WebElement nextPageElement) {
		boolean found = false;
		while(!isAtEndOfList(webdriver, nextPageElement)) {
			try {
				selectElementByLinkText(webdriver, container, linkText);
				found = true;
			} catch (NoSuchElementException e) {
				click(webdriver, nextPageElement);
				waitForLoadImageToFinish(webdriver);
			}
		}

		/* If on last page, then try to find the element
		 * If already found on page, then won't try to find it again
		 * If still can't find element, then error thrown */
		if(!found)
			selectElementByLinkText(webdriver, container, linkText);
	}
	/**
	 * Allows for selecting an item from a List in component by an attribute value
	 * Does not have wait for page to load line.
	 */
	public void selectElementFromListInComponentByAttributeNoLoad(WebDriver webdriver, List<WebElement> elementList, String attributeTitle, String attributeValue) {
		for (WebElement element : elementList) {
			if (isElementAttributeCorrect(webdriver, element, attributeTitle, attributeValue)) {
				this.click(webdriver, element);
				break;
			}
		}
	}
	/**
	 * Allows for clicking on an element if it is present on a page
	 * @param elementToSelect - the Element you want to click if it is present
	 */
	public void selectIfElementIsDisplayed(WebDriver webdriver, WebElement elementToSelect) {
		if(isElementDisplayed(webdriver, elementToSelect)) this.click(webdriver, elementToSelect);
	}
	public void selectSubPageDropdown(WebDriver webDriver, String itemToSelect) {
		WebElement dropdownToClick = webDriver.findElement(By.cssSelector("[id*=hl-natural-field][class=nl-field-link]"));
		if (isElementDisplayed(webDriver, dropdownToClick)) {
			this.click(webDriver, dropdownToClick);
			List<WebElement> subPageDropdownList = webDriver.findElements(By.cssSelector("[aria-labelledby*=hl-natural-field] [role=option] span"));
			selectElementFromListByExactText(webDriver, subPageDropdownList, itemToSelect);
			waitForPageToLoad(webDriver);
		}
	}

	//Verify functions
	public boolean isPageTitleCorrect(WebDriver webdriver, String title){
		this.waitForPageToLoad(webdriver);
		WebElement pageTitle = webdriver.findElement(By.cssSelector("h1[id*='PageTitle']"));
		return pageTitle.getText().toUpperCase().equals(title.toUpperCase());
	}
	public boolean isElementInListByExactText(WebDriver webdriver, List<WebElement> elementList, String elementText, WebElement nextPageElement){
		while(!isElementInListByExactText(webdriver, elementList, elementText.toUpperCase())) {
			if (isAtEndOfList(webdriver, nextPageElement)) {
				return false;
			}
			else {
				this.click(webdriver, nextPageElement);
				this.waitForLoadImageToFinish(webdriver);
			}
		}
		return true;
	}
	public boolean isElementInReactListByExactText(WebDriver webdriver, List<WebElement> elementList, String elementText, WebElement nextPageElement){
		while(!isElementInListByExactText(webdriver, elementList, elementText.toUpperCase())) {
			if (isAtEndOfReactList(webdriver, nextPageElement)) {
				return false;
			}
			else {
				this.click(webdriver, nextPageElement);
				this.waitForLoadImageToFinish(webdriver);
			}
		}
		return true;
	}
	protected boolean isElementInReactListByContainsText(WebDriver webdriver, List<WebElement> elementList, String elementText, WebElement nextPageElement){
		while(!isElementInListByContainsText(webdriver, elementList, elementText.toUpperCase())){
			if (isAtEndOfReactList(webdriver, nextPageElement))
				return false;
			else {
				this.click(webdriver, nextPageElement);
				this.waitForLoadImageToFinish(webdriver);
			}
		}
		return true;
	}
	protected boolean isElementInListByContainsText(WebDriver webdriver, List<WebElement> elementList, String elementText, WebElement nextPageElement){
		while(!isElementInListByContainsText(webdriver, elementList, elementText.toUpperCase())){
			if (isAtEndOfList(webdriver, nextPageElement))
				return false;
			else {
				this.click(webdriver, nextPageElement);
				this.waitForLoadImageToFinish(webdriver);
			}
		}
		return true;
	}
	public boolean isElementInListByExactText(WebDriver webdriver, List<WebElement> elementList, String text){
		String textUpperCase = text.toUpperCase();
		for(WebElement element : elementList){
			this.scrollToAndHighlightElement(webdriver, element);
			if(element.getText().trim().toUpperCase().equals(textUpperCase) &&
					this.isElementDisplayed(webdriver, element)){
				return true;
			}
		}
		return false;
	}
	public boolean isElementAtEndOfListByExactText(WebDriver webdriver, List<WebElement> elementList, String elementText, WebElement nextPageElement){
		while(!isElementInListByExactText(webdriver, elementList, elementText.toUpperCase())) {
			if (isAtEndOfList(webdriver, nextPageElement)) {
				return false;
			}
			else {
				this.click(webdriver, nextPageElement);
				this.waitForLoadImageToFinish(webdriver);
			}
		}

		int i = elementList.size() - 1;
		return this.isTextEquals(elementList.get(i), elementText.toUpperCase());
	}
	public boolean isMultipleElementsInListByExactTest(WebDriver webdriver, List<WebElement> elementList, String[] elements) {
		Set<String> elementSet = convertArrayIntoSet(elements);
		int numToCheck = elementSet.size();
		int counter = 0;

		for(WebElement element : elementList) {
			//if(this.isElementDisplayed(webdriver, element)) {
				this.scrollToAndHighlightElement(webdriver, element);
				String elementName = element.getText().trim().toUpperCase();
				if(elementSet.contains(elementName)) {
					counter++;
					if(counter >= numToCheck) break;
				}
			//}
		}
		return numToCheck == counter;
	}
	public boolean isElementInListByContainsText(WebDriver webdriver, List<WebElement> elementList, String text){
		String textUpperCase = text.toUpperCase();
		for(WebElement element : elementList){
			this.scrollToAndHighlightElement(webdriver, element);
			if(element.getText().toUpperCase().contains(textUpperCase) &&
					this.isElementDisplayed(webdriver, element)){
				return true;
			}
		}
		return false;
	}
	public boolean isElementInListByContainsTextNoScroll(WebDriver webdriver, List<WebElement> elementList, String text){
		String textUpperCase = text.toUpperCase();
		for(WebElement element : elementList){
			if(element.getText().toUpperCase().contains(textUpperCase) &&
					this.isElementDisplayed(webdriver, element)){
				return true;
			}
		}
		return false;
	}
	public boolean isElementAttributeInListCorrect(WebDriver webdriver, List<WebElement> elementList, String attribute, String value) throws Exception {
		this.waitForVisibilityOfAllElements(webdriver, elementList);
		try {
			for (WebElement option : elementList) {
				if (option.getAttribute(attribute).equalsIgnoreCase(value)) return true;
			}
            throw new Exception("No element was found in the list with the specified attribute value.");
		} catch (NullPointerException e) {
			return false;
		}
	}
	public boolean isElementAttributeInListCorrect(WebDriver webdriver, List<WebElement> elementList,String elementText, String attribute, String value) throws Exception {
		this.waitForVisibilityOfAllElements(webdriver, elementList);
		try {
			for (WebElement option : elementList) {
				if (option.getText().equalsIgnoreCase(elementText)) {
					return option.getAttribute(attribute).equalsIgnoreCase(value);
				}
			}
		} catch (NullPointerException e) {
			return false;
		}
		throw new Exception("No element was found in the list with the specified text.");
	}
	public boolean isElementAttributeInListByContainsText(WebDriver webdriver, List<WebElement> elementList,String elementText, String attribute, String value) throws Exception {
		this.waitForVisibilityOfAllElements(webdriver, elementList);
		try {
			for (WebElement option : elementList) {
				if (option.getText().equalsIgnoreCase(elementText)) {
					return option.getAttribute(attribute).contains(value);
				}
			}
		} catch (NullPointerException e) {
			return false;
		}
		throw new Exception("No element was found in the list with the specified text.");
	}
	public boolean isAtEndOfList(WebDriver webdriver, WebElement nextPageElement){
		try {
			nextPageElement.isDisplayed();
			this.waitForAttributeToBe(webdriver, 3, nextPageElement, "class", "aspNetDisabled");
			return true;
		}
		catch (TimeoutException e){
			return false;
		}
		catch (NoSuchElementException e){
			return true;
		}
	}
	public boolean isAtEndOfReactList(WebDriver webdriver, WebElement nextPageElement){
		try {
			nextPageElement.isDisplayed();
			this.waitForAttributeToContain(webdriver, nextPageElement, "class", "disabled");
			return true;
		}
		catch (TimeoutException e){
			return false;
		}
		catch (NoSuchElementException e){
			return true;
		}
	}
    public boolean isAtEndOfFrontEndReactList(WebDriver webdriver, WebElement nextPageElement) {
        try {
            nextPageElement.isDisplayed();
            return this.isElementAttributeCorrect(webdriver, nextPageElement,"aria-disabled", "true");
        }
        catch (TimeoutException e){
            return false;
        }
        catch (NoSuchElementException e){
            return true;
        }
    }
	public boolean isElementDisplayed (WebDriver webdriver, WebElement element){
		try {
			this.waitForVisibilityOfElement(webdriver, 8, element);
			this.scrollToAndHighlightElement(webdriver, element);
			return true;
		}
		catch (TimeoutException | NoSuchElementException e){
			return false;
		}
	}
	public boolean isElementDisplayedNoScroll (WebDriver webdriver, WebElement element){
		try {
			this.waitForVisibilityOfElement(webdriver, 8, element);
			return true;
		}
		catch (TimeoutException | NoSuchElementException e){
			return false;
		}
	}
	public boolean isElementDisplayed (WebDriver webdriver, int wait, WebElement element){
		try {
			this.waitForVisibilityOfElement(webdriver, wait, element);
			return true;
		}
		catch (TimeoutException e){
			return false;
		}
	}
    public boolean isElementClickable(WebDriver webdriver, int wait, WebElement element){
        try {
            this.waitForElementToBeClickable(webdriver, wait, element);
            return true;
        }
        catch (TimeoutException | NoSuchElementException e){
            return false;
        }
    }
    public boolean isElementPresent(WebDriver webdriver, String cssSelector){
        try {
            WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
            return true;
        }
        catch (TimeoutException | NoSuchElementException e){
            return false;
        }
    }
	public boolean isElementListDisplayed (WebDriver webdriver, List<WebElement> element){
		try {
			this.waitForVisibilityOfAllElements(webdriver, 8, element);
			return true;
		}
		catch (TimeoutException e){
			return false;
		}
	}
	public boolean isElementListDisplayed (WebDriver webdriver, int wait, List<WebElement> element){
		try {
			this.waitForVisibilityOfAllElements(webdriver, wait, element);
			return true;
		}
		catch (TimeoutException e){
			return false;
		}
	}
	public boolean isElementNotDisplayed (WebDriver webdriver, int wait, WebElement element){
		int count = 0;
		while (count < wait){
			try {
				element.isDisplayed();
				count++;
			}
			catch (NoSuchElementException e) {
				return true;
			}
		}
		return false;
	}
	public boolean isCorrespondingElementTextCorrect(WebDriver webdriver, List<WebElement> elementList1,String pageText, List<WebElement> elementList2, String expectedText){
		String pageTextUpperCase = pageText.toUpperCase();
		for (int i=0; i < elementList1.size(); i++){
			this.scrollToAndHighlightElement(webdriver, elementList1.get(i));
			if (elementList1.get(i).getText().toUpperCase().equals(pageTextUpperCase)){
				this.scrollToAndHighlightElement(webdriver, elementList2.get(i));
				return elementList2.get(i).getText().toUpperCase().equals(expectedText.toUpperCase());
			}
		}
		return false;
	}
	//isCorrespondingElementTextCorrect only stops when the first instance of pageText is found, this keeps going until it finds the correct instance of pageText that is paired with expectedText
	public boolean isCorrespondingElementTextCorrectRecursively(WebDriver webdriver, List<WebElement> elementList1,String pageText, List<WebElement> elementList2, String expectedText){
		String pageTextUpperCase = pageText.toUpperCase();
		for (int i=0; i < elementList1.size(); i++){
			this.scrollToAndHighlightElement(webdriver, elementList1.get(i));
			if (elementList1.get(i).getText().toUpperCase().equals(pageTextUpperCase)){
				this.scrollToAndHighlightElement(webdriver, elementList2.get(i));
				if(elementList2.get(i).getText().toUpperCase().equals(expectedText.toUpperCase()))
					return true;
			}
		}
		return false;
	}
	public boolean isCorrespondingElementTextCorrectByContains(WebDriver webdriver, List<WebElement> elementList1,String pageText, List<WebElement> elementList2, String expectedText){
		String pageTextUpperCase = pageText.toUpperCase();
		for (int i=0; i < elementList1.size(); i++){
			this.scrollToAndHighlightElement(webdriver, elementList1.get(i));
			if (elementList1.get(i).getText().toUpperCase().equals(pageTextUpperCase)){
				this.scrollToAndHighlightElement(webdriver, elementList2.get(i));
				return elementList2.get(i).getText().toUpperCase().contains(expectedText.toUpperCase());
			}
		}
		return false;
	}
	public boolean isCorrespondingElementDisplayed(WebDriver webdriver, List<WebElement> elementList1,String pageText, List<WebElement> elementList2, WebElement nextPageElement){
		while(!isCorrespondingElementDisplayed(webdriver, elementList1, pageText.toUpperCase(), elementList2)) {
			if (isAtEndOfList(webdriver, nextPageElement)) {
				return false;
			}
			else {
				this.click(webdriver, nextPageElement);
				this.waitForLoadImageToFinish(webdriver);
			}
		}
		return true;
	}
	public boolean isCorrespondingElementDisplayed(WebDriver webdriver, List<WebElement> elementList1, String pageText, List<WebElement> elementList2) {
		String pageTextUpperCase = pageText.toUpperCase();
		for (int i = 0; i < elementList1.size(); i++) {
			this.scrollToAndHighlightElement(webdriver, elementList1.get(i));
			if (elementList1.get(i).getText().toUpperCase().equals(pageTextUpperCase)) {
				try {
					this.scrollToAndHighlightElement(webdriver, elementList2.get(i));
					return this.isElementDisplayed(webdriver, elementList2.get(i));
				}catch (IndexOutOfBoundsException e){
					return false;
				}
			}
		}
		return false;
	}
    public boolean isCorrespondingElementDisplayedByContains(WebDriver webdriver, List<WebElement> elementList1, String pageText, List<WebElement> elementList2) {
        for (int i = 0; i < elementList1.size(); i++) {
            this.scrollToAndHighlightElement(webdriver, elementList1.get(i));
            if (elementList1.get(i).getText().toUpperCase().contains(pageText.toUpperCase())) {
                try {
                    this.scrollToAndHighlightElement(webdriver, elementList2.get(i));
                    return this.isElementDisplayed(webdriver, elementList2.get(i));
                }catch (IndexOutOfBoundsException e){
                    return false;
                }
            }
        }
        return false;
    }
	public boolean isCorrespondingElementContainsTextCorrect(WebDriver webdriver, List<WebElement> elementList1,String pageText, List<WebElement> elementList2, String expectedText){
		String pageTextUpperCase = pageText.toUpperCase();
		for (int i=0; i < elementList1.size(); i++){
			this.scrollToAndHighlightElement(webdriver, elementList1.get(i));
			if (elementList1.get(i).getText().toUpperCase().contains(pageTextUpperCase)){
				this.scrollToAndHighlightElement(webdriver, elementList2.get(i));
				return elementList2.get(i).getText().toUpperCase().contains(expectedText.toUpperCase());
			}
		}
		return false;
	}
    public boolean isCorrespondingElementAttributeCorrect(WebDriver webdriver, List<WebElement> elementList1,String pageText, List<WebElement> elementList2, String attributeToGet, String expectedAttributeValue){
        String pageTextUpperCase = pageText.toUpperCase();
        for (int i=0; i < elementList1.size(); i++){
            this.scrollToAndHighlightElement(webdriver, elementList1.get(i));
            if (elementList1.get(i).getText().toUpperCase().contains(pageTextUpperCase)){
                this.scrollToAndHighlightElement(webdriver, elementList2.get(i));
                return isElementAttributeCorrect(webdriver, elementList2.get(i), attributeToGet, expectedAttributeValue);
            }
        }
        return false;
    }
	public boolean isCorrespondingElementAttributeCorrectByContains(WebDriver webdriver, List<WebElement> elementList1,String pageText, List<WebElement> elementList2, String attributeToGet, String expectedAttributeValue){
		String pageTextUpperCase = pageText.toUpperCase();
		for (int i=0; i < elementList1.size(); i++){
			this.scrollToAndHighlightElement(webdriver, elementList1.get(i));
			if (elementList1.get(i).getText().toUpperCase().contains(pageTextUpperCase)){
				this.scrollToAndHighlightElement(webdriver, elementList2.get(i));
				return isElementAttributeContains(webdriver, elementList2.get(i), attributeToGet, expectedAttributeValue);
			}
		}
		return false;
	}
	public boolean isElementTextCorrect(WebDriver webdriver, WebElement element, String expectedText){
		this.scrollToAndHighlightElement(webdriver, element);
		return isTextEquals(element, expectedText.toUpperCase());
	}
	public boolean isElementTextCorrectByContains(WebDriver webdriver, WebElement element, String expectedText){
		this.scrollToAndHighlightElement(webdriver, element);
		return element.getText().trim().toUpperCase().contains(expectedText.toUpperCase());
	}
	public boolean isElementInListByExactText(WebDriver webdriver, List<WebElement> elementList, String text, List<WebElement> selectPageButtonList){
		this.waitForVisibilityOfAllElements(webdriver, elementList);
		int i = 0;
		while (i < selectPageButtonList.size()) {
			if (this.isElementInListByExactText(webdriver, elementList, text)) {
				return true;
			} else {
				this.click(webdriver, selectPageButtonList.get(i));
			}
			i++;
		}
		return false;
	}
	public boolean isElementAttributeCorrect(WebDriver webdriver, WebElement element, String attribute, String value) {
		try {
			this.waitForVisibilityOfElement(webdriver, element);
			this.scrollToAndHighlightElement(webdriver, element);
			return element.getAttribute(attribute).toUpperCase().equals(value.toUpperCase());
		} catch (NullPointerException | NoSuchElementException e) {
			return false;
		}
	}
	public boolean isElementAttributeCorrect(WebDriver webdriver, int wait, WebElement element, String attribute, String value) {
		try {
			this.waitForVisibilityOfElement(webdriver, wait, element);
			this.scrollToAndHighlightElement(webdriver, element);
			return element.getAttribute(attribute).toUpperCase().equals(value.toUpperCase());
		} catch (NullPointerException | NoSuchElementException e) {
			return false;
		}
	}
	public boolean isElementAttributeContains(WebDriver webdriver, WebElement element, String attribute, String value){
		this.waitForVisibilityOfElement(webdriver, element);
		try {
			this.scrollToAndHighlightElement(webdriver, element);
			return element.getAttribute(attribute).toUpperCase().contains(value.toUpperCase());
		} catch (NullPointerException e) {
			return false;
		}
	}
	public boolean isElementAttributeContainsNoScroll(WebElement element, String attribute, String value){
		try {
			return element.getAttribute(attribute).toUpperCase().contains(value.toUpperCase());
		} catch (NullPointerException e) {
			return false;
		}
	}
	public boolean isElementCSSPropertyCorrect(WebDriver webdriver, WebElement element, String property, String value){
		//this method will not work with shorthand CSS properties
		this.waitForPageToLoadNoJavascript(webdriver);
		try {
			this.scrollToAndHighlightElement(webdriver, element);
			return element.getCssValue(property).toUpperCase().contains(value.toUpperCase());
		} catch (NullPointerException e) {
			return false;
		}
	}
	public boolean isAttributeOnElement(WebDriver webDriver, WebElement element, String attribute){
		this.waitForVisibilityOfElement(webDriver, element);
		this.scrollToAndHighlightElement(webDriver, element);
		try {
			element.getAttribute(attribute);
		}
		catch (NullPointerException e) {
			return true;
		}
		return false;
	}
	public boolean isElementListInAZOrderCaseSensitive(WebDriver webdriver, List<WebElement> elementList){
		List<String> textList = new ArrayList<>();
		for(WebElement element: elementList){
			this.scrollToAndHighlightElement(webdriver, element);
			textList.add(element.getText());
		}
		for(int i = 0; i < textList.size() - 1; i++){
			if(textList.get(i).compareTo(textList.get(i+1)) > 0){
				return false;
			}
		}
		return true;
	}
	public boolean isElementListInAZOrderNoncaseSensitive(List<WebElement> elementList){
		List<String> textList = new ArrayList<>();
		for(WebElement element: elementList){
			textList.add(element.getText());
		}
		for(int i = 0; i < textList.size() - 1; i++){
			if(textList.get(i).compareToIgnoreCase(textList.get(i+1)) > 0){
				return false;
			}
		}
		return true;
	}
	public boolean isElementListInZAOrderCaseSensitive(List<WebElement> elementList){
		List<String> textList = new ArrayList<>();
		for(WebElement element: elementList){
			textList.add(element.getText());
		}
		for(int i = 0; i < textList.size() - 1; i++){
			if(textList.get(i).compareTo(textList.get(i+1)) < 0){
				return false;
			}
		}
		return true;
	}
	public boolean isElementListInZAOrderNoncaseSensitive(List<WebElement> elementList){
		List<String> textList = new ArrayList<>();
		for(WebElement element: elementList){
			textList.add(element.getText());
		}
		for(int i = 0; i < textList.size() - 1; i++){
			if(textList.get(i).compareToIgnoreCase(textList.get(i+1)) < 0){
				return false;
			}
		}
		return true;
	}
	public boolean isOptionSelectedInSingleSelectionDropdown(WebDriver webdriver, WebElement dropdownElement, String option){
		Select select = new Select(dropdownElement);
		return this.isElementInListByExactText(webdriver, select.getAllSelectedOptions(), option);
	}
	public boolean isListInAZOrderCaseSensitive(List<String> textList){
		for(int i = 0; i < textList.size() - 1; i++){
			if(textList.get(i).compareTo(textList.get(i+1)) > 0){
				return false;
			}
		}
		return true;
	}
	public boolean isListInZAOrderCaseSensitive(List<String> textList){
		for(int i = 0; i < textList.size() - 1; i++){
			if(textList.get(i).compareTo(textList.get(i+1)) < 0){
				return false;
			}
		}
		return true;
	}
	public boolean isCurrentURLCorrectByContains(WebDriver webdriver, String substring){
		this.waitForPageToLoad(webdriver,20);
		return webdriver.getCurrentUrl().toUpperCase().contains(substring.toUpperCase());
	}
	public boolean isCurrentUrlCorrectByExactText(WebDriver webdriver, String substring){
		return webdriver.getCurrentUrl().toUpperCase().equals(substring.toUpperCase());
	}
	public boolean isElementClickable(WebElement element){
		return element.isDisplayed() && element.isEnabled();
	}
	/**
	 * Allows for acquiring the value of an input field that doesn't have a 'value' attribute where getText and getAttribute('value') don't work
	 */
	public boolean isInputFieldCorrectJavascript(WebDriver webdriver, WebElement elementToUse, String textToCheckFor) {
		return ((JavascriptExecutor)webdriver).executeScript("return arguments[0].value", elementToUse).toString().equalsIgnoreCase(textToCheckFor);
	}
	/**
	 * Method that allows for returning if the input 'value' attribute matches what it is expected to be - this is helpful for
	 * react modals that don't allow getText to get input values
	 * @param inputElement -input element to check the value attribute of
	 * @param textToCheckFox - text to check if matches what is in the value attribute
	 */
	public boolean isInputFieldValueCorrect(WebDriver webDriver, WebElement inputElement, String textToCheckFox) {
		this.waitForPageToLoad(webDriver);
		return this.isElementAttributeCorrect(webDriver, inputElement, "value", textToCheckFox);
	}
	/**
	 * Takes a webelement, extracts the text, formats it, then compares it to a string.
	 * @param element WebElement to extract text from.
	 * @param text Text to compare.
	 * @return true if text from element matches text parameter.
	 */
	public boolean isTextEquals(WebElement element, String text) {
		return element.getText().trim().toUpperCase().equals(text.toUpperCase());
	}
	public boolean isTextEquals(WebDriver webdriver, WebElement element, String text) {
		this.scrollToAndHighlightElement(webdriver, element);
		return element.getText().trim().toUpperCase().equals(text.toUpperCase());
	}
	public boolean isTextEqualsNoScroll(WebDriver webdriver, WebElement element, String text) {
//		this.scrollToAndHighlightElement(webdriver, element);
		return element.getText().trim().toUpperCase().equals(text.toUpperCase());
	}
	public boolean isElementPresentByCSS(WebDriver webdriver, WebElement outerElement, String css) {
		try {
			WebElement element = outerElement.findElement(By.cssSelector(css));
			return isElementDisplayed(webdriver, element);
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	/**
	 * Allows for verifying a given string datetime stamp is of a particular format/pattern
	 * @param timeStampString - string of a timestamp, e.g. 11-19-2020 17:27:12
	 * @param expectedFormat - a datetime format to check if the string matches, e.g. MM-dd-yyyy HH:mm:ss
	 */
	public boolean isTimeStampCorrectFormat(String timeStampString, String expectedFormat) {
		SimpleDateFormat format = new SimpleDateFormat(expectedFormat);
		format.setLenient(false);

		try{
			format.parse(timeStampString);
			return true;
		}
		catch(ParseException e) {
			return false;
		}
	}
	public boolean isTimeWithinIntervalSpecified(String format, String timeUnit, int numberOfTimeRange, String expectedTime, String timeToCompare) {
		boolean isBetween = false;
		LocalDateTime time1 = LocalDateTime.parse(expectedTime, DateTimeFormatter.ofPattern(format));
		LocalDateTime time2 = LocalDateTime.parse(expectedTime, DateTimeFormatter.ofPattern(format));

		try {
			switch (timeUnit.toUpperCase()) {
				case "YEARS":
					time1 = time1.minusYears(numberOfTimeRange);
					time2 = time2.plusYears(numberOfTimeRange);
					break;
				case "MONTHS":
					time1 = time1.minusMonths(numberOfTimeRange);
					time2 = time2.plusMonths(numberOfTimeRange);
					break;
				case "WEEKS":
					time1 = time1.minusWeeks(numberOfTimeRange);
					time2 = time2.plusWeeks(numberOfTimeRange);
					break;
				case "DAYS":
					time1 = time1.minusDays(numberOfTimeRange);
					time2 = time2.plusDays(numberOfTimeRange);
					break;
				case "HOURS":
					time1 = time1.minusHours(numberOfTimeRange);
					time2 = time2.plusHours(numberOfTimeRange);
					break;
				default:
					time1 = time1.minusMinutes(numberOfTimeRange);
					time2 = time2.plusMinutes(numberOfTimeRange);
					break;
			}

			LocalDateTime dateTimeToCheck = LocalDateTime.parse(timeToCompare, DateTimeFormatter.ofPattern(format));

			if (dateTimeToCheck.isBefore(time2) && dateTimeToCheck.isAfter(time1)
					|| dateTimeToCheck.isEqual(LocalDateTime.parse(expectedTime, DateTimeFormatter.ofPattern(format)))) isBetween = true;

		} catch (DateTimeException e) {
			e.printStackTrace();
		}
		return isBetween;
	}
	/*
Method that allow for stepping into an iframe, checking if element is found, then stepping out of iframe again
 */
	public boolean isIframedElementDisplayed(WebDriver webdriver, WebElement iframeElement, WebElement elementToFind) {
		waitForPageToLoad(webdriver);
		if(this.isElementDisplayed(webdriver, iframeElement)) {
			this.waitForVisibilityOfFrameAndSwitchToIt(webdriver, iframeElement);
		}
		boolean isElementOnIframeFound = isElementDisplayed(webdriver, 12, elementToFind);
		webdriver.switchTo().defaultContent();
		return isElementOnIframeFound;
	}

	//Window functions
	protected void switchToNewWindow(WebDriver webdriver){
        Set<String> windowHandles = webdriver.getWindowHandles();
        String newWindowId = (String) windowHandles.toArray()[windowHandles.size()-1];
        try {
            webdriver.switchTo().window(newWindowId);
        } catch (NoSuchWindowException ignored) {
        }

	}
	protected void closeCurrentWindow(WebDriver webDriver){
		webDriver.close();
	}
	public void closeCurrentAndSwitchToNewWindow(WebDriver webdriver) {
		this.closeCurrentWindow(webdriver);
		this.switchToNewWindow(webdriver);
	}

	//Selenium
    public void click(WebDriver webdriver, WebElement element){
		waitForElementToBeClickable(webdriver, element);
		scrollToAndHighlightElement(webdriver, element);
        element.click();
    }
    public void clickNoScroll(WebDriver webdriver, WebElement element){
        waitForElementToBeClickable(webdriver, element);
        element.click();
    }
    public void click(WebDriver webdriver, int time, WebElement element){
		waitForElementToBeClickable(webdriver, time, element);
        scrollToAndHighlightElement(webdriver, element);
        waitForElementToBeClickable(webdriver, time, element);
        element.click();
    }
	public void clickWithJavascript(WebDriver webdriver, WebElement element) {
		this.waitForElementToBeClickable(webdriver, element);
		JavascriptExecutor executor = (JavascriptExecutor)webdriver;
		executor.executeScript("arguments[0].click();", element);
		this.waitForPageToLoad(webdriver);
	}
	/**
	 * Allows for selecting an SVG Element typically found on a react page
	 */
	public void clickSVG(WebDriver webdriver, WebElement svgElementToSelect) {
		Actions builder = new Actions(webdriver);
		builder.click(svgElementToSelect).build().perform();
	}

	//Enter data functions
	public void enterText(WebDriver webdriver, WebElement element, String text){
		this.waitForElementToBeClickable(webdriver, element);
		this.scrollToAndHighlightElement(webdriver, element);
		element.click();
		element.clear();
		element.sendKeys(text);
	}

	public void enterReactText(WebDriver webdriver, WebElement element, String text){
		this.waitForElementToBeClickable(webdriver, element);
		this.scrollToAndHighlightElement(webdriver, element);
		element.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
		element.sendKeys(Keys.BACK_SPACE);
	    element.sendKeys(text);
	}
	public void enterReactTextNoClear(WebDriver webdriver, WebElement element, String text){
		this.waitForElementToBeClickable(webdriver, element);
		this.scrollToAndHighlightElement(webdriver, element);
		element.sendKeys(text);
	}
	public void enterTextNoScroll(WebDriver webdriver, WebElement element, String text){
		this.waitForElementToBeClickable(webdriver, element);
//		this.scrollToAndHighlightElement(webdriver, element);
		element.click();
		element.clear();
		element.sendKeys(text);
	}

	//Key/value functions.
	/**
	 * This function copies the form values from the changeFields array into the baseCopy array which has the same fields
	 * but blank values. The equals check disregards case.
	 * It does this by going through each element in changeFields and checks it against each element in baseArray.
	 * If the first value matches, then the second value of baseCopy is set to the second value of changeFields.
	 *
	 * Potential issue: Should boolean found be set to false after each iteration of the outer for loop? Each form field
	 * in changeFields should have a corresponding form field in baseArray. If not, then it should throw an exception.
	 */
	protected String[][] addValuesToKeyValueBaseArray(String[][] baseArray, String[][] changeFields) throws Exception {
		boolean found = false;
		for (int i = 0; i < changeFields.length; i++) {
			for (int j = 0; j < baseArray.length; j++) {
				if (baseArray[j][0].toUpperCase().equals(changeFields[i][0].toUpperCase())) {
					baseArray[j][1] = changeFields[i][1];
					found = true;
					break;
				}
			}
			if (!found) {
				throw new Exception("The expected form field " +  changeFields[i][0]+ " was not found.");
			}
		}
		return baseArray;
	}
	private Set<String> convertArrayIntoSet(String[] elements) {
		Set<String> elementSet = new HashSet<>();
		for(String element : elements)
			elementSet.add(element.trim().toUpperCase());
		return elementSet;
	}

	//Scroll functions
	protected void highlightElement(WebDriver webDriver, WebElement element){
//        JavascriptExecutor js=(JavascriptExecutor)webDriver;
//        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
//        try
//        {
//            Thread.sleep(100);
//        }
//        catch (InterruptedException | NoSuchElementException e) {
//        }
//        js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);
	}
	public void moveToElementFromListByExactText(WebDriver webdriver, List<WebElement> elementList, String text){
		boolean elementFound = false;
		String textUpperCase = text.toUpperCase();
		for(WebElement element : elementList) {
			if(element.getText().toUpperCase().equals(textUpperCase)) {
				Actions action = new Actions(webdriver);
				action.moveToElement(element).perform();
				elementFound = true;
				break;
			}
		}
		if(!elementFound){
			throw new NoSuchElementException(generateErrorMessage(text));
		}
	}
	protected void scrollToElement(WebDriver webDriver, WebElement element){
		try{
			JavascriptExecutor js=(JavascriptExecutor)webDriver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		}catch (NoSuchElementException e){

		}
	}
	protected void scrollAboveElement(WebDriver webDriver, WebElement element){
		try{
			JavascriptExecutor js=(JavascriptExecutor)webDriver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
			js.executeScript("window.scrollBy(0, -100)");
		}catch (NoSuchElementException e){

		}

/*		Actions actions = new Actions(webDriver);
		actions.moveToElement(element=);
		actions.perform();*/
	}

	protected void scrollAboveElement150(WebDriver webDriver, WebElement element){
		try{
			JavascriptExecutor js=(JavascriptExecutor)webDriver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
			js.executeScript("window.scrollBy(0, -150)");
		}catch (NoSuchElementException e){
		}
	}
	protected void scrollToAndHighlightElement(WebDriver webDriver, WebElement element){
		try {
//			JavascriptExecutor js=(JavascriptExecutor)webDriver;

			boolean found = (Boolean)((JavascriptExecutor)webDriver).executeScript(
					"var elem = arguments[0],                 " +
							"  box = elem.getBoundingClientRect(),    " +
							"  cx = box.left + box.width / 2,         " +
							"  cy = box.top + box.height / 2,         " +
							"  e = document.elementFromPoint(cx, cy); " +
							"for (; e; e = e.parentElement) {         " +
							"  if (e === elem)                        " +
							"    return true;                         " +
							"}                                        " +
							"return false;                            "
					, element);

			if(!found)
				scrollAboveElement(webDriver, element);

//			String originalStyle = element.getAttribute("style");
//        	js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "background: yellow; border: 2px solid red");

			//Set this to determine how long the box is visible
//            Thread.sleep(25);
//
//            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
		}
		catch (/*InterruptedException |*/ NoSuchElementException e) {
		}
	}
	protected void scrollToAndHighlightCorrespondingElement(WebDriver webdriver, List<WebElement> elementList1, List<WebElement> elementList2, String text){
		this.waitForVisibilityOfAllElements(webdriver, elementList1);
		String textUpperCase = text.toUpperCase();
		for (int i=0; i < elementList1.size(); i ++){
			if (elementList1.get(i).getText().toUpperCase().equals(textUpperCase)) {
				this.scrollToAndHighlightElement(webdriver, elementList2.get(i));
				break;
			}
		}
	}
	protected void scrollAboveCorrespondingElement(WebDriver webdriver, List<WebElement> elementList1, List<WebElement> elementList2, String text){
		this.waitForVisibilityOfAllElements(webdriver, elementList1);
		String textUpperCase = text.toUpperCase();
		for (int i=0; i < elementList1.size(); i ++){
			if (elementList1.get(i).getText().toUpperCase().equals(textUpperCase)) {
				this.scrollAboveElement150(webdriver, elementList2.get(i));
				break;
			}
		}
	}

	protected void scrollToAndHighlightElementInListByExactText(WebDriver webdriver, List<WebElement> elementList, String text){
		String textUpperCase = text.toUpperCase();
		for(WebElement element : elementList){
			if(element.getText().toUpperCase().equals(textUpperCase)){
				this.scrollToAndHighlightElement(webdriver, element);
				break;
			}
		}
	}
	protected void scrollToAndHighlightElementInListByContainsText(WebDriver webdriver, List<WebElement> elementList, String text){
		String textUpperCase = text.toUpperCase();
		for(WebElement element : elementList){
			if(element.getText().toUpperCase().contains(textUpperCase)){
				this.scrollToAndHighlightElement(webdriver, element);
				break;
			}
		}
	}
	/**
	 * Scrolls to the Bottom of current page you are on, helpful if items on the page require scroll to load
	 */
	public void scrollToBottomOfPage(WebDriver webdriver) {
		((JavascriptExecutor) webdriver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	public void scrollToTopOfPage(WebDriver webdriver) {
		((JavascriptExecutor) webdriver).executeScript("window.scrollTo(0, 0)");
	}
	/**
	 * Scrolls to the Top of current page you are on, helpful if pages, like Iframes, are scrolled down making elements uninteractlabe
	 * @param iframe - the iframe you need to switch out of, scroll to top of page, then switch back into
	 */
	public void scrollToTopOfIframedAdminPage(WebDriver webdriver, WebElement iframe) {
		webdriver.switchTo().defaultContent();
		((JavascriptExecutor) webdriver).executeScript("window.scrollTo(0,0)");
		if(this.isElementDisplayed(webdriver, iframe)) this.waitForVisibilityOfFrameAndSwitchToIt(webdriver, iframe);
	}

	//Other functions
	/**
	 * This method collects js console errors found on the page currently navigated to on calling this method.
	 * @throws URISyntaxException - Do not remove from method signature.
	 * Ensure the methods that call on getJSLogsInfo also throw URISyntaxException or Exception
	 */
	public void getJSLogsInfo(WebDriver webdriver) throws URISyntaxException {
		LogEntries logEntries = webdriver.manage().logs().get(LogType.BROWSER);

		for(LogEntry entry : logEntries) {
			//Convert epoch time from the log entry in milliseconds to local date time
			LocalDateTime convertedEpochTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(entry.getTimestamp()), ZoneId.systemDefault());

			//Format the time stamp
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
			String formattedDateTime = convertedEpochTime.format(formatter);

			//Attach readable message to Allure report
			String attachedConsoleErrorMessage = formattedDateTime + " " + ZoneId.systemDefault().getId() + " " + entry.getLevel() + " " + entry.getMessage();
			Allure.addAttachment("JS Console Errors:", attachedConsoleErrorMessage);
		}
	}

	public String generateErrorMessage(String text){
		String method = Thread.currentThread().getStackTrace()[3].toString().split("select")[1].split("[(]")[0];
		method = method.replaceAll("(?!^)([A-Z])", " $1");
		return "The text \"" + text + "\" was not found with/as a " + method.toLowerCase() + ".";
	}
	//commenting this out because we're trying to reduce overall build time.
	/**
	 * Method that allows for showing/hiding columns on a React Grid
	 * @param columnName - name of column to hide/show
	 * @param showOrHide - boolean representing to show or hide the column
	 */
	public void showHideReactColumn(WebDriver webdriver, String columnName, boolean showOrHide) {
		this.click(webdriver, webdriver.findElement(By.cssSelector("#column-visibility-selection")));
		// acquire the column values and associated checkboxes
		List<WebElement> columnCheckboxes = webdriver.findElements(By.cssSelector(".nl-select-option input"));
		List<WebElement> columnNames = webdriver.findElements(By.cssSelector(".nl-select-option span"));
		WebElement checkBoxForName = this.getCorrespondingElementInList(columnNames, columnCheckboxes, columnName);
		// enable or disable column displaying based on boolean passed in
		if(checkBoxForName.isSelected() && !showOrHide || !checkBoxForName.isSelected() && showOrHide) this.click(webdriver, checkBoxForName);
		new Actions(webdriver).sendKeys(Keys.ESCAPE).build().perform();
	}
	/**
	 * Method used for waiting for an input to be present, then sending a image path to it
	 */
	public void uploadFileFromBrowserStack(WebDriver webdriver, By inputElementLocator, String imageTypePath) {
		WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(8));
		wait.until(ExpectedConditions.visibilityOfElementLocated(inputElementLocator));
		webdriver.findElement((inputElementLocator)).sendKeys(imageTypePath);
	}
	/**
	 * Method for allowing selecting the number of Entries to display on a react grid list page
	 * @param entryNumber - integer consisting of the Entries drop-down options (needs to be either 15, 25, 50, or 100)
	 */
	public void setReactGridListEntriesCount(WebDriver webdriver, int entryNumber) {
		WebElement viewEntriesDropdown = webdriver.findElement(By.cssSelector("[name=pagination-selection]"));
		String numberToSelectToString = Integer.toString(entryNumber);
		// if entry count is not already what you wish to set it to
		if(!this.isOptionSelectedInSingleSelectionDropdown(webdriver, viewEntriesDropdown, numberToSelectToString)) {
			selectDropdownOptionByVisibleText(viewEntriesDropdown, numberToSelectToString);
		}
		waitForPageToLoad(webdriver);
	}
    /**
     * Method that allows for selecting tabbed page options within any React Page
     * @param tabToNavigateTo - text of the tab you want to click on
     */
    public void navigateToTabbedReactPage(WebDriver webdriver, String tabToNavigateTo) {
        List<WebElement> tabbedPages = webdriver.findElements(By.cssSelector("[role=tablist] li a"));
        this.selectElementFromListByExactText(webdriver, tabbedPages, tabToNavigateTo);
        waitForPageToLoad(webdriver);
    }
	/**
     * Method that allows for selecting tabbed page options on Iframed .aspx pages
     * @param iframeElement - element of the Iframe
     * @param tabToNavigateTo - text of the tab you want to click on
     */
    public void navigateToTabbedIframePage(WebDriver webdriver, WebElement iframeElement, String tabToNavigateTo) {
        List<WebElement> tabbedPages = webdriver.findElements(By.cssSelector(".nav-tabs a"));
        this.selectElementFromListByExactText(webdriver, tabbedPages, tabToNavigateTo);
        this.waitForPageToLoad(webdriver);
        if(this.isElementDisplayed(webdriver, iframeElement)) {
            this.waitForVisibilityOfFrameAndSwitchToIt(webdriver, iframeElement);
        }
    }
    public void setIframedRMEntriesCount(WebDriver webdriver, int entryNumber) {
        WebElement viewEntriesDropdown = webdriver.findElement(By.cssSelector("select[name*=Table_1]"));
        selectDropdownOptionByVisibleText(viewEntriesDropdown, Integer.toString(entryNumber));
        waitForPageToLoad(webdriver);
    }
    /**
     * Allows for searching a React Admin Core Page for content
     * @param textToSearchFor - the text to enter in the search field
     */
    public void searchAdminCoreReactPage(WebDriver webdriver, String textToSearchFor) {
        waitForPageToLoad(webdriver);
        WebElement searchField = webdriver.findElement(By.cssSelector("[name='hlc-datagrid-search']"));
        WebElement searchButton = webdriver.findElement(By.cssSelector("[title=Search][role=button]"));
        // show search field if hidden
        if (!isElementDisplayed(webdriver, searchField)) this.click(webdriver, searchButton);

        // if search is not already showing results you are seeking, then perform search
        if (!isElementAttributeCorrect(webdriver, searchField,"value", textToSearchFor)) {
            // enter text to search for
            this.click(webdriver, searchField);
            searchField.clear();
            this.waitForPageToLoad(webdriver);
            searchField.sendKeys(textToSearchFor);
			this.waitForReactTableToRefresh(webdriver);
        }
    }
	public void searchAdminCoreReactPageNew(WebDriver webdriver, String textToSearchFor) {
		waitForPageToLoad(webdriver);
		WebElement searchField = webdriver.findElement(By.cssSelector("[name='hlc-datagrid-search']"));
		WebElement searchButton = webdriver.findElement(By.cssSelector("[title=Search][role=button]"));
		// show search field if hidden
		if (!isElementDisplayed(webdriver, searchField)) this.click(webdriver, searchButton);

		// if search is not already showing results you are seeking, then perform search
		if (!isElementAttributeCorrect(webdriver, searchField,"value", textToSearchFor)) {
			// enter text to search for
			this.click(webdriver, searchField);
			searchField.clear();
			this.waitForPageToLoad(webdriver);
			searchField.sendKeys(textToSearchFor);
			this.waitForReactTableToRefreshNew(webdriver);
		}
	}
	
    /**
     * Allows for multi-selecting (control-clicking) an element in a react list
     * @param listOfElements - list to multi-select elements from
     * @param textOfElementToClick - element to control-select
     * @param nextPageButton - next page button elemeent
     */
    public void controlClickElementInReactList(WebDriver webdriver, List<WebElement> listOfElements, String textOfElementToClick, WebElement nextPageButton) {
        if(isElementInReactListByExactText(webdriver, listOfElements, textOfElementToClick, nextPageButton)) {
            int index = this.getIndexOfElementInListByExactText(listOfElements, textOfElementToClick);
            new Actions(webdriver).keyDown(Keys.LEFT_CONTROL).click(listOfElements.get(index)).build().perform();
            this.waitForPageToLoad(webdriver);
        }
    }
    public void acknowledgePendo(WebDriver webdriver) {
        this.waitForPageToLoad(webdriver);
        List<WebElement> pendoAcknowledgeButtons = webdriver.findElements(By.cssSelector("[id*=pendo-button]"));
        if(pendoAcknowledgeButtons.size()>0) for(WebElement button : pendoAcknowledgeButtons) {
            if(this.isTextEquals(button, "Dismiss") || this.isTextEquals(button, "Got it!")) {
                this.click(webdriver, button);
                break;
            }
        }
        this.waitForPageToLoad(webdriver);
    }
	public void acknowledgePendoNoWaits(WebDriver webdriver) {
		List<WebElement> pendoAcknowledgeButtons = webdriver.findElements(By.cssSelector("[id*=pendo-button]"));
		if(pendoAcknowledgeButtons.size()>0) for(WebElement button : pendoAcknowledgeButtons) {
			if(this.isTextEquals(button, "Dismiss") || this.isTextEquals(button, "Got it!")) {
				this.click(webdriver, button);
				break;
			}
		}
	}
    public void acknowledgePendoInIframe(WebDriver webdriver) {
        this.waitForPageToLoad(webdriver);
        // Switch to main page Iframe
        List<WebElement> iframes = webdriver.findElements(By.cssSelector(".chrome-page-body iframe"));
        if(iframes.size() >0) waitForVisibilityOfFrameAndSwitchToIt(webdriver, iframes.get(0));
        acknowledgePendo(webdriver);
    }

	/* This method accepts actions (parameter 2) and an int that indicates how many times an action should be retried
	 * If the action fails, print a log message and refresh the page
	 */
	public void performWithRetriesAndRefresh(WebDriver webdriver, Supplier<Boolean> actionToRun, int numberOfRetries) {
		int attempts = 0;
		while(attempts < numberOfRetries) {
			try {
				if(actionToRun.get()) {
					break;
				}
			} catch (Exception e) {
				System.out.println("Attempt: " + attempts + " failed with exception: ");
				e.printStackTrace();
				webdriver.navigate().refresh();
			}
		}
	}
}