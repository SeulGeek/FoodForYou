package com.example.foodforyou.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodforyou.R;
import com.example.foodforyou.model.MainCategory;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LinearLayout studyDietMainCategory;
    private LinearLayout healthyDietMainCategory;
    private LinearLayout homeMealMainCategory;
    private LinearLayout eventDietMainCategory;
    private LinearLayout refreshDietMainCategory;

    private ArrayList<String> dietSeCode;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        getMainCategoryTitleResponse();
    }

    private void init() {
        studyDietMainCategory = findViewById(R.id.study_diet_main_category_layout);
        healthyDietMainCategory = findViewById(R.id.healthy_diet_main_category_layout);
        homeMealMainCategory = findViewById(R.id.home_meal_main_category_layout);
        eventDietMainCategory = findViewById(R.id.event_diet_main_category_layout);
        refreshDietMainCategory = findViewById(R.id.refresh_diet_main_category_layout);
    }

    private void getMainCategoryTitleResponse() {
        // Get Api response
        new Thread(new Runnable() {
            @Override
            public void run() {
                getMainCategoryResponse();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i< dietSeCode.size(); i++) {
                            // 각 List의 값들을 MainCategory 객체에 set 해줍니다
                            MainCategory mainCategoryData = new MainCategory();
                            mainCategoryData.setDietSeCode(dietSeCode.get(i));
                        }
                    }
                });
            }
        }).start();
        onClickCategory();
    }

    private void getMainCategoryResponse() {
        dietSeCode = new ArrayList<>();

        String apiKey = getString(R.string.recommended_food_recipe_api_key);
        String apiUrl = "http://api.nongsaro.go.kr/service/recomendDiet/mainCategoryList?apiKey=" + apiKey;

        try{
            URL url = new URL(apiUrl);
            InputStream inputStream = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(inputStream, "UTF-8"));

            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (xpp.getName().equals("item")) { // fist research result

                        } else if (xpp.getName().equals("dietSeCode")) {
                            xpp.next();
                            dietSeCode.add(xpp.getText());
                        }
                        break;
                }
                eventType= xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onClickCategory() {
        intent = new Intent(getApplicationContext(), RecommendDietListActivity.class);

        studyDietMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("dietSeCode", dietSeCode.get(0));
                intent.putExtra("mainCategoryName", getString(R.string.study_diet_main_category_name));
                startActivity(intent);
            }
        });

        healthyDietMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("dietSeCode", dietSeCode.get(1));
                intent.putExtra("mainCategoryName", getString(R.string.healthy_diet_main_category_name));
                startActivity(intent);
            }
        });

        homeMealMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("dietSeCode", dietSeCode.get(2));
                intent.putExtra("mainCategoryName", getString(R.string.home_meal_main_category_name));
                startActivity(intent);
            }
        });

        eventDietMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("dietSeCode", dietSeCode.get(3));
                intent.putExtra("mainCategoryName", getString(R.string.event_diet_main_category_name));
                startActivity(intent);
            }
        });

        refreshDietMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("dietSeCode", dietSeCode.get(4));
                intent.putExtra("mainCategoryName", getString(R.string.refresh_diet_main_category_name));
                startActivity(intent);
            }
        });
    }

}