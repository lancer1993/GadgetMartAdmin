package com.gadget_mart.admin.callbacks;

import com.gadget_mart.admin.model.User;

public interface UserCallBack {

    void onSuccess(User user);
    void onFailure(String message);

}
