package in.co.rays.proj4.bean;

import java.util.Date;

public class EnrollmentBean extends BaseBean {
	
	 
	private String enrollmentCode;
	private String studentName;
	private	String courseName;
	 private Date enrollmentDate;
	 
	public String getEnrollmentCode() {
		return enrollmentCode;
	}
	public void setEnrollmentCode(String enrollmentCode) {
		this.enrollmentCode = enrollmentCode;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Date getEnrollmentDate() {
		return enrollmentDate;
	}
	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	 
}

