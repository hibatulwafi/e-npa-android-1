package com.sukabumikota.sipajar.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SemuaPesanItem implements Parcelable {
    @SerializedName("pesan_id")
    private String pesan_id;
    @SerializedName("pesan_isi")
    private String pesan_isi;
    @SerializedName("pesan_subjek")
    private String pesan_subjek;
    @SerializedName("id_pengirim")
    private String id_pengirim;
    @SerializedName("pengirim")
    private String pengirim;
    @SerializedName("penerima")
    private String penerima;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("status")
    private String status;

    public String getPesan_id() {
        return pesan_id;
    }

    public void setPesan_id(String pesan_id) {
        this.pesan_id = pesan_id;
    }

    public String getPesan_isi() {
        return pesan_isi;
    }

    public void setPesan_isi(String pesan_isi) {
        this.pesan_isi = pesan_isi;
    }

    public String getPesan_subjek() {
        return pesan_subjek;
    }

    public void setPesan_subjek(String pesan_subjek) {
        this.pesan_subjek = pesan_subjek;
    }

    public String getId_pengirim() {
        return id_pengirim;
    }

    public void setId_pengirim(String id_pengirim) {
        this.id_pengirim = id_pengirim;
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public String getPenerima() {
        return penerima;
    }

    public void setPenerima(String penerima) {
        this.penerima = penerima;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return "pesan{" +
                "pesan_id = '" +pesan_id + '\'' +
                ",pesan_isi = '" +pesan_isi + '\'' +
                ",pesan_subjek = '" +pesan_subjek + '\'' +
                ",id_pengirim = '" +id_pengirim + '\'' +
                ",pengirim = '" +pengirim + '\'' +
                ",penerima = '" +penerima + '\'' +
                ",tanggal = '" +tanggal + '\'' +
                ",status = '" +status + '\'' +
                "}";

    }
    protected SemuaPesanItem(Parcel in) {
        this.pesan_id = in.readString();
        this.pesan_isi = in.readString();
        this.pesan_subjek = in.readString();
        this.id_pengirim = in.readString();
        this.pengirim = in.readString();
        this.penerima = in.readString();
        this.tanggal = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<SemuaPesanItem> CREATOR = new Parcelable.Creator<SemuaPesanItem>() {
        @Override
        public SemuaPesanItem createFromParcel(Parcel in) {
            return new SemuaPesanItem(in);
        }

        @Override
        public SemuaPesanItem[] newArray(int size) {
            return new SemuaPesanItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pesan_id);
        dest.writeString(this.pesan_isi);
        dest.writeString(this.pesan_subjek);
        dest.writeString(this.id_pengirim);
        dest.writeString(this.pengirim);
        dest.writeString(this.penerima);
        dest.writeString(this.tanggal);
        dest.writeString(this.status);

    }
}