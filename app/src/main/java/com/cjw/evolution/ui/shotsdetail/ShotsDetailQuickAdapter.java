package com.cjw.evolution.ui.shotsdetail;

import android.text.Html;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cjw.evolution.R;
import com.cjw.evolution.data.model.Comment;
import com.cjw.evolution.utils.TimeUtils;

import java.util.List;

/**
 * Created by CJW on 2016/10/18.
 */

public class ShotsDetailQuickAdapter extends BaseQuickAdapter<Comment> {

    public ShotsDetailQuickAdapter(List<Comment> data) {
        super(R.layout.layout_item_comment, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Comment comment) {
        Glide.with(mContext)
                .load(comment.getUser().getAvatar_url())
                .placeholder(R.drawable.head_default)
                .override(100, 100)
                .dontAnimate()
                .into((ImageView) baseViewHolder.getView(R.id.avatar));
        baseViewHolder.setText(R.id.user_name,comment.getUser().getName())
                .setText(R.id.comment_content,Html.fromHtml(comment.getBody()).toString().trim())
                .setText(R.id.comment_time,TimeUtils.formatShotsTime(comment.getCreated_at()));
//        userName.setText(comment.getUser().getName());
//        commentContent.setText();
//        commentTime.setText(TimeUtils.formatShotsTime(comment.getCreated_at()));
    }
}
