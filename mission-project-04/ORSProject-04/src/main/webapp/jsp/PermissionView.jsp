<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.PermissionCtl"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<html>
<head>
<title>Add Permission</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<form action="<%=ORSView.PERMISSION_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.PermissionBean"
			scope="request"></jsp:useBean>

		<div align="center">
			<h1 align="center" style="margin-bottom: -15; color: navy">
				<%
					if (bean != null && bean.getPermissionId() != null && bean.getPermissionId() > 0) {
				%>Update<%
					} else {
				%>Add<%
					}
				%>
				Permission
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<H3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</H3>

				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>
			</div>

			<input type="hidden" name="permissionId" value="<%=bean.getPermissionId() != null ? bean.getPermissionId() : 0%>">
			<input type="hidden" name="createdBy"    value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"   value="<%=bean.getModifiedBy()%>">
			<input type="hidden" name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>
				<tr>
					<th align="left">Permission Code<span style="color: red">*</span></th>
					<td><input type="text" name="permissionCode"
						placeholder="Enter Permission Code"
						value="<%=DataUtility.getStringData(bean.getPermissionCode())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("permissionCode", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Permission Name<span style="color: red">*</span></th>
					<td><input type="text" name="permissionName"
						placeholder="Enter Permission Name"
						value="<%=DataUtility.getStringData(bean.getPermissionName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("permissionName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Module Name<span style="color: red">*</span></th>
					<td><input type="text" name="moduleName"
						placeholder="Enter Module Name"
						value="<%=DataUtility.getStringData(bean.getModuleName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("moduleName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Permission Status<span style="color: red">*</span></th>
					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("ACTIVE", "ACTIVE");
							map.put("INACTIVE", "INACTIVE");
							String htmlList = HTMLUtility.getList("permissionStatus", bean.getPermissionStatus(), map);
						%>
						<%=htmlList%>
					</td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("permissionStatus", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th></th>
					<%
						if (bean != null && bean.getPermissionId() != null && bean.getPermissionId() > 0) {
					%>
					<td align="left" colspan="2">
						<input type="submit" name="operation" value="<%=PermissionCtl.OP_UPDATE%>">
						<input type="submit" name="operation" value="<%=PermissionCtl.OP_CANCEL%>">
					</td>
					<%
						} else {
					%>
					<td align="left" colspan="2">
						<input type="submit" name="operation" value="<%=PermissionCtl.OP_SAVE%>">
						<input type="submit" name="operation" value="<%=PermissionCtl.OP_RESET%>">
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
