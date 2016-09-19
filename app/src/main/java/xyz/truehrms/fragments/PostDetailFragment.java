package xyz.truehrms.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.truehrms.R;
import xyz.truehrms.activities.DashboardActivity;
import xyz.truehrms.basecontroller.AppCompatFragment;
import xyz.truehrms.bean.LikePost;
import xyz.truehrms.bean.UnLikePost;
import xyz.truehrms.dataholder.DataHolder;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;

public class PostDetailFragment extends AppCompatFragment {

    private RoundedImageView ivPostUserPicture;
    private ProgressBar progressBar;
    private String selectedPostId;
    private ImageView likes_img;
    private boolean isLiked = false;
    private PostLikeFragment postLikeFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
        ((DashboardActivity) getActivity()).setToolbarForPostDetail();
        String imageUrl = getArguments().getString(Constant.EMPLOYEE_PIC);

        if (imageUrl != null && imageUrl.length() > 0) {
            imageUrl = imageUrl.replaceAll(" ", "%20");
            try {
                Picasso.with(getActivity()).load(imageUrl).placeholder(ContextCompat.getDrawable(getActivity(), R.drawable.icon_profile)).
                        error(ContextCompat.getDrawable(getActivity(), R.drawable.icon_profile)).into(ivPostUserPicture);
            } catch (Exception e) {
                ivPostUserPicture.setImageResource(R.drawable.icon_profile);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (DataHolder.getInstance() == null)
            ((DashboardActivity) getActivity()).restartApp();
    }

    private void initializeViews(View rootView) {

        progressBar = (ProgressBar) rootView.findViewById(R.id.post_detail_progress);
        ivPostUserPicture = (RoundedImageView) rootView.findViewById(R.id.post_user_picture);

        TextView tvPostUserName = (TextView) rootView.findViewById(R.id.post_user_name);
        TextView tvPostTime = (TextView) rootView.findViewById(R.id.post_time);
        TextView tvPostText = (TextView) rootView.findViewById(R.id.post_text);
        tvPostText.setMovementMethod(new ScrollingMovementMethod());
        TextView tvPostTextDesc = (TextView) rootView.findViewById(R.id.post_text_description);
        likes_img = (ImageView) rootView.findViewById(R.id.likes_img);

        selectedPostId = getArguments().getString(Constant.POST_ID);
        tvPostUserName.setText(getArguments().getString(Constant.SELECTED_POST_EMP_NAME));
        tvPostTime.setText(getArguments().getString(Constant.SELECTED_POST_TIME));
        tvPostText.setText(getArguments().getString(Constant.SELECTED_POST_CONTENT));

        tvPostTextDesc.setText(StringEscapeUtils.unescapeJava(Html.fromHtml(getArguments().getString("postDesc")).toString()));

        isLiked = getArguments().getBoolean(Constant.IS_LIKED);

        if (getArguments().getBoolean(Constant.IS_LIKED)) {
            likes_img.setImageResource(R.drawable.ic_like);
        } else {
            likes_img.setImageResource(R.drawable.ic_unlike);
        }

        final CommentsFragment commentsFragment = CommentsFragment.newInstance(selectedPostId);
        final ViewPager mPostDetailPager = (ViewPager) rootView.findViewById(R.id.post_detail_pager);

        mPostDetailPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*if (position == 0) {
                    InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    if (commentsFragment.comment_content != null)
                        commentsFragment.comment_content.requestFocus();
                } else {
                    ((DashboardActivity) getActivity()).hideKeyboard();
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout mPostDetailTabs = (TabLayout) rootView.findViewById(R.id.post_detail_tabs);
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        postLikeFragment = PostLikeFragment.newInstance(selectedPostId);
        fragmentArrayList.add(commentsFragment);
        fragmentArrayList.add(postLikeFragment);
        CommentsLikePagerAdapter mCommentsLikePagerAdapter = new CommentsLikePagerAdapter(getChildFragmentManager(), fragmentArrayList);

        // Set up the ViewPager with the sections adapter.
        mPostDetailPager.setAdapter(mCommentsLikePagerAdapter);
        mPostDetailTabs.setupWithViewPager(mPostDetailPager);
        mPostDetailTabs.getTabAt(0).setText("Comments");
        mPostDetailTabs.getTabAt(1).setText("Likes");

        likes_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isProgressBarVisible()) {
                    if (isLiked) {
                        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

                            isLiked = false;
                            likes_img.setImageResource(R.drawable.ic_unlike);

                            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
                            Call<UnLikePost> likePostCall;

                            if (!isServiceCallExist(Constant.UN_LIKE_POST)) {
                                likePostCall = retrofitApiService.unLikePost(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), selectedPostId, String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
                                putServiceCallInServiceMap(likePostCall, Constant.UN_LIKE_POST);
                            } else {
                                likePostCall = getServiceCallIfExist(Constant.UN_LIKE_POST);

                                if (likePostCall == null) {
                                    likePostCall = retrofitApiService.unLikePost(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), selectedPostId, String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
                                    putServiceCallInServiceMap(likePostCall, Constant.UN_LIKE_POST);
                                }
                            }

                            progressBar.setVisibility(View.VISIBLE);
                            manageLikeUnLikeButton(false);
                            likePostCall.enqueue(new Callback<UnLikePost>() {
                                @Override
                                public void onResponse(Call<UnLikePost> call, Response<UnLikePost> response) {
                                    if (postLikeFragment != null)
                                        postLikeFragment.getCommentLikesList();
                                    manageLikeUnLikeButton(true);
                                    progressBar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onFailure(Call<UnLikePost> call, Throwable t) {
                                    progressBar.setVisibility(View.GONE);
                                    manageLikeUnLikeButton(true);
                                    t.printStackTrace();
                                }
                            });
                        } else {
                            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
                        }

                    } else {
                        if (((DashboardActivity) getActivity()).isInternetAvailable()) {
                            isLiked = true;
                            likes_img.setImageResource(R.drawable.ic_like);
                            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();

                            Call<LikePost> likePostCall;
                            if (!isServiceCallExist(Constant.LIKE_POST)) {
                                likePostCall = retrofitApiService.likePost(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), selectedPostId, String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
                                putServiceCallInServiceMap(likePostCall, Constant.LIKE_POST);
                            } else {
                                likePostCall = getServiceCallIfExist(Constant.LIKE_POST);

                                if (likePostCall == null) {
                                    likePostCall = retrofitApiService.likePost(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), selectedPostId, String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
                                    putServiceCallInServiceMap(likePostCall, Constant.LIKE_POST);
                                }
                            }

                            manageLikeUnLikeButton(false);
                            progressBar.setVisibility(View.VISIBLE);
                            likePostCall.enqueue(new Callback<LikePost>() {
                                @Override
                                public void onResponse(Call<LikePost> call, Response<LikePost> response) {
                                    if (postLikeFragment != null)
                                        postLikeFragment.getCommentLikesList();
                                    manageLikeUnLikeButton(true);
                                    progressBar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onFailure(Call<LikePost> call, Throwable t) {
                                    manageLikeUnLikeButton(true);
                                    progressBar.setVisibility(View.GONE);
                                    t.printStackTrace();
                                }
                            });
                        } else {
                            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((DashboardActivity) getActivity()).enableSwipeFromRightNavigation();
        ((DashboardActivity) getActivity()).manageOtherAppNavigation(true);
    }

    private void manageLikeUnLikeButton(boolean value) {
        likes_img.setEnabled(value);
    }

    private boolean isProgressBarVisible() {
        return progressBar.getVisibility() == View.VISIBLE;
    }

    public class CommentsLikePagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentArrayList;

        public CommentsLikePagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
            super(fm);
            this.fragmentArrayList = fragmentArrayList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return fragmentArrayList.size();
        }
    }
}
