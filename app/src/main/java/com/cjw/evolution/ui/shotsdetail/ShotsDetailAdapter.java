package com.cjw.evolution.ui.shotsdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjw.evolution.R;
import com.cjw.evolution.data.model.Comment;
import com.cjw.evolution.data.model.Shots;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by CJW on 2016/10/9.
 */

public class ShotsDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int TYPE_DETAIL = 1;
    private final static int TYPE_COMMENT = 2;
    private final static int TYPE_NO_COMMENT = 3;
    private final static int TYPE_REPLY = 4;

    private Shots shots;
    private List<Comment> commentList;

    private boolean showNoComment;

    public void setShowNoComment(boolean showNoComment) {
        this.showNoComment = showNoComment;
    }

    public ShotsDetailAdapter(Shots shots, List<Comment> commentList) {
        this.shots = shots;
        this.commentList = commentList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_DETAIL:
                view = layoutInflater.inflate(R.layout.shots_detail_layout, parent, false);
                return new DetailViewHolder(view);
            case TYPE_COMMENT:
                view = layoutInflater.inflate(R.layout.layout_item_comment, parent, false);
                return new CommentViewHolder(view);
            case TYPE_NO_COMMENT:
                view = layoutInflater.inflate(R.layout.layout_no_comment, parent, false);
                return new NoCommentViewHolder(view);
            case TYPE_REPLY:
                view = layoutInflater.inflate(R.layout.layout_reply, parent, false);
                return new ReplyViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_DETAIL:
                Log.d("getItemViewType","TYPE_DETAIL");
                ((DetailViewHolder) holder).bindShots(shots);
                break;
            case TYPE_COMMENT:
                Log.d("getItemViewType","TYPE_COMMENT");
                ((CommentViewHolder) holder).bindData(commentList.get(position - 1));
                break;
            case TYPE_NO_COMMENT:
                Log.d("getItemViewType","TYPE_NO_COMMENT");
                ((NoCommentViewHolder) holder).showNoComment(showNoComment);
                break;
            case TYPE_REPLY:
                Log.d("getItemViewType","TYPE_REPLY");
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return hasComment() ? commentList.size() + 2 : 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {

            return TYPE_DETAIL;
        }
        int footerPosition = hasComment() ? commentList.size() + 1 : 2;
        if (position == footerPosition) {
            return TYPE_REPLY;
        }
        return hasComment() ? TYPE_COMMENT : TYPE_NO_COMMENT;
    }

    private boolean hasComment() {
        return commentList != null && !commentList.isEmpty();
    }


    static class DetailViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.item_summary)
        TextView itemSummary;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.avatar)
        CircleImageView avatar;
        @BindView(R.id.txt_like_count)
        TextView txtLikeCount;
        @BindView(R.id.layout_like)
        RelativeLayout layoutLike;
        @BindView(R.id.txt_view_count)
        TextView txtViewCount;
        @BindView(R.id.item_views)
        RelativeLayout itemViews;
        @BindView(R.id.share_layout)
        RelativeLayout shareLayout;

        private Context context;

        public DetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        public void bindShots(Shots shots) {
            itemTitle.setText(shots.getTitle());
            String description = !TextUtils.isEmpty(shots.getDescription()) ? shots.getDescription() : "";
            itemSummary.setText(Html.fromHtml(description).toString().trim());
            userName.setText(shots.getUser().getName());
            Glide.with(context)
                    .load(shots.getUser().getAvatar_url())
                    .placeholder(R.drawable.head_default)
                    .override(100, 100)
                    .crossFade()
                    .into(avatar);
            txtLikeCount.setText(String.format(context.getString(R.string.like_count), String.valueOf(shots.getLikes_count())));
            txtViewCount.setText(String.format(context.getString(R.string.view_count), String.valueOf(shots.getViews_count())));
        }
    }

    static class NoCommentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_no_comment)
        TextView txtNoComment;
        @BindView(R.id.loading_comment)
        ProgressBar loadingComment;

        public NoCommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void showNoComment(boolean showNoComment) {
            if (showNoComment) {
                loadingComment.setVisibility(View.GONE);
                txtNoComment.setVisibility(View.VISIBLE);
            } else {
                loadingComment.setVisibility(View.VISIBLE);
                txtNoComment.setVisibility(View.GONE);
            }
        }

        public void hideItems(){
            loadingComment.setVisibility(View.GONE);
            txtNoComment.setVisibility(View.GONE);
        }
    }

    static class ReplyViewHolder extends RecyclerView.ViewHolder {

        public ReplyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        CircleImageView avatar;
        @BindView(R.id.comment_time)
        TextView commentTime;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.comment_content)
        TextView commentContent;

        private Context context;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        public void bindData(Comment comment) {
            Glide.with(context)
                    .load(comment.getUser().getAvatar_url())
                    .placeholder(R.drawable.head_default)
                    .override(100, 100)
                    .crossFade()
                    .into(avatar);
            userName.setText(comment.getUser().getName());
            commentContent.setText(Html.fromHtml(comment.getBody()).toString().trim());

        }
    }
}
