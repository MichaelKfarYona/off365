package tests;

import java.io.IOException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import pages.OutlookPage;
import pages.Settings;
import pages.Settings.ApplicationName;
import pages.SharePointPage;

public class TestSharePoint extends Settings{
	
	ExtentTest testLog = null;

	@Test(enabled = true, priority = 0, groups = { "SharePoint" })
	public void sendMessageWeb() throws InterruptedException, IOException {
		System.out.println("TestOutlook - > sendMessageWeb");
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Start: Outlook");
		loginAsAmdocsUserSettings(ApplicationName.SHAREPOINT); // Settings.class method
		
		SharePointPage sharePoint = new SharePointPage(driver);
		sharePoint.searchInSharePoint("AUTOMATION_TEST_TEAM_109");
		
		//testLog.log(Status.INFO, "Create new message");
		
		
			
	}

}
