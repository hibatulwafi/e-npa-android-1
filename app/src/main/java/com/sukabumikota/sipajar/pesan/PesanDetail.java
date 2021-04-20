package com.sukabumikota.sipajar.pesan;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.sukabumikota.sipajar.R;
import com.sukabumikota.sipajar.apihelper.BaseApiService;
import com.sukabumikota.sipajar.apihelper.koneksi;
import com.sukabumikota.sipajar.login.SharedPrafManager;
import com.sukabumikota.sipajar.model.SemuaPesanItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesanDetail extends AppCompatActivity {
    SharedPrafManager sharedPrefManager;
    TextView txtpesantanggal,txtpengirim,txtsubjek,txtpesan;
    EditText editsubjek,editisi;
    Button btnbalas;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context mContext;
    public static final String EXTRA_DATA= "extra_data";
    String  pesan_isi, pesan_subjek,pengirim,penerima;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_detail);
        txtpesantanggal = findViewById(R.id.txtpesantanggal);
        txtpengirim = findViewById(R.id.txtpengirim);
        txtsubjek = findViewById(R.id.txtsubjek);
        txtpesan = findViewById(R.id.txtpesan);
        editsubjek = findViewById(R.id.editsubjek);
        editisi = findViewById(R.id.editisi);
        btnbalas = findViewById(R.id.btnbalas);
        sharedPrefManager = new SharedPrafManager(this); // ini
        SemuaPesanItem semuapesanitem = getIntent().getParcelableExtra(EXTRA_DATA);
        mContext = this;
        mApiService = koneksi.getAPIService();
        txtpesantanggal.setText(semuapesanitem.getTanggal());
        txtpengirim.setText(semuapesanitem.getPengirim());
        txtsubjek.setText(semuapesanitem.getPesan_subjek());
        txtpesan.setText(semuapesanitem.getPesan_isi());

        btnbalas.setOnClickListener( new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (editsubjek.getText().toString().length()==0){
                    editsubjek.setError("Jangan Kosong");
                }else if (editisi.getText().toString().length()==0){
                    editisi.setError("Jangan Kosong");
                }else{
                tanyaKirim();
                }
            }
        });

    }

    public void tanyaKirim(){

        SemuaPesanItem semuapesanitem = getIntent().getParcelableExtra(EXTRA_DATA);
        pesan_isi = editisi.getText().toString();
        pesan_subjek = editsubjek.getText().toString();
        pengirim = sharedPrefManager.getSPId();
        penerima =  semuapesanitem.getId_pengirim();

        new MaterialStyledDialog.Builder(this)
                .setTitle("Yakin Kirim Pesan?")
                .setDescription("Anda akan mengirim Pesan.\n" +
                        editisi.getText().toString()+ " " +
                        editsubjek.getText().toString()+ " " +
                        sharedPrefManager.getSPId()+ " " +
                        semuapesanitem.getId_pengirim()+

                        "Klik Ya untuk mengirim!")
                .setIcon(R.drawable.question)
                .setHeaderDrawable(R.drawable.bg_gradient_orange)
                .withDialogAnimation(true)
                .withIconAnimation(true)
                .setCancelable(false)
                .setPositiveText("Ya")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        loading = ProgressDialog.show(mContext, null, "Tunggu yah..",true,false);
                        requestSimpanData(pesan_isi,pesan_subjek,pengirim,penerima);
                    }
                })
                .setNegativeText("Kembali")
                .show();
    }

    private void requestSimpanData(String pesan_isi,String pesan_subjek,String pengirim,String penerima ){

        mApiService.KirimPesan(pesan_isi, pesan_subjek, pengirim ,penerima)
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

}
