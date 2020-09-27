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

    public static final String MAIN_CATEGORY_NAME_KEY = "mainCategoryName";

    // API response's tag name
    public static final String ITEM = "item";

    // API response's element
    public static final String CONTENTS_NO = "cntntsNo";
    public static final String DIET_NAME = "dietNm";
    public static final String FOOD_NAME = "fdNm";
    public static final String CONTENTS_SUBJECT = "cntntsSj";
    public static final String CONTENTS_CHARGER_NAME = "cntntsChargerEsntlNm";
    public static final String REGISTER_DATE = "registDt";
    public static final String CONTENTS_READ_COUNT = "cntntsRdcnt"; // the view count
    public static final String FILE_SEPARATION_CODE = "rtnFileSeCode";
    public static final String FILE_SEQUENCE= "rtnFileSn"; // file order
    public static final String ORIGINAL_FILE_NAME= "rtnOriginFileNm";
    public static final String SAVE_FILE_NAME= "rtnStreFileNu";
    public static final String IMAGE_DESCRIPTION= "rtnImageDc";
    public static final String THUMBNAIL_FILE_NAME= "rtnThumbFileNm";
    public static final String IMAGE_SEPARATION_CODE= "rtnImgSeCode";

    // SEND RESPONSE VALUE'S KEY
    public static final String CONTENTS_NO_KEY = "cntntsNo";

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
                    //TODO: Do process non-exist page when user click add diet button
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
                Intent intent = new Intent(DietListActivity.this, FoodListActivity.class);
                PreferenceManager.setString(mContext, CONTENTS_NO_KEY, String.valueOf(dataManager.getDietListResponses().get(position).getCntntsNo()));
                PreferenceManager.setString(mContext, MAIN_CATEGORY_NAME_KEY, mainCategoryName);
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
                    // The XML data starts
                } else if (eventType == XmlPullParser.START_TAG) {
                    startTag = xpp.getName();
                    if (startTag.equals(ITEM)) {
                        isItemType = true;
                        dataManager.getDietListResponses().add(new DietListResponse());
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (isItemType) {
                        switch (startTag) {
                            case CONTENTS_NO:
                                dataManager.getLastDietListData().setCntntsNo(Integer.parseInt(xpp.getText()));
                                break;
                            case DIET_NAME:
                                dataManager.getLastDietListData().setDietNm(xpp.getText());
                                break;
                            case FOOD_NAME:

                            case CONTENTS_SUBJECT:

                            case CONTENTS_CHARGER_NAME:

                            case REGISTER_DATE:

                            case CONTENTS_READ_COUNT:

                            case FILE_SEPARATION_CODE:

                            case FILE_SEQUENCE:

                            case ORIGINAL_FILE_NAME:

                            case SAVE_FILE_NAME:

                            case IMAGE_DESCRIPTION:
                                dataManager.getLastDietListData().setRtnImageDc(xpp.getText());
                                break;
                            case THUMBNAIL_FILE_NAME:

                            case IMAGE_SEPARATION_CODE:
                                break;
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    String endTag = xpp.getName() ;
                    if (endTag.equals(ITEM)) {
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