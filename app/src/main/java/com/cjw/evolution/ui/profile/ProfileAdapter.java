package com.cjw.evolution.ui.profile;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cjw.evolution.data.model.Shots;

import java.util.List;

/**
 * Created by chenjianwei on 2016/12/18.
 */

public class ProfileAdapter extends BaseQuickAdapter<Shots> {

    public ProfileAdapter(int layoutResId, List<Shots> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Shots shots) {

    }
}
