<%@page import="in.co.rays.proj4.controller.AttendanceCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Attendance</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

<form action="<%=ORSView.ATTENDANCE__CTL%>" method="post">

	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean"
		class="in.co.rays.proj4.bean.AttendanceBean" scope="request"/>

	<div align="center">

		<h1 style="color: navy">
			<%
				if (bean != null && bean.getId() > 0) {
			%>
				Update
			<%
				} else {
			%>
				Add
			<%
				}
			%>
			Attendance
		</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>
			<h3><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h3>
		</div>

		<!-- Hidden Fields -->
		<input type="hidden" name="id" value="<%=bean.getId()%>">
		<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
		<input type="hidden" name="createdDatetime"
			value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
		<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

		<table>

			<!-- Person Name -->
			<tr>
				<th align="left">Person Name<span style="color:red">*</span></th>
				<td>
					<input type="text" name="personName"
						placeholder="Enter Person Name"
						value="<%=DataUtility.getStringData(bean.getPersonName())%>">
				</td>
				<td><font color="red">
					<%=ServletUtility.getErrorMessage("personName", request)%>
				</font></td>
			</tr>

			<!-- Attendance Date -->
			<tr>
				<th align="left">Attendance Date<span style="color:red">*</span></th>
				<td>
					<input type="text" name="attendanceDate" id="adate"
						placeholder="Select Attendance Date"
						value="<%=DataUtility.getDateString(bean.getAttendanceDate())%>">
				</td>
				<td><font color="red">
					<%=ServletUtility.getErrorMessage("attendanceDate", request)%>
				</font></td>
			</tr>

			<!-- Attendance Status -->
			<tr>
				<th align="left">Status<span style="color:red">*</span></th>
				<td>
					<%
						HashMap<String,String> map = new HashMap<>();
						map.put("Present", "Present");
						map.put("Absent", "Absent");
						map.put("Leave", "Leave");

						String statusList =
							HTMLUtility.getList("attendanceStatus",
								bean.getAttendanceStatus(), map);
					%>
					<%=statusList%>
				</td>
				<td><font color="red">
					<%=ServletUtility.getErrorMessage("attendanceStatus", request)%>
				</font></td>
			</tr>

			<!-- Remarks -->
			<tr>
				<th align="left">Remarks</th>
				<td>
					<input type="text" name="remarks"
						placeholder="Enter Remarks"
						value="<%=DataUtility.getStringData(bean.getRemarks())%>">
				</td>
				<td></td>
			</tr>

			<tr><th></th><td></td></tr>

			<!-- Buttons -->
			<tr>
				<th></th>
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				<td colspan="2">
					<input type="submit" name="operation"
						value="<%=AttendanceCtl.OP_UPDATE%>">
					<input type="submit" name="operation"
						value="<%=AttendanceCtl.OP_CANCEL%>">
				</td>
				<%
					} else {
				%>
				<td colspan="2">
					<input type="submit" name="operation"
						value="<%=AttendanceCtl.OP_SAVE%>">
					<input type="submit" name="operation"
						value="<%=AttendanceCtl.OP_RESET%>">
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
