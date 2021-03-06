package com.example.nepalaya.Table;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nepalaya.ApiService.APIService;
import com.example.nepalaya.ApiService.ApiHelper;
import com.example.nepalaya.CategoryList.Category_Menu;
import com.example.nepalaya.R;
import com.example.nepalaya.SchedulePreference;
import com.example.nepalaya.Table.Listener.TableOnClickListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableHome extends Fragment {


    Call<JsonElement> call;
    SchedulePreference schedulePreference;
    Context context;
    String id;
    ArrayList<TableModel> tableModels;
    String TableId, TableName;
    TableAdapter tableAdapter;
    RecyclerView table_recycleview;
    EditText searchViewtable;
    TableOnClickListener tableOnClickListener;
    String tbfloorid, floorID, floorIdfromFloorHome, floor_id;
    String tableidd;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_table, container, false);

        schedulePreference = new SchedulePreference(getContext());

        id = schedulePreference.getUserId(); // UNCHeck the value
        table_recycleview = rootview.findViewById(R.id.table_recycleview);


        tbfloorid = schedulePreference.getFloorid();
        tableidd = schedulePreference.gettableid();
        System.out.println("table id from sharedpreference" + tableidd);

        Bundle bundle = getArguments();
        floorIdfromFloorHome = bundle.getString("floor_id");


        if (tableidd != null) {
//            String tableid = getArguments().getString("table_id");


            Category_Menu category_menu = new Category_Menu();

            Bundle bd = new Bundle();

            bd.putString("floor_id", floor_id);
            bd.putString("table_id", tableidd);


            category_menu.setArguments(bd);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager != null ? fragmentManager.beginTransaction() : null;
            assert fragmentTransaction != null;
//            fragmentTransaction.replace(R.id.container, category_menu).addToBackStack("back").commit();
        }


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        table_recycleview.setLayoutManager(mLayoutManager);

        searchViewtable = rootview.findViewById(R.id.floorsearchedit);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        searchViewtable.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tableAdapter.getFilter().filter(searchViewtable.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Bundle bd = getArguments();
        floorID = bd.getString("floor_id");

        getData();
        ShowDetail();

        return rootview;
    }

    public void getData() {
        APIService apiService = (APIService) ApiHelper.getInstance().getService(APIService.class);
        call = apiService.TableListbyFloorID(floorIdfromFloorHome);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JSONObject data = new JSONObject();
                if (response.isSuccessful()) {
                    String responseValue = response.body().toString();
                    if (responseValue != null) {
//                        view.setVisibility(View.VISIBLE); //un-hide recyclerView
//                        shimmerFrameLayout.stopShimmer(); //stop shimmer effect
//                        shimmerFrameLayout.setVisibility(View.GONE); //hide the shimmer effect
                        try {
                            JSONObject jsonObject = new JSONObject(responseValue);
                            String responseCode = jsonObject.getString("status");
                            if (responseCode.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                tableModels = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsnItems = jsonArray.getJSONObject(i);
                                    TableId = jsnItems.getString("tableid");
                                    TableName = jsnItems.getString("tablename");


                                    tableModels.add(new TableModel(TableId, TableName));
                                }
                                tableAdapter = new TableAdapter(tableModels, getContext(), tableOnClickListener);
                                table_recycleview.setAdapter(tableAdapter);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {

                    Toast.makeText(getActivity(), "hey error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toasty.error(getActivity(), t.getMessage(), Toasty.LENGTH_LONG).show();
            }
        });
    }

    public void ShowDetail() {
        tableOnClickListener = new TableOnClickListener() {
            @Override
            public void onItemClick(String table_id, String table_name) {
                /*  FoodHome foodHome = new FoodHome();*/

                Category_Menu category_menu = new Category_Menu();

                Bundle bd = new Bundle();

                bd.putString("floor_id", floorID);
                bd.putString("table_id", table_id);
                bd.putInt("table_name", Integer.parseInt(table_name));
                schedulePreference.setTableid(table_id);

//                foodHome.setArguments(bd);
                category_menu.setArguments(bd);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager != null ? fragmentManager.beginTransaction() : null;
                assert fragmentTransaction != null;
                fragmentTransaction.replace(R.id.container, category_menu).addToBackStack("back").commit();


             /*   Intent gotofoodhome = new Intent(getActivity(), FoodHome.class);
                gotofoodhome.putExtras(bd);
                startActivity(gotofoodhome);*/


            }
        };
    }

}
