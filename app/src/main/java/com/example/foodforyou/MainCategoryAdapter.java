package com.example.foodforyou;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodforyou.model.MainCategory;

import java.util.ArrayList;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.ViewHolder> {
    private ArrayList<MainCategory> listData = new ArrayList<>();

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public MainCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_category_item, parent, false);

        return new ViewHolder(view);
    }

    // onBindViewHolder - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull MainCategoryAdapter.ViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(MainCategory data) {
        listData.add(data);
    }

    // ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView mainCategoryName;

        ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView_main_category);
            mainCategoryName = itemView.findViewById(R.id.tv_main_recipe_category);
        }

        void onBind(MainCategory data) {
            mainCategoryName.setText(data.getDietSeName());
        }
    }

}
