package com.example.nepalaya.OrderList.OrderTabs.Cancel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nepalaya.OrderList.OrderTabs.Completed.CompletedAdapter;
import com.example.nepalaya.OrderList.OrderTabs.Completed.Completedmodel;
import com.example.nepalaya.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CancelAdapter extends RecyclerView.Adapter<CancelAdapter.MyViewHolder> {

    List<CancelModel> cancelModels;

    public CancelAdapter(List<CancelModel> cancelModels) {
        this.cancelModels = cancelModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_pending, parent, false);
        return new CancelAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final CancelModel cancelModel = cancelModels.get(position);

        holder.orderid.setText(cancelModel.getOrderid());
        holder.orderdate.setText(cancelModel.getOrderDate());
        holder.customername.setText(cancelModel.getCustomerName());
        holder.tablename.setText(cancelModel.getTableName());
        holder.totalamount.setText(cancelModel.getTotalAmount());
    }

    @Override
    public int getItemCount() {
        return cancelModels.size();
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
