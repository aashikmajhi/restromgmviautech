package com.example.nepalaya.OrderList.OrderTabs.Completed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nepalaya.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompletedAdapter extends RecyclerView.Adapter<CompletedAdapter.MyViewHolder> {

    List<Completedmodel> completedmodels;

    public CompletedAdapter(List<Completedmodel> completedmodels) {
        this.completedmodels = completedmodels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_pending, parent, false);
        return new CompletedAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Completedmodel completedmodel = completedmodels.get(position);

        holder.orderid.setText(completedmodel.getOrderid());
        holder.orderdate.setText(completedmodel.getOrderDate());
        holder.customername.setText(completedmodel.getCustomerName());
        holder.tablename.setText(completedmodel.getTableName());
        holder.totalamount.setText(completedmodel.getTotalAmount());
    }

    @Override
    public int getItemCount() {
        return completedmodels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pen_order_id)
        TextView orderid;

        @BindView(R.id.pen_CustomerName)
        TextView customername;

        @BindView(R.id.pen_TableName)
        TextView tablename;

        @BindView(R.id.pen_OrderDate)
        TextView orderdate;

        @BindView(R.id.pen_TotalAmount)
        TextView totalamount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
