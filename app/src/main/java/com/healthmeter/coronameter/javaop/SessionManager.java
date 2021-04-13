package com.healthmeter.coronameter.javaop;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {


    private final SharedPreferences prefs;

    public SessionManager(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setSpinnerDefaultCountry(String selectedCountry) {
        prefs.edit().putString("spinnerDefaultCountry", selectedCountry).apply();
    }

    public void setSpinnerDefaultLanguage(String selectedLanguage) {
        prefs.edit().putString("spinnerDefaultLanguage", selectedLanguage).apply();
    }

    public String  getSpinnerDefaultCountry() {
        return prefs.getString("spinnerDefaultCountry","Empty");
    }

    public String  getSpinnerDefaultLanguage() {
        return prefs.getString("spinnerDefaultLanguage","Empty");
    }

    public void setSwipeView(Boolean isShow) {
        prefs.edit().putBoolean("swipeView", isShow).apply();
    }

    public Boolean  getSwipeView() {
        return prefs.getBoolean("swipeView",true);
    }

}