package com.sukabumikota.sipajar.apihelper;

import com.sukabumikota.sipajar.model.ResponseObjekPajak;
import com.sukabumikota.sipajar.model.ResponsePesan;
import com.sukabumikota.sipajar.model.ResponseStatusLaporan;
import com.sukabumikota.sipajar.model.ResponseTagihan;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BaseApiService {



    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("editprofile")
    Call<ResponseBody> editRequest(@Field("id") String id,
                                   @Field("email") String email,
                                   @Field("nama") String nama,
                                   @Field("password_old") String password_old,
                                   @Field("password_new") String password_new,
                                   @Field("repassword_new") String repassword_new,
                                   @Field("alamat") String alamat);

    @FormUrlEncoded
    @POST("objekpajak")
    Call<ResponseObjekPajak> getObjekPajak(@Field("wp_id") String wp_id);

    @GET("tagihan/{id}")
    Call<ResponseTagihan> getTagihan(@Path("id") String id);

    @GET("tagihanop/{op}/{wp}")
    Call<ResponseTagihan> getTagihanOP(@Path("op") String op,@Path("wp") String wp);

    @FormUrlEncoded
    @POST("caritagihan")
    Call<ResponseTagihan> CariTagihan(@Field("query") String query,
                                         @Field("wp_id") String wp_id
    );

    @FormUrlEncoded
    @POST("uploadimage")
    Call<ResponseBody> getUpload(
            @Field("name") String name,
            @Field("image") String image,
            @Field("op_id") String op_id,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude
    );

    @FormUrlEncoded
    @POST("uploadImageDet")
    Call<String> getUploadDet(
            @Field("name") String name,
            @Field("image") String image,
            @Field("op_id") String op_id,
            @Field("path") String path,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("meteran") String meteran,
            @Field("keterangan") String keterangan
            );

    @FormUrlEncoded
    @POST("statuslaporan")
    Call<ResponseStatusLaporan> getStatusLaporan(@Field("wp_id") String wp_id);

    @FormUrlEncoded
    @POST("caristatuslaporan")
    Call<ResponseStatusLaporan> CariStatusLaporan(@Field("query") String query,
                                 @Field("wp_id") String wp_id
    );

    @GET("pesanmasuk/{id}")
    Call<ResponsePesan> getPesan(@Path("id") String id);

    @GET("pesankeluar/{id}")
    Call<ResponsePesan> getPesanKeluar(@Path("id") String id);

    @FormUrlEncoded
    @POST("kirimpesan")
    Call<ResponseBody> KirimPesan(@Field("pesan_isi") String pesan_isi,
                                      @Field("pesan_subjek") String pesan_subjek,
                                      @Field("pengirim") String pengirim,
                                      @Field("penerima") String penerima
                                      );
    @FormUrlEncoded
    @POST("kirimpesankeluar")
    Call<ResponseBody> KirimPesanKeluar(@Field("pesan_isi") String pesan_isi,
                                  @Field("pesan_subjek") String pesan_subjek,
                                  @Field("pengirim") String pengirim
    );

    @FormUrlEncoded
    @POST("caripesan")
    Call<ResponsePesan> CariPesan(@Field("query") String query,
                                  @Field("wp_id") String wp_id
    );

    @FormUrlEncoded
    @POST("caripesankeluar")
    Call<ResponsePesan> CariPesanKeluar(@Field("query") String query,
                                  @Field("wp_id") String wp_id
    );

    @FormUrlEncoded
    @POST("cekpassword")
    Call<ResponseBody> CekPassword(@Field("email") String email);
}