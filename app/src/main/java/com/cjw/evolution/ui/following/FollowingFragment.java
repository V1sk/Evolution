package com.cjw.evolution.ui.following;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cjw.evolution.R;
import com.cjw.evolution.data.model.Following;
import com.cjw.evolution.data.source.FollowingRepository;
import com.cjw.evolution.ui.base.BaseFragment;
import com.cjw.evolution.ui.common.LineDividerDecoration;
import com.cjw.evolution.ui.profile.ProfileActivity;
import com.cjw.evolution.ui.shots.ShotsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FollowingFragment extends BaseFragment implements FollowingContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.following_list_recycler_view)
    RecyclerView followingListRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.empty_view)
    TextView emptyView;

    private FollowingContract.Presenter presenter;
    private FollowingAdapter followingAdapter;
    private List<Following> userList = new ArrayList<>();
    private View notLoadingView;

    public FollowingFragment() {
        // Required empty public constructor
    }


    public static FollowingFragment newInstance() {
        FollowingFragment fragment = new FollowingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        followingListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        followingListRecyclerView.addItemDecoration(new LineDividerDecoration(getActivity(), LineDividerDecoration.VERTICAL_LIST));
        followingAdapter = new FollowingAdapter(userList);
        followingAdapter.openLoadAnimation();
        followingAdapter.openLoadMore(ShotsPresenter.PAGE_SIZE);
        followingListRecyclerView.setAdapter(followingAdapter);
        followingAdapter.setOnLoadMoreListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        followingListRecyclerView.setVisibility(View.GONE);
        followingListRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra(ProfileActivity.EXTRA_FOLLOWING, (Following) baseQuickAdapter.getItem(i));
                startActivity(intent);
            }
        });
        new FollowingPresenter(FollowingRepository.getInstance(), this).subscribe();
        presenter.refresh();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_following, menu);

    }

    @Override
    public void showFollowing(List<Following> followingList, boolean refresh) {
        if (refresh)
            userList.clear();
        followingAdapter.addData(followingList);
        followingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetFollowingError(Throwable throwable) {
        Snackbar.make(getView(), R.string.can_not_load_data, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showOrHideEmptyView() {
        boolean isEmpty = followingAdapter.getItemCount() == 0;
        emptyView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        followingListRecyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showLoadingIndicator() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideLoadingIndicator() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadMoreFailed() {
        followingAdapter.showLoadMoreFailedView();

    }

    @Override
    public void noMoreFollowing() {
        followingAdapter.loadComplete();
        if (notLoadingView == null)
            notLoadingView = LayoutInflater.from(getActivity()).inflate(R.layout.not_loading, (ViewGroup) followingListRecyclerView.getParent(), false);
        followingAdapter.addFooterView(notLoadingView);
    }

    @Override
    public void setPresenter(FollowingContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onRefresh() {
        presenter.refresh();
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.loadMore();
    }
}
