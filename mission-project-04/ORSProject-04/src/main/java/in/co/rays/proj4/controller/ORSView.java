package in.co.rays.proj4.controller;

public interface ORSView {

	public String APP_CONTEXT = "/ORSProject-04";

	public String PAGE_FOLDER = "/jsp";

	public String WELCOME_VIEW = PAGE_FOLDER + "/Welcome.jsp";
	public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";

	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";
	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";

	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";

	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
	public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";

	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";
	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";

	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";
	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";

	public String GET_MARKSHEET_VIEW = PAGE_FOLDER + "/GetMarksheetView.jsp";
	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";

	public String MARKSHEET_MERIT_LIST_VIEW = PAGE_FOLDER + "/MarksheetMeritListView.jsp";
	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";

	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";
	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";

	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";
	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";

	public String ROLE_VIEW = PAGE_FOLDER + "/RoleView.jsp";
	public String ROLE_CTL = APP_CONTEXT + "/ctl/RoleCtl";

	public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleListView.jsp";
	public String ROLE_LIST_CTL = APP_CONTEXT + "/ctl/RoleListCtl";

	public String COLLEGE_VIEW = PAGE_FOLDER + "/CollegeView.jsp";
	public String COLLEGE_CTL = APP_CONTEXT + "/ctl/CollegeCtl";

	public String COLLEGE_LIST_VIEW = PAGE_FOLDER + "/CollegeListView.jsp";
	public String COLLEGE_LIST_CTL = APP_CONTEXT + "/ctl/CollegeListCtl";

	public String STUDENT_VIEW = PAGE_FOLDER + "/StudentView.jsp";
	public String STUDENT_CTL = APP_CONTEXT + "/ctl/StudentCtl";

	public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentListView.jsp";
	public String STUDENT_LIST_CTL = APP_CONTEXT + "/ctl/StudentListCtl";

	public String MARKSHEET_VIEW = PAGE_FOLDER + "/MarksheetView.jsp";
	public String MARKSHEET_CTL = APP_CONTEXT + "/ctl/MarksheetCtl";

	public String MARKSHEET_LIST_VIEW = PAGE_FOLDER + "/MarksheetListView.jsp";
	public String MARKSHEET_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetListCtl";

	public String COURSE_VIEW = PAGE_FOLDER + "/CourseView.jsp";
	public String COURSE_CTL = APP_CONTEXT + "/ctl/CourseCtl";

	public String SUBJECT_VIEW = PAGE_FOLDER + "/SubjectView.jsp";
	public String SUBJECT_CTL = APP_CONTEXT + "/ctl/SubjectCtl";

	public String TIMETABLE_LIST_VIEW = PAGE_FOLDER + "/TimetableListView.jsp";
	public String TIMETABLE_LIST_CTL = APP_CONTEXT + "/ctl/TimetableListCtl";

	public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";
	public String FACULTY_CTL = APP_CONTEXT + "/ctl/FacultyCtl";
	
	public String COURSE_LIST_VIEW = PAGE_FOLDER + "/CourseListView.jsp";
	public String COURSE_LIST_CTL = APP_CONTEXT + "/ctl/CourseListCtl";
	
	public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectListView.jsp";
	public String SUBJECT_LIST_CTL = APP_CONTEXT + "/ctl/SubjectListCtl";
	
	public String TIMETABLE_VIEW = PAGE_FOLDER + "/TimetableView.jsp";
	public String TIMETABLE_CTL = APP_CONTEXT + "/ctl/TimetableCtl";

	public String FACULTY_LIST_VIEW = PAGE_FOLDER + "/FacultyListView.jsp";
	public String FACULTY_LIST_CTL = APP_CONTEXT + "/ctl/FacultyListCtl";

	public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView.jsp";
	public String ERROR_CTL = APP_CONTEXT + "/ErrorCtl";
	
	public String PATIENT_VIEW = PAGE_FOLDER + "/PatientView.jsp";
	public String PATIENT_CTL = APP_CONTEXT + "/ctl/PatientCtl";

