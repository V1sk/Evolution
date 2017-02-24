package com.cjw.evolution.data.source;

import com.cjw.evolution.data.model.Follows;

import java.util.List;

import rx.Observable;

/**
 * Created by CJW on 2016/10/23.
 */

public interface FollowingContract {

    Observable<List<Follows>> getFollowing(int page, int pageSize);

    Observable<List<Follows>> getUserFollowing(long userId, int page, int pageSize);

    Observable<List<Follows>> getUserFollowers(long userId, int page, int pageSize);

    Observable<Void> following(long userId);

    Observable<Void> follow(long userId);

    Observable<Void> unfollow(long userId);

}
