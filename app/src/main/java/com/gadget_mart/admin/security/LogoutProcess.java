package com.gadget_mart.admin.security;

import android.content.Context;
import android.content.Intent;

import com.gadget_mart.admin.LoginActivity;

public class LogoutProcess {

    public static void logout(Context context){

        com.gadget_mart.admin.security.SaveSharedPreference.removeUsername(context);
        com.gadget_mart.admin.security.SaveSharedPreference.removePassword(context);
        com.gadget_mart.admin.security.SaveSharedPreference.removeUserId(context);
        com.gadget_mart.admin.security.SaveSharedPreference.removeUserFullName(context);
        com.gadget_mart.admin.security.TokenIdentifier.removeTOKEN(context);
        com.gadget_mart.admin.security.TokenIdentifier.removeRefreshToken(context);
        Intent intent=new Intent(context, LoginActivity.class);
        context.startActivity(intent);

    }

}
