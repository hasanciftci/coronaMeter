package com.healthmeter.coronameter.tableview.tableviewcountries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.healthmeter.coronameter.R;
import com.healthmeter.coronameter.javaop.SessionManager;
import com.healthmeter.coronameter.javaop.StaticVar;
import com.evrencoskun.tableview.TableView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CountriesTableActivity extends AppCompatActivity {

    private TableView mTableViewCountries;
    private ProgressBar mProgressBarCountries;
    private TableAdapterCountries mTableAdapterCountries;

    private AdView mAdView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries_table);
        mTableViewCountries = findViewById(R.id.my_TableViewCountries);
        mProgressBarCountries = findViewById(R.id.progressBarCountries);

        mAdView = findViewById(R.id.adViewBanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        showProgressBar();

        List<TableItemCountries> tableItemCountriesList = new ArrayList<>();

        if (StaticVar.rootCountry != null) {
            for (List<String> list : StaticVar.rootCountry) {
                TableItemCountries tableItemCountries = new TableItemCountries();
                tableItemCountries.country = list.get(0);
                tableItemCountries.total_case = list.get(1);
                tableItemCountries.new_case = list.get(2);
                tableItemCountries.total_death = list.get(3);
                tableItemCountries.new_death = list.get(4);
                tableItemCountries.total_recovered = list.get(5);
                tableItemCountries.active_case = list.get(6);
                tableItemCountries.serious_critical = list.get(7);
                tableItemCountries.topcases_1mpop = list.get(8);
                tableItemCountries.topdeaths_1mpop = list.get(9);
                tableItemCountries.total_tests = list.get(10);
                tableItemCountries.tottests_1mpop = list.get(11);
                // add
                tableItemCountriesList.add(tableItemCountries);
            }
        }

        initializeTableView(mTableViewCountries);
        mTableAdapterCountries.setTableItemList(tableItemCountriesList);

        hideProgressBar();
    }

    private void initializeTableView(TableView tableView) {

        // Create TableView Adapter
        mTableAdapterCountries = new TableAdapterCountries(this);
        tableView.setAdapter(mTableAdapterCountries);

        // Create listener
        tableView.setTableViewListener(new TableViewListenerCountries(tableView));
    }

    public void showProgressBar() {
        mProgressBarCountries.setVisibility(View.VISIBLE);
        mTableViewCountries.setVisibility(View.INVISIBLE);
    }

    public void hideProgressBar() {
        mProgressBarCountries.setVisibility(View.INVISIBLE);
        mTableViewCountries.setVisibility(View.VISIBLE);
    }
}
