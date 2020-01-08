package desktop.pages;

import java.util.Random;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.winium.WiniumDriver;

public class OneDriveDesktop {

	WiniumDriver driver;
	private WebElement newAmdocsMeeting, txtTo, txtSubject, btnSend, lblMeetingName, txtLocation, btnNewEmail, txtMessageTo, txtSubjectMessage = null;
	String TEST_TITLE = "AUTOTEST_"+getRandom();

	public OneDriveDesktop(WiniumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	
	
	
	
	public int getRandom() {Random rand = new Random(); 
	int value = rand.nextInt(100000);
	return value;}
}
