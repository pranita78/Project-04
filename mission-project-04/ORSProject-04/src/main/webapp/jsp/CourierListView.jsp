<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.bean.CourierBean"%>
<%@page import="in.co.rays.proj4.controller.CourierListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>

<html>
<head>
    <title>Courier List</title>
    <link rel="icon" type="image/png"
        href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

<%@include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.CourierBean"
    scope="request"></jsp:useBean>

<div align="center">

    <h1 align="center" style="margin-bottom: -15; color: navy;">
        Courier List
    </h1>

    <div style="height: 15px; margin-bottom: 12px">
        <h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>
        <h3><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h3>
    </div>

    <form action="<%=ORSView.COURIER_LIST_CTL%>" method="post">

        <%
            int pageNo = ServletUtility.getPageNo(request);
            int pageSize = ServletUtility.getPageSize(request);
            int index = ((pageNo - 1) * pageSize) + 1;
            int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

            List<CourierBean> list = (List<CourierBean>) ServletUtility.getList(request);
            Iterator<CourierBean> it = list.iterator();
        %>

        <input type="hidden" name="pageNo" value="<%=pageNo%>">
        <input type="hidden" name="pageSize" value="<%=pageSize%>">

        <!-- 🔍 Search Section -->
        <table style="width: 100%">
            <tr>
                <td align="center">

                    <label><b>Tracking No :</b></label>
                    <input type="text" name="trackingNumber"
                    placeholder="Enter Tracking No"
                        value="<%=ServletUtility.getParameter("trackingNumber", request)%>">

                    &emsp;

                    <label><b>Sender :</b></label>
                    <input type="text" name="senderName"
                    placeholder="Enter senderName"
                        value="<%=ServletUtility.getParameter("senderName", request)%>">

                    &emsp;

                    <label><b>Status :</b></label>
                    <select name="deliveryStatus">
                        <option value="">--All--</option>
                        <option value="Pending">Pending</option>
                        <option value="Shipped">Shipped</option>
                        <option value="Delivered">Delivered</option>
                    </select>

                    &emsp;

                    <input type="submit" name="operation"
                        value="<%=CourierListCtl.OP_SEARCH%>">

                    <input type="submit" name="operation"
                        value="<%=CourierListCtl.OP_RESET%>">

                </td>
            </tr>
        </table>

        <br>

        <!-- 📋 List Table -->
        <table border="1" style="width: 100%; border: groove;">

            <tr style="background-color: #e1e6f1e3;">
                <th width="5%"><input type="checkbox" id="selectall" /></th>
                <th width="5%">S.No</th>
                <th width="15%">Tracking No</th>
                <th width="15%">Sender</th>
                <th width="15%">Receiver</th>
                <th width="15%">Dispatch Date</th>
                <th width="15%">Delivery Date</th>
                <th width="10%">Status</th>
                <th width="5%">Edit</th>
            </tr>

            <%
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                while (it.hasNext()) {
                    bean = it.next();
            %>

            <tr>
                <td align="center">
                    <input type="checkbox" name="ids"
                        value="<%=bean.getId()%>">
                </td>

                <td align="center"><%=index++%></td>

                <td align="center"><%=bean.getTrackingNumber()%></td>
                <td align="center"><%=bean.getSenderName()%></td>
                <td align="center"><%=bean.getReceiverName()%></td>
                <td align="center"><%=sdf.format(bean.getDispatchDate())%></td>
                <td align="center">
                    <%=bean.getDeliveryDate() != null ? sdf.format(bean.getDeliveryDate()) : ""%>
                </td>
                <td align="center"><%=bean.getDeliveryStatus()%></td>

                <td align="center">
                    <a href="CourierCtl?id=<%=bean.getId()%>">Edit</a>
                </td>
            </tr>

            <%
                }
            %>
        </table>

        <!-- 📄 Pagination -->
        <table style="width: 100%">
            <tr>
                <td style="width: 25%">
                    <input type="submit" name="operation"
                        value="<%=CourierListCtl.OP_PREVIOUS%>"
                        <%=pageNo > 1 ? "" : "disabled"%>>
                </td>

                <td align="center" style="width: 25%">
                    <input type="submit" name="operation"
                        value="<%=CourierListCtl.OP_NEW%>">
                </td>

                <td align="center" style="width: 25%">
                    <input type="submit" name="operation"
                        value="<%=CourierListCtl.OP_DELETE%>">
                </td>

                <td style="width: 25%" align="right">
                    <input type="submit" name="operation"
                        value="<%=CourierListCtl.OP_NEXT%>"
                        <%=nextListSize != 0 ? "" : "disabled"%>>
                </td>
            </tr>
        </table>

    </form>
</div>

<%@ include file="Footer.jsp"%>

</body>
</html>