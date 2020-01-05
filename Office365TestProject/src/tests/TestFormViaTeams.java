package tests;



import java.io.IOException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import pages.MainPage;
import pages.Office365Page;
import pages.Settings;
import pages.TeamsChannelPage;
import pages.Settings.ApplicationName;
 
public class TestFormViaTeams extends Settings {

	ExtentHtmlReporter htmlReporter;
	String loginName = "Michael@msglab.tech";
	String password = "Amdocs@123";
	final int lineNumber = 2;
	final String APP_NAME = "Teams";
	int teamNumber = getRandom();
	ExtentTest testLog;
	final String newPlanName = "AUTOMATION_PLAN_NAME_";
	final String formName = "AUTO_TEST_FORM_VIA_TEAMS_";
	
	@Test(priority = 1, groups = { "FormsViaTeams" })
	public void addFormsViaTeams() throws Exception {
		System.out.println("TestFormsViaTeams - > addFormsViaTeams");
		testLog = extent.createTest(getClass().getName());
		String element = "Forms";
		String nameF = formName + getRandom();
		loginAsAmdocsUserSettings(ApplicationName.TEAMS); // Settings.class method
		//loginAsAmdocsUser(APP_NAME);
		testLog.log(Status.INFO, "Loggedin");
		try {
		TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
		teamsPage.clickTeamsTab();
		testLog.log(Status.INFO, "Open Teams");
		Thread.sleep(1000);
		teamsPage.clickAddATabButton();
		testLog.log(Status.INFO, "Click add tab button");
		Thread.sleep(5000);
		teamsPage.chooseElementFromAddATab(element);
		testLog.log(Status.INFO, "Element '"+element+"' has been selected");
		Thread.sleep(3000);
		teamsPage.fillTheFormViaTeams(nameF);
		testLog.log(Status.INFO, "The form is filled");
		if(teamsPage.searchTabByName(nameF)==true) {testLog.pass("The new form has been created and checked successfully! - " + nameF);}
		else {testLog.fail("Error checking form existence! - " + nameF, MediaEntityBuilder.createScreenCaptureFromPath(nameF+".png").build());}
		}
		catch(Exception exc) {System.out.println("Something went wrong! "+exc.toString());}
		//Assert.assertTrue(teamsPage.searchTabByName(nameF));
		
	}
	
	
	
	
	
	  public void loginAsAmdocsUser(String appName) throws InterruptedException,
	  IOException{ testLog.log(Status.INFO, "Login as User"); 
	  MainPage mainPage =  new MainPage(driver); Thread.sleep(2000); mainPage.setLogin(loginName);
	  if(loginName.equals("Michael@msglab.tech")) {mainPage.setPassword(password); mainPage.signInNo();} 
	  testLog.log(Status.INFO,"Open Office 365"); 
	  Office365Page officePage = new Office365Page(driver);
	  System.out.println("OFFICE 365"); Thread.sleep(2000);
	  testLog.log(Status.INFO, "Click Teams link ");
	  officePage.chooseApplication(appName);
	 
									  ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
									  driver.switchTo().window(tabs.get(1));
									  
									  if(appName == "Teams") { TeamsChannelPage teamsPage = new
									  TeamsChannelPage(driver); try { Thread.sleep(8000); } catch
									  (InterruptedException e) {e.printStackTrace(); } System.out.println(appName);
									  teamsPage.clickByLinkWeb(); } try { Thread.sleep(8000); } catch
									  (InterruptedException e) {e.printStackTrace(); }  
									  testLog.log(Status.INFO, "Logged in as User - "+ loginName); 
									  }
}
