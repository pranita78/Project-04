package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.BankBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * @author Pranita Gayakward
 */
public class BankModel {

	/* ================= Next PK ================= */

	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_bank");
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

	/* ================= Add ================= */

	public long add(BankBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		BankBean duplicate = findByMobileNo(bean.getMobileNo());
		if (duplicate != null) {
			throw new DuplicateRecordException("Mobile No already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_bank values(?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getBankName());
			pstmt.setString(3, bean.getBranchName());
			pstmt.setString(4, bean.getAccountHolderName());
			pstmt.setString(5, bean.getMobileNo());
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
				throw new ApplicationException("Exception : Add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Bank");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	/* ================= Update ================= */

	public void update(BankBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		BankBean duplicate = findByMobileNo(bean.getMobileNo());
		if (duplicate != null && duplicate.getId() != bean.getId()) {
			throw new DuplicateRecordException("Mobile No already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_bank set bank_name=?, branch_name=?, account_holder_name=?, mobile_no=?, "
							+ "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getBankName());
			pstmt.setString(2, bean.getBranchName());
			pstmt.setString(3, bean.getAccountHolderName());
			pstmt.setString(4, bean.getMobileNo());
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
				throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in update Bank");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/* ================= Delete ================= */

	public void delete(BankBean bean) throws ApplicationException {
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_bank where id=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Bank");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/* ================= Find By PK ================= */

	public BankBean findByPk(long pk) throws ApplicationException {

		BankBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_bank where id=?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new BankBean();
				bean.setId(rs.getLong(1));
				bean.setBankName(rs.getString(2));
				bean.setBranchName(rs.getString(3));
				bean.setAccountHolderName(rs.getString(4));
				bean.setMobileNo(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in find Bank by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/* ================= Find By Mobile ================= */

	public BankBean findByMobileNo(String mobileNo) throws ApplicationException {

		BankBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_bank where mobile_no=?");
			pstmt.setString(1, mobileNo);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new BankBean();
				bean.setId(rs.getLong(1));
				bean.setBankName(rs.getString(2));
				bean.setBranchName(rs.getString(3));
				bean.setAccountHolderName(rs.getString(4));
				bean.setMobileNo(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in find Bank by Mobile No");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/* ================= List & Search ================= */

	public List<BankBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<BankBean> search(BankBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		ArrayList<BankBean> list = new ArrayList<>();

		StringBuffer sql = new StringBuffer("select * from st_bank where 1=1");

		if (bean != null) {

			if (bean.getBankName() != null && bean.getBankName().length() > 0) {
				sql.append(" and bank_name like '").append(bean.getBankName()).append("%'");
			}

			if (bean.getBranchName() != null && bean.getBranchName().length() > 0) {
				sql.append(" and branch_name like '").append(bean.getBranchName()).append("%'");
			}
			if (bean.getAccountHolderName() != null && bean.getAccountHolderName().length() > 0) {
				sql.append(" and account_holder_name like '").append(bean.getAccountHolderName()).append("%'");
			}

			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" and mobile_no like '").append(bean.getMobileNo()).append("%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit ").append(pageNo).append(", ").append(pageSize);
		}

		System.out.println("sql ---> " + sql.toString());

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new BankBean();
				bean.setId(rs.getLong(1));
				bean.setBankName(rs.getString(2));
				bean.setBranchName(rs.getString(3));
				bean.setAccountHolderName(rs.getString(4));
				bean.setMobileNo(rs.getString(5));
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
			throw new ApplicationException("Exception : Exception in search Bank");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}
