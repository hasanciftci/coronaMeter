package com.healthmeter.coronameter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.healthmeter.coronameter.javaop.CheckNetworkConnection;
import com.healthmeter.coronameter.javaop.SessionManager;
import com.healthmeter.coronameter.javaop.StaticVar;
import com.healthmeter.coronameter.tableview.tableviewage.AgeGenderActivity;
import com.healthmeter.coronameter.tableview.tableviewcountries.CountriesTableActivity;


import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/*Ana kod
        ca-app-pub-4857677240863208~1521758943

        Yerel Gelişmiş
        ca-app-pub-4857677240863208/7181279732

        main banner
        ca-app-pub-4857677240863208/7620548843

        Main InterstitialAd
        ca-app-pub-4857677240863208/7401605733

        test reklamları
        ca-app-pub-3940256099942544/2247696110*/

public class MainActivity extends AppCompatActivity {

    private TextView totalCase;
    private TextView deaths;
    private TextView recovered;
    private SessionManager session;
    private InterstitialAd mInterstitialAdMainPage;
    int adsCount;
    private String message;
    SmartMaterialSpinner spProvince;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(this);
        String languageSelected="not-set";
        if(!session.getSpinnerDefaultLanguage().equals("Empty")) {
            if (session.getSpinnerDefaultLanguage().equals("Use Phone Language")) {
                languageSelected = "UPL";
            }
            if (session.getSpinnerDefaultLanguage().equals("Türkçe")) {
                languageSelected = "tr";
            }
            if (session.getSpinnerDefaultLanguage().equals("English")) {
                languageSelected = "en";
            }
            if (session.getSpinnerDefaultLanguage().equals("Deutsche")) {
                languageSelected = "de";
            }
            if (session.getSpinnerDefaultLanguage().equals("Italiano")) {
                languageSelected = "it";
            }
            if (session.getSpinnerDefaultLanguage().equals("Español")) {
                languageSelected = "es";
            }
            if (session.getSpinnerDefaultLanguage().equals("Français")) {
                languageSelected = "fr";
            }
            setLanguageForApp(languageSelected);
        }

        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        totalCase = findViewById(R.id.total_cases);
        deaths = findViewById(R.id.deaths);
        recovered = findViewById(R.id.recovered);
        TextView textViewLive = findViewById(R.id.textViewLive);
        textViewLive.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_fade_out_anim));

        CheckNetworkConnection checkNetworkConnection = new CheckNetworkConnection();
        checkNetworkConnection.checkConnection(this);


        if (!StaticVar.isNetworkConnected) {
            Toast.makeText(this, getResources().getString(R.string.no_network_connection), Toast.LENGTH_LONG).show();
            totalCase.setText("No");
            deaths.setText("Network");
            recovered.setText("Connection!");
        }

        retrieveDataFromFireBase();

        goActivity();
