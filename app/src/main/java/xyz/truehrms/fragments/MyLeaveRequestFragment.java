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
import xyz.truehrms.bean.MyLeaveRequests;
import xyz.truehrms.parameters.Parameters;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;
import xyz.truehrms.widgets.EndlessRecyclerOnScrollListener;

public class MyLeaveRequestFragment extends AppCompatFragment implements AdapterView.OnItemSelectedListener {
    private Spinner spinr_month, spinr_year;
    private int month;
    private RecyclerView list_leave_req_my;
    private String token;
    private ProgressBar progressBar;
    private Button btn_punch_load_more;
    private String year, currentYear;
    private LinearLayoutManager linearLayoutManager;
    private List<MyLeaveRequests.Result.LeaveListResult> resultList;
    private MyLeaveRequestAdapter myLeaveRequestAdapter;
    private int spinnerCount = 0;
    private RelativeLayout container_no_data;
    private ImageButton img_reload;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_leave_request, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinr_month = (Spinner) view.findViewById(R.id.spinr_month);
        spinr_year = (Spinner) view.findViewById(R.id.spinr_year);
        list_leave_req_my = (RecyclerView) view.findViewById(R.id.list_leave_req_my);
        progressBar = (ProgressBar) view.findViewById(R.id.myleavereq_progress);

        btn_punch_load_more = (Button) view.findViewById(R.id.btn_punch_load_more);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        list_leave_req_my.setLayoutManager(linearLayoutManager);
        ArrayAdapter<String> adapterMonths = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.month_array));

        container_no_data = (RelativeLayout) view.findViewById(R.id.container_no_data);

        img_reload = (ImageButton) view.findViewById(R.id.img_reload);
        img_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMyLeaveRequest(String.valueOf(month + 1), year);
            }
        });

        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = currentYear = String.valueOf(calendar.get(Calendar.YEAR));
        spinr_month.setAdapter(adapterMonths);
        spinr_month.setSelection(month);

        Calendar calendarForYear = Calendar.getInstance();
        calendarForYear.set(Calendar.YEAR, 2015);

        ArrayList<String> yearArrayList = new ArrayList<>();
        while (calendarForYear.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR)) {
            yearArrayList.add(String.valueOf(calendarForYear.get(Calendar.YEAR)));
            calendarForYear.roll(Calendar.YEAR, true);
        }

        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, yearArrayList);
        spinr_year.setAdapter(adapterYear);
        spinr_year.setSelection(yearArrayList.size() - 1);

        spinr_month.setOnItemSelectedListener(this);
        spinr_year.setOnItemSelectedListener(this);
        callMyLeaveRequest(String.valueOf(month + 1), this.year);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinnerCount < 2) {
            spinnerCount++;
        } else {
            Spinner spinner = (Spinner) parent;
            switch (spinner.getId()) {
                case R.id.spinr_month:
                    year = spinr_year.getSelectedItem().toString();
                    if ((position + 1) > (month + 1) && year.equalsIgnoreCase(currentYear)) {
                        clearItemOfRecyclerView();
                    } else {
                        month = position;
                        callMyLeaveRequest(String.valueOf(month + 1), year);
                    }
                    break;
                case R.id.spinr_year:
                    month = spinr_month.getSelectedItemPosition();
                    year = spinr_year.getSelectedItem().toString();
                    callMyLeaveRequest(String.valueOf(month + 1), year);
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void callMyLeaveRequest(final String m, final String y) {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

            clearItemOfRecyclerView();
            progressBar.setVisibility(View.VISIBLE);
            token = ((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN);

            Parameters serviceParams = new Parameters();
            serviceParams.setEmployeeID(String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
            serviceParams.setMonth(m);
            serviceParams.setYear(y);
            serviceParams.setSearchInput("");
            serviceParams.setPageSize("10");
            serviceParams.setPageIndex("1");

            RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
            Call<MyLeaveRequests> myLeaveRequestsCall = apiService.myLeaveRequests(token, serviceParams);
            hideReloadData();

            putServiceCallInServiceMap(myLeaveRequestsCall, Constant.MY_LEAVE_REQUESTS);

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
                                    list_leave_req_my.setAdapter(myLeaveRequestAdapter);
                                }
                                progressBar.setVisibility(View.GONE);

                                final int totalRecord = response.body().getResult().getCount();
                                list_leave_req_my.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
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

                                                    Parameters pendingLevReqParams = new Parameters();
                                                    pendingLevReqParams.setEmployeeID(String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
                                                    pendingLevReqParams.setMonth(m);
                                                    pendingLevReqParams.setYear(y);
                                                    pendingLevReqParams.setSearchInput("");
                                                    pendingLevReqParams.setPageSize("10");
                                                    pendingLevReqParams.setPageIndex(String.valueOf(current_page));

                                                    Call<MyLeaveRequests> myLeaveRequestsCall = apiService.myLeaveRequests(token, pendingLevReqParams);
                                                    putServiceCallInServiceMap(myLeaveRequestsCall, Constant.MY_LEAVE_REQUESTS);
                                                    myLeaveRequestsCall.enqueue(new Callback<MyLeaveRequests>() {
                                                        @Override
                                                        public void onResponse(Call<MyLeaveRequests> call, Response<MyLeaveRequests> response) {
                                                            if (response.isSuccessful() && response.body().getStatusCode() == 200.0) {
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
        list_leave_req_my.setVisibility(View.GONE);
        img_reload.setVisibility(visibility);
    }

    private void hideReloadData() {
        container_no_data.setVisibility(View.GONE);
        list_leave_req_my.setVisibility(View.VISIBLE);
    }
}
