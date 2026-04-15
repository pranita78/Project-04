package in.co.rays.proj4.bean;

public class PermissionBean  extends BaseBean{
	
	 private Long permissionId;

	 private String permissionCode;

	 private String permissionName;

	 private String moduleName;

	 private String permissionStatus;

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getPermissionStatus() {
		return permissionStatus;
	}

	public void setPermissionStatus(String permissionStatus) {
		this.permissionStatus = permissionStatus;
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
