package com.example.nepalaya.OrderList.OrderTabs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nepalaya.ApiService.APIService;
import com.example.nepalaya.ApiService.ApiHelper;
import com.example.nepalaya.Cart.Cart;
import com.example.nepalaya.CategoryList.AddToCartDatabase.Database;
import com.example.nepalaya.CategoryList.FoodList.FoodHome;
import com.example.nepalaya.CategoryList.FoodList.FoodListModel;
import com.example.nepalaya.FoodHome.AddsonesModel;
import com.example.nepalaya.OrderList.OrderTabs.DialogAdapter.OrderDialogAdapter;
import com.example.nepalaya.OrderList.OrderTabs.OrderListModel.PenAddonslistModel;
import com.example.nepalaya.OrderList.OrderTabs.OrderListModel.PendingOrderlistModel;
import com.example.nepalaya.OrderList.OrderTabs.Pending.PendingAdapter;
import com.example.nepalaya.OrderList.OrderTabs.Pending.PendingListener;
import com.example.nepalaya.OrderList.OrderTabs.Pending.PendingModel;
import com.example.nepalaya.R;
import com.example.nepalaya.SchedulePreference;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingOrder extends Fragment {

    @BindView(R.id.pending_recycle)
    RecyclerView pendingrecycleview;

    ProgressDialog progressDialog;

    Call<JsonElement> call;
    ArrayList<PendingModel> pendingModels;
    ArrayList<PendingOrderlistModel> pendingOrderlistModels;
    ArrayList<PenAddonslistModel> penAddonslistModels;

    String orderid, CustomerName, TableName, OrderDate, TotalAmount;

    PendingAdapter pendingAdapter;

    OrderDialogAdapter orderDialogAdapter;

    PendingListener pendingListener;

    int Dproductid, Dprice, DVarientid, DItemqty, Daddons;
    String DProductName, DVarientName;

    String addonsName, add_on_id, add_on_qty;
    float addonsprice;


    String grandtotal;
    SchedulePreference schedulePreference;

    Database database;

    String addonesdata = "";
    float addonespricedata;

    public PendingOrder(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.pending_order, container, false);
        ButterKnife.bind(this, rootview);

        schedulePreference = new SchedulePreference(getContext());

        database = new Database(getContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        pendingrecycleview.setLayoutManager(layoutManager);

        progressdialog();
        getData();
        Showdetail();
        return rootview;

    }

    public void progressdialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }


    public void getData() {
        int id = 168;
        APIService apiService = (APIService) ApiHelper.getInstance().getService(APIService.class);
        call = apiService.Pendinglist(id);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JSONObject data = new JSONObject();
                if (response.isSuccessful()) {
                    String responsevalue = response.body().toString();
                    progressDialog.hide();
                    if (response != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(responsevalue);
                            JSONArray dataouter = jsonObject.getJSONArray("data");
                            pendingModels = new ArrayList<>();

                            for (int i = 0; i < dataouter.length(); i++) {
                                JSONObject jsonObject1 = dataouter.getJSONObject(i);

                                orderid = jsonObject1.getString("order_id");
                                CustomerName = jsonObject1.getString("CustomerName");
                                TableName = jsonObject1.getString("TableName");
                                OrderDate = jsonObject1.getString("OrderDate");
                                TotalAmount = jsonObject1.getString("TotalAmount");


                                pendingModels.add(new PendingModel(orderid, CustomerName, TableName, OrderDate, TotalAmount));
                            }

                            pendingAdapter = new PendingAdapter(pendingModels, pendingListener);
                            pendingrecycleview.setAdapter(pendingAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
//                    Toast.makeText(getContext(), "No Data is Available", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }


    public void Showdetail() {
        pendingListener = new PendingListener() {
            @Override
            public void PendingListener(int orderid, int tableid) {


//                getorderdata(orderid);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog, null);
                RecyclerView orderadapyer = dialogView.findViewById(R.id.order_recycleview);
                TextView table_name = dialogView.findViewById(R.id.table_name);
                TextView orderno = dialogView.findViewById(R.id.order_no);
                TextView ct_grandtotal = dialogView.findViewById(R.id.ct_grandtotal);
                CardView ct_updatefood = dialogView.findViewById(R.id.ct_updatefood);

                ct_updatefood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        database.Deletedatabase();

                        schedulePreference.tableidremove();
                        schedulePreference.checkupdateremove();
                        schedulePreference.orderidremove();
//                        String tableid = schedulePreference.gettableid();
//                        if (tableid == null) {

                        database = new Database(getContext());


                        schedulePreference.setOrderid(String.valueOf(orderid));


                        AddToDatabase();
                        for (int i = 0; i < pendingOrderlistModels.size(); i++) {
                            PendingOrderlistModel ff = pendingOrderlistModels.get(i);

                            Dproductid = ff.getDproductid();
                            DProductName = ff.getDProductName();
                            Dprice = ff.getDprice();
                            DVarientName = ff.getDVarientName();
                            DVarientid = ff.getDVarientid();
                            DItemqty = ff.getDItemqty();
                            Daddons = ff.getDaddons();

                            AddToDatabase();
                            int vv = ff.getDVarientid();

                            if (penAddonslistModels != null) {
                                for (int j = 0; j < penAddonslistModels.size(); j++) {
                                    PenAddonslistModel jj = penAddonslistModels.get(j);

                                    DVarientid = jj.getAddonsvarientid();


                                    if (vv == DVarientid) {

                                        addonsName = jj.getAddonsName();
                                        add_on_id = jj.getAdd_on_id();
                                        addonsprice = jj.getAddprice();
                                        add_on_qty = jj.getAdd_on_qty();

                                        int qqq = (int) (addonsprice * Integer.parseInt(add_on_qty));

                                        addonesdata += "\n" + addonsName + "(" + add_on_qty + ")";
                                        addonespricedata += qqq;


                                        database.updateforAddones(DVarientid, addonesdata, addonespricedata);

                                        AddaddonestoDatabase(DVarientid, add_on_qty, addonsprice, add_on_id);
                                    }

                                    System.out.println("Addones value " + addonsName);
                                }
                            }


                            addonespricedata = 0;
                            addonesdata = "";

                        }


                        Intent gotocart = new Intent(getContext(), Cart.class);
                        Bundle bundle = new Bundle();

                        bundle.putString("gg", "helo");
                        bundle.putString("tablename", String.valueOf(tableid));

                        String updatevalue = "1";
                        schedulePreference.setcheckpdate(updatevalue);
                        schedulePreference.setTableid(String.valueOf(tableid));
                        gotocart.putExtras(bundle);
                        startActivity(gotocart);
//                        } else {
//                            Toast.makeText(getContext(), "Please Cancel the Current Order Before Updating", Toast.LENGTH_SHORT).show();
//                        }
                    }
                });

                table_name.setText(String.valueOf("Table " + tableid));
                orderno.setText(String.valueOf("Order: # " + orderid));


                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                orderadapyer.setLayoutManager(layoutManager);

                int orderidd = orderid;


                System.out.println("Order id is" + orderidd);
                APIService apiService = (APIService) ApiHelper.getInstance().getService(APIService.class);
                call = apiService.UpdateOrder(orderidd);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        if (response.isSuccessful()) {
                            String responsevalue = response.body().toString();
                            JSONArray notification = null;
                            try {
                                JSONObject jsonObject = new JSONObject(responsevalue);

                                JSONObject dataouter = jsonObject.getJSONObject("data");
                                grandtotal = dataouter.getString("Grandtotal");
                                JSONArray jsonArray = dataouter.getJSONArray("iteminfo");
                                pendingOrderlistModels = new ArrayList<>();
                                penAddonslistModels = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    Dproductid = jsonObject1.getInt("ProductsID");
                                    DProductName = jsonObject1.getString("ProductName");
                                    Dprice = jsonObject1.getInt("price");
                                    DVarientName = jsonObject1.getString("Varientname");
                                    DVarientid = jsonObject1.getInt("Varientid");
                                    DItemqty = jsonObject1.getInt("Itemqty");
                                    Daddons = jsonObject1.getInt("addons");

                                    int addVarientid = DVarientid;

                                    pendingOrderlistModels.add(new PendingOrderlistModel(Dproductid, Dprice, DVarientid, DItemqty, Daddons, DProductName, DVarientName));

                                    if (Daddons == 1) {


                                        JSONArray jsonArray1 = jsonObject1.getJSONArray("addonsinfo");
                                        for (int j = 0; j < jsonArray1.length(); j++) {

                                            JSONObject jsn = jsonArray1.getJSONObject(j);


                                            addonsName = jsn.getString("addonsName");
                                            add_on_id = jsn.getString("add_on_id");
                                            addonsprice = Float.parseFloat(jsn.getString("price"));
                                            add_on_qty = jsn.getString("add_on_qty");

                                            penAddonslistModels.add(new PenAddonslistModel(addVarientid, addonsName, add_on_id, addonsprice, add_on_qty));
                                            System.out.println("Addones list is here" + penAddonslistModels);
                                        }
                                    }

                                    System.out.println("Pending List Models are as Lissted Here" + pendingOrderlistModels);

                                }

                                orderDialogAdapter = new OrderDialogAdapter(pendingOrderlistModels, penAddonslistModels);  // Do Changes here
                                orderadapyer.setAdapter(orderDialogAdapter);
                                // Do changes here
                                ct_grandtotal.setText(grandtotal);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(getContext(), "hey error", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        Toasty.error(getContext(), t.getMessage(), Toasty.LENGTH_LONG).show();

                    }
                });


//                orderDialogAdapter = new OrderDialogAdapter(pendingOrderlistModels);  // Do Changes here
//                orderadapyer.setAdapter(orderDialogAdapter);

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        };
    }

    public void AddaddonestoDatabase(int gvariid, String gaddqty, float gadprice, String gadonid) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("Varientid", gvariid);
        contentValues.put("Addonsquantity", gaddqty);
        contentValues.put("addonsprice", gadprice);
        contentValues.put("addonsid", gadonid);

        database.addadone(contentValues);

    }

    public void AddToDatabase() {
        // Adding Data into Database
        ContentValues contentValues = new ContentValues();
        contentValues.put("Varient_ID", DVarientid);
        contentValues.put("Cart_INFO", DVarientName);
        contentValues.put("Cart_UNIT", DItemqty);
        contentValues.put("Total_Price", "");
        contentValues.put("Notes", "");
        contentValues.put("Product_ID", Dproductid);
        contentValues.put("addonsid", Daddons);
        contentValues.put("add_on_name", "");
        contentValues.put("addonsprice", "passprodutidfh");
        contentValues.put("addonstotal", "0");
        contentValues.put("Cart_UNITPRICE", Dprice);

        database.insertfood(contentValues);

//        System.out.println("CartPrice" + cartPrice + "CaertQuantity" + cartquantityval + "cartquantity" + cartquatity);
    }


}
