package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.AccountBean;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.AccountModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "AccountCtl", urlPatterns = { "/ctl/AccountCtl" })
public class AccountCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Saving", "Saving");
		map.put("Current", "Current");
		request.setAttribute("map", map);
	}

	@Override
protected boolean validate(HttpServletRequest request) {

    String op = request.getParameter("operation");

    // RESET aur CANCEL par validation skip
    if (OP_RESET.equalsIgnoreCase(op) || OP_CANCEL.equalsIgnoreCase(op)) {
        return true;
    }

    boolean pass = true;

    String accountNo = request.getParameter("accountNo");

    if (DataValidator.isNull(accountNo)) {
        request.setAttribute("accountNo",
                PropertyReader.getValue("error.require", "Account Number"));
        pass = false;

    } else if (!accountNo.trim().toUpperCase().matches(
            "^(?:[A-Z]{5}[0-9]{11}|[A-Z]{4}[0-9][A-Z][0-9]{10}|[A-Z]{3}[0-9]{2}[A-Z]{2}[0-9]{9}|[A-Z]{2}[0-9]{3}[A-Z]{3}[0-9]{8}|[A-Z]{1}[0-9]{4}[A-Z]{4}[0-9]{7}|[0-9]{5}[A-Z]{5}[0-9]{6}|[0-9]{6}[A-Z]{5}[0-9]{5}|[0-9]{7}[A-Z]{5}[0-9]{4}|[0-9]{8}[A-Z]{5}[0-9]{3}|[0-9]{9}[A-Z]{5}[0-9]{2}|[0-9]{10}[A-Z]{5}[0-9]{1}|[0-9]{11}[A-Z]{5})$")) {

        request.setAttribute("accountNo",
                "Account No must be 16 characters (uppercase letters & digits)");
        pass = false;
    }

    // baaki fields
    if (DataValidator.isNull(request.getParameter("accountType"))) {
        request.setAttribute("accountType",
                PropertyReader.getValue("error.require", "Account Type"));
        pass = false;
    }

    if (DataValidator.isNull(request.getParameter("bankName"))) {
        request.setAttribute("bankName",
                PropertyReader.getValue("error.require", "Bank Name"));
        pass = false;
    }

    if (DataValidator.isNull(request.getParameter("balance"))) {
        request.setAttribute("balance",
                PropertyReader.getValue("error.require", "Balance"));
        pass = false;
    }

    return pass;
}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));
		AccountModel model = new AccountModel();

		if (id > 0) {
			try {
				AccountBean bean = model.findByPk(id);
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
		AccountModel model = new AccountModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			AccountBean bean = (AccountBean) populateBean(request);

			try {
				model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully saved", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Account No already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			AccountBean bean = (AccountBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully updated", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Account No already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ACCOUNT_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ACCOUNT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.ACCOUNT_VIEW;
	}
}

