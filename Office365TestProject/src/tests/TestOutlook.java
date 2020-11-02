package tests;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import pages.DelvePage;
import pages.OutlookPage;
import pages.OutlookPage.Condition;
import pages.OutlookPage.ImportanceLevel;
import pages.OutlookPage.LeftBottomMenuItem;
import pages.OutlookPage.LeftMenuItem;
import pages.OutlookPage.MoveToAction;
import pages.OutlookPage.SelectedAction;
import pages.Settings;

public class TestOutlook extends Settings {

	final String RECIPIENT = "michael.prudnikov@amdocs.com";
	final String SUBJECT = "TEST_SUBJECT_";
	final String EMAIL_BODY = "This is a test email. You do not need to answer it.";
	final String CALENDAR_TITLE ="OUTLOOK_CALENDAR_";
	ExtentTest testLog = null;

	/* TC5 */
	// , groups = { "Outlook" }
	@Test(enabled = true, priority = 2)
	public void sendMessageWeb() throws InterruptedException, IOException {
		System.out.println("TestOutlook - > sendMessageWeb");
		String MAIL_SUBJECT = SUBJECT+ getRandom();
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Start: Outlook");
		loginAsAmdocsUserSettings(ApplicationName.OUTLOOK); // Settings.class method
		OutlookPage outlook = new OutlookPage(driver);
		testLog.log(Status.INFO, "Create new message");
		// outlook.createNewMessage(RECIPIENT, MAIL_SUBJECT, EMAIL_BODY);
		outlook.createNewMessage("prudnikov.michael@aol.com", MAIL_SUBJECT, EMAIL_BODY);
		if (outlook.verificationOfTheSentLetter(MAIL_SUBJECT)) {testLog.pass("The message has been send successfully!");}
		else {testLog.fail("Smthing went wrong....");}
	}

	/* TC3 */
	// , groups = {"Outlook"}
	@Test(enabled=true, priority = 3)
	public void createNewMeetingOutlookWeb() throws InterruptedException, IOException {
		System.out.println("TestOutlook - > createNewMeeting");
		
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Start: Outlook");
		loginAsAmdocsUserSettings(ApplicationName.OUTLOOK); // Settings.class method
		OutlookPage outlook = new OutlookPage(driver);
		String TITLE = CALENDAR_TITLE + getRandom();
		outlook.choosePresentationBottomMenu(LeftBottomMenuItem.CALENDAR);
		outlook.clickNewEvent();
		List<String> listE = new ArrayList();
		//listE.add("Dolphie.lobo@amdocs.com");
		listE.add("michael.prudnikov@amdocs.com");
		outlook.specifyCalendarProperties(TITLE, listE);
		outlook.specifyNewDateAndTimeMeeting("01/06/2020", "16:00", "16:30", true);
		
		outlook.clickSendButton();
		boolean res = outlook.checkCalendar(TITLE);
		if (res==true) {testLog.pass("The event created successfully!");}
		else {testLog.fail("Smthing went wrong.... TestOutlook.java -> createNewMeetingOutlookWeb");}Thread.sleep(1000);
	}
	
	/* TC3n */
	// , groups = {"Outlook"}
	@Test(enabled=true, priority = 3)
	public void createNewTeamsMeetingOutlookWeb() throws InterruptedException, IOException {
		System.out.println("TestOutlook - > createNewMeeting");
		
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Start: Outlook");
		loginAsAmdocsUserSettings(ApplicationName.OUTLOOK); // Settings.class method
		OutlookPage outlook = new OutlookPage(driver);
		String TITLE = CALENDAR_TITLE + getRandom();
		outlook.choosePresentationBottomMenu(LeftBottomMenuItem.CALENDAR);
		outlook.clickNewEvent();
		List<String> listE = new ArrayList();
		//listE.add("Dolphie.lobo@amdocs.com");
		listE.add("asus555post@gmail.com");
		outlook.specifyCalendarProperties(TITLE, listE);
		outlook.specifyNewDateAndTimeMeeting("01/12/2020", "16:00", "16:30", true);
		outlook.teamsMeetingSwitcher(true);
		outlook.clickSendButton();
		boolean res = outlook.checkCalendar(TITLE);
		if (res==true) {testLog.pass("The event created successfully!");}
		else {testLog.fail("Smthing went wrong.... TestOutlook.java -> createNewMeetingOutlookWeb");}Thread.sleep(1000);
	}
	
