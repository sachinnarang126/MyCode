package xyz.truehrms.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.truehrms.R;
import xyz.truehrms.activities.AddPostActivity;
import xyz.truehrms.activities.DashboardActivity;
import xyz.truehrms.adapters.DashboardPagerAdapter;
import xyz.truehrms.adapters.PostAdapter;
import xyz.truehrms.basecontroller.AppCompatFragment;
import xyz.truehrms.bean.EmployeeAttendance;
import xyz.truehrms.bean.Post;
import xyz.truehrms.dataholder.DataHolder;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;
import xyz.truehrms.widgets.EndlessRecyclerOnScrollListener;

public class DashboardFragment extends AppCompatFragment implements ViewPager.OnPageChangeListener {
    private final int REQUEST_CODE_ADD_POST = 10;
    private List<Post.Result> postsList;
    private Button btn_load_more;
    private String token;
    private ProgressBar progressBar;
    private DashboardPagerAdapter dashboardPagerAdapter;
    private DashboardPagerFragment dashboardPagerFragment;
    private LinearLayout pagerIndicator;
    private ImageView[] dots;
    private int dotsCount;
    private boolean isLast = false;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    public static DashboardFragment getInstance() {
        return new DashboardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        token = ((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        progressBar = (ProgressBar) view.findViewById(R.id.posts_progress);

        pagerIndicator = (LinearLayout) view.findViewById(R.id.viewPagerCountDots);
        ViewPager mPager = (ViewPager) view.findViewById(R.id.pager_dashboard);
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        dashboardPagerFragment = new DashboardPagerFragment();
        fragmentArrayList.add(dashboardPagerFragment);
        btn_load_more = (Button) view.findViewById(R.id.btn_load_more);

        fragmentArrayList.add(new DashboardPagerFragment());
        fragmentArrayList.add(new DashboardPagerFragment());

        dashboardPagerAdapter = new DashboardPagerAdapter(getChildFragmentManager(), fragmentArrayList);
        mPager.setAdapter(dashboardPagerAdapter);
        mPager.addOnPageChangeListener(this);
        setUiPageViewController();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), AddPostActivity.class), REQUEST_CODE_ADD_POST);
            }
        });

        postsList = new ArrayList<>();
        postAdapter = new PostAdapter(getActivity(), postsList);
        recyclerView.setAdapter(postAdapter);

        if (((DashboardActivity) getActivity()).getPreference().hasAdminControl()) {
            fab.setVisibility(View.VISIBLE);
            getPunchInDetails();
            getPosts();
        } else if (((DashboardActivity) getActivity()).hasPermission(Constant.DASHBOARD_VIEW)) {
            fab.setVisibility(View.VISIBLE);
            getPunchInDetails();
            getPosts();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            if (requestCode == REQUEST_CODE_ADD_POST && data.hasExtra("post_added")) {
                if (data.getBooleanExtra("post_added", false))
                    getPosts();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (DataHolder.getInstance() == null)
            ((DashboardActivity) getActivity()).restartApp();
    }

    void getPunchInDetails() {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {
            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
            Call<EmployeeAttendance> employeeAttendanceCall;

            if (!isServiceCallExist(Constant.GET_PUNCH_DETAILS)) {
                employeeAttendanceCall = retrofitApiService.getPunchDetails(token, String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
                putServiceCallInServiceMap(employeeAttendanceCall, Constant.GET_PUNCH_DETAILS);
            } else {
                employeeAttendanceCall = getServiceCallIfExist(Constant.GET_PUNCH_DETAILS);

                if (employeeAttendanceCall == null) {
                    employeeAttendanceCall = retrofitApiService.getPunchDetails(token, String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
                    putServiceCallInServiceMap(employeeAttendanceCall, Constant.GET_PUNCH_DETAILS);
                }
            }

            employeeAttendanceCall.enqueue(new Callback<EmployeeAttendance>() {
                @Override
                public void onResponse(Call<EmployeeAttendance> call, Response<EmployeeAttendance> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0) {
                            EmployeeAttendance.Result rs = response.body().getResult();
                            dashboardPagerFragment.setValue(rs.getPunchIN(), rs.getPunchOUT(), rs.getPunchDate());
                        }
                    } else {
                        try {
                            if (isAdded() && getActivity() != null)
                                ((DashboardActivity) getActivity()).showToast(getString(R.string.server_error));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<EmployeeAttendance> call, Throwable t) {
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

    private void getPosts() {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {
            progressBar.setVisibility(View.VISIBLE);
            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
            Call<Post> postsCall;

            if (!isServiceCallExist(Constant.GET_POSTS)) {
                postsCall = retrofitApiService.getPosts(token, String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getCompanyId()), "1", "10", String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()), "0");
                putServiceCallInServiceMap(postsCall, Constant.GET_POSTS);
            } else {
                postsCall = getServiceCallIfExist(Constant.GET_POSTS);

                if (postsCall == null) {
                    postsCall = retrofitApiService.getPosts(token, String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getCompanyId()), "1", "10", String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()), "0");
                    putServiceCallInServiceMap(postsCall, Constant.GET_POSTS);
                }
            }

            postsCall.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0) {
                            if (response.body().getResult() != null && response.body().getResult().size() > 0) {
                                if (postsList != null) {
                                    postsList.clear();
                                    postsList.addAll(response.body().getResult());
                                    postAdapter.notifyDataSetChanged();
                                }
                                setAdapter();
                            }
                            progressBar.setVisibility(View.GONE);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            displayDataReloadAlert();
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        displayDataReloadAlert();
                    }
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    displayDataReloadAlert();
                    t.printStackTrace();
                }
            });
        } else {
            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }

    public void setAdapter() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        int totalRecord = 0;
        if (postsList.size() > 0)
            totalRecord = postsList.get(0).getTotalRecord();

        final int finalTotalRecord = totalRecord;
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void scroolabove(int current_page) {
                if (current_page == 1) {
                    btn_load_more.setVisibility(View.GONE);
                } else {
                    if (isLast) {
                        btn_load_more.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onLoadMore(final int current_page) {
                if (finalTotalRecord > ((current_page - 1) * 10)) {
                    isLast = true;
                    btn_load_more.setVisibility(View.VISIBLE);
                } else {
                    btn_load_more.setVisibility(View.GONE);
                }
                btn_load_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isLast = false;
                        if (((DashboardActivity) getActivity()).isInternetAvailable()) {
                            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
                            progressBar.setVisibility(View.VISIBLE);
                            Call<Post> postsCall = retrofitApiService.getPosts(token, "1", String.valueOf(current_page), "10",
                                    String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()), "0");
                            putServiceCallInServiceMap(postsCall, Constant.GET_POSTS);
                            postsCall.enqueue(new Callback<Post>() {
                                @Override
                                public void onResponse(Call<Post> call, Response<Post> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body().getStatusCode() == 200.0) {
                                            int ListCount = postAdapter.getItemCount();
                                            postsList.addAll(response.body().getResult());
                                            postAdapter.notifyItemRangeInserted(ListCount, postsList.size() - 1);
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Post> call, Throwable t) {
                                    progressBar.setVisibility(View.GONE);
                                    t.printStackTrace();
                                }
                            });
                        } else {
                            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
                        }

                        btn_load_more.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recyclerView.setAdapter(null);
    }

    private void setUiPageViewController() {
        if (pagerIndicator.getParent() != null) {
            pagerIndicator.removeAllViews();
        }
        dotsCount = dashboardPagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pagerIndicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selecteditem_dot));

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nonselecteditem_dot));
        }
        dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void displayDataReloadAlert() {
        try {
            if (isAdded() && getActivity() != null) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("True HR")
                        .setMessage("Error receiving post from server, Reload Post...?")
                        .setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                getPosts();
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                getActivity().finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
