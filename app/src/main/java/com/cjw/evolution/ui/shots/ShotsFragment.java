package com.cjw.evolution.ui.shots;

import android.app.Activity;
import android.app.ActivityOptions;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cjw.evolution.R;
import com.cjw.evolution.data.ExtrasKey;
import com.cjw.evolution.data.model.Shots;
import com.cjw.evolution.data.source.ShotsRepository;
import com.cjw.evolution.ui.base.BaseFragment;
import com.cjw.evolution.ui.common.ShotsDecoration;
import com.cjw.evolution.ui.shotsdetail.ShotsDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShotsFragment extends BaseFragment implements ShotsContract.View, SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.shots_list_recycler_view)
    RecyclerView shotsListRecyclerView;
    @BindView(R.id.empty_view)
    TextView emptyView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private ShotsContract.Presenter presenter;
    private List<Shots> mData = new ArrayList<>();

    private ShotsQuickAdapter quickAdapter;

    public ShotsFragment() {
        // Required empty public constructor
    }


    public static ShotsFragment newInstance() {
        ShotsFragment fragment = new ShotsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        presenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shots, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shotsListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        quickAdapter = new ShotsQuickAdapter(mData);
        quickAdapter.openLoadAnimation();
        shotsListRecyclerView.setAdapter(quickAdapter);
        quickAdapter.setOnLoadMoreListener(this);
        shotsListRecyclerView.addItemDecoration(new ShotsDecoration());
        shotsListRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                View itemImage = view.findViewById(R.id.item_image);
                itemImage.setTransitionName(getContext().getResources().getString(R.string.transition_shot));
                Intent intent = new Intent(getContext(), ShotsDetailActivity.class);
                intent.putExtra(ExtrasKey.EXTRAS_SHOTS_DETAIL, mData.get(position));
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), itemImage, itemImage.getTransitionName());
                getContext().startActivity(intent, options.toBundle());
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
        shotsListRecyclerView.setVisibility(View.GONE);
        new ShotsPresenter(ShotsRepository.getInstance(), this).subscribe();
        presenter.refresh(ShotsFilterType.DEFAULT_SORT);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_shots, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showShots(List<Shots> shotsList, boolean refresh) {
        if (refresh)
            mData.clear();
        mData.addAll(shotsList);
        quickAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetShotsError(Throwable throwable) {
        Snackbar.make(getView(), R.string.can_not_load_data, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showOrHideEmptyView() {
        quickAdapter.loadMoreComplete();
        boolean isEmpty = quickAdapter.getItemCount() == 0;
        emptyView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        shotsListRecyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
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
        quickAdapter.loadMoreFail();
    }

    @Override
    public void setPresenter(ShotsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onRefresh() {
        presenter.refresh(ShotsFilterType.DEFAULT_SORT);
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.loadMore(ShotsFilterType.DEFAULT_SORT);
    }
}
