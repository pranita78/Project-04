package in.co.rays.proj4.controller;

import java.io.IOException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.TrainingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.TrainingModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "TrainingCtl", urlPatterns = { "/ctl/TrainingCtl" })
public class TrainingCtl extends BaseCtl {

    Logger log = Logger.getLogger(TrainingCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("trainingCode"))) {
            request.setAttribute("trainingCode",
                    PropertyReader.getValue("error.require", "Training Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("trainingName"))) {
            request.setAttribute("trainingName",
                    PropertyReader.getValue("error.require", "Training Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("trainerName"))) {
            request.setAttribute("trainerName",
                    PropertyReader.getValue("error.require", "Trainer Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("trainingDate"))) {
            request.setAttribute("trainingDate",
                    PropertyReader.getValue("error.require", "Training Date"));
            pass = false;

        } else if (!DataValidator.isDate(request.getParameter("trainingDate"))) {
            request.setAttribute("trainingDate",
                    PropertyReader.getValue("error.date", "Training Date"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("trainingStatus"))) {
            request.setAttribute("trainingStatus",
                    PropertyReader.getValue("error.require", "Training Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        TrainingBean bean = new TrainingBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setTrainingCode(DataUtility.getString(request.getParameter("trainingCode")));
        bean.setTrainingName(DataUtility.getString(request.getParameter("trainingName")));
        bean.setTrainerName(DataUtility.getString(request.getParameter("trainerName")));
        bean.setTrainingDate(DataUtility.getDate(request.getParameter("trainingDate")));
        bean.setTrainingStatus(DataUtility.getString(request.getParameter("trainingStatus")));

        populateDTO(bean, request);

        return bean;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        TrainingModel model = new TrainingModel();

        if (id > 0) {
            try {
                TrainingBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);

            } catch (ApplicationException e) {
                log.error(e);
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
        TrainingModel model = new TrainingModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            TrainingBean bean = (TrainingBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                    ServletUtility.setSuccessMessage("Training updated successfully", request);
                } else {
                    model.add(bean);
                    ServletUtility.setSuccessMessage("Training added successfully", request);
                }

                ServletUtility.setBean(bean, request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Training Code already exists", request);

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleExceptionDB(getView(), request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.TRAINING_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.TRAINING_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.TRAINING_VIEW;
    
    }
}