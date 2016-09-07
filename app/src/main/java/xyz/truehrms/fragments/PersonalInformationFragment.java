package xyz.truehrms.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.truehrms.R;
import xyz.truehrms.activities.DashboardActivity;
import xyz.truehrms.basecontroller.AppCompatFragment;
import xyz.truehrms.parameters.EditProfile;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;
import xyz.truehrms.widgets.CalendarEditText;

public class PersonalInformationFragment extends AppCompatFragment {
    private EditText et_father_name, et_mother_name, et_current_address, et_permanent_address,
            et_emergency_contact, et_city;
    private ProgressBar progressBar;
    //    private RadioButton rb_male, rb_female;
    private CalendarEditText calender_dob;
    private Spinner sp_blood_group;

    public static PersonalInformationFragment getInstance(String fatherName, String motherName, String curntAdd, String perAdd,
                                                          int sex, String dob, String bloodGroup, String emergencyContact, String city) {
        PersonalInformationFragment personalInformationFragment = new PersonalInformationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fatherName", fatherName);
        bundle.putString("motherName", motherName);
        bundle.putString("curntAdd", curntAdd);
        bundle.putString("perAdd", perAdd);
        bundle.putString("dob", dob);
        bundle.putString("bloodGroup", bloodGroup);
        bundle.putString("emergencyContact", emergencyContact);
        bundle.putString("city", city);
        bundle.putInt("sex", sex);
        personalInformationFragment.setArguments(bundle);
        return personalInformationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_information, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_father_name = (EditText) view.findViewById(R.id.et_father_name);
        et_mother_name = (EditText) view.findViewById(R.id.et_mother_name);
        et_current_address = (EditText) view.findViewById(R.id.et_current_address);
        et_permanent_address = (EditText) view.findViewById(R.id.et_permanent_address);
        progressBar = (ProgressBar) view.findViewById(R.id.edit_profile_progress_bar);
        final RadioButton rb_male = (RadioButton) view.findViewById(R.id.rb_male);
        final RadioButton rb_female = (RadioButton) view.findViewById(R.id.rb_female);

        calender_dob = (CalendarEditText) view.findViewById(R.id.calender_dob);
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.YEAR, -18);
        calender_dob.setMaxDate(calendar);
        calender_dob.setEnableTouch(false);

//        et_blood_group = (EditText) view.findViewById(R.id.et_blood_group);
        sp_blood_group = (Spinner) view.findViewById(R.id.sp_blood_group);
        et_emergency_contact = (EditText) view.findViewById(R.id.et_emergency_contact);
        et_city = (EditText) view.findViewById(R.id.et_city);
        List<String> bloodList = Arrays.asList(getResources().getStringArray(R.array.blood_group_array));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, bloodList);

        sp_blood_group.setAdapter(adapter);
        sp_blood_group.setEnabled(false);
        sp_blood_group.setFocusableInTouchMode(false);

        setTextFilter();

        ImageView iv_edit_personal_information = (ImageView) view.findViewById(R.id.iv_edit_personal_information);
        Bundle bundle = getArguments();

        if (bundle != null) {
            et_mother_name.setText(bundle.getString("motherName"));
            et_father_name.setText(bundle.getString("fatherName"));
            et_permanent_address.setText(bundle.getString("perAdd"));
            et_current_address.setText(bundle.getString("curntAdd"));
            calender_dob.setText(bundle.getString("dob"));
            int index = bloodList.indexOf(bundle.getString("bloodGroup"));
            if (index > 0) {
                sp_blood_group.setSelection(index);
            }
//            et_blood_group.setText(bundle.getString("bloodGroup"));
            et_emergency_contact.setText(bundle.getString("emergencyContact"));
            et_city.setText(bundle.getString("city"));

            if (bundle.getInt("sex") == 1) {
                rb_male.setChecked(true);
                rb_female.setChecked(false);
                rb_male.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                rb_female.setTextColor(ContextCompat.getColor(getActivity(), R.color.gray));
            } else if (bundle.getInt("sex") == 2) {
                rb_male.setChecked(false);
                rb_female.setChecked(true);
                rb_female.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                rb_male.setTextColor(ContextCompat.getColor(getActivity(), R.color.gray));
            } else {
                rb_male.setChecked(false);
                rb_female.setChecked(false);
            }
        }

        iv_edit_personal_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!((DashboardActivity) getActivity()).getPreference().hasAdminControl()) {
                    if (((DashboardActivity) getActivity()).hasPermission(Constant.PROFILE_EDIT)) {
                        prepareUpdateProfileCall(v, rb_male, rb_female);
                    } else {
                        ((DashboardActivity) getActivity()).showToast(getString(R.string.error_edit_profile));
                    }
                } else {
                    prepareUpdateProfileCall(v, rb_male, rb_female);
                }

                if (rb_female.isChecked()) {
                    rb_female.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                    rb_male.setTextColor(ContextCompat.getColor(getActivity(), R.color.gray));
                } else {
                    rb_male.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                    rb_female.setTextColor(ContextCompat.getColor(getActivity(), R.color.gray));
                }
            }
        });
    }

    private void callEditProfileService(RadioButton rb_female) {

        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

            progressBar.setVisibility(View.VISIBLE);
            RetrofitApiService apiService = RetrofitClient.getRetrofitClient();

            EditProfile editProfile = new EditProfile();
            editProfile.setAddress1(et_current_address.getText().toString().trim());
            editProfile.setAddress2(et_permanent_address.getText().toString().trim());
//            editProfile.setEmployeeID(((DashboardActivity) getActivity()).userDetailsObj.getId().toString());
            try {
                editProfile.setId(((DashboardActivity) getActivity()).userDetailsObj.getId());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                editProfile.setId(-1);
            }

            editProfile.setModifiedBy(String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
            editProfile.setId(((DashboardActivity) getActivity()).userDetailsObj.getId());
            editProfile.setFatherName(et_father_name.getText().toString().trim());
            editProfile.setMotherName(et_mother_name.getText().toString().trim());
            editProfile.setBloodgroup(sp_blood_group.getSelectedItem().toString());
            editProfile.setCity(et_city.getText().toString().trim());
            editProfile.setDob(changeDateFormatOfString("dd/MM/yyyy", "MM/dd/yyyy", calender_dob.getText().toString().trim()));
            editProfile.setEmergencycontactnumber(et_emergency_contact.getText().toString().trim());

            if (rb_female.isChecked()) {
                editProfile.setSex("2");
            } else {
                editProfile.setSex("1");
            }

            Call<xyz.truehrms.bean.EditProfile> editProfileCall = apiService.editProfile(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), editProfile);
            ((DashboardActivity) getActivity()).hideKeyboard();

            putServiceCallInServiceMap(editProfileCall, Constant.EDIT_PROFILE);

            editProfileCall.enqueue(new Callback<xyz.truehrms.bean.EditProfile>() {
                @Override
                public void onResponse(Call<xyz.truehrms.bean.EditProfile> call, Response<xyz.truehrms.bean.EditProfile> response) {
                    if (response.isSuccessful()) {

                        xyz.truehrms.bean.EditProfile.Result result = response.body().getResult();
                        if (response.body().getStatusCode() == 200.0 && result != null) {

                            et_mother_name.setText(result.getMotherName());
                            et_father_name.setText(result.getFatherName());
                            et_permanent_address.setText(result.getAddress2());
                            et_current_address.setText(result.getAddress1());
                            /*try {
                                calender_dob.setText(result.getDob().split("T")[0]);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }*/
                            if (!result.getEmergencycontactnumber().isEmpty()) {
                                et_emergency_contact.setText(result.getEmergencycontactnumber());
                            }
                            et_city.setText(result.getCity());
                            progressBar.setVisibility(View.GONE);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            try {
                                if (isAdded() && getActivity() != null)
                                    ((DashboardActivity) getActivity()).showToast(getString(R.string.server_error));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        try {
                            if (isAdded() && getActivity() != null)
                                ((DashboardActivity) getActivity()).showToast(getString(R.string.server_error));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<xyz.truehrms.bean.EditProfile> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    ((DashboardActivity) getActivity()).showToast(getString(R.string.server_error));
                    t.printStackTrace();
                }
            });
        } else {
            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }

    private void setTextFilter() {
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {

                    if (!Character.isLetter(source.charAt(i)) && source.charAt(i) != ' ') {
                        return "";
                    }
                }
                return null;
            }
        };
        et_father_name.setFilters(new InputFilter[]{filter});
        et_mother_name.setFilters(new InputFilter[]{filter});
        et_city.setFilters(new InputFilter[]{filter});
    }

    private boolean isValidFields(String dob, String fatherName, String motherName, String contactNo, String currentAddress,
                                  String permanentAddress, String city) {

        if (dob.isEmpty()) {
            ((DashboardActivity) getActivity()).showToast("Please enter date of birth");
            return false;
        } else if (fatherName.isEmpty()) {
            ((DashboardActivity) getActivity()).showToast("Please enter father name");
            return false;
        } else if (motherName.isEmpty()) {
            ((DashboardActivity) getActivity()).showToast("Please enter mother name");
            return false;
        } else if (sp_blood_group.getSelectedItemPosition() == 0) {
            ((DashboardActivity) getActivity()).showToast("Please select blood group");
            return false;
        } else if (contactNo.length() < 10) {
            ((DashboardActivity) getActivity()).showToast(getString(R.string.valid_contact_no));
            return false;
        } else if (currentAddress.isEmpty()) {
            ((DashboardActivity) getActivity()).showToast("please enter current address");
            return false;
        } else if (permanentAddress.isEmpty()) {
            ((DashboardActivity) getActivity()).showToast("please enter permanent address");
            return false;
        } else if (city.isEmpty()) {
            ((DashboardActivity) getActivity()).showToast("please enter city");
            return false;
        }
        return true;
    }

    public String changeDateFormatOfString(String inputFormat, String outputFormat, String inputDate) {
        Date parsed;
        String outputDate = "";
        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.getDefault());
        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }

    private void prepareUpdateProfileCall(View v, RadioButton rb_male, RadioButton rb_female) {
        if (v.getTag().toString().equals("show")) {
            v.setTag("hide");
            manageViews(true, (ImageView) v, rb_male, rb_female);
        } else {
            if (!isValidFields(calender_dob.getText().toString().trim(), et_father_name.getText().toString().trim(),
                    et_mother_name.getText().toString().trim(), et_emergency_contact.getText().toString().trim(),
                    et_current_address.getText().toString().trim(), et_permanent_address.getText().toString().trim(),
                    et_city.getText().toString().trim())) {
                return;
            }
            v.setTag("show");
            manageViews(false, (ImageView) v, rb_male, rb_female);
            callEditProfileService(rb_female);
        }
    }

    private void manageViews(boolean isEnable, ImageView imageView, RadioButton rb_male, RadioButton rb_female) {
        if (isEnable) {
            imageView.setImageResource(R.drawable.check);
        } else {
            imageView.setImageResource(R.drawable.edit);
        }

        et_father_name.setFocusableInTouchMode(isEnable);
        et_father_name.setEnabled(isEnable);

        rb_male.setEnabled(isEnable);
        rb_female.setEnabled(isEnable);

        et_mother_name.setFocusableInTouchMode(isEnable);
        et_mother_name.setEnabled(isEnable);

        et_permanent_address.setFocusableInTouchMode(isEnable);
        et_permanent_address.setEnabled(isEnable);

        et_current_address.setFocusableInTouchMode(isEnable);
        et_current_address.setEnabled(isEnable);

        calender_dob.setFocusableInTouchMode(isEnable);
        calender_dob.setEnabled(isEnable);
        calender_dob.setEnableTouch(isEnable);

                            /*et_blood_group.setFocusableInTouchMode(true);
                            et_blood_group.setEnabled(true);*/

        sp_blood_group.setEnabled(isEnable);
        sp_blood_group.setFocusableInTouchMode(isEnable);

        et_emergency_contact.setFocusableInTouchMode(isEnable);
        et_emergency_contact.setEnabled(isEnable);

        et_city.setFocusableInTouchMode(isEnable);
        et_city.setEnabled(isEnable);
    }

}
