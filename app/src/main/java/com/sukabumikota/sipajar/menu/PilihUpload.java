package com.sukabumikota.sipajar.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.sukabumikota.sipajar.MainActivity;
import com.sukabumikota.sipajar.R;

import com.sukabumikota.sipajar.lappajak.statuslaporandata;

public class PilihUpload extends AppCompatActivity {
CardView CVfotoupload, CVpilihdetail,CVpilihriwayat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_upload);
        CVfotoupload = findViewById(R.id.pilihfoto);
        CVpilihriwayat = findViewById(R.id.pilihriwayat);

        CVfotoupload.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent a = new Intent(PilihUpload.this, PilihOp.class);
                        startActivity(a);
                    }
                }
        );

        CVpilihriwayat.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent b = new Intent(PilihUpload.this, statuslaporandata.class);
                        startActivity(b);
                    }
                }
        );
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }
}
