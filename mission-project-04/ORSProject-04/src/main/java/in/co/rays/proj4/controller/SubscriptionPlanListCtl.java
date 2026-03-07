package in.co.rays.proj4.controller;

import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.SubscriptionPlanBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.SubscriptionPlanModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "SubscriptionPlanListCtl", urlPatterns = { "/ctl/SubscriptionPlanListCtl" })
public class SubscriptionPlanListCtl extends BaseCtl {

	Logger log = Logger.getLogger(SubscriptionPlanListCtl.class);

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		SubscriptionPlanBean bean = new SubscriptionPlanBean();

		bean.setPlanName(DataUtility.getString(request.getParameter("planName")));
		bean.setPrice(DataUtility.getDouble(request.getParameter("price")));
		bean.setValidityDays(DataUtility.getString(request.getParameter("validityDays")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		SubscriptionPlanBean bean = (SubscriptionPlanBean) populateBean(request);
		SubscriptionPlanModel model = new SubscriptionPlanModel();

		try {

			List<SubscriptionPlanBean> list = model.search(bean, pageNo, pageSize);
			List<SubscriptionPlanBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {

			ServletUtility.handleExceptionDBList(getView(), bean, pageNo, pageSize, request, response);
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		SubscriptionPlanBean bean = (SubscriptionPlanBean) populateBean(request);
		SubscriptionPlanModel model = new SubscriptionPlanModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op)
					|| OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.SUBSCRIPTIONPLAN_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					SubscriptionPlanBean deletebean = new SubscriptionPlanBean();

					for (String id : ids) {

						deletebean.setId(DataUtility.getLong(id));
						model.delete(deletebean.getId());

					}

					ServletUtility.setSuccessMessage("Subscription Plan deleted successfully", request);

				} else {

					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.SUBSCRIPTIONPLAN_LIST_CTL, request, response);
				return;

			} else if (OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.SUBSCRIPTIONPLAN_LIST_CTL, request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {

				ServletUtility.setErrorMessage("No record found ", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {

			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	@Override
	protected String getView() {
		return ORSView.SUBSCRIPTIONPLAN_LIST_VIEW;
	}
}