<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.NotificationCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Notification</title>
</head>
<body>
    <%@ include file="Header.jsp"%>
    <form action="<%=ORSView.NOTIFICATION_CTL%>" method="post">
        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.NotificationBean"
            scope="request"></jsp:useBean>

        <h1 align="center" style="margin-bottom: -15; color: navy">
            <%if (bean != null && bean.getId() > 0) {%>Update<%} else {%>Add<%}%>
            Notification
        </h1>
        <div style="height: 15px; margin-bottom: 12px">
            <H3 align="center"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></H3>
            <H3 align="center"><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></H3>
        </div>

        <input type="hidden" name="id" value="<%=bean.getId()%>">
        <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
        <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
        <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
        <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

        <table align="center">
            <tr>
                <th>Notification Code</th>
                <td><input type="text" name="notificationCode"
                    value="<%=DataUtility.getStringData(bean.getNotificationCode())%>"
                    placeholder="enter notification code here"></td>
                <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("notificationCode", request)%></font></td>
            </tr>
            <tr>
                <th>Message</th>
                <td><input type="text" name="message"
                    value="<%=DataUtility.getStringData(bean.getMessage())%>"
                    placeholder="enter message here"></td>
                <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("message", request)%></font></td>
            </tr>
            <tr>
                <th>Sent To</th>
                <td><input type="text" name="sentTo"
                    value="<%=DataUtility.getStringData(bean.getSentTo())%>"
                    placeholder="enter recipient here"></td>
                <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("sentTo", request)%></font></td>
            </tr>
            <tr>
                <th>Sent Time</th>
                <td><input type="text" id="udatee" name="sentTime"
                    value="<%=DataUtility.getDateString(bean.getSentTime())%>"></td>
                <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("sentTime", request)%></font></td>
            </tr>
            <tr>
                <th>Notification Status</th>
                <td><%HashMap map = new HashMap();
                map.put("done", "done");
                map.put("in progress", "in progress");
                %>
                <%=HTMLUtility.getList("notificationStatus", bean.getNotificationStatus(), map) %>
                </td>
                <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("notificationStatus", request)%></font></td>
            </tr>
            <tr>
                <th></th>
                <%if (bean != null && bean.getId() > 0) {%>
                <td align="left" colspan="2">
                    <input type="submit" name="operation" value="<%=NotificationCtl.OP_UPDATE%>">
                    <input type="submit" name="operation" value="<%=NotificationCtl.OP_CANCEL%>">
                <%} else {%>
                <td align="left" colspan="2">
                    <input type="submit" name="operation" value="<%=NotificationCtl.OP_SAVE%>">
                    <input type="submit" name="operation" value="<%=NotificationCtl.OP_RESET%>">
                <%}%>
                </td>
            </tr>
        </table>
    </form>
    <%@ include file="Footer.jsp"%>
</body>
</html>