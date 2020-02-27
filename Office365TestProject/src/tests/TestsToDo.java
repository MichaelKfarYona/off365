package tests;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import pages.MainPage;
import pages.Office365Page;
import pages.Settings;
import pages.TeamsChannelPage;
import pages.ToDo;
import pages.Settings.ApplicationName;
import pages.ToDo.LeftMenuItems;

public class TestsToDo extends Settings{
	ExtentHtmlReporter htmlReporter;
	final String newTeamName = "AUTOMATION_TEST_TEAM_";
	final String listName = "LIST_NAME_";
	final String taskName = "TASK_NAME_";
	final int lineNumber = 2;
	String loginName = "Michael@msglab.tech"; String password = "Ahmshere577561!";
	int teamNumber = getRandom();
	ExtentTest testLog = null;
	
 
	 @Test(enabled = true, priority = 4, groups = { "ToDo" })
	    public void toDoApp() throws InterruptedException, IOException {
		 System.out.println("TestsToDo - > toDoApp");
	    	testLog = extent.createTest(getClass().getName());
	    	testLog.log(Status.INFO, "Start: ToDo app");
	    	try {
	    		loginAsAmdocsUserSettings(ApplicationName.TODO); // Settings.class method
	    	//loginAsAmdocsUser("To Do");
	    	Thread.sleep(2000);
	    	ToDo toDoPage = new ToDo(driver);
	    	Thread.sleep(7000);
	    	toDoPage.createNewToDoList(listName+getRandom());
	    	toDoPage.addTaskToTheList(taskName, lineNumber);
	    	Assert.assertEquals(toDoPage.getTaskItemBody(), lineNumber);
	    	testLog.log(Status.INFO, "List and tasks has been created!");
	    	Thread.sleep(4000);
	    	
	    	toDoPage.chooseleftItem(LeftMenuItems.MY_DAY);
	    	testLog.pass("List and tasks has been created successfully!");}
	    	catch(Exception e) {testLog.fail("Something went wrong: "+e.toString());}
	    	
	    	// toDoPage.addTaskToTheList(taskName, lineNumber);
	    	// testLog.pass("My Day Tasks has been created successfully!");
	    }
	 
	//***********************************LogIn as User*************************************
	

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

