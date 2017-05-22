<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" type="text/css" href="style.css" />
<link rel="stylesheet" type="text/css" href="fonts/css/font-awesome.css">

<meta charset="ISO-8859-1">
<title>Insert title here</title>

<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script>
	$(function() {

		$("#submit").click(function() {

			var username = $("[name='username']").val();
			var password = $("[name='password']").val();

			$.ajax({
				url : "UserRegistration",
				data : {
					username : username,
					password : password
				},
				method : "POST",
				success : function(data) {

					if (data.res == "0") {
						alert(data.message);
					} else if (data.res == "1") {
						alert(data.message);
						window.location.href = "DisplayLoginFolder";
					}

				}
			})

		});

	});
</script>

</head>
<body>

	<div class="new_folder_header">New User</div>
	<div class="add_folder_box">
		<form action="UserRegistration" method="post">

			<div class="input_text_box">
				<Label>Username: <input type="text" name="username" /></Label>
			</div>
			<div class="input_text_box">
				<Label>Password: <input type="password" name="password" />
				</Label>
			</div>
			<div class="submission" id="submit">
				Register
				<!--  	<input type="submit" value="Register" /> -->
			</div>

		</form>
	</div>
</body>
</html>