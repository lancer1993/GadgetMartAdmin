package com.gadget_mart.admin.service;

import android.content.Context;
import android.util.Log;

import com.gadget_mart.admin.api.ApiService;
import com.gadget_mart.admin.callbacks.RetailerCallBack;
import com.gadget_mart.admin.callbacks.RetailerSaveCallBack;
import com.gadget_mart.admin.model.ResponseModel;
import com.gadget_mart.admin.model.RetailerModel;
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

public class RetailerService {

    public RetailerService(){
    }

    public void getAllRetailers(Context context, RetailerCallBack retailerCallBack) {
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
        Call<List<RetailerModel>> call = apiService.getRetailers();
        call.enqueue(new Callback<List<RetailerModel>>() {
            @Override
            public void onResponse(Call<List<RetailerModel>> call, retrofit2.Response<List<RetailerModel>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    retailerCallBack.onSuccess(response.body());
                }else{
                    retailerCallBack.onFailure("Retailers cannot found");
                }
            }

            @Override
            public void onFailure(Call<List<RetailerModel>> call, Throwable t) {
                Log.e("Cannot load retailers", t.getMessage());
                retailerCallBack.onFailure("Retailers cannot found");
            }
        });
    }

    public void saveRetailer(Context context, RetailerModel retailerModel, RetailerSaveCallBack retailerSaveCallBack){
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
        Call<ResponseModel> call = apiService.saveRetailer(retailerModel);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
                if (response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getCode() == 200) {
                        retailerSaveCallBack.onRetailerSaveSuccess(response.body().getResponse(),response.body().getCode());
                    } else {
                        retailerSaveCallBack.onRetailerSaveFailure(response.body().getResponse(),response.body().getCode());
                    }
                } else {
                    retailerSaveCallBack.onRetailerSaveFailure(response.body().getResponse(),response.body().getCode());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("Cannot save retailer", t.getMessage());
                retailerSaveCallBack.onRetailerSaveFailure("Retailer save failure",204);
            }
        });
    }

    public void updateRetailer(Context context, RetailerModel retailerModel, RetailerSaveCallBack retailerSaveCallBack){
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
        Call<ResponseModel> call = apiService.updateRetailer(retailerModel);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
                if (response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getCode() == 200) {
                        retailerSaveCallBack.onRetailerSaveSuccess(response.body().getResponse(),response.body().getCode());
                    } else {
                        retailerSaveCallBack.onRetailerSaveFailure(response.body().getResponse(),response.body().getCode());
                    }
                } else {
                    retailerSaveCallBack.onRetailerSaveFailure(response.body().getResponse(),response.body().getCode());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("Cannot save retailer", t.getMessage());
                retailerSaveCallBack.onRetailerSaveFailure("Retailer save failure",204);
            }
        });
    }

}
