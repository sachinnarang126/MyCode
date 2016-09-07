package xyz.truehrms.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import xyz.truehrms.activities.AttendanceRequestActivity;
import xyz.truehrms.activities.DashboardActivity;
import xyz.truehrms.adapters.AttendanceAdapter;
import xyz.truehrms.basecontroller.AppCompatFragment;
import xyz.truehrms.bean.AllAttendance;
import xyz.truehrms.bean.EmployeeListForTeamLead;
import xyz.truehrms.bean.TeamForManager;
import xyz.truehrms.dataholder.DataHolder;
import xyz.truehrms.parameters.Parameters;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;

public class AttendanceFragment extends AppCompatFragment {
    private RecyclerView list_attendance_detail;
    private ProgressBar progressBar;
    private AutoCompleteTextView emp_name_list;
    private Spinner spinner_month, spinner_year;
    private boolean isFirstTime = true;
    private String userID, year, currentYear;
    private int currentMonth, month;
    private List<AllAttendance.Result.AaDatum> datumList;
    private HashMap<String, String> employeeIdMap;
    private AttendanceAdapter attendanceAdapter;
    private RelativeLayout container_no_data;
    private ImageButton img_reload;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout _container = (LinearLayout) view.findViewById(R.id.container);
        if (((DashboardActivity) getActivity()).getPreference().hasAdminControl()) {
            _container.setVisibility(View.VISIBLE);
            initFab(view);
            initView(view);
        } else {
            if (((DashboardActivity) getActivity()).hasPermission(Constant.VIEW_ATTENDANCE_VIEW)) {
                _container.setVisibility(View.VISIBLE);
                initView(view);
            } else {
                _container.setVisibility(View.GONE);
                ((DashboardActivity) getActivity()).showToast(getString(R.string.error_view_attendance));
            }

            if (((DashboardActivity) getActivity()).hasPermission(Constant.APPLY_LEAVE_EDIT)) {
                initFab(view);
            }
        }
    }

    private void initFab(View view) {
        FloatingActionButton apply_leave_fab = (FloatingActionButton) view.findViewById(R.id.apply_leave_fab);
        apply_leave_fab.setVisibility(View.VISIBLE);
        apply_leave_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AttendanceRequestActivity.class);
                intent.putExtra("isFromFab", true);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (DataHolder.getInstance() == null)
            ((DashboardActivity) getActivity()).restartApp();
    }

    private void initView(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.my_attandanc_progress);
        emp_name_list = (AutoCompleteTextView) view.findViewById(R.id.emp_name_list);
        list_attendance_detail = (RecyclerView) view.findViewById(R.id.list_attendence_detail);
        list_attendance_detail.setLayoutManager(new LinearLayoutManager(getActivity()));
        spinner_month = (Spinner) view.findViewById(R.id.spinner_month);
        spinner_year = (Spinner) view.findViewById(R.id.spinner_year);

        container_no_data = (RelativeLayout) view.findViewById(R.id.container_no_data);

        userID = String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId());

        if (((DashboardActivity) getActivity()).userDetailsObj.getFirstname() != null) {
            String name = ((DashboardActivity) getActivity()).userDetailsObj.getFirstname() + " " + ((DashboardActivity) getActivity()).userDetailsObj.getLastname() + " (" + ((DashboardActivity) getActivity()).userDetailsObj.getEmpcode() + ")";
            emp_name_list.setText(name);
        }

        if (((DashboardActivity) getActivity()).getPreference().hasAdminControl()) {
            getListOfEmployee("''");

        } else if (((DashboardActivity) getActivity()).hasPermission(Constant.ATTENDANCE_REQ_MANAGER)) {
            getTeamForManager();
        } else {
            emp_name_list.setEnabled(false);
        }
        Calendar calendar = Calendar.getInstance();
        month = currentMonth = calendar.get(Calendar.MONTH);
        currentYear = year = String.valueOf(calendar.get(Calendar.YEAR));
        ArrayAdapter<String> adapter_months = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.month_array));

        spinner_month.setAdapter(adapter_months);
        spinner_month.setSelection(currentMonth);

        Calendar calendarForYear = Calendar.getInstance();
        calendarForYear.set(Calendar.YEAR, 2015);

        ArrayList<String> yearArrayList = new ArrayList<>();
        while (calendarForYear.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR)) {
            yearArrayList.add(String.valueOf(calendarForYear.get(Calendar.YEAR)));
            calendarForYear.roll(Calendar.YEAR, true);
        }

        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, yearArrayList);
        spinner_year.setAdapter(adapter_year);
        spinner_year.setSelection(yearArrayList.size() - 1);

        img_reload = (ImageButton) view.findViewById(R.id.img_reload);
        img_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllAttendance(userID, String.valueOf(month + 1), year);
            }
        });

        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (((DashboardActivity) getActivity()).getPreference().hasAdminControl()) {
                    month = position;
                    year = spinner_year.getSelectedItem().toString();
                    if ((position + 1) > (currentMonth + 1) && year.equalsIgnoreCase(currentYear)) {
                        clearItemsOfRecyclerView();
                    } else {
                        getAllAttendance(userID, String.valueOf(month + 1), year);
                    }
                } else {
                    if (((DashboardActivity) getActivity()).hasPermission(Constant.VIEW_ATTENDANCE_VIEW)) {
                        month = position;
                        year = spinner_year.getSelectedItem().toString();
                        if ((position + 1) > (currentMonth + 1) && year.equalsIgnoreCase(currentYear)) {
                            clearItemsOfRecyclerView();
                        } else {
                            getAllAttendance(userID, String.valueOf(month + 1), year);
                        }
                    } else {
                        ((DashboardActivity) getActivity()).showToast(getString(R.string.error_view_attendance));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = spinner_year.getSelectedItem().toString();
                month = spinner_month.getSelectedItemPosition();

                if (isFirstTime) {
                    isFirstTime = false;
                } else {
                    getAllAttendance(userID, String.valueOf(String.valueOf(month + 1)), year);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        emp_name_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = emp_name_list.getText().toString().trim();
                /*int start = emp_name_list.getText().toString().trim().indexOf("(");
                int end = emp_name_list.getText().toString().trim().indexOf(")");
                userID = SelectedText.substring(start + 1, end);*/
                userID = employeeIdMap.get(selectedText);
                System.out.println("-----------SelectedText " + selectedText + " userID " + userID);
                getAllAttendance(userID, String.valueOf(month + 1), year);

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
                            employeeIdMap = new HashMap<String, String>();
                            for (String data : response.body().getResult()) {
                                String idArray[] = data.split(" - ");
                                try {
                                    employeeIdMap.put(idArray[0], idArray[1]);
                                } catch (IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }
                            }
//                            List<String> employNameList = response.body().getResult();
                            ArrayAdapter<String> adapterEmployee = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList(employeeIdMap.keySet()));
                            emp_name_list.setAdapter(adapterEmployee);
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

    public void getTeamForManager() {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {
            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
            if (((DashboardActivity) getActivity()).userDetailsObj.getId() != 0) {

                Call<TeamForManager> teamForManagerCall;
                if (!isServiceCallExist(Constant.GET_TEAM_FOR_MANAGER)) {
                    teamForManagerCall = retrofitApiService.getTeamForManager(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
                    putServiceCallInServiceMap(teamForManagerCall, Constant.GET_TEAM_FOR_MANAGER);
                } else {
                    teamForManagerCall = getServiceCallIfExist(Constant.GET_TEAM_FOR_MANAGER);

                    if (teamForManagerCall == null) {
                        teamForManagerCall = retrofitApiService.getTeamForManager(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
                        putServiceCallInServiceMap(teamForManagerCall, Constant.GET_TEAM_FOR_MANAGER);
                    }
                }

                teamForManagerCall.enqueue(new Callback<TeamForManager>() {
                    @Override
                    public void onResponse(Call<TeamForManager> call, Response<TeamForManager> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatusCode() == 200.0) {
                                List<TeamForManager.Result> teamMemberList = response.body().getResult();
                                if (teamMemberList != null && teamMemberList.size() > 0) {
                                    employeeIdMap = new HashMap<String, String>();
//                                    ArrayList<String> teamMemberArrayList = new ArrayList<>();
//                                    teamMemberArrayList.add("All");

                                    for (int i = 0; i < teamMemberList.size(); i++) {
                                        if (teamMemberList.get(i).getFirstname() != null && teamMemberList.get(i).getFirstname().length() > 0) {
//                                            teamMemberArrayList.add(teamMemberList.get(i).getFirstname() + "(" + teamMemberList.get(i).getEmpcode() + ")");
                                            employeeIdMap.put(teamMemberList.get(i).getFirstname(), teamMemberList.get(i).getEmpcode());
                                        }
                                    }

                                    if (employeeIdMap.size() > 1) {
                                        ArrayAdapter<String> adapterTeamMember = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList(employeeIdMap.keySet()));
                                        emp_name_list.setAdapter(adapterTeamMember);
                                    }
                                }
                            } else {
                                try {
                                    if (isAdded() && getActivity() != null)
                                        ((DashboardActivity) getActivity()).showToast(response.body().getErrors().get(0).toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TeamForManager> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        } else {
            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
        }

    }

    public void getAllAttendance(final String userID, String month, String year) {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {
            clearItemsOfRecyclerView();

            progressBar.setVisibility(View.VISIBLE);

            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
            Parameters serviceParams = new Parameters();
            serviceParams.setSearchInput("");
            serviceParams.setEmployeeID(userID);
            serviceParams.setMonth(month);
            serviceParams.setYear(year);
            serviceParams.setPageIndex("1");
            serviceParams.setPageSize("10");

            hideReloadData();

            Call<AllAttendance> employeeAttendanceCall = retrofitApiService.getAllAttendance(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), serviceParams);
            putServiceCallInServiceMap(employeeAttendanceCall, Constant.GET_ALL_ATTENDANCE);

            employeeAttendanceCall.enqueue(new Callback<AllAttendance>() {
                @Override
                public void onResponse(Call<AllAttendance> call, Response<AllAttendance> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0) {
                            if (response.body().getResult() != null) {
                                if (response.body().getResult().getAaData() != null &&
                                        response.body().getResult().getAaData().size() > 0) {
                                    if (datumList != null) {
                                        datumList.clear();
                                        datumList.addAll(response.body().getResult().getAaData());
                                    } else {
                                        datumList = response.body().getResult().getAaData();
                                    }
                                    setAdapter(userID);
                                    progressBar.setVisibility(View.GONE);
                                } else {
                                    clearItemsOfRecyclerView();
                                    progressBar.setVisibility(View.GONE);
                                    showReloadData(View.INVISIBLE);
                                }
                            } else {
                                clearItemsOfRecyclerView();
                                showReloadData(View.INVISIBLE);
                            }
                        } else {
                            clearItemsOfRecyclerView();
                            progressBar.setVisibility(View.GONE);
                            showReloadData(View.VISIBLE);
                        }
                    } else {
                        clearItemsOfRecyclerView();
                        progressBar.setVisibility(View.GONE);
                        showReloadData(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<AllAttendance> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    showReloadData(View.VISIBLE);
                    t.printStackTrace();
                }
            });
        } else {
            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }

    private void setAdapter(String userID) {
        boolean isCurrentUserEmpCode;
        if (userID.trim().equals(String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()).trim())) {
            isCurrentUserEmpCode = true;
        } else {
            isCurrentUserEmpCode = false;
        }
        if (attendanceAdapter == null) {
            attendanceAdapter = new AttendanceAdapter(getActivity(), datumList, isCurrentUserEmpCode);
            list_attendance_detail.setAdapter(attendanceAdapter);
        } else {
            attendanceAdapter.notifyDataSetChanged();
        }
    }

    private void clearItemsOfRecyclerView() {
        if (datumList != null) {
            datumList.clear();
            if (attendanceAdapter != null)
                attendanceAdapter.notifyDataSetChanged();
        }
    }

    private void showReloadData(int visibility) {
        container_no_data.setVisibility(View.VISIBLE);
        list_attendance_detail.setVisibility(View.GONE);
        img_reload.setVisibility(visibility);
    }

    private void hideReloadData() {
        container_no_data.setVisibility(View.GONE);
        list_attendance_detail.setVisibility(View.VISIBLE);
    }
}
