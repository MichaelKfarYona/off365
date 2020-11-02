package tests;


import java.io.IOException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import pages.DelvePage;
import pages.DelvePage.LeftMenu;
import pages.OutlookPage;
import pages.Settings;
import pages.Settings.ApplicationName;

public class TestDelve extends Settings{
	ExtentTest testLog = null;
	String SUBJECT = "Test_";
	
	/* TC16 DELVE */
	@Test(enabled = true, priority = 2)
	public void delveAndMembersValidation() throws InterruptedException, IOException {
		System.out.println("TestDelve - > Delve validation");
		boolean res;
		String MAIL_SUBJECT = SUBJECT+ getRandom();
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Start: Delve");
		loginAsAmdocsUserSettings(ApplicationName.DELVE); // Settings.class method
		DelvePage delvePage = new DelvePage(driver);
		delvePage.chooseLeftMenuItem(LeftMenu.ME);
		res = delvePage.checkListFile();
		if (res) {testLog.pass("The list is consistent.");}
		else {testLog.fail("Smthing went wrong....");}
	}

}
