package com.healthmeter.coronameter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.healthmeter.coronameter.javaop.OnSwipeTouchListener;
import com.healthmeter.coronameter.javaop.SessionManager;
import com.healthmeter.coronameter.javaop.StaticVar;

import java.util.List;

public class SingleCountryActivity extends AppCompatActivity {

    private SmartMaterialSpinner spProvince;

    TextView textViewTotalCases;
    TextView textViewNewCases;
    TextView textViewTotalDeaths;
    TextView textViewNewDeaths;
    TextView textViewTotalRecovered;
    TextView textViewActiveCases;
    TextView textViewSerious;
    TextView textViewTotalCases1Mpop;
    TextView textViewTotalDeaths1Mpop;
    TextView textViewTotalTest;
    TextView textViewTotalTest1mpop;
    ImageView imageView;
    private SessionManager session;
    RelativeLayout single_main_Relativelayout;
    int positionSelectedItem;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    int adsCount = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_country);

        textViewTotalCases = findViewById(R.id.textViewtotalCase);
        textViewNewCases = findViewById(R.id.textViewNewCase);
        textViewTotalDeaths = findViewById(R.id.textViewTotalDeath);
        textViewNewDeaths = findViewById(R.id.textViewNewDeath);
        textViewTotalRecovered = findViewById(R.id.textViewTotalRecovered);
        textViewActiveCases = findViewById(R.id.textViewActiveCases);
        textViewSerious = findViewById(R.id.textViewSerious);
        textViewTotalCases1Mpop = findViewById(R.id.textViewTotalCase1MPop);
        textViewTotalDeaths1Mpop = findViewById(R.id.textViewTopDeath1MPop);
        textViewTotalTest=findViewById(R.id.textViewTotalTest);
        textViewTotalTest1mpop=findViewById(R.id.textViewTotalTest1mpop);

        single_main_Relativelayout = findViewById(R.id.single_main_Relativelayout);
        imageView= findViewById(R.id.imageViewSwipe);

        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.right_to_left_anim));

        mAdView = findViewById(R.id.adViewBanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.ad_unit_id_InterstitialAd_test));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        session = new SessionManager(this);
        if(session.getSwipeView()){
            session.setSwipeView(false);
        }else {
            imageView.clearAnimation();
            imageView.setVisibility(View.INVISIBLE);
        }
        initSpinner();


        single_main_Relativelayout.setOnTouchListener(new OnSwipeTouchListener(SingleCountryActivity.this) {
            public void onSwipeTop() {
                //Toast.makeText(SingleCountryActivity.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                imageView.clearAnimation();
                imageView.setVisibility(View.INVISIBLE);

                if (positionSelectedItem == 0) {
                    spProvince.setSelection(StaticVar.rootCountry.size() - 1);
                } else {
                    spProvince.setSelection(positionSelectedItem - 1);
                }

                //Toast.makeText(SingleCountryActivity.this, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                imageView.clearAnimation();
                imageView.setVisibility(View.INVISIBLE);
                if (positionSelectedItem == StaticVar.rootCountry.size() - 1) {
                    spProvince.setSelection(0);
                } else {
                    spProvince.setSelection(positionSelectedItem + 1);
                }
                //Toast.makeText(SingleCountryActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                //Toast.makeText(SingleCountryActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });


    }


    private void initSpinner() {
        spProvince = findViewById(R.id.spinnerSmartSingleCountry);

        spProvince.setItem(StaticVar.countriesList);

        String strCountry = "Empty";
        if (StaticVar.selectedCountry != "Empty") {
            strCountry = StaticVar.selectedCountry;
        } else if (session.getSpinnerDefaultCountry() != "Empty") {
            strCountry = session.getSpinnerDefaultCountry();
        } else {
            spProvince.setSelection(StaticVar.countriesList.size() - 1);
        }

        if (strCountry != "Empty") {
            int i = 0;
            positionSelectedItem = 0;
            for (List<String> list : StaticVar.rootCountry) {
                for (String string : list) {
                    if (string.equals(strCountry)) {
                        positionSelectedItem = i;

                    }
                }
                i++;
            }

            spProvince.setSelection(positionSelectedItem);
        }

        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                if (adsCount == 9) {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                        adsCount=0;
                    }

                }
                adsCount++;

                String selectedCountry = spProvince.getSelectedItem().toString();
                positionSelectedItem = position;

                if (StaticVar.rootCountry != null) {

                    for (List<String> list : StaticVar.rootCountry) {
                        for (String string : list) {
                            if (string.equals(selectedCountry)) {
                                if (list.get(1).equals("")) {
                                    textViewTotalCases.setText("-");
                                } else {
                                    textViewTotalCases.setText(list.get(1));
                                }

                                if (list.get(2).equals("")) {
                                    textViewNewCases.setText("-");
                                } else {
                                    textViewNewCases.setText(list.get(2));
                                }

                                if (list.get(3).equals("")) {
                                    textViewTotalDeaths.setText("-");
                                } else {
                                    textViewTotalDeaths.setText(list.get(3));
                                }

                                if (list.get(4).equals("")) {
                                    textViewNewDeaths.setText("-");
                                } else {
                                    textViewNewDeaths.setText(list.get(4));
                                }

                                if (list.get(5).equals("")) {
                                    textViewTotalRecovered.setText("-");
                                } else {
                                    textViewTotalRecovered.setText(list.get(5));
                                }

                                if (list.get(6).equals("")) {
                                    textViewActiveCases.setText("-");
                                } else {
                                    textViewActiveCases.setText(list.get(6));
                                }

                                if (list.get(7).equals("")) {
                                    textViewSerious.setText("-");
                                } else {
                                    textViewSerious.setText(list.get(7));
                                }

                                if (list.get(8).equals("")) {
                                    textViewTotalCases1Mpop.setText("-");
                                } else {
                                    textViewTotalCases1Mpop.setText(list.get(8));
                                }

                                if (list.get(9).equals("")) {
                                    textViewTotalDeaths1Mpop.setText("-");
                                } else {
                                    textViewTotalDeaths1Mpop.setText(list.get(9));
                                }

                                if (list.get(10).equals("")) {
                                    textViewTotalTest.setText("-");
                                } else {
                                    textViewTotalTest.setText(list.get(10));
                                }

                                if (list.get(11).equals("")) {
                                    textViewTotalTest1mpop.setText("-");
                                } else {
                                    textViewTotalTest1mpop.setText(list.get(11));
                                }
                            }
                        }

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

}
