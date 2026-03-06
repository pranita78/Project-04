package in.co.rays.proj4.controller;

import java.io.IOException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.InventoryBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.InventoryModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "InventoryCtl", urlPatterns = { "/ctl/InventoryCtl" })
public class InventoryCtl extends BaseCtl {

	Logger log = Logger.getLogger(InventoryCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

	    boolean pass = true;

	    if (DataValidator.isNull(request.getParameter("itemCode"))) {
	        request.setAttribute("itemCode", "Item Code is required");
	        pass = false;
	    }

	    if (DataValidator.isNull(request.getParameter("itemName"))) {
	        request.setAttribute("itemName", "Item Name is required");
	        pass = false;
	    }

	    if (DataValidator.isNull(request.getParameter("quantity"))) {
	        request.setAttribute("quantity", "Quantity is required");
	        pass = false;
	    } else if (!DataValidator.isInteger(request.getParameter("quantity"))) {
	        request.setAttribute("quantity", "Quantity must be number");
	        pass = false;
	    }

	    if (DataValidator.isNull(request.getParameter("price"))) {
	        request.setAttribute("price", "Price is required");
	        pass = false;
	    } 

	    if (DataValidator.isNull(request.getParameter("itemStatus"))) {
	        request.setAttribute("itemStatus", "Item Status is required");
	        pass = false;
	    }

	    return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		InventoryBean bean = new InventoryBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setItemCode(DataUtility.getString(request.getParameter("itemCode")));
		bean.setItemName(DataUtility.getString(request.getParameter("itemName")));
		bean.setQuantity(DataUtility.getInt(request.getParameter("quantity")));
		bean.setPrice(DataUtility.getBigDecimal(request.getParameter("price")));
		bean.setItemStatus(DataUtility.getString(request.getParameter("itemStatus")));

		populateDTO(bean, request);

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		InventoryModel model = new InventoryModel();

		if (id > 0) {
			try {
				InventoryBean bean = model.findByPk(id);
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

		InventoryModel model = new InventoryModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			InventoryBean bean = (InventoryBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Inventory added successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Item Code already exists", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleExceptionDB(getView(), request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			InventoryBean bean = (InventoryBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Inventory updated successfully", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleExceptionDB(getView(), request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.INVENTORY_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.INVENTORY_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.INVENTORY_VIEW;
	}
}