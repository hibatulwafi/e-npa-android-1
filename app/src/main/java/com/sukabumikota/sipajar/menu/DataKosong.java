package com.sukabumikota.sipajar.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sukabumikota.sipajar.MainActivity;
import com.sukabumikota.sipajar.R;

public class DataKosong extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kosong);
    }

    public void onClose(View view) {
        Intent back=new Intent(DataKosong.this, MainActivity.class);
        startActivity(back);
    }
}
