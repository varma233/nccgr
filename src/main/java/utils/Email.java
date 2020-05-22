package utils;

import static io.restassured.RestAssured.given;

public class Email {
	// EXAMPLES : https://documentation.mailgun.com/en/latest/api-sending.html#examples
	
	public static void send(String to, String text) throws Exception {
		
		given()
			.baseUri("https://api.mailgun.net/v3/"+System.getenv("MAILGUN_DOMAIN"))
			.auth().basic("api", System.getenv("MAILGUN_API_KEY"))
			.queryParam("from", "Login Details <"+System.getenv("MAILGUN_SMTP_LOGIN")+">")
			.queryParam("to", to) 
			.queryParam("subject", "Test  Mail")
			.queryParam("text", text)
			
			.log().all()
			.post("/messages").
		then()
			.statusCode(200);
		
	}
}
