package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PowerpointPresentationPage {

	public enum TopMenuTab{
		FILE, HOME, INSERT, DESIGN, TRANSITIONS, ANIMATIONS, SLIDE_SHOW, REVIEW, VIEW, HELP}
	WebDriver driver;
	private WebElement txtField, linkFile, menuTabElement = null;
	public PowerpointPresentationPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);}
	
	public void chooseTopMenuOption(TopMenuTab menuItem){
		String parametrTopMenuItem = null;
		switch(menuItem) { 
		case FILE: parametrTopMenuItem = "File";  break;
		case HOME: parametrTopMenuItem = "Home";  break;
		case INSERT: parametrTopMenuItem = "Insert";  break;
		case DESIGN: parametrTopMenuItem = "Design";  break;
		case TRANSITIONS: parametrTopMenuItem = "Transitions";  break;
		case ANIMATIONS: parametrTopMenuItem = "Animations";  break;
		case SLIDE_SHOW: parametrTopMenuItem = "Slide show";  break;
		case REVIEW: parametrTopMenuItem = "Review";  break;
		case VIEW: parametrTopMenuItem = "View";  break;
		case HELP: parametrTopMenuItem = "Help";  break;
	} 
		menuTabElement = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div//button//*[contains(text(),'"+parametrTopMenuItem+"')]")));
		menuTabElement.click();
	}	
}
