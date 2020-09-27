package com.app.foodforyou.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.foodforyou.R;
import com.app.foodforyou.model.MainCategory;
import com.app.foodforyou.viewModel.NetworkConnectionStateMonitor;
import com.app.foodforyou.viewModel.PreferenceManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // API response's tag name
    public static final String ITEM = "item";

    // API response's element
    public static final String DIET_SEPARATION_CODE = "dietSeCode";

    // SEND RESPONSE VALUE'S KEY
    public static final String MAIN_CATEGORY_NAME_KEY = "mainCategoryName";
    public static final String DIET_SEPARATION_CODE_KEY = "dietSeCode";

    private Context mContext;
    private NetworkConnectionStateMonitor networkConnectionStateMonitor;

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

        if (networkConnectionStateMonitor != null) {
            getMainCategoryTitleResponse();
        }
    }

    private void init() {
        mContext = this;

        if (networkConnectionStateMonitor == null) {
            networkConnectionStateMonitor = new NetworkConnectionStateMonitor(mContext);
            networkConnectionStateMonitor.register();


            //TODO: Add progress dialog (or bar) after first release
            if (!networkConnectionStateMonitor.isConnected()) {

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
                                if (networkConnectionStateMonitor.isConnected()) {
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
                        if (xpp.getName().equals(ITEM)) { // fist research result

                        } else if (xpp.getName().equals(DIET_SEPARATION_CODE)) {
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
        intent = new Intent(this, DietListActivity.class);

        studyDietMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.setString(mContext, DIET_SEPARATION_CODE_KEY, dietSeCode.get(0));
                PreferenceManager.setString(mContext, MAIN_CATEGORY_NAME_KEY, getString(R.string.study_diet_main_category_name));
                startActivity(intent);
            }
        });

        healthyDietMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.setString(mContext, DIET_SEPARATION_CODE_KEY, dietSeCode.get(1));
                PreferenceManager.setString(mContext, MAIN_CATEGORY_NAME_KEY, getString(R.string.healthy_diet_main_category_name));
                startActivity(intent);
            }
        });

        homeMealMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.setString(mContext, DIET_SEPARATION_CODE_KEY, dietSeCode.get(2));
                PreferenceManager.setString(mContext, MAIN_CATEGORY_NAME_KEY, getString(R.string.home_meal_main_category_name));
                startActivity(intent);
            }
        });

        eventDietMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.setString(mContext, DIET_SEPARATION_CODE_KEY, dietSeCode.get(3));
                PreferenceManager.setString(mContext, MAIN_CATEGORY_NAME_KEY, getString(R.string.event_diet_main_category_name));
                startActivity(intent);
            }
        });

        refreshDietMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.setString(mContext, DIET_SEPARATION_CODE_KEY, dietSeCode.get(4));
                PreferenceManager.setString(mContext, MAIN_CATEGORY_NAME_KEY, getString(R.string.refresh_diet_main_category_name));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkConnectionStateMonitor.unRegister();
    }

}