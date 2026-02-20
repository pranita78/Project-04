<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.TrainingListCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.bean.BaseBean"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>

<html>
<head>
    <title>Training List</title>
    <link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
<%@include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.TrainingBean" scope="request"></jsp:useBean>

<div align="center">
<h1 align="center" style="margin-bottom: -15; color: navy;">Training List</h1>

<div style="height: 15px; margin-bottom: 12px">
<h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>
<h3><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h3>
</div>

<form action="<%=ORSView.TRAINING_LIST_CTL%>" method="post">

<%
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int index = ((pageNo - 1) * pageSize) + 1;
int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

List list = ServletUtility.getList(request);
Iterator it = list.iterator();
%>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

<!-- Search Panel -->
<table style="width: 100%">
<tr>
<td align="center">

<label><b>Training Code :</b></label>
<input type="text" name="trainingCode"
value="<%=ServletUtility.getParameter("trainingCode", request)%>">&emsp;

<label><b>Training Name :</b></label>
<input type="text" name="trainingName"
value="<%=ServletUtility.getParameter("trainingName", request)%>">&emsp;

<label><b>Status :</b></label>
<select name="trainingStatus">
<option value="">--Select--</option>
<option value="Planned">Planned</option>
<option value="Ongoing">Ongoing</option>
<option value="Completed">Completed</option>
</select>&emsp;

<input type="submit" name="operation" value="<%=TrainingListCtl.OP_SEARCH%>">
<input type="submit" name="operation" value="<%=TrainingListCtl.OP_RESET%>">

</td>
</tr>
</table>

<br>

<!-- Data Table -->
<table border="1" style="width: 100%; border: groove;">
<tr style="background-color: #e1e6f1e3;">
<th width="5%"><input type="checkbox" id="selectall" /></th>
<th width="5%">S.No</th>
<th width="15%">Training Code</th>
<th width="20%">Training Name</th>
<th width="20%">Trainer Name</th>
<th width="15%">Training Date</th>
<th width="10%">Status</th>
<th width="5%">Edit</th>
</tr>

<%
while (it.hasNext()) {
bean = (in.co.rays.proj4.bean.TrainingBean) it.next();
SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
String date = sdf.format(bean.getTrainingDate());
%>

<tr>
<td align="center">
<input type="checkbox" name="ids" value="<%=bean.getId()%>">
</td>

<td align="center"><%=index++%></td>
<td align="center"><%=bean.getTrainingCode()%></td>
<td align="center"><%=bean.getTrainingName()%></td>
<td align="center"><%=bean.getTrainerName()%></td>
<td align="center"><%=date%></td>
<td align="center"><%=bean.getTrainingStatus()%></td>

<td align="center">
<a href="TrainingCtl?id=<%=bean.getId()%>">Edit</a>
</td>
</tr>

<% } %>
</table>

<br>

<!-- Pagination -->
<table style="width: 100%">
<tr>
<td style="width: 25%">
<input type="submit" name="operation"
value="<%=TrainingListCtl.OP_PREVIOUS%>"
<%=pageNo > 1 ? "" : "disabled"%>>
</td>

<td align="center" style="width: 25%">
<input type="submit" name="operation"
value="<%=TrainingListCtl.OP_NEW%>">
</td>

<td align="center" style="width: 25%">
<input type="submit" name="operation"
value="<%=TrainingListCtl.OP_DELETE%>">
</td>

<td align="right" style="width: 25%">
<input type="submit" name="operation"
value="<%=TrainingListCtl.OP_NEXT%>"
<%=nextListSize != 0 ? "" : "disabled"%>>
</td>
</tr>
</table>

</form>
</div>

<%@ include file="Footer.jsp"%>
</body>
</html>