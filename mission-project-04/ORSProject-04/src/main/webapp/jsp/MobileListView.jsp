<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.MobileListCtl"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
    <title>Mobile List</title>
    <link rel="icon" type="image/png"
          href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16"/>
</head>

<body>
<%@ include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.MobileBean"
             scope="request"></jsp:useBean>

<div align="center">

    <h1 style="margin-bottom: -15; color: navy;">Mobile List</h1>

    <div style="height: 15px; margin-bottom: 12px">
        <h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>
        <h3><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h3>
    </div>

    <form action="<%=ORSView.MOBILE_LIST_CTL%>" method="post">

        <%
            int pageNo = ServletUtility.getPageNo(request);
            int pageSize = ServletUtility.getPageSize(request);
            int index = ((pageNo - 1) * pageSize) + 1;
            int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

            List list = (List) ServletUtility.getList(request);
            Iterator it = list.iterator();
        %>

        <input type="hidden" name="pageNo" value="<%=pageNo%>">
        <input type="hidden" name="pageSize" value="<%=pageSize%>">

        <!-- 🔍 Search Section -->
        <table style="width: 100%">
            <tr>
                <td align="center">
                    <label><b>Mobile No :</b></label>
                    <input type="text" name="BrandName" maxlength="10"
                           placeholder="Enter brand No"
                           value="<%=ServletUtility.getParameter("BrandName", request)%>">
                    &emsp;

                    <input type="submit" name="operation"
                           value="<%=MobileListCtl.OP_SEARCH%>">
                    &nbsp;
                    <input type="submit" name="operation"
                           value="<%=MobileListCtl.OP_RESET%>">
                </td>
            </tr>
        </table>

        <br>

        <!-- 📋 List Table -->
        <table border="1" style="width: 60%; border: groove;">
            <tr style="background-color: #e1e6f1e3;">
                <th width="5%"><input type="checkbox" id="selectall"></th>
                <th width="10%">S.No</th>
                <th width="45%">Mobile Number</th>
                <th width="10%">Edit</th>
            </tr>

            <%
                while (it.hasNext()) {
                    in.co.rays.proj4.bean.MobileBean mobileBean =
                            (in.co.rays.proj4.bean.MobileBean) it.next();
            %>

            <tr>
                <td align="center">
                    <input type="checkbox" class="case"
                           name="ids" value="<%=mobileBean.getId()%>">
                </td>
                <td align="center"><%=index++%></td>
                <td align="center"><%=mobileBean.getBrand()%></td>
                <td align="center">
                    <a href="MobileCtl?id=<%=mobileBean.getId()%>">Edit</a>
                </td>
            </tr>

            <%
                }
            %>
        </table>

        <!-- 🔁 Pagination / Actions -->
        <table style="width: 60%">
            <tr>
                <td width="25%">
                    <input type="submit" name="operation"
                           value="<%=MobileListCtl.OP_PREVIOUS%>"
                           <%=pageNo > 1 ? "" : "disabled"%>>
                </td>

                <td align="center" width="25%">
                    <input type="submit" name="operation"
                           value="<%=MobileListCtl.OP_NEW%>">
                </td>

                <td align="center" width="25%">
                    <input type="submit" name="operation"
                           value="<%=MobileListCtl.OP_DELETE%>">
                </td>

                <td align="right" width="25%">
                    <input type="submit" name="operation"
                           value="<%=MobileListCtl.OP_NEXT%>"
                           <%=nextListSize != 0 ? "" : "disabled"%>>
                </td>
            </tr>
        </table>

    </form>
</div>

<%@ include file="Footer.jsp"%>
</body>
</html>
