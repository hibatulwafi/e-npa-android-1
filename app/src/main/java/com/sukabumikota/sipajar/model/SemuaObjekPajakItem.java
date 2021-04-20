package com.sukabumikota.sipajar.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SemuaObjekPajakItem implements Parcelable{
    @SerializedName("op_id")
    private String op_id;
    @SerializedName("op_nama")
    private String op_nama;
    @SerializedName("nama_wajib_pajak")
    private String nama_wajib_pajak;
    @SerializedName("op_alamat")
    private String op_alamat;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("latitude" )
    private String latitude;
    @SerializedName("tanggal_registrasi" )
    private String tanggal_registrasi;
    @SerializedName("nomor_ipat" )
    private String nomor_ipat;
    @SerializedName("tanggal" )
    private String tanggal;
    @SerializedName("status_meteran" )
    private String status_meteran;

    public String getOp_id() {
        return op_id;
    }

    public void setOp_id(String op_id) {
        this.op_id = op_id;
    }

    public String getOp_nama() {
        return op_nama;
    }

    public void setOp_nama(String op_nama) {
        this.op_nama = op_nama;
    }

    public String getNama_wajib_pajak() {
        return nama_wajib_pajak;
    }

    public void setNama_wajib_pajak(String nama_wajib_pajak) {
        this.nama_wajib_pajak = nama_wajib_pajak;
    }

    public String getOp_alamat() {
        return op_alamat;
    }

    public void setOp_alamat(String op_alamat) {
        this.op_alamat = op_alamat;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getTanggal_registrasi() {
        return tanggal_registrasi;
    }

    public void setTanggal_registrasi(String tanggal_registrasi) {
        this.tanggal_registrasi = tanggal_registrasi;
    }

    public String getNomor_ipat() {
        return nomor_ipat;
    }

    public void setNomor_ipat(String nomor_ipat) {
        this.nomor_ipat = nomor_ipat;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus_meteran() {
        return status_meteran;
    }

    public void setStatus_meteran(String status_meteran) {
        this.status_meteran = status_meteran;
    }

    @Override
    public String toString(){
        return "objekpajak{" +
            "op_id = '" +op_id + '\'' +
            ",op_nama = '" +op_nama + '\'' +
            ",nama_wajib_pajak = '" +nama_wajib_pajak + '\'' +
            ",op_alamat = '" +op_alamat + '\'' +
            ",longitude = '" +longitude + '\'' +
            ",latitude = '" +latitude + '\'' +
            ",tanggal_registrasi = '" +tanggal_registrasi + '\'' +
            ",nomor_ipat = '" +nomor_ipat + '\'' +
            ",tanggal = '" +tanggal + '\'' +
            ",status_meteran = '" +status_meteran + '\'' +
            "}";

    }

    protected SemuaObjekPajakItem(Parcel in) {
        this.op_id = in.readString();
        this.op_alamat = in.readString();
        this.op_nama = in.readString();
        this.nama_wajib_pajak = in.readString();
        this.nomor_ipat = in.readString();
        this.tanggal_registrasi = in.readString();
        this.tanggal = in.readString();
        this.status_meteran = in.readString();
        this.longitude = in.readString();
        this.latitude = in.readString();
    }

    public static final Creator<SemuaObjekPajakItem> CREATOR = new Parcelable.Creator<SemuaObjekPajakItem>() {
        @Override
        public SemuaObjekPajakItem createFromParcel(Parcel in) {
            return new SemuaObjekPajakItem(in);
        }

        @Override
        public SemuaObjekPajakItem[] newArray(int size) {
            return new SemuaObjekPajakItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.op_id);
        dest.writeString(this.op_alamat);
        dest.writeString(this.op_nama);
        dest.writeString(this.nama_wajib_pajak);
        dest.writeString(this.nomor_ipat);
        dest.writeString(this.tanggal_registrasi);
        dest.writeString(this.tanggal);
        dest.writeString(this.status_meteran);
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
    }
}