package com.ptuddd.doitien.activity;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void setBackButtonToolbar(boolean b){
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(b);
    }

    public void showDialogLoading(String loadingmessage,boolean cancelable){
        dialog = ProgressDialog.show(this, "",
                loadingmessage, true);
        dialog.setCancelable(cancelable);
    }
    public void cancleDialogLoading(){
        if(dialog.isShowing())
            dialog.cancel();
    }
}