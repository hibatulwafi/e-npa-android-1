package com.sukabumikota.sipajar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sukabumikota.sipajar.R;
import com.sukabumikota.sipajar.apihelper.koneksi;
import com.sukabumikota.sipajar.model.SemuaTagihanItem;
import com.sukabumikota.sipajar.tagihan.TagihanDetail;

import java.util.List;

public class TagihanAdapter extends RecyclerView.Adapter<TagihanAdapter.TagihanHolder>{
    List<SemuaTagihanItem> semuatagihanItemList;
    Context mContext;

    public TagihanAdapter(Context context, List<SemuaTagihanItem> tagihanlist){
        this.mContext = context;
        semuatagihanItemList = tagihanlist;
    }
    @NonNull
    @Override
    public TagihanAdapter.TagihanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rvtagihan, parent, false);
        return new TagihanAdapter.TagihanHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull TagihanAdapter.TagihanHolder holder, int position){
        final SemuaTagihanItem semuatagihanitem = semuatagihanItemList.get(position);
        if (semuatagihanitem.getTg_id().equals("")){
            holder.txtnama.setText("Data Kosong");
            holder.txttanggal.setText("Data Kosong");
            holder.txtstatus.setText("Data Kosong");
        }else {
            String bulan = semuatagihanitem.getBulan();
            holder.txtnama.setText(semuatagihanitem.getOp_nama());
            holder.txttanggal.setText(koneksi.nama_bulan(bulan)+" "+semuatagihanitem.getTahun());
            holder.txtstatus.setText("Rp."+semuatagihanitem.getTotal_tagihan());
        }

        holder.cv_tagihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToDetail = new Intent(mContext, TagihanDetail.class);
                moveToDetail.putExtra(TagihanDetail.EXTRA_DATA, semuatagihanitem);
                mContext.startActivity(moveToDetail);
            }
        });
    }
    @Override
    public int getItemCount(){
        return semuatagihanItemList.size();
    }

    public class TagihanHolder extends RecyclerView.ViewHolder {
        public TextView txtnama, txttanggal,txtstatus;
        CardView cv_tagihan;
        public TagihanHolder(@NonNull View itemView){
            super(itemView);
            txtnama = itemView.findViewById(R.id.tvnamaop);
            txttanggal = itemView.findViewById(R.id.tvtanggal);
            txtstatus = itemView.findViewById(R.id.tvstatus);
            cv_tagihan = itemView.findViewById(R.id.cvtagihan);
        }
    }

}