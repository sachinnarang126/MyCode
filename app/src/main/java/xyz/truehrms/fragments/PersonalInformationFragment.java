package xyz.truehrms.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;

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

public class PersonalInformationFragment extends AppCompatFragment {
    private ImageView persnl_edit;
    private EditText fathr_nm_edt, edt_mthr, edt_curnt_adr, edt_permnt_adr;
    private ProgressBar progressBar;
    private RadioButton rb_male, rb_female;

    public static PersonalInformationFragment getInstance(String fatherName, String motherName, String curntAdd,
                                                          String perAdd, int sex) {
        PersonalInformationFragment personalInformationFragment = new PersonalInformationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fatherName", fatherName);
        bundle.putString("motherName", motherName);
        bundle.putString("curntAdd", curntAdd);
        bundle.putString("perAdd", perAdd);
        bundle.putInt("sex", sex);
        personalInformationFragment.setArguments(bundle);
        return personalInformationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_persona_information, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fathr_nm_edt = (EditText) view.findViewById(R.id.fathr_nm_edt);
        edt_mthr = (EditText) view.findViewById(R.id.edt_mthr);
        edt_curnt_adr = (EditText) view.findViewById(R.id.edt_curnt_adr);
        edt_permnt_adr = (EditText) view.findViewById(R.id.edt_permnt_adr);
        progressBar = (ProgressBar) view.findViewById(R.id.edit_profile_progress_bar);
        rb_male = (RadioButton) view.findViewById(R.id.rb_male);
        rb_female = (RadioButton) view.findViewById(R.id.rb_female);

        setTextFilter();
        persnl_edit = (ImageView) view.findViewById(R.id.persnl_edit);
       /* if (sexId == 1) {
            // txt_sex.setText("Male");

        } else {
            // txt_sex.setText("Female");

        }*/
        Bundle bundle = getArguments();

        if (bundle != null) {
            edt_mthr.setText(bundle.getString("motherName"));
            fathr_nm_edt.setText(bundle.getString("fatherName"));
            edt_permnt_adr.setText(bundle.getString("perAdd"));
            edt_curnt_adr.setText(bundle.getString("curntAdd"));
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

        persnl_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!((DashboardActivity) getActivity()).getPreference().hasAdminControl()) {
                    if (((DashboardActivity) getActivity()).hasPermission(Constant.PROFILE_EDIT)) {
                        if (v.getTag().toString().equals("show")) {
                            v.setTag("hide");
                            persnl_edit.setImageResource(R.drawable.check);
                            fathr_nm_edt.setFocusableInTouchMode(true);
                            fathr_nm_edt.setEnabled(true);
                            rb_male.setEnabled(true);
                            rb_female.setEnabled(true);
                            edt_mthr.setFocusableInTouchMode(true);
                            edt_mthr.setEnabled(true);
                            edt_permnt_adr.setFocusableInTouchMode(true);
                            edt_permnt_adr.setEnabled(true);
                            edt_curnt_adr.setFocusableInTouchMode(true);
                            edt_curnt_adr.setEnabled(true);

                        } else {
                            fathr_nm_edt.setFocusableInTouchMode(false);
                            fathr_nm_edt.setEnabled(false);
                            edt_mthr.setFocusableInTouchMode(false);
                            edt_mthr.setEnabled(false);
                            edt_permnt_adr.setFocusableInTouchMode(false);
                            edt_permnt_adr.setEnabled(false);
                            edt_curnt_adr.setFocusableInTouchMode(false);
                            edt_curnt_adr.setEnabled(false);
                            rb_male.setEnabled(false);
                            rb_female.setEnabled(false);
                            v.setTag("show");
                            persnl_edit.setImageResource(R.drawable.edit);

                            callEditProfileService(fathr_nm_edt.getText().toString().trim(), edt_mthr.getText().toString().trim(), edt_curnt_adr.getText().toString().trim(), edt_permnt_adr.getText().toString().trim());

                        }
                    } else {
                        ((DashboardActivity) getActivity()).showToast(getString(R.string.error_edit_profile));
                    }
                } else {
                    if (v.getTag().toString().equals("show")) {
                        v.setTag("hide");
                        persnl_edit.setImageResource(R.drawable.check);
                        fathr_nm_edt.setFocusableInTouchMode(true);
                        fathr_nm_edt.setEnabled(true);
                        edt_mthr.setFocusableInTouchMode(true);
                        edt_mthr.setEnabled(true);
                        edt_permnt_adr.setFocusableInTouchMode(true);
                        edt_permnt_adr.setEnabled(true);
                        rb_male.setEnabled(true);
                        rb_female.setEnabled(true);
                        edt_curnt_adr.setFocusableInTouchMode(true);
                        edt_curnt_adr.setEnabled(true);

                    } else {
                        fathr_nm_edt.setFocusableInTouchMode(false);
                        fathr_nm_edt.setEnabled(false);
                        edt_mthr.setFocusableInTouchMode(false);
                        edt_mthr.setEnabled(false);
                        edt_permnt_adr.setFocusableInTouchMode(false);
                        edt_permnt_adr.setEnabled(false);
                        rb_male.setEnabled(false);
                        rb_female.setEnabled(false);
                        edt_curnt_adr.setFocusableInTouchMode(false);
                        edt_curnt_adr.setEnabled(false);
                        v.setTag("show");
                        persnl_edit.setImageResource(R.drawable.edit);
                        callEditProfileService(fathr_nm_edt.getText().toString().trim(), edt_mthr.getText().toString().trim(), edt_curnt_adr.getText().toString().trim(), edt_permnt_adr.getText().toString().trim());

                    }
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

    private void callEditProfileService(String trim, String s, String trim1, String s1) {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

            progressBar.setVisibility(View.VISIBLE);
            RetrofitApiService apiService = RetrofitClient.getRetrofitClient();

            EditProfile editProfile = new EditProfile();
            editProfile.setAddress1(edt_curnt_adr.getText().toString().trim());
            editProfile.setAddress2(edt_permnt_adr.getText().toString().trim());
//            editProfile.setEmployeeID(((DashboardActivity) getActivity()).userDetailsObj.getId().toString());
            try {
                editProfile.setId(((DashboardActivity) getActivity()).userDetailsObj.getId());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                editProfile.setId(-1);
            }
            editProfile.setModifiedBy(String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
            editProfile.setId(((DashboardActivity) getActivity()).userDetailsObj.getId());
            editProfile.setFatherName(fathr_nm_edt.getText().toString().trim());
            editProfile.setMotherName(edt_mthr.getText().toString().trim());
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

                            edt_mthr.setText(result.getMotherName());
                            fathr_nm_edt.setText(result.getFatherName());
                            edt_permnt_adr.setText(result.getAddress2());
                            edt_curnt_adr.setText(result.getAddress1());
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
                    if (!Character.isLetter(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        fathr_nm_edt.setFilters(new InputFilter[]{filter});
        edt_mthr.setFilters(new InputFilter[]{filter});
    }
}