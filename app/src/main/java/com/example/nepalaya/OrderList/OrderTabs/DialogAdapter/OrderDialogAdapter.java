package com.example.nepalaya.OrderList.OrderTabs.DialogAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nepalaya.FoodHome.DialogAddsone.DialogAdapter;
import com.example.nepalaya.OrderList.OrderTabs.OrderListModel.PenAddonslistModel;
import com.example.nepalaya.OrderList.OrderTabs.OrderListModel.PendingOrderlistModel;
import com.example.nepalaya.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDialogAdapter extends RecyclerView.Adapter<OrderDialogAdapter.MyViewHolder> {


    ArrayList<PendingOrderlistModel> pendingOrderlistModels;
    ArrayList<PenAddonslistModel> penAddonslistModels;
    String addname = "";

    public OrderDialogAdapter(ArrayList<PendingOrderlistModel> pendingOrderlistModels, ArrayList<PenAddonslistModel> penAddonslistModels) {
        this.pendingOrderlistModels = pendingOrderlistModels;
        this.penAddonslistModels = penAddonslistModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_dialog_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final PendingOrderlistModel pendingOrderlistModel = pendingOrderlistModels.get(position);

        holder.ctproductname.setText(pendingOrderlistModel.getDProductName());
//        holder.ctvarientname.setText(pendingOrderlistModel.getDVarientName());
        holder.ctunitprice.setText(String.valueOf(pendingOrderlistModel.getDprice()));
        holder.ctqty.setText(String.valueOf(pendingOrderlistModel.getDItemqty()));
        int varientid = pendingOrderlistModel.getDVarientid();

        holder.cttotal.setText((String.valueOf(pendingOrderlistModel.getDprice() * pendingOrderlistModel.getDItemqty())));

/*        if (penAddonslistModels != null) {
            for (int i = 0; i < penAddonslistModels.size(); i++) {
                PenAddonslistModel ff = penAddonslistModels.get(i);
                int addid = ff.getAddonsvarientid();
                if (varientid == addid) {

                    addname += addname + ff.getAddonsName();
                    holder.ctvarientname.setText(pendingOrderlistModel.getDVarientName() + addname);

                } else {
                    holder.ctvarientname.setText(String.valueOf(pendingOrderlistModel.getDVarientName()));
                }
            }

        }*/
        holder.ctvarientname.setText(String.valueOf(pendingOrderlistModel.getDVarientName()));
        addname = "";

    }


    @Override
    public int getItemCount() {
        return pendingOrderlistModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ct_productname)
        TextView ctproductname;

        @BindView(R.id.ct_varientname)
        TextView ctvarientname;

        @BindView(R.id.ct_unitprice)
        TextView ctunitprice;

        @BindView(R.id.ct_qty)
        TextView ctqty;

        @BindView(R.id.ct_total)
        TextView cttotal;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
