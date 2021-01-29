package com.gadget_mart.admin.callbacks;

public interface AuthenticationCallBack {

    void onSuccessAuthentication(String message, int code);
    void onFailureAuthentication(String message, int code);

}
