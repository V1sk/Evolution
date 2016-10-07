package com.cjw.evolution.ui.designers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjw.evolution.R;
import com.cjw.evolution.ui.base.BaseFragment;


public class DesignersFragment extends BaseFragment {


    public DesignersFragment() {
        // Required empty public constructor
    }


    public static DesignersFragment newInstance() {
        DesignersFragment fragment = new DesignersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_designers, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_designers,menu);
    }
}
