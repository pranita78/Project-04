package in.co.rays.proj4.bean;

import java.util.Date;

public class VersionBean extends BaseBean {

	 private String versionNumber;

	 private String releaseNotes;

    private	Date releaseDate;

	 private String versionStatus;

	 
	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getReleaseNotes() {
		return releaseNotes;
	}

	public void setReleaseNotes(String releaseNotes) {
		this.releaseNotes = releaseNotes;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getVersionStatus() {
		return versionStatus;
	}

	public void setVersionStatus(String versionStatus) {
		this.versionStatus = versionStatus;
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
