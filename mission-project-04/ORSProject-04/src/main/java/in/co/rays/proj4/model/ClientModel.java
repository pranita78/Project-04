package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ClientBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ClientModel {

    public static Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_client");
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

    public long add(ClientBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_client values (?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getClientName());
            pstmt.setString(3, bean.getAddress());
            pstmt.setString(4, bean.getPhone());
            pstmt.setString(5, bean.getPriortiy());
            pstmt.setString(6, bean.getCreatedBy());
            pstmt.setString(7, bean.getModifiedBy());
            pstmt.setTimestamp(8, bean.getCreatedDatetime());

            int i = pstmt.executeUpdate();
            System.out.println("Data Added => " + i);

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback Exception");
            }
            throw new ApplicationException("Exception in add Client");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    public void update(ClientBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_client set client_name=?, address=?, phone=?, priority=?, created_by=?, modified_by=?, created_datetime=? where id=?");

            pstmt.setString(1, bean.getClientName());
            pstmt.setString(2, bean.getAddress());
            pstmt.setString(3, bean.getPhone());
            pstmt.setString(4, bean.getPriortiy());
            pstmt.setString(5, bean.getCreatedBy());
            pstmt.setString(6, bean.getModifiedBy());
            pstmt.setTimestamp(7, bean.getCreatedDatetime());
            pstmt.setLong(8, bean.getId());

            int i = pstmt.executeUpdate();
            System.out.println("Data Updated => " + i);

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback Exception");
            }
            throw new ApplicationException("Exception in update Client");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public void delete(long id) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("delete from st_client where id=?");
            pstmt.setLong(1, id);

            int i = pstmt.executeUpdate();
            System.out.println("Data Deleted => " + i);

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback Exception");
            }
            throw new ApplicationException("Exception in delete Client");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public ClientBean findByPk(long id) throws ApplicationException {

        ClientBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from st_client where id=?");
            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new ClientBean();
                bean.setId(rs.getLong(1));
                bean.setClientName(rs.getString(2));
                bean.setAddress(rs.getString(3));
                bean.setPhone(rs.getString(4));
                bean.setPriortiy(rs.getNString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in get Client by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }
    public List<ClientBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    public List<ClientBean> search(ClientBean bean, int pageNo, int pageSize) throws ApplicationException {

        Connection conn = null;

        StringBuffer sql = new StringBuffer("select * from st_client where 1=1 ");

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" and id =" + bean.getId());
            }

            if (bean.getClientName() != null && bean.getClientName().length() > 0) {
                sql.append(" and client_name like '" + bean.getClientName() + "%'");
            }

            if (bean.getAddress() != null && bean.getAddress().length() > 0) {
                sql.append(" and address like '" + bean.getAddress() + "%'");
            }

            if (bean.getPhone() != null && bean.getPhone().length() > 0) {
                sql.append(" and phone like '" + bean.getPhone() + "%'");
            }

            if (bean.getPriortiy() != null && bean.getPriortiy().length() > 0) {
                sql.append(" and priority like '" + bean.getPriortiy() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        System.out.println("sql => " + sql);

        List<ClientBean> list = new ArrayList<ClientBean>();

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new ClientBean();

                bean.setId(rs.getLong(1));
                bean.setClientName(rs.getString(2));
                bean.setAddress(rs.getString(3));
                bean.setPhone(rs.getString(4));
                bean.setPriortiy(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));

                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in search Client");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}