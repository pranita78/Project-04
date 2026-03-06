<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.InsuranceListCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.InsuranceBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

<html>

<head>

<title>Insurance List</title>

<link rel="icon" type="image/png"
href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16"/>

</head>

<body>

<%@include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.InsuranceBean" scope="request"></jsp:useBean>

<div align="center">

<h1 style="margin-bottom:-15; color:navy;">Insurance List</h1>

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

<form action="<%=ORSView.INSURANCE_LIST_CTL%>" method="post">

<%

int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);

int index = ((pageNo - 1) * pageSize) + 1;

int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

List<InsuranceBean> list = (List<InsuranceBean>) ServletUtility.getList(request);

Iterator<InsuranceBean> it = list.iterator();

%>


<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">


<table style="width:100%">

<tr>

<td align="center">

<label><b>Insurance Number :</b></label>

<input type="text" name="insuranceNumber"
placeholder="Enter Insurance Number"
value="<%=ServletUtility.getParameter("insuranceNumber", request)%>">

&nbsp;&nbsp;

<label><b>Car Id :</b></label>

<input type="text" name="carId"
placeholder="Enter Car Id"
value="<%=ServletUtility.getParameter("carId", request)%>">

&nbsp;&nbsp;

<input type="submit" name="operation"
value="<%=InsuranceListCtl.OP_SEARCH%>">

&nbsp;

<input type="submit" name="operation"
value="<%=InsuranceListCtl.OP_RESET%>">

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

<th>Insurance Number</th>

<th>Car Id</th>

<th>Expiry Date</th>

<th>Status</th>

<th>Edit</th>

</tr>


<%

while(it.hasNext()){

bean = it.next();

SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

String date = "";

if(bean.getExpiryDate()!=null){

date = sdf.format(bean.getExpiryDate());

}

%>


<tr>

<td style="text-align:center">

<input type="checkbox" class="case"
name="ids"
value="<%=bean.getInsuranceId()%>">

</td>

<td style="text-align:center"><%=index++%></td>

<td style="text-align:center">
<%=bean.getInsuranceNumber()%>
</td>

<td style="text-align:center">
<%=bean.getCarId()%>
</td>

<td style="text-align:center">
<%=date%>
</td>

<td style="text-align:center">
<%=bean.getInsuranceStatus()%>
</td>

<td style="text-align:center">

<a href="InsuranceCtl?id=<%=bean.getInsuranceId()%>">

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
value="<%=InsuranceListCtl.OP_PREVIOUS%>"
<%=pageNo > 1 ? "" : "disabled"%>>

</td>


<td align="center" style="width:25%">

<input type="submit"
name="operation"
value="<%=InsuranceListCtl.OP_NEW%>">

</td>


<td align="center" style="width:25%">

<input type="submit"
name="operation"
value="<%=InsuranceListCtl.OP_DELETE%>">

</td>


<td align="right" style="width:25%">

<input type="submit"
name="operation"
value="<%=InsuranceListCtl.OP_NEXT%>"
<%=nextListSize != 0 ? "" : "disabled"%>>

</td>

</tr>

</table>


</form>

</div>

<%@ include file="Footer.jsp"%>

</body>

</html>