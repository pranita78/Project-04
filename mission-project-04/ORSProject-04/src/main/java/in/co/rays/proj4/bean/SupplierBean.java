package in.co.rays.proj4.bean;

import java.util.Date;

public class SupplierBean  extends BaseBean{
	
	private String Name;
	private String Category;
	private Date Dob;
	private Integer PaymentTerm;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public Date getDob() {
		return Dob;
	}
	public void setDob(Date dob) {
		Dob = dob;
	}
	public Integer getPaymentTerm() {
		return PaymentTerm;
	}
	public void setPaymentTerm(Integer paymentTerm) {
		PaymentTerm = paymentTerm;
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
