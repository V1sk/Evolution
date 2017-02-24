package com.cjw.evolution.ui.profile.follows;

import com.cjw.evolution.data.model.Follows;
import com.cjw.evolution.data.source.FollowingRepository;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chenjianwei on 2017/2/24.
 */

public class FollowsPresenter implements FollowsContact.Presenter {

    private FollowsContact.View view;
    private FollowingRepository followingRepository;
    private CompositeSubscription compositeSubscription;

    private int page = 1;
    private int pageSize = 20;

    public FollowsPresenter(FollowsContact.View view, FollowingRepository followingRepository) {
        this.view = view;
        this.followingRepository = followingRepository;
        view.setPresenter(this);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void listFollowing(long userId) {
        Subscription subscription = followingRepository.getUserFollowing(userId, page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Follows>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (page == 1) {
                            view.loadFailed();
                        }else{
                            view.loadMoreFailed();
                        }
                    }

                    @Override
                    public void onNext(List<Follows> followsList) {
                        view.onListFollows(followsList);
                        if (followsList.size() >= pageSize) {
                            page++;
                        } else {
                            view.noMoreData();
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }

    @Override
    public void listFollowers(long userId) {
        Subscription subscription = followingRepository.getUserFollowers(userId, page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Follows>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (page == 1) {
                            view.loadFailed();
                        }else{
                            view.loadMoreFailed();
                        }
                    }

                    @Override
                    public void onNext(List<Follows> followsList) {
                        view.onListFollows(followsList);
                        if (followsList.size() >= pageSize) {
                            page++;
                        } else {
                            view.noMoreData();
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
