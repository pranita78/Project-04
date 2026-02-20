package in.co.rays.proj4.bean;

import java.util.Date;

public class TrainingBean extends BaseBean {
	
	 private String trainingCode;

	 private String trainingName;

	 private String trainerName;

	 private Date trainingDate;

	 private String trainingStatus;
 
	 
	public String getTrainingCode() {
		return trainingCode;
	}

	public void setTrainingCode(String trainingCode) {
		this.trainingCode = trainingCode;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public Date getTrainingDate() {
		return trainingDate;
	}

	public void setTrainingDate(Date trainingDate) {
		this.trainingDate = trainingDate;
	}

	public String getTrainingStatus() {
		return trainingStatus;
	}

	public void setTrainingStatus(String trainingStatus) {
		this.trainingStatus = trainingStatus;
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
