package com.cjw.evolution.ui.profile;

import android.util.Log;

import com.cjw.evolution.data.model.Shots;
import com.cjw.evolution.data.source.FollowingRepository;
import com.cjw.evolution.data.source.ShotsRepository;
import com.google.gson.Gson;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chenjianwei on 2016/12/17.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    private static final String TAG = "ProfilePresenter";

    private ProfileContract.View mView;
    private FollowingRepository followingRepository;
    private ShotsRepository shotsRepository;

    private CompositeSubscription compositeSubscription;

    public ProfilePresenter(ProfileContract.View mView) {
        this.mView = mView;
        followingRepository = FollowingRepository.getInstance();
        shotsRepository = ShotsRepository.getInstance();
        compositeSubscription = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void following(long userId) {
        Subscription subscription = followingRepository.following(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onFollowingResult(false);
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        mView.onFollowingResult(true);
                    }
                });
        compositeSubscription.add(subscription);
    }

    @Override
    public void follow(long userId) {
        Subscription subscription = followingRepository.follow(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void aVoid) {
                        mView.onFollowingResult(true);
                    }
                });
        compositeSubscription.add(subscription);
    }

    @Override
    public void unfollow(long userId) {
        Subscription subscription = followingRepository.unfollow(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void aVoid) {
                        mView.onFollowingResult(false);
                    }
                });
        compositeSubscription.add(subscription);
    }

    @Override
    public void listShotsForUser(long userId, int page, int pageSize) {
        Subscription subscription = shotsRepository.listShotsForUser(userId, page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Shots>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Shots> shotsList) {
                        Log.d(TAG, new Gson().toJson(shotsList));
                        mView.onListUsersShots(shotsList);
                    }
                });
        compositeSubscription.add(subscription);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeSubscription.clear();
        mView = null;
    }
}
