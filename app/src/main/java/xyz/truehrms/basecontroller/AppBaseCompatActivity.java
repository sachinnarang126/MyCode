package xyz.truehrms.basecontroller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import xyz.truehrms.activities.SplashActivity;
import xyz.truehrms.bean.Permissions;
import xyz.truehrms.dataholder.DataHolder;
import xyz.truehrms.utils.Preferences;


public abstract class AppBaseCompatActivity extends AppCompatActivity {

    /**
     * holds the executing or executed service call instances
     */

    private HashMap<String, Call> mServiceCallsMap;

    /**
     * this function will cancel all the service which can have an asynchronous response from server
     *
     * @param serviceCallList pass the list of service you want to cancel
     */

    private void cancelAllServiceCalls(List<Call> serviceCallList) {
        for (Call call : serviceCallList)
            if (call != null) call.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceCallsMap = new HashMap<>();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mServiceCallsMap != null) {
            System.out.println("--------->>>Destroying all services associated with this Activity<<<<--------");
            cancelAllServiceCalls(new ArrayList<>(mServiceCallsMap.values()));
            mServiceCallsMap = null;
        }
    }

    /**
     * display the toast
     *
     * @param text text to display
     */

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * check the permission if user is not admin or super-admin
     *
     * @param permissionCode permission code
     * @return true if user has the permission otherwise false
     */

    public boolean hasPermission(int permissionCode) {
        boolean hasPermission = false;
        try {
            for (Permissions.Result result : DataHolder.getInstance().getResultList()) {
                if (result.getIsActive() && result.getActionId() == permissionCode) {
                    hasPermission = true;
                    break;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return hasPermission;
    }

    /**
     * @return returns the shared preferences accessible on each activiy
     */

    public Preferences getPreference() {
        return Preferences.getPreference(this);
    }


    /**
     * hide keyboard without checking visible or not
     */

    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            if (getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    /**
     * checks whether internet is enabled or not
     *
     * @return true if enabled otherwise false
     */

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                    && PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                return activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
            } else
                return false;
        } else {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        }
    }

    /**
     * Check if the certain package(app) is installed on device or not
     *
     * @param targetPackage package name
     * @return true if installed otherwise false
     */

    public boolean isPackageExist(String targetPackage) {
        List<ApplicationInfo> packages;
        PackageManager pm;
        pm = getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(targetPackage)) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(targetPackage);
                startActivity(launchIntent);
                return true;
            }
        }
        openPlayStoreIfPackageNotExists(targetPackage);
        return false;
    }

    /**
     * open the play store certain app is not installed
     *
     * @param pack app package
     */

    public void openPlayStoreIfPackageNotExists(String pack) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + pack)));
    }

    /**
     * returns the service call object from service map, you can not override this method.
     *
     * @param key key value of the service call (Basically the url)
     * @param <T> Generic type of the service call
     * @return Returns the Generic type if exists otherwise null
     */

    final public <T> Call<T> getServiceCallIfExist(String key) {
        if (!mServiceCallsMap.containsKey(key))
            return mServiceCallsMap.get(key);
        else
            return null;
    }

    /**
     * create Call Service and put it in Service Map, you can not override this method.
     *
     * @param call Call Service object
     * @param key  key value of Call Service (Basically URL)
     * @param <T>  Generic type of Call Service
     */

    final public <T> void putServiceCallInServiceMap(Call<T> call, String key) {
        mServiceCallsMap.put(key, call);
    }

    /**
     * checks whether call service exists in service map or not, you can not override this method.
     *
     * @param key key of call service (Basically URL)
     * @return true or false
     */

    final public boolean isServiceCallExist(String key) {
        return mServiceCallsMap.containsKey(key);
    }

    /**
     * restart the APP if singleton(DataHolder.getInstance()) instance is null
     * Basically check when user open the app from background after some time
     */

    public void restartApp() {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
