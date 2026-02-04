package in.co.rays.proj4.bean;

import java.util.Date;

public class ReportBean extends BaseBean {

    private long id;    
    private String reportName;
    private Date generatedDate;
    private String generatedBy;
    private String reportStatus;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Date generatedDate) {
        this.generatedDate = generatedDate;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    @Override
    public String getKey() {
        return String.valueOf(id);
    }

    @Override
    public String getValue() {
        return reportName;
    }
}
