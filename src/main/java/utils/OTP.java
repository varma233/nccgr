package utils;

import java.util.Random;

public class OTP {
	public static String getOTP(){
		Random generator = new Random();
		generator.setSeed(System.currentTimeMillis());			  
		return (generator.nextInt(899999) + 100000)+"";	
	}

}
