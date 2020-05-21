package rough;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class Rough {

	public static void main(String[] args) {
		JSONObject request = new JSONObject();
		List<String> phonenumbers =  new ArrayList<String>();
		phonenumbers.add("+918939517189");
		request.put("phone", phonenumbers);
		request.put("text", "Hi...\nYour OTP is : 123456");
		
		System.out.println(request.toJSONString());

	}

}
