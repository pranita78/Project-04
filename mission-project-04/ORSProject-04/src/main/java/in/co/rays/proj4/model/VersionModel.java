package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.VersionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class VersionModel {

    // ================= NEXT PK =================
    public static Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_version");
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
    public long add(VersionBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_version values (?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getVersionNumber());
            pstmt.setString(3, bean.getReleaseNotes());
            pstmt.setDate(4, new Date(bean.getReleaseDate().getTime()));
            pstmt.setString(5, bean.getVersionStatus());
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
                throw new ApplicationException("Rollback Exception");
            }
            throw new ApplicationException("Exception in add Version");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    // ================= UPDATE =================
    public void update(VersionBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_version set version_number=?, release_notes=?, release_date=?, version_status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getVersionNumber());
            pstmt.setString(2, bean.getReleaseNotes());
            pstmt.setDate(3, new Date(bean.getReleaseDate().getTime()));
            pstmt.setString(4, bean.getVersionStatus());
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
                throw new ApplicationException("Rollback Exception");
            }
            throw new ApplicationException("Exception in update Version");
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

            PreparedStatement pstmt = conn.prepareStatement("delete from st_version where id=?");
            pstmt.setLong(1, id);
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in delete Version");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ================= FIND BY PK =================
    public VersionBean findByPk(long id) throws ApplicationException {

        VersionBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from st_version where id=?");
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new VersionBean();
                bean.setId(rs.getLong(1));
                bean.setVersionNumber(rs.getString(2));
                bean.setReleaseNotes(rs.getString(3));
                bean.setReleaseDate(rs.getDate(4));
                bean.setVersionStatus(rs.getString(5));
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

    // ================= LIST =================
    public List<VersionBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    public List<VersionBean> search(VersionBean bean, int pageNo, int pageSize) throws ApplicationException {

        Connection conn = null;
        List<VersionBean> list = new ArrayList<VersionBean>();

        StringBuffer sql = new StringBuffer("select * from st_version where 1=1 ");

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }

            if (bean.getVersionNumber() != null && bean.getVersionNumber().length() > 0) {
                sql.append(" and version_number like '" + bean.getVersionNumber() + "%'");
            }

            if (bean.getReleaseNotes() != null && bean.getReleaseNotes().length() > 0) {
                sql.append(" and release_notes like '" + bean.getReleaseNotes() + "%'");
            }

            if (bean.getReleaseDate() != null) {
                sql.append(" and release_date like '" 
                        + new java.sql.Date(bean.getReleaseDate().getTime()) + "%'");
            }

            if (bean.getVersionStatus() != null && bean.getVersionStatus().length() > 0) {
                sql.append(" and version_status like '" + bean.getVersionStatus() + "%'");
            }
        }

        // Pagination
        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        System.out.println("SQL => " + sql);

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new VersionBean();

                bean.setId(rs.getLong(1));
                bean.setVersionNumber(rs.getString(2));
                bean.setReleaseNotes(rs.getString(3));
                bean.setReleaseDate(rs.getDate(4));
                bean.setVersionStatus(rs.getString(5));
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
            throw new ApplicationException("Exception in search Version");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}