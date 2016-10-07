package com.cjw.evolution.ui.shots;

import com.cjw.evolution.data.model.Shots;
import com.cjw.evolution.data.source.ShotsRepository;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by CJW on 2016/10/2.
 */

public class ShotsPresenter implements ShotsContract.Presenter {

    private CompositeSubscription compositeSubscription;

    private int page = 1;
    private int pageSize = 10;

    private ShotsRepository shotsRepository;
    private ShotsContract.View view;

    private boolean hasMoreData = true;
    private boolean loadingMore = false;

    public ShotsPresenter(ShotsRepository shotsRepository, ShotsContract.View view) {
        this.shotsRepository = shotsRepository;
        this.view = view;
        compositeSubscription = new CompositeSubscription();
        view.setPresenter(this);
    }

    @Override
    public void refresh(String sort) {
        page = 1;
        hasMoreData = true;
        getShots(sort, true);
    }

    @Override
    public void loadMore(String sort) {
        if (hasMoreData && !loadingMore)
            getShots(sort, false);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (compositeSubscription != null) {
            compositeSubscription.clear();
        }
        view = null;
    }

    private void getShots(String sort, final boolean refresh) {
        if (refresh) {
            view.showLoadingIndicator();
        } else {
            loadingMore = true;
        }
        Subscription subscription = shotsRepository.getShots(sort, page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Shots>>() {
                    @Override
                    public void onCompleted() {
                        loadingMore = false;
                        view.hideLoadingIndicator();
                        view.showOrHideEmptyView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        loadingMore = false;
                        view.hideLoadingIndicator();
                        view.onGetShotsError(e);
                    }

                    @Override
                    public void onNext(List<Shots> shotsList) {
                        view.showShots(shotsList, refresh);
                        if (shotsList.size() == pageSize) {
                            page++;
                        } else {
                            hasMoreData = false;
                        }
                    }
                });
        compositeSubscription.add(subscription);

    }
}
