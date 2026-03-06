/**
 * @author Pranita Gayakwad
 *
 */
package in.co.rays.proj4.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.InventoryBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class InventoryModel {

    public static Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_inventory");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception : Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk + 1;
    }

    public long add(InventoryBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_inventory values (?,?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getItemCode());
            pstmt.setString(3, bean.getItemName());
            pstmt.setInt(4, bean.getQuantity());
            pstmt.setBigDecimal(5, bean.getPrice());
            pstmt.setString(6, bean.getItemStatus());
            pstmt.setString(7, bean.getCreatedBy());
            pstmt.setString(8, bean.getModifiedBy());
            pstmt.setTimestamp(9, bean.getCreatedDatetime());
            pstmt.setTimestamp(10, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : add rollback");
            }
            throw new ApplicationException("Exception in add Inventory");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    public void update(InventoryBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_inventory set item_code=?, item_name=?, quantity=?, price=?, item_status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getItemCode());
            pstmt.setString(2, bean.getItemName());
            pstmt.setInt(3, bean.getQuantity());
            pstmt.setBigDecimal(4, bean.getPrice());
            pstmt.setString(5, bean.getItemStatus());
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
                throw new ApplicationException("Exception : update rollback");
            }
            throw new ApplicationException("Exception in update Inventory");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public void delete(long id) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("delete from st_inventory where id=?");
            pstmt.setLong(1, id);
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in delete Inventory");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public InventoryBean findByPk(long id) throws ApplicationException {

        InventoryBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from st_inventory where id=?");
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new InventoryBean();
                bean.setId(rs.getLong(1));
                bean.setItemCode(rs.getString(2));
                bean.setItemName(rs.getString(3));
                bean.setQuantity(rs.getInt(4));
                bean.setPrice(rs.getBigDecimal(5));
                bean.setItemStatus(rs.getString(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDatetime(rs.getTimestamp(9));
                bean.setModifiedDatetime(rs.getTimestamp(10));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPk Inventory");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public List<InventoryBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    public List<InventoryBean> search(InventoryBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        Connection conn = null;
        StringBuffer sql = new StringBuffer("select * from st_inventory where 1=1 ");

        if (bean != null) {

            if (bean.getItemCode() != null && bean.getItemCode().length() > 0) {
                sql.append(" and item_code like '" + bean.getItemCode() + "%'");
            }

            if (bean.getItemName() != null && bean.getItemName().length() > 0) {
                sql.append(" and item_name like '" + bean.getItemName() + "%'");
            }

            if (bean.getItemStatus() != null && bean.getItemStatus().length() > 0) {
                sql.append(" and item_status like '" + bean.getItemStatus() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        List<InventoryBean> list = new ArrayList<>();

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new InventoryBean();
                bean.setId(rs.getLong(1));
                bean.setItemCode(rs.getString(2));
                bean.setItemName(rs.getString(3));
                bean.setQuantity(rs.getInt(4));
                bean.setPrice(rs.getBigDecimal(5));
                bean.setItemStatus(rs.getString(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDatetime(rs.getTimestamp(9));
                bean.setModifiedDatetime(rs.getTimestamp(10));
                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in search Inventory");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}