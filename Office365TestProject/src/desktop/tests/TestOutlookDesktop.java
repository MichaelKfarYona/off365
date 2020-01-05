package desktop.tests;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import desktop.Config;
import desktop.pages.OutlookDesktop;
import desktop.pages.OutlookDesktop.LeftBottomMenu;

public class TestOutlookDesktop extends Config{
	ExtentHtmlReporter htmlReporter;
	ExtentTest testLogOutlook = null;
	String meetingName = null;
	boolean meetingInTheCalendar;
	
	@Test
	public void createNewAmdocsMeeting() throws InterruptedException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver); Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		Thread.sleep(6000);
		String meetingName = outlookDesk.specifyMeetingProperties();
		meetingInTheCalendar = outlookDesk.checkMeetingExistance(meetingName);
		System.out.println("RESULT: "+meetingInTheCalendar);
		Thread.sleep(1000);
		testLogOutlook.pass("Test Passed");
		
	}

	
	
	
	
	
}
