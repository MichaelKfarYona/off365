package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class _exe {

	public static void main(String[] args) {

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
	public static int randomNumInRange(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}}
