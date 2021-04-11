package com.app.foodforyou.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.foodforyou.R;
import com.app.foodforyou.viewModel.NetworkConnectionStateMonitor;
import com.squareup.picasso.Picasso;

public class FoodRecipeDetailsActivity extends AppCompatActivity {

    public static final String NULL_VALUE = " - ";

    private Context mContext;
    private NetworkConnectionStateMonitor mNetworkConnectionStateMonitor;

    private String mFoodImage;
    private String mMainCategoryName;
    private String mFoodName;
    private String mFoodMaterial;
    private String mRecipeOrder;
    private String mCalorieInfo;
    private String mCarbohydratesInfo;
    private String mProteinInfo;
    private String mLipidInfo;

    private ImageView mFoodImageView;
    private TextView mFoodNameTextView;
    private TextView mFoodMaterialTextView;
    private TextView mRecipeOrderTextView;
    private TextView mCalorieInfoTextView;
    private TextView mCarbohydratesInfoTextView;
    private TextView mProteinInfoTextView;
    private TextView mLipidInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_recipe_details);

        mContext = this;

        if (mNetworkConnectionStateMonitor == null) {
            mNetworkConnectionStateMonitor = new NetworkConnectionStateMonitor(mContext);
            mNetworkConnectionStateMonitor.register();
        }

        if (mNetworkConnectionStateMonitor != null) {
            init();

            getApiResponse();

            setDataOnView();
        }

    }

    private void init() {
        mFoodImageView = findViewById(R.id.food_image_view);
        mFoodNameTextView = findViewById(R.id.food_name_text_view);
        mFoodMaterialTextView = findViewById(R.id.food_material_text_view);
        mRecipeOrderTextView = findViewById(R.id.recipe_order_text_view);
        mCalorieInfoTextView = findViewById(R.id.calorie_info_text_view);
        mCarbohydratesInfoTextView = findViewById(R.id.carbohydrates_info_text_view);
        mProteinInfoTextView = findViewById(R.id.protein_info_text_view);
        mLipidInfoTextView = findViewById(R.id.lipid_info_text_view);
    }

    private void getApiResponse() {
        if (getIntent() == null) {
            return;
        }

        mMainCategoryName = getIntent().getStringExtra(FoodListActivity.MAIN_CATEGORY_NAME_KEY);
        mFoodImage = getIntent().getStringExtra(FoodListActivity.FOOD_IMAGE_KEY);
        mFoodName = getIntent().getStringExtra(FoodListActivity.FOOD_NAME_KEY);
        mFoodMaterial = getIntent().getStringExtra(FoodListActivity.MATERIAL_INFO_KEY);
        mRecipeOrder = getIntent().getStringExtra(FoodListActivity.RECIPE_ORDER_KEY);
        mCalorieInfo = getIntent().getStringExtra(FoodListActivity.CALORIE_INFO_KEY);
        mCarbohydratesInfo = getIntent().getStringExtra(FoodListActivity.CARBOHYDRATES_INFO_KEY);
        mProteinInfo = getIntent().getStringExtra(FoodListActivity.PROTEIN_INFO_KEY);
        mLipidInfo = getIntent().getStringExtra(FoodListActivity.LIPID_INFO_KEY);
    }

    private void setDataOnView() {
        if (!TextUtils.isEmpty(mFoodImage)) {
            Picasso.get().load(mFoodImage).into(mFoodImageView);
            mFoodImageView.setPadding(0,0,0,0);
        } else {
            mFoodImageView.setImageResource(R.drawable.img_none);
            mFoodImageView.setPadding(0,40,0,0);
        }
        mFoodNameTextView.setText(mFoodName);
        mFoodMaterialTextView.setText(mFoodMaterial);
        mRecipeOrderTextView.setText(mRecipeOrder);

        if (!TextUtils.isEmpty(mCalorieInfo)) {
            mCalorieInfoTextView.setText(getString(R.string.kcal, mCalorieInfo));
        } else {
            mCalorieInfoTextView.setText(getString(R.string.kcal, NULL_VALUE));
        }

        if (!TextUtils.isEmpty(mCarbohydratesInfo)) {
            mCarbohydratesInfoTextView.setText(getString(R.string.gram, mCarbohydratesInfo));
        } else {
            mCarbohydratesInfoTextView.setText(getString(R.string.gram, NULL_VALUE));
        }

        if (!TextUtils.isEmpty(mProteinInfo)) {
            mProteinInfoTextView.setText(getString(R.string.gram, mProteinInfo));
        } else {
            mProteinInfoTextView.setText(getString(R.string.gram, NULL_VALUE));
        }

        if (!TextUtils.isEmpty(mLipidInfo)) {
            mLipidInfoTextView.setText(getString(R.string.gram, mLipidInfo));
        } else {
            mLipidInfoTextView.setText(getString(R.string.gram, NULL_VALUE));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNetworkConnectionStateMonitor.unRegister();
    }
}
