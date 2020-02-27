package desktop.tests;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import desktop.Config;
import desktop.pages.ConnectToOutlook;
import desktop.pages.OutlookDesktop;
import desktop.pages.OutlookDesktop.CancelRecurrence;
import desktop.pages.OutlookDesktop.LeftBottomMenu;
import desktop.pages.OutlookDesktop.MeetingInThePast;
import desktop.pages.OutlookDesktop.OpenRecurrenceType;
import desktop.pages.OutlookDesktop.RecurrencePattern;

public class TestOutlookDesktop extends Config {
	ExtentHtmlReporter htmlReporter;
	ExtentTest testLogOutlook = null;
	String driverPath = "c:\\DRIVERS\\chromedriver.exe";
	String meetingName = null;
	String recuringMeetingName = null;
	String createdRecMeeting = null;
	String newID = null;
	String recurringMeetingInThePast = "8256903#";
	boolean meetingInTheCalendar, delMeeting;
	List<String>meetingNameAndId;
	ArrayList<String> recuringOnePerWeekMeetingName;
	String createdRecMeetingID = null;
	String meetingNameNew;
	String allDayMeetingID;
	 private static File getHomeDir() {
	        FileSystemView fsv = FileSystemView.getFileSystemView();
	        return fsv.getHomeDirectory();}
	 
	 private static BufferedImage grabScreen() { 
	        try { return new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	        } catch (SecurityException e) { } catch (AWTException e) {	        }
	        return null; }
	public void createScreenShot(String path, String filename) {
	 try {
         //ImageIO.write(grabScreen(), "png", new File(getHomeDir(), "TESTscreen.png"));
         ImageIO.write(grabScreen(), "png", new File(path, filename+getRandom()+".png"));
     } catch (IOException e) {
         System.out.println("IO exception"+e + " Check createScreenShot method...");
     }
 }
	/* TC#1 - Schedule a meeting */
	@Test(enabled = true, priority = 0, groups = { "DesktopOutlook" })
	public void createNewAmdocsMeeting() throws InterruptedException, AWTException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		//ArrayList<String> meetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com");
		meetingNameNew = outlookDesk.specifyMeetingPropertiesReturnName("yoelap@amdocs.com");
		System.out.println("Meeting name: " + meetingNameNew);
		outlookDesk.btnSendMeeting_ByRobot();
		outlookDesk.exitAndSendLaterOrNot();
		if (meetingNameNew.length() > 0) {
			testLogOutlook.pass("Test Passed");
		} else { createScreenShot("C:\\1","TC1_");
			testLogOutlook.fail("Test failed...");
		}
	}

	/* TC#2 - Schedule an invalid meeting */
	@Test(enabled = true, priority = 7, groups = { "DesktopOutlook" })
	public void createInvalidNewAmdocsMeeting() throws InterruptedException, AWTException {
		boolean res;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		List<String> myList = new ArrayList<>();
		myList.add("yoelap@amdocs.com");
		myList.add("michael.prudnikov@amdocs.com");
	//	ArrayList<String> meetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com; michael.prudnikov@amdocs.com");
		String meetingName = outlookDesk.specifyMeetingPropertiesWithoutGettingID("yoelap@amdocs.com; michael.prudnikov@amdocs.com");
		// outlookDesk.specifyNewDay(-1);
		outlookDesk.specifyWrongDay(-1);
		res = outlookDesk.sendOrNotMeeting(MeetingInThePast.SEND_ANYWAY);
System.out.println("Result : " + res);
		System.out.println("Meeting name: " + meetingName);
		if (res) {
			testLogOutlook.pass("Test Passed! ");
		} else {createScreenShot("C:\\1","TC2_");
			testLogOutlook.fail("Test failed...");
		}
	}
	
