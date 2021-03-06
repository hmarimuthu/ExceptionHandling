package com.etaap.webui.selenium;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

import com.etaap.common.util.LogUtil;
import com.etaap.core.TestBed;
import com.etaap.core.TestBedManager;
import com.etaap.core.config.util.ConfigUtil;
import com.etaap.core.current.CurrentDrivers;
import com.etaap.core.exception.HandleException;
import com.etaap.core.exception.PageException;
import com.etaap.core.resources.TestTypes;
import com.etaap.core.resources.WaitCondition;
import com.etaap.webui.ITafElement;
import com.etaap.webui.ITafElementFactory;
import com.etaap.webui.TafElementFactoryManager;
import com.etaap.webui.util.BrowserInfoUtil;

import io.appium.java_client.AppiumDriver;

/**
 * The Class WebPage.
 */
public class WebPage {

	/** The log. */
	private static Log log = LogUtil.getLog(WebPage.class);

	/** The driver. */
	protected WebDriver driver = null;

	/** The test type. */
	private String testType = null;

	String testBedName = null;

	ITestContext currentContext = null;

	public WebPage() {
		log.debug(" Driver is not created in WebPage");
	}

	/**
	 * Initializes web driver and test bed manager.
	 * 
	 * @throws Exception
	 */

	public WebPage(ITestContext context) {

		testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");

		TestBed testBed = createTestBed();
		addToCurrentTestBeds(testBed);
		addDrivertoCurrentDrivers(testBed);
		identifyTestBedType(testBed);

		this.currentContext = context;

	}

	private void identifyTestBedType(TestBed testBed) {
		if (testBed.getTestType().equals(TestTypes.WEB.getTestType())) {
			this.driver = (RemoteWebDriver) testBed.getDriver();
			this.testType = TestTypes.WEB.getTestType();

		} else {
			this.driver = (AppiumDriver) testBed.getDriver();
			this.testType = TestTypes.MOBILE.getTestType();

		}
	}

	private Object createDriver() {
		return TestBedManager.INSTANCE.getCurrentTestBeds().createDriver(testBedName);
	}

	private void addToCurrentTestBeds(TestBed testBed) {
		TestBedManager.INSTANCE.getCurrentTestBeds().put(testBedName, testBed);
	}

	private TestBed createTestBed() {
		TestBed testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
		testBed.setDriver(createDriver());
		return testBed;
	}

	private void addDrivertoCurrentDrivers(TestBed testBed) {
		// Add new driver to list of Drivers maintain by TestBedManager
		CurrentDrivers currentDrivers = TestBedManager.INSTANCE.getCurrentDrivers();
		currentDrivers.addDriver(testBedName, testBed.getDriver());
	}

	/**
	 * 
	 * Returns driver.
	 * 
	 * @return instance of web driver
	 * 
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * Loads web page URL.
	 * 
	 * @param pageUrl
	 *            web page URL
	 */
	public void loadPage(String pageUrl) {
		this.driver.get(pageUrl);
		maximizeWindow();

	}

	private void maximizeWindow() {
		BrowserInfoUtil biUtil = new BrowserInfoUtil(testBedName);
		if (biUtil.isFF() || biUtil.isIE() || biUtil.isChrome()) {
			if (TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getPlatform().getName()
					.equalsIgnoreCase("Mac") && new BrowserInfoUtil(testBedName).isChrome()) {
				this.resize(1400, 700);
			}

			else if (new BrowserInfoUtil(testBedName).isFF() || new BrowserInfoUtil(testBedName).isIE()
					|| new BrowserInfoUtil(testBedName).isChrome() || new BrowserInfoUtil(testBedName).isSafari()) {

				this.driver.manage().window().maximize();
			}
		}
	}

