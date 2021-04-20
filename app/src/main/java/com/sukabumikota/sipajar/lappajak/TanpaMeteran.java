package com.sukabumikota.sipajar.lappajak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sukabumikota.sipajar.R;

import com.sukabumikota.sipajar.menu.PilihOp;

public class TanpaMeteran extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanpa_meteran);
    }

    public void onClose(View view) {
        Intent noinet = new Intent(TanpaMeteran.this, PilihOp.class);
        startActivity(noinet);
    }
}