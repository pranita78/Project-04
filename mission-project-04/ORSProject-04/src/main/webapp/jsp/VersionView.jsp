<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.VersionCtl"%> 
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Version</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

<form action="<%=ORSView.VERSION_CTL%>" method="post">

<%@ include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.VersionBean"
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
Version
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
	<th align="left">Version Number<span style="color: red">*</span></th>
	<td>
		<input type="text" name="versionNumber"
			placeholder="Enter Version Number"
			value="<%=DataUtility.getStringData(bean.getVersionNumber())%>">
	</td>
	<td style="position: fixed;">
		<font color="red">
			<%=ServletUtility.getErrorMessage("versionNumber", request)%>
		</font>
	</td>
</tr>

<tr>
	<th align="left">Release Notes<span style="color: red">*</span></th>
	<td>
		<textarea name="releaseNotes" placeholder="Enter Release Notes"><%=DataUtility.getStringData(bean.getReleaseNotes())%></textarea>
	</td>
	<td style="position: fixed;">
		<font color="red">
			<%=ServletUtility.getErrorMessage("releaseNotes", request)%>
		</font>
	</td>
</tr>

<tr>
	<th align="left">Release Date<span style="width: 98%"
						style="color: red">*</span></th>
					<td><input type="date" name="releaseDate" id="udate"
					placeholder="Select Release Date"
			value="<%=DataUtility.getDateString(bean.getReleaseDate())%>">
	</td>
	<td style="position: fixed;">
		<font color="red">
			<%=ServletUtility.getErrorMessage("releaseDate", request)%>
		</font>
	</td>
</tr>

<tr>
	<th align="left">Version Status<span style="color: red">*</span></th>
	<td>
	<%
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Active", "Active");
		map.put("Inactive", "Inactive");

		String htmlList = HTMLUtility.getList("versionStatus",
				bean.getVersionStatus(), map);
	%>
	<%=htmlList%>
	</td>
	<td style="position: fixed;">
		<font color="red">
			<%=ServletUtility.getErrorMessage("versionStatus", request)%>
		</font>
	</td>
</tr>

<tr>
	<th></th>
	<%
		if (bean != null && bean.getId() > 0) {
	%>
	<td align="left" colspan="2">
		<input type="submit" name="operation"
			value="<%=VersionCtl.OP_UPDATE%>">
		<input type="submit" name="operation"
			value="<%=VersionCtl.OP_CANCEL%>">
	</td>
	<%
		} else {
	%>
	<td align="left" colspan="2">
		<input type="submit" name="operation"
			value="<%=VersionCtl.OP_SAVE%>">
		<input type="submit" name="operation"
			value="<%=VersionCtl.OP_RESET%>">
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
