package com.hb.popupbble;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hemba on 9/22/2017.
 */

public class RecyclerViewListener extends RecyclerView.OnScrollListener {
    private PopupBubble mPopupBubble;

    public RecyclerViewListener(PopupBubble popupBubble){
        mPopupBubble=popupBubble;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy>0){
            //scrolling up
            if (mPopupBubble!=null)
                mPopupBubble.setVisibility(View.VISIBLE);
        }else{
            //scrolling down
            if (mPopupBubble!=null)
                mPopupBubble.setVisibility(View.INVISIBLE);
            if (isTopVisible(recyclerView))
                mPopupBubble.deactivate();
        }

    }
    private Boolean isTopVisible(RecyclerView recyclerView){
        LinearLayoutManager linearLayoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();
        int position=linearLayoutManager.findFirstVisibleItemPosition();
        return position==0;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        switch (newState) {

            case RecyclerView.SCROLL_STATE_IDLE:
                //System.out.println("The RecyclerView is not scrolling");
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                //System.out.println("Scrolling now");
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                //System.out.println("Scroll Settling");
                break;
        }
    }
}
