package xyz.truehrms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import org.apache.commons.lang3.StringEscapeUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.truehrms.R;
import xyz.truehrms.basecontroller.AppBaseCompatActivity;
import xyz.truehrms.dataholder.DataHolder;
import xyz.truehrms.parameters.AddPost;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;
import xyz.truehrms.widgets.MaterialSpinner;


public class AddPostActivity extends AppBaseCompatActivity {
    private EditText etPostContentTitle, et_post_content_description;
    private MaterialSpinner spinner_post_visibility;
    private boolean isServiceExecuting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.add_post));
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icn_close);

        etPostContentTitle = (EditText) findViewById(R.id.et_post_content);
        et_post_content_description = (EditText) findViewById(R.id.et_post_content_title);
        spinner_post_visibility = (MaterialSpinner) findViewById(R.id.spinner_post_visibility);

        String arr[] = new String[]{"Visible to All", "Visible to Team"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arr);
        spinner_post_visibility.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (DataHolder.getInstance() == null) restartApp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.done_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.attnd_req_done:
                if (!isServiceExecuting) {
                    if (etPostContentTitle.getText().toString().trim().length() == 0) {
                        showToast(getString(R.string.enter_title));
                    } else if (et_post_content_description.getText().toString().trim().length() == 0) {
                        showToast(getString(R.string.enter_content));
                    } else if (spinner_post_visibility.getSelectedItemPosition() == 0) {
                        showToast(getString(R.string.select_post_visibility));
                    } else {
                        addPost(etPostContentTitle.getText().toString().trim(),
                                et_post_content_description.getText().toString().trim(),
                                String.valueOf(spinner_post_visibility.getSelectedItemPosition()));
                    }
                }
                return true;

            case android.R.id.home:
                Intent intent = getIntent();
                intent.putExtra("post_added", false);
                setResult(10, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addPost(String postContent, String description, String postVisibility) {
        if (isInternetAvailable()) {
            description = StringEscapeUtils.escapeJava(description);
            String token = getPreference().getToken(Constant.TOKEN);
            String employeeId = String.valueOf(getPreference().getUserDetails(Constant.USER_DETAIL_OBJ).getId());
            AddPost addPost = new AddPost();
            addPost.setPostcontent(postContent);
            addPost.setPostedby(employeeId);
            addPost.setVisibleTo(postVisibility);
            addPost.setCompany_id(String.valueOf(getPreference().getUserDetails(Constant.USER_DETAIL_OBJ).getCompanyId()));
            addPost.setPostcontentDesp(description);
            RetrofitApiService apiService = RetrofitClient.getRetrofitClient();

            isServiceExecuting = true;

            Call<xyz.truehrms.bean.AddPost> addPostCall = apiService.addPost(token, addPost);
            putServiceCallInServiceMap(addPostCall, Constant.ADD_POSTS);
            addPostCall.enqueue(new Callback<xyz.truehrms.bean.AddPost>() {
                @Override
                public void onResponse(Call<xyz.truehrms.bean.AddPost> call, Response<xyz.truehrms.bean.AddPost> response) {
                    if (response.isSuccessful() && response.body().getStatusCode() == 200.0) {
                        etPostContentTitle.setText("");
                        et_post_content_description.setText("");
                        Intent intent = getIntent();
                        intent.putExtra("post_added", true);
                        setResult(10, intent);
                        finish();
                    }
                    isServiceExecuting = false;
                }

                @Override
                public void onFailure(Call<xyz.truehrms.bean.AddPost> call, Throwable t) {
                    isServiceExecuting = false;
                    t.printStackTrace();
                }
            });
        } else {
            showToast(getString(R.string.error_internet));
        }
    }
}
