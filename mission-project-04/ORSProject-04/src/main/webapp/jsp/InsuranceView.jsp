<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.InsuranceCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Insurance</title>

<link rel="icon" type="image/png"
href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16"/>

</head>

<body>

<form action="<%=ORSView.INSURANCE_CTL%>" method="post">

<%@ include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.InsuranceBean" scope="request"></jsp:useBean>

<div align="center">

<h1 style="margin-bottom:-15; color:navy">

<%

if(bean != null && bean.getInsuranceId() != null && bean.getInsuranceId() > 0){
%>

Update Insurance

<%
}else{
%>

Add Insurance

<%
}
%>

</h1>


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


<input type="hidden" name="id" value="<%=bean.getId()%>">

<input type="hidden" name="createdBy"
value="<%=bean.getCreatedBy()%>">

<input type="hidden" name="modifiedBy"
value="<%=bean.getModifiedBy()%>">

<input type="hidden" name="createdDatetime"
value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">

<input type="hidden" name="modifiedDatetime"
value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">


<table>


<tr>
<th align="left">Insurance Number<span style="color:red">*</span></th>

<td>

<input type="text" name="insuranceNumber"
placeholder="Enter Insurance Number"
value="<%=DataUtility.getStringData(bean.getInsuranceNumber())%>">

</td>

<td style="position:fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("insuranceNumber", request)%>
</font>
</td>

</tr>



<tr>

<th align="left">Car Id<span style="color:red">*</span></th>

<td>

<input type="text" name="carId"
placeholder="Enter Car Id"
value="<%=bean.getCarId()==null ? "" : bean.getCarId()%>">

</td>

<td style="position:fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("carId", request)%>
</font>
</td>

</tr>



<tr>

<th align="left">Expiry Date<span style="color:red">*</span></th>

<td>

<input type="text" name="expiryDate" id="udate"
placeholder="Select Expiry Date"
value="<%=bean.getExpiryDate()==null ? "" : DataUtility.getDateString(bean.getExpiryDate())%>">

</td>

<td style="position:fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("expiryDate", request)%>
</font>
</td>

</tr>



<tr>

<th align="left">Insurance Status<span style="color:red">*</span></th>

<td>

<%
HashMap<String,String> map = new HashMap<String,String>();

map.put("Active","Active");
map.put("Expired","Expired");
map.put("Pending","Pending");

String htmlList = HTMLUtility.getList("insuranceStatus", bean.getInsuranceStatus(), map);
%>

<%=htmlList%>

</td>

<td style="position:fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("insuranceStatus", request)%>
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

if(bean != null && bean.getInsuranceId() != null && bean.getInsuranceId() > 0){

%>

<td align="left" colspan="2">

<input type="submit" name="operation"
value="<%=InsuranceCtl.OP_UPDATE%>">

<input type="submit" name="operation"
value="<%=InsuranceCtl.OP_CANCEL%>">

</td>

<%

}else{

%>

<td align="left" colspan="2">

<input type="submit" name="operation"
value="<%=InsuranceCtl.OP_SAVE%>">

<input type="submit" name="operation"
value="<%=InsuranceCtl.OP_RESET%>">

</td>

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