package com.example.foodforyou.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodforyou.R;
import com.squareup.picasso.Picasso;

public class FoodRecipeDetails extends AppCompatActivity {
    private String foodImage;
    private String foodName;
    private String foodMaterial;
    private String recipeOrder;
    private String calorieInfo;
    private String carbohydratesInfo;
    private String proteinInfo;
    private String lipidInfo;

    private ImageView foodImageView;
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

        init();

        getApiResponse();

        setDataOnView();
    }

    private void init() {
        foodImageView = findViewById(R.id.food_image_view);
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
        Picasso.get().load(foodImage).into(foodImageView);
        foodNameTextView.setText(foodName);
        foodMaterialTextView.setText(foodMaterial);
        recipeOrderTextView.setText(recipeOrder);

        calorieInfoTextView.setText(getString(R.string.kcal, calorieInfo));
        carbohydratesInfoTextView.setText(getString(R.string.gram, carbohydratesInfo));
        proteinInfoTextView.setText(getString(R.string.gram, proteinInfo));
        lipidInfoTextView.setText(getString(R.string.gram, lipidInfo));
    }
}
