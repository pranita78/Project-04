package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.SupplierBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class SupplierModel {

    public static Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_supplier");
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

    public long add(SupplierBean bean) throws ApplicationException {

        Connection conn = null;
        int pk = 0;

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_supplier values (?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getName());
            pstmt.setString(3, bean.getCategory());
            pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
            pstmt.setInt(5, bean.getPaymentTerm());
            pstmt.setString(6, bean.getCreatedBy());
            pstmt.setString(7, bean.getModifiedBy());
            pstmt.setTimestamp(8, bean.getCreatedDatetime());
            pstmt.setTimestamp(9, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback error");
            }
            throw new ApplicationException("Exception in add Supplier");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    public void update(SupplierBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_supplier set name=?, category=?, dob=?, payment_term=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getName());
            pstmt.setString(2, bean.getCategory());
            pstmt.setDate(3, new Date(bean.getDob().getTime()));
            pstmt.setInt(4, bean.getPaymentTerm());
            pstmt.setString(5, bean.getCreatedBy());
            pstmt.setString(6, bean.getModifiedBy());
            pstmt.setTimestamp(7, bean.getCreatedDatetime());
            pstmt.setTimestamp(8, bean.getModifiedDatetime());
            pstmt.setLong(9, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback error");
            }
            throw new ApplicationException("Exception in update Supplier");
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
                    "delete from st_supplier where id=?");

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
            throw new ApplicationException("Exception in delete Supplier");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public SupplierBean findByPk(long id) throws ApplicationException {

        SupplierBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(
                    "select * from st_supplier where id=?");

            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = new SupplierBean();

                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setCategory(rs.getString(3));
                bean.setDob(rs.getDate(4));
                bean.setPaymentTerm(rs.getInt(5));
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
    
    public List<SupplierBean> search(SupplierBean bean, int pageNo, int pageSize)
    		throws ApplicationException {

    		
    		List<SupplierBean> list = new ArrayList<>();
    		Connection conn = null;

    		StringBuffer sql = new StringBuffer("select * from st_supplier where 1=1");

    		if (bean != null) {

    		    if (bean.getName() != null && bean.getName().length() > 0) {
    		        sql.append(" and name like '" + bean.getName() + "%'");
    		    }

    		    if (bean.getCategory() != null && bean.getCategory().length() > 0) {
    		        sql.append(" and category like '" + bean.getCategory() + "%'");
    		    }
    		}

    		// pagination
    		if (pageSize > 0) {
    		    pageNo = (pageNo - 1) * pageSize;
    		    sql.append(" limit " + pageNo + "," + pageSize);
    		}

    		try {
    		    conn = JDBCDataSource.getConnection();

    		    PreparedStatement pstmt = conn.prepareStatement(sql.toString());
    		    ResultSet rs = pstmt.executeQuery();

    		    while (rs.next()) {

    		        SupplierBean sbean = new SupplierBean();

    		        sbean.setId(rs.getLong(1));
    		        sbean.setName(rs.getString(2));
    		        sbean.setCategory(rs.getString(3));
    		        sbean.setDob(rs.getDate(4));
    		        sbean.setPaymentTerm(rs.getInt(5));
    		        sbean.setCreatedBy(rs.getString(6));
    		        sbean.setModifiedBy(rs.getString(7));
    		        sbean.setCreatedDatetime(rs.getTimestamp(8));
    		        sbean.setModifiedDatetime(rs.getTimestamp(9));

    		        list.add(sbean);
    		    }

    		    rs.close();
    		    pstmt.close();

    		} catch (Exception e) {
    		    throw new ApplicationException("Exception in search Supplier");

    		} finally {
    		    JDBCDataSource.closeConnection(conn);
    		}

    		return list;

    		}

    		public List<SupplierBean> list() throws ApplicationException {
    		return search(null, 0, 0);
    		}
}