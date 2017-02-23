package com.cjw.evolution.ui.shotsdetail.likes;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cjw.evolution.R;
import com.cjw.evolution.data.ExtrasKey;
import com.cjw.evolution.data.model.LikeUser;
import com.cjw.evolution.data.source.ShotsDetailRepository;
import com.cjw.evolution.ui.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LikeBottomSheetDialogFragment extends BottomSheetDialogFragment implements LikesContract.View,BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.like_count)
    TextView likeCount;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private View notLoadingView;
    private BottomSheetBehavior bottomSheetBehavior;

    private List<LikeUser> likeUsers = new ArrayList<>();
    private LikeAdapter likeAdapter;
    private LikesContract.Presenter presenter;

    public LikeBottomSheetDialogFragment() {
        // Required empty public constructor
    }


    public static LikeBottomSheetDialogFragment newInstance(long shotsId,int likeCount) {
        LikeBottomSheetDialogFragment fragment = new LikeBottomSheetDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ExtrasKey.EXTRAS_SHOTS_ID,shotsId);
        bundle.putInt(ExtrasKey.EXTRAS_LIKE_COUNT,likeCount);
        fragment.setArguments(bundle);
        return fragment;
    }


    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_like_bottom_sheet_dialog, null);
        dialog.setContentView(contentView);
        ButterKnife.bind(this, contentView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        likeCount.setText(String.valueOf(getArguments().getInt(ExtrasKey.EXTRAS_LIKE_COUNT))+" Likes");

        likeAdapter = new LikeAdapter(likeUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(likeAdapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                LikeUser likeUser = (LikeUser) baseQuickAdapter.getItem(i);
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra(ProfileActivity.EXTRA_FOLLOWING, likeUser.getUser());
                startActivity(intent);
            }
        });
        likeAdapter.openLoadMore(LikePresenter.PAGE_SIZE);
        likeAdapter.openLoadAnimation();
        likeAdapter.setOnLoadMoreListener(this);

        new LikePresenter(ShotsDetailRepository.getInstance(),this).subscribe();
        presenter.listLikes(getArguments().getLong(ExtrasKey.EXTRAS_SHOTS_ID));
    }


    @Override
    public void onListLikes(List<LikeUser> likeUserList) {
        likeAdapter.addData(likeUserList);
        if(likeAdapter.getItemCount()>0){
            loadingProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void noMoreData() {
        likeAdapter.loadComplete();
        if (notLoadingView == null)
            notLoadingView = LayoutInflater.from(getActivity()).inflate(R.layout.not_loading, (ViewGroup) recyclerView.getParent(), false);
        likeAdapter.addFooterView(notLoadingView);
    }

    @Override
    public void setPresenter(LikesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.listLikes(getArguments().getLong(ExtrasKey.EXTRAS_SHOTS_ID));
    }
}
