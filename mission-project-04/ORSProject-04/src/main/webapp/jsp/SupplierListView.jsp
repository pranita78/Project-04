<%@page import="in.co.rays.proj4.bean.SupplierBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.SupplierListCtl"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Supplier List</title>
<link rel="icon" type="image/png"
href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

<%@include file="Header.jsp"%>

<jsp:useBean id="bean"
class="in.co.rays.proj4.bean.SupplierBean"
scope="request"></jsp:useBean>

<div align="center">

<h1 align="center"
style="margin-bottom: -15; color: navy;">
Supplier List
</h1>

<div style="height: 15px; margin-bottom: 12px">

<h3>
<font color="red">
<%=ServletUtility.getErrorMessage(request)%>
</font>
</h3>

<h3>
<font color="green">
<%=ServletUtility.getSuccessMessage(request)%>
</font>
</h3>

</div>

<form action="<%=ORSView.SUPPLIER_LIST_CTL%>" method="post">

<%
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int index = ((pageNo - 1) * pageSize) + 1;

int nextListSize = DataUtility.getInt(
request.getAttribute("nextListSize").toString());

List<SupplierBean> list =
(List<SupplierBean>) ServletUtility.getList(request);

Iterator<SupplierBean> it = list.iterator();

if (list.size() != 0) {
%>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

<!-- 🔍 Search Panel (User jaisa hi style) -->

<table style="width: 100%">
<tr>
<td align="center">

<label><b>Name :</b></label>
<input type="text" name="name"
placeholder="Enter Name"
value="<%=ServletUtility.getParameter("name", request)%>">

&nbsp;&nbsp;

<label><b>Category :</b></label>
<input type="text" name="category"
placeholder="Enter Category"
value="<%=ServletUtility.getParameter("category", request)%>">

&nbsp;&nbsp;

<input type="submit" name="operation"
value="<%=SupplierListCtl.OP_SEARCH%>">

&nbsp;

<input type="submit" name="operation"
value="<%=SupplierListCtl.OP_RESET%>">

</td>
</tr>
</table>

<br>

<!-- 📋 Table (User jaisa design) -->

<table border="1" style="width: 100%; border: groove;">

<tr style="background-color: #e1e6f1e3;">
<th width="5%">
<input type="checkbox" id="selectall" />
</th>

<th width="5%">S.No</th>
<th width="20%">Name</th>
<th width="20%">Category</th>
<th width="20%">Date of Birth</th>
<th width="15%">Payment Term</th>
<th width="5%">Edit</th>
</tr>

<%
SimpleDateFormat sdf =
new SimpleDateFormat("dd-MM-yyyy");

while (it.hasNext()) {

bean = it.next();

String date =
(bean.getDob() != null)
? sdf.format(bean.getDob())
: "";
%>

<tr>

<td style="text-align: center;">
<input type="checkbox"
class="case"
name="ids"
value="<%=bean.getId()%>">
</td>

<td style="text-align: center;">
<%=index++%>
</td>

<td style="text-align: center; text-transform: capitalize;">
<%=bean.getName()%>
</td>

<td style="text-align: center;">
<%=bean.getCategory()%>
</td>

<td style="text-align: center;">
<%=date%>
</td>

<td style="text-align: center;">
<%=bean.getPaymentTerm()%>
</td>

<td style="text-align: center;">
<a href="SupplierCtl?id=<%=bean.getId()%>">
Edit
</a>
</td>

</tr>

<%
}
%>

</table>

<!-- 📄 Pagination (same as UserList) -->

<table style="width: 100%">
<tr>

<td style="width: 25%">
<input type="submit" name="operation"
value="<%=SupplierListCtl.OP_PREVIOUS%>"
<%=pageNo > 1 ? "" : "disabled"%>>
</td>

<td align="center" style="width: 25%">
<input type="submit" name="operation"
value="<%=SupplierListCtl.OP_NEW%>">
</td>

<td align="center" style="width: 25%">
<input type="submit" name="operation"
value="<%=SupplierListCtl.OP_DELETE%>">
</td>

<td style="width: 25%" align="right">
<input type="submit" name="operation"
value="<%=SupplierListCtl.OP_NEXT%>"
<%=nextListSize != 0 ? "" : "disabled"%>>
</td>

</tr>
</table>

<%
} else {
%>

<table>
<tr>
<td align="right">

<input type="submit" name="operation"
value="<%=SupplierListCtl.OP_BACK%>">

</td>
</tr>
</table>

<%
}
%>

</form>
</div>

<%@include file="Footer.jsp"%>

</body>
</html>
