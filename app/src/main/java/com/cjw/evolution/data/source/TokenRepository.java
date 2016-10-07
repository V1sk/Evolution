package com.cjw.evolution.data.source;

import com.cjw.evolution.data.Injection;
import com.cjw.evolution.data.model.Token;
import com.cjw.evolution.data.source.local.LocalTokenDataSource;
import com.cjw.evolution.data.source.remote.RemoteTokenDataSource;

import rx.Observable;

/**
 * Created by CJW on 2016/10/1.
 */

public class TokenRepository implements TokenContract {

    private TokenContract.Local mLocalDataSource;
    private TokenContract.Remote mRemoteDataSource;

    private TokenRepository() {
        this.mLocalDataSource = new LocalTokenDataSource();
        this.mRemoteDataSource = new RemoteTokenDataSource(Injection.provideDribbbleAuthApi());
    }

    private static class SingletonHolder {
        private static final TokenRepository INSTANCE = new TokenRepository();
    }

    public static TokenRepository getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean storeToken(Token token) {
        mLocalDataSource.deleteAll();
        return mLocalDataSource.save(token);
    }

    @Override
    public Token restoreToken() {
        return mLocalDataSource.token();
    }

    @Override
    public Observable<Token> accessToken(String authorizeCode) {
        return mRemoteDataSource.accessToken(authorizeCode);
    }
}
