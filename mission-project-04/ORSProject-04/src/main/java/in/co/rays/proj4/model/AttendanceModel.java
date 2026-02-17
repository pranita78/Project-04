/**
 * @author Pranita Gayakward
 *
 */
package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.AttendanceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class AttendanceModel {

	// ---------- Next PK ----------
	public static Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"select max(Id) from st_attendance");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new DatabaseException("Exception in getting PK of Attendance");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	// ---------- Add ----------
	public long add(AttendanceBean bean)
			throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_attendance values (?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getPersonName());
			pstmt.setDate(3, new java.sql.Date(bean.getAttendanceDate().getTime()));
			pstmt.setString(4, bean.getAttendanceStatus());
			pstmt.setString(5, bean.getRemarks());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());

			int i = pstmt.executeUpdate();
			System.out.println("Attendance Added => " + i);

			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in add Attendance");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// ---------- Update ----------
	public void update(AttendanceBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_attendance set personName=?, attendanceDate=?, attendanceStatus=?, remarks=?, "
							+ "createdBy=?, modifiedBy=?, createdDatetime=?, modifiedDatetime=? where attendanceId=?");

			pstmt.setString(1, bean.getPersonName());
			pstmt.setDate(3, new java.sql.Date(bean.getAttendanceDate().getTime()));

			pstmt.setString(3, bean.getAttendanceStatus());
			pstmt.setString(4, bean.getRemarks());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());

			int i = pstmt.executeUpdate();
			System.out.println("Attendance Updated => " + i);

			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in update Attendance");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ---------- Delete ----------
	public void delete(long id) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt =
					conn.prepareStatement("delete from st_attendance where attendanceId=?");
			pstmt.setLong(1, id);

			int i = pstmt.executeUpdate();
			System.out.println("Attendance Deleted => " + i);

			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in delete Attendance");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ---------- Find By PK ----------
	public AttendanceBean findByPk(long id) throws ApplicationException {

		AttendanceBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt =
					conn.prepareStatement("select * from st_attendance where attendanceId=?");
			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new AttendanceBean();
				bean.setId(rs.getLong(1));
				bean.setPersonName(rs.getString(2));
				bean.setAttendanceDate(rs.getDate("attendanceDate"));
				bean.setAttendanceStatus(rs.getString(4));
				bean.setRemarks(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in find Attendance by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ---------- List ----------
	public List<AttendanceBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	// ---------- Search ----------
	public List<AttendanceBean> search(AttendanceBean bean, int pageNo, int pageSize)
			throws ApplicationException {

		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_attendance where 1=1 ");

		if (bean != null) {

			if (bean.getPersonName() != null && bean.getPersonName().length() > 0) {
				sql.append("and personName like '" + bean.getPersonName() + "%'");
			}

			if (bean.getAttendanceStatus() != null && bean.getAttendanceStatus().length() > 0) {
				sql.append(" and attendanceStatus like '" + bean.getAttendanceStatus() + "%'");
			}

			if (bean.getAttendanceDate() != null) {
				sql.append(" and attendanceDate='" + bean.getAttendanceDate() + "'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println("sql => " + sql);

		List<AttendanceBean> list = new ArrayList<>();

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new AttendanceBean();
				bean.setId(rs.getLong(1));
				bean.setPersonName(rs.getString(2));
				bean.setAttendanceDate(rs.getDate(3));

				bean.setAttendanceStatus(rs.getString(4));
				bean.setRemarks(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search Attendance");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}
