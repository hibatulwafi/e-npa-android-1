package com.sukabumikota.sipajar.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTagihan {

    @SerializedName("tagihan")
    private List<SemuaTagihanItem> tagihan;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public List<SemuaTagihanItem> getTagihan() {
        return tagihan;
    }

    public void setTagihan(List<SemuaTagihanItem> tagihan) {
        this.tagihan = tagihan;
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
                "ResponseTagihan{" +
                        "tagihan = '" +tagihan + '\'' +
                        ",error = '" +error + '\'' +
                        ",message = '" +message + '\''+
                        "}";
    }
}
