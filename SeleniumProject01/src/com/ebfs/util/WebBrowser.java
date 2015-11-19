package com.ebfs.util;

import java.awt.Toolkit;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebBrowser {

	public static WebDriver driver = null;
	public static String returnStatus = Constants.KEYWORD_PASS; // --- That will
																// be fail
	public static String forceBrowserType = "notSet";

	public static String openBrowser(String argURL) {
		try {
			openBrowser("firefox", argURL);
		} catch (Exception ex) {
			System.out.println(
					"WebBrowser.openBrowser(String argURL) -->Exception while Open Browser : " + ex.getMessage());
			Log.write("Open URL in Firefox. URL:" + argURL, "Exception : " + ex.getMessage(), false);
		}
		return returnStatus;
	}

	public static String openBrowser(String argBrowserType, String argURL) throws IOException {
		if (forceBrowserType.equalsIgnoreCase("notSet")) {
			setBrowserType(argBrowserType);
		} else {
			setBrowserType(forceBrowserType);
		}

		try {
			driver.get(argURL);
			Thread.sleep(10000L);
			closePopUp();
			returnStatus = Constants.KEYWORD_PASS;

		} catch (Exception e) {

			Log.write("Open URL in Firefox. URL:" + argURL, "Exception : " + e.getMessage(), false);
			Log.takeSnapshot("browserOpen");
			returnStatus = Constants.KEYWORD_FAIL;
		}

		return returnStatus;
	}

	public static String closeBroser() throws IOException {
		boolean pfStatus = false;
		try {

			driver.quit();
			returnStatus = Constants.KEYWORD_PASS;
			pfStatus = true;
		} catch (Exception e) {
			Log.write("Exception while Closing Browser", " Error :: " + e.getMessage(), false);
			Log.takeSnapshot("Close Browser");
			returnStatus = Constants.KEYWORD_FAIL;
		}
		Log.write("Close Browser", "Browsers should be closed", pfStatus);

		return returnStatus;
	}

	public static String setBrowserType(String argBrowserType) throws IOException {
		argBrowserType = argBrowserType.toLowerCase();
		switch (argBrowserType) {
		case "firefox": {

			try {
				driver = new FirefoxDriver();
				returnStatus = maximizeWindow();
			} catch (Exception e) {
				returnStatus = Constants.KEYWORD_FAIL;
			}
			break;

		}

		case "chrome": {

			try {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\BrowserDriver\\chromedriver.exe");
				driver = new ChromeDriver();
				returnStatus = maximizeWindow();
			} catch (Exception e) {
				Log.write("Set Browser Type: " + argBrowserType, "Exception : " + e.getMessage(), false);
				returnStatus = Constants.KEYWORD_FAIL;

			}

			break;

		}

		case "ie": {

			try {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\BrowserDriver\\IEDriverServer.exe");

				DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
				caps.setCapability("ignoreZoomSetting", true);
				caps.setCapability("ignoreProtectedModeSettings", true);
				// Delete Browser Cache since IE does not open a clean profile
				// unlike Chrome & FireFox
				try {
					Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				driver = new InternetExplorerDriver(caps);
				driver.manage().deleteAllCookies();

				returnStatus = maximizeWindow();

			} catch (Exception e) {
				System.out.println(e.getMessage());
				Log.write("Set Browser Type: " + argBrowserType, "Exception : " + e.getMessage(), false);
				returnStatus = Constants.KEYWORD_FAIL;
			}
			break;

		}
		case "agent": {
			try {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\BrowserDriver\\chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("user-data-dir=C:/Users/user_name/AppData/Local/Google/Chrome/User Data");
				options.addArguments(
						"--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X; en-us) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53");
				options.addArguments("--start-maximized");
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);

				driver = new ChromeDriver(capabilities);
				returnStatus = maximizeWindow();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				Log.write("Set Browser Type: " + argBrowserType, "Exception : " + e.getMessage(), false);
				returnStatus = Constants.KEYWORD_FAIL;
			}
		}

		}

		return returnStatus;

	}

	public static String maximizeWindow() throws IOException {
		try {
			String JS_GET_MAX_WIDTH = "return (window.screen ? window.screen.availWidth : arguments[0]);";
			String JS_GET_MAX_HEIGHT = "return (window.screen ? window.screen.availHeight : arguments[0]);";

			Toolkit toolkit = Toolkit.getDefaultToolkit();
			int width = ((Long) ((JavascriptExecutor) driver).executeScript(JS_GET_MAX_WIDTH,
					(int) toolkit.getScreenSize().getWidth())).intValue();
			int height = ((Long) ((JavascriptExecutor) driver).executeScript(JS_GET_MAX_HEIGHT,
					(int) toolkit.getScreenSize().getHeight())).intValue();
			org.openqa.selenium.Dimension screenResolution = new org.openqa.selenium.Dimension(width, height);
			driver.manage().window().setSize(screenResolution);
			returnStatus = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			Log.write("Maximize Browser Window: ", "Exception : " + e.getMessage(), false);

			returnStatus = Constants.KEYWORD_FAIL;

		}

		return returnStatus;
	}

	public static String closePopUp() throws IOException {
		try {
			Set<String> windowId = driver.getWindowHandles();
			Iterator<String> iter = windowId.iterator();
			String currentid = driver.getWindowHandle();
			while (iter.hasNext()) {
				String popup = (String) iter.next();
				Thread.sleep(5000L);
				driver.switchTo().window(popup);
				Thread.sleep(3000L);
				driver.close();
				Thread.sleep(3000L);
				driver.switchTo().window(currentid);
				returnStatus = Constants.KEYWORD_PASS;
				Log.write("Close Popup: ", " Pop up closed successfully : ", true);

			}
			returnStatus = Constants.KEYWORD_PASS;

		} catch (Exception e) {

			returnStatus = Constants.KEYWORD_FAIL;
			Log.write("Close Popup: ", "Exception while closing popup:  " + e.getMessage(), false);

		}

		return returnStatus;

	}

}
