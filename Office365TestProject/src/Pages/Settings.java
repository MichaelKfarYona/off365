package Pages;


import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;


public class Settings {

	protected static ExtentReports extent;
	
    private static ThreadLocal parentTest = new ThreadLocal();
    private static ThreadLocal test = new ThreadLocal();
	ExtentHtmlReporter htmlReporter;
    String driverPath = "c:\\DRIVERS\\chromedriver.exe";
    protected static WebDriver driver;
    //Michael Lab user
    String loginName = "Michael@msglab.tech"; String password = "Amdocs@123";
    //Steve user
   // String loginName = "yoelap@amdocs.com"; String password = "Newpass1!";
    static ExtentTest testLogger;
    @BeforeTest
    public void ReportSetup() {
    	htmlReporter = new ExtentHtmlReporter("extentReportOffice365.html");
    	//htmlReporter = new ExtentHtmlReporter("C:\\REPORTS\\extentReportOffice365.html");
    	// create ExtentReports and attach reporter(s)
    	// set Lang https://overcoder.net/q/1009176/%D1%83%D1%81%D1%82%D0%B0%D0%BD%D0%BE%D0%B2%D0%B8%D1%82%D0%B5-%D1%8F%D0%B7%D1%8B%D0%BA-chrome-%D1%81-%D0%BF%D0%BE%D0%BC%D0%BE%D1%89%D1%8C%D1%8E-selenium-chromedriver
    	extent = new ExtentReports();
    	
    	extent.attachReporter(htmlReporter);
    }
    @BeforeMethod
    public void Setup(){
    	ChromeOptions options = new ChromeOptions();
    	
    	options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
    	
    	options.addArguments("--disable-notifications");
    	
    	options.addArguments("--lang=en"); 
    	System.setProperty("webdriver.chrome.driver",driverPath);
    	driver = new ChromeDriver(options);
    	driver.manage().window().maximize();
    	driver.get("https://login.microsoftonline.com/");
    	driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
    }

    @AfterMethod
    public static void tearDown(ITestResult results){
		/*
		 * if (results.getStatus() == ITestResult.FAILURE) { testLogger.log(Status.FAIL,
		 * "Test case failed: " +results.getName()); testLogger.log(Status.FAIL,
		 * "Test case failed: " +results.getThrowable()); } else if(results.getStatus()
		 * == ITestResult.SKIP) {testLogger.log(Status.SKIP,
		 * "Test case skipped is: "+results.getName()); }
		 */
		//extent.flush();
        driver.close();
        driver.quit();
    }
    @AfterTest
    public static void tearDownAll() {
    	
        
    	extent.flush();
    	
    }
    
    
    public void loginAsAmdocsUserSettings(String appName) throws InterruptedException, IOException{
    	testLogger.log(Status.INFO, "Login as User");
        MainPage mainPage = new MainPage(driver);
		Thread.sleep(2000);
        mainPage.setLogin(loginName); 
        if(loginName.equals("Michael@msglab.tech")) {mainPage.setPassword(password); mainPage.signInNo();}
        testLogger.log(Status.INFO, "Open Office 365");
        Office365Page officePage = new Office365Page(driver);
        System.out.println("OFFICE 365");
        Thread.sleep(2000);
        testLogger.log(Status.INFO, "Click Teams link ");
        officePage.chooseApplication(appName);
        /************************
         * Switch between tabs *
         ************************/
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
       driver.switchTo().window(tabs.get(1));
       
       if(appName == "Teams") {
  	   TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
  	   try { Thread.sleep(8000);
       } catch (InterruptedException e) {e.printStackTrace();        }
       System.out.println(appName);      
       teamsPage.clickByLinkWeb();
       }
        try { Thread.sleep(8000);
        } catch (InterruptedException e) {e.printStackTrace();        }
       // test.log(Status.INFO, "Logged in as User - "+ loginName);
    }
    
    public void switchNewOpenedTab(int number) {
    	ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		  driver.switchTo().window(tabs.get(number));
    }

	/*********************************
	 * Random string with INT length *
	 *********************************/
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
    /***************** 
	 * Random method *
	 *****************/
	public int getRandom() {Random rand = new Random(); 
	int value = rand.nextInt(100000);
	return value;}
	/************************** 
	 * Random method in range *
	 **************************/
	public static int randomNumInRange(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}

}