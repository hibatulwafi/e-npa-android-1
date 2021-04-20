package com.sukabumikota.sipajar.pesan;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sukabumikota.sipajar.MainActivity;
import com.sukabumikota.sipajar.R;
import com.sukabumikota.sipajar.adapter.KirimPesanAdapter;
import com.sukabumikota.sipajar.apihelper.BaseApiService;
import com.sukabumikota.sipajar.apihelper.koneksi;
import com.sukabumikota.sipajar.dialog.InternetDialogActivity;
import com.sukabumikota.sipajar.login.SharedPrafManager;
import com.sukabumikota.sipajar.menu.PilihPesan;
import com.sukabumikota.sipajar.model.ResponsePesan;
import com.sukabumikota.sipajar.model.SemuaPesanItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KirimPesan extends AppCompatActivity {
    ProgressDialog loading;
    Context mContext;
    List<SemuaPesanItem> semuapesanItemList = new ArrayList<>();
    KirimPesanAdapter pesanAdapter;
    BaseApiService mApiiService;
    RecyclerView recyclerView;
    String wp_id;
    SharedPrafManager sharedPrefManager; // ini
    //Buat Pencarian
    private ImageButton SearchBtn;
    private EditText SearchField,editsubjek,editisi;
    String  pesan_isi, pesan_subjek,pengirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirim_pesan);
        recyclerView = findViewById(R.id.rvpesan);
        //Buat Pencarian
        SearchBtn = findViewById(R.id.search_btn);
        SearchField = findViewById(R.id.search_field);
        sharedPrefManager = new SharedPrafManager(this); // ini
        mContext = this;
        mApiiService = koneksi.getAPIService();
        pesanAdapter = new KirimPesanAdapter(this, semuapesanItemList);
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
        mApiiService.CariPesanKeluar(searchText, wp_id).enqueue(new Callback<ResponsePesan>() {
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
                            recyclerView.setAdapter(new KirimPesanAdapter(mContext, semuapesanItemList));
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
                Intent noinet = new Intent(KirimPesan.this, InternetDialogActivity.class);
                startActivity(noinet);
            }
        });
    }

    private void getResultListPesan() {
        loading = ProgressDialog.show(this, null, "Harap Tunggu.....", true, false);
        wp_id = sharedPrefManager.getSPId();
        mApiiService.getPesanKeluar(wp_id).enqueue(new Callback<ResponsePesan>() {
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
                            recyclerView.setAdapter(new KirimPesanAdapter(mContext, semuapesanItemList));
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
                Intent noinet = new Intent(KirimPesan.this, InternetDialogActivity.class);
                startActivity(noinet);
            }
        });
    }

    public void refresh(View view) {
        Intent i = new Intent(KirimPesan.this, KirimPesan.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);
    }


    public void add(View view) {
        final Dialog dialog = new Dialog(KirimPesan.this);
        //Memasang Title / Judul pada Custom Dialog
        dialog.setTitle("Kirim Pesan");
        //Memasang Desain Layout untuk Custom Dialog
        dialog.setContentView(R.layout.dialog_kirim_pesan);
        //Memasang Listener / Aksi saat tombol OK di Klik
        Button DialogButton = dialog.findViewById(R.id.btnkirim);
        // Dialog
        editsubjek = dialog.findViewById(R.id.dialogeditsubjek);
        editisi = dialog.findViewById(R.id.dialogeditisi);

        DialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pesan_isi = editisi.getText().toString();
                pesan_subjek = editsubjek.getText().toString();
                pengirim = sharedPrefManager.getSPId();
                requestSimpanData(pesan_isi,pesan_subjek, pengirim);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void requestSimpanData(String pesan_isi,String pesan_subjek,String pengirim){

        mApiiService.KirimPesanKeluar(pesan_isi, pesan_subjek, pengirim)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){
                                    Toast.makeText(mContext, "DATA BERHASIL DISIMPAN", Toast.LENGTH_SHORT).show();
                                } else {
                                    // jika login gagal
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
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