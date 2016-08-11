package xyz.truehrms.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.truehrms.R;
import xyz.truehrms.activities.DashboardActivity;
import xyz.truehrms.bean.ApproveLeave;
import xyz.truehrms.bean.CancelLeaveRequest;
import xyz.truehrms.bean.GetLeaveRequestTeam;
import xyz.truehrms.bean.RejectLeave;
import xyz.truehrms.fragments.TeamLeaveRequestFragment;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;

public class TeamLeaveRequestAdapter extends RecyclerView.Adapter<TeamLeaveRequestAdapter.ViewHolder> {

    private Context context;
    private List<GetLeaveRequestTeam.Result.LeaveListResult> leaveListResult;
    private TeamLeaveRequestFragment teamLeaveRequestFragment;
    private String month, year;

    public TeamLeaveRequestAdapter(String m, String y, List<GetLeaveRequestTeam.Result.LeaveListResult> leaveListResult,
                                   Context context, TeamLeaveRequestFragment teamLeaveRequestFragment) {
        this.leaveListResult = leaveListResult;
        this.context = context;
        month = m;
        year = y;
        this.teamLeaveRequestFragment = teamLeaveRequestFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_leave_request_team, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (leaveListResult != null) {
            final int isApproved = leaveListResult.get(position).getIsapproved();
            try {
                holder.txt_reqtr_name.setText(leaveListResult.get(position).getEmpname());
                holder.txt_atndReq_reason.setText(leaveListResult.get(position).getReason());

                switch (isApproved) {
                    case 0:
                        holder.team_atndReq_aprvd.setVisibility(View.VISIBLE);
                        holder.team_atndReq_reject.setText(context.getString(R.string.reject));
                        holder.team_atndReq_reject.setTextColor(ContextCompat.getColor(context, R.color.black));
                        holder.team_atndReq_aprvd.setTextColor(ContextCompat.getColor(context, R.color.black));
                        holder.team_atndReq_aprvd.setText(context.getString(R.string.approve));
                        break;

                    case 1:
                        holder.team_atndReq_aprvd.setVisibility(View.VISIBLE);
                        holder.team_atndReq_aprvd.setText(context.getString(R.string.approved));
                        holder.team_atndReq_reject.setText(context.getString(R.string.cancel));
                        holder.team_atndReq_reject.setTextColor(ContextCompat.getColor(context, R.color.black));
                        holder.team_atndReq_aprvd.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                        break;

                    case 2:
                        holder.team_atndReq_aprvd.setVisibility(View.INVISIBLE);
                        holder.team_atndReq_reject.setText(context.getString(R.string.rejected));
                        holder.team_atndReq_reject.setTextColor(ContextCompat.getColor(context, R.color.red));
                        break;

                    case 3:
                        holder.team_atndReq_aprvd.setVisibility(View.INVISIBLE);
                        holder.team_atndReq_reject.setText(context.getString(R.string.cancelled));
                        holder.team_atndReq_reject.setTextColor(ContextCompat.getColor(context, R.color.red));
                        break;
                }
                try {
                    String date = getFormattedDate(leaveListResult.get(position).getFromdate()) + " - " + getFormattedDate(leaveListResult.get(position).getTodate());
                    holder.txt_atndReq_date.setText(date);
                    String appliedOn = "Applied On: " + getFormattedDate(leaveListResult.get(position).getRequestedon());
                    holder.txt_atnd_req_apliedOn.setText(appliedOn);
                } catch (ParseException e) {
//                    e.printStackTrace();
                }
            } catch (Exception e) {
//                e.printStackTrace();
            }

            holder.team_atndReq_aprvd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((DashboardActivity) context).getPreference().hasAdminControl()) {
                        callApprovedWebServices(leaveListResult.get(position).getId());
                    } else {
                        if (((DashboardActivity) context).hasPermission(Constant.APPROVE_LEAVE_REQ_EDIT)) {
                            callApprovedWebServices(leaveListResult.get(position).getId());
                        } else {
                            if (context != null) {
                                ((DashboardActivity) context).showToast(context.getString(R.string.error_approve_leave));
                            }
                        }
                    }
                }
            });

            holder.team_atndReq_reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isApproved == 0 || isApproved == 1) {
                        if (((DashboardActivity) context).getPreference().hasAdminControl()) {
                            showReasonInputDialog(isApproved, leaveListResult.get(position).getId());
                        } else {
                            if (((DashboardActivity) context).hasPermission(Constant.APPROVE_LEAVE_REQ_EDIT)) {
                                showReasonInputDialog(isApproved, leaveListResult.get(position).getId());
                            } else {
                                if (context != null) {
                                    ((DashboardActivity) context).showToast(context.getString(R.string.error_reject_leave));
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    private void callRejectService(int id, String reason) {
        RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
        Call<RejectLeave> rejectCall = apiService.rejectLeave(((DashboardActivity) context).getPreference().
                getToken(Constant.TOKEN), String.valueOf(id), reason);
        if (((DashboardActivity) context).isInternetAvailable()) {
            rejectCall.enqueue(new Callback<RejectLeave>() {
                @Override
                public void onResponse(Call<RejectLeave> call, Response<RejectLeave> response) {
                    teamLeaveRequestFragment.callTeamRequestService(month, year);
                }

                @Override
                public void onFailure(Call<RejectLeave> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            ((DashboardActivity) context).showToast(context.getString(R.string.error_internet));
        }
    }

    private void callCancelService(int id, String reason) {
        RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
        Call<CancelLeaveRequest> cancelCall = apiService.cancelLeaveRequest(((DashboardActivity) context).getPreference().getToken(Constant.TOKEN), String.valueOf(id), reason);
        if (((DashboardActivity) context).isInternetAvailable()) {
            cancelCall.enqueue(new Callback<CancelLeaveRequest>() {
                @Override
                public void onResponse(Call<CancelLeaveRequest> call, Response<CancelLeaveRequest> response) {
                    teamLeaveRequestFragment.callTeamRequestService(month, year);
                }

                @Override
                public void onFailure(Call<CancelLeaveRequest> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    private void callApprovedWebServices(int id) {
        RetrofitApiService apiService = RetrofitClient.getRetrofitClient();

        if (((DashboardActivity) context).isInternetAvailable()) {
            Call<ApproveLeave> rejectAttendanceCall = apiService.approveLeave(((DashboardActivity) context).getPreference().getToken(Constant.TOKEN), String.valueOf(id));
            rejectAttendanceCall.enqueue(new Callback<ApproveLeave>() {
                @Override
                public void onResponse(Call<ApproveLeave> call, Response<ApproveLeave> response) {
                    teamLeaveRequestFragment.callTeamRequestService(month, year);
                }

                @Override
                public void onFailure(Call<ApproveLeave> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            ((DashboardActivity) context).showToast(context.getString(R.string.error_internet));
        }
    }

    @Override
    public int getItemCount() {
        return leaveListResult.size();
    }

    private String getFormattedDate(String toBeParsedDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date date = simpleDateFormat.parse(toBeParsedDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + " " +
                calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(Calendar.YEAR);
    }

    private void showReasonInputDialog(final int value, final int id) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Enter Reason");
        /*TextView title = new TextView(context);
        // You Can Customise your Title here
        title.setText("Enter Reason");
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 12, 0, 0);
        title.setTextSize(18);
        alertDialog.setCustomTitle(title);*/

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_alert, null);
        alertDialog.setView(view);
        final EditText et_reason = (EditText) view.findViewById(R.id.et_reason);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);

        final AlertDialog dialog = alertDialog.create();

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = et_reason.getText().toString().trim();

                if (reason.length() > 0) {
                    if (value == 0) {
                        // call reject service
                        callRejectService(id, reason);
                    } else if (value == 1) {
                        // call cancel service
                        callCancelService(id, reason);
                    }
                    dialog.dismiss();
                } else {
                    ((DashboardActivity) context).showToast("Please enter reason");
                }
            }
        });
        dialog.show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_reqtr_name, txt_atnd_req_apliedOn, txt_atndReq_reason, txt_atndReq_date,
                team_atndReq_aprvd, team_atndReq_reject;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_atnd_req_apliedOn = (TextView) itemView.findViewById(R.id.txt_atnd_req_apliedOn);
            txt_reqtr_name = (TextView) itemView.findViewById(R.id.txt_reqtr_name);
            txt_atndReq_reason = (TextView) itemView.findViewById(R.id.txt_atndReq_reason);
            txt_atndReq_date = (TextView) itemView.findViewById(R.id.txt_atndReq_date);
            team_atndReq_aprvd = (TextView) itemView.findViewById(R.id.team_leaveReq_aprvd);
            team_atndReq_reject = (TextView) itemView.findViewById(R.id.team_leaveReq_reject);
        }
    }
}

