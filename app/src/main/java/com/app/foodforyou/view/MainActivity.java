package com.app.foodforyou.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.foodforyou.BuildConfig;
import com.app.foodforyou.R;
import com.app.foodforyou.model.MainCategory;
import com.app.foodforyou.viewModel.NetworkConnectionStateMonitor;
import com.app.foodforyou.viewModel.PreferenceManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getCanonicalName();

    // API response's tag name
    public static final String ITEM = "item";

    // API response's element
    public static final String DIET_SEPARATION_CODE = "dietSeCode";

    // SEND RESPONSE VALUE'S KEY
    public static final String MAIN_CATEGORY_NAME_KEY = "mainCategoryName";
    public static final String DIET_SEPARATION_CODE_KEY = "dietSeCode";

    private Context mContext;
    private NetworkConnectionStateMonitor mNetworkConnectionStateMonitor;

    private LinearLayout mStudyDietMainCategory;
    private LinearLayout mHealthyDietMainCategory;
    private LinearLayout mHomeMealMainCategory;
    private LinearLayout mEventDietMainCategory;
    private LinearLayout mRefreshDietMainCategory;
    private AdView mAdView;

    private ArrayList<String> mDietSeCode;

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        if (mNetworkConnectionStateMonitor != null) {
            getMainCategoryTitleResponse();
        }
    }

    private void init() {
        mContext = this;

        if (mNetworkConnectionStateMonitor == null) {
            mNetworkConnectionStateMonitor = new NetworkConnectionStateMonitor(mContext);
            mNetworkConnectionStateMonitor.register();


            //TODO: Add progress dialog (or bar) after first release
            if (!mNetworkConnectionStateMonitor.isConnected()) {

                final AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                        .setCancelable(false)
                        .setMessage(R.string.dialog_network_alert_message)
                        .setPositiveButton(R.string.dialog_positive_button, null)
                        .create();

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mNetworkConnectionStateMonitor.isConnected()) {
                                    alertDialog.dismiss();
                                    Toast.makeText(mContext, R.string.network_connected_message, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mContext, R.string.network_unconnected_message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

                alertDialog.show();
            }
        }

        mStudyDietMainCategory = findViewById(R.id.study_diet_main_category_layout);
        mHealthyDietMainCategory = findViewById(R.id.healthy_diet_main_category_layout);
        mHomeMealMainCategory = findViewById(R.id.home_meal_main_category_layout);
        mEventDietMainCategory = findViewById(R.id.event_diet_main_category_layout);
        mRefreshDietMainCategory = findViewById(R.id.refresh_diet_main_category_layout);
        mAdView = findViewById(R.id.adView);

        runAdMob();
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
                        for (int i = 0; i< mDietSeCode.size(); i++) {
                            // 각 List의 값들을 MainCategory 객체에 set 해줍니다
                            MainCategory mainCategoryData = new MainCategory();
                            mainCategoryData.setDietSeCode(mDietSeCode.get(i));
                        }
                    }
                });
            }
        }).start();

        onClickCategory();
    }

    private void getMainCategoryResponse() {
        mDietSeCode = new ArrayList<>();

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
                        if (xpp.getName().equals(ITEM)) { // fist research result

                        } else if (xpp.getName().equals(DIET_SEPARATION_CODE)) {
                            xpp.next();
                            mDietSeCode.add(xpp.getText());
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
        mIntent = new Intent(this, DietListActivity.class);

        mStudyDietMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMainCategory(0, R.string.study_diet_main_category_name);
            }
        });

        mHealthyDietMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMainCategory(1, R.string.healthy_diet_main_category_name);
            }
        });

        mHomeMealMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMainCategory(2, R.string.home_meal_main_category_name);
            }
        });

        mEventDietMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMainCategory(3, R.string.event_diet_main_category_name);
            }
        });

        mRefreshDietMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMainCategory(4, R.string.refresh_diet_main_category_name);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNetworkConnectionStateMonitor.unRegister();
    }

    private void runAdMob() {
        if (BuildConfig.DEBUG) {
            // If you would like to test real device, please write your device Id here.
            List<String> testDeviceIds = Arrays.asList("");
            RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
            MobileAds.setRequestConfiguration(configuration);
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        boolean isTestDevice = adRequest.isTestDevice(this);
        Log.v(TAG, "Is Admob test device? : " + isTestDevice); // to confirm it worked

        mAdView.loadAd(adRequest);
    }

    private void showUnstableNetworkErrorDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setCancelable(false)
                .setMessage(R.string.dialog_unstable_network_alert_message)
                .setPositiveButton(R.string.dialog_positive_button, null)
                .create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        finish();
                    }
                });
            }
        });
        alertDialog.show();
    }

    private void setMainCategory(int index, int stringId) {
        if (mDietSeCode.size() > 0) {
            PreferenceManager.setString(mContext, DIET_SEPARATION_CODE_KEY, mDietSeCode.get(index));
            PreferenceManager.setString(mContext, MAIN_CATEGORY_NAME_KEY, getString(stringId));
            startActivity(mIntent);
        } else {
            showUnstableNetworkErrorDialog();
        }
    }
}