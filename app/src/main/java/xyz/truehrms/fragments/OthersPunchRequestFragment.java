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
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.truehrms.R;
import xyz.truehrms.activities.DashboardActivity;
import xyz.truehrms.adapters.MyPunchRequestAdapter;
import xyz.truehrms.basecontroller.AppCompatFragment;
import xyz.truehrms.bean.EmployeeListForTeamLead;
import xyz.truehrms.bean.MyAttendanceRequest;
import xyz.truehrms.parameters.Parameters;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;
import xyz.truehrms.widgets.EndlessRecyclerOnScrollListener;

public class OthersPunchRequestFragment extends AppCompatFragment implements AdapterView.OnItemSelectedListener {
    private LinearLayoutManager linearLayoutManager;
    private List<MyAttendanceRequest.Result.AaDatum> datumList;
    private Spinner spinr_month, spinr_year;
    private RecyclerView recyclerView;
    private String token;
    private ProgressBar progressBar;
    private Button btn_punch_load_more;
    private AutoCompleteTextView other_punch_emp_name;
    private String year, currentYear;
    private int spinnerInitCount = 0;
    private int month, currentMonth;
    //    private List<String> employeeNameList;
    private HashMap<String, String> employeeIDMap;
    private String empID;
    private MyPunchRequestAdapter myPunchRequestAdapter;
    private RelativeLayout container_no_data;
    private ImageButton img_reload;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_other_punch_request, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.leave_req_team_list);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        other_punch_emp_name = (AutoCompleteTextView) view.findViewById(R.id.other_punch_emp_name);
        spinr_month = (Spinner) view.findViewById(R.id.spinr_month_team);
        btn_punch_load_more = (Button) view.findViewById(R.id.btn_punch_load_more);
        spinr_year = (Spinner) view.findViewById(R.id.spinr_year_team);
        progressBar = (ProgressBar) view.findViewById(R.id.team_leavereq_progress);
        token = ((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN);

        ArrayAdapter<String> adapter_months = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.month_array));

        spinr_year.setOnItemSelectedListener(this);
        spinr_month.setOnItemSelectedListener(this);

        container_no_data = (RelativeLayout) view.findViewById(R.id.container_no_data);

        img_reload = (ImageButton) view.findViewById(R.id.img_reload);
        img_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAttendanceFromServer(empID, String.valueOf(month + 1), year);
            }
        });

        Calendar calendar = Calendar.getInstance();
        month = currentMonth = calendar.get(Calendar.MONTH);
        year = currentYear = String.valueOf(calendar.get(Calendar.YEAR));

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
            String empName = ((DashboardActivity) getActivity()).userDetailsObj.getFirstname() + " " + ((DashboardActivity) getActivity()).userDetailsObj.getLastname() + " (" + ((DashboardActivity) getActivity()).userDetailsObj.getEmpcode() + ")";
            other_punch_emp_name.setText(empName);
        }

        getListOfEmployee("''");
        getAttendanceFromServer(empID, String.valueOf(month + 1), year);

        System.out.println(" empID " + empID);

        other_punch_emp_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String SelectedText = other_punch_emp_name.getText().toString().trim();
                /*int start = other_punch_emp_name.getText().toString().trim().indexOf("(");
                int end = other_punch_emp_name.getText().toString().trim().indexOf(")");
                empID = SelectedText.substring(start + 1, end);*/
                empID = employeeIDMap.get(SelectedText);
                System.out.println("SelectedText " + SelectedText + " empID " + empID);
                getAttendanceFromServer(empID, String.valueOf(month + 1), year);
            }
        });
    }

    private void getListOfEmployee(String str) {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
            Call<EmployeeListForTeamLead> employeeListCall;

            if (!isServiceCallExist(Constant.GET_EMPLOYEES_BY_NAME_OR_EMP_CODE)) {
                employeeListCall = retrofitApiService.getEmployeesByNameOrEmpCode(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN),
                        str, ((DashboardActivity) getActivity()).userDetailsObj.getCompanyId());
                putServiceCallInServiceMap(employeeListCall, Constant.GET_EMPLOYEES_BY_NAME_OR_EMP_CODE);
            } else {
                employeeListCall = getServiceCallIfExist(Constant.GET_EMPLOYEES_BY_NAME_OR_EMP_CODE);
                if (employeeListCall == null) {
                    employeeListCall = retrofitApiService.getEmployeesByNameOrEmpCode(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN),
                            str, ((DashboardActivity) getActivity()).userDetailsObj.getCompanyId());
                    putServiceCallInServiceMap(employeeListCall, Constant.GET_EMPLOYEES_BY_NAME_OR_EMP_CODE);
                }
            }

            employeeListCall.enqueue(new Callback<EmployeeListForTeamLead>() {
                @Override
                public void onResponse(Call<EmployeeListForTeamLead> call, Response<EmployeeListForTeamLead> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0) {
                            employeeIDMap = new HashMap<>();
//                            employeeNameList = response.body().getResult();
                            for (String data : response.body().getResult()) {
                                String idArray[] = data.split(" - ");
                                try {
                                    employeeIDMap.put(idArray[0], idArray[1]);
                                } catch (IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList(employeeIDMap.keySet()));
//                            ArrayAdapter adapter_Employee = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList(employeeIDMap.values()));
                            other_punch_emp_name.setAdapter(adapter);
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
                    getAttendanceFromServer(empID, String.valueOf(month + 1), year);
                    break;

                case R.id.spinr_month_team:
                    year = spinr_year.getSelectedItem().toString();
                    if ((position + 1) > (currentMonth + 1) && currentYear.equalsIgnoreCase(year)) {
                        clearItemsOfRecyclerView();
                    } else {
                        month = position;
                        getAttendanceFromServer(empID, String.valueOf(month + 1), year);
                    }
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getAttendanceFromServer(final String empID, final String m, final String y) {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

            clearItemsOfRecyclerView();
            progressBar.setVisibility(View.VISIBLE);

            final RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
            final Parameters serviceParams = new Parameters();

            serviceParams.setEmployeeID(empID);
            serviceParams.setMonth(m);
            serviceParams.setYear(y);
            serviceParams.setSearchInput("");
            serviceParams.setPageSize("10");
            serviceParams.setPageIndex("1");

            Call<MyAttendanceRequest> myAttendanceRequestCall = apiService.myAttendanceRequest(token, serviceParams);
            putServiceCallInServiceMap(myAttendanceRequestCall, Constant.MY_ATTENDANCE_REQ);
            hideReloadData();

            myAttendanceRequestCall.enqueue(new Callback<MyAttendanceRequest>() {
                @Override
                public void onResponse(Call<MyAttendanceRequest> call, Response<MyAttendanceRequest> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0 && response.body().getResult() != null) {
                            if (response.body().getResult().getAaData() != null &&
                                    response.body().getResult().getAaData().size() > 0) {

                                if (datumList != null) {
                                    datumList.clear();
                                    datumList.addAll(response.body().getResult().getAaData());
                                    myPunchRequestAdapter.notifyDataSetChanged();
                                } else {
                                    datumList = response.body().getResult().getAaData();
                                    myPunchRequestAdapter = new MyPunchRequestAdapter(datumList);
                                    recyclerView.setAdapter(myPunchRequestAdapter);
                                }

                                progressBar.setVisibility(View.GONE);
                                final int totalRecord = response.body().getResult().getRecordsTotal();

                                recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
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

                                                    Parameters serviceParams = new Parameters();
                                                    serviceParams.setSearchInput("");
                                                    serviceParams.setEmployeeID(empID);
                                                    serviceParams.setMonth(m);
                                                    serviceParams.setYear(y);
                                                    serviceParams.setPageIndex(String.valueOf(current_page));
                                                    serviceParams.setPageSize("10");

                                                    Call<MyAttendanceRequest> myAttendanceRequestCall1 = apiService.myAttendanceRequest(token, serviceParams);
                                                    putServiceCallInServiceMap(myAttendanceRequestCall1, Constant.MY_ATTENDANCE_REQ);

                                                    myAttendanceRequestCall1.enqueue(new Callback<MyAttendanceRequest>() {
                                                        @Override
                                                        public void onResponse(Call<MyAttendanceRequest> call, Response<MyAttendanceRequest> response) {
                                                            if (response.isSuccessful()) {
                                                                if (response.body().getResult().getAaData() != null) {
                                                                    datumList.addAll(response.body().getResult().getAaData());
                                                                    myPunchRequestAdapter.notifyDataSetChanged();
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<MyAttendanceRequest> call, Throwable t) {
                                                            t.printStackTrace();
                                                        }
                                                    });
                                                } else {
                                                    ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
                                                }
                                            }
                                        });
                                    }
                                });
                            } else {
                                clearItemsOfRecyclerView();
                                btn_punch_load_more.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                                showReloadData(View.INVISIBLE);
                            }
                        } else {
                            clearItemsOfRecyclerView();
                            btn_punch_load_more.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            showReloadData(View.VISIBLE);
                        }
                    } else {
                        clearItemsOfRecyclerView();
                        btn_punch_load_more.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        showReloadData(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<MyAttendanceRequest> call, Throwable t) {
                    clearItemsOfRecyclerView();
                    btn_punch_load_more.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    showReloadData(View.VISIBLE);
                    t.printStackTrace();
                }
            });
        } else {
            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }

    private void clearItemsOfRecyclerView() {
        if (datumList != null) {
            datumList.clear();
            if (myPunchRequestAdapter != null)
                myPunchRequestAdapter.notifyDataSetChanged();
        }
    }

    private void showReloadData(int visibility) {
        container_no_data.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        img_reload.setVisibility(visibility);
    }

    private void hideReloadData() {
        container_no_data.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
