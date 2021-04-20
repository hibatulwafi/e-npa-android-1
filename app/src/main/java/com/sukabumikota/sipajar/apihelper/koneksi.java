package com.sukabumikota.sipajar.apihelper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class koneksi {
    //public static final String BASE_URL_API = "http://sipajar.sukabumikota.com/api/";
    //public static final String GAMBAR_URL = "http://sipajar.sukabumikota.com/";
    public static final String BASE_URL_API = "http://192.168.43.254/PajakAir/public/api/";
    public static final String GAMBAR_URL = "http://192.168.43.254/PajakAir/public/";
    public static  BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).
                create(BaseApiService.class);
    }
    private static Retrofit retrofit;
    public static Retrofit getApiClient() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL_API).
                addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

    public static String nama_bulan(String bln){
        String bulan = null;
        if(bln.equals("1")){
            bulan="Januari";
        }else if(bln.equals("2")){
            bulan="Februari";
        }else if(bln.equals("3")){
            bulan="Maret";
        }else if(bln.equals("4")){
            bulan="April";
        }else if(bln.equals("5")){
            bulan="Mei";
        }else if(bln.equals("6")){
            bulan="Juni";
        }else if(bln.equals("7")){
            bulan="Juli";
        }else if(bln.equals("8")){
            bulan="Agustus";
        }else if(bln.equals("9")){
            bulan="September";
        }else if(bln.equals("10")){
            bulan="Oktober";
        }else if(bln.equals("11")){
            bulan="November";
        }else if(bln.equals("12")){
            bulan="Desember";
        }
        return bulan;
    }
}
