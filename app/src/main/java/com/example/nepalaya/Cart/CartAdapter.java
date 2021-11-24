package com.example.nepalaya.Cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nepalaya.Cart.Listener.CartListener;
import com.example.nepalaya.CategoryList.AddToCartDatabase.OfflineAddonsModel;
import com.example.nepalaya.CategoryList.AddToCartDatabase.OfflineCheckModel;
import com.example.nepalaya.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    ArrayList<OfflineCheckModel> offlineCheckModels;
    Context context;

    CartListener cartListener;
    ArrayList<OfflineAddonsModel> offlineAddonsModels;

    int TotalPrice;


    public CartAdapter(ArrayList<OfflineCheckModel> offlineCheckModels, Context context, CartListener cartListener, ArrayList<OfflineAddonsModel> offlineAddonsModels) {
        this.offlineCheckModels = offlineCheckModels;
        this.context = context;
        this.cartListener = cartListener;
        this.offlineAddonsModels = offlineAddonsModels;

    }

    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.customcartlist, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final OfflineCheckModel offlineCheckModel = offlineCheckModels.get(position);

        holder.cartfoodname.setText(offlineCheckModel.getCart_INFO() + offlineCheckModel.getAdd_on_name());
        holder.cartunitprice.setText(offlineCheckModel.getCart_UNITPRICE());
        holder.cartquantity.setText(offlineCheckModel.getCart_UNIT());

        int Varientid = Integer.parseInt(offlineCheckModel.getVarient_ID());

        int Cartquantity = Integer.parseInt(holder.cartquantity.getText().toString());
        float price = Float.parseFloat(offlineCheckModel.getCart_UNITPRICE().toString());

        float addonestotal = Float.parseFloat(offlineCheckModel.getAddonstotal());

        float pricelist = Cartquantity * price;

        pricelist += addonestotal;

        holder.carttotalprice.setText(String.valueOf(pricelist));

        TotalPrice += pricelist;

        System.out.println("Your Total Price is" + TotalPrice);
        int addons = 1;

        Gotocart(TotalPrice, Varientid, Cartquantity);

        holder.ccremoveqty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int Cartquantityy = Integer.parseInt(holder.cartquantity.getText().toString());

                if (Cartquantityy > 1) {

                    Cartquantityy--;

                    holder.cartquantity.setText(String.valueOf(Cartquantityy));

                    float pricelist = Cartquantityy * price;

                    pricelist += addonestotal;

                    TotalPrice -= 1 * price;

                    holder.carttotalprice.setText(String.valueOf(pricelist));

                    int getcartqty = Integer.parseInt(holder.cartquantity.getText().toString());

                    Gotocart(TotalPrice, Varientid, getcartqty);
                }

            }
        });

        holder.ccaddqty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Cartquantityy = Integer.parseInt(holder.cartquantity.getText().toString());

                Cartquantityy++;

                holder.cartquantity.setText(String.valueOf(Cartquantityy));
                System.out.println("Cart Quantity" + Cartquantityy);

                float pricelist = Cartquantityy * price;

                pricelist += addonestotal;

                TotalPrice += 1 * price;

                holder.carttotalprice.setText(String.valueOf(pricelist));

                Gotocart(TotalPrice, Varientid, Cartquantityy);

            }
        });

    }


    public void Gotocart(int Foodtotal, int Varientid, int foodqty) {
        cartListener.Cartlistener(Foodtotal, Varientid, foodqty);
    }

    @Override
    public int getItemCount() {
        return offlineCheckModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cartfoodname, cartunitprice, cartquantity, carttotalprice;
        ImageView ccremoveqty, ccaddqty;


        public MyViewHolder(View itemView) {
            super(itemView);

            cartfoodname = itemView.findViewById(R.id.cartfoodname);

            cartunitprice = itemView.findViewById(R.id.cartunitprice);
            cartquantity = itemView.findViewById(R.id.cartquantity);
            carttotalprice = itemView.findViewById(R.id.carttotalprice);
            ccremoveqty = itemView.findViewById(R.id.ccremoveqty);
            ccaddqty = itemView.findViewById(R.id.ccaddqty);

        }
    }
}
