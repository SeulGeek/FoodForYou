package com.example.foodforyou.view;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodforyou.R;
import com.example.foodforyou.model.RecommendDietDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecommendDietFoodListAdapter extends RecyclerView.Adapter<RecommendDietFoodListAdapter.ViewHolder> {

    private ArrayList<RecommendDietDetail> response = new ArrayList<>();

    public interface OnFoodItemClickListener {
        void onFoodItemClick(View view, int position);
    }

    private OnFoodItemClickListener mListener = null;

    public void setOnFoodItemClickListener(OnFoodItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecommendDietFoodListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_diet_food_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendDietFoodListAdapter.ViewHolder holder, int position) {
        holder.onBind(response.get(position));
    }

    @Override
    public int getItemCount() {
        return response.size();
    }

    public void addItem(RecommendDietDetail data) {
        response.add(data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout foodListLayout;
        ImageView foodImageView;
        TextView foodName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foodListLayout = itemView.findViewById(R.id.food_list_layout);
            foodImageView = itemView.findViewById(R.id.food_image_view);
            foodName = itemView.findViewById(R.id.food_name_text_view);

            foodListLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mListener != null) {
                        mListener.onFoodItemClick(v, position);
                    }
                }
            });
        }

        void onBind(RecommendDietDetail data) {
            if (!TextUtils.isEmpty(data.getRtnImageDc())) {
                Picasso.get().load(data.getRtnImageDc()).into(foodImageView);
            } else {
                foodImageView.setImageResource(R.drawable.img_none); //TODO: change svg image
            }

            foodName.setText(data.getFdNm());
        }
    }
}
