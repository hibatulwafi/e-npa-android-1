package com.sukabumikota.sipajar.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sukabumikota.sipajar.MainActivity;
import com.sukabumikota.sipajar.R;

import com.sukabumikota.sipajar.login.SharedPrafManager;

public class Profile extends AppCompatActivity {
    TextView tvprofileusername,tvprofiletgldaftar,tvprofilenama,tvprofilnpwpd,tvprofileusernameinfo,tvprofilealamat,tvprofiledaftar;
    SharedPrafManager sharedPrefManager; // ini

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPrefManager = new SharedPrafManager(this); // ini
        tvprofileusername = findViewById(R.id.txtprofileusername);
        tvprofiletgldaftar = findViewById(R.id.txtprofiletgldaftar);
        tvprofilenama = findViewById(R.id.txtprofilenama);
        tvprofilnpwpd = findViewById(R.id.txtprofilnpwpd);
        tvprofileusernameinfo = findViewById(R.id.txtprofileusernameinfo);
        tvprofilealamat = findViewById(R.id.txtprofilealamat);
        tvprofiledaftar = findViewById(R.id.txtprofiledaftar);

        tvprofileusername.setText(sharedPrefManager.getSPEmail());
        tvprofiletgldaftar.setText(sharedPrefManager.getSPTgldaftar());
        tvprofilenama.setText(sharedPrefManager.getSPNama());
        tvprofilnpwpd.setText(sharedPrefManager.getSPNpwpd());
        tvprofileusernameinfo.setText(sharedPrefManager.getSPEmail());
        tvprofilealamat.setText(sharedPrefManager.getSPAlamat());
        tvprofiledaftar.setText(sharedPrefManager.getSPTgldaftar());
    }
    public void onBackClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void edit(View view) {
        startActivity(new Intent(this, GantiProfile.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}
