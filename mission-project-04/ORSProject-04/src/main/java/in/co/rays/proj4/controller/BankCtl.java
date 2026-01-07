package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BankBean;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.BankModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * @author Pranita Gayakward
 */
@WebServlet(name = "BankCtl", urlPatterns = { "/ctl/BankCtl" })
public class BankCtl extends BaseCtl {

    /* ================= Validation ================= */

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("bankName"))) {
            request.setAttribute("bankName",
                    PropertyReader.getValue("error.require", "Bank Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("branchName"))) {
            request.setAttribute("branchName",
                    PropertyReader.getValue("error.require", "Branch Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("accountHolderName"))) {
            request.setAttribute("accountHolderName",
                    PropertyReader.getValue("error.require", "Account Holder Name"));
            pass = false;
        }

        String mobile = request.getParameter("mobileNo");

        if (DataValidator.isNull(mobile)) {
            request.setAttribute("mobileNo",
                    PropertyReader.getValue("error.require", "Mobile No"));
            pass = false;
        } 
        // Indian mobile number validation (10 digits, starts with 6-9)
        else if (!mobile.matches("^[6-9][0-9]{9}$")) {
            request.setAttribute("mobileNo",
                    PropertyReader.getValue("error.invalid", "Mobile No"));
            pass = false;
        }

        return pass;
    }

    /* ================= Populate Bean ================= */

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        BankBean bean = new BankBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setBankName(DataUtility.getString(request.getParameter("bankName")));
        bean.setBranchName(DataUtility.getString(request.getParameter("branchName")));
        bean.setAccountHolderName(
                DataUtility.getString(request.getParameter("accountHolderName")));
        bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

        populateDTO(bean, request);

        return bean;
    }

    /* ================= GET ================= */

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));
        BankModel model = new BankModel();

        if (id > 0) {
            try {
                BankBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);
    }

    /* ================= POST ================= */

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        BankModel model = new BankModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            BankBean bean = (BankBean) populateBean(request);

            try {
                model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage(
                        "Data is successfully saved", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage(
                        "Mobile No already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            BankBean bean = (BankBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage(
                        "Data is successfully updated", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage(
                        "Mobile No already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.BANK_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.BANK_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /* ================= View ================= */

    @Override
    protected String getView() {
        return ORSView.BANK_VIEW;
    }
}
