<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css" />
<link rel="stylesheet" type="text/css" href="fonts/css/font-awesome.css">
<meta charset="ISO-8859-1">
<title>Edit Folder</title>
</head>
<body>

	<div class="new_folder_header">Edit Folder</div>
	<div class="add_folder_box">
		<form action="EditFolder" method="post">
			<div class="input_text_box">
				<Label>Edit Folder: <input type="text" name="folder_name"
					value="${folder.getName()}" /></Label>
			</div>
			<input type="hidden" name="current_folder" value="${current_folder}" />
			<div class="input_submit_box">
			<input type="submit" value="Submit" />
			</div>
		</form>
	</div>
	<div class="back_link">
		<i class="fa fa-arrow-left" aria-hidden="true"></i> <a
			href="DisplayFolder?current_folder=${current_folder}">Back</a>
	</div>
</body>
</html>