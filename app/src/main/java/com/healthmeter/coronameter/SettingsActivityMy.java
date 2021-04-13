package com.healthmeter.coronameter;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.healthmeter.coronameter.javaop.SessionManager;
import com.healthmeter.coronameter.javaop.StaticVar;

import java.util.Locale;

public class SettingsActivityMy extends AppCompatActivity {

    private SmartMaterialSpinner spProvinceCountry;
    private SmartMaterialSpinner spProvinceLanguage;
    private SessionManager session;
    private Button btnContactUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!StaticVar.languageSelected.equals("Empty")) {
            setLanguageForApp(StaticVar.languageSelected);
        }
        setContentView(R.layout.settings_activity_my);
        btnContactUs=findViewById(R.id.btnContactUs);
        btnContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "coronameter19@gmail.com", null));
                startActivity(intent);
            }
        });

        session = new SessionManager(this);
        initSpinnerCountry();
        initSpinnerLanguage();
    }

    private void initSpinnerCountry() {
        spProvinceCountry = findViewById(R.id.spinnerSmartSettingsCountry);

        spProvinceCountry.setItem(StaticVar.countriesList);

        if (session.getSpinnerDefaultCountry().equals("Empty"))
            spProvinceCountry.setSelection(-1);
        else {
            int i = 0;
            int positionSelectedItem = 0;
            for (String string : StaticVar.countriesList) {
                if (string.equals(session.getSpinnerDefaultCountry())) {
                    positionSelectedItem = i;
                }
                i++;
            }
            spProvinceCountry.setSelection(positionSelectedItem);
        }

        spProvinceCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                String selectedCountry = spProvinceCountry.getSelectedItem().toString();
                session.setSpinnerDefaultCountry(selectedCountry);
                StaticVar.selectedCountry = selectedCountry;
                StaticVar.initSpinnerCase = 1;
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
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

    private void initSpinnerLanguage() {

        spProvinceLanguage = findViewById(R.id.spinnerSmartSettingsLanguage);

        spProvinceLanguage.setItem(StaticVar.languageList);

        if (session.getSpinnerDefaultLanguage().equals("Empty"))
            spProvinceLanguage.setSelection(-1);
        else {
            spProvinceLanguage.setSelection(((ArrayAdapter)spProvinceLanguage.getAdapter()).getPosition(session.getSpinnerDefaultLanguage()));
            //spProvinceLanguage.setSelection(5);
/*            int i = 0;
            int positionSelectedItem = 0;
            for (String string : StaticVar.languageList) {
                if (string.equals(session.getSpinnerDefaultLanguage())) {
                    positionSelectedItem = i;
                }
                i++;
            }
            spProvinceLanguage.setSelection(positionSelectedItem);*/
        }

        spProvinceLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                String selectedLanguage = spProvinceLanguage.getSelectedItem().toString();

                if (!session.getSpinnerDefaultLanguage().equals(selectedLanguage)) {
                    if (selectedLanguage.equals("Use Phone Language")) {
                        StaticVar.languageSelected = "UPL";
                    }
                    if (selectedLanguage.equals("Türkçe")) {
                        StaticVar.languageSelected = "tr";
                    }
                    if (selectedLanguage.equals("English")) {
                        StaticVar.languageSelected = "en";
                    }
                    if (selectedLanguage.equals("Deutsche")) {
                        StaticVar.languageSelected = "de";
                    }
                    if (selectedLanguage.equals("Italiano")) {
                        StaticVar.languageSelected = "it";
                    }
                    if (selectedLanguage.equals("Español")) {
                        StaticVar.languageSelected = "es";
                    }
                    if (selectedLanguage.equals("Français")) {
                        StaticVar.languageSelected = "fr";
                    }
                    session.setSpinnerDefaultLanguage(selectedLanguage);
                    StaticVar.fromLangSelected=1;
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}