// Create new Rec meeting and try to connect via Avaya and Polycom
	/* TC#3 - Schedule Recurring meeting */
	@Test(enabled = true, priority = 4, groups = { "DesktopOutlook" })
	public void createReoccurringMeeting() throws InterruptedException, AWTException {
		boolean isExist;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "TC#3 - Schedule Recurring meeting");
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		ArrayList<String> recuringMeetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com; michael.prudnikov@amdocs.com");
		testLogOutlook.log(Status.INFO, "Add Recurrence");
		isExist = outlookDesk.addRecurrence(RecurrencePattern.DAILY, true, 1, 0, 0);
		System.out.println("Meeting name : " + recuringMeetingName.get(0));
		createdRecMeeting = recuringMeetingName.get(0);
		createdRecMeetingID = recuringMeetingName.get(1);
		if (isExist) {
			testLogOutlook.pass("Test Passed");
		} else {createScreenShot("C:\\1","TC3_");
			testLogOutlook.fail("Test failed...");
		}
		Thread.sleep(500);
	}
	
	/* TC#35 Call using new Recurrence meeting ID. */
	@Test(enabled = true, priority = 5, dependsOnMethods="createReoccurringMeeting")
	@Parameters({"_TestEnvAvayaPhoneNumber"})
	public void checkBeforeConnectionNewRecMeetingIDAvaya(String _TestEnvAvayaPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "TC#35 - Call using new Recurrence meeting ID. Test env. Avaya.");
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_TestEnvAvayaPhoneNumber);
		OutlookDesktop od = new OutlookDesktop(driver);
		setRes = od.callByNumber(_TestEnvAvayaPhoneNumber, createdRecMeetingID);
		// setRes = od.callByNumber(_TestEnvAvayaPhoneNumber, "8259662#");
		if(setRes) {testLogOutlook.pass("Test Passed! | Connect is available through the new ID");}
		else {createScreenShot("C:\\1","TC35_"); testLogOutlook.fail("Test failed... : ");}
		
	}
	/* TC#36 Call using new Recurrence meeting ID.  */
	@Test(enabled = true, priority = 5, dependsOnMethods="createReoccurringMeeting")
	@Parameters({"_TestEnvPolycomPhoneNumber"})
	public void checkBeforeConnectionNewRecMeetingIDPolycom(String _TestEnvPolycomPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_TestEnvPolycomPhoneNumber);
		OutlookDesktop od = new OutlookDesktop(driver);
		//setRes = od.callByNumber(_ProductionAvayaPhoneNumber_1, newID);
		setRes = od.callByNumber(_TestEnvPolycomPhoneNumber, createdRecMeetingID);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Connect is available through the new ID");}
		else {createScreenShot("C:\\1","TC36_"); testLogOutlook.fail("Test failed... : ");}
		
	}
	
	
	/* TC#37 Call using new Recurrence meeting ID.  (Prod. Avaya)*/
	@Test(enabled = true, priority = 5, dependsOnMethods="createReoccurringMeeting")
	@Parameters({"_ProductionAvayaPhoneNumber_1"})
	public void checkBeforeConnectionNewRecMeetingIDAvayaProduction(String _ProductionAvayaPhoneNumber_1) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_ProductionAvayaPhoneNumber_1);
		OutlookDesktop od = new OutlookDesktop(driver);
		//setRes = od.callByNumber(_ProductionAvayaPhoneNumber_1, newID);
		setRes = od.callByNumber(_ProductionAvayaPhoneNumber_1, createdRecMeetingID);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Connect is available through the new ID");}
		else {createScreenShot("C:\\1","TC37_"); testLogOutlook.fail("Test failed... : ");}
		
	}
	
	/* TC#38 Call using new Recurrence meeting ID.  (Prod. Polycom) */
	@Test(enabled = true, priority = 5, dependsOnMethods="createReoccurringMeeting")
	@Parameters({"_ProductionPolycomPhoneNumber"})
	public void checkBeforeConnectionNewRecMeetingIDPolycomProduction(String _ProductionPolycomPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_ProductionPolycomPhoneNumber);
		OutlookDesktop od = new OutlookDesktop(driver);
		//setRes = od.callByNumber(_ProductionAvayaPhoneNumber_1, newID);
		setRes = od.callByNumber(_ProductionPolycomPhoneNumber, createdRecMeetingID);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Connect is available through the new ID");}
		else {createScreenShot("C:\\1","TC38_"); testLogOutlook.fail("Test failed... : ");}
		
	}
	
	/* TC#39 Call using new Recurrence meeting ID. (Prod. Polycom 2)*/
	@Test(enabled = true, priority = 5, dependsOnMethods="createReoccurringMeeting")
	@Parameters({"_ProductionAvayaPhoneNumber_2"})
	public void checkBeforeConnectionNewRecMeetingIDAvayaProduction_2(String _ProductionAvayaPhoneNumber_2) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_ProductionAvayaPhoneNumber_2);
		OutlookDesktop od = new OutlookDesktop(driver);
		//setRes = od.callByNumber(_ProductionAvayaPhoneNumber_1, newID);
		setRes = od.callByNumber(_ProductionAvayaPhoneNumber_2, createdRecMeetingID);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Connect is available through the new ID");}
		else {createScreenShot("C:\\1","TC39_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	
//***************************************************************************************
	/* TC#23 - Schedule Reoccurring meeting 2 days ago */
	@Test(enabled = true, priority = 4, groups = { "DesktopOutlook" })
	public void createReoccurringMeeting2DaysAgo() throws InterruptedException, AWTException {
		boolean isExist;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		ArrayList<String> recuringMeetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com; michael.prudnikov@amdocs.com");
		testLogOutlook.log(Status.INFO, "Add Recurrence");
		isExist = outlookDesk.addRecurrence(RecurrencePattern.DAILY, true, -1, 0, 2);
		System.out.println("Meeting name : " + recuringMeetingName.get(0));
		createdRecMeeting = recuringMeetingName.get(0);
		if (isExist) {
			testLogOutlook.pass("Test Passed");
		} else {createScreenShot("C:\\1","TC23_");
			testLogOutlook.fail("Test failed...");
		}
		Thread.sleep(1000);
	}
	

	/* TC#4 - Schedule Reoccurring meeting invalid */
	@Test(enabled = true, priority = 7, groups = { "DesktopOutlook" })
	public void createInvalidReoccurringMeeting() throws InterruptedException, AWTException {
		boolean isExist;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		ArrayList<String> recuringMeetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com; michael.prudnikov@amdocs.com");
		testLogOutlook.log(Status.INFO, "Add Recurrence");
		isExist = outlookDesk.addRecurrence(RecurrencePattern.DAILY, false, 1, 0, -2);

		System.out.println("Meeting name : " + recuringMeetingName.get(0));
		if (isExist) {
			testLogOutlook.pass("Test Passed");
		} else {createScreenShot("C:\\1","TC4_");
			testLogOutlook.fail("Test failed...");
		}
	}

	/* TC#5 - Cancel Reoccurring meeting */
	@Test(enabled = true, priority = 5)
	public void reoccurringCancellationOne() throws InterruptedException, AWTException {
		boolean res;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Meeting cancellation");
		
		//res = outlookDesk.cancelReoccurringMeeting(CancelRecurrence.CANCEL_OCCURRENCE, createdRecMeeting);
		res = outlookDesk.fastCancelReoccurringMeeting(CancelRecurrence.CANCEL_OCCURRENCE, "Test_008");
		
		//res = outlookDesk.cancelReoccurringMeeting(CancelRecurrence.CANCEL_SERIES, recuringMeetingName);
		if (res) {
			testLogOutlook.pass(recuringMeetingName + " cancelled...");
		} else {createScreenShot("C:\\1","TC5_");
			testLogOutlook.fail("Failed");
		}

	}

	/* TC#19 - Cancel Reoccurring meeting */
	@Test(enabled = true, priority = 6)
	public void reoccurringCancellationAll() throws InterruptedException, AWTException {
		boolean res;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Meeting cancellation");
		
		//res = outlookDesk.cancelReoccurringMeeting(CancelRecurrence.CANCEL_SERIES, createdRecMeeting);
		res = outlookDesk.fastCancelReoccurringMeeting(CancelRecurrence.CANCEL_SERIES, "Test_008");
		if (res) {
			testLogOutlook.pass(recuringMeetingName + " cancelled...");
		} else {createScreenShot("C:\\1","TC19_");
			testLogOutlook.fail("Failed");
		}

	}
	
	/* TC#6 - Update meeting date\time */
	@Test(enabled = true, priority = 2, groups = { "DesktopOutlook" })
	public void updateMeetingDate() throws InterruptedException {
		boolean change;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		outlookDesk.reminderDismissAll(); // Na tot sluchay esli poyavlyaetsa popup
		testLogOutlook.log(Status.INFO, "Open the meeting");
		// outlookDesk.updateMeetingDate(meetingName, 1);
		change = outlookDesk.updateMeetingDate(meetingNameNew, 1);
		//change = outlookDesk.updateMeetingDate("AUTOTEST_082187", 1);
		//change = outlookDesk.updateMeetingDate(meetingName, 1);
		if (change) {
			testLogOutlook.pass("Test Passed");
		} else {createScreenShot("C:\\1","TC6_");
			testLogOutlook.fail("Test failed...");
		}
	}

	/* TC#7 - Update all events from recurring meeting date\time */
	@Test
	public void updateAllReoccurringMeeting() throws InterruptedException{
		boolean passFail;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		passFail = outlookDesk.updateAllRMeetings("ReoccurringTest", RecurrencePattern.DAILY, 1, 5, false, 0);
		if(passFail) {testLogOutlook.pass("Test Passed");}
		else {createScreenShot("C:\\1","TC7_"); testLogOutlook.fail("Test fail...");}
	}
	
	/* TC#8 - Update 1 event from recurring meeting date\time */
	@Test(enabled = true, priority = 5, groups = { "DesktopOutlook" })
	public void updateReoccurringMeeting() throws InterruptedException, AWTException {
		boolean passFail;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Open the meeting. Change the time meeting.");
		passFail = outlookDesk.updateReoccurringMeeting("TestRecurUpdateOne", OpenRecurrenceType.OPEN_OCCURENCE, 1, 0, 0);
		if (passFail) {
			testLogOutlook.pass("Test Passed");
		} else {createScreenShot("C:\\1","TC8_");
			testLogOutlook.fail("Test fail...");
		}
	}

	/* TC#9 - Update meeting participants */
	@Test(enabled = true, priority = 1, groups = { "DesktopOutlook" })
	public void updateMeetingParticipants() throws InterruptedException, AWTException {
		boolean res;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		outlookDesk.reminderDismissAll(); // Na tot sluchay esli poyavlyaetsa popup
		testLogOutlook.log(Status.INFO, "Open the meeting");
		// update opened meeting
		res = outlookDesk.updateMeetingParticipants(meetingNameNew, "michael prudnikov", 1);
		//res = outlookDesk.updateMeetingParticipants(meetingName, "michael prudnikov", 1);
		
		if(res) {testLogOutlook.pass("Test Passed");}
		else{createScreenShot("C:\\1","TC9_"); testLogOutlook.fail("Test failed...");}
		Thread.sleep(1000);
	}

	/* TC#10 - Forward a meeting */
	@Test(enabled = true, priority = 1, groups = { "DesktopOutlook" })
	public void forwardMeeting() throws InterruptedException {
		boolean forward;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		outlookDesk.reminderDismissAll(); // Na tot sluchay esli poyavlyaetsa popup
		testLogOutlook.log(Status.INFO, "Open the meeting");
		forward = outlookDesk.clickForward(meetingNameNew , "nocsupportsvc@amdocs.com");
		//forward = outlookDesk.clickForward(meetingName, "nocsupportsvc@amdocs.com");
		if (forward) {
			testLogOutlook.pass("Test Passed");
		} else {createScreenShot("C:\\1","TC10_");
			testLogOutlook.fail("Test failed...");
		}
	}
	
	/* TC#11 - Prepose another time */
	@Test(enabled = true, priority = 0, groups = { "DesktopOutlook" })
	//@Parameters({"_MEETING_NAME_DIFFERENT_USER"})
	public void createNewAmdocsMeetingAndPreposeAnotherTime() throws InterruptedException, AWTException {
		
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(1000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		//ArrayList<String> meetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com");
		meetingNameNew = outlookDesk.specifyMeetingPropertiesReturnName("yoelap@amdocs.com");
		System.out.println("Meeting name: " + meetingNameNew);
		outlookDesk.btnSendMeeting_ByRobot();
		outlookDesk.exitAndSendLaterOrNot();
		
		//Open as yoel
		//outlookDesk.openOutlookAsDifferentUser("yoelap@amdocs.com", "Random@224466");
		
		//ConnectToOutlook conOutlook = new ConnectToOutlook();
		//conOutlook.openOutlookAsDifferentUserRUNAS("yoelap@amdocs.com", "Random@224466", meetingNameNew);
		
		outlookDesk.openOutlookAsDifferentUserRUNAS("yoelap@amdocs.com", "Random@224466", meetingNameNew);
		//outlookDesk.openOutlookAsDifferentUser();
		
		
		if (meetingNameNew.length() > 0) {
			testLogOutlook.pass("Test Passed");
		} else {createScreenShot("C:\\1","TC11_");
			testLogOutlook.fail("Test failed...");
		}
	}
	

	/* TC#12 - Dial in the meeting id and connect Skype */
	@Test(enabled = true, priority = 2)
	public void checkSkypeMeeting() throws InterruptedException, AWTException {
		boolean res;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		outlookDesk.reminderDismissAll(); // Na tot sluchay esli poyavlyaetsa popup
		testLogOutlook.log(Status.INFO, "Open the meeting");
		
		outlookDesk.clickByMeetingName(meetingNameNew);
		//outlookDesk.clickByMeetingName("AUTOTEST_55766");
		outlookDesk.reminderDismissAll(); // Na tot sluchay esli poyavlyaetsa popup
		testLogOutlook.log(Status.INFO, "Join the meeting via skype.");
		outlookDesk.clickToJoinSkypeMeeting();
		//res = outlookDesk.runAndVerificationSkypeMeeting("AUTOTEST_55766");
		res = outlookDesk.runAndVerificationSkypeMeeting(meetingNameNew);
		// outlookDesk.runAndVerificationSkypeMeeting("AUTOTEST_24190");
		outlookDesk.closeSkypeWindows();
		Thread.sleep(1000);
		outlookDesk.clickOKButton();
		if (res) {
			testLogOutlook.pass("Test Passed!");
		} else {createScreenShot("C:\\1","TC12_");
			testLogOutlook.fail("Fail...");
		}
	}

	/* TC#13 - Click on the "all access numbers" */
	@Test(enabled = true, priority = 1, groups = { "DesktopOutlook" })
	public void checkAccessNumbers() throws InterruptedException, AWTException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		outlookDesk.reminderDismissAll(); // Na tot sluchay esli poyavlyaetsa popup
		testLogOutlook.log(Status.INFO, "Open the meeting");
		outlookDesk.clickByMeetingName("AUTO");
		outlookDesk.ifRecMeeting();
		testLogOutlook.log(Status.INFO, "Open all access numbers list.");
		outlookDesk.openAccessNumbers();
		outlookDesk.allowAccess();
		boolean res = outlookDesk.checkCountry("Israel");
		if(res) {testLogOutlook.pass("Test Passed!");}
		else {createScreenShot("C:\\1","TC13_"); Exception e;}
		
	}

	/* TC#14 - Cancel meeting */
	@Test(enabled = true, priority = 3)
	public void meetingCancellation() throws InterruptedException, AWTException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Meeting cancellation");
	    delMeeting = outlookDesk.amdocsMeetingCancellation("Test87639764");
		// delMeeting = outlookDesk.amdocsMeetingCancellation(meetingNameNew);
		//delMeeting = outlookDesk.amdocsMeetingCancellation("AUTOTEST_061345");
		if (delMeeting == true) {
			testLogOutlook.pass("Deleted");
		} else {createScreenShot("C:\\1","TC14_");
			testLogOutlook.fail("Test failed...");
		}
		Thread.sleep(2000);
	}
	/* TC#15 - Skype meeting. Call number _TestEnvAvayaPhoneNumber*/
	@Test(enabled = true, priority = 1)
	@Parameters({"_TestEnvAvayaPhoneNumber"})
	public void checkSkypeMeetingCallNumber(String _TestEnvAvayaPhoneNumber) throws IOException, InterruptedException, AWTException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		System.out.println("+++++++ "+_TestEnvAvayaPhoneNumber);
		//outlookDesk.callFromSkype(_TestEnvAvayaPhoneNumber, "AUTOTEST_082187");
		outlookDesk.callFromSkype(_TestEnvAvayaPhoneNumber, meetingNameNew);
		Thread.sleep(1000);
	}
	
	
	 /* TC#20 - Call number Polycom. Test env*/
	@Test(enabled = true, priority = 2)
	@Parameters({"_TestEnvPolycomPhoneNumber"})
	public void check_TestEnvPolycomPhoneNumber(String _TestEnvPolycomPhoneNumber) throws IOException, InterruptedException, AWTException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		System.out.println("+++++++ "+_TestEnvPolycomPhoneNumber);
		outlookDesk.callFromSkype(_TestEnvPolycomPhoneNumber, "AUTOTEST_082187");
		Thread.sleep(1000);
	} 
	 /* TC#21 - Call number Avaya. Prod env _ProductionAvayaPhoneNumber_2*/
		@Test(enabled = true, priority = 2)
		@Parameters({"_ProductionAvayaPhoneNumber_2"})
		public void check_ProductionAvayaPhoneNumber(String _ProductionAvayaPhoneNumber_2) throws IOException, InterruptedException, AWTException {
			testLogOutlook = extent.createTest(getClass().getName());
			testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
			OutlookDesktop outlookDesk = new OutlookDesktop(driver);
			outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
			System.out.println("+++++++ "+_ProductionAvayaPhoneNumber_2);
			outlookDesk.callFromSkype(_ProductionAvayaPhoneNumber_2, "AUTOTEST_082187");
			Thread.sleep(1000);
		} 
	  
		/* TC#22 - Call number Polycom. Prod env */
		@Test(enabled = true, priority = 2)
		@Parameters({"_ProductionPolycomPhoneNumber"})
		public void check_ProdPolycomPhoneNumber(String _ProductionPolycomPhoneNumber) throws IOException, InterruptedException, AWTException {
			testLogOutlook = extent.createTest(getClass().getName());
			testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
			OutlookDesktop outlookDesk = new OutlookDesktop(driver);
			outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
			System.out.println("+++++++ "+_ProductionPolycomPhoneNumber);
			outlookDesk.callFromSkype(_ProductionPolycomPhoneNumber, "AUTOTEST_082187");
			Thread.sleep(1000);
		} 
	 
	
	
	/* TC#16 - Verify before end meeting. Set up a meeting in the future and try to join it.*/
	@Test(enabled = true, priority = 0, groups = { "DesktopOutlook" })
	@Parameters({"_TestEnvAvayaPhoneNumber"})
	public void verifyRoomCreationBeforeMeetingStartTime(String _TestEnvAvayaPhoneNumber) throws InterruptedException, AWTException, IOException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		boolean res;
		Thread.sleep(1000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		List<String>meetingNameAndId = outlookDesk.createBeforeMeetingInTheFuture("yoelap@amdocs.com", 1, 5); // sozdaem meeting na 1 chs pozze tekushego vremeni
		System.out.println("Meeting name: " + meetingNameAndId.get(0).toString());
		//outlookDesk.clickByMeetingName(meetingName);
		//outlookDesk.reminderDismissAll(); // Na tot sluchay esli poyavlyaetsa popup
		testLogOutlook.log(Status.INFO, "Join the meeting via skype.");
		
		res = outlookDesk.callSkypeByList(_TestEnvAvayaPhoneNumber, meetingNameAndId);
		
		//outlookDesk.btnSendMeeting();
		//outlookDesk.exitAndSendLaterOrNot();
		if (res) {
			testLogOutlook.pass("Test Passed");
		} else { createScreenShot("C:\\1","TC16_");
			testLogOutlook.fail("Test failed...");
		}
	}
	
	/* TC#17 peredaem luboi meeting name i pitaemsa sdelat connect. */
	@Test(enabled = true, priority = 3, groups = { "DesktopOutlook" })
	@Parameters({"_ProductionAvayaPhoneNumber_1", "_AfterMeetingName"})
	public void verifyRoomCreationAfterMeetingStartTime(String _ProductionAvayaPhoneNumber_1, String _AfterMeetingName) throws InterruptedException, AWTException, IOException {
		boolean res;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(1000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		System.out.println("Meeting name: " + _AfterMeetingName);
		testLogOutlook.log(Status.INFO, "Join the meeting via skype.");
		
		res = outlookDesk.callFromSkype(_ProductionAvayaPhoneNumber_1, _AfterMeetingName);
		System.out.println("Result : "+res);
		//outlookDesk.btnSendMeeting();
		outlookDesk.exitAndSendLaterOrNot();
		if (!res) {
			testLogOutlook.pass("Test Passed! | No way to connect to a meeting in the past.");
		} else { createScreenShot("C:\\1","TC17_");
			testLogOutlook.fail("Test failed... : Connected to a meeting in the past.");
		}
	}
	// Update old meeting and try to connect (18, 24, 25, 26, 27,28)
	/* TC#18 Update meeting from the past and try to connect.(Time updating) NOT RECURRENCE */
	@Test(enabled = true, priority = 3, groups = { "DesktopOutlook" })
	@Parameters({"_MeetingInThePast"})
	public void updateMeetingFromThePast(String _MeetingInThePast) throws InterruptedException, AWTException {
		ArrayList<String> res;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(1000); 
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		
		//System.out.println("Meeting name: " + "AUTOTEST_082187");
		//res = outlookDesk.updateOneMeetingDateAndTime("AUTOTEST_082187", 0, 30);
		 System.out.println("Meeting name: " + _MeetingInThePast);
		 res = outlookDesk.updateOneMeetingDateAndTime(_MeetingInThePast, 0, 30, 0);
		
		newID = res.get(1);
		if (!(res.get(0).equals(res.get(1)))) {testLogOutlook.pass("Test Passed! | Meeting ID has been changed!");}
		else {createScreenShot("C:\\1","TC18_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	
	/* TC#24 Call using new meeting ID. NOT RECURRENCE */
	@Test(enabled = true, priority = 4, dependsOnMethods="updateMeetingFromThePast")
	@Parameters({"_TestEnvAvayaPhoneNumber"})
	public void checkConnectionNewMeetingIDAvaya(String _TestEnvAvayaPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_TestEnvAvayaPhoneNumber);
		OutlookDesktop od = new OutlookDesktop(driver);
		setRes = od.callByNumber(_TestEnvAvayaPhoneNumber, newID);
		// setRes = od.callByNumber(_TestEnvAvayaPhoneNumber, "8259662#");
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Connect is available through the new ID");}
		else {createScreenShot("C:\\1","TC24_"); testLogOutlook.fail("Test failed... : ");}
		
	}
	/* TC#25 Call using new meeting ID. NOT RECURRENCE */
	@Test(enabled = true, priority = 4, dependsOnMethods="updateMeetingFromThePast")
	@Parameters({"_TestEnvPolycomPhoneNumber"})
	public void checkConnectionNewMeetingIDPolycom(String _TestEnvPolycomPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_TestEnvPolycomPhoneNumber);
		OutlookDesktop od = new OutlookDesktop(driver);
		//setRes = od.callByNumber(_ProductionAvayaPhoneNumber_1, newID);
		setRes = od.callByNumber(_TestEnvPolycomPhoneNumber, newID);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Connect is available through the new ID");}
		else {createScreenShot("C:\\1","TC25_"); testLogOutlook.fail("Test failed... : ");}
		
	}
	
	
	/* TC#26 Call using new meeting ID. NOT RECURRENCE (Prod. Avaya)*/
	@Test(enabled = true, priority = 4, dependsOnMethods="updateMeetingFromThePast")
	@Parameters({"_ProductionAvayaPhoneNumber_1"})
	public void checkConnectionNewMeetingIDAvayaProduction(String _ProductionAvayaPhoneNumber_1) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_ProductionAvayaPhoneNumber_1);
		OutlookDesktop od = new OutlookDesktop(driver);
		//setRes = od.callByNumber(_ProductionAvayaPhoneNumber_1, newID);
		setRes = od.callByNumber(_ProductionAvayaPhoneNumber_1, newID);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Connect is available through the new ID");}
		else {createScreenShot("C:\\1","TC26_"); testLogOutlook.fail("Test failed... : ");}
		
	}
	
	/* TC#27 Call using new meeting ID. NOT RECURRENCE! (Prod. Polycom) */
	@Test(enabled = true, priority = 4, dependsOnMethods="updateMeetingFromThePast")
	@Parameters({"_ProductionPolycomPhoneNumber"})
	public void checkConnectionNewMeetingIDPolycomProduction(String _ProductionPolycomPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_ProductionPolycomPhoneNumber);
		OutlookDesktop od = new OutlookDesktop(driver);
		//setRes = od.callByNumber(_ProductionAvayaPhoneNumber_1, newID);
		setRes = od.callByNumber(_ProductionPolycomPhoneNumber, newID);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Connect is available through the new ID");}
		else {createScreenShot("C:\\1","TC27_"); testLogOutlook.fail("Test failed... : ");}
		
	}
	
	/* TC#28 Call using new meeting ID. NOT RECURRENCE (Prod. Polycom 2)*/
	@Test(enabled = true, priority = 4, dependsOnMethods="updateMeetingFromThePast")
	@Parameters({"_ProductionAvayaPhoneNumber_2"})
	public void checkConnectionNewMeetingIDAvayaProduction_2(String _ProductionAvayaPhoneNumber_2) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_ProductionAvayaPhoneNumber_2);
		OutlookDesktop od = new OutlookDesktop(driver);
		//setRes = od.callByNumber(_ProductionAvayaPhoneNumber_1, newID);
		setRes = od.callByNumber(_ProductionAvayaPhoneNumber_2, newID);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Connect is available through the new ID");}
		else {createScreenShot("C:\\1","TC28_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#29 Create Amdocs meeting in the future. */
	@Test(enabled = true, priority = 0, groups = { "DesktopOutlook" })
	public void createAmdocsMeetingInTheFuture() throws InterruptedException, AWTException, IOException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(1000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		meetingNameAndId = outlookDesk.createBeforeMeetingInTheFuture("yoelap@amdocs.com", 1, 5); // sozdaem meeting na 1 chs pozze tekushego vremeni
		System.out.println("Meeting name: " + meetingNameAndId.get(0).toString());
		//outlookDesk.clickByMeetingName(meetingName);
		//outlookDesk.reminderDismissAll(); // Na tot sluchay esli poyavlyaetsa popup
		//outlookDesk.btnSendMeeting();
		//outlookDesk.exitAndSendLaterOrNot();
		if (meetingNameAndId.size()>0) {
			testLogOutlook.pass("Test Passed. Meeting created. ID : "+ meetingNameAndId.get(1).toString());
		} else {createScreenShot("C:\\1","TC29_");
			testLogOutlook.fail("Test failed...");
		}
	}
	
	/* TC#30 Verify room creation on Avaya (30 minutes before meeting start time). ()*/ // main param - meetingNameAndId
	@Test(enabled = true, priority = 4, dependsOnMethods="createAmdocsMeetingInTheFuture")
	@Parameters({"_TestEnvAvayaPhoneNumber"})
	public void checkConnectionBeforeMeetingAvayaTestEnv(String _TestEnvAvayaPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_TestEnvAvayaPhoneNumber);
		OutlookDesktop od = new OutlookDesktop(driver);
		String mID = meetingNameAndId.get(1);
		setRes = od.callByNumber(_TestEnvAvayaPhoneNumber, mID);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Joining a meeting is not possible. More than 30 minutes to meet.");}
		else {createScreenShot("C:\\1","TC30_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#31 Verify room creation on Polycom (30 minutes before meeting start time). ()*/ // main param - meetingNameAndId
	@Test(enabled = true, priority = 4, dependsOnMethods="createAmdocsMeetingInTheFuture")
	@Parameters({"_TestEnvPolycomPhoneNumber"})
	public void checkConnectionBeforeMeetingPolycomTestEnv(String _TestEnvPolycomPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_TestEnvPolycomPhoneNumber);
		OutlookDesktop od = new OutlookDesktop(driver);
		String mID = meetingNameAndId.get(1);
		setRes = od.callByNumber(_TestEnvPolycomPhoneNumber, mID);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Joining a meeting is not possible. More than 30 minutes to meet.");}
		else {createScreenShot("C:\\1","TC31_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#32 Verify room creation on Avaya (30 minutes before meeting start time). (Production)*/ // main param - meetingNameAndId
	@Test(enabled = true, priority = 4, dependsOnMethods="createAmdocsMeetingInTheFuture")
	@Parameters({"_ProductionAvayaPhoneNumber_1"})
	public void checkConnectionBeforeMeetingAvayaProduction_1(String _ProductionAvayaPhoneNumber_1) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_ProductionAvayaPhoneNumber_1);
		OutlookDesktop od = new OutlookDesktop(driver);
		String mID = meetingNameAndId.get(1);
		setRes = od.callByNumber(_ProductionAvayaPhoneNumber_1, mID);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Joining a meeting is not possible. More than 30 minutes to meet.");}
		else {createScreenShot("C:\\1","TC32_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#33 Verify room creation on Avaya (30 minutes before meeting start time). (Production)*/ // main param - meetingNameAndId
	@Test(enabled = true, priority = 4, dependsOnMethods="createAmdocsMeetingInTheFuture")
	@Parameters({"_ProductionAvayaPhoneNumber_2"})
	public void checkConnectionBeforeMeetingAvayaProduction_2(String _ProductionAvayaPhoneNumber_2) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_ProductionAvayaPhoneNumber_2);
		OutlookDesktop od = new OutlookDesktop(driver);
		String mID = meetingNameAndId.get(1);
		setRes = od.callByNumber(_ProductionAvayaPhoneNumber_2, mID);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Joining a meeting is not possible. More than 30 minutes to meet.");}
		else {createScreenShot("C:\\1","TC33_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#34 Verify room creation on Avaya (30 minutes before meeting start time). (Production)*/ // main param - meetingNameAndId
	@Test(enabled = true, priority = 4, dependsOnMethods="createAmdocsMeetingInTheFuture")
	@Parameters({"_ProductionPolycomPhoneNumber"})
	public void checkConnectionBeforeMeetingPolycomProduction(String _ProductionPolycomPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_ProductionPolycomPhoneNumber);
		OutlookDesktop od = new OutlookDesktop(driver);
		String mID = meetingNameAndId.get(1);
		setRes = od.callByNumber(_ProductionPolycomPhoneNumber, mID);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Joining a meeting is not possible. More than 30 minutes to meet.");}
		else {createScreenShot("C:\\1","TC34_"); testLogOutlook.fail("Test failed... : ");}
	}
	
// ********** Try to connect to Rec meeting (After the end of the allowed connection time.) ********** TestRecMeetingPast | ID: String recurringMeetingInThePast = "8256903#"; 
	/* TC#40 Verify room creation on Avaya (30 minutes before meeting start time). ()*/ // main param - meetingNameAndId
	@Test(enabled = true, priority = 4, dependsOnMethods="createAmdocsMeetingInTheFuture")
	@Parameters({"_TestEnvAvayaPhoneNumber"})
	public void checkConnectionAfterMeetingAvayaTestEnv(String _TestEnvAvayaPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_TestEnvAvayaPhoneNumber);
		OutlookDesktop od = new OutlookDesktop(driver);
		String mID = meetingNameAndId.get(1);
		setRes = od.callByNumber(_TestEnvAvayaPhoneNumber, recurringMeetingInThePast);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Joining a meeting is not possible. More than 30 minutes to meet.");}
		else {createScreenShot("C:\\1","TC40_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#41 Verify room creation on Polycom (30 minutes before meeting start time). ()*/ // main param - meetingNameAndId
	@Test(enabled = true, priority = 4, dependsOnMethods="createAmdocsMeetingInTheFuture")
	@Parameters({"_TestEnvPolycomPhoneNumber"})
	public void checkConnectionAfterMeetingPolycomTestEnv(String _TestEnvPolycomPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_TestEnvPolycomPhoneNumber);
		OutlookDesktop od = new OutlookDesktop(driver);
		String mID = meetingNameAndId.get(1);
		setRes = od.callByNumber(_TestEnvPolycomPhoneNumber, recurringMeetingInThePast);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Joining a meeting is not possible. More than 30 minutes to meet.");}
		else {createScreenShot("C:\\1","TC41_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#42 Verify room creation on Avaya (30 minutes before meeting start time). (Production)*/ // main param - meetingNameAndId
	@Test(enabled = true, priority = 4, dependsOnMethods="createAmdocsMeetingInTheFuture")
	@Parameters({"_ProductionAvayaPhoneNumber_1"})
	public void checkConnectionAfterMeetingAvayaProduction_1(String _ProductionAvayaPhoneNumber_1) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_ProductionAvayaPhoneNumber_1);
		OutlookDesktop od = new OutlookDesktop(driver);
		String mID = meetingNameAndId.get(1);
		setRes = od.callByNumber(_ProductionAvayaPhoneNumber_1, recurringMeetingInThePast);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Joining a meeting is not possible. More than 30 minutes to meet.");}
		else {createScreenShot("C:\\1","TC42_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#43 Verify room creation on Avaya (30 minutes before meeting start time). (Production)*/ // main param - meetingNameAndId
	@Test(enabled = true, priority = 4, dependsOnMethods="createAmdocsMeetingInTheFuture")
	@Parameters({"_ProductionAvayaPhoneNumber_2"})
	public void checkConnectionAfterMeetingAvayaProduction_2(String _ProductionAvayaPhoneNumber_2) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_ProductionAvayaPhoneNumber_2);
		OutlookDesktop od = new OutlookDesktop(driver);
		String mID = meetingNameAndId.get(1);
		setRes = od.callByNumber(_ProductionAvayaPhoneNumber_2, recurringMeetingInThePast);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Joining a meeting is not possible. More than 30 minutes to meet.");}
		else {createScreenShot("C:\\1","TC43_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#44 Verify room creation on Avaya (30 minutes before meeting start time). (Production)*/ // main param - meetingNameAndId
	@Test(enabled = true, priority = 4, dependsOnMethods="createAmdocsMeetingInTheFuture")
	@Parameters({"_ProductionPolycomPhoneNumber"})
	public void checkConnectionAfterMeetingPolycomProduction(String _ProductionPolycomPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_ProductionPolycomPhoneNumber);
		OutlookDesktop od = new OutlookDesktop(driver);
		String mID = meetingNameAndId.get(1);
		setRes = od.callByNumber(_ProductionPolycomPhoneNumber, recurringMeetingInThePast);
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Joining a meeting is not possible. More than 30 minutes to meet.");}
		else {createScreenShot("C:\\1","TC44_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/*
	+ _ProductionAvayaPhoneNumber_1
	+ _ProductionAvayaPhoneNumber_2
	 _ProductionPolycomPhoneNumber
	+ _TestEnvAvayaPhoneNumber
	+ _TestEnvPolycomPhoneNumber 
	 */
	
	/* TC#45 Create new Recurrence meeting 1 time per week */
	@Test(enabled = true, priority = 4)
	public void createRecurrenceMeetingOneTimePerWeek() throws InterruptedException, AWTException {
		boolean isExist;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		recuringOnePerWeekMeetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com"); //return title and ID
		//outlookDesk.changeDate(-2);
		testLogOutlook.log(Status.INFO, "Add Recurrence");
		isExist = outlookDesk.addRecurrence(RecurrencePattern.WEEKLY, true, 0, 32, 0); // sozdau meeting cherez 32 min
		// isExist = outlookDesk.addRecurrenceNew(RecurrencePattern.WEEKLY, false, 0, 50, 0); // sozdau meeting cherez 50 min
		if(isExist) {testLogOutlook.pass("Test Passed! | ");}
		else {createScreenShot("C:\\1","TC45_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#46 Reschedule recurrence meeting 1 time per week / another time and try to connect */
	@Test(enabled = true, priority = 4, dependsOnMethods = "createRecurrenceMeetingOneTimePerWeek")
	public void rescheduleRecMeetingOnePerWeekToDaily() throws InterruptedException {
		boolean passFail;
	testLogOutlook = extent.createTest(getClass().getName());
	testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
	OutlookDesktop outlookDesk = new OutlookDesktop(driver);
	Thread.sleep(2000);
	testLogOutlook.log(Status.INFO, "Open Calendar");
	outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
	passFail = outlookDesk.updateAllRMeetings(recuringOnePerWeekMeetingName.get(0).toString(), RecurrencePattern.DAILY, 0, 15, true, 8);
	if(passFail) {testLogOutlook.pass("Test Passed! "+recuringOnePerWeekMeetingName.get(0).toString() + " updated!");}
	else {createScreenShot("C:\\1","TC46_"); testLogOutlook.fail("Test fail...");}
	Thread.sleep(2000);
	}
	
	/* TC#47 Connection to rescheduled recurrence meeting. Weekly to Daily */
	@Test(enabled = true, priority = 4, dependsOnMethods = "rescheduleRecMeetingOnePerWeekToDaily")
	@Parameters({"_TestEnvAvayaPhoneNumber"})
	public void connectToRescheduledRecMeetingWeeklyToDailyAvayaTestEnv(String _TestEnvAvayaPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean passFail;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		passFail = outlookDesk.callByNumber(_TestEnvAvayaPhoneNumber, recuringOnePerWeekMeetingName.get(1).toString());
		if(passFail) {testLogOutlook.pass("Test Passed! | .");}
		else {createScreenShot("C:\\1","TC47_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#48 Connection to rescheduled recurrence meeting. Weekly to Daily */
	@Test(enabled = true, priority = 4, dependsOnMethods = "rescheduleRecMeetingOnePerWeekToDaily")
	@Parameters({"_TestEnvPolycomPhoneNumber"})
	public void connectToRescheduledRecMeetingWeeklyToDailyPolicomTestEnv(String _TestEnvPolycomPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean passFail;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		passFail = outlookDesk.callByNumber(_TestEnvPolycomPhoneNumber, recuringOnePerWeekMeetingName.get(1).toString());
		if(passFail) {testLogOutlook.pass("Test Passed! | .");}
		else {createScreenShot("C:\\1","TC48_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#49 Connection to rescheduled recurrence meeting. Weekly to Daily */
	@Test(enabled = true, priority = 4, dependsOnMethods = "rescheduleRecMeetingOnePerWeekToDaily")
	@Parameters({"_ProductionAvayaPhoneNumber_1"})
	public void connectToRescheduledRecMeetingWeeklyToDailyAvayaProduction_1(String _ProductionAvayaPhoneNumber_1) throws IOException, AWTException, InterruptedException {
		boolean passFail;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		passFail = outlookDesk.callByNumber(_ProductionAvayaPhoneNumber_1, recuringOnePerWeekMeetingName.get(1).toString());
		if(passFail) {testLogOutlook.pass("Test Passed! | .");}
		else {createScreenShot("C:\\1","TC49_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#50 Connection to rescheduled recurrence meeting. Weekly to Daily */
	@Test(enabled = true, priority = 4, dependsOnMethods = "rescheduleRecMeetingOnePerWeekToDaily")
	@Parameters({"_ProductionAvayaPhoneNumber_2"})
	public void connectToRescheduledRecMeetingWeeklyToDailyAvayaProduction_2(String _ProductionAvayaPhoneNumber_2) throws IOException, AWTException, InterruptedException {
		boolean passFail;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		passFail = outlookDesk.callByNumber(_ProductionAvayaPhoneNumber_2, recuringOnePerWeekMeetingName.get(1).toString());
		if(passFail) {testLogOutlook.pass("Test Passed! | .");}
		else {createScreenShot("C:\\1","TC50_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#51 Connection to rescheduled recurrence meeting. Weekly to Daily */
	@Test(enabled = true, priority = 4, dependsOnMethods = "rescheduleRecMeetingOnePerWeekToDaily")
	@Parameters({"_ProductionPolycomPhoneNumber"})
	public void connectToRescheduledRecMeetingWeeklyToDailyPolycomProduction_2(String _ProductionPolycomPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean passFail;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		passFail = outlookDesk.callByNumber(_ProductionPolycomPhoneNumber, recuringOnePerWeekMeetingName.get(1).toString());
		if(passFail) {testLogOutlook.pass("Test Passed! | .");}
		else {createScreenShot("C:\\1","TC51_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#52 Amdocs meeting window main test */
	@Test(enabled = true, priority = 0)
	public void checkMainAmdocsMeetingWindowTemplate() throws InterruptedException, AWTException {
		boolean isExist;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		isExist = outlookDesk.getLinksFromAmdocsMeetingWindow();
		outlookDesk.closeWithoutChanges();
		if(isExist) {testLogOutlook.pass("Test Passed! | Meeting template works properly.");}
		else {createScreenShot("C:\\1","TC52_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	
	//**********************************************************************
	
	/* TC#53 - Schedule Reoccurring meeting in 2 hours*/
	@Test(enabled = true, priority = 4)
	public void createReoccurringMeeting_2() throws InterruptedException, AWTException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		boolean isExist;
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		ArrayList<String> recuringMeetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com");
		testLogOutlook.log(Status.INFO, "Add Recurrence");
		isExist = outlookDesk.addRecurrence(RecurrencePattern.DAILY, true, 2, 0, 0);
		System.out.println("Meeting name : " + recuringMeetingName.get(0));
		createdRecMeeting = recuringMeetingName.get(0);
		createdRecMeetingID = recuringMeetingName.get(1);
		if (isExist) {
			testLogOutlook.pass("Test Passed");
		} else {createScreenShot("C:\\1","TC53_");
			testLogOutlook.fail("Test failed...");
		}
		//Thread.sleep(500);
	}
	/* TC#54 - Update series from recurring meeting date and time(valid) */
	@Test(enabled = true, priority = 5, dependsOnMethods = "createReoccurringMeeting_2")
	public void updateReoccurringMeetingSeries() throws InterruptedException, AWTException {
		boolean passFail;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Open the meeting. Change the time meeting.");
		//passFail = outlookDesk.updateReoccurringMeetingTimeAndDay(createdRecMeeting, OpenRecurrenceType.OPEN_OCCURENCE, 0, 30);
		//passFail = outlookDesk.updateReoccurringMeetingTimeAndDay(createdRecMeeting, OpenRecurrenceType.OPEN_SERIES, 0, 30);
		passFail = outlookDesk.updateReoccurringMeeting(createdRecMeeting, OpenRecurrenceType.OPEN_SERIES, 0, 30, 0);
		if (passFail) {
			testLogOutlook.pass("Test Passed");
		} else {createScreenShot("C:\\1","TC54_");
			testLogOutlook.fail("Test fail...");
		}
	}
	
	/* TC#55 - Update 1 event from recurring meeting date and time(valid) */
	@Test(enabled = true, priority = 6,  dependsOnMethods = "createReoccurringMeeting_2")
	public void updateReoccurringMeetingOne() throws InterruptedException, AWTException {
		boolean passFail;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Open the meeting. Change the time meeting.");
		//passFail = outlookDesk.updateReoccurringMeetingTimeAndDay(createdRecMeeting, OpenRecurrenceType.OPEN_OCCURENCE, 0, 30);
		passFail = outlookDesk.updateReoccurringMeetingTimeAndDay(createdRecMeeting, OpenRecurrenceType.OPEN_OCCURENCE, 0, 30);
		if (passFail) {
			testLogOutlook.pass("Test Passed");
		} else {createScreenShot("C:\\1","TC55_");
			testLogOutlook.fail("Test fail...");
		}
	}
	
	
	/* TC#56 - Connect to rescheduled meeting (Avaya. Test env). The test must be started 2 minutes after the updateReoccurringMeetingOne test is completed!*/
	@Test(enabled = true, priority = 5, dependsOnMethods="createReoccurringMeeting_2")
	@Parameters({"_TestEnvAvayaPhoneNumber"})
	public void connectionUpdatedRecurringMeetingOneAvayaTestEnv(String _TestEnvAvayaPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean passFail;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		passFail = outlookDesk.callByNumber(_TestEnvAvayaPhoneNumber, createdRecMeetingID);
		if(passFail) {testLogOutlook.pass("Test Passed! | .");}
		else { createScreenShot("C:\\1","TC56_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#57 - Connect to rescheduled meeting (Polycom. Test env).  The test must be started 2 minutes after the updateReoccurringMeetingOne test is completed!*/
	@Test(enabled = true, priority = 6,  dependsOnMethods = "updateReoccurringMeetingOne")
	@Parameters({"_TestEnvPolycomPhoneNumber"})
	public void connectionUpdatedRecurringMeetingOnePolycomTestEnv(String _TestEnvPolycomPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean passFail;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		passFail = outlookDesk.callByNumber(_TestEnvPolycomPhoneNumber, createdRecMeetingID);
		if(passFail) {testLogOutlook.pass("Test Passed! | .");}
		else {createScreenShot("C:\\1","TC57_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#58 - Create recurrence meeting and reschedule 1 event - Invalid day and time*/
	@Test(enabled = true, priority = 6)
	public void createRecurrenceMeetingAndReschadule1Event() throws InterruptedException, AWTException {
		boolean isExist;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		ArrayList<String> recuringMeetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com");
		testLogOutlook.log(Status.INFO, "Add Recurrence");
		isExist = outlookDesk.addRecurrence(RecurrencePattern.DAILY, true, 1, 0, 1);
		System.out.println("Meeting name : " + recuringMeetingName.get(0));
		createdRecMeeting = recuringMeetingName.get(0);
		createdRecMeetingID = recuringMeetingName.get(1);
		boolean res = outlookDesk.updateReoccurringMeetingAndDay("", OpenRecurrenceType.OPEN_OCCURENCE, 0, 0, 1);
		if(res) {testLogOutlook.pass("Test Passed! | .");}
		else {createScreenShot("C:\\1","TC58_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#59 Update meeting from the past and try to connect.(Time and Date updating) NOT RECURRENCE */
	@Test(enabled = true, priority = 3, groups = { "DesktopOutlook" })
	@Parameters({"_MeetingInThePast"})
	public void updateMeetingFromThePastTimeAndDateInOneDay(String _MeetingInThePast) throws InterruptedException, AWTException {
		ArrayList<String> res;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(1000); 
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		
		System.out.println("Meeting name: " + "TestPast_002");
		//res = outlookDesk.updateOneMeetingDateAndTime("AUTOTEST_082187", 0, 30);
		 //System.out.println("Meeting name: " + _MeetingInThePast);
		 //res = outlookDesk.updateOneMeetingDateAndTime(_MeetingInThePast, 0, 30, 1);
		 res = outlookDesk.updateOneMeetingDateAndTime("TestPast_002", 0, 30, 1);
		
		newID = res.get(1);
		if (!(res.get(0).equals(res.get(1)))) {testLogOutlook.pass("Test Passed! | Meeting ID has been changed!");}
		else {createScreenShot("C:\\1","TC59_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	
	/* TC#60 - Try to connect. Deleted meeting. Relevant time. Avaya */
	@Test(enabled = true, priority = 0, groups = { "DesktopOutlook" })
	@Parameters({"_TestEnvAvayaPhoneNumber"})
	public void connectToDeletedMeetingRelevantTimeAvaya(String _TestEnvAvayaPhoneNumber) throws InterruptedException, AWTException, IOException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "TC#60");
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		ArrayList<String> newMeetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com");
		testLogOutlook.log(Status.INFO, "Add Recurrence");
		createdRecMeeting = newMeetingName.get(0);
		createdRecMeetingID = newMeetingName.get(1);
		outlookDesk.btnSendMeeting_ByRobot();
		outlookDesk.amdocsMeetingCancellation(createdRecMeeting);
//		outlookDesk.exitAndSendLaterOrNot();
		setRes = outlookDesk.callByNumber(_TestEnvAvayaPhoneNumber, createdRecMeetingID);
		if(setRes) {testLogOutlook.pass("Test Passed! | Connect is available through the new ID");}
		else {createScreenShot("C:\\1","TC60_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#61 - Try to connect. Deleted meeting. Relevant time. Polycom */
	@Test(enabled = true, priority = 0, groups = { "DesktopOutlook" })
	@Parameters({"_TestEnvPolycomPhoneNumber"})
	public void connectToDeletedMeetingRelevantTimePolycom(String _TestEnvPolycomPhoneNumber) throws InterruptedException, AWTException, IOException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "TC#60");
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		ArrayList<String> newMeetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com");
		testLogOutlook.log(Status.INFO, "Add Recurrence");
		createdRecMeeting = newMeetingName.get(0);
		createdRecMeetingID = newMeetingName.get(1);
		outlookDesk.btnSendMeeting_ByRobot();
		outlookDesk.amdocsMeetingCancellation(createdRecMeeting);
//		outlookDesk.exitAndSendLaterOrNot();
		setRes = outlookDesk.callByNumber(_TestEnvPolycomPhoneNumber, createdRecMeetingID);
		if(setRes) {testLogOutlook.pass("Test Passed! | Connect is available through the new ID");}
		else {createScreenShot("C:\\1","TC61_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#62 - Try to connect. Deleted meeting. Relevant time. Avaya  (Production)*/
	@Test(enabled = true, priority = 0, groups = { "DesktopOutlook" })
	@Parameters({"_ProductionAvayaPhoneNumber_1"})
	public void connectToDeletedMeetingRelevantTimeAvayaProdEnv(String _ProductionAvayaPhoneNumber_1) throws InterruptedException, AWTException, IOException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "TC#60");
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		ArrayList<String> newMeetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com");
		testLogOutlook.log(Status.INFO, "Add Recurrence");
		createdRecMeeting = newMeetingName.get(0);
		createdRecMeetingID = newMeetingName.get(1);
		outlookDesk.btnSendMeeting_ByRobot();
		outlookDesk.amdocsMeetingCancellation(createdRecMeeting);

		setRes = outlookDesk.callByNumber(_ProductionAvayaPhoneNumber_1, createdRecMeetingID);
		if(setRes) {testLogOutlook.pass("Test Passed! | Connect is available through the new ID");}
		else {createScreenShot("C:\\1","TC62_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#63 - Try to connect. Deleted meeting. Relevant time. Avaya 2 (Production)*/
	@Test(enabled = true, priority = 0, groups = { "DesktopOutlook" })
	@Parameters({"_ProductionAvayaPhoneNumber_2"})
	public void connectToDeletedMeetingRelevantTimeAvaya2ProdEnv(String _ProductionAvayaPhoneNumber_2) throws InterruptedException, AWTException, IOException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "TC#60");
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		ArrayList<String> newMeetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com");
		testLogOutlook.log(Status.INFO, "Add Recurrence");
		createdRecMeeting = newMeetingName.get(0);
		createdRecMeetingID = newMeetingName.get(1);
		outlookDesk.btnSendMeeting_ByRobot();
		outlookDesk.amdocsMeetingCancellation(createdRecMeeting);
//		outlookDesk.exitAndSendLaterOrNot();
		setRes = outlookDesk.callByNumber(_ProductionAvayaPhoneNumber_2, createdRecMeetingID);
		if(setRes) {testLogOutlook.pass("Test Passed! | Connect is available through the new ID");}
		else {createScreenShot("C:\\1","TC63_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#64 - Try to connect. Deleted meeting. Relevant time. Polycom (Production)*/
	@Test(enabled = true, priority = 0, groups = { "DesktopOutlook" })
	@Parameters({"_ProductionPolycomPhoneNumber"})
	public void connectToDeletedMeetingRelevantTimePolycomProdEnv(String _ProductionPolycomPhoneNumber) throws InterruptedException, AWTException, IOException {
		boolean setRes;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "TC#60");
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		ArrayList<String> newMeetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com");
		testLogOutlook.log(Status.INFO, "Add Recurrence");
		createdRecMeeting = newMeetingName.get(0);
		createdRecMeetingID = newMeetingName.get(1);
		outlookDesk.btnSendMeeting_ByRobot();
		outlookDesk.amdocsMeetingCancellation(createdRecMeeting);
//		outlookDesk.exitAndSendLaterOrNot();
		setRes = outlookDesk.callByNumber(_ProductionPolycomPhoneNumber, createdRecMeetingID);
		if(setRes) {testLogOutlook.pass("Test Passed! | Connect is available through the new ID");}
		else {createScreenShot("C:\\1","TC64_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#65 - Create rec meeting. Update 1 in the past */
	@Test(enabled = true, priority = 4, groups = { "DesktopOutlook" })
	public void createReoccurringMeetingUpdateOneInThePast() throws InterruptedException, AWTException {
		boolean isExist;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "TC#65");
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		ArrayList<String> recuringMeetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com");
		testLogOutlook.log(Status.INFO, "Add Recurrence");
		isExist = outlookDesk.addRecurrence(RecurrencePattern.DAILY, true, -1, 0, 0);
		createdRecMeeting = recuringMeetingName.get(0);
		testLogOutlook.log(Status.INFO, "Try to move 1 meeting to the past");
		isExist = outlookDesk.updateReoccurringMeetingAndDay(createdRecMeeting, OpenRecurrenceType.OPEN_OCCURENCE, 0, 0, -1);
		if (isExist) {
			testLogOutlook.pass("Test Passed");
		} else {createScreenShot("C:\\1","TC65_");
			testLogOutlook.fail("Test failed...");
		}
		Thread.sleep(1000);
	}
	
	/* TC#66 Create meeting without meeting ID*/
	@Test
	public void createMeetingWithoutID() throws InterruptedException, AWTException, IOException {
		boolean isExist;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		isExist = outlookDesk.specifyMeetingPropertiesAndDeleteMeetingID("yoelap@amdocs.com");
		if(isExist) {testLogOutlook.pass("Test Passed! | Meeting ID has been changed!");}
		else {createScreenShot("C:\\1","TC66_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#67 Create all day meeting */
	@Test(enabled = true, priority = 4, groups = { "AllDayMeeting" })
	public void createAllDayMeeting() throws InterruptedException, AWTException, IOException {
		ArrayList<Object> myResList;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "* Create all day meeting.");
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		myResList = outlookDesk.createAllDayMeeting("yoelap@amdocs.com", true);
		allDayMeetingID = (String) myResList.get(1);
		if (!allDayMeetingID.isEmpty()) {testLogOutlook.pass("Test Passed! | All day meeting has been created.");}
		else {createScreenShot("C:\\1","TC67_"); testLogOutlook.fail("Test failed... : ");}
	}
	/* TC#68 - Connect to all day meeting (Avaya. Test env). */
	@Test(enabled = true, priority = 5, dependsOnMethods="createAllDayMeeting")
	@Parameters({"_TestEnvAvayaPhoneNumber"})
	public void connectionAllDayMeetingAvayaTestEnv(String _TestEnvAvayaPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean passFail;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		passFail = outlookDesk.callByNumber(_TestEnvAvayaPhoneNumber, allDayMeetingID);
		if(passFail) {testLogOutlook.pass("Test Passed! | .");}
		else { createScreenShot("C:\\1","TC68_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#69 - Connect to all day meeting (Polycom. Test env). */
	@Test(enabled = true, priority = 5, dependsOnMethods="createAllDayMeeting")
	@Parameters({"_TestEnvPolycomPhoneNumber"})
	public void connectionAllDayMeetingPolycomTestEnv(String _TestEnvPolycomPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean passFail;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		passFail = outlookDesk.callByNumber(_TestEnvPolycomPhoneNumber, allDayMeetingID);
		if(passFail) {testLogOutlook.pass("Test Passed! | .");}
		else { createScreenShot("C:\\1","TC69_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#70 - Connect to all day meeting (Avaya 1. Production env). */
	@Test(enabled = true, priority = 5, dependsOnMethods="createAllDayMeeting")
	@Parameters({"_ProductionAvayaPhoneNumber_1"})
	public void connectionAllDayMeetingAvayaProductionEnv(String _ProductionAvayaPhoneNumber_1) throws IOException, AWTException, InterruptedException {
		boolean passFail;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		passFail = outlookDesk.callByNumber(_ProductionAvayaPhoneNumber_1, allDayMeetingID);
		if(passFail) {testLogOutlook.pass("Test Passed! | .");}
		else { createScreenShot("C:\\1","TC70_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#71 - Connect to all day meeting (Avaya 2. Production env). */
	@Test(enabled = true, priority = 5, dependsOnMethods="createAllDayMeeting")
	@Parameters({"_ProductionAvayaPhoneNumber_2"})
	public void connectionAllDayMeetingAvaya2ProductionEnv(String _ProductionAvayaPhoneNumber_2) throws IOException, AWTException, InterruptedException {
		boolean passFail;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		passFail = outlookDesk.callByNumber(_ProductionAvayaPhoneNumber_2, allDayMeetingID);
		if(passFail) {testLogOutlook.pass("Test Passed! | .");}
		else { createScreenShot("C:\\1","TC71_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#72 - Connect to all day meeting (Polycom. Production env). */
	@Test(enabled = true, priority = 5, dependsOnMethods="createAllDayMeeting")
	@Parameters({"_ProductionPolycomPhoneNumber"})
	public void connectionAllDayMeetingPolycomProductionEnv(String _ProductionPolycomPhoneNumber) throws IOException, AWTException, InterruptedException {
		boolean passFail;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		passFail = outlookDesk.callByNumber(_ProductionPolycomPhoneNumber, allDayMeetingID);
		if(passFail) {testLogOutlook.pass("Test Passed! | .");}
		else { createScreenShot("C:\\1","TC72_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#73 Create more than 24 hours meeting */
	@Test(enabled = true, priority = 0, groups = { "24HoursMeeting" })
	public void create24HoursMeeting() throws InterruptedException, AWTException, IOException {
		boolean myResList;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "* Create 24 hours meeting.");
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		myResList = outlookDesk.schedule24HoursMeeting("yoelap@amdocs.com", 1);
		if (myResList) {testLogOutlook.pass("Test Passed! | All day meeting has been created.");}
		else {createScreenShot("C:\\1","TC73_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/* TC#74 Create more than 24 hours meeting */
	@Test(enabled = true, priority = 0, groups = { "24HoursMeeting" })
	public void create6MonthMeeting() throws InterruptedException, AWTException, IOException {
		boolean isExist;
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "* Create 6 months meeting.");
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		ArrayList<String> recuringMeetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com; michael.prudnikov@amdocs.com");
		testLogOutlook.log(Status.INFO, "Add Recurrence");
		isExist = outlookDesk.addRecurrence(RecurrencePattern.DAILY, false, 1, 0, -2);
		
		if (isExist) {testLogOutlook.pass("Test Passed! | You cannot schedule Amdocs Meeting with reoccurrence range of more than 6 months.");}
		else {createScreenShot("C:\\1","TC74_"); testLogOutlook.fail("Test failed... : ");}
	}
	
	/*
	 +_TestEnvAvayaPhoneNumber
	 +_TestEnvPolycomPhoneNumber 
	 +_ProductionAvayaPhoneNumber_1
	 +_ProductionAvayaPhoneNumber_2
	 +_ProductionPolycomPhoneNumber
	 
	 */
	/********************************************************************************************************************/

	@Test
	public void takeMailHeaders() throws InterruptedException, AWTException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.MAIL);
		outlookDesk.copyMailHeader("New Tester for ITEx");
		Thread.sleep(2000);
		// after all
	}

	@Test(enabled = false, priority = 4)
	public void openMailFromTheMailList() throws InterruptedException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.MAIL);
		outlookDesk.searchMail("STRING");
	}

	@Test(enabled = true, priority = 1, groups = { "DesktopOutlook" })
	public void createNewMeeting() throws InterruptedException, AWTException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Meeting");
		outlookDesk.clickNewMeeting();
		outlookDesk.specifyLocation("TestLocation");
		ArrayList<String> meetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com");
		outlookDesk.btnSendMeeting_ByRobot();
		// meetingInTheCalendar = outlookDesk.checkMeetingExistance(meetingName);
		// System.out.println("RESULT: "+meetingInTheCalendar);
		if(meetingName.size()>0) {
		testLogOutlook.pass("Test Passed");}
		else {testLogOutlook.fail("Test failed...");}

	}

	@Test(enabled = true, priority = 2, groups = { "DesktopOutlook" })
	public void createNewMessage() throws InterruptedException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Mail");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.MAIL);
		outlookDesk.clickCreateNewMessage();
		String title;
		title = outlookDesk.specifyNewMessageInfo("yoelap@amdocs.com");
		System.out.println("MESSAGE_TITLE - " + title);
		testLogOutlook.pass("New message has been created!");

	}

}
