
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

label {
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

</head>
<body>


	<div class="container">
		<form name="input" action="SignupServlet" method="post">

			<br />
			<%	if ((String) request.getAttribute("registrationstatus") == "failure") 
					out.print("<center><label><font color=\"red\">Failed to register. Please check details & try again...</font></label></center>");
			%>
			<br /> <img src="images/NCC.jpg" alt="NCC" class="responsiveimg"
				width="200" height="100"> <br />

			<div class="row">
				<div class="col-25">
					<label for="firstname">First Name</label>
				</div>
				<div class="col-75">
					<input type="text" id="firstname" name="firstname" pattern="[a-zA-Z][a-zA-Z\s]*.{3,}"
						title="Must contain minimum 3 letters" placeholder="First name.."
						required>
				</div>
			</div>

			<div class="row">
				<div class="col-25">
					<label for="lastname">Last Name</label>
				</div>
				<div class="col-75">
					<input type="text" id="lastname" name="lastname" pattern="[a-zA-Z][a-zA-Z\s]*"
						title="Must contain minimum 1 letters" 
						placeholder="Last name.." required>
				</div>
			</div>

			<div class="row">
				<div class="col-25">
					<label for="mobile">Mobile Number</label>
				</div>
				<div class="col-75">
					<input type="tel" id="mobile" name="mobile" pattern="[0-9]{10}"
						title="Must contain only 10 numbers" placeholder="Mobile number.."
						required>
				</div>
			</div>

			<div class="row">
				<div class="col-25">
					<label for="email">Email ID</label>
				</div>
				<div class="col-75">
					<input type="email" id="email" name="email"
						pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
						title="Must be in characters@characters.domain format. Ex: abc@gmail.com, xyz@yahoo.co.in, etc..."
						placeholder="E-mail.." required>
				</div>
			</div>

			<div class="row">
				<div class="col-25">
					<label for="password">Password</label>
				</div>
				<div class="col-75">
					<input type="password" id="password" name="password"
						pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}"
						title="Must contain at least one  number and one uppercase and lowercase letter, and at least 6 or more characters"
						placeholder="Password.." required>
				</div>
			</div>
			
			<div class="row">
				<div class="col-25">
					<label for="authkey">Authorization Key</label>
				</div>
				<div class="col-75">
					<input type="tel" id="authkey" name="authkey"
						pattern="[0-9]{6}"
						title="Must be 6 digits"
						placeholder="Contact Admin to get this code" required>
				</div>
			</div>

			<br />

			<div class="row">
				<input type="submit" value="Sign Up">
			</div>
		</form>
	</div>

</body>
</html>
