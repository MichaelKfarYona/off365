package tests;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import pages.ExcelWorkboobPage;
import pages.MainPage;
import pages.Office365Page;
import pages.OneDrive;
import pages.Settings;
import pages.TeamsChannelPage;
import pages.WordDocumentPage;
import pages.ExcelWorkboobPage.TopMenuTab;
import pages.OneDrive.LeftMenuItem;
import pages.OneDrive.NewMenuItem;
import pages.Settings.ApplicationName;

public class TestOneDrive extends Settings{

	ExtentHtmlReporter htmlReporter;
	final String FOLDER_NAME = "AUTOMATION_TEST_FOLDER_";
	final String LINKNAME = "TEST_LINK_";
	final int lineNumber = 2;
	String wordDocumentTitle, excelDocumentTitle = null;
	String loginName = "Michael@msglab.tech";String password = "Amdocs@123";
	//String loginName = "yoelap@amdocs.com"; String password = "Newpass1!";
	final String ownerName = "Michael Prudnikov";
	int teamNumber = getRandom();
	ExtentTest testLog = null;
 
	//********** Add new folder **********
	@Test(enabled = true, priority = 0, description = "Add new Folder", groups = { "OneDrive" })
	public void addNewFolderOneDrive() throws Exception {
		System.out.println("TestOneDrive - > addNewFolderOneDrive");
		testLog = extent.createTest(getClass().getName());
		testLog.log(Status.INFO, "Create new folder: Started");
		loginAsAmdocsUserSettings(ApplicationName.ONEDRIVE); // Settings.class method
		//loginAsAmdocsUser("OneDrive");
		OneDrive oneDrive = new OneDrive(driver);
		String tempFolderName = FOLDER_NAME+getRandom();
		oneDrive.addItemOneDrive_NewMenu(NewMenuItem.FOLDER);
		oneDrive.specifyNewFolderName(tempFolderName);
		testLog.log(Status.INFO, "Folder has been created : " + tempFolderName);
		if(oneDrive.checkingElementExistence(tempFolderName)== true) {testLog.pass("The directory exists in the list.");}
		else {testLog.fail("The directory not exists in the list!");}
		//Assert.assertEquals(oneDrive.checkingElementExistence(tempFolderName), true); 
		
		//oneDrive.clickByLeftMenuItem(LeftMenuItem.RECYCLE_BIN);
	Thread.sleep(2000);
	}
	//***********************************************
	//***************** Add new link ****************
	//***********************************************
	@Test(enabled = true, priority = 0, description = "Add new Link", groups = { "OneDrive" })
	public void createNewLink() throws InterruptedException, IOException {
		System.out.println("TestOneDrive - > createNewLink");
	testLog = extent.createTest(getClass().getName());
	testLog.log(Status.INFO, "Create new link: Started");
	//String myString = randomStringGneration();
	String add = "NEW";
	String webSite = "www.google.com";
	loginAsAmdocsUser("OneDrive");
	OneDrive oneDrive = new OneDrive(driver);
	oneDrive.addItemOneDrive_NewMenu(NewMenuItem.LINK);
	String result = oneDrive.specifyNewLink(webSite, add);
	Thread.sleep(1000);
	oneDrive.checkingElementExistence(result);
	Thread.sleep(2000);
	}
	
	//********************************************************
	//***************** Add new Word document ****************
	//********************************************************
	@Test(enabled = true, priority = 0, description = "Add new Word document", groups = { "OneDrive" })
	public void createNewWordDocument() throws InterruptedException, IOException {
	testLog = extent.createTest(getClass().getName());
	testLog.log(Status.INFO, "Create new Word document: Started");
	loginAsAmdocsUser("OneDrive");
	OneDrive oneDrive = new OneDrive(driver);
	oneDrive.addItemOneDrive_NewMenu(NewMenuItem.WORD_DOCUMENT);
	Thread.sleep(5000);
		
		 ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		 driver.switchTo().window(tabs.get(2));
		 WebElement myFrame = driver.findElement(By.xpath("//iframe[@name='WebApplicationFrame']"));
		 driver.switchTo().frame(myFrame);
		
	  WordDocumentPage wdp = new WordDocumentPage(driver);
	  wordDocumentTitle = driver.getTitle();
	  System.out.println("+++++++ "+wordDocumentTitle);
	  wdp.specifyText();
	  driver.close();
	  Thread.sleep(2000);	
	  ArrayList<String> parrentTab = new ArrayList<String> (driver.getWindowHandles());
		 driver.switchTo().window(parrentTab.get(1));
		 Thread.sleep(2000);
		 if(oneDrive.checkingElementExistence(wordDocumentTitle) == true) {testLog.pass("The Word document exists in the list.");}
		 else {testLog.fail("The Word document not exists in the list.");}
		 //Assert.assertEquals(oneDrive.checkingElementExistence(wordDocumentTitle), true); 
		 Thread.sleep(2000);
	}
	
	//*********************************************************
	//***************** Add new Excel workbook ****************
	//*********************************************************
	@Test(enabled = false, priority = 0, description = "Add new Excel workbook", groups = { "OneDrive" })
	public void createNewExcelWorkbook() throws InterruptedException, IOException {
	testLog = extent.createTest(getClass().getName());
	testLog.log(Status.INFO, "Create new Excel workbook: Started");
	loginAsAmdocsUser("OneDrive");
	OneDrive oneDrive = new OneDrive(driver);
	oneDrive.addItemOneDrive_NewMenu(NewMenuItem.EXCEL_WORKBOOK);
	Thread.sleep(5000);
		 ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		 driver.switchTo().window(tabs.get(2));
		 WebElement myFrame = driver.findElement(By.xpath("//iframe[@name='WebApplicationFrame']"));
		 driver.switchTo().frame(myFrame);
		 ExcelWorkboobPage excelDocument = new ExcelWorkboobPage(driver);
		 excelDocumentTitle = driver.getTitle();
		 System.out.println("Excel document title: " + excelDocumentTitle);
		 //excelDocument.specifyStringToTheLine("TEST_MESSAGE", 3);
	  excelDocument.chooseTabInTheDocument(TopMenuTab.INSERT);
	  excelDocument.chooseTabInTheDocument(TopMenuTab.DATA);
	  excelDocument.chooseTabInTheDocument(TopMenuTab.REVIEW);
	  excelDocument.chooseTabInTheDocument(TopMenuTab.HOME);
	  
	  driver.close();
	  Thread.sleep(1000);	
	  ArrayList<String> parrentTab = new ArrayList<String> (driver.getWindowHandles());
	  driver.switchTo().window(parrentTab.get(1));Thread.sleep(2000);
	  if(oneDrive.checkingElementExistence(excelDocumentTitle) == true) {testLog.pass("The Word document exists in the list.");}
	  else {testLog.fail("The Word document not exists in the list.");}
	  //Assert.assertEquals(oneDrive.checkingElementExistence(excelDocumentTitle), true); 
	  Thread.sleep(2000);
	}
	
	
	
	
	
	
	
	  public void loginAsAmdocsUser(String appName) throws InterruptedException,
	  IOException{ testLog.log(Status.INFO, "Login as User"); 
	  MainPage mainPage =
	  new MainPage(driver); Thread.sleep(2000); mainPage.setLogin(loginName);
	  if(loginName.equals("Michael@msglab.tech")) {mainPage.setPassword(password); mainPage.signInNo();}
	  testLog.log(Status.INFO,
	 "Open Office 365"); Office365Page officePage = new Office365Page(driver);
	  System.out.println("OFFICE 365");
	  Thread.sleep(8000);
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
