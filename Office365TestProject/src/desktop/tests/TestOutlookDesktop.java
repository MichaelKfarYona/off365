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
	
	@Test(enabled=false, priority = 0, groups = { "DesktopOutlook" })
	public void createNewAmdocsMeeting() throws InterruptedException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver); Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();//Thread.sleep(6000);
		String meetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com");
		//meetingInTheCalendar = outlookDesk.checkMeetingExistance(meetingName);
		//System.out.println("RESULT: "+meetingInTheCalendar);
		testLogOutlook.pass("Test Passed");
		
	}
	@Test(enabled=false, priority = 1, groups = { "DesktopOutlook" })
	public void createNewMeeting() throws InterruptedException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver); Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Meeting");
		outlookDesk.clickNewMeeting();
		outlookDesk.specifyLocation("TestLocation");
		String meetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com");
		//meetingInTheCalendar = outlookDesk.checkMeetingExistance(meetingName);
		//System.out.println("RESULT: "+meetingInTheCalendar);
		testLogOutlook.pass("Test Passed");
		
	}
	
	@Test(enabled=true, priority = 0, groups = { "DesktopOutlook" })
	public void createNewMessage() throws InterruptedException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver); Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Mail");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.MAIL);
		outlookDesk.clickCreateNewMessage();
		String title;
		title = outlookDesk.specifyNewMessageInfo("yoelap@amdocs.com");
		System.out.println("MESSAGE_TITLE - "+title);
		testLogOutlook.pass("New message has been created!");
	
	}
	
	
}
