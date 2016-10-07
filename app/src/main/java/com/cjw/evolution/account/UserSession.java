package com.cjw.evolution.account;

import com.cjw.evolution.EvoApplication;
import com.cjw.evolution.RxBus;
import com.cjw.evolution.data.model.Token;
import com.cjw.evolution.data.model.User;
import com.cjw.evolution.data.source.TokenRepository;
import com.cjw.evolution.data.source.UserRepository;
import com.cjw.evolution.event.SignInEvent;
import com.cjw.evolution.event.SignOutEvent;
import com.cjw.evolution.event.UserUpdatedEvent;
import com.cjw.evolution.utils.DbUtils;
import com.cjw.evolution.utils.NetworkUtils;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by CJW on 2016/10/1.
 */

public class UserSession {

    private Token mToken;
    private User mUser;

    private TokenRepository mTokenRepository;
    private UserRepository mUserRepository;

    private UserSession() {
        mTokenRepository = TokenRepository.getInstance();
        mUserRepository = UserRepository.getInstance();
        restoreSession();
    }

    private static class SingletonHolder {
        private static final UserSession INSTANCE = new UserSession();
    }

    public static UserSession getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Observable<User> signIn(final String authorizeCode) {
        return mTokenRepository.accessToken(authorizeCode)
                .doOnNext(new Action1<Token>() {
                    @Override
                    public void call(Token token) {
                        mToken = token;
                    }
                })
                .flatMap(new Func1<Token, Observable<User>>() {
                    @Override
                    public Observable<User> call(Token token) {
                        return mUserRepository.user(true);
                    }
                }).doOnNext(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        mUser = user;
                        storeSession();
                        RxBus.getInstance().post(new SignInEvent());
                    }
                });
    }

    public void signOut() {
        // Broadcast sign out event
        RxBus.getInstance().post(new SignOutEvent());
        // Clear Database
        DbUtils.clearDataBase();

        mToken = null;
        mUser = null;
    }

    public Observable<User> user(boolean forceUpdate) {
        return mUserRepository.user(forceUpdate && NetworkUtils.isNetworkAvailable(EvoApplication.getInstance()))
                .doOnNext(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        mUser = user;
                        storeSession();
                        RxBus.getInstance().post(new UserUpdatedEvent(mUser));
                    }
                });
    }

    private void restoreSession() {
        mToken = mTokenRepository.restoreToken();
        if (isSignedIn()) {
            mUser = mUserRepository.restoreUser();
        }

    }

    private void storeSession() {
        mTokenRepository.storeToken(mToken);
        mUserRepository.save(mUser);
        // mUserRepository.storeUser(); // Already handled by UserRepository

    }

    public boolean isSignedIn() {
        return mToken != null && mToken.getAccess_token() != null;
    }

    public Token getmToken() {
        return mToken;
    }

    public void setmToken(Token mToken) {
        this.mToken = mToken;
    }

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }
}
