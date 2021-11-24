package com.example.nepalaya.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nepalaya.ApiService.APIService;
import com.example.nepalaya.ApiService.ApiHelper;
import com.example.nepalaya.Cart.Listener.CartListener;
import com.example.nepalaya.CategoryList.AddToCartDatabase.Database;
import com.example.nepalaya.CategoryList.AddToCartDatabase.OfflineAddonsModel;
import com.example.nepalaya.CategoryList.AddToCartDatabase.OfflineCheckModel;
import com.example.nepalaya.CategoryList.FoodList.FoodListModel;
import com.example.nepalaya.MainActivity;
import com.example.nepalaya.R;
import com.example.nepalaya.SchedulePreference;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart extends AppCompatActivity {

    @BindView(R.id.cartnotes)
    EditText cartnotes;

    @BindView(R.id.cartvat)
    TextView cartvat;

    @BindView(R.id.cart_ser)
    TextView cartser;

    @BindView(R.id.cart_total)
    TextView carttot;

    @BindView(R.id.cartrecycle)
    RecyclerView cartrecycle;

    @BindView(R.id.table_no)
    TextView tableno;

    @BindView(R.id.btn_placeorder)
    TextView btnplaceor;

  /*  @BindView(R.id.backarrows)
    ImageView btnbackarrow;*/

    Call<JsonElement> call;

    ArrayList<FoodListModel> foodListModels;
    String tablename;

    ArrayList<OfflineCheckModel> offlineCheckModels = new ArrayList<>(); // for ofline
    ArrayList<OfflineAddonsModel> offlineAddonsModels = new ArrayList<>(); //for Offline uses

    CartAdapter cartAdapter;

    Database database;

    CartListener cartListener;

    int id, TypeID, VatAmount, Tableid, CustomerID, Total, Grandtotal;

    int etfoodtotal;

    JSONArray foodinfo;

    JSONArray parent = new JSONArray();

    int checklist = 0;

    SchedulePreference schedulePreference;

    String checkupdate, stableid, sorderid;

    float newtotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ButterKnife.bind(this);

        schedulePreference = new SchedulePreference(getApplicationContext());

        database = new Database(Cart.this);

        Bundle bundle = getIntent().getExtras();
        String cartPrice = String.valueOf(bundle.getFloat("cartPrice"));
        String cartquantityval = bundle.getString("cartquantityval");
        String passprodutidfh = bundle.getString("passprodutidfh");
        String quanitynegfh = bundle.getString("quanitynegfh");
        tablename = bundle.getString("tablename");

        System.out.println("table Name" + tablename);

        stableid = schedulePreference.gettableid();
        sorderid = schedulePreference.getOrderid();

        tableno.setText("Table No: " + stableid);
        checkupdate = schedulePreference.getcheckupdate();

        System.out.println("check update in table" + checkupdate);

        if (checkupdate != null) {
            btnplaceor.setText("UPDATE ORDER");
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        cartrecycle.setLayoutManager(layoutManager);

        offlineCheckModels.addAll(database.getAllCartList());

//        foodModels.add(new FoodModel(cartPrice, cartquantityval, passprodutidfh, quanitynegfh));

//        ShowDetail();
        offlineAddonsModels.addAll(database.getallAddonsList());

       /* try {

            for (int i = 0; i < offlineCheckModels.size(); i++) {
                checklist++;
                OfflineCheckModel offlineCheckModel1 = offlineCheckModels.get(i);
                JSONObject jsonObject1 = new JSONObject();
                parent.put(jsonObject1);
                jsonObject1.put("ProductsID", Integer.parseInt(offlineCheckModel1.getProduct_ID()));
                jsonObject1.put("quantity", Integer.parseInt(offlineCheckModel1.getCart_UNIT()));
                jsonObject1.put("itemnote", "This is Note");
                jsonObject1.put("variantid", Integer.parseInt(offlineCheckModel1.getVarient_ID()));

                float addonsvalue = Float.parseFloat(offlineCheckModel1.getAddonstotal());
                System.out.println(addonsvalue);
                if (addonsvalue == 0.0) {
                    jsonObject1.put("addons", Integer.parseInt("0"));
                } else if (addonsvalue != 0) {

                    jsonObject1.put("addons", Integer.parseInt("1"));
                    JSONArray addonesArr = new JSONArray();
                    jsonObject1.put("addonsinfo", addonesArr);

                    for (int jj = 0; jj < offlineAddonsModels.size(); jj++) {

                        int varientid = Integer.parseInt(offlineCheckModel1.getVarient_ID());

                        OfflineAddonsModel offlineAddonsModel = offlineAddonsModels.get(jj);

                        if (varientid == offlineAddonsModel.getVarientid()) {

                            JSONObject jsonObject = new JSONObject();
                            addonesArr.put(jsonObject);
                            jsonObject.put("addonsquantity", offlineAddonsModel.getAddonsquantity());
                            jsonObject.put("addonsprice", offlineAddonsModel.getAddonsprice());
                            jsonObject.put("addonsid", offlineAddonsModel.getAddonsid());

                        }
                    }

                }
            }
            System.out.println(parent);

        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        ShowDetail();

        cartAdapter = new CartAdapter(offlineCheckModels, getApplicationContext(), cartListener, offlineAddonsModels);
        cartrecycle.setAdapter(cartAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(cartrecycle);


    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        } // Rearranger

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:
//                offlineCheckModels.get(position);
                    final OfflineCheckModel offlineCheckModel = offlineCheckModels.get(position);
                    database.deleteFood(offlineCheckModel.getVarient_ID());
                    database.CartDelVarientid(offlineCheckModel.getVarient_ID());
                    System.out.println("Varient id to delete from cart lists" + offlineCheckModel.getVarient_ID());
                    offlineCheckModels.remove(position);


                    cartAdapter.notifyItemRemoved(position);
                    TotalPrice();
                  /*  offlineCheckModels.removeAll(offlineCheckModels);
                    offlineAddonsModels.removeAll(offlineAddonsModels);

                    offlineCheckModels.addAll(database.getAllCartList());
                    offlineAddonsModels.addAll(database.getallAddonsList());*/
//                    checklist--;
//                    System.out.println("Check List value is" + checklist);

                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(Cart.this, R.color.dark_red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_forever)
                    .setSwipeLeftLabelColor(ContextCompat.getColor(Cart.this, R.color.white))
                    .addSwipeLeftLabel("DELETE")
                    .setSwipeLeftLabelTextSize(0, 45)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @OnClick({R.id.btnplaceorder, R.id.cart_additem, R.id.cancel_order_btn, R.id.backarrows})
    public void OnClick(View view) {

        switch (view.getId()) {
            case R.id.btnplaceorder:
                checklist = offlineCheckModels.size();
                System.out.println("Check List value is" + checklist);
                if (checklist == 0) {
                    System.out.println("models checking" + offlineCheckModels);
                    Toasty.error(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                    break;
                } else if (checklist > 0 & checkupdate == null) {
                    database = new Database(Cart.this);
                    offlineCheckModels.removeAll(offlineCheckModels);
                    offlineAddonsModels.removeAll(offlineAddonsModels);

                    offlineCheckModels.addAll(database.getAllCartList());
                    offlineAddonsModels.addAll(database.getallAddonsList());

                    Jsonprint();
                    FetchData();
                    break;
                } else if (checklist > 0 & checkupdate != null) {

                    database = new Database(Cart.this);
                    offlineCheckModels.removeAll(offlineCheckModels);
                    offlineAddonsModels.removeAll(offlineAddonsModels);

                    offlineCheckModels.addAll(database.getAllCartList());
                    offlineAddonsModels.addAll(database.getallAddonsList());
                    Jsonprint();

                    FetchDataUpdate();

                    Toasty.success(getApplicationContext(), "Update Operation Successfull Complete", Toast.LENGTH_SHORT).show();
                    break;
                }
                break;

            case R.id.cart_additem:

                Intent i = new Intent(Cart.this, MainActivity.class);
                Bundle bd = new Bundle();
                bd.putString("table_id", "1");
                i.putExtras(bd);
                startActivity(i);
                finish();
                break;

            case R.id.cancel_order_btn:
                database.Deletedatabase();

                schedulePreference.tableidremove();

                Toasty.error(getApplicationContext(), "Order is Cancel", Toast.LENGTH_SHORT).show();

                schedulePreference.checkupdateremove();

                Intent cancelbtn = new Intent(Cart.this, MainActivity.class);
                startActivity(cancelbtn);
                finish();
                break;

            case R.id.backarrows:
                super.onBackPressed();
                break;

        }
    }


    public void Jsonprint() {


//        ArrayList<OfflineCheckModel> offlineCheckModels = new ArrayList<>(); // for ofline
//        ArrayList<OfflineAddonsModel> offlineAddonsModels = new ArrayList<>(); //for Offline uses


        try {

            for (int i = 0; i < offlineCheckModels.size(); i++) {
                OfflineCheckModel offlineCheckModel1 = offlineCheckModels.get(i);
                JSONObject jsonObject1 = new JSONObject();
                parent.put(jsonObject1);
                jsonObject1.put("ProductsID", Integer.parseInt(offlineCheckModel1.getProduct_ID()));
                jsonObject1.put("quantity", Integer.parseInt(offlineCheckModel1.getCart_UNIT()));
                jsonObject1.put("itemnote", "This is Note");
                jsonObject1.put("variantid", Integer.parseInt(offlineCheckModel1.getVarient_ID()));

                float addonsvalue = Float.parseFloat(offlineCheckModel1.getAddonstotal());
                System.out.println(addonsvalue);
                if (addonsvalue == 0.0) {
                    jsonObject1.put("addons", Integer.parseInt("0"));
                } else if (addonsvalue != 0) {

                    jsonObject1.put("addons", Integer.parseInt("1"));
                    JSONArray addonesArr = new JSONArray();
                    jsonObject1.put("addonsinfo", addonesArr);

                    for (int jj = 0; jj < offlineAddonsModels.size(); jj++) {

                        int varientid = Integer.parseInt(offlineCheckModel1.getVarient_ID());

                        OfflineAddonsModel offlineAddonsModel = offlineAddonsModels.get(jj);

                        if (varientid == offlineAddonsModel.getVarientid()) {

                            JSONObject jsonObject = new JSONObject();
                            addonesArr.put(jsonObject);
                            jsonObject.put("addonsquantity", offlineAddonsModel.getAddonsquantity());
                            jsonObject.put("addonsprice", offlineAddonsModel.getAddonsprice());
                            jsonObject.put("addonsid", offlineAddonsModel.getAddonsid());

                        }
                    }

                }
            }
            System.out.println(parent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void ShowDetail() {

        cartListener = new CartListener() {
            @Override
            public void Cartlistener(int Foodtotal, int Varientid, int foodqty) {

                /*checklist++;
                System.out.println("Check List value is" + checklist);*/
//                carttot.setText(String.valueOf(Foodtotal));
                etfoodtotal = Foodtotal;

                System.out.println("Varient id" + Varientid + "Food Quantity" + foodqty);

                database.updatefood(Varientid, foodqty);


                /*offlineCheckModels.removeAll(offlineCheckModels);
                offlineCheckModels.addAll(database.getAllCartList()); // Update database*/

                TotalPrice();

               /* newtotal = 0;

                for (int i = 0; i < offlineCheckModels.size(); i++) {
                    OfflineCheckModel offlineCheckModel1 = offlineCheckModels.get(i);
                    int qty = Integer.parseInt(offlineCheckModel1.getCart_UNIT());
                    float price = Float.parseFloat(offlineCheckModel1.getCart_UNITPRICE());

                    newtotal += price * qty;
                    float addons = Float.parseFloat(offlineCheckModel1.getAddonstotal());
                    newtotal += addons;

                    System.out.println("New Total is" + newtotal);
                }

                carttot.setText(String.valueOf(newtotal));*/

            }
        };

    }

    public void TotalPrice() {

        offlineCheckModels.removeAll(offlineCheckModels);
        offlineCheckModels.addAll(database.getAllCartList()); // Update database

        newtotal = 0;


        for (int i = 0; i < offlineCheckModels.size(); i++) {
            OfflineCheckModel offlineCheckModel1 = offlineCheckModels.get(i);
            int qty = Integer.parseInt(offlineCheckModel1.getCart_UNIT());
            float price = Float.parseFloat(offlineCheckModel1.getCart_UNITPRICE());

            newtotal += price * qty;
            float addons = Float.parseFloat(offlineCheckModel1.getAddonstotal());
            newtotal += addons;

            System.out.println("New Total is" + newtotal);


        }

        carttot.setText(String.valueOf(newtotal));
    }

    public void FetchDataUpdate() {

//        TotalPrice();

        id = 168;
        TypeID = 1;
        VatAmount = 0;
        Tableid = Integer.parseInt(stableid);
        CustomerID = 1;
//        Total = etfoodtotal;
        Total = (int) newtotal;
//        Grandtotal = etfoodtotal;
        Grandtotal = (int) newtotal;
        System.out.println("Sending total Value for Cartones" + newtotal);
        foodinfo = parent;


        System.out.println("Fetching Json array" + foodinfo + "Table Name" + Tableid);

        APIService apiService = (APIService) ApiHelper.getInstance().getService(APIService.class);
        call = apiService.UpdateFoodCart(id, VatAmount, sorderid, Tableid, Total, Grandtotal, foodinfo);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                Toast.makeText(getBaseContext(), "Food is Added to cart", Toast.LENGTH_SHORT).show();
//                Toasty.success(getApplicationContext(), "Order is Placed", Toast.LENGTH_SHORT).show();

                database.Deletedatabase();

                schedulePreference.tableidremove();
                schedulePreference.checkupdateremove();
                schedulePreference.orderidremove();

                Intent i = new Intent(Cart.this, MainActivity.class);
                startActivity(i);
                finish();

//                getSupportFragmentManager().beginTransaction().replace(R.id.container, new TableHome(), "Food Menu").addToBackStack(null).addToBackStack(null).commitAllowingStateLoss();

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Internet connection failed !", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void FetchData() {

        id = 168;
        TypeID = 1;
        VatAmount = 0;
        Tableid = Integer.parseInt(stableid);
        CustomerID = 1;
        Total = (int) newtotal;
        Grandtotal = (int) newtotal;
        foodinfo = parent;


        System.out.println("Fetching Json array" + foodinfo + "Table Name" + Tableid);

        APIService apiService = (APIService) ApiHelper.getInstance().getService(APIService.class);
        call = apiService.FoodCart(id, TypeID, VatAmount, Tableid, CustomerID, Total, Grandtotal, foodinfo);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                Toast.makeText(getBaseContext(), "Food is Added to cart", Toast.LENGTH_SHORT).show();
                Toasty.success(getApplicationContext(), "Order is Placed", Toast.LENGTH_SHORT).show();

                database.Deletedatabase();

                schedulePreference.tableidremove();

                Intent i = new Intent(Cart.this, MainActivity.class);
                startActivity(i);
                finish();


//                getSupportFragmentManager().beginTransaction().replace(R.id.container, new TableHome(), "Food Menu").addToBackStack(null).addToBackStack(null).commitAllowingStateLoss();

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Internet connection failed !", Toast.LENGTH_SHORT).show();

            }
        });

    }


}