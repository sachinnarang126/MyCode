package xyz.truehrms.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.truehrms.R;

public class BasicFragment extends Fragment {

    public static BasicFragment getInstance(String email, String dob, int empCode, String reportingTo,
                                            ArrayList<String> reporting_to_me, String shift, String designation, String divisionName, String doj) {
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("dob", dob);
        bundle.putString("doj", doj);
        bundle.putInt("empCode", empCode);
        bundle.putStringArrayList("reporting_to_me", reporting_to_me);
        bundle.putString("shift", shift);
        bundle.putString("reportingTo", reportingTo);
        bundle.putString("designation", designation);
        bundle.putString("divisionName", divisionName);
        BasicFragment basicFragment = new BasicFragment();
        basicFragment.setArguments(bundle);
        return basicFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic, container, false);
        TextView txt_company = (TextView) view.findViewById(R.id.txt_company);
        TextView txt_departmnt = (TextView) view.findViewById(R.id.txt_departmnt);
        TextView txt_roll = (TextView) view.findViewById(R.id.txt_roll);
        TextView txt_shift = (TextView) view.findViewById(R.id.txt_shift);
        TextView txt_emp_code = (TextView) view.findViewById(R.id.txt_emp_code);
        TextView txt_doj_val = (TextView) view.findViewById(R.id.txt_doj_val);
        TextView txt_emp_email = (TextView) view.findViewById(R.id.txt_emp_email);
        TextView txt_officl_email = (TextView) view.findViewById(R.id.txt_officl_email);
        TextView txt_dob = (TextView) view.findViewById(R.id.txt_dob);
        TextView txt_reportng_to = (TextView) view.findViewById(R.id.txt_reportng_to);
        TextView txt_reportng_tome = (TextView) view.findViewById(R.id.txt_reportng_tome);
//        txt_departmnt.setText();
//        txt_roll.setText();
        Bundle bundle = getArguments();

        if (bundle != null) {

            txt_shift.setText(bundle.getString("shift"));
            txt_emp_code.setText(String.valueOf(bundle.getInt("empcode")));
            txt_doj_val.setText(bundle.getString("doj"));
            txt_emp_email.setText(bundle.getString("email"));
            // txt_officl_email.setText();
            txt_dob.setText(bundle.getString("dob"));

            String reprtng_tome = TextUtils.join(",", bundle.getStringArrayList("reporting_to_me"));
            txt_reportng_tome.setText(reprtng_tome);
            txt_reportng_to.setText(bundle.getString("reportingTo"));
            txt_roll.setText(bundle.getString("designation"));
            txt_departmnt.setText(bundle.getString("divisionName"));
        }
        return view;
    }
}
