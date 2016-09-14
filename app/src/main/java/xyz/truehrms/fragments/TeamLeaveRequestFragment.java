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
import xyz.truehrms.adapters.TeamLeaveRequestAdapter;
import xyz.truehrms.basecontroller.AppCompatFragment;
import xyz.truehrms.bean.GetLeaveRequestTeam;
import xyz.truehrms.bean.TeamForManager;
import xyz.truehrms.parameters.Parameters;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;
import xyz.truehrms.widgets.EndlessRecyclerOnScrollListener;

public class TeamLeaveRequestFragment extends AppCompatFragment implements AdapterView.OnItemSelectedListener {
    private LinearLayoutManager linearLayoutManager;
    private Spinner spinr_month_team, spinr_year_team, team_member_spiner;
    private RecyclerView leave_req_team_list;
    private ProgressBar progressBar;
    private Button btn_punch_load_more;
    private String year, currentYear, token;
    private List<GetLeaveRequestTeam.Result.LeaveListResult> resultList, globalResultList;
    private List<TeamForManager.Result> teamMemberList;
    private ArrayList<String> team_member;
    private int month, currentMonth, count = 0, spinnerCount = 0;
    private TeamLeaveRequestAdapter teamLeaveRequestAdapter;
    private RelativeLayout container_no_data;
    private ImageButton img_reload;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_team_leave_request, container, false);
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
        leave_req_team_list = (RecyclerView) view.findViewById(R.id.leave_req_team_list);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        leave_req_team_list.setLayoutManager(linearLayoutManager);
        team_member_spiner = (Spinner) view.findViewById(R.id.team_member_spiner);
        spinr_month_team = (Spinner) view.findViewById(R.id.spinr_month_team);
        btn_punch_load_more = (Button) view.findViewById(R.id.btn_punch_load_more);
        spinr_year_team = (Spinner) view.findViewById(R.id.spinr_year_team);
        progressBar = (ProgressBar) view.findViewById(R.id.team_leavereq_progress);
        token = ((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN);
        ArrayAdapter<String> adapter_months = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.month_array));

        container_no_data = (RelativeLayout) view.findViewById(R.id.container_no_data);

        img_reload = (ImageButton) view.findViewById(R.id.img_reload);
        img_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTeamRequestService(String.valueOf(month + 1), year);
            }
        });

        spinr_year_team.setOnItemSelectedListener(this);
        spinr_month_team.setOnItemSelectedListener(this);
        team_member_spiner.setOnItemSelectedListener(this);

        Calendar calendar = Calendar.getInstance();
        month = currentMonth = calendar.get(Calendar.MONTH);
        year = currentYear = String.valueOf(calendar.get(Calendar.YEAR));

        Calendar calendarForYear = Calendar.getInstance();
        calendarForYear.set(Calendar.YEAR, 2015);

        ArrayList<String> yearArrayList = new ArrayList<>();
        while (calendarForYear.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR)) {
            yearArrayList.add(String.valueOf(calendarForYear.get(Calendar.YEAR)));
            calendarForYear.roll(Calendar.YEAR, true);
        }

        ArrayAdapter<String> adapter_year = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, yearArrayList);

        callTeamRequestService(String.valueOf(month + 1), year);

        spinr_month_team.setAdapter(adapter_months);
        spinr_month_team.setSelection(month);
        spinr_year_team.setAdapter(adapter_year);

        spinr_year_team.setSelection(yearArrayList.size() - 1);
    }

    public void callService() {
        if (count == 0) {
            // first get team for manager
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

                            if (response.isSuccessful()) {
                                if (response.body().getStatusCode() == 200.0) {
                                    teamMemberList = response.body().getResult();
                                    if (teamMemberList != null && teamMemberList.size() > 0) {
                                        team_member = new ArrayList<>();
                                        team_member.add("All");
                                        for (int i = 0; i < teamMemberList.size(); i++) {
                                            if (teamMemberList.get(i).getFirstname() != null && teamMemberList.get(i).getFirstname().length() > 0) {
                                                team_member.add(teamMemberList.get(i).getFirstname());
                                            }
                                        }
                                        if (team_member.size() > 1) {
                                            ArrayAdapter<String> adapter_team_memeber = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, team_member);
                                            team_member_spiner.setAdapter(adapter_team_memeber);
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<TeamForManager> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } else {
                    ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
                }
            }
            count = 1;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinnerCount < 3) {
            spinnerCount++;
        } else {
            Spinner spinner = (Spinner) parent;
            switch (spinner.getId()) {
                case R.id.spinr_year_team:
                    month = spinr_month_team.getSelectedItemPosition();
                    year = spinr_year_team.getSelectedItem().toString();
                    callTeamRequestService(String.valueOf(month + 1), year);
                    break;
                case R.id.spinr_month_team:
                    if ((position + 1) > (currentMonth + 1) && year.equalsIgnoreCase(currentYear)) {
                        clearItemsOfRecyclerView();
                    } else {
                        month = position;
                        year = spinr_year_team.getSelectedItem().toString();
                        callTeamRequestService(String.valueOf(month + 1), year);
                    }

                    break;
                case R.id.team_member_spiner:
                    if (position == 0) {
                        callTeamRequestService(String.valueOf(month + 1), year);
                    } else {
                        if (teamMemberList != null) {
                            String teamMemberEmpcode = teamMemberList.get(position - 1).getEmpcode();
                            System.out.println("teamMemberList " + teamMemberList);
                            if (globalResultList != null && globalResultList.size() > 0) {
                                resultList.clear();
                                for (int i = 0; i < globalResultList.size(); i++) {
                                    if (teamMemberEmpcode.contentEquals(globalResultList.get(i).getEmpcode())) {
                                        resultList.add(globalResultList.get(i));
                                    }
                                }

                                if (teamLeaveRequestAdapter != null) {
                                    teamLeaveRequestAdapter.notifyDataSetChanged();
                                }
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

    public void callTeamRequestService(final String m, final String y) {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

            clearItemsOfRecyclerView();

            Parameters serviceParams = new Parameters();
            serviceParams.setEmployeeID(String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
            serviceParams.setMonth(m);
            serviceParams.setYear(y);
            serviceParams.setSearchInput("");
            serviceParams.setPageSize("10");
            serviceParams.setPageIndex("1");

            final RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
            Call<GetLeaveRequestTeam> getLeaveReqTeamCall = apiService.getLeaveRequestTeam(token, serviceParams);
            hideReloadData();
            progressBar.setVisibility(View.VISIBLE);

            putServiceCallInServiceMap(getLeaveReqTeamCall, Constant.LEAVE_REQ_TEAM);

            getLeaveReqTeamCall.enqueue(new Callback<GetLeaveRequestTeam>() {
                @Override
                public void onResponse(Call<GetLeaveRequestTeam> call, Response<GetLeaveRequestTeam> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0) {
                            if (response.body().getResult() != null &&
                                    response.body().getResult().getLeaveListResult().size() > 0) {

                                if (resultList != null) {
                                    resultList.clear();
                                    resultList.addAll(response.body().getResult().getLeaveListResult());
                                    teamLeaveRequestAdapter.notifyDataSetChanged();
                                } else {
                                    resultList = response.body().getResult().getLeaveListResult();
                                    teamLeaveRequestAdapter = new TeamLeaveRequestAdapter(m, y, resultList, getActivity(), TeamLeaveRequestFragment.this);
                                    leave_req_team_list.setAdapter(teamLeaveRequestAdapter);
                                }
                                globalResultList = new ArrayList<>(response.body().getResult().getLeaveListResult());
//                            globalResultList = response.body().getResult().getLeaveListResult();
                                progressBar.setVisibility(View.GONE);
                                final int totalRecord = response.body().getResult().getCount();
                                leave_req_team_list.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
                                    @Override
                                    public void scroolabove(int current_page) {

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
                                                    Parameters serviceParams = new Parameters();
                                                    serviceParams.setEmployeeID(String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
                                                    serviceParams.setMonth(m);
                                                    serviceParams.setYear(y);
                                                    serviceParams.setSearchInput("");
                                                    serviceParams.setPageSize("10");
                                                    serviceParams.setPageIndex(String.valueOf(current_page));
                                                    Call<GetLeaveRequestTeam> getLeaveReqTeamCall = apiService.getLeaveRequestTeam(token, serviceParams);
                                                    putServiceCallInServiceMap(getLeaveReqTeamCall, Constant.LEAVE_REQ_TEAM);
                                                    getLeaveReqTeamCall.enqueue(new Callback<GetLeaveRequestTeam>() {
                                                        @Override
                                                        public void onResponse(Call<GetLeaveRequestTeam> call, Response<GetLeaveRequestTeam> response) {
                                                            if (response.isSuccessful() && response.body().getStatusCode() == 200.0) {
                                                                resultList.addAll(response.body().getResult().getLeaveListResult());
                                                                teamLeaveRequestAdapter.notifyDataSetChanged();
                                                                globalResultList.addAll(response.body().getResult().getLeaveListResult());
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<GetLeaveRequestTeam> call, Throwable t) {
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
                                progressBar.setVisibility(View.GONE);
                                btn_punch_load_more.setVisibility(View.GONE);
                                showReloadData(View.INVISIBLE);
                            }
                        } else {
                            clearItemsOfRecyclerView();
                            progressBar.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            btn_punch_load_more.setVisibility(View.GONE);
                            showReloadData(View.VISIBLE);
                        }
                    } else {
                        clearItemsOfRecyclerView();
                        progressBar.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        btn_punch_load_more.setVisibility(View.GONE);
                        showReloadData(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<GetLeaveRequestTeam> call, Throwable t) {
                    clearItemsOfRecyclerView();
                    progressBar.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    btn_punch_load_more.setVisibility(View.GONE);
                    showReloadData(View.VISIBLE);
                    t.printStackTrace();
                }
            });
        } else {
            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }

    private void clearItemsOfRecyclerView() {
        if (resultList != null) {
            resultList.clear();
            if (teamLeaveRequestAdapter != null)
                teamLeaveRequestAdapter.notifyDataSetChanged();
        }
    }

    private void showReloadData(int visibility) {
        container_no_data.setVisibility(View.VISIBLE);
        leave_req_team_list.setVisibility(View.GONE);
        img_reload.setVisibility(visibility);
    }

    private void hideReloadData() {
        container_no_data.setVisibility(View.GONE);
        leave_req_team_list.setVisibility(View.VISIBLE);
    }
}