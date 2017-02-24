package com.cjw.evolution.ui.profile.follows;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.cjw.evolution.data.model.Follows;
import com.cjw.evolution.data.model.User;
import com.cjw.evolution.data.source.FollowingRepository;
import com.cjw.evolution.ui.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenjianwei on 2017/2/24.
 */

public class FollowsBottomSheet extends BottomSheetDialogFragment implements FollowsContact.View, BaseQuickAdapter.RequestLoadMoreListener {

    public static final int TYPE_FOLLOWING = 1;
    public static final int TYPE_FOLLOWERS = 2;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;

    private View notLoadingView;

    private FollowsAdapter followsAdapter;
    private List<Follows> followsList = new ArrayList<>();
    private FollowsContact.Presenter presenter;

    private User user;

    public FollowsBottomSheet() {

    }

    public static FollowsBottomSheet newInstance(User user, int type) {
        FollowsBottomSheet followsBottomSheet = new FollowsBottomSheet();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ExtrasKey.EXTRAS_USER, user);
        bundle.putInt(ExtrasKey.EXTRAS_FOLLOWS_TYPE, type);
        followsBottomSheet.setArguments(bundle);
        return followsBottomSheet;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getArguments().getParcelable(ExtrasKey.EXTRAS_USER);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        initView(dialog);
        initRecyclerView();
        fetchData();

    }

    private void fetchData() {
        new FollowsPresenter(this, FollowingRepository.getInstance()).subscribe();
        switch (getArguments().getInt(ExtrasKey.EXTRAS_FOLLOWS_TYPE)) {
            case TYPE_FOLLOWERS:
                presenter.listFollowers(user.getId());
                title.setText(user.getFollowers_count() + " Followers");
                break;
            case TYPE_FOLLOWING:
                presenter.listFollowing(user.getId());
                title.setText(user.getFollowings_count() + " Following");
                break;
        }
    }

    private void initRecyclerView() {
        followsAdapter = new FollowsAdapter(followsList, getArguments().getInt(ExtrasKey.EXTRAS_FOLLOWS_TYPE));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(followsAdapter);
        followsAdapter.openLoadAnimation();
        followsAdapter.setOnLoadMoreListener(this);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Follows follows = (Follows) adapter.getItem(position);
                User user =  getArguments().getInt(ExtrasKey.EXTRAS_FOLLOWS_TYPE) == FollowsBottomSheet.TYPE_FOLLOWERS ? follows.getFollower() : follows.getFollowee();
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra(ProfileActivity.EXTRA_FOLLOWING, user);
                startActivity(intent);
            }
        });
    }

    private void initView(Dialog dialog) {
        View contentView = View.inflate(getContext(), R.layout.layout_user_bottom_sheet_dialog, null);
        dialog.setContentView(contentView);
        ButterKnife.bind(this, contentView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }


    @Override
    public void onLoadMoreRequested() {
        switch (getArguments().getInt(ExtrasKey.EXTRAS_FOLLOWS_TYPE)) {
            case TYPE_FOLLOWERS:
                presenter.listFollowers(user.getId());
                break;
            case TYPE_FOLLOWING:
                presenter.listFollowing(user.getId());
                break;
        }
    }

    @Override
    public void onListFollows(List<Follows> followsList) {
        loadingProgress.setVisibility(View.GONE);
        this.followsList.addAll(followsList);
        followsAdapter.notifyDataSetChanged();
        followsAdapter.loadMoreComplete();
    }

    @Override
    public void loadFailed() {
        dismiss();
    }

    @Override
    public void loadMoreFailed() {
        followsAdapter.loadMoreFail();
    }

    @Override
    public void noMoreData() {
        followsAdapter.loadMoreComplete();
        followsAdapter.setEnableLoadMore(false);
        if (notLoadingView == null)
            notLoadingView = LayoutInflater.from(getActivity()).inflate(R.layout.not_loading, (ViewGroup) recyclerView.getParent(), false);
        followsAdapter.addFooterView(notLoadingView);
    }

    @Override
    public void setPresenter(FollowsContact.Presenter presenter) {
        this.presenter = presenter;
    }
}
