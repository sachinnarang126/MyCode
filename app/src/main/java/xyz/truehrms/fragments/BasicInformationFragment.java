package xyz.truehrms.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.truehrms.R;

public class BasicInformationFragment extends Fragment {

    public static BasicInformationFragment getInstance(String empCode, String name, String email, String phone, String designation,
                                                       String department, String doj, String teamSize, String location, String shiftTiming,
                                                       String extension, String role, String manager, ArrayList<String> reportingToMe) {
        Bundle bundle = new Bundle();
        bundle.putString("empCode", empCode);
        bundle.putString("name", name);
        bundle.putString("email", email);
        bundle.putString("phone", phone);

        bundle.putString("designation", designation);
        bundle.putString("department", department);
        bundle.putString("doj", doj);
        bundle.putString("teamSize", teamSize);
        bundle.putString("location", location);
        bundle.putString("shiftTiming", shiftTiming);
        bundle.putString("extension", extension);
        bundle.putString("role", role);

        bundle.putString("manager", manager);
        bundle.putStringArrayList("reportingToMe", reportingToMe);
        BasicInformationFragment basicInformationFragment = new BasicInformationFragment();
        basicInformationFragment.setArguments(bundle);
        return basicInformationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_information, container, false);
        TextView txt_emp_id = (TextView) view.findViewById(R.id.txt_emp_id_value);
        TextView txt_name = (TextView) view.findViewById(R.id.txt_name_value);
        TextView txt_email = (TextView) view.findViewById(R.id.txt_email_value);
        TextView txt_phone = (TextView) view.findViewById(R.id.txt_phone_value);
        TextView txt_designation = (TextView) view.findViewById(R.id.txt_designation_value);
        TextView txt_department = (TextView) view.findViewById(R.id.txt_department_value);
        TextView txt_doj = (TextView) view.findViewById(R.id.txt_doj_val);
        TextView txt_team_size = (TextView) view.findViewById(R.id.txt_team_size_value);
        TextView txt_shift_timing = (TextView) view.findViewById(R.id.txt_shift_timing_value);
        TextView txt_extension = (TextView) view.findViewById(R.id.txt_extension_value);
        TextView txt_role = (TextView) view.findViewById(R.id.txt_role_value);
        TextView txt_manager = (TextView) view.findViewById(R.id.txt_manager_value);
        TextView tx_location_value = (TextView) view.findViewById(R.id.tx_location_value);
        TextView txt_reporting_to_me = (TextView) view.findViewById(R.id.txt_reporting_to_me_value);
        txt_reporting_to_me.setMovementMethod(new ScrollingMovementMethod());
//        txt_departmnt.setText();
//        txt_roll.setText();
        Bundle bundle = getArguments();

        if (bundle != null) {
            txt_emp_id.setText(bundle.getString("empCode"));
            txt_name.setText(bundle.getString("name"));
            txt_email.setText(bundle.getString("email"));
            txt_phone.setText(bundle.getString("phone"));

            txt_designation.setText(bundle.getString("designation"));
            txt_department.setText(bundle.getString("department"));
            txt_doj.setText(bundle.getString("doj"));
            txt_team_size.setText(bundle.getString("teamSize"));
            tx_location_value.setText(bundle.getString("location"));
            txt_shift_timing.setText(bundle.getString("shiftTiming"));
            txt_extension.setText(bundle.getString("extension"));
            txt_role.setText(bundle.getString("role"));

            txt_manager.setText(bundle.getString("manager"));
            String reportingToMe = TextUtils.join(",", bundle.getStringArrayList("reportingToMe"));
            txt_reporting_to_me.setText(reportingToMe);
        }
        return view;
    }
}
