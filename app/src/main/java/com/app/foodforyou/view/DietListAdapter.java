package com.app.foodforyou.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.foodforyou.R;
import com.app.foodforyou.model.DietListResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DietListAdapter extends RecyclerView.Adapter<DietListAdapter.ViewHolder>
        implements OnDietClickListener{

    private ArrayList<DietListResponse> mResponse = new ArrayList<>();
    private String mMainCategoryName;
    private OnDietClickListener mListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_diet_list_item, parent, false);
        return new ViewHolder(view, mListener) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mResponse.get(position), mMainCategoryName);
    }

    @Override
    public int getItemCount() {
        return mResponse.size();
    }

    public void addItem(DietListResponse data, String mainCategoryName) {
        this.mMainCategoryName = mainCategoryName;
        mResponse.add(data);
    }

    public void setOnItemClickListener(OnDietClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onDietItemClick(ViewHolder holder, View view, int position) {
        if (mListener != null) {
            mListener.onDietItemClick(holder, view, position);
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

        void onBind(DietListResponse data, String mainCategoryName) {
            Picasso.get().load(data.getRtnImageDc()).into(imageView);
            mainCategoryNameTextView.setText(mainCategoryName);
            dietNameTextView.setText(data.getDietNm());
        }
    }

}
