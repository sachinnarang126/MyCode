package xyz.truehrms.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import xyz.truehrms.Interface.OnCancelPicassoCall;
import xyz.truehrms.R;
import xyz.truehrms.bean.CommentLikeList;

public class PostLikeAdapter extends RecyclerView.Adapter<PostLikeAdapter.ViewHolder> {
    private List<CommentLikeList.Result.LikeList> likeList;
    private Context context;

    public PostLikeAdapter(Context context, List<CommentLikeList.Result.LikeList> likeList) {
        this.likeList = likeList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_likes, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (likeList != null && likeList.size() > 0) {

            holder.likeby.setText(likeList.get(position).getEmpName().equalsIgnoreCase("") ? "NA" : likeList.get(position).getEmpName());
            String imageUrl = likeList.get(position).getEmpImage().trim();

            if (imageUrl.length() > 0) {
                try {
                    Picasso.with(context).load(imageUrl).placeholder(R.drawable.icon_profile).
                            error(ContextCompat.getDrawable(context, R.drawable.icon_profile)).into(holder.mAvatar);
                } catch (Exception e) {
                    holder.mAvatar.setImageResource(R.drawable.icon_profile);
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return likeList.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.cleanUp();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements OnCancelPicassoCall {
        private TextView likeby;
        private RoundedImageView mAvatar;

        public ViewHolder(View v) {
            super(v);
            likeby = (TextView) v.findViewById(R.id.likes_by_txt);
            mAvatar = (RoundedImageView) v.findViewById(R.id.likes_avatar);
        }

        @Override
        public void cleanUp() {
            Picasso.with(mAvatar.getContext())
                    .cancelRequest(mAvatar);
            mAvatar.setImageDrawable(null);
        }
    }
}
