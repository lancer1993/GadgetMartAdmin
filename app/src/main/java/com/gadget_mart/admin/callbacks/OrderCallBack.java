package com.gadget_mart.admin.callbacks;

import com.gadget_mart.admin.model.OrderModel;

import java.util.List;

public interface OrderCallBack {

    void onOrderSuccess(List<OrderModel> list);
    void onOrderFailure(String message);
}
