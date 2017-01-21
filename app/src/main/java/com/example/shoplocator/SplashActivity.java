package com.example.shoplocator;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shoplocator.ui.shops.ItemListActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                Intent intent = new Intent(SplashActivity.this, ItemListActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }

}
