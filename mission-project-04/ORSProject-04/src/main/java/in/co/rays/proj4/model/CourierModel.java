package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CourierBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class CourierModel {

    // ================= NEXT PK =================
    public static Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_courier");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    // ================= ADD =================
    public long add(CourierBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_courier values (?,?,?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getTrackingNumber());
            pstmt.setString(3, bean.getSenderName());
            pstmt.setString(4, bean.getReceiverName());
            pstmt.setDate(5, new java.sql.Date(bean.getDispatchDate().getTime()));
            pstmt.setDate(6, bean.getDeliveryDate() != null ?
                    new java.sql.Date(bean.getDeliveryDate().getTime()) : null);
            pstmt.setString(7, bean.getDeliveryStatus());
            pstmt.setString(8, bean.getCreatedBy());
            pstmt.setString(9, bean.getModifiedBy());
            pstmt.setTimestamp(10, bean.getCreatedDatetime());
            pstmt.setTimestamp(11, bean.getModifiedDatetime());

            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback Exception");
            }
            throw new ApplicationException("Exception in add Courier");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    // ================= UPDATE =================
    public void update(CourierBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_courier set tracking_number=?, sender_name=?, receiver_name=?, dispatch_date=?, delivery_date=?, delivery_status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getTrackingNumber());
            pstmt.setString(2, bean.getSenderName());
            pstmt.setString(3, bean.getReceiverName());
            pstmt.setDate(4, new java.sql.Date(bean.getDispatchDate().getTime()));
            pstmt.setDate(5, bean.getDeliveryDate() != null ?
                    new java.sql.Date(bean.getDeliveryDate().getTime()) : null);
            pstmt.setString(6, bean.getDeliveryStatus());
            pstmt.setString(7, bean.getCreatedBy());
            pstmt.setString(8, bean.getModifiedBy());
            pstmt.setTimestamp(9, bean.getCreatedDatetime());
            pstmt.setTimestamp(10, bean.getModifiedDatetime());
            pstmt.setLong(11, bean.getId());

            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback Exception");
            }
            throw new ApplicationException("Exception in update Courier");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ================= DELETE =================
    public void delete(long id) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "delete from st_courier where id=?");

            pstmt.setLong(1, id);
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in delete Courier");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ================= FIND BY PK =================
    public CourierBean findByPk(long id) throws ApplicationException {

        CourierBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_courier where id=?");

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new CourierBean();

                bean.setId(rs.getLong(1));
                bean.setTrackingNumber(rs.getString(2));
                bean.setSenderName(rs.getString(3));
                bean.setReceiverName(rs.getString(4));
                bean.setDispatchDate(rs.getDate(5));
                bean.setDeliveryDate(rs.getDate(6));
                bean.setDeliveryStatus(rs.getString(7));
                bean.setCreatedBy(rs.getString(8));
                bean.setModifiedBy(rs.getString(9));
                bean.setCreatedDatetime(rs.getTimestamp(10));
                bean.setModifiedDatetime(rs.getTimestamp(11));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPk Courier");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ================= LIST =================
    public List<CourierBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    // ================= search =================
    public List<CourierBean> search(CourierBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        Connection conn = null;

        StringBuffer sql = new StringBuffer("select * from st_courier where 1=1 ");

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" and id =" + bean.getId());
            }

            if (bean.getTrackingNumber() != null && bean.getTrackingNumber().length() > 0) {
                sql.append(" and tracking_number like '" + bean.getTrackingNumber() + "%'");
            }

            if (bean.getSenderName() != null && bean.getSenderName().length() > 0) {
                sql.append(" and sender_name like '" + bean.getSenderName() + "%'");
            }

            if (bean.getReceiverName() != null && bean.getReceiverName().length() > 0) {
                sql.append(" and receiver_name like '" + bean.getReceiverName() + "%'");
            }

            if (bean.getDispatchDate() != null) {
                sql.append(" and dispatch_date like '" 
                    + new java.sql.Date(bean.getDispatchDate().getTime()) + "%'");
            }

            if (bean.getDeliveryDate() != null) {
                sql.append(" and delivery_date like '" 
                    + new java.sql.Date(bean.getDeliveryDate().getTime()) + "%'");
            }

            if (bean.getDeliveryStatus() != null && bean.getDeliveryStatus().length() > 0) {
                sql.append(" and delivery_status like '" + bean.getDeliveryStatus() + "%'");
            }

            if (bean.getCreatedBy() != null && bean.getCreatedBy().length() > 0) {
                sql.append(" and created_by like '" + bean.getCreatedBy() + "%'");
            }

            if (bean.getModifiedBy() != null && bean.getModifiedBy().length() > 0) {
                sql.append(" and modified_by like '" + bean.getModifiedBy() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        System.out.println("sql => " + sql);

        List<CourierBean> list = new ArrayList<CourierBean>();

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new CourierBean();

                bean.setId(rs.getLong(1));
                bean.setTrackingNumber(rs.getString(2));
                bean.setSenderName(rs.getString(3));
                bean.setReceiverName(rs.getString(4));
                bean.setDispatchDate(rs.getDate(5));
                bean.setDeliveryDate(rs.getDate(6));
                bean.setDeliveryStatus(rs.getString(7));
                bean.setCreatedBy(rs.getString(8));
                bean.setModifiedBy(rs.getString(9));
                bean.setCreatedDatetime(rs.getTimestamp(10));
                bean.setModifiedDatetime(rs.getTimestamp(11));

                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in search Courier");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
    
}