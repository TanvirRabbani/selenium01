package com.bfs.report;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestReport {
 
  ExtentReports report;
	ExtentTest logger;
	WebDriver driver;

	@Test
	public void verifyBlogTitle() {
		report = new ExtentReports("C:\\Storage\\Report\\LearnAutomation.html",true);
		logger = report.startTest("VerifyBlogTitle");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		logger.log(LogStatus.INFO, "Browser started ");
		driver.get("http://ebfs.bruteforcesolution.net/ebfs/index.php");
		logger.log(LogStatus.INFO, "Application is up and running");
		String title = driver.getTitle();
		Assert.assertTrue(title.contains("Power of Choice"));
		logger.log(LogStatus.PASS, "Title verified");
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			//String screenshot_path = Utility.captureScreenshot(driver, result.getName());
			File scrfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String screenshot_path = "C:\\Storage\\Report\\" + scrfile.getName();
			File tmp = new File(screenshot_path);
			try{
			FileUtils.copyFile(scrfile, tmp);
			}catch(Exception ex){
				System.out.println("Exception::");
			}
			System.out.println("Path:" + screenshot_path);
			String image = logger.addScreenCapture(screenshot_path);
			logger.log(LogStatus.FAIL, "Title verification", image);
		}

		report.endTest(logger);
		report.flush();

		driver.get("C:\\Storage\\Report\\LearnAutomation.html");
	}
}
