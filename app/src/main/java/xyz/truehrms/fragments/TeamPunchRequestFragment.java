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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import xyz.truehrms.adapters.TeamPunchRequestAdapter;
import xyz.truehrms.basecontroller.AppCompatFragment;
import xyz.truehrms.bean.ApprovedAttendance;
import xyz.truehrms.bean.TeamForManager;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.parameters.Parameters;
import xyz.truehrms.utils.Constant;
import xyz.truehrms.widgets.EndlessRecyclerOnScrollListener;

public class TeamPunchRequestFragment extends AppCompatFragment implements AdapterView.OnItemSelectedListener {
    private Spinner spinr_month_team, spinr_year_team, team_member_spinner;
    private int month, currentMonth;
    private RecyclerView list_attendence_request;
    private ArrayAdapter<String> teamMember;
    private String token;
    private ArrayList<String> teamMemberList;
    private ProgressBar progressBar;
    private String year, currentYear;
    private Button btn_punch_load_more;
    private int count = 0;
    private LinearLayoutManager linearLayoutManager;
    private List<ApprovedAttendance.Result.AaDatum> allTeamMemberList, allTeamMemberGlobalList;
    private int spinnerInit = 0;
    private List<TeamForManager.Result> resultList;
    private TeamPunchRequestAdapter teamPunchRequestAdapter;
    private RelativeLayout container_no_data;
    private ImageButton img_reload;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_team_punch_request, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout _container = (LinearLayout) view.findViewById(R.id.container);

