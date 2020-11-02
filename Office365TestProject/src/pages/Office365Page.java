package pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.Settings.ApplicationName;

public class Office365Page {
	WebDriver driver;
	private WebElement appLink;
	
	public enum ApplicationName{
		OUTLOOK, ONEDRIVE, WORD, EXCEL, POWERPOINT, ONENOTE, SHAREPOINT,TEAMS,YAMMER,DYNAMICS365,POWER_AUTOMATE, FORMS, PLANNER, TODO, DELVE
	}
	public void chooseApplicationByName(ApplicationName item) {
		String parametrListItem = null;
		switch(item) { 
		case OUTLOOK: parametrListItem = "Outlook";  break;
		case ONEDRIVE: parametrListItem = "OneDrive";  break;
		case WORD: parametrListItem = "Word";  break;
		case EXCEL: parametrListItem = "Excel";  break;
		case POWERPOINT: parametrListItem = "PowerPoint";  break;
		case ONENOTE: parametrListItem = "OneNote"; break; 
		case SHAREPOINT: parametrListItem = "SharePoint"; break;
		case TEAMS:parametrListItem = "Teams"; break;
		case YAMMER:parametrListItem = "Yammer"; break;
		case DYNAMICS365:parametrListItem = "Dynamics 365"; break;
		case POWER_AUTOMATE:parametrListItem = "Power Automate"; break;
		case FORMS:parametrListItem = "Forms"; break;
		case PLANNER:parametrListItem = "Planner"; break;
		case TODO:parametrListItem = "To Do"; break;
		case DELVE:parametrListItem = "Delve"; break;
		}
		WebElement leftMenuItemElement = driver.findElement(By.xpath("//div[@class='hero-container']//div[@title='"+parametrListItem+"']"));
		leftMenuItemElement.click();
	}
	
    public Office365Page(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    // Show all apps
    public void OpenAllApps() {
    	List<WebElement> listEl = driver.findElements(By.xpath("//i[@data-icon-name='allAppsLogo']"));
    	if(listEl.size()>0)
    	{
    	WebElement allApps = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//i[@data-icon-name='allAppsLogo']")));
    	allApps.click();
    	}
    	
    }
    // open file . O365 main page
   public void openFileOnMainPage(String fileName) {
	   // .//button[@type='button' and contains(text(),'Document.docx')]/ancestor::span
	   WebElement fileInTheList = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div/span[contains(text(),'"+fileName+"')]")));
	   fileInTheList.click();
   }
   // **********************************************************************************************************8
   public void cleanCanvas() throws AWTException, InterruptedException {
	   WebElement fileInTheList = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='Outline']")));
	   fileInTheList.click();
	 
	   Thread.sleep(1000);
	   
	   Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_DELETE);
		
		Thread.sleep(2000);
   }
    
   @FindBy(xpath = "//span[@class='ms-ohp-svg-Icon ms-ohp-Icon ms-ohp-Icon--teamsLogo ms-ohp-Icon--teamsLogoFill ng-star-inserted']")
    public WebElement teamsLink;
 
	/*****************************************
	 * Choose application on Office 365 page * 
	 * @throws InterruptedException 
	 *****************************************/
   				   
       public void chooseApplication(String appName) throws InterruptedException {
		/*
		 * List<WebElement> allApps =
		 * driver.findElements(By.xpath("//div[@class and contains(text(),'All apps')]")
		 * ); //Thread.sleep(3000); if(allApps.size()>0) { allApps.get(0).click();
		 * System.out.println(" Office365.java ***** "+ allApps.get(0)); }
		 */
    	   appLink = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@title='"+appName+"']"))); 
    			   //driver.findElement(By.xpath("//div[@title='"+appName+"']"));
    	   appLink.click();
       }
       
    public void WebApplication() {
    	WebElement link = driver.findElement(By.xpath("//a[@class='use-app-lnk']"));
    	link.click();
    }

	public void showAllApps() {
		WebElement allApps = driver.findElement(By.xpath("//i[@data-icon-name='allAppsLogo']"));
		allApps.click();
		
	}
}
