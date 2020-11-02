package pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import javax.swing.text.DefaultEditorKit.PasteAction;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FileFromMainPage {
	WebDriver driver;
    public FileFromMainPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void switchToFrame() {
    	//iframe[@id='WebApplicationFrame']
    	
    	//span[@class='NormalTextRun' and contains(text(),'Test')]
    }
    public void closeCurrentTab() {
    	driver.close();
    }
    public void cleanAndPasteCanvas(String text) throws AWTException, InterruptedException {
    	WebElement iframeElement = (new WebDriverWait(driver, 10))
    			  .until(ExpectedConditions.presenceOfElementLocated(By.id("WebApplicationFrame")));
    	driver.switchTo().frame("WebApplicationFrame");
    	System.out.println("TITLE FROM cleanCanvas method"+driver.getTitle());
 	   // WebElement fileInTheList = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='NormalTextRun' and contains(text(),'"+text+"')]")));
 	   WebElement fileInTheList = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='NormalTextRun' or @class='NormalTextRun SpellingErrorV2']")));
 	  // fileInTheList.getText().replaceAll("[a-zA-Z]+", "");
 	 
 	   Thread.sleep(1000);
 	  
 	   Robot robot = new Robot();
 		robot.keyPress(KeyEvent.VK_CONTROL);robot.delay(100);
 		robot.keyPress(KeyEvent.VK_A);robot.delay(100);
 		robot.keyRelease(KeyEvent.VK_A);robot.delay(100);
 		robot.keyRelease(KeyEvent.VK_CONTROL);robot.delay(500);
 		robot.keyPress(KeyEvent.VK_DELETE);robot.delay(1000);
 	  
 		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
 		clipboard.setContents(new StringSelection(text), null);
 		robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);robot.delay(2000);
    }
    
    public boolean checkCanvasAutosave(String text) throws AWTException, InterruptedException {
    	WebElement iframeElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("WebApplicationFrame")));
    	driver.switchTo().frame("WebApplicationFrame");
    	System.out.println("TITLE FROM cleanCanvas method"+driver.getTitle());
    	Boolean isPresent = driver.findElements(By.xpath("//span[@class='NormalTextRun' or @class='NormalTextRun SpellingErrorV2' and contains(text(),'"+text+"')]")).size()>0;
    	return isPresent;
    }

}
