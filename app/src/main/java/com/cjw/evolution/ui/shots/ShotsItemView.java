package com.cjw.evolution.ui.shots;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cjw.evolution.R;
import com.cjw.evolution.data.model.Shots;
import com.cjw.evolution.ui.common.adapter.IAdapterView;
import com.cjw.evolution.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by CJW on 2016/10/2.
 */

public class ShotsItemView extends CardView implements IAdapterView<Shots> {

    @BindView(R.id.user_avatar)
    CircleImageView userAvatar;
    @BindView(R.id.item_time)
    TextView itemTime;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.item_image)
    ImageView itemImage;
    @BindView(R.id.icon_attachment)
    ImageView iconAttachment;
    @BindView(R.id.like_count)
    TextView likeCount;
    @BindView(R.id.comment_count)
    TextView commentCount;
    @BindView(R.id.view_count)
    TextView viewCount;

    public ShotsItemView(Context context) {
        super(context);
        View.inflate(context, R.layout.item_shots_layout, this);
        ButterKnife.bind(this);
    }

    @Override
    public void bind(Shots item, int position) {
        final int[] imageSize = item.bestSize();
        Glide.with(getContext())
                .load(item.getUser().getAvatar_url())
                .placeholder(R.drawable.head_default)
                .override(100, 100)
                .dontAnimate()
                .into(userAvatar);
        Glide.with(getContext())
                .load(item.getBestImage())
                .placeholder(R.color.text_summary_color)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .bitmapTransform(new BlurTransformation(context, 25))
                .crossFade()
                .override(imageSize[0], imageSize[1])
                .into(itemImage);
        userName.setText(item.getUser().getName());
        if (item.getAttachments_count() > 0) {
            iconAttachment.setVisibility(View.VISIBLE);
        } else {
            iconAttachment.setVisibility(View.GONE);
        }
        likeCount.setText(String.valueOf(item.getLikes_count()));
        commentCount.setText(String.valueOf(item.getComments_count()));
        viewCount.setText(String.valueOf(item.getViews_count()));
        itemTime.setText(TimeUtils.formatShotsTime(item.getCreated_at()));
    }
}
