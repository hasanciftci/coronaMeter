package com.healthmeter.coronameter.tableview.tableviewage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractSorterViewHolder;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.healthmeter.coronameter.R;
import com.healthmeter.coronameter.tableview.holder.CellViewHolder;
import com.healthmeter.coronameter.tableview.holder.ColumnHeaderViewHolder;
import com.healthmeter.coronameter.tableview.holder.RowHeaderViewHolder;
import com.healthmeter.coronameter.tableview.model.CellModel;
import com.healthmeter.coronameter.tableview.model.ColumnHeaderModel;
import com.healthmeter.coronameter.tableview.model.RowHeaderModel;
import com.healthmeter.coronameter.tableview.tableviewcountries.TableItemCountries;
import com.healthmeter.coronameter.tableview.tableviewcountries.TableViewModelCountries;

import java.util.List;

public class TableAdapterAge extends AbstractTableAdapter<ColumnHeaderModel, RowHeaderModel,
        CellModel> {

    private TableViewModelAge tableViewModelAge;

    public TableAdapterAge(Context p_jContext) {
        super(p_jContext);

        this.tableViewModelAge = new TableViewModelAge();
    }


    @Override
    public AbstractViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        View layout;

        // Get default Cell xml Layout
        layout = LayoutInflater.from(mContext).inflate(R.layout.tableview_cell_layout,
                parent, false);

        // Create a Cell ViewHolder
        return new CellViewHolder(layout);

    }


    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object p_jValue, int
            p_nXPosition, int p_nYPosition) {
        CellModel cell = (CellModel) p_jValue;
        // Get the holder to update cell item text
        ((CellViewHolder) holder).setCellModel(cell, p_nXPosition);

    }

    @Override
    public AbstractSorterViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout
                .tableview_column_header_layout, parent, false);

        return new ColumnHeaderViewHolder(layout, getTableView());
    }


    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object p_jValue, int
            p_nXPosition) {
        ColumnHeaderModel columnHeader = (ColumnHeaderModel) p_jValue;

        // Get the holder to update cell item text
        ColumnHeaderViewHolder columnHeaderViewHolder = (ColumnHeaderViewHolder) holder;

        columnHeaderViewHolder.setColumnHeaderModel(columnHeader, p_nXPosition);
    }

    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {

        // Get Row Header xml Layout
        View layout = LayoutInflater.from(mContext).inflate(R.layout.tableview_row_header_layout,
                parent, false);

        // Create a Row Header ViewHolder
        return new RowHeaderViewHolder(layout);
    }


    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object p_jValue, int
            p_nYPosition) {

        RowHeaderModel rowHeaderModel = (RowHeaderModel) p_jValue;

        RowHeaderViewHolder rowHeaderViewHolder = (RowHeaderViewHolder) holder;
        rowHeaderViewHolder.row_header_textview.setText(rowHeaderModel.getData());

    }

    @Override
    public View onCreateCornerView() {
        return LayoutInflater.from(mContext).inflate(R.layout.tableview_corner_layout, null, false);
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCellItemViewType(int position) {
        return tableViewModelAge.getCellItemViewType(position);
    }


    /**
     * This method is not a generic Adapter method. It helps to generate lists from single user
     * list for this adapter.
     */
    public void setTableItemList(List<TableItemAge> tableItemAgeList) {
        // Generate the lists that are used to TableViewAdapter
        tableViewModelAge.generateListForTableView(tableItemAgeList);

        // Now we got what we need to show on TableView.
        setAllItems(tableViewModelAge.getColumHeaderModeList(), tableViewModelAge
                .getRowHeaderModelList(), tableViewModelAge.getCellModelList());
    }

}
