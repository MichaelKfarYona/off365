package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import Pages.MainPage;
import Pages.Office365Page;
import Pages.Settings;
import Pages.TeamsChannelPage;
import Pages.TeamsChannelPage.KindOfTeam;
import Pages.TeamsChannelPage.LeftMenuTeams;
import Pages.TeamsChannelPage.MessageTypeInConversationsTeamsTab;
import Pages.TeamsChannelPage.TeamsMenuItemPopUp;
import Pages.TeamsChannelPage.UserStatus;

public class TestTeamsChannelPage extends Settings {

	public enum LeftMenuItem{
		OUTLOOK, ONEDRIVE, WORD, EXCEL, POWERPOINT, ONENOTE, SHAREPOINT,TEAMS, YAMMER, DYNAMICS_365, POWER_AUTOMATE, FORMS, TO_DO, PLANNER,DELVE
	}
	ExtentHtmlReporter htmlReporter;final String newTeamName = "AUTOMATION_TEST_TEAM_";
	final int lineNumber = 2;String loginName = "Michael@msglab.tech";String password = "Amdocs@123";
	final String ownerName = "Michael Prudnikov";int teamNumber = getRandom();ExtentTest testLog = null;
	final String meetingTitle = "AUTOCREATED_TITLE_";
	
	//String[] invitePeople;
//*********************************************************************************************************
//************************************* Create a new Team Online ********************************************
//*********************************************************************************************************
	@Test(enabled = true, priority = 0, description = "Create a new Team Online", groups = { "Teams" })
	public void createNewTeam() throws Exception {
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Create new team: Started");
		loginAsAmdocsUser("Teams");
		TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
		teamsPage.clickTeamsTab();
		Thread.sleep(3000);
		teamsPage.createTeam(); Thread.sleep(2000);
		teamsPage.buildATeamFromScratch(KindOfTeam.PUBLIC, newTeamName, teamNumber);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		testLog.pass("The New Team has been created! You can run 'checkNewTeam' or/and 'deleteNewTeam' method!");
		Thread.sleep(3000);
	}

//*********************************************************************************************************
//************************************* Checking the existence of the team *********************************
//*********************************************************************************************************
	@Test(enabled = true, priority = 0, description = "Check New team creation", groups = { "Teams" })
	public void checkNewTeam() throws Exception {
		// ExtentTest testLog = extent.createTest(getClass().getName());
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Checking the existence of the team");
		loginAsAmdocsUser("Teams");
		testLog.log(Status.INFO, "Logged in to the Teams");
		TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
		teamsPage.clickTeamsTab();
		teamsPage.teamCheck(newTeamName, teamNumber);
		boolean element = teamsPage.elementIsNotPresent("//div[@class='filter-list-no-results']");
		if(element==true) {testLog.pass("The team " + newTeamName + teamNumber + " is on the list.");}
		else {testLog.fail("The team " + newTeamName + teamNumber + " is not on the list.");}
		Thread.sleep(2000);
		// extent.flush();
	}
	
	/*********************************************************************************************************
	********************************************* Deleting the team ******************************************
	*********************************************************************************************************/
		@Test(enabled = true, priority = 4, description = "Delete the team", groups = { "Teams" })
		public void deleteNewTeam() throws Exception {
			testLog = extent.createTest(getClass().getName());
			testLog.log(Status.INFO, "Checking the existence of the team");
			loginAsAmdocsUser("Teams");
			testLog.log(Status.INFO, "Logged in to the Teams");
			TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
			teamsPage.clickTeamsTab();
			String teamToDelete = newTeamName+teamNumber;
			//teamsPage.teamCheck(newTeamName, teamNumber);
			teamsPage.openTeamSettingsDot(teamToDelete, TeamsMenuItemPopUp.DELETE_THE_TEAM);
			
			boolean element = teamsPage.elementIsNotPresent("//div[@class='filter-list-no-results']");
			if(element==true) {testLog.pass("The team " + newTeamName + teamNumber + " is on the list.");}
			else {testLog.fail("The team " + newTeamName + teamNumber + " is not on the list.");}
			Thread.sleep(2000);
			// extent.flush();
		}

	
	/*****************************************************************
	***************** * Chat attachment. OneDrive * ***************** dodelat
	 *****************************************************************/
		@Test(enabled = false, priority = 1, description = "Attachment_OdeDrive", groups = { "Teams" })
		public void chatAttachmentOneDrive() throws Exception {
			testLog = extent.createTest(getClass().getName());
			testLog.log(Status.INFO, "Attachment in chat");
			loginAsAmdocsUser("Teams");
			TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
			teamsPage.clickChatTab();
			teamsPage.chooseAttachment_OneDrive_Chat();
			Thread.sleep(3000);
			
		}
	
