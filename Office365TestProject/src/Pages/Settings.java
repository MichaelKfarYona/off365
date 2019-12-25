package Pages;


import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


import Pages.OneDrive.LeftMenuItem;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;


public class Settings {
	 public enum ApplicationName{
			OUTLOOK, ONEDRIVE, WORD, EXCEL, POWERPOINT, ONENOTE, SHAREPOINT,TEAMS,YAMMER,DYNAMICS365,POWER_AUTOMATE, FORMS, PLANNER, TODO, DELVE
		}
		public void chooseApplicationByName(ApplicationName item) {
			String parametrListItem = null;
			switch(item) { 
			case OUTLOOK: parametrListItem = "Outlook";  break;
			case ONEDRIVE: parametrListItem = "OneDrive";  break;
			case WORD: parametrListItem = "Word";  break;
			case EXCEL: parametrListItem = "Excel";  break;
			case POWERPOINT: parametrListItem = "PowerPoint";  break;
			case ONENOTE: parametrListItem = "OneNote"; break; 
			case SHAREPOINT: parametrListItem = "SharePoint"; break;
			case TEAMS:parametrListItem = "Teams"; break;
			case YAMMER:parametrListItem = "Yammer"; break;
			case DYNAMICS365:parametrListItem = "Dynamics 365"; break;
			case POWER_AUTOMATE:parametrListItem = "Power Automate"; break;
			case FORMS:parametrListItem = "Forms"; break;
			case PLANNER:parametrListItem = "Planner"; break;
			case TODO:parametrListItem = "To Do"; break;
			case DELVE:parametrListItem = "Delve"; break;
			}
			WebElement leftMenuItemElement = driver.findElement(By.xpath("//div[@class='hero-container']//div[@title='"+parametrListItem+"']"));
			leftMenuItemElement.click();
		}
	protected static ExtentReports extent;
	static long timeSpent = 0;
	static long start = 0;
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
    @BeforeSuite
    public void setTimePoint() {start = System.currentTimeMillis();}
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
		
		//extent.flush();
        driver.close();
        driver.quit();
    }
    @AfterSuite
    public void timeSpent() {
    	timeSpent = System.currentTimeMillis() - start;
    	Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(timeSpent);
    	SimpleDateFormat format = new SimpleDateFormat("mm:ss");
    	System.out.println("Spent on scripting: "+format.format(cal.getTime()));
    }
    @AfterTest
    public static void tearDownAll() {
    	
    	
    	
    	extent.flush();
    	
    }
    
    
    public void loginAsAmdocsUserSettings(ApplicationName appName) throws InterruptedException, IOException{
    	//testLogger = extent.createTest(getClass().getName());
    	//testLogger.log(Status.INFO, "Login as User");
        MainPage mainPage = new MainPage(driver);
		Thread.sleep(3000);
        mainPage.setLogin(loginName); 
        if(loginName.equals("Michael@msglab.tech")) {mainPage.setPassword(password); mainPage.signInNo();}
        //testLogger.log(Status.INFO, "Open Office 365");
        Office365Page officePage = new Office365Page(driver);
        System.out.println("OFFICE 365");
        Thread.sleep(2000);
        //testLogger.log(Status.INFO, "Click Teams link ");
        chooseApplicationByName(appName);
       // officePage.chooseApplication(appName);
        /************************
         * Switch between tabs *
         ************************/
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
       driver.switchTo().window(tabs.get(1));
       //System.out.println("******* "+appName.toString());
       if(appName.toString().equals("TEAMS")) {
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