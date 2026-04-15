package in.co.rays.proj4.controller;

import java.io.IOException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.PermissionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.PermissionModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "PermissionCtl", urlPatterns = { "/ctl/PermissionCtl" })
public class PermissionCtl extends BaseCtl {

    Logger log = Logger.getLogger(PermissionCtl.class);

    // =========================================================
    // PRELOAD  (UserCtl mein RoleList load hoti thi — 
    //           Permission ka koi preload dependency nahi hai)
    // =========================================================
    @Override
    protected void preload(HttpServletRequest request) {
        // No preload dependency for Permission
    }

    // =========================================================
    // VALIDATE
    // =========================================================
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("permissionCode"))) {
            request.setAttribute("permissionCode", PropertyReader.getValue("error.require", "Permission Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("permissionName"))) {
            request.setAttribute("permissionName", PropertyReader.getValue("error.require", "Permission Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("moduleName"))) {
            request.setAttribute("moduleName", PropertyReader.getValue("error.require", "Module Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("permissionStatus"))) {
            request.setAttribute("permissionStatus", PropertyReader.getValue("error.require", "Permission Status"));
            pass = false;
        }

        return pass;
    }

    // =========================================================
    // POPULATE BEAN
    // =========================================================
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        PermissionBean bean = new PermissionBean();

        bean.setPermissionId(DataUtility.getLong(request.getParameter("permissionId")));
        bean.setPermissionCode(DataUtility.getString(request.getParameter("permissionCode")));
        bean.setPermissionName(DataUtility.getString(request.getParameter("permissionName")));
        bean.setModuleName(DataUtility.getString(request.getParameter("moduleName")));
        bean.setPermissionStatus(DataUtility.getString(request.getParameter("permissionStatus")));

        populateDTO(bean, request);

        return bean;
    }

    // =========================================================
    // DO GET
    // =========================================================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("permissionId"));

        PermissionModel model = new PermissionModel();

        if (id > 0) {
            try {
                PermissionBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);
    }

    // =========================================================
    // DO POST
    // =========================================================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        PermissionModel model = new PermissionModel();

        long id = DataUtility.getLong(request.getParameter("permissionId"));

        if (OP_SAVE.equalsIgnoreCase(op)) {
            PermissionBean bean = (PermissionBean) populateBean(request);
            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Permission added successfully", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Permission Code already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleExceptionDB(getView(), request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {
            PermissionBean bean = (PermissionBean) populateBean(request);
            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Permission updated successfully", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Permission Code already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleExceptionDB(getView(), request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.PERMISSION_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.PERMISSION_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    // =========================================================
    // GET VIEW
    // =========================================================
    @Override
    protected String getView() {
        return ORSView.PERMISSION_VIEW;
    }

}