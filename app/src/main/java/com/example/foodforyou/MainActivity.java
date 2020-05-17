package com.example.foodforyou;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView mMainRecipeCategory;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button inquiryButton = findViewById(R.id.button_inquiry);
        mMainRecipeCategory = findViewById(R.id.tv_main_recipe_category);

        // Get Api response
        inquiryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data = getXmlData();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMainRecipeCategory.setText(data);
                            }
                        });
                    }
                }).start();
            }
        });

    }

    private String getXmlData() {
        String apiKey = ""; //TODO: set your api key
        String apiUrl = "http://api.nongsaro.go.kr/service/recomendDiet/mainCategoryList?apiKey=" + apiKey;
        StringBuffer strBuffer = new StringBuffer();

        try{
            URL url = new URL(apiUrl);
            InputStream inputStream = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(inputStream, "UTF-8"));

            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        strBuffer.append("parsing start...\n\n");
                        break;
                    case XmlPullParser.START_TAG:
                        if (xpp.getName().equals("item")) { // fist research result

                        } else if (xpp.getName().equals("dietSeName")) {
                            xpp.next();
                            strBuffer.append(xpp.getText() + "\n");
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        if (xpp.getName().equals("item")) {
                            strBuffer.append("\n"); // first research close, change line
                        }
                }
                eventType= xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        strBuffer.append("parsing end...\n");
        return strBuffer.toString();
    }

}
