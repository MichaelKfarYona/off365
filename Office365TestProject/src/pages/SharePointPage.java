package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SharePointPage {

	public enum LeftBottomMenuItem {
		MAIL, CALENDAR, PEOPLE, TO_DO
	}

	WebDriver driver;
	WebElement txtSearchField, btnMainSharePointPage = null;

	public SharePointPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void searchInSharePoint(String item) throws InterruptedException {
		txtSearchField = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='search']")));
		txtSearchField.sendKeys(item, Keys.ENTER);
		Thread.sleep(3000);
	}
	public void backToMainSharePoinPage() {
		btnMainSharePointPage = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'SharePoint')]")));
		btnMainSharePointPage.click();
	}
}
