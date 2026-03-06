package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.DonationCampBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class DonationCampModel {

	public static Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(camp_id) from st_donationcamp");
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

	public long add(DonationCampBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {

			pk = nextPk();

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_donationcamp values (?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getCampName());
			pstmt.setDate(3, new java.sql.Date(bean.getCampDate().getTime()));
			pstmt.setString(4, bean.getOrganizer());
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
			}

			throw new ApplicationException("Exception in add DonationCamp");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(DonationCampBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(

					"update st_donationcamp set camp_name=?, camp_date=?, organizer=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where camp_id=?");

			pstmt.setString(1, bean.getCampName());
			pstmt.setDate(2, new java.sql.Date(bean.getCampDate().getTime()));
			pstmt.setString(3, bean.getOrganizer());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.setLong(8, bean.getCampId());

			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
			}

			throw new ApplicationException("Exception in update DonationCamp");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(long id) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_donationcamp where camp_id=?");

			pstmt.setLong(1, id);
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
			}

			throw new ApplicationException("Exception in delete DonationCamp");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public DonationCampBean findByPk(long id) throws ApplicationException {

		Connection conn = null;
		DonationCampBean bean = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"select * from st_donationcamp where camp_id=?");

			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new DonationCampBean();

				bean.setCampId(rs.getLong(1));
				bean.setCampName(rs.getString(2));
				bean.setCampDate(rs.getDate(3));
				bean.setOrganizer(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in getting DonationCamp by PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}


public List<DonationCampBean> list() throws ApplicationException {
	return search(null, 0, 0);
}
public List<DonationCampBean> search(DonationCampBean bean, int pageNo, int pageSize) throws ApplicationException {

	Connection conn = null;

	StringBuffer sql = new StringBuffer("select * from st_donationcamp where 1=1 ");

	if (bean != null) {


		if (bean.getCampName() != null && bean.getCampName().length() > 0) {
			sql.append(" and camp_name like '" + bean.getCampName() + "%'");
		}

		if (bean.getCampDate() != null) {
			sql.append(" and camp_date like '" + new java.sql.Date(bean.getCampDate().getTime()) + "%'");
		}

		if (bean.getOrganizer() != null && bean.getOrganizer().length() > 0) {
			sql.append(" and organizer like '" + bean.getOrganizer() + "%'");
		}

	}

	if (pageSize > 0) {
		pageNo = (pageNo - 1) * pageSize;
		sql.append(" limit " + pageNo + "," + pageSize);
	}

	System.out.println("sql => " + sql);

	List<DonationCampBean> list = new ArrayList<DonationCampBean>();

	try {

		conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new DonationCampBean();

			bean.setCampId(rs.getLong(1));
			bean.setCampName(rs.getString(2));
			bean.setCampDate(rs.getDate(3));
			bean.setOrganizer(rs.getString(4));
			bean.setCreatedBy(rs.getString(5));
			bean.setModifiedBy(rs.getString(6));
			bean.setCreatedDatetime(rs.getTimestamp(7));
			bean.setModifiedDatetime(rs.getTimestamp(8));

			list.add(bean);
		}

		rs.close();
		pstmt.close();

	} catch (Exception e) {
		e.printStackTrace();
		throw new ApplicationException("Exception in search DonationCamp");

	} finally {
		JDBCDataSource.closeConnection(conn);
	}

	return list;
}
}