package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ShiftBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ShiftModel {

    // ===================== NEXT PK =====================
    public static Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_shift");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception in getting PK in Shift");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    // ===================== ADD =====================
    public long add(ShiftBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        ShiftBean existBean = findByCode(bean.getShiftCode());
        if (existBean != null) {
            throw new DuplicateRecordException("Shift Code already exists");
        }

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_shift values (?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getShiftCode());
            pstmt.setString(3, bean.getShiftName());
            pstmt.setDate(4, new java.sql.Date(bean.getStartTime().getTime()));
            pstmt.setDate(5, new java.sql.Date(bean.getEndTime().getTime()) );
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
                throw new ApplicationException("Add rollback exception");
            }
            throw new ApplicationException("Exception in adding Shift");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    // ===================== UPDATE =====================
    public void update(ShiftBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        ShiftBean existBean = findByCode(bean.getShiftCode());
        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Shift Code already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_shift set shift_code=?, shift_name=?, start_time=?, end_time=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getShiftCode());
            pstmt.setString(2, bean.getShiftName());
            pstmt.setDate(3, new java.sql.Date(bean.getStartTime().getTime()));
            pstmt.setDate(4,new java.sql.Date(bean.getStartTime().getTime()));
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
                throw new ApplicationException("Update rollback exception");
            }
            throw new ApplicationException("Exception in updating Shift");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ===================== DELETE =====================
    public void delete(long id) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("delete from st_shift where id=?");
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
            throw new ApplicationException("Exception in deleting Shift");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ===================== FIND BY PK =====================
    public ShiftBean findByPk(long id) throws ApplicationException {

        ShiftBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from st_shift where id=?");
            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new ShiftBean();
                bean.setId(rs.getLong(1));
                bean.setShiftCode(rs.getString(2));
                bean.setShiftName(rs.getString(3));
                bean.setStartTime(rs.getTimestamp(4));
                bean.setEndTime(rs.getTimestamp(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Shift by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ===================== FIND BY CODE =====================
    public ShiftBean findByCode(String code) throws ApplicationException {

        ShiftBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from st_shift where shift_code=?");
            pstmt.setString(1, code);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new ShiftBean();
                bean.setId(rs.getLong(1));
                bean.setShiftCode(rs.getString(2));
                bean.setShiftName(rs.getString(3));
                bean.setStartTime(rs.getTimestamp(4));
                bean.setEndTime(rs.getTimestamp(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Shift by Code");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ===================== LIST =====================
    public List<ShiftBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    // ===================== SEARCH =====================
    public List<ShiftBean> search(ShiftBean bean, int pageNo, int pageSize) throws ApplicationException {

        Connection conn = null;
        StringBuffer sql = new StringBuffer("select * from st_shift where 1=1 ");
        List<ShiftBean> list = new ArrayList<ShiftBean>();

        if (bean != null) {

            if (bean.getShiftCode() != null && bean.getShiftCode().length() > 0) {
                sql.append(" and shift_code like '" + bean.getShiftCode() + "%'");
            }

            if (bean.getShiftName() != null && bean.getShiftName().length() > 0) {
                sql.append(" and shift_name like '" + bean.getShiftName() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new ShiftBean();
                bean.setId(rs.getLong(1));
                bean.setShiftCode(rs.getString(2));
                bean.setShiftName(rs.getString(3));
                bean.setStartTime(rs.getTimestamp(4));
                bean.setEndTime(rs.getTimestamp(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));

                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in searching Shift");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}