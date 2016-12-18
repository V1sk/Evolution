package com.cjw.evolution.data.source;

import com.cjw.evolution.data.Injection;
import com.cjw.evolution.data.model.Shots;
import com.cjw.evolution.data.source.remote.api.DribbbleApiService;

import java.util.List;

import rx.Observable;

/**
 * Created by CJW on 2016/10/2.
 */

public class ShotsRepository implements ShotsContract {

    private DribbbleApiService dribbbleApiService;

    private ShotsRepository() {
        dribbbleApiService = Injection.provideDribbbleApi();
    }

    private static class SingletonHolder {
        private static final ShotsRepository INSTANCE = new ShotsRepository();
    }

    public static ShotsRepository getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Observable<List<Shots>> getShots(String sort, int page, int pageSize) {
        return dribbbleApiService.getShots(sort, page, pageSize);
    }

    @Override
    public Observable<List<Shots>> listShotsForUser(long userId, int page, int pageSize) {
        return dribbbleApiService.listShotsForUser(userId, page, pageSize);
    }
}
