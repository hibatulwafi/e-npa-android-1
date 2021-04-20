package com.sukabumikota.sipajar.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SemuaTagihanItem implements Parcelable{
    @SerializedName("tg_id")
    private String tg_id;
    @SerializedName("op_nama")
    private String op_nama;
    @SerializedName("nama_wajib_pajak")
    private String nama_wajib_pajak;
    @SerializedName("bulan")
    private String bulan;
    @SerializedName("tahun")
    private String tahun;
    @SerializedName("tarif")
    private String tarif;
    @SerializedName("denda")
    private String denda;
    @SerializedName("total_tagihan")
    private String total_tagihan;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("status")
    private String status;
    @SerializedName("pemakaian")
    private String pemakaian;

    public String getPemakaian() {
        return pemakaian;
    }

    public void setPemakaian(String pemakaian) {
        this.pemakaian = pemakaian;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTg_id() {
        return tg_id;
    }

    public void setTg_id(String tg_id) {
        this.tg_id = tg_id;
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

    public String getBulan() { return bulan;}
    public void setBulan(String bulan) { this.bulan = bulan; }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }



    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public String getDenda() {
        return denda;
    }

    public void setDenda(String denda) {
        this.denda = denda;
    }

    public String getTotal_tagihan() {
        return total_tagihan;
    }

    public void setTotal_tagihan(String total_tagihan) {
        this.total_tagihan = total_tagihan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString(){
        return "tagihan{" +
                "tg_id = '" +tg_id + '\'' +
                ",op_nama = '" +op_nama + '\'' +
                ",nama_wajib_pajak = '" +nama_wajib_pajak + '\'' +
                ",tarif = '" +tarif + '\'' +
                ",denda = '" +denda + '\'' +
                ",total_tagihan = '" +total_tagihan + '\'' +
                ",alamat = '" +alamat + '\'' +
                ",status = '" +status + '\'' +
                ",pemakaian = '" +pemakaian + '\'' +
                ",bulan = '" +bulan + '\'' +
                ",tahun = '" +tahun + '\'' +
                "}";

    }

    protected SemuaTagihanItem(Parcel in) {
        this.tg_id = in.readString();
        this.op_nama = in.readString();
        this.nama_wajib_pajak = in.readString();
        this.tarif = in.readString();
        this.denda = in.readString();
        this.total_tagihan = in.readString();
        this.alamat = in.readString();
        this.status = in.readString();
        this.pemakaian = in.readString();
        this.bulan = in.readString();
        this.tahun = in.readString();
    }

    public static final Parcelable.Creator<SemuaTagihanItem> CREATOR = new Parcelable.Creator<SemuaTagihanItem>() {
        @Override
        public SemuaTagihanItem createFromParcel(Parcel in) {
            return new SemuaTagihanItem(in);
        }

        @Override
        public SemuaTagihanItem[] newArray(int size) {
            return new SemuaTagihanItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tg_id);
        dest.writeString(this.op_nama);
        dest.writeString(this.nama_wajib_pajak);
        dest.writeString(this.tarif);
        dest.writeString(this.denda);
        dest.writeString(this.total_tagihan);
        dest.writeString(this.alamat);
        dest.writeString(this.status);
        dest.writeString(this.pemakaian);
        dest.writeString(this.bulan);
        dest.writeString(this.tahun);

    }
}
