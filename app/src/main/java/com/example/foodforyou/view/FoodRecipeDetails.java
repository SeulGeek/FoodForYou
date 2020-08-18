package com.example.foodforyou.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodforyou.R;
import com.example.foodforyou.viewModel.NetworkConnectionStateMonitor;
import com.squareup.picasso.Picasso;

public class FoodRecipeDetails extends AppCompatActivity {

    public static final String NULL_VALUE = " - ";

    private Context mContext;
    private NetworkConnectionStateMonitor networkConnectionStateMonitor;

    private String foodImage;
    private String mainCategoryName;
    private String foodName;
    private String foodMaterial;
    private String recipeOrder;
    private String calorieInfo;
    private String carbohydratesInfo;
    private String proteinInfo;
    private String lipidInfo;

    private ImageView foodImageView;
    private TextView mainCategoryNameTextView;
    private TextView foodNameTextView;
    private TextView foodMaterialTextView;
    private TextView recipeOrderTextView;
    private TextView calorieInfoTextView;
    private TextView carbohydratesInfoTextView;
    private TextView proteinInfoTextView;
    private TextView lipidInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_recipe_details);

        mContext = this;

        if (networkConnectionStateMonitor == null) {
            networkConnectionStateMonitor = new NetworkConnectionStateMonitor(mContext);
            networkConnectionStateMonitor.register();
        }

        if (networkConnectionStateMonitor != null) {
            init();

            getApiResponse();

            setDataOnView();
        }

    }

    private void init() {
        foodImageView = findViewById(R.id.food_image_view);
        mainCategoryNameTextView = findViewById(R.id.main_category_name_text_view);
        foodNameTextView = findViewById(R.id.food_name_text_view);
        foodMaterialTextView = findViewById(R.id.food_material_text_view);
        recipeOrderTextView = findViewById(R.id.recipe_order_text_view);
        calorieInfoTextView = findViewById(R.id.calorie_info_text_view);
        carbohydratesInfoTextView = findViewById(R.id.carbohydrates_info_text_view);
        proteinInfoTextView = findViewById(R.id.protein_info_text_view);
        lipidInfoTextView = findViewById(R.id.lipid_info_text_view);
    }

    private void getApiResponse() {
        Intent intent = getIntent();
        mainCategoryName = intent.getStringExtra("mainCategoryName");
        foodImage = intent.getStringExtra("foodImage");
        foodName = intent.getStringExtra("foodName");
        foodMaterial = intent.getStringExtra("materialInfo");
        recipeOrder = intent.getStringExtra("recipeOrder");
        calorieInfo = intent.getStringExtra("calorieInfo");
        carbohydratesInfo = intent.getStringExtra("carbohydratesInfo");
        proteinInfo = intent.getStringExtra("proteinInfo");
        lipidInfo = intent.getStringExtra("lipidInfo");
    }

    private void setDataOnView() {
        if (!TextUtils.isEmpty(foodImage)) {
            Picasso.get().load(foodImage).into(foodImageView);
            foodImageView.setPadding(0,0,0,0);
        } else {
            foodImageView.setImageResource(R.drawable.img_none);
            foodImageView.setPadding(0,40,0,0);
        }
        mainCategoryNameTextView.setText(mainCategoryName);
        foodNameTextView.setText(foodName);
        foodMaterialTextView.setText(foodMaterial);
        recipeOrderTextView.setText(recipeOrder);

        if (!TextUtils.isEmpty(calorieInfo)) {
            calorieInfoTextView.setText(getString(R.string.kcal, calorieInfo));
        } else {
            calorieInfoTextView.setText(getString(R.string.kcal, NULL_VALUE));
        }

        if (!TextUtils.isEmpty(carbohydratesInfo)) {
            carbohydratesInfoTextView.setText(getString(R.string.gram, carbohydratesInfo));
        } else {
            carbohydratesInfoTextView.setText(getString(R.string.gram, NULL_VALUE));
        }

        if (!TextUtils.isEmpty(proteinInfo)) {
            proteinInfoTextView.setText(getString(R.string.gram, proteinInfo));
        } else {
            proteinInfoTextView.setText(getString(R.string.gram, NULL_VALUE));
        }

        if (!TextUtils.isEmpty(lipidInfo)) {
            lipidInfoTextView.setText(getString(R.string.gram, lipidInfo));
        } else {
            lipidInfoTextView.setText(getString(R.string.gram, NULL_VALUE));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkConnectionStateMonitor.unRegister();
    }
}
