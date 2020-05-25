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

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.ViewHolder>
        implements OnMainCategoryClickListener {
    private ArrayList<MainCategory> listData = new ArrayList<>();
    private OnMainCategoryClickListener listener;

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public MainCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_category_item, parent, false);

        return new ViewHolder(view, this);
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

    public MainCategory getItem (int position) {
        return listData.get(position);
    }

    // listener
    // 어댑터 클래스 밖에서 이벤트를 처리하는 것이 일반적
    public void setOnItemClickListener(OnMainCategoryClickListener listener) {
        this.listener = listener;
    }

    // 뷰홀더 클래스 안에서 뷰가 클릭되었을 때 호출되는 메서드
    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    // ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView mainCategoryName;

        ViewHolder(View itemView, final OnMainCategoryClickListener listener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView_main_category);
            mainCategoryName = itemView.findViewById(R.id.tv_main_recipe_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        void onBind(MainCategory data) {
            mainCategoryName.setText(data.getDietSeName());
        }
    }

}
