package com.cjw.evolution.data.source;

import com.cjw.evolution.data.model.Following;

import java.util.List;

import rx.Observable;

/**
 * Created by CJW on 2016/10/23.
 */

public interface FollowingContract {

    Observable<List<Following>> getFollowing(int page, int pageSize);

}
