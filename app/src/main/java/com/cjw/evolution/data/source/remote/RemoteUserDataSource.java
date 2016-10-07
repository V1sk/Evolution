package com.cjw.evolution.data.source.remote;

import com.cjw.evolution.data.model.User;
import com.cjw.evolution.data.source.UserContract;
import com.cjw.evolution.data.source.remote.api.DribbbleApiService;

import rx.Observable;

/**
 * Created by CJW on 2016/10/1.
 */

public class RemoteUserDataSource implements UserContract.Remote {

    private DribbbleApiService dribbbleApiService;

    public RemoteUserDataSource(DribbbleApiService dribbbleApiService) {
        this.dribbbleApiService = dribbbleApiService;
    }

    @Override
    public Observable<User> user() {
        return dribbbleApiService.getUser();
    }

}
