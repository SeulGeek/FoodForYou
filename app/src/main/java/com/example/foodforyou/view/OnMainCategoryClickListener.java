package com.example.foodforyou.view;

import android.view.View;

import com.example.foodforyou.view.MainCategoryAdapter;

public interface OnMainCategoryClickListener {
    public void onItemClick(MainCategoryAdapter.ViewHolder holder, View view, int position);
}
