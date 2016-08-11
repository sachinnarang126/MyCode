package xyz.truehrms.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.truehrms.R;
import xyz.truehrms.activities.AttendanceRequestActivity;
import xyz.truehrms.basecontroller.AppCompatFragment;
import xyz.truehrms.bean.LeaveDayTypes;
import xyz.truehrms.bean.LeaveSummary;
import xyz.truehrms.bean.LeaveTypes;
import xyz.truehrms.bean.PersonInCharge;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.parameters.ApplyLeave;
import xyz.truehrms.utils.Constant;
import xyz.truehrms.widgets.CalendarEditText;
import xyz.truehrms.widgets.MaterialSpinner;

public class ApplyLeaveFragment extends AppCompatFragment implements AdapterView.OnItemSelectedListener {
    private EditText address_leave_edt, contact_no_edt, reason_leave_edt;
    private String selectedPersonIncharge = "";
    private int selectedPersonInChargeID;
    private String selectedLvTyp = "";
    private String frmDyTyp = "";
    private String toDyType = "";
    private TextView txt_cl_circle, txt_el_circle, txt_sl_circle;
    private CalendarEditText from_cal, to_cal;
    private MaterialSpinner spinner_leave_type, spinner_start_date, spinner_end_date, spinner_person_in_charge;
    private ProgressBar progressBar;
    private List<String> personInChargesList = new ArrayList<>(), leaveTypeList = new ArrayList<>(), leaveDayTypesList = new ArrayList<>();
    private List<Integer> personInChargesIDList = new ArrayList<>();
    private String token;
    private int Selected_pos = -1;
    private double day, leaveDays;
    private boolean hasLeaveSummary;
    private boolean isVisibleToUser, hasToCallReloadDataAlert;
    private boolean isCompensatoryLeave;
    private boolean isAlertVisible;

    public String changeDateFormatOfString(String inputFormat, String outputFormat, String inputDate) {
        Date parsed;
        String outputDate = "";
        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.getDefault());
        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apply_leave, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        address_leave_edt = (EditText) view.findViewById(R.id.adress_leave_edt);
        reason_leave_edt = (EditText) view.findViewById(R.id.reason_leav_edt);
        contact_no_edt = (EditText) view.findViewById(R.id.contact_no_edt);
        from_cal = (CalendarEditText) view.findViewById(R.id.from_cal);
        to_cal = (CalendarEditText) view.findViewById(R.id.to_cal);
        txt_cl_circle = (TextView) view.findViewById(R.id.txt_cl_circle);
        txt_el_circle = (TextView) view.findViewById(R.id.txt_el_circle);
        token = ((AttendanceRequestActivity) getActivity()).getPreference().getToken(Constant.TOKEN);
        progressBar = (ProgressBar) view.findViewById(R.id.leave_req_progress_bar);
        txt_sl_circle = (TextView) view.findViewById(R.id.txt_sl_circle);
        spinner_start_date = (MaterialSpinner) view.findViewById(R.id.spinr_from_daytype);
        spinner_end_date = (MaterialSpinner) view.findViewById(R.id.spinr_to_daytype);
        spinner_leave_type = (MaterialSpinner) view.findViewById(R.id.spinner_leave_type);
        spinner_person_in_charge = (MaterialSpinner) view.findViewById(R.id.spinner_person_incharge);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        from_cal.setMinDate(calendar);
        to_cal.setMinDate(calendar);

        spinner_leave_type.setOnItemSelectedListener(this);
        spinner_person_in_charge.setOnItemSelectedListener(this);
        spinner_start_date.setOnItemSelectedListener(this);
        spinner_end_date.setOnItemSelectedListener(this);

