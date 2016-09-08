package xyz.truehrms.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import atownsend.swipeopenhelper.BaseSwipeOpenViewHolder;
import xyz.truehrms.Interface.OnCancelPicassoCall;
import xyz.truehrms.R;
import xyz.truehrms.bean.CommentLikeList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {

    private List<CommentLikeList.Result.CommentList> mCommentList;
    private Context context;
    private SimpleDateFormat simpleDateFormat;
    private ButtonCallbacks callbacks;

    public CommentsAdapter(Context context, List<CommentLikeList.Result.CommentList> commentList,
                           ButtonCallbacks callbacks) {
        this.context = context;
        String format = "yyyy-MM-dd'T'HH:mm:ss";
        simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        mCommentList = commentList;
        this.callbacks = callbacks;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (mCommentList.get(position).getCommentDate() != null) {
            String toParse;
            try {
                toParse = mCommentList.get(position).getCommentDate();
                Date parsed = simpleDateFormat.parse(toParse);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(parsed);

                String date = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + " " + calendar.get(Calendar.DAY_OF_MONTH) + "," + calendar.get(Calendar.YEAR);
                holder.mCommentDate.setText(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (mCommentList.get(position).getEmpName() != null) {
            holder.mCommentBy.setText(mCommentList.get(position).getEmpName().equalsIgnoreCase("") ? "NA" : mCommentList.get(position).getEmpName());
        }

        if (mCommentList.get(position).getComment() != null) {
            holder.mCommentText.setText(mCommentList.get(position).getComment().equalsIgnoreCase("") ? "NA" : Html.fromHtml(mCommentList.get(position).getComment()));
        }

        String imageUrl = mCommentList.get(position).getEmpImage().trim();
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

    public void remove(int position) {
        mCommentList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    @Override
    public void onViewRecycled(MyViewHolder holder) {
        super.onViewRecycled(holder);
        holder.cleanUp();
    }

    public interface ButtonCallbacks {
        void removePosition(int position);

        void editPosition(int position);
    }

    public class MyViewHolder extends BaseSwipeOpenViewHolder implements OnCancelPicassoCall {
        //        public LinearLayout contentView;
        public RelativeLayout contentView;
        public TextView deleteButton;
        public TextView editButton;
        private TextView mCommentBy, mCommentText, mCommentDate;
        private RoundedImageView mAvatar;

        public MyViewHolder(View view) {
            super(view);
            mCommentBy = (TextView) view.findViewById(R.id.tv_commentBy);
            mCommentText = (TextView) view.findViewById(R.id.tv_commentText);
            mCommentDate = (TextView) view.findViewById(R.id.tv_commentDate);
            mAvatar = (RoundedImageView) view.findViewById(R.id.icn_avatar);
            contentView = (RelativeLayout) view.findViewById(R.id.content_view);
            deleteButton = (TextView) view.findViewById(R.id.delete_button);
            editButton = (TextView) view.findViewById(R.id.edit_button);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callbacks.removePosition(getAdapterPosition());
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callbacks.editPosition(getAdapterPosition());
                }
            });
        }

        @NonNull
        @Override
        public View getSwipeView() {
            return contentView;
        }

        @Override
        public float getEndHiddenViewSize() {
            return editButton.getMeasuredWidth();
        }

        @Override
        public float getStartHiddenViewSize() {
            return deleteButton.getMeasuredWidth();
        }

        @Override
        public void notifyStartOpen() {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.red));
        }

        @Override
        public void notifyEndOpen() {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.blue));
        }

        @Override
        public void cleanUp() {
            Picasso.with(mAvatar.getContext())
                    .cancelRequest(mAvatar);
            mAvatar.setImageDrawable(null);
        }
    }
}