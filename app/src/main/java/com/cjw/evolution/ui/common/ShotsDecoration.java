package com.cjw.evolution.ui.common;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjw.evolution.utils.ViewUtil;

/**
 * Created by CJW on 2016/10/2.
 */

public class ShotsDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public ShotsDecoration() {
        space = ViewUtil.dpToPx(8);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemPosition =
                ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        int totalItemCount = parent.getAdapter().getItemCount();
        if (itemPosition == 0) {
            outRect.right = space;
            outRect.top = space;
            outRect.bottom = space;
            outRect.left = space;
        } else {
            outRect.right = space;
            outRect.top = 0;
            outRect.bottom = space;
            outRect.left = space;
        }
    }
}
