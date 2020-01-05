package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OutlookPage {

	public enum LeftMenuItem {INBOX, SENT_ITEMS, DRAFTS, DELETED_ITEMS, JUNC_EMAIL, ARCHIVE}
	public enum LeftBottomMenuItem {MAIL, CALENDAR, PEOPLE, TO_DO}

	WebDriver driver;
	WebElement btnNewMessage, txtTo, txtSubject, txtMailBody, btnSend = null;
	WebElement btnNewEvent, txtAddATitle, txtInviteAttendees, btnSave, btnDiscard, btnSchedulingAssistant, btnBusy, btnCategorize, btnResponseOptions = null;
	WebElement txtDate, txtTimeStart, txtTimeFinish, tableDate, txtTimeStartPicker, txtTimeEndPicker, txtSearchField, btnSendCalend = null;
	private WebElement appLauncher = null;

	public OutlookPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	public void addInviteesMail(String str) throws InterruptedException {
		txtInviteAttendees = (new WebDriverWait(driver, 5))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@aria-label='Invite attendees']")));
		txtInviteAttendees.sendKeys(str); Thread.sleep(1000); txtInviteAttendees.sendKeys(Keys.ENTER); 
	} 
	//Filling out a meeting form
	public void specifyCalendarProperties(String eventTitle, List<String> attendeesList) {
		txtAddATitle = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Add a title']")));
		txtAddATitle.sendKeys(eventTitle);
		attendeesList.forEach((item) -> {try {addInviteesMail(item);} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	public boolean checkCalendar(String calendarName) throws InterruptedException {
		txtSearchField = driver.findElement(By.xpath("//input[@placeholder='Search']"));
		txtSearchField.sendKeys(calendarName, Keys.ENTER); Thread.sleep(1000);
		List<WebElement> eventList = driver.findElements(By.xpath("//div[@title='"+calendarName+"']"));
		if(eventList.size()>0) {return true;}
		else {return false;}
		
		
	}
	// specify non default Date and time
	public void specifyNewDateAndTimeMeeting(String newDate, String timeOne, String timeTwo) throws InterruptedException {
		
		txtTimeStart = driver.findElement(By.xpath("//input[@aria-label='Start time']"));
		txtTimeStart.sendKeys(Keys.END); 
		for(int i=0; i<5;i++) {
		txtTimeStart.sendKeys(Keys.BACK_SPACE);}
		txtTimeStart.sendKeys(timeOne);
		
		txtTimeFinish = driver.findElement(By.xpath("//input[@aria-label='End time']"));
		txtTimeFinish.sendKeys(Keys.END); 
		for(int i=0; i<5;i++) {
		txtTimeFinish.sendKeys(Keys.BACK_SPACE);}
		txtTimeFinish.sendKeys(timeTwo);
		
		
	}
	// Form Calendar controls
	
	public void clickSaveCalendarButton() {
		btnSave = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Save')]"))); 
		btnSave.click();
	}
	public void clickSendButton() {
		btnSendCalend = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Send')]"))); 
		btnSendCalend.click();
		
	}
	public void clickDiscardButton() {
		btnDiscard = driver.findElement(By.xpath("//span[contains(text(),'Discard')]"));
		btnDiscard.click();
	}

	public void clickSchedulingAssistant() {
		btnSchedulingAssistant = driver.findElement(By.xpath("//span[contains(text(),'Scheduling Assistant')]"));
		btnSchedulingAssistant.click();
	}
	public void clickBusy() {
		btnBusy = driver.findElement(By.xpath("//span[contains(text(),'Busy')]"));
		btnBusy.click();
	}
	public void clickCategorize() {
		btnCategorize = driver.findElement(By.xpath("//span[contains(text(),'Categorize')]"));
		btnCategorize.click();
	}
	
	public void clickResponseOptions() {
		btnResponseOptions = driver.findElement(By.xpath("//span[contains(text(),'Response options')]"));
		btnResponseOptions.click();
	}

	
	// Calendar methods
	public void choosePresentationBottomMenu(LeftBottomMenuItem item) {
		String clickItem= null;
		switch (item) {
		case MAIL:
			clickItem = "//button[@title='Mail']";
			break;
		case CALENDAR:
			clickItem = "//a[@aria-label='Calendar']";
			break;
		case PEOPLE:
			clickItem = "//a[@aria-label='People']";
			break;
		case TO_DO:
			clickItem = "//a[@aria-label='To Do']";
			break;
		}
		WebElement btnBottomLine = driver.findElement(By.xpath(clickItem));
		btnBottomLine.click();
	}

	public void clickByAppLauncherLeftCorner() {
		appLauncher = driver.findElement(By.xpath(""));
	}

	public void clickByLeftMenuElement(LeftMenuItem item) {
		String parametrListItem = null;
		switch (item) {
		case INBOX:
			parametrListItem = "Inbox";
			break;
		case SENT_ITEMS:
			parametrListItem = "Sent Items";
			break;
		case DRAFTS:
			parametrListItem = "Drafts";
			break;
		case DELETED_ITEMS:
			parametrListItem = "Deleted Items";
			break;
		case JUNC_EMAIL:
			parametrListItem = "Junk Email";
			break;
		case ARCHIVE:
			parametrListItem = "Archive";
			break;
		}
		WebElement leftMenuItemElement = driver.findElement(By.xpath("//div[@title='" + parametrListItem + "']"));
		leftMenuItemElement.click();
	}

	// Add new event in calendar
	public void clickNewEvent() {
	btnNewEvent = (new WebDriverWait(driver, 10))
			.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'New event')]")));
	btnNewEvent.click();

	}
	
	public void clickByCreateNewMessageButton() {
		System.out.println("click btn new msg");

		btnNewMessage = (new WebDriverWait(driver, 25))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'New message')]")));
		btnNewMessage.click();
	}

	public void specifyToSubjectMailBody(String mail, String subject, String mailBody) {
		System.out.println("click TO");

		txtTo = (new WebDriverWait(driver, 25)).until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//*[@id='app']/div//div[1]/div[1]/div[1]/div[1]/div[1]/div/div/div/div/div[1]/div/div/input")));
		txtTo.sendKeys(mail, Keys.ENTER);
		System.out.println("click subject");
		txtSubject = driver.findElement(By.xpath("//div[2]/div/div/div/input"));
		txtSubject.sendKeys(subject, Keys.ENTER);
		System.out.println("click mailBody");
		txtMailBody = driver.findElement(By.xpath("//div[@dir='ltr']//div"));
		txtMailBody.sendKeys(mailBody, Keys.ENTER);
	}

	public void createNewMessage(String mail, String subject, String mailBody) throws InterruptedException {
		clickByCreateNewMessageButton();
		specifyToSubjectMailBody(mail, subject, mailBody);

		System.out.println("click send");
		btnSend = driver.findElement(By.xpath("//button[@aria-label='Send']"));
		btnSend.click();
		Thread.sleep(2000);
	}

	public boolean verificationOfTheSentLetter(String subject) {
		clickByLeftMenuElement(LeftMenuItem.SENT_ITEMS);
		List<WebElement> itemBySubject = driver.findElements(By.xpath("//div/div/div/div[2]/div[2]/div/span[contains(text(),'"+subject+"')]"));
		if (itemBySubject.size()>0) {return true;}
		else {return false;}
				
	}
	
	
	public boolean checkRecievedMail(String reminderName) throws InterruptedException {
		WebElement txtSearch = (new WebDriverWait(driver, 25)).until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span/div/div/div[contains(text(),'" + reminderName + "')]")));
		if (txtSearch.isDisplayed()) {
			return true;
		} else
			return false;
	}

	public void topSearchTextInOutlook(String text) {

	}

}
