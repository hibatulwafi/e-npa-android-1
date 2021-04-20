package com.sukabumikota.sipajar.objekpajak;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sukabumikota.sipajar.R;
import com.sukabumikota.sipajar.adapter.TagihanAdapter;
import com.sukabumikota.sipajar.apihelper.BaseApiService;
import com.sukabumikota.sipajar.apihelper.koneksi;
import com.sukabumikota.sipajar.dialog.InternetDialogActivity;
import com.sukabumikota.sipajar.login.SharedPrafManager;
import com.sukabumikota.sipajar.model.ResponseTagihan;
import com.sukabumikota.sipajar.model.SemuaObjekPajakItem;
import com.sukabumikota.sipajar.model.SemuaTagihanItem;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OPFragment2 extends Fragment {
    public static final String EXTRA_DATA= "extra_data";
    ProgressDialog loading;
    Context mContext;
    List<SemuaTagihanItem> semuatagihanItemList = new ArrayList<>();
    TagihanAdapter tagihanAdapter;
    BaseApiService mApiiService;
    RecyclerView recyclerView;
    String wp_id;
    SharedPrafManager sharedPrefManager; // ini
    String op_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_op2, container, false);
        recyclerView = rootView.findViewById(R.id.rvtagihan);
        sharedPrefManager = new SharedPrafManager(getActivity()); // ini
        mContext = getActivity();
        mApiiService = koneksi.getAPIService();
        tagihanAdapter = new TagihanAdapter(getActivity(), semuatagihanItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        SemuaObjekPajakItem semuaobjekpajakitem = getActivity().getIntent().getParcelableExtra(EXTRA_DATA);

        op_id = semuaobjekpajakitem.getOp_id();
        getResultListTagihan(op_id);


        return rootView;
    }
    private void getResultListTagihan(String op_id) {
        loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu.....", true, false);
        wp_id = sharedPrefManager.getSPId();
        mApiiService.getTagihanOP(op_id,wp_id).enqueue(new Callback<ResponseTagihan>() {
            @Override
            public void onResponse(Call<ResponseTagihan> call, Response<ResponseTagihan> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    Boolean error = response.body().isError();
                    semuatagihanItemList = response.body().getTagihan();
                    if(error.equals(true)){
                        String msg = "Tidak ada data !";
                        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
                    }else{
                        if(semuatagihanItemList==null){
                            String msg = "Kesalahan Mengambil Data";
                            Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
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
                Intent noinet = new Intent(getActivity(), InternetDialogActivity.class);
                startActivity(noinet);
            }
        });
    }
}
