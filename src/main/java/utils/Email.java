package utils;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class Email {
	// EXAMPLES : https://documentation.mailgun.com/en/latest/api-sending.html#examples
	
	public static void send(String to, String password) throws Exception {
		
		JSONObject request = new JSONObject();
		request.put("password",password);
		request.toJSONString();
		
		given()
			.baseUri("https://api.mailgun.net/v3/"+System.getenv("MAILGUN_DOMAIN"))
			.auth().basic("api", System.getenv("MAILGUN_API_KEY"))
			.queryParam("from", "Password Recovery <"+System.getenv("MAILGUN_SMTP_LOGIN")+">")
			.queryParam("to", to) // hard coded
			.queryParam("subject", "Test  Mail") // hard coded
//			.queryParam("text", "Test Message")
			.queryParam("template", "newpassword")
			.queryParam("h:X-Mailgun-Variables", request)
			
			.log().all()
			.post("/messages").
		then()
			.statusCode(200);
		
	}
}
