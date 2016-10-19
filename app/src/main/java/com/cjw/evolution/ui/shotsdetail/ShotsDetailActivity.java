package com.cjw.evolution.ui.shotsdetail;

import android.content.Context;
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
import android.text.Html;
import android.text.TextUtils;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
import de.hdodenhof.circleimageview.CircleImageView;

public class ShotsDetailActivity extends BaseActivity implements ShotsDetailContract.View, BaseQuickAdapter.RequestLoadMoreListener {

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

    private View notLoadingView;
    private ProgressBar loadingView;
    private ImageView retryView;
    View footerView;

    private ShotsDetailContract.Presenter presenter;
    private Shots shots;
    private List<Comment> commentList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;

    private ShotsDetailQuickAdapter shotsDetailQuickAdapter;

    private boolean isLike = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shots_detail);
        ButterKnife.bind(this);
        fab.hide();
        getWindow().getSharedElementReturnTransition().addListener(shotReturnHomeListener);
        supportActionBar(toolbar);
        getExtras();

        shotsDetailQuickAdapter = new ShotsDetailQuickAdapter(commentList);
        openLoadMore();

        initHeaderView();
        initFooterView();
        initAppBar();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(shotsDetailQuickAdapter);
        new ShotsDetailPresenter(ShotsDetailRepository.getInstance(), this).subscribe();
        presenter.getCommentList(shots.getId());
        presenter.checkIfLike(shots.getId());
    }

    private void initAppBar() {
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
    }

    private void initFooterView() {
        footerView = getLayoutInflater().inflate(R.layout.item_load_more_layout, (ViewGroup) recyclerView.getParent(), false);
        loadingView = (ProgressBar) footerView.findViewById(R.id.progress_bar);
        retryView = (ImageView) footerView.findViewById(R.id.btn_reload);
        shotsDetailQuickAdapter.addFooterView(footerView);
    }

    private void initHeaderView() {
        View headView = LayoutInflater.from(this).inflate(R.layout.shots_detail_layout, null);
        HeaderViewHolder headerViewHolder = new HeaderViewHolder(headView);
        shotsDetailQuickAdapter.addHeaderView(headView);
        headerViewHolder.setData(shots);
    }

    private void openLoadMore() {
        shotsDetailQuickAdapter.openLoadAnimation();
        shotsDetailQuickAdapter.openLoadMore(ShotsDetailPresenter.PAGE_SIZE);
        shotsDetailQuickAdapter.setOnLoadMoreListener(this);
    }

    @Override
    protected void onDestroy() {
        presenter.unsubscribe();
        super.onDestroy();
    }

    @OnClick(R.id.fab)
    public void onClick() {
        if (!isLike) {
            presenter.like(shots.getId());
        } else {
            presenter.unLike(shots.getId());
        }
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
        shotsDetailQuickAdapter.removeFooterView(footerView);
        shotsDetailQuickAdapter.addData(commentList);
        openLoadMore();
    }

    @Override
    public void onGetCommentError(String err) {
        loadingView.setVisibility(View.INVISIBLE);
        retryView.setVisibility(View.VISIBLE);
        retryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingView.setVisibility(View.VISIBLE);
                retryView.setVisibility(View.INVISIBLE);
                onLoadMoreRequested();
            }
        });
    }

    @Override
    public void onLikeShots() {
        isLike = true;
        fab.setImageResource(R.drawable.ic_favorite_white_24dp);
    }

    @Override
    public void onUnLikeShots() {
        isLike = false;
        fab.setImageResource(R.drawable.ic_favorite_outline_white_24dp);
    }

    @Override
    public void showOrHideEmptyView() {

    }

    @Override
    public void noMoreComments() {
        shotsDetailQuickAdapter.loadComplete();
        if (notLoadingView == null)
            notLoadingView = getLayoutInflater().inflate(R.layout.not_loading, (ViewGroup) recyclerView.getParent(), false);
        shotsDetailQuickAdapter.addFooterView(notLoadingView);
    }

    @Override
    public void onLoadMoreCommentFailed() {
        shotsDetailQuickAdapter.removeFooterView(footerView);
    }

    @Override
    public void setPresenter(ShotsDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.getCommentList(shots.getId());
    }

    static class HeaderViewHolder {
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.item_summary)
        TextView itemSummary;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.avatar)
        CircleImageView avatar;
        @BindView(R.id.user_layout)
        LinearLayout userLayout;
        @BindView(R.id.ic_like)
        ImageView icLike;
        @BindView(R.id.txt_like_count)
        TextView txtLikeCount;
        @BindView(R.id.layout_like)
        RelativeLayout layoutLike;
        @BindView(R.id.ic_view)
        ImageView icView;
        @BindView(R.id.txt_view_count)
        TextView txtViewCount;
        @BindView(R.id.item_views)
        RelativeLayout itemViews;
        @BindView(R.id.ic_share)
        ImageView icShare;
        @BindView(R.id.share)
        TextView share;
        @BindView(R.id.share_layout)
        RelativeLayout shareLayout;

        private Context context;

        HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
            context = view.getContext();
        }

        public void setData(Shots shots) {
            itemTitle.setText(shots.getTitle());
            String description = !TextUtils.isEmpty(shots.getDescription()) ? shots.getDescription() : "";
            itemSummary.setText(Html.fromHtml(description).toString().trim());
            userName.setText(shots.getUser().getName());
            Glide.with(context)
                    .load(shots.getUser().getAvatar_url())
                    .placeholder(R.drawable.head_default)
                    .override(100, 100)
                    .crossFade()
                    .into(avatar);
            txtLikeCount.setText(String.format(context.getString(R.string.like_count), String.valueOf(shots.getLikes_count())));
            txtViewCount.setText(String.format(context.getString(R.string.view_count), String.valueOf(shots.getViews_count())));
        }
    }
}
