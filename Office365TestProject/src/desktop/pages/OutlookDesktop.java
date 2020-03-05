package desktop.pages;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jetty.websocket.client.io.UpgradeConnection.SendUpgradeRequest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.FindElements;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.WiniumDriver;

import com.sun.jna.platform.win32.User32;
import com.thoughtworks.selenium.Selenium;

import net.bytebuddy.utility.privilege.GetSystemPropertyAction;
import pages.OneDrive.NewMenuItem;

public class OutlookDesktop {
	public enum LeftBottomMenu {MAIL, CALENDAR, PEOPLE, TASKS}
	public enum TopMenu {FILE, HOME, SEND_RECEIVE, FOLDER, VIEW, SEARCH}
	public enum MeetingInThePast {DONT_SEND, SEND_ANYWAY}
	public enum RecurrencePattern {DAILY, WEEKLY, MONTHLY, YEARLY}
	public enum CancelRecurrence{CANCEL_OCCURRENCE, CANCEL_SERIES}
	public enum OpenRecurrenceType{OPEN_OCCURENCE, OPEN_SERIES}
	public enum SkypeTopMenuButton{CONTACTS, CHAT_ROOMS, CONVERSATIONS, PHONE, MEETINGS}
	final String _TEST_ENV_CONFIG = "C:\\DRIVERS\\_OutlookConfig\\tenv\\AmdocsAddIn.dll.config";
	final String _PRODUCTION_ENV_CONFIG = "C:\\DRIVERS\\_OutlookConfig\\penv\\AmdocsAddIn.dll.config";
	final String _APP_PATH = "C:\\Program Files (x86)\\Amdocs\\Outlook Add-In\\";
	final String _FILE_NAME = "AmdocsAddIn.dll.config";
	//WebDriver chromeDriver = new ChromeDriver();
	WiniumDriver driver;
	String driverPath = "c:\\DRIVERS\\chromedriver.exe";
	
	private WebElement newAmdocsMeeting,
	txtTo, txtSubject, btnSend, txtSearchQuery,
	btnDeleteMeeting, sendUpdate, mailInTheList,
	btnConfirmCloseing, btnCloseMeeting, btnMeetingInThePast, 
	txtLocation, btnNewEmail, txtMessageTo,
	txtSubjectMessage, btnCreatedMeeting, btnOpen,
	linkJoinSkypeMeeting, btnCancelMeeting, btnSendCancellation, startTime = null;
	String TEST_TITLE = "AUTOTEST_"+getRandom();

	public OutlookDesktop(WiniumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public OutlookDesktop() {}
	public void sendAnyWay() {WebElement buttonExecute = driver.findElement(By.xpath("//*[@Name='Send Anyway']"));
	buttonExecute.click();
	List<WebElement> errorList = driver.findElements(By.name("Send Meeting Error"));
	WebElement confirmBtn = driver.findElement(By.xpath("//*[@Name='OK']"));
	confirmBtn.click();
	WebElement closeWindow = driver.findElement(By.xpath("//*[@Name='Close']"));
	closeWindow.click();
	confirmBtn = driver.findElement(By.xpath("//*[@Name='OK']"));
	confirmBtn.click();
	}
	/* OpenRecurrenceType{OPEN_OCCURENCE, OPEN_SERIES}*/
	public boolean openRecurrenceTypeTopMenu(OpenRecurrenceType type) throws AWTException {
		boolean flag= true;
		String myType = null;
		Robot rob = new Robot();
		rob.keyPress(KeyEvent.VK_CONTROL);
		rob.keyPress(KeyEvent.VK_O);
		rob.keyRelease(KeyEvent.VK_O);
		rob.keyRelease(KeyEvent.VK_CONTROL); rob.delay(1000);
		/*
		WebElement openMenu = driver.findElement(By.xpath("//*[@Name='Open']"));
		openMenu.click();
		*/
		
		switch(type) {
		case OPEN_OCCURENCE : 
			rob.keyPress(KeyEvent.VK_ENTER);
			rob.keyRelease(KeyEvent.VK_ENTER); rob.delay(500); flag = true; 
			break;  
		case OPEN_SERIES : 
			rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB); rob.delay(500);
			rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(500);
			rob.keyPress(KeyEvent.VK_DOWN);		rob.keyRelease(KeyEvent.VK_DOWN);rob.delay(500);
			rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(500);
			rob.keyPress(KeyEvent.VK_ENTER);	rob.keyRelease(KeyEvent.VK_ENTER); rob.delay(500);flag = false; 
			break;  
		//case OPEN_OCCURENCE : myType = "Open Occurrence";  break;
		//case OPEN_SERIES : myType = "Open Series"; break;
		}
		//WebElement choosenType = driver.findElement(By.xpath("//*[@Name='"+myType+"']"));
		//choosenType.click();
		return flag;
	}
	public void skypeMenu(SkypeTopMenuButton item) {
		String menuItem = "";
		switch(item){
		case CONTACTS : menuItem = "Contacts"; break;
		case CHAT_ROOMS : menuItem = "Chat Rooms"; break;
		case CONVERSATIONS : menuItem = "Conversations"; break;
		case PHONE : menuItem = "Phone"; break;
		case MEETINGS : menuItem = "Meetings"; break;
		}
		WebElement choosenSkypeTopMenyItem = driver.findElement(By.xpath("//*[@LocalizedControlType='List Item' and contains(@Name,'"+menuItem+"')]")); 
		
		choosenSkypeTopMenyItem.click();
		
	}
	public void chooseReccurrencePattern(RecurrencePattern recPattern) throws AWTException {
		
		Robot r = new Robot();
		switch(recPattern) {
		case DAILY : 
			r.keyPress(KeyEvent.VK_ALT);
			r.keyPress(KeyEvent.VK_D);
			r.keyRelease(KeyEvent.VK_D);
			r.keyRelease(KeyEvent.VK_ALT);
			break;
		case WEEKLY : 
			r.keyPress(KeyEvent.VK_ALT);
			r.keyPress(KeyEvent.VK_W);
			r.keyRelease(KeyEvent.VK_W);
			r.keyRelease(KeyEvent.VK_ALT);
			break;
		case MONTHLY : 
			r.keyPress(KeyEvent.VK_ALT);
			r.keyPress(KeyEvent.VK_M);
			r.keyRelease(KeyEvent.VK_M);
			r.keyRelease(KeyEvent.VK_ALT);
			break;
		case YEARLY : 
			r.keyPress(KeyEvent.VK_ALT);
			r.keyPress(KeyEvent.VK_Y);
			r.keyRelease(KeyEvent.VK_Y);
			r.keyRelease(KeyEvent.VK_ALT); 
			break;
		}
		
	}
	// value - esli NE true, vstavlyaets data 1/1/2020
	public void chooseReccurrencePattern(RecurrencePattern recPattern, boolean value) throws InterruptedException, AWTException {
		if(!value) {
			WebElement startDate = driver.findElement(By.xpath("//*[@AutomationId='8222']"));
			startDate.clear();
			startDate.sendKeys("Wed 1/1/2020");
			}
		Robot r = new Robot();
		switch(recPattern) {
		case DAILY : 
			r.keyPress(KeyEvent.VK_ALT);
			r.keyPress(KeyEvent.VK_D);
			r.keyRelease(KeyEvent.VK_D);
			r.keyRelease(KeyEvent.VK_ALT);
			break;
		case WEEKLY : 
			r.keyPress(KeyEvent.VK_ALT);
			r.keyPress(KeyEvent.VK_W);
			r.keyRelease(KeyEvent.VK_W);
			r.keyRelease(KeyEvent.VK_ALT);
			break;
		case MONTHLY : 
			r.keyPress(KeyEvent.VK_ALT);
			r.keyPress(KeyEvent.VK_M);
			r.keyRelease(KeyEvent.VK_M);
			r.keyRelease(KeyEvent.VK_ALT);
			break;
		case YEARLY : 
			r.keyPress(KeyEvent.VK_ALT);
			r.keyPress(KeyEvent.VK_Y);
			r.keyRelease(KeyEvent.VK_Y);
			r.keyRelease(KeyEvent.VK_ALT); 
			break;
		} Thread.sleep(1000);
		
		
	}
	public boolean sendOrNotMeeting(MeetingInThePast menuItem) throws InterruptedException, AWTException {
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		switch(menuItem) {
	case DONT_SEND : 
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		break;
	case SEND_ANYWAY :
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.keyRelease(KeyEvent.VK_RIGHT);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		break;
	
		}
		/*
		 * WebElement buttonExecute =
		 * driver.findElement(By.xpath("//*[@Name='"+param+"']"));
		 * buttonExecute.click();
		 */
		List<WebElement> errorList = driver.findElements(By.name("Send Meeting Error"));
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		//WebElement confirmBtn = driver.findElement(By.xpath("//*[@Name='OK']"));
		//confirmBtn.click();
		//WebElement closeWindow = driver.findElement(By.xpath("//*[@Name='Close']"));
		//closeWindow.click();
		//confirmBtn = driver.findElement(By.xpath("//*[@Name='OK']"));
		//confirmBtn.click();
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.delay(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		if(errorList.size() > 0) {return true;}
		else {return false;}
		
	}
	// Choose Left menu item
	public void chooseTopMenuItem(TopMenu menuItems) throws InterruptedException {
		Thread.sleep(2000);
		String parametrListItem = null;
		
		switch (menuItems) {
		case FILE:
			parametrListItem = "File Tab";
			break;
		case HOME:
			parametrListItem = "Home";
			break;
		case SEND_RECEIVE:
			parametrListItem = "Send / Receive";
			break;
		case FOLDER:
			parametrListItem = "Folder";
			break;
		case VIEW:
			parametrListItem = "View";
			break;
		}
		WebElement topBottomItem = (new WebDriverWait(driver, 4))
				.until(ExpectedConditions.presenceOfElementLocated(By.name(parametrListItem)));
		topBottomItem.click();
	}
	// Choose Left menu item
	public void chooseNewMenuItem(LeftBottomMenu menuItem) throws InterruptedException {
		Thread.sleep(3000);
		String parametrListItem = null;
		
		switch (menuItem) {
		case MAIL:
			parametrListItem = "Mail";
			break;
		case CALENDAR:
			parametrListItem = "Calendar";
			break;
		case PEOPLE:
			parametrListItem = "People";
			break;
		case TASKS:
			parametrListItem = "Tasks";
			break;
			
		} 
		WebElement leftBottomItem = (new WebDriverWait(driver, 17))
				.until(ExpectedConditions.presenceOfElementLocated(By.name(parametrListItem)));
		leftBottomItem.click();
	}
	public void ifRecMeeting() throws InterruptedException, AWTException {
		Thread.sleep(2000);
		/*
		 * try {List<WebElement> listA =
		 * driver.findElements(By.xpath("//*[@Name='Open Recurring Item']"));
		 * if(listA.size()>0) { System.out.println("LIST SIZE : "+listA.size()); Robot r
		 * = new Robot(); r.keyPress(KeyEvent.VK_ENTER);
		 * r.keyRelease(KeyEvent.VK_ENTER);} } catch(Exception e) {}
		 */
		Robot r = new Robot(); r.keyPress(KeyEvent.VK_ENTER);
		  r.keyRelease(KeyEvent.VK_ENTER);
	}

	public void copyMailHeader(String mailName) throws InterruptedException, AWTException {
		searchMail(mailName); 									
		Actions action = new Actions(driver);  Thread.sleep(2000);   				
		mailInTheList = driver.findElement(By.xpath("//*[@ClassName='LeafRow' and contains(@Name,'"+mailName+"')]")); 
		action.doubleClick(mailInTheList).perform();Thread.sleep(1000); 
		chooseTopMenuItem(TopMenu.FILE);	Thread.sleep(2000);				

		WebElement mailProperties = (new WebDriverWait(driver, 4))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@ClassName='NetUIStickyButton' and contains(@Name,'Properties')]"))); 
		mailProperties.click();						Thread.sleep(1000);	
		WebElement headersField = driver.findElement(By.xpath("//*[@AutomationId='4105']"));
		headersField.click();
		String val = headersField.getAttribute("Value");
		System.out.println("VALUE - "+ val);
		
		/*
		Robot robot = new Robot();
		robot.delay(2000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_A);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		*/
		

		// have to be  completed 
	}
	public void specifyWrongDay(int day) throws AWTException {
		Robot rob = new Robot();
		String newDay = createDate(day);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(250);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(250);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(250);
		rob.keyPress(KeyEvent.VK_CONTROL);  rob.keyPress(KeyEvent.VK_A);rob.delay(250);
		rob.keyRelease(KeyEvent.VK_A); rob.keyRelease(KeyEvent.VK_CONTROL);
		rob.keyPress(KeyEvent.VK_DELETE); rob.keyRelease(KeyEvent.VK_DELETE);
		pasteString(newDay); rob.delay(250); rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(250);
		btnSendMeeting_ByRobot();
		
	}
	/* Specify INCORRECT date and time */
	public void specifyNewDay(int day) throws InterruptedException, AWTException {
		//String wd = "Sun 1/5/2020";
		WebElement startDate = driver.findElement(By.xpath("//*[@AutomationId='4098']")); 
		startDate.clear();
		startDate.sendKeys(createDate(day));
		btnSendMeeting_ByRobot();
		//btnSend = driver.findElement(By.name("Send"));
		//btnSend.click();Thread.sleep(1000);
		
	}
	
