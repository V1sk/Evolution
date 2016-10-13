package com.cjw.evolution.ui.shotsdetail;

import com.cjw.evolution.data.model.Comment;
import com.cjw.evolution.data.source.ShotsDetailRepository;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by CJW on 2016/10/13.
 */

public class ShotsDetailPresenter implements ShotsDetailContract.Presenter {

    private CompositeSubscription compositeSubscription;
    private ShotsDetailRepository shotsDetailRepository;
    private ShotsDetailContract.View view;

    private int page = 1;
    private int pageSize = 30;
    private boolean hasMoreData = true;
    private boolean loadingMore = false;

    public ShotsDetailPresenter(ShotsDetailRepository shotsDetailRepository, ShotsDetailContract.View view) {
        this.shotsDetailRepository = shotsDetailRepository;
        this.view = view;
        view.setPresenter(this);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void getCommentList(long shotsId) {
        Subscription subscription = shotsDetailRepository.getComments(shotsId, page, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Comment>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onGetCommentError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                        view.onGetCommentSuccess(comments);
                    }
                });
        compositeSubscription.add(subscription);
    }

    @Override
    public void checkIfLike(long shotsId) {

    }

    @Override
    public void like(long shotsId) {

    }

    @Override
    public void unLike(long shotsId) {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeSubscription.clear();
        view = null;
    }
}
