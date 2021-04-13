package com.healthmeter.coronameter.tableview.tableviewcountries;

import android.view.Gravity;

import com.healthmeter.coronameter.R;
import com.healthmeter.coronameter.javaop.MyAppContext;
import com.healthmeter.coronameter.tableview.model.CellModel;
import com.healthmeter.coronameter.tableview.model.ColumnHeaderModel;
import com.healthmeter.coronameter.tableview.model.RowHeaderModel;

import java.util.ArrayList;
import java.util.List;

public class TableViewModelCountries {
    // View Types

    private List<ColumnHeaderModel> mColumnHeaderModelList;
    private List<RowHeaderModel> mRowHeaderModelList;
    private List<List<CellModel>> mCellModelList;

    public int getCellItemViewType(int column) {
        return 0;
    }

    public int getColumnTextAlign(int column) {
        switch (column) {
            case 0:
                return Gravity.LEFT;
            default:
                return Gravity.CENTER;
        }

    }

    private List<ColumnHeaderModel> createColumnHeaderModelList() {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_country_country)));
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_country_total_case)));
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_country_New_Case)));
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_country_Total_Death)));
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_country_New_Death)));
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_country_Active_Case)));
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_country_Total_Recovered)));
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_country_Serious_Critical)));
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_country_TopCases_1MPop)));
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_country_TopDeaths_1MPop)));
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_country_Total_tests)));
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_country_tottest_1mpop)));

        return list;
    }

    private List<List<CellModel>> createCellModelList(List<TableItemCountries> tableItemCountriesList) {
        List<List<CellModel>> lists = new ArrayList<>();

        for (int i = 0; i < tableItemCountriesList.size(); i++) {
            TableItemCountries tableItemCountries = tableItemCountriesList.get(i);

            List<CellModel> list = new ArrayList<>();

            // The order should be same with column header list;
            list.add(new CellModel("1-" + i, tableItemCountries.country));
            list.add(new CellModel("2-" + i, tableItemCountries.total_case));
            list.add(new CellModel("3-" + i, tableItemCountries.new_case));
            list.add(new CellModel("4-" + i, tableItemCountries.total_death));
            list.add(new CellModel("5-" + i, tableItemCountries.new_death));
            list.add(new CellModel("6-" + i, tableItemCountries.active_case));
            list.add(new CellModel("7-" + i, tableItemCountries.total_recovered));
            list.add(new CellModel("8-" + i, tableItemCountries.serious_critical));
            list.add(new CellModel("9-" + i, tableItemCountries.topcases_1mpop));
            list.add(new CellModel("10-" + i, tableItemCountries.topdeaths_1mpop));
            list.add(new CellModel("11-" + i, tableItemCountries.total_tests));
            list.add(new CellModel("12-" + i, tableItemCountries.tottests_1mpop));

            // Add
            lists.add(list);
        }

        return lists;
    }

    private List<RowHeaderModel> createRowHeaderList(int size) {
        List<RowHeaderModel> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            // In this example, Row headers just shows the index of the TableView List.
            list.add(new RowHeaderModel(String.valueOf(i + 1)));
        }
        return list;
    }


    public List<ColumnHeaderModel> getColumHeaderModeList() {
        return mColumnHeaderModelList;
    }

    public List<RowHeaderModel> getRowHeaderModelList() {
        return mRowHeaderModelList;
    }

    public List<List<CellModel>> getCellModelList() {
        return mCellModelList;
    }


    public void generateListForTableView(List<TableItemCountries> tableItemCountries) {
        mColumnHeaderModelList = createColumnHeaderModelList();
        mCellModelList = createCellModelList(tableItemCountries);
        mRowHeaderModelList = createRowHeaderList(tableItemCountries.size());
    }

}



