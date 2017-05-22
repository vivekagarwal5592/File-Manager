<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css" />
<link rel="stylesheet" type="text/css" href="fonts/css/font-awesome.css">
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="new_folder_header">Login</div>
	<div class="add_folder_box">
		<form action="DisplayLoginFolder" method="post">


			<div class="input_text_box">
				<Label>Username: <input type="text" name="username" />
				</Label>
			</div>

			<div class="input_text_box">
				<Label>Password <input type="password" name="password" />
				</Label>
			</div>

			<div class="input_submit_box">
				<input type="submit" value="Login" />
			</div>
		</form>

	</div>
	<div class="registration-link" style="">
	<a href="UserRegistration">Register</a>
	</div>
</body>
</html>