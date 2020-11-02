package pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.Settings.ApplicationName;

public class OneNote {
	public int getRandom() {Random rand = new Random(); int value = rand.nextInt(100000); return value;}
	WebElement btnNewNotebook, txtNewNotebookName, btnCreate, lnkClickHere, txtSectionNameField, btnOK, mainDiv, elementInTheList;
	String NOTEBOOK_NAME = "TEST_BOOK_"+getRandom();
	String SECTION_NAME = "SECTION_"+getRandom();
	
	WebDriver driver;
	public OneNote(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
	public String createNewNotebook(String nameNB) {
		btnNewNotebook = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button/span[contains(text(),'New')]")));
		btnNewNotebook.click();
		txtNewNotebookName = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='dialog__actions']//input")));
		txtNewNotebookName.sendKeys(nameNB);
		btnCreate = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button/span[contains(text(),'Create')]")));
		btnCreate.click();
		return nameNB;
	}
	
	//ul//div[contains(text(),'Open in Desktop App')]
	
	public void checkNewNotebook() {
		mainDiv = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='WopiDocWACContainer']")));
		
		driver.switchTo().frame("WebApplicationFrame");
		lnkClickHere = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='FishbowlContainer']")));
		lnkClickHere.click();
		txtSectionNameField = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div/input[@class='WACDialogInput']")));
		txtSectionNameField.sendKeys(SECTION_NAME);
		btnOK = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@id='WACDialogActionButton']")));
		btnOK.click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
	}
	public void openNoteInDesktopApp(String name, MenuItemRightClick item) {
		WebElement newNote = driver.findElement(By.xpath("//span[contains(text(),'"+name+"')]"));
		Actions action = new Actions(driver).contextClick(newNote);
		action.build().perform();
		chooseMenuItem(item);
	}
	public enum MenuItemRightClick{
		OPEN_IN_BROWSER, OPEN_IN_DESKTOP_APP, SHARE,COPY_LINK, ADD_TO_PINNED,REMOVE_FROM_LIST
	}
	
	public void chooseMenuItem(MenuItemRightClick item) {
		String parametrListItem = null;
		switch(item) {
		case OPEN_IN_BROWSER: parametrListItem = "Open in Browser"; break;
		case OPEN_IN_DESKTOP_APP: parametrListItem = "Open in Desktop App"; break;
		}
		WebElement leftMenuItemElement = driver.findElement(By.xpath("//li[@role='listitem']//div[contains(text(),'"+parametrListItem+"')]"));
		leftMenuItemElement.click();
	}
	public void openOneNote2016Popup() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.keyRelease(KeyEvent.VK_LEFT);robot.delay(250);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);robot.delay(1000);
		
	}
	
	public void clickTopLineMenu(String topLine) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btnOK = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[contains(text(),'"+topLine+"')]")));
		btnOK.click();
		//h3[contains(text(),' My notebooks ')]
		
	}
	public void pageRefresh() throws AWTException {
		Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_F5);
		robot.keyRelease(KeyEvent.VK_F5);
		
	}
	
	public boolean checkList(String notebookName) throws InterruptedException {
		Thread.sleep(5000);
		List<WebElement> listOfElements = driver.findElements(By.xpath("//div/span[contains(text(), '"+notebookName+"')]"));
		if (listOfElements.size()>0) {return true;}
		else return false;
		
	}
	
	public boolean openDocument(String docName) throws InterruptedException, AWTException {
		Thread.sleep(3000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F);
		robot.keyRelease(KeyEvent.VK_F);
		robot.keyRelease(KeyEvent.VK_ALT); robot.delay(100);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_O);
		robot.keyRelease(KeyEvent.VK_O);
		robot.keyRelease(KeyEvent.VK_ALT); robot.delay(500);
		List<WebElement> listDoc = driver.findElements(By.xpath("//*[@LocalizedControlType='Button' and contains(@Name,'"+docName+"')]"));
		
		if (listDoc.size()>0) {listDoc.get(0).click(); return true;}
		else {return false;}
		//WebElement myDocument = driver.findElement(By.xpath("//*[@LocalizedControlType='Button' and contains(@Name,'"+docName+"')]"));
		//myDocument.click();
		

	}
	
	// C:\Users\michapru\AppData\Local\Microsoft\OneNote\16.0\Backup\My Notebook
	public boolean checkSyncOneNote(String noteName) throws AWTException, InterruptedException {
		Thread.sleep(3000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F);
		robot.keyRelease(KeyEvent.VK_F);
		robot.keyRelease(KeyEvent.VK_ALT); robot.delay(100);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_I);
		robot.keyRelease(KeyEvent.VK_I);
		robot.keyRelease(KeyEvent.VK_ALT); robot.delay(100);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_ALT); robot.delay(500);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_ALT); robot.delay(5000);
		
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_ALT); robot.delay(500);
		
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F);
		robot.keyRelease(KeyEvent.VK_F);
		robot.keyRelease(KeyEvent.VK_ALT); robot.delay(100);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_O);
		robot.keyRelease(KeyEvent.VK_O);
		robot.keyRelease(KeyEvent.VK_ALT);robot.delay(500);
		
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_R);
		robot.keyRelease(KeyEvent.VK_R);
		robot.keyRelease(KeyEvent.VK_ALT);robot.delay(1000);
		
		/*
		 * WebElement element = (new WebDriverWait(driver, 10))
		 * .until(ExpectedConditions.presenceOfElementLocated(By.
		 * xpath("//*[@LocalizedControlType='Tab Item' and contains(@Name,'Recent')]")))
		 * ; element.click();
		 */
		List<WebElement> recentNotebook = driver.findElements(By.xpath("//*[@LocalizedControlType='Tab Item' and contains(@Name,'"+noteName+"')]"));
		if(recentNotebook.size()>0) {return true;}
		else return false;
		
		/*
		 * WebElement myNotebookInside = (new WebDriverWait(driver, 10))
		 * .until(ExpectedConditions.presenceOfElementLocated(By.
		 * xpath("//*[@LocalizedControlType='Tab Item' and contains(@Name,'OneDrive - AMDOCS')]"
		 * ))); myNotebookInside.click();
		 */
		
		/*
		 * WebElement myNotebookCollection = (new WebDriverWait(driver, 10))
		 * .until(ExpectedConditions.presenceOfElementLocated(By.
		 * xpath("//*[@LocalizedControlType='Button' and contains(@Name,'Notebooks')]"))
		 * ); myNotebookCollection.click();
		 */
		
		 
		/*
		WebElement myNotebook = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@LocalizedControlType='Text' and contains(@Name,'My Notebook')]")));
		myNotebook.click();
		*/
		
		/* otkrit OneNote Desk - >
		 *  My Notebook -> 
		   		LocalizedControlType:	"Text"
    			Name:	"My Notebook"
    
		 *  Other notebooks -> 
		    		 LocalizedControlType:	"Button"
    				Name:	"Open Other Notebooks"
    				libo ALT +F , Alt+O
    				
    	Otkrit OneDrive -
    				LocalizedControlType:	"Tab Item"
    				Name:	"OneDrive - AMDOCS"
    				
    				Otkrit Notebooks: 
    					LocalizedControlType:	"Button"
    					Name:	"Notebooks"
    
    
    Naiti sozdany element:  LocalizedControlType:	"Button"
    Name:	"TEST_BOOK_56220"

    */
		
	}
	public void closeActiveWindow() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_ALT);robot.delay(250);
	}

}
