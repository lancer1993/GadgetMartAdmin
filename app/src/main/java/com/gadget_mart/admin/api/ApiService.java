package com.gadget_mart.admin.api;

import com.gadget_mart.admin.model.AuthKeyModel;
import com.gadget_mart.admin.model.AuthenticatedUser;
import com.gadget_mart.admin.model.Credentials;
import com.gadget_mart.admin.model.OrderDetails;
import com.gadget_mart.admin.model.OrderModel;
import com.gadget_mart.admin.model.PasswordResetModel;
import com.gadget_mart.admin.model.ResponseModel;
import com.gadget_mart.admin.model.RetailerModel;
import com.gadget_mart.admin.model.TokenModel;
import com.gadget_mart.admin.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("authentication/authenticateUser")
    Call<AuthenticatedUser> authenticateUser(@Body Credentials credentials);

    @POST("authentication/refreshToken")
    Call<TokenModel> refreshToken(@Body AuthKeyModel authKeyModel);

    @POST("user/resetPassword")
    Call<ResponseModel> resetPassword(@Body PasswordResetModel passwordResetModel);

    @GET("retailer/getRetailers")
    Call<List<RetailerModel>> getRetailers();

    @GET("order/getAllOrdersOfRetailers/{idRetailer}")
    Call<List<OrderModel>> getAllOrdersOfRetailers(@Path("idRetailer") int idRetailer);

    @GET("orderDetails/getOrderDetailsByOrder/{idOrder}")
    Call<List<OrderDetails>> getOrderDetailsByOrder(@Path("idOrder") int idOrder);

    @GET("user/getUserById/{id}")
    Call<User> getUserById(@Path("id") int id);

    @POST("retailer/saveRetailer")
    Call<ResponseModel> saveRetailer(@Body RetailerModel retailerModel);

    @POST("retailer/updateRetailer")
    Call<ResponseModel> updateRetailer(@Body RetailerModel retailerModel);

}
