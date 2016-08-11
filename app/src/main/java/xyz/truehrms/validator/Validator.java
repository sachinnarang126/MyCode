package xyz.truehrms.validator;

import android.content.Context;

import xyz.truehrms.R;

public class Validator {

    public static String validateLoginFields(Context context, String userName, String password) {
        if (userName.length() == 0) {
            return context.getString(R.string.enter_user_name);
        } else if (password.length() == 0) {
            return (context.getString(R.string.enter_password));
        } else {
            return "";
        }
    }

    private static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

}
