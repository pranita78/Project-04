package in.co.rays.proj4.controller;

import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.PermissionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.PermissionModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "PermissionListCtl", urlPatterns = { "/ctl/PermissionListCtl" })
public class PermissionListCtl extends BaseCtl {

    Logger log = Logger.getLogger(PermissionListCtl.class);

    // =========================================================
    // PRELOAD (Permission ka koi preload dependency nahi)
    // =========================================================
    @Override
    protected void preload(HttpServletRequest request) {
        // No preload dependency for Permission
    }

    // =========================================================
    // POPULATE BEAN
    // =========================================================
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        PermissionBean bean = new PermissionBean();

        bean.setPermissionCode(DataUtility.getString(request.getParameter("permissionCode")));
        bean.setPermissionName(DataUtility.getString(request.getParameter("permissionName")));
        bean.setModuleName(DataUtility.getString(request.getParameter("moduleName")));
        bean.setPermissionStatus(DataUtility.getString(request.getParameter("permissionStatus")));

        return bean;
    }

    // =========================================================
    // DO GET
    // =========================================================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        PermissionBean bean = (PermissionBean) populateBean(request);
        PermissionModel model = new PermissionModel();

        try {
            List<PermissionBean> list = model.search(bean, pageNo, pageSize);
            List<PermissionBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);
            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            ServletUtility.handleExceptionDBList(getView(), bean, pageNo, pageSize, request, response);
            return;
        }
    }

    // =========================================================
    // DO POST
    // =========================================================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        PermissionBean bean = (PermissionBean) populateBean(request);
        PermissionModel model = new PermissionModel();

        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");

        try {

            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.PERMISSION_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;
                if (ids != null && ids.length > 0) {
                    PermissionBean deleteBean = new PermissionBean();
                    for (String id : ids) {
                        deleteBean.setId(DataUtility.getInt(id));
                        model.delete(deleteBean.getPermissionId());
                        ServletUtility.setSuccessMessage("Permission deleted successfully", request);
                    }
                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.PERMISSION_LIST_CTL, request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.PERMISSION_LIST_CTL, request, response);
                return;
            }

            list = model.search(bean, pageNo, pageSize);
            next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found ", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);
            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
            return;
        }
    }

    // =========================================================
    // GET VIEW
    // =========================================================
    @Override
    protected String getView() {
        return ORSView.PERMISSION_LIST_VIEW;
    }
}