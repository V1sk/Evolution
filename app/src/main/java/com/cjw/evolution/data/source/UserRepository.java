package com.cjw.evolution.data.source;

import android.os.Handler;

import com.cjw.evolution.data.Injection;
import com.cjw.evolution.data.model.User;
import com.cjw.evolution.data.source.local.LocalUserDataSource;
import com.cjw.evolution.data.source.remote.RemoteUserDataSource;

import rx.Observable;

/**
 * Created by CJW on 2016/10/1.
 */

public class UserRepository implements UserContract {

    private UserContract.Local mLocalDataSource;
    private UserContract.Remote mRemoteDataSource;
    private Handler handler;

    private UserRepository() {
        handler = new Handler();
        this.mLocalDataSource = new LocalUserDataSource();
        this.mRemoteDataSource = new RemoteUserDataSource(Injection.provideDribbbleApi());
    }

    private static class SingletonHolder {
        private static final UserRepository INSTANCE = new UserRepository();
    }

    public static UserRepository getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public User restoreUser() {
        return mLocalDataSource._user();
    }

    @Override
    public boolean save(User user) {
        mLocalDataSource.save(user);
        return true;
    }

    @Override
    public Observable<User> user(boolean forceUpdate) {
        Observable<User> remote = mRemoteDataSource.user();
        if (forceUpdate) {
            return remote;
        }
        Observable<User> local = mLocalDataSource.user();
        return Observable.concat(local.first(), remote);
    }

}
