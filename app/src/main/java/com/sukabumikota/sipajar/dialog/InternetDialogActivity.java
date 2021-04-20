package com.sukabumikota.sipajar.dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sukabumikota.sipajar.MainActivity;
import com.sukabumikota.sipajar.R;

public class InternetDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_dialog);

        // CALL getInternetStatus() function to check for internet and display error dialog
      /*  if (new InternetDialog(this).getInternetStatus()) {
            Toast.makeText(this, "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void onClose(View view) {
        Intent back=new Intent(InternetDialogActivity.this, MainActivity.class);
        startActivity(back);
    }
}
