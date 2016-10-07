package com.cjw.evolution.data.source.remote;

import com.cjw.evolution.data.model.Token;
import com.cjw.evolution.data.source.TokenContract;
import com.cjw.evolution.data.source.remote.api.DribbbleAuthService;
import com.cjw.evolution.network.ServerConfig;

import rx.Observable;

/**
 * Created by CJW on 2016/10/1.
 */

public class RemoteTokenDataSource implements TokenContract.Remote {

    private DribbbleAuthService dribbbleAuthService;

    public RemoteTokenDataSource(DribbbleAuthService dribbbleAuthService) {
        this.dribbbleAuthService = dribbbleAuthService;
    }

    @Override
    public Observable<Token> accessToken(String authorizeCode) {
        return dribbbleAuthService.accessToken(ServerConfig.DRIBBBLE_CLIENT_ID, ServerConfig.DRIBBBLE_CLIENT_SECRET, authorizeCode);
    }
}
