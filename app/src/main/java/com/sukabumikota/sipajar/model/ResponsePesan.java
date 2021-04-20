package com.sukabumikota.sipajar.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePesan{

        @SerializedName("pesan")
        private List<SemuaPesanItem> pesan;

        @SerializedName("error")
        private boolean error;

        @SerializedName("message")
        private String message;

        public List<SemuaPesanItem> getPesan() {
            return pesan;
        }

        public void setPesan(List<SemuaPesanItem> pesan) {
            this.pesan = pesan;
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
                    "ResponsePesan{" +
                            "pesan = '" +pesan + '\'' +
                            ",error = '" +error + '\'' +
                            ",message = '" +message + '\''+
                            "}";
        }
}
