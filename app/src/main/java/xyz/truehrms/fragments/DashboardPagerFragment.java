package xyz.truehrms.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import xyz.truehrms.R;

public class DashboardPagerFragment extends Fragment {
    private TextView punchInTxt, punchOutTxt, dateTxt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragnment_dashboard_pager, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        punchInTxt = (TextView) view.findViewById(R.id.punchInTxt);
        punchOutTxt = (TextView) view.findViewById(R.id.punchOutTxt);
        dateTxt = (TextView) view.findViewById(R.id.dateTxt);
        Calendar calendar = Calendar.getInstance();
        String date = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()) + ", " +
                calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + " " +
                calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(Calendar.YEAR);
        dateTxt.setText(date);
    }

    public void setValue(String punchIN, String punchOUT, String punchDate) {
        if (punchIN.length() > 0 && punchOUT.length() > 0 && punchDate.length() > 0) {
            punchInTxt.setText(punchIN);
            punchOutTxt.setText(punchOUT);

            String format = "yyyy-MM-dd'T'HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
            Date parsed = null;

            try {
                parsed = simpleDateFormat.parse(punchDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsed);
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            SimpleDateFormat dayFormate = new SimpleDateFormat("EEEE", Locale.getDefault());
            SimpleDateFormat MonthFormate = new SimpleDateFormat("MMM", Locale.getDefault());
            String monthFromDate = MonthFormate.format(parsed);
            String dayFromDate = dayFormate.format(parsed);
            String parsed_date = dayFromDate + " " + monthFromDate + " " + day + "," + year;
            dateTxt.setText(parsed_date);
        }
    }
}
