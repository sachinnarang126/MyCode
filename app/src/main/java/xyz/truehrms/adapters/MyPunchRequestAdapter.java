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
import xyz.truehrms.bean.MyAttendanceRequest;

public class MyPunchRequestAdapter extends RecyclerView.Adapter<MyPunchRequestAdapter.ViewHolder> {
    private List<MyAttendanceRequest.Result.AaDatum> result;

    public MyPunchRequestAdapter(List<MyAttendanceRequest.Result.AaDatum> result) {
        this.result = result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_attendance_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (result != null) {
            String isApproved = result.get(position).getIsapproved();
            switch (isApproved) {
                case "0":
                    holder.txt_aprvd_rejeted.setText("Pending");
                    break;

                case "1":
                    holder.txt_aprvd_rejeted.setText("Approved");
                    break;

                case "2":
                    holder.txt_aprvd_rejeted.setText("Rejected");
                    break;
            }

            holder.txt_atnd_req_team_msg.setText(result.get(position).getPunchdescription());
            holder.txt_atnd_apliedTo.setText(result.get(position).getManagerName());
            String time;

            if (result.get(position).getPunchtype() == 1) {
                time = "Punch In";
            } else {
                time = "Punch Out";
            }
            holder.txt_atnd_date.setText(time);

            try {
                String text = "Applied on:" + getFormattedDate(result.get(position).getCreatedon());
                holder.txt_atnd_apliedOn.setText(text);
                text = getFormattedDate(result.get(position).getPunchdate()) + " (" + result.get(position).getPunchtime() + ")";
                holder.txt_time.setText(text);
            } catch (ParseException e) {
//                e.printStackTrace();
            }
        }
        //   txt_atnd_apliedTo.setText("AppliedTo:"+result.get(position).getManagerName().toString());
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
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date date = simpleDateFormat.parse(toBeParsedTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Integer yer = calendar.get(Calendar.HOUR);
        String year = yer.toString();
        Integer day = calendar.get(Calendar.MINUTE);
//        SimpleDateFormat MonthFormate = new SimpleDateFormat("MMM",Locale.getDefault());
//        String monthFromDate = MonthFormate.format(date);

        return year + ":" + day;
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_aprvd_rejeted, txt_atnd_req_team_msg, txt_atnd_apliedOn, txt_atnd_apliedTo, txt_atnd_date, txt_time;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_aprvd_rejeted = (TextView) itemView.findViewById(R.id.txt_aprvd_rejeted);
            txt_atnd_req_team_msg = (TextView) itemView.findViewById(R.id.txt_atnd_req_team_msg);
            txt_atnd_apliedOn = (TextView) itemView.findViewById(R.id.txt_atnd_apliedOn);
            txt_atnd_apliedTo = (TextView) itemView.findViewById(R.id.txt_atnd_apliedTo);
            txt_atnd_date = (TextView) itemView.findViewById(R.id.txt_punch);
            txt_time = (TextView) itemView.findViewById(R.id.txt_time);
        }
    }
}
