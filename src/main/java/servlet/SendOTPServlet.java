package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sms.SMS;
import sms.SendSMS;

public class SendOTPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SendOTPServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("Inside SendOTPServlet...");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

//    	String phone = "+918939517189";
//    	String message = "Hi..from TILL...";
//
//    	
//        try {
//        	
//            String result= SMS.send("{\"phone\":[\""+phone+"\"], \"text\":\""+message+"\"}" );
//            out.print("<h2> <font color=green> "+result+"</font><h2>");
//        } catch(Exception e) {
//            System.out.println(e.toString());
//            out.print("<h2> <font color=red> "+e.toString()+"</font><h2>");
//        }

		try {
			SendSMS.send("+918939517189", "112233 : Use this OTP for resetting your password ");
			out.print("<h2> <font color=green> OTP sent succesfully</font><h2>");
		} catch (Exception e) {
			System.out.println(e.toString());
			out.print("<h2> <font color=red> " + e.toString() + "</font><h2>");
		}

		out.close();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
