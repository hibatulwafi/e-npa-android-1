package com.sukabumikota.sipajar.lappajak;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.jaeger.library.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.sukabumikota.sipajar.R;
import com.sukabumikota.sipajar.apihelper.BaseApiService;
import com.sukabumikota.sipajar.apihelper.koneksi;
import com.sukabumikota.sipajar.login.SharedPrafManager;
import com.sukabumikota.sipajar.model.SemuaObjekPajakItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DenganDetail extends AppCompatActivity {
    SharedPrafManager sharedPrefManager;
    Context mContext;
    private Button btnupload;
    private ImageView imageView;
    EditText editTextMeteran, editTextKeterangan;
    TextView tvLongitude, tvLatitude,tvOp;

    private FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    protected Location mLastLocation;
    String op_id;
    ProgressDialog loading;
    private final int code = 100, code2 = 200, code3 = 300;
    private final int GALLERY = 1;
    private final int CAMERA = 2;

    Bitmap bitmap;
    public static final String EXTRA_DATA= "extra_data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dengan_detail);
        SemuaObjekPajakItem allResponsePajak = getIntent().getParcelableExtra(EXTRA_DATA);
        sharedPrefManager = new SharedPrafManager(this);
        btnupload = findViewById(R.id.uploadImg);
        imageView = findViewById(R.id.imageView);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);
        editTextMeteran = findViewById(R.id.editTextMeteran);
        editTextKeterangan = findViewById(R.id.editTextKeterangan);
        tvOp = findViewById(R.id.op);
        tvOp.setText("Objek Pajak "+allResponsePajak.getOp_nama());
        op_id=allResponsePajak.getOp_id();
        ButterKnife.bind(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getDrawable() == null) {
                    Toast.makeText(DenganDetail.this, "Gambar jangan kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    tanyaKirim();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihDialog(code);
            }
        });
    }

    private void pilihDialog(int codePilih) {

        if (codePilih == 100) {
            new MaterialStyledDialog.Builder(this)
                    .setTitle("Ambil Gambar")
                    .setDescription("Harus Jelas yah, Agar mempermudah pengecekan")
                    .setIcon(R.drawable.ic_launcher_background)
                    .setHeaderColor(R.color.colorPrimary)
                    .withDialogAnimation(true)
                    .withIconAnimation(true)
                    .setCancelable(true)

                    .setNegativeText("Camera")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intentCamera, CAMERA);
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imageView.setImageBitmap(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(DenganDetail.this, "Gagal!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            if (data != null) {
                bitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private void uploadImage(Bitmap bitmap){
        loading = ProgressDialog.show(this, null,
                "Harap Tunggu...", true, false);

        String pathchild= sharedPrefManager.getSPEmail();
        String longitude = tvLongitude.getText().toString();
        String latitude = tvLatitude.getText().toString();
        String path = "storage/"+pathchild+"/";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(koneksi.BASE_URL_API)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        BaseApiService api = retrofit.create(BaseApiService.class);
        String op_idd = op_id;
        Log.d("helloo",encodedImage +"   >>"+imgname);
        Log.d("imggggg","   >>"+imgname);
        Call<String> call = api.getUploadDet(imgname, encodedImage,op_idd, path, longitude,latitude,editTextMeteran.getText().toString(),editTextKeterangan.getText().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                assert response.body() != null;

                if (response.isSuccessful()) {
                    loading.dismiss();

                    if (response.body() != null) {
                        Log.i("onSuccessimg", response.body().toString());
                        Toast.makeText(DenganDetail.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonRESULTS = new JSONObject(response.body().toString());
                            String message = jsonRESULTS.getString("message");
                            messagebox(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");
                        Toast.makeText(DenganDetail.this,"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loading.dismiss();
                onNothingData("Tidak Ada Akses Internet");
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            requestPermissions();
            getLastLocation();

        }

    }
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();

                            tvLatitude.setText(String.format(String.valueOf(mLastLocation.getLatitude())));
                            tvLongitude.setText(String.format(String.valueOf(mLastLocation.getLongitude())));
                        } else {
                            showSnackbar(getString(R.string.no_location_detected));
                        }
                    }
                });


    }

    private void showSnackbar(final String text) {
        View container = findViewById(R.id.main_activity_container);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(DenganDetail.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "Semua permission disetujui!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Belum semua permission disetujui!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            Toast.makeText(getApplicationContext(), "Ditolak semua!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Sesuatu yang salah! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();

        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        if (shouldProvideRationale) {

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {

                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }

    public void tanyaKirim(){
        new MaterialStyledDialog.Builder(this)
                .setTitle("Yakin Data Akan Dikirim?")
                .setDescription("Anda akan mengirim laporan.\n" +
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
                        uploadImage(bitmap);
                    }
                })
                .setNegativeText("Kembali")
                .show();
    }

    private void onNothingData(String message) {
        new MaterialStyledDialog.Builder(mContext)
                .setTitle(message)
                .setDescription("")
                .setIcon(R.drawable.question)
                .setHeaderDrawable(R.drawable.bg_gradient_orange)
                .withDialogAnimation(true)
                .withIconAnimation(true)
                .setCancelable(false)
                .setNegativeText("OK")
                .show();
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
                        finishAffinity();
                        System.exit(0);
                    }
                })
                .setNegativeText("Kembali")
                .show();
    }
}