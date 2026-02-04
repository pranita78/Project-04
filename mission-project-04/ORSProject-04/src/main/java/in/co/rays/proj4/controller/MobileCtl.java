package in.co.rays.proj4.controller;

import java.io.IOException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.MobileBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.MobileModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "MobileCtl", urlPatterns = { "/ctl/MobileCtl" })
public class MobileCtl extends BaseCtl {

	Logger log = Logger.getLogger(MobileCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("BrandName"))) {
			request.setAttribute("BrandName",
					PropertyReader.getValue("error.require", "Brand"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("model"))) {
			request.setAttribute("model",
					PropertyReader.getValue("error.require", "model"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("price"))) {
			request.setAttribute("price",
					PropertyReader.getValue("error.require", "price"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("storage"))) {
			request.setAttribute("storage",
					PropertyReader.getValue("error.require", "storage"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("loanDate"))) {
			request.setAttribute("loanDate",
					PropertyReader.getValue("error.require", "loanDate"));
			pass = false;
		}


		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		MobileBean bean = new MobileBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setBrand(DataUtility.getString(request.getParameter("BrandName")));
		bean.setModel(DataUtility.getString(request.getParameter("model")));
		bean.setPrice(DataUtility.getDouble(request.getParameter("price")));
		bean.setStorage(DataUtility.getLong(request.getParameter("storage")));
		bean.setLoanDate(DataUtility.getDate(request.getParameter("loanDate")));
	

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));
		MobileModel model = new MobileModel();

		if (id > 0) {
			try {
				MobileBean bean = model.findByPk(id);
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
		long id = DataUtility.getLong(request.getParameter("id"));

		MobileModel model = new MobileModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			MobileBean bean = (MobileBean) populateBean(request);
			try {
				model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Mobile brand  added successfully", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleExceptionDB(getView(), request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			MobileBean bean = (MobileBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Mobile brand updated successfully", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleExceptionDB(getView(), request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MOBILE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MOBILE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.MOBILE_VIEW;
	}
}
