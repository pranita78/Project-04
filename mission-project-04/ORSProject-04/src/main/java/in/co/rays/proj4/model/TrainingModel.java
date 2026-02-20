package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.TrainingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class TrainingModel {

    public static Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_training");
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

    public long add(TrainingBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_training values (?,?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getTrainingCode());
            pstmt.setString(3, bean.getTrainingName());
            pstmt.setString(4, bean.getTrainerName());
            pstmt.setDate(5, new java.sql.Date(bean.getTrainingDate().getTime()));
            pstmt.setString(6, bean.getTrainingStatus());
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
                throw new ApplicationException("Rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in add Training");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    public void update(TrainingBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_training set training_code=?, training_name=?, trainer_name=?, training_date=?, training_status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getTrainingCode());
            pstmt.setString(2, bean.getTrainingName());
            pstmt.setString(3, bean.getTrainerName());
            pstmt.setDate(4, new java.sql.Date(bean.getTrainingDate().getTime()));
            pstmt.setString(5, bean.getTrainingStatus());
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
                throw new ApplicationException("Rollback Exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in update Training");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public void delete(long id) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("delete from st_training where id=?");
            pstmt.setLong(1, id);
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in delete Training");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public TrainingBean findByPk(long id) throws ApplicationException {

        TrainingBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from st_training where id=?");
            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new TrainingBean();
                bean.setId(rs.getLong(1));
                bean.setTrainingCode(rs.getString(2));
                bean.setTrainingName(rs.getString(3));
                bean.setTrainerName(rs.getString(4));
                bean.setTrainingDate(rs.getDate(5));
                bean.setTrainingStatus(rs.getString(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDatetime(rs.getTimestamp(9));
                bean.setModifiedDatetime(rs.getTimestamp(10));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPk Training");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public List<TrainingBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    public List<TrainingBean> search(TrainingBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        Connection conn = null;

        StringBuffer sql = new StringBuffer("select * from st_training where 1=1 ");

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" and id =" + bean.getId());
            }

            if (bean.getTrainingCode() != null && bean.getTrainingCode().length() > 0) {
                sql.append(" and training_code like '" + bean.getTrainingCode() + "%'");
            }

            if (bean.getTrainingName() != null && bean.getTrainingName().length() > 0) {
                sql.append(" and training_name like '" + bean.getTrainingName() + "%'");
            }

            if (bean.getTrainerName() != null && bean.getTrainerName().length() > 0) {
                sql.append(" and trainer_name like '" + bean.getTrainerName() + "%'");
            }

            if (bean.getTrainingDate() != null) {
                sql.append(" and training_date = '"
                        + new java.sql.Date(bean.getTrainingDate().getTime()) + "'");
            }

            if (bean.getTrainingStatus() != null && bean.getTrainingStatus().length() > 0) {
                sql.append(" and training_status like '" + bean.getTrainingStatus() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        System.out.println("sql => " + sql);

        List<TrainingBean> list = new ArrayList<TrainingBean>();

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new TrainingBean();

                bean.setId(rs.getLong(1));
                bean.setTrainingCode(rs.getString(2));
                bean.setTrainingName(rs.getString(3));
                bean.setTrainerName(rs.getString(4));
                bean.setTrainingDate(rs.getDate(5));
                bean.setTrainingStatus(rs.getString(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDatetime(rs.getTimestamp(9));
                bean.setModifiedDatetime(rs.getTimestamp(10));

                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in search Training");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}