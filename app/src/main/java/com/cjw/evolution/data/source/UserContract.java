package com.cjw.evolution.data.source;

import com.cjw.evolution.data.model.User;

import rx.Observable;

/**
 * Created by CJW on 2016/10/1.
 */

public interface UserContract {

    interface Local {

        Observable<User> user();

        User _user();

        boolean save(User user);

        int deleteAll();
    }

    interface Remote {

        Observable<User> user();
    }

    User restoreUser();

    boolean save(User user);

    Observable<User> user(boolean forceUpdate);

}
