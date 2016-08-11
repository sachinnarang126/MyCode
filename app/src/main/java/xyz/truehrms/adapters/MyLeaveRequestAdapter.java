package xyz.truehrms.adapters;

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

import xyz.truehrms.R;
import xyz.truehrms.bean.MyLeaveRequests;


public class MyLeaveRequestAdapter extends RecyclerView.Adapter<MyLeaveRequestAdapter.ViewHolder> {

    private List<MyLeaveRequests.Result.LeaveListResult> resultList;

    public MyLeaveRequestAdapter(List<MyLeaveRequests.Result.LeaveListResult> leaveListResult) {
        this.resultList = leaveListResult;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_leave_request, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (resultList != null) {
            if (resultList.get(position).getIsapproved() == 0) {
                holder.txt_aprvd_rejeted.setText("Pending");
            } else if (resultList.get(position).getIsapproved() == 1) {
                holder.txt_aprvd_rejeted.setText("Approved");
            } else {
                holder.txt_aprvd_rejeted.setText("Rejected");
            }

            String leaveType = resultList.get(position).getLeavetype();
            if (resultList.get(position).getTypeofleave() == 4) {
                try {
                    leaveType += "(" + getFormattedDate(resultList.get(position).getComplimentaryAgainst()) + ")";
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            holder.txt_leave_type.setText(leaveType);

            holder.txt_atnd_req_team_msg.setText(resultList.get(position).getReason());
            String appliedTo = "Applied To: " + resultList.get(position).getManagerName();
            holder.txt_atnd_apliedTo.setText(appliedTo);

            try {
                String text = "Applied On: " + getFormattedDate(resultList.get(position).getRequestedon());
                holder.txt_atnd_apliedOn.setText(text);
            } catch (ParseException e) {
//                e.printStackTrace();
            }

            try {
                holder.tvPersonName.setVisibility(View.VISIBLE);
                String date = getFormattedDate(resultList.get(position).getFromdate()) + " - " + getFormattedDate(resultList.get(position).getTodate());
                holder.tvPersonName.setText(date);
            } catch (ParseException e) {
//                e.printStackTrace();
                holder.tvPersonName.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    private String getFormattedDate(String toBeParsedDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date date = simpleDateFormat.parse(toBeParsedDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + " " +
                calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(Calendar.YEAR);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_aprvd_rejeted, txt_atnd_req_team_msg, txt_atnd_apliedOn, txt_atnd_apliedTo, txt_leave_type, tvPersonName;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_aprvd_rejeted = (TextView) itemView.findViewById(R.id.txt_aprvd_rejeted);
            txt_atnd_req_team_msg = (TextView) itemView.findViewById(R.id.txt_atnd_req_team_msg);
            txt_atnd_apliedOn = (TextView) itemView.findViewById(R.id.txt_atnd_apliedOn);
            txt_atnd_apliedTo = (TextView) itemView.findViewById(R.id.txt_atnd_apliedTo);
            txt_leave_type = (TextView) itemView.findViewById(R.id.txt_leave_type);
            tvPersonName = (TextView) itemView.findViewById(R.id.txt_time);
        }
    }
}
