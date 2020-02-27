package desktop.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.WiniumDriver;

public class ConnectToOutlook {

	private WiniumDriver driver;

	public ConnectToOutlook(WiniumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public ConnectToOutlook() {}
	

	/* Vstavka stroki v aktivnoe pole */
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
		/*
		 * WebElement invitationMail = (new WebDriverWait(driver,
		 * 12)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(
		 * "//*[contains(@Name,'"+name+"')]"))); invitationMail.click(); WebElement
		 * proposeNewTime =
		 * driver.findElement(By.xpath("//*[contains(@Name,'Propose New Time')]"));
		 * proposeNewTime.click();
		 */
	}
}
