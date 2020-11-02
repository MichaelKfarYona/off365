package tests;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import pages.Forms;
import pages.MainPage;
import pages.Office365Page;
import pages.Settings;
import pages.TeamsChannelPage;
import pages.Forms.FormType;
import pages.Settings.ApplicationName;


public class TestForms extends Settings{
 

	ExtentHtmlReporter htmlReporter;
	final String newTeamName = "AUTOMATION_TEST_TEAM_";
	final String listName = "LIST_NAME_";
	final String taskName = "TASK_NAME_";
	final int lineNumber = 2;
	String loginName = "Michapru@amdocs.com"; String password = "Ahmshere577561)";
	// String loginName = "Michael@msglab.tech"; String password = "Ahmshere577561^";
	int teamNumber = getRandom();
	ExtentTest testLogger = null;
// //a[@aria-label="Explore all your apps"]
	  public void loginAsAmdocsUser(String appName) throws InterruptedException, IOException{
		  
	    	testLogger.log(Status.INFO, "Login as User");
	        MainPage mainPage = new MainPage(driver);Thread.sleep(2000);
	        mainPage.setLogin(loginName); 
	        if(loginName.equals("Michael@msglab.tech")) {mainPage.setPassword(password); mainPage.signInNo();}
	        
	        testLogger.log(Status.INFO, "Open Office 365");
	        Office365Page officePage = new Office365Page(driver);
	        System.out.println("OFFICE 365");
	        Thread.sleep(2000);
	        testLogger.log(Status.INFO, "Click App link ");
	        officePage.chooseApplication(appName);
	        /************************
	         * Switch between tabs *
	         ************************/
	        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	       driver.switchTo().window(tabs.get(1));
	       
	       if(appName == "Teams") {
	  	   TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
	  	   try { Thread.sleep(8000);
	       } catch (InterruptedException e) {e.printStackTrace();        }
	       System.out.println(appName);      
	       teamsPage.clickByLinkWeb();
	       }
	        try { Thread.sleep(8000);
	        } catch (InterruptedException e) {e.printStackTrace();        }
	       // test.log(Status.INFO, "Logged in as User - "+ loginName);
	    }
	//********************************************************************************************************
	//*************************************** Forms: Create a new form ***************************************
	//********************************************************************************************************
	    @Test(enabled = true, priority = 1, groups = { "Forms" })
	    public void formsMainFunctionality() throws InterruptedException, IOException {
	    	System.out.println("TestForms->formsMainFunctionality");
	    	testLogger = extent.createTest(getClass().getName());
	    	testLogger.log(Status.INFO, "Start Forms app");
	    	try {loginAsAmdocsUserProduction(ApplicationName.FORMS, 1);
	    		//loginAsAmdocsUserSettings(ApplicationName.FORMS); // Settings.class method
	    		
	    	//loginAsAmdocsUser("Forms");
	    	String myParam = "Test_Form_Name_"+getRandom();
	    	Thread.sleep(2000);
	    	Forms formsPage = new Forms(driver);
	    	formsPage.createNewFormEnteranceWindow(myParam);
	    	formsPage.addNewForm(FormType.CHOICE);
	    	Thread.sleep(3000);
	    	formsPage.fillTheChoiceForm("My_Mega_Question", 2);
	    	Thread.sleep(3000);
	    	//formsPage.addNewQuestion();
	    	formsPage.goToFormsMainPage();
	    	testLogger.log(Status.INFO,"Forms has been created!");
	    	//formsPage.deleteOptions();
	    	Thread.sleep(5000);
	    	if(formsPage.searchFormByName(myParam) == true) {testLogger.pass("The Form '"+myParam+"' is exists!");}
	    	else {testLogger.fail("The Form '"+myParam+"' is not exists!", MediaEntityBuilder.createScreenCaptureFromPath(myParam+".png").build());}
	    	}
	    	catch(Exception exception) {testLogger.fatal("ERROR! " + exception.toString());}
	    	//Assert.assertEquals(formsPage.searchFormByName(myParam), true); 
	    }

	//********************************************************************************************************
	//*************************************** Forms: Create a new Quiz ***************************************
	//********************************************************************************************************
		    @Test(enabled = true, priority = 1, groups = { "Forms" })
		    public void createQuiz() throws InterruptedException, IOException {
		    	System.out.println("TestForms -> createQuiz");
		    	testLogger = extent.createTest(getClass().getName());
		    	testLogger.log(Status.INFO, "Start Forms app");
		    	try {
		    		//loginAsAmdocsUserSettings(ApplicationName.FORMS); // Settings.class method
		    	loginAsAmdocsUser("Forms");
		    	String myParam = "Test_Form_Name_"+getRandom();
		    	Thread.sleep(2000);
		    	Forms formsPage = new Forms(driver);
		    	
		    	formsPage.closeNewFormEnteranceWindow();
		    	formsPage.clickByNewQuiz();
		    	
		    	formsPage.addNewForm(FormType.CHOICE);
		    	Thread.sleep(3000);
		    	formsPage.fillTheChoiceForm("Your question could be here ...", 2);
		    	Thread.sleep(3000);
		    	//formsPage.addNewQuestion();
		    	formsPage.goToFormsMainPage();
		    	testLogger.log(Status.INFO,"Quiz has been created!");
		    	//formsPage.deleteOptions();
		    	Thread.sleep(5000);
		    	if(formsPage.searchFormByName(myParam) == true) {testLogger.pass("The Quiz '"+myParam+"' is exists!");}
		    	else {testLogger.fail("The Form '"+myParam+"' is not exists!", MediaEntityBuilder.createScreenCaptureFromPath("Quiz_"+myParam+".png").build());}
		    	}
		    	catch(Exception exception) {testLogger.fatal("ERROR! " + exception.toString());}
		    	//Assert.assertEquals(formsPage.searchFormByName(myParam), true); 
		    }
	    }

