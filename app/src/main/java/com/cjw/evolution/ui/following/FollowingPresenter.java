package com.cjw.evolution.ui.following;

import com.cjw.evolution.data.model.Follows;
import com.cjw.evolution.data.source.FollowingRepository;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by CJW on 2016/10/23.
 */

public class FollowingPresenter implements FollowingContract.Presenter {

    private CompositeSubscription compositeSubscription;

    private int page = 1;
    public static final int PAGE_SIZE = 30;

    private FollowingRepository followingRepository;
    private FollowingContract.View view;

    private boolean hasMoreData = true;
    private boolean loadingMore = false;

    public FollowingPresenter(FollowingRepository followingRepository, FollowingContract.View view) {
        this.followingRepository = followingRepository;
        this.view = view;
        compositeSubscription = new CompositeSubscription();
        view.setPresenter(this);
    }

    @Override
    public void refresh() {
        hasMoreData = true;
        loadData();
    }


    @Override
    public void loadMore() {
        if (hasMoreData && !loadingMore)
            loadData();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        view = null;
        compositeSubscription.clear();
    }

    private void loadData() {
        if (page == 1) {
            view.showLoadingIndicator();
        } else {
            loadingMore = true;
        }
        Subscription subscription = followingRepository.getFollowing(page, PAGE_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Follows>>() {
                    @Override
                    public void onCompleted() {
                        view.hideLoadingIndicator();
                        loadingMore = false;
                        view.showOrHideEmptyView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoadingIndicator();
                        loadingMore = false;
                        view.showOrHideEmptyView();
                        e.printStackTrace();
                        if (page != 1) {
                            view.showLoadMoreFailed();
                        }
                        view.onGetFollowingError(e);
                    }

                    @Override
                    public void onNext(List<Follows> users) {
                        view.showFollowing(users, page == 1);
                        if (users.size() == PAGE_SIZE) {
                            page++;
                        } else {
                            hasMoreData = false;
                            view.noMoreFollowing();
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }
}
