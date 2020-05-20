 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
* {
	box-sizing: border-box;
}

input, select, textarea {
	width: 100%;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 4px;
	resize: vertical;
}

label, .signup {
	padding: 12px 12px 12px 0;
	display: inline-block;
}



input[type=submit] {
	background-color: #4CAF50;
	color: white;
	padding: 12px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	float: right;
}

input[type=submit]:hover {
	background-color: #45a049;
}

.container {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}

.col-25 {
	float: left;
	width: 25%;
	margin-top: 6px;
}

.col-75 {
	float: left;
	width: 75%;
	margin-top: 6px;
}

/* Clear floats after the columns */
.row:after {
	content: "";
	display: table;
	clear: both;
}

.responsiveimg {
	width: 100%;
	max-width: 200px;
	height: auto;
	display: block;
	margin-left: auto;
	margin-right: auto;
}

/* Responsive layout - when the screen is less than 600px wide, make the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
	.col-25, .col-75, input[type=submit] {
		width: 100%;
		margin-top: 0;
	}
}

body {
	margin: 0;
	font-family: Arial, Helvetica, sans-serif;
}
</style>

<%
    String mobile = (String) session.getAttribute("mobile");
    if (mobile != null && mobile.length() != 0) {
    	response.sendRedirect("home.jsp");
    }
 %>
</head>
<body>


	<div class="container">
		<form name="input" action="LoginServlet" method="post">

			<br/>
			
			<%	if ((String) request.getAttribute("registrationstatus") == "success") 
					out.print("<center><label><font color=\"green\">Registration Successful !</font></label></center>");
			%>

			<br /> <img src="images/NCC.jpg" alt="NCC" class="responsiveimg"
				width="200" height="100"> <br />

			<div class="row">
				<div class="col-25">
					<label for="mobile">Mobile Number</label>
				</div>
				<div class="col-75">
					<input type="tel" id="mobile" name="mobile"
						placeholder="Mobile number.." required>
				</div>
			</div>

			<div class="row">
				<div class="col-25">
					<label for="password">Password</label>
				</div>
				<div class="col-75">
					<input type="password" id="password" name="password"
						placeholder="Password.." required>
				</div>
			</div>

			<br />

			<div class="row">
				<input type="submit" value="Sign In">
			</div>

		</form>

		<br /> <br />
		<div class="row">
			<div class="col-25">
				<label for="signup">New User?</label>
			</div>
			<div class="col-75 signup">
				<a style="text-decoration:none" href="signup.jsp"><font color="#45a049"><b>Sign Up</b></font> </a> <label> here</label>
			</div>
		</div>
	</div>

</body>
</html>
