package desktop.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
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
	private WebElement newAmdocsMeeting, txtTo, txtSubject, btnSend, lblMeetingName = null;
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
		WebElement leftBottomItem = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.name(parametrListItem)));
		leftBottomItem.click();
	}

	public void clickNewAmdocsMeeting() {
		newAmdocsMeeting = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.name("New Amdocs Meeting")));
		newAmdocsMeeting.click();
	}

	public String specifyMeetingProperties() throws InterruptedException { 
		txtSubject = (new WebDriverWait(driver, 7)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@AutomationId='4100']")));
		txtSubject.sendKeys(TEST_TITLE);
		txtTo = (new WebDriverWait(driver, 7)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@AutomationId='4106']")));
		txtTo.sendKeys("yoelap@amdocs.com"); Thread.sleep(2000);
		btnSend = driver.findElement(By.name("Send"));
		btnSend.click();Thread.sleep(2000);
		return TEST_TITLE;

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

	
	
	 /***************** 
		 * Random method *
		 *****************/
		public int getRandom() {Random rand = new Random(); 
		int value = rand.nextInt(100000);
		return value;}
}
