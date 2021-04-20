package com.sukabumikota.sipajar.tagihan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sukabumikota.sipajar.MainActivity;
import com.sukabumikota.sipajar.R;
import com.sukabumikota.sipajar.model.SemuaTagihanItem;

import com.sukabumikota.sipajar.apihelper.koneksi;

public class TagihanDetail extends AppCompatActivity {
    TextView txtalamat,txtopnama,txtpenggunaan,txttarif,txttanggal,txtstatus,txttotaltagihan,txtdenda,txtpemakaian;

    public static final String EXTRA_DATA= "extra_data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagihan_detail);
        txtalamat = findViewById(R.id.txtalamat);
        txtopnama = findViewById(R.id.txtopnama);
        txttarif = findViewById(R.id.txttarif);
        txttanggal = findViewById(R.id.txttanggal);
        txtstatus = findViewById(R.id.txtstatus);
        txttotaltagihan = findViewById(R.id.txttotaltagihan);
        txtdenda = findViewById(R.id.txtdenda);
        txtpemakaian = findViewById(R.id.txtpenggunaan);
        SemuaTagihanItem semuatagihanitem = getIntent().getParcelableExtra(EXTRA_DATA);

        txtalamat.setText(semuatagihanitem.getAlamat());
        txtopnama.setText(semuatagihanitem.getOp_nama());
        txttarif.setText(semuatagihanitem.getTarif());
        txttanggal.setText(koneksi.nama_bulan(semuatagihanitem.getBulan())+" "+ semuatagihanitem.getTahun());
        txtstatus.setText(semuatagihanitem.getStatus());
        txttotaltagihan.setText(semuatagihanitem.getTotal_tagihan());
        txtdenda.setText(semuatagihanitem.getDenda());
        txtpemakaian.setText(semuatagihanitem.getPemakaian());
    }
    public void onBackClick(View view) {
        startActivity(new Intent(this, Tagihan.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, Tagihan.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }
}
