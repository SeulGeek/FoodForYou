package com.app.foodforyou.view;

import android.view.View;

public interface OnDietClickListener {
    void onDietItemClick(RecommendDietListAdapter.ViewHolder holder, View view, int position);
}
