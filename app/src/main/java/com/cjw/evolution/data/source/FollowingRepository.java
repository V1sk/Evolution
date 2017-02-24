package com.cjw.evolution.data.source;

import com.cjw.evolution.data.Injection;
import com.cjw.evolution.data.model.Follows;
import com.cjw.evolution.data.source.remote.api.DribbbleApiService;

import java.util.List;

import rx.Observable;

/**
 * Created by CJW on 2016/10/23.
 */

public class FollowingRepository implements FollowingContract {

    private DribbbleApiService dribbbleApiService;

    private FollowingRepository() {
        dribbbleApiService = Injection.provideDribbbleApi();
    }

    private static class SingletonHolder {
        private static final FollowingRepository INSTANCE = new FollowingRepository();
    }

    public static FollowingRepository getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Observable<List<Follows>> getFollowing(int page, int pageSize) {
        return dribbbleApiService.getFollowing(page, pageSize);
    }

    @Override
    public Observable<List<Follows>> getUserFollowing(long userId, int page, int pageSize) {
        return dribbbleApiService.getUserFollowing(userId, page, pageSize);
    }

    @Override
    public Observable<List<Follows>> getUserFollowers(long userId, int page, int pageSize) {
        return dribbbleApiService.getUserFollowers(userId, page, pageSize);
    }

    @Override
    public Observable<Void> following(long userId) {
        return dribbbleApiService.following(userId);
    }

    @Override
    public Observable<Void> follow(long userId) {
        return dribbbleApiService.follow(userId);
    }

    @Override
    public Observable<Void> unfollow(long userId) {
        return dribbbleApiService.unfollow(userId);
    }
}
