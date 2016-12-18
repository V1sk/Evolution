package com.cjw.evolution.ui.profile;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cjw.evolution.R;
import com.cjw.evolution.data.model.Shots;

import java.util.List;

/**
 * Created by chenjianwei on 2016/12/18.
 */

public class ProfileAdapter extends BaseQuickAdapter<Shots> {

    public ProfileAdapter(int layoutResId, List<Shots> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Shots shots) {
        final int[] imageSize = shots.bestSize();
        Glide.with(mContext)
                .load(shots.getBestImage())
                .placeholder(R.color.textColorSecondary)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .override(imageSize[0], imageSize[1])
                .into((ImageView) baseViewHolder.getView(R.id.profile_item_image));
    }
}
