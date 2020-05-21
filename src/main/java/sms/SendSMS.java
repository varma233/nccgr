package sms;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import io.restassured.http.ContentType;


public class SendSMS {

	public static void send(String mobilenumber, String message) throws Exception {

		JSONObject request = new JSONObject();
		List<String> phonenumbers = new ArrayList<String>();
		phonenumbers.add(mobilenumber);
		request.put("phone", phonenumbers);
		request.put("text", message);
		request.toJSONString();

		given()			
			.body(request)
			.contentType(ContentType.JSON)
			.header("Content-Type", "application/json")
			.log().all()
			.post(System.getenv("TILL_URL")).
		then()
			.statusCode(200);
		
		
//		MatcherAssert.assertThat(response.statusCode(), equalTo(200));

	}

}