	/* TC7 */
	// , groups = {"Outlook"}
	@Test(enabled=true, priority = 3)
	public void outlookWebRulesImportance() throws InterruptedException, IOException, AWTException {
		System.out.println("TestOutlook - > Rules");
		String RuleName = "TEST_RULE_"+getRandom();
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Start: Outlook");
		loginAsAmdocsUserSettings(ApplicationName.OUTLOOK); // Settings.class method
		OutlookPage outlook = new OutlookPage(driver);
		outlook.openAllOutlookSettings();
		outlook.openRules();
		System.out.println("Step 1");
		outlook.specifyRuleInfo(RuleName, Condition.IMPORTANCE, ImportanceLevel.HIGH, SelectedAction.PIN_TO_TOP, "yoelap@amdocs.com", "Delete", MoveToAction.ARCHIVE);
		System.out.println("Step 5");
		boolean e = outlook.checkRulesList(RuleName, true);
			//outlook.openRulesWithoutAdding();
		System.out.println("Step 8 "+ e );
		if (e == true) {testLog.pass("The rule created successfully!");}
		else {testLog.fail("Smthing went wrong.... TestOutlook.java -> outlookWebRulesImportance");}Thread.sleep(1000);
	}
	// All in one
	@Test(enabled=true, priority = 3)
	public void outlookWebFromToArchive() throws InterruptedException, IOException, AWTException {
		System.out.println("TestOutlook - > Al in one");
		String RuleName = "TEST_RULE_"+getRandom();
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Start: Outlook");
		loginAsAmdocsUserSettings(ApplicationName.OUTLOOK); // Settings.class method
		OutlookPage outlook = new OutlookPage(driver);
		outlook.openAllOutlookSettings();
		outlook.openRules();
		testLog.log(Status.INFO, "Add rule");
		outlook.specifyRuleInfo(RuleName, Condition.FROM, ImportanceLevel.HIGH, SelectedAction.MOVE_TO, "yoelap@amdocs.com", "Delete", MoveToAction.ARCHIVE);
		outlook.closeSettings();
		testLog.log(Status.INFO, "Create and send new message");
		outlook.createNewMessage("yoelap@amdocs.com", RuleName, EMAIL_BODY);
		outlook.clickByLeftMenuElement(LeftMenuItem.ARCHIVE);
		outlook.refreshPage();
		testLog.log(Status.INFO, "Check if the rule works correctly");
		//outlook.clickByLeftMenuElement(LeftMenuItem.ARCHIVE);
		boolean res = outlook.findInArchive(RuleName);
		
		  if (res==true)
		  {testLog.pass("The rule created and checked successfully!");} else {testLog.
		  fail("Smthing went wrong.... TestOutlook.java -> outlookWebFromToArchive");}
		  Thread.sleep(1000);
		 
	}
	
	/* TC7 - 1 */
	// , groups = {"Outlook"}
	@Test(enabled=true, priority = 3)
	public void outlookWebRulesFrom() throws InterruptedException, IOException, AWTException {
		System.out.println("TestOutlook - > Rules");
		String RuleName = "TEST_RULE_"+getRandom();
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Start: Outlook");
		loginAsAmdocsUserSettings(ApplicationName.OUTLOOK); // Settings.class method
		OutlookPage outlook = new OutlookPage(driver);
		outlook.openAllOutlookSettings();
		outlook.openRules();
		outlook.specifyRuleInfo(RuleName, Condition.FROM, ImportanceLevel.HIGH, SelectedAction.PIN_TO_TOP, "yoelap@amdocs.com", "Delete", MoveToAction.ARCHIVE);
			boolean e = outlook.checkRulesList(RuleName, true);
		if (e==true) {testLog.pass("The rule created successfully!");}
		else {testLog.fail("Smthing went wrong.... TestOutlook.java -> outlookWebRulesFrom");}Thread.sleep(1000);
	}
	
	/* TC16 Delve */ // Look DelvePage
	// , groups = {"Outlook"}
	@Test(enabled=true, priority = 3)
	public void openDelveAndMembersValidation() throws InterruptedException, IOException, AWTException {
		System.out.println("TestOutlook - > Delve");
		String RuleName = "TEST_DELVE_"+getRandom();
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Start: Outlook");
		loginAsAmdocsUserSettings(ApplicationName.DELVE); // Settings.class method
		DelvePage delvePage = new DelvePage(driver);
		//OutlookPage outlook = new OutlookPage(driver);
		
		// moi files v delve
		//div[@class="UnifiedCardPanel-module__compactCard__1tN9y"]
				
		
	}
	@Test(enabled = true, priority=5)
	public void deleteRules() throws InterruptedException, IOException {
		System.out.println("TestOutlook - > Delete all rules");
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Start: Outlook");
		loginAsAmdocsUserSettings(ApplicationName.OUTLOOK); // Settings.class method
		OutlookPage outlook = new OutlookPage(driver);
		testLog.log(Status.INFO, "Open settings");
		outlook.openAllOutlookSettings();
		testLog.log(Status.INFO, "Open rules");
		outlook.openRulesWithoutAdding();
		outlook.deleteAllRules();
		testLog.pass("The rules has been deleted!");
	}
}
