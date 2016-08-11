package xyz.truehrms.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.truehrms.R;
import xyz.truehrms.activities.DashboardActivity;
import xyz.truehrms.adapters.PostLikeAdapter;
import xyz.truehrms.basecontroller.AppCompatFragment;
import xyz.truehrms.bean.CommentLikeList;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;

public class PostLikeFragment extends AppCompatFragment {
    private String selectedPostId;
    private List<CommentLikeList.Result.LikeList> likeList;
    private RecyclerView likes_recyclerView;
    private PostLikeAdapter postLikeAdapter;
//    private ProgressBar progressBar;

    public static PostLikeFragment newInstance(String selectedPostId) {

        PostLikeFragment fragment = new PostLikeFragment();
        Bundle args = new Bundle();
        args.putString("selectedPostId", selectedPostId);
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_like, container, false);
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        selectedPostId = getArguments().getString("selectedPostId");

//        progressBar = (ProgressBar) rootView.findViewById(R.id.likes_progress);
        likes_recyclerView = (RecyclerView) rootView.findViewById(R.id.likes_recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        likes_recyclerView.setLayoutManager(mLayoutManager);
        getCommentLikesList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        likes_recyclerView.setAdapter(null);
    }

    public void getCommentLikesList() {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

//            progressBar.setVisibility(View.VISIBLE);

            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
            Call<CommentLikeList> commentsLikesListsCall;

            if (!isServiceCallExist(Constant.GET_COMMENTS_LIKES)) {
                commentsLikesListsCall = retrofitApiService.getCommentsLikes(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), Integer.parseInt(selectedPostId));
                putServiceCallInServiceMap(commentsLikesListsCall, Constant.GET_COMMENTS_LIKES);
            } else {
                commentsLikesListsCall = getServiceCallIfExist(Constant.GET_COMMENTS_LIKES);
                if (commentsLikesListsCall == null) {
                    commentsLikesListsCall = retrofitApiService.getCommentsLikes(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), Integer.parseInt(selectedPostId));
                    putServiceCallInServiceMap(commentsLikesListsCall, Constant.GET_COMMENTS_LIKES);
                }
            }

            commentsLikesListsCall.enqueue(new Callback<CommentLikeList>() {
                @Override
                public void onResponse(Call<CommentLikeList> call, Response<CommentLikeList> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0 && response.body().getResult() != null) {
                            if (response.body().getResult().getLikeList() != null && response.body().getResult().getLikeList().size() > 0) {
                                if (likeList != null) {
                                    likeList.clear();
                                    likeList.addAll(response.body().getResult().getLikeList());
                                    postLikeAdapter.notifyDataSetChanged();
                                } else {
                                    likeList = response.body().getResult().getLikeList();
                                    postLikeAdapter = new PostLikeAdapter(getActivity(), likeList);
                                    likes_recyclerView.setAdapter(postLikeAdapter);
                                }
//                                progressBar.setVisibility(View.GONE);
                            } else {
                                clearItemsOfRecyclerView();
//                                progressBar.setVisibility(View.GONE);
                            }
                        } else {
                            clearItemsOfRecyclerView();
//                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        clearItemsOfRecyclerView();
//                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<CommentLikeList> call, Throwable t) {
//                    progressBar.setVisibility(View.GONE);
                    t.printStackTrace();
                }
            });
        } else {
            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }

    private void clearItemsOfRecyclerView() {
        if (likeList != null) {
            likeList.clear();
            if (postLikeAdapter != null) {
                postLikeAdapter.notifyDataSetChanged();
            }
        }
    }
}
