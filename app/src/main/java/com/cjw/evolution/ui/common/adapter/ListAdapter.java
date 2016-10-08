package com.cjw.evolution.ui.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cjw.evolution.ui.common.recyclerview.LoadMoreStatus;
import com.cjw.evolution.ui.common.widget.LoadMoreView;

import java.util.List;

/**
 * Created by CJW on 2016/10/2.
 */

public abstract class ListAdapter<T, V extends IAdapterView> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_DATA = 1; // data
    private static final int TYPE_LOAD_MORE = 2;// load more

    private List<T> mData;
    private Context mContext;

    private OnItemClickListener onItemClickListener;
    private View.OnClickListener onReloadMoreListener;
    private int itemClickPosition = RecyclerView.NO_POSITION;

    private boolean showLoadMore;
    private int loadMoreStatus = LoadMoreStatus.LOAD_MORE_STATUS_NORMAL;

    public ListAdapter(List<T> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    protected abstract V createView(Context context);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case TYPE_DATA:
                itemView = (View) createView(mContext);
                break;
            case TYPE_LOAD_MORE:
                itemView = new LoadMoreView(mContext);
                break;
        }
        final RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(itemView) {
        };
        if (onItemClickListener != null && viewType == TYPE_DATA) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickPosition = position;
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        IAdapterView itemView = (IAdapterView) holder.itemView;
        switch (getItemViewType(position)) {
            case TYPE_DATA:
                itemView.bind(getItem(position), position);
                break;
            case TYPE_LOAD_MORE:
                ((LoadMoreView)itemView).setReloadListener(onReloadMoreListener);
                itemView.bind(loadMoreStatus, position);
                break;
        }

    }

    @Override
    public int getItemCount() {
        if (showLoadMore)
            return mData == null ? 1 : mData.size() + 1;

        return mData == null ? 0 : mData.size();

    }

    @Override
    public int getItemViewType(int position) {
        if (showLoadMore) {
            if (position < mData.size()) {
                return TYPE_DATA;
            }
            return TYPE_LOAD_MORE;
        }
        return TYPE_DATA;
    }

    public T getItem(int position) {
        if (showLoadMore) {
            if (position < mData.size()) {
                return mData.get(position);
            }
            return null;
        }

        return mData.get(position);
    }

    public List<T> getmData() {
        return mData;
    }

    public void setmData(List<T> mData) {
        this.mData = mData;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public int getItemClickPosition() {
        return itemClickPosition;
    }

    public void setItemClickPosition(int itemClickPosition) {
        this.itemClickPosition = itemClickPosition;
    }

    public boolean isShowLoadMore() {
        return showLoadMore;
    }

    public void setShowLoadMore(boolean showLoadMore) {
        this.showLoadMore = showLoadMore;
    }

    public int getLoadMoreStatus() {
        return loadMoreStatus;
    }

    public void setLoadMoreStatus(int loadMoreStatus) {
        this.loadMoreStatus = loadMoreStatus;
        notifyDataSetChanged();
    }

    public void setOnReloadMoreListener(View.OnClickListener onReloadMoreListener) {
        this.onReloadMoreListener = onReloadMoreListener;
    }
}
