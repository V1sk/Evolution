package com.cjw.evolution.ui.shotsdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cjw.evolution.R;
import com.cjw.evolution.data.ExtrasKey;
import com.cjw.evolution.data.model.Comment;
import com.cjw.evolution.data.model.Shots;
import com.cjw.evolution.data.source.ShotsDetailRepository;
import com.cjw.evolution.ui.base.BaseActivity;
import com.cjw.evolution.utils.AnimUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShotsDetailActivity extends BaseActivity implements ShotsDetailContract.View {

    final long ANIMATION_DURATION = 500;

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

    private ShotsDetailContract.Presenter presenter;
    private Shots shots;
    private List<Comment> commentList = new ArrayList<>();
    private ShotsDetailAdapter detailAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shots_detail);
        ButterKnife.bind(this);
        fab.hide();
        getWindow().getSharedElementReturnTransition().addListener(shotReturnHomeListener);
        supportActionBar(toolbar);
        getExtras();
        collapsingToolbar.setTitle(shots.getTitle());
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        final int[] imageSize = shots.bestSize();
        Glide.with(this)
                .load(shots.getBestImage())
                .placeholder(R.color.textColorSecondary)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .override(imageSize[0], imageSize[1])
                .into(shotsImage);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        detailAdapter = new ShotsDetailAdapter(shots, commentList);
        recyclerView.setAdapter(detailAdapter);
        new ShotsDetailPresenter(ShotsDetailRepository.getInstance(), this).subscribe();
        presenter.getCommentList(shots.getId());
    }

    @Override
    protected void onDestroy() {
        presenter.unsubscribe();
        super.onDestroy();
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

    private void animateRecyclers() {
        recyclerView.setAlpha(0f);
        ViewPropertyAnimatorCompat appNameAnimator = ViewCompat.animate(recyclerView)
                .alpha(1)
                .setDuration(ANIMATION_DURATION);
        ViewPropertyAnimatorCompatSet animatorSet = new ViewPropertyAnimatorCompatSet();
        animatorSet.play(appNameAnimator);
    }

    private Transition.TransitionListener shotReturnHomeListener = new AnimUtils
            .TransitionListenerAdapter() {
        @Override
        public void onTransitionStart(Transition transition) {
            super.onTransitionStart(transition);
            recyclerView.setVisibility(View.GONE);
        }

        @Override
        public void onTransitionEnd(Transition transition) {
            recyclerView.setVisibility(View.VISIBLE);
            animateRecyclers();
            fab.show();
        }
    };

    @Override
    public void onGetCommentSuccess(List<Comment> commentList) {
        this.commentList.addAll(commentList);
        detailAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetCommentError(String err) {

    }

    @Override
    public void onLikeShots() {

    }

    @Override
    public void onUnLikeShots() {

    }

    @Override
    public void showOrHideEmptyView() {

    }

    @Override
    public void onLoadingStatusChange(int status) {

    }

    @Override
    public void setPresenter(ShotsDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
