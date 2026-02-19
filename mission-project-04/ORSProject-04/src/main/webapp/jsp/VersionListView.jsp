<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.VersionListCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.VersionBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>

<html>
<head>
<title>Version List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

<%@include file="Header.jsp"%>

<jsp:useBean id="bean"
	class="in.co.rays.proj4.bean.VersionBean"
	scope="request"></jsp:useBean>

<div align="center">

<h1 align="center" style="margin-bottom: -15; color: navy;">
	Version List
</h1>

<div style="height: 15px; margin-bottom: 12px">
	<h3><font color="red">
		<%=ServletUtility.getErrorMessage(request)%>
	</font></h3>

	<h3><font color="green">
		<%=ServletUtility.getSuccessMessage(request)%>
	</font></h3>
</div>

<form action="<%=ORSView.VERSION_LIST_CTL%>" method="post">

<%
	int pageNo = ServletUtility.getPageNo(request);
	int pageSize = ServletUtility.getPageSize(request);
	int index = ((pageNo - 1) * pageSize) + 1;
	int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

	List<VersionBean> list = (List<VersionBean>) ServletUtility.getList(request);
	Iterator<VersionBean> it = list.iterator();
%>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

<!-- 🔍 Search Panel -->
<table style="width: 100%">
<tr>
<td align="center">

<label><b>Version Number :</b></label>
<input type="text" name="versionNumber"
	placeholder="Enter Version Number"
	value="<%=ServletUtility.getParameter("versionNumber", request)%>">

&nbsp;&nbsp;

<label><b>Status :</b></label>
<input type="text" name="versionStatus"
	placeholder="Enter Status"
	value="<%=ServletUtility.getParameter("versionStatus", request)%>">

&nbsp;&nbsp;

<input type="submit" name="operation"
	value="<%=VersionListCtl.OP_SEARCH%>">

&nbsp;

<input type="submit" name="operation"
	value="<%=VersionListCtl.OP_RESET%>">

</td>
</tr>
</table>

<br>

<%
	if (list.size() != 0) {
%>

<table border="1" style="width: 100%; border: groove;">

<tr style="background-color: #e1e6f1e3;">
	<th width="5%"><input type="checkbox" id="selectall" /></th>
	<th width="5%">S.No</th>
	<th width="15%">Version Number</th>
	<th width="25%">Release Notes</th>
	<th width="15%">Release Date</th>
	<th width="10%">Status</th>
	<th width="10%">Edit</th>
</tr>

<%
	while (it.hasNext()) {

		bean = it.next();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String date = "";
		if (bean.getReleaseDate() != null) {
			date = sdf.format(bean.getReleaseDate());
		}
%>

<tr>
	<td align="center">
		<input type="checkbox" name="ids"
			value="<%=bean.getId()%>">
	</td>

	<td align="center"><%=index++%></td>

	<td align="center">
		<%=bean.getVersionNumber()%>
	</td>

	<td align="center">
		<%=bean.getReleaseNotes()%>
	</td>

	<td align="center">
		<%=date%>
	</td>

	<td align="center">
		<%=bean.getVersionStatus()%>
	</td>

	<td align="center">
		<a href="VersionCtl?id=<%=bean.getId()%>">
			Edit
		</a>
	</td>
</tr>

<%
	}
%>

</table>

<br>

<!-- Pagination Buttons -->
<table style="width: 100%">
<tr>

<td style="width: 25%">
	<input type="submit" name="operation"
		value="<%=VersionListCtl.OP_PREVIOUS%>"
		<%=pageNo > 1 ? "" : "disabled"%>>
</td>

<td align="center" style="width: 25%">
	<input type="submit" name="operation"
		value="<%=VersionListCtl.OP_NEW%>">
</td>

<td align="center" style="width: 25%">
	<input type="submit" name="operation"
		value="<%=VersionListCtl.OP_DELETE%>">
</td>

<td align="right" style="width: 25%">
	<input type="submit" name="operation"
		value="<%=VersionListCtl.OP_NEXT%>"
		<%=nextListSize != 0 ? "" : "disabled"%>>
</td>

</tr>
</table>

<%
	} else {
%>

<h3>No Record Found</h3>

<input type="submit" name="operation"
	value="<%=VersionListCtl.OP_BACK%>">

<%
	}
%>

</form>
</div>

<%@ include file="Footer.jsp"%>

</body>
</html>
