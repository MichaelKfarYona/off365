package desktop.tests;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import desktop.ConfigOneDrive;
import desktop.pages.OneDriveDesktop;

public class TestOneDriveDesktop extends ConfigOneDrive{

	ExtentHtmlReporter htmlReporter;
	ExtentTest testLogOutlook = null;
	String meetingName = null;
	boolean meetingInTheCalendar;
	
	// 1920x1080
	public static void clickOD(int x, int y) throws AWTException{
	    Robot bot = new Robot();
	    bot.mouseMove(x, y);    
	    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	@Test(enabled=true, priority = 0, groups = { "DesktopOutlook" })
	public void checkOneDriveDesktop() throws InterruptedException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OneDriveDesktop oneDriveDesktop = new OneDriveDesktop(driver);

		
		Thread.sleep(3000);
		
	}
	
}