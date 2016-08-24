package xyz.truehrms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.truehrms.R;
import xyz.truehrms.basecontroller.AppBaseCompatActivity;
import xyz.truehrms.bean.Permissions;
import xyz.truehrms.bean.TokenData;
import xyz.truehrms.bean.ValidateResponse;
import xyz.truehrms.dataholder.DataHolder;
import xyz.truehrms.parameters.User;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;
import xyz.truehrms.utils.Preferences;

public class LoginActivity extends AppBaseCompatActivity {
    private String token;
    private ProgressBar progressBar;
    private EditText edt_password, edt_username;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btnSignIn = (Button) findViewById(R.id.btn_signin);

        final String deviceID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        if (btnSignIn != null) {
            btnSignIn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edt_username.getText().toString().trim().length() == 0) {
                        showToast(getString(R.string.error_user_name));
                    } else if (edt_password.getText().toString().trim().length() == 0) {
                        showToast(getString(R.string.error_password));
                    } else {
                        hideKeyboard();
                        getToken(edt_password.getText().toString().trim(), RetrofitClient.getRetrofitClient(), deviceID);
                    }
                }
            });
        }
    }

    /*private void passwordSandwich(String pass, String salt, String deviceID, RetrofitApiService apiService) {
        manageProgressBar(true);
        String saltHex = toHex(salt);
        String sandwich = "";

        for (int i = 0; i < pass.length(); i++) {
            String passHex = toHex(String.valueOf(pass.toCharArray()[i]));
            sandwich += String.valueOf(passHex) + saltHex;
        }

        if (sandwich.contains(" ")) {
            sandwich = sandwich.replaceAll(" ", "");
        }

        getToken(pass, apiService, deviceID);
    }
*/
    /*public String toHex(String arg) {
        return String.format("%4x", new BigInteger(1, arg.getBytes()));
    }*/


    public void getToken(String sandwich, final RetrofitApiService apiService, String deviceID) {
        final String pass = sandwich;
        User u = new User();
        u.setUsername(deviceID);
        u.setDeviceType("Mobile");
        u.setAccountNumber("fl");

        final Call<TokenData> callData = apiService.getToken(u);
        putServiceCallInServiceMap(callData, Constant.GET_TOKEN);

        if (isInternetAvailable()) {
            manageProgressBar(true);
            callData.enqueue(new Callback<TokenData>() {
                @Override
                public void onResponse(Call<TokenData> call, Response<TokenData> response) {
                    if (response.isSuccessful()) {
                        try {
                            token = response.body().getResult().getToken();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            manageProgressBar(false);
                            showToast(getString(R.string.server_error));
                            return;
                        }

                        final User user = new User();
                        user.setUser_email(edt_username.getText().toString().trim());
                        user.setPassword(pass);
                        user.setToken("a152e84173914146e4bc4f391sd0f686ebc4f31");
                        user.setIp_address("10.20.3.133");
                        user.setMac_address("00-22-19-1A-F8-02");
                        user.setUser_agent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");

                        Call<ValidateResponse> callTokenData = apiService.validateToken(token, user);
                        putServiceCallInServiceMap(callTokenData, Constant.VALIDATE_TOKEN);

                        if (isInternetAvailable()) {
                            callTokenData.enqueue(new Callback<ValidateResponse>() {
                                @Override
                                public void onResponse(Call<ValidateResponse> call, Response<ValidateResponse> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body().getStatusCode() == 200.0) {

                                            ValidateResponse.Result obj = response.body().getResult();
                                            Preferences preferences = getPreference();
                                            preferences.saveToken(Constant.TOKEN, token);
                                            preferences.setStatus(Constant.IS_TOKEN_GOT, true);
                                            preferences.saveUser(Constant.SAVE_USER, user);
                                            preferences.saveAvatar(obj.getAvatar().trim());
                                            preferences.saveUserDetails(Constant.USER_DETAIL_OBJ, obj);

                                            if (obj.getRolename().trim().equalsIgnoreCase("Superadmin") /*|| obj.getId() == 7*//*obj.getRoleId() == Constant.SUPER_ADMIN || obj.getRoleId() == Constant.ADMIN*/) {
                                                getPreference().setHasAdminControl(true);
                                                manageProgressBar(false);
                                                Intent in = new Intent(LoginActivity.this, DashboardActivity.class);
                                                startActivity(in);
                                                finish();
                                            } else {
                                                // get permissions for user if user is not admin/super admin
                                                getPreference().setHasAdminControl(false);
                                                getPermissions(obj.getUserID(), token, apiService);
                                            }

                                        } else {
                                            manageProgressBar(false);
                                            showToast(response.body().getErrors().toString());
                                        }
                                    } else {
                                        manageProgressBar(false);
                                        showToast(getString(R.string.server_error));
                                    }
                                }

                                @Override
                                public void onFailure(Call<ValidateResponse> call, Throwable t) {
                                    t.printStackTrace();
                                    manageProgressBar(false);
                                }
                            });
                        } else {
                            manageProgressBar(false);
                            showToast(getString(R.string.error_internet));
                        }
                    }
                }

                @Override
                public void onFailure(Call<TokenData> call, Throwable t) {
                    t.printStackTrace();
                    manageProgressBar(false);
                    showToast(getString(R.string.server_error));
                }
            });
        } else {
            manageProgressBar(false);
            showToast(getString(R.string.error_internet));
        }
    }

    private void getPermissions(int ID, String token, RetrofitApiService apiService) {
        if (isInternetAvailable()) {

            Call<Permissions> call = apiService.getPermissions(token, ID);
            putServiceCallInServiceMap(call, Constant.GET_EMPLOYEE_PERMISSION);

            call.enqueue(new Callback<Permissions>() {
                @Override
                public void onResponse(Call<Permissions> call, Response<Permissions> response) {
                    manageProgressBar(false);
                    if (response.body().getStatusCode() == 200.0) {
                        DataHolder.getInstance().setResultList(response.body().getResult());
                        Intent in = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(in);
                        finish();
                    } else {
                        showToast(response.body().getErrors().toString());
                    }
                }

                @Override
                public void onFailure(Call<Permissions> call, Throwable t) {
                    t.printStackTrace();
                    manageProgressBar(false);
                }
            });
        } else {
            manageProgressBar(false);
            showToast(getString(R.string.error_internet));
        }
    }

    private void manageProgressBar(boolean hasToShowProgressBar) {
        if (hasToShowProgressBar) {
            btnSignIn.setEnabled(false);
            edt_password.setEnabled(false);
            edt_username.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            btnSignIn.setEnabled(true);
            edt_password.setEnabled(true);
            edt_username.setEnabled(true);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
