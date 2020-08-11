package com.example.foodforyou.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodforyou.R;
import com.example.foodforyou.model.RecommendDietListResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecommendDietListAdapter extends RecyclerView.Adapter<RecommendDietListAdapter.ViewHolder>
        implements OnDietClickListener{

    private ArrayList<RecommendDietListResponse> response = new ArrayList<>();
    private String mainCategoryName;
    private OnDietClickListener listener;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_diet_list_item, parent, false);
        return new ViewHolder(view, listener) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(response.get(position), mainCategoryName);
    }

    @Override
    public int getItemCount() {
        return response.size();
    }

    public void addItem(RecommendDietListResponse data, String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
        response.add(data);
    }

    public void setOnItemClickListener(OnDietClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDietItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onDietItemClick(holder, view, position);
        }
    }


    //ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView mainCategoryNameTextView;
        TextView dietNameTextView;
        RelativeLayout button;

        ViewHolder(@NonNull View itemView, final OnDietClickListener listener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView_recommend_diet);
            mainCategoryNameTextView = itemView.findViewById(R.id.main_category_name_text_view);
            dietNameTextView = itemView.findViewById(R.id.tv_recommend_diet_category);
            button = itemView.findViewById(R.id.add_diet_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onDietItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        void onBind(RecommendDietListResponse data, String mainCategoryName) {
            Picasso.get().load(data.getRtnImageDc()).into(imageView);
            mainCategoryNameTextView.setText(mainCategoryName);
            dietNameTextView.setText(data.getDietNm());
        }
    }

}