	/**
	 * Returns current URL.
	 * 
	 * @return the current url
	 * @throws InterruptedException
	 */
	public String getCurrentUrl() throws InterruptedException {

		Thread.sleep(2500);
		/*
		 * JavascriptExecutor javascript = (JavascriptExecutor) driver; String
		 * uRLUsingJs=(String)javascript.executeScript("return document.URL");
		 * return uRLUsingJs;
		 */
		return driver.getCurrentUrl();

		/*
		 * Attachment need change in webpage java class under getCurrentUrl
		 * method we are getting url with java script not selenium public String
		 * getCurrentUrl() throws InterruptedException{ Thread.sleep(500);
		 * JavascriptExecutor javascript = (JavascriptExecutor) driver; String
		 * uRLUsingJs=(String)javascript.executeScript("return document.URL");
		 * System.out.println("CURRENT URL IS "+uRLUsingJs); return uRLUsingJs;
		 * 
		 * } return driver.getCurrentUrl();
		 */
	}

	/**
	 * Returns the web page title
	 * 
	 * @return web page title
	 */
	public String getPageTitle() {
		return driver.getTitle();
	}

	/**
	 * Creates the element.
	 * 
	 * @param webElement
	 *            the web element
	 * @param testType
	 *            the test type
	 * @return the i taf element
	 */
	private ITafElement createElement(WebElement webElement, String testType) {

		ITafElementFactory factory = TafElementFactoryManager.getFactory(testType);
		return factory.createElement(webElement, this.currentContext);

	}

	// public ITafElement findObjectByIdQTP(String id) throws PageException {
	// WebElement webElement = null;
	// try {
	// webElement = driver.findElement(By.id(id));
	// } catch (Exception e) {
	// log.error("Failed to find object using given id" + " = " + id + ",
	// message : " + e.toString());
	// throw new PageException("Failed to find object using given id , message :
	// " + e.toString());
	// }
	// // return PageObjectFactory.createPageObject(webElement,testType);
	// return new QTPElement(webElement, null);
	// }

	/**
	 * Find object by id.
	 * 
	 * @param id
	 *            the id
	 * @return the web element
	 * @throws PageException
	 *             the page exception
	 */
	@HandleException(expected = { PageException.class })
	public ITafElement findObjectById(String id) throws PageException {
		WebElement webElement = null;
		try {
			webElement = driver.findElement(By.id(id));
		} catch (Exception e) {
			log.error("Failed to find object using given id" + " = " + id + ", message : " + e.toString());
			throw new PageException("Failed to find object using given id , message : " + e.toString());
		}
		// return PageObjectFactory.createPageObject(webElement,testType);
		return createElement(webElement, testType);
	}

	/**
	 * Find object by name.
	 * 
	 * @param name
	 *            the name
	 * @return the web element
	 * @throws PageException
	 *             the page exception
	 */
	@HandleException(expected = { PageException.class })
	public ITafElement findObjectByName(String name) throws PageException {

		WebElement webElement = null;
		try {
			webElement = driver.findElement(By.name(name));
		} catch (Exception e) {
			log.error("Failed to find object using given name" + " = " + name + ", message : " + e.toString());
			throw new PageException("Failed to find object using given name, message : " + e.toString());
		}

		return createElement(webElement, testType);
	}

	/**
	 * Find object byx path.
	 * 
	 * @param xpath
	 *            the xpath
	 * @return the web element
	 * @throws PageException
	 *             the page exception
	 */
	@HandleException(expected = { PageException.class })
	public ITafElement findObjectByxPath(String xpath) throws PageException {
		WebElement webElement = null;
		try {
			webElement = driver.findElement(By.xpath(xpath));
		} catch (Exception e) {
			log.error("Failed to find object using given xpath" + " = " + " , message : " + e.getMessage());
			throw new PageException("Failed to find object using given xpath, message : " + e.toString());
		}

		return createElement(webElement, testType);
	}

	/**
	 * Find object by css.
	 * 
	 * @param css
	 *            the css
	 * @return the web element
	 * @throws PageException
	 *             the page exception
	 */
	@HandleException(expected = { PageException.class })
	public ITafElement findObjectByCss(String css) throws PageException {
		WebElement webElement = null;
		try {
			webElement = driver.findElement(By.cssSelector(css));
		} catch (Exception e) {
			log.error("Failed to find object using given css" + " = " + css + ", message : " + e.toString());
			throw new PageException("Failed to find object using given css, message : " + e.toString());
		}
		return createElement(webElement, testType);
	}

