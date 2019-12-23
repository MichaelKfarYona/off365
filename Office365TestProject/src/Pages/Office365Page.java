package Pages;

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

public class Office365Page {
	WebDriver driver;
	private WebElement appLink;
	
    public Office365Page(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
   @FindBy(xpath = "//span[@class='ms-ohp-svg-Icon ms-ohp-Icon ms-ohp-Icon--teamsLogo ms-ohp-Icon--teamsLogoFill ng-star-inserted']")
    public WebElement teamsLink;

	/*****************************************
	 * Choose application on Office 365 page * 
	 * @throws InterruptedException 
	 *****************************************/
       public void chooseApplication(String appName) throws InterruptedException {
    	   //Thread.sleep(3000);
    	   appLink = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@title='"+appName+"']"))); 
    			   //driver.findElement(By.xpath("//div[@title='"+appName+"']"));
    	   appLink.click();
       }
       
    public void WebApplication() {
    	WebElement link = driver.findElement(By.xpath("//a[@class='use-app-lnk']"));
    	link.click();
    }
}
