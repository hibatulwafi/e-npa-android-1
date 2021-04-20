package com.sukabumikota.sipajar.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseStatusLaporan {

    @SerializedName("statuslaporan")
    private List<SemuaStatusLaporanItem> statuslaporan;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public List<SemuaStatusLaporanItem> getStatuslaporan() {
        return statuslaporan;
    }

    public void setStatuslaporan(List<SemuaStatusLaporanItem> statuslaporan) {
        this.statuslaporan = statuslaporan;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    @Override
    public String toString(){
        return
                "ResponseStatusLaporan{" +
                        "statuslaporan = '" +statuslaporan + '\'' +
                        ",error = '" +error + '\'' +
                        ",message = '" +message + '\''+
                        "}";
    }
}
