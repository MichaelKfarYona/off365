package desktop;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Config_OutOffOutlook {
	protected static WiniumDriver driver = null;
    static WiniumDriverService service = null;
    static DesktopOptions options = null;
    static ExtentHtmlReporter htmlReporter;
   
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
	
	@AfterTest
	public void tearDown(){
		extent.flush();
	    service.stop();
	    // driver.close();
		// driver.quit();
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
	
}
