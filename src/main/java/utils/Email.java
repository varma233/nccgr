package utils;

import static io.restassured.RestAssured.given;

public class Email {
	public static void send() throws Exception {

		given().
			baseUri("https://api.mailgun.net/v3/"+System.getenv("MAILGUN_DOMAIN")).
			auth().basic("api", System.getenv("MAILGUN_API_KEY")).
			queryParam("from", "naveen@herokuapp.com").
			queryParam("to", "naveenkakarlapudi+spam@gmail.com").
			queryParam("subject", "Test  Mail").
			queryParam("text", "Test Message")
//			.body(request)
//			.contentType(ContentType.JSON)
//			.header("Content-Type", "application/json")
		
			.log().all()
			.post("/messages").
		then()
			.statusCode(200);
		
	}
}
