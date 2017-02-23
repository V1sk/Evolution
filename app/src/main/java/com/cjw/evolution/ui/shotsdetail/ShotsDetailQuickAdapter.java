package com.cjw.evolution.ui.shotsdetail;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cjw.evolution.R;
import com.cjw.evolution.data.model.Comment;
import com.cjw.evolution.ui.profile.ProfileActivity;
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
    protected void convert(BaseViewHolder baseViewHolder, final Comment comment) {
        Glide.with(mContext)
                .load(comment.getUser().getAvatar_url())
                .placeholder(R.drawable.head_default)
                .override(100, 100)
                .dontAnimate()
                .into((ImageView) baseViewHolder.getView(R.id.avatar));
        baseViewHolder.setText(R.id.user_name,comment.getUser().getName())
                .setText(R.id.comment_content,Html.fromHtml(comment.getBody()).toString().trim())
                .setText(R.id.comment_time,TimeUtils.formatShotsTime(comment.getCreated_at()));
        ImageView avatar = baseViewHolder.getView(R.id.avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra(ProfileActivity.EXTRA_FOLLOWING, comment.getUser());
                mContext.startActivity(intent);
            }
        });
//        userName.setText(comment.getUser().getName());
//        commentContent.setText();
//        commentTime.setText(TimeUtils.formatShotsTime(comment.getCreated_at()));
    }
}