//////////////////////////////////////ADS/////////////////////////////////////////////////////////////////////////////////
        mInterstitialAdMainPage = new InterstitialAd(getApplicationContext());
        mInterstitialAdMainPage.setAdUnitId(getResources().getString(R.string.ad_unit_id_InterstitialAd_test));
        mInterstitialAdMainPage.loadAd(new AdRequest.Builder().build());

        mInterstitialAdMainPage.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAdMainPage.loadAd(new AdRequest.Builder().build());
            }

        });


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private void setLanguageForApp(String languageToLoad) {
        Locale locale;
        if (languageToLoad.equals("not-set")) { //use any value for default
            locale = Locale.getDefault();
        } else if(languageToLoad.equals("UPL")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = Resources.getSystem().getConfiguration().getLocales().get(0);
            }else {
                locale = Resources.getSystem().getConfiguration().locale;
            }
        } else {
            locale = new Locale(languageToLoad);
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private void goActivity() {

        Button clickCountryDetailTable = findViewById(R.id.btnCdt);

        clickCountryDetailTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticVar.isNetworkConnected) {
                    if (StaticVar.rootCountry.size() != 0) {
                        Intent i = new Intent(MainActivity.this, SingleCountryActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.data_not_ready_yet), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network_connection), Toast.LENGTH_LONG).show();
                }
            }
        });


        Button clickAllCountriesTable = findViewById(R.id.btnACT);

        clickAllCountriesTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticVar.isNetworkConnected) {
                    Intent i = new Intent(MainActivity.this, CountriesTableActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network_connection), Toast.LENGTH_LONG).show();
                }
            }
        });

        Button clickWorldVirusMapWHO = findViewById(R.id.btnWHO);

        clickWorldVirusMapWHO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticVar.isNetworkConnected) {
                    Intent i = new Intent(MainActivity.this, WorldMapWHOActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network_connection), Toast.LENGTH_LONG).show();
                }
            }
        });

        Button clickWorldVirusMapJHU = findViewById(R.id.btnJHU);

        clickWorldVirusMapJHU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticVar.isNetworkConnected) {
                    Intent i = new Intent(MainActivity.this, WorldMapJHUActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network_connection), Toast.LENGTH_LONG).show();
                }
            }
        });

        Button clickAgeGenderStats = findViewById(R.id.btnAGS);

        clickAgeGenderStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticVar.isNetworkConnected) {
                    Intent i = new Intent(MainActivity.this, AgeGenderActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network_connection), Toast.LENGTH_LONG).show();
                }
            }
        });

        Button clickReferences = findViewById(R.id.btnRef);

        clickReferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticVar.isNetworkConnected) {
                    Intent i = new Intent(MainActivity.this, ReferencesActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network_connection), Toast.LENGTH_LONG).show();
                }
            }
        });

        Button clickSettings = findViewById(R.id.btnSetting);

        clickSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticVar.isNetworkConnected) {
                    Intent i = new Intent(MainActivity.this, SettingsActivityMy.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network_connection), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initSpinner() {
        spProvince = findViewById(R.id.spinnerSmart);

        spProvince.setItem(StaticVar.countriesList);
        String strCountry = "Empty";
        if (StaticVar.selectedCountry != "Empty") {
            strCountry = StaticVar.selectedCountry;
        } else if (session.getSpinnerDefaultCountry() != "Empty") {
            strCountry = session.getSpinnerDefaultCountry();
        } else {
            spProvince.setSelection(0);
        }
//mySpinner.setSelection(((ArrayAdapter)mySpinner.getAdapter()).getPosition("Value"))
        if (strCountry != "Empty") {
            int i = 0;
            int positionSelectedItem = 0;
            for (List<String> list : StaticVar.rootCountry) {
                for (String string : list) {
                    if (string.equals(strCountry)) {
                        positionSelectedItem = i;
                        break;
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
                    if (mInterstitialAdMainPage.isLoaded()) {
                        mInterstitialAdMainPage.show();
                        adsCount = 0;
                    }
                }
                adsCount++;

                String selectedCountry = spProvince.getSelectedItem().toString();

                StaticVar.selectedCountry = selectedCountry;

                if (StaticVar.rootCountry != null) {

                    for (List<String> list : StaticVar.rootCountry) {
                        for (String string : list) {
                            if (string.equals(selectedCountry)) {
                                if (list.get(1).equals("")) {
                                    totalCase.setText("-");
                                } else {
                                    totalCase.setText(list.get(1));
                                }

                                if (list.get(3).equals("")) {
                                    deaths.setText("-");
                                } else {
                                    deaths.setText(list.get(3));
                                }

                                if (list.get(5).equals("")) {
                                    recovered.setText("-");
                                } else {
                                    recovered.setText(list.get(5));
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

    @Override
    protected void onResume() {
        super.onResume();
        if (StaticVar.initSpinnerCase == 1) {
            initSpinner();
            StaticVar.initSpinnerCase = 0;
        }
        if(StaticVar.fromLangSelected==1){
            StaticVar.fromLangSelected=0;
            StaticVar.languageSelected = "Empty";
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    private void retrieveDataFromFireBase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Countries");
        //attaching value event listener
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iterating through all the nodes
                StaticVar.isNetworkConnected = true;
                StaticVar.rootCountry.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                    };
                    List<String> arr = postSnapshot.getValue(t);
                    StaticVar.rootCountry.add(arr);
                }

                if (StaticVar.rootCountry != null) {
                    StaticVar.countriesList.clear();
                    for (List<String> list : StaticVar.rootCountry) {
                        StaticVar.countriesList.add(list.get(0));
                    }
                }

                initSpinner();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        myRef = database.getReference("AgeStatics");
        //attaching value event listener
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iterating through all the nodes
                StaticVar.rootAge.clear();
                for (DataSnapshot postSnapshot : Objects.requireNonNull(dataSnapshot).getChildren()) {
                    GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                    };
                    List<String> arr = postSnapshot.getValue(t);
                    StaticVar.rootAge.add(arr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef = database.getReference("GenderStatics");
        //attaching value event listener
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iterating through all the nodes
                StaticVar.rootGender.clear();
                for (DataSnapshot postSnapshot : Objects.requireNonNull(dataSnapshot).getChildren()) {
                    GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                    };
                    List<String> arr = postSnapshot.getValue(t);
                    StaticVar.rootGender.add(arr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef = database.getReference("PreConditionStat");
        //attaching value event listener
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iterating through all the nodes
                StaticVar.rootPreCon.clear();
                for (DataSnapshot postSnapshot : Objects.requireNonNull(dataSnapshot).getChildren()) {
                    GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                    };
                    List<String> arr = postSnapshot.getValue(t);
                    StaticVar.rootPreCon.add(arr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef = database.getReference("Who");
        //attaching value event listener
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iterating through all the nodes
                StaticVar.urlWHO = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef = database.getReference("Jhu");
        //attaching value event listener
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iterating through all the nodes
                StaticVar.urlJHU = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef = database.getReference("Message");
        //attaching value event listener
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iterating through all the nodes
                message = dataSnapshot.getValue(String.class);

                if (!message.equals("NoProblem")) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final RelativeLayout relativeLayoutADV = findViewById(R.id.relativeLayoutADV);

        myRef = database.getReference("Ads");
        //attaching value event listener
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iterating through all the nodes
                if (Objects.equals(dataSnapshot.getValue(String.class), "Ok")) {

                    AdView mAdView = new AdView(getApplicationContext());
                    RelativeLayout.LayoutParams params = new RelativeLayout
                            .LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    mAdView.setLayoutParams(params);
                    mAdView.setAdSize(AdSize.SMART_BANNER);
                    mAdView.setAdUnitId(getResources().getString(R.string.ad_unit_id_banner_test));

                    relativeLayoutADV.addView(mAdView);

                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);

                } else {
                    relativeLayoutADV.removeAllViews();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
