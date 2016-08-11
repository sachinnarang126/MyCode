package xyz.truehrms.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.truehrms.R;
import xyz.truehrms.activities.DashboardActivity;
import xyz.truehrms.adapters.MyLeaveRequestAdapter;
import xyz.truehrms.basecontroller.AppCompatFragment;
import xyz.truehrms.bean.EmployeeListForTeamLead;
import xyz.truehrms.bean.MyLeaveRequests;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.parameters.Parameters;
import xyz.truehrms.utils.Constant;
import xyz.truehrms.widgets.EndlessRecyclerOnScrollListener;

public class OthersLeaveRequestFragment extends AppCompatFragment implements AdapterView.OnItemSelectedListener {
    private Spinner spinr_month, spinr_year;
    private RecyclerView other_leave_req_list;
    private String token;
    private ProgressBar progressBar;
    private Button btn_punch_load_more;
    private AutoCompleteTextView otherPunchEmpName;
    private String year, currentYear;
    private LinearLayoutManager linearLayoutManager;
    private List<MyLeaveRequests.Result.LeaveListResult> resultList;
    private int spinnerInitCount = 0;
    private int month, currentMonth;
    private ArrayAdapter adapter_Employee;
    private List<String> employeeNameList;
    private String empID;
    private MyLeaveRequestAdapter myLeaveRequestAdapter;
    private RelativeLayout container_no_data;
    private ImageButton img_reload;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_other_leave_request, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        other_leave_req_list = (RecyclerView) view.findViewById(R.id.leave_req_team_list);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        other_leave_req_list.setLayoutManager(linearLayoutManager);
        otherPunchEmpName = (AutoCompleteTextView) view.findViewById(R.id.other_punch_emp_name);
        spinr_month = (Spinner) view.findViewById(R.id.spinr_month_team);
        btn_punch_load_more = (Button) view.findViewById(R.id.btn_punch_load_more);
        spinr_year = (Spinner) view.findViewById(R.id.spinr_year_team);
        progressBar = (ProgressBar) view.findViewById(R.id.team_leavereq_progress);
        token = ((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN);
        ArrayAdapter<String> adapter_months = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.month_array));


        spinr_year.setOnItemSelectedListener(this);
        spinr_month.setOnItemSelectedListener(this);

        Calendar calendar = Calendar.getInstance();
        month = currentMonth = calendar.get(Calendar.MONTH);
        year = currentYear = String.valueOf(calendar.get(Calendar.YEAR));

        container_no_data = (RelativeLayout) view.findViewById(R.id.container_no_data);

        img_reload = (ImageButton) view.findViewById(R.id.img_reload);
        img_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callService(empID, String.valueOf(month + 1), year);
            }
        });

        spinr_month.setAdapter(adapter_months);
        spinr_month.setSelection(month);

        Calendar calendarForYear = Calendar.getInstance();
        calendarForYear.set(Calendar.YEAR, 2015);

        ArrayList<String> yearArrayList = new ArrayList<>();
        while (calendarForYear.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR)) {
            yearArrayList.add(String.valueOf(calendarForYear.get(Calendar.YEAR)));
            calendarForYear.roll(Calendar.YEAR, true);
        }

        ArrayAdapter<String> adapter_year = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, yearArrayList);
        spinr_year.setAdapter(adapter_year);
        spinr_year.setSelection(yearArrayList.size() - 1);

        empID = String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId());

        if (((DashboardActivity) getActivity()).userDetailsObj.getFirstname() != null) {
            String empName = ((DashboardActivity) getActivity()).userDetailsObj.getFirstname() + "(" + ((DashboardActivity) getActivity()).userDetailsObj.getEmpcode().toString() + ")";
            otherPunchEmpName.setText(empName);
        }

        otherPunchEmpName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String SelectedText = otherPunchEmpName.getText().toString().trim();
                int start = otherPunchEmpName.getText().toString().trim().indexOf("(");
                int end = otherPunchEmpName.getText().toString().trim().indexOf(")");
                empID = SelectedText.substring(start + 1, end);
                callService(empID, String.valueOf(month + 1), year);

            }
        });
        getListOfEmployee("''");
        callService(empID, String.valueOf(month + 1), year);
    }


    private void getListOfEmployee(String str) {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
            Call<EmployeeListForTeamLead> employeeListCall;

            if (!isServiceCallExist(Constant.GET_EMPLOYEES_BY_NAME_OR_EMP_CODE)) {
                employeeListCall = retrofitApiService.getEmployeesByNameorEmpCode(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), str);
                putServiceCallInServiceMap(employeeListCall, Constant.GET_EMPLOYEES_BY_NAME_OR_EMP_CODE);
            } else {
                employeeListCall = getServiceCallIfExist(Constant.GET_EMPLOYEES_BY_NAME_OR_EMP_CODE);
                if (employeeListCall == null) {
                    employeeListCall = retrofitApiService.getEmployeesByNameorEmpCode(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), str);
                    putServiceCallInServiceMap(employeeListCall, Constant.GET_EMPLOYEES_BY_NAME_OR_EMP_CODE);
                }
            }

            employeeListCall.enqueue(new Callback<EmployeeListForTeamLead>() {
                @Override
                public void onResponse(Call<EmployeeListForTeamLead> call, Response<EmployeeListForTeamLead> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0) {
                            employeeNameList = response.body().getResult();
                            adapter_Employee = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, employeeNameList);
                            otherPunchEmpName.setAdapter(adapter_Employee);
                        }
                    }
                }

                @Override
                public void onFailure(Call<EmployeeListForTeamLead> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinnerInitCount < 2) {
            spinnerInitCount++;
        } else {
            Spinner spinner = (Spinner) parent;
            switch (spinner.getId()) {
                case R.id.spinr_year_team:
                    month = spinr_month.getSelectedItemPosition();
                    year = spinr_year.getSelectedItem().toString();
                    callService(empID, String.valueOf(month + 1), year);
                    break;
                case R.id.spinr_month_team:
                    year = spinr_year.getSelectedItem().toString();
                    if (position > currentMonth && currentYear.equalsIgnoreCase(year)) {
                        clearItemOfRecyclerView();
                    } else {
                        month = position;
                        callService(empID, String.valueOf(month + 1), year);
                    }
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void callService(final String empcodce, final String m, final String y) {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

            clearItemOfRecyclerView();
            progressBar.setVisibility(View.VISIBLE);
            token = ((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN);

            Parameters pendingLeaveRequestParams = new Parameters();
            pendingLeaveRequestParams.setEmployeeID(empcodce);
            pendingLeaveRequestParams.setMonth(m);
            pendingLeaveRequestParams.setYear(y);
            pendingLeaveRequestParams.setSearchInput("");
            pendingLeaveRequestParams.setPageSize("10");
            pendingLeaveRequestParams.setPageIndex("1");

            RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
            Call<MyLeaveRequests> myLeaveRequestsCall = apiService.myLeaveRequests(token, pendingLeaveRequestParams);

            putServiceCallInServiceMap(myLeaveRequestsCall, Constant.MY_LEAVE_REQUESTS);
            hideReloadData();

            myLeaveRequestsCall.enqueue(new Callback<MyLeaveRequests>() {
                @Override
                public void onResponse(Call<MyLeaveRequests> call, Response<MyLeaveRequests> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0 && response.body().getResult() != null) {
                            if (response.body().getResult().getLeaveListResult() != null &&
                                    response.body().getResult().getLeaveListResult().size() > 0) {

                                if (resultList != null) {
                                    resultList.clear();
                                    resultList.addAll(response.body().getResult().getLeaveListResult());
                                    myLeaveRequestAdapter.notifyDataSetChanged();
                                } else {
                                    resultList = response.body().getResult().getLeaveListResult();
                                    myLeaveRequestAdapter = new MyLeaveRequestAdapter(resultList);
                                    other_leave_req_list.setAdapter(myLeaveRequestAdapter);
                                }

                                progressBar.setVisibility(View.GONE);
                                final int totalRecord = response.body().getResult().getCount();

                                other_leave_req_list.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
                                    @Override
                                    public void scroolabove(int lastVisibleItemPosition) {
                                    }

                                    @Override
                                    public void onLoadMore(final int current_page) {
                                        if (totalRecord > ((current_page - 1) * 10)) {
                                            btn_punch_load_more.setVisibility(View.VISIBLE);
                                        } else {
                                            btn_punch_load_more.setVisibility(View.GONE);
                                        }
                                        btn_punch_load_more.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (((DashboardActivity) getActivity()).isInternetAvailable()) {
                                                    btn_punch_load_more.setVisibility(View.GONE);

                                                    RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
                                                    Parameters pendngLevReqParams = new Parameters();

                                                    pendngLevReqParams.setEmployeeID(empcodce);
                                                    pendngLevReqParams.setMonth(m);
                                                    pendngLevReqParams.setYear(y);
                                                    pendngLevReqParams.setSearchInput("");
                                                    pendngLevReqParams.setPageSize("10");
                                                    pendngLevReqParams.setPageIndex(String.valueOf(current_page));

                                                    Call<MyLeaveRequests> myLeaveRequestsCall = apiService.myLeaveRequests(token, pendngLevReqParams);
                                                    putServiceCallInServiceMap(myLeaveRequestsCall, Constant.MY_LEAVE_REQUESTS);
                                                    myLeaveRequestsCall.enqueue(new Callback<MyLeaveRequests>() {
                                                        @Override
                                                        public void onResponse(Call<MyLeaveRequests> call, Response<MyLeaveRequests> response) {
                                                            if (response.isSuccessful()) {
                                                                if (response.body().getResult().getLeaveListResult() != null) {
                                                                    resultList.addAll(response.body().getResult().getLeaveListResult());
                                                                    myLeaveRequestAdapter.notifyDataSetChanged();
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<MyLeaveRequests> call, Throwable t) {
                                                            t.printStackTrace();
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                });
                            } else {
                                clearItemOfRecyclerView();
                                btn_punch_load_more.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                                showReloadData(View.INVISIBLE);
                            }
                        } else {
                            clearItemOfRecyclerView();
                            btn_punch_load_more.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            showReloadData(View.VISIBLE);
                        }
                    } else {
                        clearItemOfRecyclerView();
                        btn_punch_load_more.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        showReloadData(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<MyLeaveRequests> call, Throwable t) {
                    t.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                    showReloadData(View.VISIBLE);
                }
            });
        } else {
            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }

    private void clearItemOfRecyclerView() {
        if (resultList != null) {
            resultList.clear();
            if (myLeaveRequestAdapter != null) {
                myLeaveRequestAdapter.notifyDataSetChanged();
            }
        }
    }

    private void showReloadData(int visibility) {
        container_no_data.setVisibility(View.VISIBLE);
        other_leave_req_list.setVisibility(View.GONE);
        img_reload.setVisibility(visibility);
    }


    private void hideReloadData() {
        container_no_data.setVisibility(View.GONE);
        other_leave_req_list.setVisibility(View.VISIBLE);
    }
}