package com.sukabumikota.sipajar.dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.sukabumikota.sipajar.R;
import com.sukabumikota.sipajar.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    private int waktu_loading=2500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //setelah loading maka akan langsung berpindah ke Recyclerview activity
                Intent login=new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        },waktu_loading);
    }
}
