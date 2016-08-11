package xyz.truehrms.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import xyz.truehrms.Interface.OnCancelPicassoCall;
import xyz.truehrms.R;
import xyz.truehrms.activities.DashboardActivity;
import xyz.truehrms.bean.Post;
import xyz.truehrms.fragments.PostDetailFragment;
import xyz.truehrms.utils.Constant;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    private List<Post.Result> postsList;

    public PostAdapter(Context context, List<Post.Result> list) {
        this.postsList = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            holder.txt_dashbrd_usr_nm.setText(postsList.get(position).getEmpName());

            holder.txt_dashbrd_post_time.setText(postsList.get(position).getPostedOnString());
            holder.txt_post.setText(Html.fromHtml(postsList.get(position).getPostcontent()));
            holder.text_post_description.setText(Html.fromHtml(postsList.get(position).getPostcontentDesp()));

            String data;
            if (postsList.get(position).getLikeCounts() != null) {
                data = postsList.get(position).getLikeCounts() + " Likes";
                holder.txt_dashbrd_like.setText(data);
            } else {
                data = "0 Likes";
                holder.txt_dashbrd_like.setText(data);
            }

            if (postsList.get(position).getIsCurrentEmpLike()) {
                holder.img_dashbrd_like.setImageResource(R.drawable.ic_like);
            } else {
                holder.img_dashbrd_like.setImageResource(R.drawable.ic_unlike);
            }

            if (postsList.get(position).getCommentCounts() != null) {
                data = postsList.get(position).getCommentCounts() + " Comments";
                holder.txt_dashbrd_comments.setText(data);
            } else {
                data = "0 Comments";
                holder.txt_dashbrd_comments.setText(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String imageUrl = postsList.get(position).getEmpImage();
        if (imageUrl.length() > 0) {
            imageUrl = imageUrl.replaceAll(" ", "%20");

            Picasso.with(context).load(imageUrl).placeholder(ContextCompat.getDrawable(context, R.drawable.icon_profile)).
                    error(ContextCompat.getDrawable(context, R.drawable.icon_profile)).into(holder.tvPostUserPicture);
        }
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.cleanUp();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, OnCancelPicassoCall {

        private TextView txt_post;
        private ImageView img_dashbrd_like;
        private TextView txt_dashbrd_usr_nm, txt_dashbrd_post_time, txt_dashbrd_like, txt_dashbrd_comments, text_post_description;
        private RoundedImageView tvPostUserPicture;

        public ViewHolder(View itemView) {
            super(itemView);
            img_dashbrd_like = (ImageView) itemView.findViewById(R.id.img_dashbrd_like);
            tvPostUserPicture = (RoundedImageView) itemView.findViewById(R.id.post_user_picture);
            txt_dashbrd_usr_nm = (TextView) itemView.findViewById(R.id.post_user_name);
            txt_dashbrd_post_time = (TextView) itemView.findViewById(R.id.post_time);
            txt_dashbrd_like = (TextView) itemView.findViewById(R.id.txt_dashbrd_like);
            txt_dashbrd_comments = (TextView) itemView.findViewById(R.id.txt_dashbrd_comments);
            txt_post = (TextView) itemView.findViewById(R.id.post_text);
            text_post_description = (TextView) itemView.findViewById(R.id.post_text_description);
            itemView.setOnClickListener(this);
            //commentLayout = (LinearLayout) itemView.findViewById(R.id.comment_layout);
        }

        @Override
        public void onClick(View v) {
            loadPostDetails();
        }

        private void loadPostDetails() {
            PostDetailFragment mFragment = new PostDetailFragment();
            String tag = context.getString(R.string.post_detail);
            Bundle mBundle = new Bundle();
            int position = getLayoutPosition();
            mBundle.putString(Constant.POST_ID, String.valueOf(postsList.get(position).getId()));
            mBundle.putBoolean(Constant.IS_LIKED, postsList.get(position).getIsCurrentEmpLike());
            mBundle.putString(Constant.SELECTED_POST_EMP_NAME, postsList.get(position).getEmpName());
            mBundle.putString(Constant.SELECTED_POST_TIME, postsList.get(position).getPostedOnString());
            mBundle.putString(Constant.SELECTED_POST_CONTENT, postsList.get(position).getPostcontent());
            mBundle.putString(Constant.EMPLOYEE_PIC, postsList.get(position).getEmpImage());
            mBundle.putString("postDesc", postsList.get(position).getPostcontentDesp());
            mFragment.setArguments(mBundle);
            ((DashboardActivity) context).startFragmentTransaction(mFragment, tag, R.id.fragmentContainer);
            ((DashboardActivity) context).disableSwipeFromRightNavigation();
            ((DashboardActivity) context).manageOtherAppNavigation(false);
//            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

        @Override
        public void cleanUp() {
            Picasso.with(tvPostUserPicture.getContext())
                    .cancelRequest(tvPostUserPicture);
            tvPostUserPicture.setImageDrawable(null);
        }
    }
}