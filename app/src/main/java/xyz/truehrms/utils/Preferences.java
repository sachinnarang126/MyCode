package xyz.truehrms.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import xyz.truehrms.parameters.User;
import xyz.truehrms.bean.Permissions;
import xyz.truehrms.bean.ValidateResponse;

public class Preferences {

    private static Preferences preferences;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Preferences(Context ctx) {
        sharedPreferences = ctx.getSharedPreferences(Constant.PREFERENCE, 0);
        editor = sharedPreferences.edit();
    }

    public static Preferences getPreference(Context context) {
        if (preferences == null) {
            preferences = new Preferences(context);
        }
        return preferences;
    }

    public SharedPreferences getSharedPreference() {
        return sharedPreferences;
    }

    public void setStatus(String key, boolean status) {
        editor.putBoolean(key, status);
        editor.commit();
    }

    public boolean getStatus(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void saveUser(String key, User user) {
        Gson obj = new Gson();
        String jsonString = obj.toJson(user);
        editor.putString(key, jsonString);
        editor.commit();
    }

    /*public void savePermission(String key, Permissions permissions) {
        Gson obj = new Gson();
        String jsonString = obj.toJson(permissions);
        editor.putString(key, jsonString);
        editor.commit();
    }

    public Permissions getPermission(String key) {
        Gson obj = new Gson();
        String jsonString = sharedPreferences.getString(key, "");
        return obj.fromJson(jsonString, Permissions.class);
    }*/

    public void saveUserDetails(String key, ValidateResponse.Result user) {
        Gson obj = new Gson();
        String jsonString = obj.toJson(user);
        editor.putString(key, jsonString);
        editor.commit();
    }

    public void saveAvatar(String Url) {
        editor.putString(Constant.AVATAR, Url);
        editor.commit();
    }

    public String getAvatar() {
        return sharedPreferences.getString(Constant.AVATAR, "");
    }

    public ValidateResponse.Result getUserDetails(String key) {
        Gson obj = new Gson();
        String jsonString = sharedPreferences.getString(key, "");
        return obj.fromJson(jsonString, ValidateResponse.Result.class);
    }

    public User getUser(String key) {
        Gson obj = new Gson();
        String jsonString = sharedPreferences.getString(key, "");
        return obj.fromJson(jsonString, User.class);
    }

    public void saveToken(String key, String token) {
        editor.putString(key, token);
        editor.commit();
    }

    public boolean hasAdminControl() {
        return sharedPreferences.getBoolean(Constant.HAS_ADMIN_CONTROL, false);
    }

    public void setHasAdminControl(boolean isAdminControl) {
        editor.putBoolean(Constant.HAS_ADMIN_CONTROL, isAdminControl).apply();
    }

    public String getToken(String key) {
        return sharedPreferences.getString(key, "Mob");
    }
}
