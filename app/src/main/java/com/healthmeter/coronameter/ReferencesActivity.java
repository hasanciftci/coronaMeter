package com.healthmeter.coronameter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_references);
        RelativeLayout linearLayout = findViewById(R.id.refMain);
        TextView txtRefWho = (TextView) findViewById(R.id.txtRefWho);
        TextView txtRefJHU = (TextView) findViewById(R.id.txtRefJHU);
        TextView txtRefOurworldInData = (TextView) findViewById(R.id.txtRefOurworldInData);
        TextView txtRefWorldoMeter = (TextView) findViewById(R.id.txtRefWorldoMeter);
        TextView txtRefMaps = (TextView) findViewById(R.id.txtRefMaps);

        if (txtRefWho != null) {
            txtRefWho.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (txtRefJHU != null) {
            txtRefJHU.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (txtRefOurworldInData != null) {
            txtRefOurworldInData.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (txtRefWorldoMeter != null) {
            txtRefWorldoMeter.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (txtRefMaps != null) {
            txtRefMaps.setMovementMethod(LinkMovementMethod.getInstance());
        }

        //ConstraintLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

    }
}
