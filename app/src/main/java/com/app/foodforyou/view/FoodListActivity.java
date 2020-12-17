package com.app.foodforyou.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.foodforyou.R;
import com.app.foodforyou.model.FoodRecipeDetail;
import com.app.foodforyou.viewModel.NetworkConnectionStateMonitor;
import com.app.foodforyou.viewModel.PreferenceManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class FoodListActivity extends AppCompatActivity {

    private Context mContext;
    private NetworkConnectionStateMonitor mNetworkConnectionStateMonitor;
    private LinearLayout mProgressBar;

    /**
    *  RESPONSE ELEMENT
    *  - API response variable name's explanation is insufficient.
    */
    public static final String FOOD_NAME = "fdNm";
    public static final String IMAGE_DESCRIPTION = "rtnImageDc";
    public static final String MATERIAL_INFORMATION = "matrlInfo";
    public static final String COOKING_METHOD_INFORMATION = "ckngMthInfo";
    public static final String CALORIE_INFORMATION = "clriInfo";
    public static final String CARBOHYDRATES_INFORMATION = "crbhInfo";
    public static final String PROTEIN_INFORMATION = "protInfo";
    public static final String LIPID_INFORMATION = "ntrfsInfo"; // It seems to mean liquid in nutrition facts as follow API's description

    // SEND RESPONSE VALUE'S KEY
    public static final String MAIN_CATEGORY_NAME_KEY = "mainCategoryName";
    public static final String FOOD_NAME_KEY = "foodName";
    public static final String FOOD_IMAGE_KEY = "foodImage";
    public static final String MATERIAL_INFO_KEY = "materialInfo";
    public static final String RECIPE_ORDER_KEY = "recipeOrder";
    public static final String CALORIE_INFO_KEY = "calorieInfo";
    public static final String CARBOHYDRATES_INFO_KEY = "carbohydratesInfo";
    public static final String PROTEIN_INFO_KEY = "proteinInfo";
    public static final String LIPID_INFO_KEY = "lipidInfo";

    private FoodListAdapter mAdapter;
    private String mMainCategoryName;
    private String mCntntsNo;
    private ArrayList<String> mFoodName;
    private ArrayList<String> mFoodImage;
    private ArrayList<String> mMaterialInfo;
    private ArrayList<String> mRecipeOrder;
    private ArrayList<String> mCalorieInfo;
    private ArrayList<String> mCarbohydratesInfo;
    private ArrayList<String> mProteinInfo;
    private ArrayList<String> mLipidInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_diet_food_list);

        mContext = this;

        if (mNetworkConnectionStateMonitor == null) {
            mNetworkConnectionStateMonitor = new NetworkConnectionStateMonitor(mContext);
            mNetworkConnectionStateMonitor.register();
        }

        if (mNetworkConnectionStateMonitor != null) {
            init();
            setRecommendDietFoodListResponse();
        }
    }

    public void init() {
        mCntntsNo = PreferenceManager.getString(mContext, "cntntsNo");
        mMainCategoryName = PreferenceManager.getString(mContext, "mainCategoryName");

        mProgressBar = findViewById(R.id.progress_bar);

        RecyclerView recyclerView = findViewById(R.id.diet_food_list_category_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        mAdapter = new FoodListAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    private void setRecommendDietFoodListResponse() {
        showProgressBar();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getRecommendDietFoodListResponse();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < mFoodName.size(); i++) {
                            hideProgressBar();
                            FoodRecipeDetail response = new FoodRecipeDetail();
                            response.setFdNm(mFoodName.get(i));
                            response.setRtnImageDc(mFoodImage.get(i));

                            mAdapter.addItem(response, mMainCategoryName);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

        mAdapter.setOnFoodItemClickListener(new FoodListAdapter.OnFoodItemClickListener() {
            @Override
            public void onFoodItemClick(View view, int position) {
                Intent intent = new Intent(FoodListActivity.this, FoodRecipeDetailsActivity.class);
                intent.putExtra(MAIN_CATEGORY_NAME_KEY, mMainCategoryName);
                intent.putExtra(FOOD_NAME_KEY, mFoodName.get(position));
                intent.putExtra(FOOD_IMAGE_KEY, mFoodImage.get(position));
                intent.putExtra(MATERIAL_INFO_KEY, mMaterialInfo.get(position));
                intent.putExtra(RECIPE_ORDER_KEY, mRecipeOrder.get(position));
                intent.putExtra(CALORIE_INFO_KEY, mCalorieInfo.get(position));
                intent.putExtra(CARBOHYDRATES_INFO_KEY, mCarbohydratesInfo.get(position));
                intent.putExtra(PROTEIN_INFO_KEY, mProteinInfo.get(position));
                intent.putExtra(LIPID_INFO_KEY, mLipidInfo.get(position));

                startActivity(intent);
            }
        });
    }

    private void getRecommendDietFoodListResponse() {
        String apiKey = getString(R.string.recommended_food_recipe_api_key);

        mFoodName = new ArrayList<>();
        mFoodImage = new ArrayList<>();
        mMaterialInfo = new ArrayList<>();
        mRecipeOrder = new ArrayList<>();
        mCalorieInfo = new ArrayList<>();
        mCarbohydratesInfo = new ArrayList<>();
        mProteinInfo = new ArrayList<>();
        mLipidInfo = new ArrayList<>();

        String apiUrl = "http://api.nongsaro.go.kr/service/recomendDiet/recomendDietDtl?apiKey=" + apiKey
                + "&cntntsNo=" + mCntntsNo;

        try {
            URL url = new URL(apiUrl);
            InputStream inputStream = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(inputStream, "UTF-8"));

            int eventType = xpp.getEventType();
            String tag = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                tag = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        switch (tag) {
                            case FOOD_NAME:
                                xpp.next();
                                mFoodName.add(xpp.getText());
                                break;
                            case IMAGE_DESCRIPTION:
                                xpp.next();
                                mFoodImage.add(xpp.getText());
                                break;
                            case MATERIAL_INFORMATION:
                                xpp.next();
                                mMaterialInfo.add(xpp.getText());
                                break;
                            case COOKING_METHOD_INFORMATION:
                                xpp.next();
                                mRecipeOrder.add(xpp.getText());
                                break;
                            case CALORIE_INFORMATION:
                                xpp.next();
                                mCalorieInfo.add(xpp.getText());
                                break;
                            case CARBOHYDRATES_INFORMATION:
                                xpp.next();
                                mCarbohydratesInfo.add(xpp.getText());
                                break;
                            case PROTEIN_INFORMATION:
                                xpp.next();
                                mProteinInfo.add(xpp.getText());
                                break;
                            case LIPID_INFORMATION:
                                xpp.next();
                                mLipidInfo.add(xpp.getText());
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
