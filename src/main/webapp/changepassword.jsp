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

.topnav {
	overflow: hidden;
	background-color: #333;
}

.topnav a {
	float: left;
	display: block;
	color: #f2f2f2;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
	font-size: 17px;
}

.topnav a:hover {
	background-color: #ddd;
	color: black;
}

.topnav a.active {
	background-color: #4CAF50;
	color: white;
}

.topnav .icon {
	display: none;
}

@media screen and (max-width: 600px) {
	.topnav a:not (:first-child ) {
		display: none;
	}
	.topnav a.icon {
		float: right;
		display: block;
	}
}

@media screen and (max-width: 600px) {
	.topnav.responsive {
		position: relative;
	}
	.topnav.responsive .icon {
		position: absolute;
		right: 0;
		top: 0;
	}
	.topnav.responsive a {
		float: none;
		display: block;
		text-align: left;
	}
}
</style>

</head>
<body>


	<div class="container">
		<form name="input" action="ChangePasswordServlet" method="post">
			<div class="topnav" id="myTopnav">
				<a href="home.jsp" class="active">Home</a> 
				<a href="LogoutServlet">Sign Out</a>
				<a href="javascript:void(0);" class="icon"
					onclick="myFunction()"> <i class="fa fa-bars"></i>
				</a>
			</div>
			<br />
			<script>
			function myFunction() {
			  var x = document.getElementById("myTopnav");
			  if (x.className === "topnav") {
			    x.className += " responsive";
			  } else {
			    x.className = "topnav";
			  }
			}
			</script>
			<br />
			
			<br /> <img src="images/NCC.jpg" alt="NCC" class="responsiveimg"
				width="200" height="100"> <br />


			<div class="row">
				<div class="col-25">
					<label for="oldpassword">Old Password</label>
				</div>
				<div class="col-75">
					<input type="password" id="oldpassword" name="oldpassword"
						placeholder="Old password.." required>
				</div>
			</div>

			<div class="row">
				<div class="col-25">
					<label for="newpassword">New Password</label>
				</div>
				<div class="col-75">
					<input type="password" id="newpassword" name="newpassword"
						placeholder="New password.." required>
				</div>
			</div>

			<div class="row">
				<div class="col-25">
					<label for="confirmpassword">Confirm Password</label>
				</div>
				<div class="col-75">
					<input type="password" id="confirmpassword" name="confirmpassword"
						placeholder="Confirm password.." required>
				</div>
			</div>

			<br />

			<div class="row">
				<input type="submit" value="Change Password"
					onclick="return Validate()">
			</div>

			<script type="text/javascript">
				function Validate() {
					var oldpassword = document.getElementById("oldpassword").value;
					var newpassword = document.getElementById("newpassword").value;
					var confirmPassword = document
							.getElementById("confirmpassword").value;
					
					if (oldpassword == newpassword ) {
						alert("Old password & new password can't be same !");
						return false;
					}

					if (password != confirmPassword) {
						alert("Passwords do not match.");
						return false;
					}
					return true;
				}
			</script>
		</form>
	</div>

</body>
</html>
