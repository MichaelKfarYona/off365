package tests;
import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import desktop.ConfigOneNote;
import pages.OneNote;
import pages.OneNote.MenuItemRightClick;
import pages.Settings;
import pages.Office365Page.ApplicationName;

public class TestOneNote extends Settings{
	ExtentTest testLog = null;
	String notebookName;
	
	
	/* TC15 Create online / Regression Suite for track 1_2_3 */
	@Test(priority = 3)
	//@Parameters({"NoteName"})
	public void oneNoteMainFunctionslity(ITestContext context) throws InterruptedException, IOException, AWTException {
	System.out.println("OneNote -> creationAndVerification. oneNoteMainFunctionslity");
	testLog = extent.createTest(getClass().getName());
	testLog.log(Status.INFO, "Start OneNote");
	// test env
	//loginAsAmdocsUserSettingsOneNote(ApplicationName.ONENOTE); // Settings.class method
	// prod env
	loginAsAmdocsUserSettingsOneNoteProduction(ApplicationName.ONENOTE); // Settings.class method
	Thread.sleep(1000);
	OneNote oneNote = new OneNote(driver);
	ConfigOneNote con = new ConfigOneNote();
	con.addContext(context);
	String cont = context.getAttribute("note").toString();
	System.out.println("***** "+ context.getAttribute("note"));
	notebookName = oneNote.createNewNotebook(cont);
	switchNewOpenedTab(1);
	Thread.sleep(3000);
	oneNote.checkNewNotebook();
	driver.close();
	switchNewOpenedTab(0);
	oneNote.pageRefresh();
	
	//oneNote.clickTopLineMenu("My notebooks");
	boolean res = oneNote.checkList(notebookName);
	Thread.sleep(1000);
	if(res) {testLog.pass("Notebook has been created!");}
	else {testLog.fail("Notebook has not been created...");}
	}
	
	/* TC10 Sync online */
	@Test(priority = 4)
	//@Parameters({"NoteName"})
	public void oneNoteSyncFunctionality(ITestContext context) throws InterruptedException, IOException, AWTException {
	System.out.println("OneNote -> oneNoteSyncFunctionality");
	testLog = extent.createTest(getClass().getName());
	testLog.log(Status.INFO, "Start OneNote");
	// test env
	//loginAsAmdocsUserSettingsOneNote(ApplicationName.ONENOTE); // Settings.class method
	// prod env
	loginAsAmdocsUserSettingsOneNoteProduction(ApplicationName.ONENOTE); // Settings.class method
	Thread.sleep(1000);
	OneNote oneNote = new OneNote(driver);
	testLog.log(Status.INFO, "Open desk app");
	oneNote.openNoteInDesktopApp(notebookName, MenuItemRightClick.OPEN_IN_DESKTOP_APP);
	oneNote.openOneNote2016Popup(); 
	Thread.sleep(3000);
	oneNote.openOneNote2016Popup();
	//Thread.sleep(2000);
	//oneNote.closeActiveWindow();
	testLog.pass("Notebook synchronized!");
	Thread.sleep(5000);
	}
	
}
