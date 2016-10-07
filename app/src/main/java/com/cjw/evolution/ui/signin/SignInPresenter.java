package com.cjw.evolution.ui.signin;

import android.util.Log;

import com.cjw.evolution.account.UserSession;
import com.cjw.evolution.data.model.User;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 8/13/16
 * Time: 9:55 PM
 * Desc: SignInPresenter
 */
public class SignInPresenter implements SignInContract.Presenter {

    private static final String TAG = "SignInPresenter";

    private SignInContract.View mView;
    private CompositeSubscription mSubscriptions;

    public SignInPresenter(SignInContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void signIn(final String authorizeCode) {
        Subscription subscription = UserSession.getInstance().signIn(authorizeCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onStart() {
                        mView.onSignInStarted();
                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, "signIn#onNext: " + user);
                    }

                    @Override
                    public void onCompleted() {

                        mView.onSignInCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onSignInError(e);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void subscribe() {
        // Empty
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
        mView = null;
    }
}
