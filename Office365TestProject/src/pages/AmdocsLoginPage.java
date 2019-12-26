package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AmdocsLoginPage {

	WebDriver driver;

    AmdocsLoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
 
    @FindBy(xpath = "//input[@id='userNameInput']")
    public WebElement userNameField;
    
    @FindBy(xpath = "//input[@id='passwordInput']")
    public WebElement passwordField;

    @FindBy(xpath = "//span[@id='submitButton']")
    public WebElement signInButton; 
    
    public void setPasswordAndSignIn(String password){
    	passwordField.sendKeys(password);
        signInButton.click();
    }
}
