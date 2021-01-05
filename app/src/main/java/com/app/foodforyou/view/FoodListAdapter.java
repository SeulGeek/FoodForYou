package com.app.foodforyou.view;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.foodforyou.R;
import com.app.foodforyou.model.FoodRecipeDetail;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {

    private ArrayList<FoodRecipeDetail> mResponse = new ArrayList<>();
    private String mMainCategoryName;

    public interface OnFoodItemClickListener {
        void onFoodItemClick(View view, int position);
    }

    private OnFoodItemClickListener mListener = null;

    public void setOnFoodItemClickListener(OnFoodItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public FoodListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_diet_food_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.ViewHolder holder, int position) {
        holder.onBindFoodListInformation(mResponse.get(position));
    }

    @Override
    public int getItemCount() {
        return mResponse.size();
    }

    public void addItem(FoodRecipeDetail data, String mainCategoryName) {
        this.mMainCategoryName = mainCategoryName;
        mResponse.add(data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout foodListLayout;
        ImageView foodImageView;
        TextView foodNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foodListLayout = itemView.findViewById(R.id.food_list_layout);
            foodImageView = itemView.findViewById(R.id.food_image_view);
            foodNameTextView = itemView.findViewById(R.id.food_name_text_view);

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

        void onBindFoodListInformation(FoodRecipeDetail data) {
            if (!TextUtils.isEmpty(data.getRtnImageDc())) {
                Picasso.get()
                        .load(data.getRtnImageDc())
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .into(foodImageView);
            } else {
                foodImageView.setImageResource(R.drawable.img_none);
            }
            foodNameTextView.setText(data.getFdNm());
        }
    }
}
