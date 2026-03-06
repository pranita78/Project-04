package in.co.rays.proj4.bean;

import java.util.Date;

public class DonationCampBean extends BaseBean {
	
	private Long campId;
	private String campName;
	private Date campDate;
	private String organizer;
	
	public Long getCampId() {
		return campId;
	}
	public void setCampId(Long campId) {
		this.campId = campId;
	}
	public String getCampName() {
		return campName;
	}
	public void setCampName(String campName) {
		this.campName = campName;
	}
	public Date getCampDate() {
		return campDate;
	}
	public void setCampDate(Date campDate) {
		this.campDate = campDate;
	}
	public String getOrganizer() {
		return organizer;
	}
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
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
