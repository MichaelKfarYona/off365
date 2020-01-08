package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import pages.OutlookPage;
import pages.OutlookPage.LeftBottomMenuItem;
import pages.OutlookPage.LeftMenuItem;
import pages.Settings;

public class TestOutlook extends Settings {

	final String RECIPIENT = "michael.prudnikov@amdocs.com";
	final String SUBJECT = "SUBJECT_";
	final String EMAIL_BODY = "This is a test email. You do not need to answer it.";
	final String CALENDAR_TITLE ="OUTLOOK_CALENDAR_";
	ExtentTest testLog = null;

	@Test(enabled = true, priority = 2, groups = { "Outlook" })
	public void sendMessageWeb() throws InterruptedException, IOException {
		System.out.println("TestOutlook - > sendMessageWeb");
		String MAIL_SUBJECT = SUBJECT+ getRandom();
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Start: Outlook");
		loginAsAmdocsUserSettings(ApplicationName.OUTLOOK); // Settings.class method
		OutlookPage outlook = new OutlookPage(driver);
		testLog.log(Status.INFO, "Create new message");
		outlook.createNewMessage(RECIPIENT, MAIL_SUBJECT, EMAIL_BODY);
		if (outlook.verificationOfTheSentLetter(MAIL_SUBJECT)) {testLog.pass("The message has been send successfully!");}
		else {testLog.fail("Smthing went wrong....");}
	}

	@Test(enabled=true, priority = 3, groups = {"Outlook"})
	public void createNewMeeting() throws InterruptedException, IOException {
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
		outlook.specifyNewDateAndTimeMeeting("05/03/2020", "16:00", "16:30");
		
		outlook.clickSendButton();
		boolean res = outlook.checkCalendar(TITLE);
		if (res==true) {testLog.pass("The event created successfully!");}
		else {testLog.fail("Smthing went wrong....");}Thread.sleep(1000);
	}
}
