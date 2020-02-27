package desktop.tests;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import desktop.Config_OutOffOutlook;
import desktop.pages.ConnectToOutlook;

public class TestDifferentUser extends Config_OutOffOutlook {
	
	ExtentHtmlReporter htmlReporter;
	ExtentTest testLogOutlook = null;
	String driverPath = "c:\\DRIVERS\\chromedriver.exe";
	String meetingName = null;
	String recuringMeetingName = null;
	String createdRecMeeting = null;
	String createdRecMeetingID = null;
	String meetingNameNew;
	
	public static void pasteString(String anystring) throws AWTException {
		StringSelection stringSelection2 = new StringSelection(anystring);
		Clipboard clipboard2 = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard2.setContents(stringSelection2, stringSelection2);
		Robot rob = new Robot();
		rob.keyPress(KeyEvent.VK_CONTROL);
		rob.keyPress(KeyEvent.VK_V);
		rob.keyRelease(KeyEvent.VK_V);
		rob.keyRelease(KeyEvent.VK_CONTROL); rob.delay(500);
	}
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
		
	}
	@Test
	public void openAsDifferentUser() throws AWTException, InterruptedException {
		ConnectToOutlook connectToOutlook = new ConnectToOutlook();
		connectToOutlook.openOutlookAsDifferentUserRUNAS("yoelap@amdocs.com", "Random@224466", "AUTOTEST_");
		
		
		
		
	}

}
