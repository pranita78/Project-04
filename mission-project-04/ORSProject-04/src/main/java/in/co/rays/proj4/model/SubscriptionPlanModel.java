package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.SubscriptionPlanBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class SubscriptionPlanModel {

	public static Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_subscriptionplan");
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

	public long add(SubscriptionPlanBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		try {

			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_subscriptionplan values(?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getPlanName());
			pstmt.setDouble(3, bean.getPrice());
			pstmt.setString(4, bean.getValidityDays());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());

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

			throw new ApplicationException("Exception in add SubscriptionPlan");

		} finally {

			JDBCDataSource.closeConnection(conn);

		}

		return pk;

	}

	public void update(SubscriptionPlanBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_subscriptionplan set plan_name=?, price=?, validity_days=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getPlanName());
			pstmt.setDouble(2, bean.getPrice());
			pstmt.setString(3, bean.getValidityDays());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.setLong(8, bean.getId());

			int i = pstmt.executeUpdate();

			System.out.println("Data Updated => " + i);

			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Update rollback exception");
			}



		} finally {

			JDBCDataSource.closeConnection(conn);

		}
	}

	public void delete(long id) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_subscriptionplan where id=?");

			pstmt.setLong(1, id);

			int i = pstmt.executeUpdate();

			System.out.println("Data Deleted => " + i);

			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete rollback exception");
			}

			throw new ApplicationException("Exception in delete");

		} finally {

			JDBCDataSource.closeConnection(conn);

		}
	}

	public SubscriptionPlanBean findByPk(long id) throws ApplicationException {

		SubscriptionPlanBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_subscriptionplan where id=?");

			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new SubscriptionPlanBean();

				bean.setId(rs.getLong(1));
				bean.setPlanName(rs.getString(2));
				bean.setPrice(rs.getDouble(3));
				bean.setValidityDays(rs.getString(4));
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

	public List<SubscriptionPlanBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<SubscriptionPlanBean> search(SubscriptionPlanBean bean, int pageNo, int pageSize)
			throws ApplicationException {

		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_subscriptionplan where 1=1 ");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id=" + bean.getId());
			}

			if (bean.getPlanName() != null && bean.getPlanName().length() > 0) {
				sql.append(" and plan_name like '" + bean.getPlanName() + "%'");
			}

			if (bean.getValidityDays() != null && bean.getValidityDays().length() > 0) {
				sql.append(" and validity_days like '" + bean.getValidityDays() + "%'");
			}

		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);

		}

		System.out.println("sql => " + sql);

		List<SubscriptionPlanBean> list = new ArrayList<SubscriptionPlanBean>();

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new SubscriptionPlanBean();

				bean.setId(rs.getLong(1));
				bean.setPlanName(rs.getString(2));
				bean.setPrice(rs.getDouble(3));
				bean.setValidityDays(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));

				list.add(bean);

			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in search SubscriptionPlan");

		} finally {

			JDBCDataSource.closeConnection(conn);

		}

		return list;

	}

}