	public void specifyNewDayN(int day) throws InterruptedException, AWTException {
		Thread.sleep(1000);
		Robot rob = new Robot();
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(500);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(500);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(500);
		pasteString(createDate(day));
		rob.delay(500);
		/*
		WebElement startDate = driver.findElement(By.xpath("//*[contains(@Name,'Start date')]"));
		
		startDate.clear();
		startDate.sendKeys(createDate(day));
		*/
	}
	public void clickSendUpdate() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_ALT); robot.delay(1000);
		
		
	}
	
	public boolean updateMeetingDateNew(String meetingName,int day) throws InterruptedException {
		try{clickByMeetingName(meetingName); 
		specifyNewDayN(day);
		clickSendUpdate();
		Robot rob = new Robot();
		List<WebElement> popup = driver.findElements(By.xpath("//*[contains(@Name,'Send Anyway')]"));
		if(popup.size()>0) {
			rob.keyPress(KeyEvent.VK_RIGHT);
			rob.keyRelease(KeyEvent.VK_RIGHT); rob.delay(250);
			rob.keyPress(KeyEvent.VK_ENTER);
			rob.keyRelease(KeyEvent.VK_ENTER);
			rob.delay(1000);
		}
		
		
		List<WebElement> listError = driver.findElements(By.xpath("//*[@LocalizedControlType='Dialog' and contains(@Name,'Send Meeting Error')]"));
		if(listError.size()>0) {
			rob.keyPress(KeyEvent.VK_ENTER);
			rob.keyRelease(KeyEvent.VK_ENTER);
		}
			
		/*
		  WebElement sendUpdate = driver.findElement(By.xpath("//*[contains(@Name,'Send Update')]"));
		  sendUpdate.click();
		*/
		return true;
		}
		catch(Exception e) {e.getStackTrace(); 
		return false;
		}
		
	}
	
	public boolean clickForward(String meeting, String newMember) {
		try {
			btnCreatedMeeting = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@Name,'"+meeting+"')]")));
			btnCreatedMeeting.click();
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_F);
			robot.keyRelease(KeyEvent.VK_F);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			List<WebElement> warningList = driver.findElements(By.xpath("//*[@LocalizedControlType='Dialog' and contains(@Name,'Microsoft Outlook')]"));
			if(warningList.size()>0) {
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				robot.delay(1000);
			}
			
		// WebElement btnForward = driver.findElement(By.xpath("//*[contains(@Name,'Forward')]"));
		// btnForward.click();
			pasteString(newMember);
			robot.delay(1000);
		//WebElement txtTo = driver.findElement(By.xpath("//*[@AutomationId='4098']"));
		//txtTo.sendKeys(newMember);
		
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.delay(1000);
			robot.keyPress(KeyEvent.VK_F9);
			robot.keyRelease(KeyEvent.VK_F9);
			robot.delay(3000);
		// WebElement sendUpdate = driver.findElement(By.xpath("//*[contains(@Name,'Send')]"));
		// sendUpdate.click();
		return true;}
		catch(Exception e) {e.getStackTrace(); return false;}
		
	}
	
	public boolean updateMeetingParticipants(String meeting, String newMailOrName, int day) throws AWTException, InterruptedException {
		btnCreatedMeeting = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@Name,'"+meeting+"')]")));
		btnCreatedMeeting.click();
		
		List<WebElement> myList = driver.findElements(By.xpath("//*[contains(@Name,'Add or Remove Attendees...')]"));
		if(myList.size()>0) {myList.get(0).click();
		}
		else{ WebElement btnaddOrRemoveAttendes = driver.findElement(By.xpath("//*[contains(@Name,'Meeting')]"));
		btnaddOrRemoveAttendes.click();
		WebElement btnAddOrRemoveAttendes = driver.findElement(By.xpath("//*[contains(@Name,'Add or Remove Attendees...')]"));
		btnAddOrRemoveAttendes.click();
		}
		Thread.sleep(1000);
		// WebElement txtstartDate = driver.findElement(By.xpath("//*[@AutomationId='101']")); 
		// txtstartDate.sendKeys(newMailOrName);
		pasteString(newMailOrName);
		
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		//clickOK();
		robot.keyPress(KeyEvent.VK_TAB);		robot.keyRelease(KeyEvent.VK_TAB); robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);		robot.keyRelease(KeyEvent.VK_TAB); robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);		robot.keyRelease(KeyEvent.VK_TAB); robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);		robot.keyRelease(KeyEvent.VK_TAB); robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);		robot.keyRelease(KeyEvent.VK_TAB); robot.delay(500);
		robot.keyPress(KeyEvent.VK_ENTER); 		robot.keyRelease(KeyEvent.VK_ENTER); robot.delay(500);
		// specifyNewDayN(day);
		
		// WebElement sendUpdate = driver.findElement(By.xpath("//*[contains(@Name,'Send Update')]"));
		// sendUpdate.click();
		robot.keyPress(KeyEvent.VK_ALT); 
		robot.keyPress(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_ALT);robot.delay(2000);
		try {
		List<WebElement> btnSendAnyway = driver.findElements(By.xpath("//*[contains(@Name,'Send Anyway')]"));
		if(btnSendAnyway.size()>0) {
			//WebElement el = driver.findElement(By.xpath("//*[contains(@Name,'Send Anyway')]"));
			//el.click();
			btnSendAnyway.get(0).click();
		}
		else {}}
		catch(Exception e){}
		return true;
		
		
		
		
	}
	// get day(increase/decrease)
	public String createDate(int number) {
		SimpleDateFormat SDFormat = new SimpleDateFormat("E MM/dd/yyyy"); 
	     Calendar cal = Calendar.getInstance(); 

	     String curr_date = SDFormat.format(cal.getTime()); 
	     System.out.println("Formatted Date: " + curr_date);
	     cal.add(Calendar.DATE, number);
	     
	     String newDate = SDFormat.format(cal.getTime()); // substract 1 month
	     System.out.println("Formatted Date: " + newDate);
	     return newDate;
	}
	
	public String createTime(int hour, int min) {
		SimpleDateFormat SDFormat = new SimpleDateFormat("h:mm a"); 
	     Calendar cal = Calendar.getInstance(); 

	     String curr_date = SDFormat.format(cal.getTime()); 
	     System.out.println("Formatted Date: " + curr_date);
	     
	     cal.add(Calendar.HOUR, hour);
	     cal.add(Calendar.MINUTE, min);
	     
	     String newTime = SDFormat.format(cal.getTime()); 
	     System.out.println("Formatted Time: " + newTime);
	     return newTime;
	}
	
	// Search mail
	public void searchMail(String mailName) throws InterruptedException {
		txtSearchQuery = driver.findElement(By.name("Search Query"));
		txtSearchQuery.sendKeys(mailName); // Thread.sleep(2000);
		//Button[@Name='Has Attachments']
		//mailInTheList = driver.findElement(By.name(mailName));
		//return mailInTheList;
	}
	
	public void clickNewAmdocsMeeting() throws InterruptedException {
		newAmdocsMeeting = (new WebDriverWait(driver, 5))
				.until(ExpectedConditions.presenceOfElementLocated(By.name("New Amdocs Meeting")));
		newAmdocsMeeting.click();Thread.sleep(2000);
	}
	public void clickNewMeeting() throws InterruptedException {newAmdocsMeeting = (new WebDriverWait(driver, 5))
			.until(ExpectedConditions.presenceOfElementLocated(By.name("New Meeting")));
	newAmdocsMeeting.click();Thread.sleep(2000);}

	public void clickToJoinSkypeMeeting() throws InterruptedException {
		linkJoinSkypeMeeting = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@Name='join Skype Meeting']")));
		linkJoinSkypeMeeting.click(); Thread.sleep(3000);
}
	
	public boolean runAndVerificationSkypeMeeting(String meetingNameInTheList) throws AWTException {
		Robot robot = new Robot();
		//GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		//GraphicsDevice device = env.getDefaultScreenDevice();
		List<WebElement> listSkypeElements = driver.findElements(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]"));
		if(listSkypeElements.size()>0) {
			
			//Robot robot = new Robot();
			robot.delay(1000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER); 
			//robot.keyPress(KeyEvent.VK_LEFT);robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.delay(2000);
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_F4);
			robot.keyRelease(KeyEvent.VK_F4);
			robot.keyRelease(KeyEvent.VK_ALT);
			
			
			//btnCloseMeeting = (new WebDriverWait(driver, 1)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@Name='Close']")));
			//btnCloseMeeting.click();
			//btnConfirmCloseing = (new WebDriverWait(driver, 2)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@Name='OK']")));
			//btnConfirmCloseing.click();
			
			
			return true;
		}
		else {return false;}
		
	}
	
	// TEST
	public String specifyMeetingPropertiesTEST(List<String> mails) throws InterruptedException { 
		txtSubject = (new WebDriverWait(driver, 7)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@AutomationId='4100']")));
		txtSubject.sendKeys(TEST_TITLE);
		txtTo = (new WebDriverWait(driver, 2)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@AutomationId='4106']")));
		for(String mail : mails) 
		{
			txtTo.sendKeys(mail); txtTo.click();
		}
			
		//mails.forEach(mail -> {txtTo.sendKeys(mail); txtTo.sendKeys(Keys.TAB); });
		
	
		
		return TEST_TITLE;

	}
	/* Send meeting functions */
	public void btnSendMeeting_ByRobot() throws AWTException {
		Robot robot = new Robot();
		//robot.delay(500);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.delay(3000);
	}
	public void btnSendMeeting() throws InterruptedException {
		btnSend = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@Name,'Send')]")));
		//btnSend = driver.findElement(By.name("Send"));
		btnSend.click();Thread.sleep(2000);
	}
	public void clickOKButton() throws AWTException {
		Robot robot = new Robot();
	robot.keyPress(KeyEvent.VK_ALT);
	robot.keyPress(KeyEvent.VK_F4);
	robot.delay(2000);
		List okButtonList = driver.findElements(By.xpath("//*[@Name='OK']"));
		if(okButtonList.size()>0) {
			okButtonList.get(0);
		btnConfirmCloseing = (WebElement) okButtonList.get(0);
		btnConfirmCloseing.click(); System.out.println("OK button .. ");
		}
	}
	/************************************************************************************************************************/
	public void sendOrNot() {
		WebElement btnSendOrNot = driver.findElement(By.xpath("//*[@AutomationId='7']"));
		btnSendOrNot.click();
	}
	
	public void openAccessNumbers() throws InterruptedException {
		Thread.sleep(2000);
		WebElement allNumbers = driver.findElement(By.xpath("//*[@Name='all access numbers']"));
		allNumbers.click(); Thread.sleep(2000);
	}
	public void allowAccess() throws InterruptedException {
		Thread.sleep(2000);
		List<WebElement> list = driver.findElements(By.xpath("//*[@Name='Allow once']"));
		if(list.size()>0) {
		WebElement btnAllowAccess = driver.findElement(By.xpath("//*[@Name='Allow once']"));
		btnAllowAccess.click();}
	}
	public boolean checkCountry(String country) throws InterruptedException {
		Thread.sleep(2000);
		//ArrayList<WebElement> myArray = driver.findElements(By.xpath("//tbody//td[contains(text(),'"+country+"')]"));
		WebElement myCountry = driver.findElement(By.xpath("//tbody//td[contains(text(),'"+country+"')]")); 
		String nameFromTable = myCountry.getText();
		System.out.println("===== "+ nameFromTable);
		if(country.equals(nameFromTable)) {
		return true;}
		else {return false;}
	}
	
	/* Close window */
	public void closeWindow() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_ALT);
	}
	
	
	public boolean callSkypeByList(String number, List<String> meetingList) throws InterruptedException, AWTException, IOException {
		Robot robot = new Robot();
		Process process=Runtime.getRuntime().exec("C:\\Program Files (x86)\\Microsoft Office\\Office16\\lync.exe");
		robot.delay(2000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_4);
		robot.keyRelease(KeyEvent.VK_4);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		//skypeMenu(SkypeTopMenuButton.PHONE);
		
		StringSelection stringSelection = new StringSelection(number);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		System.out.println("Try to call by number : "+ number);
		Actions act = new Actions(driver);
		act.click(driver.findElement(By.xpath("//*[@LocalizedControlType='Button' and contains(@Name,'Call')]"))).perform();
		
	
		System.out.println("End act ***");
		Thread.sleep(2000);
		WebElement txtConfirenceIDEditBox = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@LocalizedControlType='Edit Box' and contains(@Name,'Dialpad Text')]")));
		txtConfirenceIDEditBox.sendKeys(meetingList.get(1));
		Thread.sleep(59000);
		List<WebElement> activeSkypeWindows = driver.findElements(By.xpath("//*[@ClassName='NetUIImage']")); 
		System.out.println("Active window class size : "+activeSkypeWindows.size());
		if(activeSkypeWindows.size()>0) {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			System.out.println("*** True * Exist **");
			return true; // connection sushestvuet
		}
		else {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			System.out.println("*** False * Not exist **");
			return false;}
		}
	// today
	
	public boolean getLinksFromAmdocsMeetingWindow() {
		boolean flag=true;
		try {
		//WebElement bodyDocument = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@LocalizedControlType='document' and contains(@Name,'Untitled Message')]")));
		List<WebElement> bodyDocument = driver.findElements(By.xpath("//*[@LocalizedControlType='link']"));
		 //String txtBody = bodyDocument.getText();
		if(bodyDocument.size()>1) {
		 System.out.println("Full text : " + bodyDocument.size());
		 
		 flag = true;}
		 }
		catch(Exception e){flag = false;}
		return flag;
		
		
	}
	public void closeWithoutChanges() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_D);
		robot.keyRelease(KeyEvent.VK_D);
		robot.keyRelease(KeyEvent.VK_CONTROL); robot.delay(500);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	public boolean callByNumber(String number, String meetingID) throws IOException, AWTException, InterruptedException {
		boolean flag = true;
		Process process=Runtime.getRuntime().exec("C:\\Program Files (x86)\\Microsoft Office\\Office16\\lync.exe");
		Robot robot = new Robot();
		robot.delay(3000);
		// LocalizedControlType:	"Title Bar"

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_4);
		robot.keyRelease(KeyEvent.VK_4);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		//skypeMenu(SkypeTopMenuButton.PHONE);
		pasteString(number);
		/*
		StringSelection stringSelection = new StringSelection(number);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		*/
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		System.out.println("Calling ..... ");
		// Actions act = new Actions(driver);
		// act.click(driver.findElement(By.xpath("//*[@LocalizedControlType='Button' and contains(@Name,'Call')]"))).perform();
		
		/*
		WebElement btnCall = driver.findElement(By.xpath("//*[@LocalizedControlType='Button' and contains(@Name,'Call')]"));
		btnCall.click();
		*/
		Thread.sleep(4000);
		robot.keyPress(KeyEvent.VK_TAB); robot.delay(250); 
		robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		/*
		WebElement txtConfirenceIDEditBox = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@LocalizedControlType='Edit Box' and contains(@Name,'Dialpad Text')]")));
		txtConfirenceIDEditBox.click(); 
		txtConfirenceIDEditBox.sendKeys(meetingID);
		*/
		
		pasteString(meetingID);Thread.sleep(2000);
		pasteString(meetingID);Thread.sleep(2000);
		pasteString(meetingID);Thread.sleep(17000);
		
		
		//Thread.sleep(58000);
		
		
		
		/*
		 * List<WebElement> skypeTitleLine = driver.findElements(By.
		 * xpath("//*[@LocalizedControlType='Button' and contains(@Name,'+')]")); if
		 * (skypeTitleLine.size()>0) // Name: "+297267340"
		 */
		try {
		List<WebElement> activeSkypeWindows = driver.findElements(By.xpath("//*[@LocalizedControlType='Button' and contains(@Name,'Hang Up')]"));
		    	
		// List<WebElement> activeSkypeWindows = driver.findElements(By.xpath("//*[contains(@Name,'1:')]"));
		System.out.println("Active window class : "+activeSkypeWindows.size());
		if(activeSkypeWindows.size()>0) {
			
			activeSkypeWindows.get(0).click();
			
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			System.out.println("*** True ***");
			flag = true; // connection sushestvuet
			}
		else {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			System.out.println("*** False ***");
			flag = false;}
			}
		catch(Exception e) {System.out.println("Check callByNumber method.");}
		return flag;
		}
		
	public void moveOut(int x, int y) {
	Actions builder = new Actions(driver);
	builder.moveByOffset(x, y);
	}
	
	
	// Skype calling
	public boolean callFromSkype(String number, String meetingName) throws IOException, InterruptedException, AWTException {
		btnCreatedMeeting = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@Name,'"+meetingName+"')]")));
		btnCreatedMeeting.click();
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(3000);
		WebElement bodyDocument = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@LocalizedControlType='document' and contains(@Name,'"+meetingName+"')]")));
				 String txtBody = bodyDocument.getText();
		String meetingID = getMeetingID(txtBody);
		System.out.println("MeetingID - " + meetingID);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.delay(1000);
		closeWindow();
		
		Process process=Runtime.getRuntime().exec("C:\\Program Files (x86)\\Microsoft Office\\Office16\\lync.exe");
		robot.delay(2000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_4);
		robot.keyRelease(KeyEvent.VK_4);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		//skypeMenu(SkypeTopMenuButton.PHONE);
		
		StringSelection stringSelection = new StringSelection(number);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		
		/*
		  robot.delay(500); robot.keyPress(KeyEvent.VK_ENTER);
		  robot.keyRelease(KeyEvent.VK_ENTER);
		 */
		System.out.println("Calling ..... ");
		 Actions act = new Actions(driver);
		act.click(driver.findElement(By.xpath("//*[@LocalizedControlType='Button' and contains(@Name,'Call')]"))).perform();