	public String PATIENT_LIST_VIEW = PAGE_FOLDER + "/PatientListView.jsp";
	public String PATIENT_LIST_CTL = APP_CONTEXT + "/ctl/PatientListCtl";
	
	

    /** Doctor management pages and controllers (custom module) */
    public String DOCTOR_VIEW = PAGE_FOLDER + "/DoctorView.jsp";
    public String DOCTOR_CTL = APP_CONTEXT + "/ctl/DoctorCtl";

    public String DOCTOR_LIST_VIEW = PAGE_FOLDER + "/DoctorListView.jsp";
    public String DOCTOR_LIST_CTL = APP_CONTEXT + "/ctl/DoctorListCtl";
    
    //account
    
    public String ACCOUNT_VIEW = PAGE_FOLDER + "/AccountView.jsp";
    public String ACCOUNT_CTL = APP_CONTEXT + "/ctl/AccountCtl";
    
    public String ACCOUNT_LIST_VIEW = PAGE_FOLDER + "/AccountListView.jsp";
    public String ACCOUNT_LIST_CTL = APP_CONTEXT + "/ctl/AccountListCtl";
    
    // report
    
    public String REPORT_VIEW = PAGE_FOLDER + "/ReportView.jsp";
    public String REPORT_CTL = APP_CONTEXT + "/ctl/ReportCtl";
    
    public String REPORT_LIST_VIEW = PAGE_FOLDER + "/ReportListView.jsp";
    public String REPORT_LIST_CTL = APP_CONTEXT + "/ctl/ReportListCtl";
    
    
  //bank
      
    public String BANK_VIEW = PAGE_FOLDER + "/BankView.jsp";
    public String BANK_CTL = APP_CONTEXT + "/ctl/BankCtl";
     
    public String BANK_LIST_VIEW = PAGE_FOLDER + "/BankListView.jsp";
    public String BANK_LIST_CTL = APP_CONTEXT + "/ctl/BankListCtl";
    
    public String MOBILE_VIEW = PAGE_FOLDER + "/MobileView.jsp";
    public String MOBILE_CTL = APP_CONTEXT + "/ctl/MobileCtl";
     
    public String MOBILE_LIST_VIEW = PAGE_FOLDER + "/MobileListView.jsp";
    public String MOBILE_LIST_CTL = APP_CONTEXT + "/ctl/MobileListCtl";
    
    public String ATTENDANCE_VIEW = PAGE_FOLDER + "/AttendanceView.jsp";
    public String ATTENDANCE__CTL = APP_CONTEXT + "/ctl/AttendanceCtl";
     
    public String ATTENDANCE__LIST_VIEW = PAGE_FOLDER + "/AttendanceListView.jsp";
    public String ATTENDANCE__LIST_CTL = APP_CONTEXT + "/ctl/AttendanceListCtl";
    
    public String SUPPLIER_VIEW = PAGE_FOLDER + "/SupplierView.jsp";
    public String SUPPLIER_CTL = APP_CONTEXT + "/ctl/SupplierCtl";

    public String SUPPLIER_LIST_VIEW = PAGE_FOLDER + "/SupplierListView.jsp";
    public String SUPPLIER_LIST_CTL = APP_CONTEXT + "/ctl/SupplierListCtl";
    
    public String VERSION_VIEW = PAGE_FOLDER + "/VersionView.jsp";
    public String VERSION_CTL = APP_CONTEXT + "/ctl/VersionCtl";

    public String VERSION_LIST_VIEW = PAGE_FOLDER + "/VersionListView.jsp";
    public String VERSION_LIST_CTL = APP_CONTEXT + "/ctl/VersionListCtl";
    
    public String TRAINING_VIEW = PAGE_FOLDER + "/TrainingView.jsp";
    public String TRAINING_CTL = APP_CONTEXT + "/ctl/TrainingCtl";

    public String TRAINING_LIST_VIEW = PAGE_FOLDER + "/TrainingListView.jsp";
    public String TRAINING_LIST_CTL = APP_CONTEXT + "/ctl/TrainingListCtl";
    