        if (!((DashboardActivity) getActivity()).getPreference().hasAdminControl()) {
            if (((DashboardActivity) getActivity()).hasPermission(Constant.APPROVE_LEAVE_REQ_VIEW)) {
                _container.setVisibility(View.VISIBLE);
                initView(view);
            } else {
                _container.setVisibility(View.GONE);
            }
        } else {
            initView(view);
            _container.setVisibility(View.VISIBLE);
        }
    }

    private void initView(View view) {
        list_attendence_request = (RecyclerView) view.findViewById(R.id.list_attendence_request);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        list_attendence_request.setLayoutManager(new LinearLayoutManager(getActivity()));
        spinr_month_team = (Spinner) view.findViewById(R.id.spinr_month_team);
        team_member_spinner = (Spinner) view.findViewById(R.id.team_member_spinner);
        spinr_year_team = (Spinner) view.findViewById(R.id.spinr_year_team);
        btn_punch_load_more = (Button) view.findViewById(R.id.btn_punch_load_more);

        container_no_data = (RelativeLayout) view.findViewById(R.id.container_no_data);

        img_reload = (ImageButton) view.findViewById(R.id.img_reload);
        img_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTeamRequest(String.valueOf(month + 1), year);
            }
        });

        progressBar = (ProgressBar) view.findViewById(R.id.team_punchreq_progress);
        token = ((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.month_array));

        Calendar calendar = Calendar.getInstance();
        month = currentMonth = calendar.get(Calendar.MONTH);

        year = currentYear = String.valueOf(calendar.get(Calendar.YEAR));
        spinr_month_team.setAdapter(monthAdapter);
        spinr_month_team.setSelection(month);

        Calendar calendarForYear = Calendar.getInstance();
        calendarForYear.set(Calendar.YEAR, 2015);

        ArrayList<String> yearArrayList = new ArrayList<>();
        while (calendarForYear.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR)) {
            yearArrayList.add(String.valueOf(calendarForYear.get(Calendar.YEAR)));
            calendarForYear.roll(Calendar.YEAR, true);
        }

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, yearArrayList);
        spinr_year_team.setAdapter(yearAdapter);

        spinr_year_team.setSelection(yearArrayList.size() - 1);
        team_member_spinner.setOnItemSelectedListener(this);
        spinr_month_team.setOnItemSelectedListener(this);
        spinr_year_team.setOnItemSelectedListener(this);
    }

    public void callService() {
        if (count == 0) {
            if (((DashboardActivity) getActivity()).userDetailsObj.getId() != 0) {
                if (((DashboardActivity) getActivity()).isInternetAvailable()) {

                    RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
                    Call<TeamForManager> teamForManagerCall;

                    if (!isServiceCallExist(Constant.GET_TEAM_FOR_MANAGER)) {
                        teamForManagerCall = apiService.getTeamForManager(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
                        putServiceCallInServiceMap(teamForManagerCall, Constant.GET_TEAM_FOR_MANAGER);
                    } else {
                        teamForManagerCall = getServiceCallIfExist(Constant.GET_TEAM_FOR_MANAGER);
                        if (teamForManagerCall == null) {
                            teamForManagerCall = apiService.getTeamForManager(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
                            putServiceCallInServiceMap(teamForManagerCall, Constant.GET_TEAM_FOR_MANAGER);
                        }
                    }

                    teamForManagerCall.enqueue(new Callback<TeamForManager>() {
                        @Override
                        public void onResponse(Call<TeamForManager> call, Response<TeamForManager> response) {
                            callTeamRequest(String.valueOf(month + 1), year);
                            if (response.isSuccessful()) {
                                if (response.body().getStatusCode() == 200.0) {
                                    resultList = response.body().getResult();
                                    if (resultList != null && resultList.size() > 0) {
                                        teamMemberList = new ArrayList<>();
                                        teamMemberList.add("All");
                                        for (int i = 0; i < resultList.size(); i++) {
                                            if (resultList.get(i).getFirstname() != null && resultList.get(i).getFirstname().length() > 0) {
                                                teamMemberList.add(resultList.get(i).getFirstname());
                                            }
                                        }
                                        if (teamMemberList.size() > 1) {
                                            teamMember = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, teamMemberList);
                                            team_member_spinner.setAdapter(teamMember);
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<TeamForManager> call, Throwable t) {
                            callTeamRequest(String.valueOf(month + 1), year);
                            t.printStackTrace();
                        }
                    });
                } else {
                    ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
                }
            } else {
                callTeamRequest(String.valueOf(month + 1), year);
            }
            // so that 2nd time service cnt cll
            count = 1;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinnerInit < 3) {
            spinnerInit++;
        } else {
            Spinner spinner = (Spinner) parent;
            switch (spinner.getId()) {
                case R.id.spinr_year_team:
                    month = spinr_month_team.getSelectedItemPosition();
                    year = spinr_year_team.getSelectedItem().toString();
                    callTeamRequest(String.valueOf(month + 1), year);
                    break;
                case R.id.spinr_month_team:
                    year = spinr_year_team.getSelectedItem().toString();
                    if ((position + 1) > (currentMonth + 1) && currentYear.equalsIgnoreCase(year)) {
                        clearItemsOfRecyclerView();
                    } else {
                        month = position;
                        callTeamRequest(String.valueOf(month + 1), year);
                    }

                    break;
                case R.id.team_member_spinner:
                    if (position == 0) {
                        callTeamRequest(String.valueOf(month + 1), year);
                    } else {
                        if (resultList != null) {
                            String teamMemberEmpcode = resultList.get(position - 1).getEmpcode();
                            if (allTeamMemberGlobalList != null && allTeamMemberGlobalList.size() > 0) {
                                allTeamMemberList.clear();
                                for (int i = 0; i < allTeamMemberGlobalList.size(); i++) {
                                    if (teamMemberEmpcode.contentEquals(allTeamMemberGlobalList.get(i).getRequestedbyId())) {
                                        allTeamMemberList.add(allTeamMemberGlobalList.get(i));
                                    }
                                }
                                if (teamPunchRequestAdapter != null)
                                    teamPunchRequestAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void callTeamRequest(final String month, final String year) {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

            clearItemsOfRecyclerView();
            progressBar.setVisibility(View.VISIBLE);

            final Parameters serviceParams = new Parameters();
            serviceParams.setSearchInput("");
            serviceParams.setEmployeeID(String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
            serviceParams.setMonth(month);
            serviceParams.setYear(year);
            serviceParams.setPageIndex("1");
            serviceParams.setPageSize("10");

            final RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
            Call<ApprovedAttendance> approvedAttendanceCall = apiService.approvedAttendance(token, serviceParams);

            hideReloadData();

            putServiceCallInServiceMap(approvedAttendanceCall, Constant.APPROVED_ATTENDANCE);
            approvedAttendanceCall.enqueue(new Callback<ApprovedAttendance>() {
                @Override
                public void onResponse(Call<ApprovedAttendance> call, Response<ApprovedAttendance> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0 && response.body().getResult() != null) {
                            if (response.body().getResult().getAaData() != null &&
                                    response.body().getResult().getAaData().size() > 0) {
                                if (allTeamMemberList != null) {
                                    allTeamMemberList.clear();
                                    allTeamMemberList.addAll(response.body().getResult().getAaData());
                                    teamPunchRequestAdapter.notifyDataSetChanged();
                                } else {
                                    allTeamMemberList = response.body().getResult().getAaData();
                                    teamPunchRequestAdapter = new TeamPunchRequestAdapter(month, year, allTeamMemberList, getActivity(), TeamPunchRequestFragment.this);
                                    list_attendence_request.setAdapter(teamPunchRequestAdapter);
                                }

                                allTeamMemberGlobalList = new ArrayList<>(response.body().getResult().getAaData());

                                progressBar.setVisibility(View.GONE);

                                final int totalRecord = response.body().getResult().getRecordsTotal();
                                list_attendence_request.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
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
                                                    serviceParams.setPageIndex(String.valueOf(current_page));

                                                    Call<ApprovedAttendance> apprvdAttndCall = apiService.approvedAttendance(token, serviceParams);
                                                    apprvdAttndCall.enqueue(new Callback<ApprovedAttendance>() {
                                                        @Override
                                                        public void onResponse(Call<ApprovedAttendance> call, Response<ApprovedAttendance> response) {
                                                            allTeamMemberList.addAll(response.body().getResult().getAaData());
                                                            teamPunchRequestAdapter.notifyDataSetChanged();
                                                            allTeamMemberGlobalList.addAll(response.body().getResult().getAaData());
                                                        }

                                                        @Override
                                                        public void onFailure(Call<ApprovedAttendance> call, Throwable t) {
                                                            t.printStackTrace();
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                });
                            } else {
                                clearItemsOfRecyclerView();
                                progressBar.setVisibility(View.GONE);
                                showReloadData(View.INVISIBLE);
                                btn_punch_load_more.setVisibility(View.GONE);
                            }

                        } else {
                            clearItemsOfRecyclerView();
                            progressBar.setVisibility(View.GONE);
                            showReloadData(View.VISIBLE);
                            btn_punch_load_more.setVisibility(View.GONE);
                        }
                    } else {
                        clearItemsOfRecyclerView();
                        progressBar.setVisibility(View.GONE);
                        showReloadData(View.VISIBLE);
                        btn_punch_load_more.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<ApprovedAttendance> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    showReloadData(View.VISIBLE);
                    btn_punch_load_more.setVisibility(View.GONE);
                    t.printStackTrace();
                }
            });
        } else {
            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }

    private void clearItemsOfRecyclerView() {
        if (allTeamMemberList != null) {
            allTeamMemberList.clear();
            if (teamPunchRequestAdapter != null)
                teamPunchRequestAdapter.notifyDataSetChanged();
        }
    }

    private void showReloadData(int visibility) {
        container_no_data.setVisibility(View.VISIBLE);
        list_attendence_request.setVisibility(View.GONE);
        img_reload.setVisibility(visibility);
    }

    private void hideReloadData() {
        container_no_data.setVisibility(View.GONE);
        list_attendence_request.setVisibility(View.VISIBLE);
    }
}