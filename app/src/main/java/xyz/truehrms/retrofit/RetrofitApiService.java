package xyz.truehrms.retrofit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import xyz.truehrms.bean.AllAttendance;
import xyz.truehrms.bean.ApproveLeave;
import xyz.truehrms.bean.ApproveRequestAttendance;
import xyz.truehrms.bean.ApprovedAttendance;
import xyz.truehrms.bean.CancelLeaveRequest;
import xyz.truehrms.bean.CommentDelete;
import xyz.truehrms.bean.CommentLikeList;
import xyz.truehrms.bean.DeleteCommentParams;
import xyz.truehrms.bean.EmployeeAttendance;
import xyz.truehrms.bean.EmployeeInfo;
import xyz.truehrms.bean.EmployeeListForTeamLead;
import xyz.truehrms.bean.GetLeaveRequestTeam;
import xyz.truehrms.bean.LeaveDayTypes;
import xyz.truehrms.bean.LeaveSummary;
import xyz.truehrms.bean.LeaveTypes;
import xyz.truehrms.bean.LikePost;
import xyz.truehrms.bean.MyAttendanceRequest;
import xyz.truehrms.bean.MyLeaveRequests;
import xyz.truehrms.bean.Occasions;
import xyz.truehrms.bean.Permissions;
import xyz.truehrms.bean.PersonInCharge;
import xyz.truehrms.bean.Post;
import xyz.truehrms.bean.RejectAttendance;
import xyz.truehrms.bean.RejectLeave;
import xyz.truehrms.bean.TeamForManager;
import xyz.truehrms.bean.TokenData;
import xyz.truehrms.bean.UnLikePost;
import xyz.truehrms.bean.ValidateResponse;
import xyz.truehrms.parameters.AddComment;
import xyz.truehrms.parameters.AddPost;
import xyz.truehrms.parameters.ApplyLeave;
import xyz.truehrms.parameters.EditProfile;
import xyz.truehrms.parameters.Parameters;
import xyz.truehrms.parameters.PunchMiss;
import xyz.truehrms.parameters.User;
import xyz.truehrms.utils.Constant;

public interface RetrofitApiService {
    //getting token
    @Headers("x-TokenType:Dev")
    @POST(Constant.GET_TOKEN)
    Call<TokenData> getToken(@Body User user);

    //getting posts
    @GET(Constant.GET_POSTS)
    Call<Post> getPosts(@Header("x-Token") String token, @Query("companyId") String companyId, @Query("pageIndex") String pageIndex, @Query("pageSize") String pageSize, @Query("employee_id") String employee_id, @Query("postid") String postID);

    @GET(Constant.GET_COMMENTS_LIKES)
    Call<CommentLikeList> getCommentsLikes(@Header("x-Token") String token, @Query("postid") int postid);

    @GET(Constant.LIKE_POST)
    Call<LikePost> likePost(@Header("x-Token") String token, @Query("postid") String postid, @Query("employee_id") String employee_id);

    @GET(Constant.UN_LIKE_POST)
    Call<UnLikePost> unLikePost(@Header("x-Token") String token, @Query("postId") String postId, @Query("employee_id") String employee_id);

    @POST(Constant.ADD_COMMENT)
    Call<xyz.truehrms.bean.AddComment> addComment(@Header("x-Token") String token, @Body AddComment addComment);

    @POST(Constant.ADD_POSTS)
    Call<xyz.truehrms.bean.AddPost> addPost(@Header("x-Token") String token, @Body AddPost addPost);

    // login
    @POST(Constant.VALIDATE_TOKEN)
    Call<ValidateResponse> validateToken(@Header("x-Token") String token, @Body User user);

    // getting punchIn Details
    @GET(Constant.GET_PUNCH_DETAILS)
    Call<EmployeeAttendance> getPunchDetails(@Header("x-Token") String token, @Query("employee_id") String employee_id);

    // getting All Attendance
    @POST(Constant.GET_ALL_ATTENDANCE)
    Call<AllAttendance> getAllAttendance(@Header("x-Token") String token, @Body Parameters serviceParams);

    // getting leave Types
    @GET(Constant.GET_LEAVE_TYPES)
    Call<LeaveTypes> getLeaveTypes(@Header("x-Token") String token);

    // getting leave Day Types
    @GET(Constant.GET_LEAVE_DAYS_TYPE)
    Call<LeaveDayTypes> getLeaveDayTypes(@Header("x-Token") String token);


