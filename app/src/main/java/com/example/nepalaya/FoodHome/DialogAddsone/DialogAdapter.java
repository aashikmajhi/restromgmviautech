package com.example.nepalaya.FoodHome.DialogAddsone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nepalaya.CategoryList.FoodList.FoodListModel;
import com.example.nepalaya.FoodHome.AddsonesModel;
import com.example.nepalaya.R;
import com.example.nepalaya.Table.NonNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.MyViewHolder> {

    ArrayList<AddsonesModel> addsonesModels;
    Context context;
    Addtoccardlistener addtoccardlistener;
    int Productidadd;
    ArrayList<FoodListModel> foodListModels;

    int totalsum = 0;
    int grandtotalsum;
    Boolean checkvalue = true;
    int productid;
    int addonespp;

    int newtotal = 0;

    String infoAddones = "";

    public DialogAdapter(ArrayList<AddsonesModel> addsonesModels, Context context, Addtoccardlistener addtoccardlistener, int Productidadd, ArrayList<FoodListModel> foodListModels) {
        this.addsonesModels = addsonesModels;
        this.context = context;
        this.addtoccardlistener = addtoccardlistener;
        this.Productidadd = Productidadd;
        this.foodListModels = foodListModels;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_addones_detail, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AddsonesModel addsonesModel = addsonesModels.get(position);

        int Addoneid = Integer.parseInt(addsonesModel.getAddonsid());


        addonespp = Integer.parseInt(addsonesModel.getProdID());

        holder.AddoneName.setText(addsonesModel.getAdd_on_name());
        holder.AddonePriceDetail.setText(addsonesModel.getAddonsprice());

//        System.out.println("Checking the looops in addones" + Addonename + Productidadd);

        System.out.println("Passing Value in Constructors:" + Productidadd);
        System.out.println("New addones pp is herer:" + addonespp);

        holder.checkboxx_addones.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                        Boolean checkboxstatus = Boolean.getBoolean(String.valueOf(holder.checkboxx_addones));
                        System.out.println(checkboxstatus);

                        if (compoundButton.isChecked()) {
                            String gg;
                            if (b = true) {

                                String Addonename = (addsonesModel.getAdd_on_name());


                                int currentvalue = Integer.parseInt(String.valueOf(holder.qty_detail_addones.getText()));
                                holder.qty_detail_addones.setText(String.valueOf(currentvalue));

                                Float Addoneprine = Float.valueOf(addsonesModel.getAddonsprice());
                                productid = Integer.parseInt(addsonesModel.getProdID());

                                int totalprice = Integer.parseInt(String.valueOf(holder.totalpricedetail.getText()));

                                totalprice += Addoneprine * currentvalue;

                                grandtotalsum += totalprice;
                                System.out.println("Grand total sum" + grandtotalsum);

                                holder.totalpricedetail.setText(String.valueOf(totalprice));

                                int quantity = Integer.parseInt(String.valueOf(holder.qty_detail_addones.getText()));

                                infoAddones += "\n" + Addonename;
                                AddonesDetail(grandtotalsum, productid, Addoneid, checkvalue, infoAddones, quantity);


//                        holder.totalpricedetail.setText(String.valueOf(totalsum));
                                holder.add_circle_addones.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        int currentvalue = Integer.parseInt(String.valueOf(holder.qty_detail_addones.getText()));
                                        currentvalue++;
                                        holder.qty_detail_addones.setText(String.valueOf(currentvalue));

                                        int totalprice = Integer.parseInt(String.valueOf(holder.totalpricedetail.getText()));
                                        System.out.println("This is my total price" + totalprice);

                                        Float Addoneprine = Float.valueOf(addsonesModel.getAddonsprice());
                                        productid = Integer.parseInt(addsonesModel.getProdID());

                                        totalprice += Addoneprine * 1;

                                        grandtotalsum += Addoneprine;

                                        System.out.println("Grand total sum" + grandtotalsum);

                                        System.out.println("Current Value is" + currentvalue);

                                        System.out.println(totalsum);
                                        checkvalue = true;
                                        holder.totalpricedetail.setText(String.valueOf(totalprice));

                                        /*checkingaddones(totalprice, productid, Addoneid, checkvalue,currentvalue);*/

                                        int quantity = Integer.parseInt(String.valueOf(holder.qty_detail_addones.getText()));


                                        AddonesDetail(grandtotalsum, productid, Addoneid, checkvalue, infoAddones, quantity);

                                        System.out.println(totalsum + productid);
                                    }
                                });

                                holder.remove_circle_addones.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        int current_value = Integer.parseInt(String.valueOf(holder.qty_detail_addones.getText()));

                                        if (current_value != 1) {
                                            current_value--;
                                            holder.qty_detail_addones.setText(String.valueOf(current_value));

                                            Float Addoneprine = Float.valueOf(addsonesModel.getAddonsprice());

                                            int totalprice = Integer.parseInt(String.valueOf(holder.totalpricedetail.getText()));

                                            totalprice -= Addoneprine * 1;

                                            grandtotalsum -= totalprice;

                                            System.out.println("Grand total sum" + grandtotalsum);

//                                    checkingaddones(totalprice, productid, Addoneid, checkvalue,current_value);

                                            checkvalue = true;
                                            holder.totalpricedetail.setText(String.valueOf(totalprice));

                                            int quantity = Integer.parseInt(String.valueOf(holder.qty_detail_addones.getText()));

                                            AddonesDetail(grandtotalsum, productid, Addoneid, checkvalue, infoAddones, quantity);
                                        }
                                    }
                                });
                            }
                        } else {
                            // not checked

                            int current_value = Integer.parseInt(String.valueOf(holder.qty_detail_addones.getText()));

                            holder.qty_detail_addones.setText(String.valueOf(current_value));

                            Float Addoneprine = Float.valueOf(addsonesModel.getAddonsprice());

                            int totalprice = Integer.parseInt(String.valueOf(holder.totalpricedetail.getText()));

                            totalprice -= Addoneprine * current_value;

                            grandtotalsum -= (Addoneprine * current_value);

                            System.out.println("Grand total sum while unchecked the value" + grandtotalsum);

                            holder.totalpricedetail.setText(String.valueOf(totalprice));
                            checkvalue = false;

                            int quantity = Integer.parseInt(String.valueOf(holder.qty_detail_addones.getText()));

                            AddonesDetail(grandtotalsum, productid, Addoneid, checkvalue, infoAddones, quantity);

                            holder.qty_detail_addones.setText(String.valueOf("1"));
                                            /*
                        AddonesDetail(totalsum, productid,Addoneid,checkvalue);
                        */
                        }
                    }
                }
        );

    }

    public void AddonesDetail(int price, int productidaddons, int addonesid, Boolean clickvalue, String add_on_name, int quantity) {
        addtoccardlistener.AddListerner(price, productidaddons, addonesid, clickvalue, add_on_name, quantity);
    }

    public void checkingaddones(int price, int productidd, int Addoneid, Boolean checkvalue, int quantity) {
        for (int jj = 0; jj < foodListModels.size(); jj++) {
            FoodListModel fff = foodListModels.get(jj);
            int productid = Integer.parseInt(fff.getProductsID());
            int addoneval = fff.getAddones();


            System.out.println("FROM FROM Addones list ------>" + "totalsum" + price + "prodctid" + productidd + "Addoneid" + Addoneid + "Checkvalue" + checkvalue
                    + "Quantity" + quantity);

            if (productid == productidd & checkvalue == true) {
                System.out.println("Yeti vitra aayo");
                if (quantity > 1) {
//                    float price = Float.parseFloat(fff.getPrice());
                    newtotal += price * quantity;
                    System.out.println("My total sum" + newtotal);

//                    System.out.println("Before Hashmap Value" + HashMap);
                } else {
                    newtotal += price * quantity;
                    System.out.println("My total sum" + newtotal);
                }
            } else if (productid == productidd & checkvalue == false) {

                System.out.println("Check value in else if");

           /*     if (quanitynegfh == 0 & cartquantityval > 1 & current_valueggh == 1 & addoneval != 1) {
                    float price = Float.parseFloat(fff.getPrice());
                    sum = sum - (price * cartquantityval);
                    System.out.println("My total sum" + sum);
                    viewcartrs.setText(String.valueOf("Rs." + sum));
                    System.out.println(addoneval);
                    System.out.println("Addones bha ko thau ma aayo mathi ko");
                } else if (addoneval == 1) {
                    if (HashMap.containsKey(productid)) {
                        System.out.println("Hashmap value" + productid);
                        float price = Float.parseFloat(fff.getPrice());
                        float nresum = sum - (price * cartquantityval);
                        int addonsprice = HashMap.get(productid);
                        sum = nresum - addonsprice;

                        System.out.println("Before Hashmap Value" + HashMap);
                        System.out.println("My total sum" + sum);
                        viewcartrs.setText(String.valueOf("Rs." + sum));
                        HashMap.remove(productid);
                        System.out.println("After Hashmap value" + HashMap);
                    } else {
                        float price = Float.parseFloat(fff.getPrice());
                        sum = sum - (price * cartquantityval);
                        System.out.println("My total sum" + sum);
                        viewcartrs.setText(String.valueOf("Rs." + sum));
                        System.out.println("Addones bha ko thau ma aayo");

                    }
                    System.out.println("Before Hashmap Value" + HashMap);

                    System.out.println("we are herer");
                } else {
                    float price = Float.parseFloat(fff.getPrice());
                    sum = sum - (price * 1);
                    System.out.println("My total sum" + sum);
                    System.out.println("Ia hasdddddddddderer");
                    viewcartrs.setText(String.valueOf("Rs." + sum));
                }*/
            } /*else if (productid == passprodutidfh & clickvaluefh == false & addoneval != 1) {
                float price = Float.parseFloat(fff.getPrice());
                sum = sum - (price * cartquantityval);
                System.out.println("My total sum" + sum);
                viewcartrs.setText(String.valueOf("Rs." + sum));
                System.out.println("Addones bha ko thau ma aayo");
            }*/
        }
    }

    @Override
    public int getItemCount() {
        return addsonesModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.addonename_desc_detail)
        TextView AddoneName;

        @BindView(R.id.addone_price_detail)
        TextView AddonePriceDetail;

        @BindView(R.id.remove_circle_addones)
        ImageView remove_circle_addones;

        @BindView(R.id.qty_detail_addones)
        TextView qty_detail_addones;

        @BindView(R.id.add_circle_addones)
        ImageView add_circle_addones;

        CheckBox checkboxx_addones;

        TextView totalpricedetail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            checkboxx_addones = (CheckBox) itemView.findViewById(R.id.checkboxx_addones);
            totalpricedetail = itemView.findViewById(R.id.total_price_detail);
        }
    }
}
