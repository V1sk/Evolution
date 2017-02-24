package com.cjw.evolution.ui.shots;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cjw.evolution.R;
import com.cjw.evolution.data.model.Shots;
import com.cjw.evolution.utils.TimeUtils;

import java.util.List;

/**
 * Created by CJW on 2016/10/16.
 */

public class ShotsQuickAdapter extends BaseQuickAdapter<Shots,BaseViewHolder> {

    public ShotsQuickAdapter(List<Shots> data) {
        super(R.layout.item_shots_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Shots shots) {
        final int[] imageSize = shots.bestSize();
        Glide.with(mContext)
                .load(shots.getUser().getAvatar_url())
                .placeholder(R.drawable.head_default)
                .override(100, 100)
                .dontAnimate()
                .into((ImageView) baseViewHolder.getView(R.id.user_avatar));
        Glide.with(mContext)
                .load(shots.getBestImage())
                .placeholder(R.color.textColorSecondary)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .override(imageSize[0], imageSize[1])
                .into((ImageView) baseViewHolder.getView(R.id.item_image));
        baseViewHolder.setText(R.id.user_name, shots.getUser().getUsername())
                .setText(R.id.like_count, String.valueOf(shots.getLikes_count()))
                .setText(R.id.view_count, String.valueOf(shots.getViews_count()))
                .setText(R.id.comment_count, String.valueOf(shots.getComments_count()))
                .setText(R.id.item_time, TimeUtils.formatShotsTime(shots.getCreated_at()))
                .setVisible(R.id.icon_attachment, shots.getAttachments_count() > 0);
    }
}
