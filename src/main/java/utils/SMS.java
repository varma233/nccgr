package utils;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONValue;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SMS {

	public static boolean send(String mobilenumber, String message)  {

		List<String> phonenumbers = new ArrayList<String>();
		phonenumbers.add(mobilenumber);

		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("phone", phonenumbers);
		obj.put("text", message);
		String request = JSONValue.toJSONString(obj);
		
		try {
		Response response = 
		given().body(request).contentType(ContentType.JSON).header("Content-Type", "application/json").log().all()
				.post(System.getenv("TILL_URL"));
		if(response.statusCode() == 200)
			return true;
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return false;
	}

}
