package com.app.foodforyou.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.foodforyou.R;
import com.app.foodforyou.model.FoodRecipeDetail;
import com.app.foodforyou.viewModel.NetworkConnectionStateMonitor;
import com.app.foodforyou.viewModel.PreferenceManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class FoodListActivity extends AppCompatActivity {

    private Context mContext;
    private NetworkConnectionStateMonitor networkConnectionStateMonitor;

    /**
    *  RESPONSE ELEMENT
    *  - API response variable name's explanation is insufficient.
    */
    public static final String FOOD_NAME = "fdNm";
    public static final String IMAGE_DESCRIPTION = "rtnImageDc";
    public static final String MATERIAL_INFORMATION = "matrlInfo";
    public static final String COOKING_METHOD_INFORMATION = "ckngMthInfo";
    public static final String CALORIE_INFORMATION = "clriInfo";
    public static final String CARBOHYDRATES_INFORMATION = "crbhInfo";
    public static final String PROTEIN_INFORMATION = "protInfo";
    public static final String LIPID_INFORMATION = "ntrfsInfo"; // It seems to mean liquid in nutrition facts as follow API's description

    // SEND RESPONSE VALUE'S KEY
    public static final String MAIN_CATEGORY_NAME_KEY = "mainCategoryName";
    public static final String FOOD_NAME_KEY = "foodName";
    public static final String FOOD_IMAGE_KEY = "foodImage";
    public static final String MATERIAL_INFO_KEY = "materialInfo";
    public static final String RECIPE_ORDER_KEY = "recipeOrder";
    public static final String CALORIE_INFO_KEY = "calorieInfo";
    public static final String CARBOHYDRATES_INFO_KEY = "carbohydratesInfo";
    public static final String PROTEIN_INFO_KEY = "proteinInfo";
    public static final String LIPID_INFO_KEY = "lipidInfo";

    private FoodListAdapter adapter;
    private String mainCategoryName;
    private String cntntsNo;
    private ArrayList<String> foodName;
    private ArrayList<String> foodImage;
    private ArrayList<String> materialInfo;
    private ArrayList<String> recipeOrder;
    private ArrayList<String> calorieInfo;
    private ArrayList<String> carbohydratesInfo;
    private ArrayList<String> proteinInfo;
    private ArrayList<String> lipidInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_diet_food_list);

        mContext = this;

        if (networkConnectionStateMonitor == null) {
            networkConnectionStateMonitor = new NetworkConnectionStateMonitor(mContext);
            networkConnectionStateMonitor.register();
        }

        if (networkConnectionStateMonitor != null) {
            init();
            setRecommendDietFoodListResponse();
        }
    }

    public void init() {
        cntntsNo = PreferenceManager.getString(mContext, "cntntsNo");
        mainCategoryName = PreferenceManager.getString(mContext, "mainCategoryName");

        RecyclerView recyclerView = findViewById(R.id.diet_food_list_category_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        adapter = new FoodListAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void setRecommendDietFoodListResponse() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getRecommendDietFoodListResponse();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < foodName.size(); i++) {
                            FoodRecipeDetail response = new FoodRecipeDetail();
                            response.setFdNm(foodName.get(i));
                            response.setRtnImageDc(foodImage.get(i));

                            adapter.addItem(response, mainCategoryName);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

        adapter.setOnFoodItemClickListener(new FoodListAdapter.OnFoodItemClickListener() {
            @Override
            public void onFoodItemClick(View view, int position) {
                Intent intent = new Intent(FoodListActivity.this, FoodRecipeDetailsActivity.class);
                intent.putExtra(MAIN_CATEGORY_NAME_KEY, mainCategoryName);
                intent.putExtra(FOOD_NAME_KEY, foodName.get(position));
                intent.putExtra(FOOD_IMAGE_KEY, foodImage.get(position));
                intent.putExtra(MATERIAL_INFO_KEY, materialInfo.get(position));
                intent.putExtra(RECIPE_ORDER_KEY, recipeOrder.get(position));
                intent.putExtra(CALORIE_INFO_KEY, calorieInfo.get(position));
                intent.putExtra(CARBOHYDRATES_INFO_KEY, carbohydratesInfo.get(position));
                intent.putExtra(PROTEIN_INFO_KEY, proteinInfo.get(position));
                intent.putExtra(LIPID_INFO_KEY, lipidInfo.get(position));

                startActivity(intent);
            }
        });
    }

    private void getRecommendDietFoodListResponse() {
        String apiKey = getString(R.string.recommended_food_recipe_api_key);

        foodName = new ArrayList<>();
        foodImage = new ArrayList<>();
        materialInfo = new ArrayList<>();
        recipeOrder = new ArrayList<>();
        calorieInfo = new ArrayList<>();
        carbohydratesInfo = new ArrayList<>();
        proteinInfo = new ArrayList<>();
        lipidInfo = new ArrayList<>();

        String apiUrl = "http://api.nongsaro.go.kr/service/recomendDiet/recomendDietDtl?apiKey=" + apiKey
                + "&cntntsNo=" + cntntsNo;

        try {
            URL url = new URL(apiUrl);
            InputStream inputStream = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(inputStream, "UTF-8"));

            int eventType = xpp.getEventType();
            String tag = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                tag = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        switch (tag) {
                            case FOOD_NAME:
                                xpp.next();
                                foodName.add(xpp.getText());
                                break;
                            case IMAGE_DESCRIPTION:
                                xpp.next();
                                foodImage.add(xpp.getText());
                                break;
                            case MATERIAL_INFORMATION:
                                xpp.next();
                                materialInfo.add(xpp.getText());
                                break;
                            case COOKING_METHOD_INFORMATION:
                                xpp.next();
                                recipeOrder.add(xpp.getText());
                                break;
                            case CALORIE_INFORMATION:
                                xpp.next();
                                calorieInfo.add(xpp.getText());
                                break;
                            case CARBOHYDRATES_INFORMATION:
                                xpp.next();
                                carbohydratesInfo.add(xpp.getText());
                                break;
                            case PROTEIN_INFORMATION:
                                xpp.next();
                                proteinInfo.add(xpp.getText());
                                break;
                            case LIPID_INFORMATION:
                                xpp.next();
                                lipidInfo.add(xpp.getText());
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkConnectionStateMonitor.unRegister();
    }
}
