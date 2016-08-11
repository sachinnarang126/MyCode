package xyz.truehrms.utils;

public class Constant {

    public static final String SALT = "#5a7k=";
    public static final String BASE_URL = "http://104.40.63.102:8095/api/";
    //    public static final String BASE_URL = "http://10.20.1.17:8094/api/";
    public static final String GET_TOKEN = "AppToken/GetToken";
    //    public static final String VALIDATE_TOKEN = "PortalAuthentication/ValidateWithEmployeeDetails";
    public static final String VALIDATE_TOKEN = "PortalAuthentication/ValidateWithSingleSignOnWithApi";
    public static final String GET_EMP = "Dashboard/getEmployee";
    public static final String GET_EMPLOYEE_PERMISSION = "Employee/getPermissionForEmployee";
    public static final String GET_POSTS = "Dashboard/getAllPostAndComments";
    public static final String GET_PUNCH_DETAILS = "Dashboard/getEmployeeAttendence";
    public static final String GET_ALL_ATTENDANCE = "attendance/getAllAttendance";
    public static final String ADD_COMMENT = "Dashboard/addComment";
    public static final String ADD_POSTS = "Dashboard/addPublicPost";
    public static final String GET_LEAVE_TYPES = "Leaves/getLeaveTypes";
    public static final String GET_LEAVE_DAYS_TYPE = "Leaves/getLeaveDaysType";
    public static final String GET_PERSON_IN_CHARGE = "Leaves/getPersonIncharge";
    //    public static final String GET_LEAVE_SUMMERY = "LeaveSummary/getLeaveSummary";
    public static final String GET_LEAVE_SUMMERY = "Leaves/getLeaveSummary";
    public static final String APPLY_LEAVE = "Leaves/applyLeave";
    public static final String GET_OCCASIONS = "Dashboard/getOccasions";
    public static final String MY_ATTENDANCE_REQ = "Attendance/MyAttendanceRequest";
    public static final String APPROVED_ATTENDANCE = "Attendance/ApprovedAttendance";
    public static final String APPROVE_REQUEST_ATTENDANCE = "Attendance/ApproveRequestAttendance";
    public static final String REJECT_ATTENDANCE = "Attendance/RejectAttendance";
    public static final String PUNCH_MISS = "Attendance/editAttendance";
    public static final String EDIT_PROFILE = "Employee/UpdateEmployeePersonalDetail";
    public static final String EDIT_PROFILE_PIC = "Employee/PostFormData";
    public static final String GET_EMPLOYEES_BY_NAME_OR_EMP_CODE = "dashboard/getEmployeesByNameorEmpCode";
    public static final String MY_LEAVE_REQUESTS = "Leaves/getLeaveRequests";
    public static final String APPROVE_LEAVE = "Leaves/approveLeave";
    public static final String REJECT_LEAVE = "Leaves/rejectLeave";
    public static final String LEAVE_REQ_TEAM = "Leaves/getPendingLeaveRequests";
    public static final String GET_TEAM_FOR_MANAGER = "employee/getTeamForManager";
    public static final String GET_COMMENTS_LIKES = "Dashboard/getAllCommentsandLikebyPostid";
    public static final String LIKE_POST = "Dashboard/likePost";
    public static final String UN_LIKE_POST = "Dashboard/unLikePost";
    public static final String DELETE_COMMENT = "Dashboard/DeleteComment";
    public static final String CANCEL_LEAVE = "Leaves/cancelLeave";

    ////shared Preference keys
    public static final String PREFERENCE = "TrueHRMS";
    public static final String IS_TOKEN_GOT = "IsToken_got";
    public static final String USER_DETAIL_OBJ = "UserDetailObj";
    public static final String HAS_ADMIN_CONTROL = "isAdmin";
    public static final String TOKEN = "Token";
    public static final String SAVE_USER = "SaveUser";
    public static final String EMPLOYEE_PIC = "pic";
    public static final String POST_ID = "PostId";
    public static final String SELECTED_POST_EMP_NAME = "Selected_post_empname";
    public static final String SELECTED_POST_TIME = "Selected_post_time";
    public static final String SELECTED_POST_CONTENT = "Selected_post_content";
    public static final String AVATAR = "Avatar";
    public static final String IS_LIKED = "IS_LIKED";

    /*public static final int SUPER_ADMIN = 1;
    public static final int ADMIN = 2;*/


    /**
     * Non-Admin Permission
     */
    public static final int DASHBOARD_VIEW = 1;
    public static final int PROFILE_VIEW = 2;
    public static final int PROFILE_EDIT = 3;
    /*public static final int PROFILE_PRINT = 4;
    public static final int PROFILE_ADD = 5;
    public static final int ATTENDANCE_LEAVES_VIEW = 6;*/
    public static final int VIEW_ATTENDANCE_VIEW = 7;
    public static final int VIEW_ATTENDANCE_EDIT = 8;
    public static final int APPROVE_ATTENDANCE_REQ_VIEW = 9;
    public static final int APPROVE_ATTENDANCE_REQ_EDIT = 10;
    //    public static final int APPLY_LEAVE_VIEW = 11;
    public static final int APPLY_LEAVE_EDIT = 12;
    public static final int APPROVE_LEAVE_REQ_VIEW = 13;
    public static final int APPROVE_LEAVE_REQ_EDIT = 14;
    /*public static final int EMPLOYEE_LIST_VIEW = 15;
    public static final int EMPLOYEE_LIST_EDIT = 16;
    public static final int EMPLOYEE_LIST_ACTIVATE = 17;
    public static final int EMPLOYEE_LIST_RESET = 18;
    public static final int EMPLOYEE_LIST_EXIT = 19;
    public static final int EMPLOYEE_LIST_CONFIRM = 20;
    public static final int EMPLOYEE_LIST_EXPORT = 21;
    public static final int IMPORT_VIEW = 22;
    public static final int IMPORT_DIVISION = 23;
    public static final int IMPORT_SHIFT = 24;
    public static final int IMPORT_COUNTRY = 25;
    public static final int IMPORT_EMPLOYEE = 26;
    public static final int IMPORT_PUNCHTIME_DETAILS = 27;
    public static final int IMPORT_DESIGNATION = 28;
    public static final int APPLIED_LEAVE_REQ_EMP_LEAVE_REQ = 29;
    public static final int MY_ATTENDANCE_REQ_MY_ATTENDANCE_REQ = 30;*/
    public static final int ATTENDANCE_REQ_MANAGER = 31;

}
