package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import Pages.MainPage;
import Pages.Office365Page;
import Pages.PowerAutomate;
import Pages.PowerAutomate.LeftMenuItemPowerAutomate;
import Pages.Settings.ApplicationName;
import Pages.Settings;
import Pages.TeamsChannelPage;
import Pages.TeamsChannelPage.KindOfTeam;

public class TestPowerAutomate extends Settings{

	ExtentHtmlReporter htmlReporter;final String newTeamName = "AUTOMATION_TEST_TEAM_";
	String loginName = "Michael@msglab.tech";String password = "Amdocs@123";
	final String ownerName = "Michael Prudnikov";int teamNumber = getRandom();ExtentTest testLog = null;
	final String AUTO_FLOW_NAME = "AUTO_TITLE_";
	final String MAIL_TO = "michael.prudnikov@amdocs.com"; 
	final String SUBJECT = "TEST_AUTOMATION_";
	final String MAIL_BODY = "TEST_";
	
	
	@Test(enabled = true, priority = 0, description = "Create a new Flow", groups = { "Flow" })
	public void createNewFlow() throws Exception {
		System.out.println("TestPowerAutomate -> createNewFlow");
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Create new team: Started");
		loginAsAmdocsUserSettings(ApplicationName.POWER_AUTOMATE); // Settings.class method
		//loginAsAmdocsUser("Power Automate");
		PowerAutomate powerPage = new PowerAutomate(driver);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		powerPage.createNewFlow();
		String nameAF = AUTO_FLOW_NAME+getRandom();
		powerPage.addAutomatedFlow(nameAF);
		Thread.sleep(3000);
		powerPage.mailForm_ChooseAction(MAIL_TO, SUBJECT, MAIL_BODY);
		Thread.sleep(2000);
		if(powerPage.flowPersistanceCheck(LeftMenuItemPowerAutomate.MY_FLOWS, nameAF) == true) {testLog.pass("Flow has been created successfully!");}
		else {testLog.fail("Something wrong...");}
		
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
