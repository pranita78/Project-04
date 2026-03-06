package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.EnrollmentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class EnrollmentModel {

    public static Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_enrollment");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception in getting PK in Enrollment");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    public long add(EnrollmentBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        EnrollmentBean existBean = findByEnrollmentCode(bean.getEnrollmentCode());

        if (existBean != null) {
            throw new DuplicateRecordException("Enrollment Code already exists");
        }

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_enrollment values (?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getEnrollmentCode());
            pstmt.setString(3, bean.getStudentName());
            pstmt.setString(4, bean.getCourseName());
            pstmt.setDate(5, new java.sql.Date(bean.getEnrollmentDate().getTime()));
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
                throw new ApplicationException("Add Rollback Exception");
            }
            throw new ApplicationException("Exception in add Enrollment");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    public void update(EnrollmentBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_enrollment set enrollment_code=?, student_name=?, course_name=?, enrollment_date=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getEnrollmentCode());
            pstmt.setString(2, bean.getStudentName());
            pstmt.setString(3, bean.getCourseName());
            pstmt.setDate(4, new java.sql.Date(bean.getEnrollmentDate().getTime()));
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
                throw new ApplicationException("Update Rollback Exception");
            }
            throw new ApplicationException("Exception in update Enrollment");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public void delete(long id) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("delete from st_enrollment where id=?");
            pstmt.setLong(1, id);
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Delete Rollback Exception");
            }
            throw new ApplicationException("Exception in delete Enrollment");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public EnrollmentBean findByPk(long id) throws ApplicationException {

        EnrollmentBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from st_enrollment where id=?");
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new EnrollmentBean();
                bean.setId(rs.getLong(1));
                bean.setEnrollmentCode(rs.getString(2));
                bean.setStudentName(rs.getString(3));
                bean.setCourseName(rs.getString(4));
                bean.setEnrollmentDate(rs.getDate(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPk Enrollment");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public EnrollmentBean findByEnrollmentCode(String code) throws ApplicationException {

        EnrollmentBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "select * from st_enrollment where enrollment_code=?");
            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new EnrollmentBean();
                bean.setId(rs.getLong(1));
                bean.setEnrollmentCode(rs.getString(2));
                bean.setStudentName(rs.getString(3));
                bean.setCourseName(rs.getString(4));
                bean.setEnrollmentDate(rs.getDate(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByEnrollmentCode");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public List<EnrollmentBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    public List<EnrollmentBean> search(EnrollmentBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        Connection conn = null;
        StringBuffer sql = new StringBuffer("select * from st_enrollment where 1=1 ");

        if (bean != null) {

            if (bean.getEnrollmentCode() != null && bean.getEnrollmentCode().length() > 0) {
                sql.append(" and enrollment_code like '" + bean.getEnrollmentCode() + "%'");
            }

            if (bean.getStudentName() != null && bean.getStudentName().length() > 0) {
                sql.append(" and student_name like '" + bean.getStudentName() + "%'");
            }

            if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
                sql.append(" and course_name like '" + bean.getCourseName() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        List<EnrollmentBean> list = new ArrayList<>();

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new EnrollmentBean();
                bean.setId(rs.getLong(1));
                bean.setEnrollmentCode(rs.getString(2));
                bean.setStudentName(rs.getString(3));
                bean.setCourseName(rs.getString(4));
                bean.setEnrollmentDate(rs.getDate(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in search Enrollment");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}