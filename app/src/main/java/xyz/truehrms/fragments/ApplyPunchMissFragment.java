package xyz.truehrms.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.truehrms.R;
import xyz.truehrms.activities.AttendanceRequestActivity;
import xyz.truehrms.basecontroller.AppCompatFragment;
import xyz.truehrms.parameters.PunchMiss;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;

public class ApplyPunchMissFragment extends AppCompatFragment implements View.OnClickListener {
    private final int TIME_DIALOG_ID = 1111;
    private EditText reason_punch_miss_edt;
    private EditText punch_miss_timeout, punch_miss_date, punch_miss_timein;
    private ProgressBar progressBar;
    private boolean isTimeInClicked;
    private String serviceDatePattern;

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            int hour;
            String am_pm;

            switch (hourOfDay) {
                case 0:
                    hour = 12;
                    am_pm = "AM";
                    break;

                case 12:
                    hour = 12;
                    am_pm = "PM";
                    break;

                default:
                    if (hourOfDay > 12) {
                        hour = hourOfDay - 12;
                        am_pm = "PM";
                    } else {
                        hour = hourOfDay;
                        am_pm = "AM";
                    }
                    break;
            }

            String strMinute = String.valueOf(minute);

            if (strMinute.length() == 1) {
                strMinute = "0" + strMinute;
            }

            String date = hour + ":" + strMinute + " " + am_pm;
            if (isTimeInClicked) {
                punch_miss_timein.setText(date);
            } else {
                punch_miss_timeout.setText(date);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apply_punch_miss, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        punch_miss_timeout = (EditText) view.findViewById(R.id.punch_miss_timeout);
        punch_miss_timein = (EditText) view.findViewById(R.id.punch_miss_timein);
        punch_miss_date = (EditText) view.findViewById(R.id.punch_miss_date);
        reason_punch_miss_edt = (EditText) view.findViewById(R.id.reason_punch_miss_edt);
        // time_picker_img = (ImageView) view.findViewById(R.id.time_picker_img);
        progressBar = (ProgressBar) view.findViewById(R.id.punch_miss_progress_bar);

        ImageView img_clock_time_in = (ImageView) view.findViewById(R.id.img_clock_time_in);
        ImageView img_clock_time_out = (ImageView) view.findViewById(R.id.img_clock_time_out);
        img_clock_time_out.setOnClickListener(this);
        img_clock_time_in.setOnClickListener(this);

        // set punch miss screen
        Intent intent = getActivity().getIntent();
        String parseDate = intent.getStringExtra("Date");
        String parseTimeout = intent.getStringExtra("Timeout");
        String parseTimein = intent.getStringExtra("Timein");
        setAdditionalParams(parseDate, parseTimeout, parseTimein);
    }

