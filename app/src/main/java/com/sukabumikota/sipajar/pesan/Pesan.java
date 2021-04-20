package com.sukabumikota.sipajar.pesan;

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

import com.sukabumikota.sipajar.R;
import com.sukabumikota.sipajar.adapter.PesanAdapter;
import com.sukabumikota.sipajar.apihelper.BaseApiService;
import com.sukabumikota.sipajar.apihelper.koneksi;
import com.sukabumikota.sipajar.dialog.InternetDialogActivity;
import com.sukabumikota.sipajar.login.SharedPrafManager;
import com.sukabumikota.sipajar.menu.PilihPesan;
import com.sukabumikota.sipajar.model.ResponsePesan;
import com.sukabumikota.sipajar.model.SemuaPesanItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pesan extends AppCompatActivity {
    ProgressDialog loading;
    Context mContext;
    List<SemuaPesanItem> semuapesanItemList = new ArrayList<>();
    PesanAdapter pesanAdapter;
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
        setContentView(R.layout.activity_pesan);
        recyclerView = findViewById(R.id.rvpesan);
        //Buat Pencarian
        SearchBtn = findViewById(R.id.search_btn);
        SearchField = findViewById(R.id.search_field);
        sharedPrefManager = new SharedPrafManager(this); // ini
        mContext = this;
        mApiiService = koneksi.getAPIService();
        pesanAdapter = new PesanAdapter(this, semuapesanItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getResultListPesan();

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = SearchField.getText().toString();
                CariData(searchText);

            }
        });
    }

    private void CariData(String searchText) {

        loading = ProgressDialog.show(this, null, "Harap Tunggu.....", true, false);
        wp_id = sharedPrefManager.getSPId();
        mApiiService.CariPesan(searchText,wp_id).enqueue(new Callback<ResponsePesan>() {
            @Override
            public void onResponse(Call<ResponsePesan> call, Response<ResponsePesan> response) {
                if (response.isSuccessful()) {
                    Boolean error = response.body().isError();
                    loading.dismiss();
                    semuapesanItemList = response.body().getPesan();
                    if(error.equals(true)){
                        String msg = "Tidak Ada Data ";
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    }else{
                        if(semuapesanItemList==null){
                            String msg = "Tidak Ada Data ";
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }else{
                            recyclerView.setAdapter(new PesanAdapter(mContext, semuapesanItemList));
                            pesanAdapter.notifyDataSetChanged();
                        }
                    }
                    //final List<SemuaPesanItem> semuaPesanItems = response.body().getPesan();
                    //recyclerView.setAdapter(new PesanAdapter(mContext, semuaPesanItems));
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponsePesan> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                Intent noinet = new Intent(Pesan.this, InternetDialogActivity.class);
                startActivity(noinet);
            }
        });
    }

    private void getResultListPesan() {
        loading = ProgressDialog.show(this, null, "Harap Tunggu.....", true, false);
        wp_id = sharedPrefManager.getSPId();
        mApiiService.getPesan(wp_id).enqueue(new Callback<ResponsePesan>() {
            @Override
            public void onResponse(Call<ResponsePesan> call, Response<ResponsePesan> response) {
                if (response.isSuccessful()) {
                    Boolean error = response.body().isError();
                    loading.dismiss();
                    semuapesanItemList = response.body().getPesan();
                    if(error.equals(true)){
                        String msg = "Tidak Ada Data ";
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    }else{
                        if(semuapesanItemList==null){
                            String msg = "Tidak Ada Data ";
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }else{
                            recyclerView.setAdapter(new PesanAdapter(mContext, semuapesanItemList));
                            pesanAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePesan> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                Intent noinet = new Intent(Pesan.this, InternetDialogActivity.class);
                startActivity(noinet);
            }
        });
    }

    public void refresh(View view) {
        Intent i = new Intent(Pesan.this, Pesan.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);
    }

    public void onBackClick(View view) {
        startActivity(new Intent(this, PilihPesan.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, PilihPesan.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }
    
}