	/**
	 * Find object by link.
	 * 
	 * @param link
	 *            the link
	 * @return the web element
	 * @throws PageException
	 *             the page exception
	 */
	@HandleException(expected = { PageException.class })
	public ITafElement findObjectByLink(String link) throws PageException {
		WebElement webElement = null;
		try {
			webElement = driver.findElement(By.linkText(link));
		} catch (Exception e) {
			log.error("Failed to find object using given link" + " = " + link + ", message : " + e.toString());
			throw new PageException("Failed to find object using given link , message : " + e.toString());
		}

		return createElement(webElement, testType);
	}

	/**
	 * Find object by partial link.
	 * 
	 * @param link
	 *            the link
	 * @return the web element
	 * @throws PageException
	 *             the page exception
	 */
	@HandleException(expected = { PageException.class })
	public ITafElement findObjectByPartialLink(String link) throws PageException {
		WebElement webElement = null;
		try {
			webElement = driver.findElement(By.partialLinkText(link));
		} catch (Exception e) {
			log.error(
					"Failed to find object using given partial link" + " = " + link + ", message : " + e.getMessage());
			throw new PageException("Failed to find object using given partial link, message : " + e.toString());
		}

		return createElement(webElement, testType);
	}

	/**
	 * Find object by class.
	 * 
	 * @param className
	 *            the class name
	 * @return the web element
	 * @throws PageException
	 *             the page exception
	 */
	@HandleException(expected = { PageException.class })
	public ITafElement findObjectByClass(String className) throws PageException {
		WebElement webElement = null;
		try {
			webElement = driver.findElement(By.className(className));
		} catch (Exception e) {
			log.error(
					"Failed to find object using given class name" + " = " + className + ", message : " + e.toString());
			throw new PageException("Failed to find object using given class name, message : " + e.toString());
		}

		return createElement(webElement, testType);
	}

	/**
	 * Find object by multiple classes.
	 * 
	 * @param className
	 *            the class name
	 * @return the web element
	 * @throws PageException
	 *             the page exception
	 */
	@HandleException(expected = { PageException.class })
	public ITafElement findObjectByCompoundClass(String classNames) throws PageException {
		WebElement webElement = null;
		try {
			webElement = driver.findElement(By.className(classNames));
		} catch (Exception e) {
			log.error("Failed to find object using given class name" + " = " + classNames + ", message : "
					+ e.toString());
			throw new PageException("Failed to find object using given class name, message : " + e.toString());
		}

		return createElement(webElement, testType);
	}

	/**
	 * Find object by tag.
	 * 
	 * @param tag
	 *            the tag
	 * @return the list
	 * @throws PageException
	 *             the page exception
	 */
	@HandleException(expected = { PageException.class })
	public ITafElement findObjectByTag(String tag) throws PageException {

		WebElement webElement = null;
		try {
			webElement = driver.findElement(By.tagName(tag));
		} catch (Exception e) {
			log.error("Failed to find objects using given Tag" + " = " + tag + ", message : " + e.toString());
			throw new PageException("Failed to find object using given Tag , message : " + e.toString());
		}
		return createElement(webElement, testType);

	}

	/**
	 * Find objects by tag.
	 * 
	 * @param tag
	 *            the tag
	 * @return the list
	 * @throws PageException
	 *             the page exception
	 */
	@HandleException(expected = { PageException.class })
	public List<ITafElement> findObjectsByTag(String tag) throws PageException {
		List<ITafElement> l1 = new ArrayList<>();
		List<WebElement> lst = null;
		try {
			lst = driver.findElements(By.tagName(tag));
		} catch (Exception e) {
			log.error("Failed to find objects using given Tag" + " = " + tag + ", message : " + e.toString());
			throw new PageException("Failed to find object using given Tag , message : " + e.toString());
		}

		for (WebElement we : lst) {
			l1.add(createElement(we, testType));
		}
		return l1;

	}

