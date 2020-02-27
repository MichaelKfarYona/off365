package desktop;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Config {
	protected static WiniumDriver driver = null;
    static WiniumDriverService service = null;
    static DesktopOptions options = null;
    static ExtentHtmlReporter htmlReporter;
    WebElement btnConfirmCloseing = null;
    protected static ExtentReports extent;
    static long start = System.currentTimeMillis();
    static long timeSpent;
	@BeforeTest
	public static void setupEnvironment(){
		//int randomPort = randomNumInRange(9000, 9999);
		htmlReporter = new ExtentHtmlReporter("extentReportOutlookDesktop.html");
    	extent = new ExtentReports();
    	extent.attachReporter(htmlReporter);
    	
	    options = new DesktopOptions(); //Instantiate Winium Desktop Options
	    options.setApplicationPath("C:\\Program Files (x86)\\Microsoft Office\\Office16\\outlook.exe");
	    
	    File driverPath = new File("C:\\DRIVERS\\Winium.Desktop.Driver.exe");
	    System.setProperty("webdriver.winium.desktop.driver","C:\\DRIVERS\\Winium.Desktop.Driver.exe");
	    service = new WiniumDriverService.Builder().usingDriverExecutable(driverPath).usingPort(9999).withVerbose(true)
	            .withSilent(false).buildDesktopService();
	    try {
	        service.start();
	    } catch (IOException e) {
	        System.out.println("Exception while starting WINIUM service");
	        e.printStackTrace();
	    }
	}

	@BeforeMethod
	public void startDriver(){
	    driver = new WiniumDriver(service,options);
	}

	@AfterMethod
	public void stopDriver(){
		if(driver!= null) {
		  driver.close(); driver.quit();
		  }
		 
	}

	@AfterTest
	public void tearDown(){
		extent.flush();
	    service.stop();
	    // driver.close();
		// driver.quit();
	}
	@AfterSuite
	public void after() {
		timeSpent = System.currentTimeMillis() - start;
    	Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(timeSpent);
    	SimpleDateFormat format = new SimpleDateFormat("mm:ss");
    	System.out.println("Spent on scripting: "+format.format(cal.getTime()));
	}
	 /***************** 
		 * Random method *
		 *****************/
		public int getRandom() {Random rand = new Random(); 
		int value = rand.nextInt(100000);
		return value;}
		
		public static int randomNumInRange(int min, int max) {
			int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
			return randomNum;
		}
// https://github.com/2gis/Winium.Desktop/wiki/Supported-Commands
		public void clickOKButton() {
			List okButtonList = driver.findElements(By.xpath("//*[@Name='OK']"));
			if(okButtonList.size()>0) {
				okButtonList.get(0);
			btnConfirmCloseing = (WebElement) okButtonList.get(0);
			btnConfirmCloseing.click(); System.out.println("OK button .. ");
			}
		}
	
}
