package com.example.nepalaya.CategoryList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.nepalaya.ApiService.APIService;
import com.example.nepalaya.ApiService.ApiHelper;
import com.example.nepalaya.CategoryList.FoodList.FoodHome;
import com.example.nepalaya.R;
import com.example.nepalaya.Table.TableModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category_Menu extends Fragment {

    Call<JsonElement> call;

    int id = 168;
    String android = "android";
    ArrayList<CategoryModel> categoryModels;
    Category_Listener category_listener;

    Category_Food_Adapter category_food_adapter;

    int CatID;
    String CatName, CatImage;

    @BindView(R.id.recycler_view)
    RecyclerView categorymenu;

    @BindView(R.id.shimmer_layout)
    ShimmerFrameLayout shimmerFrameLayout;

    @BindView(R.id.search_view)
    EditText searchviewcat;

    int tableid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_food__menu, container, false);

        ButterKnife.bind(this, rootview);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        categorymenu.setLayoutManager(layoutManager);
        categorymenu.setHasFixedSize(true);

        Bundle bundle = getArguments();
        tableid = Integer.parseInt(bundle.getString("table_id"));

        getData();
        ShowDetail();

        searchviewcat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                category_food_adapter.getFilter().filter(searchviewcat.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return rootview;

    }

    public void getData() {
        APIService apiService = (APIService) ApiHelper.getInstance().getService(APIService.class);
        call = apiService.categoryList(id, android);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JSONObject data = new JSONObject();
                if (response.isSuccessful()) {
                    String responseValue = response.body().toString();
                    if (responseValue != null) {

                        categorymenu.setVisibility(View.VISIBLE); //un-hide recyclerView
                        shimmerFrameLayout.stopShimmer(); //stop shimmer effect
                        shimmerFrameLayout.setVisibility(View.GONE); //hide the shimmer effect

                        try {
                            JSONObject jsonObject = new JSONObject(responseValue);
                            String responseCode = jsonObject.getString("status");
                            if (responseCode.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                categoryModels = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsnItems = jsonArray.getJSONObject(i);

                                    CatID = jsnItems.getInt("CategoryID");
                                    CatName = jsnItems.getString("Name");
                                    CatImage = jsnItems.getString("categoryimage");

                                    categoryModels.add(new CategoryModel(CatID, CatName, CatImage));
                                }

                                category_food_adapter = new Category_Food_Adapter(categoryModels, getContext(), category_listener);
                                categorymenu.setAdapter(category_food_adapter);
                         /*       searchView.setOnQueryTextListener(new SearchView.OnQueryTexstListener() {
                                    @Override
                                    public boolean onQueryTextSubmit(String query) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onQueryTextChange(String newText) {
                                        foodMenuAdapter.getFilter().filter(newText);
                                        //searchView.clearFocus();
                                        return false;
                                    }
                                });*/


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
//                    progressBar.setVisibility(View.VISIBLE);
//                    categorymenu.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "hey error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toasty.error(getContext(), t.getMessage(), Toasty.LENGTH_LONG).show();
            }
        });
    }

    public void ShowDetail() {
        category_listener = new Category_Listener() {
            @Override
            public void OnitemClicked(int Categoryid, String foodname) {
                FoodHome foodHome = new FoodHome();

                Bundle bundle = new Bundle();
                bundle.putInt("Categoryid", Categoryid);
                bundle.putInt("tableid", tableid);
                bundle.putString("foodlist", foodname);

                foodHome.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager != null ? fragmentManager.beginTransaction() : null;
                assert fragmentTransaction != null;

                Intent gotofoodhome = new Intent(getActivity(), FoodHome.class);
                gotofoodhome.putExtras(bundle);
                startActivity(gotofoodhome);
            }
        };

    }

}
