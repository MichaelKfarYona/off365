package tests;

import java.awt.AWTException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.winium.WiniumDriver;

import desktop.pages.OutlookDesktop;

public class _exe {
	static WiniumDriver driver;
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
	
	public static void main(String[] args) throws IOException, InterruptedException, AWTException {
		createDate(0);
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
		int lineLength = 36;
		char[] alphabetA = new String("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789").toCharArray();
		for(int n=0; n < lineLength; n++) {
			int randomSymbol = randomNumInRange(0,alphabetA.length-1);
			char cr = alphabetA[randomSymbol];
			String result = String.valueOf(cr);
			System.out.print(result);
			
		}
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
		

}
	private static OutlookDesktop OutlookDesktop(WiniumDriver driver2) {
		// TODO Auto-generated method stub
		return null;
	}
	public static int randomNumInRange(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}}