	/**
	 * Find objects by xpath.
	 * 
	 * @param xpath
	 *            the xpath
	 * @return the list
	 * @throws PageException
	 *             the page exception
	 */
	@HandleException(expected = { PageException.class })
	public List<ITafElement> findObjectsByXpath(String xpath) throws PageException {
		List<ITafElement> l1 = new ArrayList<>();
		List<WebElement> lst = null;
		try {
			lst = driver.findElements(By.xpath(xpath));
		} catch (Exception e) {
			log.error("Failed to find objects using given xpath" + " = " + xpath + ", message : " + e.toString());
			throw new PageException("Failed to find object using given xpath , message : " + e.toString());
		}
		for (WebElement we : lst) {
			l1.add(createElement(we, testType));
		}
		return l1;
	}

	/**
	 * Find objects using link.
	 * 
	 * @param link
	 *            the link
	 * @return the list
	 * @throws PageException
	 *             the page exception
	 */
	@HandleException(expected = { PageException.class })
	public List<ITafElement> findObjectsByLink(String link) throws PageException {
		List<ITafElement> l1 = new ArrayList<>();
		List<WebElement> lst = null;
		try {
			lst = driver.findElements(By.linkText(link));
		} catch (Exception e) {
			log.error("Failed to find objects using given link" + " = " + link + ", message : " + e.toString());
			throw new PageException("Failed to find object using given link , message : " + e.toString());
		}
		for (WebElement we : lst) {
			l1.add(createElement(we, testType));
		}
		return l1;
	}

	/**
	 * Find objects using partial link.
	 * 
	 * @param link
	 *            the link
	 * @return the list
	 * @throws PageException
	 *             the page exception
	 */
	@HandleException(expected = { PageException.class })
	public List<ITafElement> findObjectsByPartialLink(String link) throws PageException {
		List<ITafElement> l1 = new ArrayList<>();
		List<WebElement> lst = null;
		try {
			lst = driver.findElements(By.partialLinkText(link));
		} catch (Exception e) {
			log.error("Failed to find objects using given link" + " = " + link + ", message : " + e.toString());
			throw new PageException("Failed to find object using given link , message : " + e.toString());
		}
		for (WebElement we : lst) {
			l1.add(createElement(we, testType));
		}
		return l1;
	}

	/**
	 * Find objects using class.
	 * 
	 * @param className
	 *            the class name
	 * @return the list
	 * @throws PageException
	 *             the page exception
	 */
	@HandleException(expected = { PageException.class })
	public List<ITafElement> findObjectsByClass(String className) throws PageException {
		List<ITafElement> l1 = new ArrayList<>();
		List<WebElement> lst = null;
		try {
			lst = driver.findElements(By.className(className));
		} catch (Exception e) {
			log.error("Failed to find objects using given class" + " = " + className + ", message : " + e.toString());
			throw new PageException("Failed to find object using given class , message : " + e.toString());
		}
		for (WebElement we : lst) {
			l1.add(createElement(we, testType));
		}
		return l1;
	}

	/**
	 * Find objects by css.
	 * 
	 * @param css
	 *            the css
	 * @return the list
	 * @throws PageException
	 *             the page exception
	 */
	@HandleException(expected = { PageException.class })
	public List<ITafElement> findObjectsByCss(String css) throws PageException {
		List<ITafElement> l1 = new ArrayList<>();
		List<WebElement> lst = null;
		try {
			lst = driver.findElements(By.cssSelector(css));
		} catch (Exception e) {
			log.error("Failed to find objects using given class" + " = " + css + ", message : " + e.toString());
			throw new PageException("Failed to find object using given class , message : " + e.toString());
		}
		for (WebElement we : lst) {
			l1.add(createElement(we, testType));
		}
		return l1;
	}

