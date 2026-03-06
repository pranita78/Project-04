<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.bean.BaseBean"%>
<%@page import="in.co.rays.proj4.controller.InventoryListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
    <title>Inventory List</title>
    <link rel="icon" type="image/png"
        href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

<%@include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.InventoryBean"
    scope="request"></jsp:useBean>

<div align="center">

    <h1 align="center" style="margin-bottom: -15; color: navy;">
        Inventory List
    </h1>

    <div style="height: 15px; margin-bottom: 12px">
        <h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>
        <h3><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h3>
    </div>

    <form action="<%=ORSView.INVENTORY_LIST_CTL%>" method="post">

        <%
            int pageNo = ServletUtility.getPageNo(request);
            int pageSize = ServletUtility.getPageSize(request);
            int index = ((pageNo - 1) * pageSize) + 1;
            int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

            List list = ServletUtility.getList(request);
            Iterator it = list.iterator();

            if (list.size() != 0) {
        %>

        <input type="hidden" name="pageNo" value="<%=pageNo%>">
        <input type="hidden" name="pageSize" value="<%=pageSize%>">

        <!-- Search Section -->
        <table style="width: 100%">
            <tr>
                <td align="center">
                    <label><b>Item Code :</b></label>
                    <input type="text" name="itemCode"
                        placeholder="Enter Item Code"
                        value="<%=ServletUtility.getParameter("itemCode", request)%>">&emsp;

                    <label><b>Item Name :</b></label>
                    <input type="text" name="itemName"
                        placeholder="Enter Item Name"
                        value="<%=ServletUtility.getParameter("itemName", request)%>">&emsp;

                    <input type="submit" name="operation"
                        value="<%=InventoryListCtl.OP_SEARCH%>">
                    &nbsp;
                    <input type="submit" name="operation"
                        value="<%=InventoryListCtl.OP_RESET%>">
                </td>
            </tr>
        </table>

        <br>

        <!-- Data Table -->
        <table border="1" style="width: 100%; border: groove;">
            <tr style="background-color: #e1e6f1e3;">
                <th width="5%"><input type="checkbox" id="selectall" /></th>
                <th width="5%">S.No</th>
                <th width="15%">Item Code</th>
                <th width="20%">Item Name</th>
                <th width="10%">Quantity</th>
                <th width="10%">Price</th>
                <th width="15%">Item Status</th>
                <th width="10%">Edit</th>
            </tr>

            <%
                while (it.hasNext()) {
                    bean = (in.co.rays.proj4.bean.InventoryBean) it.next();
            %>

            <tr>
                <td style="text-align: center;">
                    <input type="checkbox" class="case" name="ids"
                        value="<%=bean.getId()%>">
                </td>
                <td style="text-align: center;"><%=index++%></td>
                <td style="text-align: center;"><%=bean.getItemCode()%></td>
                <td style="text-align: center;"><%=bean.getItemName()%></td>
                <td style="text-align: center;"><%=bean.getQuantity()%></td>
                <td style="text-align: center;"><%=bean.getPrice()%></td>
                <td style="text-align: center;"><%=bean.getItemStatus()%></td>
                <td style="text-align: center;">
                    <a href="InventoryCtl?id=<%=bean.getId()%>">Edit</a>
                </td>
            </tr>

            <%
                }
            %>
        </table>

        <!-- Pagination Buttons -->
        <table style="width: 100%">
            <tr>
                <td style="width: 25%">
                    <input type="submit" name="operation"
                        value="<%=InventoryListCtl.OP_PREVIOUS%>"
                        <%=pageNo > 1 ? "" : "disabled"%>>
                </td>
                <td align="center" style="width: 25%">
                    <input type="submit" name="operation"
                        value="<%=InventoryListCtl.OP_NEW%>">
                </td>
                <td align="center" style="width: 25%">
                    <input type="submit" name="operation"
                        value="<%=InventoryListCtl.OP_DELETE%>">
                </td>
                <td style="width: 25%" align="right">
                    <input type="submit" name="operation"
                        value="<%=InventoryListCtl.OP_NEXT%>"
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
                        value="<%=InventoryListCtl.OP_BACK%>">
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