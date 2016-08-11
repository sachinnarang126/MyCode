package xyz.truehrms.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import xyz.truehrms.R;
import xyz.truehrms.activities.AttendanceRequestActivity;
import xyz.truehrms.activities.DashboardActivity;
import xyz.truehrms.bean.AllAttendance;
import xyz.truehrms.utils.Constant;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
    private Activity activity;
    private SimpleDateFormat formatter;
    private List<AllAttendance.Result.AaDatum> attendanceList;
    private boolean isCurrentUserEmpCode;

    public AttendanceAdapter(Activity activity, List<AllAttendance.Result.AaDatum> attendanceList, boolean isCurrentUserEmpCode) {
        this.activity = activity;
        this.attendanceList = attendanceList;
        this.isCurrentUserEmpCode = isCurrentUserEmpCode;
        formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_attendance_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final AllAttendance.Result.AaDatum attendanceObj = attendanceList.get(position);
        String toParse;
        Calendar calendar = Calendar.getInstance();
        try {
            holder.total_hrs.setText(attendanceObj.getTotalhrs());
            toParse = attendanceObj.getPunchdate();
            Date parsed = formatter.parse(toParse);
            calendar.setTime(parsed);

            String detailDate = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + " " +
                    calendar.get(Calendar.DAY_OF_MONTH) + "," + calendar.get(Calendar.YEAR);

            holder.txt_atnd_detail_day.setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
            holder.txt_atnd_detail_date.setText(detailDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String timeIn = attendanceObj.getPunchintime();
            if (timeIn.matches("00:00") || timeIn.isEmpty()) {
                holder.txt_atnd_detail_timein.setText(activity.getString(R.string.miss));
                holder.txt_atnd_detail_timein.setTextColor(ContextCompat.getColor(activity, R.color.red));
            } else {
                holder.txt_atnd_detail_timein.setText(timeIn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            final String timeout = attendanceObj.getPunchouttime();
            if (timeout.matches("00:00") || timeout.isEmpty()) {
                holder.txt_atnd_detail_timeout.setText(activity.getString(R.string.miss));
                holder.txt_atnd_detail_timeout.setTextColor(ContextCompat.getColor(activity, R.color.red));
            } else {
                holder.txt_atnd_detail_timeout.setText(timeout);
            }

            holder.txt_atnd_detail_timein.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.txt_atnd_detail_timein.getText().toString().contentEquals("miss")) {
                        if (isCurrentUserEmpCode) {
                            if (((DashboardActivity) activity).getPreference().hasAdminControl()) {
                                Intent intent = new Intent(activity, AttendanceRequestActivity.class);
                                intent.putExtra("Date", attendanceObj.getPunchdate());

                                // intent.putExtra("istime_out", false);
                                intent.putExtra("Timeout", attendanceObj.getPunchouttime());
                                intent.putExtra("Timein", attendanceObj.getPunchintime());
                                activity.startActivity(intent);
                            } else {
                                if (((DashboardActivity) activity).hasPermission(Constant.VIEW_ATTENDANCE_EDIT)) {
                                    Intent intent = new Intent(activity, AttendanceRequestActivity.class);
                                    intent.putExtra("Date", attendanceObj.getPunchdate());

                                    // intent.putExtra("istime_out", false);
                                    intent.putExtra("Timeout", attendanceObj.getPunchouttime());
                                    intent.putExtra("Timein", attendanceObj.getPunchintime());
                                    activity.startActivity(intent);
                                } else {
                                    ((DashboardActivity) activity).showToast(activity.getString(R.string.error_edit_attendance));
                                }
                            }
                        } else {
                            ((DashboardActivity) activity).showToast(activity.getString(R.string.error_edit_other_attendance));
                        }
                    }
                }
            });

            holder.txt_atnd_detail_timeout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.txt_atnd_detail_timeout.getText().toString().contentEquals("miss")) {
                        if (isCurrentUserEmpCode) {
                            if (((DashboardActivity) activity).getPreference().hasAdminControl()) {
                                Intent intent = new Intent(activity, AttendanceRequestActivity.class);
                                intent.putExtra("Date", attendanceObj.getPunchdate());
                                intent.putExtra("Timeout", attendanceObj.getPunchouttime());
                                intent.putExtra("Timein", attendanceObj.getPunchintime());
                                activity.startActivity(intent);
                            } else {
                                if (((DashboardActivity) activity).hasPermission(Constant.VIEW_ATTENDANCE_EDIT)) {
                                    Intent intent = new Intent(activity, AttendanceRequestActivity.class);
                                    intent.putExtra("Date", attendanceObj.getPunchdate());
                                    intent.putExtra("Timeout", attendanceObj.getPunchouttime());
                                    intent.putExtra("Timein", attendanceObj.getPunchintime());
                                    activity.startActivity(intent);
                                } else {
                                    ((DashboardActivity) activity).showToast(activity.getString(R.string.error_edit_attendance));
                                }
                            }
                        } else {
                            ((DashboardActivity) activity).showToast(activity.getString(R.string.error_edit_other_attendance));
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView total_hrs, txt_atnd_detail_day, txt_atnd_detail_date, txt_atnd_detail_timein, txt_atnd_detail_timeout/*, txt_atnd_detail_leavetype*/;

        public ViewHolder(View itemView) {
            super(itemView);
            total_hrs = (TextView) itemView.findViewById(R.id.total_hrs);
            txt_atnd_detail_day = (TextView) itemView.findViewById(R.id.txt_atnd_detail_day);
//            txt_atnd_detail_leavetype = (TextView) itemView.findViewById(R.id.txt_atnd_detail_leavetype);
            txt_atnd_detail_date = (TextView) itemView.findViewById(R.id.txt_atnd_detail_date);
            txt_atnd_detail_timein = (TextView) itemView.findViewById(R.id.txt_atnd_detail_timein);
            txt_atnd_detail_timeout = (TextView) itemView.findViewById(R.id.txt_atnd_detail_timeout);
        }
    }
}
