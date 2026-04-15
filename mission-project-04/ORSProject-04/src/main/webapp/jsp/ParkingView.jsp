<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ParkingCtl"%> 
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Parking</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

<form action="<%=ORSView.PARKING_CTL%>" method="post">

	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ParkingBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy">
			<%
			if (bean != null && bean.getParkingId() != null && bean.getParkingId() > 0){
			%>Update<%
				} else {
			%>Add<%
				}
			%>
			Parking
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

		<input type="hidden" name="id" value="<%=bean.getParkingId()%>">
		<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
		<input type="hidden" name="createdDatetime"
			value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
		<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

		<table>

			<tr>
				<th align="left">Location<span style="color: red">*</span></th>
				<td>
					<input type="text" name="location"
						placeholder="Enter Location"
						value="<%=DataUtility.getStringData(bean.getLocation())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("location", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th align="left">Capacity<span style="color: red">*</span></th>
				<td>
					<input type="text" name="capacity"
						placeholder="Enter Capacity"
						value="<%=DataUtility.getStringData(bean.getCapacity())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("capacity", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th align="left">Fee<span style="color: red">*</span></th>
				<td>
					<input type="text" name="fee"
						placeholder="Enter Fee"
						value="<%=DataUtility.getStringData(bean.getFee())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("fee", request)%>
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
				if (bean != null && bean.getParkingId() != null && bean.getParkingId() > 0) {
				%>

				<td align="left" colspan="2">
					<input type="submit" name="operation" value="<%=ParkingCtl.OP_UPDATE%>">
					<input type="submit" name="operation" value="<%=ParkingCtl.OP_CANCEL%>">
				</td>

				<%
					} else {
				%>

				<td align="left" colspan="2">
					<input type="submit" name="operation" value="<%=ParkingCtl.OP_SAVE%>">
					<input type="submit" name="operation" value="<%=ParkingCtl.OP_RESET%>">
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