	/**
	 * Wait on element.
	 * 
	 * @param element
	 *            the element
	 * @param wait
	 *            the wait
	 * @param frame
	 *            the frame
	 * @return the web element
	 * @throws PageException
	 *             the page exception
	 */
	@HandleException(expected = { PageException.class })
	private WebElement waitOnElement(By element, int wait, String frame) throws PageException {
		WebElement identifier = null;
		for (int i = 0; i < wait; i++) {
			try {
				if (frame == null)
					identifier = driver.findElement(element);
				else {
					driver.switchTo().defaultContent();
					identifier = driver.switchTo().frame(frame).findElement(element);
				}
				// if element is found return
				if (identifier != null)
					break;
				// Thread.sleep(WAIT_TO_CHECK);
			} catch (Exception e) {

				log.error("Failed to find object, message : " + e.toString());
				throw new PageException("Failed to find object, message : " + e.toString());
			}
		}
		return identifier;
	}

	/**
	 * Waits on web element.
	 * 
	 * @param element
	 *            the element
	 * @param wait
	 *            the wait
	 * @return the web element
	 * @throws PageException
	 *             the page exception
	 */
	public WebElement waitOnElement(By element, int wait) throws PageException {
		return waitOnElement(element, wait, null);
	}

	/**
	 * Wait on element to get loaded based on the Expected Condition.
	 * 
	 * @param element
	 *            the element
	 * @param wait
	 *            the wait
	 * @param frame
	 *            the frame
	 * @param condition
	 *            the condition
	 * @return the web element
	 * @throws PageException
	 *             the page exception
	 */
	@SuppressWarnings("unchecked")
	@HandleException(expected = { PageException.class })
	private WebElement waitOnElement(By element, int wait, String frame, WaitCondition condition) throws PageException {
		WebElement identifier = null;
		try {
			Method method = ExpectedConditions.class.getMethod(condition.getCondition(), By.class);
			WebDriverWait w = new WebDriverWait(((WebDriver) driver), wait);
			if (frame != null)
				w.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
			identifier = w.until((ExpectedCondition<WebElement>) method.invoke(ExpectedConditions.class, element));
		} catch (Exception e) {
			identifier = null;
			log.error("Failed to find object, message : " + e.toString());
			throw new PageException("Failed to find object, message : " + e.toString());
		}
		return identifier;
	}

	/**
	 * Wait on element.
	 * 
	 * @param element
	 *            the element
	 * @param wait
	 *            the wait
	 * @param condition
	 *            the condition
	 * @return the list
	 * @throws PageException
	 *             the page exception
	 */

	@SuppressWarnings("unchecked")
	@HandleException(expected = { PageException.class })
	private List<WebElement> waitOnElements(By element, int wait, WaitCondition condition) throws PageException {
		List<WebElement> identifiers = null;
		try {

			Method method = ExpectedConditions.class.getMethod(condition.getCondition(), By.class);
			WebDriverWait w = new WebDriverWait((WebDriver) driver, wait);
			identifiers = w
					.until((ExpectedCondition<List<WebElement>>) method.invoke(ExpectedConditions.class, element));
		} catch (Exception e) {
			identifiers = null;
			log.error("Failed to find object, message : " + e.toString());
			throw new PageException("Failed to find object, message : " + e.toString());
		}
		return identifiers;
	}

	/**
	 * Click on "Continue to this Website(not recommended)" link on Certificate
	 * Error Page for IE browser.
	 */
	public void certificateErrorClick() {
		if (new BrowserInfoUtil(testBedName).isIE()) {
			driver.navigate().to("javascript:document.getElementById('overridelink').click()");
		}
	}

