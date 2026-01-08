package in.co.rays.proj4.bean;

public class BankBean extends BaseBean {

	
    private String bankName;
    private String branchName;
    private String accountHolderName;
    private String mobileNo;

    /* ================= Getters & Setters ================= */

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /* ================= BaseBean Methods ================= */

    @Override
    public String getKey() {
        return String.valueOf(getId());
    }

    @Override
    public String getValue() {
        return bankName;
    }
}