    public String INVENTORY_VIEW = PAGE_FOLDER + "/InventoryView.jsp";
    public String INVENTORY_CTL = APP_CONTEXT + "/ctl/InventoryCtl";

    public String INVENTORY_LIST_VIEW = PAGE_FOLDER + "/InventoryListView.jsp";
    public String INVENTORY_LIST_CTL = APP_CONTEXT + "/ctl/InventoryListCtl";

    public String SHIFT_VIEW = PAGE_FOLDER + "/ShiftView.jsp";
    public String SHIFT_CTL = APP_CONTEXT + "/ctl/ShiftCtl";

    public String SHIFT_LIST_VIEW = PAGE_FOLDER + "/ShiftListView.jsp";
    public String SHIFT_LIST_CTL = APP_CONTEXT + "/ctl/ShiftListCtl";
    
    public String ENROLLMENT_VIEW = PAGE_FOLDER + "/EnrollmentView.jsp";
    public String ENROLLMENT_CTL = APP_CONTEXT + "/ctl/EnrollmentCtl";

    public String ENROLLMENT_LIST_VIEW = PAGE_FOLDER + "/EnrollmentListView.jsp";
    public String ENROLLMENT_LIST_CTL = APP_CONTEXT + "/ctl/EnrollmentListCtl";
     
    public String EVENT_VIEW = PAGE_FOLDER + "/EventView.jsp";
    public String EVENT_CTL = APP_CONTEXT + "/ctl/EventCtl";

    public String EVENT_LIST_VIEW = PAGE_FOLDER + "/EventListView.jsp";
    public String EVENT_LIST_CTL = APP_CONTEXT + "/ctl/EventListCtl";
    
    public String COURIER_VIEW = PAGE_FOLDER + "/CourierView.jsp";
    public String COURIER_CTL = APP_CONTEXT + "/ctl/CourierCtl";

    public String COURIER_LIST_VIEW = PAGE_FOLDER + "/CourierListView.jsp";
    public String COURIER_LIST_CTL = APP_CONTEXT + "/ctl/CourierListCtl";
    
    public String NOTIFICATION_VIEW = PAGE_FOLDER + "/NotificationView.jsp";
    public String NOTIFICATION_CTL = APP_CONTEXT + "/ctl/NotificationCtl";

    public String NOTIFICATION_LIST_VIEW = PAGE_FOLDER + "/NotificationListView.jsp";
    public String NOTIFICATION_LIST_CTL = APP_CONTEXT + "/ctl/NotificationListCtl";
    
 // Donation Camp Controller
 	public String DONATIONCAMP_CTL = APP_CONTEXT + "/ctl/DonationCampCtl";
 	public String DONATIONCAMP_LIST_CTL = APP_CONTEXT + "/ctl/DonationCampListCtl";

 	// Donation Camp View
 	public String DONATIONCAMP_VIEW = "/jsp/DonationCampView.jsp";
 	public String DONATIONCAMP_LIST_VIEW = "/jsp/DonationCampListView.jsp";

 	  public String INSURANCE_VIEW = PAGE_FOLDER + "/InsuranceView.jsp";
 	    public String INSURANCE_CTL = APP_CONTEXT + "/ctl/InsuranceCtl";

 	    public String INSURANCE_LIST_VIEW = PAGE_FOLDER + "/InsuranceListView.jsp";
 	    public String INSURANCE_LIST_CTL = APP_CONTEXT + "/ctl/InsuranceListCtl";
 	    
 	   public String SUBSCRIPTIONPLAN_VIEW = PAGE_FOLDER + "/SubscriptionPlanView.jsp";
 	  public String SUBSCRIPTIONPLAN_CTL = APP_CONTEXT + "/ctl/SubscriptionPlanCtl";

 	  public String SUBSCRIPTIONPLAN_LIST_VIEW = PAGE_FOLDER + "/SubscriptionPlanListView.jsp";
 	  public String SUBSCRIPTIONPLAN_LIST_CTL = APP_CONTEXT + "/ctl/SubscriptionPlanListCtl";
    
    public String JAVA_DOC = APP_CONTEXT + "/doc/index.html";

}