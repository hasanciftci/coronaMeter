package com.healthmeter.coronameter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import com.healthmeter.coronameter.javaop.StaticVar;

public class WorldMapWHOActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_world_map_who);

        WebView webView = findViewById(R.id.webViewWHO);
        webView.getSettings().setJavaScriptEnabled(true);
        if (StaticVar.urlWHO == null)
            webView.loadUrl("https://experience.arcgis.com/experience/685d0ace521648f8a5beeeee1b9125cd");
        else webView.loadUrl(StaticVar.urlWHO);
    }
}
