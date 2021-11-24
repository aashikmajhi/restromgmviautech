package com.example.nepalaya.ApiService;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @FormUrlEncoded
    @POST("V1/sign_in")
    Call<JsonElement> login(@Field("email") String email,
                            @Field("password") String password);

    @FormUrlEncoded
    @POST("V1/categorylist")
    Call<JsonElement> categoryList(@Field("id") int id,
                                   @Field("android") String android);

    @FormUrlEncoded
    @POST("V1/foodlist")
    Call<JsonElement> FoodList(@Field("id") String id,
                               @Field("CategoryID") String Categoryid);

    @FormUrlEncoded
    @POST("V1/tablelist")
    Call<JsonElement> TableList(@Field("id") int id);

    @FormUrlEncoded
    @POST("V1/foodcart")
    Call<JsonElement> FoodCart(@Field("id") int id,
                               @Field("TypeID") int TypeID,
                               @Field("VatAmount") int VatAmount,
                               @Field("TableId") int TableId,
                               @Field("CustomerID") int CustomerID,
                               @Field("Total") int Total,
                               @Field("Grandtotal") int GrandTotal,
                               @Field("foodinfo") JSONArray foodinfo);

    @FormUrlEncoded
    @POST("V1/pendingorder")
    Call<JsonElement> Pendinglist(@Field("id") int id);

    @FormUrlEncoded
    @POST("V1/completeorder")
    Call<JsonElement> Completedlist(@Field("id") int id,
                                    @Field("start") int start);

    @FormUrlEncoded
    @POST("V1/cancelorder")
    Call<JsonElement> Cancellist(@Field("id") int id,
                                 @Field("start") int start);

    @FormUrlEncoded
    @POST("V1/updateorder")
    Call<JsonElement> UpdateOrder(@Field("Orderid") int orderid);

    @FormUrlEncoded
    @POST("V1/modifyfoodcart")
    Call<JsonElement> UpdateFoodCart(@Field("id") int id,
                                     @Field("VatAmount") int VatAmount,
                                     @Field("Orderid") String Orderid,
                                     @Field("TableId") int TableId,
                                     @Field("Total") int Total,
                                     @Field("Grandtotal") int GrandTotal,
                                     @Field("foodinfo") JSONArray foodinfo);


    @FormUrlEncoded
    @POST("V1/processingorder")
    Call<JsonElement> ProcessingOrder(@Field("id") int id);


}
