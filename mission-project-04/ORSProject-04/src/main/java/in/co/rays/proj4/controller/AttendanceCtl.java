package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.AttendanceBean;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.AttendanceModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "AttendanceCtl", urlPatterns = { "/ctl/AttendanceCtl" })
public class AttendanceCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(AttendanceCtl.class);

	// ---------- Validation ----------
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("personName"))) {
			request.setAttribute("personName",
					PropertyReader.getValue("error.require", "Person Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("personName"))) {
			request.setAttribute("personName", "Invalid Person Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("attendanceDate"))) {
			request.setAttribute("attendanceDate",
					PropertyReader.getValue("error.require", "Attendance Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("attendanceDate"))) {
			request.setAttribute("attendanceDate",
					PropertyReader.getValue("error.date", "Attendance Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("attendanceStatus"))) {
			request.setAttribute("attendanceStatus",
					PropertyReader.getValue("error.require", "Attendance Status"));
			pass = false;
		}

		return pass;
	}

	// ---------- Populate Bean ----------
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		AttendanceBean bean = new AttendanceBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setPersonName(DataUtility.getString(request.getParameter("personName")));
		bean.setAttendanceDate(DataUtility.getDate(request.getParameter("attendanceDate")));
		bean.setAttendanceStatus(DataUtility.getString(request.getParameter("attendanceStatus")));
		bean.setRemarks(DataUtility.getString(request.getParameter("remarks")));

		populateDTO(bean, request);

		return bean;
	}

	// ---------- GET ----------
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));
		AttendanceModel model = new AttendanceModel();

		if (id > 0) {
			try {
				AttendanceBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	// ---------- POST ----------
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		AttendanceModel model = new AttendanceModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			AttendanceBean bean = (AttendanceBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Attendance added successfully", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleExceptionDB(getView(), request, response);
				return;
			}  catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Attendance already exists", request);
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			AttendanceBean bean = (AttendanceBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Attendance updated successfully", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleExceptionDB(getView(), request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ATTENDANCE__LIST_CTL, request, response);
			return;
			

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ATTENDANCE__CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	// ---------- View ----------
	@Override
	protected String getView() {
		return ORSView.ATTENDANCE_VIEW;
	}
}
