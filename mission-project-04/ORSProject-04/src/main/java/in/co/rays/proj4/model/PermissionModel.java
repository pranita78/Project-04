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

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.PermissionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class PermissionModel {

    // =========================================================
    // NEXT PRIMARY KEY
    // =========================================================
    public static Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(permission_id) FROM st_permission");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = (rs.getInt(1));
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception : Exception in getting PK in Permission");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    // =========================================================
    // ADD
    // =========================================================
    public long add(PermissionBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        PermissionBean existBean = findByPermissionCode(bean.getPermissionCode());

        if (existBean != null) {
            throw new DuplicateRecordException("Permission Code already exists in Permission");
        }

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO st_permission VALUES (?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getPermissionCode());
            pstmt.setString(3, bean.getPermissionName());
            pstmt.setString(4, bean.getModuleName());
            pstmt.setString(5, bean.getPermissionStatus());
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
                throw new ApplicationException("Exception : add rollback exception" + ex.getMessage());
            }
            throw new ApplicationException("Exception in add Permission");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    // =========================================================
    // UPDATE
    // =========================================================
    public void update(PermissionBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        PermissionBean beanExist = findByPermissionCode(bean.getPermissionCode());

        if (beanExist != null && beanExist.getPermissionId() != bean.getPermissionId()) {
            throw new DuplicateRecordException("Permission Code already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE st_permission SET permission_code=?, permission_name=?, module_name=?, " +
                    "permission_status=?, created_by=?, modified_by=?, created_datetime=?, " +
                    "modified_datetime=? WHERE permission_id=?");

            pstmt.setString(1, bean.getPermissionCode());
            pstmt.setString(2, bean.getPermissionName());
            pstmt.setString(3, bean.getModuleName());
            pstmt.setString(4, bean.getPermissionStatus());
            pstmt.setString(5, bean.getCreatedBy());
            pstmt.setString(6, bean.getModifiedBy());
            pstmt.setTimestamp(7, bean.getCreatedDatetime());
            pstmt.setTimestamp(8, bean.getModifiedDatetime());
            pstmt.setLong(9, bean.getPermissionId());

            int i = pstmt.executeUpdate();
            System.out.println("Data Updated => " + i);

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception Update rollback exception" + ex.getMessage());
            }
            throw new ApplicationException("Exception in update Permission");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // =========================================================
    // DELETE
    // =========================================================
    public void delete(long id) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM st_permission WHERE permission_id=?");
            pstmt.setLong(1, id);
            int i = pstmt.executeUpdate();
            System.out.println("Data Deleted => " + i);

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback Exception" + ex.getMessage());
            }
            throw new ApplicationException("Exception : Exception in delete Permission");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // =========================================================
    // FIND BY PRIMARY KEY
    // =========================================================
    public PermissionBean findByPk(long id) throws ApplicationException {

        PermissionBean bean = null;
        Connection conn = null;

        StringBuffer sql = new StringBuffer("SELECT * FROM st_permission WHERE permission_id = ?");

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new PermissionBean();
                bean.setPermissionId(rs.getLong(1));
                bean.setPermissionCode(rs.getString(2));
                bean.setPermissionName(rs.getString(3));
                bean.setModuleName(rs.getString(4));
                bean.setPermissionStatus(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in getting PermissionByPk()");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // =========================================================
    // FIND BY PERMISSION CODE
    // =========================================================
    public PermissionBean findByPermissionCode(String permissionCode) throws ApplicationException {

        PermissionBean bean = null;
        Connection conn = null;

        StringBuffer sql = new StringBuffer("SELECT * FROM st_permission WHERE permission_code = ?");

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, permissionCode);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new PermissionBean();
                bean.setPermissionId(rs.getLong(1));
                bean.setPermissionCode(rs.getString(2));
                bean.setPermissionName(rs.getString(3));
                bean.setModuleName(rs.getString(4));
                bean.setPermissionStatus(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in getting Permission by PermissionCode");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // =========================================================
    // LIST (all records)
    // =========================================================
    public List<PermissionBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    // =========================================================
    // SEARCH (with pagination)
    // =========================================================
    public List<PermissionBean> search(PermissionBean bean, int pageNo, int pageSize) throws ApplicationException {

        Connection conn = null;

        StringBuffer sql = new StringBuffer("SELECT * FROM st_permission WHERE 1=1 ");

        if (bean != null) {

            if (bean.getPermissionId() != null && bean.getPermissionId() > 0) {
                sql.append(" AND permission_id = " + bean.getPermissionId());
            }

            if (bean.getPermissionCode() != null && bean.getPermissionCode().length() > 0) {
                sql.append(" AND permission_code LIKE '" + bean.getPermissionCode() + "%'");
            }

            if (bean.getPermissionName() != null && bean.getPermissionName().length() > 0) {
                sql.append(" AND permission_name LIKE '" + bean.getPermissionName() + "%'");
            }

            if (bean.getModuleName() != null && bean.getModuleName().length() > 0) {
                sql.append(" AND module_name LIKE '" + bean.getModuleName() + "%'");
            }

            if (bean.getPermissionStatus() != null && bean.getPermissionStatus().length() > 0) {
                sql.append(" AND permission_status LIKE '" + bean.getPermissionStatus() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" LIMIT " + pageNo + "," + pageSize);
        }

        System.out.println("sql => " + sql);

        List<PermissionBean> list = new ArrayList<PermissionBean>();

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new PermissionBean();
                bean.setPermissionId(rs.getLong(1));
                bean.setPermissionCode(rs.getString(2));
                bean.setPermissionName(rs.getString(3));
                bean.setModuleName(rs.getString(4));
                bean.setPermissionStatus(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
                list.add(bean);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in search Permission");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}
