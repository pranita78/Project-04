<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.InventoryCtl"%> 
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Inventory</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<form action="<%=ORSView.INVENTORY_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.InventoryBean"
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
				Inventory
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
					<th align="left">Item Code<span style="color: red">*</span></th>
					<td>
						<input type="text" name="itemCode"
							placeholder="Enter Item Code"
							value="<%=DataUtility.getStringData(bean.getItemCode())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("itemCode", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Item Name<span style="color: red">*</span></th>
					<td>
						<input type="text" name="itemName"
							placeholder="Enter Item Name"
							value="<%=DataUtility.getStringData(bean.getItemName())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("itemName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Quantity<span style="color: red">*</span></th>
					<td>
						<input type="number" name="quantity"
							placeholder="Enter Quantity"
							value="<%=DataUtility.getStringData(bean.getQuantity())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("quantity", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Price<span style="color: red">*</span></th>
					<td>
						<input type="text" name="price"
							placeholder="Enter Price"
							value="<%=DataUtility.getStringData(bean.getPrice())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("price", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Item Status<span style="color: red">*</span></th>
					<td>
						<input type="text" name="itemStatus"
							placeholder="Enter Status (Available/Out of Stock)"
							value="<%=DataUtility.getStringData(bean.getItemStatus())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("itemStatus", request)%>
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
							value="<%=InventoryCtl.OP_UPDATE%>">
						<input type="submit" name="operation"
							value="<%=InventoryCtl.OP_CANCEL%>">
					<%
						} else {
					%>
					<td align="left" colspan="2">
						<input type="submit" name="operation"
							value="<%=InventoryCtl.OP_SAVE%>">
						<input type="submit" name="operation"
							value="<%=InventoryCtl.OP_RESET%>">
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