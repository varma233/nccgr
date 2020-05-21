package sms;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.json.simple.JSONObject;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class SendSMS {

	public static void send() throws Exception {

		JSONObject request = new JSONObject();
		List<String> phonenumbers = new ArrayList<String>();
		phonenumbers.add("+918939517189");
		request.put("phone", phonenumbers);
		request.put("text", "Hi...\nYour OTP is : 123456");
		request.toJSONString();

		Response response = 
				given()
					.baseUri(System.getenv("TILL_URL"))
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
					.header("Content-Type", "application/json; charset=UTF-8")
					.body(request)
					.log().all()
				.when()
					.post();
		
		System.out.println(response);
		
		MatcherAssert.assertThat(response.statusCode(), equalTo(201));

	}

}
