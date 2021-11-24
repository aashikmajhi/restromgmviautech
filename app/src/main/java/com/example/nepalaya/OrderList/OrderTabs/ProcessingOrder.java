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
import com.example.nepalaya.OrderList.OrderTabs.Pending.PendingAdapter;
import com.example.nepalaya.OrderList.OrderTabs.Pending.PendingListener;
import com.example.nepalaya.OrderList.OrderTabs.Pending.PendingModel;
import com.example.nepalaya.OrderList.OrderTabs.Processing.ProcessingAdapter;
import com.example.nepalaya.OrderList.OrderTabs.Processing.ProcessingModel;
import com.example.nepalaya.R;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProcessingOrder extends Fragment {


    @BindView(R.id.processing_recycler)
    RecyclerView processing_recycler;

    ProgressDialog progressDialog;

    Call<JsonElement> call;
    ArrayList<ProcessingModel> processingModels;

    String orderid, CustomerName, TableName, OrderDate, TotalAmount;

    ProcessingAdapter processingAdapter;


    public ProcessingOrder() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.processingorder, container, false);
        ButterKnife.bind(this, rootview);

        progressdialog();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        processing_recycler.setLayoutManager(layoutManager);

        getDataa();

        return rootview;
    }


    public void progressdialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void getDataa() {
        int id = 168;
        APIService apiService = (APIService) ApiHelper.getInstance().getService(APIService.class);
        call = apiService.ProcessingOrder(id);
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
                            processingModels = new ArrayList<>();

                            for (int i = 0; i < dataouter.length(); i++) {
                                JSONObject jsonObject1 = dataouter.getJSONObject(i);

                                orderid = jsonObject1.getString("order_id");
                                CustomerName = jsonObject1.getString("CustomerName");
                                TableName = jsonObject1.getString("TableName");
                                OrderDate = jsonObject1.getString("OrderDate");
                                TotalAmount = jsonObject1.getString("TotalAmount");

                                processingModels.add(new ProcessingModel(orderid, CustomerName, TableName, OrderDate, TotalAmount));
                            }

                            processingAdapter = new ProcessingAdapter(processingModels);
                            processing_recycler.setAdapter(processingAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }


}
