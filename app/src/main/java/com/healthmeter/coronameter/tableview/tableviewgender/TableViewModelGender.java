package com.healthmeter.coronameter.tableview.tableviewgender;

import android.view.Gravity;

import com.healthmeter.coronameter.R;
import com.healthmeter.coronameter.javaop.MyAppContext;
import com.healthmeter.coronameter.tableview.model.CellModel;
import com.healthmeter.coronameter.tableview.model.ColumnHeaderModel;
import com.healthmeter.coronameter.tableview.model.RowHeaderModel;
import com.healthmeter.coronameter.tableview.tableviewgender.TableItemGender;

import java.util.ArrayList;
import java.util.List;

public class TableViewModelGender {
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
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_gender_gender)));
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_age_death_rate_CC)));
        list.add(new ColumnHeaderModel(MyAppContext.getAppContext().getString(R.string.tableview_age_death_rate_AC)));

        return list;
    }

    private List<List<CellModel>> createCellModelList(List<TableItemGender> tableItemGenderList) {
        List<List<CellModel>> lists = new ArrayList<>();

        for (int i = 0; i < tableItemGenderList.size(); i++) {
            TableItemGender tableItemGender = tableItemGenderList.get(i);

            List<CellModel> list = new ArrayList<>();

            // The order should be same with column header list;
            list.add(new CellModel("1-" + i, tableItemGender.gender));
            list.add(new CellModel("2-" + i, tableItemGender.death_rate_cc));
            list.add(new CellModel("3-" + i, tableItemGender.death_rate_ac));

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


    public void generateListForTableView(List<TableItemGender> tableItemGender) {
        mColumnHeaderModelList = createColumnHeaderModelList();
        mCellModelList = createCellModelList(tableItemGender);
        mRowHeaderModelList = createRowHeaderList(tableItemGender.size());
    }

}



