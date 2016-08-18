package xyz.truehrms.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.truehrms.R;
import xyz.truehrms.activities.DashboardActivity;
import xyz.truehrms.adapters.ProfilePagerAdapter;
import xyz.truehrms.basecontroller.AppCompatFragment;
import xyz.truehrms.bean.EmployeeInfo;
import xyz.truehrms.dataholder.DataHolder;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;
import xyz.truehrms.utils.FileUtils;

public class ProfileFragment extends AppCompatFragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ProgressBar progressBar;
    private RoundedImageView profilePic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton icn_edit_picture = (ImageButton) view.findViewById(R.id.icn_edit_picture);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_profl_tab);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs_profile);
        profilePic = (RoundedImageView) view.findViewById(R.id.user_pic);
        viewPager.setOffscreenPageLimit(2);
        icn_edit_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DashboardActivity) getActivity()).hideKeyboard();
                if (!((DashboardActivity) getActivity()).getPreference().hasAdminControl()) {
                    if (((DashboardActivity) getActivity()).hasPermission(Constant.PROFILE_EDIT)) {
                        selectImage();
                    } else {
                        ((DashboardActivity) getActivity()).showToast(getString(R.string.error_edit_profile));
                    }
                } else {
                    selectImage();
                }
            }
        });

        if (((DashboardActivity) getActivity()).userDetailsObj != null) {
            updateUserImage(((DashboardActivity) getActivity()).userDetailsObj.getAvatar().trim());

        }
        progressBar = (ProgressBar) view.findViewById(R.id.progress_profile);
        getEmployeeFromServer();
    }

    private void updateUserImage(String imageUrl) {
        try {
            Picasso.with(getActivity()).load(imageUrl).placeholder(ContextCompat.getDrawable(getActivity(), R.drawable.icon_profile)).
                    error(ContextCompat.getDrawable(getActivity(), R.drawable.icon_profile)).into(profilePic);
        } catch (Exception e) {
            profilePic.setImageResource(R.drawable.icon_profile);
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (DataHolder.getInstance() == null)
            ((DashboardActivity) getActivity()).restartApp();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((DashboardActivity) getActivity()).hideKeyboard();
        if (profilePic != null)
            Picasso.with(getActivity()).cancelRequest(profilePic);
    }

    public void addFragmentForViewPager(String email, String dob, int empCode, String reportng_to,
                                        ArrayList<String> reportng_tome, String shift, String fatherName, String motherName,
                                        String currentAddress, String permanentAddress, int sex, String designation, String divisionName, String doj) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        BasicFragment basicFragment = BasicFragment.getInstance(email, dob, empCode, reportng_to, reportng_tome, shift, designation, divisionName, doj);
        fragments.add(basicFragment);
        PersonalInformationFragment personalInformationFragment = PersonalInformationFragment.getInstance(fatherName, motherName, currentAddress, permanentAddress, sex);

        fragments.add(personalInformationFragment);
        fragments.add(LeaveFragment.getInstance());

        ////////set view pager adapter
        ProfilePagerAdapter profilePagradaptr = new ProfilePagerAdapter(getChildFragmentManager(), fragments);

        viewPager.setAdapter(profilePagradaptr);
        //set tab with view pager
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.basic);
        tabLayout.getTabAt(1).setText(R.string.personal);
        tabLayout.getTabAt(2).setText(R.string.leave);
        progressBar.setVisibility(View.GONE);
    }

    private void getEmployeeFromServer() {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
            Call<EmployeeInfo> getEmployeeCall;

            if (!isServiceCallExist(Constant.GET_EMP)) {
                getEmployeeCall = apiService.getEmployee(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
                putServiceCallInServiceMap(getEmployeeCall, Constant.GET_EMP);
            } else {
                getEmployeeCall = getServiceCallIfExist(Constant.GET_EMP);
                if (getEmployeeCall == null) {
                    getEmployeeCall = apiService.getEmployee(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
                    putServiceCallInServiceMap(getEmployeeCall, Constant.GET_EMP);
                }
            }

            getEmployeeCall.enqueue(new Callback<EmployeeInfo>() {
                @Override
                public void onResponse(Call<EmployeeInfo> call, Response<EmployeeInfo> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0) {
                            EmployeeInfo.Result result = response.body().getResult();

                            addFragmentForViewPager(result.getEmail(), result.getDob(), 1, result.getReportingTo(),
                                    new ArrayList<>(result.getReportingtome()), result.getShiftname(), result.getFathername(), result.getMothername(),
                                    result.getAddress1(), result.getAddress2(), result.getSex(), result.getDesignationDesignation1(), result.getDivisionname(), result.getDoj());
                            String imageUrl = result.getAvatar().trim();
                            updateUserImage(imageUrl);
                            ((DashboardActivity) getActivity()).setDashboardPicture(imageUrl);
                            ((DashboardActivity) getActivity()).getPreference().saveAvatar(imageUrl);
                        } else {
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<EmployeeInfo> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    if (isAdded() && getActivity() != null)
                        ((DashboardActivity) getActivity()).showToast(getString(R.string.server_error));
                    t.printStackTrace();
                }
            });
        } else {
            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    try {
                        checkCameraPermission();
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                        String errorMessage = "Your device doesn't support capturing images";
                        ((DashboardActivity) getActivity()).showToast(errorMessage);
                    }
                } else if (items[item].equals("Choose from Gallery")) {
                    try {
                        checkExternalStoragePermission();
                    } catch (Exception e) {
                        String errorMessage = "There are no images!";
                        ((DashboardActivity) getActivity()).showToast(errorMessage);
                    }

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    //  @TargetApi(Build.VERSION_CODES.M)
    public void checkCameraPermission() {
        final int permsRequestCode = 200;
        int hasCameraPermission, hasWriteExternalPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasCameraPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
            hasWriteExternalPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

            ArrayList<String> permissionList = new ArrayList<>();
            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.CAMERA);
            }

            if (hasWriteExternalPermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (permissionList.size() > 0) {
                String permissionArray[] = new String[permissionList.size()];
                requestPermissions(permissionList.toArray(permissionArray),
                        permsRequestCode);
            } else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        }
    }

    // @TargetApi(Build.VERSION_CODES.M)
    public void checkExternalStoragePermission() {
        final int permsRequestCode_ES = 201;
        int hasPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        permsRequestCode_ES);
                return;
            }

        }
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), 2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case 200:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length == 2) {

                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        ((DashboardActivity) getActivity()).showToast(getString(R.string.error_camera));
                        return;
                    }

                    if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                        ((DashboardActivity) getActivity()).showToast(getString(R.string.error_read_write_file));
                        return;
                    }

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                } else if (grantResults.length == 1) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);
                    } else {
                        if (permissions[0].contentEquals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_read_write_file));
                        } else {
                            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_camera));
                        }
                    }
                } else {
                    ((DashboardActivity) getActivity()).showToast(getString(R.string.error_permission));
                }

                break;

            case 201:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                            grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select File"), 2);
                    } else {
                        ((DashboardActivity) getActivity()).showToast(getString(R.string.error_gallery));
                    }
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }

    }


    //    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 1) {
                if (data != null) {
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                    FileOutputStream fo;
                    try {
                        destination.createNewFile();
                        fo = new FileOutputStream(destination);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    /*Bitmap Profile = ((DashboardActivity) getActivity()).getRoundedShapeBitmap(thumbnail, profilePic);
                    if (Profile != null) {
                        profilePic.setImageBitmap(Profile);
                    }*/

                    String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), thumbnail, "jhjh", null);
                    Uri selectedImageUri = Uri.parse(path);
                    String mPicturePath = FileUtils.compressImage(getActivity(), selectedImageUri);
                    File file = new File(mPicturePath);
                    uploadUserImage(file, thumbnail);
                }
            } else if (requestCode == 2) {

                if (data != null) {
                    Uri selectedImageUri = data.getData();
                    String[] projection = {MediaStore.MediaColumns.DATA};
                    CursorLoader cursorLoader = new CursorLoader(getActivity(), selectedImageUri, projection, null, null, null);
                    Cursor cursor = cursorLoader.loadInBackground();
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                    cursor.moveToFirst();
                    String selectedImagePath = cursor.getString(column_index);
                    Bitmap bm;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(selectedImagePath, options);
                    final int REQUIRED_SIZE = 200;
                    int scale = 1;
                    while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                            && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                        scale *= 2;
                    options.inSampleSize = scale;
                    options.inJustDecodeBounds = false;
                    bm = BitmapFactory.decodeFile(selectedImagePath, options);

                    String mPicturePath = FileUtils.compressImage(getActivity(), selectedImageUri);
                    File file = new File(mPicturePath);
                    uploadUserImage(file, bm);
                }

            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Image not Selected..", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Image not Selected..", Toast.LENGTH_LONG).show();
        }
    }


    private void uploadUserImage(final File file, final Bitmap bitmap) {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

            RetrofitApiService apiService = RetrofitClient.getMultipartRetrofitClient(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), "acebdf13572468");
            RequestBody empcode, filename, requestBody;

            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            //   MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body = MultipartBody.Part.createFormData("EmployeePic", file.getName(), requestBody);
            filename = RequestBody.create(MediaType.parse("multipart/form-data"), "filename.png");
            empcode = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
            Call<ResponseBody> call = apiService.upload(body, empcode, filename);

            putServiceCallInServiceMap(call, Constant.EDIT_PROFILE_PIC);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body().string());
                            String result = jsonObject.optString("Result");
                            if (result != null) {

                                if (bitmap != null)
                                    profilePic.setImageBitmap(bitmap);

                                JSONObject jsonObject1 = new JSONObject(result);
                                String avatar = jsonObject1.optString("avatar").trim();
                                ((DashboardActivity) getActivity()).userDetailsObj.setAvatar(avatar);
                                ((DashboardActivity) getActivity()).setDashboardPicture(avatar);
                                ((DashboardActivity) getActivity()).getPreference().saveAvatar(avatar);
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ((DashboardActivity) getActivity()).showToast("Error occurred while uploading image");
                    }

                    if (file.exists()) {
                        file.delete();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (file.exists()) {
                        file.delete();
                    }
                    ((DashboardActivity) getActivity()).showToast("Error occurred while uploading image");
                    t.printStackTrace();
                }
            });
        } else {
            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }
}