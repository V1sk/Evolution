package com.cjw.evolution.ui.shots;

import android.content.Context;

import com.cjw.evolution.data.model.Shots;
import com.cjw.evolution.ui.common.adapter.ListAdapter;

import java.util.List;

/**
 * Created by CJW on 2016/10/2.
 */

public class ShotsAdapter extends ListAdapter<Shots, ShotsItemView> {

    public ShotsAdapter(List<Shots> mData, Context mContext) {
        super(mData, mContext);
    }

    @Override
    protected ShotsItemView createView(Context context) {
        return new ShotsItemView(context);
    }
}
