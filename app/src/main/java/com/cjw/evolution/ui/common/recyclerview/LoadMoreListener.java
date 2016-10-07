package com.cjw.evolution.ui.common.recyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by CJW on 2016/10/7.
 */

public class LoadMoreListener extends RecyclerView.OnScrollListener {

    private int lastVisibleItemPosition;
    private int currentScrollState = 0;

    private LoadMoreCallback loadMoreCallback;

    public LoadMoreListener(LoadMoreCallback loadMoreCallback) {
        this.loadMoreCallback = loadMoreCallback;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        currentScrollState = newState;
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int visibleItemCount = linearLayoutManager.getChildCount();
        int totalItemCount = linearLayoutManager.getItemCount();
        if (visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE &&
                lastVisibleItemPosition >= totalItemCount - 1) {
            if (loadMoreCallback != null) {
                loadMoreCallback.onLoadMore();
            }
        }
    }
}
