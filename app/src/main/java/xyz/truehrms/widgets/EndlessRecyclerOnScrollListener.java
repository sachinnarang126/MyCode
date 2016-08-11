package xyz.truehrms.widgets;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 0; // The minimum amount of items to have below your current scroll position before loading more.
    private int current_page = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    public abstract void scroolabove(int current_page);


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) {
            // Scroll Down
            //       System.out.println("down");
            scroolabove(2);
        } else if (dy < 0) {
            // Scroll Up
            // System.out.println("upppppppppppp");
            scroolabove(1);


        }
// Here get the child count, item count and visibleitems
        // from layout manager
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        // Now check if userScrolled is true and also check if
        // the item is end then update recycler view and set
        // userScrolled to false

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                //  System.out.println("loading false");
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached
            current_page++;
            onLoadMore(current_page);
            loading = true;
        }
    }

    public abstract void onLoadMore(int current_page);
}