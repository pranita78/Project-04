package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.SubscriptionPlanBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.SubscriptionPlanModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "SubscriptionPlanCtl", urlPatterns = { "/ctl/SubscriptionPlanCtl" })
public class SubscriptionPlanCtl extends BaseCtl {

	Logger log = Logger.getLogger(SubscriptionPlanCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("planName"))) {
			request.setAttribute("planName",
					PropertyReader.getValue("error.require", "Plan Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("price"))) {
			request.setAttribute("price",
					PropertyReader.getValue("error.require", "Price"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("validityDays"))) {
			request.setAttribute("validityDays",
					PropertyReader.getValue("error.require", "Validity Days"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		SubscriptionPlanBean bean = new SubscriptionPlanBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setPlanName(DataUtility.getString(request.getParameter("planName")));
		bean.setPrice(DataUtility.getDouble(request.getParameter("price")));
		bean.setValidityDays(DataUtility.getString(request.getParameter("validityDays")));

		populateDTO(bean, request);

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		SubscriptionPlanModel model = new SubscriptionPlanModel();

		if (id > 0) {
			try {
				SubscriptionPlanBean bean = model.findByPk(id);
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

		SubscriptionPlanModel model = new SubscriptionPlanModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			SubscriptionPlanBean bean = (SubscriptionPlanBean) populateBean(request);

			try {

				long pk = model.add(bean);

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Subscription Plan Added Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Subscription Plan already exists", request);

			} catch (ApplicationException e) {

				e.printStackTrace();
				ServletUtility.handleExceptionDB(getView(), request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			SubscriptionPlanBean bean = (SubscriptionPlanBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Subscription Plan Updated Successfully", request);

			} catch (ApplicationException e) {

				e.printStackTrace();
				ServletUtility.handleExceptionDB(getView(), request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SUBSCRIPTIONPLAN_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SUBSCRIPTIONPLAN_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.SUBSCRIPTIONPLAN_VIEW;
	}
}