package xyz.truehrms.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.List;

import atownsend.swipeopenhelper.SwipeOpenItemTouchHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.truehrms.R;
import xyz.truehrms.activities.DashboardActivity;
import xyz.truehrms.adapters.CommentsAdapter;
import xyz.truehrms.basecontroller.AppCompatFragment;
import xyz.truehrms.bean.CommentDelete;
import xyz.truehrms.bean.CommentLikeList;
import xyz.truehrms.bean.DeleteCommentParams;
import xyz.truehrms.parameters.AddComment;
import xyz.truehrms.retrofit.RetrofitApiService;
import xyz.truehrms.retrofit.RetrofitClient;
import xyz.truehrms.utils.Constant;

public class CommentsFragment extends AppCompatFragment implements CommentsAdapter.ButtonCallbacks {
    public EditText comment_content;
    private String selectedPostId = "";
    private RecyclerView commentsRecyclerView;
    private ProgressBar progressBar;
    private TextView btn_comment_submit;
    private List<CommentLikeList.Result.CommentList> commentLists;
    private CommentsAdapter commentsAdapter;
    private SwipeOpenItemTouchHelper helper;

    public static CommentsFragment newInstance(String selectedPostId) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("selectedPostId", selectedPostId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        selectedPostId = getArguments().getString("selectedPostId");
        progressBar = (ProgressBar) rootView.findViewById(R.id.comment_progress);
        comment_content = (EditText) rootView.findViewById(R.id.comment_content);

        /*InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);*/
        comment_content.requestFocus();

        commentsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        btn_comment_submit = (TextView) rootView.findViewById(R.id.btn_comment_submit);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        commentsRecyclerView.setLayoutManager(mLayoutManager);
        progressBar.setVisibility(View.VISIBLE);

        callCommentsService();

        btn_comment_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DashboardActivity) getActivity()).hideKeyboard();
                if (!isProgressBarVisible()) {
                    String commentText = comment_content.getText().toString().trim();
                    commentText = StringEscapeUtils.escapeJava(commentText);
                    if (commentText.length() > 0) {
                        if (((DashboardActivity) getActivity()).isInternetAvailable()) {
                            btn_comment_submit.setEnabled(false);
                            progressBar.setVisibility(View.VISIBLE);

                            AddComment addComment = new AddComment();
                            addComment.setCommentby(String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));
                            addComment.setCommentcontent(commentText);
                            addComment.setPostedfiles("");
                            addComment.setPostedFilesTitle("");
                            addComment.setPostid(selectedPostId);

                            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
                            Call<xyz.truehrms.bean.AddComment> addCommentCall = retrofitApiService.addComment(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), addComment);
                            putServiceCallInServiceMap(addCommentCall, Constant.ADD_COMMENT);

                            addCommentCall.enqueue(new Callback<xyz.truehrms.bean.AddComment>() {
                                @Override
                                public void onResponse(Call<xyz.truehrms.bean.AddComment> call, Response<xyz.truehrms.bean.AddComment> response) {
                                    comment_content.setText("");
                                    callCommentsService();
                                }

                                @Override
                                public void onFailure(Call<xyz.truehrms.bean.AddComment> call, Throwable t) {
                                    comment_content.setText("");
                                    enableSubmitButton();
                                    progressBar.setVisibility(View.GONE);
                                    t.printStackTrace();
                                }
                            });
                        } else {
                            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
                        }
                    } else {
                        ((DashboardActivity) getActivity()).showToast(getString(R.string.enter_comment));
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        commentsRecyclerView.setAdapter(null);
    }

    public void callCommentsService() {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
            Call<CommentLikeList> commentsLikesListsCall = retrofitApiService.getCommentsLikes(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), Integer.parseInt(selectedPostId));
            putServiceCallInServiceMap(commentsLikesListsCall, Constant.GET_COMMENTS_LIKES);

            commentsLikesListsCall.enqueue(new Callback<CommentLikeList>() {
                @Override
                public void onResponse(Call<CommentLikeList> call, Response<CommentLikeList> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0) {

                            if (commentLists != null)
                                commentLists.clear();

                            commentLists = response.body().getResult().getCommentList();
                            if (commentLists.size() > 0) {
                                commentsAdapter = new CommentsAdapter(getActivity(), commentLists, CommentsFragment.this);
                                commentsRecyclerView.setAdapter(commentsAdapter);
                                commentsRecyclerView.scrollToPosition(commentLists.size() - 1);

                                helper = new SwipeOpenItemTouchHelper(new SwipeOpenItemTouchHelper.SimpleCallback(
                                        SwipeOpenItemTouchHelper.START | SwipeOpenItemTouchHelper.END));
                                helper.attachToRecyclerView(commentsRecyclerView);
                                helper.setCloseOnAction(true);
                            }
                        }
                    }
                    enableSubmitButton();
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<CommentLikeList> call, Throwable t) {
                    enableSubmitButton();
                    progressBar.setVisibility(View.GONE);
                    t.printStackTrace();
                }
            });
        } else {
            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }

    private boolean isProgressBarVisible() {
        return progressBar.getVisibility() == View.VISIBLE;
    }

    private void enableSubmitButton() {
        if (btn_comment_submit != null)
            btn_comment_submit.setEnabled(true);
    }

    @Override
    public void removePosition(int position) {
        if (commentLists.get(position).getEmpId() == ((DashboardActivity) getActivity()).userDetailsObj.getId()) {
            callCommentDeleteService(String.valueOf(commentLists.get(position).getCommentId()), position);
        } else {
            ((DashboardActivity) getActivity()).showToast(getActivity().getString(R.string.error_comment_delete));
            if (helper != null)
                helper.closeOpenPosition(position);
        }
    }

    @Override
    public void editPosition(int position) {
        ((DashboardActivity) getActivity()).showToast("It will be implemented later");
        if (helper != null)
            helper.closeOpenPosition(position);
    }

    public void callCommentDeleteService(String commentID, final int position) {
        if (((DashboardActivity) getActivity()).isInternetAvailable()) {

            DeleteCommentParams deleteCommentParams = new DeleteCommentParams();
            deleteCommentParams.setCommentId(commentID);
            deleteCommentParams.setCommentby(String.valueOf(((DashboardActivity) getActivity()).userDetailsObj.getId()));

            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
            Call<CommentDelete> commentDeleteCall = retrofitApiService.deleteComment(((DashboardActivity) getActivity()).getPreference().getToken(Constant.TOKEN), deleteCommentParams);

            putServiceCallInServiceMap(commentDeleteCall, Constant.DELETE_COMMENT);

            progressBar.setVisibility(View.VISIBLE);
            commentDeleteCall.enqueue(new Callback<CommentDelete>() {
                @Override
                public void onResponse(Call<CommentDelete> call, Response<CommentDelete> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200.0) {
                            if (commentsAdapter != null) {
                                commentsAdapter.remove(position);
                            }
                        } else {
                            try {
                                if (isAdded() && getActivity() != null)
                                    ((DashboardActivity) getActivity()).showToast(getString(R.string.error_delete_comment));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        try {
                            if (isAdded() && getActivity() != null)
                                ((DashboardActivity) getActivity()).showToast(getString(R.string.error_delete_comment));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<CommentDelete> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    t.printStackTrace();
                    try {
                        if (isAdded() && getActivity() != null)
                            ((DashboardActivity) getActivity()).showToast(getString(R.string.server_error));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            ((DashboardActivity) getActivity()).showToast(getString(R.string.error_internet));
        }
    }
}
