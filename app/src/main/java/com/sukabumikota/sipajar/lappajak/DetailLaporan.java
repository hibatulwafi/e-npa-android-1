package com.sukabumikota.sipajar.lappajak;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sukabumikota.sipajar.R;
import com.sukabumikota.sipajar.apihelper.koneksi;
import com.sukabumikota.sipajar.model.SemuaStatusLaporanItem;

import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class DetailLaporan extends AppCompatActivity {
    ImageView ivdetail;
    TextView kode, tanggal,status;
    public static final String EXTRA_DATA= "extra_data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan);
        kode = findViewById(R.id.txtkodedet);
        tanggal = findViewById(R.id.txttanggaldet);
        status = findViewById(R.id.txtstatusdet);
        ivdetail = findViewById(R.id.ivdetail);
        SemuaStatusLaporanItem semuastatuslaporanitem = getIntent().getParcelableExtra(EXTRA_DATA);

        kode.setText("#"+semuastatuslaporanitem.getFoto_id());
        tanggal.setText(koneksi.nama_bulan(semuastatuslaporanitem.getFoto_bulan())+semuastatuslaporanitem.getFoto_tahun());
        status.setText(semuastatuslaporanitem.getStatus());

        Glide.with(this)
                .load(koneksi.GAMBAR_URL
                        + "bukti/"
                        + semuastatuslaporanitem.getNama())
                .apply(bitmapTransform(new VignetteFilterTransformation(new PointF(0.5f, 0.5f),
                        new float[]{0.0f, 0.0f, 0.0f}, 0.4f, 1.0f)).dontAnimate())
                .into(ivdetail);
    }

    public void back(View view) {
        Intent b = new Intent(DetailLaporan.this, statuslaporandata.class);
        startActivity(b);
    }
}
