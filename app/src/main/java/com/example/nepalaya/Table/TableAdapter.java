package com.example.nepalaya.Table;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nepalaya.R;
import com.example.nepalaya.Table.Listener.OnClickListener;

import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.MYViewHolder> implements Filterable {


    ArrayList<TableModel> tableModels, tableModelsfilter;
    Context context;
    TableFilter tableFilter;
    OnClickListener listener;

    public TableAdapter(ArrayList<TableModel> tableModels, Context context, OnClickListener listener) {
        this.tableModels = tableModels;
        this.tableModelsfilter = tableModels;
        this.context = context;
        this.listener = listener;

    }

    @NonNull
    @Override
    public MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_table, parent, false);
        return new MYViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MYViewHolder holder, int position) {
        final TableModel tableModel = tableModels.get(position);
        holder.tablename.setText(tableModel.getTable_name());


        holder.onclicklayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String table_id = tableModel.getTable_id();
                String table_name = tableModel.getTable_name();
                String table_location = tableModel.getTable_location();
                getDetail(table_id, table_name, table_location);

            }
        });

    }

    public void getDetail(String table_id, String table_name, String table_location) {
        listener.onItemClick(table_id, table_name, table_location);

    }

    @Override
    public int getItemCount() {
        return tableModels.size();
    }

    @Override
    public Filter getFilter() {

        if (tableFilter == null) {
            tableFilter = new TableFilter(this, tableModelsfilter);
        }

        return tableFilter;
    }


    public class MYViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.table_name_desc)
        TextView tablename;

        @BindView(R.id.clicklayer_table)
        CardView onclicklayer;

        public MYViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
