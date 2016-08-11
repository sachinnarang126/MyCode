package xyz.truehrms.adapters;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import xyz.truehrms.bean.ApproveRequestAttendance;
import xyz.truehrms.bean.ApprovedAttendance;
import xyz.truehrms.bean.RejectAttendance;
import xyz.truehrms.fragments.TeamPunchRequestFragment;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;

public class TeamPunchRequestAdapter extends RecyclerView.Adapter<TeamPunchRequestAdapter.ViewHolder> {

    private String month, year;
    private List<ApprovedAttendance.Result.AaDatum> result;
    private TeamPunchRequestFragment teamPunchRequestFragment;
    private Activity activity;

    public TeamPunchRequestAdapter(String month, String year, List<ApprovedAttendance.Result.AaDatum> result, Activity activity, TeamPunchRequestFragment teamPunchRequestFragment) {
        this.result = result;
        this.activity = activity;
        this.month = month;
        this.year = year;
        this.teamPunchRequestFragment = teamPunchRequestFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_attendance_request_team, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (result != null) {
            final String isApproved = result.get(position).getIsapproved();
            try {
                try {
//                    holder.txt_atndReq_date.setText(getFormattedDate(result.get(position).getPunchdate()) + " (" + getFormattedTime(result.get(position).getPunchtime()) + ")");
                    holder.txt_atndReq_date.setText(getFormattedDate(result.get(position).getPunchdate()) + " (" + result.get(position).getPunchtime() + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                holder.txt_atnd_req_apliedOn.setText(getFormattedDate(result.get(position).getCreatedon()));
                holder.txt_reqtr_name.setText(result.get(position).getEmpName());
                holder.txt_atndReq_reason.setText(result.get(position).getPunchdescription());

                /*
                    * case - 0 admin/manager can approve or reject attendance request
                    *
                    * case -1 attendance request is approved admin/manager can cancel it
                    *
                    * case -2 attendance request is rejected admin/manager can't do anything
                    *
                    * case -3 attendance request is cancelled
                    * */

                switch (isApproved) {

                    case "0":
                        holder.team_atndReq_aprvd.setVisibility(View.VISIBLE);
                        holder.team_atndReq_reject.setText(activity.getString(R.string.reject));
                        holder.team_atndReq_reject.setTextColor(ContextCompat.getColor(activity, R.color.black));
                        holder.team_atndReq_aprvd.setTextColor(ContextCompat.getColor(activity, R.color.black));
                        holder.team_atndReq_aprvd.setText(activity.getString(R.string.approve));
                        break;

                    case "1":
                        holder.team_atndReq_aprvd.setVisibility(View.VISIBLE);
                        holder.team_atndReq_aprvd.setText(activity.getString(R.string.approved));
                        holder.team_atndReq_reject.setText(activity.getString(R.string.cancel));
                        holder.team_atndReq_reject.setTextColor(ContextCompat.getColor(activity, R.color.gray));
                        holder.team_atndReq_aprvd.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                        break;

                    case "2":
                        holder.team_atndReq_aprvd.setVisibility(View.INVISIBLE);
                        holder.team_atndReq_reject.setText(activity.getString(R.string.rejected));
                        holder.team_atndReq_reject.setTextColor(ContextCompat.getColor(activity, R.color.red));
                        break;

                    case "3":
                        holder.team_atndReq_aprvd.setVisibility(View.INVISIBLE);
                        holder.team_atndReq_reject.setText(activity.getString(R.string.cancelled));
                        holder.team_atndReq_reject.setTextColor(ContextCompat.getColor(activity, R.color.red));
                        break;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.team_atndReq_aprvd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isApproved.equalsIgnoreCase("0")) {
                        if (((DashboardActivity) activity).getPreference().hasAdminControl()) {
                            callApprovedWebServices(result.get(position).getId(), result.get(position).getEmployee_id());
                        } else {
                            if (((DashboardActivity) activity).hasPermission(Constant.APPROVE_ATTENDANCE_REQ_EDIT)) {
                                callApprovedWebServices(result.get(position).getId(), result.get(position).getEmployee_id());
                            } else {
                                if (activity != null) {
                                    ((DashboardActivity) activity).showToast(activity.getString(R.string.error_approve_attendance));
                                }
                            }
                        }
                    }
                }
            });
            holder.team_atndReq_reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isApproved.equalsIgnoreCase("0")) {

                        if (((DashboardActivity) activity).getPreference().hasAdminControl()) {
                            callRejectService(result.get(position).getId(), result.get(position).getEmployee_id());
                        } else {
                            if (((DashboardActivity) activity).hasPermission(Constant.APPROVE_ATTENDANCE_REQ_EDIT)) {
                                callRejectService(result.get(position).getId(), result.get(position).getEmployee_id());
                            } else {
                                if (activity != null) {
                                    ((DashboardActivity) activity).showToast(activity.getString(R.string.error_reject_attendance));
                                }
                            }
                        }
                    } else if (isApproved.equalsIgnoreCase("1")) {
                        ((DashboardActivity) activity).showToast("It will be implemented later");
                        /*if (((DashboardActivity) activity).getPreference().hasAdminControl()) {

                        } else {
                            if (((DashboardActivity) activity).hasPermission(Constant.APPROVE_ATTENDANCE_REQ_EDIT)) {

                            } else {
                                if (activity != null) {
                                    ((DashboardActivity) activity).showToast(activity.getString(R.string.error_reject_attendance));
                                }
                            }
                        }*/
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    private String getFormattedDate(String toBeParsedDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date date = simpleDateFormat.parse(toBeParsedDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + " " +
                calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(Calendar.YEAR);
    }

    /*private String getFormattedTime(String toBeParsedTime) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date date = simpleDateFormat.parse(toBeParsedTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
    }*/

    private void callRejectService(int id, int employeeId) {
        RetrofitApiService apiService = RetrofitClient.getRetrofitClient();

        if (((DashboardActivity) activity).isInternetAvailable()) {
            Call<RejectAttendance> rejectAttendanceCall = apiService.rejectAttendance(((DashboardActivity) activity).getPreference().getToken(Constant.TOKEN),
                    String.valueOf(id), String.valueOf(employeeId));
            rejectAttendanceCall.enqueue(new Callback<RejectAttendance>() {
                @Override
                public void onResponse(Call<RejectAttendance> call, Response<RejectAttendance> response) {
                    teamPunchRequestFragment.callTeamRequest(month, year);
                }

                @Override
                public void onFailure(Call<RejectAttendance> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            ((DashboardActivity) activity).showToast(activity.getString(R.string.error_internet));
        }
    }

    private void callApprovedWebServices(int id, int employeeId) {
        RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
        Call<ApproveRequestAttendance> approveRequestAttendanceCall = apiService.approveRequestAttendance(((DashboardActivity) activity).getPreference().getToken(Constant.TOKEN), String.valueOf(id),
                String.valueOf(employeeId));

        if (((DashboardActivity) activity).isInternetAvailable()) {
            approveRequestAttendanceCall.enqueue(new Callback<ApproveRequestAttendance>() {
                @Override
                public void onResponse(Call<ApproveRequestAttendance> call, Response<ApproveRequestAttendance> response) {
                    teamPunchRequestFragment.callTeamRequest(month, year);
                }

                @Override
                public void onFailure(Call<ApproveRequestAttendance> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            ((DashboardActivity) activity).showToast(activity.getString(R.string.error_internet));
        }

    }

    /*private void showReasonInputDialog(final Integer value, final Integer id) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        // alertDialog.setTitle("Monthly Budget");
        TextView title = new TextView(activity);
        // You Can Customise your Title here
        title.setText("Enter Reason");
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 12, 0, 0);
        title.setTextSize(18);
        alertDialog.setCustomTitle(title);

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_alert, null);
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
//                        callRejectService(id, reason);
                    } else if (value == 1) {
                        // call cancel service
//                        callCancelService(id, reason);
                    }
                    dialog.dismiss();
                } else {
                    ((DashboardActivity) activity).showToast("Please enter reason");
                }
            }
        });
        dialog.show();
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_reqtr_name, txt_atnd_req_apliedOn, txt_atndReq_reason, txt_atndReq_date,
                team_atndReq_aprvd, team_atndReq_reject;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_atnd_req_apliedOn = (TextView) itemView.findViewById(R.id.txt_atnd_req_apliedOn);
            this.txt_reqtr_name = (TextView) itemView.findViewById(R.id.txt_reqtr_name);
            this.txt_atndReq_reason = (TextView) itemView.findViewById(R.id.txt_atndReq_reason);
            this.txt_atndReq_date = (TextView) itemView.findViewById(R.id.txt_atndReq_date);
            this.team_atndReq_aprvd = (TextView) itemView.findViewById(R.id.team_atndReq_aprvd);
            this.team_atndReq_reject = (TextView) itemView.findViewById(R.id.team_atndReq_reject);
        }
    }
}
