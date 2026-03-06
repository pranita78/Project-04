<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.DonationCampCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Donation Camp</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

<form action="<%=ORSView.DONATIONCAMP_CTL%>" method="post">

<%@ include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.DonationCampBean"
	scope="request"></jsp:useBean>

<div align="center">

<h1 align="center" style="margin-bottom: -15; color: navy">

<%
if (bean != null && bean.getCampId() > 0) {
%>

Update

<%
} else {
%>

Add

<%
}
%>

Donation Camp

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
<th align="left">Camp Name<span style="color:red">*</span></th>

<td>
<input type="text" name="campName"
placeholder="Enter Camp Name"
value="<%=DataUtility.getStringData(bean.getCampName())%>">
</td>

<td style="position: fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("campName", request)%>
</font>
</td>

</tr>

<tr>

<th align="left">Camp Date<span style="color:red">*</span></th>

<td>
<input type="text" name="campDate" id="udate"
placeholder="Select Camp Date"
value="<%=DataUtility.getDateString(bean.getCampDate())%>">
</td>

<td style="position: fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("campDate", request)%>
</font>
</td>

</tr>

<tr>

<th align="left">Organizer<span style="color:red">*</span></th>

<td>
<input type="text" name="organizer"
placeholder="Enter Organizer Name"
value="<%=DataUtility.getStringData(bean.getOrganizer())%>">
</td>

<td style="position: fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("organizer", request)%>
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

if (bean != null && bean.getCampId() > 0) {

%>

<td align="left" colspan="2">

<input type="submit"
name="operation"
value="<%=DonationCampCtl.OP_UPDATE%>">

<input type="submit"
name="operation"
value="<%=DonationCampCtl.OP_CANCEL%>">

<%

} else {

%>

<td align="left" colspan="2">

<input type="submit"
name="operation"
value="<%=DonationCampCtl.OP_SAVE%>">

<input type="submit"
name="operation"
value="<%=DonationCampCtl.OP_RESET%>">

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