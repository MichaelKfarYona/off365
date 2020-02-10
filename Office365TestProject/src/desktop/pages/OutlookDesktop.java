package desktop.pages;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.WiniumDriver;

import com.sun.jna.platform.win32.User32;

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
	
	WiniumDriver driver;
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
	public void openRecurrenceTypeTopMenu(OpenRecurrenceType type) throws AWTException {
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
			rob.keyRelease(KeyEvent.VK_ENTER);  
			break;
		case OPEN_SERIES : 
			rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB); rob.delay(500);
			rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(500);
			rob.keyPress(KeyEvent.VK_DOWN);		rob.keyRelease(KeyEvent.VK_DOWN);rob.delay(500);
			rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);rob.delay(500);
			rob.keyPress(KeyEvent.VK_ENTER);	rob.keyRelease(KeyEvent.VK_ENTER);  
			break; 
		//case OPEN_OCCURENCE : myType = "Open Occurrence";  break;
		//case OPEN_SERIES : myType = "Open Series"; break;
		}
		//WebElement choosenType = driver.findElement(By.xpath("//*[@Name='"+myType+"']"));
		//choosenType.click();
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
		Thread.sleep(2000);
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
		WebElement leftBottomItem = (new WebDriverWait(driver, 3))
				.until(ExpectedConditions.presenceOfElementLocated(By.name(parametrListItem)));
		leftBottomItem.click();
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
	
	public boolean updateMeetingDate(String meetingName,int day) throws InterruptedException {
		try{clickByMeetingName(meetingName); 
		specifyNewDayN(day);
		clickSendUpdate();
		Robot rob = new Robot();
		List<WebElement> popup = driver.findElements(By.xpath("//*[contains(@Name,'Send Anyway')]"));
		if(popup.size()>0) {
			rob.keyPress(KeyEvent.VK_RIGHT);
			rob.keyRelease(KeyEvent.VK_RIGHT); rob.delay(500);
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
		robot.keyRelease(KeyEvent.VK_ALT);robot.delay(500);
		
		List<WebElement> btnSendAnyway = driver.findElements(By.xpath("//*[contains(@Name,'Send Anyway')]"));
		if(btnSendAnyway.size()>0) {WebElement el = driver.findElement(By.xpath("//*[contains(@Name,'Send Anyway')]"));
		el.click();
		}
		else {}
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
		newAmdocsMeeting = (new WebDriverWait(driver, 3))
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
			robot.delay(3000);
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
		WebElement allNumbers = driver.findElement(By.xpath("//*[@Name='all access numbers']"));
		allNumbers.click(); Thread.sleep(2000);
	}
	public void allowAccess() {
		List<WebElement> list = driver.findElements(By.xpath("//*[@Name='Allow once']"));
		if(list.size()>0) {
		WebElement btnAllowAccess = driver.findElement(By.xpath("//*[@Name='Allow once']"));
		btnAllowAccess.click();}
	}
	public boolean checkCountry(String country) {
		WebElement myCountry = driver.findElement(By.xpath("//tbody/tr[2]/td[1]")); 
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
		Thread.sleep(40000);
		List<WebElement> activeSkypeWindows = driver.findElements(By.xpath("//*[@ClassName='NetUINetUIDialog']")); 
		System.out.println("Active window class : "+activeSkypeWindows.size());
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
	
	public boolean callByNumber(String number, String meetingID) throws IOException, AWTException, InterruptedException {
		
		Process process=Runtime.getRuntime().exec("C:\\Program Files (x86)\\Microsoft Office\\Office16\\lync.exe");
		Robot robot = new Robot();
		robot.delay(2000);
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
		System.out.println("Calling ..... ");
		// Actions act = new Actions(driver);
		// act.click(driver.findElement(By.xpath("//*[@LocalizedControlType='Button' and contains(@Name,'Call')]"))).perform();
		
		WebElement btnCall = driver.findElement(By.xpath("//*[@LocalizedControlType='Button' and contains(@Name,'Call')]"));
		btnCall.click();
		
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
		robot.delay(500);
		//robot.keyPress(KeyEvent.VK_ENTER);
		//robot.keyRelease(KeyEvent.VK_ENTER);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_O);
		robot.keyRelease(KeyEvent.VK_O);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		
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
		robot.keyRelease(KeyEvent.VK_CONTROL);
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
		robot.keyRelease(KeyEvent.VK_ALT);
		pasteString(newDay);
		robot.delay(500);
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		//clickOK();
		
		myRes = checkRecurrenceType();
		btnSendMeeting_ByRobot();
		
		System.out.println("Result - "+ myRes);
		return myRes;
		
	}
	public boolean checkRecurrenceType() {
		List<WebElement> recList = driver.findElements(By.xpath("//*[@Name='Recurrence']"));
		if(recList.size() > 0) {return true;}
		else {return false;}
	}
	/* Update 1 meeting date and time . NOT R ! */
	public ArrayList<String> updateOneMeetingDateAndTime(String meetingName, int hour, int min) throws AWTException {
		String tn = createTime(hour, min);
		String dayToday = createDate(0);
		List<WebElement> eventList = driver.findElements(By.xpath("//*[contains(@Name,'"+meetingName+"')]"));
		if(!(eventList.size() > 0)) {
			Robot robb = new Robot();
			robb.keyPress(KeyEvent.VK_ALT);
			robb.keyPress(KeyEvent.VK_UP);
			robb.keyRelease(KeyEvent.VK_UP);
			robb.keyRelease(KeyEvent.VK_ALT);
		}
		btnMeetingInThePast = driver.findElement(By.xpath("//*[contains(@Name,'"+meetingName+"')]"));
		btnMeetingInThePast.click();
		
		Robot rob = new Robot();
		rob.keyPress(KeyEvent.VK_ENTER);
		rob.keyRelease(KeyEvent.VK_ENTER);
		rob.delay(1000);
		rob.setAutoDelay(1000);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);
		pasteString(dayToday);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);
		pasteString(tn);
		
		// poluchit  new id
		WebElement bodyDocument = (new WebDriverWait(driver, 1)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@LocalizedControlType='document' and contains(@Name,'"+meetingName+"')]")));
		String txtBody = bodyDocument.getText();
		String oldMeetingID = getMeetingID(txtBody);
		System.out.println("Old ID : "+oldMeetingID);
		alt_F4();
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);
		rob.keyPress(KeyEvent.VK_UP);
		rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);
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
	
	/* Vstavka stroki v aktivnoe pole */
	public void pasteString(String anystring) throws AWTException {
		StringSelection stringSelection2 = new StringSelection(anystring);
		Clipboard clipboard2 = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard2.setContents(stringSelection2, stringSelection2);
		Robot rob = new Robot();
		rob.keyPress(KeyEvent.VK_CONTROL);
		rob.keyPress(KeyEvent.VK_V);
		rob.keyRelease(KeyEvent.VK_V);
		rob.keyRelease(KeyEvent.VK_CONTROL);
	}
	public void alt_F4() throws AWTException {
		Robot rob = new Robot();
		rob.keyPress(KeyEvent.VK_ALT);
		rob.keyPress(KeyEvent.VK_F4);
		rob.keyRelease(KeyEvent.VK_F4);
		rob.keyRelease(KeyEvent.VK_ALT);
	}
	/* Update series */
	public boolean updateAllRMeetings(String meetingNameInTheList, RecurrencePattern rpat, int timeNew, int min) {
		String tn = createTime(timeNew, min);
	//	WebElement mainWindow = driver.findElement(By.xpath("//*[@LocalizedControlType='window' and contains(@Name,'Calendar')]"));
		
		try {
			btnCreatedMeeting = driver.findElement(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]"));
			btnCreatedMeeting.click();
			Robot rob = new Robot();
			rob.keyPress(KeyEvent.VK_ENTER);
			rob.keyRelease(KeyEvent.VK_ENTER);
			rob.delay(1000);
			rob.setAutoDelay(1000);
			rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);
			rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);
			rob.keyPress(KeyEvent.VK_DOWN);		rob.keyRelease(KeyEvent.VK_DOWN);
			rob.keyPress(KeyEvent.VK_TAB);		rob.keyRelease(KeyEvent.VK_TAB);
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
			
			String newTime = createTime(1, 5);
			pasteString(newTime);
		//WebElement btnRecurrenceMainCalendar = driver.findElement(By.xpath("//Options/*[contains(@Name,'Recurrence')]"));
		//btnRecurrenceMainCalendar.click();
		//WebElement txtStartTime = driver.findElement(By.xpath("//*[@LocalizedControlType='radio button' and contains(@Name,'Start')]"));
		//txtStartTime.clear();

			chooseReccurrencePattern(rpat);
		
		
		// WebElement txtStartTime = driver.findElement(By.xpath("//*[@LocalizedControlType='edit' and contains(@Name,'Start')]"));
		// txtStartTime.clear();
		// txtStartTime.sendKeys(newTime);
		
		
		clickOK(); 
		rob.keyPress(KeyEvent.VK_ENTER);
		rob.keyRelease(KeyEvent.VK_ENTER);
		//clickOK();
		
		//WebElement sendUpdate = driver.findElement(By.xpath("//*[contains(@Name,'Send Update')]"));
		//sendUpdate.click();
		clickSendUpdate();
		
		exitAndSendLaterOrNot();
		return true;}
		catch(Exception e) {e.getStackTrace(); return false;}
	}
	
	
	/* create before meeting */
	public List<String> createBeforeMeetingInTheFuture(String mail, int hour, int min) throws AWTException, InterruptedException {
		/*novoe okno - mainWindow*/
		Thread.sleep(3000);
		Robot robot = new Robot();
		String newTime = createTime(hour, min);
		String endTime = createTime(hour, min+30);
		pasteString(mail);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);						
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		pasteString(TEST_TITLE);robot.delay(1000);
		robot.keyPress(KeyEvent.VK_TAB);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		pasteString(newTime);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);
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
		
		//robot.delay(1000);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_ALT);
		
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
	 * @param meetingNameInTheList
	 * @param rt
	 * @param timeNew
	 * @param min
	 * @return
	 * @throws AWTException 
	 */
	public boolean updateReoccurringMeeting(String meetingNameInTheList, OpenRecurrenceType rt, int timeNew, int min) throws AWTException {
		String tn = createTime(timeNew, min);
		Robot rob = new Robot();
		WebElement mainWindow = driver.findElement(By.xpath("//*[@LocalizedControlType='window' and contains(@Name,'Calendar')]"));
		
		try {
			btnCreatedMeeting = mainWindow.findElement(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]"));
			btnCreatedMeeting.click();
			
			
		//btnCreatedMeeting = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]")));
		//btnCreatedMeeting.click();
		openRecurrenceTypeTopMenu(rt); 
		
		List<WebElement> timeFieldList = driver.findElements(By.xpath("//edit[contains(@Name,'Start time')]"));
		
		if(timeFieldList.size()>0) {
			
			rob.keyPress(KeyEvent.VK_TAB); rob.keyRelease(KeyEvent.VK_TAB); rob.delay(500);
			rob.keyPress(KeyEvent.VK_TAB); rob.keyRelease(KeyEvent.VK_TAB); rob.delay(500);
			rob.keyPress(KeyEvent.VK_TAB); rob.keyRelease(KeyEvent.VK_TAB); rob.delay(500);
			rob.keyPress(KeyEvent.VK_TAB); rob.keyRelease(KeyEvent.VK_TAB); rob.delay(500);
			rob.keyPress(KeyEvent.VK_DELETE); rob.keyRelease(KeyEvent.VK_DELETE); rob.delay(500);
			pasteString(tn);
			
		//startTime = driver.findElement(By.xpath("//edit[contains(@Name,'Start time')]"));
		//startTime.clear(); startTime.sendKeys(tn);
		}
		else {startTime = driver.findElement(By.xpath("//*[@AutomationId='4096']"));
		startTime.clear(); startTime.sendKeys(tn);
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
		pasteString(TEST_TITLE);robot.delay(1000);
		
		
	return TEST_TITLE;

}
	/*Original*/
		public ArrayList<String> specifyMeetingProperties(String mail) throws InterruptedException, AWTException {
			Thread.sleep(4000);
			Robot robot = new Robot();
			
			pasteString(mail);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.delay(500);
			robot.keyPress(KeyEvent.VK_TAB);						
			pasteString(TEST_TITLE);robot.delay(1000);
			
			WebElement newbodyDocument = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@LocalizedControlType='document' and contains(@Name,'"+TEST_TITLE+"')]")));
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
		public String specifyMeetingPropertiesReturnName(String mail) throws InterruptedException, AWTException {
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
	
	public boolean amdocsMeetingCancellation(String meetingNameInTheList) throws InterruptedException {
		btnCreatedMeeting = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]")));
		btnCreatedMeeting.click();
		Thread.sleep(2000);
		List<WebElement> list = driver.findElements(By.name("Cancel Meeting"));
		if(list.size()>0) {
			btnCancelMeeting = driver.findElement(By.name("Cancel Meeting"));
			btnCancelMeeting.click();Thread.sleep(1000);
			btnSendCancellation = driver.findElement(By.name("Send Cancellation"));
			btnSendCancellation.click();
			return true;
		}
		
		else {btnDeleteMeeting = driver.findElement(By.name("Delete"));
		btnDeleteMeeting.click(); return false;}
		
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
