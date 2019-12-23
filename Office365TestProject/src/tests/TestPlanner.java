package tests;



import java.io.IOException;
import java.util.ArrayList;

import org.testng.Assert;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import Pages.MainPage;
import Pages.Office365Page;
import Pages.Planner;
import Pages.Planner.Privacy;
import Pages.Settings;
import Pages.TeamsChannelPage;


public class TestPlanner extends Settings{

	ExtentHtmlReporter htmlReporter;
	final String newTeamName = "AUTOMATION_TEST_TEAM_";
	final String listName = "LIST_NAME_";
	final String taskName = "TASK_NAME_";
	final int lineNumber = 2;
	String loginName = "Michael@msglab.tech"; String password = "Amdocs@123";
	int teamNumber = getRandom();
	ExtentTest testLog = null;
	final String newPlanName = "AUTOMATION_PLAN_NAME_";
	
	@Test(priority = 1, groups = { "Planner" })
	public void creationAndVerificationOfNewPlan() throws Exception {
		testLog = extent.createTest(getClass().getName());
    	testLog.log(Status.INFO, "Start Planner app");
    	try {
    	loginAsAmdocsUser("Planner");
    	String myParamPlannerName = newPlanName+getRandom();
    	Thread.sleep(1000);
    	Planner plannerPage = new Planner(driver);
    	plannerPage.createANewPlan(myParamPlannerName);
    	plannerPage.privacyOption(Privacy.PUBLIC);
    	plannerPage.clickCreatePlan();
    	testLog.log(Status.INFO, "Plan has been created!");
    	plannerPage.returnToTheMainPlannerPage();
    	//Assert.assertTrue(plannerPage.verificationOfTheExistenceOfThePlan(myParamPlannerName)); 
    	
    	String testString = plannerPage.choosePlanFromTheList(myParamPlannerName);Thread.sleep(3000);
    	System.out.println(" ******* "+testString);
    	plannerPage.specifyTaskName("Test_Name_");Thread.sleep(1000);
    	plannerPage.specifyAssignName("Steve");Thread.sleep(1000);
    	testLog.pass("Verification of the existence of the plan was successful! Task assigned!");
    	}
    	catch(Exception e) {testLog.fail("Something went wrong! "+e.toString());}
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

