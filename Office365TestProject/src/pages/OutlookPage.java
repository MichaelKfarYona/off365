package pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mongodb.client.ListIndexesIterable;

public class OutlookPage {

	public enum LeftMenuItem {INBOX, SENT_ITEMS, DRAFTS, DELETED_ITEMS, JUNC_EMAIL, ARCHIVE}
	public enum LeftBottomMenuItem {MAIL, CALENDAR, PEOPLE, TO_DO}
	public enum Condition{FROM, TO, 
		IM_ON_THE_TO_LINE, IM_ON_THE_CC_LINE, IM_ON_THE_TO_OR_CC_LINE, IM_NOT_ON_THE_TO_LINE, IM_ONLY_RECIPIENT,
		SUBJECT_INCLUDES, SUBJECT_OR_BODY_INCLUDES,
		MESSAGE_BODY_INCLUDES, SENDER_ADDRESS_INCLUDES, RECIPIENT_ADDRESS_INCLUDES, MESSAGE_HEADER_INCLUDES,
		IMPORTANCE, SENSITIVITY, CLASSIFICATION,
		FLAG, TYPE, HAS_ATTACHMENT,
		AT_LEAST, AT_MOST,
		BEFORE, AFTER,
		APPLY_TO_ALL_MESSAGES}
	public enum ImportanceLevel{HIGH, NORMAL, LOW}
	public enum SelectedAction{MOVE_TO, COPY_TO, DELETE, PIN_TO_TOP}
	public enum MoveToAction{ARCHIVE, INBOX, DELETED_ITEMS}
		

	WebDriver driver;
	WebElement btnNewMessage, btnSaveRule, btnSelectAnActionValue, btnSelectAnAction, btnValueFromList, btnSelectACondition,  btnAddNewRule, txtTo, txtSubject, txtMailBody, btnRules, btnSend = null;
	WebElement btnNewEvent, txtAddATitle, txtInviteAttendees, btnSave, btnDiscard, btnSchedulingAssistant, btnBusy, btnCategorize, btnResponseOptions = null;
	WebElement txtDate, btnSettings, lnkAllSettings, txtTimeStart, txtTimeFinish, tableDate, txtTimeStartPicker, txtTimeEndPicker, txtSearchField, btnSendCalend = null;
	private WebElement appLauncher = null;

