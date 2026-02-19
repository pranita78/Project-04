package in.co.rays.proj4.controller;

import java.io.IOException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.VersionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.VersionModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "VersionCtl", urlPatterns = { "/ctl/VersionCtl" })
public class VersionCtl extends BaseCtl {

    Logger log = Logger.getLogger(VersionCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("versionNumber"))) {
            request.setAttribute("versionNumber",
                    PropertyReader.getValue("error.require", "Version Number"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("releaseNotes"))) {
            request.setAttribute("releaseNotes",
                    PropertyReader.getValue("error.require", "Release Notes"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("releaseDate"))) {
            request.setAttribute("releaseDate",
                    PropertyReader.getValue("error.require", "Release Date"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("releaseDate"))) {
            request.setAttribute("releaseDate",
                    PropertyReader.getValue("error.date", "Release Date"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("versionStatus"))) {
            request.setAttribute("versionStatus",
                    PropertyReader.getValue("error.require", "Version Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        VersionBean bean = new VersionBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setVersionNumber(DataUtility.getString(request.getParameter("versionNumber")));
        bean.setReleaseNotes(DataUtility.getString(request.getParameter("releaseNotes")));
        bean.setReleaseDate(DataUtility.getDate(request.getParameter("releaseDate")));
        bean.setVersionStatus(DataUtility.getString(request.getParameter("versionStatus")));

        populateDTO(bean, request);

        return bean;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        VersionModel model = new VersionModel();

        if (id > 0) {
            try {
                VersionBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        VersionModel model = new VersionModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            VersionBean bean = (VersionBean) populateBean(request);

            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Version added successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Version already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleExceptionDB(getView(), request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            VersionBean bean = (VersionBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Version updated successfully", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleExceptionDB(getView(), request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.VERSION_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.VERSION_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.VERSION_VIEW;
    }
}
