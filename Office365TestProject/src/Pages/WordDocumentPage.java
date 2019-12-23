package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WordDocumentPage {

	WebDriver driver;
	private WebElement txtMainParagraph, linkFile = null;
	public WordDocumentPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	public void specifyText() {
		
		/*
		 * linkFile = driver.findElement(By.xpath("//span[contains(text(),'File')]"));
		 * linkFile.click();
		 */
		txtMainParagraph = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[@class='Paragraph']")));
		txtMainParagraph.sendKeys("TEXT_TEST_TEXT_TEST_TEXT_TEST_TEXT_TEST_TEXT_TEST_TEXT_TEST_");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
