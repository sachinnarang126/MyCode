package xyz.truehrms.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.truehrms.R;
import xyz.truehrms.activities.DashboardActivity;
import xyz.truehrms.dataholder.DataHolder;
import xyz.truehrms.utils.Constant;

public class LeaveRequestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leave_request, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean hasManagerPermission = false;

        ViewPager view_leave_req_pager = (ViewPager) view.findViewById(R.id.view_leave_req_pager);
        TabLayout view_leave_req_tab = (TabLayout) view.findViewById(R.id.view_leave_req_tab);
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

        final MyLeaveRequestFragment myLeaveRequestFragment;
        TeamLeaveRequestFragment teamLeaveRequestFragment = null;
        view_leave_req_tab.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), android.R.color.white));

        if (((DashboardActivity) getActivity()).getPreference().hasAdminControl()) {
            myLeaveRequestFragment = new MyLeaveRequestFragment();
            fragmentArrayList.add(myLeaveRequestFragment);
            teamLeaveRequestFragment = new TeamLeaveRequestFragment();
            fragmentArrayList.add(teamLeaveRequestFragment);
            fragmentArrayList.add(new OthersLeaveRequestFragment());
        } else {
            hasManagerPermission = ((DashboardActivity) getActivity()).hasPermission(Constant.APPROVE_ATTENDANCE_REQ_VIEW);
            if (hasManagerPermission) {
                myLeaveRequestFragment = new MyLeaveRequestFragment();
                fragmentArrayList.add(myLeaveRequestFragment);
                teamLeaveRequestFragment = new TeamLeaveRequestFragment();
                fragmentArrayList.add(teamLeaveRequestFragment);
            } else {
                myLeaveRequestFragment = new MyLeaveRequestFragment();
                fragmentArrayList.add(myLeaveRequestFragment);
                view_leave_req_tab.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
            }
        }

        LeaveReqPagerAdaptr leaveReqPagerAdaptr = new LeaveReqPagerAdaptr(getChildFragmentManager(), fragmentArrayList);
        view_leave_req_pager.setAdapter(leaveReqPagerAdaptr);
        view_leave_req_tab.setupWithViewPager(view_leave_req_pager);

        if (((DashboardActivity) getActivity()).userDetailsObj.getFirstname() != null) {
            view_leave_req_tab.getTabAt(0).setText(((DashboardActivity) getActivity()).userDetailsObj.getFirstname());
        } else {
            view_leave_req_tab.getTabAt(0).setText("");
        }

        if (((DashboardActivity) getActivity()).getPreference().hasAdminControl()) {
            view_leave_req_tab.getTabAt(1).setText(getString(R.string.team));
            view_leave_req_tab.getTabAt(2).setText(getString(R.string.others));
        } else if (hasManagerPermission) {
            view_leave_req_tab.getTabAt(1).setText(getString(R.string.team));
        }

        final TeamLeaveRequestFragment finalTeamLeaveRequestFragment = teamLeaveRequestFragment;
        view_leave_req_pager.setOffscreenPageLimit(2);

        view_leave_req_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //call leave service s=first time

                if (position == 1) {
                    ((DashboardActivity) getActivity()).hideKeyboard();
                    if (!((DashboardActivity) getActivity()).getPreference().hasAdminControl()) {
                        if (((DashboardActivity) getActivity()).hasPermission(Constant.APPROVE_LEAVE_REQ_VIEW)) {
                            if (finalTeamLeaveRequestFragment != null)
                                finalTeamLeaveRequestFragment.callService();
                        } else {
                            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_applied_leave));
                        }
                    } else {
                        if (finalTeamLeaveRequestFragment != null)
                            finalTeamLeaveRequestFragment.callService();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (DataHolder.getInstance() == null)
            ((DashboardActivity) getActivity()).restartApp();
    }

    public class LeaveReqPagerAdaptr extends FragmentStatePagerAdapter {
        private ArrayList<Fragment> fragmentArrayList;

        public LeaveReqPagerAdaptr(FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
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
