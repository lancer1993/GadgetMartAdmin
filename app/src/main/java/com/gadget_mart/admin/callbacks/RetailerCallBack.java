package com.gadget_mart.admin.callbacks;

import com.gadget_mart.admin.model.RetailerModel;

import java.util.List;

public interface RetailerCallBack {

    void onSuccess(List<RetailerModel> list);
    void onFailure(String message);
}
