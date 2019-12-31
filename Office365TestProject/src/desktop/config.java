package desktop;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;

public class config {
// https://github.com/2gis/Winium.Desktop/wiki/Supported-Commands
	public static void main(String[] args) throws MalformedURLException, Exception {
		DesktopOptions options = new DesktopOptions();
		WiniumDriver winiumDriver;
		options.setApplicationPath("C:\\Users\\michapru\\AppData\\Local\\Microsoft\\Teams\\current\\Teams.exe");
		//options.setApplicationPath("C:\\Program Files (x86)\\Microsoft Office\\Office16\\outlook.exe");
		
		winiumDriver = new WiniumDriver(new URL("http://localhost:9999"),options);
		
		
		Thread.sleep(2000);
		List<WebElement> list = winiumDriver.findElementsByXPath("//*");
		System.out.println("List size - "+list.size());
		list.get(0).sendKeys(Keys.F1);
		//list.get(0).sendKeys(Keys.chord(Keys.CONTROL, "c"));
		
		//WebElement tewv = winiumDriver.findElement(By.name("Calendar")); 
		//tewv.click();
		
		/*
		WebElement elementT = (new WebDriverWait(winiumDriver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.name("Calendar")));
		elementT.click();
		*/
		Thread.sleep(3000);
		
		winiumDriver.close();
		winiumDriver.quit();
		//winiumDriver.findElement(By.xpath("//*[contains(text(), 'Teams')]")).click();
		/*
		winiumDriver.findElement(By.name("New Email")).click();
		winiumDriver.switchTo().activeElement();
		*/
		//winiumDriver.switchTo().frame(1);
		//winiumDriver.switchTo().window(tabs.get(1));
		//winiumDriver.findElement(By.name("To")).sendKeys("blabla@mail.com");
		//winiumDriver.findElement(By.name("Subject"));
		/*
		 * WebElement s = winiumDriver.findElement(By.name("Seven")); s.click();
		 * winiumDriver.findElement(By.name("Seven")).click();
		 */
		
	}
 
}
