package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ParkingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ParkingModel {

    public static Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("select max(parking_id) from st_parking");
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

    // ADD
    public long add(ParkingBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_parking values (?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getLocation());
            pstmt.setInt(3, bean.getCapacity());
            pstmt.setDouble(4, bean.getFee());
            pstmt.setString(5, bean.getCreatedBy());
            pstmt.setString(6, bean.getModifiedBy());
            pstmt.setTimestamp(7, bean.getCreatedDatetime());
            pstmt.setTimestamp(8, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();

            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback error " + ex.getMessage());
            }
            throw new ApplicationException("Exception in add Parking");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    // UPDATE
    public void update(ParkingBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_parking set location=?, capacity=?, fee=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where parking_id=?");

            pstmt.setString(1, bean.getLocation());
            pstmt.setInt(2, bean.getCapacity());
            pstmt.setDouble(3, bean.getFee());
            pstmt.setString(4, bean.getCreatedBy());
            pstmt.setString(5, bean.getModifiedBy());
            pstmt.setTimestamp(6, bean.getCreatedDatetime());
            pstmt.setTimestamp(7, bean.getModifiedDatetime());
            pstmt.setLong(8, bean.getParkingId());

            pstmt.executeUpdate();
            conn.commit();

            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback error " + ex.getMessage());
            }
            throw new ApplicationException("Exception in update Parking");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // DELETE
    public void delete(long id) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("delete from st_parking where parking_id=?");
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
            throw new ApplicationException("Exception in delete Parking");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // FIND BY PK
    public ParkingBean findByPk(long id) throws ApplicationException {

        ParkingBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(
                    "select * from st_parking where parking_id=?");

            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new ParkingBean();

                bean.setParkingId(rs.getLong(1));
                bean.setLocation(rs.getString(2));
                bean.setCapacity(rs.getInt(3));
                bean.setFee(rs.getDouble(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
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

    // LIST & SEARCH
    public List<ParkingBean> search(ParkingBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        Connection conn = null;

        StringBuffer sql = new StringBuffer("select * from st_parking where 1=1 ");

        if (bean != null) {

            if (bean.getParkingId() > 0) {
                sql.append(" and parking_id=" + bean.getParkingId());
            }

            if (bean.getLocation() != null && bean.getLocation().length() > 0) {
                sql.append(" and location like '" + bean.getLocation() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        List<ParkingBean> list = new ArrayList<>();

        try {
            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new ParkingBean();

                bean.setParkingId(rs.getLong(1));
                bean.setLocation(rs.getString(2));
                bean.setCapacity(rs.getInt(3));
                bean.setFee(rs.getDouble(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));

                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in search");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}