	public OutlookPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	// RULES
	public void deleteAllRules() throws InterruptedException {
		
		List<WebElement> listRules = driver.findElements(By.xpath("//button//i[@data-icon-name='Delete']"));
		for(WebElement item : listRules) {
			item.click();Thread.sleep(500);
			WebElement confirmButton = driver.findElement(By.xpath("//span[contains(text(),'OK')]"));
			confirmButton.click();
			Thread.sleep(500);
		}
	}
	/* RULES */
	public void openOutlookSettings() {
	btnSettings = (new WebDriverWait(driver, 15))
		.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@aria-label='Settings']")));
	btnSettings.click();
	}
	public void openAllOutlookSettings() {
		openOutlookSettings();
		lnkAllSettings = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button/span/span[contains(text(),'View')]")));
		lnkAllSettings.click();
		
	}
	
	public void openRulesWithoutAdding() throws InterruptedException {
		Thread.sleep(500);
		btnRules = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button//span[contains(text(),'Rules')]")));
		btnRules.click();
		Thread.sleep(1000);
	}
	
	public void openRules() throws InterruptedException {
		Thread.sleep(500);
		btnRules = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button//span[contains(text(),'Rules')]")));
		btnRules.click();
		Thread.sleep(500);
		btnAddNewRule = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button//span[contains(text(),'Add new rule')]")));
		btnAddNewRule.click();
	}
	
	public void specifyRuleInfo(String ruleName, Condition condition, ImportanceLevel importanceLevel, SelectedAction action, String senderOrReciever, String actionValue, MoveToAction moveAction) throws AWTException, InterruptedException {
		System.out.println("Step 2");
		pasteFromClipboard(ruleName);
		btnSelectACondition = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Select a condition')]")));
		btnSelectACondition.click();
		System.out.println("Step 3");
		chooseCondition(condition);
		if(condition.equals(Condition.IMPORTANCE)) {
			chooseLevel(importanceLevel);
		}
		
		/*
		btnValueFromList = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='listbox']//button/span/span[contains(text(),'"+condition+"')]")));
		btnValueFromList.click();
		*/
		if(condition.equals(Condition.FROM)) {
			pasteFromClipboard(senderOrReciever);
	        /*
			List<WebElement> addedField = driver.findElements(By.xpath("//div/input[@role='combobox']"));
			if(addedField.size()>0) {addedField.get(0).sendKeys(senderOrReciever); Robot robot = new Robot();  robot.keyPress(KeyEvent.VK_ENTER);
	        robot.keyRelease(KeyEvent.VK_ENTER);
	        }
	        */
	        
		}
		selectAnAction(action);
		if(action.equals(SelectedAction.MOVE_TO)) {
			chooseMoveToAction(moveAction);
			WebElement runRuleNow = driver.findElement(By.xpath("//span[contains(text(),'Run rule now')]"));
			runRuleNow.click();
		}
		
		/*
		btnSelectAnActionValue = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='listbox']//button//span[contains(text(),'"+actionValue+"')]")));
		btnSelectAnActionValue.click();
		*/
		System.out.println("Step 4");
		Thread.sleep(1000);
		btnSaveRule = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button//span[contains(text(),'Save')]")));
		btnSaveRule.click();
	}
	public void closeSettings() throws InterruptedException {
		Thread.sleep(500);
		WebElement closeSettings = driver.findElement(By.xpath("//i[@data-icon-name='Cancel']"));
		closeSettings.click();
	}
	public void refreshPage() throws AWTException {
		Robot robot = new Robot();
		robot.delay(500);
		robot.keyPress(KeyEvent.VK_F5);
        robot.keyRelease(KeyEvent.VK_F5); robot.delay(1000);
	}
	public boolean findInArchive(String ruleName) {
		List<WebElement> itemInTheArchive = driver.findElements(By.xpath("//span[contains(text(),'"+ruleName+"')]"));
		if(itemInTheArchive.size()>0) {return true;}
		else{return false;}
		
	}
	
	public void chooseMoveToAction(MoveToAction act) {
		WebElement selectAFolder = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Select a folder')]")));
		selectAFolder.click();
		
		String myAct = null;
		switch(act) {
		case ARCHIVE: myAct = "Archive";
		}
		WebElement selectedFolder = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='menuitemcheckbox']//span[contains(text(),'"+myAct+"')]")));
		selectedFolder.click();
		
		
	}
	public void selectAnAction(SelectedAction act) throws InterruptedException {
		btnSelectAnAction = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Select an action')]")));
		btnSelectAnAction.click();
		Thread.sleep(500);
		String condition = null;
		switch(act) {
		case MOVE_TO: condition = "Move to";
		break;
		case COPY_TO: condition = "Copy to";
		break;
		case DELETE: condition = "Delete";
		break;
		case PIN_TO_TOP: condition = "Pin to top";
		break;
		}
		WebElement actionItem = driver.findElement(By.xpath("//button/span/span[contains(text(),'"+condition+"')]"));
		actionItem.click();
		}
	
	// just several cases to test
	public void chooseCondition(Condition item) {
		String condition= null;
		switch (item) {
		case FROM: condition = "From";
		break;
		case TO: condition = "To";
		break;
		case IMPORTANCE: condition = "Importance";
		break;
		case IM_ON_THE_TO_LINE: condition = "m on the To line";
		break;
		
		//button/span/span[contains(text(),'Importance')]
		/*
		IM_ON_THE_TO_LINE, IM_ON_THE_CC_LINE, IM_ON_THE_TO_OR_CC_LINE, IM_NOT_ON_THE_TO_LINE, IM_ONLY_RECIPIENT,
		SUBJECT_INCLUDES, SUBJECT_OR_BODY_INCLUDES,
		MESSAGE_BODY_INCLUDES, SENDER_ADDRESS_INCLUDES, RECIPIENT_ADDRESS_INCLUDES, MESSAGE_HEADER_INCLUDES,
		IMPORTANCE, SENSITIVITY, CLASSIFICATION,
		FLAG, TYPE, HAS_ATTACHMENT,
		AT_LEAST, AT_MOST,
		BEFORE, AFTER,
		APPLY_TO_ALL_MESSAGES
		*/
		}
		WebElement btnBottomLine = driver.findElement(By.xpath("//div[@role='listbox']//button/span/span[contains(text(),'"+condition+"')]"));
		btnBottomLine.click();
	}
	public void chooseLevel(ImportanceLevel item) {
		WebElement btnOption = driver.findElement(By.xpath("//span[contains(text(),'Select an option')]"));
		btnOption.click();
		
		String condition= null;
		switch (item) {
		case HIGH: condition = "High";
		break;
		case NORMAL: condition = "Normal";
		break;
		case LOW: condition = "Low";
		break;
		
		}
		WebElement btnOptionSelect = driver.findElement(By.xpath("//button/span/span[contains(text(),'"+condition+"')]"));
		btnOptionSelect.click();
	}
	public boolean checkRulesList(String ruleName, boolean deleteOrNotAfterCreation) throws InterruptedException {
		boolean res = true;
		System.out.println("Step 6");
		//Thread.sleep(1000);
		List<WebElement> ruleList = driver.findElements(By.xpath("//div[@role='tabpanel']//div[contains(text(),'"+ruleName+"')]"));
		System.out.println("Step 6");
		System.out.println("Rule : "+ruleList.toString());
		res = ruleList.isEmpty();
		System.out.println("Step 7");
		
			// need to implement! problem: dymanic ids and names.
		//	.//button[@type='button' and contains(text(),'Document.docx')]/ancestor::span
		//	.//i[@data-icon-name='Delete']/ancestor::div//div[contains(text(),'h7')]
		
		
		return res;
	}
	
	// **************************************************************************
	public void addInviteesMail(String str) throws InterruptedException {
		txtInviteAttendees = (new WebDriverWait(driver, 5))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@aria-label='Invite attendees']")));
		txtInviteAttendees.sendKeys(str); Thread.sleep(1000); txtInviteAttendees.sendKeys(Keys.ENTER); 
	} 
	//Filling out a meeting form
	public void specifyCalendarProperties(String eventTitle, List<String> attendeesList) {
		txtAddATitle = (new WebDriverWait(driver, 12))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Add a title']")));
		txtAddATitle.sendKeys(eventTitle);
		attendeesList.forEach((item) -> {try {addInviteesMail(item);} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	public boolean checkCalendar(String calendarName) throws InterruptedException {
		//txtSearchField = driver.findElement(By.xpath("//input[@placeholder='Search']"));
		//txtSearchField.sendKeys(calendarName, Keys.ENTER); 
		Thread.sleep(1000);
		List<WebElement> eventList = driver.findElements(By.xpath("//span[contains(text(),'"+calendarName+"')]"));
		if(eventList.size()>0) {return true;}
		else {return false;}
		
		
	}
	// specify non default Date and time
	public void specifyNewDateAndTimeMeeting(String newDate, String timeOne, String timeTwo, boolean useOrNot) throws InterruptedException {
		if(useOrNot) {
		
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
				.xpath("//div/input[@aria-label='To']")));
		//txtTo.sendKeys(mail);
		 txtTo.sendKeys(mail, Keys.ENTER);
		/*
		 * try { Robot robot = new Robot(); robot.keyPress(KeyEvent.VK_TAB);
		 * robot.keyRelease(KeyEvent.VK_TAB); } catch (AWTException e) {
		 * e.printStackTrace(); }
		 */
		System.out.println("click subject");
		txtSubject = driver.findElement(By.xpath("//div[2]/div/div/div/input"));
		txtSubject.sendKeys(subject, Keys.ENTER);
		System.out.println("click mailBody");
		txtMailBody = driver.findElement(By.xpath("//div[@role='textbox']"));
		txtMailBody.sendKeys(mailBody, Keys.ENTER);
	}

	public void createNewMessage(String mail, String subject, String mailBody) throws InterruptedException {
		clickByCreateNewMessageButton();
		specifyToSubjectMailBody(mail, subject, mailBody);

		System.out.println("click send");
		btnSend = driver.findElement(By.xpath("//button[@aria-label='Send']"));
		btnSend.click();
		Thread.sleep(5000);
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
	 public void pasteFromClipboard(String text) throws AWTException {
		 Robot robot = new Robot();
		 Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	 		clipboard.setContents(new StringSelection(text), null);
	 		robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        robot.keyRelease(KeyEvent.VK_V);
	 }
	public void topSearchTextInOutlook(String text) {

	}
	public void teamsMeetingSwitcher(boolean switcher) {
		if (switcher) {
			WebElement onOff = driver.findElement(By.xpath("//button[@role='switch'][@aria-label='Teams meeting']"));
			onOff.click();
		}
		
		
	}

}
