package com.example.foodforyou.view;

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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class RecommendDietFoodListActivity extends AppCompatActivity {

    private RecommendDietFoodListAdapter adapter;
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
        setContentView(R.layout.activity_main);

        init();

        setRecommendDietFoodListResponse();
    }

    public void init() {
        Intent intent = getIntent();
        cntntsNo = intent.getStringExtra("cntntsNo");

        RecyclerView recyclerView = findViewById(R.id.recycler_view_main_category);

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

                            adapter.addItem(response);
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
        foodName = new ArrayList<>();
        foodImage = new ArrayList<>();
        String apiKey = ""; //TODO: set your api key
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
            String startTag = "";
            boolean isItemType = false;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    // XML data start
                } else if (eventType == XmlPullParser.START_TAG) {
                    startTag = xpp.getName();
                    if (startTag.equals("item")) isItemType = true;
                } else if (eventType == XmlPullParser.TEXT) {
                    if (isItemType) {
                        if (startTag.equals("fdNm")) {
                            foodName.add(xpp.getText());
                        } else if (startTag.equals("rtnImageDc")) {
                            foodImage.add(xpp.getText());
                        } else if (startTag.equals("matrlInfo")) {
                            materialInfo.add(xpp.getText());
                        } else if (startTag.equals("ckngMthInfo")) {
                            recipeOrder.add(xpp.getText());
                        } else if (startTag.equals("clriInfo")) {
                            calorieInfo.add(xpp.getText());
                        } else if (startTag.equals("crbhInfo")) {
                            carbohydratesInfo.add(xpp.getText());
                        } else if (startTag.equals("protInfo")) {
                            proteinInfo.add(xpp.getText());
                        } else if (startTag.equals("ntrfsInfo")) {
                            lipidInfo.add(xpp.getText());
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    String endTag = xpp.getName();
                    if (endTag.equals("item")) {
                        startTag = "";
                        isItemType = false;
                    }
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
