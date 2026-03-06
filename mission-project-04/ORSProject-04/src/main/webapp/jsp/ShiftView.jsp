<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ShiftCtl"%> 
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Shift</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

<form action="<%=ORSView.SHIFT_CTL%>" method="post">

	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ShiftBean"
		scope="request"></jsp:useBean>

	<div align="center">
		<h1 align="center" style="margin-bottom: -15; color: navy">
			<% if (bean != null && bean.getId() > 0) { %>
				Update
			<% } else { %>
				Add
			<% } %>
			Shift
		</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<H3 align="center">
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
			</H3>
			<H3 align="center">
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
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
				<th align="left">Shift Code<span style="color: red">*</span></th>
				<td>
					<input type="text" name="shiftCode"
						placeholder="Enter Shift Code"
						value="<%=DataUtility.getStringData(bean.getShiftCode())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("shiftCode", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th align="left">Shift Name<span style="color: red">*</span></th>
				<td>
					<input type="text" name="shiftName"
						placeholder="Enter Shift Name"
						value="<%=DataUtility.getStringData(bean.getShiftName())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("shiftName", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th align="left">Start Date<span style="color: red">*</span></th>
				<td>
					<input type="text" id="udate" name="startTime"
					placeholder="Enter start Date"
						value="<%=DataUtility.getDateString(bean.getStartTime())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("startTime", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th align="left">End Date<span style="color: red">*</span></th>
				<td>
					<input type="text" id="udate3" name="endTime"
					placeholder="Enter end Date"
						value="<%=DataUtility.getDateString(bean.getEndTime())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("endTime", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th></th>
				<td></td>
			</tr>

			<tr>
				<th></th>
				<% if (bean != null && bean.getId() > 0) { %>
				<td align="left" colspan="2">
					<input type="submit" name="operation" value="<%=ShiftCtl.OP_UPDATE%>">
					<input type="submit" name="operation" value="<%=ShiftCtl.OP_CANCEL%>">
				</td>
				<% } else { %>
				<td align="left" colspan="2">
					<input type="submit" name="operation" value="<%=ShiftCtl.OP_SAVE%>">
					<input type="submit" name="operation" value="<%=ShiftCtl.OP_RESET%>">
				</td>
				<% } %>
			</tr>

		</table>
	</div>

</form>

<%@ include file="Footer.jsp"%>

</body>
</html>