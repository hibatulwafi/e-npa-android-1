package com.sukabumikota.sipajar.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.sukabumikota.sipajar.MainActivity;
import com.sukabumikota.sipajar.R;
import com.sukabumikota.sipajar.pesan.KirimPesan;
import com.sukabumikota.sipajar.pesan.Pesan;

public class PilihPesan extends AppCompatActivity {
   CardView cvpesankeluar , cvpesanmasuk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_pesan);

        cvpesanmasuk = findViewById(R.id.cvpesanmasuk);
        cvpesankeluar = findViewById(R.id.cvpesankeluar);

        cvpesanmasuk.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent masuk = new Intent(PilihPesan.this, Pesan.class);
                        startActivity(masuk);
                    }
                }
        );

        cvpesankeluar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent tentang = new Intent(PilihPesan.this, KirimPesan.class);
                        startActivity(tentang);
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
