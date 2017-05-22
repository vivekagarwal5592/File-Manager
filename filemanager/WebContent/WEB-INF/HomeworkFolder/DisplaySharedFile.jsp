<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<link rel="stylesheet" type="text/css" href="fonts/css/font-awesome.css">
</head>
<body>

	<div class="section1">
		<div class="section1_header">Operations</div>

		<div class="back">

			

				<a
					href="DisplayFolder?current_folder=${current_folder_details.getParent_id()}">
					<i class="fa fa-arrow-left" aria-hidden="true"></i>Back
				</a>

		

		</div>

		
		<div class="new_file">
			<a href="LogoutFolder">Logout</a>
		</div>
		 	

	</div>

	<div class="section2">



		<div class="section2_header">
			<c:out value="Shared Files" />
		</div>



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

							<a href="DownloadFile?current_folder=${i.getId()}"> <c:out
									value="${i.getName()}" /> <i class="fa fa-download"
								aria-hidden="true"></i>
							</a>




						</div>
					</td>
					<td>
						<div class="date">
							<format:formatDate pattern="MM/dd/yyyy hh:mm a"
								value="${i.getDate()}" />

						</div>
					<td><c:if test="${i.getSize() gt 1024}">

							<format:formatNumber type="number" maxFractionDigits="0"
								value="${i.getSize()/1024}" />
							<span class="size"> KB </span>


						</c:if> <c:if test="${i.getSize() lt 1024}">

							<c:out value="${i.getSize()}" />
							<span class="size"> B </span>

						</c:if></td>
					<td></td>


				</tr>
			</c:forEach>
		</TABLE>
	</div>
</body>
</html>