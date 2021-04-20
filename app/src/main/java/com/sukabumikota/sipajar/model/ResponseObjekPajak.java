package com.sukabumikota.sipajar.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseObjekPajak {

    @SerializedName("objekpajak")
    private List<SemuaObjekPajakItem> objekpajak;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public List<SemuaObjekPajakItem> getObjekpajak() {
        return objekpajak;
    }

    public void setObjekpajak(List<SemuaObjekPajakItem> objekpajak) {
        this.objekpajak = objekpajak;
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
                "ResponseObjekPajak{" +
                        "objekpajak = '" +objekpajak + '\'' +
                        ",error = '" +error + '\'' +
                        ",message = '" +message + '\''+
                        "}";
    }
}
