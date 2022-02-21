package com.example.nepalaya.Floor;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.nepalaya.Floor.Listener.FloorOnClickListener;
import com.example.nepalaya.R;
import com.example.nepalaya.SchedulePreference;
import com.example.nepalaya.Table.TableHome;
import com.google.firebase.database.annotations.Nullable;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FloorHome extends Fragment {

    ArrayList<FloorModel> floorModels;
    Context context;
    Call<JsonElement> call;
    int id;
    SchedulePreference schedulePreference;
    String tbfloorid, floorName, flooridd;
    RecyclerView floor_recycleview;
    FloorAdapter floorAdapter;
    FloorOnClickListener floorOnClickListener;


    @Nullable
    @Override
    public View onCreateView(@Nonnull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_floor, container, false);


        schedulePreference = new SchedulePreference(getContext());

        id = Integer.parseInt(schedulePreference.getUserId()); // UNCHeck the value
        floor_recycleview = rootview.findViewById(R.id.floor_recycleview);


        flooridd = schedulePreference.getFloorid();
        System.out.println("floor id from sharedpreference" + flooridd);

        if (flooridd != null) {

            Category_Menu category_menu = new Category_Menu();
            Bundle floorbundle = new Bundle();

            category_menu.setArguments(floorbundle);

            floorbundle.putString("floor_id", flooridd);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager != null ? fragmentManager.beginTransaction() : null;
            assert fragmentTransaction != null;
            fragmentTransaction.replace(R.id.container, category_menu).addToBackStack("back").commit();
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        floor_recycleview.setLayoutManager(mLayoutManager);

        getFloordata();
        showFloorDetails();
        return rootview;
    }

    public void getFloordata() {
        APIService apiService = (APIService) ApiHelper.getInstance().getService(APIService.class);
        call = apiService.FloorList();
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JSONObject data = new JSONObject();
                if (response.isSuccessful()) {
                    String responseValue = response.body().toString();
                    if (responseValue != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseValue);
                            String responseCode = jsonObject.getString("status");
                            if (responseCode.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                floorModels = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsnItems = jsonArray.getJSONObject(i);
                                    tbfloorid = jsnItems.getString("tbfloorid");
                                    floorName = jsnItems.getString("floorName");


                                    floorModels.add(new FloorModel(tbfloorid, floorName));
                                }
                                floorAdapter = new FloorAdapter(floorModels, getContext(), floorOnClickListener);
                                floor_recycleview.setAdapter(floorAdapter);

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
                Toasty.error(getActivity(), t.getMessage(), Toasty.LENGTH_SHORT).show();
            }


        });
    }

    public void showFloorDetails() {
        floorOnClickListener = new FloorOnClickListener() {
            @Override
            public void onItemClick(String floor_id, String floor_name) {
//                Category_Menu category_menu = new Category_Menu();
               TableHome tableHome = new TableHome();

                Bundle floorBundle = new Bundle();
                floorBundle.putString("floor_id", floor_id);
                floorBundle.putString("floorName", floor_name);

                tableHome.setArguments(floorBundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager != null ? fragmentManager.beginTransaction() : null;
                assert fragmentTransaction != null;
                fragmentTransaction.replace(R.id.container, tableHome).addToBackStack("back").commit();
            }
        };

    }
}
