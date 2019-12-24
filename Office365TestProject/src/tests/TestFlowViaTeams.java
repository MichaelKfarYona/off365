package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import Pages.MainPage;
import Pages.Office365Page;

import Pages.Planner;
import Pages.PowerAutomate;
import Pages.Settings;
import Pages.TeamsChannelPage;
import Pages.TeamsChannelPage.FlowNewMenuItem;
import Pages.PowerAutomate.LeftMenuItemPowerAutomate;

public class TestFlowViaTeams extends Settings{
	ExtentHtmlReporter htmlReporter;final String newTeamName = "AUTOMATION_TEST_TEAM_";
	final int lineNumber = 2;String loginName = "Michael@msglab.tech";String password = "Amdocs@123";
	final String ownerName = "Michael Prudnikov";int teamNumber = getRandom();ExtentTest testLog = null;
	final String AUTO_FLOW_NAME = "AUTO_TITLE_VT_";
	final String MAIL_TO = "michael.prudnikov@amdocs.com"; 
	final String SUBJECT = "TEST_AUTOMATION_";
	final String MAIL_BODY = "TEST_VIA_TEAMS_";

	@Test(enabled = true, priority = 0, description = "Create Flow via Teams", groups = { "Flow" })
	public void createNewFlow() throws Exception {
		System.out.println("TestFlowViaTeams->createNewFlow");
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Create new Flow: Started");
		//loginAsAmdocsUser("Teams"); // this.class method
		loginAsAmdocsUserSettings(ApplicationName.TEAMS); // Settings.class method
		TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
		teamsPage.clickTeamsTab();
		String element = "Flow";
		teamsPage.clickAddATabButton();
		teamsPage.chooseElementFromAddATab(element);
		teamsPage.closeFlowMainWindow();
		teamsPage.clickTeamsTab();
		Thread.sleep(1000);
		
		teamsPage.clickAddNewFlow();
		teamsPage.chooseNewFlowType(FlowNewMenuItem.CREATE_FROM_TEMPLATE);
		teamsPage.chooseFlowTemplateByDescription("Post messages");
		Thread.sleep(3000);
		teamsPage.clickContinueButton();
		Thread.sleep(3000);
		List<String> checkList = teamsPage.chooseElementFromDropDownList_ByIndex(1);
		Thread.sleep(2000);
		teamsPage.clickSaveBtnFlow();
		Thread.sleep(2000);
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		  driver.switchTo().window(tabs.get(0));
		  Office365Page officePage = new Office365Page(driver);
		  System.out.println("OFFICE 365"); Thread.sleep(2000);
		  testLog.log(Status.INFO, "Click Teams link ");
		  officePage.chooseApplication("Planner");
		  Thread.sleep(2000);
		  Planner plannerToValidate = new Planner(driver);
		  ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
		  driver.switchTo().window(tabs2.get(2));
		  plannerToValidate.createNewTaskByPlanName(checkList.get(1),checkList.get(0));
		  Thread.sleep(1000);
		  ArrayList<String> tabs3 = new ArrayList<String> (driver.getWindowHandles());
		  driver.switchTo().window(tabs3.get(1));
		  teamsPage = new TeamsChannelPage(driver);
		  teamsPage.clickTeamsTab();
		  
		  teamsPage.chooseTopMenuItem("Posts");
		  
		  if(teamsPage.checkMessageByText(checkList.get(1)) == true) {testLog.pass("Flow works properly!");}
	    	else {testLog.fail("Smthing wrong!..");}
	    	
	    	
		  
		  //switch and //span[@class='tileName' and contains(text(),'AUTOMATION_PLAN....')]
		/*
		 * powerPage.createNewFlow(); String nameAF = AUTO_FLOW_NAME+getRandom();
		 * powerPage.addAutomatedFlow(nameAF); Thread.sleep(3000);
		 * powerPage.mailForm_ChooseAction(MAIL_TO, SUBJECT, MAIL_BODY);
		 * Thread.sleep(2000);
		 * if(powerPage.flowPersistanceCheck(LeftMenuItemPowerAutomate.MY_FLOWS, nameAF)
		 * == true) {testLog.pass("Flow has been created successfully!");} else
		 * {testLog.fail("Something wrong...");}
		 */
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
									  (InterruptedException e) {e.printStackTrace(); }  testLog.log(Status.INFO,
									  "Logged in as User - "+ loginName); }
}
