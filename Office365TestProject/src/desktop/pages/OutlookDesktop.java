package desktop.pages;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
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

import pages.OneDrive.NewMenuItem;

public class OutlookDesktop {
	public enum LeftBottomMenu {
		MAIL, CALENDAR, PEOPLE, TASKS
	}

	WiniumDriver driver;
	private WebElement newAmdocsMeeting, txtTo, txtSubject, btnSend, lblMeetingName, btnConfirmCloseing, btnCloseMeeting, txtLocation, btnNewEmail, txtMessageTo, txtSubjectMessage, btnCreatedMeeting, btnOpen, linkJoinSkypeMeeting = null;
	String TEST_TITLE = "AUTOTEST_"+getRandom();

	public OutlookDesktop(WiniumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
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
	 *  Open an appointment by name. *
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
	
	
	 /***************** 
		 * Random method *
		 *****************/
		public int getRandom() {Random rand = new Random(); 
		int value = rand.nextInt(100000);
		return value;}
}
