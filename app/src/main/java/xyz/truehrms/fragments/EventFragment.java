package xyz.truehrms.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.truehrms.R;
import xyz.truehrms.activities.DashboardActivity;
import xyz.truehrms.adapters.EventAdapter;
import xyz.truehrms.basecontroller.AppCompatFragment;
import xyz.truehrms.bean.Occasions;
import xyz.truehrms.dataholder.DataHolder;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;

public class EventFragment extends AppCompatFragment {
    private RecyclerView recyclerView;
    private ProgressBar progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        if (getArguments() != null) {
            String occasionType = getArguments().getString("occasionType");
            setOccasionType(Integer.parseInt(occasionType));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (DataHolder.getInstance() == null)
            ((DashboardActivity) getActivity()).restartApp();
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.birthdy_recyclerview);
        progress = (ProgressBar) view.findViewById(R.id.happng_tody_prgrs_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recyclerView.setAdapter(null);
    }

    public void setOccasionType(final int occasionType) {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

            String token = ((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN);
            String employeeID = String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId());

            progress.setVisibility(View.VISIBLE);
            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
            Call<Occasions> occasionsCall;

            if (!isServiceCallExist(Constant.GET_OCCASIONS)) {
                occasionsCall = retrofitApiService.getOccasions(token, String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getCompanyId()), employeeID);
                putServiceCallInServiceMap(occasionsCall, Constant.GET_OCCASIONS);
            } else {
                occasionsCall = getServiceCallIfExist(Constant.GET_OCCASIONS);

                if (occasionsCall == null) {
                    occasionsCall = retrofitApiService.getOccasions(token, String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getCompanyId()), employeeID);
                    putServiceCallInServiceMap(occasionsCall, Constant.GET_OCCASIONS);
                }
            }

            occasionsCall.enqueue(new Callback<Occasions>() {
                @Override
                public void onResponse(Call<Occasions> call, Response<Occasions> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0) {
                            if (response.body().getResult() != null && response.body().getResult().size() > 0) {
                                List<Occasions.Result> occasion_list = response.body().getResult();
                                List<Occasions.Result> occasion_list_type = new ArrayList<>();
                                for (int i = 0; i < occasion_list.size(); i++) {
                                    if (occasion_list.get(i).getOccationType() == occasionType) {
                                        occasion_list_type.add(occasion_list.get(i));
                                    }
                                }
                                if (occasion_list_type.size() > 0) {
                                    EventAdapter eventAdapter = new EventAdapter(getActivity(), occasion_list_type);
                                    recyclerView.setAdapter(eventAdapter);
                                } else
                                    ((DashboardActivity) getActivity()).showToast(getString(R.string.no_record));

                            } else {
                                ((DashboardActivity) getActivity()).showToast(getString(R.string.no_record));
                            }
                        } else {
                            ((DashboardActivity) getActivity()).showToast(getString(R.string.no_record));
                        }
                    } else {
                        ((DashboardActivity) getActivity()).showToast(getString(R.string.no_record));
                    }
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<Occasions> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    t.printStackTrace();
                }
            });
        } else {
            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }
}
