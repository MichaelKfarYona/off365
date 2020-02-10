package desktop.tests;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import desktop.ConfigOneDrive;
import desktop.pages.OneDriveDesktop;
import desktop.pages.OneDriveDesktop.BottomMenu;

public class TestOneDriveDesktop extends ConfigOneDrive{

	ExtentHtmlReporter htmlReporter;
	ExtentTest testLogOutlook = null;
	String meetingName = null;
	boolean meetingInTheCalendar;
	String _sourcePath = "C:\\DRIVERS\\Screenshot_1.png", _detinationPath = "C:\\Users\\michapru\\OneDrive - AMDOCS\\", _fileName = "Screenshot_1.png";

	@Test(enabled=true, priority = 1, groups = { "DesktopOneDrive" })
	public void addEntityToTheOneDriveFolder() throws InterruptedException { boolean elementExistence;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Open OneDrive");
		OneDriveDesktop oneDriveDesktop = new OneDriveDesktop(driver);
		oneDriveDesktop.copyFileOrFolder("OneDrive - AMDOCS", _sourcePath, _detinationPath, _fileName);
		oneDriveDesktop.swithToTaskBarOrTray("OneDrive - AMDOCS"); 
		elementExistence = oneDriveDesktop.checkingElementExistence(_fileName);
		if(elementExistence==true) {testLogOutlook.pass("Test Pass");}
		else {testLogOutlook.fail("Fail...");}
	}
	
	@Test(enabled=true, priority = 0, groups = { "DesktopOneDrive" })
	public void checkOneDriveDesktop() throws InterruptedException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Open OneDrive");
		boolean myFlag;
		OneDriveDesktop oneDriveDesktop = new OneDriveDesktop(driver);
		testLogOutlook.log(Status.INFO, "Switch to tray app");
		oneDriveDesktop.swithToTaskBarOrTray("OneDrive - AMDOCS");
		testLogOutlook.log(Status.INFO, "Check condition");
		myFlag = oneDriveDesktop.checkOneDriveCondition(); 
		System.out.println("My Flag = "+myFlag);
		if(myFlag) {testLogOutlook.pass("Test passed!");}
		else {testLogOutlook.fail("Failed");}
		Thread.sleep(1000);
	}
	
}