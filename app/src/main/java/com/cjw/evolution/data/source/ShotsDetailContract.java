package com.cjw.evolution.data.source;


import com.cjw.evolution.data.model.Comment;
import com.cjw.evolution.data.model.LikeResponse;
import com.cjw.evolution.data.model.LikeUser;

import java.util.List;

import rx.Observable;

/**
 * Created by CJW on 2016/10/13.
 */

public interface ShotsDetailContract {

    Observable<List<Comment>> getComments(long shotsId, int page, int pageSize);

    Observable<LikeResponse> checkIfShotsLike(long shotsId);

    Observable<LikeResponse> like(long shotsId);

    Observable unLike(long shotsId);

    Observable<List<LikeUser>> listLikes(long shotsId, int page, int pageSize);

}
