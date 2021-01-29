package com.gadget_mart.admin.callbacks;

import com.gadget_mart.admin.model.OrderDetails;

import java.util.List;

public interface OrderDetailCallBack {

    void onOrderDetailSuccess(List<OrderDetails> list);
    void onOrderDetailFailure(String message);
}
