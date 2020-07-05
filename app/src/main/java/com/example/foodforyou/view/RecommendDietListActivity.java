package com.example.foodforyou.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodforyou.R;
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
    private ArrayList<String> cntntsNo;
    private ArrayList<String> imgLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_diet_list);

        init();

        setRecommendDietListResponse();
    }

    private void init() {
        Intent intent = getIntent();
        dietSeCode = intent.getStringExtra("dietSeCode");
        if (dietSeCode != null) {
            Log.d("dietSeCode:", dietSeCode);
        }

        RecyclerView recyclerView = findViewById(R.id.diet_list_category_recycler_view);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
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
                            RecommendDietListResponse response = new RecommendDietListResponse();
                            response.setDietNm(dietName.get(i));
                            response.setRtnImageDc(imgLink.get(i));

                            Log.d("Recommend Text: ", dietName.get(i));
                            adapter.addItem(response);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

        adapter.setOnItemClickListener(new OnDietClickListener() {
            @Override
            public void onDietItemClick(RecommendDietListAdapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getApplicationContext(), RecommendDietFoodListActivity.class);
                intent.putExtra("cntntsNo", cntntsNo.get(position));
                startActivity(intent);
            }
        });
    }

    private void getRecommendDietListResponse() {
        dietName = new ArrayList<>();
        cntntsNo = new ArrayList<>();
        imgLink = new ArrayList<>();
        String apiKey = ""; //TODO: set your api key
        String apiUrl = "http://api.nongsaro.go.kr/service/recomendDiet/recomendDietList?apiKey=" + apiKey
                + "&dietSeCode=" + dietSeCode;

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
                    startTag = xpp.getName();
                    if (startTag.equals("item")) isItemType = true;

                } else if (eventType == XmlPullParser.TEXT) {
                    String text = xpp.getText() ;
                    if (isItemType) {
                        if (startTag.equals("cntntsNo")) {
                            cntntsNo.add(xpp.getText());
                        } else if (startTag.equals("dietNm")) {
                            dietName.add(xpp.getText());
                        } else if (startTag.equals("fdNm")) {

                        } else if (startTag.equals("cntntsSj")) {

                        } else if (startTag.equals("cntntsChargerEsntlNm")) {

                        } else if (startTag.equals("registDt")) {

                        } else if (startTag.equals("cntntsRdcnt")) {

                        } else if (startTag.equals("rtnFileSeCode")) {

                        } else if (startTag.equals("rtnFileSn")) {

                        } else if (startTag.equals("rtnStreFileNu")) {

                        } else if (startTag.equals("rtnImageDc")) {
                            imgLink.add(xpp.getText());
                        } else if (startTag.equals("rtnThumbFileNm")) {

                        } else if (startTag.equals("rtnImgSeCode")) {

                        }
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
