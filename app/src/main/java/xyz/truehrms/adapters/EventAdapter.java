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
import xyz.truehrms.bean.Occasions;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Occasions.Result> occasionListType;
    private Context context;

    public EventAdapter(Context context, List<Occasions.Result> occasionListType) {
        this.occasionListType = occasionListType;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_happening_two, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (occasionListType.get(position).getEmpName() != null && occasionListType.get(position).getEmpName().length() > 0) {
            holder.hapng_usr_nm.setText(occasionListType.get(position).getEmpName());

        }
        if (occasionListType.get(position).getEmpDesignation() != null) {
            holder.hapng_usr_designatn.setText(occasionListType.get(position).getEmpDesignation());
        }
        String imageUrl = occasionListType.get(position).getEmpImage().trim();
        if (imageUrl.length() > 0) {
            try {
                Picasso.with(context).load(imageUrl).placeholder(ContextCompat.getDrawable(context, R.drawable.icon_profile)).
                        error(ContextCompat.getDrawable(context, R.drawable.icon_profile)).into(holder.hapng_usr_pic);
            } catch (Exception e) {
                holder.hapng_usr_pic.setImageResource(R.drawable.icon_profile);
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return occasionListType.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.cleanUp();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnCancelPicassoCall {
        private TextView hapng_usr_nm;
        private TextView hapng_usr_designatn;
        private RoundedImageView hapng_usr_pic;

        public ViewHolder(View itemView) {
            super(itemView);
            hapng_usr_pic = (RoundedImageView) itemView.findViewById(R.id.hapng_usr_pic);
            hapng_usr_nm = (TextView) itemView.findViewById(R.id.hapng_usr_nm);
            hapng_usr_designatn = (TextView) itemView.findViewById(R.id.hapng_usr_designatn);
        }

        @Override
        public void cleanUp() {
            Picasso.with(hapng_usr_pic.getContext())
                    .cancelRequest(hapng_usr_pic);
            hapng_usr_pic.setImageDrawable(null);
        }
    }
}
