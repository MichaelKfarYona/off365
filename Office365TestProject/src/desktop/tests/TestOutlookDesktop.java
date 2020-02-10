package desktop.tests;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import desktop.Config;
import desktop.pages.OutlookDesktop;
import desktop.pages.OutlookDesktop.CancelRecurrence;
import desktop.pages.OutlookDesktop.LeftBottomMenu;
import desktop.pages.OutlookDesktop.MeetingInThePast;
import desktop.pages.OutlookDesktop.OpenRecurrenceType;
import desktop.pages.OutlookDesktop.RecurrencePattern;

public class TestOutlookDesktop extends Config {
	ExtentHtmlReporter htmlReporter;
	ExtentTest testLogOutlook = null;

	String meetingName = null;
	String recuringMeetingName = null;
	String createdRecMeeting = null;
	String newID = null;
	String recurringMeetingInThePast = "8256903#";
	boolean meetingInTheCalendar, delMeeting;
	List<String>meetingNameAndId;
	String createdRecMeetingID = null;
	/* TC#1 - Schedule a meeting */
	@Test(enabled = true, priority = 0, groups = { "DesktopOutlook" })
	public void createNewAmdocsMeeting() throws InterruptedException, AWTException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(1000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Create New Amdocs Meeting");
		outlookDesk.clickNewAmdocsMeeting();
		//ArrayList<String> meetingName = outlookDesk.specifyMeetingProperties("yoelap@amdocs.com");
		String meetingNameNew = outlookDesk.specifyMeetingPropertiesReturnName("yoelap@amdocs.com");
		System.out.println("Meeting name: " + meetingNameNew);
		outlookDesk.btnSendMeeting_ByRobot();
		outlookDesk.exitAndSendLaterOrNot();
		if (meetingNameNew.length() > 0) {
			testLogOutlook.pass("Test Passed");
		} else {
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
		outlookDesk.specifyNewDay(-1);
		res = outlookDesk.sendOrNotMeeting(MeetingInThePast.SEND_ANYWAY);
System.out.println("Result : " + res);
		System.out.println("Meeting name: " + meetingName);
		if (res) {
			testLogOutlook.pass("Test Passed! ");
		} else {
			testLogOutlook.fail("Test failed...");
		}
	}
	
// Create new Rec meeting and try to connect via Avaya and Polycom
	/* TC#3 - Schedule Reoccurring meeting */
	@Test(enabled = true, priority = 4, groups = { "DesktopOutlook" })
	public void createReoccurringMeeting() throws InterruptedException, AWTException {
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
		isExist = outlookDesk.addRecurrence(RecurrencePattern.DAILY, true, 1, 0, 0);
		System.out.println("Meeting name : " + recuringMeetingName.get(0));
		createdRecMeeting = recuringMeetingName.get(0);
		createdRecMeetingID = recuringMeetingName.get(1);
		if (isExist) {
			testLogOutlook.pass("Test Passed");
		} else {
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
		testLogOutlook.log(Status.INFO, "Try to connect. Number : "+_TestEnvAvayaPhoneNumber);
		OutlookDesktop od = new OutlookDesktop(driver);
		setRes = od.callByNumber(_TestEnvAvayaPhoneNumber, createdRecMeetingID);
		// setRes = od.callByNumber(_TestEnvAvayaPhoneNumber, "8259662#");
		
		if(setRes) {testLogOutlook.pass("Test Passed! | Connect is available through the new ID");}
		else {testLogOutlook.fail("Test failed... : ");}
		
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
		else {testLogOutlook.fail("Test failed... : ");}
		
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
		else {testLogOutlook.fail("Test failed... : ");}
		
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
		else {testLogOutlook.fail("Test failed... : ");}
		
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
		else {testLogOutlook.fail("Test failed... : ");}
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
		} else {
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
		isExist = outlookDesk.addRecurrence(RecurrencePattern.DAILY, false, 1, 0, 0);

		System.out.println("Meeting name : " + recuringMeetingName.get(0));
		if (isExist) {
			testLogOutlook.pass("Test Passed");
		} else {
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
		} else {
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
		} else {
			testLogOutlook.fail("Failed");
		}

	}
	
	/* TC#6 - Update meeting date\time */
	@Test(enabled = true, priority = 1, groups = { "DesktopOutlook" })
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
		change = outlookDesk.updateMeetingDate("AUTOTEST_082187", 1);
		//change = outlookDesk.updateMeetingDate(meetingName, 1);
		if (change) {
			testLogOutlook.pass("Test Passed");
		} else {
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
		passFail = outlookDesk.updateAllRMeetings("ReoccurringTest", RecurrencePattern.DAILY, 1, 5);
		if(passFail) {testLogOutlook.pass("Test Passed");}
		else {testLogOutlook.fail("Test fail...");}
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
		passFail = outlookDesk.updateReoccurringMeeting("ReoccurringTest", OpenRecurrenceType.OPEN_OCCURENCE, 2, 5);
		if (passFail) {
			testLogOutlook.pass("Test Passed");
		} else {
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
		res = outlookDesk.updateMeetingParticipants("AUTOTEST_37630", "michael prudnikov", 1);
		//res = outlookDesk.updateMeetingParticipants(meetingName, "michael prudnikov", 1);
		
		if(res) {testLogOutlook.pass("Test Passed");}
		else{testLogOutlook.fail("Test failed...");}
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
		forward = outlookDesk.clickForward("TestF2", "nocsupportsvc@amdocs.com");
		//forward = outlookDesk.clickForward(meetingName, "nocsupportsvc@amdocs.com");
		if (forward) {
			testLogOutlook.pass("Test Passed");
		} else {
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
		outlookDesk.clickByMeetingName("AUTOTEST_55766");
		outlookDesk.reminderDismissAll(); // Na tot sluchay esli poyavlyaetsa popup
		testLogOutlook.log(Status.INFO, "Join the meeting via skype.");
		outlookDesk.clickToJoinSkypeMeeting();
		res = outlookDesk.runAndVerificationSkypeMeeting("AUTOTEST_55766");
		// outlookDesk.runAndVerificationSkypeMeeting("AUTOTEST_24190");
		outlookDesk.closeSkypeWindows();
		Thread.sleep(1000);
		outlookDesk.clickOKButton();
		if (res) {
			testLogOutlook.pass("Test Passed!");
		} else {
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
		outlookDesk.clickByMeetingName(meetingName);
		testLogOutlook.log(Status.INFO, "Open all access numbers list.");
		outlookDesk.openAccessNumbers();
		outlookDesk.allowAccess();
		outlookDesk.checkCountry("Israel");
	}

	/* TC#14 - Cancel meeting */
	@Test(enabled = true, priority = 3)
	public void meetingCancellation() throws InterruptedException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		Thread.sleep(2000);
		testLogOutlook.log(Status.INFO, "Open Calendar");
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		testLogOutlook.log(Status.INFO, "Meeting cancellation");
		// delMeeting = outlookDesk.amdocsMeetingCancellation(meetingName);
		delMeeting = outlookDesk.amdocsMeetingCancellation("AUTOTEST_061345");
		if (delMeeting == true) {
			testLogOutlook.pass("Deleted");
		} else {
			testLogOutlook.fail("Test failed...");
		}
		Thread.sleep(2000);
	}
	/* TC#15 - Skype meeting. Call number _TestEnvAvayaPhoneNumber*/
	@Test(enabled = true, priority = 2)
	@Parameters({"_TestEnvAvayaPhoneNumber"})
	public void checkSkypeMeetingCallNumber(String _TestEnvAvayaPhoneNumber) throws IOException, InterruptedException, AWTException {
		testLogOutlook = extent.createTest(getClass().getName());
		testLogOutlook.log(Status.INFO, "Start Desktop Outlook");
		OutlookDesktop outlookDesk = new OutlookDesktop(driver);
		outlookDesk.chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		System.out.println("+++++++ "+_TestEnvAvayaPhoneNumber);
		outlookDesk.callFromSkype(_TestEnvAvayaPhoneNumber, "AUTOTEST_082187");
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
		} else {
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
		} else {
			testLogOutlook.fail("Test failed... : Connected to a meeting in the past.");
		}
	}
	// Update old meeting and try to connect (18, 24, 25, 26, 27,28)
	/* TC#18 Update meeting from the past and try to connect. NOT RECURRENCE */
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
		 res = outlookDesk.updateOneMeetingDateAndTime(_MeetingInThePast, 0, 30);
		System.out.println("STEP 5");
		newID = res.get(1);
		if (!(res.get(0).equals(res.get(1)))) {testLogOutlook.pass("Test Passed! | Meeting ID has been changed!");}
		else {testLogOutlook.fail("Test failed... : ");}
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
		else {testLogOutlook.fail("Test failed... : ");}
		
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
		else {testLogOutlook.fail("Test failed... : ");}
		
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
		else {testLogOutlook.fail("Test failed... : ");}
		
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
		else {testLogOutlook.fail("Test failed... : ");}
		
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
		else {testLogOutlook.fail("Test failed... : ");}
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
		} else {
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
		else {testLogOutlook.fail("Test failed... : ");}
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
		else {testLogOutlook.fail("Test failed... : ");}
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
		else {testLogOutlook.fail("Test failed... : ");}
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
		else {testLogOutlook.fail("Test failed... : ");}
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
		else {testLogOutlook.fail("Test failed... : ");}
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
		else {testLogOutlook.fail("Test failed... : ");}
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
		else {testLogOutlook.fail("Test failed... : ");}
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
		else {testLogOutlook.fail("Test failed... : ");}
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
		else {testLogOutlook.fail("Test failed... : ");}
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
		else {testLogOutlook.fail("Test failed... : ");}
	}
	
	/*
	+ _ProductionAvayaPhoneNumber_1
	+ _ProductionAvayaPhoneNumber_2
	 _ProductionPolycomPhoneNumber
	+ _TestEnvAvayaPhoneNumber
	+ _TestEnvPolycomPhoneNumber 
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
