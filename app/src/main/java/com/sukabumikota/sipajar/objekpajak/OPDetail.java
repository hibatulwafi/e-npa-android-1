package com.sukabumikota.sipajar.objekpajak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sukabumikota.sipajar.R;
import com.sukabumikota.sipajar.adapter.TabAdapter;
import com.sukabumikota.sipajar.menu.PilihPesan;
import com.sukabumikota.sipajar.model.SemuaObjekPajakItem;

public class OPDetail extends AppCompatActivity {
TextView txtnamaop,txttanggalregis,txtopalamat,txtnoipat,txtnamawp;
    public static final String EXTRA_DATA= "extra_data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opdetail);

        txtnamaop = findViewById(R.id.txtnamaop);
        txttanggalregis = findViewById(R.id.txttanggalregis);
        //txtopalamat = findViewById(R.id.txtopalamat);
        //txtnoipat = findViewById(R.id.txtnoipat);
        //txtnamawp = findViewById(R.id.txtnamawp);

        SemuaObjekPajakItem semuaobjekpajakitem = getIntent().getParcelableExtra(EXTRA_DATA);

        txtnamaop.setText(semuaobjekpajakitem.getOp_nama());
        txttanggalregis.setText(semuaobjekpajakitem.getTanggal_registrasi());
        //txtopalamat.setText(semuaobjekpajakitem.getOp_alamat());
        //txtnoipat.setText(semuaobjekpajakitem.getNomor_ipat());
        //txtnamawp.setText(semuaobjekpajakitem.getNama_wajib_pajak());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Detail"));
        tabLayout.addTab(tabLayout.newTab().setText("Riwayat"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager =(ViewPager)findViewById(R.id.view_pager);

        TabAdapter tabsAdapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void onBackClick(View view) {
        startActivity(new Intent(this, objekpajakdata.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, objekpajakdata.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }
}