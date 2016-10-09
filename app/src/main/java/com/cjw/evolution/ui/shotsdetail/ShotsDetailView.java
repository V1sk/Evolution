package com.cjw.evolution.ui.shotsdetail;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjw.evolution.R;
import com.cjw.evolution.data.model.Shots;
import com.cjw.evolution.ui.common.adapter.IAdapterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by CJW on 2016/10/9.
 */

public class ShotsDetailView extends RelativeLayout implements IAdapterView<Shots> {

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
    @BindView(R.id.txt_view_count)
    TextView txtViewCount;
    @BindView(R.id.share)
    TextView share;

    public ShotsDetailView(Context context) {
        super(context);
        View.inflate(context, R.layout.shots_detail_layout, this);
        ButterKnife.bind(this);
    }

    @Override
    public void bind(Shots item, int position) {
        itemTitle.setText(item.getTitle());
        String description = !TextUtils.isEmpty(item.getDescription()) ? item.getDescription() : "";
        itemSummary.setText(Html.fromHtml(description).toString().trim());
        userName.setText(item.getUser().getName());
        Glide.with(getContext())
                .load(item.getUser().getAvatar_url())
                .placeholder(R.drawable.head_default)
                .override(100, 100)
                .dontAnimate()
                .into(avatar);
        txtLikeCount.setText(String.format(getContext().getString(R.string.like_count), String.valueOf(item.getLikes_count())));
        txtViewCount.setText(String.format(getContext().getString(R.string.view_count), String.valueOf(item.getViews_count())));
    }
}