/*		8253355
		WebElement txtSearchInput = driver.findElement(By.xpath("//*[@LocalizedControlType='Edit Box' and contains(@Name,'Search Input')]"));
		txtSearchInput.sendKeys(number);

*/
		
		
/*		
		 WebElement btnCall = driver.findElement(By.xpath("//*[@LocalizedControlType='Button' and contains(@Name,'Call')]"));
		 btnCall.click();
	*/	
		
		Thread.sleep(2000);
		WebElement txtConfirenceIDEditBox = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@LocalizedControlType='Edit Box' and contains(@Name,'Dialpad Text')]")));
		txtConfirenceIDEditBox.sendKeys(meetingID);
		Thread.sleep(40000);
		List<WebElement> activeSkypeWindows = driver.findElements(By.xpath("//*[@ClassName='NetUINetUIDialog']")); 
		System.out.println("Active window class : "+activeSkypeWindows.size());
		if(activeSkypeWindows.size()>0) {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			System.out.println("*** True ***");
			return true; // connection sushestvuet
		}
		else {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			System.out.println("*** False ***");
			return false;}
		}
		
		
	
		//swithToTaskBarOrTray("Show");
		//297267340 
		
	
	public void swithToTaskBarOrTray(String appName) {
		User32.INSTANCE.GetForegroundWindow(); 
		WebElement oneDriveTrayIcon = driver.findElement(By.xpath("//*[contains(@Name,'"+appName+"')]"));
		oneDriveTrayIcon.click();
		/*	 HWND hwnd = User32.INSTANCE.FindWindow(null , "OneDrive - AMDOCS");
		  	 User32.INSTANCE.ShowWindow(hwnd,1);									 */
	}
	/*********************************
	 |  Open an appointment by name. |
	 | @throws AWTException          |
	 *********************************/
	public void clickByMeetingName(String meetingNameInTheList) throws AWTException {
		btnCreatedMeeting = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]")));
		btnCreatedMeeting.click();
		
		Robot robot = new Robot();
		
		//robot.keyPress(KeyEvent.VK_ENTER);
		//robot.keyRelease(KeyEvent.VK_ENTER);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_O);
		robot.keyRelease(KeyEvent.VK_O);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.delay(500);
		// btnOpen = (new WebDriverWait(driver, 1)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@Name='Open']")));
		// btnOpen.click();
	}
	public void changeDay() {}
	/* Recurrence */
	public boolean addRecurrence(RecurrencePattern RP, boolean valid, int hour, int min, int day) throws AWTException, InterruptedException{
		boolean myRes;
		String tn = createTime(hour, min);
		//String dayToday = createDate(day);
		Robot robot = new Robot();
		//robot.delay(1000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_G);
		robot.keyRelease(KeyEvent.VK_G);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.delay(1000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_CONTROL); robot.delay(500);
		pasteString(tn);
		/*
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(500);robot.keyPress(KeyEvent.VK_TAB);robot.delay(500);robot.keyPress(KeyEvent.VK_TAB);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(500);robot.keyPress(KeyEvent.VK_TAB);robot.delay(500);robot.keyPress(KeyEvent.VK_TAB);robot.delay(500);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		pasteString(dayToday);
		*/
		String newDay = createDate(day);
		chooseReccurrencePattern(RP, valid);
		/*
		 * WebElement startPastDate = driver.findElement(By.
		 * xpath("//*[@LocalizedControlType='edit' and contains(@Name,'Start')]"));
		 * startPastDate.clear(); startPastDate.sendKeys(newDay);
		 */
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_ALT);robot.delay(500);
		pasteString(newDay);
		robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);		robot.keyRelease(KeyEvent.VK_TAB); robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);		robot.keyRelease(KeyEvent.VK_TAB); robot.delay(500);
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		//clickOK();
		
		myRes = checkRecurrenceType();
		btnSendMeeting_ByRobot();
		Thread.sleep(1000);
		System.out.println("Result - "+ myRes);
		return myRes;
		
	}
	
	public boolean addRecurrenceNew(RecurrencePattern RP, boolean valid, int hour, int min, int day) throws AWTException, InterruptedException{
		boolean myRes;
		String tn = createTime(hour, min);
		//String dayToday = createDate(day);
		Robot robot = new Robot();
		robot.delay(1000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_G);
		robot.keyRelease(KeyEvent.VK_G);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.delay(1000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		pasteString(tn);
		
		String newDay = createDate(day);
		chooseReccurrencePattern(RP);
		System.out.println("DAY - "+newDay);
		
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_ALT);
			robot.delay(500);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.delay(500);
		pasteString(newDay);
		robot.delay(500);
		
		robot.keyPress(KeyEvent.VK_TAB);		robot.keyRelease(KeyEvent.VK_TAB); robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);		robot.keyRelease(KeyEvent.VK_TAB); robot.delay(500);
		// suda mozno vstavit chislo povtoreniy
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		//clickOK();
		
		myRes = checkRecurrenceType();
		btnSendMeeting_ByRobot();
		
		System.out.println("Result - "+ myRes);
		return myRes;
		
	}
	
	public boolean checkRecurrenceType() {
		List<WebElement> recList = driver.findElements(By.xpath("//*[@LocalizedControlType='Button' and contains(@ToggleState,'On')]"));
		System.out.println("Toggle state size : "+ recList.size());
		
		//ToggleState:	"Off
		if(recList.size() > 0) {return true;}
		else {return false;}
	}
	/* Update 1 meeting date and time . NOT R ! */
	public ArrayList<String> updateOneMeetingDateAndTime(String meetingName, int hour, int min, int day) throws AWTException {
		String tn = createTime(hour, min);
		String dayToday = createDate(day);
		Robot rob = new Robot();
		List<WebElement> eventList = driver.findElements(By.xpath("//*[contains(@Name,'"+meetingName+"')]"));
		if(!(eventList.size() > 0)) {
			
			rob.keyPress(KeyEvent.VK_ALT);
			rob.keyPress(KeyEvent.VK_UP);
			rob.keyRelease(KeyEvent.VK_UP);
			rob.keyRelease(KeyEvent.VK_ALT);rob.delay(500);
		}
		
		btnMeetingInThePast = driver.findElement(By.xpath("//*[contains(@Name,'"+meetingName+"')]"));
		btnMeetingInThePast.click();
		
		rob.keyPress(KeyEvent.VK_ENTER);
		rob.keyRelease(KeyEvent.VK_ENTER);
		rob.delay(1000);
		rob.setAutoDelay(1000);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(250);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(250);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(250);
		pasteString(dayToday);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(250);
		pasteString(tn);
		
		// poluchit  new id
		WebElement bodyDocument = (new WebDriverWait(driver, 1)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@LocalizedControlType='document' and contains(@Name,'"+meetingName+"')]")));
		String txtBody = bodyDocument.getText();
		String oldMeetingID = getMeetingID(txtBody);
		System.out.println("Old ID : "+oldMeetingID);
		alt_F4();
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(250);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(250);
		rob.keyPress(KeyEvent.VK_UP); rob.delay(250);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(250);
		rob.keyPress(KeyEvent.VK_ENTER);
		rob.keyRelease(KeyEvent.VK_ENTER);
		rob.delay(1000);
		rob.keyPress(KeyEvent.VK_ENTER);
		rob.keyRelease(KeyEvent.VK_ENTER);
		rob.delay(3000);
		WebElement newbodyDocument = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@LocalizedControlType='document' and contains(@Name,'"+meetingName+"')]")));
		String txtBodyNew = newbodyDocument.getText();
		String newMeetingID = getMeetingID(txtBodyNew);
		System.out.println("New ID : "+newMeetingID);
		alt_F4();
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);
		rob.keyPress(KeyEvent.VK_UP);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);
		rob.keyPress(KeyEvent.VK_ENTER);
		rob.keyRelease(KeyEvent.VK_ENTER);
		
		ArrayList<String> IDList = new ArrayList();
		IDList.add(oldMeetingID);
		IDList.add(newMeetingID);
		return IDList;
		/*
		 * if(oldMeetingID.equals(newMeetingID)) {return true;} else {return false;}
		 */
		
	}
	public void openOutlookAsDifferentUser(String user, String password) throws AWTException, InterruptedException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_WINDOWS);
		r.keyPress(KeyEvent.VK_R);
		r.keyRelease(KeyEvent.VK_R);
		r.keyRelease(KeyEvent.VK_WINDOWS);r.delay(250);
		Thread.sleep(3000);
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_A);
		r.keyRelease(KeyEvent.VK_A);
		r.keyRelease(KeyEvent.VK_CONTROL); r.delay(250);
		
		// pasteString("runas /user:"+user+" \"C:\\Program Files (x86)\\Microsoft Office\\Office16\\OUTLOOK.EXE\"");
		pasteString("C:\\_RESULTS\\Outlook");
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);r.delay(500);
		Actions actions = new Actions(driver);
		WebElement outlookLink = driver.findElement(By.xpath("//*[@LocalizedControlType='list item' and contains(@Name,'OUTLOOK')]"));
		r.keyPress(KeyEvent.VK_SHIFT);
		actions.contextClick(outlookLink).perform();
		r.keyRelease(KeyEvent.VK_SHIFT);
	//			pasteString(password);
	//	r.keyPress(KeyEvent.VK_ENTER); 
		r.keyPress(KeyEvent.VK_DOWN);	r.keyRelease(KeyEvent.VK_DOWN); r.delay(250);
		r.keyPress(KeyEvent.VK_DOWN);	r.keyRelease(KeyEvent.VK_DOWN); r.delay(250);
		r.keyPress(KeyEvent.VK_DOWN);	r.keyRelease(KeyEvent.VK_DOWN); r.delay(250);
		r.keyPress(KeyEvent.VK_DOWN);	r.keyRelease(KeyEvent.VK_DOWN); r.delay(250);
		r.keyPress(KeyEvent.VK_DOWN);	r.keyRelease(KeyEvent.VK_DOWN); r.delay(250);
		r.keyPress(KeyEvent.VK_DOWN);	r.keyRelease(KeyEvent.VK_DOWN); r.delay(250);
		r.keyPress(KeyEvent.VK_ENTER);  r.keyRelease(KeyEvent.VK_ENTER); r.delay(1000);
		pasteString(user); r.keyPress(KeyEvent.VK_TAB);	r.keyRelease(KeyEvent.VK_TAB); r.delay(250);
		pasteString(password); r.keyPress(KeyEvent.VK_TAB);	r.keyRelease(KeyEvent.VK_TAB); r.delay(250);
		r.keyPress(KeyEvent.VK_TAB);	r.keyRelease(KeyEvent.VK_TAB); r.delay(250);
		r.keyPress(KeyEvent.VK_ENTER);  r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(7000);
	}
	
	public void changeOutlookAccount() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ALT); r.keyPress(KeyEvent.VK_F);
		r.keyRelease(KeyEvent.VK_F); r.keyRelease(KeyEvent.VK_ALT);r.delay(1000);
		
		r.keyPress(KeyEvent.VK_ALT); r.keyPress(KeyEvent.VK_D);
		r.keyRelease(KeyEvent.VK_D); r.keyRelease(KeyEvent.VK_ALT);r.delay(1000);
		
		r.keyPress(KeyEvent.VK_ALT); r.keyPress(KeyEvent.VK_S);
		r.keyRelease(KeyEvent.VK_S); r.keyRelease(KeyEvent.VK_ALT);r.delay(1000);
		
	}
	// Ok
	public void openOutlookAsDifferentUserRUNAS(String user, String password, String name) throws AWTException, InterruptedException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_WINDOWS);
		r.keyPress(KeyEvent.VK_R);
		r.keyRelease(KeyEvent.VK_R);
		r.keyRelease(KeyEvent.VK_WINDOWS);r.delay(2000);
		
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_A);
		r.keyRelease(KeyEvent.VK_A);
		r.keyRelease(KeyEvent.VK_CONTROL); r.delay(500);
		pasteString("cmd");
		r.keyPress(KeyEvent.VK_ENTER); r.keyRelease(KeyEvent.VK_ENTER);r.delay(1000);
		//String cmdCommand = "runas /user:"+user+" C:\\Program Files (x86)\\Microsoft Office\\Office16\\OUTLOOK.EXE";
		 pasteString("runas /user:"+user+" \"C:\\Program Files (x86)\\Microsoft Office\\Office16\\OUTLOOK.EXE\"");r.delay(500);
		r.keyPress(KeyEvent.VK_ENTER); r.keyRelease(KeyEvent.VK_ENTER);r.delay(500);
		pasteString(password);r.delay(500);
		r.keyPress(KeyEvent.VK_ENTER); r.keyRelease(KeyEvent.VK_ENTER); r.delay(5000);
		r.keyPress(KeyEvent.VK_ALT);
		r.keyPress(KeyEvent.VK_F);
		r.keyRelease(KeyEvent.VK_F);r.delay(1000);
		r.keyPress(KeyEvent.VK_D);
		r.keyRelease(KeyEvent.VK_D);r.delay(5000);
		r.keyPress(KeyEvent.VK_E);
		r.keyRelease(KeyEvent.VK_E);r.delay(500);
		r.keyRelease(KeyEvent.VK_ALT); r.delay(500);
		
		r.keyPress(KeyEvent.VK_Y);
		r.keyRelease(KeyEvent.VK_Y); r.delay(500);
		
		WebElement invitationMail = (new WebDriverWait(driver, 12)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@Name,'Sign In')]")));
		invitationMail.click();
	
		pasteString(user); r.keyPress(KeyEvent.VK_ENTER); r.keyRelease(KeyEvent.VK_ENTER); r.delay(1000);
		pasteString(password); r.keyPress(KeyEvent.VK_ENTER); r.keyRelease(KeyEvent.VK_ENTER); r.delay(2000);
	}
	public void processBuilderRun() throws IOException {
		//Process process=Runtime.getRuntime().exec("runas /env /user:"+user+" \"C:\\Program Files (x86)\\Microsoft Office\\Office16\\OUTLOOK.EXE\"");
	}
	// yoelap@amdocs.com  // Random@224466
	public void openChromePageAsDifferentUser(String user, String password) throws AWTException, InterruptedException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_WINDOWS);
		r.keyPress(KeyEvent.VK_R);
		r.keyRelease(KeyEvent.VK_R);
		r.keyRelease(KeyEvent.VK_WINDOWS);r.delay(250);
		Thread.sleep(3000);
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_A);
		r.keyRelease(KeyEvent.VK_A);
		r.keyRelease(KeyEvent.VK_CONTROL); r.delay(250);
		
		 pasteString("runas /user:"+user+" \"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe\"");
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);r.delay(500);
		pasteString(password);
		r.keyPress(KeyEvent.VK_ENTER); 
			
		r.keyRelease(KeyEvent.VK_ENTER);r.delay(7000);
		//pasteString("https://outlook.office.com/mail/inbox");
		pasteString("https://www.office.com/");
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);r.delay(1000);
	}
	public static boolean selectWindow(WebDriver driver, String windowTitle){
	    //Search ALL currently available windows
	    for (String handle : driver.getWindowHandles()) {
	        String newWindowTitle = driver.switchTo().window(handle).getTitle();
	        System.out.println("WINDOWTITLE : "+newWindowTitle);
	        if(newWindowTitle.equalsIgnoreCase(windowTitle))
	            //if it was found break out of the wait
	            return true;
	    }
	    return false;

	}
	
	
	/* Vstavka stroki v aktivnoe pole */
	public void pasteString(String anystring) throws AWTException {
		StringSelection stringSelection2 = new StringSelection(anystring);
		Clipboard clipboard2 = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard2.setContents(stringSelection2, stringSelection2);
		Robot rob = new Robot();
		rob.keyPress(KeyEvent.VK_CONTROL);
		rob.keyPress(KeyEvent.VK_V);
		rob.keyRelease(KeyEvent.VK_V);
		rob.keyRelease(KeyEvent.VK_CONTROL); rob.delay(500);
	}
	public void alt_F4() throws AWTException {
		Robot rob = new Robot();
		rob.keyPress(KeyEvent.VK_ALT);
		rob.keyPress(KeyEvent.VK_F4);
		rob.keyRelease(KeyEvent.VK_F4);
		rob.keyRelease(KeyEvent.VK_ALT);
	}
	/* Update series */
	public boolean updateAllRMeetings(String meetingNameInTheList, RecurrencePattern rpat, int timeNew, int min, boolean checkNexWeek, int endAfter) {
		String tn = createTime(timeNew, min);
		boolean myFlag = true;
	//	WebElement mainWindow = driver.findElement(By.xpath("//*[@LocalizedControlType='window' and contains(@Name,'Calendar')]"));
		
		try {
			btnCreatedMeeting = driver.findElement(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]"));
			btnCreatedMeeting.click();
			Robot rob = new Robot();
			rob.keyPress(KeyEvent.VK_ENTER);
			rob.keyRelease(KeyEvent.VK_ENTER);
			rob.delay(1000);
			//rob.setAutoDelay(1000);
			rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(250);
			rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(250);
			rob.keyPress(KeyEvent.VK_DOWN);		rob.keyRelease(KeyEvent.VK_DOWN);rob.delay(250);
			rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(250);
			rob.keyPress(KeyEvent.VK_ENTER);	rob.keyRelease(KeyEvent.VK_ENTER);
			// tab-> tab-> DOWN -> ENTER
		/*	
			WebElement rdbAllSeries = driver.findElement(By.xpath("//*[@LocalizedControlType='radio button' and contains(@Name,'The entire series')]"));
			rdbAllSeries.click();
			clickOK();
		*/
			rob.delay(2000);
			rob.keyPress(KeyEvent.VK_CONTROL);
			rob.keyPress(KeyEvent.VK_G);
			rob.keyRelease(KeyEvent.VK_G);
			rob.keyRelease(KeyEvent.VK_CONTROL);
			
			//String newTime = createTime(1, 5);
			pasteString(tn);
		//WebElement btnRecurrenceMainCalendar = driver.findElement(By.xpath("//Options/*[contains(@Name,'Recurrence')]"));
		//btnRecurrenceMainCalendar.click();
		//WebElement txtStartTime = driver.findElement(By.xpath("//*[@LocalizedControlType='radio button' and contains(@Name,'Start')]"));
		//txtStartTime.clear();

			chooseReccurrencePattern(rpat);
		if(endAfter>0) {
			rob.keyPress(KeyEvent.VK_TAB);	rob.keyRelease(KeyEvent.VK_TAB); rob.delay(250);
			rob.keyPress(KeyEvent.VK_TAB);	rob.keyRelease(KeyEvent.VK_TAB); rob.delay(250);
			rob.keyPress(KeyEvent.VK_TAB);	rob.keyRelease(KeyEvent.VK_TAB); rob.delay(250);
			rob.keyPress(KeyEvent.VK_TAB);	rob.keyRelease(KeyEvent.VK_TAB); rob.delay(250);
			rob.keyPress(KeyEvent.VK_DOWN);	rob.keyRelease(KeyEvent.VK_DOWN); rob.delay(250);
			rob.keyPress(KeyEvent.VK_TAB);	rob.keyRelease(KeyEvent.VK_TAB); rob.delay(250);
			pasteString(Integer.toString(endAfter)); rob.delay(250);
		}
		// WebElement txtStartTime = driver.findElement(By.xpath("//*[@LocalizedControlType='edit' and contains(@Name,'Start')]"));
		// txtStartTime.clear();
		// txtStartTime.sendKeys(newTime);
		
		
		//clickOK(); 
		clickOKByRobot();
		
		rob.keyPress(KeyEvent.VK_ENTER);
		rob.keyRelease(KeyEvent.VK_ENTER);rob.delay(250);
		//clickOK();
		
		//WebElement sendUpdate = driver.findElement(By.xpath("//*[contains(@Name,'Send Update')]"));
		//sendUpdate.click();
		clickSendUpdate();
		if(checkNexWeek) {
			rob.keyPress(KeyEvent.VK_ALT);
			rob.keyPress(KeyEvent.VK_DOWN);
			rob.keyRelease(KeyEvent.VK_DOWN);
			rob.keyRelease(KeyEvent.VK_ALT);
			List<WebElement> elementsOnTheTable = driver.findElements(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]"));
			if(elementsOnTheTable.size()>0) {myFlag = true;}
		}
		
		exitAndSendLaterOrNot();
		return myFlag;}
		catch(Exception e) {e.getStackTrace(); return false;}
	}
	public void clickOKByRobot() throws AWTException {
		Robot rob = new Robot();
		rob.keyPress(KeyEvent.VK_ALT);
		rob.keyPress(KeyEvent.VK_S);
		rob.keyRelease(KeyEvent.VK_S);
		rob.keyRelease(KeyEvent.VK_ALT);
	}
	
	/* create before meeting */
	public List<String> createBeforeMeetingInTheFuture(String mail, int hour, int min) throws AWTException, InterruptedException {
		/*novoe okno - mainWindow*/
		Thread.sleep(4000);
		Robot robot = new Robot();
		String newTime = createTime(hour, min);
		String endTime = createTime(hour, min+30);
		pasteString(mail);
		robot.keyPress(KeyEvent.VK_RIGHT);robot.delay(250); 
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);						
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		pasteString(TEST_TITLE);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		pasteString(newTime);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		pasteString(endTime);robot.delay(1000);
		/*
		WebElement mainWindow = driver.findElement(By.xpath("//*[@LocalizedControlType='window' and contains(@Name,'Meeting')]"));
		txtSubject = mainWindow.findElement(By.xpath("//*[@AutomationId='4100']"));
		txtSubject.sendKeys(TEST_TITLE);
		txtTo = mainWindow.findElement(By.xpath("//*[@AutomationId='4106']"));
		txtTo.sendKeys(mail); 
		String newTime = createTime(hour, min);
		WebElement txtStartTime = driver.findElement(By.xpath("//*[@LocalizedControlType='edit' and contains(@Name,'Start time')]"));
		txtStartTime.clear();
		txtStartTime.sendKeys(newTime);
		String endTime = createTime(hour, min+30);
		WebElement txtEndTime = driver.findElement(By.xpath("//*[@LocalizedControlType='edit' and contains(@Name,'End time')]"));
		txtEndTime.clear();
		txtEndTime.sendKeys(endTime);
		*/
		
		WebElement newbodyDocument = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@LocalizedControlType='document' and contains(@Name,'"+TEST_TITLE+"')]")));
		String txtBodyNew = newbodyDocument.getText();
		String MeetingID = getMeetingID(txtBodyNew);
		List<String> meetingNameAndID = new ArrayList<String>();
		meetingNameAndID.add(TEST_TITLE);
		meetingNameAndID.add(MeetingID);
		
								robot.delay(1000);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_CONTROL); robot.delay(1000);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_ALT);
		
		//clickSend();
		
		//btnSendAnyway(sendOrNot);
		return meetingNameAndID;
	}
	
	
	
	
	
	
	public List<String> createLoadMeeting(String mail, int hour, int min) throws AWTException, InterruptedException {
		/*novoe okno - mainWindow*/
		Thread.sleep(4000);
		Robot robot = new Robot();
		String newTime = createTime(hour, min);
		String endTime = createTime(hour, min+30);
		pasteString(mail);
		robot.keyPress(KeyEvent.VK_RIGHT);robot.delay(250); 
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);						
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		pasteString(TEST_TITLE);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		pasteString(newTime);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		pasteString(endTime);robot.delay(1000);
		/*
		WebElement mainWindow = driver.findElement(By.xpath("//*[@LocalizedControlType='window' and contains(@Name,'Meeting')]"));
		txtSubject = mainWindow.findElement(By.xpath("//*[@AutomationId='4100']"));
		txtSubject.sendKeys(TEST_TITLE);
		txtTo = mainWindow.findElement(By.xpath("//*[@AutomationId='4106']"));
		txtTo.sendKeys(mail); 
		String newTime = createTime(hour, min);
		WebElement txtStartTime = driver.findElement(By.xpath("//*[@LocalizedControlType='edit' and contains(@Name,'Start time')]"));
		txtStartTime.clear();
		txtStartTime.sendKeys(newTime);
		String endTime = createTime(hour, min+30);
		WebElement txtEndTime = driver.findElement(By.xpath("//*[@LocalizedControlType='edit' and contains(@Name,'End time')]"));
		txtEndTime.clear();
		txtEndTime.sendKeys(endTime);
		*/
		
		WebElement newbodyDocument = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@LocalizedControlType='document' and contains(@Name,'"+TEST_TITLE+"')]")));
		String txtBodyNew = newbodyDocument.getText();
		String MeetingID = getMeetingID(txtBodyNew);
		List<String> meetingNameAndID = new ArrayList<String>();
		meetingNameAndID.add(TEST_TITLE);
		meetingNameAndID.add(MeetingID);
		
								robot.delay(1000);
	/*	
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_CONTROL); robot.delay(1000);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_ALT);
		*/
		//clickSend();
		
		//btnSendAnyway(sendOrNot);
		return meetingNameAndID;
	}
	
	public void btnSendAnyway(boolean sendNot) {
		WebElement confirmation;
		if(sendNot) {
		confirmation = driver.findElement(By.xpath("//*[contains(@Name,'Send Anyway')]"));
		confirmation.click();}
		else {confirmation = driver.findElement(By.xpath("//*[contains(@Name,'Don't Send')]"));
		confirmation.click();}
	}
	/* Update recurring meeting date\time */ //ReoccurringTest
	/**
	 * @param meetingNameInTheList - Meeting name
	 * @param rt - Recurrence type (one of all, All)
	 * @param timeNew - Hour (counted from the current moment)
	 * @param min - Minute/s (counted from the current moment)
	 * @param day - Day/s (counted from the current day)
	 * @return
	 * @throws AWTException 
	 * @throws InterruptedException 
	 */
	public boolean updateReoccurringMeetingTimeAndDay(String meetingNameInTheList, OpenRecurrenceType rt, int timeNew, int min) throws AWTException, InterruptedException {
		String tn = createTime(timeNew, min);
		
		boolean one_or_not;
		Robot rob = new Robot();
		WebElement mainWindow = driver.findElement(By.xpath("//*[@LocalizedControlType='window' and contains(@Name,'Calendar')]"));
		System.out.println("* Pitaemsa naiti miting by name");Thread.sleep(10000);
		try {
			btnCreatedMeeting = mainWindow.findElement(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]"));
			btnCreatedMeeting.click();
			
			
		//btnCreatedMeeting = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]")));
		//btnCreatedMeeting.click();
			
			one_or_not = openRecurrenceTypeTopMenu(rt); 
		if(one_or_not) { System.out.println("* Type = "+one_or_not);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_TAB);		robot.keyRelease(KeyEvent.VK_TAB); robot.delay(250);
			robot.keyPress(KeyEvent.VK_TAB);		robot.keyRelease(KeyEvent.VK_TAB); robot.delay(250);
			robot.keyPress(KeyEvent.VK_TAB);		robot.keyRelease(KeyEvent.VK_TAB); robot.delay(250);
			robot.keyPress(KeyEvent.VK_TAB);		robot.keyRelease(KeyEvent.VK_TAB); robot.delay(250);
			pasteString(tn); robot.delay(500);
			rob.keyPress(KeyEvent.VK_ALT); 
			rob.keyPress(KeyEvent.VK_S);
			rob.keyRelease(KeyEvent.VK_S);rob.keyRelease(KeyEvent.VK_ALT);rob.delay(500);
			List<WebElement> list = driver.findElements(By.xpath("//*[@LocalizedControlType='Dialog' and contains(@Name,'Microsoft Outlook')]"));
			if(list.size()>0) {
				rob.keyPress(KeyEvent.VK_RIGHT);
			rob.keyRelease(KeyEvent.VK_RIGHT); rob.delay(250);
			rob.keyPress(KeyEvent.VK_ENTER);
			rob.keyRelease(KeyEvent.VK_ENTER);rob.delay(250);
			}
			else {}
			
		}
		
		else
		{System.out.println("* Type = "+one_or_not);
		
			rob.keyPress(KeyEvent.VK_CONTROL); rob.keyPress(KeyEvent.VK_G); 
			rob.keyRelease(KeyEvent.VK_G); rob.keyRelease(KeyEvent.VK_CONTROL);rob.delay(250);
			rob.keyPress(KeyEvent.VK_CONTROL); rob.keyPress(KeyEvent.VK_A); 
			rob.keyRelease(KeyEvent.VK_A); rob.keyRelease(KeyEvent.VK_CONTROL);rob.delay(250);
			
			pasteString(tn);rob.delay(250);
			rob.keyPress(KeyEvent.VK_ALT); rob.keyPress(KeyEvent.VK_S); 
			rob.keyRelease(KeyEvent.VK_S); rob.keyRelease(KeyEvent.VK_ALT);rob.delay(250);
			
			//pasteString(newDay);rob.delay(250);
			rob.keyPress(KeyEvent.VK_ENTER); rob.keyRelease(KeyEvent.VK_ENTER); rob.delay(500);
			
			rob.keyPress(KeyEvent.VK_ENTER); rob.keyRelease(KeyEvent.VK_ENTER); rob.delay(500);
			
			rob.keyPress(KeyEvent.VK_ALT); 
			rob.keyPress(KeyEvent.VK_S);
			rob.keyRelease(KeyEvent.VK_S);
			rob.keyRelease(KeyEvent.VK_ALT);rob.delay(500);
			
				/*
				 * rob.keyPress(KeyEvent.VK_RIGHT); rob.keyRelease(KeyEvent.VK_RIGHT);
				 * rob.delay(250); rob.keyPress(KeyEvent.VK_ENTER);
				 * rob.keyRelease(KeyEvent.VK_ENTER);rob.delay(250);
				 */
			
		 
		}
//********************************************************************************************************************************************			
			
	//	sendUpdate = driver.findElement(By.xpath("//*[contains(@Name,'Send Update')]"));
	//	sendUpdate.click();
		
		List<WebElement> popup = driver.findElements(By.xpath("//*[contains(@Name,'Send Anyway')]"));
		if(popup.size() > 0) {WebElement confirmation = driver.findElement(By.xpath("//*[contains(@Name,'Send Anyway')]"));
		confirmation.click();}
		
		return true;}
		catch(Exception ex){return false;}
		
		
		
	}
	/* Update recurring meeting date\time */ //ReoccurringTest
	/**
	 * @param meetingNameInTheList - Meeting name
	 * @param rt - Recurrence type (one of all, All)
	 * @param timeNew - Hour (counted from the current moment)
	 * @param min - Minute/s (counted from the current moment)
	 * @return
	 * @throws AWTException 
	 */
	public boolean updateReoccurringMeeting(String meetingNameInTheList, OpenRecurrenceType rt, int timeNew, int min, int day) throws AWTException {
		String tn = createTime(timeNew, min);
		Robot rob = new Robot();
		String newDay = createDate(day);
		//WebElement mainWindow = driver.findElement(By.xpath("//*[@LocalizedControlType='window' and contains(@Name,'Calendar')]"));
		
		try {
			btnCreatedMeeting = driver.findElement(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]"));
			btnCreatedMeeting.click();
				openRecurrenceTypeTopMenu(rt);
				Thread.sleep(2000);
				switch(rt) {
				case OPEN_OCCURENCE : 
					rob.keyPress(KeyEvent.VK_TAB); rob.keyRelease(KeyEvent.VK_TAB); rob.delay(500);
					rob.keyPress(KeyEvent.VK_TAB); rob.keyRelease(KeyEvent.VK_TAB); rob.delay(500);
					rob.keyPress(KeyEvent.VK_TAB); rob.keyRelease(KeyEvent.VK_TAB); rob.delay(500);
					rob.keyPress(KeyEvent.VK_TAB); rob.keyRelease(KeyEvent.VK_TAB); rob.delay(500);
					rob.keyPress(KeyEvent.VK_DELETE); rob.keyRelease(KeyEvent.VK_DELETE); rob.delay(500);
					pasteString(tn);
					break;  
				case OPEN_SERIES :
					rob.keyPress(KeyEvent.VK_CONTROL); rob.keyPress(KeyEvent.VK_G); 
					rob.keyRelease(KeyEvent.VK_G); rob.keyRelease(KeyEvent.VK_CONTROL);rob.delay(500);
					rob.keyPress(KeyEvent.VK_CONTROL); rob.keyPress(KeyEvent.VK_A); 
					rob.keyRelease(KeyEvent.VK_A); rob.keyRelease(KeyEvent.VK_CONTROL);rob.delay(250);
					
					pasteString(tn);rob.delay(250);
					rob.keyPress(KeyEvent.VK_ALT); rob.keyPress(KeyEvent.VK_S); 
					rob.keyRelease(KeyEvent.VK_S); rob.keyRelease(KeyEvent.VK_ALT);rob.delay(250);
					
					pasteString(newDay);rob.delay(250);
					rob.keyPress(KeyEvent.VK_ENTER); rob.keyRelease(KeyEvent.VK_ENTER); rob.delay(500);
					
					rob.keyPress(KeyEvent.VK_ENTER); rob.keyRelease(KeyEvent.VK_ENTER); rob.delay(500);
					
					
					
					break;
					
				}
		
		
		
			
	//	sendUpdate = driver.findElement(By.xpath("//*[contains(@Name,'Send Update')]"));
	//	sendUpdate.click();
		rob.keyPress(KeyEvent.VK_ALT); 
		rob.keyPress(KeyEvent.VK_S);
		rob.keyRelease(KeyEvent.VK_S);
		rob.keyRelease(KeyEvent.VK_ALT);rob.delay(500);
		List<WebElement> popup = driver.findElements(By.xpath("//*[contains(@Name,'Send Anyway')]"));
		if(popup.size() > 0) {WebElement confirmation = driver.findElement(By.xpath("//*[contains(@Name,'Send Anyway')]"));
		confirmation.click();}
		
		return true;}
		catch(Exception ex){return false;}
		
	}
	
	/** Ok
	 * @param meetingNameInTheList - Meeting name
	 * @param rt - Recurrence type (one of all, All)
	 * @param timeNew - Hour (counted from the current moment)
	 * @param min - Minute/s (counted from the current moment)
	 * @return
	 * @throws AWTException 
	 */
	public boolean updateReoccurringMeetingAndDay(String meetingNameInTheList, OpenRecurrenceType rt, int timeNew, int min, int day) throws AWTException {
		String tn = createTime(timeNew, min);
		boolean flag;
		Robot rob = new Robot();
		String newDay = createDate(day);
		
		try {
			btnCreatedMeeting = driver.findElement(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]"));
			btnCreatedMeeting.click();
			
		openRecurrenceTypeTopMenu(rt); 
		switch(rt) {
		case OPEN_OCCURENCE:
			rob.keyPress(KeyEvent.VK_TAB); rob.keyRelease(KeyEvent.VK_TAB); rob.delay(500);
			rob.keyPress(KeyEvent.VK_TAB); rob.keyRelease(KeyEvent.VK_TAB); rob.delay(500);
			rob.keyPress(KeyEvent.VK_TAB); rob.keyRelease(KeyEvent.VK_TAB); rob.delay(500);
			pasteString(newDay);
			rob.keyPress(KeyEvent.VK_TAB); rob.keyRelease(KeyEvent.VK_TAB); rob.delay(500);
			rob.keyPress(KeyEvent.VK_DELETE); rob.keyRelease(KeyEvent.VK_DELETE); rob.delay(500);
			pasteString(tn);
			rob.keyPress(KeyEvent.VK_ALT); 
			rob.keyPress(KeyEvent.VK_S);
			rob.keyRelease(KeyEvent.VK_S);
			rob.keyRelease(KeyEvent.VK_ALT);rob.delay(500);
			rob.keyPress(KeyEvent.VK_ENTER); // do u want to change just this one?
			rob.keyRelease(KeyEvent.VK_ENTER);rob.delay(500);
			rob.keyPress(KeyEvent.VK_RIGHT); 
			rob.keyRelease(KeyEvent.VK_RIGHT);rob.delay(500);
			rob.keyPress(KeyEvent.VK_ENTER); 
			rob.keyRelease(KeyEvent.VK_ENTER);rob.delay(500);
			break;
		case OPEN_SERIES:
			rob.keyPress(KeyEvent.VK_CONTROL);
			rob.keyPress(KeyEvent.VK_G);
			rob.keyRelease(KeyEvent.VK_G);
			rob.keyRelease(KeyEvent.VK_CONTROL); rob.delay(1000);
			pasteString(tn);rob.delay(500);
			rob.keyPress(KeyEvent.VK_ALT); 
			rob.keyPress(KeyEvent.VK_S);
			rob.keyRelease(KeyEvent.VK_S);
			rob.keyRelease(KeyEvent.VK_ALT);rob.delay(500);
			rob.keyPress(KeyEvent.VK_ENTER);
			rob.keyRelease(KeyEvent.VK_ENTER);rob.delay(500);
			rob.keyPress(KeyEvent.VK_ALT); 
			rob.keyPress(KeyEvent.VK_S);
			rob.keyRelease(KeyEvent.VK_S);
			rob.keyRelease(KeyEvent.VK_ALT);rob.delay(500);
		break;
		}
		rob.delay(500);
		 List<WebElement> errors = driver.findElements(By.xpath("//*[contains(@Name,'Send Meeting Error')]")); 
				if(errors.size()>0) {
					rob.keyPress(KeyEvent.VK_ENTER);
					rob.keyRelease(KeyEvent.VK_ENTER);rob.delay(500);

					rob.keyPress(KeyEvent.VK_ALT);
					rob.keyPress(KeyEvent.VK_F4);
					rob.keyRelease(KeyEvent.VK_F4);
					rob.keyRelease(KeyEvent.VK_ALT);rob.delay(500);
					
					rob.keyPress(KeyEvent.VK_ENTER);
					rob.keyRelease(KeyEvent.VK_ENTER);rob.delay(500);
					flag = true;
					return flag;
				}
				else {flag = true;
					return flag;}
		
		
		
		}
		catch(Exception ex){return false;}
		
		
	}
	
	/* Fast Cancellation Recurring Meeting*/
	 public boolean fastCancelReoccurringMeeting(CancelRecurrence recurrenceType , String meetingNameInTheList) throws AWTException {
		 Robot rob = new Robot();
		 btnCreatedMeeting = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]")));
		 btnCreatedMeeting.click();
		 rob.keyPress(KeyEvent.VK_DELETE);
		 rob.keyRelease(KeyEvent.VK_DELETE);rob.delay(500);
		 try {
		 switch (recurrenceType) {
			case CANCEL_OCCURRENCE : 
	rob.keyPress(KeyEvent.VK_ENTER); break;
			case CANCEL_SERIES :
				rob.keyPress(KeyEvent.VK_TAB); rob.keyRelease(KeyEvent.VK_TAB);rob.delay(500);
				rob.keyPress(KeyEvent.VK_TAB); rob.keyRelease(KeyEvent.VK_TAB);rob.delay(500);
				rob.keyPress(KeyEvent.VK_DOWN); rob.keyRelease(KeyEvent.VK_DOWN);rob.delay(500);
				rob.keyPress(KeyEvent.VK_TAB); rob.keyRelease(KeyEvent.VK_TAB);rob.delay(500);
				rob.keyPress(KeyEvent.VK_ENTER); rob.keyRelease(KeyEvent.VK_ENTER);rob.delay(3000);
				rob.keyPress(KeyEvent.VK_ALT);
				rob.keyPress(KeyEvent.VK_S);
				rob.keyRelease(KeyEvent.VK_S);
				rob.keyRelease(KeyEvent.VK_ALT); rob.delay(500);
				break;
		 }
		 return true;}
		 catch(Exception e) {return false;}
		 
	 }
	/* Delete Recurring Meeting */
	public boolean cancelReoccurringMeeting(CancelRecurrence recurrenceType , String meetingNameInTheList) throws InterruptedException {
		String parametrItem = null;
		WebElement _type;
		btnCreatedMeeting = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]")));
		btnCreatedMeeting.click();
		
		Thread.sleep(1000);
		List<WebElement> list = driver.findElements(By.name("Cancel Meeting"));
		if(list.size()>0) {
			btnCancelMeeting = driver.findElement(By.name("Cancel Meeting"));
			btnCancelMeeting.click();Thread.sleep(1000);
			/*
			 * btnSendCancellation = driver.findElement(By.name("Send Cancellation"));
			 * btnSendCancellation.click();
			 */
		}
		try {
			Robot robot = new Robot();
		switch (recurrenceType) {
		case CANCEL_OCCURRENCE : 
robot.keyPress(KeyEvent.VK_DOWN);
robot.keyRelease(KeyEvent.VK_DOWN);robot.delay(500);
robot.keyPress(KeyEvent.VK_ENTER);
robot.keyRelease(KeyEvent.VK_ENTER);
robot.delay(2000);
robot.keyPress(KeyEvent.VK_ALT);
robot.keyPress(KeyEvent.VK_S);
robot.keyRelease(KeyEvent.VK_S);
robot.keyRelease(KeyEvent.VK_ALT);
		//case CANCEL_OCCURRENCE : parametrItem = "Cancel Occurrence"; _type = driver.findElement(By.xpath("//*[@Name='"+parametrItem+"']"));
		//_type.click(); 
		break;
		case CANCEL_SERIES : 
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);robot.delay(500);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);robot.delay(500);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);robot.delay(2000);
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_ALT);
				/*
				 * parametrItem = "Cancel Series"; _type =
				 * driver.findElement(By.xpath("//*[@Name='"+parametrItem+"']")); _type.click();
				 */ 
		
		//btnSendCancellation = driver.findElement(By.name("Send Cancellation"));
		//btnSendCancellation.click();
		break;
		} return true;}
		catch(Exception e) {return false;}
		
	}
	public void reminderDismissAll() {
		List<WebElement> dismissList = driver.findElements(By.xpath("//*[@Name='Dismiss All']"));
		if(dismissList.size()>0) {
			WebElement dismiss = driver.findElement(By.xpath("//*[@Name='Dismiss All']"));
			dismiss.click();
		}
		 
	}
	/*Click OK button*/
	public void clickOK() {
		WebElement confirmBtn = driver.findElement(By.xpath("//*[@Name='OK']"));
		confirmBtn.click();
	}
	/*Click Send button*/
	public void clickSend() {
		WebElement btnSend = driver.findElement(By.xpath("//*[@Name='Send']"));
		btnSend.click();
	}
	
	/* MeetingID from meeting body */
	public String getMeetingID(String innerText) {
		String value = null;
		Pattern pattern = Pattern.compile("\\d+(\\#)");
	    String word = innerText; 
	    Matcher matcher = pattern.matcher(word);
	    int start = 0;
	    while (matcher.find(start)) {
	       value = word.substring(matcher.start(), matcher.end());
	       //int result = Integer.parseInt(value);
	       System.out.println(value);
	       start = matcher.end();
	    }
		return value;
	}
	public String specifyMeetingPropertiesWithoutGettingID(String mail) throws InterruptedException, AWTException {
		Thread.sleep(4000);
		Robot robot = new Robot();
		
		pasteString(mail);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);						
		pasteString(TEST_TITLE);robot.delay(500);
		
	return TEST_TITLE;

}
	public boolean schedule24HoursMeeting(String mail, int endDay) throws AWTException, InterruptedException {
		Thread.sleep(4000);
		boolean flag = false;
		Robot robot = new Robot();
		pasteString(mail); robot.keyPress(KeyEvent.VK_RIGHT); robot.delay(250); robot.keyPress(KeyEvent.VK_TAB); 
		pasteString(TEST_TITLE); robot.delay(250);
		 robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  pasteString(createDate(endDay));
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(500);
		  robot.keyPress(KeyEvent.VK_ALT); robot.keyPress(KeyEvent.VK_S);
		  robot.keyRelease(KeyEvent.VK_S); robot.keyRelease(KeyEvent.VK_ALT);
		  robot.delay(500);
		  List<WebElement> sendMeetingError = driver.findElements(By.xpath("//*[@Name='Send Meeting Error']"));
		  if(sendMeetingError.size()>0) {flag = true;}
		  else {flag = false;}
		  robot.keyPress(KeyEvent.VK_ENTER);  robot.keyRelease(KeyEvent.VK_ENTER);robot.delay(250);
		  alt_F4(); robot.delay(500);
		  robot.keyPress(KeyEvent.VK_ENTER);  robot.keyRelease(KeyEvent.VK_ENTER);robot.delay(250);
		  return flag;
	}
	
	
	public ArrayList<Object> createAllDayMeeting(String mail, boolean allDayParametr, boolean deleteOrNotAfter) throws AWTException, InterruptedException {
		Thread.sleep(4000);
		ArrayList<Object> listResult = new ArrayList<Object>();
		listResult.add(TEST_TITLE);
		boolean flag = false;
		Robot robot = new Robot();
		pasteString(mail); robot.keyPress(KeyEvent.VK_RIGHT); robot.delay(250); robot.keyPress(KeyEvent.VK_TAB); 
								
		pasteString(TEST_TITLE); robot.delay(250);
		System.out.println("Meeting name "+ TEST_TITLE);
		
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  if(allDayParametr) {
			  	robot.keyPress(KeyEvent.VK_SPACE); robot.delay(250);
			  	flag = true;
		  }
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  WebElement newbodyDocument = (new WebDriverWait(driver, 4)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@LocalizedControlType='document' and contains(@Name,'"+TEST_TITLE+"')]")));
			String txtBodyNew = newbodyDocument.getText();
			String MeetingID = getMeetingID(txtBodyNew);
		  robot.keyPress(KeyEvent.VK_ALT); robot.keyPress(KeyEvent.VK_S);
		  robot.keyRelease(KeyEvent.VK_S); robot.keyRelease(KeyEvent.VK_ALT);
		  robot.delay(500);
		  robot.keyPress(KeyEvent.VK_F9);
		  robot.keyRelease(KeyEvent.VK_F9);
		  	
		  	listResult.add(MeetingID);
		  List<WebElement> createdMeeting = driver.findElements(By.xpath("//*[contains(@Name,'"+TEST_TITLE+"')]"));
		  if (createdMeeting.size()>0) {flag = true;}
		  		else {flag = false;}
		  	listResult.add(flag);
		  	if(deleteOrNotAfter) {WebElement meetingAllDay = driver.findElement(By.xpath("//*[@LocalizedControlType='list item' and contains(@Name,'"+TEST_TITLE+"')]"));
		  	meetingAllDay.click();
		  	 robot.keyPress(KeyEvent.VK_DELETE); 
			  robot.keyRelease(KeyEvent.VK_DELETE); robot.delay(500);
			  robot.keyPress(KeyEvent.VK_ALT); robot.keyPress(KeyEvent.VK_S);
			  robot.keyRelease(KeyEvent.VK_S); robot.keyRelease(KeyEvent.VK_ALT);
			  robot.delay(500);
		  	}
		  	else {}
		  		return listResult;
		  
	}
	
	public boolean specifyMeetingPropertiesAndDeleteMeetingID(String mail) throws InterruptedException, AWTException, IOException {
		Thread.sleep(4000);
		boolean flag = false;
		Robot robot = new Robot();
		pasteString(mail); robot.keyPress(KeyEvent.VK_RIGHT); robot.delay(250); robot.keyPress(KeyEvent.VK_TAB); 
								
		pasteString(TEST_TITLE); robot.delay(250);
		System.out.println("Meeting name "+ TEST_TITLE);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_TAB); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_DOWN); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_DOWN); robot.delay(250);
		  
		  robot.keyPress(KeyEvent.VK_CONTROL);robot.keyPress(KeyEvent.VK_DELETE); 
		   robot.keyRelease(KeyEvent.VK_DELETE); robot.keyRelease(KeyEvent.VK_CONTROL); //robot.delay(250);
		   robot.keyPress(KeyEvent.VK_CONTROL);robot.keyPress(KeyEvent.VK_DELETE); 
		   robot.keyRelease(KeyEvent.VK_DELETE); robot.keyRelease(KeyEvent.VK_CONTROL); //robot.delay(250);
		   robot.keyPress(KeyEvent.VK_CONTROL);robot.keyPress(KeyEvent.VK_DELETE); 
		   robot.keyRelease(KeyEvent.VK_DELETE); robot.keyRelease(KeyEvent.VK_CONTROL); //robot.delay(250);
		   robot.keyPress(KeyEvent.VK_CONTROL);robot.keyPress(KeyEvent.VK_DELETE); 
		   robot.keyRelease(KeyEvent.VK_DELETE); robot.keyRelease(KeyEvent.VK_CONTROL); //robot.delay(250);
		   robot.keyPress(KeyEvent.VK_CONTROL);robot.keyPress(KeyEvent.VK_DELETE); 
		   robot.keyRelease(KeyEvent.VK_DELETE); robot.keyRelease(KeyEvent.VK_CONTROL); //robot.delay(250);
		   
		/*
		 * WebElement el = (new WebDriverWait(driver,
		 * 4)).until(ExpectedConditions.presenceOfElementLocated(By.
		 * xpath("//*[@LocalizedControlType='document' and contains(@Name,'"+TEST_TITLE+
		 * "')]"))); String str = el.getText().replaceAll("\\d+(\\#)", ""); //String str
		 * = el.getAttribute("Text").replaceFirst("\\d+(\\#)", "");
		 * robot.keyPress(KeyEvent.VK_CONTROL); robot.keyPress(KeyEvent.VK_A);
		 * robot.keyRelease(KeyEvent.VK_A); robot.keyRelease(KeyEvent.VK_CONTROL);
		 * robot.delay(250); robot.keyPress(KeyEvent.VK_DELETE);
		 * robot.keyRelease(KeyEvent.VK_DELETE); robot.delay(250); pasteString(str);
		 */
		
		 Thread.sleep(250);
		//Object str = el.getText().replaceAll("\\d+(\\#)", "");
		//System.out.println("RESULT : "+str);
		/*
		  robot.keyPress(KeyEvent.VK_CONTROL); robot.keyPress(KeyEvent.VK_A);
		  robot.keyRelease(KeyEvent.VK_A); robot.keyRelease(KeyEvent.VK_CONTROL); robot.delay(250);
		  robot.keyPress(KeyEvent.VK_DELETE); robot.keyRelease(KeyEvent.VK_DELETE);
		  pasteString((String) str); robot.delay(250);
		*/
		  /*
		  
		 */
		// WebElement newbodyDocument = (new WebDriverWait(driver, 4)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@LocalizedControlType='document' and contains(@Name,'"+TEST_TITLE+"')]")));
