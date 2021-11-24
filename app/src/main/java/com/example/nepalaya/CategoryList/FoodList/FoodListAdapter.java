package com.example.nepalaya.CategoryList.FoodList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nepalaya.CategoryList.AddToCartDatabase.OfflineCheckModel;

import com.example.nepalaya.FoodHome.Listener.AddToCardListener;
import com.example.nepalaya.FoodHome.Listener.ListenerList;
import com.example.nepalaya.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.MyViewHolder> {

    List<OfflineCheckModel> offlineCheckModels;

    ArrayList<FoodListModel> foodListModels;
    Context context;
    String FoodName;

    int one = 1; // Added for CheckOnes

    int Addsone; // Added for CheckOnes

    int Incff; // Added for CheckOnes

    int quantityval = 0; // Added for CheckOnes

    Boolean clickvalue = true; // Added for Checkones

    AddToCardListener addToCardListener; // Added for Checkones

    int addonesvalue = 1;
    ListenerList listener;


    public FoodListAdapter(ArrayList<FoodListModel> foodListModels, Context context, AddToCardListener addToCardListener, List<OfflineCheckModel> offlineCheckModel, ListenerList listener) {
        this.foodListModels = foodListModels;
        this.context = context;
        this.addToCardListener = addToCardListener;
        this.offlineCheckModels = offlineCheckModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_home, parent, false);
        return new FoodListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.MyViewHolder holder, int position) {
        final FoodListModel foodListModel = foodListModels.get(position);

        holder.title_food.setText(foodListModel.getVariantName());
        holder.food_rs.setText(foodListModel.getPrice());

        FoodName = foodListModel.getProductName();

        int varientid = Integer.parseInt(foodListModel.getVarientid());

        if (offlineCheckModels != null) {

            for (int jj = 0; jj < offlineCheckModels.size(); jj++) {
                OfflineCheckModel ff = offlineCheckModels.get(jj);

                int offvarient = Integer.parseInt(ff.getVarient_ID());
                int oddproductid = Integer.parseInt(ff.getProduct_ID());
                int quantityy = Integer.parseInt(ff.getCart_UNIT());
                float priceyy = Float.parseFloat(ff.getCart_UNITPRICE());
                String CartVarientNameyy = ff.getCart_INFO();


                int quantityyyy = 0;
                int current_valuegg = Integer.parseInt(String.valueOf(holder.chbContentcheck.getText()));

                if (varientid == offvarient) {

                    holder.chbContent.setVisibility(View.VISIBLE);
                    holder.chbContent.setChecked(true);
                    System.out.println("CHecking for Automatic checking");
                    quantityval++;

                    holder.total_amount_qty_.setText(String.valueOf(quantityy));

                    current_valuegg = 2;

                    holder.chbContentcheck.setText(String.valueOf(current_valuegg));

                    getDeFoodCart(quantityy, priceyy, quantityval, oddproductid, quantityyyy, clickvalue, current_valuegg, CartVarientNameyy, offvarient);// Product Id Repleace with Vareinet id
                } else {

//                    getDeFoodCart(quantityy, priceyy, quantityval, offvarient, quantityyyy, clickvalue, current_valuegg, CartVarientNameyy);// Product Id Repleace with Vareinet id

                }

            }
        }


  /*      if (varientid == 15) { // Checking the Value for Add to Cart
            int current_valuegg = Integer.parseInt(String.valueOf(holder.chbContentcheck.getText()));

            holder.chbContent.setVisibility(View.VISIBLE);
            holder.chbContent.setChecked(true);
            System.out.println("CHecking for Automatic checking");
            quantityval++;

            current_valuegg = 2;
            holder.chbContentcheck.setText(String.valueOf(current_valuegg));
        }*/

        holder.onclicklayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current_valuegg = Integer.parseInt(String.valueOf(holder.chbContentcheck.getText()));
                String itemvalue = holder.total_amount_qty_.getText().toString();

                Addsone = foodListModel.getAddones();

                System.out.println(addonesvalue);
/*
                if (Addsone == one & current_valuegg == 1) {
                    holder.onclicklayer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            addonesvalue++;
                            System.out.println(addonesvalue);
                            String VarientID = foodListModel.getVarientid();
                            getdetail(VarientID);

                        }
                    });
                } else {
                    System.out.println("bayo");

                }*/

                if (current_valuegg == 1) {
                    holder.chbContent.setVisibility(View.VISIBLE);
                    holder.chbContent.setChecked(true);

                    quantityval++;

                    Addsone = foodListModel.getAddones();
                    if (Addsone == one) {
                        String VarientID = foodListModel.getVarientid();
                        getdetail(VarientID);
                    }

                    holder.add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

//                            Addsone = foodModel.getAddones();

                            int current_value = Integer.parseInt(String.valueOf(holder.total_amount_qty_.getText()));
                            current_value++;

                            holder.total_amount_qty_.setText(String.valueOf(current_value));

                            int passprodutid = Integer.parseInt(foodListModel.getProductsID());
                            int passvarientid = Integer.parseInt(foodListModel.getVarientid());
                            int Quantity = Integer.parseInt(String.valueOf(holder.total_amount_qty_.getText()));
                            Float Price = Float.valueOf(foodListModel.getPrice());
                            int quanityneg = 0;
                            clickvalue = true;
                            String CartVarientName = foodListModel.getVariantName();

                            int current_valuegg = 1;
//                            getDeFoodCart(Quantity, Price, quantityval, passprodutid, quanityneg, clickvalue, current_valuegg);// Product Id Repleace with Vareinet id
                            getDeFoodCart(Quantity, Price, quantityval, passprodutid, quanityneg, clickvalue, current_valuegg, CartVarientName, passvarientid);// Product Id Repleace with Vareinet id
                        }
                    });

                    holder.remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            int current_value = Integer.parseInt(String.valueOf(holder.total_amount_qty_.getText()));

                            if (current_value == 1) {

                            } else {
                                current_value--;
                                holder.total_amount_qty_.setText(String.valueOf(current_value));

                                int passprodutid = Integer.parseInt(foodListModel.getProductsID());
                                int passvarientid = Integer.parseInt(foodListModel.getVarientid());

//                                int Quantity = Integer.parseInt(String.valueOf(holder.total_amount_qty_.getText()));
                                Float Price = Float.valueOf(foodListModel.getPrice());
                                int quanityneg = Integer.parseInt(String.valueOf(holder.total_amount_qty_.getText()));
//                                int Quantity = 0;
                                clickvalue = false;
                                String CartVarientName = foodListModel.getVariantName();

                                int Quantity = Integer.parseInt(String.valueOf(holder.total_amount_qty_.getText()));
                                int current_valuegg = 1;
//                                getDeFoodCart(Quantity, Price, quantityval, passprodutid, quanityneg, clickvalue, current_valuegg);
                                getDeFoodCart(Quantity, Price, quantityval, passprodutid, quanityneg, clickvalue, current_valuegg, CartVarientName, passvarientid);
                            }
                        }
                    });

                    current_valuegg = 2;
                    holder.chbContentcheck.setText(String.valueOf(current_valuegg));

                    int passprodutid = Integer.parseInt(foodListModel.getProductsID());
                    int passvarientid = Integer.parseInt(foodListModel.getVarientid());

                    int Quantity = Integer.parseInt(String.valueOf(holder.total_amount_qty_.getText()));


                    Float Price = Float.valueOf(foodListModel.getPrice());


                    int quanityneg = 0;
                    Boolean clickvalue = true;
                    String CartVarientName = foodListModel.getVariantName();

