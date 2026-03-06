package in.co.rays.proj4.controller;

import java.io.IOException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CourierBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourierModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "CourierCtl", urlPatterns = { "/ctl/CourierCtl" })
public class CourierCtl extends BaseCtl {

    Logger log = Logger.getLogger(CourierCtl.class);

    @Override
    protected void preload(HttpServletRequest request) {
        // Courier me koi dropdown nahi hai, isliye empty
    }

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("trackingNumber"))) {
            request.setAttribute("trackingNumber",
                    PropertyReader.getValue("error.require", "Tracking Number"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("senderName"))) {
            request.setAttribute("senderName",
                    PropertyReader.getValue("error.require", "Sender Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("receiverName"))) {
            request.setAttribute("receiverName",
                    PropertyReader.getValue("error.require", "Receiver Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("dispatchDate"))) {
            request.setAttribute("dispatchDate",
                    PropertyReader.getValue("error.require", "Dispatch Date"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("dispatchDate"))) {
            request.setAttribute("dispatchDate",
                    PropertyReader.getValue("error.date", "Dispatch Date"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("deliveryStatus"))) {
            request.setAttribute("deliveryStatus",
                    PropertyReader.getValue("error.require", "Delivery Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        CourierBean bean = new CourierBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setTrackingNumber(DataUtility.getString(request.getParameter("trackingNumber")));
        bean.setSenderName(DataUtility.getString(request.getParameter("senderName")));
        bean.setReceiverName(DataUtility.getString(request.getParameter("receiverName")));
        bean.setDispatchDate(DataUtility.getDate(request.getParameter("dispatchDate")));
        bean.setDeliveryDate(DataUtility.getDate(request.getParameter("deliveryDate")));
        bean.setDeliveryStatus(DataUtility.getString(request.getParameter("deliveryStatus")));

        populateDTO(bean, request);

        return bean;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        CourierModel model = new CourierModel();

        if (id > 0) {
            try {
                CourierBean bean = model.findByPk(id);
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

        CourierModel model = new CourierModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            CourierBean bean = (CourierBean) populateBean(request);

            try {
                model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Courier added successfully", request);

            } catch (ApplicationException | DuplicateRecordException e) {
                e.printStackTrace();
                ServletUtility.handleExceptionDB(getView(), request, response);
                return;
            
			}

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            CourierBean bean = (CourierBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Courier updated successfully", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleExceptionDB(getView(), request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.COURIER_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.COURIER_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.COURIER_VIEW;
    }
}