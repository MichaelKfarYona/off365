package tests;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.DefaultEditorKit.PasteAction;

import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.WiniumDriver;
import org.testng.TestNG;

import com.sun.jna.platform.win32.User32;

import desktop.pages.OutlookDesktop;

public class _exe {
	static ChromeDriver driver;
	
	
	public static String createDate(int number) {
		SimpleDateFormat SDFormat = new SimpleDateFormat("E MM/dd/yyyy"); 
	     Calendar cal = Calendar.getInstance(); 

	     String curr_date = SDFormat.format(cal.getTime()); 
	     System.out.println("Formatted Date: " + curr_date);
	     cal.add(Calendar.DATE, number);
	     
	     String newDate = SDFormat.format(cal.getTime()); // substract 1 month
	     System.out.println("Formatted Date: " + newDate);
	     return newDate;
	}
	
	public static String createTime(int d, int m) {
		SimpleDateFormat SDFormat = new SimpleDateFormat("h:mm a"); 
	     Calendar cal = Calendar.getInstance(); 

	     String curr_date = SDFormat.format(cal.getTime()); 
	     System.out.println("Formatted Date: " + curr_date);
	     
	     cal.add(Calendar.HOUR, d);
	     cal.add(Calendar.MINUTE, m);
	     
	     String newTime = SDFormat.format(cal.getTime()); 
	     System.out.println("Formatted Time: " + newTime);
	     return newTime;
	}
	
	public static void pasteString(String anystring) throws AWTException {
		StringSelection stringSelection2 = new StringSelection(anystring);
		Clipboard clipboard2 = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard2.setContents(stringSelection2, stringSelection2);
		Robot rob = new Robot();
		rob.keyPress(KeyEvent.VK_CONTROL);
		rob.keyPress(KeyEvent.VK_V);
		rob.keyRelease(KeyEvent.VK_V);
		rob.keyRelease(KeyEvent.VK_CONTROL); rob.delay(500);
	}

