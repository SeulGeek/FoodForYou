package com.app.foodforyou.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
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

    private NetworkConnectionStateMonitor mNetworkConnectionStateMonitor;

    private Context mContext;
    private int mPageNo = 1;
    private int mCurrentItemSize = 0;
    private String mMainCategoryName;
    private String mDietSeCode;

    private RelativeLayout mAddItemButton;
    private DietListAdapter mAdapter;
    private LinearLayout mProgressBar;
    private DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_diet_list);

        mContext = this;

        if (mNetworkConnectionStateMonitor == null) {
            mNetworkConnectionStateMonitor = new NetworkConnectionStateMonitor(mContext);
            mNetworkConnectionStateMonitor.register();
        }

        if (mNetworkConnectionStateMonitor != null) {
            init();
            setRecommendDietListResponse();

            mAddItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"< clicked addItemButton >");
                    if (mPageNo >= 1) {
                        mPageNo += 1;
                    }
                    //TODO: Do process non-exist page when user click add diet button
                    setRecommendDietListResponse();
                }
            });

        }
    }

    private void init() {

        mDietSeCode = PreferenceManager.getString(mContext, "dietSeCode");
        mMainCategoryName = PreferenceManager.getString(mContext, "mainCategoryName");

        mProgressBar = findViewById(R.id.progress_bar);

        mDataManager = new DataManager();
        RecyclerView recyclerView = findViewById(R.id.diet_list_category_recycler_view);
        mAddItemButton = findViewById(R.id.add_diet_button);

        mAdapter = new DietListAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    private void setRecommendDietListResponse() {
        showProgressBar();
        // Get Api response
        new Thread(new Runnable() {
            @Override
            public void run() {
                getRecommendDietListResponse();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = mCurrentItemSize; i < mDataManager.getDietListResponses().size(); i++) {
                            hideProgressBar();

                            mAdapter.addItem(mDataManager.getDietListResponses().get(i), mMainCategoryName);
                            mAdapter.notifyDataSetChanged();
                            mCurrentItemSize = mDataManager.getDietListResponses().size();
                        }
                    }
                });
            }
        }).start();

        mAdapter.setOnItemClickListener(new OnDietClickListener() {
            @Override
            public void onDietItemClick(DietListAdapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(DietListActivity.this, FoodListActivity.class);
                PreferenceManager.setString(mContext, CONTENTS_NO_KEY, String.valueOf(mDataManager.getDietListResponses().get(position).getCntntsNo()));
                PreferenceManager.setString(mContext, MAIN_CATEGORY_NAME_KEY, mMainCategoryName);
                startActivity(intent);
            }
        });
    }

    private void getRecommendDietListResponse() {
        String apiKey = getString(R.string.recommended_food_recipe_api_key);
        String apiUrl = "http://api.nongsaro.go.kr/service/recomendDiet/recomendDietList?apiKey=" + apiKey
                + "&dietSeCode=" + mDietSeCode + "&pageNo=" + mPageNo;

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
                        mDataManager.getDietListResponses().add(new DietListResponse());
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (isItemType) {
                        switch (startTag) {
                            case CONTENTS_NO:
                                mDataManager.getLastDietListData().setCntntsNo(Integer.parseInt(xpp.getText()));
                                break;
                            case DIET_NAME:
                                mDataManager.getLastDietListData().setDietNm(xpp.getText());
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
                                mDataManager.getLastDietListData().setRtnImageDc(xpp.getText());
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
        for (int i = 0; i < mDataManager.getDietListResponses().size(); i++) {
            Log.d(TAG, String.valueOf(mDataManager.getDietListResponses().get(i).getCntntsNo()));
            Log.d(TAG, String.valueOf(mDataManager.getDietListResponses().get(i).getDietNm()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNetworkConnectionStateMonitor.unRegister();
    }

    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}