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
import xyz.truehrms.adapters.MyPunchRequestAdapter;
import xyz.truehrms.basecontroller.AppCompatFragment;
import xyz.truehrms.bean.MyAttendanceRequest;
import xyz.truehrms.parameters.Parameters;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;
import xyz.truehrms.widgets.EndlessRecyclerOnScrollListener;

public class MyPunchRequestFragment extends AppCompatFragment implements AdapterView.OnItemSelectedListener {
    private Spinner spinr_month, spinr_year;
    private int month, currentMonth, spinnerInitCount = 0;
    private RecyclerView list_attdnce_req_tl;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private Button btn_punch_load_more;
    private List<MyAttendanceRequest.Result.AaDatum> datumList;
    private String year, currentYear, token;
    private MyPunchRequestAdapter myPunchRequestAdapter;
    private RelativeLayout container_no_data;
    private ImageButton img_reload;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_punch_request, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinr_month = (Spinner) view.findViewById(R.id.spinr_month);
        btn_punch_load_more = (Button) view.findViewById(R.id.btn_punch_load_more);
        spinr_year = (Spinner) view.findViewById(R.id.spinr_year);
        list_attdnce_req_tl = (RecyclerView) view.findViewById(R.id.list_attdnce_req_tl);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        list_attdnce_req_tl.setLayoutManager(linearLayoutManager);
        progressBar = (ProgressBar) view.findViewById(R.id.my_punchreq_progress);

        container_no_data = (RelativeLayout) view.findViewById(R.id.container_no_data);
        img_reload = (ImageButton) view.findViewById(R.id.img_reload);
        img_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMyAttendanceRequest(String.valueOf(month + 1), year);
            }
        });

        ArrayAdapter<String> adapter_months = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.month_array));
        // set Selected Current mnth nd year
        Calendar calendar = Calendar.getInstance();
        currentMonth = month = calendar.get(Calendar.MONTH);
        currentYear = year = String.valueOf(calendar.get(Calendar.YEAR));

        spinr_month.setAdapter(adapter_months);
        spinr_month.setSelection(currentMonth);

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

        spinr_month.setOnItemSelectedListener(this);
        spinr_year.setOnItemSelectedListener(this);

        callMyAttendanceRequest(String.valueOf(month + 1), currentYear);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinnerInitCount < 2) {
            spinnerInitCount++;
        } else {
            Spinner spinner = (Spinner) parent;
            switch (spinner.getId()) {
                case R.id.spinr_year:
                    year = spinr_year.getSelectedItem().toString();
                    month = spinr_month.getSelectedItemPosition();
                    callMyAttendanceRequest(String.valueOf(month + 1), year);
                    break;
                case R.id.spinr_month:
                    year = spinr_year.getSelectedItem().toString();
                    if ((position + 1) > (currentMonth + 1) && currentYear.equalsIgnoreCase(year)) {
                        clearItemsOfRecyclerView();
                    } else {
                        month = position;
                        callMyAttendanceRequest(String.valueOf(month + 1), year);
                    }

                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void callMyAttendanceRequest(final String month, final String year) {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

            clearItemsOfRecyclerView();
            progressBar.setVisibility(View.VISIBLE);
            token = ((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN);
            Parameters serviceParams = new Parameters();

            serviceParams.setSearchInput("");
            serviceParams.setEmployeeID(String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
            serviceParams.setMonth(month);
            serviceParams.setYear(year);
            serviceParams.setPageIndex("1");
            serviceParams.setPageSize("10");

            final RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
            Call<MyAttendanceRequest> myAttendanceRequestCall = apiService.myAttendanceRequest(token, serviceParams);
            hideReloadData();

            putServiceCallInServiceMap(myAttendanceRequestCall, Constant.MY_ATTENDANCE_REQ);
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
                                    list_attdnce_req_tl.setAdapter(myPunchRequestAdapter);
                                }
                                progressBar.setVisibility(View.GONE);
                                final int totalRecord = response.body().getResult().getRecordsTotal();
                                list_attdnce_req_tl.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
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
                                                    serviceParams.setEmployeeID(String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
                                                    serviceParams.setMonth(month);
                                                    serviceParams.setYear(year);
                                                    serviceParams.setPageIndex(String.valueOf(current_page));
                                                    serviceParams.setPageSize("10");
                                                    Call<MyAttendanceRequest> myAttendanceRequestCall1 = apiService.myAttendanceRequest(token, serviceParams);
                                                    putServiceCallInServiceMap(myAttendanceRequestCall1, Constant.MY_ATTENDANCE_REQ);
                                                    myAttendanceRequestCall1.enqueue(new Callback<MyAttendanceRequest>() {
                                                        @Override
                                                        public void onResponse(Call<MyAttendanceRequest> call, Response<MyAttendanceRequest> response) {
                                                            if (response.isSuccessful() && response.body().getStatusCode() == 200.0) {
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
                                progressBar.setVisibility(View.GONE);
                                btn_punch_load_more.setVisibility(View.GONE);
                                showReloadData(View.INVISIBLE);
                            }
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
                }

                @Override
                public void onFailure(Call<MyAttendanceRequest> call, Throwable t) {
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
        if (datumList != null) {
            datumList.clear();
            if (myPunchRequestAdapter != null)
                myPunchRequestAdapter.notifyDataSetChanged();
        }
    }

    private void showReloadData(int visibility) {
        container_no_data.setVisibility(View.VISIBLE);
        list_attdnce_req_tl.setVisibility(View.GONE);
        img_reload.setVisibility(visibility);
    }

    private void hideReloadData() {
        container_no_data.setVisibility(View.GONE);
        list_attdnce_req_tl.setVisibility(View.VISIBLE);
    }
}