    private void setParams(String date, String timeIn, String timeout) {
        punch_miss_date.setText(date);
        punch_miss_timein.setText(timeIn);
        punch_miss_timeout.setText(timeout);
        /*if (timeout.matches("0:0")) {
            isTimeOut = true;
            punch_miss_timeout.setOnClickListener(this);
        } else {
            isTimeOut = false;
            punch_miss_timeout.setFocusable(false);

        }*/
        /*if (timein.matches("0:0")) {
            isTimeIn = true;
            punch_miss_timein.setOnClickListener(this);
        } else {
            isTimeIn = false;
            punch_miss_timein.setFocusable(false);
        }*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_clock_time_in:
                createDialog(TIME_DIALOG_ID).show();
                isTimeInClicked = true;
                break;
            case R.id.img_clock_time_out:
                createDialog(TIME_DIALOG_ID).show();
                isTimeInClicked = false;
                break;
        }
    }

    public void setAdditionalParams(String parseDate, String parseTimeout, String parseTimeIn) {
        if (parseDate != null){
            String punchFormat = "yyyy-MM-dd'T'HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(punchFormat, Locale.getDefault());
            try {
                Date parsed = simpleDateFormat.parse(parseDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(parsed);
                parseDate = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + ", " + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
                serviceDatePattern = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*try {
            Date parsed = simpleDateFormat.parse(parseTimeIn);
            Integer hours = parsed.getHours();
            Integer min = parsed.getMinutes();
            timeIn = hours.toString() + ":" + min.toString();
            // ReqTime=hours.toString()+":"+min.toString()+":"+"00";
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Date parsed = simpleDateFormat.parse(parseTimeout);
            Integer hours = parsed.getHours();
            Integer min = parsed.getMinutes();
            timeOut = hours.toString() + ":" + min.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        setParams(parseDate, parseTimeIn, parseTimeout);
    }

    private Dialog createDialog(int timeDialogId) {
        Calendar calendar = Calendar.getInstance();
        switch (timeDialogId) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(getActivity(), timePickerListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), false);
        }
        return null;
    }

    public void callPunchMissService() {
//        if (isTimeIn || isTimeOut) {
        if (((AttendanceRequestActivity) getActivity()).isInternetAvailable()) {
            ((AttendanceRequestActivity) getActivity()).setIsExecutingService(true);
            progressBar.setVisibility(View.VISIBLE);
            PunchMiss punchMiss = new PunchMiss();
            punchMiss.setPunchDate(serviceDatePattern);

            if (reason_punch_miss_edt.getText().length() == 0) {
                ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.empty_punch));
                return;
            } else {
                punchMiss.setReason(reason_punch_miss_edt.getText().toString().trim());
            }

            boolean isTimeOut = false, isTimeIn = false;
            punchMiss.setIspunchout(String.valueOf(isTimeOut));
            punchMiss.setIspunchin(String.valueOf(isTimeIn));
            punchMiss.setPunchOutTime(punch_miss_timeout.getText().toString());
            punchMiss.setPunchInTime(punch_miss_timein.getText().toString());
            punchMiss.setAttendanceid("0");
        /*if (isTimeOut) {
            punchMiss.setPunchOutTime(punch_miss_timeout.getText().toString() + ":" + "00");
        } else {
            punchMiss.setPunchOutTime(timeOut + ":" + "00");
        }*/
        /*if (isTimeIn) {
            punchMiss.setPunchInTime(punch_miss_timein.getText().toString() + ":" + "00");
        } else {
            punchMiss.setPunchInTime(timeIn + ":" + "00");
        }*/
            punchMiss.setEmployee_id(String.valueOf(((AttendanceRequestActivity) getActivity()).userDetailsObj.getId()));
            punchMiss.setEmployeemanager_id(String.valueOf(((AttendanceRequestActivity) getActivity()).userDetailsObj.getManagerId()));
            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();

            Call<xyz.truehrms.bean.PunchMiss> punchMissCall = retrofitApiService.punchMiss(((AttendanceRequestActivity) getActivity()).getPreference().getToken(Constant.TOKEN), punchMiss);
            putServiceCallInServiceMap(punchMissCall, Constant.PUNCH_MISS);

            punchMissCall.enqueue(new Callback<xyz.truehrms.bean.PunchMiss>() {
                @Override
                public void onResponse(Call<xyz.truehrms.bean.PunchMiss> call, Response<xyz.truehrms.bean.PunchMiss> response) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        if (isAdded() && getActivity() != null) {
                            if (response.isSuccessful() && response.body().getStatusCode() == 200.0) {
                                ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.submit_success));
                            } else {
                                ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.server_error));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ((AttendanceRequestActivity) getActivity()).setIsExecutingService(false);
                    getActivity().finish();
                }

                @Override
                public void onFailure(Call<xyz.truehrms.bean.PunchMiss> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    ((AttendanceRequestActivity) getActivity()).setIsExecutingService(false);
                    if (!isDetached())
                        ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.server_error));
                    t.printStackTrace();
                }
            });
        } else {
            ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }
}
