package in.co.rays.proj4.controller;

import java.io.IOException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.DonationCampBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.DonationCampModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "DonationCampCtl", urlPatterns = { "/ctl/DonationCampCtl" })

public class DonationCampCtl extends BaseCtl {

	Logger log = Logger.getLogger(DonationCampCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("campName"))) {
			request.setAttribute("campName", PropertyReader.getValue("error.require", "Camp Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("campDate"))) {
			request.setAttribute("campDate", PropertyReader.getValue("error.require", "Camp Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("campDate"))) {
			request.setAttribute("campDate", PropertyReader.getValue("error.date", "Camp Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("organizer"))) {
			request.setAttribute("organizer", PropertyReader.getValue("error.require", "Organizer"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		DonationCampBean bean = new DonationCampBean();

		bean.setCampId(DataUtility.getLong(request.getParameter("campId")));
		bean.setCampName(DataUtility.getString(request.getParameter("campName")));
		bean.setCampDate(DataUtility.getDate(request.getParameter("campDate")));
		bean.setOrganizer(DataUtility.getString(request.getParameter("organizer")));

		populateDTO(bean, request);

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("campId"));

		DonationCampModel model = new DonationCampModel();

		if (id > 0) {
			try {
				DonationCampBean bean = model.findByPk(id);
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

		DonationCampModel model = new DonationCampModel();

		long id = DataUtility.getLong(request.getParameter("campId"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			DonationCampBean bean = (DonationCampBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Donation Camp added successfully", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleExceptionDB(getView(), request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			DonationCampBean bean = (DonationCampBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Donation Camp updated successfully", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleExceptionDB(getView(), request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.DONATIONCAMP_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.DONATIONCAMP_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.DONATIONCAMP_VIEW;
	}

}