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
import xyz.truehrms.utils.Constant;

public class PunchRequestFragment extends Fragment {
    private TeamPunchRequestFragment teamPunchRequestFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_punch_request, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_attnd_req_pager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.view_attnd_req_tab);
        boolean hasManagerPermission = false;
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), android.R.color.white));
        if (((DashboardActivity) getActivity()).getPreference().hasAdminControl()) {
            fragmentArrayList.add(new MyPunchRequestFragment());
            teamPunchRequestFragment = new TeamPunchRequestFragment();
            fragmentArrayList.add(teamPunchRequestFragment);
            fragmentArrayList.add(new OthersPunchRequestFragment());

        } else {
            hasManagerPermission = ((DashboardActivity) getActivity()).hasPermission(Constant.APPROVE_LEAVE_REQ_VIEW);
            if (hasManagerPermission) {
                fragmentArrayList.add(new MyPunchRequestFragment());
                teamPunchRequestFragment = new TeamPunchRequestFragment();
                fragmentArrayList.add(teamPunchRequestFragment);
            } else {
                fragmentArrayList.add(new MyPunchRequestFragment());
                tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
            }
        }

        ViewAttendanceRequestPagerAdapter viewAttendanceRequestPagerAdapter = new ViewAttendanceRequestPagerAdapter(getChildFragmentManager(), fragmentArrayList);
        viewPager.setAdapter(viewAttendanceRequestPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        if (((DashboardActivity) getActivity()).userDetailsObj.getFirstname() != null) {
            String name = ((DashboardActivity) getActivity()).userDetailsObj.getFirstname();
            tabLayout.getTabAt(0).setText(name);

        } else {
            tabLayout.getTabAt(0).setText("");

        }
        if (((DashboardActivity) getActivity()).getPreference().hasAdminControl()) {
            tabLayout.getTabAt(1).setText("Team");
            tabLayout.getTabAt(2).setText("Others");
        } else if (hasManagerPermission) {
            tabLayout.getTabAt(1).setText("Team");
        }
        viewPager.setOffscreenPageLimit(2);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    ((DashboardActivity) getActivity()).hideKeyboard();
                    if (!((DashboardActivity) getActivity()).getPreference().hasAdminControl()) {
                        if (((DashboardActivity) getActivity()).hasPermission(Constant.APPROVE_ATTENDANCE_REQ_VIEW)) {
                            teamPunchRequestFragment.callService();
                        } else {
                            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_view_applied_attendance));
                        }
                    } else {
                        teamPunchRequestFragment.callService();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class ViewAttendanceRequestPagerAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Fragment> fragmentArrayList;

        public ViewAttendanceRequestPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
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