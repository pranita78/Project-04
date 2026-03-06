<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.EnrollmentCtl"%> 
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Enrollment</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

<form action="<%=ORSView.ENROLLMENT_CTL%>" method="post">

	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.EnrollmentBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy">
			<%
				if (bean != null && bean.getId() > 0) {
			%>Update<%
				} else {
			%>Add<%
				}
			%>
			Enrollment
		</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<H3 align="center">
				<font color="red">
					<%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H3>

			<H3 align="center">
				<font color="green">
					<%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H3>
		</div>

		<input type="hidden" name="id" value="<%=bean.getId()%>">
		<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
		<input type="hidden" name="createdDatetime"
			value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
		<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

		<table>

			<tr>
				<th align="left">Enrollment Code<span style="color:red">*</span></th>
				<td>
					<input type="text" name="enrollmentCode"
						placeholder="Enter Enrollment Code"
						value="<%=DataUtility.getStringData(bean.getEnrollmentCode())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("enrollmentCode", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th align="left">Student Name<span style="color:red">*</span></th>
				<td>
					<input type="text" name="studentName"
						placeholder="Enter Student Name"
						value="<%=DataUtility.getStringData(bean.getStudentName())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("studentName", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th align="left">Course Name<span style="color:red">*</span></th>
				<td>
					<input type="text" name="courseName"
						placeholder="Enter Course Name"
						value="<%=DataUtility.getStringData(bean.getCourseName())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("courseName", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th align="left">Enrollment Date<span style="color:red">*</span></th>
				<td>
					<input type="text" name="enrollmentDate" id="udate"
						placeholder="Select Enrollment Date"
						value="<%=DataUtility.getDateString(bean.getEnrollmentDate())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("enrollmentDate", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th></th>
				<td></td>
			</tr>

			<tr>
				<th></th>

				<%
					if (bean != null && bean.getId() > 0) {
				%>
				<td align="left" colspan="2">
					<input type="submit" name="operation" value="<%=EnrollmentCtl.OP_UPDATE%>">
					<input type="submit" name="operation" value="<%=EnrollmentCtl.OP_CANCEL%>">
				</td>
				<%
					} else {
				%>
				<td align="left" colspan="2">
					<input type="submit" name="operation" value="<%=EnrollmentCtl.OP_SAVE%>">
					<input type="submit" name="operation" value="<%=EnrollmentCtl.OP_RESET%>">
				</td>
				<%
					}
				%>

			</tr>

		</table>
	</div>

</form>

<%@ include file="Footer.jsp"%>

</body>
</html>