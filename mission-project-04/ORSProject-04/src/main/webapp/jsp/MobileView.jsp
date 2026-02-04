<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.MobileCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Mobile</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<form action="<%=ORSView.MOBILE_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.MobileBean"
			scope="request"></jsp:useBean>

		<div align="center">

			<h1 style="margin-bottom: -15; color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>Update<%
					} else {
				%>Add<%
					}
				%>
				Mobile
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				</h3>
				<h3>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
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
					<th align="left">Mobile Brand<span style="color: red">*</span></th>
					<td>
						<input type="text" name="BrandName" maxlength="10"
							placeholder="Enter Brand Name"
							value="<%=DataUtility.getStringData(bean.getBrand())%>">
					</td>
					<td style="position: fixed">
						<font color="red">
							<%=ServletUtility.getErrorMessage("BrandName", request)%>
						</font>
					</td>
				</tr>
				<tr>
				<th align="left">Model<span style="color: red">*</span></th>
					<td>
						<input type="text" name="model" maxlength="10"
							placeholder="Enter model Name"
							value="<%=DataUtility.getStringData(bean.getModel())%>">
					</td>
					<td style="position: fixed">
						<font color="red">
							<%=ServletUtility.getErrorMessage("model", request)%>
						</font>
					</td>
				</tr>
				<tr>
				<th align="left">Price<span style="color: red">*</span></th>
					<td>
						<input type="text" name="price" maxlength="10"
							placeholder="Enter price"
							value="<%=DataUtility.getStringData(bean.getPrice())%>">
					</td>
					<td style="position: fixed">
						<font color="red">
							<%=ServletUtility.getErrorMessage("price", request)%>
						</font>
					</td>
				</tr>
				<tr>
				<th align="left">Storage<span style="color: red">*</span></th>
					<td>
						<input type="text" name="storage" maxlength="10"
							placeholder="Enter Brand Name"
							value="<%=DataUtility.getStringData(bean.getStorage())%>">
					</td>
					<td style="position: fixed">
						<font color="red">
							<%=ServletUtility.getErrorMessage("storage", request)%>
						</font>
					</td>
				</tr>
				
				<tr>
				<th align="left">loan Date<span style="color: red">*</span></th>
					<td>
						<input type="text" id="udate" name="loanDate" maxlength="10"
							placeholder="Enter Brand Name"
							value="<%=DataUtility.getStringData(bean.getLoanDate())%>">
					</td>
					<td style="position: fixed">
						<font color="red">
							<%=ServletUtility.getErrorMessage("loanDate", request)%>
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
					<td colspan="2">
						<input type="submit" name="operation" value="<%=MobileCtl.OP_UPDATE%>">
						<input type="submit" name="operation" value="<%=MobileCtl.OP_CANCEL%>">
					</td>
					<%
						} else {
					%>
					<td colspan="2">
						<input type="submit" name="operation" value="<%=MobileCtl.OP_SAVE%>">
						<input type="submit" name="operation" value="<%=MobileCtl.OP_RESET%>">
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
