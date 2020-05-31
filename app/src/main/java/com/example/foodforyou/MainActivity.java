package com.example.foodforyou;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodforyou.model.MainCategory;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MainCategoryAdapter adapter;
    private ArrayList<String> dietSeCode;
    private ArrayList<String> mainCategoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        getMainCategoryTitleResponse();
    }

    private void init() {
        recyclerView = findViewById(R.id.recycler_view_main_category);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MainCategoryAdapter();
        recyclerView.setAdapter(adapter);
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

                            Log.i("dietSecode: ", dietSeCode.get(i));

                            mainCategoryData.setDietSeName(mainCategoryTitle.get(i));

                            // 각 값들이 들어간 data를 adapter에 추가합니다.
                            adapter.addItem(mainCategoryData);
                        }

                        // adapter의 값이 변경되었다는 것을 알려줍니다.
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

    }


    private void getMainCategoryResponse() {
        dietSeCode = new ArrayList<>();
        mainCategoryTitle = new ArrayList<>();
        String apiKey = ""; //TODO: set your api key
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
                        } else if (xpp.getName().equals("dietSeName")) {
                            xpp.next();
                            mainCategoryTitle.add(xpp.getText());
                        }
                        break;
                }
                eventType= xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