        loadDataFromServer();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > -1) {
            MaterialSpinner spinner = (MaterialSpinner) parent;
            switch (spinner.getId()) {

                case R.id.spinr_from_daytype:
                    frmDyTyp = String.valueOf(position + 1);
                    Selected_pos = position;
                    if (from_cal.getText().toString().length() > 0) {
                        if (from_cal.getText().toString().equals(to_cal.getText().toString())) {
                            spinner_end_date.setSelection(position + 1);
                        }
                    }
                    break;
                case R.id.spinr_to_daytype:
                    if (from_cal.getText().toString().length() > 0) {
                        if (from_cal.getText().toString().equals(to_cal.getText().toString())) {
                            spinner_end_date.setSelection(Selected_pos + 1);
                            toDyType = String.valueOf(Selected_pos + 1);
                        } else {
                            toDyType = String.valueOf(position + 1);
                        }
                    }
                    break;
                case R.id.spinner_person_incharge:
                    String a[] = personInChargesList.get(position).split("-");
                    for (int i = 0; i < a.length; i++) {
                        if (i == 1) {
                            selectedPersonIncharge = a[i].trim();
                        }
                    }
                    selectedPersonInChargeID = personInChargesIDList.get(position);
                    break;
                case R.id.spinner_leave_type:
                    if (leaveTypeList.get(position).equalsIgnoreCase("Compensatory")) {
                        isCompensatoryLeave = true;
                        spinner_end_date.setSelection(4);
                        spinner_start_date.setSelection(4);
                        spinner_start_date.setEnabled(false);
                        spinner_end_date.setEnabled(false);
                        from_cal.setHint("Leave For");
                        to_cal.setHint("Compensatory Adjustment");
                        toDyType = "4";

                        Calendar calendar = Calendar.getInstance();
                        Calendar calendar1 = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH, 1);
                        from_cal.setMinDate(calendar);

                        calendar1.set(Calendar.DAY_OF_MONTH, 1);
                        calendar1.set(Calendar.MONTH, 0);
                        to_cal.setMinDate(calendar1);

                        calendar1.clear();
                        calendar1 = Calendar.getInstance();
                        calendar1.roll(Calendar.DAY_OF_MONTH, false);
                        to_cal.setMaxDate(calendar1);

                        from_cal.setText("");
                        to_cal.setText("");
                    } else {
                        isCompensatoryLeave = false;
                        from_cal.setHint(getString(R.string.from));
                        to_cal.setHint(getString(R.string.to));
                        spinner_end_date.setSelection(0);
                        spinner_start_date.setSelection(0);
                        spinner_start_date.setEnabled(true);
                        spinner_end_date.setEnabled(true);
                        toDyType = "";

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH, 1);
                        from_cal.setMinDate(calendar);
                        to_cal.setMinDate(calendar);

                        from_cal.setText("");
                        to_cal.setText("");
                    }
                    selectedLvTyp = String.valueOf(position + 1);
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void callLeaveSummeryService() {
        hasLeaveSummary = false;
        RetrofitApiService apiService = RetrofitClient.getRetrofitClient();

        if (((AttendanceRequestActivity) getActivity()).isInternetAvailable()) {
            Call<LeaveSummary> leaveSummeryCall;

            if (!isServiceCallExist(Constant.GET_LEAVE_SUMMERY)) {
                leaveSummeryCall = apiService.getLeaveSummery(token, ((AttendanceRequestActivity) getActivity()).userDetailsObj.getId());
                putServiceCallInServiceMap(leaveSummeryCall, Constant.GET_LEAVE_SUMMERY);
            } else {
                leaveSummeryCall = getServiceCallIfExist(Constant.GET_LEAVE_SUMMERY);

                if (leaveSummeryCall == null) {
                    leaveSummeryCall = apiService.getLeaveSummery(token, ((AttendanceRequestActivity) getActivity()).userDetailsObj.getId());
                    putServiceCallInServiceMap(leaveSummeryCall, Constant.GET_LEAVE_SUMMERY);
                }
            }

            showProgressBar();

            leaveSummeryCall.enqueue(new Callback<LeaveSummary>() {
                @Override
                public void onResponse(Call<LeaveSummary> call, Response<LeaveSummary> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0) {
                            hasLeaveSummary = true;
                            List<LeaveSummary.Result> resultList = response.body().getResult();
                            for (int i = 0; i < resultList.size(); i++) {
                                String LeaveType = resultList.get(i).getLeaveType();
                                switch (LeaveType) {
                                    case "Casual Leave":
                                        txt_cl_circle.setText(String.valueOf(resultList.get(i).getBalance()));
                                        break;
                                    case "Earned Leave":
                                        txt_el_circle.setText(String.valueOf(resultList.get(i).getBalance()));
                                        break;
                                    case "Sick Leave":
                                        txt_sl_circle.setText(String.valueOf(resultList.get(i).getBalance()));
                                        break;
                                }
                            }
                            hideProgressBar();
                        } else {
                            hideProgressBarForceFully();
                            displayDataReloadAlert();
                        }
                    } else {
                        hideProgressBarForceFully();
                        displayDataReloadAlert();
                    }

                }

                @Override
                public void onFailure(Call<LeaveSummary> call, Throwable t) {
                    hasLeaveSummary = false;
                    hideProgressBarForceFully();
                    displayDataReloadAlert();
                    t.printStackTrace();
                }
            });
        } else {
            ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }

    public void getLeaveTypes() {
        if (((AttendanceRequestActivity) getActivity()).isInternetAvailable()) {

            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
            Call<LeaveTypes> leaveTypesCall;

            if (!isServiceCallExist(Constant.GET_LEAVE_TYPES)) {
                leaveTypesCall = retrofitApiService.getLeaveTypes(token);
                putServiceCallInServiceMap(leaveTypesCall, Constant.GET_LEAVE_TYPES);
            } else {
                leaveTypesCall = getServiceCallIfExist(Constant.GET_LEAVE_TYPES);

                if (leaveTypesCall == null) {
                    leaveTypesCall = retrofitApiService.getLeaveTypes(token);
                    putServiceCallInServiceMap(leaveTypesCall, Constant.GET_LEAVE_TYPES);
                }
            }

            leaveTypesCall.enqueue(new Callback<LeaveTypes>() {
                @Override
                public void onResponse(Call<LeaveTypes> call, Response<LeaveTypes> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0) {
                            List<LeaveTypes.Result> rs = response.body().getResult();
                            leaveTypeList.clear();
                            for (int i = 0; i < rs.size(); i++) {
                                leaveTypeList.add(rs.get(i).getName());
                            }
                            String[] list = new String[leaveTypeList.size()];
                            setAdapter(1, leaveTypeList.toArray(list));
                            hideProgressBar();
                        } else {
                            hideProgressBarForceFully();
                            displayDataReloadAlert();
                        }

                    } else {
                        hideProgressBarForceFully();
                        displayDataReloadAlert();
                    }
                }

                @Override
                public void onFailure(Call<LeaveTypes> call, Throwable t) {
                    hideProgressBarForceFully();
                    displayDataReloadAlert();
                    t.printStackTrace();
                }
            });
        } else {
            ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (hasToCallReloadDataAlert && isVisibleToUser) {
            displayDataReloadAlert();
        }
    }

    public void getLeaveDayTypes() {
        RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
        if (((AttendanceRequestActivity) getActivity()).isInternetAvailable()) {

            Call<LeaveDayTypes> leaveDayTypesCall;

            if (!isServiceCallExist(Constant.GET_LEAVE_DAYS_TYPE)) {
                leaveDayTypesCall = retrofitApiService.getLeaveDayTypes(token);
                putServiceCallInServiceMap(leaveDayTypesCall, Constant.GET_LEAVE_DAYS_TYPE);
            } else {
                leaveDayTypesCall = getServiceCallIfExist(Constant.GET_LEAVE_DAYS_TYPE);

                if (leaveDayTypesCall == null) {
                    leaveDayTypesCall = retrofitApiService.getLeaveDayTypes(token);
                    putServiceCallInServiceMap(leaveDayTypesCall, Constant.GET_LEAVE_DAYS_TYPE);
                }
            }

            leaveDayTypesCall.enqueue(new Callback<LeaveDayTypes>() {
                @Override
                public void onResponse(Call<LeaveDayTypes> call, Response<LeaveDayTypes> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0) {
                            List<LeaveDayTypes.Result> rs = response.body().getResult();
                            leaveDayTypesList.clear();
                            for (int i = 1; i < rs.size(); i++) {
                                leaveDayTypesList.add(rs.get(i).getDayType());
                            }
                            String[] list = new String[leaveDayTypesList.size()];
                            list = leaveDayTypesList.toArray(list);
                            setAdapter(2, list);
                            hideProgressBar();
                        } else {
                            hideProgressBarForceFully();
                            displayDataReloadAlert();
                        }

                    } else {
                        hideProgressBarForceFully();
                        displayDataReloadAlert();
                    }
                }

                @Override
                public void onFailure(Call<LeaveDayTypes> call, Throwable t) {
                    hideProgressBarForceFully();
                    displayDataReloadAlert();
                    t.printStackTrace();
                }
            });
        } else {
            ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }

    public void getPersonInCharge() {
        RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
        if (((AttendanceRequestActivity) getActivity()).isInternetAvailable()) {
            Call<PersonInCharge> personInChargeCall;
            if (!isServiceCallExist(Constant.GET_PERSON_IN_CHARGE)) {
                personInChargeCall = retrofitApiService.getPersonInCharge(token, String.valueOf(((AttendanceRequestActivity) getActivity()).userDetailsObj.getId()));
                putServiceCallInServiceMap(personInChargeCall, Constant.GET_PERSON_IN_CHARGE);
            } else {
                personInChargeCall = getServiceCallIfExist(Constant.GET_PERSON_IN_CHARGE);

                if (personInChargeCall == null) {
                    personInChargeCall = retrofitApiService.getPersonInCharge(token, String.valueOf(((AttendanceRequestActivity) getActivity()).userDetailsObj.getId()));
                    putServiceCallInServiceMap(personInChargeCall, Constant.GET_PERSON_IN_CHARGE);
                }
            }

            personInChargeCall.enqueue(new Callback<PersonInCharge>() {
                @Override
                public void onResponse(Call<PersonInCharge> call, Response<PersonInCharge> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0) {
                            List<PersonInCharge.Result> rs = response.body().getResult();
                            personInChargesList.clear();
                            personInChargesIDList.clear();
                            for (int i = 0; i < rs.size(); i++) {
                                personInChargesList.add(rs.get(i).getFirstname());
                                personInChargesIDList.add(rs.get(i).getEmpid());
                            }
                            String[] list = new String[personInChargesList.size()];
                            personInChargesList.toArray(list);
                            setAdapter(3, list);
                            hideProgressBar();
                        } else {
                            hideProgressBarForceFully();
                            displayDataReloadAlert();
                        }
                    } else {
                        hideProgressBarForceFully();
                        displayDataReloadAlert();
                    }
                }

                @Override
                public void onFailure(Call<PersonInCharge> call, Throwable t) {
                    hideProgressBarForceFully();
                    displayDataReloadAlert();
                    t.printStackTrace();
                }
            });
        } else {
            ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }

    public void setAdapter(int type, String list[]) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list);
        switch (type) {
            case 1:
                spinner_leave_type.setAdapter(adapter);
                break;
            case 2:
                spinner_start_date.setAdapter(adapter);
                spinner_end_date.setAdapter(adapter);
                break;
            case 3:
                spinner_person_in_charge.setAdapter(adapter);
                break;
        }
    }

    public void callApplyLeaveService() {
        if (selectedLvTyp.trim().length() == 0) {
            ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.leave_type));
        } else if (from_cal.getText().toString().trim().length() == 0) {
            ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.start_date));
        } else if (to_cal.getText().toString().trim().length() == 0) {
            ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.end_date));
        } else if (frmDyTyp.trim().length() == 0) {
            ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.start_day_type));
        } else if (toDyType.length() == 0) {
            ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.end_day_type));
        } else if (selectedPersonIncharge.trim().length() < 1) {
            ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.select_incharge));
        } else if (reason_leave_edt.getText().toString().trim().length() == 0) {
            ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.enter_reason));
        } else if (!isValidPhoneNumber(contact_no_edt.getText().toString().trim())) {
            ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.valid_contact_no));
        } else if (address_leave_edt.getText().toString().trim().length() == 0) {
            ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.enter_address));
        } else {
            long daysDiff = 0;
            String from_cal_obj = changeDateFormatOfString("dd/MM/yyyy", "MM/dd/yyyy", from_cal.getText().toString());
            String to_cal_obj = changeDateFormatOfString("dd/MM/yyyy", "MM/dd/yyyy", to_cal.getText().toString());
            //////// calculate No of days for leave
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            try {
                Date date1 = sdf.parse(from_cal_obj);
                c1.setTime(date1);
                Date date2 = sdf.parse(to_cal_obj);
                c2.setTime(date2);
                long end = c2.getTimeInMillis();
                long start = c1.getTimeInMillis();

                /*c1 = Calendar.getInstance();
                c1.set(Calendar.HOUR_OF_DAY, 0);
                c1.set(Calendar.MINUTE, 0);
                c1.set(Calendar.SECOND, 0);
                c1.set(Calendar.MILLISECOND, 0);

                System.out.println("start " + start);
                System.out.println("start " + c1.getTimeInMillis());
                if (start < c1.getTimeInMillis()) {
                    ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.error_start_date));
                    return;
                }*/
//                daysDiff = TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
                daysDiff = TimeUnit.MILLISECONDS.toDays(end - start);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (!isCompensatoryLeave && daysDiff < 0) {
                ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.error_date));
                return;
            } /*else if (isCompensatoryLeave && daysDiff > 0) {
                ((AttendanceRequestActivity) getActivity()).showToast("Compensatory date must be less than applying date");
                return;
            }*/ else if (daysDiff == 0) {
                if (toDyType.equalsIgnoreCase("1")) {
                    leaveDays = 0.25;
                } else if (toDyType.equalsIgnoreCase("2")) {
                    leaveDays = 0.5;
                } else if (toDyType.equalsIgnoreCase("3")) {
                    leaveDays = 0.75;
                } else if (toDyType.equalsIgnoreCase("4")) {
                    leaveDays = 1.0;
                }
            } else {
                switch (frmDyTyp) {
                    case "1":
                        if (toDyType.equalsIgnoreCase("1")) {
                            day = 0.5;
                        } else if (toDyType.equalsIgnoreCase("2")) {
                            day = 0.75;
                        } else if (toDyType.equalsIgnoreCase("3")) {
                            day = 1.0;
                        } else if (toDyType.equalsIgnoreCase("4")) {
                            day = 1.25;
                        }
                        break;
                    case "2":
                        if (toDyType.equalsIgnoreCase("1")) {
                            day = 0.75;
                        } else if (toDyType.equalsIgnoreCase("2")) {
                            day = 1.0;
                        } else if (toDyType.equalsIgnoreCase("3")) {
                            day = 1.25;
                        } else if (toDyType.equalsIgnoreCase("4")) {
                            day = 1.5;
                        }
                        break;
                    case "3":
                        if (toDyType.equalsIgnoreCase("1")) {
                            day = 1.0;
                        } else if (toDyType.equalsIgnoreCase("2")) {
                            day = 1.25;
                        } else if (toDyType.equalsIgnoreCase("3")) {
                            day = 1.5;
                        } else if (toDyType.equalsIgnoreCase("4")) {
                            day = 1.75;
                        }
                        break;
                    case "4":
                        if (toDyType.equalsIgnoreCase("1")) {
                            day = 1.25;
                        } else if (toDyType.equalsIgnoreCase("2")) {
                            day = 1.5;
                        } else if (toDyType.equalsIgnoreCase("3")) {
                            day = 1.75;
                        } else if (toDyType.equalsIgnoreCase("4")) {
                            day = 2.0;
                        }
                        break;
                }
                leaveDays = (daysDiff - 1.0) + day;
            }
            ApplyLeave applyLeave = new ApplyLeave();
            applyLeave.setAddress(address_leave_edt.getText().toString());
            applyLeave.setContactnumber(contact_no_edt.getText().toString());
            applyLeave.setEmpcode(((AttendanceRequestActivity) getActivity()).userDetailsObj.getEmpcode());
            applyLeave.setEmployee_id(String.valueOf(((AttendanceRequestActivity) getActivity()).userDetailsObj.getId()));
            applyLeave.setFirstD_Type(frmDyTyp);
            applyLeave.setLastD_Type(toDyType);
            applyLeave.setTypeofleave(selectedLvTyp);
            applyLeave.setTodate(to_cal_obj);
            applyLeave.setPersonincharge(selectedPersonIncharge);
            applyLeave.setLeavedays(String.valueOf(leaveDays));
            applyLeave.setFromdate(from_cal_obj);
            applyLeave.setReason(reason_leave_edt.getText().toString());

            applyLeave.setSelectedPersionInchage(selectedPersonIncharge);
            applyLeave.setPersonincharge_id(selectedPersonInChargeID);
            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();

            if (((AttendanceRequestActivity) getActivity()).isInternetAvailable()) {
                Call<xyz.truehrms.bean.ApplyLeave> applyLeaveCall = retrofitApiService.applyLeave(token, applyLeave);

                putServiceCallInServiceMap(applyLeaveCall, Constant.APPLY_LEAVE);
                showProgressBar();
                ((AttendanceRequestActivity) getActivity()).setIsExecutingService(true);

                applyLeaveCall.enqueue(new Callback<xyz.truehrms.bean.ApplyLeave>() {
                    @Override
                    public void onResponse(Call<xyz.truehrms.bean.ApplyLeave> call, Response<xyz.truehrms.bean.ApplyLeave> response) {
                        hideProgressBarForceFully();
                        if (response.isSuccessful()) {
                            if (response.body().getStatusCode() == 200.0) {
                                try {
                                    if (isAdded() && getActivity() != null)
                                        ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.submit_success));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    if (isAdded() && getActivity() != null)
                                        ((AttendanceRequestActivity) getActivity()).showToast(response.body().getErrors().toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<xyz.truehrms.bean.ApplyLeave> call, Throwable t) {
                        hideProgressBarForceFully();
                        t.printStackTrace();
                    }
                });
            } else {
                ((AttendanceRequestActivity) getActivity()).showToast(getString(R.string.error_internet));
            }
        }
    }

    private void hideProgressBar() {
        if (hasLeaveSummary && leaveTypeList.size() > 0 && leaveDayTypesList.size() > 0 && personInChargesList.size() > 0) {
            ((AttendanceRequestActivity) getActivity()).setIsExecutingService(false);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBarForceFully() {
        if (progressBar.getVisibility() == View.VISIBLE)
            progressBar.setVisibility(View.GONE);
    }

    private void displayDataReloadAlert() {
        try {
            if (!isAlertVisible && isVisibleToUser && getActivity() != null) {
                hasToCallReloadDataAlert = false;
                isAlertVisible = true;
                new AlertDialog.Builder(getActivity())
                        .setTitle("True HR")
                        .setMessage("Error receiving data from server, Reload Data...?")
                        .setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isAlertVisible = false;
                                dialog.dismiss();
                                loadDataFromServer();
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isAlertVisible = false;
                                dialog.dismiss();
                                getActivity().finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                hasToCallReloadDataAlert = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromServer() {
        ((AttendanceRequestActivity) getActivity()).setIsExecutingService(true);
        callLeaveSummeryService();
        getLeaveTypes();
        getLeaveDayTypes();
        getPersonInCharge();
    }

    private boolean isValidPhoneNumber(CharSequence target) {
//        return !TextUtils.isEmpty(target) && android.util.Patterns.PHONE.matcher(target).matches();
        return target.length() > 10;
    }
}
