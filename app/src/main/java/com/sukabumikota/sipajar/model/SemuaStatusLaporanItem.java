package com.sukabumikota.sipajar.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
public class SemuaStatusLaporanItem implements Parcelable {
    @SerializedName("nama")
    private String nama;
    @SerializedName("op_nama")
    private String op_nama;
    @SerializedName("foto_id")
    private String foto_id;
    @SerializedName("foto_bulan")
    private String foto_bulan;
    @SerializedName("foto_tahun")
    private String foto_tahun;
    @SerializedName("status" )
    private String status;
    @SerializedName("npa")
    private String npa;


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getOp_nama() {
        return op_nama;
    }

    public void setOp_nama(String op_nama) {
        this.op_nama = op_nama;
    }

    public String getFoto_id() {
        return foto_id;
    }

    public void setFoto_id(String foto_id) {
        this.foto_id = foto_id;
    }

    public String getFoto_bulan() {
        return foto_bulan;
    }

    public void setFoto_bulan(String foto_bulan) {
        this.foto_bulan = foto_bulan;
    }

    public String getFoto_tahun() {
        return foto_tahun;
    }

    public void setFoto_tahun(String foto_tahun) {
        this.foto_tahun = foto_tahun;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getNpa() {
        return npa;
    }

    public void setNpa(String npa) {
        this.npa = npa;
    }

    @Override
    public String toString(){
        return "statuslaporan{" +
                "nama = '" +nama + '\'' +
                ",foto_id = '" +foto_id + '\'' +
                ",op_nama = '" +op_nama + '\'' +
                ",foto_bulan = '" +foto_bulan + '\'' +
                ",foto_tahun = '" +foto_tahun + '\'' +
                ",status = '" +status + '\'' +
                ",npa = '" +npa + '\'' +
                "}";

    }

    protected SemuaStatusLaporanItem(Parcel in) {
        this.nama = in.readString();
        this.foto_id = in.readString();
        this.op_nama = in.readString();
        this.foto_bulan = in.readString();
        this.foto_tahun = in.readString();
        this.status = in.readString();
        this.npa = in.readString();
    }

    public static final Creator<SemuaStatusLaporanItem> CREATOR = new Parcelable.Creator<SemuaStatusLaporanItem>() {
        @Override
        public SemuaStatusLaporanItem createFromParcel(Parcel in) {
            return new SemuaStatusLaporanItem(in);
        }

        @Override
        public SemuaStatusLaporanItem[] newArray(int size) {
            return new SemuaStatusLaporanItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nama);
        dest.writeString(this.foto_id);
        dest.writeString(this.op_nama);
        dest.writeString(this.foto_bulan);
        dest.writeString(this.foto_tahun);
        dest.writeString(this.status);
        dest.writeString(this.npa);
    }
}