package pages;


import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import pages.OneDrive.LeftMenuItem;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Settings {
	
	 public enum ApplicationName{
			OUTLOOK, ONEDRIVE, WORD, EXCEL, POWERPOINT, ONENOTE, SHAREPOINT,TEAMS,YAMMER,DYNAMICS365,POWER_AUTOMATE, FORMS, PLANNER, TODO, DELVE
		}
	 //
	 public void pasteFromClipboard(String text) throws AWTException {
		 Robot robot = new Robot();
		 Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	 		clipboard.setContents(new StringSelection(text), null);
	 		robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        robot.keyRelease(KeyEvent.VK_V);
	 }
		public void chooseApplicationByName(ApplicationName item) throws InterruptedException {
			List<WebElement> allApps = driver.findElements(By.xpath("//i[@data-icon-name='allAppsLogo']"));
	    	   //Thread.sleep(3000);
	    	   if(allApps.size()>0) {
	    		   allApps.get(0).click();
	    		   Thread.sleep(2000);
	    	   }
	       
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
			
			
			WebElement leftMenuItemElement = (new WebDriverWait(driver, 17))
			.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@title='"+parametrListItem+"' and contains(text(),'"+parametrListItem+"')]")));
			
			/* WebElement leftMenuItemElement = driver.findElement(By.xpath("//div[@class='hero-container']//div[@title='"+parametrListItem+"']"));
			*/
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
	
/* LOGIN NAME */
    // Prod
    String loginName = "michapru@amdocs.com"; String password = "Ahmshere577561)";
    
    // ***** Michael Lab user *****
    
    // String loginName = "Michael@msglab.tech"; String password = "Ahmshere577561^";
   
    // ***** Steve user *****
    
    //String loginName = "yoelap@amdocs.com"; String password = "Random!224466";
    
    static ExtentTest testLogger;
    @BeforeTest
    public void ReportSetup() {
    	htmlReporter = new ExtentHtmlReporter("extentReportOffice365.html");
    	// set Lang https://overcoder.net/q/1009176/%D1%83%D1%81%D1%82%D0%B0%D0%BD%D0%BE%D0%B2%D0%B8%D1%82%D0%B5-%D1%8F%D0%B7%D1%8B%D0%BA-chrome-%D1%81-%D0%BF%D0%BE%D0%BC%D0%BE%D1%89%D1%8C%D1%8E-selenium-chromedriver
    	extent = new ExtentReports();
    	
    	extent.attachReporter(htmlReporter);
    }
    @BeforeSuite
    public void setTimePoint() {
    	
    	start = System.currentTimeMillis();
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
    
    public void loginAsAmdocsUserSettingsM(ApplicationName appName) throws InterruptedException, IOException{
    	//testLogger = extent.createTest(getClass().getName());
    	//testLogger.log(Status.INFO, "Login as User");
        MainPage mainPage = new MainPage(driver);
		Thread.sleep(3000);
        mainPage.setLogin("Michael@msglab.tech"); 
        if(loginName.equals("Michael@msglab.tech")) {mainPage.setPassword(password); mainPage.signInNo();}
        //testLogger.log(Status.INFO, "Open Office 365");
        Thread.sleep(3000);
        Office365Page officePage = new Office365Page(driver);
        System.out.println("OFFICE 365");
        
		/*
		 * List<WebElement> allApps =
		 * driver.findElements(By.xpath("//div[@class and contains(text(),'All apps')]")
		 * ); //Thread.sleep(3000); if(allApps.size()>0) { allApps.get(0).click();
		 * System.out.println(" Settings.java ***** "+ allApps.get(0));
		 * 
		 * }
		 */
        
        //Thread.sleep(1000);
        //testLogger.log(Status.INFO, "Click Teams link ");
        chooseApplicationByName(appName);
       // officePage.chooseApplication(appName);
        Thread.sleep(2000);
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
       driver.switchTo().window(tabs.get(1));
       //System.out.println("******* "+appName.toString());
       if(appName.toString().equals("TEAMS")) {
  	   TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
  	   try { Thread.sleep(10000);
       } catch (InterruptedException e) {e.printStackTrace();        }
       System.out.println(appName);      
       teamsPage.clickByLinkWeb();
       }
        try { Thread.sleep(10000);
        } catch (InterruptedException e) {e.printStackTrace();        }
       // test.log(Status.INFO, "Logged in as User - "+ loginName);
    }
    
    // MAIN METHOD
    public void loginAsAmdocsUserSettings(ApplicationName appName) throws InterruptedException, IOException{
    	//testLogger = extent.createTest(getClass().getName());
    	//testLogger.log(Status.INFO, "Login as User");
        MainPage mainPage = new MainPage(driver);
		Thread.sleep(3000);
        mainPage.setLogin(loginName); 
        
        if(loginName.equals("Michael@msglab.tech")) {mainPage.setPassword(password); mainPage.signInNo();}
        //testLogger.log(Status.INFO, "Open Office 365");
        Thread.sleep(3000);
        Office365Page officePage = new Office365Page(driver);
        System.out.println("OFFICE 365");
       // officePage.showAllApps();
        
		/*
		 * List<WebElement> allApps =
		 * driver.findElements(By.xpath("//div[@class and contains(text(),'All apps')]")
		 * ); //Thread.sleep(3000); if(allApps.size()>0) { allApps.get(0).click();
		 * System.out.println(" Settings.java ***** "+ allApps.get(0));
		 * 
		 * }
		 */
        
        Thread.sleep(1000);
        //testLogger.log(Status.INFO, "Click Teams link ");
        chooseApplicationByName(appName);
       // officePage.chooseApplication(appName);
        Thread.sleep(2000);
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
       driver.switchTo().window(tabs.get(1));
       //System.out.println("******* "+appName.toString());
       if(appName.toString().equals("TEAMS")) {
  	   TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
  	   try { Thread.sleep(10000);
       } catch (InterruptedException e) {e.printStackTrace();        }
       System.out.println(appName);      
       teamsPage.clickByLinkWeb();
       }
        try { Thread.sleep(10000);
        } catch (InterruptedException e) {e.printStackTrace();        }
       // test.log(Status.INFO, "Logged in as User - "+ loginName);
    }

    // For One Note
    public void loginAsAmdocsUserSettingsOneNote(ApplicationName appName) throws InterruptedException, IOException{
    	//testLogger = extent.createTest(getClass().getName());
    	//testLogger.log(Status.INFO, "Login as User");
        MainPage mainPage = new MainPage(driver);
		Thread.sleep(3000);
        mainPage.setLogin(loginName); 
        if(loginName.equals("Michael@msglab.tech")) {mainPage.setPassword(password); mainPage.signInNo();}
        //testLogger.log(Status.INFO, "Open Office 365");
        Thread.sleep(3000);
        Office365Page officePage = new Office365Page(driver);
        System.out.println("OFFICE 365");
        
        //testLogger.log(Status.INFO, "Click Teams link ");
        chooseApplicationByName(appName);
       // officePage.chooseApplication(appName);
        Thread.sleep(2000);
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
       driver.switchTo().window(tabs.get(0));
       //System.out.println("******* "+appName.toString());
       if(appName.toString().equals("TEAMS")) {
  	   TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
  	   try { Thread.sleep(2000);
       } catch (InterruptedException e) {e.printStackTrace();        }
       System.out.println(appName);      
       teamsPage.clickByLinkWeb();
       }
        try { Thread.sleep(2000);
        } catch (InterruptedException e) {e.printStackTrace();        }
       // test.log(Status.INFO, "Logged in as User - "+ loginName);
    }
    
    public void loginAsAmdocsUserSettingsTD(ApplicationName appName) throws InterruptedException, IOException{
    	//testLogger = extent.createTest(getClass().getName());
    	//testLogger.log(Status.INFO, "Login as User");
        MainPage mainPage = new MainPage(driver);
		Thread.sleep(3000);
        mainPage.setLogin(loginName); 
        if(loginName.equals("Michael@msglab.tech")) {mainPage.setPassword(password); mainPage.signInNo();}
        //testLogger.log(Status.INFO, "Open Office 365");
        Thread.sleep(3000);
        Office365Page officePage = new Office365Page(driver);
        System.out.println("OFFICE 365");
        
        //testLogger.log(Status.INFO, "Click Teams link ");
        chooseApplicationByName(appName);
       // officePage.chooseApplication(appName);
        Thread.sleep(2000);
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
       driver.switchTo().window(tabs.get(1));
       //System.out.println("******* "+appName.toString());
       if(appName.toString().equals("TEAMS")) {
  	   TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
  	   try { Thread.sleep(2000);
       } catch (InterruptedException e) {e.printStackTrace();        }
       System.out.println(appName);      
       teamsPage.clickByLinkWeb();
       }
        try { Thread.sleep(2000);
        } catch (InterruptedException e) {e.printStackTrace();        }
       // test.log(Status.INFO, "Logged in as User - "+ loginName);
    }
    
 // For One Note Production
    public void loginAsAmdocsUserSettingsOneNoteProduction(ApplicationName appName) throws InterruptedException, IOException{
    	//testLogger = extent.createTest(getClass().getName());
    	//testLogger.log(Status.INFO, "Login as User");
        MainPage mainPage = new MainPage(driver);
		Thread.sleep(3000);
        mainPage.setLogin("yoelap@amdocs.com"); 
        
		/*
		  if(loginName.equals("yoelap@amdocs.com"))
		  {mainPage.setPassword("Random#224466"); mainPage.signInNo();}
		 */
        //testLogger.log(Status.INFO, "Open Office 365");
        Thread.sleep(3000);
        Office365Page officePage = new Office365Page(driver);
        System.out.println("OFFICE 365");
        
        //testLogger.log(Status.INFO, "Click Teams link ");
        chooseApplicationByName(appName);
       // officePage.chooseApplication(appName);
        Thread.sleep(2000);
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
       driver.switchTo().window(tabs.get(0));
       //System.out.println("******* "+appName.toString());
       if(appName.toString().equals("TEAMS")) {
  	   TeamsChannelPage teamsPage = new TeamsChannelPage(driver);
  	   try { Thread.sleep(2000);
       } catch (InterruptedException e) {e.printStackTrace();        }
       System.out.println(appName);      
       teamsPage.clickByLinkWeb();
       }
        try { Thread.sleep(2000);
        } catch (InterruptedException e) {e.printStackTrace();        }
       // test.log(Status.INFO, "Logged in as User - "+ loginName);
    }
    
    public void loginAsAmdocsUserProduction(ApplicationName appName, int tabNumber) throws InterruptedException, IOException{
    	//testLogger = extent.createTest(getClass().getName());
    	//testLogger.log(Status.INFO, "Login as User");
        MainPage mainPage = new MainPage(driver);
		Thread.sleep(3000);
        mainPage.setLogin(loginName); 
        if(loginName.equals("yoelap@amdocs.com")) {mainPage.setPassword("Random#224466"); mainPage.signInNo();}
        //testLogger.log(Status.INFO, "Open Office 365");
        Thread.sleep(3000);
        Office365Page officePage = new Office365Page(driver);
        System.out.println("OFFICE 365");
        
        //testLogger.log(Status.INFO, "Click Teams link ");
        chooseApplicationByName(appName);
       // officePage.chooseApplication(appName);
        Thread.sleep(3000);
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
       driver.switchTo().window(tabs.get(tabNumber));
       //System.out.println("******* "+appName.toString());
		/*
		 * if(appName.toString().equals("TEAMS")) { TeamsChannelPage teamsPage = new
		 * TeamsChannelPage(driver); try { Thread.sleep(2000); } catch
		 * (InterruptedException e) {e.printStackTrace(); } System.out.println(appName);
		 * teamsPage.clickByLinkWeb(); } try { Thread.sleep(2000); } catch
		 * (InterruptedException e) {e.printStackTrace(); }
		 */
       // test.log(Status.INFO, "Logged in as User - "+ loginName);
    }
    
    public void loginAsAmdocsUserSettingsWithoutApp() throws InterruptedException, IOException{
    	//testLogger = extent.createTest(getClass().getName());
    	//testLogger.log(Status.INFO, "Login as User");
        MainPage mainPage = new MainPage(driver);
		Thread.sleep(3000);
        mainPage.setLogin(loginName); 
        if(loginName.equals("Michael@msglab.tech")) {mainPage.setPassword(password); mainPage.signInNo();}
        //testLogger.log(Status.INFO, "Open Office 365");
     
        
        System.out.println("OFFICE 365");
        
        
        Thread.sleep(2000);
        //testLogger.log(Status.INFO, "Click Teams link ");
       
       // officePage.chooseApplication(appName);
       
       //System.out.println("******* "+appName.toString());
       
       
       // test.log(Status.INFO, "Logged in as User - "+ loginName);
    }
    
	/* Switch between tabs */
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