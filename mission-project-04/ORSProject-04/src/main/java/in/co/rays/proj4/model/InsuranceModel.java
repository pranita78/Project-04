package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.InsuranceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class InsuranceModel {

	public static Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(insuranceId) from st_insurance");
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

	public long add(InsuranceBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		try {

			pk = nextPk();

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_insurance values(?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getInsuranceNumber());
			pstmt.setLong(3, bean.getCarId());
			pstmt.setDate(4, new java.sql.Date(bean.getExpiryDate().getTime()));
			pstmt.setString(5, bean.getInsuranceStatus());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());

			int i = pstmt.executeUpdate();

			System.out.println("Data Added => " + i);

			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception");
			}

			throw new ApplicationException("Exception in add Insurance");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(InsuranceBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_insurance set insuranceNumber=?, carId=?, expiryDate=?, insuranceStatus=?, createdBy=?, modifiedBy=?, createdDatetime=?, modifiedDatetime=? where insuranceId=?");

			pstmt.setString(1, bean.getInsuranceNumber());
			pstmt.setLong(2, bean.getCarId());
			pstmt.setDate(3, new java.sql.Date(bean.getExpiryDate().getTime()));
			pstmt.setString(4, bean.getInsuranceStatus());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getInsuranceId());

			int i = pstmt.executeUpdate();

			System.out.println("Data Updated => " + i);

			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : update rollback exception");
			}

			throw new ApplicationException("Exception in update Insurance");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(long id) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_insurance where insuranceId=?");

			pstmt.setLong(1, id);

			int i = pstmt.executeUpdate();

			System.out.println("Data Deleted => " + i);

			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete rollback Exception");
			}

			throw new ApplicationException("Exception in delete Insurance");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public InsuranceBean findByPk(long id) throws ApplicationException {

		InsuranceBean bean = null;

		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_insurance where insuranceId=?");

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new InsuranceBean();

				bean.setInsuranceId(rs.getLong(1));
				bean.setInsuranceNumber(rs.getString(2));
				bean.setCarId(rs.getLong(3));
				bean.setExpiryDate(rs.getDate(4));
				bean.setInsuranceStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));

			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			e.printStackTrace();
			throw new ApplicationException("Exception in findByPk");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<InsuranceBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<InsuranceBean> search(InsuranceBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_insurance where 1=1 ");

		if (bean != null) {

			
			if (bean.getInsuranceNumber() != null && bean.getInsuranceNumber().length() > 0) {
				sql.append(" and insuranceNumber like '" + bean.getInsuranceNumber() + "%'");
			}

			if (bean.getCarId() > 0) {
				sql.append(" and carId =" + bean.getCarId());
			}

			if (bean.getExpiryDate() != null) {
				sql.append(" and expiryDate like '" + new java.sql.Date(bean.getExpiryDate().getTime()) + "%'");
			}

			if (bean.getInsuranceStatus() != null && bean.getInsuranceStatus().length() > 0) {
				sql.append(" and insuranceStatus like '" + bean.getInsuranceStatus() + "%'");
			}

		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println("sql => " + sql);

		List<InsuranceBean> list = new ArrayList<InsuranceBean>();

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new InsuranceBean();

				bean.setInsuranceId(rs.getLong(1));
				bean.setInsuranceNumber(rs.getString(2));
				bean.setCarId(rs.getLong(3));
				bean.setExpiryDate(rs.getDate(4));
				bean.setInsuranceStatus(rs.getString(5));
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
			throw new ApplicationException("Exception in search Insurance");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}