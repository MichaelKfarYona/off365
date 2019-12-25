package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Pages.OneDrive.LeftMenuItem;

public class ExcelWorkboobPage {
	public enum TopMenuTab{
		HOME, INSERT, DATA, REVIEW, VIEW, HELP
	} 
	WebDriver driver;
	private WebElement txtField, linkFile, menuTabElement = null;
	public ExcelWorkboobPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	public void chooseTabInTheDocument(TopMenuTab menuTab) {
		
			String parametrTopMenuItem = null;
			switch(menuTab) { 
			case HOME: parametrTopMenuItem = "Home";  break;
			case INSERT: parametrTopMenuItem = "Insert";  break;
			case DATA: parametrTopMenuItem = "Data";  break;
			case REVIEW: parametrTopMenuItem = "Review";  break;
			case VIEW: parametrTopMenuItem = "View";  break;
			case HELP: parametrTopMenuItem = "Help";  break;
			}
			menuTabElement = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='cui-tt-span' and contains(text(),'\"+parametrTopMenuItem+\"')]")));
		//menuTabElement = driver.findElement(By.xpath("//span[@class='cui-tt-span' and contains(text(),'"+parametrTopMenuItem+"')]"));
		menuTabElement.click();
	}
	// Add some info to canvas
	public void specifyStringToTheLine(String anyInfo, int lineNumber) {
		WebElement lineOnCanvas = driver.findElement(By.xpath("//div[@class='ewr-rhc' and contains(text(),'"+lineNumber+"')]"));
		lineOnCanvas.click();
		lineOnCanvas.sendKeys(anyInfo);
		
	}
	
}
