package Pages;

import java.util.Random;

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
		FOLDER, WORD_DOCUMENT,EXCEL_WORKBOOK, POWERPOINT_DOCUMENT,ONENOTE_NOTEBOOK,FORMS_FOR_EXCEL, LINK
	}

	WebElement btnNew, btnUploadFilesFromPC, txtEnterFolderName, btnMagnifier, btnCreate, elementFromList = null;
	WebElement btnFiles, btnRecent, btnShared, btnRecycleBin, txtEnterTheLink, btnCreateButton= null;
	final String folderName = "FOLDER_NAME_";
	public int getRandom() {Random rand = new Random(); int value = rand.nextInt(100000); return value;}
	
	WebDriver driver;
	public OneDrive(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
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
		WebElement folder = driver.findElement(By.xpath("//span[contains(text(), '"+parametrListItem+"')]"));
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
		
			txtEnterTheLink = driver.findElement(By.xpath("//input[@id='od-CreateShortcut-urlField']"));
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
