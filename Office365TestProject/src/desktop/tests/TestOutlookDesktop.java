package desktop.tests;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;

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

	@Test(enabled=true, priority = 2, groups = { "DesktopOutlook" })
	public void checkmeeting() throws InterruptedException, AWTException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver); Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		outlookDesk.clickByMeetingName(meetingName);
		outlookDesk.clickToJoinSkypeMeeting();
		outlookDesk.runAndVerificationSkypeMeeting(meetingName);
		Thread.sleep(2000);
		outlookDesk.clickOKButton();
		
	}
	
	@Test(enabled=true, priority = 0, groups = { "DesktopOutlook" })
	public void createNewAmdocsMeeting() throws InterruptedException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver); Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();//Thread.sleep(6000);
		
		List<String> myList = new ArrayList<>();
		myList.add("yoelap@amdocs.com");
		myList.add("michael.prudnikov@amdocs.com");
		//String meetingName = outlookDesk.specifyMeetingPropertiesTEST(myList);
		 meetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com; michael.prudnikov@amdocs.com");
		 System.out.println("Meeting name: "+meetingName);

		testLogOutlook.pass("Test Passed");
		
	}
	@Test(enabled=true, priority = 1, groups = { "DesktopOutlook" })
	public void createNewMeeting() throws InterruptedException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver); Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Meeting");
		outlookDesk.clickNewMeeting();
		outlookDesk.specifyLocation("TestLocation");
		meetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com");
		//meetingInTheCalendar = outlookDesk.checkMeetingExistance(meetingName);
		//System.out.println("RESULT: "+meetingInTheCalendar);
		testLogOutlook.pass("Test Passed");
		
	}
	
	@Test(enabled=true, priority = 2, groups = { "DesktopOutlook" })
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