	public static void openOutlookAsDifferentUserRUNAS(String user, String password, String name) throws AWTException, InterruptedException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_WINDOWS);
		r.keyPress(KeyEvent.VK_R);
		r.keyRelease(KeyEvent.VK_R);
		r.keyRelease(KeyEvent.VK_WINDOWS);r.delay(2000);
		
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_A);
		r.keyRelease(KeyEvent.VK_A);
		r.keyRelease(KeyEvent.VK_CONTROL); r.delay(500);
		pasteString("cmd");
		r.keyPress(KeyEvent.VK_ENTER); r.keyRelease(KeyEvent.VK_ENTER);r.delay(1000);
		//String cmdCommand = "runas /user:"+user+" C:\\Program Files (x86)\\Microsoft Office\\Office16\\OUTLOOK.EXE";
		 pasteString("runas /user:"+user+" \"C:\\Program Files (x86)\\Microsoft Office\\Office16\\OUTLOOK.EXE\"");r.delay(500);
		r.keyPress(KeyEvent.VK_ENTER); r.keyRelease(KeyEvent.VK_ENTER);r.delay(500);
		pasteString(password);r.delay(500);
		r.keyPress(KeyEvent.VK_ENTER); r.keyRelease(KeyEvent.VK_ENTER); r.delay(5000);
		r.keyPress(KeyEvent.VK_ALT);
		r.keyPress(KeyEvent.VK_F);
		r.keyRelease(KeyEvent.VK_F);
		r.keyPress(KeyEvent.VK_D);
		r.keyRelease(KeyEvent.VK_D);
		r.keyPress(KeyEvent.VK_E);
		r.keyRelease(KeyEvent.VK_E);
		r.keyRelease(KeyEvent.VK_ALT); r.delay(500);
		
		r.keyPress(KeyEvent.VK_Y);
		r.keyRelease(KeyEvent.VK_Y); r.delay(500);
		
		WebElement invitationMail = (new WebDriverWait(driver, 12)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@Name,'Sign In')]")));
		invitationMail.click();
		//WebElement proposeNewTime = driver.findElement(By.xpath("//*[contains(@Name,'Propose New Time')]"));
		//proposeNewTime.click();
		
	}
	
	public static void openAnotherAccount(String user, String password, String name) throws AWTException, InterruptedException {
		Robot r = new Robot();
		r.delay(1000);
		r.keyPress(KeyEvent.VK_WINDOWS);
		r.keyPress(KeyEvent.VK_R);
		r.keyRelease(KeyEvent.VK_R);
		r.keyRelease(KeyEvent.VK_WINDOWS);r.delay(1000);
		pasteString("tsdiscon");
		r.keyPress(KeyEvent.VK_ENTER);	r.keyRelease(KeyEvent.VK_ENTER); r.delay(5000);
		r.keyPress(KeyEvent.VK_L);
		r.keyRelease(KeyEvent.VK_L); r.delay(5000);
		
		
		r.keyPress(KeyEvent.VK_TAB);	r.keyRelease(KeyEvent.VK_TAB); r.delay(250);
		r.keyPress(KeyEvent.VK_TAB);	r.keyRelease(KeyEvent.VK_TAB); r.delay(250);
		r.keyPress(KeyEvent.VK_TAB);	r.keyRelease(KeyEvent.VK_TAB); r.delay(250);
		r.keyPress(KeyEvent.VK_TAB);	r.keyRelease(KeyEvent.VK_TAB); r.delay(250);
		r.keyPress(KeyEvent.VK_ENTER);	r.keyRelease(KeyEvent.VK_ENTER); r.delay(250);
		pasteString(user); r.delay(250);
		pasteString(password); r.delay(250);
		r.keyPress(KeyEvent.VK_ENTER);	r.keyRelease(KeyEvent.VK_ENTER); r.delay(250);
		
		
	
		
	}
	// create screen shot
	 private static File getHomeDir() {
	        FileSystemView fsv = FileSystemView.getFileSystemView();
	        return fsv.getHomeDirectory();
	    }
	 
	 private static BufferedImage grabScreen() { 
	        try {
	            return new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	        } catch (SecurityException e) {
	        } catch (AWTException e) {
	        }
	        return null;
	    }
	 
	 /**
	 * @param iteration
	 * @param XML
	 */
	public static void runTestSuite(int iteration, String XML) {
	      List<String> suites = new ArrayList<String>(); 
	      suites.add(XML); //path of .xml file to be run-provide complete path 
	      TestNG tng = new TestNG(); 
	      tng.setTestSuites(suites); 
	      	for(int i=0;i<iteration;i++) { 
	      		tng.run(); i++; }//run test suite
		}
	 
	 // MAIN
	public static void main(String[] args) throws IOException, InterruptedException, AWTException {
		String id1 = "_id1";
		String id2 = "_id2";
		String name1 = "_name1";
		String name2 = "_name2";
		
		List<String> iDAndNameList = new ArrayList<String>();
		iDAndNameList.add(id1);
		iDAndNameList.add(name1);
		iDAndNameList.add(id2);
		iDAndNameList.add(name2);
		
		Map<String, String> myMap = new HashMap<String, String>();
		myMap.put(name1, id1);
		myMap.put(name2, id2);
		myMap.size();
		
		// for (Map.Entry<String, String> entry : myMap.entrySet()) {
		 //       System.out.println("Name =  " + entry.getKey() + " ID = " + entry.getValue());           
		  //  }
		
		 for(Iterator<Map.Entry<String, String>> it = myMap.entrySet().iterator(); it.hasNext(); ) {
				Map.Entry<String, String> entry = it.next();
				if(entry.getKey().equals("_name1")) {
					it.remove();
				}
				
			}
		 
		 for (Map.Entry<String, String> entry1 : myMap.entrySet()) {
		       System.out.println("Name =  " + entry1.getKey() + " ID = " + entry1.getValue());           
		    }
		 
		/*
		Set< Map.Entry< String, String> > st = myMap.entrySet();    
		  
	       for (Map.Entry< String, String> me:st) 
	       { 
	           System.out.print(me.getKey()+":"); 
	           System.out.println(me.getValue()); 
	       }
	       */ 
		/*
		ArrayList<List<String>> arrayList = new ArrayList<List<String>>();
		arrayList.add(list);
		System.out.println(arrayList.get(0).toString());
		*/
		//runTestSuite(1, "desktopTesting.xml");
		
	   } 
	   
	 
	    
	
		/*
		 * String driverPath = "c:\\DRIVERS\\chromedriver.exe"; String mode =
		 * "runas /user:yoelap@amdocs.com 'C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe'"
		 * ;
		 * 
		 * String user = "yoelap@amdocs.com"; // Process
		 * process=Runtime.getRuntime().exec("runas /env /user:"
		 * +user+" \"C:\\Program Files (x86)\\Microsoft Office\\Office16\\OUTLOOK.EXE\""
		 * ); Runtime.getRuntime().exec("runas /env /user:"
		 * +user+" \"C:\\Program Files (x86)\\Microsoft Office\\Office16\\OUTLOOK.EXE\""
		 * );
		 */
		/*
		 * String cmdCommand = "runas /user:"
		 * +user+" \"C:\\Program Files (x86)\\Microsoft Office\\Office16\\OUTLOOK.EXE\""
		 * ; System.out.println(cmdCommand);
		 */
    	/*
    	
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_WINDOWS);
		r.keyPress(KeyEvent.VK_R);
		r.keyRelease(KeyEvent.VK_R);
		r.keyRelease(KeyEvent.VK_WINDOWS);r.delay(250);
		Thread.sleep(3000);
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_A);
		r.keyRelease(KeyEvent.VK_A);
		r.keyRelease(KeyEvent.VK_CONTROL); r.delay(250);
		driver = new ChromeDriver();
		//pasteString(mode);
		 pasteString("runas /user:yoelap@amdocs.com \"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe\"");
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);r.delay(500);
		pasteString("Random@224466");
		r.keyPress(KeyEvent.VK_ENTER); 
			
		r.keyRelease(KeyEvent.VK_ENTER);r.delay(7000);
		pasteString("https://www.office.com/");
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);r.delay(500);
		
		System.setProperty("webdriver.chrome.driver",driverPath);
		
    	driver.manage().window().maximize();
    	
    	*/
		/*
		ChromeOptions options = new ChromeOptions();
    	options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
    	
    	options.addArguments("--disable-notifications");
    	
    	options.addArguments("--lang=en"); 
    	System.setProperty("webdriver.chrome.driver",driverPath);
    	driver = new ChromeDriver(options);
    	driver.manage().window().maximize();
    	driver.get("https://login.microsoftonline.com/");
    	driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
    	
    	
		*/
		/*
		User32.INSTANCE.GetForegroundWindow(); 
		WebElement oneDriveTrayIcon = driver.findElement(By.xpath("//*[contains(@Name,'"+appName+"')]"));
		oneDriveTrayIcon.click();
		*/
		
		/*	 HWND hwnd = User32.INSTANCE.FindWindow(null , "OneDrive - AMDOCS");
		  	 User32.INSTANCE.ShowWindow(hwnd,1);
		
		// pasteString("runas /user:yoelap@amdocs.com \"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe\"");
		
		// runas /user:yoelap@amdocs.com "C:\Program Files (x86)\Google\Chrome\Application\chrome.exe"
		

		//createTime(1, 30);
		
/*
		OutlookDesktop od = new OutlookDesktop(driver);
		od._ChangeEnvironment(false);
	*/	
		/*
		Pattern pattern = Pattern.compile("\\d+(\\#)");
	    String word = "MeetingID: 12345678# sometext ';bfg 357hfg1346h"; // мой пример строки
	    Matcher matcher = pattern.matcher(word);
	    int start = 0;
	    while (matcher.find(start)) {
	       String value = word.substring(matcher.start(), matcher.end());
	       //int result = Integer.parseInt(value);
	       System.out.println(value);
	       start = matcher.end();
	       
	    }
		
		*/
		
		
		
		
		//*******************************************************
		/*
		
			SimpleDateFormat SDFormat = new SimpleDateFormat("h:mm a"); 
		     Calendar cal = Calendar.getInstance(); 

		     String curr_date = SDFormat.format(cal.getTime()); 
		     System.out.println("Formatted Date: " + curr_date);
		     cal.add(Calendar.HOUR, 3);
		     
		     String newDate = SDFormat.format(cal.getTime()); // substract 1 month
		     System.out.println("Formatted Time: " + newDate);
		     //return newDate;
		*/
		/*******************************DIFFERENT USER*******************************/
		 

		
		
		
		//**********************************************
		
		/*Generator sluchainih simvolov*/
		/*
		int lineLength = 36;
		char[] alphabetA = new String("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789").toCharArray();
		for(int n=0; n < lineLength; n++) {
			int randomSymbol = randomNumInRange(0,alphabetA.length-1);
			char cr = alphabetA[randomSymbol];
			String result = String.valueOf(cr);
			System.out.print(result);
			
		}
		*/
		
		/*
		 * for (int i =0; i < alphabetA.length; i++) System.out.println(alphabetA[i]);
		 */
		
		/*
		 String data = "(2)";
		    int[] digits = findDigits(data);
		    int length = digits.length;
		    for (int i = 0; i < length; ++i) {
		        System.out.print((int)digits[i]);
		    }
		    }
		 
		    private static int[] findDigits(String str) {
		    int length = str.length(), count = 0;
		    char[] data = str.toCharArray();
		    int[] result = new int[length];
		    for (int i = 0; i < data.length; ++i) {
		        if (Character.isDigit(data[i])) {
		        result[count++] = Integer.parseInt(Character.toString(data[i]));
		        }
		    }
		 
		    return Arrays.copyOfRange(result, 0, count);
	
	*/
		


	private static OutlookDesktop OutlookDesktop(WiniumDriver driver2) {
		// TODO Auto-generated method stub
		return null;
	}
	public static int randomNumInRange(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
		
	}
	



