package com.healthmeter.coronameter;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.healthmeter.coronameter.javaop.StaticVar;

public class WorldMapJHUActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_world_map_jhu);

        WebView webView = findViewById(R.id.webViewJHU);
        webView.getSettings().setJavaScriptEnabled(true);


        if (StaticVar.urlJHU == null)
            webView.loadUrl("https://www.arcgis.com/apps/opsdashboard/index.html#/bda7594740fd40299423467b48e9ecf6");
        else webView.loadUrl(StaticVar.urlJHU);

    }
}
