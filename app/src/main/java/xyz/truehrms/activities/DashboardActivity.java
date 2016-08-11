package xyz.truehrms.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import xyz.truehrms.R;
import xyz.truehrms.basecontroller.AppBaseCompatActivity;
import xyz.truehrms.bean.ValidateResponse;
import xyz.truehrms.dataholder.DataHolder;
import xyz.truehrms.fragments.AttendanceFragment;
import xyz.truehrms.fragments.DashboardFragment;
import xyz.truehrms.fragments.EventFragment;
import xyz.truehrms.fragments.LeaveRequestFragment;
import xyz.truehrms.fragments.PostDetailFragment;
import xyz.truehrms.fragments.ProfileFragment;
import xyz.truehrms.fragments.PunchRequestFragment;
import xyz.truehrms.utils.Constant;

public class DashboardActivity extends AppBaseCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    public ValidateResponse.Result userDetailsObj;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private TextView user_name, userEmail;
    private RoundedImageView user_profile_pic;
    private boolean hasToShowMenu = true;
    private NavigationView navigationViewRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initialize();

        if (getPreference().getUserDetails(Constant.USER_DETAIL_OBJ) != null) {
            userDetailsObj = getPreference().getUserDetails(Constant.USER_DETAIL_OBJ);
        }

        setDashboardPicture(getPreference().getAvatar());

        String userName = userDetailsObj.getFirstname() + " " + userDetailsObj.getLastname();
        user_name.setText(userName);
        userEmail.setText(userDetailsObj.getEmail());

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View view) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                hideKeyboard();
            }
        };
        drawer.setScrimColor(ContextCompat.getColor(this, android.R.color.transparent));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        loadDashboardFragment();
    }

    public void setDashboardPicture(String imageUrl) {
        try {
            Picasso.with(this).load(imageUrl).placeholder(ContextCompat.getDrawable(this, R.drawable.icon_profile)).
                    error(ContextCompat.getDrawable(this, R.drawable.icon_profile)).into(user_profile_pic);
        } catch (Exception e) {
            user_profile_pic.setImageResource(R.drawable.icon_profile);
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (DataHolder.getInstance() == null)
            restartApp();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            // Always cancel the request here, this is safe to call even if the image has been loaded.
            // This ensures that the anonymous callback we have does not prevent the activity from
            // being garbage collected. It also prevents our callback from getting invoked even after the
            // activity has finished.
            if (user_profile_pic != null)
                Picasso.with(this).cancelRequest(user_profile_pic);
        }
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationViewLeft = (NavigationView) findViewById(R.id.nav_view);
        navigationViewRight = (NavigationView) findViewById(R.id.nav_view_right);

        if (navigationViewLeft != null) {
            navigationViewLeft.setNavigationItemSelectedListener(this);
        }

        if (navigationViewRight != null) {
            navigationViewRight.setNavigationItemSelectedListener(this);
        }

        View navView;
        if (navigationViewLeft != null) {
            navView = navigationViewLeft.inflateHeaderView(R.layout.navigation_header_dashboard);
            user_profile_pic = (RoundedImageView) navView.findViewById(R.id.user_profile_pic);
            user_name = (TextView) navView.findViewById(R.id.user_name);
            userEmail = (TextView) navView.findViewById(R.id.UserEmail);
            ImageButton ibEditProfile = (ImageButton) navView.findViewById(R.id.ib_edit_profile);
            ibEditProfile.setOnClickListener(this);
        }

        LinearLayout layout_true_project, layout_true_crm, layout_true_talent;

        layout_true_project = (LinearLayout) findViewById(R.id.layout_project);
        layout_true_crm = (LinearLayout) findViewById(R.id.layout_crm);
        layout_true_talent = (LinearLayout) findViewById(R.id.layout_talent);

        layout_true_project.setOnClickListener(this);
        layout_true_crm.setOnClickListener(this);
        layout_true_talent.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.getItem(0).setVisible(hasToShowMenu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_right:
                drawer.openDrawer(GravityCompat.END); /*Opens the Right Drawer*/
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        Fragment fragment;
        Bundle bundle;
        switch (item.getItemId()) {
            case R.id.nav_dashboard:
                toolbar.setTitle(getString(R.string.app_name));
                manageOtherAppNavigation(true);
                enableSwipeFromRightNavigation();
                startFragmentTransaction(DashboardFragment.getInstance(), getString(R.string.dashborad), R.id.fragmentContainer);
                return true;

            case R.id.subnav_attendances:
                toolbar.setTitle(getString(R.string.attendance));
                manageOtherAppNavigation(false);
                disableSwipeFromRightNavigation();
                startFragmentTransaction(new AttendanceFragment(), getString(R.string.attendance), R.id.fragmentContainer);
                return true;

            case R.id.subnav_attendance_requests:
                toolbar.setTitle(getString(R.string.view_attendence_request));
                manageOtherAppNavigation(false);
                disableSwipeFromRightNavigation();
                startFragmentTransaction(new PunchRequestFragment(), getString(R.string.manager_punch_request_fragment), R.id.fragmentContainer);
                return true;

            case R.id.subnav_leave_requests:
                toolbar.setTitle(getString(R.string.leave_requests));
                manageOtherAppNavigation(false);
                disableSwipeFromRightNavigation();
                startFragmentTransaction(new LeaveRequestFragment(), getString(R.string.leave_requests), R.id.fragmentContainer);
                return true;

            case R.id.subnav_birthdays:
                toolbar.setTitle(getString(R.string.birthday_heading));
                manageOtherAppNavigation(false);
                disableSwipeFromRightNavigation();
                fragment = new EventFragment();
                bundle = new Bundle();
                bundle.putString("occasionType", "1");
                fragment.setArguments(bundle);
                disableSwipeFromRightNavigation();
                startFragmentTransaction(fragment, getString(R.string.birthday_heading), R.id.fragmentContainer);
                return true;

            case R.id.subnav_aniversaries:
                toolbar.setTitle(getString(R.string.aniversary_heading));
                manageOtherAppNavigation(false);
                fragment = new EventFragment();
                bundle = new Bundle();
                bundle.putString("occasionType", "2");
                fragment.setArguments(bundle);
                disableSwipeFromRightNavigation();
                startFragmentTransaction(fragment, getString(R.string.aniversary_heading), R.id.fragmentContainer);
                return true;

            case R.id.subnav_new_joiners:
                toolbar.setTitle(getString(R.string.new_joinee_heading));
                manageOtherAppNavigation(false);
                fragment = new EventFragment();
                bundle = new Bundle();
                bundle.putString("occasionType", "3");
                fragment.setArguments(bundle);
                disableSwipeFromRightNavigation();
                startFragmentTransaction(fragment, getString(R.string.new_joinee_heading), R.id.fragmentContainer);
                return true;

/*            case R.id.nav_tickets:
                Utils.startFragmentTransaction(this, TicketsFragmentsFragment.getInstance(), getString(R.string.Tickets), R.id.fragmentContainer);
                return true;*/
/*
            case R.id.nav_profile:
                *//*setMenuItemChecked();
                item.setChecked(true);*//*
                toolbar.setTitle(getString(R.string.Profile));
                ProfileFragment profileFragment = new ProfileFragment();
                Utils.startFragmentTransaction(DashboardActivity.this, profileFragment, getString(R.string.Profile), R.id.fragmentContainer);

                return true;*/
            case R.id.nav_logout:
                manageOtherAppNavigation(false);
                disableSwipeFromRightNavigation();
                logout();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {  /*Closes the Appropriate Drawer*/
            drawer.closeDrawer(GravityCompat.END);
        } else {
            getCurrentFragment(getString(R.string.back_pressed), "");
        }
    }

    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        SharedPreferences sharedPreferences = getPreference().getSharedPreference();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        finish();
    }

    public void setToolbarForPostDetail() {
        toolbar.setTitle(getString(R.string.post_detail));

        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_back));
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                getCurrentFragment(getString(R.string.back_pressed), "");
            }
        });
    }

    public void getCurrentFragment(String check, String loadValue) {
        Fragment mFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (mFragment instanceof PostDetailFragment) {
            if (check.equalsIgnoreCase(getString(R.string.back_pressed))) {
                loadDashboardFragment();
            }
        } else {
            finish();
        }
    }

    private void loadDashboardFragment() {
        toolbar.setTitle(getString(R.string.app_name));
        toggle.setDrawerIndicatorEnabled(true);
        startFragmentTransaction(DashboardFragment.getInstance(), getString(R.string.dashborad), R.id.fragmentContainer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_edit_profile:
                if (!getPreference().hasAdminControl()) {
                    try {
                        if (hasPermission(Constant.PROFILE_VIEW)) {
                            manageOtherAppNavigation(false);
                            disableSwipeFromRightNavigation();
                            toolbar.setTitle(getString(R.string.profile));
                            ProfileFragment profileFragment = new ProfileFragment();
                            startFragmentTransaction(profileFragment, getString(R.string.profile), R.id.fragmentContainer);
                            onBackPressed();
                        } else {
                            showToast(getString(R.string.error_view_profile));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        manageOtherAppNavigation(false);
                        disableSwipeFromRightNavigation();
                    }
                } else {
                    manageOtherAppNavigation(false);
                    disableSwipeFromRightNavigation();
                    toolbar.setTitle(getString(R.string.profile));
                    ProfileFragment profileFragment = new ProfileFragment();
                    startFragmentTransaction(profileFragment, getString(R.string.profile), R.id.fragmentContainer);
                    onBackPressed();
                }
                break;

            case R.id.layout_project:
                drawer.closeDrawer(GravityCompat.END);
                isPackageExist(getString(R.string.package_project));

                break;
            case R.id.layout_crm:
                drawer.closeDrawer(GravityCompat.END);
                isPackageExist(getString(R.string.package_crm));

                break;

            case R.id.layout_talent:
                drawer.closeDrawer(GravityCompat.END);
                isPackageExist(getString(R.string.package_talent));
                break;
        }
    }

    public void startFragmentTransaction(Fragment fragment, String tag, int container) {
        try {
            FragmentManager mFragmentManager = getSupportFragmentManager();
            Fragment fragmentFromBackStack = mFragmentManager.findFragmentByTag(tag);
            if (fragmentFromBackStack == null) {
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(container, fragment, tag);
                // fragmentTransaction.addToBackStack(tag);
                fragmentTransaction.commit();
            } else {
                // this called if add to back stack
                mFragmentManager.popBackStack(tag, 0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void disableSwipeFromRightNavigation() {
        if (drawer != null && navigationViewRight != null)
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, navigationViewRight);
    }

    public void enableSwipeFromRightNavigation() {
        if (drawer != null && navigationViewRight != null)
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, navigationViewRight);
    }

    public void manageOtherAppNavigation(boolean visibility) {
        hasToShowMenu = visibility;
        invalidateOptionsMenu();
    }
}