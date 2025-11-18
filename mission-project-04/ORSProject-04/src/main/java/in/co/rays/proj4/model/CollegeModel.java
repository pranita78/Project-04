package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class CollegeModel {

	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pstmt = conn.prepareStatement("select max(id) from st_college");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pk = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new DatabaseException("Exception in getting PK: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	public long add(CollegeBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		int pk = 0;

		CollegeBean duplicateCollegeName = findByName(bean.getName());

		if (duplicateCollegeName != null) {
			throw new DuplicateRecordException("College Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_college values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getAddress());
			pstmt.setString(4, bean.getState());
			pstmt.setString(5, bean.getCity());
			pstmt.setString(6, bean.getPhoneNo());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			
			
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				if (conn != null)
					conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add College: " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	public void update(CollegeBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		CollegeBean beanExist = findByName(bean.getName());

		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("College is already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_college set name = ?, address = ?, state = ?, city = ?, phone_no = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getAddress());
			pstmt.setString(3, bean.getState());
			pstmt.setString(4, bean.getCity());
			pstmt.setString(5, bean.getPhoneNo());
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
				if (conn != null)
					conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating College: " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(CollegeBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_college where id = ?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				if (conn != null)
					conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete college: " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public CollegeBean findByPk(long pk) throws ApplicationException {

		String sql = "select * from st_college where id = ?";

		CollegeBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting College by pk: " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public CollegeBean findByName(String name) throws ApplicationException {

		String sql = "select * from st_college where name = ?";

		CollegeBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting College by Name: " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public List<CollegeBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<CollegeBean> search(CollegeBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_college where 1=1");
		List<Object> params = new ArrayList<>();

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}

			if (bean.getName() != null && !bean.getName().isEmpty()) {
				sql.append(" and name like ?");
				params.add(bean.getName() + "%");
			}
			if (bean.getAddress() != null && !bean.getAddress().isEmpty()) {
				sql.append(" and address like ?");
				params.add(bean.getAddress() + "%");
			}
			if (bean.getState() != null && !bean.getState().isEmpty()) {
				sql.append(" and state like ?");
				params.add(bean.getState() + "%");
			}
			if (bean.getCity() != null && !bean.getCity().isEmpty()) {
				sql.append(" and city like ?");
				params.add(bean.getCity() + "%");
			}
			if (bean.getPhoneNo() != null && !bean.getPhoneNo().isEmpty()) {
				sql.append(" and phone_no = ?");
				params.add(bean.getPhoneNo());
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit ?, ?");
			params.add(pageNo);
			params.add(pageSize);
		}

		List<CollegeBean> list = new ArrayList<>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(i + 1, params.get(i));
			}

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				CollegeBean cbean = new CollegeBean();
				cbean.setId(rs.getLong(1));
				cbean.setName(rs.getString(2));
				cbean.setAddress(rs.getString(3));
				cbean.setState(rs.getString(4));
				cbean.setCity(rs.getString(5));
				cbean.setPhoneNo(rs.getString(6));
				list.add(cbean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search college: " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}
