package com.cjw.evolution.ui.shotsdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cjw.evolution.R;
import com.cjw.evolution.data.ExtrasKey;
import com.cjw.evolution.data.model.Comment;
import com.cjw.evolution.data.model.Shots;
import com.cjw.evolution.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShotsDetailActivity extends BaseActivity {

    @BindView(R.id.shots_image)
    ImageView shotsImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Shots shots;
    private List<Comment> commentList = new ArrayList<>();
    private ShotsDetailAdapter detailAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shots_detail);
        ButterKnife.bind(this);
        supportActionBar(toolbar);
        getExtras();
        collapsingToolbar.setTitle(shots.getTitle());
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        final int[] imageSize = shots.bestSize();
        Glide.with(this)
                .load(shots.getBestImage())
                .placeholder(R.color.text_summary_color)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .override(imageSize[0], imageSize[1])
                .into(shotsImage);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        detailAdapter = new ShotsDetailAdapter(shots, commentList);
        recyclerView.setAdapter(detailAdapter);
    }

    @OnClick(R.id.fab)
    public void onClick() {
    }

    private void getExtras() {
        Intent intent = getIntent();
        if (intent.hasExtra(ExtrasKey.EXTRAS_SHOTS_DETAIL)) {
            shots = intent.getParcelableExtra(ExtrasKey.EXTRAS_SHOTS_DETAIL);
        } else {
            finish();
        }
    }

}
