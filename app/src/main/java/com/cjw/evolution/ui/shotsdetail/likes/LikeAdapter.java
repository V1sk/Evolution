package com.cjw.evolution.ui.shotsdetail.likes;

import android.text.Html;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cjw.evolution.R;
import com.cjw.evolution.data.model.LikeUser;
import com.cjw.evolution.utils.TimeUtils;

import java.util.List;

/**
 * Created by chenjianwei on 2017/2/23.
 */

public class LikeAdapter extends BaseQuickAdapter<LikeUser> {

    public LikeAdapter(List<LikeUser> data) {
        super(R.layout.layout_item_like_user, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, LikeUser likeUser) {
        Glide.with(mContext)
                .load(likeUser.getUser().getAvatar_url())
                .placeholder(R.drawable.head_default)
                .override(100, 100)
                .dontAnimate()
                .into((ImageView) baseViewHolder.getView(R.id.avatar));
        baseViewHolder.setText(R.id.user_name,likeUser.getUser().getName())
                .setText(R.id.content, Html.fromHtml(likeUser.getUser().getBio()))
                .setText(R.id.time, TimeUtils.formatShotsTime(likeUser.getCreated_at()));
    }
}
