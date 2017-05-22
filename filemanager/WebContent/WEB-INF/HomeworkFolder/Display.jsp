<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" type="text/css" href="style.css">
<link rel="stylesheet" type="text/css" href="fonts/css/font-awesome.css">
<title>Display</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script>
	$(function() {

		$(".share_folder")
				.click(
						function() {

							var user_name = prompt("Enter the user you want to share the file with");
							var id = $(this).attr("data-entry-id");
							$.ajax({
								url : "ShareFile",

								data : {
									"folder_id" : id,
									"user_name" : user_name
								},
								success : function(data) {
									if (data.res == "0") {
										alert(data.message);
									} else if (data.res == "1") {
										alert(data.message);
										window.location.href = "DisplayLoginFolder";
									}
								}

							});

						});

		$("#add_new_folder")
				.click(
						function() {

							var current_folder = $(this).attr("data-entry-id");
							var folder_name = prompt("Enter the new folder name");

							if(folder_name !=null){
							$
									.ajax({
										url : "AddFolder",
										data : {
											"current_folder" : current_folder,
											"folder_name" : folder_name
										},
										method : "POST",
										success : function(data) {
											console.log(data);
											var row = $("<tr></tr>");
											row
													.append(
															"<td><div class='folder_name'><a href='DisplayFolder?current_folder="
																	+ data.id
																	+ "'>"
																	+ folder_name
																	+ "</a>"

																	+ "</div></td>")
													.append(
															"<td>" + data.date
																	+ "</td>")
													.append("<td>"
													//size is empty since it is folder
													+ "</td>")
													.append(
															"<td><div class='link_decoration delete_node' data-entry-id='"+data.id+"'>Delete</div>"
																	+ ""
																	+ "<div class='link_decoration'><a href='EditFolder?current_folder="
																	+ data.id
																	+ "'>Edit</a></div>"

																	+ ""
																	+ "</td>");
											$(".display_box").append(row);
											$(".delete_node").click(function() {
	
												delete_node($(this));
															});
										}
									});
							}
							});
	

		$(".delete_node").click(function() {

			delete_node($(this));
		});

		function delete_node(node) {

			$.ajax({
				url : "DeleteFolder",
				data : {
					current_folder : node.attr("data-entry-id")
				},
				success : function() {
					$(node).parent().parent("tr").remove();
				}
			})
		}

	});
</script>

</head>
<body>

	<div class="section1">
		<div class="section1_header">Operations</div>
		<div class="back">

			<c:if
				test="${current_folder_details.getParent_id()!=null || current_folder !=null}">

				<a
					href="DisplayFolder?current_folder=${current_folder_details.getParent_id()}">
					<i class="fa fa-arrow-left" aria-hidden="true"></i>Back
				</a>

			</c:if>

		</div>

		<div class="new_folder" id="add_new_folder"
			data-entry-id="${current_folder}">

			New folder

			<!-- 	<a href="AddFolder?current_folder=${current_folder}">New Folder</a>  -->
		</div>
		<div class="new_file">
			<a href="UploadFile?current_folder=${current_folder}">Upload File</a>
		</div>
		<div class="new_file">
			<a href="LogoutFolder">Logout</a>
		</div>
		<div class="new_file">
			<a href="DisplaySharedFile?current_folder=${current_folder}">Shared
				Files</a>
		</div>

	</div>

	<div class="section2">

		<c:if
			test="${current_folder_details.getParent_id()!=null || current_folder !=null}">

			<div class="section2_header">
				<c:out value="${current_folder_details.getName()}" />
			</div>

		</c:if>

		<TABLE class="display_box" border='2px solid black'>
			<tr>
				<th>Name</th>
				<th>Date Uploaded</th>
				<th>Size</th>
				<th>Operation</th>
			</tr>


			<c:forEach items="${folder}" var="i">
				<tr>
					<td>
						<div class="folder_name">
							<c:if test="${!i.isFolder()}">
								<a href="DownloadFile?current_folder=${i.getId()}"> <c:out
										value="${i.getName()}" /> <i class="fa fa-download"
									aria-hidden="true"></i>
								</a>
							</c:if>

							<c:if test="${i.isFolder()}">

								<a href="DisplayFolder?current_folder=${i.getId()}"> <c:out
										value="${i.getName()}" /> <i class="fa fa-arrow-right"
									aria-hidden="true"></i>
								</a>
							</c:if>

						</div>
					</td>
					<td>
						<div class="date">
							<format:formatDate pattern="MM/dd/yyyy hh:mm a"
								value="${i.getDate()}" />
						</div>
					<td><c:if test="${!i.isFolder()}">
							<c:if test="${i.getSize() gt 1024}">
								<format:formatNumber type="number" maxFractionDigits="0"
									value="${i.getSize()/1024}" />
								<span class="size"> KB </span>


							</c:if>

							<c:if test="${i.getSize() lt 1024}">

								<c:out value="${i.getSize()}" />
								<span class="size"> B </span>

							</c:if>



						</c:if></td>
					<td><div class="link_decoration delete_node"
							data-entry-id="${i.getId()}">
							Delete
							<!--  				<a href="DeleteFolder?current_folder=${i.getId()}">Delete </a> -->
						</div>
						<div class="link_decoration">
							<a href="EditFolder?current_folder=${i.getId()}">Edit </a>
						</div> <c:if test="${!i.isFolder()}">
							<div class="link_decoration share_folder"
								data-entry-id="${i.getId()}">Share</div>
						</c:if></td>


				</tr>
			</c:forEach>
		</TABLE>
	</div>
</body>
</html>