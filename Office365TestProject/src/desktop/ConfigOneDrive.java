package desktop;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ConfigOneDrive {
	

		protected static WiniumDriver driver = null;
	    static WiniumDriverService service = null;
	    static DesktopOptions options = null;
	    static ExtentHtmlReporter htmlReporter;
	    protected static ExtentReports extent;
		@BeforeTest
		public static void setupEnvironment(){
			htmlReporter = new ExtentHtmlReporter("extentReportOneDriveDesktop.html");
	    	extent = new ExtentReports();
	    	extent.attachReporter(htmlReporter);
		    options = new DesktopOptions(); //Instantiate Winium Desktop Options
		    options.setApplicationPath("C:\\Users\\michapru\\AppData\\Local\\Microsoft\\OneDrive\\OneDrive.exe");
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
			//driver.quit();
		    driver.close();
		}

		@AfterTest
		public void tearDown(){
			extent.flush();
		    service.stop();
		}
		 /***************** 
			 * Random method *
			 *****************/
			public int getRandom() {Random rand = new Random(); 
			int value = rand.nextInt(100000);
			return value;}
		
	// https://github.com/2gis/Winium.Desktop/wiki/Supported-Commands
		
		
	}
