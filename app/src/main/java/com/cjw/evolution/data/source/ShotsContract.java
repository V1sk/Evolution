package com.cjw.evolution.data.source;


import com.cjw.evolution.data.model.Shots;

import java.util.List;

import rx.Observable;

/**
 * Created by CJW on 2016/10/2.
 */

public interface ShotsContract {

    Observable<List<Shots>> getShots(String sort, int page, int pageSize);

}
