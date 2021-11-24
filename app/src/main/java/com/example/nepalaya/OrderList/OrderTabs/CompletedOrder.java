package com.example.nepalaya.OrderList.OrderTabs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nepalaya.ApiService.APIService;
import com.example.nepalaya.ApiService.ApiHelper;
import com.example.nepalaya.OrderList.OrderTabs.Completed.CompletedAdapter;
import com.example.nepalaya.OrderList.OrderTabs.Completed.Completedmodel;
import com.example.nepalaya.OrderList.OrderTabs.Pending.PendingAdapter;
import com.example.nepalaya.OrderList.OrderTabs.Pending.PendingModel;
import com.example.nepalaya.R;
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

public class CompletedOrder extends Fragment {


    @BindView(R.id.complete_recycle)
    RecyclerView completedrecycleview;

    Call<JsonElement> call;
    ProgressDialog progressDialog;

    ArrayList<Completedmodel> completedmodels;

    String orderid, CustomerName, TableName, OrderDate, TotalAmount;

    CompletedAdapter completedAdapter;

    public CompletedOrder() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.completedorder, container, false);

        ButterKnife.bind(this, rootview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        completedrecycleview.setLayoutManager(layoutManager);

        progressdialog();

        getData();

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
        int start = 0;
        APIService apiService = (APIService) ApiHelper.getInstance().getService(APIService.class);
        call = apiService.Completedlist(id, start);
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
                            JSONObject dataouter = jsonObject.getJSONObject("data");

                            JSONArray jsonArray = dataouter.getJSONArray("orderinfo");

                            completedmodels = new ArrayList<>();
                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(j);

                                orderid = jsonObject2.getString("order_id");
                                CustomerName = jsonObject2.getString("CustomerName");
                                TableName = jsonObject2.getString("TableName");
                                OrderDate = jsonObject2.getString("OrderDate");
                                TotalAmount = jsonObject2.getString("TotalAmount");


                                completedmodels.add(new Completedmodel(orderid, CustomerName, TableName, OrderDate, TotalAmount));

                                Toast.makeText(getContext(), "Completedorder", Toast.LENGTH_SHORT).show();

                            }


                            completedAdapter = new CompletedAdapter(completedmodels);
                            completedrecycleview.setAdapter(completedAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(getContext(), "Completedorder", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

}
