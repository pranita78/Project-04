package in.co.rays.proj4.bean;

import java.util.Date;

public class ShiftBean extends BaseBean{
	
	 
	private String shiftCode;
	private String shiftName;
	private Date startTime;
	private	Date endTime;
	
	
	public String getShiftCode() {
		return shiftCode;
	}
	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Override
	public String getKey() {
		return null;
	}
	@Override
	public String getValue() {
		return null;
	}
	
	
	

}
