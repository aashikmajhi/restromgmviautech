package com.example.nepalaya.OrderList.OrderTabs.Pending;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nepalaya.CategoryList.FoodList.FoodListAdapter;
import com.example.nepalaya.R;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.MyViewHolder> {

    List<PendingModel> pendingModels;
    PendingListener pendingListener;


    public PendingAdapter(List<PendingModel> pendingModels, PendingListener pendingListener) {
        this.pendingModels = pendingModels;
        this.pendingListener = pendingListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_pending, parent, false);
        return new PendingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final PendingModel pendingModel = pendingModels.get(position);


        holder.orderid.setText(pendingModel.getOrderid());
        holder.orderdate.setText(pendingModel.getOrderDate());
        holder.customername.setText(pendingModel.getCustomerName());
        holder.tablename.setText(pendingModel.getTableName());
        holder.totalamount.setText(pendingModel.getTotalAmount());

        holder.viewdetaileye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Detail(Integer.parseInt(pendingModel.getOrderid()), Integer.parseInt(pendingModel.getTableName()));
            }
        });


    }

    public void Detail(int orderid, int tableid) {
        pendingListener.PendingListener(orderid, tableid);
    }

    @Override
    public int getItemCount() {
        return pendingModels.size();
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

        @BindView(R.id.pen_viewdetaileye)
        ImageView viewdetaileye;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
