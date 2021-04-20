package com.sukabumikota.sipajar.login;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrafManager {

  public static final String SP_PAJAK_APP = "spPajak";
  public static final String SP_NAMA = "spNama";
  public static final String SP_EMAIL = "spEmail";
  public static final String SP_ID = "spId";
  public static final String SP_TGLDAFTAR = "spTgl";
  public static final String SP_NPWPD = "spNpwpd";
  public static final String SP_ALAMAT = "spAlamat";
  public static final String SP_SUDAH_LOGIN = "spSudahLogin";

  SharedPreferences sp;
  SharedPreferences.Editor spEditor;

  public SharedPrafManager(Context context){
    sp = context.getSharedPreferences(SP_PAJAK_APP, Context.MODE_PRIVATE);
    spEditor = sp.edit();
  }

  public void saveSPString(String keySP, String value){
    spEditor.putString(keySP, value);
    spEditor.commit();
  }

  public void saveSPInt(String keySP, int value){
    spEditor.putInt(keySP, value);
    spEditor.commit();
  }

  public void saveSPBoolean(String keySP, boolean value){
    spEditor.putBoolean(keySP, value);
    spEditor.commit();
  }

  public String getSPNama(){
    return sp.getString(SP_NAMA, "");
  }
  public String getSPEmail(){
    return sp.getString(SP_EMAIL, "");
  }
  public String getSPId(){
    return sp.getString(SP_ID, "");
  }
  public String getSPTgldaftar(){
    return sp.getString(SP_TGLDAFTAR, "");
  }
  public String getSPNpwpd(){
    return sp.getString(SP_NPWPD, "");
  }
  public String getSPAlamat(){
    return sp.getString(SP_ALAMAT, "");
  }
  public Boolean getSPSudahLogin(){
    return sp.getBoolean(SP_SUDAH_LOGIN, false);
  }
}
