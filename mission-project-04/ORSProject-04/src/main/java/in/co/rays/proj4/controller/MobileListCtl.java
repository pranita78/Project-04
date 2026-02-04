package in.co.rays.proj4.controller;

import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.MobileBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.MobileModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "MobileListCtl", urlPatterns = { "/ctl/MobileListCtl" })
public class MobileListCtl extends BaseCtl {

    Logger log = Logger.getLogger(MobileListCtl.class);

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        MobileBean bean = new MobileBean();
        bean.setBrand(DataUtility.getString(request.getParameter("BrandName")));
        return bean;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        MobileBean bean = (MobileBean) populateBean(request);
        MobileModel model = new MobileModel();

        try {
            List<MobileBean> list = model.search(bean, pageNo, pageSize);
            List<MobileBean> next = model.search(bean, pageNo + 1, pageSize);

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

        MobileBean bean = (MobileBean) populateBean(request);
        MobileModel model = new MobileModel();

        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");

        try {

            if ("Search".equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

                if ("Search".equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if ("Next".equalsIgnoreCase(op)) {
                    pageNo++;
                } else if ("Previous".equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            } else if ("New".equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.MOBILE_CTL, request, response);
                return;

            } else if ("Delete".equalsIgnoreCase(op)) {
                pageNo = 1;
                if (ids != null && ids.length > 0) {
                    MobileBean deleteBean = new MobileBean();
                    for (String id : ids) {
                        deleteBean.setId(DataUtility.getLong(id));
                        model.delete(deleteBean.getId());
                        ServletUtility.setSuccessMessage("Mobile Number deleted successfully", request);
                    }
                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if ("Reset".equalsIgnoreCase(op) || "Back".equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.MOBILE_LIST_CTL, request, response);
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
        return ORSView.MOBILE_LIST_VIEW;
    }
}