    @GET(Constant.GET_EMPLOYEE_PERMISSION)
    Call<Permissions> getPermissions(@Header("x-Token") String token, @Query("empId") int empID,@Query("productId") int productID);

    // getting Person In Charge
    @GET(Constant.GET_PERSON_IN_CHARGE)
    Call<PersonInCharge> getPersonInCharge(@Header("x-Token") String token, @Query("empid") String empid);

    ///GET_LEAVE_SUMMERY
    @GET(Constant.GET_LEAVE_SUMMERY)
    Call<LeaveSummary> getLeaveSummery(@Header("x-Token") String token, @Query("empid") int empid);

    // apply leave
    @POST(Constant.APPLY_LEAVE)
    Call<xyz.truehrms.bean.ApplyLeave> applyLeave(@Header("x-Token") String token, @Body ApplyLeave applyLeave);

    @POST(Constant.MY_ATTENDANCE_REQ)
    Call<MyAttendanceRequest> myAttendanceRequest(@Header("x-Token") String token, @Body Parameters serviceParams);

    @POST(Constant.APPROVED_ATTENDANCE)
    Call<ApprovedAttendance> approvedAttendance(@Header("x-Token") String token, @Body Parameters serviceParams);

    @POST(Constant.PUNCH_MISS)
    Call<xyz.truehrms.bean.PunchMiss> punchMiss(@Header("x-Token") String token, @Body PunchMiss punchMiss);

    @GET(Constant.GET_OCCASIONS)
    Call<Occasions> getOccasions(@Header("x-Token") String token, @Query("companyId") String companyId, @Query("employee_id") String employee_id);

    @GET(Constant.GET_EMP)
    Call<EmployeeInfo> getEmployee(@Header("x-Token") String token, @Query("employee_id") String employee_id);


    @POST(Constant.EDIT_PROFILE)
    Call<xyz.truehrms.bean.EditProfile> editProfile(@Header("x-Token") String token, @Body EditProfile editProfile);

    @Multipart
    @POST(Constant.EDIT_PROFILE_PIC)
    Call<ResponseBody> upload(@Part MultipartBody.Part EmployeePic, @Part("employee_id") RequestBody employee_id, @Part("Filename") RequestBody Filename);

    @GET(Constant.APPROVE_REQUEST_ATTENDANCE)
    Call<ApproveRequestAttendance> approveRequestAttendance(@Header("x-Token") String token, @Query("id") String id, @Query("employee_id") String employee_id);

    @GET(Constant.REJECT_ATTENDANCE)
    Call<RejectAttendance> rejectAttendance(@Header("x-Token") String token, @Query("id") String id, @Query("employee_id") String employee_id);

    @GET(Constant.GET_EMPLOYEES_BY_NAME_OR_EMP_CODE)
    Call<EmployeeListForTeamLead> getEmployeesByNameOrEmpCode(@Header("x-Token") String token, @Query("findStr") String str, @Query("companyid")int companyID);

    @POST(Constant.MY_LEAVE_REQUESTS)
    Call<MyLeaveRequests> myLeaveRequests(@Header("x-Token") String token, @Body Parameters serviceParams);

    @POST(Constant.DELETE_COMMENT)
    Call<CommentDelete> deleteComment(@Header("x-Token") String token, @Body DeleteCommentParams commentParams);

    @GET(Constant.APPROVE_LEAVE)
    Call<ApproveLeave> approveLeave(@Header("x-Token") String token, @Query("leaveid") String leaveid/*, @Query("empCode") String empCode*/);

    @GET(Constant.REJECT_LEAVE)
    Call<RejectLeave> rejectLeave(@Header("x-Token") String token, @Query("leaveid") String leaveid, /*@Query("empCode") String empCode,*/ @Query("reason") String reason);

    @POST(Constant.LEAVE_REQ_TEAM)
    Call<GetLeaveRequestTeam> getLeaveRequestTeam(@Header("x-Token") String token, @Body Parameters serviceParams);

    @GET(Constant.GET_TEAM_FOR_MANAGER)
    Call<TeamForManager> getTeamForManager(@Header("x-Token") String token, @Query("id") String id);

    @GET(Constant.CANCEL_LEAVE)
    Call<CancelLeaveRequest> cancelLeaveRequest(@Header("x-Token") String token, @Query("leaveid") String id, @Query("reason") String reason);

}