	/**
	 * sleep time.
	 * 
	 * @param timeout
	 *            the timeout
	 */
	public void sleep(long timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Resize window to given width and height.
	 * 
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	public void resize(int width, int height) {
		driver.manage().window().setSize(new org.openqa.selenium.Dimension(width, height));
		return;
	}

	/**
	 * Resize window to given height width by adding wait.
	 * 
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param wait
	 *            the wait
	 */
	public void resize(int width, int height, int wait) {
		driver.manage().window().setSize(new org.openqa.selenium.Dimension(width, height));
		sleep(wait);
		return;
	}

	/**
	 * Zooms in the web page.
	 */
	public void zoomIn(int counter) {
		TestBed testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
		WebElement html = driver.findElement(By.tagName("html"));
		if (ConfigUtil.isWindows(testBed)) {
			for (int i = 0; i < counter; i++)
				html.sendKeys(Keys.chord(Keys.CONTROL, Keys.ADD));
		} else if (ConfigUtil.isMac(testBed)) {
			for (int i = 0; i < counter; i++)
				html.sendKeys(Keys.chord(Keys.COMMAND, Keys.ADD));
		} else {
			log.error("Zoom in not supported for this Operating System");
		}
	}

	/**
	 * Zooms out the web page.
	 */
	public void zoomOut(int counter) {
		TestBed testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
		WebElement html = driver.findElement(By.tagName("html"));

		if (ConfigUtil.isWindows(testBed)) {
			for (int i = 0; i < counter; i++)
				html.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
		} else if (ConfigUtil.isMac(testBed)) {
			for (int i = 0; i < counter; i++)
				html.sendKeys(Keys.chord(Keys.COMMAND, Keys.SUBTRACT));
		} else {
			log.error("Zoom out not supported for this Operating System");

		}
	}

	public void zoomTo100() {
		TestBed testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
		WebElement html = driver.findElement(By.tagName("html"));
		if (ConfigUtil.isWindows(testBed)) {

			html.sendKeys(Keys.chord(Keys.CONTROL, "0"));
		} else if (ConfigUtil.isMac(testBed)) {

			html.sendKeys(Keys.chord(Keys.COMMAND, "0"));
		} else {
			log.error("Zoom is not supported for this Operating System");
		}
	}

	/**
	 * Scroll up the Page.
	 */
	public void scrollUp(int count) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		for (int i = 0; i < count; i++)
			jse.executeScript("window.scrollBy(0,-250)", "");
	}

