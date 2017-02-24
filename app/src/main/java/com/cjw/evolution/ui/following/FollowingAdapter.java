package com.cjw.evolution.ui.following;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cjw.evolution.R;
import com.cjw.evolution.data.model.Follows;

import java.util.List;

/**
 * Created by CJW on 2016/10/23.
 */

public class FollowingAdapter extends BaseQuickAdapter<Follows,BaseViewHolder> {

    public FollowingAdapter(List<Follows> data) {
        super(R.layout.item_following_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Follows follows) {
        Glide.with(mContext)
                .load(follows.getFollowee().getAvatar_url())
                .placeholder(R.drawable.head_default)
                .override(200, 200)
                .dontAnimate()
                .into((ImageView) baseViewHolder.getView(R.id.user_avatar));
        baseViewHolder.setText(R.id.user_name, follows.getFollowee().getUsername())
                .setText(R.id.user_location, follows.getFollowee().getLocation())
                .setText(R.id.user_bio, follows.getFollowee().getBio());
    }
}
