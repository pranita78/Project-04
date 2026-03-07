package in.co.rays.proj4.bean;

public class SubscriptionPlanBean extends BaseBean {
	
	private Long Id;
	private String planName;
	private Double price;
	private String validityDays;
	
	
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getValidityDays() {
		return validityDays;
	}
	public void setValidityDays(String validityDays) {
		this.validityDays = validityDays;
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