//                    getDeFoodCart(Quantity, Price, quantityval, passprodutid, quanityneg, clickvalue, current_valuegg);
                    getDeFoodCart(Quantity, Price, quantityval, passprodutid, quanityneg, clickvalue, current_valuegg, CartVarientName, passvarientid);

                    System.out.println(current_valuegg);

                } else if (current_valuegg == 2) {
                    holder.chbContent.setVisibility(View.GONE);
                    holder.chbContent.setChecked(false);
                    holder.remove_img.setClickable(false);
                    holder.add_img.setClickable(false);
                    current_valuegg = 1;
                    holder.chbContentcheck.setText(String.valueOf(current_valuegg));
                    System.out.println(current_valuegg);
                    quantityval--;
                    String CartVarientName = foodListModel.getVariantName();

                    int passprodutid = Integer.parseInt(foodListModel.getProductsID());
                    int passvarientid = Integer.parseInt(foodListModel.getVarientid());

                    int Quantity = Integer.parseInt(String.valueOf(holder.total_amount_qty_.getText()));
                    Float Price = Float.valueOf(foodListModel.getPrice());
                    int quanityneg = 0;
                    clickvalue = false;
//                    getDeFoodCart(Quantity, Price, quantityval, passprodutid, quanityneg, clickvalue, current_valuegg);

                    getDeFoodCart(Quantity, Price, quantityval, passprodutid, quanityneg, clickvalue, current_valuegg, CartVarientName, passvarientid);

                }/* else if (current_valuegg == 2 && itemvalue > 1 && Addsone != 1) {
                    Toast.makeText(context, "Please Decrease Food Quanity to uncheck item!", Toast.LENGTH_SHORT).show();
                }*/ else if (Addsone == one && current_valuegg == 2) {
                    holder.onclicklayer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           /* System.out.println("Hello");
                            String ProductsID = foodModel.getProductsID();
                            getdetail(ProductsID);*/
                            System.out.println("ajsidjaisjdi");
                        }
                    });
                }

//                holder.total_amount_qty_.setText(foodModel.getQnty());
            }

        });


        if (foodListModel.getProductName() == FoodName) {
            System.out.println("IF MA AAYO");
        } else {
            System.out.println("Your Product Name is:" + foodListModel.getProductName());
        }

    }

    public void getdetail(String ProductsID) {
        listener.onItemClicked(ProductsID);

    }

    public void getDeFoodCart(int Quantity, Float Price, int quantityval, int passprodutid, int quanityneg, Boolean clickvalue, int current_valuegg, String Varientname, int varientid) {
        addToCardListener.AddListerner(Quantity, Price, quantityval, passprodutid, quanityneg, clickvalue, current_valuegg, Varientname, varientid);
    }

    @Override
    public int getItemCount() {
        return foodListModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.title_food)
        TextView title_food;

        @BindView(R.id.food_rs)
        TextView food_rs;

        @BindView(R.id.onclickLayer)
        RelativeLayout onclicklayer;

        @BindView(R.id.remove_img)
        ImageView remove_img;

        @BindView(R.id.add_img)
        ImageView add_img;

        @BindView(R.id.food_desc)
        TextView food_desc;

        @BindView(R.id.chbContent)
        CheckBox chbContent;

        @BindView(R.id.chbContentcheck)
        TextView chbContentcheck;

        MaterialTextView total_amount_qty_;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            total_amount_qty_ = itemView.findViewById(R.id.total_amount_qty_);

        }
    }
}
