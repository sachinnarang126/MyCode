package xyz.truehrms.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.truehrms.R;
import xyz.truehrms.activities.DashboardActivity;
import xyz.truehrms.basecontroller.AppCompatFragment;
import xyz.truehrms.bean.LeaveSummary;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;

public class LeaveFragment extends AppCompatFragment {
    private TextView carried_over_casul_txt, entitled_casul_txt, balanced_earned_txt, taken_earned_txt, entitled_earned_txt, carried_over_earned_txt, taken_sick_txt, balanced_casul_txt, taken_casul_txt, balanced_sick_txt, carried_over_sick_txt, entitled_sick_txt;

    public static Fragment getInstance() {
        return new LeaveFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leave, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        taken_casul_txt = (TextView) view.findViewById(R.id.taken_casul_txt);
        balanced_casul_txt = (TextView) view.findViewById(R.id.balanced_casul_txt);
        entitled_casul_txt = (TextView) view.findViewById(R.id.entitled_casul_txt);
        carried_over_casul_txt = (TextView) view.findViewById(R.id.carried_over_casul_txt);
        entitled_sick_txt = (TextView) view.findViewById(R.id.entitled_sick_txt);
        balanced_sick_txt = (TextView) view.findViewById(R.id.balanced_sick_txt);
        taken_sick_txt = (TextView) view.findViewById(R.id.taken_sick_txt);
        carried_over_sick_txt = (TextView) view.findViewById(R.id.carried_over_sick_txt);
        taken_earned_txt = (TextView) view.findViewById(R.id.taken_earned_txt);
        balanced_earned_txt = (TextView) view.findViewById(R.id.balanced_earned_txt);
        entitled_earned_txt = (TextView) view.findViewById(R.id.entitled_earned_txt);
        carried_over_earned_txt = (TextView) view.findViewById(R.id.carried_over_earned_txt);

        callLeaveSummeryService();
    }

    private void callLeaveSummeryService() {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

            RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
            Call<LeaveSummary> leaveSummeryCall;

            if (!isServiceCallExist(Constant.GET_LEAVE_SUMMERY)) {
                leaveSummeryCall = apiService.getLeaveSummery(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), ((DashboardActivity) getActivity()).userDetailsObj.getId());
                putServiceCallInServiceMap(leaveSummeryCall, Constant.GET_LEAVE_SUMMERY);
            } else {
                leaveSummeryCall = getServiceCallIfExist(Constant.GET_LEAVE_SUMMERY);

                if (leaveSummeryCall == null) {
                    leaveSummeryCall = apiService.getLeaveSummery(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), ((DashboardActivity) getActivity()).userDetailsObj.getId());
                    putServiceCallInServiceMap(leaveSummeryCall, Constant.GET_LEAVE_SUMMERY);
                }
            }

            leaveSummeryCall.enqueue(new Callback<LeaveSummary>() {
                @Override
                public void onResponse(Call<LeaveSummary> call, Response<LeaveSummary> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0) {
                            if (response.body().getResult() != null) {
                                List<LeaveSummary.Result> resultList = response.body().getResult();
                                for (int i = 0; i < resultList.size(); i++) {
                                    String LeaveType = resultList.get(i).getLeaveType();
                                    switch (LeaveType) {
                                        case "Casual Leave":
                                            carried_over_casul_txt.setText(String.valueOf(resultList.get(i).getCarriedOver()));
                                            entitled_casul_txt.setText(String.valueOf(resultList.get(i).getEntitled()));
                                            balanced_casul_txt.setText(String.valueOf(resultList.get(i).getBalance()));
                                            taken_casul_txt.setText(String.valueOf(resultList.get(i).getTaken()));
                                            break;
                                        case "Earned Leave":
                                            carried_over_earned_txt.setText(String.valueOf(resultList.get(i).getCarriedOver()));
                                            entitled_earned_txt.setText(String.valueOf(resultList.get(i).getEntitled()));
                                            balanced_earned_txt.setText(String.valueOf(resultList.get(i).getBalance()));
                                            taken_earned_txt.setText(String.valueOf(resultList.get(i).getTaken()));
                                            break;
                                        case "Sick Leave":
                                            carried_over_sick_txt.setText(String.valueOf(resultList.get(i).getCarriedOver()));
                                            entitled_sick_txt.setText(String.valueOf(resultList.get(i).getEntitled()));
                                            balanced_sick_txt.setText(String.valueOf(resultList.get(i).getBalance()));
                                            taken_sick_txt.setText(String.valueOf(resultList.get(i).getTaken()));
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<LeaveSummary> call, Throwable t) {
                    try {
                        if (isAdded() && getActivity() != null)
                            ((DashboardActivity) getActivity()).showToast(getString(R.string.server_error));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    t.printStackTrace();
                }
            });
        } else {
            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }
}
