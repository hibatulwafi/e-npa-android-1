package com.sukabumikota.sipajar;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.sukabumikota.sipajar.R;

import com.sukabumikota.sipajar.apihelper.BaseApiService;
import com.sukabumikota.sipajar.apihelper.koneksi;
import com.sukabumikota.sipajar.bantuan.bantuan;
import com.sukabumikota.sipajar.login.LoginActivity;
import com.sukabumikota.sipajar.login.SharedPrafManager;
import com.sukabumikota.sipajar.menu.GantiProfile;
import com.sukabumikota.sipajar.menu.PilihPesan;
import com.sukabumikota.sipajar.menu.PilihUpload;
import com.sukabumikota.sipajar.menu.Profile;
import com.sukabumikota.sipajar.menu.Tentang;
import com.sukabumikota.sipajar.objekpajak.objekpajakdata;
import com.sukabumikota.sipajar.tagihan.Tagihan;
import com.sukabumikota.sipajar.utils.DarkModePrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private long exitTime = 0;

    private static final int MODE_DARK = 0;
    private static final int MODE_LIGHT = 1;
    private BottomNavigationView bottomNavigationView;
    String email;
    SharedPrafManager sharedPrefManager;
    Context mContext;
    BaseApiService mApiService;
    CardView CVLappajak, CVPesan , CVObjekpajak, CVBantuan,CVTagihan,CVProfile;
    public static final String SP_PAJAK_APP = "spPajak";
    public static final String SP_NAMA = "spNama";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_ID = "spId";
    public static final String SP_TGLDAFTAR = "spTgl";
    public static final String SP_NPWPD = "spNpwpd";
    public static final String SP_ALAMAT = "spAlamat";
    public static final String SP_SUDAH_LOGIN = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setDarkMode(getWindow());
        setContentView(R.layout.activity_main);
        sharedPrefManager = new SharedPrafManager(this);
        CVPesan = findViewById(R.id.pesan);
        CVLappajak= findViewById(R.id.lappajak);
        CVObjekpajak = findViewById(R.id.objekpajak);
        CVBantuan = findViewById(R.id.bantu);
        CVTagihan = findViewById(R.id.tagihan);
        CVProfile = findViewById(R.id.profile);
        // buat cek password
        mContext = this;
        mApiService = koneksi.getAPIService();
        cekpassword();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.navigationHome);
        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });

        CVPesan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent tentang = new Intent(MainActivity.this, PilihPesan.class);
                        startActivity(tentang);
                    }
                }
        );

        CVLappajak.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent tentang = new Intent(MainActivity.this, PilihUpload.class);
                        startActivity(tentang);
                    }
                }
        );

        CVObjekpajak.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent tentang = new Intent(MainActivity.this, objekpajakdata.class);
                        startActivity(tentang);
                    }
                }
        );

        CVBantuan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent tentang = new Intent(MainActivity.this, bantuan.class);
                        startActivity(tentang);
                    }
                }
        );

        CVTagihan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent tentang = new Intent(MainActivity.this, Tagihan.class);
                        startActivity(tentang);
                    }
                }
        );

        CVProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent tentang = new Intent(MainActivity.this, Profile.class);
                        startActivity(tentang);
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
            verifyExit();
        } else {
            finish();

        }
    }

    private void verifyExit() {
        new MaterialStyledDialog.Builder(this)
                .setTitle("Yakin Logout?")
                .setDescription("Anda akan keluar dari sesi.\n" +
                        "Klik Ya untuk logout!")
                .setIcon(R.drawable.li_exit)
                .setHeaderDrawable(R.drawable.bg_gradient_bluesea)
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
                .setNegativeText("Kembali")
                .show();
    }

    public void endSession() {
        sharedPrefManager.saveSPBoolean(sharedPrefManager.SP_SUDAH_LOGIN, false);
        startActivity(new Intent(MainActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationHome:
                    return true;
                case  R.id.navigationAbout:
                    Intent tentang = new Intent(MainActivity.this, Tentang.class);
                    startActivity(tentang);
                    return true;
                case  R.id.navigationMenu:
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.openDrawer(GravityCompat.START);
                    return true;
            }
            return false;
        }
    };

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_laporan) {
            Intent navdirect = new Intent(MainActivity.this, PilihUpload.class);
            startActivity(navdirect);
        } else if (id == R.id.nav_titiksumur) {
            Intent navdirect = new Intent(MainActivity.this, objekpajakdata.class);
            startActivity(navdirect);
        } else if (id == R.id.nav_bantu) {
            Intent navdirect = new Intent(MainActivity.this, bantuan.class);
            startActivity(navdirect);
        } else if (id == R.id.nav_tagihan) {
            Intent navdirect = new Intent(MainActivity.this, Tagihan.class);
            startActivity(navdirect);
        } else if (id == R.id.nav_profile) {
            Intent navdirect = new Intent(MainActivity.this, Profile.class);
            startActivity(navdirect);
        } else if (id == R.id.nav_pesan) {
            Intent navdirect = new Intent(MainActivity.this, PilihPesan.class);
            startActivity(navdirect);
        } else if (id == R.id.nav_dark_mode) {
         /*   DarkModePrefManager darkModePrefManager = new DarkModePrefManager(this);
            darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode());
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate();*/
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //create a seperate class file, if required in multiple activities
    public void setDarkMode(Window window){
        if(new DarkModePrefManager(this).isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            changeStatusBar(MODE_DARK,window);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            changeStatusBar(MODE_LIGHT,window);
        }
    }
    public void changeStatusBar(int mode, Window window){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
            //Light mode
            if(mode==MODE_LIGHT){
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    // Buat time out session
    public static final long DISCONNECT_TIMEOUT = 5 * 60 * 1000; // 30 sec = 30 * 1000 ms
    private Handler disconnectHandler = new Handler() {
        public void handleMessage(Message msg) {
        }
    };

    private Runnable disconnectCallback = new Runnable() {
        @Override
        public void run() {

            // Perform any required operation on disconnect
            new MaterialStyledDialog.Builder(MainActivity.this)
                    .setTitle("Session Abis")
                    .setDescription("Anda akan keluar otomatis.\n" +
                            "Klik Ya untuk logout!")
                    .setIcon(R.drawable.question)
                    .setHeaderDrawable(R.drawable.bg_gradient_bluesea)
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
    };

    public void resetDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
    }

    public void stopDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback);
    }

    @Override
    public void onUserInteraction() {
        resetDisconnectTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        resetDisconnectTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopDisconnectTimer();
    }



    private void cekpassword(){
        email = sharedPrefManager.getSPEmail();
        mApiService.CekPassword(email).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call< ResponseBody > call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try{
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("success").equalsIgnoreCase("0")) {
                            //
                            String message = jsonRESULTS.getString("message");
                            editpassword(message);
                        }else{
                            //Toast.makeText(mContext, "Halo, harap ganti password secara berkala." ,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                } else{
                    Toast.makeText(mContext, "Response Gagal Memanggil" ,Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure (Call<ResponseBody> call, Throwable t){
                Toast.makeText(mContext, "Tak Ada Koneksi Internet" ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editpassword(String message) {
        new MaterialStyledDialog.Builder(this)
                .setTitle("Akun kamu tidak aman")
                .setDescription(message)
                .setIcon(R.drawable.li_bantuan)
                .setHeaderDrawable(R.drawable.bg_gradient_bluesea)
                .withDialogAnimation(true)
                .withIconAnimation(true)
                .setCancelable(false)
                .setPositiveText("Ya")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startActivity(new Intent(mContext, GantiProfile.class));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
                    }
                })
                .setNegativeText("Kembali")
                .show();
    }
}

