package com.cjw.evolution.ui.profile.follows;

import android.text.Html;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cjw.evolution.R;
import com.cjw.evolution.data.model.Follows;
import com.cjw.evolution.data.model.User;

import java.util.List;

/**
 * Created by chenjianwei on 2017/2/23.
 */

public class FollowsAdapter extends BaseQuickAdapter<Follows, BaseViewHolder> {

    private int type;

    public FollowsAdapter(List<Follows> data, int type) {
        super(R.layout.layout_item_like_user, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Follows follows) {
        User user = type == FollowsBottomSheet.TYPE_FOLLOWERS ? follows.getFollower() : follows.getFollowee();
        Glide.with(mContext)
                .load(user.getAvatar_url())
                .placeholder(R.drawable.head_default)
                .override(100, 100)
                .dontAnimate()
                .into((ImageView) baseViewHolder.getView(R.id.avatar));
        baseViewHolder.setText(R.id.user_name, user.getName())
                .setText(R.id.content, Html.fromHtml(user.getBio()))
                .setVisible(R.id.time, false);
    }
}
