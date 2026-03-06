<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.bean.BaseBean"%>
<%@page import="in.co.rays.proj4.controller.DonationCampListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>

<html>
<head>
<title>Donation Camp List</title>

<link rel="icon" type="image/png"
href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

<%@include file="Header.jsp"%>

<jsp:useBean id="bean"
class="in.co.rays.proj4.bean.DonationCampBean"
scope="request"></jsp:useBean>

<div align="center">

<h1 align="center" style="margin-bottom: -15; color: navy;">
Donation Camp List
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

<form action="<%=ORSView.DONATIONCAMP_LIST_CTL%>" method="post">

<%

int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);

int index = ((pageNo - 1) * pageSize) + 1;

int nextListSize =
DataUtility.getInt(request.getAttribute("nextListSize").toString());

List list = ServletUtility.getList(request);

Iterator it = list.iterator();

if (list.size() != 0) {

%>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

<table style="width: 100%">

<tr>

<td align="center">

<label><b>Camp Name :</b></label>

<input type="text"
name="campName"
placeholder="Enter Camp Name"
value="<%=ServletUtility.getParameter("campName", request)%>">

&nbsp;

<input type="submit"
name="operation"
value="<%=DonationCampListCtl.OP_SEARCH%>">

&nbsp;

<input type="submit"
name="operation"
value="<%=DonationCampListCtl.OP_RESET%>">

</td>

</tr>

</table>

<br>

<table border="1" style="width: 100%; border: groove;">

<tr style="background-color: #e1e6f1e3;">

<th width="5%">
<input type="checkbox" id="selectall" />
</th>

<th width="5%">S.No</th>

<th width="25%">Camp Name</th>

<th width="25%">Camp Date</th>

<th width="25%">Organizer</th>

<th width="10%">Edit</th>

</tr>

<%

while (it.hasNext()) {

bean = (in.co.rays.proj4.bean.DonationCampBean) it.next();

SimpleDateFormat sdf =
new SimpleDateFormat("dd-MM-yyyy");

String date = sdf.format(bean.getCampDate());

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

<td style="text-align: center;">
<%=bean.getCampName()%>
</td>

<td style="text-align: center;">
<%=date%>
</td>

<td style="text-align: center;">
<%=bean.getOrganizer()%>
</td>

<td style="text-align: center;">

<a href="DonationCampCtl?campId=<%=bean.getCampId()%>">

Edit

</a>

</td>

</tr>

<%

}

%>

</table>

<table style="width: 100%">

<tr>

<td style="width: 25%">

<input type="submit"
name="operation"
value="<%=DonationCampListCtl.OP_PREVIOUS%>"
<%=pageNo > 1 ? "" : "disabled"%>>

</td>

<td align="center" style="width: 25%">

<input type="submit"
name="operation"
value="<%=DonationCampListCtl.OP_NEW%>">

</td>

<td align="center" style="width: 25%">

<input type="submit"
name="operation"
value="<%=DonationCampListCtl.OP_DELETE%>">

</td>

<td style="width: 25%" align="right">

<input type="submit"
name="operation"
value="<%=DonationCampListCtl.OP_NEXT%>"
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

<input type="submit"
name="operation"
value="<%=DonationCampListCtl.OP_BACK%>">

</td>

</tr>

</table>

<%

}

%>

</form>

</div>

<%@ include file="Footer.jsp"%>

</body>

</html>