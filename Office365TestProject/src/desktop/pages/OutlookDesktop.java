package desktop.pages;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.datatransfer.Clipboard;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.WiniumDriver;

import net.bytebuddy.utility.privilege.GetSystemPropertyAction;
import pages.OneDrive.NewMenuItem;

public class OutlookDesktop {
	public enum LeftBottomMenu {MAIL, CALENDAR, PEOPLE, TASKS}
	public enum TopMenu{FILE, HOME, SEND_RECEIVE, FOLDER, VIEW, SEARCH}

	WiniumDriver driver;
	private WebElement newAmdocsMeeting,
	txtTo, txtSubject, btnSend, txtSearchQuery,
	btnDeleteMeeting, lblMeetingName, mailInTheList,
	btnConfirmCloseing, btnCloseMeeting, 
	txtLocation, btnNewEmail, txtMessageTo,
	txtSubjectMessage, btnCreatedMeeting, btnOpen,
	linkJoinSkypeMeeting, btnCancelMeeting, btnSendCancellation = null;
	String TEST_TITLE = "AUTOTEST_"+getRandom();

	public OutlookDesktop(WiniumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
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
		WebElement leftBottomItem = (new WebDriverWait(driver, 8))
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
		mailProperties.click();							
		WebElement headersField = (new WebDriverWait(driver, 2)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@AutomationId='4105']")));
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
		
		// internetHeadersField = AutomationId:	"4105"
		

		
		
		// Name:	"Close"
		// have to be  completed 
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
		newAmdocsMeeting.click();Thread.sleep(3000);
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
		List listSkypeElements = driver.findElements(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]"));
		if(listSkypeElements.size()>0) {
			
			//Robot robot = new Robot();
			robot.delay(2000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER); 
			//robot.keyPress(KeyEvent.VK_LEFT);robot.keyPress(KeyEvent.VK_ENTER);
			
			btnCloseMeeting = (new WebDriverWait(driver, 1)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@Name='Close']")));
			btnCloseMeeting.click();
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
		
		 // Dolphie.Lobo@amdocs.com
		btnSend = driver.findElement(By.name("Send"));
		btnSend.click();Thread.sleep(2000);
		return TEST_TITLE;

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
	/*********************************
	 |  Open an appointment by name. |
	 *********************************/
	public void clickByMeetingName(String meetingNameInTheList) {
		btnCreatedMeeting = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@Name,'"+meetingNameInTheList+"')]")));
		btnCreatedMeeting.click();
		btnOpen = (new WebDriverWait(driver, 1)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@Name='Open']")));
		btnOpen.click();
	}
	
	/*Original*/
		public String specifyMeetingProperties(String mail) throws InterruptedException { 
		txtSubject = (new WebDriverWait(driver, 7)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@AutomationId='4100']")));
		txtSubject.sendKeys(TEST_TITLE);
		txtTo = (new WebDriverWait(driver, 2)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@AutomationId='4106']")));
		txtTo.sendKeys(mail); 
		//txtTo.sendKeys(Keys.TAB); 
		Thread.sleep(1000); // Dolphie.Lobo@amdocs.com
		btnSend = driver.findElement(By.name("Send"));
		btnSend.click();Thread.sleep(3000);
		return TEST_TITLE;

	}
	public void specifyLocation(String location) {
		txtLocation = (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@AutomationId='4102']")));
		txtLocation.sendKeys(location);
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
	
	 /***************** 
		 * Random method *
		 *****************/
		public int getRandom() {Random rand = new Random(); 
		int value = rand.nextInt(100000);
		return value;}
}
