package com.gadget_mart.admin.callbacks;

public interface RetailerSaveCallBack {

    void onRetailerSaveSuccess(String message, int code);
    void onRetailerSaveFailure(String message, int code);
}
