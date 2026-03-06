package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.EventBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class EventModel {

    public static Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_event");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception in getting PK in Event");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    public long add(EventBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        EventBean existBean = findByEventCode(bean.getEventCode());

        if (existBean != null) {
            throw new DuplicateRecordException("Event Code already exists");
        }

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_event values (?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getEventCode());
            pstmt.setString(3, bean.getEventName());
            pstmt.setString(4, bean.getOrganizer());
            pstmt.setDate(5, new java.sql.Date(bean.getEventDate().getTime()));
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
                throw new ApplicationException("Add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in add Event");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    public void update(EventBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        EventBean existBean = findByEventCode(bean.getEventCode());

        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Event Code already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_event set event_code=?, event_name=?, organizer=?, event_date=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getEventCode());
            pstmt.setString(2, bean.getEventName());
            pstmt.setString(3, bean.getOrganizer());
            pstmt.setDate(4, new java.sql.Date(bean.getEventDate().getTime()));
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
                throw new ApplicationException("Update rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in update Event");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public void delete(long id) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("delete from st_event where id=?");
            pstmt.setLong(1, id);
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in delete Event");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public EventBean findByPk(long id) throws ApplicationException {

        EventBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from st_event where id=?");
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new EventBean();
                bean.setId(rs.getLong(1));
                bean.setEventCode(rs.getString(2));
                bean.setEventName(rs.getString(3));
                bean.setOrganizer(rs.getString(4));
                bean.setEventDate(rs.getDate(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPk Event");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public EventBean findByEventCode(String code) throws ApplicationException {

        EventBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from st_event where event_code=?");
            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new EventBean();
                bean.setId(rs.getLong(1));
                bean.setEventCode(rs.getString(2));
                bean.setEventName(rs.getString(3));
                bean.setOrganizer(rs.getString(4));
                bean.setEventDate(rs.getDate(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByEventCode");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }
    public List<EventBean> list() throws ApplicationException {
    	return search(null, 0, 0);
    }

    public List<EventBean> search(EventBean bean, int pageNo, int pageSize) 
    		throws ApplicationException {

    	Connection conn = null;

    	StringBuffer sql = new StringBuffer("select * from st_event where 1=1 ");

    	if (bean != null) {

    		if (bean.getId() > 0) {
    			sql.append(" and id =" + bean.getId());
    		}

    		if (bean.getEventCode() != null && bean.getEventCode().length() > 0) {
    			sql.append(" and event_code like '" + bean.getEventCode() + "%'");
    		}

    		if (bean.getEventName() != null && bean.getEventName().length() > 0) {
    			sql.append(" and event_name like '" + bean.getEventName() + "%'");
    		}

    		if (bean.getOrganizer() != null && bean.getOrganizer().length() > 0) {
    			sql.append(" and organizer like '" + bean.getOrganizer() + "%'");
    		}
    		
    		System.out.println(sql.toString());

    		if (bean.getEventDate() != null) {
    			sql.append(" and event_date like '" 
    				+ new java.sql.Date(bean.getEventDate().getTime()) + "%'");
    		}
    	}

    	if (pageSize > 0) {
    		pageNo = (pageNo - 1) * pageSize;
    		sql.append(" limit " + pageNo + "," + pageSize);
    	}

    	System.out.println("sql => " + sql);

    	List<EventBean> list = new ArrayList<EventBean>();

    	try {
    		conn = JDBCDataSource.getConnection();
    		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
    		ResultSet rs = pstmt.executeQuery();

    		while (rs.next()) {
    			bean = new EventBean();
    			bean.setId(rs.getLong(1));
    			bean.setEventCode(rs.getString(2));
    			bean.setEventName(rs.getString(3));
    			bean.setOrganizer(rs.getString(4));
    			bean.setEventDate(rs.getDate(5));
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
    		throw new ApplicationException("Exception in search Event");

    	} finally {
    		JDBCDataSource.closeConnection(conn);
    	}

    	return list;
    }
}