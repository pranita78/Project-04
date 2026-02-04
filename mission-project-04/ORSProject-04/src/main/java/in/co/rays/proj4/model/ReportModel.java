package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ReportBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ReportModel {

    /**
     * Get next primary key
     */
    public static Integer nextPk() throws DatabaseException {
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM st_report");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new DatabaseException("Exception in getting PK of Report");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk + 1;
    }

    /**
     * Add a new report
     */
    public long add(ReportBean bean) throws ApplicationException, DuplicateRecordException {
        Connection conn = null;
        int pk = 0;

        // Check duplicate report name
        if (findByName(bean.getReportName()) != null) {
            throw new DuplicateRecordException("Report Name already exists");
        }

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO st_report VALUES (?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getReportName());
            pstmt.setDate(3, new java.sql.Date(bean.getGeneratedDate().getTime()));
            pstmt.setString(4, bean.getGeneratedBy());
            pstmt.setString(5, bean.getReportStatus());
            
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Add rollback exception: " + ex.getMessage());
            }
            throw new ApplicationException("Exception in adding Report");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    /**
     * Update report
     */
    public void update(ReportBean bean) throws ApplicationException, DuplicateRecordException {
        Connection conn = null;

        // Check duplicate report name
        ReportBean existBean = findByName(bean.getReportName());
        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Report Name already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE st_report SET report_name=?, generated_date=?, generated_by=?, report_status=?, created_by=?, modified_by=? WHERE id=?");
            pstmt.setString(1, bean.getReportName());
            pstmt.setDate(2, new java.sql.Date(bean.getGeneratedDate().getTime()));
            pstmt.setString(3, bean.getGeneratedBy());
            pstmt.setString(4, bean.getReportStatus());
            pstmt.setLong(7, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Update rollback exception: " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Report");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Delete report
     */
    public void delete(long id) throws ApplicationException {
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM st_report WHERE id=?");
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Delete rollback exception: " + ex.getMessage());
            }
            throw new ApplicationException("Exception in deleting Report");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Find report by PK
     */
    public ReportBean findByPk(long id) throws ApplicationException {
        Connection conn = null;
        ReportBean bean = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_report WHERE id=?");
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                bean = new ReportBean();
                bean.setId(rs.getLong("id"));
                bean.setReportName(rs.getString("report_name"));
                bean.setGeneratedDate(rs.getDate("generated_date"));
                bean.setGeneratedBy(rs.getString("generated_by"));
                bean.setReportStatus(rs.getString("report_status"));
               
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Report by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Find report by name
     */
    public ReportBean findByName(String name) throws ApplicationException {
        Connection conn = null;
        ReportBean bean = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_report WHERE report_name=?");
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                bean = new ReportBean();
                bean.setId(rs.getLong("id"));
                bean.setReportName(rs.getString("report_name"));
                bean.setGeneratedDate(rs.getDate("generated_date"));
                bean.setGeneratedBy(rs.getString("generated_by"));
                bean.setReportStatus(rs.getString("report_status"));
                
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Report by name");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Search reports with pagination
     */
    public List<ReportBean> search(ReportBean bean, int pageNo, int pageSize) throws ApplicationException {
        Connection conn = null;
        List<ReportBean> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT * FROM st_report WHERE 1=1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" AND id=" + bean.getId());
            }
            if (bean.getReportName() != null && bean.getReportName().length() > 0) {
                sql.append(" AND report_name LIKE '" + bean.getReportName() + "%'");
            }
            if (bean.getGeneratedBy() != null && bean.getGeneratedBy().length() > 0) {
                sql.append(" AND generated_by LIKE '" + bean.getGeneratedBy() + "%'");
            }
            if (bean.getReportStatus() != null && bean.getReportStatus().length() > 0) {
                sql.append(" AND report_status LIKE '" + bean.getReportStatus() + "%'");
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
                bean = new ReportBean();
                bean.setId(rs.getLong("id"));
                bean.setReportName(rs.getString("report_name"));
                bean.setGeneratedDate(rs.getDate("generated_date"));
                bean.setGeneratedBy(rs.getString("generated_by"));
                bean.setReportStatus(rs.getString("report_status"));
                 
                list.add(bean);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in searching Report");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    /**
     * Get all reports
     */
    public List<ReportBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }
}
