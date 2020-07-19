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

    //TODO: change variable name to clarify
    private LinearLayout mainCategoryLayout1;
    private LinearLayout mainCategoryLayout2;
    private LinearLayout mainCategoryLayout3;
    private LinearLayout mainCategoryLayout4;
    private LinearLayout mainCategoryLayout5;

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
        mainCategoryLayout1 = findViewById(R.id.main_category_layout_1);
        mainCategoryLayout2 = findViewById(R.id.main_category_layout_2);
        mainCategoryLayout3 = findViewById(R.id.main_category_layout_3);
        mainCategoryLayout4 = findViewById(R.id.main_category_layout_4);
        mainCategoryLayout5 = findViewById(R.id.main_category_layout_5);
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

        mainCategoryLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("dietSeCode", dietSeCode.get(0));
                startActivity(intent);
            }
        });

        mainCategoryLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("dietSeCode", dietSeCode.get(1));
                startActivity(intent);
            }
        });

        mainCategoryLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("dietSeCode", dietSeCode.get(2));
                startActivity(intent);
            }
        });

        mainCategoryLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("dietSeCode", dietSeCode.get(3));
                startActivity(intent);
            }
        });

        mainCategoryLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("dietSeCode", dietSeCode.get(4));
                startActivity(intent);
            }
        });
    }
}
