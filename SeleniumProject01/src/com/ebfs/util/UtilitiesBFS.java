package com.ebfs.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.ebfs.util.WebBrowser.driver;

public class UtilitiesBFS {

	/**
	 * @usage : To generate unique html report name.
	 * @return: will return current date with format yyyy-MM-dd HH-mm-ss
	 */
	public static String getDateString() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String dateStr = dateFormat.format(date);
		return dateStr;

	}

	/**
	 * @usage : to generate unique email address for user registration
	 * @return: will return a text with size 7
	 */
	public static String getRandString() {
		String retRandString = "";
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 7; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		retRandString = sb.toString();
		System.out.println("Random String for last name and email: " + retRandString);

		return retRandString;
	}

	// Sending console messages to a log file

	public static void logEntry() {
		String fileName = new SimpleDateFormat("yyyyMMddhhmm'.txt'").format(new Date());

		File file = new File(fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			PrintStream ps = new PrintStream(fos);
			System.setOut(ps);

		} catch (Exception e) {
			System.out.println("File is there, but there is other exception in logVerify method");
			e.printStackTrace();
		}

	}
	public static boolean waitForElementToLoad(String objType, String argsElementName, String frindlyName){
		boolean retStauts= waitForElementToLoad(objType, argsElementName, frindlyName, 5);
		return retStauts;
	}
	
	public static boolean waitForElementToLoad(String objType, String argsElementName, String frindlyName, int maxWaitSec) {
		boolean retStauts = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, maxWaitSec); // wait for max  of 5 seconds
			if (objType.equalsIgnoreCase("xpath"))
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(argsElementName)));
			else if (objType.equalsIgnoreCase("className"))
				wait.until(ExpectedConditions.presenceOfElementLocated(By.className(argsElementName)));

			Log.write("Wait For Element To Load", "Element: " + frindlyName + "  loaded after click", true);
			retStauts = true;
		} catch (Exception ex) {
			System.out.println("Exception: UtilitiesPCH.Java-->waitForElementToLoad" + ex.getMessage());
			Log.write("Wait For Element To Load", "Element: " + frindlyName + " did not load after click", false);
		}

		return retStauts;

	}



	public static boolean waitForElementToInvisible(String objType, String argsElementName, String frindlyName) {
		boolean retStauts = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5); // wait for max
																// of 5 seconds
			if (objType.equalsIgnoreCase("xpath"))
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(argsElementName)));
			else if (objType.equalsIgnoreCase("className"))
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(argsElementName)));

			Log.write("Wait For Element To Invisible", "Element: " + frindlyName + " Invisibled after click ", true);
			retStauts = true;
		} catch (Exception ex) {
			System.out.println("Exception: UtilitiesPCH.Java-->waitForElementToLoad" + ex.getMessage());
			Log.write("Wait For Element To Invisible", "Element: " + frindlyName + " does not Invisible after click ", false);
		}

		return retStauts;

	}

	public static String captureScreenshot() throws IOException {
		String snapFile = "";
		try {

			File scrfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			snapFile = "./Snapshot/" + scrfile.getName();
			File tmp = new File(snapFile);
			FileUtils.copyFile(scrfile, tmp);
		} catch (Exception e) {
			System.out.println("Exception Occured while taking snapshot :" + e.getMessage());
		}
		snapFile = "." + snapFile;
		return snapFile;
	}

	// Using for testing different Utilities methods. Will be removed
	public static void main(String[] args) {

	}

}
