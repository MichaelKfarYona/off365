package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Pages.OneDrive.LeftMenuItem;

public class PowerAutomate {
	public enum LeftMenuItemPowerAutomate{
		HOME, ACTION_ITEMS, MY_FLOWS, CREATE, TEMPLATES,CONNECTORS, DATA, AI_BUILDER, SOLUTIONS, LEARN
	}
	//div[@role='presentation']//div[contains(text(),'')]

	WebDriver driver;
	private WebElement btnCreate, btnAutomatedFlow = null;
	private WebElement txtFlowName, btnFlowsTrigger, btnCreateFlow = null;
	private WebElement txtFolderName, folderPic,rootFolder = null;
	private WebElement btnNewStep = null;
	public PowerAutomate(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	 
	public void clickByLeftMenuItem(LeftMenuItemPowerAutomate item) throws InterruptedException {
		String parametrListItem = null;
		switch(item) { 
		case HOME: parametrListItem = "Home";  break;
		case ACTION_ITEMS: parametrListItem = "Action items";  break;
		case MY_FLOWS: parametrListItem = "My flows";  break;
		case CREATE: parametrListItem = "Create";  break;
		case TEMPLATES: parametrListItem = "Recycle bin";  break;
		case CONNECTORS: parametrListItem = "Connectors";  break;
		case DATA: parametrListItem = "Data";  break;
		case AI_BUILDER: parametrListItem = "AI Builder";  break;
		case SOLUTIONS: parametrListItem = "Solutions";  break;
		case LEARN: parametrListItem = "Learn";  break;
		}
		WebElement leftMenuItemElement = driver.findElement(By.xpath("//div[@role='presentation']//div[contains(text(),'"+parametrListItem+"')]"));
		leftMenuItemElement.click();
		Thread.sleep(2000);
		driver.switchTo().alert().accept(); // ALERT
	}
	
	public void createNewFlow() {
		btnCreate = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ms-Button-label label-113' and contains(text(),'Create')]")));
		btnCreate.click();
	}
	public void addAutomatedFlow(String autoFlowName) throws InterruptedException {
		boolean isPresent;
		if(isPresent = driver.findElements(By.xpath("//div[@class='fl-Announcement']//button[@aria-label='Close']")).size()>0) {		WebElement popup = driver.findElement(By.xpath("//div[@class='fl-Announcement']//button[@aria-label='Close']"));
		popup.click();}
		else {System.out.println("There is no notification!");}
		
		WebElement linkAutoFlow = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div//h2[@class='fl-CardTitle cardTitle-141' and contains(text(),'Automated flow')]")));
		linkAutoFlow.click();
		
		//Thread.sleep(2000);
		//driver.switchTo().activeElement();
		txtFlowName = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@aria-label='Flow name']")));
		txtFlowName.sendKeys(autoFlowName);
		btnFlowsTrigger = driver.findElement(By.xpath("//div[@aria-live='polite']//span[contains(text(),'OneDrive for Business')]"));
		btnFlowsTrigger.click();
		btnCreateFlow = driver.findElement(By.xpath("//div[@class='ms-Button-label label-158' and contains(text(),'Create')]"));btnCreateFlow.click();
		System.out.println("TITLE _ "+driver.getTitle());
		txtFolderName = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='msla-editor-input-control']")));
		txtFolderName.click();
		
		Thread.sleep(2000);
		folderPic = driver.findElement(By.xpath("//div[@class='msla-parameter-picker-area']"));
		folderPic.click();
		rootFolder = driver.findElement(By.xpath("//div[@class='msla-text' and contains(text(),'Root')]"));
		rootFolder.click();
		Thread.sleep(2000);
		btnNewStep = driver.findElement(By.xpath("//button[contains(text(),'New step')]"));
		btnNewStep.click();
	}
	public void mailForm_ChooseAction(String sendMailTo, String subject, String mailBody) throws InterruptedException {
		WebElement mailAction = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@title='Mail']")));
		mailAction.click();
		WebElement sendAnEmailNotification = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='msla-operation-text']")));
		sendAnEmailNotification.click();
		
		WebElement txtEmailBody = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//flow-designer/div/div/div/div/div/div[1]/div/div/div/div/div[2]/div/div/div[2]/div/div/div[1]/div[2]/div/div/div/div[3]/div/div/div[2]/div/div[2]/div/div[2]/div/div/div/div")));
		txtEmailBody.click();
		txtEmailBody.sendKeys(mailBody);
		
		WebElement txtSubject = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='public-DraftStyleDefault-block public-DraftStyleDefault-ltr']")));
		txtSubject.click();
		txtSubject.sendKeys(subject);
		WebElement txtTo = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='msla-input-control-token-literal']")));
		txtTo.sendKeys(sendMailTo);

		 WebElement btnSaveFlow = driver.findElement(By.xpath("//button[@class='msla-button msla-action-buttons' and contains(text(),'Save')]"));
		 btnSaveFlow.click(); Thread.sleep(2000);
	}
	public boolean flowPersistanceCheck(LeftMenuItemPowerAutomate flowName, String flowNameinTable) throws InterruptedException {
		clickByLeftMenuItem(flowName);
		boolean isPersist;
		 if(isPersist = driver.findElements(By.xpath("//div[@aria-live='polite']//a[contains(text(),'"+flowNameinTable+"')]")).size()>0){return true;}
		 else {return false;}
		
		
	}
	public void createFolder() {
		System.out.println("TITLE _ "+driver.getTitle());
		txtFolderName = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='msla-editor-input-control']")));
		txtFolderName.sendKeys("/");
	}
}
