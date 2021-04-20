package com.sukabumikota.sipajar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sukabumikota.sipajar.R;
import com.sukabumikota.sipajar.model.SemuaPesanItem;
import com.sukabumikota.sipajar.pesan.PesanDetail;

import java.util.List;

public class PesanAdapter extends RecyclerView.Adapter<PesanAdapter.PesanHolder>{
    List<SemuaPesanItem> semuapesanItemList;
    Context mContext;

    public PesanAdapter(Context context, List<SemuaPesanItem> pesanlist){
        this.mContext = context;
        semuapesanItemList = pesanlist;
    }
    @NonNull
    @Override
    public PesanAdapter.PesanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rvpesan, parent, false);
        return new PesanAdapter.PesanHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull PesanAdapter.PesanHolder holder, int position){
        final SemuaPesanItem semuapesanitem = semuapesanItemList.get(position);
        if (semuapesanitem.getPesan_id() == null){
            //finish();
            Toast.makeText(mContext, "Data Kosong", Toast.LENGTH_SHORT).show();
        }else {
            holder.txtpengirim.setText(semuapesanitem.getPengirim());
            holder.txttanggal.setText(semuapesanitem.getTanggal());
            holder.txtsubjek.setText(semuapesanitem.getPesan_subjek());
        }

        holder.cv_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToDetail = new Intent(mContext, PesanDetail.class);
                moveToDetail.putExtra(PesanDetail.EXTRA_DATA, semuapesanitem);
                mContext.startActivity(moveToDetail);
            }
        });
    }
    @Override
    public int getItemCount(){
        return semuapesanItemList.size();
    }

    public class PesanHolder extends RecyclerView.ViewHolder {
        public TextView txtpengirim, txtsubjek,txttanggal;
        CardView cv_pesan;
        public PesanHolder(@NonNull View itemView){
            super(itemView);
            txtpengirim = itemView.findViewById(R.id.tvpesanpengirim);
            txttanggal = itemView.findViewById(R.id.tvpesantanggal);
            txtsubjek = itemView.findViewById(R.id.tvpesansubjek);
            cv_pesan = itemView.findViewById(R.id.cvpesan);
        }
    }

}