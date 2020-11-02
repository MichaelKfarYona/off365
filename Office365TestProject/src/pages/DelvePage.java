package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DelvePage {
	public enum LeftMenu {HOME, ME, FAVORITES}
	WebDriver driver;
	
	public DelvePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void chooseLeftMenuItem(LeftMenu item) {
		String menuItem = "";
		switch(item){
		case HOME : menuItem = "Home"; break;
		case ME : menuItem = "Me"; break;
		case FAVORITES : menuItem = "Favorites"; break;
		}
		WebElement leftMenuItem = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@data-selector='tagLabel' and contains(text(),'"+menuItem+"')]")));
		leftMenuItem.click();
	}
	
	public boolean checkListFile() {
		List<WebElement> listFile = driver.findElements(By.xpath("//div[@data-selector='tidbit']"));
		if(listFile.size()>0) {
			return true;
		}
		else return false;
		
	}
	
	
}
