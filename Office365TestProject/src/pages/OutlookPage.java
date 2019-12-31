package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

public class OutlookPage {

	public enum LeftMenuItem {
		INBOX, SENT_ITEMS, DRAFTS, DELETED_ITEMS, JUNC_EMAIL, ARCHIVE
	}

	public enum LeftBottomMenuItem {
		MAIL, CALENDAR, PEOPLE, TO_DO
	}

	WebDriver driver;
	WebElement btnNewMessage, txtTo, txtSubject, txtMailBody, btnSend = null;
	private WebElement appLauncher = null;

	public OutlookPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	public void choosePresentationBottomMenu(LeftBottomMenuItem item) {
		int clickItem = 0;
		switch (item) {
		case MAIL:
			clickItem = 0;
			break;
		case CALENDAR:
			clickItem = 1;
			break;
		case PEOPLE:
			clickItem = 2;
			break;
		case TO_DO:
			clickItem = 3;
			break;
		}
		WebElement btnBottomLine = driver
				.findElement(By.xpath("//div[@aria-orientation='horizontal']/div[" + clickItem + "]//span"));
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
