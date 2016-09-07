package xyz.truehrms.activities;

import android.content.Intent;
import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.truehrms.R;
import xyz.truehrms.basecontroller.AppBaseCompatActivity;
import xyz.truehrms.bean.Permissions;
import xyz.truehrms.bean.ValidateResponse;
import xyz.truehrms.dataholder.DataHolder;
import xyz.truehrms.parameters.User;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;
import xyz.truehrms.utils.Preferences;

public class SplashActivity extends AppBaseCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        boolean isUserLoggedIn = getPreference().getStatus(Constant.IS_TOKEN_GOT);

        if (isUserLoggedIn) {
            User um = getPreference().getUser(Constant.SAVE_USER);
            validateUser(um, getPreference().getToken(Constant.TOKEN));
        } else
            openLoginActivity();
    }

    void validateUser(User um, final String token) {
        if (isInternetAvailable()) {

            final RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
            Call<ValidateResponse> callTokenData;

            if (!isServiceCallExist(Constant.VALIDATE_TOKEN)) {
                callTokenData = apiService.validateToken(token, um);
                putServiceCallInServiceMap(callTokenData, Constant.VALIDATE_TOKEN);
            } else {
                callTokenData = getServiceCallIfExist(Constant.VALIDATE_TOKEN);
                if (callTokenData == null) {
                    callTokenData = apiService.validateToken(token, um);
                    putServiceCallInServiceMap(callTokenData, Constant.VALIDATE_TOKEN);
                }
            }

            callTokenData.enqueue(new Callback<ValidateResponse>() {
                @Override
                public void onResponse(Call<ValidateResponse> call, Response<ValidateResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0) {
                            ValidateResponse.Result obj = response.body().getResult();
                            Preferences preferences = getPreference();
                            preferences.saveToken(Constant.TOKEN, token);
                            preferences.setStatus(Constant.IS_TOKEN_GOT, true);
                            preferences.saveAvatar(obj.getAvatar().trim());
                            preferences.saveUserDetails(Constant.USER_DETAIL_OBJ, obj);
                            if (obj.getRolename().trim().equalsIgnoreCase("Superadmin") /*|| obj.getId() == 7*//*obj.getRoleId() == Constant.SUPER_ADMIN || obj.getRoleId() == Constant.ADMIN*/) {
                                getPreference().setHasAdminControl(true);
                                Intent in = new Intent(SplashActivity.this, DashboardActivity.class);
                                startActivity(in);
                                finish();
                            } else {
                                // get permissions for user if user is not admin /super admin
                                getPreference().setHasAdminControl(false);
                                getPermissions(obj.getUserID(), token, apiService);
                            }
                        } else {
                            openLoginActivity();
                        }
                    } else {
                        openLoginActivity();
                    }
                }

                @Override
                public void onFailure(Call<ValidateResponse> call, Throwable t) {
                    openLoginActivity();
                    t.printStackTrace();
                }
            });
        } else {
            showToast(getString(R.string.error_internet));
        }
    }

    private void getPermissions(int ID, String token, RetrofitApiService apiService) {
        if (isInternetAvailable()) {
            Call<Permissions> permissioncall;
            if (!isServiceCallExist(Constant.GET_EMPLOYEE_PERMISSION)) {
                permissioncall = apiService.getPermissions(token, ID, Constant.PRODUCT_ID);
                putServiceCallInServiceMap(permissioncall, Constant.GET_EMPLOYEE_PERMISSION);
            } else {
                permissioncall = getServiceCallIfExist(Constant.GET_EMPLOYEE_PERMISSION);

                if (permissioncall == null) {
                    permissioncall = apiService.getPermissions(token, ID, Constant.PRODUCT_ID);
                    putServiceCallInServiceMap(permissioncall, Constant.GET_EMPLOYEE_PERMISSION);
                }
            }

            permissioncall.enqueue(new Callback<Permissions>() {
                @Override
                public void onResponse(Call<Permissions> call, Response<Permissions> response) {
                    if (response.isSuccessful() && response.body().getStatusCode() == 200.0) {
                        DataHolder.getInstance().setResultList(response.body().getResult());
                        Intent in = new Intent(SplashActivity.this, DashboardActivity.class);
                        startActivity(in);
                        finish();
                    } else {
                        openLoginActivity();
                    }
                }

                @Override
                public void onFailure(Call<Permissions> call, Throwable t) {
                    openLoginActivity();
                    t.printStackTrace();
                }
            });
        } else {
            showToast(getString(R.string.error_internet));
        }
    }

    private void openLoginActivity() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }
}
