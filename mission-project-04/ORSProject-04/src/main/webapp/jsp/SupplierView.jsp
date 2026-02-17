<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.SupplierCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Supplier</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

<form action="<%=ORSView.SUPPLIER_CTL%>" method="post">

<%@ include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.SupplierBean"
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

Supplier
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
<th align="left">Supplier Name<span style="color: red">*</span></th>
<td>
<input type="text" name="name"
placeholder="Enter Supplier Name"
value="<%=DataUtility.getStringData(bean.getName())%>">
</td>
<td style="position: fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("name", request)%>
</font>
</td>
</tr>

<tr>
<th align="left">Category<span style="color: red">*</span></th>
<td>
<input type="text" name="category"
placeholder="Enter Category"
value="<%=DataUtility.getStringData(bean.getCategory())%>">
</td>
<td style="position: fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("category", request)%>
</font>
</td>
</tr>

<tr>
<th align="left">Date of Birth<span style="width: 98%"
						style="color: red">*</span></th>
					<td><input type="text" name="dob" id="udate"
						placeholder="Select Date of Birth"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
<td style="position: fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("dob", request)%>
</font>
</td>
</tr>

<tr>
<th align="left">Payment Term<span style="color: red">*</span></th>
<td>
<input type="text" name="paymentTerm"
placeholder="Enter Payment Term"
value="<%=DataUtility.getStringData(bean.getPaymentTerm())%>">
</td>
<td style="position: fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("paymentTerm", request)%>
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

<input type="submit"
name="operation"
value="<%=SupplierCtl.OP_UPDATE%>">

<input type="submit"
name="operation"
value="<%=SupplierCtl.OP_CANCEL%>">

<%
} else {
%>

<td align="left" colspan="2">

<input type="submit"
name="operation"
value="<%=SupplierCtl.OP_SAVE%>">

<input type="submit"
name="operation"
value="<%=SupplierCtl.OP_RESET%>">

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
