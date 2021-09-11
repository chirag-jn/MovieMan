package com.chiragjn.movieman.activity;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chiragjn.movieman.networking.ApiManager;
import com.chiragjn.movieman.networking.RetrofitClient;

abstract public class BaseActivity extends AppCompatActivity {

    abstract void bindView();

    public void showErrorToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }
}
