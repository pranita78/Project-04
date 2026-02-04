/**
 * @author Pranita Gayakward
 *
 */
package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.MobileBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class MobileModel {

    /* ================= PK ================= */

    public static Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement("SELECT MAX(ID) FROM st_mobile");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new DatabaseException("Exception in getting PK in Mobile");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk + 1;
    }

    /* ================= ADD ================= */

    public long add(MobileBean bean) throws ApplicationException {

        Connection conn = null;
        int pk = 0;

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO st_mobile VALUES (?,?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getBrand());
            pstmt.setString(3, bean.getModel());
            pstmt.setDouble(4, bean.getPrice());
            pstmt.setLong(5, bean.getStorage());
            pstmt.setDate(6, new java.sql.Date(bean.getLoanDate().getTime()));

             
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Add rollback exception");
            }
            throw new ApplicationException("Exception in add Mobile");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    /* ================= UPDATE ================= */

    public void update(MobileBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE st_mobile SET BRAND=?, MODEL=?, PRICE=?, STORAGE=?, LOAN_DATE=?, "
              + "CREATED_BY=?, MODIFIED_BY=?, CREATED_DATETIME=?, MODIFIED_DATETIME=? WHERE ID=?");

            pstmt.setString(1, bean.getBrand());
            pstmt.setString(2, bean.getModel());
            pstmt.setDouble(3, bean.getPrice());
            pstmt.setLong(4, bean.getStorage());
            pstmt.setDate(5, new java.sql.Date(bean.getLoanDate().getTime()));
            pstmt.setString(6, bean.getCreatedBy());
            pstmt.setString(7, bean.getModifiedBy());
            pstmt.setTimestamp(8, bean.getCreatedDatetime());
            pstmt.setTimestamp(9, bean.getModifiedDatetime());
            pstmt.setLong(10, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Update rollback exception");
            }
            throw new ApplicationException("Exception in update Mobile");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /* ================= DELETE ================= */

    public void delete(long id) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                conn.prepareStatement("DELETE FROM st_mobile WHERE ID=?");
            pstmt.setLong(1, id);
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Delete rollback exception");
            }
            throw new ApplicationException("Exception in delete Mobile");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /* ================= FIND BY PK ================= */

    public MobileBean findByPk(long id) throws ApplicationException {

        MobileBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement("SELECT * FROM st_mobile WHERE ID=?");
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new MobileBean();
                bean.setId(rs.getLong(1));
                bean.setBrand(rs.getString(2));
                bean.setModel(rs.getString(3));
                bean.setPrice(rs.getDouble(4));
                bean.setStorage(rs.getLong(5));
                bean.setLoanDate(rs.getDate(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDatetime(rs.getTimestamp(9));
                bean.setModifiedDatetime(rs.getTimestamp(10));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPk Mobile");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /* ================= LIST ================= */

    public List<MobileBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /* ================= SEARCH ================= */

    public List<MobileBean> search(MobileBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        Connection conn = null;
        List<MobileBean> list = new ArrayList<>();

        StringBuffer sql =
            new StringBuffer("SELECT * FROM st_mobile WHERE 1=1 ");

        if (bean != null) {

            if (bean.getBrand() != null && bean.getBrand().length() > 0) {
                sql.append(" AND BRAND LIKE '" + bean.getBrand() + "%'");
            }

            if (bean.getModel() != null && bean.getModel().length() > 0) {
                sql.append(" AND MODEL LIKE '" + bean.getModel() + "%'");
            }

            if (bean.getPrice() != null && bean.getPrice() > 0) {
                sql.append(" AND PRICE = " + bean.getPrice());
            }

            if (bean.getStorage() != null && bean.getStorage() > 0) {
                sql.append(" AND STORAGE = " + bean.getStorage());
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" LIMIT " + pageNo + "," + pageSize);
        }

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new MobileBean();
                bean.setId(rs.getLong(1));
                bean.setBrand(rs.getString(2));
                bean.setModel(rs.getString(3));
                bean.setPrice(rs.getDouble(4));
                bean.setStorage(rs.getLong(5));
                bean.setLoanDate(rs.getDate(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDatetime(rs.getTimestamp(9));
                bean.setModifiedDatetime(rs.getTimestamp(10));
                list.add(bean);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in search Mobile");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return list;
    }
}
