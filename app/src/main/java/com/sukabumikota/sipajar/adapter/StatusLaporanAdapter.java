package com.sukabumikota.sipajar.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sukabumikota.sipajar.R;
import com.sukabumikota.sipajar.apihelper.koneksi;
import com.sukabumikota.sipajar.lappajak.DetailLaporan;
import com.sukabumikota.sipajar.model.SemuaStatusLaporanItem;

import java.util.List;

import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class StatusLaporanAdapter extends RecyclerView.Adapter<StatusLaporanAdapter.StatusLaporanHolder>{
    List<SemuaStatusLaporanItem> semuastatuslaporanItemList;
    Context mContext;

    public StatusLaporanAdapter(Context context, List<SemuaStatusLaporanItem> statuslaporanlist){
        this.mContext = context;
        semuastatuslaporanItemList = statuslaporanlist;
    }
    @NonNull
    @Override
    public StatusLaporanAdapter.StatusLaporanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rvstatuslaporan, parent, false);
        return new StatusLaporanAdapter.StatusLaporanHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull StatusLaporanAdapter.StatusLaporanHolder holder, int position){
        final SemuaStatusLaporanItem semuastatuslaporanitem = semuastatuslaporanItemList.get(position);
        if (semuastatuslaporanitem.getFoto_id().equals("")){
            holder.txtnama.setText("Data Kosong");
            holder.txtstatus.setText("Data Kosong");
            holder.txttanggal.setText("Data Kosong");
        }else {
            // Buat Warna
            if (semuastatuslaporanitem.getStatus().equals("Sedang Proses")){
                holder.txtstatus.setTextColor(ContextCompat.getColor(mContext, R.color.dot_inactive_screen1));
            }else if(semuastatuslaporanitem.getStatus().equals("Ditolak")){
                holder.txtstatus.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            }else if(semuastatuslaporanitem.getStatus().equals("Diterima")){
                holder.txtstatus.setTextColor(ContextCompat.getColor(mContext, R.color.color7));
            }else{

            }
            if (semuastatuslaporanitem.getNpa().equals("0")){
                holder.txtnpa.setText("");
            }else{
                holder.txtnpa.setText(semuastatuslaporanitem.getNpa()+" M");
            }
            holder.txtnama.setText(semuastatuslaporanitem.getOp_nama());
            holder.txtstatus.setText(semuastatuslaporanitem.getStatus());
            holder.txttanggal.setText("Bulan : "+ koneksi.nama_bulan(semuastatuslaporanitem.getFoto_bulan())+" - "+semuastatuslaporanitem.getFoto_tahun());
            Glide.with(holder.itemView.getContext())
                    .load(koneksi.GAMBAR_URL
                            + "bukti/"
                            + semuastatuslaporanitem.getNama())
                    .apply(bitmapTransform(new VignetteFilterTransformation(new PointF(0.5f, 0.5f),
                            new float[]{0.0f, 0.0f, 0.0f}, 0.4f, 1.0f)).dontAnimate())
                    .into(holder.img_berita);
        }

        holder.cvop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToDetail = new Intent(mContext, DetailLaporan.class);
                moveToDetail.putExtra(DetailLaporan.EXTRA_DATA, semuastatuslaporanitem);
                mContext.startActivity(moveToDetail);
            }
        });
    }
    @Override
    public int getItemCount(){
        return semuastatuslaporanItemList.size();
    }

    public class StatusLaporanHolder extends RecyclerView.ViewHolder {
        public TextView txtnama, txtstatus,txttanggal,txtnpa;
        CardView cvop;
        ImageView img_berita;
        public StatusLaporanHolder(@NonNull View itemView){
            super(itemView);
            txtnama = itemView.findViewById(R.id.tvnamakode);
            txtstatus = itemView.findViewById(R.id.tvstatus);
            txttanggal = itemView.findViewById(R.id.tvtanggalstatus);
            img_berita = itemView.findViewById(R.id.imgtagihan);
            cvop= itemView.findViewById(R.id.cvtagihan);
            txtnpa = itemView.findViewById(R.id.tvnpa);
        }
    }



}
