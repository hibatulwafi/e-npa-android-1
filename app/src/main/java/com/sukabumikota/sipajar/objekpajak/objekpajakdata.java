package com.sukabumikota.sipajar.objekpajak;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sukabumikota.sipajar.MainActivity;
import com.sukabumikota.sipajar.R;
import com.sukabumikota.sipajar.adapter.ObjekPajakAdapter;
import com.sukabumikota.sipajar.apihelper.BaseApiService;
import com.sukabumikota.sipajar.apihelper.koneksi;
import com.sukabumikota.sipajar.dialog.InternetDialogActivity;
import com.sukabumikota.sipajar.login.SharedPrafManager;
import com.sukabumikota.sipajar.model.ResponseObjekPajak;
import com.sukabumikota.sipajar.model.SemuaObjekPajakItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class objekpajakdata extends AppCompatActivity {
    ProgressDialog loading;
    Context mContext;
    List<SemuaObjekPajakItem> semuaobjekpajakItemList = new ArrayList<>();
    ObjekPajakAdapter objekpajakAdapter;
    BaseApiService mApiiService;
    RecyclerView recyclerView;
    String wp_id;
    SharedPrafManager sharedPrefManager; // ini


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objekpajakdata);
        recyclerView = findViewById(R.id.rvop);
        sharedPrefManager = new SharedPrafManager(this);
        mContext = this;
        mApiiService = koneksi.getAPIService();
        objekpajakAdapter = new ObjekPajakAdapter(this, semuaobjekpajakItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getResultListObjekPajak();
    }
    private void getResultListObjekPajak() {
        loading = ProgressDialog.show(this, null, "Harap Tunggu.....", true, false);
        wp_id = sharedPrefManager.getSPId();
        mApiiService.getObjekPajak(wp_id).enqueue(new Callback<ResponseObjekPajak>() {
            @Override
            public void onResponse(Call<ResponseObjekPajak> call, Response<ResponseObjekPajak> response) {
                if (response.isSuccessful()) {
                    Boolean error = response.body().isError();
                    loading.dismiss();
                    semuaobjekpajakItemList = response.body().getObjekpajak();
                    if(error.equals(true)){
                        String msg = "Tidak Ada Data Titik Sumur";
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    }else{
                        if(semuaobjekpajakItemList==null){
                            String msg = "Tidak Ada Data Titik Sumur";
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }else{
                            recyclerView.setAdapter(new ObjekPajakAdapter(mContext, semuaobjekpajakItemList));
                            objekpajakAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObjekPajak> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                Intent noinet = new Intent(objekpajakdata.this, InternetDialogActivity.class);
                startActivity(noinet);
            }
        });
    }

    public void onBackClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }

}