/*	  
		 String sstr = newbodyDocument.getText();
		  StringBuffer sb = new StringBuffer();
	        // Pattern p = Pattern.compile("^[(].*[)]$");
	        Pattern p = Pattern.compile("\\d+(\\#)");
	        String [] str = sstr.split(" ");
	        for (String string : str) {
	        Matcher m = p.matcher(string);
	            if(!m.matches()){
	                sb.append(string);
	                sb.append(" ");
	            }
	        }
	        System.out.println(sb.toString());
	        String body = sb.toString();
*/
		  //Selenium selenium = null;
		  //selenium.shiftKeyDown();
		  
		 
		  // Runtime.getRuntime().exec("rundll32 user32.dll,LockWorkStation");
		  
		/*
		 * Actions actions = new Actions(driver);
		 * actions.sendKeys(Keys.chord(Keys.SHIFT,Keys.ARROW_DOWN)).build().perform();
		 */
	        
			// actions.moveToElement(newbodyDocument, 2, 2).click().build().perform(); robot.delay(250);
		  robot.delay(1000);
		
		  robot.keyPress(KeyEvent.VK_ALT); robot.keyPress(KeyEvent.VK_S);
		  robot.keyRelease(KeyEvent.VK_S); robot.keyRelease(KeyEvent.VK_ALT);
		  robot.delay(500);
		 
		List<WebElement> sendMeetingError = driver.findElements(By.xpath("//*[@Name='Send Meeting Error']"));
		if (sendMeetingError.size() > 0) {
			robot.keyPress(KeyEvent.VK_ENTER); robot.delay(250);
			alt_F4();
			robot.keyPress(KeyEvent.VK_ENTER); robot.delay(250);			flag = true;}
		else {flag = false;}
		
	
	return flag;

}

	
	/*Original*/
		public ArrayList<String> specifyMeetingProperties(String mail) throws InterruptedException, AWTException {
			Thread.sleep(4000);
			Robot robot = new Robot();
			//robot.setAutoDelay(1000);
			pasteString(mail); robot.keyPress(KeyEvent.VK_RIGHT);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.delay(500);
			//robot.keyPress(KeyEvent.VK_TAB);						
			pasteString(TEST_TITLE);
			
			WebElement newbodyDocument = (new WebDriverWait(driver, 4)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@LocalizedControlType='document' and contains(@Name,'"+TEST_TITLE+"')]")));
			String txtBodyNew = newbodyDocument.getText();
			String MeetingID = getMeetingID(txtBodyNew);
			ArrayList<String> meetingNameAndID = new ArrayList<String>();
			meetingNameAndID.add(TEST_TITLE);
			meetingNameAndID.add(MeetingID);
		
			//createDate(1);
		
		
		
		//txtTo.sendKeys(Keys.TAB); 
		//Thread.sleep(1000); // Dolphie.Lobo@amdocs.com
		
		return meetingNameAndID;

	}
		public void changeDate(int number) throws AWTException {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_TAB); robot.delay(500);
			robot.keyPress(KeyEvent.VK_TAB); robot.delay(500);
			robot.keyPress(KeyEvent.VK_TAB); robot.delay(500);
			String newStertDate = createDate(number);
			pasteString(newStertDate);
			robot.keyPress(KeyEvent.VK_TAB);
		}
		public String specifyMeetingPropertiesReturnID(String mail) throws InterruptedException, AWTException {
			Thread.sleep(3000);
			Robot robot = new Robot();
			String meetingName = TEST_TITLE;
			pasteString(mail);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.delay(500);
			robot.keyPress(KeyEvent.VK_TAB);						
			pasteString(meetingName);robot.delay(1000);
			
		
		return meetingName;
		}
		public String specifyMeetingPropertiesReturnName(String mail, int minPlus) throws InterruptedException, AWTException {
			Thread.sleep(3000);
			Robot robot = new Robot();
			String meetingName = TEST_TITLE;
			pasteString(mail); robot.keyPress(KeyEvent.VK_RIGHT); robot.keyRelease(KeyEvent.VK_RIGHT);
			robot.delay(500);
			//robot.keyPress(KeyEvent.VK_TAB); robot.keyRelease(KeyEvent.VK_TAB); robot.delay(250);
			robot.keyPress(KeyEvent.VK_TAB); robot.keyRelease(KeyEvent.VK_TAB);	robot.delay(500);					
			pasteString(meetingName);robot.delay(1000);
			if(minPlus!=0) {
				robot.keyPress(KeyEvent.VK_TAB); robot.keyRelease(KeyEvent.VK_TAB);	robot.delay(250);
				robot.keyPress(KeyEvent.VK_TAB); robot.keyRelease(KeyEvent.VK_TAB);	robot.delay(250);
				robot.keyPress(KeyEvent.VK_TAB); robot.keyRelease(KeyEvent.VK_TAB);	robot.delay(250);
				robot.keyPress(KeyEvent.VK_TAB); robot.keyRelease(KeyEvent.VK_TAB);	robot.delay(250);
				String newTime = createTime(0, minPlus);
				pasteString(newTime);robot.delay(1000);
			}
			
		
		return meetingName;

	}
		// for load testing
		public List<String> specifyLoadMeeting(String mail, int minPlus) throws InterruptedException, AWTException {
			Thread.sleep(3000);
			
			List<String> resultList = new ArrayList<String>();
			
			Robot robot = new Robot();
			
			pasteString(mail); robot.keyPress(KeyEvent.VK_RIGHT); robot.keyRelease(KeyEvent.VK_RIGHT);
			robot.delay(500);
			//robot.keyPress(KeyEvent.VK_TAB); robot.keyRelease(KeyEvent.VK_TAB); robot.delay(250);
			robot.keyPress(KeyEvent.VK_TAB); robot.keyRelease(KeyEvent.VK_TAB);	robot.delay(500);					
			pasteString(TEST_TITLE);robot.delay(1000);
			robot.keyPress(KeyEvent.VK_TAB); robot.keyRelease(KeyEvent.VK_TAB);	robot.delay(250);
			WebElement bodyDocument = (new WebDriverWait(driver, 1)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@LocalizedControlType='document' and contains(@Name,'"+TEST_TITLE+"')]")));
			String txtBody = bodyDocument.getText();
			String oldMeetingID = getMeetingID(txtBody);
			resultList.add(TEST_TITLE);
			resultList.add(oldMeetingID);
			if(minPlus!=0) {
				
				robot.keyPress(KeyEvent.VK_TAB); robot.keyRelease(KeyEvent.VK_TAB);	robot.delay(250);
				robot.keyPress(KeyEvent.VK_TAB); robot.keyRelease(KeyEvent.VK_TAB);	robot.delay(250);
				robot.keyPress(KeyEvent.VK_TAB); robot.keyRelease(KeyEvent.VK_TAB);	robot.delay(250);
				String newTime = createTime(0, minPlus);
				pasteString(newTime);robot.delay(1000);
			}
			
		
		return resultList;

	}
		public void exitAndSendLaterOrNot() throws InterruptedException, AWTException {
			List<WebElement> exitList = driver.findElements(By.xpath("//*[contains(@Name,'Exit and Send Later')]"));
			if(exitList.size()>0) {
				WebElement buttonWarning = driver.findElement(By.xpath("//*[contains(@Name,'Don't Exit')]"));
				buttonWarning.click();
				chooseTopMenuItem(TopMenu.SEND_RECEIVE);
				Robot robot = new Robot();
				robot.delay(1000);
				robot.keyPress(KeyEvent.VK_F9);
				robot.keyRelease(KeyEvent.VK_F9);
			}
		}
		
	public void specifyLocation(String location) {
		txtLocation = (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@AutomationId='4102']")));
		txtLocation.sendKeys(location);
	}
	// zakrit tekushuu vkladku skype
	public void closeSkypeWindows() {
		List<WebElement> skypeWindowsList = driver.findElements(By.xpath("//*[contains(@Name,'Close Current Tab')]"));
		if(skypeWindowsList.size()>0) {
			WebElement btnCloseCurrent = driver.findElement(By.xpath("//*[contains(@Name,'Close Current Tab')]"));
			btnCloseCurrent.click();
		}
		
	}
	public boolean checkMeetingExistance(String meetingName) {
		List<WebElement> meetingList = driver.findElements(By.xpath("//*[contains(@Name,'"+meetingName+"')]"));
		meetingList.forEach((element) -> System.out.println("ELEMENT = "+element.getText()));
		if(meetingList.size()>0) {return true;}
		else {return false;}
	}

	public void swithWindowDesktopApp() {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));

		driver.switchTo().frame(1);
	}
