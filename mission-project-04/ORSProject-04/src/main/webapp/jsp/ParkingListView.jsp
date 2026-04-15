<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ParkingListCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.ParkingBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

<html>

<head>
<title>Parking List</title>

<link rel="icon" type="image/png"
href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16"/>

</head>

<body>

<%@include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ParkingBean" scope="request"></jsp:useBean>

<div align="center">

<h1 style="margin-bottom:-15; color:navy;">Parking List</h1>

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

<form action="<%=ORSView.PARKING_LIST_CTL%>" method="post">

<%
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);

int index = ((pageNo - 1) * pageSize) + 1;

int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

List<ParkingBean> list = (List<ParkingBean>) ServletUtility.getList(request);

Iterator<ParkingBean> it = list.iterator();
%>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

<table style="width:100%">

<tr>

<td align="center">

<label><b>Location :</b></label>

<input type="text" name="location"
placeholder="Enter Location"
value="<%=ServletUtility.getParameter("location", request)%>">

&nbsp;&nbsp;

<input type="submit" name="operation"
value="<%=ParkingListCtl.OP_SEARCH%>">

&nbsp;

<input type="submit" name="operation"
value="<%=ParkingListCtl.OP_RESET%>">

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

<th>Location</th>
<th>Capacity</th>
<th>Fee</th>

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
value="<%=bean.getParkingId()%>">
</td>

<td style="text-align:center"><%=index++%></td>

<td style="text-align:center">
<%=bean.getLocation()%>
</td>

<td style="text-align:center">
<%=bean.getCapacity()%>
</td>

<td style="text-align:center">
<%=bean.getFee()%>
</td>

<td style="text-align:center">
<a href="ParkingCtl?id=<%=bean.getParkingId()%>">
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
value="<%=ParkingListCtl.OP_PREVIOUS%>"
<%=pageNo > 1 ? "" : "disabled"%>>
</td>

<td align="center" style="width:25%">
<input type="submit"
name="operation"
value="<%=ParkingListCtl.OP_NEW%>">
</td>

<td align="center" style="width:25%">
<input type="submit"
name="operation"
value="<%=ParkingListCtl.OP_DELETE%>">
</td>

<td align="right" style="width:25%">
<input type="submit"
name="operation"
value="<%=ParkingListCtl.OP_NEXT%>"
<%=nextListSize != 0 ? "" : "disabled"%>>
</td>

</tr>

</table>

</form>

</div>

<%@ include file="Footer.jsp"%>

</body>
</html>