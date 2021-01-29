package com.gadget_mart.admin.service;

import android.content.Context;
import android.util.Log;

import com.gadget_mart.admin.api.ApiService;
import com.gadget_mart.admin.callbacks.UserCallBack;
import com.gadget_mart.admin.model.PasswordResetModel;
import com.gadget_mart.admin.model.ResponseModel;
import com.gadget_mart.admin.model.User;
import com.gadget_mart.admin.security.SaveSharedPreference;
import com.gadget_mart.admin.security.TokenIdentifier;
import com.gadget_mart.admin.util.Constant;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {

    public void resetUserPassword(PasswordResetModel passwordResetModel) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
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
        Call<ResponseModel> call = apiService.resetPassword(passwordResetModel);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
                if (response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getCode() == 200) {
                        Constant.passwordReset = true;
                    } else {
                        Constant.passwordReset = false;
                    }
                } else {
                    Constant.passwordReset = false;
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Constant.passwordReset = false;
                Log.e("Cannot reset password", t.getMessage());
            }
        });
    }

    public void viewProfile(Context context, UserCallBack userCallBack) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
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
        Call<User> call = apiService.getUserById(SaveSharedPreference.getUserId(context));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if (response.body() != null) {
                    userCallBack.onSuccess(response.body());
                } else {
                    userCallBack.onFailure("User details cannot find");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                userCallBack.onFailure("User details cannot find");
                Log.e("USER LOADING ERROR", t.getMessage());
            }
        });
    }
}
