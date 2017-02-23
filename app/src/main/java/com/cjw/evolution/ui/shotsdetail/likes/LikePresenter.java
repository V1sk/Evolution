package com.cjw.evolution.ui.shotsdetail.likes;

import com.cjw.evolution.data.model.LikeUser;
import com.cjw.evolution.data.source.ShotsDetailRepository;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chenjianwei on 2017/2/23.
 */

public class LikePresenter implements LikesContract.Presenter {

    private ShotsDetailRepository shotsDetailRepository;
    private LikesContract.View view;
    private CompositeSubscription compositeSubscription;

    private int page = 1;
    public static final int PAGE_SIZE = 20;

    public LikePresenter(ShotsDetailRepository shotsDetailRepository, LikesContract.View view) {
        this.shotsDetailRepository = shotsDetailRepository;
        this.view = view;
        view.setPresenter(this);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void listLikes(long shotsId) {
        Subscription subscription = shotsDetailRepository.listLikes(shotsId, page, PAGE_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<LikeUser>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<LikeUser> likeUser) {
                        view.onListLikes(likeUser);
                        if(likeUser.size()>=PAGE_SIZE){
                            page++;
                        }else{
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
        view = null;
        compositeSubscription.clear();
    }
}
