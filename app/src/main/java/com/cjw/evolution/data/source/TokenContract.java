package com.cjw.evolution.data.source;

import com.cjw.evolution.data.model.Token;

import rx.Observable;

/**
 * Created by CJW on 2016/10/1.
 */

public interface TokenContract {

    interface Local {

        Token token();

        boolean save(Token token);

        boolean deleteAll();
    }

    interface Remote {

        Observable<Token> accessToken(String authorizeCode);

    }

    // Local

    boolean storeToken(Token token);

    Token restoreToken();

    // Remote

    Observable<Token> accessToken(String authorizeCode);

}
