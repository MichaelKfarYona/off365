package desktop.tests;

import java.awt.AWTException;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import desktop.ConfigOneNote;
import pages.OneNote;

public class TestOneNoteSync extends ConfigOneNote{
	ExtentTest testLog = null;
	
	/* TC9 TC13 Sync with Local */
	@Test (enabled = true, priority = 5)
	public void checkOneNoteSyncLocal(ITestContext context) throws AWTException, InterruptedException {
		
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Start OneNote");
		OneNote on = new OneNote(driver);
		String str = context.getAttribute("note").toString();
		System.out.println("***** "+str);
		testLog.log(Status.INFO, "Check OneNote sync...");
		boolean res = on.checkSyncOneNote(str);
		on.closeActiveWindow();
		if(!res) {testLog.pass("Notebook has been created and synchronized!");}
		else {testLog.fail("Not sync...");}
	}
	
	/* TC15 Open a OneNote document in local */
	@Test (enabled = true, priority = 5)
	public void openOneNoteDocLocal(ITestContext context) throws AWTException, InterruptedException {
		
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Start OneNote");
		OneNote on = new OneNote(driver);
		String str = context.getAttribute("note").toString();
		System.out.println("***** "+str);
		//boolean res = on.openDocument("TEST_NOTEBOOK_83022");
		testLog.log(Status.INFO, "Open doc in local.");
		boolean res = on.openDocument(str);
		on.closeActiveWindow();
		if(res) {testLog.pass("Notebook has been created and synchronized!");}
		else {testLog.fail("Not sync...");}
	}
	
	
}
