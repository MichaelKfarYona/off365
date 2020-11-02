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

import pages.FileFromMainPage;
import pages.MainPage;
import pages.Office365Page;
import pages.OutlookPage;
import pages.Settings;
import pages.TeamsChannelPage;
import pages.Settings.ApplicationName;
import pages.TeamsChannelPage.KindOfTeam;
import pages.TeamsChannelPage.LeftMenuTeams;
import pages.TeamsChannelPage.MessageTypeInConversationsTeamsTab;
import pages.TeamsChannelPage.TeamsMenuItemPopUp;
import pages.TeamsChannelPage.UserStatus;

public class TestTeamsChannelPage extends Settings {

	public enum LeftMenuItem{
		OUTLOOK, ONEDRIVE, WORD, EXCEL, POWERPOINT, ONENOTE, SHAREPOINT,TEAMS, YAMMER, DYNAMICS_365, POWER_AUTOMATE, FORMS, TO_DO, PLANNER,DELVE
	}
	ExtentHtmlReporter htmlReporter;final String newTeamName = "AUTOMATION_TEST_TEAM_";
	final int lineNumber = 2;String loginName = "Michael@msglab.tech";String password = "Ahmshere577561!";
	final String ownerName = "Michael Prudnikov";int teamNumber = getRandom();ExtentTest testLog = null;
	final String meetingTitle = "AUTOCREATED_TITLE_";
	
	
	@Test(enabled = true, priority = 0, description = "Share files via chat")
	public void shareFilesViaChatTeams() throws Exception {
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Create new team: started");
		//loginAsAmdocsUserSettingsM(ApplicationName.TEAMS); // Settings.class method
		//loginAsAmdocsUserProduction(ApplicationName.TEAMS,1);
		loginAsAmdocsUser("Teams");
		testLog.log(Status.INFO, "Open Teams page");
		TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
		teamsPage.clickByLinkWeb();
		teamsPage.clickTeamsTab();
		Thread.sleep(1000);
		//teamsPage.createTeam(); Thread.sleep(2000);
		teamsPage.clickByLeftMenuItem(LeftMenuTeams.CHAT);
		testLog.log(Status.INFO, "Open chat tab");
		teamsPage.buildATeamFromScratch(KindOfTeam.PUBLIC, newTeamName, teamNumber);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		testLog.pass("The New Team has been created!");
		Thread.sleep(1000);
	}
	
	
	//String[] invitePeople;
//*********************************************************************************************************
//************************************* Create a new Team Online ********************************************
//*********************************************************************************************************
	//, groups = { "Teams" }
	// as Michael!
	@Test(enabled = true, priority = 0, description = "Create a new Team Online")
	public void createNewTeam() throws Exception {
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Create new team: started");
		//loginAsAmdocsUserSettingsM(ApplicationName.TEAMS); // Settings.class method
		//loginAsAmdocsUserProduction(ApplicationName.TEAMS,1);
		loginAsAmdocsUser("Teams");
		testLog.log(Status.INFO, "Open Teams page");
		TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
		teamsPage.clickByLinkWeb();
		teamsPage.clickTeamsTab();
		Thread.sleep(1000);
		//teamsPage.createTeam(); Thread.sleep(2000);
		teamsPage.clickJoinOrCreateATeam();Thread.sleep(2000);
		testLog.log(Status.INFO, "Attempt to create a team");
		teamsPage.buildATeamFromScratch(KindOfTeam.PUBLIC, newTeamName, teamNumber);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		testLog.pass("The New Team has been created!");
		Thread.sleep(1000);
	}
 // , groups = { "Teams" }
	@Test(enabled = false, priority = 0, description = "Create a new Team Online")
	public void createNewPrivateTeam() throws Exception {
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Create new team: started");
		loginAsAmdocsUserSettings(ApplicationName.TEAMS); // Settings.class method
		//loginAsAmdocsUser("Teams");
		testLog.log(Status.INFO, "Open Teams page");
		TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
		teamsPage.clickTeamsTab();
		Thread.sleep(1000);
		teamsPage.createTeam(); Thread.sleep(2000);
		testLog.log(Status.INFO, "Attempt to create a team");
		
		teamsPage.buildATeamFromScratch(KindOfTeam.PRIVATE, newTeamName, teamNumber);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		testLog.pass("The New Team has been created! You can run 'checkNewTeam' or/and 'deleteNewTeam' method!");
		Thread.sleep(3000);
	}
//*********************************************************************************************************
//************************************* Checking the existence of the team *********************************
//*********************************************************************************************************
	// , groups = { "Teams" }
	@Test(enabled = true, priority = 0, description = "Check New team creation")
	public void checkNewTeam() throws Exception {
		// ExtentTest testLog = extent.createTest(getClass().getName());
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Checking the existence of the team");
		//loginAsAmdocsUserSettingsM(ApplicationName.TEAMS); // Settings.class method
		loginAsAmdocsUser("Teams");
		testLog.log(Status.INFO, "Open Teams page");
		TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
		teamsPage.clickTeamsTab();
		testLog.log(Status.INFO, "Check the existence of the group.");
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
	// , groups = { "Teams" }
		@Test(enabled = true, priority = 5, description = "Delete the team")
		public void deleteNewTeam() throws Exception {
			testLog = extent.createTest(getClass().getName());
			testLog.log(Status.INFO, "Checking the existence of the team");
			//loginAsAmdocsUserSettingsM(ApplicationName.TEAMS); // Settings.class method
			loginAsAmdocsUser("Teams");
			testLog.log(Status.INFO, "Logged in to the Teams");
			TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
			teamsPage.clickTeamsTab();
			String teamToDelete = newTeamName+teamNumber;
			//teamsPage.teamCheck(newTeamName, teamNumber);
			testLog.log(Status.INFO, "Deleting the Team");
			teamsPage.openTeamSettingsDot(teamToDelete, TeamsMenuItemPopUp.DELETE_THE_TEAM);
			
			boolean element = teamsPage.elementIsNotPresent("//div[@class='filter-list-no-results']");
			if(element==true) {testLog.pass("The team " + newTeamName + teamNumber + " is on the list.");}
			else {testLog.fail("The team " + newTeamName + teamNumber + " is not on the list.");}
			Thread.sleep(2000);
			// extent.flush();
		}
		
// ***************** 
		
		/* TC19 Autosave option . any doc*/  
		@Test(enabled = true, priority = 1, description = "Teams : Doc autosave option.")
		public void autosaveOption() throws Exception {
			testLog = extent.createTest(getClass().getName());
			testLog.log(Status.INFO, "Autosave option");
			// Through the main Office page
			loginAsAmdocsUserSettingsWithoutApp();
			Office365Page officePage = new Office365Page(driver);
			officePage.openFileOnMainPage("test");			
			switchNewOpenedTab(1);
			testLog.log(Status.INFO, "Switch to filepage.");
				FileFromMainPage filePage = new FileFromMainPage(driver);
			filePage.cleanAndPasteCanvas("Test");
			driver.close();
			switchNewOpenedTab(0);
			officePage.openFileOnMainPage("test");
			testLog.log(Status.INFO, "Check autosave mode.");
			switchNewOpenedTab(1);
			FileFromMainPage filePageSaved = new FileFromMainPage(driver);
			boolean res = filePageSaved.checkCanvasAutosave("Test");
			if(res) {testLog.pass("Autosaved");}
			else {testLog.fail("File not saved...");}
			Thread.sleep(3000);
			System.out.println("Result : "+res);
			
		/*//Through the teams
		 * loginAsAmdocsUserSettings(ApplicationName.TEAMS); // Settings.class method
		 * //loginAsAmdocsUser("Teams"); testLog.log(Status.INFO, "Open Teams page");
		 * TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
		 * teamsPage.clickTeamsTab();
		 * teamsPage.openTeamSettingsDotAndCheckFile("TestOwners", "test.docx");
		 */
		}
		
	
	/*****************************************************************
	***************** * Chat attachment. OneDrive * *****************      													dodelat
	 *****************************************************************/
		@Test(enabled = false, priority = 1, description = "Attachment_OdeDrive", groups = { "Teams" })
		public void chatAttachmentOneDrive() throws Exception {
			testLog = extent.createTest(getClass().getName());
			testLog.log(Status.INFO, "Attachment in chat");
			loginAsAmdocsUserSettings(ApplicationName.TEAMS); // Settings.class method
			//loginAsAmdocsUser("Teams");
			TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
			teamsPage.clickChatTab();
			teamsPage.chooseAttachment_OneDrive_Chat();
			Thread.sleep(3000);
			
		}
	
	/*************************************************** 
	***************** * Change status * ***************** 																	dodelat
	 ***************************************************/
		@Test(enabled = false, priority = 1, description = "Change status", groups = { "Teams" })
		public void setStatusInTeams() throws Exception {
			testLog = extent.createTest(getClass().getName());
			testLog.log(Status.INFO, "Change status in Teams");
			loginAsAmdocsUserSettings(ApplicationName.TEAMS); // Settings.class method
			//loginAsAmdocsUser("Teams");
			testLog.log(Status.INFO, "Open Teams page");
			TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
			teamsPage.clickTeamsTab();
			testLog.log(Status.INFO, "Change status");
			String statusName = teamsPage.chooseStatus(UserStatus.APPEAR_AWAY);
			if(statusName.equals("Appear away") == true) {testLog.pass("Status changed successfully!");}
			else {testLog.fail("Status not changed.");}
			
		}

	
	
//**************************************************************************************************************************
//******* TC23	 Assign role validation. This test needs to be run after the team is created. In the same test group	 *******
//**************************************************************************************************************************
	@Test(enabled = true, priority = 1, description = "Teams : Assign role validation")
	public void assignRoleValidation() throws Exception {
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Assign role validation");
		//loginAsAmdocsUserSettings(ApplicationName.TEAMS); // Settings.class method
		loginAsAmdocsUser("Teams");
		testLog.log(Status.INFO, "Open Teams page");
		TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
		teamsPage.clickTeamsTab();
		testLog.log(Status.INFO, "Open TeamMembers menu");
		teamsPage.openTeamMembersSettingsDot("AUTOMATION_TEST_TEAM_109");
		//teamsPage.openTeamMembersSettingsDot(newTeamName+teamNumber);
		teamsPage.collapseExpandMembersAndGuests();
		testLog.log(Status.INFO, "Owner users check");
		//teamsPage.checkOwnerOrMember2();
		teamsPage.listMembersAndGuestUsers();
		/*
		Map<String, String> mapOwnersUsers = new HashMap<String, String>(teamsPage.checkOwnerOrMember2());
		for (Map.Entry<String, String> entry : mapOwnersUsers.entrySet()) {
			Assert.assertEquals(entry.getKey(), "Michael Prudnikov"); 
			Assert.assertEquals(entry.getValue(), "Owner"); 
			// System.out.println(entry.getKey() + " - " + entry.getValue());
		}
		*/
		testLog.log(Status.INFO, "Guest users check");
		HashMap<String, String> resultMapGuest = new HashMap<String, String>(teamsPage.listMembersAndGuestUsers());
		//resultMapGuest = teamsPage.listMembersAndGuestUsers();
		if(resultMapGuest.isEmpty()) {testLog.log(Status.INFO, "Guests are absent.");}
		else {testLog.log(Status.INFO, "Guests are present.");}
		testLog.pass("Role check completed successfully");Thread.sleep(1000);
	}
	//*************************************************************************************************
	// Create new Meeting. invitePeople - names and mails of those who need to be added to the meeting.
	//*************************************************************************************************
	//  groups = { "Teams" }
	@Test(enabled = true, priority = 2, description = "Teams : Create new meeting")
	public void addNewMeeting() throws InterruptedException, IOException {
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Assign role validation");
		String[] invitePeople = {"michael.prudnikov@amdocs.com"}; // You can add as many users as you like "Dolphie.Lobo@amdocs.com" "prudnikov.michael@aol.com", "michael.prudnikov@amdocs.com"
		loginAsAmdocsUserProduction(ApplicationName.TEAMS,1);
		
		//loginAsAmdocsUserSettings(ApplicationName.TEAMS); // Settings.class method
		//loginAsAmdocsUser("Teams");
		TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
		
		Thread.sleep(3000);
		teamsPage.chooseWebApp();
		teamsPage.clickByLeftMenuItem(LeftMenuTeams.CALENDAR);
		String newMeetingTitle = meetingTitle+getRandom();
		teamsPage.createNewMeetingInCalendar(newMeetingTitle, invitePeople);
		teamsPage.openWeek();
		boolean res = teamsPage.checkMeeting(newMeetingTitle);
		//Assert.assertEquals(newMeetingTitle, teamsPage.createNewMeetingInCalendar(newMeetingTitle, invitePeople)); 
		if(res) {
		testLog.pass("New meeting has been created: "+ newMeetingTitle+ " *** "+res);
		}
		else {testLog.fail("Meeting not found: "+ newMeetingTitle+ " *** "+res);}
		
		// check email in the Outlook
			  
	}
	
// Michael Lab
	  public void loginAsAmdocsUser(String appName) throws InterruptedException,
	  IOException{ testLog.log(Status.INFO, "Login as User"); 
	  MainPage mainPage =  new MainPage(driver); Thread.sleep(2000); mainPage.setLogin(loginName);
	  if(loginName.equals("Michael@msglab.tech")) {mainPage.setPassword(password); mainPage.signInNo();}
	  testLog.log(Status.INFO,"Open Office 365"); 
	  Office365Page officePage = new Office365Page(driver);
	  System.out.println("OFFICE 365"); Thread.sleep(2000);
	  testLog.log(Status.INFO, "Click Teams link ");
	  officePage.OpenAllApps();
	  officePage.chooseApplication(appName);
	 
									  ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
									  driver.switchTo().window(tabs.get(1));
									  
									  if(appName == "Teams") { TeamsChannelPage teamsPage = new
									  TeamsChannelPage(driver); try { Thread.sleep(8000); } catch
									  (InterruptedException e) {e.printStackTrace(); } System.out.println(appName);
									  teamsPage.clickByLinkWeb(); } try { Thread.sleep(8000); } catch
									  (InterruptedException e) {e.printStackTrace(); }  testLog.log(Status.INFO,
									  "Logged in as User - "+ loginName); }
									 
	
	  
	  
	/*****************************************************************************************
	 ********************************** Add A New Flow ***************************************
	 *****************************************************************************************/
	  // , groups = { "Teams" }
		@Test(enabled = true, priority = 2, description = "Add new tab")
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
		 */Thread.sleep(2000);
		}
	/******************************************************************************************************/
	/********************************************Add A New Web Tab*****************************************/
	/******************************************************************************************************/
		//, groups = { "Teams" }
	@Test(enabled = true, priority = 2, description = "Add new tab")
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
		teamsPage.sendMessageInTeamsConversations(MessageTypeInConversationsTeamsTab.GIPHY);
		Thread.sleep(3000);
	}
	
	/*************************************************************************************************/
	/***************************************** Giphy message *****************************************/
	/*************************************************************************************************/
	@Test(enabled = true, priority = 4, description = "Emoji sending")
	public void sendEmojiStickerToTheChat() throws Exception {
		testLog = extent.createTest(getClass().getName());
		
		loginAsAmdocsUser("Teams");
		TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
		teamsPage.clickTeamsTab();
		teamsPage.sendMessageInTeamsConversations(MessageTypeInConversationsTeamsTab.EMOJI);
		Thread.sleep(3000);
	}
	public void switchTab(int tabNumber) {
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
		  driver.switchTo().window(tabs2.get(tabNumber));
	}
}
