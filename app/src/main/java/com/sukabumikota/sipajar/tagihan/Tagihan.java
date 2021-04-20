package com.sukabumikota.sipajar.tagihan;

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
import com.sukabumikota.sipajar.adapter.TagihanAdapter;
import com.sukabumikota.sipajar.apihelper.BaseApiService;
import com.sukabumikota.sipajar.apihelper.koneksi;
import com.sukabumikota.sipajar.dialog.InternetDialogActivity;
import com.sukabumikota.sipajar.login.SharedPrafManager;
import com.sukabumikota.sipajar.model.ResponseTagihan;
import com.sukabumikota.sipajar.model.SemuaTagihanItem;
import com.sukabumikota.sipajar.objekpajak.objekpajakdata;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tagihan extends AppCompatActivity {
    ProgressDialog loading;
    Context mContext;
    List<SemuaTagihanItem> semuatagihanItemList = new ArrayList<>();
    TagihanAdapter tagihanAdapter;
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
        setContentView(R.layout.activity_tagihan);
        recyclerView = findViewById(R.id.rvtagihan);
        sharedPrefManager = new SharedPrafManager(this); // ini
        //Buat Pencarian
        SearchBtn = findViewById(R.id.search_btn);
        SearchField = findViewById(R.id.search_field);
        mContext = this;
        mApiiService = koneksi.getAPIService();
        tagihanAdapter = new TagihanAdapter(this, semuatagihanItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getResultListTagihan();
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = SearchField.getText().toString();
                CariData(searchText);

            }
        });

    }
    private void getResultListTagihan() {
        loading = ProgressDialog.show(this, null, "Harap Tunggu.....", true, false);
        wp_id = sharedPrefManager.getSPId();
        mApiiService.getTagihan(wp_id).enqueue(new Callback<ResponseTagihan>() {
            @Override
            public void onResponse(Call<ResponseTagihan> call, Response<ResponseTagihan> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    Boolean error = response.body().isError();
                    semuatagihanItemList = response.body().getTagihan();
                    if(error.equals(true)){
                        String msg = "Tidak ada data tagihan!";
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    }else{
                        if(semuatagihanItemList==null){
                            String msg = "Kesalahan Mengambil Data";
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }else{
                            recyclerView.setAdapter(new TagihanAdapter(mContext, semuatagihanItemList));
                            tagihanAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTagihan> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                Intent noinet = new Intent(Tagihan.this, InternetDialogActivity.class);
                startActivity(noinet);
            }
        });
    }
    private void CariData(String searchText) {
        loading = ProgressDialog.show(this, null, "Harap Tunggu.....", true, false);
        wp_id = sharedPrefManager.getSPId();
        mApiiService.CariTagihan(searchText, wp_id).enqueue(new Callback<ResponseTagihan>() {
            @Override
            public void onResponse(Call<ResponseTagihan> call, Response<ResponseTagihan> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    Boolean error = response.body().isError();
                    semuatagihanItemList = response.body().getTagihan();
                    if(error.equals(true)){
                        String msg = "Tidak ada data tagihan!";
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    }else{
                        if(semuatagihanItemList==null){
                            String msg = "Kesalahan Mengambil Data";
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }else{
                            recyclerView.setAdapter(new TagihanAdapter(mContext, semuatagihanItemList));
                            tagihanAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTagihan> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                Intent noinet = new Intent(Tagihan.this, InternetDialogActivity.class);
                startActivity(noinet);
            }
        });
    }
    public void refresh(View view) {
        Intent i = new Intent(Tagihan.this, Tagihan.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);
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