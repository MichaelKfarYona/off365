package pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OneDrive {
	public enum LeftMenuItem{
		FILES, RECENT, SHARED, DISCOVER, RECYCLE_BIN
	}
	public enum NewMenuItem{
		FOLDER, WORD_DOCUMENT, EXCEL_WORKBOOK, POWERPOINT_DOCUMENT, ONENOTE_NOTEBOOK, FORMS_FOR_EXCEL, LINK
	}
	public enum LinkSettings{ANYONE_WITH_THE_LINK, PEOLE_IN_AMDOCS_MSGLAB_WITH_THE_LINK, PEOPLE_WITH_EXISTING_ACCESS, SPECIFIC_PEOPLE}
	public enum UploadType{FILES, FOLDER}
	WebElement btnUpload = null;
	WebElement btnNew, btnUploadFilesFromPC, txtEnterFolderName, btnMagnifier, btnCreate, elementFromList = null;
	WebElement btnFiles, btnRecent, btnShared, btnRecycleBin, txtEnterTheLink, btnCreateButton, listElement, checkBoxOfTheElement, btnDelete, confirmationButton = null;
	WebElement btnShareDocument, txtEmailOrName, btnSendLink, linkSendLink = null;
	final String folderName = "FOLDER_NAME_";
	public int getRandom() {Random rand = new Random(); int value = rand.nextInt(100000); return value;}
	
	WebDriver driver;
	public OneDrive(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
// ********** Uploading Files and folders ********** 
	 public void uploadFileOrFolder(UploadType uploadType, String path) throws InterruptedException {
		 Thread.sleep(4000);
		 // btnUpload = driver.findElement(By.xpath("//button[@name='Upload']"));
		 btnUpload = (new WebDriverWait(driver, 20))
		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@name='Upload']")));
		 
		 btnUpload.click();
		boolean FOLDER_CHOOSEN = false;
		 	String parametrListItem = null;
			switch(uploadType) { 
			case FILES: parametrListItem = "Files";  break;
			case FOLDER: parametrListItem = "Folder";  FOLDER_CHOOSEN = true;  break;
			}
			setClipboard(path);
			WebElement uploadMenuItem = driver.findElement(By.xpath("//button/div/span[contains(text(),'"+parametrListItem+"')]"));
			uploadMenuItem.click();
			
			try {				
				popupUpload(FOLDER_CHOOSEN);			
				} 
				catch (AWTException e) {e.printStackTrace();}
				Thread.sleep(2000);
	 }
	 
	 public static void setClipboard(String str) {
	        StringSelection ss = new StringSelection(str);
	        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	    }
	 public void popupUpload(boolean folder_) throws AWTException, InterruptedException {
		 Robot robot = new Robot();
		// Ctrl-V + Enter on Win
		robot.delay(3000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL); robot.delay(250);
		robot.keyPress(KeyEvent.VK_ENTER); robot.delay(250);
		if (folder_ = true) {robot.delay(1000);robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(2000);robot.keyPress(KeyEvent.VK_LEFT); robot.delay(1000); robot.keyPress(KeyEvent.VK_ENTER);Thread.sleep(2000);}
	 }
	 // Delete element OneDrive
	 public boolean deleteElementFromTheOneDriveList(String elementName) throws InterruptedException {
		 Thread.sleep(2000);
		 //listElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@type='button' and contains(text(),'"+elementName+"')]")));
		 try {
		 checkBoxOfTheElement =(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@aria-label='Checkbox for "+elementName+"']"))); 
		 checkBoxOfTheElement.click();
		 Robot rob = new Robot();
		 
		 rob.keyPress(KeyEvent.VK_DELETE);
		 rob.keyRelease(KeyEvent.VK_DELETE); rob.delay(250);
			/*
			 * btnDelete = (new WebDriverWait(driver,
			 * 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(
			 * "//i[@data-icon-name='delete']"))); btnDelete.click();
			 */
		 confirmationButton = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[2]/div/span[1]/button")));
		 confirmationButton.click();
		 return true;
		 }
		 catch(Exception e) {System.out.println("Smthing wrong ... Check deleteElementFromTheOneDriveList method! "+e.getStackTrace());return false;}
	 }
	 // Share document
	 public void shareOneDriveDocument(String elementName, String mail, LinkSettings linkSettings) throws InterruptedException, AWTException {
		 checkBoxOfTheElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@aria-label='Checkbox for "+elementName+"']"))); 
		 checkBoxOfTheElement.click();
		 btnShareDocument = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button//span[contains(text(),'Share')]")));
		 btnShareDocument.click();Thread.sleep(3000);
		 WebElement iFrame= driver.findElement(By.xpath("//iframe[@id='shareFrame']"));
			driver.switchTo().frame(iFrame);
		
			
			//List<WebElement> list = driver.findElements(By.xpath("//span[contains(text(), 'outside of your organization')]"));		
			//System.out.println("***** STEP 6");
			//if (list.size()>0) {
				WebElement chooseWhoWouldYouLike = driver.findElement(By.xpath("//div[@class='od-ShareLinkDescription']"));
				chooseWhoWouldYouLike.click();
				String linkSettingsParametr = null;
				switch(linkSettings) { 
				case ANYONE_WITH_THE_LINK: linkSettingsParametr = "Anyone";  break;
				case PEOLE_IN_AMDOCS_MSGLAB_WITH_THE_LINK: linkSettingsParametr = "MSGLAB"; break;  
				case PEOPLE_WITH_EXISTING_ACCESS : linkSettingsParametr = "existing"; break;
				case SPECIFIC_PEOPLE: linkSettingsParametr = "Specific"; break;
				}
				WebElement LinkTo = (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='od-ShareLinkDescription-label' and contains(text(),'"+linkSettingsParametr+"')]")));
				LinkTo.click();
				WebElement btnApply = driver.findElement(By.xpath("//div[contains(text(),'Apply')]"));
				btnApply.click();
				
				txtEmailOrName = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@role='combobox']")));
				txtEmailOrName.sendKeys(mail);
				Thread.sleep(2000);
		
		  Robot robot = new Robot(); 
		  robot.keyPress(KeyEvent.VK_ENTER);
		  Thread.sleep(2000);

			btnSendLink = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Send')]")));
			btnSendLink.click(); 
			driver.switchTo().parentFrame();
			
	 }
	 
	// Proverka suchestvovaniya elementa v spiske OneDrive
	public boolean checkingElementExistence(String elementIn) throws InterruptedException {
		Thread.sleep(3000);
		//elementFromList = driver.findElement(By.xpath("//button[@type='button' and contains(text(),'"+elementIn+"')]"));
		elementFromList = driver.findElement(By.xpath("//button[@data-automationid='FieldRenderer-name' and contains(text(),'"+elementIn+"')]"));
		String elementNew = elementFromList.getText();
		if(elementIn.equals(elementNew)) {
			return true;
		}
		return false;
		
	}
	//* Click by left menu item
	public void clickByLeftMenuItem(LeftMenuItem item) {
		String parametrListItem = null;
		switch(item) { 
		case FILES: parametrListItem = "Files";  break;
		case RECENT: parametrListItem = "Recent";  break;
		case SHARED: parametrListItem = "Shared";  break;
		case DISCOVER: parametrListItem = "Discover";  break;
		case RECYCLE_BIN: parametrListItem = "Recycle bin";  break;
		}
		WebElement leftMenuItemElement = driver.findElement(By.xpath("//span[@class='LeftNav-linkText' and contains(text(), '"+parametrListItem+"')]"));
		leftMenuItemElement.click();
	}
	// Choose New menu item
	public void chooseNewMenuItem(NewMenuItem menuItem) {
		String parametrListItem = null;
		switch(menuItem) { 
		case FOLDER: parametrListItem = "Folder";  break;
		case WORD_DOCUMENT: parametrListItem = "Word document";  break;
		case EXCEL_WORKBOOK: parametrListItem = "Excel workbook";  break;
		case POWERPOINT_DOCUMENT: parametrListItem = "PowerPoint presentation";  break;
		case ONENOTE_NOTEBOOK: parametrListItem = "OneNote notebook";  break;
		case FORMS_FOR_EXCEL: parametrListItem = "Forms for Excel";  break;
		case LINK: parametrListItem = "Link";  break;
		}
		WebElement folder = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(), '"+parametrListItem+"')]")));
		folder.click();
	}
	public void specifyNewFolderName(String fn) {
		txtEnterFolderName = driver.findElement(By.xpath("//input[@aria-label='Enter your folder name']"));
		txtEnterFolderName.sendKeys(fn, Keys.ENTER);
	}
	
	public void addItemOneDrive_NewMenu(NewMenuItem menuItem) throws InterruptedException {
		Thread.sleep(2000);
		btnNew = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@name='New']")));
		//btnNew = driver.findElement(By.xpath("//button[@name='New']"));
		btnNew.click();
		chooseNewMenuItem(menuItem);
		Thread.sleep(1000);
	}
	public String specifyNewLink(String link, String addition) throws InterruptedException {
		
			txtEnterTheLink = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='od-CreateShortcut-urlField']")));
			txtEnterTheLink.sendKeys(link);
			btnCreateButton = driver.findElement(By.xpath("//button[@id='od-CreateShortcut-createButton']"));
			btnCreateButton.click();
			return link;
		
		
	}
	
	public void addNewLinkOneDrive(String fn) throws InterruptedException {
		btnNew = driver.findElement(By.xpath("//button[@name='New']"));
		btnNew.click();
	}
	
}
