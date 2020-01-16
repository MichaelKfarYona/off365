package desktop.pages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.WiniumDriver;
import com.sun.jna.platform.win32.User32;

public class OneDriveDesktop {

	WiniumDriver driver;
	private WebElement newAmdocsMeeting, txtTo, txtSubject, btnSend, lblMeetingName, txtLocation, btnNewEmail, txtMessageTo, txtSubjectMessage = null;
	String TEST_TITLE = "AUTOTEST_"+getRandom();

	public OneDriveDesktop(WiniumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean checkOneDriveCondition() {
		List<WebElement> list = null;
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@Name,'OneDrive is up to date')]")));
		
		list = driver.findElements(By.xpath("//*[contains(@Name,'OneDrive is up to date')]"));
		if(list.size()>0) {return true;}
		else {
			return false;
		}
	}
	public void swithToTaskBarOrTray(String appName) {
		User32.INSTANCE.GetForegroundWindow();
		WebElement oneDriveTrayIcon = driver.findElement(By.xpath("//*[contains(@Name,'"+appName+"')]"));
		oneDriveTrayIcon.click();
		/*
		 * HWND hwnd = User32.INSTANCE.FindWindow(null , "OneDrive - AMDOCS");
		 * User32.INSTANCE.ShowWindow(hwnd,1);
		 */
	}

	 /***********************************************************\
	 * Copy a file or directory. Need to use an absolute path  *
	 \***********************************************************/
	public String copyFileOrFolder(String appName, String pathSource_, String pathDetination_, String fileName) {
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
	
	
	public boolean checkingElementExistence(String fileName) {
		List<WebElement> copiedFileFolder = driver.findElements(By.xpath("//*[contains(@Name,'"+fileName+"')]"));
		if(copiedFileFolder.size()>0) {return true;}
		else {return false;}
		
	}
	
	
	public int getRandom() {Random rand = new Random(); 
	int value = rand.nextInt(100000);
	return value;}
}
