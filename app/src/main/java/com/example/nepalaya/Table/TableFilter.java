package com.example.nepalaya.Table;

import android.widget.Filter;
import android.widget.Filterable;


import java.util.ArrayList;
import java.util.List;

public class TableFilter extends Filter {

    TableAdapter tableAdapter;
    List<TableModel> filterlist;

    public TableFilter(TableAdapter tableAdapter, ArrayList<TableModel> filterlist) {
        this.tableAdapter = tableAdapter;
        this.filterlist = filterlist;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {

        FilterResults results = new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if (charSequence != null && charSequence.length() > 0) {
            //CHANGE TO UPPER
            charSequence = charSequence.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<TableModel> filteredDate = new ArrayList<>();

            for (int i = 0; i < filterlist.size(); i++) {
                //CHECK
                if (filterlist.get(i).getTable_name().toUpperCase().contains(charSequence)) {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredDate.add(filterlist.get(i));
                }
            }

            results.count = filteredDate.size();
            results.values = filteredDate;
        } else {
            results.count = filterlist.size();
            results.values = filterlist;

        }
        return results;

    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        // adapter.contactUsModels = (ArrayList<BivagcatMiddle>) filterResults.values;
        tableAdapter.tableModels = (ArrayList<TableModel>) filterResults.values;

        //REFRESH
        tableAdapter.notifyDataSetChanged();
    }
}
