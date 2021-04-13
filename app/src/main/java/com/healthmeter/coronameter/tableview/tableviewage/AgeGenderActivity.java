package com.healthmeter.coronameter.tableview.tableviewage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.healthmeter.coronameter.R;
import com.healthmeter.coronameter.javaop.SessionManager;
import com.healthmeter.coronameter.javaop.StaticVar;
import com.evrencoskun.tableview.TableView;
import com.healthmeter.coronameter.tableview.tableviewgender.TableAdapterGender;
import com.healthmeter.coronameter.tableview.tableviewgender.TableItemGender;
import com.healthmeter.coronameter.tableview.tableviewgender.TableViewListenerGender;
import com.healthmeter.coronameter.tableview.tableviewprecon.TableAdapterPreCon;
import com.healthmeter.coronameter.tableview.tableviewprecon.TableItemPreCon;
import com.healthmeter.coronameter.tableview.tableviewprecon.TableViewListenerPreCon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class AgeGenderActivity extends AppCompatActivity {

    private TableView mTableViewAge;
    private TableAdapterAge mTableAdapterAge;
    private TableView mTableViewGender;
    private TableAdapterGender mTableAdapterGender;
    private TableView mTableViewPreCon;
    private TableAdapterPreCon mTableAdapterPreCon;
    private ProgressBar mProgressBarAge;

    private AdView mAdView;
    private SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_gender);
        mTableViewAge = findViewById(R.id.my_TableViewAge);
        mTableViewGender = findViewById(R.id.my_TableViewGender);
        mTableViewPreCon = findViewById(R.id.my_TableViewPreCon);

        mProgressBarAge = findViewById(R.id.progressBarAge);
        session = new SessionManager(this);


        mAdView = findViewById(R.id.adViewBanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        showProgressBar();

        List<TableItemAge> tableItemAgeList = new ArrayList<>();

        if (StaticVar.rootAge != null) {
            for (List<String> list : StaticVar.rootAge) {
                TableItemAge tableItemAge = new TableItemAge();
                tableItemAge.age = list.get(0);
                tableItemAge.death_rate_cc = list.get(1);
                if (Locale.getDefault().getLanguage().equals("tr") || session.getSpinnerDefaultLanguage().equals("Türkçe")) {
                    if (list.get(2).equals("no fatalities")) {
                        tableItemAge.death_rate_ac = "0";
                    } else {
                        tableItemAge.death_rate_ac = list.get(2);
                    }
                } else {
                    tableItemAge.death_rate_ac = list.get(2);
                }

                // add
                tableItemAgeList.add(tableItemAge);
            }
        }

        initializeTableView(mTableViewAge);
        mTableAdapterAge.setTableItemList(tableItemAgeList);


        List<TableItemGender> tableItemGenderList = new ArrayList<>();

        if (StaticVar.rootGender != null) {
            for (List<String> list : StaticVar.rootGender) {
                TableItemGender tableItemGender = new TableItemGender();
                if (Locale.getDefault().getLanguage().equals("tr") || session.getSpinnerDefaultLanguage().equals("Türkçe")) {
                    if (list.get(0).equals("Male")) {
                        tableItemGender.gender = "Erkek";
                    } else if (list.get(0).equals("Female")) {
                        tableItemGender.gender = "Kadın";
                    } else {
                        tableItemGender.gender = list.get(0);
                    }
                } else {
                    tableItemGender.gender = list.get(0);
                }

                tableItemGender.death_rate_cc = list.get(1);
                tableItemGender.death_rate_ac = list.get(2);
                // add
                tableItemGenderList.add(tableItemGender);
            }
        }

        initializeTableViewGender(mTableViewGender);
        mTableAdapterGender.setTableItemList(tableItemGenderList);

        List<TableItemPreCon> tableItemPreConList = new ArrayList<>();

        if (StaticVar.rootPreCon != null) {
            for (List<String> list : StaticVar.rootPreCon) {
                TableItemPreCon tableItemPreCon = new TableItemPreCon();
                if (Locale.getDefault().getLanguage().equals("tr") || session.getSpinnerDefaultLanguage().equals("Türkçe")) {
                    if (list.get(0).equals("Cardiovascular disease")) {
                        tableItemPreCon.pre_con = "Kalp-Damar Hastalığı";
                    } else if (list.get(0).equals("Diabetes")) {
                        tableItemPreCon.pre_con = "Diyabet";
                    } else if (list.get(0).equals("Chronic respiratory disease")) {
                        tableItemPreCon.pre_con = "Kronik Solunum Yolu Hastalıkları";
                    } else if (list.get(0).equals("Hypertension")) {
                        tableItemPreCon.pre_con = "Hipertansiyon";
                    } else if (list.get(0).equals("Cancer")) {
                        tableItemPreCon.pre_con = "Kanser";
                    } else if (list.get(0).equals("no pre-existing conditions")) {
                        tableItemPreCon.pre_con = "Önceden Hastalığı Yok";
                    } else {
                        tableItemPreCon.pre_con = list.get(0);
                    }
                } else {
                    tableItemPreCon.pre_con = list.get(0);
                }
                tableItemPreCon.death_rate_cc = list.get(1);
                tableItemPreCon.death_rate_ac = list.get(2);
                // add
                tableItemPreConList.add(tableItemPreCon);
            }
        }

        initializeTableViewPreCon(mTableViewPreCon);
        mTableAdapterPreCon.setTableItemList(tableItemPreConList);

        hideProgressBar();
    }

    private void initializeTableView(TableView tableView) {

        // Create TableView Adapter
        mTableAdapterAge = new TableAdapterAge(this);
        tableView.setAdapter(mTableAdapterAge);

        // Create listener
        tableView.setTableViewListener(new TableViewListenerAge(tableView));
    }

    private void initializeTableViewGender(TableView tableView) {

        // Create TableView Adapter
        mTableAdapterGender = new TableAdapterGender(this);
        tableView.setAdapter(mTableAdapterGender);

        // Create listener
        tableView.setTableViewListener(new TableViewListenerGender(tableView));
    }

    private void initializeTableViewPreCon(TableView tableView) {

        // Create TableView Adapter
        mTableAdapterPreCon = new TableAdapterPreCon(this);
        tableView.setAdapter(mTableAdapterPreCon);

        // Create listener
        tableView.setTableViewListener(new TableViewListenerPreCon(tableView));
    }

    public void showProgressBar() {
        mProgressBarAge.setVisibility(View.VISIBLE);
        mTableViewAge.setVisibility(View.INVISIBLE);
    }

    public void hideProgressBar() {
        mProgressBarAge.setVisibility(View.INVISIBLE);
        mTableViewAge.setVisibility(View.VISIBLE);
    }
}
