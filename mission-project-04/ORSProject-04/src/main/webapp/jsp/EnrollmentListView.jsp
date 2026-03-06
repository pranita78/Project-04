<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.EnrollmentListCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
    <title>Enrollment List</title>
    <link rel="icon" type="image/png"
        href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

<%@include file="Header.jsp"%>

<jsp:useBean id="bean"
    class="in.co.rays.proj4.bean.EnrollmentBean"
    scope="request"></jsp:useBean>

<div align="center">

    <h1 align="center" style="margin-bottom: -15; color: navy;">
        Enrollment List
    </h1>

    <div style="height: 15px; margin-bottom: 12px">
        <h3><font color="red">
            <%=ServletUtility.getErrorMessage(request)%>
        </font></h3>

        <h3><font color="green">
            <%=ServletUtility.getSuccessMessage(request)%>
        </font></h3>
    </div>

    <form action="<%=ORSView.ENROLLMENT_LIST_CTL%>" method="post">

        <%
            int pageNo = ServletUtility.getPageNo(request);
            int pageSize = ServletUtility.getPageSize(request);
            int index = ((pageNo - 1) * pageSize) + 1;
            int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

            List<in.co.rays.proj4.bean.EnrollmentBean> list =
                (List<in.co.rays.proj4.bean.EnrollmentBean>) ServletUtility.getList(request);

            Iterator<in.co.rays.proj4.bean.EnrollmentBean> it = list.iterator();

            if (list.size() != 0) {
        %>

        <input type="hidden" name="pageNo" value="<%=pageNo%>">
        <input type="hidden" name="pageSize" value="<%=pageSize%>">

        <!-- 🔎 Search Section -->
        <table style="width: 100%">
            <tr>
                <td align="center">

                    <label><b>Enrollment Code :</b></label>
                    <input type="text" name="enrollmentCode"
                        placeholder="Enter Code"
                        value="<%=ServletUtility.getParameter("enrollmentCode", request)%>">&emsp;

                    <label><b>Student Name :</b></label>
                    <input type="text" name="studentName"
                        placeholder="Enter Student Name"
                        value="<%=ServletUtility.getParameter("studentName", request)%>">&emsp;

                    <label><b>Course Name :</b></label>
                    <input type="text" name="courseName"
                        placeholder="Enter Course Name"
                        value="<%=ServletUtility.getParameter("courseName", request)%>">&emsp;

                    <input type="submit" name="operation"
                        value="<%=EnrollmentListCtl.OP_SEARCH%>">

                    &nbsp;

                    <input type="submit" name="operation"
                        value="<%=EnrollmentListCtl.OP_RESET%>">
                </td>
            </tr>
        </table>

        <br>

        <!-- 📋 Table -->
        <table border="1" style="width: 100%; border: groove;">

            <tr style="background-color: #e1e6f1e3;">
                <th width="5%"><input type="checkbox" id="selectall" /></th>
                <th width="5%">S.No</th>
                <th width="15%">Enrollment Code</th>
                <th width="20%">Student Name</th>
                <th width="20%">Course Name</th>
                <th width="15%">Enrollment Date</th>
                <th width="10%">Edit</th>
            </tr>

            <%
                while (it.hasNext()) {

                    in.co.rays.proj4.bean.EnrollmentBean eBean =
                        (in.co.rays.proj4.bean.EnrollmentBean) it.next();

                    SimpleDateFormat sdf =
                        new SimpleDateFormat("dd-MM-yyyy");

                    String date = sdf.format(eBean.getEnrollmentDate());
            %>

            <tr>
                <td align="center">
                    <input type="checkbox" class="case"
                        name="ids"
                        value="<%=eBean.getId()%>">
                </td>

                <td align="center"><%=index++%></td>
                <td align="center"><%=eBean.getEnrollmentCode()%></td>
                <td align="center"><%=eBean.getStudentName()%></td>
                <td align="center"><%=eBean.getCourseName()%></td>
                <td align="center"><%=date%></td>

                <td align="center">
                    <a href="EnrollmentCtl?id=<%=eBean.getId()%>">
                        Edit
                    </a>
                </td>
            </tr>

            <%
                }
            %>
        </table>

        <!-- ⬅ Pagination Buttons -->
        <table style="width: 100%">
            <tr>
                <td style="width: 25%">
                    <input type="submit" name="operation"
                        value="<%=EnrollmentListCtl.OP_PREVIOUS%>"
                        <%=pageNo > 1 ? "" : "disabled"%>>
                </td>

                <td align="center" style="width: 25%">
                    <input type="submit" name="operation"
                        value="<%=EnrollmentListCtl.OP_NEW%>">
                </td>

                <td align="center" style="width: 25%">
                    <input type="submit" name="operation"
                        value="<%=EnrollmentListCtl.OP_DELETE%>">
                </td>

                <td align="right" style="width: 25%">
                    <input type="submit" name="operation"
                        value="<%=EnrollmentListCtl.OP_NEXT%>"
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
                        value="<%=EnrollmentListCtl.OP_BACK%>">
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