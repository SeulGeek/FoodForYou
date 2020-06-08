package com.example.foodforyou.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodforyou.R;
import com.example.foodforyou.model.RecommendDietListResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecommendDietListAdapter extends RecyclerView.Adapter<RecommendDietListAdapter.ViewHolder> {
    private ArrayList<RecommendDietListResponse> response = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_diet_list_item, parent, false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(response.get(position));
    }

    @Override
    public int getItemCount() {
        return response.size();
    }

    public void addItem(RecommendDietListResponse data) {
        response.add(data);
    }

    //ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView dietName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView_recommend_diet);
            dietName = itemView.findViewById(R.id.tv_recommend_diet_category);
        }

        void onBind(RecommendDietListResponse data) {
            Picasso.get().load(data.getRtnImageDc()).into(imageView);
            dietName.setText(data.getDietNm());
        }
    }

}
