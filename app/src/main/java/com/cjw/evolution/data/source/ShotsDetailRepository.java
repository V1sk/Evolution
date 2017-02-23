package com.cjw.evolution.data.source;


import com.cjw.evolution.data.Injection;
import com.cjw.evolution.data.model.Comment;
import com.cjw.evolution.data.model.LikeResponse;
import com.cjw.evolution.data.model.LikeUser;
import com.cjw.evolution.data.source.remote.api.DribbbleApiService;

import java.util.List;

import rx.Observable;

/**
 * Created by CJW on 2016/10/13.
 */

public class ShotsDetailRepository implements ShotsDetailContract {

    private DribbbleApiService dribbbleApiService;

    private ShotsDetailRepository() {
        dribbbleApiService = Injection.provideDribbbleApi();
    }

    private static class SingletonHolder {
        private static final ShotsDetailRepository INSTANCE = new ShotsDetailRepository();
    }

    public static ShotsDetailRepository getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Observable<List<Comment>> getComments(long shotsId, int page, int pageSize) {
        return dribbbleApiService.getComments(shotsId, page, pageSize);
    }

    @Override
    public Observable<LikeResponse> checkIfShotsLike(long shotsId) {
        return dribbbleApiService.checkIfLike(shotsId);
    }

    @Override
    public Observable<LikeResponse> like(long shotsId) {
        return dribbbleApiService.addToLike(shotsId);
    }

    @Override
    public Observable<Object> unLike(long shotsId) {
        return dribbbleApiService.unLike(shotsId);
    }

    @Override
    public Observable<List<LikeUser>> listLikes(long shotsId, int page, int pageSize) {
        return dribbbleApiService.listLikes(shotsId, page, pageSize);
    }
}
