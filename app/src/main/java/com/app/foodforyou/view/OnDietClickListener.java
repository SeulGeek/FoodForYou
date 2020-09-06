package com.app.foodforyou.view;

import android.view.View;

public interface OnDietClickListener {
    void onDietItemClick(DietListAdapter.ViewHolder holder, View view, int position);
}
