package com.example.foodforyou;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodforyou.model.RecommendDietListResponse;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class RecommendDietListActivity extends AppCompatActivity {

    private RecommendDietListAdapter adapter;
    private String dietSeCode;
    private ArrayList<String> dietName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_diet_list);

        init();

        setRecommendDietListResponse();
    }

    private void init() {
        Intent intent = getIntent();
        dietSeCode = intent.getStringExtra("dietSeCode");
        if (dietSeCode != null) {
            Log.d("dietSeCode:", dietSeCode);
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view_recommend_diet_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecommendDietListAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void setRecommendDietListResponse() {
        // Get Api response
        new Thread(new Runnable() {
            @Override
            public void run() {
                getRecommendDietListResponse();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < dietName.size(); i++) {
                            RecommendDietListResponse recommendDietListResponse = new RecommendDietListResponse();
                            recommendDietListResponse.setDietNm(dietName.get(i));

                            Log.d("Recommend Text: ", dietName.get(i));
                            adapter.addItem(recommendDietListResponse);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private void getRecommendDietListResponse() {
        dietName = new ArrayList<>();
        String apiKey = ""; //TODO: set your api key
        String apiUrl = "http://api.nongsaro.go.kr/service/recomendDiet/recomendDietList?apiKey=" + apiKey
                + "&dietSeCode=" + dietSeCode + "&pageNm=1&numOfRows=3";

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
                    // XML 데이터 시작
                } else if (eventType == XmlPullParser.START_TAG) {
                    startTag = xpp.getName() ;
                    if (startTag.equals("item")) isItemType = true;

                } else if (eventType == XmlPullParser.TEXT) {
                    String text = xpp.getText() ;
                    if (isItemType && startTag.equals("cntntsSj")) {
                        dietName.add(xpp.getText());
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    String endTag = xpp.getName() ;
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
