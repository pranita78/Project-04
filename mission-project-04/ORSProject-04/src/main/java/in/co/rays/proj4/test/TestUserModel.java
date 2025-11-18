package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.model.UserModel;

public class TestUserModel {
	public static void main(String[] args)throws Exception {
		
		testAdd();
		
	}

	private static void testAdd() throws Exception {
	
		UserModel model = new UserModel();
		UserBean bean = new UserBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		bean.setFirstName("sejal");
		bean.setLastName("chourasiya");
		bean.setLogin("sejal@gmail.com");
		bean.setPassword("123");
		bean.setDob(sdf.parse("2000-12-22"));
		bean.setMobileNo("1234567890");
		bean.setRoleId(1);
		bean.setGender("Female");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		model.add(bean);
	}
}