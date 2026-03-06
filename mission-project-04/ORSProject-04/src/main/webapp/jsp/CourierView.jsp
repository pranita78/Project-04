<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.CourierCtl"%> 
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Courier</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<form action="<%=ORSView.COURIER_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.CourierBean"
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
				Courier
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
					<th align="left">Tracking Number<span style="color: red">*</span></th>
					<td><input type="text" name="trackingNumber"
						placeholder="Enter Tracking Number"
						value="<%=DataUtility.getStringData(bean.getTrackingNumber())%>"></td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("trackingNumber", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Sender Name<span style="color: red">*</span></th>
					<td><input type="text" name="senderName"
						placeholder="Enter Sender Name"
						value="<%=DataUtility.getStringData(bean.getSenderName())%>"></td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("senderName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Receiver Name<span style="color: red">*</span></th>
					<td><input type="text" name="receiverName"
						placeholder="Enter Receiver Name"
						value="<%=DataUtility.getStringData(bean.getReceiverName())%>"></td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("receiverName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Dispatch Date<span style="color: red">*</span></th>
					<td><input type="text" name="dispatchDate" id="udatee"
						placeholder="Select Dispatch Date"
						value="<%=DataUtility.getDateString(bean.getDispatchDate())%>"></td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("dispatchDate", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Delivery Date<span style="color: red">*</span></th>
					<td><input type="text" name="deliveryDate" id="udate"
						placeholder="Select Delivery Date"
						value="<%=DataUtility.getDateString(bean.getDeliveryDate())%>"></td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("deliveryDate", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Delivery Status<span style="color: red">*</span></th>
					<td>
						<select name="deliveryStatus">
							<option value="">--Select--</option>
							<option value="Pending"
								<%= "Pending".equals(bean.getDeliveryStatus()) ? "selected" : "" %>>
								Pending</option>
							<option value="Shipped"
								<%= "Shipped".equals(bean.getDeliveryStatus()) ? "selected" : "" %>>
								Shipped</option>
							<option value="Delivered"
								<%= "Delivered".equals(bean.getDeliveryStatus()) ? "selected" : "" %>>
								Delivered</option>
						</select>
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("deliveryStatus", request)%>
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
							value="<%=CourierCtl.OP_UPDATE%>">
						<input type="submit" name="operation"
							value="<%=CourierCtl.OP_CANCEL%>">
					<%
						} else {
					%>
					<td align="left" colspan="2">
						<input type="submit" name="operation"
							value="<%=CourierCtl.OP_SAVE%>">
						<input type="submit" name="operation"
							value="<%=CourierCtl.OP_RESET%>">
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