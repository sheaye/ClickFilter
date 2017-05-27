package com.sheaye.sample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.sheaye.ClickFilter;

/**
 * Created by yexinyan on 2017/5/26.
 */

public class BasicFragment extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ClickFilter.bind(this, view);
    }
}
