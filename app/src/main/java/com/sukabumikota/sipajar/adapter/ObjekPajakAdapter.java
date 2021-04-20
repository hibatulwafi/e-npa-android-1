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
import com.sukabumikota.sipajar.model.SemuaObjekPajakItem;
import com.sukabumikota.sipajar.objekpajak.OPDetail;

import java.util.List;

public class ObjekPajakAdapter extends RecyclerView.Adapter<ObjekPajakAdapter.ObjekPajakHolder>{
        List<SemuaObjekPajakItem> semuaobjekpajakItemList;
        Context mContext;

public ObjekPajakAdapter(Context context, List<SemuaObjekPajakItem> objekpajaklist){
        this.mContext = context;
        semuaobjekpajakItemList = objekpajaklist;
        }
@NonNull
@Override
public ObjekPajakAdapter.ObjekPajakHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rvobjekpajak, parent, false);
        return new ObjekPajakAdapter.ObjekPajakHolder(itemView);
        }
@Override
public void onBindViewHolder(@NonNull ObjekPajakAdapter.ObjekPajakHolder holder, int position){
    final SemuaObjekPajakItem semuaobjekpajakitem = semuaobjekpajakItemList.get(position);
    if (semuaobjekpajakitem.getOp_id().equals("")){
        holder.txtnama.setText("Data Kosong");
        holder.txtalamat.setText("Data Kosong");
    }else {
        holder.txtnama.setText(semuaobjekpajakitem.getOp_nama());
        holder.txtalamat.setText(semuaobjekpajakitem.getOp_alamat());
    }

    holder.cvop.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           // Intent mIntent = new Intent(view.getContext(), Maintance.class);
          //  view.getContext().startActivity(mIntent);
            Intent moveToDetail = new Intent(mContext, OPDetail.class);
            moveToDetail.putExtra(OPDetail.EXTRA_DATA, semuaobjekpajakitem);
            mContext.startActivity(moveToDetail);
        }
    });
}
@Override
public int getItemCount(){
        return semuaobjekpajakItemList.size();
        }

public class ObjekPajakHolder extends RecyclerView.ViewHolder {
    public TextView txtnama, txtalamat,txtid;
    CardView cvop;
    public ObjekPajakHolder(@NonNull View itemView){
        super(itemView);
        txtnama = itemView.findViewById(R.id.tvnamaop);
        txtalamat = itemView.findViewById(R.id.tvalamatop);
        cvop = itemView.findViewById(R.id.cvop);
    }
}

}