	/**
	 * Scrolls down the Page.
	 */
	public void scrollDown(int count) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		for (int i = 0; i < count; i++)
			jse.executeScript("window.scrollBy(0,250)", "");
	}

	/**
	 * Scrolls right to the page.
	 */
	public void scrollRight() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(250,0)", "");

	}

	/**
	 * Scrolls left to the page.
	 */
	public void scrollLeft() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(-250,0)", "");
	}

	/**
	 * Scroll to element.
	 * 
	 * @param element
	 *            the element
	 */
	public void scrollToElement(ITafElement element) {
		org.openqa.selenium.WebElement elementToScroll = element.getWebElement();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", elementToScroll);
	}

	/**
	 * Scroll bottom.
	 */
	public void scrollBottom() {
		/*
		 * Actions actions = new Actions(driver);
		 * actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
		 */

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript(
				"window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
	}

	/**
	 * Scroll top.
	 */
	public void scrollTop() {
		Actions actions = new Actions(driver);
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).perform();
	}

	/**
	 * Full scroll in slow motion.
	 * 
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void fullScrollInSlowMotion() throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		for (int second = 0;; second++) {
			if (second >= 60) {
				break;
			}
			jse.executeScript("window.scrollBy(0,800)", ""); // y value '800'
																// can be
																// altered
			Thread.sleep(1000);
		}

	}

	/**
	 * Gets the transition url.
	 * 
	 * @param previousUrl
	 *            the previous url
	 * @param driverWait
	 *            the driver wait
	 * @param implicitWait
	 *            the implicit wait
	 * @return the transition url
	 */
	public String getTransitionUrl(final String previousUrl, int driverWait, int implicitWait) {
		WebDriverWait wait = new WebDriverWait(driver, driverWait);
		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
		ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return (d.getCurrentUrl() != previousUrl);
			}
		};
		wait.until(e);
		return driver.getCurrentUrl();
	}

	/**
	 * Returns true if current URL has parameters appended.
	 * 
	 * @return true if current URL has parameters appended.
	 */
	public boolean StringParameterAppend() {
		String url = driver.getCurrentUrl();
		boolean flag = url.indexOf("?") != -1;
		if (flag)
			return false;
		else
			return true;
	}

	/**
	 * Navigates to the given URL.
	 * 
	 * @param url
	 *            URL to navigate.
	 */
	public void navigateToUrl(String url) {
		driver.navigate().to(url);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Switch to new tab.
	 */
	/*
	 * public void openNewTab(){ Set<String> windows =
	 * driver.getWindowHandles(); log.info("After click no. of windows:"
	 * +windows.size()); log.info("Before Switch "+driver.getWindowHandle());
	 * for(String window: windows){ driver.switchTo().window(window); log.info(
	 * "After Switch "+driver.getWindowHandle()); log.info("Page URL:"
	 * +driver.getCurrentUrl()); log.info("Switched Page title is:"
	 * +driver.getTitle()); } }
	 */

	/**
	 * Switch to new empty tab.
	 */
	public void openNewTab() {
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.COMMAND + "t");
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(tabs.size() - 1));
		maximizeWindow();
	}

	/**
	 * Switch to new tab with URL.
	 */
	public void openNewTab(String url) {
		openNewTab();
		driver.navigate().to(url);
	}

	/**
	 * Switch to new empty tab.
	 */
	public void openURLInNewTab(String urlLink) {
		new Actions(this.driver).keyDown(Keys.COMMAND).sendKeys("t").keyUp(Keys.COMMAND).perform();
		driver.navigate().to(urlLink);
		// String selectLinkOpeninNewTab = Keys.chord(Keys.COMMAND + "t");

		// driver.findElement(By.linkText(urlLink)).sendKeys(selectLinkOpeninNewTab);
	}

	/**
	 * Switch to new window with URL.
	 */
	public void openURLInNewWindow(String urlLink) {
		WebElement link = driver.findElement(By.linkText(urlLink));
		Actions newWindow = new Actions(driver);
		newWindow.keyDown(Keys.SHIFT).click(link).keyUp(Keys.SHIFT).build().perform();
	}

	/**
	 * Navigates back.
	 * 
	 * @return the back to url
	 */
	public void getBackToUrl() {
		driver.navigate().back();
		return;
	}

	/**
	 * Toggle window.
	 */
	public void toggleWindow() {
		String currentHandle = driver.getWindowHandle();
		log.info("currentHandle:: " + currentHandle);
		Set<String> windows = driver.getWindowHandles();
		log.info("no. of windows::" + windows.size());
		windows.remove(currentHandle);
		log.info("no. of windows after remove current handle ::" + windows.size());
		if (windows.size() == 1) {
			log.info("switching to window ::" + (String) ((windows.toArray())[0]));
			driver.switchTo().window((String) ((windows.toArray())[0]));
		}
	}

	/**
	 * Switch the focus to given titled window
	 * 
	 * @param windowTitle
	 */
	public void switchWindow(String windowTitle) {

		boolean isSwitched = false;
		// Get the focus to the parent window
		String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, Keys.TAB);
		driver.findElement(By.cssSelector("body")).sendKeys(selectLinkOpeninNewTab);

		String currentWindow = driver.getWindowHandle();
		Set<String> availableWindows = driver.getWindowHandles();

		if (!availableWindows.isEmpty()) {
			for (String windowId : availableWindows) {
				if (driver.switchTo().window(windowId).getTitle().equals(windowTitle)) {
					isSwitched = true;
					break;
				}

			}
			if (!isSwitched) {
				driver.switchTo().window(currentWindow);
			}
		}

	}

	/**
	 * change the focus on the adjacent window tabs
	 */
	public void toggleTab() {

		String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, Keys.TAB);
		driver.findElement(By.cssSelector("body")).sendKeys(selectLinkOpeninNewTab);

	}

	/**
	 * Close_toggle window.
	 */
	public void closeToggleWindow() {

		String currentHandle = driver.getWindowHandle();
		log.info("currentHand le:: " + currentHandle);
		Set<String> windows = driver.getWindowHandles();
		log.info("no. of windows::" + windows.size());
		driver.switchTo().window(currentHandle).close();
		windows.remove(currentHandle);
		log.info("no. of windows after remove current handle ::" + windows.size());
		if (windows.size() == 1) {
			log.info("switching to window ::" + (String) ((windows.toArray())[0]));
			driver.switchTo().window((String) ((windows.toArray())[0]));
		}
	}

	public void setDriver(WebDriver driver2) {
		this.driver = (RemoteWebDriver) driver2;

	}
}
