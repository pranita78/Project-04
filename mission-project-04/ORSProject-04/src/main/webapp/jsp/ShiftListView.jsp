<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ShiftListCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.bean.BaseBean"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj4.bean.ShiftBean"%>

<html>
<head>
    <title>Shift List</title>
    <link rel="icon" type="image/png"
        href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

<%@include file="Header.jsp"%>

<jsp:useBean id="bean"
    class="in.co.rays.proj4.bean.ShiftBean"
    scope="request"></jsp:useBean>

<div align="center">

    <h1 align="center" style="margin-bottom: -15; color: navy;">
        Shift List
    </h1>

    <div style="height: 15px; margin-bottom: 12px">
        <h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>
        <h3><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h3>
    </div>

    <form action="<%=ORSView.SHIFT_LIST_CTL%>" method="post">

        <%
            int pageNo = ServletUtility.getPageNo(request);
            int pageSize = ServletUtility.getPageSize(request);
            int index = ((pageNo - 1) * pageSize) + 1;
            int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

            List<ShiftBean> list = (List<ShiftBean>) ServletUtility.getList(request);
            Iterator<ShiftBean> it = list.iterator();
        %>

        <input type="hidden" name="pageNo" value="<%=pageNo%>">
        <input type="hidden" name="pageSize" value="<%=pageSize%>">

        <!-- Search Panel -->
        <table style="width: 100%">
            <tr>
                <td align="center">
                    <label><b>Shift Code :</b></label>
                    <input type="text" name="shiftCode"
                        placeholder="Enter Shift Code"
                        value="<%=ServletUtility.getParameter("shiftCode", request)%>">&emsp;

                    <label><b>Shift Name :</b></label>
                    <input type="text" name="shiftName"
                        placeholder="Enter Shift Name"
                        value="<%=ServletUtility.getParameter("shiftName", request)%>">&emsp;

                    <input type="submit" name="operation"
                        value="<%=ShiftListCtl.OP_SEARCH%>">

                    <input type="submit" name="operation"
                        value="<%=ShiftListCtl.OP_RESET%>">
                </td>
            </tr>
        </table>

        <br>

        <% if (list != null && list.size() > 0) { %>

        <!-- List Table -->
        <table border="1" style="width: 100%; border: groove;">

            <tr style="background-color: #e1e6f1e3;">
                <th width="5%"><input type="checkbox" id="selectall" /></th>
                <th width="5%">S.No</th>
                <th width="15%">Shift Code</th>
                <th width="20%">Shift Name</th>
                <th width="20%">Start Time</th>
                <th width="20%">End Time</th>
                <th width="10%">Edit</th>
            </tr>

            <%
                while (it.hasNext()) {
                    bean = (ShiftBean) it.next();
            %>

            <tr>
                <td align="center">
                    <input type="checkbox" class="case"
                        name="ids"
                        value="<%=bean.getId()%>">
                </td>

                <td align="center"><%=index++%></td>
                <td align="center"><%=bean.getShiftCode()%></td>
                <td align="center"><%=bean.getShiftName()%></td>
                <td align="center"><%=bean.getStartTime()%></td>
                <td align="center"><%=bean.getEndTime()%></td>

                <td align="center">
                    <a href="ShiftCtl?id=<%=bean.getId()%>">Edit</a>
                </td>
            </tr>

            <% } %>

        </table>

        <!-- Pagination Buttons -->
        <table style="width: 100%">
            <tr>
                <td width="25%">
                    <input type="submit" name="operation"
                        value="<%=ShiftListCtl.OP_PREVIOUS%>"
                        <%=pageNo > 1 ? "" : "disabled"%>>
                </td>

                <td align="center" width="25%">
                    <input type="submit" name="operation"
                        value="<%=ShiftListCtl.OP_NEW%>">
                </td>

                <td align="center" width="25%">
                    <input type="submit" name="operation"
                        value="<%=ShiftListCtl.OP_DELETE%>">
                </td>

                <td align="right" width="25%">
                    <input type="submit" name="operation"
                        value="<%=ShiftListCtl.OP_NEXT%>"
                        <%=nextListSize != 0 ? "" : "disabled"%>>
                </td>
            </tr>
        </table>

        <% } else { %>

        <table>
            <tr>
                <td align="right">
                    <input type="submit" name="operation"
                        value="<%=ShiftListCtl.OP_BACK%>">
                </td>
            </tr>
        </table>

        <% } %>

    </form>
</div>

<%@ include file="Footer.jsp"%>

</body>
</html>