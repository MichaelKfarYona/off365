package pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import org.testng.Assert;

import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import pages.Forms.FormType;
import pages.TeamsChannelPage.KindOfTeam;
import pages.ToDo.LeftMenuItems;

public class TestClass extends Settings {
	
	public static void main(String[]args) throws AWTException {
		// 1920x1080
		
		    Robot bot = new Robot();
		    bot.mouseMove(1650, 1052);    
		    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		    bot.delay(3000);
		
	}
	/*
	 * ExtentHtmlReporter htmlReporter; final String newTeamName =
	 * "AUTOMATION_TEST_TEAM_"; final String listName = "LIST_NAME_"; final String
	 * taskName = "TASK_NAME_"; final int lineNumber = 2; String loginName =
	 * "Michael@msglab.tech"; String password = "Amdocs@123"; int teamNumber =
	 * getRandom(); ExtentTest testLog = null;
	 * 
	 * String loginName = "michapru@amdocs.com"; String password = "Amdocs123";
	 * 
	 * //*************************************Create a new Team
	 * Online***************************************
	 * 
	 * @Test(priority = 0, description="Create a new Team Online") public void
	 * createNewTeam() throws Exception { testLog =
	 * extent.createTest(getClass().getName()); testLog.log(Status.INFO, "START");
	 * loginAsAmdocsUser("Teams"); TeamsChannelPage teamsPage = new
	 * TeamsChannelPage(driver); //driver.manage().timeouts().implicitlyWait(5,
	 * TimeUnit.SECONDS); teamsPage.clickTeamsTab(); teamsPage.createTeam();
	 * Thread.sleep(2000); teamsPage.buildATeamFromScratch(KindOfTeam.PUBLIC,
	 * newTeamName, teamNumber); driver.manage().timeouts().implicitlyWait(2,
	 * TimeUnit.SECONDS); testLog.pass("The New Team has been created!");
	 * testLog.log(Status.INFO, "THE END"); // driver.close();driver.quit(); }
	 * //*************************************Checking the existence of the
	 * team***************************************
	 * 
	 * @Test(priority = 1, description="Check New team creation") public void
	 * checkNewTeam() throws Exception { //ExtentTest testLog =
	 * extent.createTest(getClass().getName()); testLog.log(Status.INFO,
	 * "Checking the existence of the team"); loginAsAmdocsUser("Teams");
	 * TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
	 * teamsPage.clickTeamsTab(); teamsPage.teamCheck(newTeamName, teamNumber);
	 * boolean element
	 * =teamsPage.elementIsNotPresent("//div[@class='filter-list-no-results']");
	 * Assert.assertTrue(element); testLog.log(Status.INFO,
	 * "The team is on the list.");
	 * testLog.pass("The team "+newTeamName+teamNumber+" is on the list.");
	 * Thread.sleep(2000); } //******************************************FLOW
	 * TAB***********************************************
	 * 
	 * @Test(priority=2, description = "Add Flow tab") public void addNewFlowTab()
	 * throws Exception, IOException { String element = "Flow";
	 * loginAsAmdocsUser("Teams"); TeamsChannelPage teamsPage = new
	 * TeamsChannelPage(driver); teamsPage.clickTeamsTab(); Thread.sleep(1000);
	 * teamsPage.clickAddATabButton(); Thread.sleep(5000);
	 * teamsPage.chooseElementFromAddATab(element);
	 * 
	 * } //*******************************************Add A New
	 * Tab************************************************
	 * 
	 * @Test(priority = 2, description="Add new tab") public void addNewTabWeb()
	 * throws Exception, IOException { String element = "Website";
	 * loginAsAmdocsUser("Teams"); TeamsChannelPage teamsPage = new
	 * TeamsChannelPage(driver); teamsPage.clickTeamsTab(); Thread.sleep(1000);
	 * teamsPage.clickAddATabButton(); Thread.sleep(5000);
	 * teamsPage.chooseElementFromAddATab(element); Thread.sleep(5000);
	 * teamsPage.specifyWebSiteNameAndURL("TestName_"+teamNumber,
	 * "https://mvnrepository.com/"); Thread.sleep(3000); teamsPage.clickTeamsTab();
	 * String res = teamsPage.checkNewTabAdded("TestName_"+teamNumber);
	 * Assert.assertEquals(res, "TestName_"+teamNumber);
	 * 
	 * Thread.sleep(3000); } //***********************************LogIn as
	 * User************************************* public void
	 * loginAsAmdocsUser(String appName) throws InterruptedException, IOException{
	 * //ExtentTest test = extent.createTest(getClass().getName()); MainPage
	 * mainPage = new MainPage(driver); Thread.sleep(2000);
	 * mainPage.setLogin(loginName); mainPage.setPassword(password);
	 * mainPage.signInNo();
	 * 
	 * Office365Page officePage = new Office365Page(driver);
	 * System.out.println("OFFICE 365"); Thread.sleep(2000);
	 * officePage.chooseApplication(appName); //Thread.sleep(2000);
	 * 
	 *//************************
		 * Switch between tabs *
		 ************************/
	/*
	 * ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	 * driver.switchTo().window(tabs.get(1));
	 * 
	 * if(appName == "Teams") { TeamsChannelPage teamsPage = new
	 * TeamsChannelPage(driver); try { Thread.sleep(8000); } catch
	 * (InterruptedException e) {e.printStackTrace(); } System.out.println(appName);
	 * teamsPage.clickByLinkWeb(); } try { Thread.sleep(8000); } catch
	 * (InterruptedException e) {e.printStackTrace(); } // test.log(Status.INFO,
	 * "Logged in as User - "+ loginName);
	 * 
	 * // test.pass("Logged as User"); //
	 * test.addScreenCaptureFromPath("C:\\REPORTS\\test12.png");
	 * 
	 * AmdocsLoginPage amdocsPage = new AmdocsLoginPage(driver);
	 * amdocsPage.setPasswordAndSignIn("Amdocs@123");
	 * 
	 * }
	 * //***************************************************************************
	 * ****************************** //************************************ To Do
	 * main functionality checking **********************************
	 * //***************************************************************************
	 * ******************************
	 * 
	 * @Test(priority = 4) public void toDoApp() throws InterruptedException,
	 * IOException { testLog = extent.createTest(getClass().getName());
	 * testLog.log(Status.INFO, "START ToDo app"); loginAsAmdocsUser("To Do");
	 * Thread.sleep(3000); ToDo toDoPage = new ToDo(driver);
	 * toDoPage.createNewToDoList(listName+getRandom());
	 * toDoPage.addTaskToTheList(taskName, lineNumber);
	 * Assert.assertEquals(toDoPage.getTaskItemBody(), lineNumber);
	 * testLog.pass("List and tasks has been created successfully!");
	 * 
	 * toDoPage.chooseleftItem(LeftMenuItems.MY_DAY); //
	 * toDoPage.addTaskToTheList(taskName, lineNumber); //
	 * testLog.pass("My Day Tasks has been created successfully!"); }
	 * 
	 * //***************************************************************************
	 * ***************************** //***************************************
	 * Forms: Create a new form ***************************************
	 * //***************************************************************************
	 * *****************************
	 * 
	 * @Test(priority = 4) public void formsMainFunctionality() throws
	 * InterruptedException, IOException { testLog =
	 * extent.createTest(getClass().getName()); testLog.log(Status.INFO,
	 * "Start Forms app"); loginAsAmdocsUser("Forms"); String myParam =
	 * "Test_Form_Name_"+getRandom(); Thread.sleep(2000); Forms formsPage = new
	 * Forms(driver); formsPage.createNewFormEnteranceWindow(myParam);
	 * formsPage.addNewForm(FormType.CHOICE); Thread.sleep(3000);
	 * formsPage.fillTheChoiceForm("My_Mega_Question", 2); Thread.sleep(3000);
	 * //formsPage.addNewQuestion();
	 * 
	 * 
	 * formsPage.goToFormsMainPage(); //formsPage.deleteOptions();
	 * Thread.sleep(5000); Assert.assertEquals(formsPage.searchFormByName(myParam),
	 * false);
	 * 
	 * }
	 *//*****************
		 * Random method *
		 *****************//*
							 * 
							 * public int getRandom() {Random rand = new Random(); int value =
							 * rand.nextInt(1000000); return value;}
							 */
}