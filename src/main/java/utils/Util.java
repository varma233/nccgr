package utils;

import java.util.Random;

public class Util {
	public static String getOTP(){
		Random generator = new Random();
		generator.setSeed(System.currentTimeMillis());			  
		return (generator.nextInt(899999) + 100000)+"";		
	}

}
