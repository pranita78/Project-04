package in.co.rays.proj4.controller;

import java.io.IOException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.ComplaintBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.ComplaintModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "ComplaintCtl", urlPatterns = { "/ctl/ComplaintCtl" })
public class ComplaintCtl extends BaseCtl {

    Logger log = Logger.getLogger(ComplaintCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("complaintCode"))) {
            request.setAttribute("complaintCode", PropertyReader.getValue("error.require", "Complaint Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("customerName"))) {
            request.setAttribute("customerName", PropertyReader.getValue("error.require", "Customer Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("customerName"))) {
            request.setAttribute("customerName", "Invalid Customer Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("complaintType"))) {
            request.setAttribute("complaintType", PropertyReader.getValue("error.require", "Complaint Type"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("complaintStatus"))) {
            request.setAttribute("complaintStatus", PropertyReader.getValue("error.require", "Complaint Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        ComplaintBean bean = new ComplaintBean();

        bean.setComplaintId(DataUtility.getLong(request.getParameter("id")));
        bean.setComplaintCode(DataUtility.getString(request.getParameter("complaintCode")));
        bean.setCustomerName(DataUtility.getString(request.getParameter("customerName")));
        bean.setComplaintType(DataUtility.getString(request.getParameter("complaintType")));
        bean.setComplaintStatus(DataUtility.getString(request.getParameter("complaintStatus")));

        populateDTO(bean, request);

        return bean;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        ComplaintModel model = new ComplaintModel();

        if (id > 0) {
            try {
                ComplaintBean bean = model.findByPk(id);
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

        ComplaintModel model = new ComplaintModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            ComplaintBean bean = (ComplaintBean) populateBean(request);

            try {
            	try {
					model.add(bean);
				} catch (DuplicateRecordException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Complaint added successfully", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleExceptionDB(getView(), request, response);
                return;
             
			}

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            ComplaintBean bean = (ComplaintBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Complaint updated successfully", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleExceptionDB(getView(), request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.COMPLAINT_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.COMPLAINT_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.COMPLAINT_VIEW;
    }
}