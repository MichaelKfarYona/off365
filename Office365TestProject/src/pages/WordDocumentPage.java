package pages;

import java.util.concurrent.ThreadLocalRandom;

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
	public void specifyText() throws InterruptedException {
		String st = randomStringGneration(50);
		txtMainParagraph = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[@class='Paragraph']")));
		txtMainParagraph.sendKeys("TEST");
		
	}
	
	
	
	
	
	public String randomStringGneration(int lineLength) {
    	String result = null;
    	/*Generator sluchainih simvolov*/
		//int lineLength = 36;
		char[] alphabetA = new String("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789").toCharArray();
		for(int n=0; n < lineLength; n++) {
			int randomSymbol = randomNumInRange(0,alphabetA.length-1);
			char cr = alphabetA[randomSymbol];
			result = String.valueOf(cr);
		}
		return result;
    }
	
	
	public static int randomNumInRange(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}
	
}
