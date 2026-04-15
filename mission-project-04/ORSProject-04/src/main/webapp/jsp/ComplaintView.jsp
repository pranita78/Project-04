<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ComplaintCtl"%> 
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Complaint</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

<form action="<%=ORSView.COMPLAINT_CTL%>" method="post">

<%@ include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ComplaintBean" scope="request"></jsp:useBean>

<h1 align="center">
<%
if (bean != null && bean.getComplaintId() != null && bean.getComplaintId() > 0) {
%>
Update Complaint
<%
} else {
%>
Add Complaint
<%
}
%>
</h1>

<div style="height: 15px; margin-bottom: 12px">
	<H3 align="center">
		<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
	</H3>

	<H3 align="center">
		<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
	</H3>
</div>

<input type="hidden" name="id" value="<%=bean.getComplaintId()%>">
<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
<input type="hidden" name="createdDatetime"
	value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
<input type="hidden" name="modifiedDatetime"
	value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

<table align="center">

<tr>
	<th align="left">Complaint Code<span style="color: red">*</span></th>
	  
	<td>
		<input type="text" name="complaintCode"
		placeholder="Enter Complaint Code"
		value="<%=DataUtility.getStringData(bean.getComplaintCode())%>">
	</td>
	<td>
		<font color="red">
			<%=ServletUtility.getErrorMessage("complaintCode", request)%>
		</font>
	</td>
</tr>

<tr>
	<th align="left">Customer Name<span style="color: red">*</span></th>
	<td>
		<input type="text" name="customerName"
		placeholder="Enter Customer Name"
		value="<%=DataUtility.getStringData(bean.getCustomerName())%>">
	</td>
	<td>
		<font color="red">
			<%=ServletUtility.getErrorMessage("customerName", request)%>
		</font>
	</td>
</tr>

<tr>
	<th align="left">Complaint Type<span style="color: red">*</span></th>
	<td>
		<input type="text" name="complaintType"
		placeholder="Enter Complaint Type"
		value="<%=DataUtility.getStringData(bean.getComplaintType())%>">
	</td>
	<td>
		<font color="red">
			<%=ServletUtility.getErrorMessage("complaintType", request)%>
		</font>
	</td>
</tr>

<tr>
	<th align="left">Complaint Status<span style="color: red">*</span></th>
	<td>
		<select name="complaintStatus">
			<option value="">--Select--</option>
			<option value="Open" <%=("Open".equals(bean.getComplaintStatus()) ? "selected" : "")%>>Open</option>
			<option value="Pending" <%=("Pending".equals(bean.getComplaintStatus()) ? "selected" : "")%>>Pending</option>
			<option value="Closed" <%=("Closed".equals(bean.getComplaintStatus()) ? "selected" : "")%>>Closed</option>
		</select>
	</td>
	<td>
		<font color="red">
			<%=ServletUtility.getErrorMessage("complaintStatus", request)%>
		</font>
	</td>
</tr>

<tr>
	<td colspan="3" align="center">
	<%
	if (bean != null && bean.getComplaintId() != null && bean.getComplaintId() > 0) {
	%>
		<input type="submit" name="operation" value="<%=ComplaintCtl.OP_UPDATE%>">
		<input type="submit" name="operation" value="<%=ComplaintCtl.OP_CANCEL%>">
	<%
	} else {
	%>
		<input type="submit" name="operation" value="<%=ComplaintCtl.OP_SAVE%>">
		<input type="submit" name="operation" value="<%=ComplaintCtl.OP_RESET%>">
	<%
	}
	%>
	</td>
</tr>

</table>

</form>

<%@ include file="Footer.jsp"%>

</body>
</html>