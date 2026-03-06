package in.co.rays.proj4.bean;

import java.util.Date;

public class InsuranceBean extends BaseBean{
	
   private Long  insuranceId;
   private String insuranceNumber;
   private Long carId;
   private Date expiryDate;
   private String insuranceStatus;
   
   
public Long getInsuranceId() {
	return insuranceId;
}
public void setInsuranceId(Long insuranceId) {
	this.insuranceId = insuranceId;
}
public String getInsuranceNumber() {
	return insuranceNumber;
}
public void setInsuranceNumber(String insuranceNumber) {
	this.insuranceNumber = insuranceNumber;
}
public Long getCarId() {
	return carId;
}
public void setCarId(Long carId) {
	this.carId = carId;
}
public Date getExpiryDate() {
	return expiryDate;
}
public void setExpiryDate(Date expiryDate) {
	this.expiryDate = expiryDate;
}
public String getInsuranceStatus() {
	return insuranceStatus;
}
public void setInsuranceStatus(String insuranceStatus) {
	this.insuranceStatus = insuranceStatus;
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
