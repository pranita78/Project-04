<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ComplaintListCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.ComplaintBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

<html>

<head>

<title>Complaint List</title>

<link rel="icon" type="image/png"
href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16"/>

</head>

<body>

<%@include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ComplaintBean" scope="request"></jsp:useBean>

<div align="center">

<h1 style="margin-bottom:-15; color:navy;">Complaint List</h1>

<div style="height:15px; margin-bottom:12px">

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

<form action="<%=ORSView.COMPLAINT_LIST_CTL%>" method="post">

<%

int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);

int index = ((pageNo - 1) * pageSize) + 1;

int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

List<ComplaintBean> list = (List<ComplaintBean>) ServletUtility.getList(request);

Iterator<ComplaintBean> it = list.iterator();

%>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

<table style="width:100%">

<tr>

<td align="center">

<label><b>Complaint Code :</b></label>

<input type="text" name="complaintCode"
placeholder="Enter Complaint Code"
value="<%=ServletUtility.getParameter("complaintCode", request)%>">

&nbsp;&nbsp;

<label><b>Customer Name :</b></label>

<input type="text" name="customerName"
placeholder="Enter Customer Name"
value="<%=ServletUtility.getParameter("customerName", request)%>">

&nbsp;&nbsp;

<input type="submit" name="operation"
value="<%=ComplaintListCtl.OP_SEARCH%>">

&nbsp;

<input type="submit" name="operation"
value="<%=ComplaintListCtl.OP_RESET%>">

</td>

</tr>

</table>

<br>

<table border="1" style="width:100%; border:groove;">

<tr style="background-color:#e1e6f1e3;">

<th width="5%">
<input type="checkbox" id="selectall">
</th>

<th width="5%">S.No</th>

<th>Complaint Code</th>

<th>Customer Name</th>

<th>Complaint Type</th>

<th>Status</th>

<th>Edit</th>

</tr>

<%

while(it.hasNext()){

bean = it.next();

%>

<tr>

<td style="text-align:center">
<input type="checkbox" class="case"
name="ids"
value="<%=bean.getComplaintId()%>">
</td>

<td style="text-align:center"><%=index++%></td>

<td style="text-align:center">
<%=bean.getComplaintCode()%>
</td>

<td style="text-align:center">
<%=bean.getCustomerName()%>
</td>

<td style="text-align:center">
<%=bean.getComplaintType()%>
</td>

<td style="text-align:center">
<%=bean.getComplaintStatus()%>
</td>

<td style="text-align:center">

<a href="ComplaintCtl?id=<%=bean.getComplaintId()%>">
Edit
</a>

</td>

</tr>

<%
}
%>

</table>

<br>

<table style="width:100%">

<tr>

<td style="width:25%">

<input type="submit"
name="operation"
value="<%=ComplaintListCtl.OP_PREVIOUS%>"
<%=pageNo > 1 ? "" : "disabled"%>>

</td>

<td align="center" style="width:25%">

<input type="submit"
name="operation"
value="<%=ComplaintListCtl.OP_NEW%>">

</td>

<td align="center" style="width:25%">

<input type="submit"
name="operation"
value="<%=ComplaintListCtl.OP_DELETE%>">

</td>

<td align="right" style="width:25%">

<input type="submit"
name="operation"
value="<%=ComplaintListCtl.OP_NEXT%>"
<%=nextListSize != 0 ? "" : "disabled"%>>

</td>

</tr>

</table>

</form>

</div>

<%@ include file="Footer.jsp"%>

</body>

</html>