package com.sukabumikota.sipajar.menu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.sukabumikota.sipajar.MainActivity;
import com.sukabumikota.sipajar.R;
import com.sukabumikota.sipajar.apihelper.BaseApiService;
import com.sukabumikota.sipajar.apihelper.koneksi;
import com.sukabumikota.sipajar.login.LoginActivity;
import com.sukabumikota.sipajar.login.SharedPrafManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GantiProfile extends AppCompatActivity {
    ProgressDialog loading;
    Context mContext;
    BaseApiService mApiiService;
    String id,email,nama,password_old,password_new,repassword_new,alamat;
    SharedPrafManager sharedPrefManager; // ini
    private EditText txtemail,txtnama,txtpasswordlama,txtpasswordbaru,txtrepasswordbaru,txtalamat;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^"+
                    "(?=.*[0-9])"+
                    "(?=.*[a-z])"+
                    "(?=.*[A-z])"+
                    //"(?=.*[a-zA-Z])"+
                    //"(?=.*[@#$%^&+=])"+
                    "(?=\\S+$)"+
                    ".{6,}"+
                    "$"
                    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_profile);
        txtemail = findViewById(R.id.txtemail);
        txtnama = findViewById(R.id.txtnama);
        txtpasswordlama = findViewById(R.id.txtpasswordlama);
        txtpasswordbaru = findViewById(R.id.txtpasswordbaru);
        txtrepasswordbaru = findViewById(R.id.txtrepasswordbaru);
        txtalamat = findViewById(R.id.txtalamat);

        mContext = this;
        sharedPrefManager = new SharedPrafManager(this); // ini
        id = sharedPrefManager.getSPId();

        txtemail.setText(sharedPrefManager.getSPEmail());
        txtnama.setText(sharedPrefManager.getSPNama());
        txtalamat.setText(sharedPrefManager.getSPAlamat());



        mApiiService = koneksi.getAPIService();
    }
    private void requestSimpanData(String id, String email,String nama,String password_old,String password_new,String repassword_new,String alamat){
        loading = ProgressDialog.show(this, null,
                "Harap Tunggu...", true, false);
        mApiiService.editRequest(id,email,nama,password_old,password_new,repassword_new,alamat)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("success").equalsIgnoreCase("1")){
                                    String error_message = jsonRESULTS.getString("message");
                                    success(error_message);
                                } else {
                                    String error_message = jsonRESULTS.getString("message");
                                    messagebox(error_message);
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
                        Toast.makeText(GantiProfile.this, "Gagal Gan",Toast.LENGTH_LONG);
                    }
                });
    }
    public void onBackClick(View view) {
        startActivity(new Intent(this, Profile.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, Profile.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }

    private void messagebox(String message){
        new MaterialStyledDialog.Builder(this)
                .setTitle("Message")
                .setDescription(message)
                .setIcon(R.drawable.profile)
                .setHeaderDrawable(R.drawable.bg_gradient_orange)
                .withDialogAnimation(true)
                .withIconAnimation(true)
                .setCancelable(false)
                .setPositiveText("Ya")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeText("Kembali")
                .show();
    }

    private void success(String message){
        new MaterialStyledDialog.Builder(this)
                .setTitle("Message")
                .setDescription(message+ " Logout Otomatis")
                .setIcon(R.drawable.profile)
                .setHeaderDrawable(R.drawable.bg_gradient_orange)
                .withDialogAnimation(true)
                .withIconAnimation(true)
                .setCancelable(false)
                .setPositiveText("Ya")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        endSession();
                    }
                })
                .show();
    }

    public void edit(View view) {
        email = txtemail.getText().toString();
        nama = txtnama.getText().toString();
        password_old = txtpasswordlama.getText().toString();
        password_new = txtpasswordbaru.getText().toString();
        repassword_new = txtrepasswordbaru.getText().toString();
        alamat = txtalamat.getText().toString();

        if (txtemail.length()==0){
            txtemail.setError("Gaboleh Kosong");
        }else if (txtnama.length()==0){
            txtnama.setError("Gaboleh Kosong");
        }else if (txtpasswordlama.length()==0){
            txtpasswordlama.setError("Gaboleh Kosong");
        }else if (!PASSWORD_PATTERN.matcher(txtpasswordbaru.getText().toString().trim()).matches()){
            txtpasswordbaru.setError("Harus lebih 6 huruf, Besar dan kecil dan setidaknya 1 nomor");
            //if(!PASSWORD_PATTERN.matcher(txtpasswordbaru.getText().toString().trim()).matches()){
            //    txtpasswordbaru.setError("Mesti > 6 Char, Uppercase dan Lowercase dan 1 nomor");
            //}
        }else if (txtrepasswordbaru.length()==0){
            txtrepasswordbaru.setError("Gaboleh Kosong");
        }else if (txtalamat.length()==0){
            txtalamat.setError("Gaboleh Kosong");
        }else{
            requestSimpanData(id,email,nama,password_old,password_new,repassword_new,alamat);
        }

        //Toast.makeText(GantiProfile.this,id+email+nama+password_old+password_new+repassword_new+alamat, Toast.LENGTH_LONG ).show();
    }

    public void endSession() {
        sharedPrefManager.saveSPBoolean(sharedPrefManager.SP_SUDAH_LOGIN, false);
        startActivity(new Intent(GantiProfile.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));


    }
}