public void clickCreateNewMessage() throws InterruptedException {
	btnNewEmail = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.name("New Email")));
	btnNewEmail.click();
		/*
		 * Actions builder = new Actions(driver);
		 * builder.sendKeys(Keys.chord(Keys.CONTROL,"N")).perform();
		 */ 
    Thread.sleep(1000);
}
public String specifyNewMessageInfo(String recipient) throws InterruptedException {
	String restTitle = "TEST_SUBJECT_"+getRandom();
	txtSubjectMessage = (new WebDriverWait(driver, 2)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@AutomationId='4101']")));
	txtSubjectMessage.sendKeys(restTitle);
	txtMessageTo = (new WebDriverWait(driver, 2)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@AutomationId='4099']")));
	txtMessageTo.sendKeys(recipient);
	btnSend = driver.findElement(By.name("Send"));
	btnSend.click();Thread.sleep(1000);
	return restTitle;
}
	
	public boolean amdocsMeetingCancellation(String meetingNameInTheList) throws InterruptedException, AWTException {
		boolean flag = false;
		
	//	reminderDismissAll();
		
		btnCreatedMeeting = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]")));
		btnCreatedMeeting.click();
		Thread.sleep(2000);
		try {
		Robot rob = new Robot();
			rob.keyPress(KeyEvent.VK_DELETE);
			rob.keyRelease(KeyEvent.VK_DELETE);
			rob.delay(2000);
			rob.keyPress(KeyEvent.VK_ALT);
			rob.keyPress(KeyEvent.VK_S);
			rob.keyRelease(KeyEvent.VK_S);
			rob.keyRelease(KeyEvent.VK_ALT);
			rob.delay(1000);
			
		//List<WebElement> list = driver.findElements(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]"));
		if(btnCreatedMeeting.isSelected()) {System.out.println("Meeting button - "+btnCreatedMeeting.isSelected());
			/*
			 * btnCancelMeeting = driver.findElement(By.name("Cancel Meeting"));
			 * btnCancelMeeting.click();Thread.sleep(1000); btnSendCancellation =
			 * driver.findElement(By.name("Send Cancellation"));
			 * btnSendCancellation.click();
			 */
			flag = false;
		}
		
		else { flag = true;}}
			catch(Exception e) {}
			return flag;
		
	}
	// Delete by one iteration
	public boolean amdocsMeetingCancellationOneIteration(String meetingNameInTheList) throws InterruptedException, AWTException {
		boolean flag = true;
		
	//	reminderDismissAll();
		//chooseNewMenuItem(LeftBottomMenu.CALENDAR);
		moveOut(10, 10);
		btnCreatedMeeting = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]")));
		btnCreatedMeeting.click();
		Thread.sleep(1000);
		try {
		Robot rob = new Robot();
			rob.keyPress(KeyEvent.VK_DELETE);
			rob.keyRelease(KeyEvent.VK_DELETE);
			rob.delay(2000);
			rob.keyPress(KeyEvent.VK_ALT);
			rob.keyPress(KeyEvent.VK_S);
			rob.keyRelease(KeyEvent.VK_S);
			rob.keyRelease(KeyEvent.VK_ALT);
			rob.delay(1000);
	/*		
		//List<WebElement> list = driver.findElements(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]"));
		if(!btnCreatedMeeting.isDisplayed()) {System.out.println("Meeting button - "+btnCreatedMeeting.isSelected());
		*/
			/*
			 * btnCancelMeeting = driver.findElement(By.name("Cancel Meeting"));
			 * btnCancelMeeting.click();Thread.sleep(1000); btnSendCancellation =
			 * driver.findElement(By.name("Send Cancellation"));
			 * btnSendCancellation.click();
			 */
			flag = true;
		
		/*else { flag = false;}
		*/
		
		}
			catch(Exception e) {flag = false;}
		
		return flag;
		//return true;
	}
	
	/* Switch between Test/Production environment */
	public void _ChangeEnvironment(boolean _TestEnvironment) {
		if (_TestEnvironment) {
			copyFileOrFolder(_TEST_ENV_CONFIG, _APP_PATH, _FILE_NAME);
			}
		else {
		copyFileOrFolder(_PRODUCTION_ENV_CONFIG, _APP_PATH, _FILE_NAME);
		}
	}
	
	
	/* (pathSource_ - full path with file name, pathDetination_ - config folder, fileName - new file name )*/
	public String copyFileOrFolder(String pathSource_, String pathDetination_, String fileName) {
		Path pathSource = Paths.get(pathSource_); 
		Path pathDestination = Paths.get(pathDetination_+fileName);
		try { 
            Files.copy(pathSource, pathDestination, StandardCopyOption.REPLACE_EXISTING); 
            System.out.println("Source file copied successfully"); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
		 return fileName;
	}
	 /***************** 
	  * Random method *
	  *****************/
		public int getRandom() {Random rand = new Random(); 
		int value = rand.nextInt(100000);
		return value;}
}
