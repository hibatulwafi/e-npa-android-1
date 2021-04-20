package com.sukabumikota.sipajar.objekpajak;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.sukabumikota.sipajar.R;
import com.sukabumikota.sipajar.model.SemuaObjekPajakItem;

import org.jetbrains.annotations.Nullable;


public class OPFragment extends Fragment {
    public static final String EXTRA_DATA= "extra_data";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_op, container, false);
       //Button buttonInFragment1 = rootView.findViewById(R.id.button_1);
        TextView txtopalamat = rootView.findViewById(R.id.txtopalamat);
        TextView txtnoipat = rootView.findViewById(R.id.txtnoipat);
        TextView txtnamawp = rootView.findViewById(R.id.txtnamawp);
        TextView txtlat = rootView.findViewById(R.id.txtlat);
        TextView txtlong = rootView.findViewById(R.id.txtlong);
        TextView txtjatuh = rootView.findViewById(R.id.tctjatuh);

        SemuaObjekPajakItem semuaobjekpajakitem = getActivity().getIntent().getParcelableExtra(EXTRA_DATA);
        txtopalamat.setText(semuaobjekpajakitem.getOp_alamat());
        txtnoipat.setText(semuaobjekpajakitem.getNomor_ipat());
        txtnamawp.setText(semuaobjekpajakitem.getNama_wajib_pajak());
        txtlat.setText(semuaobjekpajakitem.getLatitude());
        txtlong.setText(semuaobjekpajakitem.getLongitude());
        txtjatuh.setText(semuaobjekpajakitem.getTanggal());
        return rootView;
}
}
