<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.EventCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Event</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

<form action="<%=ORSView.EVENT_CTL%>" method="post">

	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean"
		class="in.co.rays.proj4.bean.EventBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy">

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
			Event
		</h1>

		<div style="height: 15px; margin-bottom: 12px">

			<h3 align="center">
				<font color="red">
					<%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

			<h3 align="center">
				<font color="green">
					<%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>

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
				<th align="left">
					Event Code <span style="color: red">*</span>
				</th>
				<td>
					<input type="text" name="eventCode"
						placeholder="Enter Event Code"
						value="<%=DataUtility.getStringData(bean.getEventCode())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("eventCode", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th align="left">
					Event Name <span style="color: red">*</span>
				</th>
				<td>
					<input type="text" name="eventName"
						placeholder="Enter Event Name"
						value="<%=DataUtility.getStringData(bean.getEventName())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("eventName", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th align="left">
					Organizer <span style="color: red">*</span>
				</th>
				<td>
					<input type="text" name="organizer"
						placeholder="Enter Organizer Name"
						value="<%=DataUtility.getStringData(bean.getOrganizer())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("organizer", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th align="left">
					Event Date <span style="color: red">*</span>
				</th>
				<td>
					<input type="text" name="eventDate" id="udatee"
						placeholder="Select Event Date"
						value="<%=DataUtility.getDateString(bean.getEventDate())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("eventDate", request)%>
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
					<input type="submit" name="operation"
						value="<%=EventCtl.OP_UPDATE%>">

					<input type="submit" name="operation"
						value="<%=EventCtl.OP_CANCEL%>">
				</td>

				<%
					} else {
				%>

				<td align="left" colspan="2">
					<input type="submit" name="operation"
						value="<%=EventCtl.OP_SAVE%>">

					<input type="submit" name="operation"
						value="<%=EventCtl.OP_RESET%>">
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