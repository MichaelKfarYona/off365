package tests;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Pages.MainPage;
import Pages.Office365Page;
import Pages.Settings;
import Pages.TeamsChannelPage;
import Pages.Settings.ApplicationName;
 
public class TestPlannerViaTeams extends Settings{
	final String APP_NAME = "Teams";
	final String PLANNER = "Planner";
	final String NAME_YOUR_PLAN_ = "AUTO_TEST_VIA_TEAMS_";
	ExtentTest testLog = null;
	String loginName = "Michael@msglab.tech";
	String password = "Amdocs@123";
	//ExtentTest logger;
	@Test(priority = 1, groups = { "PlannerViaTeams" })
	public void addPlannerViaTeams() throws Exception {
		System.out.println("TestPlannerViaTeams -> addPlannerViaTeams");
		testLog = extent.createTest(getClass().getName());
    	testLog.log(Status.INFO, "Start Browser");
    	loginAsAmdocsUserSettings(ApplicationName.TEAMS); // Settings.class method
		//loginAsAmdocsUser(APP_NAME);
		TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
		String plannerNameToTab = NAME_YOUR_PLAN_+getRandom();
		testLog.log(Status.INFO, "Teams is opened");
		teamsPage.clickTeamsTab();
		Thread.sleep(1000);
		teamsPage.clickForPlannerViaTeams();
		//teamsPage.clickAddATabButton();
		Thread.sleep(5000);
		teamsPage.chooseElementFromAddATab(PLANNER);
		testLog.log(Status.INFO, "Application "+PLANNER+" selected");
		teamsPage.fillPlanerName(plannerNameToTab);
		testLog.log(Status.INFO, "Name specified: "+plannerNameToTab);
		Thread.sleep(3000);
		if(teamsPage.checkNewPlannerViaTeams(plannerNameToTab) == true) {testLog.pass("The new plan has been created and checked successfully!");}
		else {testLog.fail("The new plan has not been created!");}
		//Assert.assertTrue(teamsPage.checkNewPlannerViaTeams(plannerNameToTab));
		
	}
	
	
	
	
	
	
	

	  public void loginAsAmdocsUser(String appName) throws InterruptedException,
	  IOException{ testLog.log(Status.INFO, "Login as User"); MainPage mainPage =
	  new MainPage(driver); Thread.sleep(2000); mainPage.setLogin(loginName);
	  if(loginName.equals("Michael@msglab.tech")) {mainPage.setPassword(password); mainPage.signInNo();} 
	  testLog.log(Status.INFO,
	 "Open Office 365"); Office365Page officePage = new Office365Page(driver);
	  System.out.println("OFFICE 365"); Thread.sleep(2000);
	  testLog.log(Status.INFO, "Click Teams link ");
	  officePage.chooseApplication(appName);
	 
									  ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
									  driver.switchTo().window(tabs.get(1));
									  
									  if(appName == "Teams") { TeamsChannelPage teamsPage = new
									  TeamsChannelPage(driver); try { Thread.sleep(8000); } catch
									  (InterruptedException e) {e.printStackTrace(); } System.out.println(appName);
									  teamsPage.clickByLinkWeb(); } try { Thread.sleep(8000); } catch
									  (InterruptedException e) {e.printStackTrace(); }  testLog.log(Status.INFO,
									  "Logged in as User - "+ loginName); }
}
