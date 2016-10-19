package com.cjw.evolution.ui.shotsdetail;

import com.cjw.evolution.data.model.Comment;
import com.cjw.evolution.data.model.LikeResponse;
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

@SuppressWarnings("unchecked")
public class ShotsDetailPresenter implements ShotsDetailContract.Presenter {

    private CompositeSubscription compositeSubscription;
    private ShotsDetailRepository shotsDetailRepository;
    private ShotsDetailContract.View view;

    private int page = 1;
    public static final int PAGE_SIZE = 20;

    public ShotsDetailPresenter(ShotsDetailRepository shotsDetailRepository, ShotsDetailContract.View view) {
        this.shotsDetailRepository = shotsDetailRepository;
        this.view = view;
        view.setPresenter(this);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void getCommentList(long shotsId) {
        Subscription subscription = shotsDetailRepository.getComments(shotsId, page, PAGE_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Comment>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (page == 1) {
                            view.onGetCommentError(e.getMessage());
                        } else {
                            view.onLoadMoreCommentFailed();
                        }
                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                        view.onGetCommentSuccess(comments);
                        if (comments.size() == PAGE_SIZE) {
                            page++;
                        } else {
                            view.noMoreComments();
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }

    @Override
    public void checkIfLike(long shotsId) {
        Subscription subscription = shotsDetailRepository.checkIfShotsLike(shotsId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<LikeResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LikeResponse likeResponse) {

                    }
                });
        compositeSubscription.add(subscription);
    }

    @Override
    public void like(long shotsId) {
        Subscription subscription = shotsDetailRepository.like(shotsId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<LikeResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LikeResponse likeResponse) {

                    }
                });
        compositeSubscription.add(subscription);

    }

    @Override
    public void unLike(long shotsId) {
        Subscription subscription = shotsDetailRepository.unLike(shotsId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {

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
        view = null;
    }
}