	/*************************************************** 
	***************** * Change status * ***************** dodelat
	 ***************************************************/
		@Test(enabled = false, priority = 1, description = "Change status", groups = { "Teams" })
		public void setStatusInTeams() throws Exception {
			testLog = extent.createTest(getClass().getName());
			testLog.log(Status.INFO, "Change status in Teams");
			loginAsAmdocsUser("Teams");
			TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
			teamsPage.clickTeamsTab();
			String statusName = teamsPage.chooseStatus(UserStatus.APPEAR_AWAY);
			if(statusName.equals("Appear away") == true) {testLog.pass("Status changed successfully!");}
			else {testLog.fail("Status not changed.");}
			
		}
	
//*********************************************************************************************************
//************************************ Assign role validation **********************************************
//*********************************************************************************************************
	@Test(enabled = true, priority = 1, description = "Teams : Assign role validation", groups = { "Teams" })
	public void assignRoleValidation() throws Exception {
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Assign role validation");
		loginAsAmdocsUser("Teams");
		TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
		teamsPage.clickTeamsTab();
		teamsPage.openTeamMembersSettingsDot(newTeamName+teamNumber);
		teamsPage.collapseExpandMembersAndGuests();
		testLog.log(Status.INFO, "Owner users check");
		Map<String, String> mapOwnersUsers = new HashMap<String, String>(teamsPage.checkOwnerOrMember());
		for (Map.Entry<String, String> entry : mapOwnersUsers.entrySet()) {
			Assert.assertEquals(entry.getKey(), "Michael Prudnikov");
			Assert.assertEquals(entry.getValue(), "Owner");
			// System.out.println(entry.getKey() + " - " + entry.getValue());
		}
		testLog.log(Status.INFO, "Guest users check");
		Map<String, String> mapGuestUsers = new HashMap<String, String>(teamsPage.checkGuestUsers());
		for (Map.Entry<String, String> entry : mapGuestUsers.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
		testLog.pass("Role check completed successfully");Thread.sleep(1000);
	}
	//*************************************************************************************************
	// Create new Meeting. invitePeople - names and mails of those who need to be added to the meeting.
	//*************************************************************************************************
	@Test(enabled = true, priority = 2, description = "Teams : Create new meeting", groups = { "Teams" })
	public void addNewMeeting() throws InterruptedException, IOException {
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Assign role validation");
		String[] invitePeople = {"prudnikov.michael@aol.com"}; // You can add as many users as you like "Dolphie.Lobo@amdocs.com"
		loginAsAmdocsUser("Teams");
		TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
		teamsPage.clickByLeftMenuItem(LeftMenuTeams.CALENDAR);
		String newMeetingTitle = meetingTitle+getRandom();
		Assert.assertEquals(newMeetingTitle, teamsPage.createNewMeetingInCalendar(newMeetingTitle, invitePeople)); 
		testLog.pass("New meeting has been created: "+ newMeetingTitle);
		Thread.sleep(3000);
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
									 
	/*
	 * //******************************************FLOW
	 * TAB***********************************************
	 * 
	 * @Test(priority=2, description = "Add Flow tab") public void addNewFlowTab()
	 * throws Exception, IOException { String element = "Flow";
	 * loginAsAmdocsUser("Teams"); TeamsChannelPage teamsPage = new
	 * TeamsChannelPage(driver); teamsPage.clickTeamsTab(); Thread.sleep(1000);
	 * teamsPage.clickAddATabButton(); Thread.sleep(5000);
	 * teamsPage.chooseElementFromAddATab(element);
	 * 
	 * }
	 */
	  
	  
	/*****************************************************************************************
	 ********************************** Add A New Flow ***************************************
	 *****************************************************************************************/
		@Test(enabled = false, priority = 2, description = "Add new tab", groups = { "Teams" })
		public void addNewFlow() throws Exception, IOException {
			testLog = extent.createTest(getClass().getName());
			String element = "Flow";
			loginAsAmdocsUser("Teams");
			TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
			teamsPage.clickTeamsTab();
			teamsPage.clickAddATabButton();
			teamsPage.chooseElementFromAddATab(element);
			Thread.sleep(3000);
			driver.switchTo().activeElement();
			
			teamsPage.flowFirstScreenSave();
			
			System.out.println("TITLE = "+driver.getTitle());
			teamsPage.specifyFlowInfo("Flow");
			
		/*
		 * teamsPage.specifyWebSiteNameAndURL("TestName_" + teamNumber, myURL);
		 * Thread.sleep(3000); teamsPage.clickByLeftMenuItem(LeftMenuTeams.TEAMS);
		 * String res = teamsPage.checkNewTabAdded("TestName_" + teamNumber);
		 * if(res.equals("TestName_" + teamNumber) &&
		 * teamsPage.checkNewTabAddedViaContent(myURL)==true)
		 * {testLog.pass("New website "+myURL+" has been added: TestName_"+
		 * teamNumber);} else {testLog.fail("The problem of creating a web site!");}
		 */Thread.sleep(3000);
		}
	/******************************************************************************************************/
	/********************************************Add A New Web Tab*****************************************/
	/******************************************************************************************************/
	@Test(enabled = false, priority = 2, description = "Add new tab", groups = { "Teams" })
	public void addNewTabWeb() throws Exception, IOException {
		testLog = extent.createTest(getClass().getName());
		String element = "Website";
		String myURL = "https://mvnrepository.com/";
		loginAsAmdocsUser("Teams");
		TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
		teamsPage.clickTeamsTab();
		teamsPage.clickAddATabButton();
		teamsPage.chooseElementFromAddATab(element);
		teamsPage.specifyWebSiteNameAndURL("TestName_" + teamNumber, myURL);
		Thread.sleep(3000);
		teamsPage.clickByLeftMenuItem(LeftMenuTeams.TEAMS);
		String res = teamsPage.checkNewTabAdded("TestName_" + teamNumber);
		if(res.equals("TestName_" + teamNumber) &&  teamsPage.checkNewTabAddedViaContent(myURL)==true) {testLog.pass("New website "+myURL+" has been added: TestName_"+ teamNumber);}
		else {testLog.fail("The problem of creating a web site!");}
		Thread.sleep(1000);
	}
	/*************************************************************************************************/
	/***************************************** Giphy message *****************************************/
	/*************************************************************************************************/
	@Test(enabled = true, priority = 4, description = "Giphy sending")
	public void sendEmojiGiphyStickerToTheChat() throws Exception {
		testLog = extent.createTest(getClass().getName());
		
		loginAsAmdocsUser("Teams");
		TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
		teamsPage.clickTeamsTab();
		teamsPage.sendMessageInTeamsConversations(MessageTypeInConversationsTeamsTab.EMOJI);
		Thread.sleep(3000);
	}
}
