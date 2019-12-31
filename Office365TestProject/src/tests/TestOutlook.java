package tests;

import java.io.IOException;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import pages.OutlookPage;
import pages.Settings;

public class TestOutlook extends Settings {

	final String RECIPIENT = "michael.prudnikov@amdocs.com";
	final String SUBJECT = "SUBJECT_";
	final String EMAIL_BODY = "This is a test email. You do not need to answer it.";
	ExtentTest testLog = null;

	@Test(enabled = true, priority = 1, groups = { "Outlook" })
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

}
