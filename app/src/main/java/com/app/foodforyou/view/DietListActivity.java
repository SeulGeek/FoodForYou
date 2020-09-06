package com.app.foodforyou.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.foodforyou.R;
import com.app.foodforyou.model.DataManager;
import com.app.foodforyou.model.DietListResponse;
import com.app.foodforyou.viewModel.NetworkConnectionStateMonitor;
import com.app.foodforyou.viewModel.PreferenceManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class DietListActivity extends AppCompatActivity {

    public static final String TAG = DietListActivity.class.getCanonicalName();
    private NetworkConnectionStateMonitor networkConnectionStateMonitor;

    private Context mContext;
    private int pageNo = 1;
    private int currentItemSize = 0;
    private String mainCategoryName;
    private String dietSeCode;

    private RelativeLayout addItemButton;
    private DietListAdapter adapter;

    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_diet_list);

        mContext = this;

        if (networkConnectionStateMonitor == null) {
            networkConnectionStateMonitor = new NetworkConnectionStateMonitor(mContext);
            networkConnectionStateMonitor.register();
        }

        if (networkConnectionStateMonitor != null) {
            init();
            setRecommendDietListResponse();

            addItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"< clicked addItemButton >");
                    if (pageNo >= 1) {
                        pageNo += 1;
                    }
                    //TODO: page가 없는 경우도 처리해주기
                    setRecommendDietListResponse();
                }
            });

        }
    }

    private void init() {

        dietSeCode = PreferenceManager.getString(mContext, "dietSeCode");
        mainCategoryName = PreferenceManager.getString(mContext, "mainCategoryName");

        dataManager = new DataManager();
        RecyclerView recyclerView = findViewById(R.id.diet_list_category_recycler_view);
        addItemButton = findViewById(R.id.add_diet_button);

        adapter = new DietListAdapter();
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
                        for (int i = currentItemSize; i < dataManager.getDietListResponses().size(); i++) {
                            adapter.addItem(dataManager.getDietListResponses().get(i), mainCategoryName);
                            adapter.notifyDataSetChanged();
                            currentItemSize = dataManager.getDietListResponses().size();
                        }
                    }
                });
            }
        }).start();

        adapter.setOnItemClickListener(new OnDietClickListener() {
            @Override
            public void onDietItemClick(DietListAdapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getApplicationContext(), FoodListActivity.class);
                PreferenceManager.setString(mContext, "cntntsNo", String.valueOf(dataManager.getDietListResponses().get(position).getCntntsNo()));
                PreferenceManager.setString(mContext,"mainCategoryName", mainCategoryName);
                startActivity(intent);
            }
        });
    }

    private void getRecommendDietListResponse() {
        String apiKey = getString(R.string.recommended_food_recipe_api_key);
        String apiUrl = "http://api.nongsaro.go.kr/service/recomendDiet/recomendDietList?apiKey=" + apiKey
                + "&dietSeCode=" + dietSeCode + "&pageNo=" + pageNo;

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
                    if (startTag.equals("item")) {
                        isItemType = true;
                        dataManager.getDietListResponses().add(new DietListResponse());
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (isItemType) {
                        if (startTag.equals("cntntsNo")) {
                            dataManager.getLastDietListData().setCntntsNo(Integer.parseInt(xpp.getText()));
                        } else if (startTag.equals("dietNm")) {
                            dataManager.getLastDietListData().setDietNm(xpp.getText());
                        } else if (startTag.equals("fdNm")) {

                        } else if (startTag.equals("cntntsSj")) {

                        } else if (startTag.equals("cntntsChargerEsntlNm")) {

                        } else if (startTag.equals("registDt")) {

                        } else if (startTag.equals("cntntsRdcnt")) {

                        } else if (startTag.equals("rtnFileSeCode")) {

                        } else if (startTag.equals("rtnFileSn")) {

                        } else if (startTag.equals("rtnStreFileNu")) {

                        } else if (startTag.equals("rtnImageDc")) {
                            dataManager.getLastDietListData().setRtnImageDc(xpp.getText());
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
        for (int i = 0; i < dataManager.getDietListResponses().size(); i++) {
            Log.d(TAG, String.valueOf(dataManager.getDietListResponses().get(i).getCntntsNo()));
            Log.d(TAG, String.valueOf(dataManager.getDietListResponses().get(i).getDietNm()));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkConnectionStateMonitor.unRegister();
    }

}