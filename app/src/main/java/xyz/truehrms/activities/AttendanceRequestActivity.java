package xyz.truehrms.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import xyz.truehrms.R;
import xyz.truehrms.basecontroller.AppBaseCompatActivity;
import xyz.truehrms.bean.ValidateResponse;
import xyz.truehrms.dataholder.DataHolder;
import xyz.truehrms.fragments.ApplyLeaveFragment;
import xyz.truehrms.fragments.ApplyPunchMissFragment;
import xyz.truehrms.utils.Constant;

public class AttendanceRequestActivity extends AppBaseCompatActivity {
    public ValidateResponse.Result userDetailsObj;
    private TabLayout tabLayout;
    private ApplyPunchMissFragment applyPunchMissFragment;
    private ApplyLeaveFragment applyLeaveFragment;
    private boolean isExecutingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_request);
        Toolbar attnd_req_toolbar = (Toolbar) findViewById(R.id.attnd_req_toolbar);
        attnd_req_toolbar.setTitle(getString(R.string.attendance_requests));
        attnd_req_toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(attnd_req_toolbar);
        ////////to set close img
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icn_close);

        if (getPreference().getUserDetails(Constant.USER_DETAIL_OBJ) != null) {
            userDetailsObj = getPreference().getUserDetails(Constant.USER_DETAIL_OBJ);
        }

        tabLayout = (TabLayout) findViewById(R.id.attnd_req_tab);
//        CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.attnd_req_pager);
        ViewPager viewPager = (ViewPager) findViewById(R.id.attnd_req_pager);
        applyLeaveFragment = new ApplyLeaveFragment();

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

        AttendanceRequestPagerAdapter viewAttendanceRequestPagerAdapter;

        if (!getIntent().getBooleanExtra("isFromFab", false)) {
            applyPunchMissFragment = new ApplyPunchMissFragment();
            fragmentArrayList.add(applyPunchMissFragment);
            fragmentArrayList.add(applyLeaveFragment);
            viewAttendanceRequestPagerAdapter = new AttendanceRequestPagerAdapter(getSupportFragmentManager(), fragmentArrayList);
            viewPager.setAdapter(viewAttendanceRequestPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setText(getString(R.string.punch_miss));
            tabLayout.getTabAt(1).setText(getString(R.string.leave));
            tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, android.R.color.white));
        } else {
            fragmentArrayList.add(applyLeaveFragment);
            viewAttendanceRequestPagerAdapter = new AttendanceRequestPagerAdapter(getSupportFragmentManager(), fragmentArrayList);
            viewPager.setAdapter(viewAttendanceRequestPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setText(getString(R.string.leave));
            tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, android.R.color.transparent));
        }


        /*if (isFromFab) {
            viewPager.setCurrentItem(1);
            // stop view pager swipe
            viewPager.setSwipeable(false);
            /// stop tab selection
            LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
            tabStrip.setEnabled(false);
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }
        } else {
            viewPager.setCurrentItem(0);
            viewPager.setSwipeable(true);
        }*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.done_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (DataHolder.getInstance() == null) {
            restartApp();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.attnd_req_done:

                if (!getIntent().getBooleanExtra("isFromFab", false)) {
                    int selectedTabPosition = tabLayout.getSelectedTabPosition();

                    if (getPreference().hasAdminControl()) {
                        if (selectedTabPosition == 0) {
                            applyPunchMissFragment.callPunchMissService();
                        } else if (!isExecutingService()) {
                            applyLeaveFragment.callApplyLeaveService();
                        }
                    } else {
                        if (hasPermission(Constant.APPLY_LEAVE_EDIT)) {
                            if (selectedTabPosition == 0) {
                                applyPunchMissFragment.callPunchMissService();
                            } else if (!isExecutingService())
                                applyLeaveFragment.callApplyLeaveService();
                        } else {
                            showToast(getString(R.string.error_add_leave));
                        }
                    }

                } else {

                    if (getPreference().hasAdminControl() && !isExecutingService()) {
                        applyLeaveFragment.callApplyLeaveService();
                    } else {
                        if (hasPermission(Constant.APPLY_LEAVE_EDIT)) {
                            if (!isExecutingService())
                                applyLeaveFragment.callApplyLeaveService();
                        } else
                            showToast(getString(R.string.error_add_leave));
                    }
                }
                break;
            case android.R.id.home:
                hideKeyboard();
                finish();
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    public void setIsExecutingService(boolean isExecutingService) {
        this.isExecutingService = isExecutingService;
    }

    public boolean isExecutingService() {
        return isExecutingService;
    }

    public class AttendanceRequestPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragmentArrayList;

        public AttendanceRequestPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
            super(fm);
            this.fragmentArrayList = fragmentArrayList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }
    }
}