String TEST_TITLE;

	public List<String> createBeforeMeetingInTheFuture(String mail, int hour, int min) throws AWTException, InterruptedException {
		/*novoe okno - mainWindow*/
		Thread.sleep(4000);
		Robot robot = new Robot();
		String newTime = createTime(hour, min);
		String endTime = createTime(hour, min+30);
		pasteString(mail);
		robot.keyPress(KeyEvent.VK_RIGHT);robot.delay(250); 
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);						
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		pasteString(TEST_TITLE);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		pasteString(newTime);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);robot.delay(250);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);
		//robot.keyRelease(KeyEvent.VK_TAB);robot.delay(500);
		pasteString(endTime);robot.delay(1000);
	
		
		WebElement newbodyDocument = (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@LocalizedControlType='document' and contains(@Name,'"+TEST_TITLE+"')]")));
		String txtBodyNew = newbodyDocument.getText();
		//String MeetingID = getMeetingID(txtBodyNew);
		List<String> meetingNameAndID = new ArrayList<String>();
		meetingNameAndID.add(TEST_TITLE);
	//	meetingNameAndID.add(MeetingID);
		
								robot.delay(1000);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_CONTROL); robot.delay(1000);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_ALT);
		
		//clickSend();
		
		//btnSendAnyway(sendOrNot);
		return meetingNameAndID;







	}


















}
