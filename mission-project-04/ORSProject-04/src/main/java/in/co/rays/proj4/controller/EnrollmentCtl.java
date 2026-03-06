package in.co.rays.proj4.controller;

import java.io.IOException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.EnrollmentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.EnrollmentModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "EnrollmentCtl", urlPatterns = { "/ctl/EnrollmentCtl" })
public class EnrollmentCtl extends BaseCtl {

    Logger log = Logger.getLogger(EnrollmentCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("enrollmentCode"))) {
            request.setAttribute("enrollmentCode",
                    PropertyReader.getValue("error.require", "Enrollment Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("studentName"))) {
            request.setAttribute("studentName",
                    PropertyReader.getValue("error.require", "Student Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("studentName"))) {
            request.setAttribute("studentName", "Invalid Student Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("courseName"))) {
            request.setAttribute("courseName",
                    PropertyReader.getValue("error.require", "Course Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("enrollmentDate"))) {
            request.setAttribute("enrollmentDate",
                    PropertyReader.getValue("error.require", "Enrollment Date"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("enrollmentDate"))) {
            request.setAttribute("enrollmentDate",
                    PropertyReader.getValue("error.date", "Enrollment Date"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        EnrollmentBean bean = new EnrollmentBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setEnrollmentCode(DataUtility.getString(request.getParameter("enrollmentCode")));
        bean.setStudentName(DataUtility.getString(request.getParameter("studentName")));
        bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
        bean.setEnrollmentDate(DataUtility.getDate(request.getParameter("enrollmentDate")));

        populateDTO(bean, request);

        return bean;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        EnrollmentModel model = new EnrollmentModel();

        if (id > 0) {
            try {
                EnrollmentBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        EnrollmentModel model = new EnrollmentModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            EnrollmentBean bean = (EnrollmentBean) populateBean(request);

            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Enrollment added successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Enrollment Code already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleExceptionDB(getView(), request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            EnrollmentBean bean = (EnrollmentBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Enrollment updated successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Enrollment Code already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleExceptionDB(getView(), request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.ENROLLMENT_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.ENROLLMENT_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.ENROLLMENT_VIEW;
    }
}