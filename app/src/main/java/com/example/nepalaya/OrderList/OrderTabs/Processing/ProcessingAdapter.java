package com.example.nepalaya.OrderList.OrderTabs.Processing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nepalaya.OrderList.OrderTabs.Pending.PendingListener;

import com.example.nepalaya.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProcessingAdapter extends RecyclerView.Adapter<ProcessingAdapter.MyViewHolder> {

    List<ProcessingModel> processingModels;


    public ProcessingAdapter(List<ProcessingModel> processingModels) {
        this.processingModels = processingModels;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_pending, parent, false);
        return new ProcessingAdapter.MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final ProcessingModel processingModel = processingModels.get(position);


        holder.orderid.setText(processingModel.getOrderid());
        holder.orderdate.setText(processingModel.getOrderDate());
        holder.customername.setText(processingModel.getCustomerName());
        holder.tablename.setText(processingModel.getTableName());
        holder.totalamount.setText(processingModel.getTotalAmount());

    /*    holder.viewdetaileye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Detail(Integer.parseInt(processingModel.getOrderid()), Integer.parseInt(processingModel.getTableName()));
            }
        });*/

    }

   /* public void Detail(int orderid, int tableid) {
        pendingListener.PendingListener(orderid, tableid);
    }*/

    @Override
    public int getItemCount() {
        return processingModels.size();
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
