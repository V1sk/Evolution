package com.cjw.evolution.ui.common.widget;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.cjw.evolution.R;
import com.cjw.evolution.ui.common.adapter.IAdapterView;

import butterknife.ButterKnife;

/**
 * Created by CJW on 2016/10/6.
 */

public class LoadMoreView extends RelativeLayout implements IAdapterView {

    public LoadMoreView(Context context) {
        super(context);
        View.inflate(context, R.layout.item_load_more_layout, this);
        ButterKnife.bind(this);
    }

    @Override
    public void bind(Object item, int position) {

    }
}
