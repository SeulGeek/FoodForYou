package com.example.foodforyou.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodforyou.R;
import com.example.foodforyou.model.RecommendDietDetail;
import com.example.foodforyou.viewModel.NetworkConnectionStateMonitor;
import com.example.foodforyou.viewModel.PreferenceManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class RecommendDietFoodListActivity extends AppCompatActivity {

    private Context mContext;
    private NetworkConnectionStateMonitor networkConnectionStateMonitor;

    private RecommendDietFoodListAdapter adapter;
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

        adapter = new RecommendDietFoodListAdapter();
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
                            RecommendDietDetail response = new RecommendDietDetail();
                            response.setFdNm(foodName.get(i));
                            response.setRtnImageDc(foodImage.get(i));

                            adapter.addItem(response, mainCategoryName);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

        adapter.setOnFoodItemClickListener(new RecommendDietFoodListAdapter.OnFoodItemClickListener() {
            @Override
            public void onFoodItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), FoodRecipeDetails.class);
                intent.putExtra("mainCategoryName", mainCategoryName);
                intent.putExtra("foodName", foodName.get(position));
                intent.putExtra("foodImage", foodImage.get(position));
                intent.putExtra("materialInfo", materialInfo.get(position));
                intent.putExtra("recipeOrder", recipeOrder.get(position));
                intent.putExtra("calorieInfo", calorieInfo.get(position));
                intent.putExtra("carbohydratesInfo", carbohydratesInfo.get(position));
                intent.putExtra("proteinInfo", proteinInfo.get(position));
                intent.putExtra("lipidInfo", lipidInfo.get(position));


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
                        if (tag.equals("fdNm")) {
                            xpp.next();
                            foodName.add(xpp.getText());
                        } else if (tag.equals("rtnImageDc")) {
                            xpp.next();
                            foodImage.add(xpp.getText());
                        } else if (tag.equals("matrlInfo")) {
                            xpp.next();
                            materialInfo.add(xpp.getText());
                        } else if (tag.equals("ckngMthInfo")) {
                            xpp.next();
                            recipeOrder.add(xpp.getText());
                        } else if (tag.equals("clriInfo")) {
                            xpp.next();
                            calorieInfo.add(xpp.getText());
                        } else if (tag.equals("crbhInfo")) {
                            xpp.next();
                            carbohydratesInfo.add(xpp.getText());
                        } else if (tag.equals("protInfo")) {
                            xpp.next();
                            proteinInfo.add(xpp.getText());
                        } else if (tag.equals("ntrfsInfo")) {
                            xpp.next();
                            lipidInfo.add(xpp.getText());
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
