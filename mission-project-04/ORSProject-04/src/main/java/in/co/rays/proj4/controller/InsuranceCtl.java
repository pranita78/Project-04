package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.InsuranceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.InsuranceModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "InsuranceCtl", urlPatterns = { "/ctl/InsuranceCtl" })

public class InsuranceCtl extends BaseCtl {

	Logger log = Logger.getLogger(InsuranceCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("insuranceNumber"))) {
			request.setAttribute("insuranceNumber",
					PropertyReader.getValue("error.require", "Insurance Number"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("carId"))) {
			request.setAttribute("carId",
					PropertyReader.getValue("error.require", "Car Id"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("expiryDate"))) {
			request.setAttribute("expiryDate",
					PropertyReader.getValue("error.require", "Expiry Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("expiryDate"))) {
			request.setAttribute("expiryDate",
					PropertyReader.getValue("error.date", "Expiry Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("insuranceStatus"))) {
			request.setAttribute("insuranceStatus",
					PropertyReader.getValue("error.require", "Insurance Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		InsuranceBean bean = new InsuranceBean();

		bean.setInsuranceId(DataUtility.getLong(request.getParameter("insuranceId")));
		bean.setInsuranceNumber(DataUtility.getString(request.getParameter("insuranceNumber")));
		bean.setCarId(DataUtility.getLong(request.getParameter("carId")));
		bean.setExpiryDate(DataUtility.getDate(request.getParameter("expiryDate")));
		bean.setInsuranceStatus(DataUtility.getString(request.getParameter("insuranceStatus")));

		populateDTO(bean, request);

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("insuranceId"));

		InsuranceModel model = new InsuranceModel();

		if (id > 0) {
			try {
				InsuranceBean bean = model.findByPk(id);
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

		InsuranceModel model = new InsuranceModel();

		long id = DataUtility.getLong(request.getParameter("insuranceId"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			InsuranceBean bean = (InsuranceBean) populateBean(request);

			try {

				long pk = model.add(bean);

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Insurance added successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Insurance Number already exists", request);

			} catch (ApplicationException e) {

				e.printStackTrace();
				ServletUtility.handleExceptionDB(getView(), request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			InsuranceBean bean = (InsuranceBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Insurance updated successfully", request);

			} catch (ApplicationException e) {

				e.printStackTrace();
				ServletUtility.handleExceptionDB(getView(), request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.INSURANCE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.INSURANCE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected void preload(HttpServletRequest request) {

	    List<String> list = new ArrayList<String>();

	    list.add("Active");
	    list.add("Expired");
	    list.add("Pending");

	    request.setAttribute("statusList", list);
	}

	@Override
	protected String getView() {
		return ORSView.INSURANCE_VIEW;
	}

}