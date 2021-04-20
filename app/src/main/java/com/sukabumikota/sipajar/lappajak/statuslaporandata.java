package com.sukabumikota.sipajar.lappajak;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sukabumikota.sipajar.MainActivity;
import com.sukabumikota.sipajar.R;

import com.sukabumikota.sipajar.adapter.StatusLaporanAdapter;
import com.sukabumikota.sipajar.apihelper.BaseApiService;
import com.sukabumikota.sipajar.apihelper.koneksi;
import com.sukabumikota.sipajar.dialog.InternetDialogActivity;
import com.sukabumikota.sipajar.login.SharedPrafManager;
import com.sukabumikota.sipajar.menu.PilihUpload;
import com.sukabumikota.sipajar.model.ResponseStatusLaporan;
import com.sukabumikota.sipajar.model.SemuaStatusLaporanItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class statuslaporandata extends AppCompatActivity {
    ProgressDialog loading;
    Context mContext;
    List<SemuaStatusLaporanItem> semuastatuslaporanItemList = new ArrayList<>();
    StatusLaporanAdapter statuslaporanAdapter;
    BaseApiService mApiiService;
    RecyclerView recyclerView;
    String wp_id;
    SharedPrafManager sharedPrefManager; // ini
    //Buat Pencarian
    private ImageButton SearchBtn;
    private EditText SearchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statuslaporandata);
        recyclerView = findViewById(R.id.rvstatus);
        sharedPrefManager = new SharedPrafManager(this); // ini
        //Buat Pencarian
        SearchBtn = findViewById(R.id.search_btn);
        SearchField = findViewById(R.id.search_field);
        mContext = this;
        mApiiService = koneksi.getAPIService();
        statuslaporanAdapter = new StatusLaporanAdapter(this, semuastatuslaporanItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getResultListStatusLaporan();
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = SearchField.getText().toString();
                CariData(searchText);

            }
        });

    }

    private void getResultListStatusLaporan() {
        loading = ProgressDialog.show(this, null, "Harap Tunggu.....", true, false);
        wp_id = sharedPrefManager.getSPId();
        mApiiService.getStatusLaporan(wp_id).enqueue(new Callback<ResponseStatusLaporan>() {
            @Override
            public void onResponse(Call<ResponseStatusLaporan> call, Response<ResponseStatusLaporan> response) {
                if (response.isSuccessful()) {
                    Boolean error = response.body().isError();
                    loading.dismiss();
                    semuastatuslaporanItemList = response.body().getStatuslaporan();
                    if(error.equals(true)){
                        String msg = "Tidak Ada Data Titik Sumur";
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    }else{
                        if(semuastatuslaporanItemList==null){
                            String msg = "Tidak Ada Data Titik Sumur";
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }else{
                            recyclerView.setAdapter(new StatusLaporanAdapter(mContext, semuastatuslaporanItemList));
                            statuslaporanAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStatusLaporan> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                Intent noinet = new Intent(statuslaporandata.this, InternetDialogActivity.class);
                startActivity(noinet);
            }
        });
    }
    private void CariData(String searchText) {
        loading = ProgressDialog.show(this, null, "Harap Tunggu.....", true, false);
        wp_id = sharedPrefManager.getSPId();
        mApiiService.CariStatusLaporan(searchText, wp_id).enqueue(new Callback<ResponseStatusLaporan>() {
            @Override
            public void onResponse(Call<ResponseStatusLaporan> call, Response<ResponseStatusLaporan> response) {
                if (response.isSuccessful()) {
                    Boolean error = response.body().isError();
                    loading.dismiss();
                    semuastatuslaporanItemList = response.body().getStatuslaporan();
                    if(error.equals(true)){
                        String msg = "Tidak Ada Data";
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    }else{
                        if(semuastatuslaporanItemList==null){
                            String msg = "Tidak Ada Data";
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }else{
                            recyclerView.setAdapter(new StatusLaporanAdapter(mContext, semuastatuslaporanItemList));
                            statuslaporanAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStatusLaporan> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                Intent noinet = new Intent(statuslaporandata.this, InternetDialogActivity.class);
                startActivity(noinet);
            }
        });
    }
    public void refresh(View view) {
        Intent i = new Intent(statuslaporandata.this, statuslaporandata.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);
    }
    public void onBackClick(View view) {
        startActivity(new Intent(this, PilihUpload.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, PilihUpload.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }

}