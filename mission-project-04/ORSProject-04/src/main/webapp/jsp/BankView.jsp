<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.BankCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.bean.BankBean"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bank</title>
</head>

<body>

<%@ include file="Header.jsp"%>

<form action="<%=ORSView.BANK_CTL%>" method="post">

    <jsp:useBean id="bean" class="in.co.rays.proj4.bean.BankBean"
        scope="request"></jsp:useBean>

    <div align="center">

        <h1 style="margin-bottom: -15; color: navy">
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
            Bank
        </h1>


        <!-- Success & Error Messages -->
        <div style="height: 15px; margin-bottom: 12px">
            <h3 align="center">
                <font color="green">
                    <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </h3>
            <h3 align="center">
                <font color="red">
                    <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </h3>
        </div>

        <!-- Hidden Fields -->
        <input type="hidden" name="id" value="<%=bean.getId()%>">
        <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
        <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
        <input type="hidden" name="createdDatetime"
            value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
        <input type="hidden" name="modifiedDatetime"
            value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

        <table>

            <!-- Bank Name -->
            <tr>
                <th align="left">Bank Name <span style="color:red">*</span></th>
                <td>
                    <input type="text" name="bankName"
                        placeholder="Enter Bank Name"
                        value="<%=DataUtility.getStringData(bean.getBankName())%>">
                </td>
                <td style="position: fixed;">
                    <font color="red">
                        <%=ServletUtility.getErrorMessage("bankName", request)%>
                    </font>
                </td>
            </tr>

            <!-- Branch Name -->
            <tr>
                <th align="left">Branch Name <span style="color:red">*</span></th>
                <td>
                    <input type="text" name="branchName"
                        placeholder="Enter Branch Name"
                        value="<%=DataUtility.getStringData(bean.getBranchName())%>">
                </td>
                <td style="position: fixed;">
                    <font color="red">
                        <%=ServletUtility.getErrorMessage("branchName", request)%>
                    </font>
                </td>
            </tr>

            <!-- Account Holder Name -->
            <tr>
                <th align="left">Account Holder Name <span style="color:red">*</span></th>
                <td>
                    <input type="text" name="accountHolderName"
                        placeholder="Enter Account Holder Name"
                        value="<%=DataUtility.getStringData(bean.getAccountHolderName())%>">
                </td>
                <td style="position: fixed;">
                    <font color="red">
                        <%=ServletUtility.getErrorMessage("accountHolderName", request)%>
                    </font>
                </td>
            </tr>

            <!-- Mobile No -->
            <tr>
                <th align="left">Mobile No <span style="color:red">*</span></th>
                <td>
                    <input type="text" name="mobileNo"
                        placeholder="Enter Mobile No"
                        value="<%=DataUtility.getStringData(bean.getMobileNo())%>">
                </td>
                <td style="position: fixed;">
                    <font color="red">
                        <%=ServletUtility.getErrorMessage("mobileNo", request)%>
                    </font>
                </td>
            </tr>

            <!-- Buttons -->
            <tr>
                <th></th>
                <%
                    if (bean != null && bean.getId() > 0) {
                %>
                <td colspan="2">
                    <input type="submit" name="operation"
                        value="<%=BankCtl.OP_UPDATE%>">
                    <input type="submit" name="operation"
                        value="<%=BankCtl.OP_CANCEL%>">
                </td>
                <%
                    } else {
                %>
                <td colspan="2">
                    <input type="submit" name="operation"
                        value="<%=BankCtl.OP_SAVE%>">
                    <input type="submit" name="operation"
                        value="<%=BankCtl.OP_RESET%>">
                </td>
                <%
                    }
                %>
            </tr>

        </table>

    </div>

</form>

</body>
</html>
