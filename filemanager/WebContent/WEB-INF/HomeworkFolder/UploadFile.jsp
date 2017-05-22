<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
<link rel="stylesheet" type="text/css" href="fonts/css/font-awesome.css">
<meta charset="ISO-8859-1">
<title></title>
</head>
<body>


	<div class="new_folder_header">Upload File</div>
	<div class="add_folder_box">
		<form action="UploadFile" method="post" enctype="multipart/form-data">
			<div class="input_file_box">
				<Label> File: <input type="file" name="file" /> <br /></Label>
			</div>
			<input type="hidden" name="current_folder" value="${current_folder}" />
			<div class="input_submit_box">
				<input type="submit" name="upload" value="Upload" />
			</div>
		</form>
	</div>
	<div class="back_link">
		<i class="fa fa-arrow-left" aria-hidden="true"></i> <a
			href="DisplayFolder?current_folder=${current_folder}">Back</a>
	</div>
</BODY>
</html>


