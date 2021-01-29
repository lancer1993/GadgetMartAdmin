package com.gadget_mart.admin.service;

import android.content.Context;
import android.util.Log;

import com.gadget_mart.admin.api.ApiService;
import com.gadget_mart.admin.callbacks.OrderCallBack;
import com.gadget_mart.admin.callbacks.OrderDetailCallBack;
import com.gadget_mart.admin.model.OrderDetails;
import com.gadget_mart.admin.model.OrderModel;
import com.gadget_mart.admin.security.TokenIdentifier;
import com.gadget_mart.admin.util.Constant;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderService {

    public OrderService() {

    }

    public void getOrdersForRetailer(Context context, int idRetailer, OrderCallBack orderCallBack) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + TokenIdentifier.getTOKEN(context))
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<OrderModel>> call = apiService.getAllOrdersOfRetailers(idRetailer);
        call.enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, retrofit2.Response<List<OrderModel>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    orderCallBack.onOrderSuccess(response.body());
                } else {
                    orderCallBack.onOrderFailure("Cannot find orders for retailers");
                }
            }

            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {
                Log.e("Cannot find orders", t.getMessage());
                orderCallBack.onOrderFailure("Cannot find orders for retailers");
            }
        });
    }

    public void getOrderDetailsForOrders(Context context, int idOrder, OrderDetailCallBack orderDetailCallBack){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + TokenIdentifier.getTOKEN(context))
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<OrderDetails>> call = apiService.getOrderDetailsByOrder(idOrder);
        call.enqueue(new Callback<List<OrderDetails>>() {
            @Override
            public void onResponse(Call<List<OrderDetails>> call, retrofit2.Response<List<OrderDetails>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    orderDetailCallBack.onOrderDetailSuccess(response.body());
                }else{
                    orderDetailCallBack.onOrderDetailFailure("Cannot find order details for order");
                }
            }

            @Override
            public void onFailure(Call<List<OrderDetails>> call, Throwable t) {
                Log.e("Cannot find order data", t.getMessage());
                orderDetailCallBack.onOrderDetailFailure("Cannot find order details for order");
            }
        });
    }

}
