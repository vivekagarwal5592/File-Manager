<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css" />
<link rel="stylesheet" type="text/css" href="fonts/css/font-awesome.css">
<meta charset="ISO-8859-1">
<title>Add Folder</title>

<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script>

	$(function() {
		
		
	});
	</script>

</head>
<body>


	<div class="new_folder_header">Add New Folder</div>
	<div class="add_folder_box">
		<form action="AddFolder" method="Post">
			<div class="input_text_box">
				<Label>New Folder: <input type="text" name="folder_name" /></Label>
			</div>
			<input type="hidden" name="current_folder" value="${current_folder}" />

			<div class="input_submit_box">
				<input type="submit" value="Submit">
			</div>
		</form>
	</div>
	<div class="back_link">
		<i class="fa fa-arrow-left" aria-hidden="true"></i> <a
			href="DisplayFolder?current_folder=${current_folder}">Back</a>
	</div>

</body>
</html>