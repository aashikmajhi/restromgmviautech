package com.example.nepalaya.CategoryList.FoodList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nepalaya.ApiService.APIService;
import com.example.nepalaya.ApiService.ApiHelper;
import com.example.nepalaya.Cart.Cart;
import com.example.nepalaya.CategoryList.AddToCartDatabase.Database;
import com.example.nepalaya.CategoryList.AddToCartDatabase.OfflineAddonsModel;
import com.example.nepalaya.CategoryList.AddToCartDatabase.OfflineCheckModel;
import com.example.nepalaya.CategoryList.CategoryModel;
import com.example.nepalaya.FoodHome.AddsonesModel;

import com.example.nepalaya.FoodHome.DialogAddsone.Addtoccardlistener;
import com.example.nepalaya.FoodHome.DialogAddsone.DialogAdapter;
import com.example.nepalaya.FoodHome.Listener.AddToCardListener;
import com.example.nepalaya.FoodHome.Listener.ListenerList;
import com.example.nepalaya.R;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodHome extends AppCompatActivity {
    @BindView(R.id.total_number)
    TextView totalnumber;

    @BindView(R.id.view_cart_rs)
    TextView viewcartrs;

    @BindView(R.id.cart_layout)
    RelativeLayout viewcart;

    @BindView(R.id.foodlistname)
    TextView foodlistname;

    private List<OfflineCheckModel> offlineCheckModels = new ArrayList<>(); // for ofline
    private List<OfflineAddonsModel> offlineAddonsModels = new ArrayList<>(); // for ofline
    ArrayList<AddsonesModel> addsonesModels;

    float sum = 0;

    Call<JsonElement> call;

    String Categoryid;

    String id = "168";

    int varientid;

    Database database;

    int addons;

    DialogAdapter dialogAdapter;

    FoodListAdapter foodListAdapter;

    AddToCardListener addToCardListener;

    Addtoccardlistener addtoccardlistener;

    ListenerList listenerList;

    int cartquatity, cartquantityval, passprodutidfh, quanitynegfh, current_valueggh, passvarientid;

    Float cartPrice;
    Boolean clickvaluefh;

    @BindView(R.id.Food_List)
    RecyclerView food_list;

    ArrayList<FoodListModel> foodListModels;
    ArrayList<CategoryModel> categoryModels;

    String Variant_id, VarientName, VarientPrice, ProductImage, ProductsID, ProductName, destcription;
    int addones;

    java.util.HashMap<Integer, Integer> HashMap = new HashMap<Integer, Integer>();

    String VarientNamecart;

    String addonsid, add_on_name, addonsprice;

    float AddonePriceDetailfh;
    int productaddons, addonesidd, addonsquanity;
    Boolean addonescheckvalue;
    String AddonesName;
    String catname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_home);
        ButterKnife.bind(this);

        database = new Database(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        Categoryid = String.valueOf(bundle.getInt("Categoryid"));
        int tablename = bundle.getInt("tableid");
        catname = bundle.getString("foodlist");


        foodlistname.setText(String.valueOf(catname));

        System.out.println("Cat Name Is ----------" + catname + "Table name is" + tablename);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        food_list.setLayoutManager(layoutManager);

        viewcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(FoodHome.this, "Cart layout click", Toast.LENGTH_SHORT).show();
                Intent gotocart = new Intent(FoodHome.this, Cart.class);
                Bundle bundle = new Bundle();
                /*bundle.putString("cartquatity", String.valueOf(cartquatity));
                bundle.putString("cartquantityval", String.valueOf(cartquantityval));
                bundle.putString("passprodutidfh", String.valueOf(passprodutidfh));
                bundle.putString("quanitynegfh", String.valueOf(quanitynegfh));*/
                bundle.putString("tablename", String.valueOf(tablename));
//                bundle.putFloat("cartPrice", cartPrice);
                gotocart.putExtras(bundle);
                startActivity(gotocart);
            }
        });

        getData();
        showCartValue();
        ShowDetail();

        offlineCheckModels.addAll(database.getAllCartList());
        CountDataBase();
    }

    public void setArguments(Bundle bundle) {
    }

    @OnClick({R.id.fhbackarrow})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.fhbackarrow:
                super.onBackPressed();
                break;
        }
    }


    public void getData() {
        APIService apiService = (APIService) ApiHelper.getInstance().getService(APIService.class);
        call = apiService.FoodList(id, Categoryid);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JSONObject data = new JSONObject();
                if (response.isSuccessful()) {
                    String responseValue = response.body().toString();
                    if (responseValue != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseValue);
                            JSONObject dataouter = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = dataouter.getJSONArray("foodinfo");

                            foodListModels = new ArrayList<>();
//                            addsonesModels = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsnItems = jsonArray.getJSONObject(i);

                                ProductsID = jsnItems.getString("ProductsID");
                                ProductName = jsnItems.getString("ProductName");
                                ProductImage = jsnItems.getString("ProductImage");
                                destcription = jsnItems.getString("destcription");
                                Variant_id = jsnItems.getString("variantid");
                                VarientName = jsnItems.getString("variantName");
                                VarientPrice = jsnItems.getString("price");
                                addones = jsnItems.getInt("addons");
                                String qnty = "0";

                                foodListModels.add(new FoodListModel(ProductsID, Variant_id, VarientName, ProductName, ProductImage, destcription, VarientPrice, addones, qnty));
//                                System.out.println("Addones Models Array Lists" + foodListModels);
//                                System.out.println("foodmodels Models Array Lists" + foodModels);

                            /*    foodListAdapter = new FoodListAdapter(foodListModels, getBaseContext(), addToCardListener, offlineCheckModels,listenerList);
                                food_list.setAdapter(foodListAdapter);
*/
                             /*   varients = jsnItems.getString("varients");

                                if (varients == "1") {
                                    JSONArray jsonArray1 = jsnItems.getJSONArray("varientInfo");
                                    varientModels = new ArrayList<>();
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        JSONObject jsn = jsonArray1.getJSONObject(j);

                                        Vvariant_id = jsn.getString("Vvariant_id");
                                        Vvariant_name = jsn.getString("Vvariant_name");
                                        Vprice = jsn.getString("Vprice");

                                        varientModels.add(new VarientModel(ProductsID, Vvariant_id, Vvariant_name, Vprice));
                                    }
                                }*/

                                qnty = qnty;
                                int addsvalue = addons;
                                if (addsvalue == 1) {

                                    JSONArray jsonArray1 = jsnItems.getJSONArray("addonsinfo");
                                    for (int j = 0; j < jsonArray1.length(); j++) {

                                        JSONObject jsn = jsonArray1.getJSONObject(j);

                                        addonsid = jsn.getString("addonsid");
                                        add_on_name = jsn.getString("add_on_name");
                                        addonsprice = jsn.getString("addonsprice");

                                        addsonesModels.add(new AddsonesModel(ProductsID, addonsid, add_on_name, addonsprice));

                                    }
                                }

                            }
//                            foodAdapter = new FoodAdapter(foodModels, getBaseContext(), listener, addsonesModels, addToCardListener);
                         /*   foodAdapter = new FoodAdapter(foodModels, getBaseContext(), listener, addToCardListener);
                            food_recycle.setAdapter(foodAdapter);*/

                            foodListAdapter = new FoodListAdapter(foodListModels, getBaseContext(), addToCardListener, offlineCheckModels, listenerList);
                            food_list.setAdapter(foodListAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {

                    Toast.makeText(FoodHome.this, "hey error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toasty.error(FoodHome.this, t.getMessage(), Toasty.LENGTH_LONG).show();
            }
        });
    }

    public void ShowDetail() {

        listenerList = new ListenerList() {
            @Override
            public void onItemClicked(String ProductsID) {
                varientid = Integer.parseInt(ProductsID);
                System.out.println("Addones pp id" + varientid);
                System.out.println("the product id which is from addones" + varientid);

         /*       AddoneDialognew addsoneDialog = new AddoneDialognew();
                Bundle bundle = new Bundle();
                bundle.putString("ProductsID", ProductsID);*/

              /*  addsoneDialog.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager != null ? fragmentManager.beginTransaction() : null;
                assert fragmentTransaction != null;
                fragmentTransaction.replace(R.id.container, addsoneDialog).addToBackStack("back").commit();*/

      /*          Intent gotoaddones = new Intent(FoodHome.this, AddoneDialognew.class);
                gotoaddones.putExtras(bundle);
                startActivity(gotoaddones);*/

                AlertDialog.Builder builder = new AlertDialog.Builder(FoodHome.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(FoodHome.this).inflate(R.layout.dialog_addsone, viewGroup, false);
                RecyclerView addonerecycle = dialogView.findViewById(R.id.addonerecycle);
                TextView btn_next = dialogView.findViewById(R.id.btn_next);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FoodHome.this);
                addonerecycle.setLayoutManager(layoutManager);

              /*  for (int ggg = 0; ggg < foodModels.size(); ggg++) {
                    FoodModel gg = foodModels.get(ggg);
                    int addones = gg.getAddones();
                    int productid = Integer.parseInt(gg.getProductsID());

                    if (addones == 1 & Productidadd == productid) {
                        dialogAdapter = new DialogAdapter(addsonesModels, getBaseContext(), addtoccardlistener, Productidadd);
                        addonerecycle.setAdapter(dialogAdapter);
//                        mainlayout.setBackgroundColor(android.graphics.Color.TRANSPARENT);

                        for (int jj = 0; jj < addsonesModels.size(); jj++) {
                            AddsonesModel fff = addsonesModels.get(jj);
//                            int productid = Integer.parseInt(fff.getProdID());
                        }

                        dialogAdapter = new DialogAdapter(addsonesModels, getBaseContext(), addtoccardlistener, Productidadd);
                        addonerecycle.setAdapter(dialogAdapter);
                    }
                }*/

                APIService apiService = (APIService) ApiHelper.getInstance().getService(APIService.class);
                call = apiService.FoodList(id, Categoryid);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        JSONObject data = new JSONObject();
                        if (response.isSuccessful()) {
                            String responseValue = response.body().toString();
                            if (responseValue != null) {
                                try {
                                    JSONObject jsonObject = new JSONObject(responseValue);
                                    JSONObject dataouter = jsonObject.getJSONObject("data");

                                    JSONArray jsonArray = dataouter.getJSONArray("foodinfo");

//                            foodModels = new ArrayList<>();
                                    addsonesModels = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsnItems = jsonArray.getJSONObject(i);

                                        String variantid = jsnItems.getString("variantid");

                                        addons = jsnItems.getInt("addons");

                                        int addsvalue = addons;
                                        int apivarientid = Integer.parseInt(variantid);

                                        System.out.println("Product id from addons" + apivarientid);

                                        System.out.println("Pp ID from addones value" + variantid);

                                        if (addsvalue == 1 && varientid == apivarientid) {

                                            JSONArray jsonArray1 = jsnItems.getJSONArray("addonsinfo");
                                            for (int j = 0; j < jsonArray1.length(); j++) {
                                                JSONObject jsn = jsonArray1.getJSONObject(j);

                                                addonsid = jsn.getString("addonsid");
                                                add_on_name = jsn.getString("add_on_name");
                                                addonsprice = jsn.getString("addonsprice");

                                                addsonesModels.add(new AddsonesModel(ProductsID, addonsid, add_on_name, addonsprice));
                                            }
                                        }
                                        System.out.println("Array List of product id" + addsonesModels);
//                                foodModels.add(new FoodModel(ProductsID, ProductName, ProductImage, destcription, price, addons, addonsid, add_on_name, addonsprice, qnty));
                                    }
                                    dialogAdapter = new DialogAdapter(addsonesModels, getBaseContext(), addtoccardlistener, varientid, foodListModels);
                                    addonerecycle.setAdapter(dialogAdapter);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(getBaseContext(), "hey error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        Toasty.error(getBaseContext(), t.getMessage(), Toasty.LENGTH_LONG).show();
                    }
                });

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btn_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sum += AddonePriceDetailfh;
                        HashMap.put(productaddons, (int) AddonePriceDetailfh);
//                        HashMap.put(Productidadd, (int) AddonePriceDetailfh);
                        alertDialog.cancel();
                        viewcartrs.setText(String.valueOf("Rs." + sum));
                    }
                });

            }
        };

        addtoccardlistener = new Addtoccardlistener() {
            @Override
            public void AddListerner(int price, int productidaddons, int addonesid, Boolean clickvalue, String add_on_name, int quantity) {

                AddonePriceDetailfh = price;

                productaddons = productidaddons;

                addonesidd = addonesid;

                addonescheckvalue = clickvalue;

                AddonesName = add_on_name;
                addonsquanity = quantity;

                System.out.println("Price" + AddonePriceDetailfh + "productid" + productaddons + "Addonesid" + addonesidd + "Addonescheckvalue" + clickvalue +
                        "AddonesName" + AddonesName + "quantity" + addonsquanity);
                System.out.println(HashMap);

                database.updateforAddones(productidaddons, AddonesName, AddonePriceDetailfh);

                if (addonescheckvalue == true & addonsquanity < 2) {
                    AddaddonestoDatabase();
                    System.out.println("Creating the database");

                } else if (addonescheckvalue == true & addonsquanity >= 2) {
                    database.UpdateDatabaseAddones(addonesidd, addonsquanity);
                    System.out.println("Update the database");


                } else if (addonescheckvalue == false) {
                    database.deleteAddons(addonesidd);
                    System.out.println("Delete the database");

                }

            }
        };
    }


    public void AddaddonestoDatabase() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Varientid", productaddons);
        contentValues.put("Addonsquantity", addonsquanity);
        contentValues.put("addonsprice", AddonePriceDetailfh);
        contentValues.put("addonsid", addonesidd);

        database.addadone(contentValues);

    }


    public void AddToDatabase() {
        // Adding Data into Database
        ContentValues contentValues = new ContentValues();
        contentValues.put("Varient_ID", passprodutidfh);
        contentValues.put("Cart_INFO", VarientNamecart);
        contentValues.put("Cart_UNIT", cartquantityval);
        contentValues.put("Total_Price", passprodutidfh);
        contentValues.put("Notes", passprodutidfh);
        contentValues.put("Product_ID", passvarientid);
        contentValues.put("addonsid", passprodutidfh);
        contentValues.put("add_on_name", "");
        contentValues.put("addonsprice", passprodutidfh);
        contentValues.put("addonstotal", "0");
        contentValues.put("Cart_UNITPRICE", cartPrice);

        database.insertfood(contentValues);

        System.out.println("CartPrice" + cartPrice + "CaertQuantity" + cartquantityval + "cartquantity" + cartquatity);
    }

    public void showCartValue() {


        addToCardListener = new AddToCardListener() {
            @Override
            public void AddListerner(int Quantity, Float Price, int quantityval, int passprodutid, int quanityneg, Boolean clickvalue, int current_valuegg, String VarientName, int varientidd) {
                cartPrice = Price; // Price
                cartquantityval = Quantity; // total Quantity
                cartquatity = quantityval; // cart total
                passprodutidfh = varientidd; //Its Varient ID
                quanitynegfh = quanityneg; // total qunaity
                clickvaluefh = clickvalue;
                current_valueggh = current_valuegg; // check and for on check
                VarientNamecart = VarientName;
                passvarientid = passprodutid; //Productid


                System.out.println("passvarient id" + passprodutidfh);
                System.out.println("product id" + passvarientid);

                System.out.println("YOUR VARIENT NAME IS:" + VarientNamecart);
                System.out.println("YOUR Cart Price IS:" + cartPrice);

                System.out.println("Cart Quantity Value is: " + cartquantityval);

                System.out.println("Pass ProductId" + passprodutid);

                if (cartquantityval > 1 & quanitynegfh != 0 & current_valueggh != 1) {

                    database.updatefood(passprodutidfh, cartquantityval);
                    System.out.println("Yes it is Greater");

                } else if (cartquantityval > 1 & current_valueggh == 1) {

                    database.updatefood(passprodutidfh, cartquantityval);
                    System.out.println("Yes it is Greater");

                } else {

                    AddToDatabase();

                }
//                totalnumber.setText(String.valueOf(cartquatity));
                CountDataBase();


                for (int jj = 0; jj < foodListModels.size(); jj++) {
                    FoodListModel fff = foodListModels.get(jj);
                    int productid = Integer.parseInt(fff.getProductsID());
                    int varientid = Integer.parseInt(fff.getVarientid());

                    int addoneval = fff.getAddones();


                    if (varientid == passprodutidfh & clickvaluefh == true) {

                        if (quanitynegfh == 0 & cartquantityval > 1 & current_valueggh == 2) {
                            float price = Float.parseFloat(fff.getPrice());
                            sum += price * cartquantityval;
                            System.out.println("My total sum" + sum);
                            viewcartrs.setText(String.valueOf("Rs." + sum));
                            System.out.println("Before Hashmap Value" + HashMap);
                        } else {
                            float price = Float.parseFloat(fff.getPrice());
                            sum += price * 1;
                            System.out.println("My total sum" + sum);
                            viewcartrs.setText(String.valueOf("Rs." + sum));
                            System.out.println("Before Hashmap Value" + HashMap);
                        }
                    } else if (varientid == passprodutidfh & clickvaluefh == false) {
                        if (quanitynegfh == 0 & cartquantityval > 1 & current_valueggh == 1 & addoneval != 1) {
                            float price = Float.parseFloat(fff.getPrice());
                            sum = sum - (price * cartquantityval);
                            System.out.println("My total sum" + sum);
                            viewcartrs.setText(String.valueOf("Rs." + sum));
                            System.out.println(addoneval);
                            System.out.println("Addones bha ko thau ma aayo mathi ko");
                            database.deleteFood(String.valueOf(varientid));

                        } else if (addoneval == 1) {
                            if (HashMap.containsKey(varientid) & quanitynegfh == 0) {
                                System.out.println("Hashmap value" + productid);
                                float price = Float.parseFloat(fff.getPrice());
                                float nresum = sum - (price * cartquantityval);
                                int addonsprice = HashMap.get(varientid);
                                sum = nresum - addonsprice;

                                System.out.println("Before Hashmap Value" + HashMap);
                                System.out.println("My total sum" + sum);
                                viewcartrs.setText(String.valueOf("Rs." + sum));
                                HashMap.remove(varientid);
                                database.deleteFood(String.valueOf(varientid));
                                System.out.println("After Hashmap value" + HashMap);
                                database.deletealladdons(varientid);

                            } else if (quanitynegfh == 0) { // IT should be change with the shared prefenece or
                                float price = Float.parseFloat(fff.getPrice());
                                sum = sum - (price * cartquantityval);
                                System.out.println("My total sum" + sum);
                                viewcartrs.setText(String.valueOf("Rs." + sum));
                                System.out.println("Addones bha ko thau ma aayo");
                                database.deleteFood(String.valueOf(varientid));

                                database.deletealladdons(varientid);
                            } else {
                                float price = Float.parseFloat(fff.getPrice());
                                sum = sum - (price * 1);
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

                            if (quanityneg == 0) {
                                database.deleteFood(String.valueOf(varientid));
                            }
                        }

                    } else if (varientid == passprodutidfh & clickvaluefh == false & addoneval != 1) {
                        float price = Float.parseFloat(fff.getPrice());
                        sum = sum - (price * cartquantityval);
                        System.out.println("My total sum" + sum);
                        viewcartrs.setText(String.valueOf("Rs." + sum));
                        System.out.println("Addones bha ko thau ma aayo");

                        database.deleteFood(String.valueOf(varientid));
                    }
                }
            }
        };
    }

    public void CountDataBase() {

//        offlineCheckModels.addAll(database.getAllCartList());

        if (offlineCheckModels != null) {

            int countofline = 0;


            for (int jj = 0; jj < offlineCheckModels.size(); jj++) {
                OfflineCheckModel ff = offlineCheckModels.get(jj);

                int offvarient = Integer.parseInt(ff.getCart_UNIT());
                int quantityy = Integer.parseInt(ff.getCart_UNIT());
                float priceyy = Float.parseFloat(ff.getCart_UNITPRICE());
                String CartVarientNameyy = ff.getCart_INFO();

                countofline++;
                System.out.println("Value of i is -----" + countofline);
                totalnumber.setText(String.valueOf(countofline));


            }
        }
    }

}