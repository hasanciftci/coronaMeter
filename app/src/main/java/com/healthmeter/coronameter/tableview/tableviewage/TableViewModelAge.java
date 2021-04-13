package com.healthmeter.coronameter.tableview.tableviewage;

import android.view.Gravity;

import com.healthmeter.coronameter.R;
import com.healthmeter.coronameter.javaop.MyAppContext;
import com.healthmeter.coronameter.tableview.model.CellModel;
import com.healthmeter.coronameter.tableview.model.ColumnHeaderModel;
import com.healthmeter.coronameter.tableview.model.RowHeaderModel;
import com.healthmeter.coronameter.tableview.tableviewcountries.TableItemCountries;

import java.util.ArrayList;
import java.util.List;

public class TableViewModelAge {
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
            case 1:
                return Gravity.CENTER;
            case 2:
                return Gravity.CENTER;
            default:
                return Gravity.CENTER;
        }

    }

    private List<ColumnHeaderModel> createColumnHeaderModelList() {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_age_age)));
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_age_death_rate_CC)));
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_age_death_rate_AC)));

        return list;
    }

    private List<List<CellModel>> createCellModelList(List<TableItemAge> tableItemAgeList) {
        List<List<CellModel>> lists = new ArrayList<>();

        for (int i = 0; i < tableItemAgeList.size(); i++) {
            TableItemAge tableItemAge = tableItemAgeList.get(i);

            List<CellModel> list = new ArrayList<>();

            // The order should be same with column header list;
            list.add(new CellModel("1-" + i, tableItemAge.age));
            list.add(new CellModel("2-" + i, tableItemAge.death_rate_cc));
            list.add(new CellModel("3-" + i, tableItemAge.death_rate_ac));

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


    public void generateListForTableView(List<TableItemAge> tableItemAge) {
        mColumnHeaderModelList = createColumnHeaderModelList();
        mCellModelList = createCellModelList(tableItemAge);
        mRowHeaderModelList = createRowHeaderList(tableItemAge.size());
    }

}



