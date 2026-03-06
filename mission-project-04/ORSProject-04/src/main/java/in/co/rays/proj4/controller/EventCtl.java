package in.co.rays.proj4.controller;

import java.io.IOException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.EventBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.EventModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "EventCtl", urlPatterns = { "/ctl/EventCtl" })
public class EventCtl extends BaseCtl {

    Logger log = Logger.getLogger(EventCtl.class);

    @Override
    protected void preload(HttpServletRequest request) {
        // Event me koi dropdown nahi hai to preload empty
    }

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("eventCode"))) {
            request.setAttribute("eventCode", 
                PropertyReader.getValue("error.require", "Event Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("eventName"))) {
            request.setAttribute("eventName", 
                PropertyReader.getValue("error.require", "Event Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("organizer"))) {
            request.setAttribute("organizer", 
                PropertyReader.getValue("error.require", "Organizer"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("eventDate"))) {
            request.setAttribute("eventDate", 
                PropertyReader.getValue("error.require", "Event Date"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("eventDate"))) {
            request.setAttribute("eventDate", 
                PropertyReader.getValue("error.date", "Event Date"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        EventBean bean = new EventBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setEventCode(DataUtility.getString(request.getParameter("eventCode")));
        bean.setEventName(DataUtility.getString(request.getParameter("eventName")));
        bean.setOrganizer(DataUtility.getString(request.getParameter("organizer")));
        bean.setEventDate(DataUtility.getDate(request.getParameter("eventDate")));

        populateDTO(bean, request);

        return bean;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));
        EventModel model = new EventModel();

        if (id > 0) {
            try {
                EventBean bean = model.findByPk(id);
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

        EventModel model = new EventModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            EventBean bean = (EventBean) populateBean(request);

            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Event added successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Event Code already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleExceptionDB(getView(), request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            EventBean bean = (EventBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Event updated successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Event Code already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleExceptionDB(getView(), request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.EVENT_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.EVENT_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.EVENT_VIEW;
    }
}