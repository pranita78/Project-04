package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ComplaintBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ComplaintModel {

    public static Integer nextPk() throws DatabaseException {
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(complaintId) from st_complaint");
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

    public long add(ComplaintBean bean) throws ApplicationException, DuplicateRecordException {
        Connection conn = null;
        int pk = 0;
        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_complaint values (?,?,?,?,?,?,?,?,?)");
            pstmt.setLong(1, pk);
            pstmt.setString(2, bean.getComplaintCode());
            pstmt.setString(3, bean.getCustomerName());
            pstmt.setString(4, bean.getComplaintType());
            pstmt.setString(5, bean.getComplaintStatus());
            pstmt.setString(6, bean.getCreatedBy());
            pstmt.setString(7, bean.getModifiedBy());
            pstmt.setTimestamp(8, bean.getCreatedDatetime());
            pstmt.setTimestamp(9, bean.getModifiedDatetime());
            int i = pstmt.executeUpdate();
            System.out.println("Data Added => " + i);
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback error " + ex.getMessage());
            }
            throw new ApplicationException("Exception in add Complaint");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    public void update(ComplaintBean bean) throws ApplicationException {
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_complaint set complaintCode=?, customerName=?, complaintType=?, "
                    + "complaintStatus=?, createdBy=?, modifiedBy=?, createdDatetime=?, "
                    + "modifiedDatetime=? where complaintId=?");
            pstmt.setString(1, bean.getComplaintCode());
            pstmt.setString(2, bean.getCustomerName());
            pstmt.setString(3, bean.getComplaintType());
            pstmt.setString(4, bean.getComplaintStatus());
            pstmt.setString(5, bean.getCreatedBy());
            pstmt.setString(6, bean.getModifiedBy());
            pstmt.setTimestamp(7, bean.getCreatedDatetime());
            pstmt.setTimestamp(8, bean.getModifiedDatetime());
            pstmt.setLong(9, bean.getComplaintId());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback error");
            }
            throw new ApplicationException("Exception in update Complaint");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public void delete(long id) throws ApplicationException {
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "delete from st_complaint where complaintId=?");
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback error");
            }
            throw new ApplicationException("Exception in delete");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public ComplaintBean findByPk(long id) throws ApplicationException {
        ComplaintBean bean = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "select * from st_complaint where complaintId=?");
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new ComplaintBean();
                bean.setComplaintId(rs.getLong(1));
                bean.setComplaintCode(rs.getString(2));
                bean.setCustomerName(rs.getString(3));
                bean.setComplaintType(rs.getString(4));
                bean.setComplaintStatus(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    public List search(ComplaintBean bean, int pageNo, int pageSize) throws ApplicationException {

        List list = new ArrayList();
        StringBuffer sql = new StringBuffer("SELECT * FROM st_complaint WHERE 1=1");

        if (bean != null) {

            // ✅ FIX — null check pehle (Long wrapper hai, primitive nahi)
            if (bean.getComplaintId() != null && bean.getComplaintId() > 0) {
                sql.append(" AND complaintId = " + bean.getComplaintId());
            }

            if (bean.getComplaintCode() != null && bean.getComplaintCode().length() > 0) {
                sql.append(" AND complaintCode LIKE '" + bean.getComplaintCode() + "%'");
            }

            if (bean.getCustomerName() != null && bean.getCustomerName().length() > 0) {
                sql.append(" AND customerName LIKE '" + bean.getCustomerName() + "%'");
            }

            if (bean.getComplaintType() != null && bean.getComplaintType().length() > 0) {
                sql.append(" AND complaintType LIKE '" + bean.getComplaintType() + "%'");
            }

            if (bean.getComplaintStatus() != null && bean.getComplaintStatus().length() > 0) {
                sql.append(" AND complaintStatus LIKE '" + bean.getComplaintStatus() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" LIMIT " + pageNo + ", " + pageSize);
        }

        // ✅ FIX — conn bahar declare karo
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ComplaintBean cb = new ComplaintBean();
                cb.setComplaintId(rs.getLong("complaintId"));
                cb.setComplaintCode(rs.getString("complaintCode"));
                cb.setCustomerName(rs.getString("customerName"));
                cb.setComplaintType(rs.getString("complaintType"));
                cb.setComplaintStatus(rs.getString("complaintStatus"));
                list.add(cb);
            }

            rs.close();
            pstmt.close(); // ✅ FIX

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in search Complaint");

        } finally {
            JDBCDataSource.closeConnection(conn); // ✅ FIX
        }

        return list;
    }
}